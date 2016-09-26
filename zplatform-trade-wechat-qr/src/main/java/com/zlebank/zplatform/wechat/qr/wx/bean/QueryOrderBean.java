/* 
 * QueryOrderBean.java  
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
 * 查询订单
 * 
 * 微信订单号 / 商户订单号 二选一
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年5月26日 下午3:49:35
 * @since 
 */
public class QueryOrderBean {
    private String transaction_id;
    private String out_trade_no ;
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
    
}
