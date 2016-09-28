/* 
 * OrderBean.java  
 * 
 * version TODO
 *
 * 2015年8月27日 
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
 * @date 2015年8月27日 上午10:41:06
 * @since 
 */
public class OrderBean implements Serializable{
    private static final long serialVersionUID = -7317360443683683832L;
    @NotEmpty(message="param.empty.version")
    @Length(max=10,message="param.error.version")
    private String version="";
    @NotEmpty(message="param.empty.encoding")
    @Length(max=2,message="param.error.encoding")
    private String encoding="";
    @NotEmpty(message="param.empty.certId")
    @Length(max=12,message="param.error.certId")
    private String certId="";
    @Length(max=256,message="param.error.frontUrl")
    private String frontUrl="";
    @Length(max=256,message="param.error.backUrl")
    private String backUrl="";
    @NotEmpty(message="param.empty.signature")
    private String signature="";
    @NotEmpty(message="param.empty.signMethod")
    @Length(max=2,message="param.error.signMethod")
    private String signMethod="";
    @NotEmpty(message="param.empty.coopInstiId")
    @Length(max=15,message="param.error.coopInstiId")
    private String coopInstiId="";
    @Length(max=15,message="param.error.merId")
    private String merId="";
    @Length(max=40,message="param.error.merName")
    private String merName="";
    @Length(max=40,message="param.error.merAbbr")
    private String merAbbr="";
    @NotEmpty(message="param.empty.orderId")
    @Length(max=32,message="param.error.orderId")
    private String orderId="";
    @NotEmpty(message="param.empty.txnType")
    @Length(max=2,message="param.error.txnType")
    private String txnType="";
    @NotEmpty(message="param.empty.txnSubType")
    @Length(max=2,message="param.error.txnSubType")
    private String txnSubType="";
    @NotEmpty(message="param.empty.bizType")
    @Length(max=6,message="param.error.bizType")
    private String bizType="";
    
    @Length(max=2,message="param.error.channelType")
    private String channelType="";
    @NotEmpty(message="param.empty.accessType")
    @Length(max=1,message="param.error.accessType")
    private String accessType="";
    @NotEmpty(message="param.empty.txnTime")
    @Length(max=14,message="param.error.txnTime")
    private String txnTime="";
    @Length(max=2,message="param.error.accType")
    private String accType="";
    @Length(max=512,message="param.error.accNo")
    private String accNo="";
    @NotEmpty(message="param.empty.txnAmt")
    @Length(max=12,message="param.error.txnAmt")
    private String txnAmt="";
    @NotEmpty(message="param.empty.currencyCode")
    @Length(max=3,message="param.error.currencyCode")
    private String currencyCode="";
    @Length(max=2048,message="param.error.customerInfo")
    private String customerInfo="";
    @NotEmpty(message="param.empty.orderTimeout")
    @Length(max=10,message="param.error.orderTimeout")
    private String orderTimeout="";
    @NotEmpty(message="param.empty.payTimeout")
    @Length(max=14,message="param.error.payTimeout")
    private String payTimeout="";
    @Length(max=2048,message="param.error.reqReserved")
    private String reqReserved="";
    @Length(max=2048,message="param.error.reserved")
    private String reserved="";
    @NotEmpty(message="param.empty.riskRateInfo")
    @Length(max=2048,message="param.error.riskRateInfo")
    private String riskRateInfo="";
    @Length(max=128,message="param.error.encryptCertId")
    private String encryptCertId="";
    @Length(max=256,message="param.error.frontFailUrl")
    private String frontFailUrl="";
    @Length(max=128,message="param.error.instalTransInfo")
    private String instalTransInfo="";
    @Length(max=4,message="param.error.defaultPayType")
    private String defaultPayType="";
    @Length(max=20,message="param.error.issInsCode")
    private String issInsCode="";
    @Length(max=4,message="param.error.supPayType")
    private String supPayType="";
    @Length(max=1024,message="param.error.userMac")
    private String userMac="";
    @Length(max=40,message="param.error.customerIp")
    private String customerIp="";
    @Length(max=1024,message="param.error.cardTransData")
    private String cardTransData="";
    @Length(max=32,message="param.error.orderDesc")
    private String orderDesc="";
    
    /**产品代码**/
    private String productcode;
    
