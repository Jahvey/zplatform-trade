/* 
 * ReaPayResultBean.java  
 * 
 * version TODO
 *
 * 2015年9月16日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月16日 下午2:00:42
 * @since 
 */
public class ReaPayResultBean {
    private String merchant_id;
    private String order_no;
    private String result_code;
    private String bind_id;
    private String result_msg;
    private String bank_name;
    private String bank_code;
    private String phone;
    
   
    private String trade_no;
    private String bank_card_type;
    private String card_last;
   
    
    
    
    private String total_fee;
    private String status;
    private String timestamp;
    
    
    
    
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
     * @return the result_code
     */
    public String getResult_code() {
        return result_code;
    }
    /**
     * @param result_code the result_code to set
     */
    public void setResult_code(String result_code) {
        this.result_code = result_code;
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
     * @return the result_msg
     */
    public String getResult_msg() {
        return result_msg;
    }
    /**
     * @param result_msg the result_msg to set
     */
    public void setResult_msg(String result_msg) {
        this.result_msg = result_msg;
    }
    /**
     * @return the bank_name
     */
    public String getBank_name() {
        return bank_name;
    }
    /**
     * @param bank_name the bank_name to set
     */
    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }
    /**
     * @return the bank_code
     */
    public String getBank_code() {
        return bank_code;
    }
    /**
     * @param bank_code the bank_code to set
     */
    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }
    
    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }
    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * @param merchant_id
     * @param order_no
     * @param result_code
     * @param bind_id
     * @param result_msg
     * @param bank_name
     * @param bank_code
     * @param phone
     */
    public ReaPayResultBean(String merchant_id, String order_no,
            String result_code, String bind_id, String result_msg,
            String bank_name, String bank_code, String phone) {
        super();
        this.merchant_id = merchant_id;
        this.order_no = order_no;
        this.result_code = result_code;
        this.bind_id = bind_id;
        this.result_msg = result_msg;
        this.bank_name = bank_name;
        this.bank_code = bank_code;
        this.phone = phone;
    }
    /**
     * @return the trade_no
     */
    public String getTrade_no() {
        return trade_no;
    }
    /**
     * @param trade_no the trade_no to set
     */
    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }
    /**
     * @return the bank_card_type
     */
    public String getBank_card_type() {
        return bank_card_type;
    }
    /**
     * @param bank_card_type the bank_card_type to set
     */
    public void setBank_card_type(String bank_card_type) {
        this.bank_card_type = bank_card_type;
    }
    /**
     * @return the card_last
     */
    public String getCard_last() {
        return card_last;
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
     * @return the status
     */
    public String getStatus() {
        return status;
    }
    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }
    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    /**
     * @param card_last the card_last to set
     */
    public void setCard_last(String card_last) {
        this.card_last = card_last;
    }
    /**
     * @param merchant_id
     * @param order_no
     * @param result_code
     * @param bind_id
     * @param result_msg
     * @param bank_name
     * @param bank_code
     * @param phone
     * @param trade_no
     * @param bank_card_type
     * @param card_last
     */
    public ReaPayResultBean(String merchant_id, String order_no,
            String result_code, String bind_id, String result_msg,
            String bank_name, String bank_code, String phone, String trade_no,
            String bank_card_type, String card_last) {
        super();
        this.merchant_id = merchant_id;
        this.order_no = order_no;
        this.result_code = result_code;
        this.bind_id = bind_id;
        this.result_msg = result_msg;
        this.bank_name = bank_name;
        this.bank_code = bank_code;
        this.phone = phone;
        this.trade_no = trade_no;
        this.bank_card_type = bank_card_type;
        this.card_last = card_last;
    }
    /**
     * @param merchant_id
     * @param order_no
     * @param result_code
     * @param bind_id
     * @param result_msg
     * @param bank_name
     * @param bank_code
     * @param phone
     * @param trade_no
     * @param bank_card_type
     * @param card_last
     * @param total_fee
     * @param status
     * @param timestamp
     */
    public ReaPayResultBean(String merchant_id, String order_no,
            String result_code, String bind_id, String result_msg,
            String bank_name, String bank_code, String phone, String trade_no,
            String bank_card_type, String card_last, String total_fee,
            String status, String timestamp) {
        super();
        this.merchant_id = merchant_id;
        this.order_no = order_no;
        this.result_code = result_code;
        this.bind_id = bind_id;
        this.result_msg = result_msg;
        this.bank_name = bank_name;
        this.bank_code = bank_code;
        this.phone = phone;
        this.trade_no = trade_no;
        this.bank_card_type = bank_card_type;
        this.card_last = card_last;
        this.total_fee = total_fee;
        this.status = status;
        this.timestamp = timestamp;
    }
    /**
     * 
     */
    public ReaPayResultBean() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    
}
