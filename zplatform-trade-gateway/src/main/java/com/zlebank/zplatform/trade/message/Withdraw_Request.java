/* 
 * Withdraw_Request.java  
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
 * @date 2015年10月23日 下午7:59:14
 * @since 
 */
public class Withdraw_Request extends BaseMessage {
    /**会员ID**/
    private String memberId;
    /**渠道类型**/
    private String channelType;
    /**接入类型**/
    private String accessType;
    /**提现金额**/
    private String amount;
    /**绑定标识号**/
    private String bindId;
    /**加密信息域**/
    private String encryptData;
    /**商户订单号**/
    private String orderId;
    /**订单发送时间**/
    private String txnTime;
    /**后台通知地址**/
    private String backUrl;

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
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getBindId() {
        return bindId;
    }
    public void setBindId(String bindId) {
        this.bindId = bindId;
    }
    public String getEncryptData() {
        return encryptData;
    }
    public void setEncryptData(String encryptData) {
        this.encryptData = encryptData;
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
    public String getMemberId() {
        return memberId;
    }
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    public String getBackUrl() {
        return backUrl;
    }
    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }
    
}
