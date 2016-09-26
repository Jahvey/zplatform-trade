/* 
 * RealnameAuth_Response.java  
 * 
 * version v1.0
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
public class MerWhiteList_Response extends BaseMessage{

    /**响应码**/
    private String respCode;
    /**应答信息**/
    private String respMsg;

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
