/* 
 * NotifyInsteadURLService.java  
 * 
 * version TODO
 *
 * 2016年3月30日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.commons.utils.BeanCopyUtil;
import com.zlebank.zplatform.commons.utils.RegExpValidatorUtil;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.commons.utils.security.AESHelper;
import com.zlebank.zplatform.commons.utils.security.AESUtil;
import com.zlebank.zplatform.commons.utils.security.RSAHelper;
import com.zlebank.zplatform.member.bean.CoopInsti;
import com.zlebank.zplatform.member.bean.MerchMK;
import com.zlebank.zplatform.member.bean.enums.MemberType;
import com.zlebank.zplatform.member.pojo.PojoMember;
import com.zlebank.zplatform.member.service.CoopInstiService;
import com.zlebank.zplatform.member.service.MemberService;
import com.zlebank.zplatform.member.service.MerchMKService;
import com.zlebank.zplatform.trade.dao.ConfigInfoDAO;
import com.zlebank.zplatform.trade.dao.InsteadPayBatchDAO;
import com.zlebank.zplatform.trade.dao.InsteadPayDetailDAO;
import com.zlebank.zplatform.trade.insteadPay.message.BaseMessage;
import com.zlebank.zplatform.trade.insteadPay.message.InsteadPayQuery_Request;
import com.zlebank.zplatform.trade.insteadPay.message.InsteadPayQuery_Response;
import com.zlebank.zplatform.trade.model.ConfigInfoModel;
import com.zlebank.zplatform.trade.model.PojoInsteadPayBatch;
import com.zlebank.zplatform.trade.service.InsteadPayService;
import com.zlebank.zplatform.trade.service.NotifyInsteadURLService;
import com.zlebank.zplatform.trade.utils.SpringContext;

/**
 * 代付URL通知类
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年3月30日 下午3:37:04
 * @since 
 */
@Service("notifyInsteadURLService")
public class NotifyInsteadURLServiceImpl implements NotifyInsteadURLService,  ApplicationListener<ContextRefreshedEvent>{

    private static final Log log = LogFactory.getLog(NotifyInsteadURLServiceImpl.class);
    
    @Autowired
    private InsteadPayBatchDAO insteadPayBatchDAO;
    
    @Autowired
    private InsteadPayDetailDAO insteadPayDetailDAO;
    
    @Autowired
    private MemberService memberService;

    @Autowired
    private CoopInstiService coopInstiService;

    //@Autowired
    private InsteadPayService insteadPayService;
    
    @Autowired
    private MerchMKService merchMKService;
    
    @Autowired
    private ConfigInfoDAO configInfoDAO;
    
    // 线程数量
    private static final int THREAD_NUM = 1;
    
    // 初始化线程池
    ExecutorService exec=Executors.newFixedThreadPool(THREAD_NUM);

    // 初始化阻塞队列（10万个）
    private BlockingQueue<InsteadPayNotifyTask> taskQueue = new LinkedBlockingQueue<InsteadPayNotifyTask>(100000);
    
    /**
     * 添加一个代付任务
     * @param batchId
     */
    @Override
    @Transactional
    public void addInsteadPayTask(Long batchId) {
        	 PojoInsteadPayBatch batch = insteadPayBatchDAO.getByBatchId(batchId);
              log.info("代付任务："+batch.getBatchNo());
             // 请求报文
             InsteadPayQuery_Request requestBean = new InsteadPayQuery_Request();
             requestBean.setBatchNo(String.valueOf(batch.getBatchNo()));
             requestBean.setTxnTime(batch.getTxnTime());
             requestBean.setMerId(batch.getMerId());
             PojoMember memberMerch = memberService.getMbmberByMemberId(batch.getMerId(), MemberType.ENTERPRISE);
             CoopInsti insti = coopInstiService.getInstiByInstiID(memberMerch.getInstiId());
             requestBean.setCoopInstiId(insti.getInstiCode()); // 合作机构
             requestBean.setVersion("1.0");
             requestBean.setTxnType("25");
             requestBean.setTxnSubType("01");
             requestBean.setEncoding("1");
             requestBean.setBizType("000205");
             requestBean.setChannelType("00");
             
             // 响应报文
             InsteadPayQuery_Response responseBean = BeanCopyUtil.copyBean(InsteadPayQuery_Response.class, requestBean);
             // 代付查询
             insteadPayService.insteadPayQuery(requestBean, responseBean);
//             String data = responseData(responseBean);
             
             // 如果URL合法，则发送通知，否则不通知。
             if (StringUtil.isEmpty(batch.getNotifyUrl()) || RegExpValidatorUtil.IsUrl(batch.getNotifyUrl())) {
                 // 添加一个代付任务
                 InsteadPayNotifyTask task = new InsteadPayNotifyTask();
                 // 组织报文 TODO: 旧版本兼容
                 List<ConfigInfoModel> configList = configInfoDAO.getConfigListByParaName("INSTEAD_PAY_NOTIFY_MER_NO_V1");
                 boolean isOld = false;
                 for (ConfigInfoModel model : configList) {
                     if (requestBean.getMerId().equals(model.getPara())) {
                         isOld = true;
                     }
                 }
                 if (isOld) {
                     // 旧版本异步通知
                     responseData_V1(responseBean, task);
                 } else {
                     // 新版本异步通知
                     responseData(responseBean,task);    
                 }
                 
                 task.setUrl(batch.getNotifyUrl());
                 taskQueue.add(task);
             } else {
                 log.warn("URL为空或非法URL，无法执行通知任务。InsteadPayBatchSeqNo->"+batch.getInsteadPayBatchSeqNo());
             }

    }
    
