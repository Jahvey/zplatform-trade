/* 
 * IndustryTradeOrderTest.java  
 * 
 * version TODO
 *
 * 2016年9月30日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zlebank.zplatform.trade.bean.industry.IndustryPayOrderBean;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.service.IndustryAccountTradeService;
import com.zlebank.zplatform.trade.utils.DateUtil;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年9月30日 上午11:13:45
 * @since 
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration("/AccountContextTest.xml") 
public class IndustryTradeOrderTest {

	@Autowired
	private IndustryAccountTradeService industryAccountTradeService;
	
	@Test
	public void test_createChargeOrder(){
		IndustryPayOrderBean orderBean = new IndustryPayOrderBean();
		orderBean.setTxnType("34");
		orderBean.setTxnSubType("00");
		orderBean.setBizType("000206");
		orderBean.setChannelType("00");
		orderBean.setMemberId("100000000000576");
		orderBean.setMerName("测试");
		orderBean.setMerAbbr("测试");
		orderBean.setOrderId("TOD"+System.currentTimeMillis());
		orderBean.setTxnTime(DateUtil.getCurrentDateTime());
		orderBean.setPayTimeout(DateUtil.formatDateTime(DateUtil.DEFAULT_DATE_FROMAT, new Date(System.currentTimeMillis()+1000*60*60)));
		orderBean.setTxnAmt("13");
		orderBean.setProductCode("");
		orderBean.setGroupCode("0000000626");
		orderBean.setCurrencyCode("156");
		orderBean.setOrderDesc("测试订单");
		orderBean.setFrontUrl("");
		orderBean.setBackUrl("");
		orderBean.setCoopInst("300000000000027");
		orderBean.setMerchId("200000000000597");
		orderBean.setCustomerIp("127.0.0.0");
		try {
			industryAccountTradeService.createIndustryPayOrder(orderBean);
		} catch (TradeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void test_createConsumeOrder(){
		IndustryPayOrderBean orderBean = new IndustryPayOrderBean();
		orderBean.setTxnType("35");
		orderBean.setTxnSubType("00");
		orderBean.setBizType("000206");
		orderBean.setChannelType("00");
		orderBean.setMemberId("100000000000576");
		orderBean.setMerName("测试");
		orderBean.setMerAbbr("测试");
		orderBean.setOrderId("TOD"+System.currentTimeMillis());
		orderBean.setTxnTime(DateUtil.getCurrentDateTime());
		orderBean.setPayTimeout(DateUtil.formatDateTime(DateUtil.DEFAULT_DATE_FROMAT, new Date(System.currentTimeMillis()+1000*60*60)));
		orderBean.setTxnAmt("13");
		orderBean.setProductCode("");
		orderBean.setGroupCode("0000000626");
		orderBean.setCurrencyCode("156");
		orderBean.setOrderDesc("测试订单");
		orderBean.setFrontUrl("");
		orderBean.setBackUrl("");
		orderBean.setCoopInst("300000000000027");
		orderBean.setMerchId("200000000000597");
		orderBean.setCustomerIp("127.0.0.0");
		try {
			industryAccountTradeService.createIndustryPayOrder(orderBean);
		} catch (TradeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
	}
}
