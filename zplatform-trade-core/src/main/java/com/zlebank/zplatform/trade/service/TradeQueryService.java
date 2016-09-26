/* 
 * TradeQueryService.java  
 * 
 * version TODO
 *
 * 2016年8月24日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import com.zlebank.zplatform.trade.bean.enums.OrderStatusEnum;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年8月24日 下午2:31:00
 * @since 
 */
public interface TradeQueryService {

	/**
	 * 查询交易结果（渠道查询）
	 * @param txnseqno
	 * @return
	 */
	public OrderStatusEnum queryTradeResult(String txnseqno);
}