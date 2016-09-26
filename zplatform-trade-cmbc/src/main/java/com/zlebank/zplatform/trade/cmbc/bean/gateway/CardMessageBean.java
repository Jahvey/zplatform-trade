/* 
 * CardMessageBean.java  
 * 
 * version TODO
 *
 * 2015年11月23日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.cmbc.bean.gateway;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import com.zlebank.zplatform.trade.bean.enums.CertifTypeEnmu;
import com.zlebank.zplatform.trade.model.PojoRealnameAuth;
import com.zlebank.zplatform.trade.model.TxnsWithholdingModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月23日 下午2:42:17
 * @since 
 */
public class CardMessageBean implements Serializable{
   
    private static final long serialVersionUID = 4685332161435561011L;
    @NotEmpty(message="param.empty.instead.cardno")
    private String cardNo;//  银行卡号
    @NotEmpty(message="param.empty.instead.cardType")
    private String cardType;// 卡类型
    @NotEmpty(message="param.empty.instead.customernm")
    private String customerNm ;// 持卡人姓名 
    @NotEmpty(message="param.empty.instead.certiftp")
    private String certifTp;//证件类型
    @NotEmpty(message="param.empty.instead.certifid")
    private String certifId;//证件号
    @NotEmpty(message="param.empty.instead.phoneNo")
    private String phoneNo;// 手机号
    private String cvn2;//cvn2
    private String expired;// 卡有效期
    
    private TxnsWithholdingModel withholding;
    private PojoRealnameAuth realnameAuth;
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
     * @return the cardType
     */
    public String getCardType() {
        return cardType;
    }
    /**
     * @param cardType the cardType to set
     */
    public void setCardType(String cardType) {
        this.cardType = cardType;
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
    /**
     * @return the cvn2
     */
    public String getCvn2() {
        return cvn2;
    }
    /**
     * @param cvn2 the cvn2 to set
     */
    public void setCvn2(String cvn2) {
        this.cvn2 = cvn2;
    }
    /**
     * @return the expired
     */
    public String getExpired() {
        return expired;
    }
    /**
     * @param expired the expired to set
     */
    public void setExpired(String expired) {
        this.expired = expired;
    }
    /**
     * @param cardNo
     * @param cardType
     * @param customerNm
     * @param certifTp
     * @param certifId
     * @param phoneNo
     * @param cvn2
     * @param expired
     */
    public CardMessageBean(String tests) {
        super();
        this.cardNo = "6217000010053213727";
        this.cardType = "0";
        this.customerNm = "喻磊";
        this.certifTp = "ZR01";
        this.certifId = "61230119880402031X";
        this.phoneNo = "13141464942";
      
    }
    /**
     * 
     */
    public CardMessageBean() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * @param cardNo
     * @param cardType
     * @param customerNm
     * @param certifTp
     * @param certifId
     * @param phoneNo
     * @param cvn2
     * @param expired
     */
    public CardMessageBean(PojoRealnameAuth realnameAuth) {
        super();
        this.cardNo = realnameAuth.getCardNo();
        this.cardType =realnameAuth.getCardType();
        this.customerNm = realnameAuth.getCustomerNm();
        this.certifTp = CertifTypeEnmu.fromValue(realnameAuth.getCertifTp()).getCmbcCode();
        this.certifId = realnameAuth.getCertifId();
        this.phoneNo = realnameAuth.getPhoneNo().toString();
        this.cvn2 = realnameAuth.getCvn2();
        this.expired = realnameAuth.getExpired();
    }
    public TxnsWithholdingModel getWithholding() {
        return withholding;
    }
    public void setWithholding(TxnsWithholdingModel withholding) {
        this.withholding = withholding;
    }
    public PojoRealnameAuth getRealnameAuth() {
        return realnameAuth;
    }
    public void setRealnameAuth(PojoRealnameAuth realnameAuth) {
        this.realnameAuth = realnameAuth;
    }
    
    
}
