/* 
 * NotifyQueueBean.java  
 * 
 * version TODO
 *
 * 2016年8月3日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean.queue;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年8月3日 上午10:36:35
 * @since 
 */
public class NotifyQueueBean implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2310401554020873944L;
	
	@JSONField(name="txnseqno")
	private String txnseqno;
	@JSONField(name="sendDateTime")
	private String sendDateTime;
	@JSONField(name="sendTimes")
	private int sendTimes;
	
	/**
	 * @return the txnseqno
	 */
	public String getTxnseqno() {
		return txnseqno;
	}
	/**
	 * @param txnseqno the txnseqno to set
	 */
	public void setTxnseqno(String txnseqno) {
		this.txnseqno = txnseqno;
	}
	/**
	 * @return the sendDateTime
	 */
	public String getSendDateTime() {
		return sendDateTime;
	}
	/**
	 * @param sendDateTime the sendDateTime to set
	 */
	public void setSendDateTime(String sendDateTime) {
		this.sendDateTime = sendDateTime;
	}
	/**
	 * @return the sendTimes
	 */
	public int getSendTimes() {
		return sendTimes;
	}
	/**
	 * @param sendTimes the sendTimes to set
	 */
	public void setSendTimes(int sendTimes) {
		this.sendTimes = sendTimes;
	}
	
	

}
