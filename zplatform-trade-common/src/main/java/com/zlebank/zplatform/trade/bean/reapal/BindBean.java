/* 
 * BindBean.java  
 * 
 * version TODO
 *
 * 2015年9月21日 
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
 * @date 2015年9月21日 下午1:52:13
 * @since 
 */
public class BindBean {
    private String merchant_id;
    private String member_id;
    private String bind_id;
    private String order_no;
    private String transtime;
    private String currency;
    private String total_fee;
    private String title;
    private String body;
    private String terminal_type;
    private String terminal_info;
    private String member_ip;
    private String seller_email;
    private String notify_url;
    private String token_id;
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
     * @return the member_id
     */
    public String getMember_id() {
        return member_id;
    }
    /**
     * @param member_id the member_id to set
     */
    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }
    /**
     * @return the bind_id
     */
    public String getBind_id() {
        return bind_id;
    }
    /**
     * @param bind_id the bind_id to set
     */
    public void setBind_id(String bind_id) {
        this.bind_id = bind_id;
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
     * @return the transtime
     */
    public String getTranstime() {
        return transtime;
    }
    /**
     * @param transtime the transtime to set
     */
    public void setTranstime(String transtime) {
        this.transtime = transtime;
    }
    /**
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }
    /**
     * @param currency the currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    /**
     * @return the total_fee
     */
    public String getTotal_fee() {
        return total_fee;
    }
    /**
     * @param total_fee the total_fee to set
     */
    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }
    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * @return the body
     */
    public String getBody() {
        return body;
    }
    /**
     * @param body the body to set
     */
    public void setBody(String body) {
        this.body = body;
    }
    /**
     * @return the terminal_type
     */
    public String getTerminal_type() {
        return terminal_type;
    }
    /**
     * @param terminal_type the terminal_type to set
     */
    public void setTerminal_type(String terminal_type) {
        this.terminal_type = terminal_type;
    }
    /**
     * @return the terminal_info
     */
    public String getTerminal_info() {
        return terminal_info;
    }
    /**
     * @param terminal_info the terminal_info to set
     */
    public void setTerminal_info(String terminal_info) {
        this.terminal_info = terminal_info;
    }
    /**
     * @return the member_ip
     */
    public String getMember_ip() {
        return member_ip;
    }
    /**
     * @param member_ip the member_ip to set
     */
    public void setMember_ip(String member_ip) {
        this.member_ip = member_ip;
    }
    /**
     * @return the seller_email
     */
    public String getSeller_email() {
        return seller_email;
    }
    /**
     * @param seller_email the seller_email to set
     */
    public void setSeller_email(String seller_email) {
        this.seller_email = seller_email;
    }
    /**
     * @return the notify_url
     */
    public String getNotify_url() {
        return notify_url;
    }
    /**
     * @param notify_url the notify_url to set
     */
    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }
    /**
     * @return the token_id
     */
    public String getToken_id() {
        return token_id;
    }
    /**
     * @param token_id the token_id to set
     */
    public void setToken_id(String token_id) {
        this.token_id = token_id;
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
     * @param member_id
     * @param bind_id
     * @param order_no
     * @param transtime
     * @param currency
     * @param total_fee
     * @param title
     * @param body
     * @param terminal_type
     * @param terminal_info
     * @param member_ip
     * @param seller_email
     * @param notify_url
     * @param token_id
     * @param version
     * @param sign_type
     * @param sign
     */
    public BindBean(String merchant_id, String member_id, String bind_id,
            String order_no, String transtime, String currency,
            String total_fee, String title, String body, String terminal_type,
            String terminal_info, String member_ip, String seller_email,
            String notify_url, String token_id, String version,
            String sign_type, String sign) {
        super();
        this.merchant_id = merchant_id;
        this.member_id = member_id;
        this.bind_id = bind_id;
        this.order_no = order_no;
        this.transtime = transtime;
        this.currency = currency;
        this.total_fee = total_fee;
        this.title = title;
        this.body = body;
        this.terminal_type = terminal_type;
        this.terminal_info = terminal_info;
        this.member_ip = member_ip;
        this.seller_email = seller_email;
        this.notify_url = notify_url;
        this.token_id = token_id;
        this.version = version;
        this.sign_type = sign_type;
        this.sign = sign;
    }


}
