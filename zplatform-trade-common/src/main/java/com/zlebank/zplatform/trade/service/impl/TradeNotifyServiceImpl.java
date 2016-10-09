/* 
 * TradeNotifyServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年7月21日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.commons.utils.RSAUtils;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.commons.utils.security.AESHelper;
import com.zlebank.zplatform.commons.utils.security.AESUtil;
import com.zlebank.zplatform.commons.utils.security.RSAHelper;
import com.zlebank.zplatform.member.bean.MerchMK;
import com.zlebank.zplatform.member.bean.enums.TerminalAccessType;
import com.zlebank.zplatform.member.service.CoopInstiMKService;
import com.zlebank.zplatform.member.service.CoopInstiService;
import com.zlebank.zplatform.member.service.MerchMKService;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.enums.TradeQueueEnum;
import com.zlebank.zplatform.trade.bean.gateway.AnonOrderAsynRespBean;
import com.zlebank.zplatform.trade.bean.gateway.OrderAsynRespBean;
import com.zlebank.zplatform.trade.bean.queue.NotifyQueueBean;
import com.zlebank.zplatform.trade.dao.ITxnsOrderinfoDAO;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsNotifyTaskModel;
import com.zlebank.zplatform.trade.model.TxnsOrderinfoModel;
import com.zlebank.zplatform.trade.pool.AsyncNotifyThreadPool;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.ITxnsNotifyTaskService;
import com.zlebank.zplatform.trade.service.TradeNotifyService;
import com.zlebank.zplatform.trade.service.TradeQueueService;
import com.zlebank.zplatform.trade.utils.DateUtil;
import com.zlebank.zplatform.trade.utils.ObjectDynamic;
import com.zlebank.zplatform.trade.utils.SynHttpRequestThread;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年7月21日 下午2:30:05
 * @since 
 */
@Service("tradeNotifyService")
public class TradeNotifyServiceImpl implements TradeNotifyService{

	private static final Log log = LogFactory.getLog(TradeNotifyServiceImpl.class);
	
	@Autowired
	private MerchMKService merchMKService;
	@Autowired
	private CoopInstiMKService coopInstiMKService;
	@Autowired
	private ITxnsOrderinfoDAO txnsOrderinfoDAO;
	@Autowired
	private ITxnsLogService txnsLogService;
	@Autowired
	private CoopInstiService coopInstiService;
	@Autowired
	private TradeQueueService tradeQueueService;
	@Autowired
	private ITxnsNotifyTaskService txnsNotifyTaskService; 
	
	/**
	 *
	 */
	@Override
	public void queueNotfiy() {
		// TODO Auto-generated method stub
		log.info("【开始异步通知任务】");
		long queueSize = tradeQueueService.getQueueSize(TradeQueueEnum.NOTIFYQUEUE);
		log.info("【异步通知队列大小】"+queueSize);
		if(queueSize>0){
			for (int i = 0; i < queueSize; i++) {
				NotifyQueueBean notifyQueueBean = tradeQueueService.queuePop(TradeQueueEnum.NOTIFYQUEUE,NotifyQueueBean.class);
				log.info("【异步通知队列数据】"+JSON.toJSONString(notifyQueueBean));
				if(notifyQueueBean==null){
					continue;
				}
				if(Long.valueOf(DateUtil.getCurrentDateTime())<Long.valueOf(notifyQueueBean.getSendDateTime())){//没有到通知时间，不发送信息，重回队列
					log.info("【异步通知队列数据,txnseqno:"+notifyQueueBean.getTxnseqno()+"异步通知时间:"+notifyQueueBean.getSendDateTime()+" 未到发送时间】");
					tradeQueueService.addNotifyQueue(notifyQueueBean);
					continue;
				}
				//判断异步通知是否成功
				TxnsNotifyTaskModel asyncNotifyTask = txnsNotifyTaskService.getAsyncNotifyTask(notifyQueueBean.getTxnseqno());
				if("00".equals(asyncNotifyTask.getSendStatus())){
					log.info("【异步通知队列数据,txnseqno:"+notifyQueueBean.getTxnseqno()+"异步通知完成】");
					continue;
				}
				notify(notifyQueueBean.getTxnseqno());
			}
			
		}
		
	}

	
	
