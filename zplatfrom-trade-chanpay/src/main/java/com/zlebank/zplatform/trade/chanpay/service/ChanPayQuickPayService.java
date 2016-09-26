/* 
 * ChanPayQuickPayService.java  
 * 
 * version TODO
 *
 * 2016年6月30日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.chanpay.service;

import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.exception.TradeException;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年6月30日 下午4:48:49
 * @since 
 */
public interface ChanPayQuickPayService {
	/**
	 * 实名认证
	 * @param tradeBean
	 * @return
	 * @throws TradeException
	 */
	public ResultBean realNameAuth(TradeBean tradeBean) throws TradeException;
	
	/**
	 * 协议签约
	 * @param tradeBean
	 * @return
	 * @throws TradeException
	 */
	public ResultBean protocolSign(TradeBean tradeBean) throws TradeException;
	
	/**
	 * 代收
	 * @param tradeBean
	 * @return
	 * @throws TradeException
	 */
	public ResultBean collectMoney(TradeBean tradeBean) throws TradeException;
	
	/**
	 * 账务处理
	 * @param resultBean
	 * @param txnseqno
	 * @return
	 */
	public ResultBean dealWithAccounting(ResultBean resultBean,String txnseqno);
	
	/**
	 * 查询畅捷代收交易结果
	 * @param txnseqno
	 * @return
	 */
	public ResultBean queryTrade(String txnseqno);
}
