/* 
 * ZLPayResultVBean.java  
 * 
 * version TODO
 *
 * 2015年8月28日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean;

import java.io.Serializable;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年8月28日 上午11:35:24
 * @since 
 */
public class ZLPayResultBean implements Serializable {

    private String instuId;
    private String merchantDate;
    private String merchantTime;
    private String merchantSeqId;
    private String pnrDate;
    private String pnrTime;
    private String pnrSeqId;
    private String respCode;
    private String respDesc;
    private String resv;
    
    //同步开会特殊字段
    private String fundDate;
    private String fundTime;
    private String fundSeqId;
    private String userId;
    private String counterNo;
    private String userNameText;
    private String certType;
    private String certId;
    private String bankCode;
    private String bankProvinceCode;
    private String bankRegionCode;
    private String cardNo;

    //入金特殊字段
    private String transAmt;

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
     * @return the pnrDate
     */
    public String getPnrDate() {
        return pnrDate;
    }

    /**
     * @param pnrDate the pnrDate to set
     */
    public void setPnrDate(String pnrDate) {
        this.pnrDate = pnrDate;
    }

    /**
     * @return the pnrTime
     */
    public String getPnrTime() {
        return pnrTime;
    }

    /**
     * @param pnrTime the pnrTime to set
     */
    public void setPnrTime(String pnrTime) {
        this.pnrTime = pnrTime;
    }

    /**
     * @return the pnrSeqId
     */
    public String getPnrSeqId() {
        return pnrSeqId;
    }

    /**
     * @param pnrSeqId the pnrSeqId to set
     */
    public void setPnrSeqId(String pnrSeqId) {
        this.pnrSeqId = pnrSeqId;
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
     * @return the respDesc
     */
    public String getRespDesc() {
        return respDesc;
    }

    /**
     * @param respDesc the respDesc to set
     */
    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
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

    /**
     * @return the fundDate
     */
    public String getFundDate() {
        return fundDate;
    }

    /**
     * @param fundDate the fundDate to set
     */
    public void setFundDate(String fundDate) {
        this.fundDate = fundDate;
    }

    /**
     * @return the fundTime
     */
    public String getFundTime() {
        return fundTime;
    }

    /**
     * @param fundTime the fundTime to set
     */
    public void setFundTime(String fundTime) {
        this.fundTime = fundTime;
    }

    /**
     * @return the fundSeqId
     */
    public String getFundSeqId() {
        return fundSeqId;
    }

    /**
     * @param fundSeqId the fundSeqId to set
     */
    public void setFundSeqId(String fundSeqId) {
        this.fundSeqId = fundSeqId;
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
     * @return the counterNo
     */
    public String getCounterNo() {
        return counterNo;
    }

    /**
     * @param counterNo the counterNo to set
     */
    public void setCounterNo(String counterNo) {
        this.counterNo = counterNo;
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
     * @return the bankProvinceCode
     */
    public String getBankProvinceCode() {
        return bankProvinceCode;
    }

    /**
     * @param bankProvinceCode the bankProvinceCode to set
     */
    public void setBankProvinceCode(String bankProvinceCode) {
        this.bankProvinceCode = bankProvinceCode;
    }

    /**
     * @return the bankRegionCode
     */
    public String getBankRegionCode() {
        return bankRegionCode;
    }

    /**
     * @param bankRegionCode the bankRegionCode to set
     */
    public void setBankRegionCode(String bankRegionCode) {
        this.bankRegionCode = bankRegionCode;
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
     * @param instuId
     * @param merchantDate
     * @param merchantTime
     * @param merchantSeqId
     * @param pnrDate
     * @param pnrTime
     * @param pnrSeqId
     * @param respCode
     * @param respDesc
     * @param resv
     * @param fundDate
     * @param fundTime
     * @param fundSeqId
     * @param userId
     * @param counterNo
     * @param userNameText
     * @param certType
     * @param certId
     * @param bankCode
     * @param bankProvinceCode
     * @param bankRegionCode
     * @param cardNo
     * @param transAmt
     */
    public ZLPayResultBean(String instuId, String merchantDate,
            String merchantTime, String merchantSeqId, String pnrDate,
            String pnrTime, String pnrSeqId, String respCode, String respDesc,
            String resv, String fundDate, String fundTime, String fundSeqId,
            String userId, String counterNo, String userNameText,
            String certType, String certId, String bankCode,
            String bankProvinceCode, String bankRegionCode, String cardNo,
            String transAmt) {
        super();
        this.instuId = instuId;
        this.merchantDate = merchantDate;
        this.merchantTime = merchantTime;
        this.merchantSeqId = merchantSeqId;
        this.pnrDate = pnrDate;
        this.pnrTime = pnrTime;
        this.pnrSeqId = pnrSeqId;
        this.respCode = respCode;
        this.respDesc = respDesc;
        this.resv = resv;
        this.fundDate = fundDate;
        this.fundTime = fundTime;
        this.fundSeqId = fundSeqId;
        this.userId = userId;
        this.counterNo = counterNo;
        this.userNameText = userNameText;
        this.certType = certType;
        this.certId = certId;
        this.bankCode = bankCode;
        this.bankProvinceCode = bankProvinceCode;
        this.bankRegionCode = bankRegionCode;
        this.cardNo = cardNo;
        this.transAmt = transAmt;
    }

    

    
}
