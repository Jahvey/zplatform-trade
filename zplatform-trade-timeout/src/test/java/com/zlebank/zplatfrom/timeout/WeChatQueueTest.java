/* 
 * WeChatQueueTest.java  
 * 
 * version TODO
 *
 * 2016年7月25日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatfrom.timeout;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zlebank.zplatform.timeout.job.TradeQueueJob;
import com.zlebank.zplatform.trade.bean.queue.TradeQueueBean;
import com.zlebank.zplatform.trade.service.TradeNotifyService;
import com.zlebank.zplatform.trade.service.TradeQueueService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年7月25日 下午3:24:35
 * @since 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/*.xml")
public class WeChatQueueTest {
	@Autowired
	private TradeQueueService tradeQueueService;
	@Autowired
	private TradeQueueJob tradeQueueJob;
	@Autowired
	private TradeNotifyService tradeNotifyService;
	
	public void test_addQueue(){
		TradeQueueBean tradeQueueBean = new TradeQueueBean();
		tradeQueueBean.setTxnseqno("1608109900056024");
		tradeQueueBean.setPayInsti("91000002");
		tradeQueueBean.setTxnDateTime("20160816134555");
		tradeQueueService.addTradeQueue(tradeQueueBean);
	}
	
	@Test
	public void test_scan(){
		test_addQueue();
		//tradeQueueJob.scanTradeQueue();
		//tradeNotifyService.notify("1608039900055720");
	}
}
