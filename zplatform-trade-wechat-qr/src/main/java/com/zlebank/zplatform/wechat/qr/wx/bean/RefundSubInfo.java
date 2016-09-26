/* 
 * RefundSubInfo.java  
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
 * 退款子类详情
 * （本版本不考虑代金券或立减优惠批次ID）
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年5月26日 下午3:23:34
 * @since 
 */
public class RefundSubInfo {
    /**商户退款单号**/
    private String out_refund_no;
    /**微信退款单号**/
    private String refund_id;
    /**退款渠道**/
    private String refund_channel;
    /**退款金额**/
    private String refund_fee;
    /**代金券或立减优惠退款金额**/
    private String coupon_refund_fee;
    /**代金券或立减优惠使用数量**/
    private String coupon_refund_count;
    /**代金券或立减优惠批次ID**/
//    private String coupon_refund_batch_id_$m;
    /**代金券或立减优惠ID**/
//    private String coupon_refund_id_$m;
    /**单个代金券或立减优惠支付金额**/
//    private String coupon_refund_fee_$m;
    /**退款状态**/
    private String refund_status;
    /**退款入账账户**/
    private String refund_recv_accout;
    public String getOut_refund_no() {
        return out_refund_no;
    }
    public void setOut_refund_no(String out_refund_no) {
        this.out_refund_no = out_refund_no;
    }
    public String getRefund_id() {
        return refund_id;
    }
    public void setRefund_id(String refund_id) {
        this.refund_id = refund_id;
    }
    public String getRefund_channel() {
        return refund_channel;
    }
    public void setRefund_channel(String refund_channel) {
        this.refund_channel = refund_channel;
    }
    public String getRefund_fee() {
        return refund_fee;
    }
    public void setRefund_fee(String refund_fee) {
        this.refund_fee = refund_fee;
    }
    public String getCoupon_refund_fee() {
        return coupon_refund_fee;
    }
    public void setCoupon_refund_fee(String coupon_refund_fee) {
        this.coupon_refund_fee = coupon_refund_fee;
    }
    public String getCoupon_refund_count() {
        return coupon_refund_count;
    }
    public void setCoupon_refund_count(String coupon_refund_count) {
        this.coupon_refund_count = coupon_refund_count;
    }
    public String getRefund_status() {
        return refund_status;
    }
    public void setRefund_status(String refund_status) {
        this.refund_status = refund_status;
    }
    public String getRefund_recv_accout() {
        return refund_recv_accout;
    }
    public void setRefund_recv_accout(String refund_recv_accout) {
        this.refund_recv_accout = refund_recv_accout;
    }

    
    
}
