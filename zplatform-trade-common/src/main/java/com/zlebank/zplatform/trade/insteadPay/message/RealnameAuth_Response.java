/* 
 * RealnameAuth_Response.java  
 * 
 * version TODO
 *
 * 2015年11月24日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.insteadPay.message;

/**
 * 实名认证【应答报文】
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月24日 下午4:47:26
 * @since 
 */
public class RealnameAuth_Response extends BaseMessage{

    /**商户订单号**/
    private String orderId;
    /**订单发送时间**/
    private String txnTime;
    /**响应码**/
    private String respCode;
    /**应答信息**/
    private String respMsg;

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
