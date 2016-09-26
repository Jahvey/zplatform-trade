/* 
 * QuickPayTest.java  
 * 
 * version TODO
 *
 * 2016年7月1日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatfrom.trade.chanpay.quickpay;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zlebank.zplatform.trade.adapter.quickpay.IQuickPayTrade;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.enums.TradeTypeEnum;
import com.zlebank.zplatform.trade.chanpay.quickpay.ChanpayQuickPayTradeThread;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年7月1日 下午5:35:39
 * @since 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/*.xml")
public class QuickPayTest {

	@Test
	@Ignore
	public void test_banksign() {
		IQuickPayTrade quickPayTrade = new ChanpayQuickPayTradeThread();
		TradeBean trade = new TradeBean();
		trade.setCardNo("6228480018543668976");
		trade.setAcctName("郭佳");
		trade.setCertType("01");
		trade.setCertId("110105198610094112");
		trade.setMobile("18600806796");
		trade.setCardType("1");
		
		trade.setMonth("");
		trade.setYear("");
		quickPayTrade.setTradeBean(trade);
		quickPayTrade.setTradeType(TradeTypeEnum.BANKSIGN);
		quickPayTrade.bankSign(trade);
	}

	@Test
	@Ignore
	public void test_sendSMS() {

	}
	
	@Test
	public void test_pay(){
		IQuickPayTrade quickPayTrade = new ChanpayQuickPayTradeThread();
		TradeBean trade = new TradeBean();
		trade.setCardNo("6228480018543668976");
		trade.setAcctName("郭佳");
		trade.setCertType("01");
		trade.setCertId("110105198610094112");
		trade.setMobile("18600806796");
		trade.setCardType("1");
		trade.setAmount("100");
		trade.setCardId(154L);
		trade.setBindCardId("16070400000302");
		trade.setTxnseqno("1607049900054019");
		trade.setMonth("");
		trade.setYear("");
		quickPayTrade.setTradeBean(trade);
		quickPayTrade.setTradeType(TradeTypeEnum.SUBMITPAY);
		quickPayTrade.submitPay(trade);
	}
}
