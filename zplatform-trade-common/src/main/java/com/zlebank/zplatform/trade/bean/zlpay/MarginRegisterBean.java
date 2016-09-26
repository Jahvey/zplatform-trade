/* 
 * MarginRegisterBean.java  
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


/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年8月21日 下午1:08:25
 * @since 
 */
public class MarginRegisterBean implements Serializable{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    private String instuId ;
    private String fundDate;
    private String fundTime;
    private String fundSeqId;
    private String counterNo;
    private String custType;
    private String userNameText;
    private String certType;
    private String certId;
    private String orgId;
    private String bankCode;
    private String bankProvinceCode;
    private String bankRegionCode;
    private String cardNo;
    private String payPassWord;
    private String mobile;
    private String identifyingCode;
    private String resv;
    /**
     * @param instuId
     * @param fundDate
     * @param fundTime
     * @param fundSeqId
     * @param counterNo
     * @param custType
     * @param userNameText
     * @param certType
     * @param certId
     * @param orgId
     * @param bankCode
     * @param bankProvinceCode
     * @param bankRegionCode
     * @param cardNo
     * @param payPassWord
     * @param mobile
     * @param identifyingCode
     * @param resv
     */
    public MarginRegisterBean(String instuId, String fundDate, String fundTime,
            String fundSeqId, String counterNo, String custType,
            String userNameText, String certType, String certId, String orgId,
            String bankCode, String bankProvinceCode, String bankRegionCode,
            String cardNo, String payPassWord, String mobile,
            String identifyingCode, String resv) {
        super();
        this.instuId = instuId;
        this.fundDate = fundDate;
        this.fundTime = fundTime;
        this.fundSeqId = fundSeqId;
        this.counterNo = counterNo;
        this.custType = custType;
        this.userNameText = userNameText;
        this.certType = certType;
        this.certId = certId;
        this.orgId = orgId;
        this.bankCode = bankCode;
        this.bankProvinceCode = bankProvinceCode;
        this.bankRegionCode = bankRegionCode;
        this.cardNo = cardNo;
        this.payPassWord = payPassWord;
        this.mobile = mobile;
        this.identifyingCode = identifyingCode;
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
     * @return the custType
     */
    public String getCustType() {
        return custType;
    }
    /**
     * @param custType the custType to set
     */
    public void setCustType(String custType) {
        this.custType = custType;
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
     * @return the orgId
     */
    public String getOrgId() {
        return orgId;
    }
    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
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
     * @return the payPassWord
     */
    public String getPayPassWord() {
        return payPassWord;
    }
    /**
     * @param payPassWord the payPassWord to set
     */
    public void setPayPassWord(String payPassWord) {
        this.payPassWord = payPassWord;
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
     * @return the identifyingCode
     */
    public String getIdentifyingCode() {
        return identifyingCode;
    }
    /**
     * @param identifyingCode the identifyingCode to set
     */
    public void setIdentifyingCode(String identifyingCode) {
        this.identifyingCode = identifyingCode;
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
        
        data.append("fundDate=");
        data.append(fundDate);
        data.append("|");
        
        data.append("fundTime=");
        data.append(fundTime);
        data.append("|");
        
        data.append("fundSeqId=");
        data.append(fundSeqId);
        data.append("|");
        
        data.append("counterNo=");
        data.append(counterNo);
        data.append("|");
        
        data.append("custType=");
        data.append(custType);
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
        
        data.append("orgId=");
        data.append(orgId);
        data.append("|");
        
        data.append("bankCode=");
        data.append(bankCode);
        data.append("|");
        
        data.append("bankProvinceCode=");
        data.append(bankProvinceCode);
        data.append("|");
        
        data.append("bankRegionCode=");
        data.append(bankRegionCode);
        data.append("|");
        
        data.append("cardNo=");
        data.append(cardNo);
        data.append("|");
        
        data.append("payPassWord=");
        data.append(payPassWord);
        data.append("|");
        
        data.append("mobile=");
        data.append(mobile);
        data.append("|");
        
        data.append("identifyingCode=");
        data.append(identifyingCode);
        data.append("|");
        
        data.append("resv=");
        data.append(resv);
        data.append("|");
        
       
        return data.toString();
    }
}
