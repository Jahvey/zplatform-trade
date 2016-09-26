/* 
 * WhiteListResultBean.java  
 * 
 * version TODO
 *
 * 2015年11月25日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.cmbc.bean;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月25日 下午1:47:56
 * @since 
 */
@XStreamAlias("Ans")
public class WhiteListResultBean implements Serializable{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3906773271736729258L;
    @XStreamAlias("Version")
    private String version;//版本号 
    @XStreamAlias("TransDate")
    private String transdate;//交易日期
    @XStreamAlias("TransTime")
    private String transtime;//交易时间
    @XStreamAlias("ReqSerialNo")
    private String reqserialno;//请求流水号   
    @XStreamAlias("ExecType")
    private String exectype;//响应类型
    @XStreamAlias("ExecCode")
    private String execcode;//响应代码
    @XStreamAlias("ExecMsg")
    private String execmsg;//响应描述
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
     * @return the transdate
     */
    public String getTransdate() {
        return transdate;
    }
    /**
     * @param transdate the transdate to set
     */
    public void setTransdate(String transdate) {
        this.transdate = transdate;
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
    
    
}
