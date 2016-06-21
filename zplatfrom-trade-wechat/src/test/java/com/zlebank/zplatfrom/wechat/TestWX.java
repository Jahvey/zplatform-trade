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
package com.zlebank.zplatfrom.wechat;

import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.wechat.exception.WXVerifySignFailedException;
import com.zlebank.zplatform.wechat.wx.WXApplication;
import com.zlebank.zplatform.wechat.wx.bean.QueryBillBean;
import com.zlebank.zplatform.wechat.wx.bean.QueryRefundBean;
import com.zlebank.zplatform.wechat.wx.bean.QueryRefundResultBean;
import com.zlebank.zplatform.wechat.wx.bean.RefundBean;
import com.zlebank.zplatform.wechat.wx.bean.RefundResultBean;
import com.zlebank.zplatform.wechat.wx.bean.WXOrderBean;

/**
 * 微信测试类
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年5月17日 下午3:08:19
 * @since
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/*.xml")
public class TestWX {

	private static final Log log = LogFactory.getLog(TestWX.class);

	
	public void test_downbill() {
		WXApplication instance = new WXApplication();
		QueryBillBean bill = new QueryBillBean();
		bill.setBill_date("20160520");
		bill.setBill_type("ALL");
		instance.downLoadBill(bill);
	}

	public void test_order() {
		WXApplication instance = new WXApplication();
		WXOrderBean order = new WXOrderBean();
		order.setBody("iPad");
		order.setDetail("iPad mini  16G  白色");
		order.setAttach("北京太阳宫");
		order.setOut_trade_no("ZL" + DateUtil.getCurrentDateTime());
		order.setTotal_fee("1");
		order.setTime_start(DateUtil.getCurrentDateTime());
		order.setTime_expire(DateUtil.formatDateTime("yyyyMMddHHmmss",
				DateUtil.skipDateTime(new Date(), 1)));
		order.setGoods_tag("WXG");
		order.setNotify_url(ConsUtil.getInstance().cons.getWechat_notify_url());

		try {
			JSONObject xml = instance.createOrder(order);
			log.info("【下订单返回结果】" + xml.toString());
		} catch (WXVerifySignFailedException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void test_createOrder() {
		WXApplication instance = new WXApplication();
		WXOrderBean order = new WXOrderBean();
		order.setBody("iPad");
		order.setDetail("iPad mini  16G  白色");
		order.setAttach("北京太阳宫");
		order.setOut_trade_no("ZL" + DateUtil.getCurrentDateTime());
		order.setTotal_fee("1");
		order.setTime_start(DateUtil.getCurrentDateTime());
		order.setTime_expire(DateUtil.formatDateTime("yyyyMMddHHmmss",
				DateUtil.skipDateTime(new Date(), 1)));
		order.setGoods_tag("WXG");
		order.setNotify_url(ConsUtil.getInstance().cons.getWechat_notify_url());

		try {
			JSONObject xml = instance.createOrder(order);
			log.debug("【下订单返回结果】" + xml.toString());
		} catch (WXVerifySignFailedException e) {
			e.printStackTrace();
		}
	}

	public void test_queryrefund() {
		WXApplication instance = new WXApplication();
		QueryRefundBean qrb = new QueryRefundBean();
		qrb.setTransaction_id("4006652001201605206059282375");
		QueryRefundResultBean refundQuery = instance.refundQuery(qrb);
		log.info("【退款查询返回结果】" + JSONObject.fromObject(refundQuery));
	}

	public void test_refund() throws WXVerifySignFailedException {
		WXApplication instance = new WXApplication();
		RefundBean rb = new RefundBean();
		rb.setOut_refund_no(String.valueOf(System.currentTimeMillis()));
		// 退款流水号（唯一，可当场生成）
		rb.setOut_trade_no("1605269000000006");// 原商户号（证联生成的）
		rb.setRefund_fee("1"); // 退款金额
		rb.setTotal_fee("1"); // 总金额
		rb.setTransaction_id("4003872001201605266327894875"); // 原微信订单号（微信返回的）
		RefundResultBean refund = instance.refund(rb); // 进行退款
		log.info("【退款返回结果】" + JSONObject.fromObject(refund));
	}

	
	/**
	 * @param args
	 * @throws WXVerifySignFailedException
	 */
	public static void main(String[] args) throws WXVerifySignFailedException {
		WXApplication instance = new WXApplication();

		/******************** 下订单 **************************/
		// WXOrderBean order = new WXOrderBean();
		// order.setBody("iPad");
		// order.setDetail("iPad mini  16G  白色");
		// order.setAttach("北京太阳宫");
		// order.setOut_trade_no("ZL"+DateUtil.getCurrentDateTime());
		// order.setTotal_fee("1");
		// order.setTime_start(DateUtil.getCurrentDateTime());
		// order.setTime_expire(DateUtil.formatDateTime("yyyyMMddHHmmss",
		// DateUtil.skipDateTime(new Date(), 1)));
		// order.setGoods_tag("WXG");
		// order.setNotify_url("http://www.weixin.qq.com/wxpay/pay.php");
		//
		// try {
		// JSONObject xml = instance.createOrder(order);
		// log.debug("【下订单返回结果】"+ xml.toString());
		// } catch (WXVerifySignFailedException e) {
		// e.printStackTrace();
		// }

		/******************** 下载对账单 **************************/
		/*
		 * QueryBillBean bill = new QueryBillBean();
		 * bill.setBill_date("20160520"); bill.setBill_type("ALL");
		 * instance.downLoadBill(bill);
		 */

		/******************** 退款 **************************/
		RefundBean rb = new RefundBean();
		rb.setOut_refund_no(String.valueOf(System.currentTimeMillis()));//
		// 退款流水号（唯一，可当场生成）
		rb.setOut_trade_no("1605269000000005");// 原商户号（证联生成的）
		rb.setRefund_fee("1");// 退款金额
		rb.setTotal_fee("1");// 总金额
		rb.setTransaction_id("4003872001201605266327894875");// 原微信订单号（微信返回的）
		RefundResultBean refund = instance.refund(rb); // 进行退款
		log.debug("【退款返回结果】" + JSONObject.fromObject(refund));

		/******************** 退款查询 **************************/
		// QueryRefundBean qrb = new QueryRefundBean();
		// qrb.setTransaction_id("4006652001201605206059282375");
		// QueryRefundResultBean refundQuery = instance.refundQuery(qrb);
		// log.debug("【退款查询返回结果】"+ JSONObject.fromObject(refundQuery));

		/******************** 订单查询 **************************/
		/*
		 * QueryOrderBean qor = new QueryOrderBean();
		 * qor.setTransaction_id("4006652001201605206059282375");
		 * QueryOrderResultBean queryOrder = instance.queryOrder(qor);
		 * log.debug("【订单查询返回结果】"+ JSONObject.fromObject(queryOrder));
		 */
	}
}
