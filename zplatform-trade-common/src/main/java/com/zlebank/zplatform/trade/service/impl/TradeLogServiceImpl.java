/* 
 * TradeLogServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年8月9日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.TradeLogService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年8月9日 上午10:23:41
 * @since 
 */
@Service("tradeLogService")
public class TradeLogServiceImpl implements TradeLogService{

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private ITxnsLogService txnsLogService;
	private static final String NAMESPACE_SPLIT = ":";
	private static final String NAMESPACE = "TRADE";
	/**
	 *
	 * @param message
	 * @param txnseqno
	 */
	@Override
	@Transactional(readOnly=true)
	public void saveRequestLog(String message, String txnseqno) {
		// TODO Auto-generated method stub
		TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		opsForValue.set(NAMESPACE+NAMESPACE_SPLIT+txnseqno+NAMESPACE_SPLIT+"Request"+NAMESPACE_SPLIT+txnsLog.getPayordno(), message, 30, TimeUnit.DAYS);
	}

	/**
	 *
	 * @param message
	 * @param txnseqno
	 */
	@Override
	@Transactional(readOnly=true)
	public void saveResponseLog(String message, String txnseqno) {
		// TODO Auto-generated method stub
		TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		opsForValue.set(NAMESPACE+NAMESPACE_SPLIT+txnseqno+NAMESPACE_SPLIT+"Response"+NAMESPACE_SPLIT+txnsLog.getPayordno(), message, 30, TimeUnit.DAYS);
	}

}
