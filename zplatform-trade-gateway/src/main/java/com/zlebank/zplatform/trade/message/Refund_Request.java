/* 
 * Refund_Request.java  
 * 
 * version v1.0
 *
 * 2015年10月23日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.message;

/**
 * Class Description
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年10月23日 下午7:57:39
 * @since 
 */
public class Refund_Request   extends BaseMessage {
    /**后台通知地址**/
    private String backUrl;
    /**个人会员号**/
    private String memberId;
    /**商户订单号**/
    private String orderId;
    /**订单发送时间**/
    private String txnTime;
    /**渠道类型**/
    private String channelType;
    /**接入类型**/
    private String accessType;
    /**原始交易流水号**/
    private String origQryId;
    /**交易金额**/
    private String txnAmt;
    /**退款描述**/
    private String orderDesc;
    /**退款方式**/
    private String refundType;
    /**请求方保留域**/
    private String reqReserved;
    

    public String getChannelType() {
        return channelType;
    }
    public String getAccessType() {
        return accessType;
    }
    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }
    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }
    public String getOrigQryId() {
        return origQryId;
    }
    public void setOrigQryId(String origQryId) {
        this.origQryId = origQryId;
    }
    public String getTxnAmt() {
        return txnAmt;
    }
    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }
    public String getOrderDesc() {
        return orderDesc;
    }
    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }
    public String getRefundType() {
        return refundType;
    }
    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }
    public String getReqReserved() {
        return reqReserved;
    }
    public void setReqReserved(String reqReserved) {
        this.reqReserved = reqReserved;
    }
    public String getBackUrl() {
        return backUrl;
    }
    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }
    public String getMemberId() {
        return memberId;
    }
    public void setMemberId(String memberId) {
        this.memberId = memberId;
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
    
}
