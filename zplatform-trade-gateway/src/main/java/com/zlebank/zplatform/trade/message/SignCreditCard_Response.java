/* 
 * SignCreditResponse.java  
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
 * 储蓄卡签约【应答报文】
 *
 * @author yangpeng
 * @version
 * @date 2015年10月9日 下午2:51:44
 * @since
 */
public class SignCreditCard_Response  extends BaseMessage {
    /** 虚拟代码 **/
    private String virtualId;
    /** 会员ID  **/
    private String memberId;
    /** 交易类型 **/
    private String txnType;
    /** 交易子类 **/
    private String txnSubType;
    /** 产品类型 **/
    private String bizType;
    /** 绑卡ID **/
    private String bindId;
    /** 响应码 **/
    private String respCode;
    /** 应答信息 **/
    private String respMsg;
    
    public String getVirtualId() {
        return virtualId;
    }
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
    public String getBindId() {
        return bindId;
    }
    public String getRespCode() {
        return respCode;
    }
    public String getRespMsg() {
        return respMsg;
    }
    public void setVirtualId(String virtualId) {
        this.virtualId = virtualId;
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
    public void setBindId(String bindId) {
        this.bindId = bindId;
    }
    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }
    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

}
