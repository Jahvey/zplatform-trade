/* 
 * CreateOrder_Request.java  
 * 
 * version TODO
 *
 * 2015年10月8日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.message;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 支付定单生成【请求报文】
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年10月8日 下午7:34:03
 * @since 
 */
public class CreateOrder_Request  extends BaseMessage {
    /**前台通知地址**/
    private String frontUrl;
    /**后台通知地址**/
    @NotEmpty
    private String backUrl;
    /**一级商户代码**/
    @NotEmpty
    private String merId;
    /**二级商户代码**/
    private String subMerId;
    /**二级商户全称**/
    private String subMerName;
    /**二级商户简称**/
    private String subMerAbbr;
    /**渠道类型**/
    @NotEmpty
    private String channelType;
    /**接入类型**/
    @NotEmpty
    private String accessType;
    /**商户订单号**/
    @NotEmpty
    private String orderId;
    /**订单发送时间**/
    @NotEmpty
    private String txnTime;
    /**支付超时时间**/
    @NotEmpty
    private String payTimeout;
    /**交易金额**/
    @NotEmpty
    private String txnAmt;
    /**交易币种**/
    @NotEmpty
    private String currencyCode;
    /**银行卡验证信息及身份信息**/
    private String customerInfo;
    /**保留域**/
    private String reserved;
    /**风险信息域**/
    private String riskRateInfo;
    /**订单描述**/
    @NotEmpty
    private String orderDesc;
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
    public String getMerId() {
        return merId;
    }
    public void setMerId(String merId) {
        this.merId = merId;
    }
    public String getSubMerId() {
        return subMerId;
    }
    public void setSubMerId(String subMerId) {
        this.subMerId = subMerId;
    }
    public String getSubMerName() {
        return subMerName;
    }
    public void setSubMerName(String subMerName) {
        this.subMerName = subMerName;
    }
    public String getSubMerAbbr() {
        return subMerAbbr;
    }
    public void setSubMerAbbr(String subMerAbbr) {
        this.subMerAbbr = subMerAbbr;
    }
 
    public String getChannelType() {
        return channelType;
    }
    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }
    public String getAccessType() {
        return accessType;
    }
    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getTxnTime() {
        return txnTime;
    }
    public void setTxnTime(String txnTime) {
        this.txnTime = txnTime;
    }
    public String getPayTimeout() {
        return payTimeout;
    }
    public void setPayTimeout(String payTimeout) {
        this.payTimeout = payTimeout;
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
    public String getCustomerInfo() {
        return customerInfo;
    }
    public void setCustomerInfo(String customerInfo) {
        this.customerInfo = customerInfo;
    }
    public String getReserved() {
        return reserved;
    }
    public void setReserved(String reserved) {
        this.reserved = reserved;
    }
    public String getRiskRateInfo() {
        return riskRateInfo;
    }
    public void setRiskRateInfo(String riskRateInfo) {
        this.riskRateInfo = riskRateInfo;
    }
    public String getOrderDesc() {
        return orderDesc;
    }
    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public static void main(String[] args) {
        System.out.println((new CreateOrder_Request()).toString());
    }
}
