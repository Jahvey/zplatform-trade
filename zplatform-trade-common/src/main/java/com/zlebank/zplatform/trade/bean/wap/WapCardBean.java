/* 
 * WapCardBean.java  
 * 
 * version TODO
 *
 * 2015年10月12日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean.wap;

import java.io.Serializable;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年10月12日 下午5:10:25
 * @since 
 */
public class WapCardBean implements Serializable{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -7381008706049911953L;
    private String cardNo;
    private String cardType;
    private String customerNm;
    private String certifTp;
    private String certifId;
    private String phoneNo;
    private String cvn2;
    private String expired;
    private String tn ;
    
    public String getTn() {
		return tn;
	}
	public void setTn(String tn) {
		this.tn = tn;
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
	public WapCardBean(String cardNo, String cardType, String customerNm,
			String certifTp, String certifId, String phoneNo, String cvn2,
			String expired) {
		super();
		this.cardNo = cardNo;
		this.cardType = cardType;
		this.customerNm = customerNm;
		this.certifTp = certifTp;
		this.certifId = certifId;
		this.phoneNo = phoneNo;
		this.cvn2 = cvn2;
		this.expired = expired;
	}
	/**
	 * 
	 */
	public WapCardBean() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}
