/* 
 * OfflineDepositNotifyBean.java  
 * 
 * version TODO
 *
 * 2015年8月21日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean.zlpay;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年8月21日 下午1:21:27
 * @since 
 */
public class OfflineDepositNotifyBean {
    
    private String instuId;
    private String merchantDate;
    private String merchantTime;
    private String merchantSeqId;
    private String userId;
    private String userNameText;
    private String certType;
    private String certId;
    private String bankCode;
    private String receibankCode;
    private String cardName;
    private String cardNo;
    private String transAmt;
    private String resv;
    /**
     * @param instuId
     * @param merchantDate
     * @param merchantTime
     * @param merchantSeqId
     * @param userId
     * @param userNameText
     * @param certType
     * @param certId
     * @param bankCode
     * @param receibankCode
     * @param cardName
     * @param cardNo
     * @param transAmt
     * @param resv
     */
    public OfflineDepositNotifyBean(String instuId, String merchantDate,
            String merchantTime, String merchantSeqId, String userId,
            String userNameText, String certType, String certId,
            String bankCode, String receibankCode, String cardName,
            String cardNo, String transAmt, String resv) {
        super();
        this.instuId = instuId;
        this.merchantDate = merchantDate;
        this.merchantTime = merchantTime;
        this.merchantSeqId = merchantSeqId;
        this.userId = userId;
        this.userNameText = userNameText;
        this.certType = certType;
        this.certId = certId;
        this.bankCode = bankCode;
        this.receibankCode = receibankCode;
        this.cardName = cardName;
        this.cardNo = cardNo;
        this.transAmt = transAmt;
        this.resv = resv;
    }
    /**
     * @return the instuId
     */
    public String getInstuId() {
        return instuId;
    }
    /**
     * @param instuId the instuId to set
     */
    public void setInstuId(String instuId) {
        this.instuId = instuId;
    }
    /**
     * @return the merchantDate
     */
    public String getMerchantDate() {
        return merchantDate;
    }
    /**
     * @param merchantDate the merchantDate to set
     */
    public void setMerchantDate(String merchantDate) {
        this.merchantDate = merchantDate;
    }
    /**
     * @return the merchantTime
     */
    public String getMerchantTime() {
        return merchantTime;
    }
    /**
     * @param merchantTime the merchantTime to set
     */
    public void setMerchantTime(String merchantTime) {
        this.merchantTime = merchantTime;
    }
    /**
     * @return the merchantSeqId
     */
    public String getMerchantSeqId() {
        return merchantSeqId;
    }
    /**
     * @param merchantSeqId the merchantSeqId to set
     */
    public void setMerchantSeqId(String merchantSeqId) {
        this.merchantSeqId = merchantSeqId;
    }
    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    /**
     * @return the userNameText
     */
    public String getUserNameText() {
        return userNameText;
    }
    /**
     * @param userNameText the userNameText to set
     */
    public void setUserNameText(String userNameText) {
        this.userNameText = userNameText;
    }
    /**
     * @return the certType
     */
    public String getCertType() {
        return certType;
    }
    /**
     * @param certType the certType to set
     */
    public void setCertType(String certType) {
        this.certType = certType;
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
     * @return the bankCode
     */
    public String getBankCode() {
        return bankCode;
    }
    /**
     * @param bankCode the bankCode to set
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
    /**
     * @return the receibankCode
     */
    public String getReceibankCode() {
        return receibankCode;
    }
    /**
     * @param receibankCode the receibankCode to set
     */
    public void setReceibankCode(String receibankCode) {
        this.receibankCode = receibankCode;
    }
    /**
     * @return the cardName
     */
    public String getCardName() {
        return cardName;
    }
    /**
     * @param cardName the cardName to set
     */
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
    /**
     * @return the cardNo
     */
    public String getCardNo() {
        return cardNo;
    }
    /**
     * @param cardNo the cardNo to set
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
    /**
     * @return the transAmt
     */
    public String getTransAmt() {
        return transAmt;
    }
    /**
     * @param transAmt the transAmt to set
     */
    public void setTransAmt(String transAmt) {
        this.transAmt = transAmt;
    }
    /**
     * @return the resv
     */
    public String getResv() {
        return resv;
    }
    /**
     * @param resv the resv to set
     */
    public void setResv(String resv) {
        this.resv = resv;
    }
    
    

}
