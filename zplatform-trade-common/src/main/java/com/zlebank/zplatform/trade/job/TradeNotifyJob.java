/* 
 * TradeNotifyJob.java  
 * 
 * version TODO
 *
 * 2016年8月3日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.trade.service.TradeNotifyService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年8月3日 上午10:48:25
 * @since 
 */
@Service
public class TradeNotifyJob {
	private static final Log log = LogFactory.getLog(TradeNotifyJob.class);
	@Autowired
	private TradeNotifyService tradeNotifyService;
	
	public void tradeNotify(){
		tradeNotifyService.queueNotfiy();
	}
}
