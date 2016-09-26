/* 
 * CreateOrder_Response_Sync.java  
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
 * 支付定单生成【应答报文】【同步】
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年10月8日 下午7:44:24
 * @since 
 */
public class CreateOrder_Response_Sync  extends BaseMessage {
    /**一级商户代码**/
    @NotEmpty
    private String merId;
    /**商户订单号**/
    @NotEmpty
    private String orderId;
    /**订单发送时间**/
    @NotEmpty
    private String txnTime;
    /**请求方保留域**/
    private String reqReserved;
    /**响应码**/
    private String respCode;
    /**应答信息**/
    @NotEmpty
    private String respMsg;
    /**受理订单号**/
    @NotEmpty
    private String tn;
    public String getMerId() {
        return merId;
    }
    public void setMerId(String merId) {
        this.merId = merId;
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
    public String getReqReserved() {
        return reqReserved;
    }
    public void setReqReserved(String reqReserved) {
        this.reqReserved = reqReserved;
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
    public String getTn() {
        return tn;
    }
    public void setTn(String tn) {
        this.tn = tn;
    }
}
