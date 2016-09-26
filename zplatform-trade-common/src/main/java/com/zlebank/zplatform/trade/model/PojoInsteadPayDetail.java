/* 
 * PojoInsteadPayDetail.java  
 * 
 * version TODO
 *
 * 2015年11月24日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * 代付批次明细表
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月24日 下午12:04:13
 * @since 
 */
@Entity
@Table(name="T_INSTEAD_PAY_DETAIL")
public class PojoInsteadPayDetail{

    /**标识**/
    private Long id;
    /**商户代码**/
    private String merId;
    /**商户订单号**/
    private String orderId;
    /**（人民币）156**/
    private String currencyCode;
    /**单笔金额(以分为单位，最长12位)**/
    private Long amt;
    /**产品类型(固定：000001)**/
    private String bizType;
    /**账号类型01:银行卡;02:存折**/
    private String accType;
    /**账号**/
    private String accNo;
    /**户名**/
    private String accName;
    /**开户行代码帐号类型取值为“02”时不能为空**/
    private String bankCode;
    /**开户行省**/
    private String issInsProvince;
    /**开户行市**/
    private String issInsCity;
    /**开户行名称**/
    private String issInsName;
    /**证件类型**/
    private String certifTp;
    /**证件号码**/
    private String certifId;
    /**手机号**/
    private Long phoneNo;
    /**账单类型**/
    private String billType;
    /**附言**/
    private String notes;
    /**状态00：已处理01**/
    private String status;
    /**初审人**/
    private Long stexauser;
    /**初审时间**/
    private Date stexatime;
    /**初审意见**/
    private String stexaopt;
    /**复核人**/
    private Long cvlexauser;
    /**复核时间**/
    private Date cvlexatime;
    /**复核意见**/
    private String cvlexaopt;
    /**通道代码**/
    private String channelCode;
    /**划拨批次文件号**/
    private String batchFileNo;
    /**划拨响应代码**/
    private String respCode;
    /**划拨响应信息**/
    private String respMsg;
    /**备注**/
    private String remarks;
    /**写入人**/
    private Long inuser;
    /**写入时间**/
    private Date intime;
    /**交易流水号**/
    private String  txnseqno;
    /**手续费**/
    private Long  txnfee;
    /**代付明细流水号**/
    private String insteadPayDataSeqNo;
    /**申请时间**/
    private Date applyTime;
    /**划拨流水ID**/
    private Long tranDataId;

    private  PojoInsteadPayBatch insteadPayBatch;

