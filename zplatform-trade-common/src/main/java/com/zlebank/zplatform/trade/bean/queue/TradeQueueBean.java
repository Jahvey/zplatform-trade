/* 
 * TradeQueueBean.java  
 * 
 * version TODO
 *
 * 2016年7月19日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean.queue;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年7月19日 下午3:25:00
 * @since 
 */
public class TradeQueueBean implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5159052981161149825L;

	/**
	 * 交易序列号
	 */
	@JSONField(name="txnseqno")
	private String txnseqno;
	/**
	 * 支付机构代码
	 */
	@JSONField(name="payInsti")
	private String payInsti;
	/**
	 * 交易时间
	 */
	@JSONField(name="txnDateTime")
	private String txnDateTime;
	
	/**
	 * 交易类型
	 */
	@JSONField(name="busiType")
	private String busiType;
	
	/**
	 * 交易实现类
	 */
	@JSONField(serialize = false)
	private String impl;
	
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
	 * @return the payInsti
	 */
	public String getPayInsti() {
		return payInsti;
	}
	/**
	 * @param payInsti the payInsti to set
	 */
	public void setPayInsti(String payInsti) {
		this.payInsti = payInsti;
	}
	/**
	 * @return the txnDateTime
	 */
	public String getTxnDateTime() {
		return txnDateTime;
	}
	/**
	 * @param txnDateTime the txnDateTime to set
	 */
	public void setTxnDateTime(String txnDateTime) {
		this.txnDateTime = txnDateTime;
	}
	
	
	/**
	 * @return the impl
	 */
	public String getImpl() {
		return impl;
	}
	/**
	 * @param impl the impl to set
	 */
	public void setImpl(String impl) {
		this.impl = impl;
	}
	
	/**
	 * @return the busiType
	 */
	public String getBusiType() {
		return busiType;
	}
	/**
	 * @param busiType the busiType to set
	 */
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	public static void main(String[] args) {
		TradeQueueBean tradeQueueBean = new TradeQueueBean();
		tradeQueueBean.setPayInsti("123455");
		String jsonString = JSON.toJSONString(tradeQueueBean);
		System.out.println(jsonString);
		
	}
}
