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
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.gateway.OrderBean;
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
	
	@Test
	public void test(){
		String json ="{\"accNo\":\"\",\"accType\":\"\",\"accessType\":\"\",\"backUrl\":\"http://192.168.101.232:8180:8180//zplatform-netsale-api/mock/resp\",\"bizType\":\"000207\",\"cardTransData\":\"\",\"certId\":\"-1\",\"channelType\":\"00\",\"coopInstiId\":\"\",\"currencyCode\":\"156\",\"customerInfo\":\"\",\"customerIp\":\"\",\"defaultPayType\":\"\",\"encoding\":\"\",\"encryptCertId\":\"\",\"frontFailUrl\":\"\",\"frontUrl\":\"http://192.168.101.232:8180:8180//zplatform-netsale-api/mock/resp\",\"instalTransInfo\":\"\",\"issInsCode\":\"\",\"memberId\":\"100000000001423\",\"merAbbr\":\"商户简称-20160914160000\",\"merId\":\"200000000001437\",\"merName\":\"商户全称-20160914160000\",\"orderDesc\":\"订单描述-20160914160000\",\"orderId\":\"OD20160914160000\",\"orderTimeout\":\"1800000\",\"orderType\":\"CONSUME\",\"payTimeout\":\"20160914170000\",\"productcode\":\"P20160914155\",\"reqReserved\":\"\",\"reserved\":\"\",\"riskRateInfo\":\"merUserId=100000000001423&commodityQty=0&commodityUnitPrice=0&\",\"signMethod\":\"\",\"signature\":\"\",\"supPayType\":\"\",\"txnAmt\":\"1\",\"txnSubType\":\"00\",\"txnTime\":\"20160914160000\",\"txnType\":\"17\",\"userMac\":\"\",\"version\":\"\"}";	
		OrderBean orderBean = JSON.parseObject(json, OrderBean.class);
		
		ResultBean verifyBusiness = prodCaseService.verifyBusiness(orderBean);
		System.out.println(JSON.toJSONString(verifyBusiness));
	}
}
