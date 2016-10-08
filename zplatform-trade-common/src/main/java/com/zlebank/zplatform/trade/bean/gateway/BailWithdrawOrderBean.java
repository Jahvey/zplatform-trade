package com.zlebank.zplatform.trade.bean.gateway;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class BailWithdrawOrderBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "param.empty.coopInstiId")
	@Length(max = 15, message = "param.error.coopInstiId")
	private String coopInstiId = "";
	@Length(max = 15, message = "param.error.fromMerId")
	private String fromMerId = "";
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

	public String getCustomerIp() {
		return customerIp;
	}

	public void setCustomerIp(String customerIp) {
		this.customerIp = customerIp;
	}

	public String getFrontUrl() {
		return frontUrl;
	}

	public void setFrontUrl(String frontUrl) {
		this.frontUrl = frontUrl;
	}

	public String getBackUrl() {
		return backUrl;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}

	public String getCoopInstiId() {
		return coopInstiId;
	}

	public void setCoopInstiId(String coopInstiId) {
		this.coopInstiId = coopInstiId;
	}

	public String getFromMerId() {
		return fromMerId;
	}

	public void setFromMerId(String fromMerId) {
		this.fromMerId = fromMerId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getTxnSubType() {
		return txnSubType;
	}

	public void setTxnSubType(String txnSubType) {
		this.txnSubType = txnSubType;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public String getOrderTimeout() {
		return orderTimeout;
	}

	public void setOrderTimeout(String orderTimeout) {
		this.orderTimeout = orderTimeout;
	}

	public String getTxnTime() {
		return txnTime;
	}

	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}

}
