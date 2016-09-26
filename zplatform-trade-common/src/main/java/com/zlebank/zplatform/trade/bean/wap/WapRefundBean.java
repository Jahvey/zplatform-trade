/* 
 * WapRefundBean.java  
 * 
 * version TODO
 *
 * 2015年10月22日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean.wap;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年10月22日 下午2:02:50
 * @since
 */
public class WapRefundBean {
    private String version;// 网关版本
    private String encoding;// 编码方式
    private String signature;// 签名
    private String signMethod;// 签名方法
    private String backUrl;// 后台通知地址
    private String virtualId;// 虚拟代码
    private String coopInstiId;//一级商户/合作机构
    private String merId;//二级商户
    private String memberId;// 会员ID
    private String txnTime;//提交时间
    private String orderId;// 商户订单号
    private String txnType;// 交易类型
    private String txnSubType;// 交易子类
    private String bizType;// 产品类型
    private String channelType;// 渠道类型
    private String accessType;// 接入类型
    private String origOrderId;// 原始交易流水号
    private String txnAmt;// 交易金额
    private String orderDesc;// 退款描述
    private String refundType;// 退款方式
    private String reqReserved;// 请求方保留域
    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }
    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }
    /**
     * @return the encoding
     */
    public String getEncoding() {
        return encoding;
    }
    /**
     * @param encoding the encoding to set
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
    /**
     * @return the signature
     */
    public String getSignature() {
        return signature;
    }
    /**
     * @param signature the signature to set
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }
    /**
     * @return the signMethod
     */
    public String getSignMethod() {
        return signMethod;
    }
    /**
     * @param signMethod the signMethod to set
     */
    public void setSignMethod(String signMethod) {
        this.signMethod = signMethod;
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
     * @return the virtualId
     */
    public String getVirtualId() {
        return virtualId;
    }
    /**
     * @param virtualId the virtualId to set
     */
    public void setVirtualId(String virtualId) {
        this.virtualId = virtualId;
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
     * @return the accessType
     */
    public String getAccessType() {
        return accessType;
    }
    /**
     * @param accessType the accessType to set
     */
    public void setAccessType(String accessType) {
        this.accessType = accessType;
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
     * @return the refundType
     */
    public String getRefundType() {
        return refundType;
    }
    /**
     * @param refundType the refundType to set
     */
    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }
    /**
     * @return the reqReserved
     */
    public String getReqReserved() {
        return reqReserved;
    }
    /**
     * @param reqReserved the reqReserved to set
     */
    public void setReqReserved(String reqReserved) {
        this.reqReserved = reqReserved;
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
	 * @return the origOrderId
	 */
	public String getOrigOrderId() {
		return origOrderId;
	}
	/**
	 * @param origOrderId the origOrderId to set
	 */
	public void setOrigOrderId(String origOrderId) {
		this.origOrderId = origOrderId;
	}
	
    
    
}
