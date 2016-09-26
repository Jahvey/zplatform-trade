/* 
 * EncryptDataBean.java  
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

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月21日 下午2:57:08
 * @since 
 */
public class EncryptDataBean implements Serializable{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    private String cardNo;
    private String cvn2;
    private String expired;
    private String phoneNo;
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
     * @param cardNo
     * @param cvn2
     * @param expired
     * @param phoneNo
     */
    public EncryptDataBean(String cardNo, String cvn2, String expired,
            String phoneNo) {
        super();
        this.cardNo = cardNo;
        this.cvn2 = cvn2;
        this.expired = expired;
        this.phoneNo = phoneNo;
    }
    

}
