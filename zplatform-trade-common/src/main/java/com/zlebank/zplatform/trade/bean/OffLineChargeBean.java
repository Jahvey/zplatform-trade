/* 
 * OffLineChargeBean.java  
 * 
 * version TODO
 *
 * 2016年8月24日 
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
 * @date 2016年8月24日 上午9:05:25
 * @since 
 */
public class OffLineChargeBean implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3789038050097283549L;
	private String txnType;// 交易类型
	private String txnSubType;// 交易子类
	private String bizType;// 产品类型
	private String channelType;// 渠道类型
	private String memberId;//会员号
	private String coopInsti;//合作机构
	private String orderId;//充值订单号
	private String txnAmt;//交易金额
	private String backURL;//后台通知地址
	private String chargeCode;//充值码
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
	 * @return the backURL
	 */
	public String getBackURL() {
		return backURL;
	}
	/**
	 * @param backURL the backURL to set
	 */
	public void setBackURL(String backURL) {
		this.backURL = backURL;
	}
	/**
	 * @return the chargeCode
	 */
	public String getChargeCode() {
		return chargeCode;
	}
	/**
	 * @param chargeCode the chargeCode to set
	 */
	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
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
	
	
	
}