    @ManyToOne
    @JoinColumn(name = "BATCH_ID")
    public PojoInsteadPayBatch getInsteadPayBatch() {
        return insteadPayBatch;
    }
    public void setInsteadPayBatch(PojoInsteadPayBatch insteadPayBatch) {
        this.insteadPayBatch = insteadPayBatch;
    }
    @GenericGenerator(name = "id_gen", strategy = "enhanced-table", parameters = {
            @Parameter(name = "table_name", value = "T_C_PRIMAY_KEY"),
            @Parameter(name = "value_column_name", value = "NEXT_ID"),
            @Parameter(name = "segment_column_name", value = "KEY_NAME"),
            @Parameter(name = "segment_value", value = "INSTEAD_PAY_DETAIL_ID"),
            @Parameter(name = "increment_size", value = "1"),
            @Parameter(name = "optimizer", value = "pooled-lo") })
    @Id
    @GeneratedValue(generator = "id_gen")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    } 
    @Column(name = "MER_ID")
    public String getMerId() {
        return merId;
    }
    public void setMerId(String merId) {
        this.merId = merId;
    }
    @Column(name = "ORDER_ID")
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    @Column(name = "CURRENCY_CODE")
    public String getCurrencyCode() {
        return currencyCode;
    }
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
    @Column(name = "AMT")
    public Long getAmt() {
        return amt;
    }
    public void setAmt(Long amt) {
        this.amt = amt;
    }
    @Column(name = "BIZ_TYPE")
    public String getBizType() {
        return bizType;
    }
    public void setBizType(String bizType) {
        this.bizType = bizType;
    }
    @Column(name = "ACC_TYPE")
    public String getAccType() {
        return accType;
    }
    public void setAccType(String accType) {
        this.accType = accType;
    }
    @Column(name = "ACC_NO")
    public String getAccNo() {
        return accNo;
    }
    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }
    @Column(name = "ACC_NAME")
    public String getAccName() {
        return accName;
    }
    public void setAccName(String accName) {
        this.accName = accName;
    }
    @Column(name = "BANK_CODE")
    public String getBankCode() {
        return bankCode;
    }
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
    @Column(name = "ISS_INS_PROVINCE")
    public String getIssInsProvince() {
        return issInsProvince;
    }
    public void setIssInsProvince(String issInsProvince) {
        this.issInsProvince = issInsProvince;
    }
    @Column(name = "ISS_INS_CITY")
    public String getIssInsCity() {
        return issInsCity;
    }
    public void setIssInsCity(String issInsCity) {
        this.issInsCity = issInsCity;
    }
    @Column(name = "ISS_INS_NAME")
    public String getIssInsName() {
        return issInsName;
    }
    public void setIssInsName(String issInsName) {
        this.issInsName = issInsName;
    }
    @Column(name = "CERTIF_TP")
    public String getCertifTp() {
        return certifTp;
    }
    public void setCertifTp(String certifTp) {
        this.certifTp = certifTp;
    }
    @Column(name = "CERTIF_ID")
    public String getCertifId() {
        return certifId;
    }
    public void setCertifId(String certifId) {
        this.certifId = certifId;
    }
    @Column(name = "PHONE_NO")
    public Long getPhoneNo() {
        return phoneNo;
    }
    public void setPhoneNo(Long phoneNo) {
        this.phoneNo = phoneNo;
    }
    @Column(name = "BILL_TYPE")
    public String getBillType() {
        return billType;
    }
    public void setBillType(String billType) {
        this.billType = billType;
    }
    @Column(name = "NOTES")
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    @Column(name = "STEXAUSER")
    public Long getStexauser() {
        return stexauser;
    }
    public void setStexauser(Long stexauser) {
        this.stexauser = stexauser;
    }
    @Generated(GenerationTime.INSERT)
    @Column(name = "STEXATIME")
    public Date getStexatime() {
        return stexatime;
    }
    public void setStexatime(Date stexatime) {
        this.stexatime = stexatime;
    }
    @Column(name = "STEXAOPT")
    public String getStexaopt() {
        return stexaopt;
    }
    public void setStexaopt(String stexaopt) {
        this.stexaopt = stexaopt;
    }
    @Column(name = "CVLEXAUSER")
    public Long getCvlexauser() {
        return cvlexauser;
    }
    public void setCvlexauser(Long cvlexauser) {
        this.cvlexauser = cvlexauser;
    }
    @Generated(GenerationTime.INSERT)
    @Column(name = "CVLEXATIME")
    public Date getCvlexatime() {
        return cvlexatime;
    }
    public void setCvlexatime(Date cvlexatime) {
        this.cvlexatime = cvlexatime;
    }
    @Column(name = "CVLEXAOPT")
    public String getCvlexaopt() {
        return cvlexaopt;
    }
    public void setCvlexaopt(String cvlexaopt) {
        this.cvlexaopt = cvlexaopt;
    }
    @Column(name = "CHANNEL_CODE")
    public String getChannelCode() {
        return channelCode;
    }
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }
    @Column(name = "BATCH_FILE_NO")
    public String getBatchFileNo() {
        return batchFileNo;
    }
    public void setBatchFileNo(String batchFileNo) {
        this.batchFileNo = batchFileNo;
    }
    @Column(name = "RESP_CODE")
    public String getRespCode() {
        return respCode;
    }
    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }
    @Column(name = "RESP_MSG")
    public String getRespMsg() {
        return respMsg;
    }
    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }
    @Column(name = "REMARKS")
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    @Column(name = "INUSER")
    public Long getInuser() {
        return inuser;
    }
    public void setInuser(Long inuser) {
        this.inuser = inuser;
    }
    @Generated(GenerationTime.INSERT)
    @Column(name = "INTIME")
    public Date getIntime() {
        return intime;
    }
    public void setIntime(Date intime) {
        this.intime = intime;
    }
    @Column(name = "TXNSEQNO")
    public String getTxnseqno() {
        return txnseqno;
    }
    public void setTxnseqno(String txnseqno) {
        this.txnseqno = txnseqno;
    }
    @Column(name = "TXNFEE")
    public Long getTxnfee() {
        return txnfee;
    }
    public void setTxnfee(Long txnfee) {
        this.txnfee = txnfee;
    }
    @Column(name = "APPLY_TIME")
    public Date getApplyTime() {
        return applyTime;
    }
    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }
    @Column(name = "INSTEAD_PAY_DATA_SEQ_NO")
    public String getInsteadPayDataSeqNo() {
        return insteadPayDataSeqNo;
    }
    public void setInsteadPayDataSeqNo(String insteadPayDataSeqNo) {
        this.insteadPayDataSeqNo = insteadPayDataSeqNo;
    }
    @Column(name = "TRAN_DATA_ID")
    public Long getTranDataId() {
        return tranDataId;
    }
    public void setTranDataId(Long tranDataId) {
        this.tranDataId = tranDataId;
    }
}
