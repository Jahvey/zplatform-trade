/* 
 * TradeQueueService.java  
 * 
 * version TODO
 *
 * 2016年7月19日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import com.zlebank.zplatform.trade.bean.enums.TradeQueueEnum;
import com.zlebank.zplatform.trade.bean.queue.NotifyQueueBean;
import com.zlebank.zplatform.trade.bean.queue.TradeQueueBean;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年7月19日 下午3:37:07
 * @since 
 */
public interface TradeQueueService {

	/**
	 * 添加交易进入交易队列
	 * @param tradeQueueBean
	 */
	public void addTradeQueue(TradeQueueBean tradeQueueBean);
	
	/**
	 * 添加超时交易进入超时交易队列
	 * @param tradeQueueBean
	 */
	public void addTimeOutQueue(TradeQueueBean tradeQueueBean);
	
	/**
	 * 添加退款交易进入退款交易队列
	 * @param tradeQueueBean
	 */
	public void addRefundQueue(TradeQueueBean tradeQueueBean);
	
	/**
	 * 添加异步通知进入异步通知队列
	 * @param notifyQueueBean
	 */
	public void addNotifyQueue(NotifyQueueBean notifyQueueBean);
	
	/**
	 * 取得交易队列中最左侧的交易
	 * @return
	 */
	public TradeQueueBean tradeQueuePop();
	
	/**
	 * 取得超时交易队列最左侧的交易
	 * @return
	 */
	public TradeQueueBean timeOutQueuePop();
	
	/**
	 * 取得退款交易队列最左侧的交易
	 * @return
	 */
	public TradeQueueBean refundQueuePop();
	
	/**
	 * 根据交易队列枚举取得队列最左侧的交易
	 * @param tradeQueueEnum
	 * @return
	 */
	public TradeQueueBean queuePop(TradeQueueEnum tradeQueueEnum);
	
	/**
	 * 取得队列的大小
	 * @param queueEnum 
	 * @return
	 */
	public long getQueueSize(TradeQueueEnum queueEnum);
	
	public <T> T queuePop(TradeQueueEnum tradeQueueEnum, Class<T> clazz);
}
