/* 
 * Order.java  
 * 
 * version v1.0
 *
 * 2015年10月8日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.message;

import com.zlebank.zplatform.trade.validator.An;
import com.zlebank.zplatform.trade.validator.N;

/**
 * 订单信息查询【应答报文】
 *
 * @author yangpeng
 * @version
 * @date 2015年10月8日 下午4:42:13
 * @since 
 */

public class QueryOrderInfo_Response extends BaseMessage {

    /**二级商户代码**/
    private String subMerId;
    /**二级商户全称**/
    private String subMerName;
    /**二级商户简称**/
    private String subMerAbbr;
    /**商户订单号**/
    private String orderId;
    /**订单发送时间**/
    private String txnTime ;
    /**交易金额**/
    private String txnAmt ;
    /**交易币种**/
    private String currencyCode ;
    /**响应码**/
    private String respCode;
    /**应答信息**/
    private String respMsg;
    /**订单描述**/
    private String orderDesc ;
    /**订单对应的个人会员号，如果是匿名支付则为999999999999999**/
    private String payMemberId ;
    /**是否已经设置支付密码**/
    private String isSetPayPwd ;
    /**订单状态**/
    private String orderStatus;

    @An(max=15,isNull=false)
    public String getSubMerId() {
        return subMerId;
    }
    @An(max=15,isNull=false)
    public String getSubMerName() {
        return subMerName;
    }
    @An(max=15,isNull=false)
    public String getSubMerAbbr() {
        return subMerAbbr;
    }
    @N(max=16,isNull=false)
    public String getOrderId() {
        return orderId;
    }
    @N(max=16,isNull=false)
    public String getTxnTime() {
        return txnTime;
    }
    @An
    public String getTxnAmt() {
        return txnAmt;
    }
    @N
    public String getCurrencyCode() {
        return currencyCode;
    }
    @An
    public String getRespCode() {
        return respCode;
    }
    @An
    public String getRespMsg() {
        return respMsg;
    }
    @N
    public String getOrderDesc() {
        return orderDesc;
    }
    public void setSubMerId(String subMerId) {
        this.subMerId = subMerId;
    }
    public void setSubMerName(String subMerName) {
        this.subMerName = subMerName;
    }
    public void setSubMerAbbr(String subMerAbbr) {
        this.subMerAbbr = subMerAbbr;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public void setTxnTime(String txnTime) {
        this.txnTime = txnTime;
    }
    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }
    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }
    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }
    public String getPayMemberId() {
        return payMemberId;
    }
    public void setPayMemberId(String payMemberId) {
        this.payMemberId = payMemberId;
    }
    public String getIsSetPayPwd() {
        return isSetPayPwd;
    }
    public void setIsSetPayPwd(String isSetPayPwd) {
        this.isSetPayPwd = isSetPayPwd;
    }
    public String getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    
}
