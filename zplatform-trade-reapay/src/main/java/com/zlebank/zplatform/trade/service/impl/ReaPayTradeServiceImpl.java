/* 
 * ReaPayTradeServiceImpl.java  
 * 
 * version TODO
 *
 * 2015年9月16日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.trade.analyzer.ReaPayTradeAnalyzer;
import com.zlebank.zplatform.trade.bean.ReaPayResultBean;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.reapal.BindBean;
import com.zlebank.zplatform.trade.bean.reapal.CreditBean;
import com.zlebank.zplatform.trade.bean.reapal.DebitBean;
import com.zlebank.zplatform.trade.bean.reapal.PayBean;
import com.zlebank.zplatform.trade.bean.reapal.QueryBean;
import com.zlebank.zplatform.trade.bean.reapal.SMSBean;
import com.zlebank.zplatform.trade.security.reapay.Decipher;
import com.zlebank.zplatform.trade.service.IReaPayTradeService;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.HttpUtils;
import com.zlebank.zplatform.trade.utils.Md5Utils;
import com.zlebank.zplatform.trade.utils.ObjectDynamic;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月16日 下午1:03:59
 * @since 
 */
@Service("reaPayTradeService")
public class ReaPayTradeServiceImpl implements IReaPayTradeService{

    private static final Log log = LogFactory.getLog(ReaPayTradeServiceImpl.class);
    
    /**
     *
     * @return
     */
    @Override
    public ResultBean debitCardSign(DebitBean debitBean) {
        ResultBean resultBean = null;
        try {
            String url = ConsUtil.getInstance().cons.getReapay_quickpay_url()+ConsUtil.getInstance().cons.getReapay_quickpay_debit_url();
            String[] unParamstring = {"sign","sign_type"};
            Map<String, String> map = ObjectDynamic.generateMap(debitBean, false, unParamstring);
            String post = buildSubmit(map,url);
            String res = Decipher.decryptData(post);
            log.info("recive reapay decryptData message:"+res);
            ReaPayResultBean reaPayResultBean = ReaPayTradeAnalyzer.generateDebitResultBean(JSON.parseObject(res));
            resultBean = new ResultBean(reaPayResultBean);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.error(e);
            resultBean = new ResultBean("RC99", "交易失败");
        }
        return resultBean;
    }
    
    

    /**
     *
     * @return
     */
    @Override
    public ResultBean creditCardSign(CreditBean creditBean) {
        ResultBean resultBean = null;
        try {
            String url = ConsUtil.getInstance().cons.getReapay_quickpay_url()+ConsUtil.getInstance().cons.getReapay_quickpay_credit_url();
            String[] unParamstring = {"sign","sign_type"};
            Map<String, String> map = ObjectDynamic.generateMap(creditBean, false, unParamstring);
            String post = buildSubmit(map,url);
            String res = Decipher.decryptData(post);
            log.info("recive reapay decryptData message:"+res);
            ReaPayResultBean reaPayResultBean = ReaPayTradeAnalyzer.generateCreditResultBean(JSON.parseObject(res));
            resultBean = new ResultBean(reaPayResultBean);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.error(e);
            resultBean = new ResultBean("RC99", "交易失败");
        }
        return resultBean;
    }

    /**
     *
     * @return
     */
    @Override
    public ResultBean bindListQuery() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public ResultBean bindCard(BindBean bindBean) {
        ResultBean resultBean = null;
        try {
            String url = ConsUtil.getInstance().cons.getReapay_quickpay_url()+ConsUtil.getInstance().cons.getReapay_quickpay_bind_url();
            String[] unParamstring = {"sign","sign_type"};
            Map<String, String> map = ObjectDynamic.generateMap(bindBean, false, unParamstring);
            String post = buildSubmit(map,url);
            String res = Decipher.decryptData(post);
            log.info("recive reapay decryptData message:"+res);
            ReaPayResultBean reaPayResultBean = ReaPayTradeAnalyzer.generateBindResultBean(JSON.parseObject(res));
            resultBean = new ResultBean(reaPayResultBean);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            resultBean = new ResultBean("RC99", "交易失败");
            log.error(e);
        }
        return resultBean;
    }

