/* 
 * QuerySignRequest.java  
 * 
 * version TODO
 *
 * 2015年10月9日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.message;

/**
 * 用户签约信息查询【请求报文】
 *
 * @author yangpeng
 * @version
 * @date 2015年10月9日 下午12:53:12
 * @since 
 */
public class QuerySignInfo_Request  extends BaseMessage {

    /**会员ID **/
    private String memberId;
    /**渠道类型**/
    private String channelType;
    /**接入类型**/
    private String accessType;
    /**卡类型**/
    private String cardType;
 
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
    public String getCardType() {
        return cardType;
    }
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}
