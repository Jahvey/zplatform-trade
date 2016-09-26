/* 
 * BaseMessage.java  
 * 
 * version v1.0
 *
 * 2015年10月8日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.insteadPay.message;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import com.zlebank.zplatform.commons.validator.Scontant;
import com.zlebank.zplatform.trade.common.validator.N;
import com.zlebank.zplatform.trade.common.validator.S;

/**
 * 响应基类
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年10月8日 下午2:48:16
 * @since 
 */
public class BaseMessage implements Serializable{
    /**版本**/
    @S(max=6)
    protected String version;
    /**编码方式**/
    @N(max=2)
    protected String encoding;
    /**签名**/
    @Length(max=1024)
    protected String signature;
    /**签名方法**/
    @Length(max=2)
    protected String signMethod ;
    /**合作机构代码**/
    protected String coopInstiId;
    /**商户代码**/
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
    @Scontant(contants={"00"})
    private String channelType;

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
    public String getCoopInstiId() {
        return coopInstiId;
    }
    public void setCoopInstiId(String coopInstiId) {
        this.coopInstiId = coopInstiId;
    }
    public String getChannelType() {
        return channelType;
    }
    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }
    
}
