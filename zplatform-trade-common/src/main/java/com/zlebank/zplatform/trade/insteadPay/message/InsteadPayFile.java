/* 
 * InsteadPayFile.java  
 * 
 * version TODO
 *
 * 2015年11月24日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.insteadPay.message;

import java.io.Serializable;

import com.zlebank.zplatform.commons.validator.Scontant;
import com.zlebank.zplatform.trade.common.validator.An;
import com.zlebank.zplatform.trade.common.validator.Ans;
import com.zlebank.zplatform.trade.common.validator.N;

/**
 * 代付文件
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月24日 下午3:02:08
 * @since 
 */
public class InsteadPayFile implements Serializable{
    /**商户代码**/
    @N(max=15)
    private String merId;
    /**商户订单号**/
    @An(min=12,max=32)
    private String orderId;
    /**（人民币）156**/
    @N(min=3,max=3)
    private String currencyCode;
    /**单笔金额(以分为单位，最长12位)**/
    @N(max=12)
    private String amt;
    /**产品类型(固定：000001)**/
    @Scontant(contants={"000001"})
    private String bizType;
    /**账号类型01:银行卡;02:存折**/
    @Scontant(contants={"01","02"})
    private String accType;
    /**账号**/
    @N(min=12,max=60)
    private String accNo;
    /**户名**/
    @Ans(max=32)
    private String accName;
    /**开户行代码帐号类型取值为“02”时不能为空**/
    @N(max=12, isNull=false)
    private String bankCode;
    /**开户行省**/
    @Ans(max=10, isNull=true)
    private String issInsProvince;
    /**开户行市**/
    @Ans(max=10, isNull=true)
    private String issInsCity;
    /**开户行名称**/
    @Ans(max=20, isNull=true)
    private String issInsName;
    /**证件类型**/
    @Scontant(contants={"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","99"})
    private String certifTp;
    /**证件号码**/
    @Ans(max=20)
    private String certifId;
    /**手机号**/
    @N(max=11)
    private String phoneNo;
    /**账单类型**/
    @Scontant(contants={"0100"})
    private String billType;
    /**附言**/
    private String notes;
    public String getMerId() {
        return merId;
    }
    public void setMerId(String merId) {
        this.merId = merId;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getCurrencyCode() {
        return currencyCode;
    }
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
    public String getAmt() {
        return amt;
    }
    public void setAmt(String amt) {
        this.amt = amt;
    }
    public String getBizType() {
        return bizType;
    }
    public void setBizType(String bizType) {
        this.bizType = bizType;
    }
    public String getAccType() {
        return accType;
    }
    public void setAccType(String accType) {
        this.accType = accType;
    }
    public String getAccNo() {
        return accNo;
    }
    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }
    public String getAccName() {
        return accName;
    }
    public void setAccName(String accName) {
        this.accName = accName;
    }
    public String getBankCode() {
        return bankCode;
    }
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
    public String getIssInsProvince() {
        return issInsProvince;
    }
    public void setIssInsProvince(String issInsProvince) {
        this.issInsProvince = issInsProvince;
    }
    public String getIssInsCity() {
        return issInsCity;
    }
    public void setIssInsCity(String issInsCity) {
        this.issInsCity = issInsCity;
    }
    public String getIssInsName() {
        return issInsName;
    }
    public void setIssInsName(String issInsName) {
        this.issInsName = issInsName;
    }
    public String getCertifTp() {
        return certifTp;
    }
    public void setCertifTp(String certifTp) {
        this.certifTp = certifTp;
    }
    public String getCertifId() {
        return certifId;
    }
    public void setCertifId(String certifId) {
        this.certifId = certifId;
    }
    public String getPhoneNo() {
        return phoneNo;
    }
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    public String getBillType() {
        return billType;
    }
    public void setBillType(String billType) {
        this.billType = billType;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
}
