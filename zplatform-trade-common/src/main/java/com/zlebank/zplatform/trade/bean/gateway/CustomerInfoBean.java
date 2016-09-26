/* 
 * CustomerInfoBean.java  
 * 
 * version TODO
 *
 * 2015年9月21日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean.gateway;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月21日 下午2:48:24
 * @since 
 */
public class CustomerInfoBean implements Serializable{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 881533422430332835L;
    
    /**
     * 证件类型
     */
    @NotEmpty(message="param.empty")
    @Length(max=2,message="param.error")
    private String certifTp;
    /**
     * 证件号码
     */
    @NotEmpty(message="param.empty")
    @Length(max=20,message="param.error")
    private String certifId;
    /**
     * 姓名
     */
    @NotEmpty(message="param.empty")
    @Length(max=30,message="param.error")
    private String customerNm;
    /**
     * 短信验证码
     */
    @Length(max=6,message="param.error")
    private String smsCode;
    /**
     * 密码
     */
    @Length(max=256,message="param.error")
    private String pin;
    /**
     * 加密信息
     */
    @NotEmpty(message="param.empty")
    @Length(max=1024,message="param.error")
    private String encryptData;
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
     * @return the customerNm
     */
    public String getCustomerNm() {
        return customerNm;
    }
    /**
     * @param customerNm the customerNm to set
     */
    public void setCustomerNm(String customerNm) {
        this.customerNm = customerNm;
    }
    /**
     * @return the smsCode
     */
    public String getSmsCode() {
        return smsCode;
    }
    /**
     * @param smsCode the smsCode to set
     */
    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
    /**
     * @return the pin
     */
    public String getPin() {
        return pin;
    }
    /**
     * @param pin the pin to set
     */
    public void setPin(String pin) {
        this.pin = pin;
    }
    /**
     * @return the encryptData
     */
    public String getEncryptData() {
        return encryptData;
    }
    /**
     * @param encryptData the encryptData to set
     */
    public void setEncryptData(String encryptData) {
        this.encryptData = encryptData;
    }
    /**
     * @param certifTp
     * @param certifId
     * @param customerNm
     * @param smsCode
     * @param pin
     * @param encryptData
     */
    public CustomerInfoBean(String certifTp, String certifId,
            String customerNm, String smsCode, String pin, String encryptData) {
        super();
        this.certifTp = certifTp;
        this.certifId = certifId;
        this.customerNm = customerNm;
        this.smsCode = smsCode;
        this.pin = pin;
        this.encryptData = encryptData;
    }
    
    
}
