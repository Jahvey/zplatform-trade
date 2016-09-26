/* 
 * WXOrder.java  
 * 
 * version TODO
 *
 * 2016年5月16日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.wechat.qr.wx.bean;

import com.zlebank.zplatform.wechat.qr.wx.common.WXConfigure;


/**
 * 微信订单
 * 
 * 只有以下属性可以填写
 *
 ************************以下是必填********************************************
         out_trade_no【商户订单号】【String(32)】【必填】
         body【商品描述】【String(128)】【必填】
         total_fee【总金额】【Int】【必填】
         notify_url【通知地址】【String(256)】【必填】参照开发文档
 ************************以下是选填********************************************
        detail【商品详情】【String(8192)】【选填】
        attach【附加数据】【String(127)】【选填】
        time_start【交易起始时间】【String(14)】【选填】（格式是时间戳yyyyMMddHHmmss）
        time_expire【交易结束时间】【String(14)】【选填】（格式是时间戳yyyyMMddHHmmss）
        goods_tag【商品标记】【String(32)】【选填】
        limit_pay【指定支付方式】【String(32)】【选填】（no_credit--指定不能使用信用卡支付）
    
    以下地址参照
 * @see https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_1
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年5月16日 下午4:02:15
 * @since 
 */
public class WXOrderBean {
    /**应用ID**/
    private String appid=WXConfigure.getAppid();
    /**商户号**/
    private String mch_id=WXConfigure.getMchid();
    /**设备号**/
    private String device_info=WXConfigure.getDeviceInfo();
    /**随机字符串**/
    private String nonce_str;
    /**签名**/
    private String sign;
    /**商品描述**/
    private String body;
    /**商品详情**/
    private String detail;
    /**附加数据**/
    private String attach;
    /**商户订单号**/
    private String out_trade_no;
    /**货币类型**/
    private String fee_type=WXConfigure.getTradeType();
    /**总金额**/
    private String total_fee;
    /**终端IP**/
    private String spbill_create_ip=WXConfigure.getIp();
    /**交易起始时间**/
    private String time_start;
    /**交易结束时间**/
    private String time_expire;
    /**商品标记**/
    private String goods_tag;
    /**通知地址**/
    private String notify_url;
    /**交易类型**/
    private String trade_type;
    /**指定支付方式**/
    private String limit_pay;
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
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
    public String getAttach() {
        return attach;
    }
    public void setAttach(String attach) {
        this.attach = attach;
    }
    public String getOut_trade_no() {
        return out_trade_no;
    }
    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }
    public String getFee_type() {
        return fee_type;
    }
    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }
    public String getTotal_fee() {
        return total_fee;
    }
    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }
    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }
    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }
    public String getTime_start() {
        return time_start;
    }
    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }
    public String getTime_expire() {
        return time_expire;
    }
    public void setTime_expire(String time_expire) {
        this.time_expire = time_expire;
    }
    public String getGoods_tag() {
        return goods_tag;
    }
    public void setGoods_tag(String goods_tag) {
        this.goods_tag = goods_tag;
    }
    public String getNotify_url() {
        return notify_url;
    }
    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }
    public String getTrade_type() {
        return trade_type;
    }
    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }
    public String getLimit_pay() {
        return limit_pay;
    }
    public void setLimit_pay(String limit_pay) {
        this.limit_pay = limit_pay;
    }
}
