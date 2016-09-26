/* 
 * AppPartyBean.java  
 * 
 * version TODO
 *
 * 2015年9月7日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean;

import java.io.Serializable;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月7日 下午12:16:55
 * @since 
 */
public class AppPartyBean implements Serializable{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -7968534210301930150L;
    
    private String appordno;//应用定单号
    private String appinst;//应用所属机构
    private String appordcommitime;//应用定单提交时间
    private String appordfintime;//应用定单完成时间
    private String txnseqno;
    private String nextStep;
    /**
     * @return the appordno
     */
    public String getAppordno() {
        return appordno;
    }
    /**
     * @param appordno the appordno to set
     */
    public void setAppordno(String appordno) {
        this.appordno = appordno;
    }
    /**
     * @return the appinst
     */
    public String getAppinst() {
        return appinst;
    }
    /**
     * @param appinst the appinst to set
     */
    public void setAppinst(String appinst) {
        this.appinst = appinst;
    }
    /**
     * @return the appordcommitime
     */
    public String getAppordcommitime() {
        return appordcommitime;
    }
    /**
     * @param appordcommitime the appordcommitime to set
     */
    public void setAppordcommitime(String appordcommitime) {
        this.appordcommitime = appordcommitime;
    }
    /**
     * @return the appordfintime
     */
    public String getAppordfintime() {
        return appordfintime;
    }
    /**
     * @param appordfintime the appordfintime to set
     */
    public void setAppordfintime(String appordfintime) {
        this.appordfintime = appordfintime;
    }
    /**
     * @return the txnseqno
     */
    public String getTxnseqno() {
        return txnseqno;
    }
    /**
     * @param txnseqno the txnseqno to set
     */
    public void setTxnseqno(String txnseqno) {
        this.txnseqno = txnseqno;
    }
    /**
     * @param appordno
     * @param appinst
     * @param appordcommitime
     * @param appordfintime
     * @param txnseqno
     */
    public AppPartyBean(String appordno, String appinst,
            String appordcommitime, String appordfintime, String txnseqno,String nextStep) {
        super();
        this.appordno = appordno;
        this.appinst = appinst;
        this.appordcommitime = appordcommitime;
        this.appordfintime = appordfintime;
        this.txnseqno = txnseqno;
        this.nextStep = nextStep;
    }
    /**
     * @return the nextStep
     */
    public String getNextStep() {
        return nextStep;
    }
    /**
     * @param nextStep the nextStep to set
     */
    public void setNextStep(String nextStep) {
        this.nextStep = nextStep;
    }
    
    

}