    /**
     * 返回数据（同步response）
     * @param responseStream
     * @param bean
     * @param task 
     */
    @SuppressWarnings("unchecked")
    private void responseData(BaseMessage bean, InsteadPayNotifyTask task) {
        if (log.isDebugEnabled()) {
            log.debug("【入参responseData】"+JSONObject.fromObject(bean));
        }
        JSONObject jsonData = JSONObject.fromObject(bean);
        // 排序
        Map<String, Object> map = new TreeMap<String, Object>();
        map =(Map<String, Object>) JSONObject.toBean(jsonData, TreeMap.class);
        jsonData = JSONObject.fromObject(map);
        
        JSONObject addit = new JSONObject();
        addit.put("accessType", "1");
        addit.put("coopInstiId", bean.getCoopInstiId());
        addit.put("merId", bean.getMerId());
        MerchMK merchMk = merchMKService.get(addit.getString("merId"));
        RSAHelper rsa = new RSAHelper(merchMk.getMemberPubKey(), merchMk.getLocalPriKey());
        String aesKey = null;
        try {
            aesKey = AESUtil.getAESKey();
            if (log.isDebugEnabled()) {
                log.debug("【AES KEY】" + aesKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        addit.put("encryKey", rsa.encrypt(aesKey));
        addit.put("encryMethod", "01");

        // 加签名
        StringBuffer originData = new StringBuffer(addit.toString());//业务数据
        originData.append(jsonData.toString());// 附加数据
        if (log.isDebugEnabled()) {
            log.debug("【应答报文】加签用字符串：" + originData.toString());
        }
        // 加签
        String sign = rsa.sign(originData.toString());
        AESHelper packer = new AESHelper(aesKey);
        JSONObject rtnSign = new JSONObject();
        rtnSign.put("signature", sign);
        rtnSign.put("signMethod", "01");
        
        // 业务数据
        task.setData(packer.pack(jsonData.toString()));
        // 附加数据
        task.setAddit(addit.toString());
        // 签名数据
        task.setSign(rtnSign.toString());
        if (log.isDebugEnabled()) {
            log.debug("【发送报文数据】【业务数据】："+task.getData());
            log.debug("【发送报文数据】【附加数据】："+task.getAddit());
            log.debug("【发送报文数据】【签名数据】："+ task.getSign());
        }
    }
    
    /**
     * 返回数据（同步response）【旧版本】
     * @param responseStream
     * @param bean
     */
    @SuppressWarnings("unchecked")
    private void responseData_V1(BaseMessage bean, InsteadPayNotifyTask task) {
        JSONObject jsonData = JSONObject.fromObject(bean);
        jsonData.put("signature", "");
        // 排序
        Map<String, Object> map = new TreeMap<String, Object>();
        map =(Map<String, Object>) JSONObject.toBean(jsonData, TreeMap.class);
        jsonData = JSONObject.fromObject(map);

        // 加签名
        if (log.isDebugEnabled()) {
            log.debug("【应答报文】加签用ID：" + bean.getMerId());
            log.debug("【应答报文】加签原数据：" + jsonData.toString());
        }
        String signature  = merchMKService.sign(bean.getMerId(), jsonData.toString());
        jsonData.put("signature", signature);
        // 返回数据
        if (log.isDebugEnabled()) {
            log.debug("【应答报文】返回数据："+jsonData.toString());
        }
        // 业务数据
        task.setData(jsonData.toString());
    }

    /**
     * 代付
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 并发N个线程执行
        for (int index = 0; index < THREAD_NUM; index++) {
            exec.execute(new ExecuteInsteadPayTaskQueue(taskQueue));
        }
//        exec.shutdown();
    }
}
