/* 
 * MemberLoginResponse.java  
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
 * 会员登陆【应答报文】
 *
 * @author yangpeng
 * @version
 * @date 2015年10月9日 下午3:58:38
 * @since 
 */
public class MemberLogin_Response  extends BaseMessage {
    /**凭证**/
    private String accessToken;
    /**响应码**/
    private String  respCode;
    /**应答信息**/
    private String  respMsg;
 
    public String getAccessToken() {
        return accessToken;
    }
    public String getRespCode() {
        return respCode;
    }
    public String getRespMsg() {
        return respMsg;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }
    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }
    
    
}
