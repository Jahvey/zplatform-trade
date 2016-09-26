/* 
 * TradeNotifyService.java  
 * 
 * version TODO
 *
 * 2016年7月21日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年7月21日 下午2:29:52
 * @since 
 */
public interface TradeNotifyService {

	/**
	 * 
	 * @param txnseqno
	 */
	public void notify(String txnseqno);
	
	/**
	 * @param txnseqno
	 */
	public void notifyExt(String txnseqno);
	
	public void queueNotfiy();
}
