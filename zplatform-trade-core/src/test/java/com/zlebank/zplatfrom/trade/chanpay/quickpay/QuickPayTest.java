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

import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zlebank.zplatform.commons.dao.pojo.BusiTypeEnum;
import com.zlebank.zplatform.trade.adapter.accounting.IAccounting;
import com.zlebank.zplatform.trade.adapter.quickpay.IQuickPayTrade;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.enums.TradeTypeEnum;
import com.zlebank.zplatform.trade.chanpay.quickpay.ChanpayQuickPayTradeThread;
import com.zlebank.zplatform.trade.factory.AccountingAdapterFactory;
import com.zlebank.zplatform.trade.service.ITxnsLogService;

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

	@Autowired
	private ITxnsLogService txnsLogService;
	@Test
	public void test_banksign() {
		/*IQuickPayTrade quickPayTrade = new ChanpayQuickPayTradeThread();
		TradeBean trade = new TradeBean();
		trade.setCardNo("6228480018543668976");
		trade.setAcctName("郭佳");
		trade.setCertType("01");
		trade.setCertId("110105198610094112");
		trade.setMobile("18600806796");
		trade.setCardType("1");
		quickPayTrade.setTradeBean(trade);
		quickPayTrade.setTradeType(TradeTypeEnum.BANKSIGN);
		quickPayTrade.bankSign(trade);*/
		
		//1606289900053941
		
		//IAccounting accounting = AccountingAdapterFactory.getInstance().getAccounting(BusiTypeEnum.fromValue("4000"));
        //ResultBean accountResultBean = accounting.accountedFor("1606289900053941");
		
		txnsLogService.updateAnonOrderToMemberOrder("1607079900054460", "100000000000576");
	}

	//@Test
	public void test_sendSMS() {
		
	}
	
	public static void main(String[] args) {
		JcaContentSignerBuilder jcaContentSignerBuilder = new JcaContentSignerBuilder("SHA1withRSA");
	}
}
