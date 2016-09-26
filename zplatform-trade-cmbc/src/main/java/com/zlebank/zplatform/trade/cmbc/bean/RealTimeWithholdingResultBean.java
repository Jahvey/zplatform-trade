/* 
 * RealTimeWithholdingBean.java  
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
 * @date 2015年11月25日 上午9:52:25
 * @since
 */
@XStreamAlias("Ans")
public class RealTimeWithholdingResultBean implements Serializable {
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5053851736070620156L;
	@XStreamAlias("Version")
    private String version;// 版本号
    @XStreamAlias("SettDate")
    private String settdate;// 清算日期
    @XStreamAlias("TransTime")
    private String transtime;// 交易时间
    @XStreamAlias("ReqSerialNo")
    private String reqserialno;// 请求流水号
    @XStreamAlias("ExecType")
    private String exectype;// 响应类型
    @XStreamAlias("ExecCode")
    private String execcode;// 响应代码
    @XStreamAlias("ExecMsg")
    private String execmsg;// 响应描述
    @XStreamAlias("MerId")
    private String merid;// 商户号
    @XStreamAlias("PaySerialNo")
    private String payserialno;// 平台处理流水号
    @XStreamAlias("Resv")
    private String resv;// 备用
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
