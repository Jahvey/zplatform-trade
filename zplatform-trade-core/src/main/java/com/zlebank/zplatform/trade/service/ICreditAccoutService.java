package com.zlebank.zplatform.trade.service;

import com.zlebank.zplatform.trade.bean.gateway.CreditConsumeOrderBean;
import com.zlebank.zplatform.trade.bean.gateway.CreditRechargeOrderBean;
import com.zlebank.zplatform.trade.bean.gateway.CreditRefundOrderBean;
import com.zlebank.zplatform.trade.bean.gateway.TransferOrderBean;
import com.zlebank.zplatform.trade.exception.CommonException;

public interface ICreditAccoutService {
	/****
	 * 授信账户充值
	 * @param order
	 * @return
	 * @throws CommonException
	 */
	public String creditAccountRecharge(CreditRechargeOrderBean order) throws CommonException;
	/***
	 * 授信账户消费
	 * @param order
	 * @return
	 * @throws CommonException
	 */
	public String creditAccountConsume(CreditConsumeOrderBean order) throws CommonException;
	
	/***
	 * 授信账户退款
	 * @param order
	 * @return
	 * @throws CommonException
	 */
	public String creditAccountRefund(CreditRefundOrderBean order) throws CommonException;
	
	
	
	
	
	
	
	
	
}
