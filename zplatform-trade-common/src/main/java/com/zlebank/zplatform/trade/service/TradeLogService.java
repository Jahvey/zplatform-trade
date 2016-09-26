/* 
 * TradeLogService.java  
 * 
 * version TODO
 *
 * 2016年8月9日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年8月9日 上午10:20:52
 * @since 
 */
public interface TradeLogService {

	/**
	 * 缓存交易请求日志
	 * @param message
	 * @param txnseqno
	 */
	public void saveRequestLog(String message,String txnseqno);
	
	/**
	 * 缓存交易应答日志
	 * @param message
	 * @param txnseqno
	 */
	public void saveResponseLog(String message,String txnseqno);
}
