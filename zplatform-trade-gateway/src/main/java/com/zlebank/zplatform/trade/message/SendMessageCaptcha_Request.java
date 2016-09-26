/* 
 * VerifyCodeRequest.java  
 * 
 * version v1.0
 *
 * 2015年10月9日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.message;

/**
 * 发送短信验证码【请求报文】
 *
 * @author yangpeng
 * @version
 * @date 2015年10月9日 下午3:02:30
 * @since 
 */
public class SendMessageCaptcha_Request  extends BaseMessage {
    /**渠道类型**/
    private String channelType;
    /**接入类型**/
    private String accessType;
    /**受理订单号**/
    private String tn;
    /**绑定标识号**/
    private String bindId;

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
    public String getTn() {
        return tn;
    }
    public void setTn(String tn) {
        this.tn = tn;
    }
    public String getBindId() {
        return bindId;
    }
    public void setBindId(String bindId) {
        this.bindId = bindId;
    }

}
