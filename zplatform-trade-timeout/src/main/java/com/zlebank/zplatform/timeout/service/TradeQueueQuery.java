/* 
 * TradeQueueQuery.java  
 * 
 * version TODO
 *
 * 2016年7月20日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.timeout.service;

import com.zlebank.zplatform.trade.bean.queue.TradeQueueBean;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年7月20日 上午10:49:13
 * @since 
 */
public interface TradeQueueQuery extends Runnable{
	public void setTradeQueueBean(TradeQueueBean tradeQueueBean);
}
