/* 
 * TradeAccTest.java  
 * 
 * version TODO
 *
 * 2016年9月2日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zlebank.zplatform.acc.bean.BusiAcct;
import com.zlebank.zplatform.acc.bean.BusiAcctQuery;
import com.zlebank.zplatform.acc.bean.enums.AcctStatusType;
import com.zlebank.zplatform.acc.bean.enums.Usage;
import com.zlebank.zplatform.acc.service.AccountQueryService;
import com.zlebank.zplatform.commons.dao.pojo.BusiTypeEnum;
import com.zlebank.zplatform.trade.adapter.accounting.IAccounting;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.factory.AccountingAdapterFactory;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年9月2日 下午3:54:26
 * @since 
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration("/AccountContextTest.xml") 
public class TradeAccTest {
	@Autowired
	private AccountQueryService accountQueryService;
	
	@Test
	public void test(){
		List<BusiAcct> busiAccList = null;
		BusiAcct fundAcct = null;
		busiAccList = accountQueryService.getBusiACCByMid("100000000000914");
		// 取得资金账户
		for (BusiAcct busiAcct : busiAccList) {
			if (Usage.BASICPAY == busiAcct.getUsage()) {
				fundAcct = busiAcct;
			}
		}
		BusiAcctQuery memberAcct = accountQueryService
				.getMemberQueryByID(fundAcct.getBusiAcctCode());
		if (memberAcct.getStatus() != AcctStatusType.NORMAL) {
			//throw new TradeException("GW19");
			System.out.println("error");
		}
	}
	
}
