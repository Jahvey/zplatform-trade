/* 
 * BaseMessage.java  
 * 
 * version TODO
 *
 * 2015年10月8日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.message;

import org.hibernate.validator.constraints.Length;

import com.zlebank.zplatform.trade.validator.N;

/**
 * 响应基类
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年10月8日 下午2:48:16
 * @since 
 */
public class BaseMessage {
    /**虚拟代码**/
    @N(max=15,isNull=false)
    private String virtualId;
    /**版本**/
    protected String version;
    /**编码方式**/
    @Length(max=2)
    protected String encoding;
    /**签名**/
    @Length(max=1024)
    protected String signature;
    /**签名方法**/
    @Length(max=2)
    protected String signMethod ;
    /**会员id**/
    protected String merId;
    /**交易类型**/
    @N(max=2,isNull=false)
    private String txnType;
    /**交易子类**/
    @N(max=2,isNull=false)
    private String txnSubType;
    /**产品类型**/
    @N(max=6,isNull=false)
    private String bizType;

    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getEncoding() {
        return encoding;
    }
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
    public String getSignature() {
        return signature;
    }
    public void setSignature(String signature) {
        this.signature = signature;
    }
    public String getSignMethod() {
        return signMethod;
    }
    public void setSignMethod(String signMethod) {
        this.signMethod = signMethod;
    }
    public String getMerId() {
        return merId;
    }
    public void setMerId(String merId) {
        this.merId = merId;
    }
    public String getTxnType() {
        return txnType;
    }
    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }
    public String getTxnSubType() {
        return txnSubType;
    }
    public void setTxnSubType(String txnSubType) {
        this.txnSubType = txnSubType;
    }
    public String getBizType() {
        return bizType;
    }
    public void setBizType(String bizType) {
        this.bizType = bizType;
    }
    public String getVirtualId() {
        return virtualId;
    }
    public void setVirtualId(String virtualId) {
        this.virtualId = virtualId;
    }
}
