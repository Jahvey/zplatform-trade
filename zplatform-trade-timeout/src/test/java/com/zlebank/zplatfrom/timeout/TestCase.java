/* 
 * TestCase.java  
 * 
 * version TODO
 *
 * 2016年7月19日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatfrom.timeout;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.trade.bean.enums.TradeQueueEnum;
import com.zlebank.zplatform.trade.bean.queue.TradeQueueBean;
import com.zlebank.zplatform.trade.service.TradeQueueService;



/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年7月19日 上午9:20:26
 * @since 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/*.xml")
public class TestCase {

	@Autowired
	private TradeQueueService tradeQueueService;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Test
	@Ignore
	public void test_redis(){
		TradeQueueBean tradeQueueBean = new TradeQueueBean();
		tradeQueueBean.setTxnseqno("1607199900064373");
		tradeQueueBean.setPayInsti("93000002");
		tradeQueueBean.setTxnDateTime("20160719161451");
		tradeQueueService.addTradeQueue(tradeQueueBean);
		
		long queueSize = tradeQueueService.getQueueSize(TradeQueueEnum.TRADEQUEUE);
		Assert.assertNotNull(queueSize);
		
		TradeQueueBean tradeQueuePop = tradeQueueService.tradeQueuePop();
		
		System.out.println(JSON.toJSONString(tradeQueuePop));
		System.out.println(queueSize);
	}
	@Test
	public void test_seq(){
		Long increment = redisTemplate.opsForValue().increment("seq", 1);
		System.out.println(increment);
	}
	
}
