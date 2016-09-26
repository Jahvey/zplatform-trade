/* 
 * WithdrawInfo.java  
 * 
 * version v1.0
 *
 * 2015年10月23日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.message;

/**
 * 提现信息
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年10月23日 下午8:35:33
 * @since 
 */
public class WithdrawInfo {

    /**收款人账户号**/
    private String accNo;
    /**收款人账户名**/
    private String accName;
    /**联行号**/
    private String bankCode;
    /**银行名称**/
    private String bankName;
    /**证件类型**/
    private String certifTp;
    /**证件号**/
    private String certifId;
    /**手机号**/
    private String phoneNo;

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
    public String getBankName() {
        return bankName;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName;
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
}
