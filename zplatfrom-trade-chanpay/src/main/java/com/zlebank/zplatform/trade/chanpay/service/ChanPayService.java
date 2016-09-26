/* 
 * ChanPayService.java  
 * 
 * version TODO
 *
 * 2016年4月29日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.chanpay.service;


import java.util.List;




import com.zlebank.zplatform.trade.chanpay.bean.ReturnMessageBean;
import com.zlebank.zplatform.trade.chanpay.bean.async.RefundAsyncResultBean;
import com.zlebank.zplatform.trade.chanpay.bean.async.TradeAsyncResultBean;
import com.zlebank.zplatform.trade.chanpay.bean.cj.G40003Bean;
import com.zlebank.zplatform.trade.chanpay.bean.order.RefundOrderBean;
import com.zlebank.zplatform.trade.chanpay.bean.query.BankItemBean;
import com.zlebank.zplatform.trade.chanpay.bean.query.QueryBankBean;
import com.zlebank.zplatform.trade.chanpay.bean.query.QueryTradeBean;
import com.zlebank.zplatform.trade.chanpay.service.impl.CjMsgSendService.sendAndGetBytes_Response;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年4月29日 下午1:20:19
 * @since 
 */
public interface ChanPayService {

	/**
	 * 退款
	 * @param refundOrderBean
	 * @return
	 */
	public ReturnMessageBean refund(RefundOrderBean refundOrderBean);
	
	/**
	 * 交易查询
	 * @param queryTradeBean
	 * @return
	 */
	public Object queryTrade(QueryTradeBean queryTradeBean);
	
	/**
	 * 查询银行列表
	 * @param queryBankBean
	 * @return
	 */
	public List<BankItemBean> queryBank(QueryBankBean queryBankBean);
	
	/**
	 * 交易异步通知处理方法
	 * @param tradeAsyncResultBean
	 * @return
	 */
	public boolean asyncNotifyTrade(TradeAsyncResultBean tradeAsyncResultBean);
	
	/**
	 * 退款异步通知处理方法
	 * @param refundAsyncResultBean
	 * @return
	 */
	public boolean asyncNotifyRefund(RefundAsyncResultBean refundAsyncResultBean);
	
	/***
	 * 获得对账文件
	 * @param inputMap
	 * @return
	 */
	public sendAndGetBytes_Response getRecAccFile(G40003Bean data);
}
