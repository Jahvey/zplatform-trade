/* 
 * PojoTranData.java  
 * 
 * version TODO
 *
 * 2016年3月8日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 划拨流水
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年3月8日 下午3:23:08
 * @since 1.3.0
 */
@Entity
@Table(name = "T_TRAN_DATA")
public class PojoTranData implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -7284460033718598602L;
    /** 标示 **/
    private Long tid;
    /** "划拨流水序列号" **/
    private String tranDataSeqNo;
    /** "划拨批次序列号" **/
    @JSONField(serialize = false)  
    private PojoTranBatch tranBatch;
    /** "业务类型" **/
    private String busiType;
    /** "业务流水号" **/
    private Long busiDataId;
    /** "账户类型(0:对私账户1：对公账户)" **/
    /** "账户号" **/
    private String accType;
    private String accNo;
    /** "账户名" **/
    private String accName;
    /** "银行代码" **/
    private String bankNo;
    /** "银行名称" **/
    private String bankName;
    /** "划拨金额" **/
    private Long tranAmt;
    /** "备注" **/
    private String remark;
    /** "状态(01:未审核00：审核通过09：审核拒绝)" **/
    private String status;
    /** "申请时间" **/
    @JSONField (format="yyyy-MM-dd HH:mm:ss") 
    private Date applyTime;
    /** "通过时间" **/
    @JSONField (format="yyyy-MM-dd HH:mm:ss") 
    private Date approveTime;
    /** "通过用户" **/
    private Long approveUser;
    /** "交易手续费" **/
    private Long tranFee;
    /** 转账流水数据 **/
    @JSONField(serialize = false)  
    private PojoBankTransferData bankTranData;
    /** 会员号/付款方的会员号 **/
    private String memberId;
    /** 交易序列号 **/
    private String txnseqno;
    
    private String busiSeqNo;
    private String merchOrderNo;
    @GenericGenerator(name = "id_gen", strategy = "enhanced-table", parameters = {
            @Parameter(name = "table_name", value = "T_C_PRIMAY_KEY"),
            @Parameter(name = "value_column_name", value = "NEXT_ID"),
            @Parameter(name = "segment_column_name", value = "KEY_NAME"),
            @Parameter(name = "segment_value", value = "T_TRANSFER_DATA_ID"),
            @Parameter(name = "increment_size", value = "1"),
            @Parameter(name = "optimizer", value = "pooled-lo")})
    @Id
    @GeneratedValue(generator = "id_gen")
    @Column(name = "TID", unique = true, nullable = false, precision = 12, scale = 0)
    public Long getTid() {
        return tid;
    }
    public void setTid(Long tid) {
        this.tid = tid;
    }
    @Column(name = "TRAN_DATA_SEQ_NO")
    public String getTranDataSeqNo() {
        return tranDataSeqNo;
    }
    public void setTranDataSeqNo(String tranDataSeqNo) {
        this.tranDataSeqNo = tranDataSeqNo;
    }
    @Column(name = "BUSI_TYPE")
    public String getBusiType() {
        return busiType;
    }
    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }
    @Column(name = "BUSI_DATA_ID")
    public Long getBusiDataId() {
        return busiDataId;
    }
    public void setBusiDataId(Long busiDataId) {
        this.busiDataId = busiDataId;
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
    @Column(name = "BANK_NO")
    public String getBankNo() {
        return bankNo;
    }
    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }
    @Column(name = "BANK_NAME")
    public String getBankName() {
        return bankName;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    @Column(name = "TRAN_AMT")
    public Long getTranAmt() {
        return tranAmt;
    }
    public void setTranAmt(Long tranAmt) {
        this.tranAmt = tranAmt;
    }
    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    @Column(name = "APPLY_TIME")
    public Date getApplyTime() {
        return applyTime;
    }
    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }
    @Column(name = "APPROVE_TIME")
    public Date getApproveTime() {
        return approveTime;
    }
    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }
    @Column(name = "APPROVE_USER")
    public Long getApproveUser() {
        return approveUser;
    }
    public void setApproveUser(Long approveUser) {
        this.approveUser = approveUser;
    }
    @Column(name = "TRAN_FEE")
    public Long getTranFee() {
        return tranFee;
    }
    public void setTranFee(Long tranFee) {
        this.tranFee = tranFee;
    }
    @Column(name = "MEMBER_ID")
    public String getMemberId() {
        return memberId;
    }
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    @Column(name = "TXNSEQNO")
    public String getTxnseqno() {
        return txnseqno;
    }
    public void setTxnseqno(String txnseqno) {
        this.txnseqno = txnseqno;
    } 
    @ManyToOne(cascade=CascadeType.PERSIST,fetch=FetchType.LAZY)
    @JoinColumn(name = "TRAN_BATCH_ID")
    public PojoTranBatch getTranBatch() {
        return tranBatch;
    }
    public void setTranBatch(PojoTranBatch tranBatch) {
        this.tranBatch = tranBatch;
    }
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "BANK_TRAN_DATA_ID")
    public PojoBankTransferData getBankTranData() {
        return bankTranData;
    }
    public void setBankTranData(PojoBankTransferData bankTranData) {
        this.bankTranData = bankTranData;
    }
    @Column(name = "busi_seqno")
    public String getBusiSeqNo() {
        return busiSeqNo;
    }
    public void setBusiSeqNo(String busiSeqNo) {
        this.busiSeqNo = busiSeqNo;
    }
    @Column(name = "merch_order_no")
    public String getMerchOrderNo() {
        return merchOrderNo;
    }
    public void setMerchOrderNo(String merchOrderNo) {
        this.merchOrderNo = merchOrderNo;
    }
}
