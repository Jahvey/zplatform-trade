/* 
 * Refund_Response.java  
 * 
 * version TODO
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
 * @date 2015年10月23日 下午7:58:09
 * @since 
 */
public class Refund_Response   extends BaseMessage {
    /**响应码**/
    private String respCode;
    /**应答信息**/
    private String respMsg;
    /**受理订单号**/
    private String tn;
    /**商户订单号**/
    private String orderId;
    /**订单发送时间**/
    private String txnTime;

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