    /**
     *
     * @return
     */
    @Override
    public ResultBean reSendSms(SMSBean smsBean) {
        ResultBean resultBean = null;
        try {
            String url = ConsUtil.getInstance().cons.getReapay_quickpay_url()+ConsUtil.getInstance().cons.getReapay_quickpay_sms_url();
            String[] unParamstring = {"sign","sign_type"};
            Map<String, String> map = ObjectDynamic.generateMap(smsBean, false, unParamstring);
            String post = buildSubmit(map,url);
            String res = Decipher.decryptData(post);
            log.info("recive reapay decryptData message:"+res);
            ReaPayResultBean reaPayResultBean = ReaPayTradeAnalyzer.generateSMSResultBean(JSON.parseObject(res));
            resultBean = new ResultBean(reaPayResultBean);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.error(e);
            resultBean = new ResultBean("RC99","发送失败");
        }
        return resultBean;
    }

    /**
     *
     * @return
     */
    @Override
    public ResultBean submitPay(PayBean payBean) {
        ResultBean resultBean = null;
        try {
            String url = ConsUtil.getInstance().cons.getReapay_quickpay_url()+ConsUtil.getInstance().cons.getReapay_quickpay_pay_url();
            String[] unParamstring = {"sign","sign_type"};
            Map<String, String> map = ObjectDynamic.generateMap(payBean, false, unParamstring);
            String post = buildSubmit(map,url);
            String res = Decipher.decryptData(post);
            log.info("recive reapay decryptData message:"+res);
            ReaPayResultBean reaPayResultBean = ReaPayTradeAnalyzer.generatePayResultBean(JSON.parseObject(res));
            resultBean = new ResultBean(reaPayResultBean);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.error(e);
            resultBean = new ResultBean("RC99", "交易失败");
        }
        return resultBean;
    }

    /**
     *
     * @return
     */
    @Override
    public ResultBean searchPayResult(QueryBean queryBean) {
        ResultBean resultBean = null;
        try {
            String url = ConsUtil.getInstance().cons.getReapay_quickpay_url()+ConsUtil.getInstance().cons.getReapay_quickpay_query_url();
            String[] unParamstring = {"sign","sign_type"};
            Map<String, String> map = ObjectDynamic.generateMap(queryBean, false, unParamstring);
            String post = buildSubmit(map,url);
            String res = Decipher.decryptData(post);
            log.info("recive reapay decryptData message:"+res);
            ReaPayResultBean reaPayResultBean = ReaPayTradeAnalyzer.generatePayResultBean(JSON.parseObject(res));
            resultBean = new ResultBean(reaPayResultBean);
            resultBean.setErrCode(reaPayResultBean.getStatus());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.error(e);
            resultBean = new ResultBean("RC99", "交易失败");
        }
        return resultBean;
    }

    /**
     *
     * @return
     */
    @Override
    public ResultBean cannelCard() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public ResultBean refund() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public ResultBean queryBankCard() {
        // TODO Auto-generated method stub
        return null;
    }
    private String buildSubmit(Map<String, String> para, String url)
            throws Exception {
        // 生成签名
        String mysign = Md5Utils.BuildMysign(para, ConsUtil.getInstance().cons.getReapay_quickpay_key());
        para.put("sign_type", "MD5");
        para.put("sign", mysign);
        // 将map转化为json 串
        String json = JSON.toJSONString(para);
        log.info("send reapay message:"+json);
        // 加密数据
        Map<String, String> maps = Decipher.encryptData(json);
        maps.put("merchant_id", ConsUtil.getInstance().cons.getReapay_quickpay_merchant_id());
        // 发送请求 并接收
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.openConnection();
        String post = httpUtils.executeReaPaypost(url, maps);;
        log.info("recive reapay message:"+post);
        return post;
    }
    
}
