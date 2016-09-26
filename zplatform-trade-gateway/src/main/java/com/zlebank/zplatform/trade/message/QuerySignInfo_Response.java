/* 
 * QuerySignResponse.java  
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
 * 用户签约信息查询【应答报文】
 *
 * @author yangpeng
 * @version
 * @date 2015年10月9日 下午1:15:54
 * @since 
 */
public class QuerySignInfo_Response  extends BaseMessage {
    /**买方ID **/
    private String memberId;
    /**交易类型**/
    private String txnType;
    /**交易子类**/
    private String txnSubType ;
    /**产品类型**/
    private String bizType;
    /**保留域**/
    private String reserved;
    /**响应码**/
    private String respCode;
    /**应答信息**/
    private String respMsg;

    public String getMemberId() {
        return memberId;
    }
    public String getTxnType() {
        return txnType;
    }
    public String getTxnSubType() {
        return txnSubType;
    }
    public String getBizType() {
        return bizType;
    }
    public String getReserved() {
        return reserved;
    }
    public String getRespCode() {
        return respCode;
    }
    public String getRespMsg() {
        return respMsg;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }
    public void setTxnSubType(String txnSubType) {
        this.txnSubType = txnSubType;
    }
    public void setBizType(String bizType) {
        this.bizType = bizType;
    }
    public void setReserved(String reserved) {
        this.reserved = reserved;
    }
    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }
    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }
   
    
    
    
    

}
