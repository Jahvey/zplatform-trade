/* 
 * OnlineDepositShortBean.java  
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
 * 快捷支付
 *
 * @author guojia
 * @version
 * @date 2015年8月21日 下午1:16:28
 * @since 
 */
public class OnlineDepositShortBean {
    private String instuId;
    private String merchantDate;
    private String merchantTime;
    private String merchantSeqId;
    private String userId;
    private String userNameText;
    private String certType;
    private String certId;
    private String bankCode;
    private String cardNo;
    private String transAmt;
    private String accountPsw;
    private String pgRecvUrl;
    private String bgRecvUrl;
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
     * @param cardNo
     * @param transAmt
     * @param accountPsw
     * @param pgRecvUrl
     * @param bgRecvUrl
     * @param resv
     */
    public OnlineDepositShortBean(String instuId, String merchantDate,
            String merchantTime, String merchantSeqId, String userId,
            String userNameText, String certType, String certId,
            String bankCode, String cardNo, String transAmt, String accountPsw,
            String pgRecvUrl, String bgRecvUrl, String resv) {
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
        this.cardNo = cardNo;
        this.transAmt = transAmt;
        this.accountPsw = accountPsw;
        this.pgRecvUrl = pgRecvUrl;
        this.bgRecvUrl = bgRecvUrl;
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
     * @return the accountPsw
     */
    public String getAccountPsw() {
        return accountPsw;
    }
    /**
     * @param accountPsw the accountPsw to set
     */
    public void setAccountPsw(String accountPsw) {
        this.accountPsw = accountPsw;
    }
    /**
     * @return the pgRecvUrl
     */
    public String getPgRecvUrl() {
        return pgRecvUrl;
    }
    /**
     * @param pgRecvUrl the pgRecvUrl to set
     */
    public void setPgRecvUrl(String pgRecvUrl) {
        this.pgRecvUrl = pgRecvUrl;
    }
    /**
     * @return the bgRecvUrl
     */
    public String getBgRecvUrl() {
        return bgRecvUrl;
    }
    /**
     * @param bgRecvUrl the bgRecvUrl to set
     */
    public void setBgRecvUrl(String bgRecvUrl) {
        this.bgRecvUrl = bgRecvUrl;
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
    
    public String toSendMsg(){
        StringBuffer data = new StringBuffer();
        data.append("instuId=");
        data.append(instuId);
        data.append("|");
        
        data.append("merchantDate=");
        data.append(merchantDate);
        data.append("|");
        
        data.append("merchantTime=");
        data.append(merchantTime);
        data.append("|");
        
        data.append("merchantSeqId=");
        data.append(merchantSeqId);
        data.append("|");
        
        data.append("userId=");
        data.append(userId);
        data.append("|");
        
        data.append("userNameText=");
        data.append(userNameText);
        data.append("|");
        
        data.append("certType=");
        data.append(certType);
        data.append("|");
        
        data.append("certId=");
        data.append(certId);
        data.append("|");
        
        data.append("bankCode=");
        data.append(bankCode);
        data.append("|");
        
        data.append("cardNo=");
        data.append(cardNo);
        data.append("|");
        
        data.append("transAmt=");
        data.append(transAmt);
        data.append("|");
        
        data.append("accountPsw=");
        data.append(accountPsw);
        data.append("|");
        
        data.append("pgRecvUrl=");
        data.append(pgRecvUrl);
        data.append("|");
        
        data.append("bgRecvUrl=");
        data.append(bgRecvUrl);
        data.append("|");
        
        data.append("resv=");
        data.append(resv);
        data.append("|");
        
        return data.toString();
    }
    
}
