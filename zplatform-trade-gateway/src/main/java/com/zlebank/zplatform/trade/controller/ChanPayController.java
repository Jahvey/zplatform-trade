/* 
 * ChanPayController.java  
 * 
 * version TODO
 *
 * 2016年4月28日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.acc.pojo.Money;
import com.zlebank.zplatform.commons.dao.pojo.BusiTypeEnum;
import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.trade.bean.PayPartyBean;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.UpdateData;
import com.zlebank.zplatform.trade.bean.chanpay.ChanPayOrderBean;
import com.zlebank.zplatform.trade.bean.gateway.OrderRespBean;
import com.zlebank.zplatform.trade.chanpay.bean.async.RefundAsyncResultBean;
import com.zlebank.zplatform.trade.chanpay.bean.async.TradeAsyncResultBean;
import com.zlebank.zplatform.trade.chanpay.bean.file.FeeTradeFileBean;
import com.zlebank.zplatform.trade.chanpay.bean.file.PayTradeFileBean;
import com.zlebank.zplatform.trade.chanpay.bean.file.ReceiptBean;
import com.zlebank.zplatform.trade.chanpay.bean.file.RefundTradeFileBean;
import com.zlebank.zplatform.trade.chanpay.bean.order.BatchOrderBean;
import com.zlebank.zplatform.trade.chanpay.bean.order.OrderItemBean;
import com.zlebank.zplatform.trade.chanpay.bean.order.SingleOrderBean;
import com.zlebank.zplatform.trade.chanpay.utils.RSA;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.factory.AccountingAdapterFactory;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsNotifyTaskModel;
import com.zlebank.zplatform.trade.model.TxnsOrderinfoModel;
import com.zlebank.zplatform.trade.service.ChanPayAsyncService;
import com.zlebank.zplatform.trade.service.IGateWayService;
import com.zlebank.zplatform.trade.service.ITxnsGatewaypayService;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.ITxnsNotifyTaskService;
import com.zlebank.zplatform.trade.service.RefundService;
import com.zlebank.zplatform.trade.service.UpdateSubject;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.ObjectDynamic;



/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年4月28日 上午10:37:25
 * @since 
 */
