/* 
 * ReaPayQueueTest.java  
 * 
 * version TODO
 *
 * 2016年7月26日 
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
import com.zlebank.zplatform.trade.service.TradeQueueService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年7月26日 上午10:26:32
 * @since 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/*.xml")
public class ReaPayQueueTest {

	@Autowired
	private TradeQueueService tradeQueueService;
	@Autowired
	private TradeQueueJob tradeQueueJob;
	public void test_addQueue(){
		TradeQueueBean tradeQueueBean = new TradeQueueBean();
		tradeQueueBean.setTxnseqno("1607199900055021");
		tradeQueueBean.setPayInsti("96000001");
		tradeQueueBean.setTxnDateTime("20160715115026");
		tradeQueueService.addTradeQueue(tradeQueueBean);
	}
	
	@Test
	public void test_scan(){
		test_addQueue();
		tradeQueueJob.scanTradeQueue();
	}
}
