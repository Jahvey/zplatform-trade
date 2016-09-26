/* 
 * TestReceiveProcessor.java  
 * 
 * version TODO
 *
 * 2015年11月17日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.cmbc.service.impl;

import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.commons.dao.pojo.BusiTypeEnum;
import com.zlebank.zplatform.commons.utils.RSAUtils;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.commons.utils.security.AESHelper;
import com.zlebank.zplatform.commons.utils.security.AESUtil;
import com.zlebank.zplatform.commons.utils.security.RSAHelper;
import com.zlebank.zplatform.member.bean.MerchMK;
import com.zlebank.zplatform.member.bean.enums.TerminalAccessType;
import com.zlebank.zplatform.member.service.CoopInstiService;
import com.zlebank.zplatform.member.service.MerchMKService;
import com.zlebank.zplatform.trade.adapter.accounting.IAccounting;
import com.zlebank.zplatform.trade.bean.AppPartyBean;
import com.zlebank.zplatform.trade.bean.PayPartyBean;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.enums.TradeTypeEnum;
import com.zlebank.zplatform.trade.bean.gateway.AnonOrderAsynRespBean;
import com.zlebank.zplatform.trade.bean.gateway.OrderAsynRespBean;
import com.zlebank.zplatform.trade.cmbc.service.CMBCCrossLineQuickPayService;
import com.zlebank.zplatform.trade.dao.ITxnsOrderinfoDAO;
import com.zlebank.zplatform.trade.dao.RspmsgDAO;
import com.zlebank.zplatform.trade.factory.AccountingAdapterFactory;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsOrderinfoModel;
import com.zlebank.zplatform.trade.model.TxnsWithholdingModel;
import com.zlebank.zplatform.trade.service.IQuickpayCustService;
import com.zlebank.zplatform.trade.service.ITradeReceiveProcessor;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.ITxnsQuickpayService;
import com.zlebank.zplatform.trade.service.ITxnsWithholdingService;
import com.zlebank.zplatform.trade.service.impl.InsteadPayNotifyTask;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.DateUtil;
import com.zlebank.zplatform.trade.utils.ObjectDynamic;
import com.zlebank.zplatform.trade.utils.SynHttpRequestThread;
import com.zlebank.zplatform.trade.utils.UUIDUtil;


/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月17日 下午2:29:36
 * @since 
 */
@Service("cmbcQuickReceiveProcessor")
public class CMBCQuickReceiveProcessor implements ITradeReceiveProcessor{
	private static final Log log = LogFactory.getLog(CMBCQuickReceiveProcessor.class);
    @Autowired
    private ITxnsLogService txnsLogService;
    @Autowired
    private IQuickpayCustService quickpayCustService;
    @Autowired
    private ITxnsOrderinfoDAO txnsOrderinfoDAO;
    @Autowired
    private MerchMKService merchMKService;
    @Autowired
    private ITxnsQuickpayService txnsQuickpayService;
    @Autowired 
    private ITxnsWithholdingService txnsWithholdingService;
    @Autowired 
    private RspmsgDAO rspmsgDAO;
    @Autowired
    private CoopInstiService coopInstiService;
    @Autowired
    private CMBCCrossLineQuickPayService cmbcCrossLineQuickPayService;
    
    /**
     *
     * @param resultBean
     * @param tradeBean
     * @param tradeType
     */
    public void onReceive(ResultBean resultBean,TradeBean tradeBean,TradeTypeEnum tradeType) {
        // TODO Auto-generated method stub
        if(tradeType==TradeTypeEnum.SUBMITPAY){//确认支付（第三方快捷支付渠道）
            saveCMBCTradeResult(resultBean,tradeBean);
        }else if(tradeType==TradeTypeEnum.BANKSIGN){//交易查询
            
        }else if(tradeType==TradeTypeEnum.UNKNOW){//银行卡签约
           
        }else if(tradeType==TradeTypeEnum.ACCOUNTING){
            //dealWithSuccessTrade(tradeBean.getTxnseqno(),(TxnsWithholdingModel)resultBean.getResultObj());
        	cmbcCrossLineQuickPayService.dealWithAccounting(tradeBean.getTxnseqno(), resultBean);
        }
    }
    
