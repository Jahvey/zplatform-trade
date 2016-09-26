/* 
 * RaiseMoneyTransferBean.java  
 * 
 * version TODO
 *
 * 2016年8月25日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean;

import java.io.Serializable;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年8月25日 上午10:48:00
 * @since
 */
public class RaiseMoneyTransferBean implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7121256769871268665L;

	private String txnType;// 交易类型
	private String txnSubType;// 交易子类
	private String bizType;// 产品类型
	private String channelType;// 渠道类型
	private String orderId;// 订单号
	private String memberId;// 商户ID
	private String financingId;// 融资人ID
	private String productCode;// 产品代码
	private String coopInsti;
	/**
	 * @return the txnType
	 */
	public String getTxnType() {
		return txnType;
	}
	/**
	 * @param txnType the txnType to set
	 */
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	/**
	 * @return the txnSubType
	 */
	public String getTxnSubType() {
		return txnSubType;
	}
	/**
	 * @param txnSubType the txnSubType to set
	 */
	public void setTxnSubType(String txnSubType) {
		this.txnSubType = txnSubType;
	}
	/**
	 * @return the bizType
	 */
	public String getBizType() {
		return bizType;
	}
	/**
	 * @param bizType the bizType to set
	 */
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	/**
	 * @return the channelType
	 */
	public String getChannelType() {
		return channelType;
	}
	/**
	 * @param channelType the channelType to set
	 */
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}
	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	/**
	 * @return the financingId
	 */
	public String getFinancingId() {
		return financingId;
	}
	/**
	 * @param financingId the financingId to set
	 */
	public void setFinancingId(String financingId) {
		this.financingId = financingId;
	}
	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}
	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	/**
	 * @return the coopInsti
	 */
	public String getCoopInsti() {
		return coopInsti;
	}
	/**
	 * @param coopInsti the coopInsti to set
	 */
	public void setCoopInsti(String coopInsti) {
		this.coopInsti = coopInsti;
	}
	
}
