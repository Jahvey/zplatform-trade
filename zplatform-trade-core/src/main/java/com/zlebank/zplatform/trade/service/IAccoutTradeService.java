package com.zlebank.zplatform.trade.service;

import com.zlebank.zplatform.trade.bean.gateway.BailRechargeOrderBean;
import com.zlebank.zplatform.trade.bean.gateway.BailWithdrawOrderBean;
import com.zlebank.zplatform.trade.bean.gateway.TransferOrderBean;
import com.zlebank.zplatform.trade.bean.gateway.QueryAccBean;
import com.zlebank.zplatform.trade.bean.gateway.QueryAccResultBean;
import com.zlebank.zplatform.trade.exception.TradeException;

public interface IAccoutTradeService {
	/****
	 * 余额查询
	 * @param query
	 * @return
	 * @throws TradeException
	 */
   public QueryAccResultBean queryMemberBalance(QueryAccBean query)throws TradeException;
	/***
	 * 基本账户转账
	 * @param order
	 * @return
	 */
	public String transfer(TransferOrderBean order)throws TradeException;
	
	/***
	 * 保证金充值
	 * @param order
	 * @return
	 */
	public  String bailAccountRecharge(BailRechargeOrderBean order)throws TradeException;
	/****
	 *保证金提取
	 * @param order
	 * @return
	 */
	public  String bailAccountWithdraw(BailWithdrawOrderBean order)throws TradeException;
	
	
	
	
	
	
	
	
}