    @SuppressWarnings("unused")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void saveCMBCTradeResult(ResultBean resultBean,TradeBean tradeBean){
            
            if(resultBean.isResultBool()){//交易成功
                TxnsWithholdingModel withholding = (TxnsWithholdingModel) resultBean.getResultObj();
                PayPartyBean payPartyBean = new PayPartyBean(tradeBean.getTxnseqno(),"01", tradeBean.getOrderId(), "93000002", ConsUtil.getInstance().cons.getCmbc_merid(), "", DateUtil.getCurrentDateTime(), "",tradeBean.getCardNo());
                payPartyBean.setPanName(tradeBean.getAcctName());
                //更新支付方信息
                txnsLogService.updatePayInfo_Fast(payPartyBean);
                //更新交易流水中心应答信息
                txnsLogService.updateCMBCWithholdingRetInfo(tradeBean.getTxnseqno(), withholding);
                String commiteTime = DateUtil.getCurrentDateTime();
                String merchOrderNo =  tradeBean.getOrderId();
                // 处理同步通知和异步通知
                // 根据原始订单拼接应答报文，异步通知商户
                TxnsOrderinfoModel gatewayOrderBean = txnsOrderinfoDAO.getOrderByTxnseqno(tradeBean.getTxnseqno());
                /**账务处理开始 **/
                // 应用方信息
                try {
                    AppPartyBean appParty = new AppPartyBean("",
                            "000000000000", commiteTime,
                            DateUtil.getCurrentDateTime(), tradeBean.getTxnseqno(), "AC000000");
                    txnsLogService.updateAppInfo(appParty);
                    IAccounting accounting = AccountingAdapterFactory.getInstance().getAccounting(BusiTypeEnum.fromValue(tradeBean.getBusitype()));
                    ResultBean accountResultBean = accounting.accountedFor(tradeBean.getTxnseqno());
                    txnsLogService.updateAppStatus(tradeBean.getTxnseqno(), accountResultBean.getErrCode(), accountResultBean.getErrMsg());
                    
                    
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                /**账务处理结束 **/
                saveSuccessCMBCTrade(tradeBean.getTxnseqno(),
                        tradeBean.getOrderId(), withholding,tradeBean.getMerchId());
                /**异步通知处理开始 **/
                ResultBean orderResp = 
                        generateAsyncRespMessage(tradeBean.getTxnseqno());
                if (orderResp.isResultBool()) {
                	if("000205".equals(gatewayOrderBean.getBiztype())){
                		AnonOrderAsynRespBean respBean = (AnonOrderAsynRespBean) orderResp
                                .getResultObj();
                        new SynHttpRequestThread(
                                gatewayOrderBean.getFirmemberno(),
                                gatewayOrderBean.getRelatetradetxn(),
                                gatewayOrderBean.getBackurl(),
                                respBean.getNotifyParam()).start();
                	}else{
                		OrderAsynRespBean respBean = (OrderAsynRespBean) orderResp
                                .getResultObj();
                        new SynHttpRequestThread(
                                gatewayOrderBean.getFirmemberno(),
                                gatewayOrderBean.getRelatetradetxn(),
                                gatewayOrderBean.getBackurl(),
                                respBean.getNotifyParam()).start();
                	}
                    
                }
                /**异步通知处理结束 **/
            }else{
                if(resultBean.getErrCode().equals("R")){//对于交易结果不确定的，需要启动查询机制进行处理，最多3分钟后有明确结果
                    PayPartyBean payPartyBean = new PayPartyBean(tradeBean.getTxnseqno(),"01", tradeBean.getOrderId(), "93000002", ConsUtil.getInstance().cons.getCmbc_merid(), "", DateUtil.getCurrentDateTime(), "",tradeBean.getCardNo());
                    //更新支付方信息
                    txnsLogService.updatePayInfo_Fast(payPartyBean);
                }else{
                    //订单状态更新为失败
                    txnsOrderinfoDAO.updateOrderToFail(tradeBean.getTxnseqno());
                    PayPartyBean payPartyBean = new PayPartyBean(tradeBean.getTxnseqno(),"01", tradeBean.getOrderId(), "93000002", ConsUtil.getInstance().cons.getCmbc_merid(), "", DateUtil.getCurrentDateTime(), "",tradeBean.getCardNo());
                    //更新支付方信息
                    txnsLogService.updatePayInfo_Fast(payPartyBean);
                    //更新支付方返回结果
                    txnsLogService.updatePayInfo_Fast_result(tradeBean.getTxnseqno(), resultBean.getErrCode(),resultBean.getErrMsg());
                }
            }
    }
    
    
    public void saveSuccessCMBCTrade(String txnseqno,String gateWayOrderNo,TxnsWithholdingModel withholding,String merchId){
        TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
        //txnsLog.setAccordfintime(DateUtil.getCurrentDateTime());
        txnsLog.setPayordfintime(DateUtil.getCurrentDateTime());
        txnsLog.setRetdatetime(DateUtil.getCurrentDateTime());
        txnsLog.setTradestatflag("00000001");//交易完成结束位
        txnsLog.setTradetxnflag("10000000");//证联支付快捷（基金交易）
        txnsLog.setRelate("10000000");
        txnsLog.setTradeseltxn(UUIDUtil.uuid());
        txnsLog.setPayrettsnseqno(withholding.getPayserialno());
        txnsLogService.updateTxnsLog(txnsLog);
        TxnsOrderinfoModel orderinfo = txnsOrderinfoDAO.getOrderByTxnseqno(txnseqno);
        orderinfo.setStatus("00");
        orderinfo.setOrderfinshtime(DateUtil.getCurrentDateTime());
        txnsOrderinfoDAO.updateOrderinfo(orderinfo);
        
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
    
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void dealWithSuccessTrade(String txnseqno,TxnsWithholdingModel withholding){
        TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
        PayPartyBean payPartyBean = null;
        if(StringUtil.isNotEmpty(withholding.getOrireqserialno())){
            TxnsWithholdingModel old_withholding = txnsWithholdingService.getWithholdingBySerialNo(withholding.getOrireqserialno());
            //更新支付方信息
            payPartyBean = new PayPartyBean(txnseqno,"01", withholding.getSerialno(), old_withholding.getChnlcode(), ConsUtil.getInstance().cons.getCmbc_merid(), "", DateUtil.getCurrentDateTime(), "",old_withholding.getAccno());
        }else{
            payPartyBean = new PayPartyBean(txnseqno,"01", withholding.getSerialno(), withholding.getChnlcode(), ConsUtil.getInstance().cons.getCmbc_merid(), "", DateUtil.getCurrentDateTime(), "",withholding.getAccno());
        }
        payPartyBean.setPanName(withholding.getAccname());
        txnsLogService.updatePayInfo_Fast(payPartyBean);
        
        
        //更新交易流水中心应答信息
        txnsLogService.updateCMBCWithholdingRetInfo(txnseqno, withholding);
        saveSuccessCMBCTrade(txnseqno,
                txnsLog.getAccordno(), withholding,txnsLog.getAccfirmerno());
        String commiteTime = DateUtil.getCurrentDateTime();
       
        
        // 处理同步通知和异步通知
        // 根据原始订单拼接应答报文，异步通知商户
        TxnsOrderinfoModel gatewayOrderBean = txnsOrderinfoDAO.getOrderByTxnseqno(txnseqno);
        /**账务处理开始 **/
        // 应用方信息
        try {
            AppPartyBean appParty = new AppPartyBean("","000000000000", commiteTime,DateUtil.getCurrentDateTime(), txnseqno, "");
            txnsLogService.updateAppInfo(appParty);
            IAccounting accounting = AccountingAdapterFactory.getInstance().getAccounting(BusiTypeEnum.fromValue(txnsLog.getBusitype()));
            accounting.accountedFor(txnseqno);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**账务处理结束 **/
        
        /**异步通知处理开始 **/
        try {
			ResultBean orderResp = 
			        generateAsyncRespMessage(txnsLog.getTxnseqno());
			if (orderResp.isResultBool()) {
				if("000205".equals(gatewayOrderBean.getBiztype())){
            		AnonOrderAsynRespBean respBean = (AnonOrderAsynRespBean) orderResp.getResultObj();
            		
            		InsteadPayNotifyTask task = new InsteadPayNotifyTask();
            		//对匿名支付订单数据进行加密加签
            		responseData(respBean, txnsLog.getAccfirmerno(), txnsLog.getAccsecmerno(), task);
            		new SynHttpRequestThread(
                            StringUtil.isNotEmpty(gatewayOrderBean.getSecmemberno())?gatewayOrderBean.getSecmemberno():gatewayOrderBean.getFirmemberno(),
                            gatewayOrderBean.getRelatetradetxn(),
                            gatewayOrderBean.getBackurl(),
                            task).start();
            	}else{
            		OrderAsynRespBean respBean = (OrderAsynRespBean) orderResp
                            .getResultObj();
                    new SynHttpRequestThread(
                    		StringUtil.isNotEmpty(gatewayOrderBean.getSecmemberno())?gatewayOrderBean.getSecmemberno():gatewayOrderBean.getFirmemberno(),
                            gatewayOrderBean.getRelatetradetxn(),
                            gatewayOrderBean.getBackurl(),
                            respBean.getNotifyParam()).start();
            	}
			   
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
    
}