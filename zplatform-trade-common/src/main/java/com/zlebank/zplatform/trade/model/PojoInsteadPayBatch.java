/* 
 * PojoInsteadPayBatch.java  
 * 
 * version TODO
 *
 * 2015年11月24日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 代付批次表
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月24日 下午12:07:25
 * @since 
 */
@Entity
@Table(name="T_INSTEAD_PAY_BATCH")
public class PojoInsteadPayBatch {

    /**标识**/
    private Long id;
    /**批次号**/
    private Long batchNo;
    /**商户号**/
    private String merId;
    /**订单发送时间(yyyyMMddhhmmss)**/
    private String txnTime;
    /**总笔数**/
    private Long totalQty;
    /**总金额**/
    private Long totalAmt;
    /**状态(00:已处理01:未处理)**/
    private String status;
    /**创建人**/
    private Long inuser;
    /**创建时间**/
    private Date intime;
    /**修改人**/
    private Long upuser;
    /**修改时间**/
    private Date uptime;
    /**备注**/
    private String notes;
    /**接入类型（01：文件导入00：接口）**/
    private String type;
    /**通过笔数**/
    private Long approveCount;
    /**通过金额**/
    private Long approveAmt;
    /**未审核笔数**/
    private Long unapproveCount;
    /**未审核金额**/
    private Long unapproveAmt;
    /**拒绝笔数**/
    private Long refuseCount;
    /**拒绝金额**/
    private Long refuseAmt;
    /**申请时间**/
    private Date applyTime;
    /**审核完成时间**/
    private Date approveFinishTime;
    /**转账完成时间**/
    private Date finishTime;
    /**代付批次序列号**/
    private String insteadPayBatchSeqNo;
    /**文件路径**/
    private String filePath;
    /**原文件名**/
    private String originalFileName;
    /**URL**/
    private String notifyUrl;
    
    /**批次明细**/
    private List<PojoInsteadPayDetail> details = new ArrayList<PojoInsteadPayDetail>();
    
    /**划拨批次**/
    @JSONField(serialize = false) 
    private List<PojoTranBatch> tranBatchs = new ArrayList<PojoTranBatch>();
    
    @OneToMany(mappedBy="insteadPayBatch")
    public List<PojoTranBatch> getTranBatchs(){
    	return tranBatchs;
    }
    public void setTranBatchs(List<PojoTranBatch> tranBatchs){
    	this.tranBatchs = tranBatchs;
    }

    @OneToMany(mappedBy="insteadPayBatch")
    public List<PojoInsteadPayDetail> getDetails() {
        return details;
    }
    public void setDetails(List<PojoInsteadPayDetail> details) {
        this.details = details;
    }
    @GenericGenerator(name = "id_gen", strategy = "enhanced-table", parameters = {
            @Parameter(name = "table_name", value = "T_C_PRIMAY_KEY"),
            @Parameter(name = "value_column_name", value = "NEXT_ID"),
            @Parameter(name = "segment_column_name", value = "KEY_NAME"),
            @Parameter(name = "segment_value", value = "INSTEAD_PAY_BATCH_ID"),
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
    @Column(name = "BATCH_NO")
    public Long getBatchNo() {
        return batchNo;
    }
    public void setBatchNo(Long batchNo) {
        this.batchNo = batchNo;
    }
    @Column(name = "MER_ID")
    public String getMerId() {
        return merId;
    }
    public void setMerId(String merId) {
        this.merId = merId;
    }
    @Column(name = "TXN_TIME")
    public String getTxnTime() {
        return txnTime;
    }
    public void setTxnTime(String txnTime) {
        this.txnTime = txnTime;
    }
    @Column(name = "TOTAL_QTY")
    public Long getTotalQty() {
        return totalQty;
    }
    public void setTotalQty(Long totalQty) {
        this.totalQty = totalQty;
    }
    @Column(name = "TOTAL_AMT")
    public Long getTotalAmt() {
        return totalAmt;
    }
    public void setTotalAmt(Long totalAmt) {
        this.totalAmt = totalAmt;
    }
    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    @Column(name = "INUSER")
    public Long getInuser() {
        return inuser;
    }
    public void setInuser(Long inuser) {
        this.inuser = inuser;
    }
    @Column(name = "INTIME")
    public Date getIntime() {
        return intime;
    }
    public void setIntime(Date intime) {
        this.intime = intime;
    }
    @Column(name = "UPUSER")
    public Long getUpuser() {
        return upuser;
    }
    public void setUpuser(Long upuser) {
        this.upuser = upuser;
    }
    @Column(name = "UPTIME")
    public Date getUptime() {
        return uptime;
    }
    public void setUptime(Date uptime) {
        this.uptime = uptime;
    }
    @Column(name = "NOTES")
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    @Column(name = "TYPE")
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    @Column(name = "APPROVE_COUNT")
    public Long getApproveCount() {
        return approveCount;
    }
    public void setApproveCount(Long approveCount) {
        this.approveCount = approveCount;
    }
    @Column(name = "APPROVE_AMT")
    public Long getApproveAmt() {
        return approveAmt;
    }
    public void setApproveAmt(Long approveAmt) {
        this.approveAmt = approveAmt;
    }
    @Column(name = "UNAPPROVE_COUNT")
    public Long getUnapproveCount() {
        return unapproveCount;
    }
    public void setUnapproveCount(Long unapproveCount) {
        this.unapproveCount = unapproveCount;
    }
    @Column(name = "UNAPPROVE_AMT")
    public Long getUnapproveAmt() {
        return unapproveAmt;
    }
    public void setUnapproveAmt(Long unapproveAmt) {
        this.unapproveAmt = unapproveAmt;
    }
    @Column(name = "REFUSE_COUNT")
    public Long getRefuseCount() {
        return refuseCount;
    }
    public void setRefuseCount(Long refuseCount) {
        this.refuseCount = refuseCount;
    }
    @Column(name = "REFUSE_AMT")
    public Long getRefuseAmt() {
        return refuseAmt;
    }
    public void setRefuseAmt(Long refuseAmt) {
        this.refuseAmt = refuseAmt;
    }
    @Column(name = "APPLY_TIME")
    public Date getApplyTime() {
        return applyTime;
    }
    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }
    @Column(name = "APPROVE_FINISH_TIME")
    public Date getApproveFinishTime() {
        return approveFinishTime;
    }
    public void setApproveFinishTime(Date approveFinishTime) {
        this.approveFinishTime = approveFinishTime;
    }
    @Column(name = "FINISH_TIME")
    public Date getFinishTime() {
        return finishTime;
    }
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }
    @Column(name = "INSTEAD_PAY_BATCH_SEQ_NO")
    public String getInsteadPayBatchSeqNo() {
        return insteadPayBatchSeqNo;
    }
    public void setInsteadPayBatchSeqNo(String insteadPayBatchSeqNo) {
        this.insteadPayBatchSeqNo = insteadPayBatchSeqNo;
    }
    @Column(name = "FILE_PATH")
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    @Column(name = "ORIGINAL_FILE_NAME")
    public String getOriginalFileName() {
        return originalFileName;
    }
    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }
    @Column(name = "NOTIFY_URL")
    public String getNotifyUrl() {
        return notifyUrl;
    }
    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}
