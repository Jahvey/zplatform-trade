/* 
 * InsteadPayResultBean.java  
 * 
 * version TODO
 *
 * 2015年11月4日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.cmbc.bean;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月4日 下午1:48:37
 * @since
 */
public class InsteadPayResultBean {
    /** 第三方流水号 */
    private String tranId;
    /** 银行流水号 */
    private String bankTranId;
    /** 账号 */
    private String accNo;
    /** 户名 */
    private String accName;
    /** 金额 */
    private Long transAmt;
    /** 付款结果 */
    private String respType;
    /** 失败返回码 */
    private String respCode;
    /** 失败原因 */
    private String respMsg;
    /** 付款日期 */
    private String tranDate;
    /** 付款时间 */
    private String tranTime;
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
     * @return the bankTranId
     */
    public String getBankTranId() {
        return bankTranId;
    }
    /**
     * @param bankTranId the bankTranId to set
     */
    public void setBankTranId(String bankTranId) {
        this.bankTranId = bankTranId;
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
     * @return the respType
     */
    public String getRespType() {
        return respType;
    }
    /**
     * @param respType the respType to set
     */
    public void setRespType(String respType) {
        this.respType = respType;
    }
    /**
     * @return the respCode
     */
    public String getRespCode() {
        return respCode;
    }
    /**
     * @param respCode the respCode to set
     */
    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }
    /**
     * @return the respMsg
     */
    public String getRespMsg() {
        return respMsg;
    }
    /**
     * @param respMsg the respMsg to set
     */
    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }
    /**
     * @return the tranDate
     */
    public String getTranDate() {
        return tranDate;
    }
    /**
     * @param tranDate the tranDate to set
     */
    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }
    /**
     * @return the tranTime
     */
    public String getTranTime() {
        return tranTime;
    }
    /**
     * @param tranTime the tranTime to set
     */
    public void setTranTime(String tranTime) {
        this.tranTime = tranTime;
    }
    /**
     * @param tranId
     * @param bankTranId
     * @param accNo
     * @param accName
     * @param transAmt
     * @param respType
     * @param respCode
     * @param respMsg
     * @param tranDate
     * @param tranTime
     */
    public InsteadPayResultBean(String tranId, String bankTranId, String accNo,
            String accName, Long transAmt, String respType, String respCode,
            String respMsg, String tranDate, String tranTime) {
        super();
        this.tranId = tranId;
        this.bankTranId = bankTranId;
        this.accNo = accNo;
        this.accName = accName;
        this.transAmt = transAmt;
        this.respType = respType;
        this.respCode = respCode;
        this.respMsg = respMsg;
        this.tranDate = tranDate;
        this.tranTime = tranTime;
    }
    
    
    public InsteadPayResultBean(String[] body) {
         //0                 1              2     3      4      5         6              7            8           9
        //第三方流水号|银行流水号|帐号|户名|金额|付款结果|失败返回码|失败原因|付款日期|付款时间
        this.tranId = body[0];
        this.bankTranId = body[1];
        this.accNo = body[2];
        this.accName = body[3];
        this.transAmt = Long.valueOf(body[4]);
        this.respType = body[5];
        this.respCode = body[6];
        this.respMsg = body[7];
        this.tranDate = body[8];
        this.tranTime = body[9];
    }
}
