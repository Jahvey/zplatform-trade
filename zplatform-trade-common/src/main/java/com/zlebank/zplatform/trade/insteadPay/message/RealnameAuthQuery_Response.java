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
 * 实名认证查询【应答报文】
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月24日 下午4:47:26
 * @since 
 */
public class RealnameAuthQuery_Response extends BaseMessage{

    /**商户订单号**/
    private String orderId;
    /**订单发送时间**/
    private String txnTime;
    /**认证状态 00-认证成功 99-认证失败**/
    private String validateStatus;
    /**原交易应答码**/
    private String origRespCode;
    /**原交易应答信息**/
    private String origRespMsg;
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
    public String getValidateStatus() {
        return validateStatus;
    }
    public void setValidateStatus(String validateStatus) {
        this.validateStatus = validateStatus;
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
