/* 
 * WithdrawNotifyBean.java  
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
 * @date 2015年8月21日 下午1:26:06
 * @since 
 */
public class WithdrawNotifyBean {
    String instuId;
    String merchantDate;
    String merchantTime;
    String merchantSeqId;
    String orderDate;
    String userId;
    String userNameText;
    String bankCode;
    String cardName;
    String cardNo;
    String transAmt;
    String resv;
    /**
     * @param instuId
     * @param merchantDate
     * @param merchantTime
     * @param merchantSeqId
     * @param orderDate
     * @param userId
     * @param userNameText
     * @param bankCode
     * @param cardName
     * @param cardNo
     * @param transAmt
     * @param resv
     */
    public WithdrawNotifyBean(String instuId, String merchantDate,
            String merchantTime, String merchantSeqId, String orderDate,
            String userId, String userNameText, String bankCode,
            String cardName, String cardNo, String transAmt, String resv) {
        super();
        this.instuId = instuId;
        this.merchantDate = merchantDate;
        this.merchantTime = merchantTime;
        this.merchantSeqId = merchantSeqId;
        this.orderDate = orderDate;
        this.userId = userId;
        this.userNameText = userNameText;
        this.bankCode = bankCode;
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
     * @return the orderDate
     */
    public String getOrderDate() {
        return orderDate;
    }
    /**
     * @param orderDate the orderDate to set
     */
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
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
        
        data.append("orderDate=");
        data.append(orderDate);
        data.append("|");
        
        data.append("userId=");
        data.append(userId);
        data.append("|");
        
        data.append("userNameText=");
        data.append(userNameText);
        data.append("|");
        
        data.append("bankCode=");
        data.append(bankCode);
        data.append("|");
        
        data.append("cardName=");
        data.append(cardName);
        data.append("|");
        
        data.append("cardNo=");
        data.append(cardNo);
        data.append("|");
        
        data.append("transAmt=");
        data.append(transAmt);
        data.append("|");
        
        data.append("resv=");
        data.append(resv);
        data.append("|");
        
        
        
        return null;
    }
}
