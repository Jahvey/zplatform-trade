/* 
 * QueryTradeState_Response.java  
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
 * 交易状态查询【应答报文】
 *
 * @author yangpeng
 * @version
 * @date 2015年10月10日 下午2:32:15
 * @since 
 */
public class QueryTradeStatus_Response extends BaseMessage{
    /**订单发送时间**/
    private String txnTime;
    /**商户订单号**/
    private String orderId;
    /**交易查询流水号**/
    private String queryId;
    /**交易传输时间**/
    private String traceTime;
    /**清算日期**/
    private String settleDate;
    /**清算币种**/
    private String settleCurrencyCode;
    /**原交易应答码**/
    private String origRespCode;
    /**原交易应答信息**/
    private String origRespMsg;
    /**响应码**/
    private String     respCode;
    /**应答信息**/
    private String     respMsg;
    

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
    public String getTraceTime() {
        return traceTime;
    }
    public void setTraceTime(String traceTime) {
        this.traceTime = traceTime;
    }
    public String getSettleDate() {
        return settleDate;
    }
    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }
    public String getSettleCurrencyCode() {
        return settleCurrencyCode;
    }
    public void setSettleCurrencyCode(String settleCurrencyCode) {
        this.settleCurrencyCode = settleCurrencyCode;
    }
    public String getOrigRespCode() {
        return origRespCode;
    }
    public void setOrigRespCode(String origRespCode) {
        this.origRespCode = origRespCode;
    }
    public String getOrigRespMsg() {
        return origRespMsg;
    }
    public void setOrigRespMsg(String origRespMsg) {
        this.origRespMsg = origRespMsg;
    }
    public String getRespCode() {
        return respCode;
    }
    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }
    public String getRespMsg() {
        return respMsg;
    }
    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }
}
