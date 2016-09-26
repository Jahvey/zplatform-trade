/* 
 * ReexchangeBean.java  
 * 
 * version TODO
 *
 * 2015年11月10日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean.cmbc;

import java.io.Serializable;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月10日 下午1:48:46
 * @since 
 */
public class ReexchangeBean implements Serializable{
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6975751455720554929L;
	/** 报盘日期*/
    private String  reexDate;
    /** 报盘批次*/
    private String  batchNo;
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
    /**退汇日期 */
    private String tranDate;
    /**
     * @return the reexDate
     */
    public String getReexDate() {
        return reexDate;
    }
    /**
     * @param reexDate the reexDate to set
     */
    public void setReexDate(String reexDate) {
        this.reexDate = reexDate;
    }
    /**
     * @return the batchNo
     */
    public String getBatchNo() {
        return batchNo;
    }
    /**
     * @param batchNo the batchNo to set
     */
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
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
     * 
     */
    public ReexchangeBean() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * @param reexDate
     * @param batchNo
     * @param tranId
     * @param bankTranId
     * @param accNo
     * @param accName
     * @param transAmt
     * @param respType
     * @param respCode
     * @param respMsg
     * @param tranDate
     */
    public ReexchangeBean(String reexDate, String batchNo, String tranId,
            String bankTranId, String accNo, String accName, Long transAmt,
            String respType, String respCode, String respMsg, String tranDate) {
        super();
        this.reexDate = reexDate;
        this.batchNo = batchNo;
        this.tranId = tranId;
        this.bankTranId = bankTranId;
        this.accNo = accNo;
        this.accName = accName;
        this.transAmt = transAmt;
        this.respType = respType;
        this.respCode = respCode;
        this.respMsg = respMsg;
        this.tranDate = tranDate;
    }
    public ReexchangeBean(String[] body) {
        super();
        this.reexDate = body[0];
        this.batchNo = body[1];
        this.tranId = body[2];
        this.bankTranId = body[3];
        this.accNo = body[4];
        this.accName = body[5];
        this.transAmt = Long.valueOf(body[6]);
        this.respType = body[7];
        this.respCode = body[8];
        this.respMsg = body[9];
        this.tranDate = body[10];
    }
    
}
