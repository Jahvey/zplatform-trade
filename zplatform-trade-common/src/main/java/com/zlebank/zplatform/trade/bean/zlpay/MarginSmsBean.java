/* 
 * MarginSmsBean.java  
 * 
 * version TODO
 *
 * 2015年8月21日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean.zlpay;

import java.io.Serializable;

import com.zlebank.zplatform.trade.bean.TradeBean;


/**
 * Class Description
 * 短信验证码实体类
 * @author guojia
 * @version
 * @date 2015年8月21日 下午12:25:13
 * @since 
 */
public class MarginSmsBean implements Serializable{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    private String instuId;
    private String merchantDate;
    private String merchantTime;
    private String merchantSeqId;
    private String tradeType;
    private String userId;
    private String userNameText;
    private String mobile;
    private String certType;
    private String certId;
    private String bankCode;
    private String cardNo;
    private String amt;
    private String resv;
    /**
     * @param instuId
     * @param merchantDate
     * @param merchantTime
     * @param merchantSeqId
     * @param tradeType
     * @param userId
     * @param userNameText
     * @param mobile
     * @param certType
     * @param certId
     * @param bankCode
     * @param cardNo
     * @param amt
     * @param resv
     */
    public MarginSmsBean(String instuId, String merchantDate,
            String merchantTime, String merchantSeqId, String tradeType,
            String userId, String userNameText, String mobile, String certType,
            String certId, String bankCode, String cardNo, String amt,
            String resv) {
        super();
        this.instuId = instuId;
        this.merchantDate = merchantDate;
        this.merchantTime = merchantTime;
        this.merchantSeqId = merchantSeqId;
        this.tradeType = tradeType;
        this.userId = userId;
        this.userNameText = userNameText;
        this.mobile = mobile;
        this.certType = certType;
        this.certId = certId;
        this.bankCode = bankCode;
        this.cardNo = cardNo;
        this.amt = amt;
        this.resv = resv;
    }
    
    
    public MarginSmsBean(TradeBean trade){
        
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
     * @return the tradeType
     */
    public String getTradeType() {
        return tradeType;
    }
    /**
     * @param tradeType the tradeType to set
     */
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
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
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }
    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
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
     * @return the amt
     */
    public String getAmt() {
        return amt;
    }
    /**
     * @param amt the amt to set
     */
    public void setAmt(String amt) {
        this.amt = amt;
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
        
        data.append("tradeType=");
        data.append(tradeType);
        data.append("|");
        
        data.append("tradeType=");
        data.append(tradeType);
        data.append("|");
        
        data.append("userId=");
        data.append(userId);
        data.append("|");
        
        data.append("userNameText=");
        data.append(userNameText);
        data.append("|");
        
        data.append("mobile=");
        data.append(mobile);
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
        
        data.append("amt=");
        data.append(amt);
        data.append("|");
        
        data.append("resv=");
        data.append(resv);
        data.append("|");
         
        return data.toString();
    }
    
}
