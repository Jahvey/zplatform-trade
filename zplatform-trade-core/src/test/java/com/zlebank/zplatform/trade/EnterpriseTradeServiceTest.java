/* 
 * EnterpriseTradeServiceTest.java  
 * 
 * version TODO
 *
 * 2016年9月2日 
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
import com.zlebank.zplatform.member.exception.DataCheckFailedException;
import com.zlebank.zplatform.member.exception.GetAccountFailedException;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.service.EnterpriseTradeService;
import com.zlebank.zplatform.trade.utils.ConsUtil;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年9月2日 上午10:25:42
 * @since 
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration("/AccountContextTest.xml") 
public class EnterpriseTradeServiceTest {

	@Autowired
	private EnterpriseTradeService enterpriseTradeService;
	
	@Test
	//@Ignore
	public void test_raiseMoneyTransferFinish(){
		long tid = 455;
		try {
			enterpriseTradeService.raiseMoneyTransferFinish(tid);
		} catch (DataCheckFailedException | GetAccountFailedException
				| TradeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	//@Ignore
	public void test_merchReimbusementFinish(){
		long tid = 11;
		try {
			enterpriseTradeService.merchReimbusementFinish(tid);
		} catch (TradeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void test(){
		System.out.println(ConsUtil.getInstance().cons.getZlebank_coopinsti_code());
		System.out.println(JSON.toJSONString(ConsUtil.getInstance().cons));
	}
}
