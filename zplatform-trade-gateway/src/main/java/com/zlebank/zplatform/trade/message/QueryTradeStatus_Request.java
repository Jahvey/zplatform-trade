/* 
 * QueryTradeState_Request.java  
 * 
 * version TODO
 *
 * 2015年10月10日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.message;

/**
 * 交易状态查询【请求报文】
 *
 * @author yangpeng
 * @version
 * @date 2015年10月10日 下午2:30:20
 * @since 
 */
public class QueryTradeStatus_Request extends BaseMessage{
    /**渠道类型**/
    private String  channelType;
    /**接入类型**/
    private String accessType;
    /**订单发送时间**/
    private String txnTime;
    /**商户订单号**/
    private String orderId;
    /**交易查询流水号**/
    private String queryId;

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
    public String getTxnTime() {
        return txnTime;
    }
    public void setTxnTime(String txnTime) {
        this.txnTime = txnTime;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getQueryId() {
        return queryId;
    }
    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }
}
