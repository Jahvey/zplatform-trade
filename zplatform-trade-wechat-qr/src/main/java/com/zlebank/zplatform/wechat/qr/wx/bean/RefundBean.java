/* 
 * RefundBean.java  
 * 
 * version TODO
 *
 * 2016年5月26日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.wechat.qr.wx.bean;

/**
 * Class Description
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年5月26日 上午9:21:20
 * @since 
 */
public class RefundBean {
    private String out_refund_no;
    private String out_trade_no;
    private String refund_fee;
    private String total_fee;
    private String transaction_id;
    public String getOut_refund_no() {
        return out_refund_no;
    }
    public void setOut_refund_no(String out_refund_no) {
        this.out_refund_no = out_refund_no;
    }
    public String getOut_trade_no() {
        return out_trade_no;
    }
    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }
    public String getRefund_fee() {
        return refund_fee;
    }
    public void setRefund_fee(String refund_fee) {
        this.refund_fee = refund_fee;
    }
    public String getTotal_fee() {
        return total_fee;
    }
    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }
    public String getTransaction_id() {
        return transaction_id;
    }
    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }
    
}
