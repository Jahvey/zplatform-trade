package com.zlebank.zplatform.wechat.qr.wx.bean;

import java.util.List;

public class QueryRefundResultBean {
    /**返回状态码**/
    private String return_code;
    /**返回信息**/
    private String return_msg;
    /**业务结果**/
    private String result_code;
    /**错误码**/
    private String err_code;
    /**错误描述**/
    private String err_code_des;
    /**应用ID**/
    private String appid;
    /**商户号**/
    private String mch_id;
    /**设备号**/
    private String device_info;
    /**随机字符串**/
    private String nonce_str;
    /**签名**/
    private String sign;
    /**微信订单号**/
    private String transaction_id;
    /**商户订单号**/
    private String out_trade_no;
    /**订单总金额**/
    private String total_fee;
    /**订单金额货币种类**/
    private String fee_type;
    /**现金支付金额**/
    private String cash_fee;
    /**退款笔数**/
    private String refund_count;
    
    private List<RefundSubInfo> refundSub;
    
    public String getReturn_code() {
        return return_code;
    }
    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }
    public String getReturn_msg() {
        return return_msg;
    }
    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }
    public String getResult_code() {
        return result_code;
    }
    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }
    public String getErr_code() {
        return err_code;
    }
    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }
    public String getErr_code_des() {
        return err_code_des;
    }
    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }
    public String getAppid() {
        return appid;
    }
    public void setAppid(String appid) {
        this.appid = appid;
    }
    public String getMch_id() {
        return mch_id;
    }
    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }
    public String getDevice_info() {
        return device_info;
    }
    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }
    public String getNonce_str() {
        return nonce_str;
    }
    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }
    public String getSign() {
        return sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
    public String getTransaction_id() {
        return transaction_id;
    }
    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }
    public String getOut_trade_no() {
        return out_trade_no;
    }
    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }
    public String getTotal_fee() {
        return total_fee;
    }
    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }
    public String getFee_type() {
        return fee_type;
    }
    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }
    public String getCash_fee() {
        return cash_fee;
    }
    public void setCash_fee(String cash_fee) {
        this.cash_fee = cash_fee;
    }
    public String getRefund_count() {
        return refund_count;
    }
    public void setRefund_count(String refund_count) {
        this.refund_count = refund_count;
    }
    public List<RefundSubInfo> getRefundSub() {
        return refundSub;
    }
    public void setRefundSub(List<RefundSubInfo> refundSub) {
        this.refundSub = refundSub;
    }
    
}
