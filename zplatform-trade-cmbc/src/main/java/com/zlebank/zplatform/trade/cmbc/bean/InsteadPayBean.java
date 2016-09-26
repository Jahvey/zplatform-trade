/* 
 * InsteadPayBean.java  
 * 
 * version TODO
 *
 * 2015年11月3日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.cmbc.bean;

import java.io.Serializable;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月3日 上午11:33:40
 * @since 
 */
public class InsteadPayBean implements Serializable{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1189282518156050109L;
    /**第三方流水号*/
    private String tranId;
    /**账号*/
    private String accNo;
    /**户名*/
    private String accName;
    /**支付行号*/
    private String bankType;
    /**开户行名称*/
    private String bankName;
    /**金额*/
    private Long transAmt;
    /**摘要*/
    private String remark;
    /**备注*/
    private String resv;
    
    
    
    
    
    /**
     * 
     */
    public InsteadPayBean() {
    }
    public InsteadPayBean(String tranId) {
        this.tranId = tranId;
        this.accNo = "6226220506989921";
        this.accName = "郭佳";
        this.bankType = "";
        this.bankName = "";
        this.transAmt = 10L;
        this.remark = "";
        this.resv = "";
    }
    /**
     * @param tranId
     * @param accNo
     * @param accName
     * @param bankType
     * @param bankName
     * @param transAmt
     * @param remark
     * @param resv
     */
    public InsteadPayBean(String tranId, String accNo, String accName,
            String bankType, String bankName, Long transAmt, String remark,
            String resv) {
        super();
        this.tranId = tranId;
        this.accNo = accNo;
        this.accName = accName;
        this.bankType = bankType;
        this.bankName = bankName;
        this.transAmt = transAmt;
        this.remark = remark;
        this.resv = resv;
    }
    /**
     * @return the tranId
     */
    public String getTranId() {
        return tranId;
    }
    /**
     * @param tranId the tranId to set
     */
    public void setTranId(String tranId) {
        this.tranId = tranId;
    }
    /**
     * @return the accNo
     */
    public String getAccNo() {
        return accNo;
    }
    /**
     * @param accNo the accNo to set
     */
    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }
    /**
     * @return the accName
     */
    public String getAccName() {
        return accName;
    }
    /**
     * @param accName the accName to set
     */
    public void setAccName(String accName) {
        this.accName = accName;
    }
    /**
     * @return the bankType
     */
    public String getBankType() {
        return bankType;
    }
    /**
     * @param bankType the bankType to set
     */
    public void setBankType(String bankType) {
        this.bankType = bankType;
    }
    /**
     * @return the bankName
     */
    public String getBankName() {
        return bankName;
    }
    /**
     * @param bankName the bankName to set
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    /**
     * @return the transAmt
     */
    public Long getTransAmt() {
        return transAmt;
    }
    /**
     * @param transAmt the transAmt to set
     */
    public void setTransAmt(Long transAmt) {
        this.transAmt = transAmt;
    }
    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }
    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
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
