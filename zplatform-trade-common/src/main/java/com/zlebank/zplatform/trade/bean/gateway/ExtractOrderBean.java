/* 
 * ExtractOrderBean.java  
 * 
 * version TODO
 *
 * 2016年9月28日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean.gateway;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年9月28日 下午3:13:55
 * @since 
 */
public class ExtractOrderBean implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7893830297923017345L;
	@NotEmpty(message = "param.empty.coopInstiId")
	@Length(max = 15, message = "param.error.coopInstiId")
	private String coopInstiId = "";
	@Length(max = 15, message = "param.error.fromMerId")
	private String merId = "";
	@Length(max = 32, message = "param.error.orderId")
	private String orderId = "";
	@NotEmpty(message = "param.empty.txnType")
	@Length(max = 2, message = "param.error.txnType")
	private String txnType = "";
	@NotEmpty(message = "param.empty.txnSubType")
	@Length(max = 2, message = "param.error.txnSubType")
	private String txnSubType = "";
	@NotEmpty(message = "param.empty.bizType")
	@Length(max = 6, message = "param.error.bizType")
	private String bizType = "";
	@NotEmpty(message = "param.empty.txnAmt")
	@Length(max = 12, message = "param.error.txnAmt")
	private String txnAmt = "";
	@NotEmpty(message = "param.empty.currencyCode")
	@Length(max = 3, message = "param.error.currencyCode")
	private String currencyCode = "";
	@Length(max = 32, message = "param.error.orderDesc")
	private String orderDesc = "";
	@NotEmpty(message = "param.empty.orderTimeout")
	@Length(max = 10, message = "param.error.orderTimeout")
	private String orderTimeout = "";
	@Length(max = 14, message = "param.error.txnTime")
	private String txnTime = "";
	@Length(max = 256, message = "param.error.frontUrl")
	private String frontUrl = "";
	@Length(max = 256, message = "param.error.backUrl")
	private String backUrl = "";
	@Length(max = 40, message = "param.error.customerIp")
	private String customerIp = "";
	/**
	 * @return the coopInstiId
	 */
	public String getCoopInstiId() {
		return coopInstiId;
	}
	/**
	 * @param coopInstiId the coopInstiId to set
	 */
	public void setCoopInstiId(String coopInstiId) {
		this.coopInstiId = coopInstiId;
	}
	/**
	 * @return the merId
	 */
	public String getMerId() {
		return merId;
	}
	/**
	 * @param merId the merId to set
	 */
	public void setMerId(String merId) {
		this.merId = merId;
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
	 * @return the txnAmt
	 */
	public String getTxnAmt() {
		return txnAmt;
	}
	/**
	 * @param txnAmt the txnAmt to set
	 */
	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}
	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}
	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	/**
	 * @return the orderDesc
	 */
	public String getOrderDesc() {
		return orderDesc;
	}
	/**
	 * @param orderDesc the orderDesc to set
	 */
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}
	/**
	 * @return the orderTimeout
	 */
	public String getOrderTimeout() {
		return orderTimeout;
	}
	/**
	 * @param orderTimeout the orderTimeout to set
	 */
	public void setOrderTimeout(String orderTimeout) {
		this.orderTimeout = orderTimeout;
	}
	/**
	 * @return the txnTime
	 */
	public String getTxnTime() {
		return txnTime;
	}
	/**
	 * @param txnTime the txnTime to set
	 */
	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}
	/**
	 * @return the frontUrl
	 */
	public String getFrontUrl() {
		return frontUrl;
	}
	/**
	 * @param frontUrl the frontUrl to set
	 */
	public void setFrontUrl(String frontUrl) {
		this.frontUrl = frontUrl;
	}
	/**
	 * @return the backUrl
	 */
	public String getBackUrl() {
		return backUrl;
	}
	/**
	 * @param backUrl the backUrl to set
	 */
	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}
	/**
	 * @return the customerIp
	 */
	public String getCustomerIp() {
		return customerIp;
	}
	/**
	 * @param customerIp the customerIp to set
	 */
	public void setCustomerIp(String customerIp) {
		this.customerIp = customerIp;
	}
	
	
}
