/* 
 * TestWX.java  
 * 
 * version TODO
 *
 * 2016年5月17日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.wechat.qr.wx;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zlebank.zplatform.wechat.qr.exception.WXVerifySignFailedException;
import com.zlebank.zplatform.wechat.qr.wx.bean.RefundBean;
import com.zlebank.zplatform.wechat.qr.wx.bean.RefundResultBean;

/**
 * 微信测试类
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年5月17日 下午3:08:19
 * @since
 */
public class TestWX {

	private static final Log log = LogFactory.getLog(TestWX.class);

	/**
	 * @param args
	 * @throws WXVerifySignFailedException
	 */
	public static void main(String[] args) throws WXVerifySignFailedException {
		WXApplication instance = new WXApplication();

		/******************** 下订单 **************************/
		/*
		 * WXOrderBean order = new WXOrderBean(); order.setBody("iPad");
		 * order.setDetail("iPad mini  16G  白色"); order.setAttach("北京太阳宫");
		 * order.setOut_trade_no("ZL"+DateUtil.getCurrentDateTime());
		 * order.setTotal_fee("1");
		 * order.setTime_start(DateUtil.getCurrentDateTime());
		 * order.setTime_expire(DateUtil.formatDateTime("yyyyMMddHHmmss",
		 * DateUtil.skipDateTime(new Date(), 1))); order.setGoods_tag("WXG");
		 * order.setNotify_url("http://www.weixin.qq.com/wxpay/pay.php");
		 * System.out.println(order.getOut_trade_no()); try { JSONObject xml =
		 * instance.createOrder(order); log.info("【下订单返回结果】"+ xml.toString()); }
		 * catch (WXVerifySignFailedException e) { e.printStackTrace(); }
		 */

		/******************** 下载对账单 **************************/
		/*
		 * QueryBillBean bill = new QueryBillBean();
		 * bill.setBill_date("20160805"); bill.setBill_type("ALL");
		 * instance.downLoadBill(bill);
		 */

		/******************** 退款 **************************/
//		RefundBean rb = new RefundBean();
//		rb.setOut_refund_no(String.valueOf(System.currentTimeMillis()));// 退款流水号（唯一，可当场生成）
//		rb.setOut_trade_no("1608109000000783");// 原商户号（证联生成的）
//		rb.setRefund_fee("1");// 退款金额
//		rb.setTotal_fee("1");// 总金额
//		//rb.setTransaction_id("4003872001201605266327894875");// 原微信订单号（微信返回的）
//		RefundResultBean refund = instance.refund(rb); // 进行退款
//		log.info("【退款返回结果】" + JSONObject.fromObject(refund));
//		System.out.println(JSONObject.fromObject(refund));
		/******************** 退款查询 **************************/
		
		  /*QueryRefundBean qrb = new QueryRefundBean();
		  qrb.setOut_refund_no("ZL20160806113740");
		  //qrb.setTransaction_id("4006652001201605206059282375");
		  QueryRefundResultBean refundQuery = instance.refundQuery(qrb);
		 log.info("【退款查询返回结果】"+ JSONObject.fromObject(refundQuery));*/
		 

		/******************** 订单查询 **************************/
		
		 /* QueryOrderBean qor = new QueryOrderBean();
		 //qor.setTransaction_id("4006652001201605206059282375");
		  qor.setOut_trade_no("ZL20160806113740"); QueryOrderResultBean
		  queryOrder = instance.queryOrder(qor); log.info("【订单查询返回结果】"+
		 JSONObject.fromObject(queryOrder));*/
		 
		/******************** 关闭订单**************************/
		
		/*CloseOrderResultBean resultBean = instance.closeOrder("ZL20160806113740");
		log.info("【关闭订单返回结果】" +JSON.toJSONString(resultBean));*/
		
		/******************** 转换短连接**************************/
		/*ShortUrlResult result = instance.shortUrl("weixin://wxpay/bizpayurl?pr=jnvymmx");
		log.info("【转换短连接返回结果】" +JSON.toJSONString(result));*/
		
	}
}
