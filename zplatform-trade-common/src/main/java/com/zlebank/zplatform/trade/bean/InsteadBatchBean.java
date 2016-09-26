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
 * 
 * 代付批次结果
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年3月11日 下午4:29:17
 * @since
 */
public class InsteadBatchBean implements Bean{
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
    private BigDecimal totalAmt;
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
    private BigDecimal approveAmt;
    /**未审核笔数**/
    private Long unapproveCount;
    /**未审核金额**/
    private BigDecimal unapproveAmt;
    /**拒绝笔数**/
    private Long refuseCount;
    /**拒绝金额**/
    private BigDecimal refuseAmt;
    /**申请时间**/
    private Date applyTime;
    /**审核完成时间**/
    private Date approveFinishTime;
    /**转账完成时间**/
    private Date finishTime;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getBatchNo() {
        return batchNo;
    }
    public void setBatchNo(Long batchNo) {
        this.batchNo = batchNo;
    }
    public String getMerId() {
        return merId;
    }
    public void setMerId(String merId) {
        this.merId = merId;
    }
    public String getTxnTime() {
        return txnTime;
    }
    public void setTxnTime(String txnTime) {
        this.txnTime = txnTime;
    }
    public Long getTotalQty() {
        return totalQty;
    }
    public void setTotalQty(Long totalQty) {
        this.totalQty = totalQty;
    }
    public BigDecimal getTotalAmt() {
        return totalAmt;
    }
    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
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
    public Long getUpuser() {
        return upuser;
    }
    public void setUpuser(Long upuser) {
        this.upuser = upuser;
    }
    public Date getUptime() {
        return uptime;
    }
    public void setUptime(Date uptime) {
        this.uptime = uptime;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Long getApproveCount() {
        return approveCount;
    }
    public void setApproveCount(Long approveCount) {
        this.approveCount = approveCount;
    }
    public BigDecimal getApproveAmt() {
        return approveAmt;
    }
    public void setApproveAmt(BigDecimal approveAmt) {
        this.approveAmt = approveAmt;
    }
    public Long getUnapproveCount() {
        return unapproveCount;
    }
    public void setUnapproveCount(Long unapproveCount) {
        this.unapproveCount = unapproveCount;
    }
    public BigDecimal getUnapproveAmt() {
        return unapproveAmt;
    }
    public void setUnapproveAmt(BigDecimal unapproveAmt) {
        this.unapproveAmt = unapproveAmt;
    }
    public Long getRefuseCount() {
        return refuseCount;
    }
    public void setRefuseCount(Long refuseCount) {
        this.refuseCount = refuseCount;
    }
    public BigDecimal getRefuseAmt() {
        return refuseAmt;
    }
    public void setRefuseAmt(BigDecimal refuseAmt) {
        this.refuseAmt = refuseAmt;
    }
    public Date getApplyTime() {
        return applyTime;
    }
    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }
    public Date getApproveFinishTime() {
        return approveFinishTime;
    }
    public void setApproveFinishTime(Date approveFinishTime) {
        this.approveFinishTime = approveFinishTime;
    }
    public Date getFinishTime() {
        return finishTime;
    }
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }
    
    
    
    

}