@Controller
@RequestMapping("/chanpay")
public class ChanPayController {
	private static final Log log =  LogFactory.getLog(ChanPayController.class);
	@Autowired
    private ChanPayAsyncService chanPayAsyncService;
	@Autowired
	private ITxnsLogService txnsLogService;
	@Autowired
	private IGateWayService gateWayService;
	@Autowired
	private ITxnsGatewaypayService txnsGatewaypayService;
	@Autowired
	private ITxnsNotifyTaskService txnsNotifyTaskService;
	@Autowired
	private RefundService refundService;
	@Autowired 
	@Qualifier("updateRefundServiceImpl")
	private  UpdateSubject  updateSubject;

	
	@RequestMapping("/createOrder")
	public ModelAndView createOrder(@RequestParam String txnseqno,@RequestParam String bankcode){
		TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
		Map<String, Object> model = new HashMap<String, Object>();
		SingleOrderBean orderBean = new SingleOrderBean();
		orderBean.setVersion(ConsUtil.getInstance().cons.getChanpay_version());
		orderBean.setPartner_id(ConsUtil.getInstance().cons.getChanpay_partner_id());
		orderBean.set_input_charset(ConsUtil.getInstance().cons.getChanpay_input_charset());
		orderBean.setIs_anonymous("Y");
		orderBean.setBank_code(bankcode);
		orderBean.setOut_trade_no((UUID.randomUUID().toString()).replace("-", ""));
		orderBean.setPay_method("2");
		orderBean.setPay_type("C,DC");
		orderBean.setService("cjt_create_instant_trade");
		orderBean.setTrade_amount(Money.valueOf(new BigDecimal(txnsLog.getAmount())).toYuan());
		orderBean.setNotify_url(ConsUtil.getInstance().cons.getChanpay_back_url());
		orderBean.setReturn_url(ConsUtil.getInstance().cons.getChanpay_front_url()+"?txnseqno="+txnseqno);
		
		ChanPayOrderBean chanPayOrderBean = new ChanPayOrderBean();
		chanPayOrderBean.setBank_code(orderBean.getBank_code());
		chanPayOrderBean.setOut_trade_no(orderBean.getOut_trade_no());
		chanPayOrderBean.setPay_method(orderBean.getPay_method());
		chanPayOrderBean.setPay_type(orderBean.getPay_type());
		chanPayOrderBean.setTrade_amount(orderBean.getTrade_amount());
		chanPayOrderBean.setTxnseqno(txnseqno);
		
		try {
			String sign = RSA.sign(buildParamter(orderBean), 
					ConsUtil.getInstance().cons.getChanpay_private_key(), 
					ConsUtil.getInstance().cons.getChanpay_input_charset());
			orderBean.setSign(sign);
			orderBean.setSign_type(ConsUtil.getInstance().cons.getChanpay_sign_type());
			model.put("order", orderBean);
			model.put("url", ConsUtil.getInstance().cons.getChanpay_url());
			//保存畅捷支付订单信息
			txnsGatewaypayService.saveChanPayGateWay(chanPayOrderBean);
			//更新订单状态
			TxnsOrderinfoModel orderinfo = gateWayService.getOrderByTxnseqno(txnseqno);
			orderinfo.setStatus("02");
			gateWayService.update(orderinfo);
			//更新交易流水状态
			PayPartyBean payPartyBean = new PayPartyBean();
			payPartyBean.setTxnseqno(txnseqno);
			payPartyBean.setPaytype("02");
			payPartyBean.setPayordno(orderBean.getOut_trade_no());
			payPartyBean.setPayinst(ConsUtil.getInstance().cons.getChanpay_channel_code());
			payPartyBean.setPayfirmerno(ConsUtil.getInstance().cons.getChanpay_partner_id());
			payPartyBean.setPayordcomtime(DateUtil.getCurrentDateTime());
			txnsLogService.updatePayInfo_Fast(payPartyBean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ModelAndView("/bank/order",model);
	}
	
	@RequestMapping("/createBatchOrder")
	public ModelAndView createBatchOrder(@RequestParam String txnseqno,@RequestParam String bankcode){
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		try {
			List<OrderItemBean> itemList = new ArrayList<OrderItemBean>();
			OrderItemBean orderItem = new OrderItemBean();
			orderItem.setOut_trade_no((UUID.randomUUID().toString()).replace("-", ""));
			orderItem.setOrder_amount("10.00");
			orderItem.setSell_id_type("MEMBER_ID");
			OrderItemBean orderItem1 = new OrderItemBean();
			orderItem1.setOut_trade_no((UUID.randomUUID().toString()).replace("-", ""));
			orderItem1.setOrder_amount("10.00");
			orderItem1.setSell_id_type("MEMBER_ID");
			itemList.add(orderItem);
			itemList.add(orderItem1);
			
			
			BatchOrderBean batchOrderBean = new BatchOrderBean();
			batchOrderBean.setVersion(ConsUtil.getInstance().cons.getChanpay_version());
			batchOrderBean.setPartner_id(ConsUtil.getInstance().cons.getChanpay_partner_id());
			batchOrderBean.set_input_charset(ConsUtil.getInstance().cons.getChanpay_input_charset());
			batchOrderBean.setService("cjt_create_batch_instant_trade");
			batchOrderBean.setRequest_no((UUID.randomUUID().toString()).replace("-", ""));
			batchOrderBean.setTrade_amount("20.00");
			batchOrderBean.setIs_anonymous("Y");
			batchOrderBean.setBank_code("TESTBANK");
			batchOrderBean.setPay_method("1");
			batchOrderBean.setPay_type("C,DC");
			batchOrderBean.setProdInfo_list(JSON.toJSONString(itemList));
			String sign = RSA.sign(buildParamter(batchOrderBean), 
					ConsUtil.getInstance().cons.getChanpay_private_key(), 
					ConsUtil.getInstance().cons.getChanpay_input_charset());
			batchOrderBean.setSign(sign);
			batchOrderBean.setSign_type(ConsUtil.getInstance().cons.getChanpay_sign_type());
			model.put("order", batchOrderBean);
			model.put("url", ConsUtil.getInstance().cons.getChanpay_url());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return new ModelAndView("/bank/batch_order",model);
	}
	
	@RequestMapping("/receipt")
	public ModelAndView receipt(){
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			ReceiptBean receiptBean = new ReceiptBean();
			receiptBean.setVersion(ConsUtil.getInstance().cons.getChanpay_version());
			receiptBean.setPartner_id(ConsUtil.getInstance().cons.getChanpay_partner_id());
			receiptBean.set_input_charset(ConsUtil.getInstance().cons.getChanpay_input_charset());
			receiptBean.setService("cjt_view_receipt");
			receiptBean.setOuter_trade_no((UUID.randomUUID().toString()).replace("-", ""));
			//receiptBean.setInner_trade_no("FI101010001050300363581");
			String sign = RSA.sign(buildParamter(receiptBean), ConsUtil.getInstance().cons.getChanpay_private_key(), ConsUtil.getInstance().cons.getChanpay_input_charset());
			receiptBean.setSign(sign);
			receiptBean.setSign_type(ConsUtil.getInstance().cons.getChanpay_sign_type());
			model.put("order", receiptBean);
			model.put("url", ConsUtil.getInstance().cons.getChanpay_url());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("/bank/receipt",model);
	}
	
	@RequestMapping("/payTradeFile")
	public ModelAndView payTradeFile(){
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			PayTradeFileBean payTradeFileBean = new PayTradeFileBean();
			payTradeFileBean.setVersion(ConsUtil.getInstance().cons.getChanpay_version());
			payTradeFileBean.setPartner_id(ConsUtil.getInstance().cons.getChanpay_partner_id());
			payTradeFileBean.set_input_charset(ConsUtil.getInstance().cons.getChanpay_input_charset());
			payTradeFileBean.setService("cjt_everyday_trade_file");
			payTradeFileBean.setTransDate("20160503");
			String sign = RSA.sign(buildParamter(payTradeFileBean), ConsUtil.getInstance().cons.getChanpay_private_key(), ConsUtil.getInstance().cons.getChanpay_input_charset());
			payTradeFileBean.setSign(sign);
			payTradeFileBean.setSign_type(ConsUtil.getInstance().cons.getChanpay_sign_type());
			model.put("order", payTradeFileBean);
			model.put("url", ConsUtil.getInstance().cons.getChanpay_url());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("/bank/pay_trade_file",model);
	}
	
	@RequestMapping("/refundTradeFile")
	public ModelAndView refundTradeFile(){
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			RefundTradeFileBean payTradeFileBean = new RefundTradeFileBean();
			payTradeFileBean.setVersion(ConsUtil.getInstance().cons.getChanpay_version());
			payTradeFileBean.setPartner_id(ConsUtil.getInstance().cons.getChanpay_partner_id());
			payTradeFileBean.set_input_charset(ConsUtil.getInstance().cons.getChanpay_input_charset());
			payTradeFileBean.setService("cjt_refund_trade_file");
			payTradeFileBean.setTransDate("20160504");
			String sign = RSA.sign(buildParamter(payTradeFileBean), ConsUtil.getInstance().cons.getChanpay_private_key(), ConsUtil.getInstance().cons.getChanpay_input_charset());
			payTradeFileBean.setSign(sign);
			payTradeFileBean.setSign_type(ConsUtil.getInstance().cons.getChanpay_sign_type());
			model.put("order", payTradeFileBean);
			model.put("url", ConsUtil.getInstance().cons.getChanpay_url());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("/bank/pay_trade_file",model);
	}
	@RequestMapping("/feeTradeFile")
	public ModelAndView feeTradeFile(){
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			FeeTradeFileBean payTradeFileBean = new FeeTradeFileBean();
			payTradeFileBean.setVersion(ConsUtil.getInstance().cons.getChanpay_version());
			payTradeFileBean.setPartner_id(ConsUtil.getInstance().cons.getChanpay_partner_id());
			payTradeFileBean.set_input_charset(ConsUtil.getInstance().cons.getChanpay_input_charset());
			payTradeFileBean.setService("cjt_fee_trade_file");
			payTradeFileBean.setTransDate("20160506");
			String sign = RSA.sign(buildParamter(payTradeFileBean), ConsUtil.getInstance().cons.getChanpay_private_key(), ConsUtil.getInstance().cons.getChanpay_input_charset());
			payTradeFileBean.setSign(sign);
			payTradeFileBean.setSign_type(ConsUtil.getInstance().cons.getChanpay_sign_type());
			model.put("order", payTradeFileBean);
			model.put("url", ConsUtil.getInstance().cons.getChanpay_url());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("/bank/pay_trade_file",model);
	}
	
	
	private String buildParamter(Object orderBean){
		Map<String,Object> parseObject = JSON.parseObject(JSON.toJSONString(orderBean), Map.class);
		 List<String> keys = new ArrayList<String>(parseObject.keySet());
	        Collections.sort(keys);
	        String prestr = "";

	        //String charset = parseObject.get("_input_charset").toString();
	        for (int i = 0; i < keys.size(); i++) {
	            String key = keys.get(i);
	            String value = parseObject.get(key).toString();
	            

	            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
	                prestr = prestr + key + "=" + value;
	            } else {
	                prestr = prestr + key + "=" + value + "&";
	            }
	        }
	        System.out.println("prestr:"+prestr);
		return prestr;
	}
	
	@RequestMapping("/reciveChanPay")
    @ResponseBody
    public String reciveChanPay(TradeAsyncResultBean tradeAsyncResultBean,HttpServletResponse response,HttpServletRequest request ) {
	    //try {
    	Map map = request.getParameterMap();
    	log.info("request data :" + JSON.toJSONString(map));
		log.info("chanpay data :" + JSON.toJSONString(tradeAsyncResultBean));
		ResultBean dealWithTradeAsync = null;
		try {
			dealWithTradeAsync = chanPayAsyncService.dealWithTradeAsync(tradeAsyncResultBean);
		} catch (TradeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(dealWithTradeAsync.isResultBool()){
			return "success";
		}else{
			
		}
	    return "success";
    }
	

	@RequestMapping("/reciveRefundChanPay")
    public String reciveRefundChanPay(RefundAsyncResultBean tradeAsyncResultBean,HttpServletResponse response,HttpServletRequest request ) {
	    try {
	    	Map map = request.getParameterMap();
	    	log.info("request data :" + JSON.toJSONString(map));
			log.info("chanpay data :" + JSON.toJSONString(tradeAsyncResultBean));
			ResultBean dealWithTradeAsync = chanPayAsyncService.dealWithRefundAsync(tradeAsyncResultBean);
			if(dealWithTradeAsync.isResultBool()){
				response.setContentType("text/html");
				response.setCharacterEncoding("utf-8");
				response.getWriter().print("success");
				response.getWriter().flush();
				response.getWriter().close();
			}else{
				
			}
			/*response.setContentType("text/html");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print("success");
			response.getWriter().flush();
			response.getWriter().close();*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TradeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	    return "";
    }
	
	@RequestMapping("/showChanPayResult.html")
	public ModelAndView showChanPayResult(String txnseqno){
		
		Map<String, Object> model = null;
		try {
			model = new HashMap<String, Object>();
			TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
			TxnsOrderinfoModel gatewayOrderBean = gateWayService
			        .getOrderinfoByOrderNoAndMemberId(txnsLog.getAccordno(),txnsLog.getAccfirmerno());
			ResultBean orderResp = gateWayService.generateRespMessage(txnsLog.getAccordno(),txnsLog.getAccfirmerno());
			OrderRespBean respBean = (OrderRespBean) orderResp.getResultObj();
			model.put("suburl", gatewayOrderBean.getFronturl() + "?"+ ObjectDynamic.generateReturnParamer(respBean, false, null));
			model.put("errMsg", "交易完成");
			model.put("respCode", "0000");
			model.put("txnseqno", txnseqno);
			// 记录同步发送的日志
			TxnsNotifyTaskModel task = new TxnsNotifyTaskModel(
			        gatewayOrderBean.getFirmemberno(),
			        gatewayOrderBean.getRelatetradetxn(), 1, 1,
			        ObjectDynamic.generateReturnParamer(respBean, false, null),
			        "00", "200", gatewayOrderBean.getFronturl(), "2");
			txnsNotifyTaskService.saveTask(task);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new ModelAndView("/fastpay/success", model);
	}

	@RequestMapping("/test")
	public void test(){
		/*String refundOrderNo = "1605239300000122";
		String merchNo = "200000000000597";
		refundService.execute(refundOrderNo, merchNo);*/
		/*WapRefundBean refundBean = new WapRefundBean();
		refundBean.setOrderId(DateUtil.getCurrentDateTime());
		refundBean.setOrigOrderId("2016051314777892");
		refundBean.setTxnAmt("1");
		refundBean.setTxnType("14");
		refundBean.setTxnSubType("00");
		refundBean.setBizType("000202");
		refundBean.setCoopInstiId("300000000000014");
		refundBean.setMerId("200000000000597");
		refundBean.setMemberId("100000000000576");
		try {
			gateWayService.refund(JSON.toJSONString(refundBean));
		} catch (TradeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*UpdateData data = new UpdateData();
		data.setResultCode("00");
		data.setTxnSeqNo("1605269900058784");
		updateSubject.update(data);*/
		
		AccountingAdapterFactory.getInstance().getAccounting(BusiTypeEnum.fromValue("1000")).accountedFor("1605279900052511");
	}
}