    /**行业代码**/
    private String industryCode;
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
     * @return the frontUrl
     */
    public String getFrontUrl() {
        return frontUrl;
    }
    /**
     * @param frontUrl the frontUrl to set
     */
    public void setFrontUrl(String frontUrl) {
        this.frontUrl = frontUrl;
    }
    /**
     * @return the backUrl
     */
    public String getBackUrl() {
        return backUrl;
    }
    /**
     * @param backUrl the backUrl to set
     */
    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
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
     * @return the channelType
     */
    public String getChannelType() {
        return channelType;
    }
    /**
     * @param channelType the channelType to set
     */
    public void setChannelType(String channelType) {
        this.channelType = channelType;
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
     * @return the accType
     */
    public String getAccType() {
        return accType;
    }
    /**
     * @param accType the accType to set
     */
    public void setAccType(String accType) {
        this.accType = accType;
    }
    /**
     * @return the accNo
     */
    public String getAccNo() {
        return accNo;
    }
    /**
     * @param accNo the accNo to set
     */
    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }
    /**
     * @return the txnAmt
     */
    public String getTxnAmt() {
        return txnAmt;
    }
    /**
     * @param txnAmt the txnAmt to set
     */
    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }
    /**
     * @return the currencyCode
     */
    public String getCurrencyCode() {
        return currencyCode;
    }
    /**
     * @param currencyCode the currencyCode to set
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
    /**
     * @return the customerInfo
     */
    public String getCustomerInfo() {
        return customerInfo;
    }
    /**
     * @param customerInfo the customerInfo to set
     */
    public void setCustomerInfo(String customerInfo) {
        this.customerInfo = customerInfo;
    }
    /**
     * @return the orderTimeout
     */
    public String getOrderTimeout() {
        return orderTimeout;
    }
    /**
     * @param orderTimeout the orderTimeout to set
     */
    public void setOrderTimeout(String orderTimeout) {
        this.orderTimeout = orderTimeout;
    }
    /**
     * @return the payTimeout
     */
    public String getPayTimeout() {
        return payTimeout;
    }
    /**
     * @param payTimeout the payTimeout to set
     */
    public void setPayTimeout(String payTimeout) {
        this.payTimeout = payTimeout;
    }
    /**
     * @return the reqReserved
     */
    public String getReqReserved() {
        return reqReserved;
    }
    /**
     * @param reqReserved the reqReserved to set
     */
    public void setReqReserved(String reqReserved) {
        this.reqReserved = reqReserved;
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
     * @return the riskRateInfo
     */
    public String getRiskRateInfo() {
        return riskRateInfo;
    }
    /**
     * @param riskRateInfo the riskRateInfo to set
     */
    public void setRiskRateInfo(String riskRateInfo) {
        this.riskRateInfo = riskRateInfo;
    }
    /**
     * @return the encryptCertId
     */
    public String getEncryptCertId() {
        return encryptCertId;
    }
    /**
     * @param encryptCertId the encryptCertId to set
     */
    public void setEncryptCertId(String encryptCertId) {
        this.encryptCertId = encryptCertId;
    }
    /**
     * @return the frontFailUrl
     */
    public String getFrontFailUrl() {
        return frontFailUrl;
    }
    /**
     * @param frontFailUrl the frontFailUrl to set
     */
    public void setFrontFailUrl(String frontFailUrl) {
        this.frontFailUrl = frontFailUrl;
    }
    /**
     * @return the instalTransInfo
     */
    public String getInstalTransInfo() {
        return instalTransInfo;
    }
    /**
     * @param instalTransInfo the instalTransInfo to set
     */
    public void setInstalTransInfo(String instalTransInfo) {
        this.instalTransInfo = instalTransInfo;
    }
    /**
     * @return the defaultPayType
     */
    public String getDefaultPayType() {
        return defaultPayType;
    }
    /**
     * @param defaultPayType the defaultPayType to set
     */
    public void setDefaultPayType(String defaultPayType) {
        this.defaultPayType = defaultPayType;
    }
    /**
     * @return the issInsCode
     */
    public String getIssInsCode() {
        return issInsCode;
    }
    /**
     * @param issInsCode the issInsCode to set
     */
    public void setIssInsCode(String issInsCode) {
        this.issInsCode = issInsCode;
    }
    /**
     * @return the supPayType
     */
    public String getSupPayType() {
        return supPayType;
    }
    /**
     * @param supPayType the supPayType to set
     */
    public void setSupPayType(String supPayType) {
        this.supPayType = supPayType;
    }
    /**
     * @return the userMac
     */
    public String getUserMac() {
        return userMac;
    }
    /**
     * @param userMac the userMac to set
     */
    public void setUserMac(String userMac) {
        this.userMac = userMac;
    }
    /**
     * @return the customerIp
     */
    public String getCustomerIp() {
        return customerIp;
    }
    /**
     * @param customerIp the customerIp to set
     */
    public void setCustomerIp(String customerIp) {
        this.customerIp = customerIp;
    }
    /**
     * @return the cardTransData
     */
    public String getCardTransData() {
        return cardTransData;
    }
    /**
     * @param cardTransData the cardTransData to set
     */
    public void setCardTransData(String cardTransData) {
        this.cardTransData = cardTransData;
    }
    /**
     * @return the orderDesc
     */
    public String getOrderDesc() {
        return orderDesc;
    }
    /**
     * @param orderDesc the orderDesc to set
     */
    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
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
	/**
	 * @return the merName
	 */
	public String getMerName() {
		return merName;
	}
	/**
	 * @param merName the merName to set
	 */
	public void setMerName(String merName) {
		this.merName = merName;
	}
	/**
	 * @return the merAbbr
	 */
	public String getMerAbbr() {
		return merAbbr;
	}
	/**
	 * @param merAbbr the merAbbr to set
	 */
	public void setMerAbbr(String merAbbr) {
		this.merAbbr = merAbbr;
	}
	
	/**
	 * @return the productcode
	 */
	public String getProductcode() {
		return productcode;
	}
	/**
	 * @param productcode the productcode to set
	 */
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	
	
	/**
	 * @return the industryCode
	 */
	public String getIndustryCode() {
		return industryCode;
	}
	/**
	 * @param industryCode the industryCode to set
	 */
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}
	/**
	 * @param version
	 * @param encoding
	 * @param certId
	 * @param frontUrl
	 * @param backUrl
	 * @param signature
	 * @param signMethod
	 * @param coopInstiId
	 * @param merId
	 * @param merName
	 * @param merAbbr
	 * @param orderId
	 * @param txnType
	 * @param txnSubType
	 * @param bizType
	 * @param channelType
	 * @param accessType
	 * @param txnTime
	 * @param accType
	 * @param accNo
	 * @param txnAmt
	 * @param currencyCode
	 * @param customerInfo
	 * @param orderTimeout
	 * @param payTimeout
	 * @param reqReserved
	 * @param reserved
	 * @param riskRateInfo
	 * @param encryptCertId
	 * @param frontFailUrl
	 * @param instalTransInfo
	 * @param defaultPayType
	 * @param issInsCode
	 * @param supPayType
	 * @param userMac
	 * @param customerIp
	 * @param cardTransData
	 * @param orderDesc
	 */
	public OrderBean(String version, String encoding, String certId,
			String frontUrl, String backUrl, String signature,
			String signMethod, String coopInstiId, String merId,
			String merName, String merAbbr, String orderId, String txnType,
			String txnSubType, String bizType, String channelType,
			String accessType, String txnTime, String accType, String accNo,
			String txnAmt, String currencyCode, String customerInfo,
			String orderTimeout, String payTimeout, String reqReserved,
			String reserved, String riskRateInfo, String encryptCertId,
			String frontFailUrl, String instalTransInfo, String defaultPayType,
			String issInsCode, String supPayType, String userMac,
			String customerIp, String cardTransData, String orderDesc) {
		super();
		this.version = version;
		this.encoding = encoding;
		this.certId = certId;
		this.frontUrl = frontUrl;
		this.backUrl = backUrl;
		this.signature = signature;
		this.signMethod = signMethod;
		this.coopInstiId = coopInstiId;
		this.merId = merId;
		this.merName = merName;
		this.merAbbr = merAbbr;
		this.orderId = orderId;
		this.txnType = txnType;
		this.txnSubType = txnSubType;
		this.bizType = bizType;
		this.channelType = channelType;
		this.accessType = accessType;
		this.txnTime = txnTime;
		this.accType = accType;
		this.accNo = accNo;
		this.txnAmt = txnAmt;
		this.currencyCode = currencyCode;
		this.customerInfo = customerInfo;
		this.orderTimeout = orderTimeout;
		this.payTimeout = payTimeout;
		this.reqReserved = reqReserved;
		this.reserved = reserved;
		this.riskRateInfo = riskRateInfo;
		this.encryptCertId = encryptCertId;
		this.frontFailUrl = frontFailUrl;
		this.instalTransInfo = instalTransInfo;
		this.defaultPayType = defaultPayType;
		this.issInsCode = issInsCode;
		this.supPayType = supPayType;
		this.userMac = userMac;
		this.customerIp = customerIp;
		this.cardTransData = cardTransData;
		this.orderDesc = orderDesc;
	}
	/**
	 * 
	 */
	public OrderBean() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
    
}
