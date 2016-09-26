/* 
 * QueryBean.java  
 * 
 * version TODO
 *
 * 2015年9月30日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean.gateway;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月30日 下午4:24:35
 * @since 
 */
public class QueryBean implements Serializable{
    
    
    private static final long serialVersionUID = 7297085099954126985L;
    @NotEmpty(message="param.empty")
    @Length(max=10,message="param.error")
    private String version="";
    @NotEmpty(message="param.empty")
    @Length(max=2,message="param.error")
    private String encoding="";
    @NotEmpty(message="param.empty")
    @Length(max=12,message="param.error")
    private String certId="";
    @NotEmpty(message="param.empty")
    @Length(max=1024,message="param.error")
    private String signature;
    @NotEmpty(message="param.empty")
    @Length(max=2,message="param.error")
    private String signMethod;
    @NotEmpty(message="param.empty")
    @Length(max=2,message="param.error")
    private String txnType="";
    @NotEmpty(message="param.empty")
    @Length(max=2,message="param.error")
    private String txnSubType="";
    @NotEmpty(message="param.empty")
    @Length(max=6,message="param.error")
    private String bizType="";
    @NotEmpty(message="param.empty")
    @Length(max=1,message="param.error")
    private String accessType;
    @NotEmpty(message="param.empty")
    @Length(max=15,message="param.error")
    private String coopInstiId="";
    @NotEmpty(message="param.empty")
    @Length(max=15,message="param.error")
    private String merId="";
    @Length(max=14,message="param.error")
    private String txnTime="";
    @Length(max=32,message="param.error")
    private String orderId="";
    @Length(max=32,message="param.error")
    private String queryId="";
    @Length(max=2048,message="param.error")
    private String reserved="";
    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }
    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }
    /**
     * @return the encoding
     */
    public String getEncoding() {
        return encoding;
    }
    /**
     * @param encoding the encoding to set
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
    /**
     * @return the certId
     */
    public String getCertId() {
        return certId;
    }
    /**
     * @param certId the certId to set
     */
    public void setCertId(String certId) {
        this.certId = certId;
    }
    /**
     * @return the signature
     */
    public String getSignature() {
        return signature;
    }
    /**
     * @param signature the signature to set
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }
    /**
     * @return the signMethod
     */
    public String getSignMethod() {
        return signMethod;
    }
    /**
     * @param signMethod the signMethod to set
     */
    public void setSignMethod(String signMethod) {
        this.signMethod = signMethod;
    }
    /**
     * @return the txnType
     */
    public String getTxnType() {
        return txnType;
    }
    /**
     * @param txnType the txnType to set
     */
    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }
    /**
     * @return the txnSubType
     */
    public String getTxnSubType() {
        return txnSubType;
    }
    /**
     * @param txnSubType the txnSubType to set
     */
    public void setTxnSubType(String txnSubType) {
        this.txnSubType = txnSubType;
    }
    /**
     * @return the bizType
     */
    public String getBizType() {
        return bizType;
    }
    /**
     * @param bizType the bizType to set
     */
    public void setBizType(String bizType) {
        this.bizType = bizType;
    }
    /**
     * @return the accessType
     */
    public String getAccessType() {
        return accessType;
    }
    /**
     * @param accessType the accessType to set
     */
    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }
    /**
     * @return the merId
     */
    public String getMerId() {
        return merId;
    }
    /**
     * @param merId the merId to set
     */
    public void setMerId(String merId) {
        this.merId = merId;
    }
    /**
     * @return the txnTime
     */
    public String getTxnTime() {
        return txnTime;
    }
    /**
     * @param txnTime the txnTime to set
     */
    public void setTxnTime(String txnTime) {
        this.txnTime = txnTime;
    }
    /**
     * @return the orderId
     */
    public String getOrderId() {
        return orderId;
    }
    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    /**
     * @return the queryId
     */
    public String getQueryId() {
        return queryId;
    }
    /**
     * @param queryId the queryId to set
     */
    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }
    /**
     * @return the reserved
     */
    public String getReserved() {
        return reserved;
    }
    /**
     * @param reserved the reserved to set
     */
    public void setReserved(String reserved) {
        this.reserved = reserved;
    }
	/**
	 * @return the coopInstiId
	 */
	public String getCoopInstiId() {
		return coopInstiId;
	}
	/**
	 * @param coopInstiId the coopInstiId to set
	 */
	public void setCoopInstiId(String coopInstiId) {
		this.coopInstiId = coopInstiId;
	}
    
    
}
