/* 
 * TradeQueueScanService.java  
 * 
 * version TODO
 *
 * 2016年7月19日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.timeout.service;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年7月19日 下午5:27:37
 * @since 
 */
public interface TradeQueueScanService {

	/**
	 * 扫描交易队列
	 */
	public void scanTradeQueue();
	
	
	public void scanOverTimeQueue();
	
	/**
	 * 扫描退款交易队列
	 */
	public void scanRefundTradeQueue();
}
