/* 
 * WapSubmitPayBean.java  
 * 
 * version TODO
 *
 * 2015年10月13日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean.wap;

import java.io.Serializable;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年10月13日 下午3:36:11
 * @since
 */
public class WapSubmitPayBean implements Serializable {
    
    private String version;// 网关版本
    private String signature;// 签名
    private String signMethod; // 签名方法
    private String virtualId;// 虚拟代码
    private String memberId;// 会员ID
    private String txnType;// 交易类型
    private String txnSubType; // 交易子类
    private String bizType;// 产品类型
    private String channelType; // 渠道类型
    private String accessType; // 接入类型
    private String tn; // /受理订单号
    private String txnAmt; // / 交易金额
    private String currencyCode;// 交易币种
    private String smsCode;// 短信验证码
    private String bindId;// 绑卡标识
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
     * @return the virtualId
     */
    public String getVirtualId() {
        return virtualId;
    }
    /**
     * @param virtualId the virtualId to set
     */
    public void setVirtualId(String virtualId) {
        this.virtualId = virtualId;
    }
    /**
     * @return the memberId
     */
    public String getMemberId() {
        return memberId;
    }
    /**
     * @param memberId the memberId to set
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
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
     * @return the tn
     */
    public String getTn() {
        return tn;
    }
    /**
     * @param tn the tn to set
     */
    public void setTn(String tn) {
        this.tn = tn;
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
     * @return the smsCode
     */
    public String getSmsCode() {
        return smsCode;
    }
    /**
     * @param smsCode the smsCode to set
     */
    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
    /**
     * @return the bindId
     */
    public String getBindId() {
        return bindId;
    }
    /**
     * @param bindId the bindId to set
     */
    public void setBindId(String bindId) {
        this.bindId = bindId;
    }
    
    
}
