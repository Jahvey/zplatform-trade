/* 
 * WXInvokePayInterface.java  
 * 
 * version TODO
 *
 * 2016年5月20日 
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
 * @date 2016年5月20日 下午4:17:32
 * @since 
 */
public class WXInvokePayInterfaceBean {
    private String appid;
    private String partnerid;
    private String prepayid;
    private String package_;
    private String noncestr;
    private String timestamp;
    public String getAppid() {
        return appid;
    }
    public void setAppid(String appid) {
        this.appid = appid;
    }
    public String getPartnerid() {
        return partnerid;
    }
    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }
    public String getPrepayid() {
        return prepayid;
    }
    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }
    public String getPackage_() {
        return package_;
    }
    public void setPackage_(String package_) {
        this.package_ = package_;
    }
    public String getNoncestr() {
        return noncestr;
    }
    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
}   
