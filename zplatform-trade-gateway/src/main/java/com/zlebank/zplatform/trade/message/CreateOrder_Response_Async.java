/* 
 * CreateOrder_Response_Async.java  
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
 * 支付定单生成【应答报文】【异步】
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年10月8日 下午7:56:22
 * @since 
 */
public class CreateOrder_Response_Async  extends BaseMessage{
    /**交易类型**/
    @NotEmpty
    private String txnType;
    /**交易子类**/
    @NotEmpty
    private String txnSubType;
    /**产品类型**/
    @NotEmpty
    private String bizType;
    /**商户订单号**/
    @NotEmpty
    private String orderId;
    /**订单发送时间**/
    @NotEmpty
    private String txnTime;
    /**受理订单号**/
    @NotEmpty
    private String tn;
    /**交易金额**/
    @NotEmpty
    private String txnAmt;
    /**交易币种**/
    @NotEmpty
    private String currencyCode;
    /**交易查询流水号**/
    @NotEmpty
    private String queryId;
    /**响应码**/
    @NotEmpty
    private String respCode;
    /**响应信息**/
    @NotEmpty
    private String respMsg;
    /**清算金额**/
    @NotEmpty
    private String settleAmt;
    /**清算币种**/
    @NotEmpty
    private String settleCurrencyCode;
    /**清算日期**/
    @NotEmpty
    private String settleDate;
    /**交易传输时间**/
    @NotEmpty
    private String traceTime;
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
    public String getTn() {
        return tn;
    }
    public void setTn(String tn) {
        this.tn = tn;
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
    public String getQueryId() {
        return queryId;
    }
    public void setQueryId(String queryId) {
        this.queryId = queryId;
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
    public String getSettleAmt() {
        return settleAmt;
    }
    public void setSettleAmt(String settleAmt) {
        this.settleAmt = settleAmt;
    }
    public String getSettleCurrencyCode() {
        return settleCurrencyCode;
    }
    public void setSettleCurrencyCode(String settleCurrencyCode) {
        this.settleCurrencyCode = settleCurrencyCode;
    }
    public String getSettleDate() {
        return settleDate;
    }
    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }
    public String getTraceTime() {
        return traceTime;
    }
    public void setTraceTime(String traceTime) {
        this.traceTime = traceTime;
    }
}
