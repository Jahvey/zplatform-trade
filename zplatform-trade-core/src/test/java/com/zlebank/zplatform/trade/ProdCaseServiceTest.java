/* 
 * ProdCaseServiceTest.java  
 * 
 * version TODO
 *
 * 2016年9月14日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.gateway.OrderBean;
import com.zlebank.zplatform.trade.bean.wap.WapWithdrawAccBean;
import com.zlebank.zplatform.trade.bean.wap.WapWithdrawBean;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.service.IGateWayService;
import com.zlebank.zplatform.trade.service.IProdCaseService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年9月14日 下午4:34:27
 * @since 
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration("/AccountContextTest.xml") 
public class ProdCaseServiceTest {

	@Autowired
	private IProdCaseService prodCaseService;
	
	public void test(){
		String json ="{\"accNo\":\"\",\"accType\":\"\",\"accessType\":\"\",\"backUrl\":\"http://192.168.101.232:8180:8180//zplatform-netsale-api/mock/resp\",\"bizType\":\"000207\",\"cardTransData\":\"\",\"certId\":\"-1\",\"channelType\":\"00\",\"coopInstiId\":\"\",\"currencyCode\":\"156\",\"customerInfo\":\"\",\"customerIp\":\"\",\"defaultPayType\":\"\",\"encoding\":\"\",\"encryptCertId\":\"\",\"frontFailUrl\":\"\",\"frontUrl\":\"http://192.168.101.232:8180:8180//zplatform-netsale-api/mock/resp\",\"instalTransInfo\":\"\",\"issInsCode\":\"\",\"memberId\":\"100000000001423\",\"merAbbr\":\"商户简称-20160914160000\",\"merId\":\"200000000001437\",\"merName\":\"商户全称-20160914160000\",\"orderDesc\":\"订单描述-20160914160000\",\"orderId\":\"OD20160914160000\",\"orderTimeout\":\"1800000\",\"orderType\":\"CONSUME\",\"payTimeout\":\"20160914170000\",\"productcode\":\"P20160914155\",\"reqReserved\":\"\",\"reserved\":\"\",\"riskRateInfo\":\"merUserId=100000000001423&commodityQty=0&commodityUnitPrice=0&\",\"signMethod\":\"\",\"signature\":\"\",\"supPayType\":\"\",\"txnAmt\":\"1\",\"txnSubType\":\"00\",\"txnTime\":\"20160914160000\",\"txnType\":\"17\",\"userMac\":\"\",\"version\":\"\"}";	
		OrderBean orderBean = JSON.parseObject(json, OrderBean.class);
		
		ResultBean verifyBusiness = prodCaseService.verifyBusiness(orderBean);
		System.out.println(JSON.toJSONString(verifyBusiness));
	}
	
	@Autowired
	private IGateWayService gateWayService;
	
	
	
	public void test_order() throws TradeException{
		String json = "{\"accNo\":\"\",\"accType\":\"\",\"accessType\":\"\",\"backUrl\":\"http://192.168.101.232:8180:8180//zplatform-netsale-api/mock/resp\",\"bizType\":\"000207\",\"cardTransData\":\"\",\"certId\":\"-1\",\"channelType\":\"00\",\"coopInstiId\":\"300000000000006\",\"currencyCode\":\"156\",\"customerInfo\":\"\",\"customerIp\":\"\",\"defaultPayType\":\"\",\"encoding\":\"\",\"encryptCertId\":\"\",\"frontFailUrl\":\"\",\"frontUrl\":\"http://192.168.101.232:8180:8180//zplatform-netsale-api/mock/resp\",\"instalTransInfo\":\"\",\"issInsCode\":\"\",\"memberId\":\"100000000001458\",\"merAbbr\":\"商户简称-20160918171728\",\"merId\":\"200000000001437\",\"merName\":\"商户全称-20160918171728\",\"orderDesc\":\"订单描述-20160918171728\",\"orderId\":\"OD20160918171721\",\"orderTimeout\":\"1800000\",\"orderType\":\"CONSUME\",\"payTimeout\":\"20160918181728\",\"productcode\":\"8000000001\",\"reqReserved\":\"\",\"reserved\":\"\",\"riskRateInfo\":\"merUserId=100000000001458&commodityQty=0&commodityUnitPrice=0&\",\"signMethod\":\"\",\"signature\":\"\",\"supPayType\":\"\",\"txnAmt\":\"132\",\"txnSubType\":\"00\",\"txnTime\":\"20160918171728\",\"txnType\":\"17\",\"userMac\":\"\",\"version\":\"\"}";
		OrderBean orderBean = JSON.parseObject(json, OrderBean.class);
		
		String dealWithWapOrder = gateWayService.dealWithWapOrder(orderBean);
	}
	
	
	public void test_Withdraw() throws TradeException{
		//{"amount":"100","coopInstiId":"300000000000006","paypassWd":"111111","backUrl":"wallet message has no this field","bindId":"2019","memberId":"100000000001423","virtualId":"wallet message has no this field","orderId":"","txnTime":""}
		
		WapWithdrawBean withdrawBean = new WapWithdrawBean();
		withdrawBean.setBindId("2019");
		withdrawBean.setAmount("1");
		withdrawBean.setCoopInstiId("300000000000006");
		withdrawBean.setMemberId("100000000001423");
		withdrawBean.setOrderId("OD"+DateUtil.getCurrentDateTime());
		withdrawBean.setTxnTime(DateUtil.getCurrentDateTime());
		withdrawBean.setTxnType("09");
		withdrawBean.setTxnSubType("00");
		withdrawBean.setBizType("000207");
		
		//WapWithdrawAccBean withdrawAccBean = new WapWithdrawAccBean();
		
		gateWayService.withdraw(JSON.toJSONString(withdrawBean));
	}
	
	
	public void test_sendMessage() throws TradeException{
		String json = "{\"bindId\":\"2060\",\"cardNo\":\"\",\"cardType\":\"\",\"certifId\":\"\",\"certifTp\":\"\",\"customerNm\":\"\",\"cvn2\":\"\",\"expired\":\"\",\"phoneNo\":\"13436975946\",\"tn\":\"160921000600063141\"}";
		gateWayService.sendSMSMessage(json);
	}
	
	@Test
	public void test_submitpay() throws TradeException{
		String json = "{\"bindId\":\"2060\",\"currencyCode\":\"\",\"memberId\":\"100000000001497\",\"paypassWd\":\"123456\",\"smsCode\":\"388381\",\"tn\":\"160921000600063142\",\"txnAmt\":\"1\"}";
		gateWayService.submitPay(json);
	}
}
