package com.zlebank.zplatform.wechat.qr.wx.bean;

/**
 * 
 * 查询退款接口
 * 
 * 【微信订单号】【商户订单号】【商户退款单号】【微信退款单号】四选一， 不可同时为空
 * 
 * @author Luxiaoshuai
 * @version
 * @date 2016年5月26日 下午3:20:19
 * @since
 */
public class QueryRefundBean {

	private String transaction_id;
	private String out_trade_no;
	private String out_refund_no;
	private String refund_id;

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
	
}
