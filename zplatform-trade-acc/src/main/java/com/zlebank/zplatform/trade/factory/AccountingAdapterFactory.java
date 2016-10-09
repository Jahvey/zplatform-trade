/* 
 * AccountingAdapterFactory.java  
 * 
 * version TODO
 *
 * 2015年9月7日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.factory;

import com.zlebank.zplatform.commons.dao.pojo.BusiTypeEnum;
import com.zlebank.zplatform.trade.accounting.credit.CreditConsumeAccounting;
import com.zlebank.zplatform.trade.accounting.credit.CreditRechargeAccounting;
import com.zlebank.zplatform.trade.accounting.credit.CreditRefundAccounting;
import com.zlebank.zplatform.trade.adapter.accounting.IAccounting;
import com.zlebank.zplatform.trade.adapter.accounting.impl.ChargeAccounting;
import com.zlebank.zplatform.trade.adapter.accounting.impl.ConsumeAccounting;
import com.zlebank.zplatform.trade.adapter.accounting.impl.InsteadPayAccoungting;
import com.zlebank.zplatform.trade.adapter.accounting.impl.RefundAccounting;
import com.zlebank.zplatform.trade.adapter.accounting.impl.SafeGuardMoneyAccounting;
import com.zlebank.zplatform.trade.adapter.accounting.impl.TransferAccounting;
import com.zlebank.zplatform.trade.adapter.accounting.impl.WithdrawAccounting;
import com.zlebank.zplatform.trade.bean.enums.BusinessEnum;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月7日 上午11:31:59
 * @since
 */
public class AccountingAdapterFactory {
	private static AccountingAdapterFactory accountingAdapterFactory;

	private AccountingAdapterFactory() {
	}

	public synchronized static AccountingAdapterFactory getInstance() {
		if (accountingAdapterFactory == null) {
			accountingAdapterFactory = new AccountingAdapterFactory();
		}
		return accountingAdapterFactory;
	}

	/*public IAccounting getAccounting(String busitype) {
		IAccounting accounting = null;
		if (busitype.equals("1000")) {// 消费
			accounting = new ConsumeAccounting();
		} else if (busitype.equals("2000")) {// 充值
			accounting = new ChargeAccounting();
		} else if (busitype.equals("6000")) {// 保障金
			accounting = new SafeGuardMoneyAccounting();
		}
		return accounting;
	}*/

	public IAccounting getAccounting(BusiTypeEnum busitype) {
		IAccounting accounting = null;
		switch (busitype) {
			case consumption:
				accounting = new ConsumeAccounting();
				break;
			case charge:
				accounting = new ChargeAccounting();
				break;
			case insteadPay:
				accounting = new InsteadPayAccoungting();
				break;
			case refund:
				accounting = new RefundAccounting();
				break;
			case safeGuard:
				accounting = new SafeGuardMoneyAccounting();
				break;
			case withdrawal:
				accounting = new WithdrawAccounting();
				break;
			case transfer:
				accounting = new TransferAccounting();
			default:
				break;
		}
		return accounting;
	}
	
	
	
	public IAccounting getAccounting(BusinessEnum code) {
		IAccounting accounting = null;
		switch (code) {
			case CREDIT_RECHARGE:
				accounting = new CreditRechargeAccounting();
				break;
			case CREDIT_CONSUME:
				accounting = new CreditConsumeAccounting();
				break;
			case CREDIT_REFUND:
				accounting = new CreditRefundAccounting();
				break;
			default:
				break;
		}
		return accounting;
		}
		
}
