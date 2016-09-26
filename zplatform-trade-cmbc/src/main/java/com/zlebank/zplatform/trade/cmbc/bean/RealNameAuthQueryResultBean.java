/* 
 * RealNameAuthQueryBean.java  
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
 * @date 2015年11月25日 下午3:24:14
 * @since 
 */
@XStreamAlias("Ans")
public class RealNameAuthQueryResultBean implements Serializable{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3874847700977242862L;
    public static final String XMLHEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    @XStreamAlias("Version")
    private String version ;//版本号 
    @XStreamAlias("SettDate")
    private String settdate;//清算日期
    @XStreamAlias("TransTime")
    private String transtime  ;//交易时间
    @XStreamAlias("ReqSerialNo")
    private String reqserialno;//请求流水号
    @XStreamAlias("ExecType")
    private String exectype ;//响应类型
    @XStreamAlias("ExecCode")
    private String execcode ;//响应代码
    @XStreamAlias("ExecMsg")
    private String execmsg  ;//响应描述
    @XStreamAlias("ValidateNo")
    private String validateno  ;//认证流水号   
    @XStreamAlias("ValidateStatus")
    private String validatestatus   ;// 认证状态
    @XStreamAlias("MerId")
    private String merid ;// 商户号 
    @XStreamAlias("OriReqSerialNo")
    private String orireqserialno;//原交易流水号  
    @XStreamAlias("OriSettDate")
    private String orisettdate;//原交易清算日期 
    @XStreamAlias("OriTransTime")
    private String oritranstime;//原交易处理时间 
    @XStreamAlias("OriPaySerialNo")
    private String oripayserialno ;//原交易平台流水号
    @XStreamAlias("OriExecType")
    private String oriexectype ;//原交易响应类型 
    @XStreamAlias("OriExecCode")
    private String oriexeccode  ;//原交易响应代码 
    @XStreamAlias("OriExecMsg")
    private String oriexecmsg ;//原交易响应描述 
    @XStreamAlias("Resv")
    private String resv ;//备用域 
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
     * @return the validateno
     */
    public String getValidateno() {
        return validateno;
    }
    /**
     * @param validateno the validateno to set
     */
    public void setValidateno(String validateno) {
        this.validateno = validateno;
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
     * @return the merid
     */
    public String getMerid() {
        return merid;
    }
    /**
     * @param merid the merid to set
     */
    public void setMerid(String merid) {
        this.merid = merid;
    }
    /**
     * @return the orireqserialno
     */
    public String getOrireqserialno() {
        return orireqserialno;
    }
    /**
     * @param orireqserialno the orireqserialno to set
     */
    public void setOrireqserialno(String orireqserialno) {
        this.orireqserialno = orireqserialno;
    }
    /**
     * @return the orisettdate
     */
    public String getOrisettdate() {
        return orisettdate;
    }
    /**
     * @param orisettdate the orisettdate to set
     */
    public void setOrisettdate(String orisettdate) {
        this.orisettdate = orisettdate;
    }
    /**
     * @return the oritranstime
     */
    public String getOritranstime() {
        return oritranstime;
    }
    /**
     * @param oritranstime the oritranstime to set
     */
    public void setOritranstime(String oritranstime) {
        this.oritranstime = oritranstime;
    }
    /**
     * @return the oripayserialno
     */
    public String getOripayserialno() {
        return oripayserialno;
    }
    /**
     * @param oripayserialno the oripayserialno to set
     */
    public void setOripayserialno(String oripayserialno) {
        this.oripayserialno = oripayserialno;
    }
    /**
     * @return the oriexectype
     */
    public String getOriexectype() {
        return oriexectype;
    }
    /**
     * @param oriexectype the oriexectype to set
     */
    public void setOriexectype(String oriexectype) {
        this.oriexectype = oriexectype;
    }
    /**
     * @return the oriexeccode
     */
    public String getOriexeccode() {
        return oriexeccode;
    }
    /**
     * @param oriexeccode the oriexeccode to set
     */
    public void setOriexeccode(String oriexeccode) {
        this.oriexeccode = oriexeccode;
    }
    /**
     * @return the oriexecmsg
     */
    public String getOriexecmsg() {
        return oriexecmsg;
    }
    /**
     * @param oriexecmsg the oriexecmsg to set
     */
    public void setOriexecmsg(String oriexecmsg) {
        this.oriexecmsg = oriexecmsg;
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
