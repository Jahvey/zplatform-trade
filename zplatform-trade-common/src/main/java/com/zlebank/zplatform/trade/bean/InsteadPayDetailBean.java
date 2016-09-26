/* 
 * InsteadPayDetailBean.java  
 * 
 * version TODO
 *
 * 2015年12月21日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean;

import java.math.BigDecimal;
import java.util.Date;

import com.zlebank.zplatform.commons.bean.Bean;

/**
 * Class Description
 *
 * @author yangpeng
 * @version
 * @date 2015年12月21日 下午7:55:43
 * @since 
 */
public class InsteadPayDetailBean implements Bean{
    /**标识**/
    private Long id;
    /**批处理的ID（注意：不是报文中的批处理号）**/
    private Long batchId;
    /**商户代码**/
    private String merId;
    /**商户订单号**/
    private String orderId;
    /**（人民币）156**/
    private String currencyCode;
    /**单笔金额(以分为单位，最长12位)**/
    private String amt;
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
    private BigDecimal  txnfee;
    /**代付明细流水号**/
    private String insteadPayDataSeqNo;
    /**申请时间**/
    private Date applyTime;
    /**划拨流水ID**/
    private Long tranDataId;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getBatchId() {
        return batchId;
    }
    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }
    public String getMerId() {
        return merId;
    }
    public void setMerId(String merId) {
        this.merId = merId;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getCurrencyCode() {
        return currencyCode;
    }
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
    public String getAmt() {
        return amt;
    }
    public void setAmt(String amt) {
        this.amt = amt;
    }
    public String getBizType() {
        return bizType;
    }
    public void setBizType(String bizType) {
        this.bizType = bizType;
    }
    public String getAccType() {
        return accType;
    }
    public void setAccType(String accType) {
        this.accType = accType;
    }
    public String getAccNo() {
        return accNo;
    }
    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }
    public String getAccName() {
        return accName;
    }
    public void setAccName(String accName) {
        this.accName = accName;
    }
    public String getBankCode() {
        return bankCode;
    }
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
    public String getIssInsProvince() {
        return issInsProvince;
    }
    public void setIssInsProvince(String issInsProvince) {
        this.issInsProvince = issInsProvince;
    }
    public String getIssInsCity() {
        return issInsCity;
    }
    public void setIssInsCity(String issInsCity) {
        this.issInsCity = issInsCity;
    }
    public String getIssInsName() {
        return issInsName;
    }
    public void setIssInsName(String issInsName) {
        this.issInsName = issInsName;
    }
    public String getCertifTp() {
        return certifTp;
    }
    public void setCertifTp(String certifTp) {
        this.certifTp = certifTp;
    }
    public String getCertifId() {
        return certifId;
    }
    public void setCertifId(String certifId) {
        this.certifId = certifId;
    }
    public Long getPhoneNo() {
        return phoneNo;
    }
    public void setPhoneNo(Long phoneNo) {
        this.phoneNo = phoneNo;
    }
    public String getBillType() {
        return billType;
    }
    public void setBillType(String billType) {
        this.billType = billType;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Long getStexauser() {
        return stexauser;
    }
    public void setStexauser(Long stexauser) {
        this.stexauser = stexauser;
    }
    public Date getStexatime() {
        return stexatime;
    }
    public void setStexatime(Date stexatime) {
        this.stexatime = stexatime;
    }
    public String getStexaopt() {
        return stexaopt;
    }
    public void setStexaopt(String stexaopt) {
        this.stexaopt = stexaopt;
    }
    public Long getCvlexauser() {
        return cvlexauser;
    }
    public void setCvlexauser(Long cvlexauser) {
        this.cvlexauser = cvlexauser;
    }
    public Date getCvlexatime() {
        return cvlexatime;
    }
    public void setCvlexatime(Date cvlexatime) {
        this.cvlexatime = cvlexatime;
    }
    public String getCvlexaopt() {
        return cvlexaopt;
    }
    public void setCvlexaopt(String cvlexaopt) {
        this.cvlexaopt = cvlexaopt;
    }
    public String getChannelCode() {
        return channelCode;
    }
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }
    public String getBatchFileNo() {
        return batchFileNo;
    }
    public void setBatchFileNo(String batchFileNo) {
        this.batchFileNo = batchFileNo;
    }
    public String getRespCode() {
        return respCode;
    }
    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }
    public String getRespMsg() {
        return respMsg;
    }
    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public Long getInuser() {
        return inuser;
    }
    public void setInuser(Long inuser) {
        this.inuser = inuser;
    }
    public Date getIntime() {
        return intime;
    }
    public void setIntime(Date intime) {
        this.intime = intime;
    }
    public String getTxnseqno() {
        return txnseqno;
    }
    public void setTxnseqno(String txnseqno) {
        this.txnseqno = txnseqno;
    }
    public BigDecimal getTxnfee() {
        return txnfee;
    }
    public void setTxnfee(BigDecimal txnfee) {
        this.txnfee = txnfee;
    }
    public String getInsteadPayDataSeqNo() {
        return insteadPayDataSeqNo;
    }
    public void setInsteadPayDataSeqNo(String insteadPayDataSeqNo) {
        this.insteadPayDataSeqNo = insteadPayDataSeqNo;
    }
    public Date getApplyTime() {
        return applyTime;
    }
    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }
    public Long getTranDataId() {
        return tranDataId;
    }
    public void setTranDataId(Long tranDataId) {
        this.tranDataId = tranDataId;
    }
}
