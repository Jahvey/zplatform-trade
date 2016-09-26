/* 
 * TradeCompleteProcessingService.java  
 * 
 * version TODO
 *
 * 2016年7月20日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.timeout.service;

import com.zlebank.zplatform.trade.bean.ResultBean;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年7月20日 上午11:43:36
 * @since 
 */
public interface TradeCompleteProcessingService {

	/**
	 * 畅捷代收交易查询补账方法
	 * @param txnseqno
	 * @param resultBean
	 */
	public void chanPayCollectMoneyCompleteTrade(String txnseqno,ResultBean resultBean);
	
	/**
	 * 民生跨行代收交易查询补账方法
	 * @param txnseqno
	 * @param resultBean
	 */
	public void cmbcCrossLineCompleteTrade(String txnseqno,ResultBean resultBean);
	
	/**
	 * 微信交易查询补账方法
	 * @param txnseqno
	 * @param resultBean
	 */
	public void weChatCompleteTrade(String txnseqno,ResultBean resultBean);
	
	/**
	 * 融宝交易查询补账方法
	 * @param txnseqno
	 * @param resultBean
	 */
	public void reaPayCompleteTrade(String txnseqno,ResultBean resultBean);
	
	/**
	 * 微信扫码支付交易查询补账方法
	 * @param txnseqno
	 * @param resultBean
	 */
	public void weChatQRCompleteTrade(String txnseqno,ResultBean resultBean);
}
