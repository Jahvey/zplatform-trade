/* 
 * TestWechat.java  
 * 
 * version TODO
 *
 * 2016年6月29日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.trade.adapter.quickpay.IRefundTrade;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.wap.WapRefundBean;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsOrderinfoModel;
import com.zlebank.zplatform.trade.service.IGateWayService;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.impl.GateWayServiceImpl;
import com.zlebank.zplatform.trade.service.impl.TxnsLogServiceImpl;
import com.zlebank.zplatform.wechat.job.RefundJob;
import com.zlebank.zplatform.wechat.service.WeChatService;
import com.zlebank.zplatform.wechat.wx.WXApplication;
import com.zlebank.zplatform.wechat.wx.bean.PayResultBean;
import com.zlebank.zplatform.wechat.wx.bean.QueryBillBean;

/**
 * 微信测试类
 *
 * @author liusm
 * @version
 * @date 2016年6月29日 下午3:08:19
 * @since 
 */
public class TestWechat {

    private static final Log log = LogFactory.getLog(TestWechat.class);
    private RefundJob refundJob;
    private IRefundTrade weChatRefundTrade;
    private WeChatService weChatService;
    private ITxnsLogService  logService;
    private IGateWayService gateWayService;

    @Before
    public void init(){
    	  ApplicationContext context = ApplicationContextUtil.get();
        refundJob = (RefundJob) context.getBean("refundJob");
     //   weChatRefundTrade = (IRefundTrade) context.getBean("weChatRefundTrade");
        weChatService = (WeChatService) context.getBean("weChatService");
        logService = (ITxnsLogService) context.getBean("txnsLogService");
        gateWayService = (IGateWayService) context.getBean("gateWayService");
    }
    @Test
    public void testBatch(){
    	try {
    		refundJob.execute();
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    }
    @Test
    public void testrefund(){
    	try {
    		TradeBean tradeBean= new TradeBean();
        	//tradeBean.setTxnseqno("1606249900053699");
    		//tradeBean.setTxnseqno("1606279900053739");
    		//tradeBean.setTxnseqno("1606279900053799");
    		//tradeBean.setTxnseqno("1606289900053889");
    		//tradeBean.setTxnseqno("1606289900053940");
    		//tradeBean.setTxnseqno("1606289900053941");
    		tradeBean.setTxnseqno("1607079900054460");
        	ResultBean resultBean=weChatRefundTrade.refund(tradeBean);
        	if(resultBean.isResultBool()){
        		System.out.println("成功");
        	}else{
        		System.out.println(resultBean.getErrCode()+""+resultBean.getErrMsg());
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    }
    
    @Test
    public void createOrder(){
    	try {
    		weChatService.creatOrder("160705002700053299");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    }
    
    @Test
    public void testAnsy(){
    		StringBuffer sq= new StringBuffer();
	    	/*sq.append("<xml><appid><![CDATA[wx16a0b09dbf94f380]]></appid><attach><![CDATA[证联]]></attach><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><device_info><![CDATA[WEB]]>");
	    	sq.append("</device_info><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[N]]></is_subscribe><mch_id><![CDATA[1345867901]]></mch_id><nonce_str><![CDATA[272CD241E851A1C65271F3BD36AA08DE]]>");
	    	sq.append("</nonce_str><openid><![CDATA[omBzYwICDExiIz1-ejI3v86oUsGU]]></openid><out_trade_no><![CDATA[1606289000000543]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code>");
	    	sq.append("<return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[7C1D8F56F3395551321144635B10F303]]></sign><time_end><![CDATA[20160628155106]]></time_end><total_fee>1</total_fee>");
	    	sq.append("<trade_type><![CDATA[APP]]></trade_type><transaction_id><![CDATA[4003202001201606288023842166]]></transaction_id></xml>");*/
    		sq.append("<xml><appid><![CDATA[wx16a0b09dbf94f380]]></appid><attach><![CDATA[证联]]></attach><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><device_info><![CDATA[WEB]]>");
    		sq.append("</device_info><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[N]]></is_subscribe><mch_id><![CDATA[1345867901]]></mch_id><nonce_str><![CDATA[8EA09D6B3077C198BDA8252FD73F85B5]]>");
    		sq.append("</nonce_str><openid><![CDATA[omBzYwICDExiIz1-ejI3v86oUsGU]]></openid><out_trade_no><![CDATA[1606299000000340]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code>");
    		sq.append("<![CDATA[SUCCESS]]></return_code><sign><![CDATA[C1E741BC7D3346667954291EC381B555]]></sign><time_end><![CDATA[20160629104129]]></time_end><total_fee>1</total_fee>");
    		sq.append("<trade_type><![CDATA[APP]]></trade_type><transaction_id><![CDATA[4003202001201606298060595950]]></transaction_id></xml>");
    		
	     	try {
	     		WXApplication bean = new WXApplication();
	     		PayResultBean result =bean.parseResultXml(sq.toString());
	     		System.out.println(result.getTransaction_id());
	     		weChatService.asyncTradeResult(result);
	            // 返回响应数据
	            String responseXml = null;
	            if ("SUCCESS".equals(result.getReturn_code()) && "SUCCESS".equals(result.getResult_code())) {
	                responseXml = bean.createResponseResultXml("SUCCESS","OK");
	            } else {
	                responseXml = bean.createResponseResultXml("FAIL", result.getReturn_code() == null ? result.getResult_code() : result.getReturn_code());
	            }
			} catch (Exception e) {
				e.printStackTrace();
			}
         
         
    }
    
    @Test
    public void downBill(){
    	try {
    		  QueryBillBean bill = new QueryBillBean();
        	  WXApplication bean = new WXApplication();
        	    bill.setBill_date("20160628");
        	    bill.setBill_type("ALL");
        	    bean.downLoadBill(bill);
        	    List<String[]> list=weChatService.dowanWeChatBill(bill);
        	    if(list.size()>0){
        	    	for(String[] item:list){
        	    		System.out.println(item[0]);
        	    	}
        	    }
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    }
    
    @Test
    public void testFundApply(){
    	TxnsLogModel txnsLog=logService.getTxnsLogByTxnseqno("1607079900054440");
    	WapRefundBean refundBean = new WapRefundBean();
    	refundBean.setOrderId(DateUtil.getCurrentDateTime());
    	refundBean.setOrigOrderId(txnsLog.getAccordno());
    	refundBean.setTxnAmt(txnsLog.getAmount()+"");
    	refundBean.setTxnType("14");
    	refundBean.setTxnSubType("00");
    	refundBean.setBizType("000202");
    	refundBean.setCoopInstiId(txnsLog.getAccfirmerno());
    	refundBean.setMerId(txnsLog.getAccsecmerno());
    	refundBean.setMemberId(txnsLog.getAccmemberid());
        try {
        	log.info("refund json:"+JSON.toJSONString(refundBean));
    		gateWayService.refund(JSON.toJSONString(refundBean));
    	} catch (TradeException e) {
    		e.printStackTrace();
    	}
    }
    
    @Test
    public void testQueryWechatOrder(){
    	TradeBean tradeBean= new TradeBean();
		tradeBean.setTxnseqno("1607079900054460");
    	ResultBean resultBean=weChatService.queryWechatOrder(tradeBean);
    	if(resultBean.isResultBool()){
    		TxnsOrderinfoModel order=(TxnsOrderinfoModel) resultBean.getResultObj();
    		System.out.println("成功"+order.getStatus());
    	}else{
    		System.out.println(resultBean.getErrCode()+""+resultBean.getErrMsg());
    	}
    }
    @Test
    public void testQueryOrder(){
    	try {
    		refundJob.queryOrder();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
    }
    
  
    
}