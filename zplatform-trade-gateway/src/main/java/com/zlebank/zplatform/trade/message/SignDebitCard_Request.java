/* 
 * SignCardRequest.java  
 * 
 * version v1.0
 *
 * 2015年10月9日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.message;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 信用卡签约【请求报文】
 *
 * @author yangpeng
 * @version
 * @date 2015年10月9日 下午2:41:24
 * @since 
 */
public class SignDebitCard_Request  extends BaseMessage {

    /**会员ID **/
    private String memberId;
    /**渠道类型**/
    private String channelType;
    /**接入类型**/
    private String accessType;
    /**储蓄卡签约信息（加密）**/
    private String encryptData;
    /**受理订单号**/
    @NotEmpty
    private String tn;

    public String getMemberId() {
        return memberId;
    }
    public String getChannelType() {
        return channelType;
    }
    public String getAccessType() {
        return accessType;
    }
 
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }
    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }
 
    public String getTn() {
        return tn;
    }
    public void setTn(String tn) {
        this.tn = tn;
    }
    public String getEncryptData() {
        return encryptData;
    }
    public void setEncryptData(String encryptData) {
        this.encryptData = encryptData;
    }
    
}
