/* 
 * RealTimePayResultBean.java  
 * 
 * version TODO
 *
 * 2015年11月9日 
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
 * @date 2015年11月9日 下午2:01:33
 * @since
 */
@XStreamAlias("TRAN_RESP")
public class RealTimePayResultBean {

    @XStreamAlias("RESP_TYPE")
    private String resp_type;// 应答码类型
    @XStreamAlias("RESP_CODE")
    private String resp_code;// 应答码
    @XStreamAlias("RESP_MSG")
    private String resp_msg;// 应答描述
    @XStreamAlias("COMPANY_ID")
    private String company_id;// 合作机构id
    @XStreamAlias("MCHNT_CD")
    private String mchnt_cd;// 商户编号
    @XStreamAlias("TRAN_DATE")
    private String tran_date;// 交易日期
    @XStreamAlias("TRAN_TIME")
    private String tran_time;// 交易时间
    @XStreamAlias("TRAN_ID")
    private String tran_id;// 渠道交易流水号
    @XStreamAlias("BANK_TRAN_ID")
    private String bank_tran_id;// 银行处理流水号
    @XStreamAlias("BANK_TRAN_DATE")
    private String bank_tran_date;// 银行交易日期
    @XStreamAlias("RESV")
    private String resv;// 备用
    /**
     * @return the resp_type
     */
    public String getResp_type() {
        return resp_type;
    }
    /**
     * @param resp_type the resp_type to set
     */
    public void setResp_type(String resp_type) {
        this.resp_type = resp_type;
    }
    /**
     * @return the resp_code
     */
    public String getResp_code() {
        return resp_code;
    }
    /**
     * @param resp_code the resp_code to set
     */
    public void setResp_code(String resp_code) {
        this.resp_code = resp_code;
    }
    /**
     * @return the resp_msg
     */
    public String getResp_msg() {
        return resp_msg;
    }
    /**
     * @param resp_msg the resp_msg to set
     */
    public void setResp_msg(String resp_msg) {
        this.resp_msg = resp_msg;
    }
    /**
     * @return the company_id
     */
    public String getCompany_id() {
        return company_id;
    }
    /**
     * @param company_id the company_id to set
     */
    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }
    /**
     * @return the mchnt_cd
     */
    public String getMchnt_cd() {
        return mchnt_cd;
    }
    /**
     * @param mchnt_cd the mchnt_cd to set
     */
    public void setMchnt_cd(String mchnt_cd) {
        this.mchnt_cd = mchnt_cd;
    }
    /**
     * @return the tran_date
     */
    public String getTran_date() {
        return tran_date;
    }
    /**
     * @param tran_date the tran_date to set
     */
    public void setTran_date(String tran_date) {
        this.tran_date = tran_date;
    }
    /**
     * @return the tran_time
     */
    public String getTran_time() {
        return tran_time;
    }
    /**
     * @param tran_time the tran_time to set
     */
    public void setTran_time(String tran_time) {
        this.tran_time = tran_time;
    }
    /**
     * @return the tran_id
     */
    public String getTran_id() {
        return tran_id;
    }
    /**
     * @param tran_id the tran_id to set
     */
    public void setTran_id(String tran_id) {
        this.tran_id = tran_id;
    }
    /**
     * @return the bank_tran_id
     */
    public String getBank_tran_id() {
        return bank_tran_id;
    }
    /**
     * @param bank_tran_id the bank_tran_id to set
     */
    public void setBank_tran_id(String bank_tran_id) {
        this.bank_tran_id = bank_tran_id;
    }
    /**
     * @return the bank_tran_date
     */
    public String getBank_tran_date() {
        return bank_tran_date;
    }
    /**
     * @param bank_tran_date the bank_tran_date to set
     */
    public void setBank_tran_date(String bank_tran_date) {
        this.bank_tran_date = bank_tran_date;
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
     * @param resp_type
     * @param resp_code
     * @param resp_msg
     * @param company_id
     * @param mchnt_cd
     * @param tran_date
     * @param tran_time
     * @param tran_id
     * @param bank_tran_id
     * @param bank_tran_date
     * @param resv
     */
    public RealTimePayResultBean(String resp_type, String resp_code,
            String resp_msg, String company_id, String mchnt_cd,
            String tran_date, String tran_time, String tran_id,
            String bank_tran_id, String bank_tran_date, String resv) {
        super();
        this.resp_type = resp_type;
        this.resp_code = resp_code;
        this.resp_msg = resp_msg;
        this.company_id = company_id;
        this.mchnt_cd = mchnt_cd;
        this.tran_date = tran_date;
        this.tran_time = tran_time;
        this.tran_id = tran_id;
        this.bank_tran_id = bank_tran_id;
        this.bank_tran_date = bank_tran_date;
        this.resv = resv;
    }
    /**
     * 
     */
    public RealTimePayResultBean() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    
    
}
