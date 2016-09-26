/* 
 * TradeQueueJob.java  
 * 
 * version TODO
 *
 * 2016年7月19日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.timeout.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.timeout.service.TradeQueueScanService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年7月19日 下午5:12:21
 * @since 
 */
@Service("tradeQueueJob")
public class TradeQueueJob {

	@Autowired
	private TradeQueueScanService tradeQueueScanService;
	
	public void scanTradeQueue(){
		tradeQueueScanService.scanTradeQueue();
	}
	
	public void scanTradeTimeOutQueue(){
		tradeQueueScanService.scanOverTimeQueue();
	}
	
	public void scanRefundTradeQueue(){
		tradeQueueScanService.scanRefundTradeQueue();
	}
	
}
