/* 
 * RealTimeQueryResultBean.java  
 * 
 * version TODO
 *
 * 2015年11月10日 
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
 * @date 2015年11月10日 上午9:30:37
 * @since
 */
@XStreamAlias("TRAN_RESP")
public class RealTimeQueryResultBean implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3489688559526869517L;
    @XStreamAlias("RESP_TYPE")
    private String respType;// 应答码类型
    @XStreamAlias("RESP_CODE")
    private String respCode;// 应答码
    @XStreamAlias("RESP_MSG")
    private String respMsg;// 应答描述
    @XStreamAlias("COMPANY_ID")
    private String companyId;// 合作机构id
    @XStreamAlias("MCHNT_CD")
    private String mchntCd;// 商户编号
    @XStreamAlias("TRAN_DATE")
    private String tranDate;// 交易日期
    @XStreamAlias("TRAN_TIME")
    private String tranTime;// 交易时间
    @XStreamAlias("TRAN_ID")
    private String tranId;// 渠道流水号
    @XStreamAlias("ORI_TRAN_DATE")
    private String oriTranDate;// 原交易日期
    @XStreamAlias("ORI_TRAN_ID")
    private String oriTranId;// 原渠道流水号
    @XStreamAlias("ORI_BANK_TRAN_ID")
    private String oriBankTranId;// 银行处理流水号
    @XStreamAlias("ORI_BANK_TRAN_DATE")
    private String oriBankTranDate;// 银行交易日期
    @XStreamAlias("ORI_RESP_TYPE")
    private String oriRespType;// 原应答码类型
    @XStreamAlias("ORI_RESP_CODE")
    private String oriRespCode;// 原交易应答码
    @XStreamAlias("ORI_RESP_MSG")
    private String oriRespMsg;// 原交易应答描述
    @XStreamAlias("RESV")
    private String resv;// 备用
    
    
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
     * @return the companyId
     */
    public String getCompanyId() {
        return companyId;
    }


    /**
     * @param companyId the companyId to set
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }


    /**
     * @return the mchntCd
     */
    public String getMchntCd() {
        return mchntCd;
    }


    /**
     * @param mchntCd the mchntCd to set
     */
    public void setMchntCd(String mchntCd) {
        this.mchntCd = mchntCd;
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
     * @return the oriTranDate
     */
    public String getOriTranDate() {
        return oriTranDate;
    }


    /**
     * @param oriTranDate the oriTranDate to set
     */
    public void setOriTranDate(String oriTranDate) {
        this.oriTranDate = oriTranDate;
    }


    /**
     * @return the oriTranId
     */
    public String getOriTranId() {
        return oriTranId;
    }


    /**
     * @param oriTranId the oriTranId to set
     */
    public void setOriTranId(String oriTranId) {
        this.oriTranId = oriTranId;
    }


    /**
     * @return the oriBankTranId
     */
    public String getOriBankTranId() {
        return oriBankTranId;
    }


    /**
     * @param oriBankTranId the oriBankTranId to set
     */
    public void setOriBankTranId(String oriBankTranId) {
        this.oriBankTranId = oriBankTranId;
    }


    /**
     * @return the oriBankTranDate
     */
    public String getOriBankTranDate() {
        return oriBankTranDate;
    }


    /**
     * @param oriBankTranDate the oriBankTranDate to set
     */
    public void setOriBankTranDate(String oriBankTranDate) {
        this.oriBankTranDate = oriBankTranDate;
    }


    /**
     * @return the oriRespType
     */
    public String getOriRespType() {
        return oriRespType;
    }


    /**
     * @param oriRespType the oriRespType to set
     */
    public void setOriRespType(String oriRespType) {
        this.oriRespType = oriRespType;
    }


    /**
     * @return the oriRespCode
     */
    public String getOriRespCode() {
        return oriRespCode;
    }


    /**
     * @param oriRespCode the oriRespCode to set
     */
    public void setOriRespCode(String oriRespCode) {
        this.oriRespCode = oriRespCode;
    }


    /**
     * @return the oriRespMsg
     */
    public String getOriRespMsg() {
        return oriRespMsg;
    }


    /**
     * @param oriRespMsg the oriRespMsg to set
     */
    public void setOriRespMsg(String oriRespMsg) {
        this.oriRespMsg = oriRespMsg;
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


    /**
     * @param respType
     * @param respCode
     * @param respMsg
     * @param companyId
     * @param mchntCd
     * @param tranDate
     * @param tranTime
     * @param tranId
     * @param oriTranDate
     * @param oriTranId
     * @param oriBankTranId
     * @param oriBankTranDate
     * @param oriRespType
     * @param oriRespCode
     * @param oriRespMsg
     * @param resv
     */
    public RealTimeQueryResultBean(String respType, String respCode,
            String respMsg, String companyId, String mchntCd, String tranDate,
            String tranTime, String tranId, String oriTranDate,
            String oriTranId, String oriBankTranId, String oriBankTranDate,
            String oriRespType, String oriRespCode, String oriRespMsg,
            String resv) {
        super();
        this.respType = respType;
        this.respCode = respCode;
        this.respMsg = respMsg;
        this.companyId = companyId;
        this.mchntCd = mchntCd;
        this.tranDate = tranDate;
        this.tranTime = tranTime;
        this.tranId = tranId;
        this.oriTranDate = oriTranDate;
        this.oriTranId = oriTranId;
        this.oriBankTranId = oriBankTranId;
        this.oriBankTranDate = oriBankTranDate;
        this.oriRespType = oriRespType;
        this.oriRespCode = oriRespCode;
        this.oriRespMsg = oriRespMsg;
        this.resv = resv;
    }


    /**
     * 
     */
    public RealTimeQueryResultBean() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
}
