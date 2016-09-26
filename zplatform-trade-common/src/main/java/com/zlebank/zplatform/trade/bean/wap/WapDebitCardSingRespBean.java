/* 
 * WapDebitCardSingRespBean.java  
 * 
 * version TODO
 *
 * 2015年10月12日 
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
 * @date 2015年10月12日 下午6:01:34
 * @since 
 */
public class WapDebitCardSingRespBean implements Serializable{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6520377795003525527L;

    private String version;
    private String encoding;
    private String signature;
    private String signMethod; 
    private String virtualId;
    private String memberId;
    private String txnType;
    private String txnSubType; 
    private String bizType;
    private String bindId;
    private String respCode;
    private String respMsg;
    private String tn;
    
    
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
    /**
     * @return the respCode
     */
    public String getRespCode() {
        return respCode;
    }
    /**
     * @param respCode the respCode to set
     */
    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }
    /**
     * @return the respMsg
     */
    public String getRespMsg() {
        return respMsg;
    }
    /**
     * @param respMsg the respMsg to set
     */
    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
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
     * @param version
     * @param encoding
     * @param signature
     * @param signMethod
     * @param virtualId
     * @param memberId
     * @param txnType
     * @param txnSubType
     * @param bizType
     * @param bindId
     * @param respCode
     * @param respMsg
     * @param tn
     */
    public WapDebitCardSingRespBean(String version, String encoding,
            String signature, String signMethod, String virtualId,
            String memberId, String txnType, String txnSubType, String bizType,
            String bindId, String respCode, String respMsg, String tn) {
        super();
        this.version = version;
        this.encoding = encoding;
        this.signature = signature;
        this.signMethod = signMethod;
        this.virtualId = virtualId;
        this.memberId = memberId;
        this.txnType = txnType;
        this.txnSubType = txnSubType;
        this.bizType = bizType;
        this.bindId = bindId;
        this.respCode = respCode;
        this.respMsg = respMsg;
        this.tn = tn;
    }
    
    
}
