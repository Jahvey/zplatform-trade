/* 
 * QueryMemberBalance_Response.java  
 * 
 * version TODO
 *
 * 2015年10月12日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.message;

/**
 * 余额查询【应答报文】
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年10月12日 下午2:09:47
 * @since 
 */
public class QueryMemberBalance_Response  extends BaseMessage{
    /**余额**/
    private String balance;
    /**响应码**/
    private String respCode;
    /**应答信息**/
    private String respMsg;
    public String getBalance() {
        return balance;
    }
    public void setBalance(String balance) {
        this.balance = balance;
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
