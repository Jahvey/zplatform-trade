/* 
 * WapWithdrawAccBean.java  
 * 
 * version TODO
 *
 * 2015年10月23日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean.wap;

import java.io.Serializable;

import com.zlebank.zplatform.member.pojo.PojoQuickpayCust;
import com.zlebank.zplatform.trade.model.QuickpayCustModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年10月23日 上午10:33:04
 * @since
 */
public class WapWithdrawAccBean implements Serializable{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 8162417102547533932L;
    private String accNo;// 收款人账户号
    private String accName;// 收款人账户名
    private String bankCode;// 联行号
    private String bankName;// 银行名称
    private String certifTp;// 证件类型
    private String certifId;// 证件号
    private String phoneNo;// 手机号
    /**
     * @return the accNo
     */
    public String getAccNo() {
        return accNo;
    }
    /**
     * @param accNo the accNo to set
     */
    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }
    /**
     * @return the accName
     */
    public String getAccName() {
        return accName;
    }
    /**
     * @param accName the accName to set
     */
    public void setAccName(String accName) {
        this.accName = accName;
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
     * @return the bankName
     */
    public String getBankName() {
        return bankName;
    }
    /**
     * @param bankName the bankName to set
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    /**
     * @return the certifTp
     */
    public String getCertifTp() {
        return certifTp;
    }
    /**
     * @param certifTp the certifTp to set
     */
    public void setCertifTp(String certifTp) {
        this.certifTp = certifTp;
    }
    /**
     * @return the certifId
     */
    public String getCertifId() {
        return certifId;
    }
    /**
     * @param certifId the certifId to set
     */
    public void setCertifId(String certifId) {
        this.certifId = certifId;
    }
    /**
     * @return the phoneNo
     */
    public String getPhoneNo() {
        return phoneNo;
    }
    /**
     * @param phoneNo the phoneNo to set
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    
    public WapWithdrawAccBean(){}
    
    /**
     * @param quickpayCust
     */
    public WapWithdrawAccBean(QuickpayCustModel quickpayCust) {
        this.accNo = quickpayCust.getCardno();
        this.accName = quickpayCust.getAccname();
        this.bankCode = quickpayCust.getBankcode();
        this.bankName = quickpayCust.getBankname();
        this.certifTp = quickpayCust.getIdtype();
        this.certifId = quickpayCust.getIdnum();
        this.phoneNo = quickpayCust.getPhone();
    }
    public WapWithdrawAccBean(PojoQuickpayCust quickpayCust) {
        this.accNo = quickpayCust.getCardno();
        this.accName = quickpayCust.getAccname();
        this.bankCode = quickpayCust.getBankcode();
        this.bankName = quickpayCust.getBankname();
        this.certifTp = quickpayCust.getIdtype();
        this.certifId = quickpayCust.getIdnum();
        this.phoneNo = quickpayCust.getPhone();
    }
    
}
