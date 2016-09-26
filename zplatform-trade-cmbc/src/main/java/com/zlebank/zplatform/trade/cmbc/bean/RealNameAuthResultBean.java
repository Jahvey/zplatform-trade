/* 
 * RealNameAuthResultBean.java  
 * 
 * version TODO
 *
 * 2015年11月23日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.cmbc.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月23日 下午2:00:46
 * @since
 */
@XStreamAlias("Ans")
public class RealNameAuthResultBean {

    // 版本号 Version
    @XStreamAlias("Version")
    private String version;
    @XStreamAlias("SettDate")
    private String settdate;// 清算日期private String
    @XStreamAlias("TransTime")
    private String transtime;// 交易时间private String
    @XStreamAlias("ReqSerialNo")
    private String reqserialno;// 请求流水号
    @XStreamAlias("ExecType")
    private String exectype;// 响应类型private String
    @XStreamAlias("ExecCode")
    private String execcode;// 响应代码private String
    @XStreamAlias("ExecMsg")
    private String execmsg;// 响应描述private String
    @XStreamAlias("ValidateStatus")
    private String validatestatus;// 认证状态private String
    @XStreamAlias("PaySerialNo")
    private String payserialno;// 平台处理流水号
    @XStreamAlias("Resv")
    private String resv;// 备用域
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
     * @return the settdate
     */
    public String getSettdate() {
        return settdate;
    }
    /**
     * @param settdate the settdate to set
     */
    public void setSettdate(String settdate) {
        this.settdate = settdate;
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
     * @return the reqserialno
     */
    public String getReqserialno() {
        return reqserialno;
    }
    /**
     * @param reqserialno the reqserialno to set
     */
    public void setReqserialno(String reqserialno) {
        this.reqserialno = reqserialno;
    }
    /**
     * @return the exectype
     */
    public String getExectype() {
        return exectype;
    }
    /**
     * @param exectype the exectype to set
     */
    public void setExectype(String exectype) {
        this.exectype = exectype;
    }
    /**
     * @return the execcode
     */
    public String getExeccode() {
        return execcode;
    }
    /**
     * @param execcode the execcode to set
     */
    public void setExeccode(String execcode) {
        this.execcode = execcode;
    }
    /**
     * @return the execmsg
     */
    public String getExecmsg() {
        return execmsg;
    }
    /**
     * @param execmsg the execmsg to set
     */
    public void setExecmsg(String execmsg) {
        this.execmsg = execmsg;
    }
    /**
     * @return the validatestatus
     */
    public String getValidatestatus() {
        return validatestatus;
    }
    /**
     * @param validatestatus the validatestatus to set
     */
    public void setValidatestatus(String validatestatus) {
        this.validatestatus = validatestatus;
    }
    /**
     * @return the payserialno
     */
    public String getPayserialno() {
        return payserialno;
    }
    /**
     * @param payserialno the payserialno to set
     */
    public void setPayserialno(String payserialno) {
        this.payserialno = payserialno;
    }
    /**
     * @return the resv
     */
    public String getResv() {
        return resv;
    }
    /**
     * @param resv the resv to set
     */
    public void setResv(String resv) {
        this.resv = resv;
    }

    
    
}
