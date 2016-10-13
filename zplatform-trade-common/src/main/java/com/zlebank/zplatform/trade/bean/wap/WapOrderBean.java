/* 
 * WapOrderBean.java  
 * 
 * version TODO
 *
 * 2015年10月12日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean.wap;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年10月12日 上午11:19:44
 * @since
 */
public class WapOrderBean implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3622055667997671419L;
    /**版本号*/
    @NotEmpty(message="param.empty")
    @Length(max=10,message="param.error")
    private String version;
    /**编码方式 */
    @NotEmpty(message="param.empty")
    @Length(max=2,message="param.error")
    private String encoding;
    /** 签名*/
    
    @Length(max=1024,message="param.error")
    private String signature;
    /**签名方法*/
    
    @Length(max=2,message="param.error")
    private String signMethod;
    /** 前台通知地址*/
  
    @Length(max=256,message="param.error")
    private String frontUrl;
    /**后台通知地址 */
   
    @Length(max=256,message="param.error")
    private String backUrl;
    
    /** 一级商户代码*/
    @NotEmpty(message="param.empty")
    @Length(max=15,message="param.error")
    private String merId="";
    /** 二级商户代码*/
    @Length(max=15,message="param.error")
    private String subMerId="";
    /** 二级商户全称*/
    @Length(max=40,message="param.error")
    private String subMerName="";
    /** 二级商户简称*/
    @Length(max=16,message="param.error")
    private String subMerAbbr="";
    
    /** 虚拟代码*/
    private String virtualId;
    
    @NotEmpty(message="param.empty")
    @Length(max=2,message="param.error")
    /**交易类型 */
    private String txnType="";
    @NotEmpty(message="param.empty")
    @Length(max=2,message="param.error")
    /** 交易子类*/
    private String txnSubType="";
    @NotEmpty(message="param.empty")
    @Length(max=6,message="param.error")
    /**产品类型 */
    private String bizType="";
    @NotEmpty(message="param.empty")
    @Length(max=2,message="param.error")
    /** 渠道类型*/
    private String channelType="";
    /** 接入类型*/
   
    @Length(max=1,message="param.error")
    private String accessType="";
    /** 商户订单号*/
    @NotEmpty(message="param.empty")
    @Length(max=32,message="param.error")
    private String orderId;
    /** 订单发送时间*/
    @NotEmpty(message="param.empty")
    @Length(max=14,message="param.error")
    private String txnTime;
    /** 支付超时时间*/
    @NotEmpty(message="param.empty")
    @Length(max=14,message="param.error")
    private String payTimeout;
    /** 交易金额*/
    @NotEmpty(message="param.empty")
    @Length(max=12,message="param.error")
    private String txnAmt;
    /** 交易币种*/
    @NotEmpty(message="param.empty")
    @Length(max=3,message="param.error")
    private String currencyCode;
    /** 银行卡验证信息及身份信息*/
    @Length(max=2048,message="param.error")
    private String customerInfo;
    /** 保留域*/
    @Length(max=2048,message="param.error")
    private String reserved;
    /** 风险信息域*/
    @Length(max=2048,message="param.error")
    private String riskRateInfo;
    /** 订单描述*/
    @Length(max=256,message="param.error")
    private String orderDesc;

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }
    /**
     * @param version
     *  the version to set
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
     * @param encoding
     *            the encoding to set
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
     * @param signature
     *            the signature to set
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
     * @param signMethod
     *            the signMethod to set
     */
    public void setSignMethod(String signMethod) {
        this.signMethod = signMethod;
    }
    /**
     * @return the frontUrl
     */
    public String getFrontUrl() {
        return frontUrl;
    }
    /**
     * @param frontUrl
     *            the frontUrl to set
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
     * @param backUrl
     *            the backUrl to set
     */
    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }
    /**
     * @return the merId
     */
    public String getMerId() {
        return merId;
    }
    /**
     * @param merId
     *            the merId to set
     */
    public void setMerId(String merId) {
        this.merId = merId;
    }
    /**
     * @return the subMerId
     */
    public String getSubMerId() {
        return subMerId;
    }
    /**
     * @param subMerId
     *            the subMerId to set
     */
    public void setSubMerId(String subMerId) {
        this.subMerId = subMerId;
    }
    /**
     * @return the subMerName
     */
    public String getSubMerName() {
        return subMerName;
    }
    /**
     * @param subMerName
     *            the subMerName to set
     */
    public void setSubMerName(String subMerName) {
        this.subMerName = subMerName;
    }
    /**
     * @return the subMerAbbr
     */
    public String getSubMerAbbr() {
        return subMerAbbr;
    }
    /**
     * @param subMerAbbr
     *            the subMerAbbr to set
     */
    public void setSubMerAbbr(String subMerAbbr) {
        this.subMerAbbr = subMerAbbr;
    }
    /**
     * @return the virtualId
     */
    public String getVirtualId() {
        return virtualId;
    }
    /**
     * @param virtualId
     *            the virtualId to set
     */
    public void setVirtualId(String virtualId) {
        this.virtualId = virtualId;
    }
    /**
     * @return the txnType
     */
    public String getTxnType() {
        return txnType;
    }
    /**
     * @param txnType
     *            the txnType to set
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
     * @param txnSubType
     *            the txnSubType to set
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
     * @param bizType
     *            the bizType to set
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
     * @param channelType
     *            the channelType to set
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
     * @param accessType
     *            the accessType to set
     */
    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }
    /**
     * @return the orderId
     */
    public String getOrderId() {
        return orderId;
    }
    /**
     * @param orderId
     *            the orderId to set
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
     * @param txnTime
     *            the txnTime to set
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
     * @param payTimeout
     *            the payTimeout to set
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
     * @param txnAmt
     *            the txnAmt to set
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
     * @param currencyCode
     *            the currencyCode to set
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
    /**
     * @return the customerInfo
     */
    public String getCustomerInfo() {
        return customerInfo;
    }
    /**
     * @param customerInfo
     *            the customerInfo to set
     */
    public void setCustomerInfo(String customerInfo) {
        this.customerInfo = customerInfo;
    }
    /**
     * @return the reserved
     */
    public String getReserved() {
        return reserved;
    }
    /**
     * @param reserved
     *            the reserved to set
     */
    public void setReserved(String reserved) {
        this.reserved = reserved;
    }
    /**
     * @return the riskRateInfo
     */
    public String getRiskRateInfo() {
        return riskRateInfo;
    }
    /**
     * @param riskRateInfo
     *            the riskRateInfo to set
     */
    public void setRiskRateInfo(String riskRateInfo) {
        this.riskRateInfo = riskRateInfo;
    }
    /**
     * @return the orderDesc
     */
    public String getOrderDesc() {
        return orderDesc;
    }
    /**
     * @param orderDesc
     *            the orderDesc to set
     */
    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

}
