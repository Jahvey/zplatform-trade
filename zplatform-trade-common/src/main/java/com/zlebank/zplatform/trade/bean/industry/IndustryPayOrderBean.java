/* 
 * IndustryConsumeOrderBean.java  
 * 
 * version TODO
 *
 * 2016年9月29日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean.industry;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 行业消费订单bean
 *
 * @author guojia
 * @version
 * @date 2016年9月29日 下午3:00:31
 * @since
 */
public class IndustryPayOrderBean implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8225578170676153753L;
	@NotEmpty(message = "param.empty.txnType")
	@Length(max = 2, message = "param.error.txnType")
	private String txnType;
	@NotEmpty(message = "param.empty.txnSubType")
	@Length(max = 2, message = "param.error.txnSubType")
	private String txnSubType;
	@NotEmpty(message = "param.empty.bizType")
	@Length(max = 6, message = "param.error.bizType")
	private String bizType;
	@NotEmpty(message = "param.empty.channelType")
	@Length(max = 2, message = "param.error.channelType")
	private String channelType;// 渠道类型
	@NotEmpty(message = "param.empty.memberId")
	@Length(max = 15, message = "param.error.memberId")
	private String memberId;// 会员ID
	@Length(max = 40, message = "param.error.merName")
	private String merName;// 商户全称
	@Length(max = 40, message = "param.error.merAbbr")
	private String merAbbr;// 商户简称
	@NotEmpty(message = "param.empty.orderId")
	@Length(max = 32, message = "param.error.orderId")
	private String orderId;// 商户订单号
	@NotEmpty(message = "param.empty.txnTime")
	@Length(max = 14, message = "param.error.txnTime")
	private String txnTime;// 订单发送时间
	@NotEmpty(message = "param.empty.orderTimeout")
	@Length(max = 14, message = "param.error.orderTimeout")
	private String payTimeout;// 支付超时时间
	@NotEmpty(message = "param.empty.txnAmt")
	@Length(max = 12, message = "param.error.txnAmt")
	private String txnAmt;// 交易金额
	@Length(max = 32, message = "param.error.productCode")
	private String productCode;// 产品代码
	@Length(max = 10, message = "param.error.groupCode")
	private String groupCode;// 群组代码
	@NotEmpty(message = "param.empty.currencyCode")
	@Length(max = 3, message = "param.error.currencyCode")
	private String currencyCode;// 交易币种
	@Length(max = 256, message = "param.error.orderDesc")
	private String orderDesc;// 订单描述
	@Length(max = 256, message = "param.error.frontUrl")
	private String frontUrl;// 前台通知地址
	@Length(max = 256, message = "param.error.backUrl")
	private String backUrl;// 后台通知地址
	@NotEmpty(message = "param.empty.coopInstiId")
	@Length(max = 15, message = "param.error.coopInstiId")
	private String coopInst;// 合作机构
	@NotEmpty(message = "param.empty.merchId")
	@Length(max = 15, message = "param.error.merchId")
	private String merchId;// 商户号
	@Length(max = 32, message = "param.error.customerIp")
	private String customerIp;
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
	 * @return the merName
	 */
	public String getMerName() {
		return merName;
	}
	/**
	 * @param merName the merName to set
	 */
	public void setMerName(String merName) {
		this.merName = merName;
	}
	/**
	 * @return the merAbbr
	 */
	public String getMerAbbr() {
		return merAbbr;
	}
	/**
	 * @param merAbbr the merAbbr to set
	 */
	public void setMerAbbr(String merAbbr) {
		this.merAbbr = merAbbr;
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
	 * @return the payTimeout
	 */
	public String getPayTimeout() {
		return payTimeout;
	}
	/**
	 * @param payTimeout the payTimeout to set
	 */
	public void setPayTimeout(String payTimeout) {
		this.payTimeout = payTimeout;
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
	 * @return the groupCode
	 */
	public String getGroupCode() {
		return groupCode;
	}
	/**
	 * @param groupCode the groupCode to set
	 */
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
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
	 * @return the coopInst
	 */
	public String getCoopInst() {
		return coopInst;
	}
	/**
	 * @param coopInst the coopInst to set
	 */
	public void setCoopInst(String coopInst) {
		this.coopInst = coopInst;
	}
	/**
	 * @return the merchId
	 */
	public String getMerchId() {
		return merchId;
	}
	/**
	 * @param merchId the merchId to set
	 */
	public void setMerchId(String merchId) {
		this.merchId = merchId;
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
