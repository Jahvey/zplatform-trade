/* 
 * PayBean.java  
 * 
 * version TODO
 *
 * 2015年9月16日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean.reapal;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月16日 下午5:40:29
 * @since 
 */
public class PayBean {
    private String merchant_id;
    private String order_no;
    private String check_code;
    private String version;
    private String sign_type;
    private String sign;
    /**
     * @return the merchant_id
     */
    public String getMerchant_id() {
        return merchant_id;
    }
    /**
     * @param merchant_id the merchant_id to set
     */
    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }
    /**
     * @return the order_no
     */
    public String getOrder_no() {
        return order_no;
    }
    /**
     * @param order_no the order_no to set
     */
    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }
    /**
     * @return the check_code
     */
    public String getCheck_code() {
        return check_code;
    }
    /**
     * @param check_code the check_code to set
     */
    public void setCheck_code(String check_code) {
        this.check_code = check_code;
    }
    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }
    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }
    /**
     * @return the sign_type
     */
    public String getSign_type() {
        return sign_type;
    }
    /**
     * @param sign_type the sign_type to set
     */
    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }
    /**
     * @return the sign
     */
    public String getSign() {
        return sign;
    }
    /**
     * @param sign the sign to set
     */
    public void setSign(String sign) {
        this.sign = sign;
    }
    /**
     * @param merchant_id
     * @param order_no
     * @param check_code
     * @param version
     * @param sign_type
     * @param sign
     */
    public PayBean(String merchant_id, String order_no, String check_code,
            String version, String sign_type, String sign) {
        super();
        this.merchant_id = merchant_id;
        this.order_no = order_no;
        this.check_code = check_code;
        this.version = version;
        this.sign_type = sign_type;
        this.sign = sign;
    }
    
    
}
