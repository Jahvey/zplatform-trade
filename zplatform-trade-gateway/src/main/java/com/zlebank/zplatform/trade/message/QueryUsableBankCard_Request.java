/* 
 * UseBank.java  
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
 * 可受理银行卡信息【请求报文】
 *
 * @author yangpeng
 * @version
 * @date 2015年10月9日 下午2:04:45
 * @since 
 */
public class QueryUsableBankCard_Request  extends BaseMessage {

    /**渠道类型**/
    private String channelType;
    /**接入类型**/
    private String accessType;

    public String getChannelType() {
        return channelType;
    }
    public String getAccessType() {
        return accessType;
    }
    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }
    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }
}
