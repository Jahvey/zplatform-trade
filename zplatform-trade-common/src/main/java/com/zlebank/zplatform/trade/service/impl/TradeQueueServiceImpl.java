/* 
 * TradeQueueServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年7月19日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.trade.bean.enums.TradeQueueEnum;
import com.zlebank.zplatform.trade.bean.queue.NotifyQueueBean;
import com.zlebank.zplatform.trade.bean.queue.TradeQueueBean;
import com.zlebank.zplatform.trade.service.TradeQueueService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年7月19日 下午3:37:29
 * @since 
 */
@Service("tradeQueueService")
public class TradeQueueServiceImpl implements TradeQueueService{
	private final static Log log = LogFactory.getLog(TradeQueueServiceImpl.class);
	
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate; 
	
	public void addTradeQueue(TradeQueueBean tradeQueueBean){
		try {
			
			
			BoundListOperations<String, String> boundListOps = redisTemplate.boundListOps(TradeQueueEnum.TRADEQUEUE.getName());
			boundListOps.rightPush(JSON.toJSONString(tradeQueueBean));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addTimeOutQueue(TradeQueueBean tradeQueueBean){
		try {
			BoundListOperations<String, String> boundListOps = redisTemplate.boundListOps(TradeQueueEnum.TIMEOUTQUEUE.getName());
			boundListOps.rightPush(JSON.toJSONString(tradeQueueBean));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public TradeQueueBean tradeQueuePop(){
		return queuePop(TradeQueueEnum.TRADEQUEUE);
	}
	
	public TradeQueueBean timeOutQueuePop(){
		return queuePop(TradeQueueEnum.TIMEOUTQUEUE);
	}
	
	public TradeQueueBean refundQueuePop(){
		return queuePop(TradeQueueEnum.REFUNDQUEUE);
	}
	
	
	public TradeQueueBean queuePop(TradeQueueEnum tradeQueueEnum){
		TradeQueueBean tradeQueueBean =null;
		try {
			ListOperations<String, String> opsForList = redisTemplate.opsForList();
			String leftPop = opsForList.leftPop(tradeQueueEnum.getName());
			log.info("Queue 【"+tradeQueueEnum.getName()+"】 POP Value:"+leftPop);
			if(StringUtil.isEmpty(leftPop)){
				return null;
			}
			tradeQueueBean = JSON.parseObject(leftPop, TradeQueueBean.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tradeQueueBean;
	}
	
	public long getQueueSize(TradeQueueEnum queueEnum){
		if(redisTemplate.hasKey(queueEnum.getName())){
			ListOperations<String, String> opsForList = redisTemplate.opsForList();
			return opsForList.size(queueEnum.getName());
		}else{
			return 0;
		}
		
	}
	
	public <T> T queuePop(TradeQueueEnum tradeQueueEnum, Class<T> clazz){
		ListOperations<String, String> opsForList = redisTemplate.opsForList();
		String leftPop = opsForList.leftPop(tradeQueueEnum.getName());
		log.info("Queue 【"+tradeQueueEnum.getName()+"】 POP Value:"+leftPop);
		if(StringUtil.isEmpty(leftPop)){
			return null;
		}
		return JSON.parseObject(leftPop, clazz);
	}

	/**
	 *
	 * @param notifyQueueBean
	 */
	@Override
	public void addNotifyQueue(NotifyQueueBean notifyQueueBean) {
		// TODO Auto-generated method stub
		try {
			BoundListOperations<String, String> boundListOps = redisTemplate.boundListOps(TradeQueueEnum.NOTIFYQUEUE.getName());
			boundListOps.rightPush(JSON.toJSONString(notifyQueueBean));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param tradeQueueBean
	 */
	@Override
	public void addRefundQueue(TradeQueueBean tradeQueueBean) {
		// TODO Auto-generated method stub
		try {
			BoundListOperations<String, String> boundListOps = redisTemplate.boundListOps(TradeQueueEnum.REFUNDQUEUE.getName());
			boundListOps.rightPush(JSON.toJSONString(tradeQueueBean));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
}