	/**
	 * 
	 * @param txnseqno
	 */
	@Override
	public void notify(String txnseqno) {
		// TODO Auto-generated method stub
		
		//AsyncNotifyThreadPool.getInstance().executeMission(null);
		
		/**异步通知处理开始 **/
        try {
			ResultBean orderResp = 
			        generateAsyncRespMessage(txnseqno);
			TxnsOrderinfoModel gatewayOrderBean = txnsOrderinfoDAO.getOrderByTxnseqno(txnseqno);
			TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
			Thread notifyThread = null;
			if (orderResp.isResultBool()) {
				if("000205".equals(gatewayOrderBean.getBiztype())){
            		AnonOrderAsynRespBean respBean = (AnonOrderAsynRespBean) orderResp.getResultObj();
            		
            		InsteadPayNotifyTask task = new InsteadPayNotifyTask();
            		//对匿名支付订单数据进行加密加签
            		responseData(respBean, txnsLog.getAccfirmerno(), txnsLog.getAccsecmerno(), task);
            		notifyThread=new SynHttpRequestThread(
                            StringUtil.isNotEmpty(gatewayOrderBean.getSecmemberno())?gatewayOrderBean.getSecmemberno():gatewayOrderBean.getFirmemberno(),
                            gatewayOrderBean.getRelatetradetxn(),
                            gatewayOrderBean.getBackurl(),
                            task);
            	}else{
            		OrderAsynRespBean respBean = (OrderAsynRespBean) orderResp
                            .getResultObj();
            		notifyThread=new SynHttpRequestThread(
                    		StringUtil.isNotEmpty(gatewayOrderBean.getSecmemberno())?gatewayOrderBean.getSecmemberno():gatewayOrderBean.getFirmemberno(),
                            gatewayOrderBean.getRelatetradetxn(),
                            gatewayOrderBean.getBackurl(),
                            respBean.getNotifyParam());
            	}
				AsyncNotifyThreadPool.getInstance().executeMission(notifyThread);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void responseData(AnonOrderAsynRespBean respBean, String coopInstCode,String merchNo,InsteadPayNotifyTask task) {
        if (log.isDebugEnabled()) {
            log.debug("【入参responseData】"+JSONObject.fromObject(respBean));
        }
        JSONObject jsonData = JSONObject.fromObject(respBean);
        // 排序
        Map<String, Object> map = new TreeMap<String, Object>();
        map =(Map<String, Object>) JSONObject.toBean(jsonData, TreeMap.class);
        jsonData = JSONObject.fromObject(map);
        
        JSONObject addit = new JSONObject();
        addit.put("accessType", "1");
        addit.put("coopInstiId", coopInstCode);
        addit.put("merId", merchNo);
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
	public ResultBean generateAsyncRespMessage(String txnseqno){
        ResultBean resultBean = null;
        try {
            TxnsOrderinfoModel orderinfo = txnsOrderinfoDAO.getOrderByTxnseqno(txnseqno);
            if(StringUtil.isEmpty(orderinfo.getBackurl())){
            	return new ResultBean("09", "no need async");
            }else {
				if(orderinfo.getBackurl().indexOf("http")<0){
					return new ResultBean("09", "no need async");
				}
			}
            
            
            TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(orderinfo.getRelatetradetxn());
             String version="v1.0";// 网关版本
             String encoding="1";// 编码方式
             String certId="";// 证书 ID
             String signature="";// 签名
             String signMethod="01";// 签名方法
             String merId=txnsLog.getAccfirmerno();// 商户代码
             String orderId=txnsLog.getAccordno();// 商户订单号
             String txnType=orderinfo.getTxntype();// 交易类型
             String txnSubType=orderinfo.getTxnsubtype();// 交易子类
             String bizType=orderinfo.getBiztype();// 产品类型
             String accessType="2";// 接入类型
             String txnTime=orderinfo.getOrdercommitime();// 订单发送时间
             String txnAmt=orderinfo.getOrderamt()+"";// 交易金额
             String currencyCode="156";// 交易币种
             String reqReserved=orderinfo.getReqreserved();// 请求方保留域
             String reserved="";// 保留域
             String queryId=txnsLog.getTradeseltxn();// 交易查询流水号
             String respCode=txnsLog.getRetcode();// 响应码
             String respMsg=txnsLog.getRetinfo();// 应答信息
             String settleAmt="";// 清算金额
             String settleCurrencyCode="";// 清算币种
             String settleDate=txnsLog.getAccsettledate();// 清算日期
             String traceNo=txnsLog.getTradeseltxn();// 系统跟踪号
             String traceTime=DateUtil.getCurrentDateTime();// 交易传输时间
             String exchangeDate="";// 兑换日期
             String exchangeRate="";// 汇率
             String accNo="";// 账号
             String payCardType="";// 支付卡类型
             String payType="";// 支付方式
             String payCardNo="";// 支付卡标识
             String payCardIssueName="";// 支付卡名称
             String bindId="";// 绑定标识号
            
             
             OrderAsynRespBean orderRespBean = new OrderAsynRespBean(version, encoding, certId, signature, signMethod, merId, orderId, txnType, txnSubType, bizType, accessType, txnTime, txnAmt, currencyCode, reqReserved, reserved, queryId, respCode, respMsg, settleAmt, settleCurrencyCode, settleDate, traceNo, traceTime, exchangeDate, exchangeRate, accNo, payCardType, payType, payCardNo, payCardIssueName, bindId);
             String privateKey= "";
             if("000204".equals(orderinfo.getBiztype())){
            	 privateKey = coopInstiService.getCoopInstiMK(orderinfo.getFirmemberno(), TerminalAccessType.WIRELESS).getZplatformPriKey();
             }else if("000201".equals(orderinfo.getBiztype())||"000205".equals(orderinfo.getBiztype())){
            	 if("0".equals(orderinfo.getAccesstype())){
                 	privateKey = merchMKService.get(orderinfo.getSecmemberno()).getLocalPriKey().trim();
                 }else if("2".equals(orderinfo.getAccesstype())){
                 	privateKey = coopInstiService.getCoopInstiMK(orderinfo.getFirmemberno(), TerminalAccessType.MERPORTAL).getZplatformPriKey();
                 }else if("1".equals(orderinfo.getAccesstype())){
                	 privateKey = merchMKService.get(orderinfo.getSecmemberno()).getLocalPriKey().trim();
                 }else{
                	 privateKey = coopInstiService.getCoopInstiMK(orderinfo.getFirmemberno(), TerminalAccessType.OPENAPI).getZplatformPriKey();
                 }
            	 //privateKey = merchMKService.get(orderinfo.getFirmemberno()).getLocalPriKey();
             }
             
             if("000205".equals(orderinfo.getBiztype())){
            	 AnonOrderAsynRespBean asynRespBean = new AnonOrderAsynRespBean(version, encoding, txnType, txnSubType, bizType, "00", orderinfo.getSecmembername(), orderId, txnTime, orderinfo.getPaytimeout(), txnAmt, settleCurrencyCode, orderinfo.getOrderdesc(), reserved, orderinfo.getStatus(), orderinfo.getTn(), respCode, respMsg);
            	 return new ResultBean(generateAsyncOrderResult(asynRespBean, privateKey.trim()));
             }
            
            resultBean = new ResultBean(generateAsyncOrderResult(orderRespBean, privateKey.trim()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            resultBean = new ResultBean("RC99", "系统异常");
        }
        return resultBean;
    }
	
	public ResultBean generateAsyncRespMessageExt(String txnseqno){
        ResultBean resultBean = null;
        try {
            TxnsOrderinfoModel orderinfo = txnsOrderinfoDAO.getOrderByTxnseqno(txnseqno);
            if(StringUtil.isEmpty(orderinfo.getBackurl())){
            	return new ResultBean("09", "no need async");
            }else {
				if(orderinfo.getBackurl().indexOf("http")<0){
					return new ResultBean("09", "no need async");
				}
			}
            TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(orderinfo.getRelatetradetxn());
             String version="v1.0";// 网关版本
             String encoding="1";// 编码方式
             String certId="";// 证书 ID
             String signature="";// 签名
             String signMethod="01";// 签名方法
             String merId=txnsLog.getAccfirmerno();// 商户代码
             String orderId=txnsLog.getAccordno();// 商户订单号
             String txnType=orderinfo.getTxntype();// 交易类型
             String txnSubType=orderinfo.getTxnsubtype();// 交易子类
             String bizType=orderinfo.getBiztype();// 产品类型
             String accessType="2";// 接入类型
             String txnTime=orderinfo.getOrdercommitime();// 订单发送时间
             String txnAmt=orderinfo.getOrderamt()+"";// 交易金额
             String currencyCode="156";// 交易币种
             String reqReserved=orderinfo.getReqreserved();// 请求方保留域
             String reserved="";// 保留域
             String queryId=txnsLog.getTradeseltxn();// 交易查询流水号
             String respCode=txnsLog.getRetcode();// 响应码
             String respMsg=txnsLog.getRetinfo();// 应答信息
             String settleAmt="";// 清算金额
             String settleCurrencyCode="";// 清算币种
             String settleDate=txnsLog.getAccsettledate();// 清算日期
             String traceNo=txnsLog.getTradeseltxn();// 系统跟踪号
             String traceTime=DateUtil.getCurrentDateTime();// 交易传输时间
             String exchangeDate="";// 兑换日期
             String exchangeRate="";// 汇率
             String accNo="";// 账号
             String payCardType="";// 支付卡类型
             String payType="";// 支付方式
             String payCardNo="";// 支付卡标识
             String payCardIssueName="";// 支付卡名称
             String bindId="";// 绑定标识号
             OrderAsynRespBean orderRespBean = new OrderAsynRespBean(version, encoding, certId, signature, signMethod, merId, orderId, txnType, txnSubType, bizType, accessType, txnTime, txnAmt, currencyCode, reqReserved, reserved, queryId, respCode, respMsg, settleAmt, settleCurrencyCode, settleDate, traceNo, traceTime, exchangeDate, exchangeRate, accNo, payCardType, payType, payCardNo, payCardIssueName, bindId);
             String privateKey= "";
             if(orderinfo.getSecmemberno().startsWith("3")){
            	 privateKey = coopInstiService.getCoopInstiMK(orderinfo.getSecmemberno(), TerminalAccessType.OPENAPI).getZplatformPriKey();
             }else if(orderinfo.getSecmemberno().startsWith("2")){
            	 privateKey = merchMKService.get(orderinfo.getSecmemberno()).getLocalPriKey().trim();
             }
             if(StringUtil.isEmpty(privateKey)){
            	 privateKey = merchMKService.get(orderinfo.getFirmemberno()).getLocalPriKey().trim();
             }
             resultBean = new ResultBean(generateAsyncOrderResult(orderRespBean, privateKey.trim()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            resultBean = new ResultBean("RC99", "系统异常");
        }
        return resultBean;
    }
	
	public OrderAsynRespBean generateAsyncOrderResult(OrderAsynRespBean orderAsyncRespBean,String privateKey) throws Exception{   
        String[] unParamstring = {"signature"};
        String dataMsg = ObjectDynamic.generateParamer(orderAsyncRespBean, false, unParamstring).trim();
        byte[] data =  URLEncoder.encode(dataMsg,"utf-8").getBytes();
        orderAsyncRespBean.setSignature(URLEncoder.encode(RSAUtils.sign(data, privateKey),"utf-8"));
        return orderAsyncRespBean;
    }
    
    @SuppressWarnings("unused")
	public AnonOrderAsynRespBean generateAsyncOrderResult(AnonOrderAsynRespBean orderAsyncRespBean,String privateKey) throws Exception{   
        String[] unParamstring = {"signature"};
        String dataMsg = ObjectDynamic.generateParamer(orderAsyncRespBean, false, unParamstring).trim();
        byte[] data =  URLEncoder.encode(dataMsg,"utf-8").getBytes();
        //orderAsyncRespBean.setSignature(URLEncoder.encode(RSAUtils.sign(data, privateKey),"utf-8").getBytes());
        return orderAsyncRespBean;
    }



	@Override
	public void notifyExt(String txnseqno) {

		/**异步通知处理开始 **/
        try {
			ResultBean orderResp = 
			        generateAsyncRespMessageExt(txnseqno);
			TxnsOrderinfoModel gatewayOrderBean = txnsOrderinfoDAO.getOrderByTxnseqno(txnseqno);
			Thread notifyThread = null;
			if (orderResp.isResultBool()) {
				OrderAsynRespBean respBean = (OrderAsynRespBean) orderResp
                        .getResultObj();
        		notifyThread=new SynHttpRequestThread(
                		StringUtil.isNotEmpty(gatewayOrderBean.getSecmemberno())?gatewayOrderBean.getSecmemberno():gatewayOrderBean.getFirmemberno(),
                        gatewayOrderBean.getRelatetradetxn(),
                        gatewayOrderBean.getBackurl(),
                        respBean.getNotifyParam());
				AsyncNotifyThreadPool.getInstance().executeMission(notifyThread);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}

	
}
