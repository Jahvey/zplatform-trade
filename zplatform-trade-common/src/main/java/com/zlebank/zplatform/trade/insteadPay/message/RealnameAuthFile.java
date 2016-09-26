/* 
 * RealnameAuthFile.java  
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

import com.zlebank.zplatform.trade.common.validator.Ans;
import com.zlebank.zplatform.trade.common.validator.N;

/**
 * 认证信息
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月24日 下午4:45:13
 * @since 
 */
public class RealnameAuthFile implements Serializable{

    /**银行卡号**/
    @N(max=20)
    private String cardNo;
    /**卡类型**/
    @N(max=1)
    private String cardType;
    /**持卡人姓名**/
    @Ans(max=30)
    private String customerNm;
    /**证件类型**/
    @N(max=2)
    private String certifTp;
    /**证件号**/
    @Ans(max=20)
    private String certifId;
    /**手机号**/
    @N(max=11)
    private String phoneNo;
    /**cvn2**/
    @N(max=3, isNull=true)
    private String cvn2;
    /**卡有效期**/
    @N(max=4, isNull=true)
    private String expired;

    public String getCardNo() {
        return cardNo;
    }
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
    public String getCardType() {
        return cardType;
    }
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
    public String getCustomerNm() {
        return customerNm;
    }
    public void setCustomerNm(String customerNm) {
        this.customerNm = customerNm;
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
    public String getCvn2() {
        return cvn2;
    }
    public void setCvn2(String cvn2) {
        this.cvn2 = cvn2;
    }
    public String getExpired() {
        return expired;
    }
    public void setExpired(String expired) {
        this.expired = expired;
    }
    
}
