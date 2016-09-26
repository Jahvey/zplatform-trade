/* 
 * PojoBankTransferBatch.java  
 * 
 * version TODO
 *
 * 2016年3月7日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 转账批次
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年3月7日 上午11:40:42
 * @since 1.3.0
 */
@Entity
@Table(name = "T_BANK_TRAN_BATCH")
public class PojoBankTransferBatch {
    /** "表标识" **/
    private Long tid;
    /** "银行转账批次序列号" **/
    private String bankTranBatchNo;
    /** "转账渠道" **/
    @JSONField(serialize=false)
    private PojoBankTransferChannel channel;
    /** "关联划拨批次渠道" **/
    @JSONField(serialize=false)
    private List<PojoTranBatch> tranBatchs;
    /** "总笔数" **/
    private Long totalCount;
    /** "总金额" **/
    private Long totalAmt;
    /** "成功笔数" **/
    private Long successCount;
    /** "成功金额" **/
    private Long successAmt;
    /** "失败笔数" **/
    private Long failCount;
    /** "失败金额" **/
    private Long failAmt;
    /** """状态（01：未审核02：审核通过03：审核拒绝）""" **/
    private String status;
    /** "开放状态（0:开放1：关闭）" **/
    private String openStatus;
    /** """转账状态(01:等待转账02：部分转账成功03：全部转账成功 04：全部失败 **/
    private String tranStatus;
    /** "申请时间 **/
    @JSONField (format="yyyy-MM-dd HH:mm:ss") 
    private Date applyTime;
    /** "默认关闭时间" **/
    @JSONField (format="yyyy-MM-dd HH:mm:ss") 
    private Date defaultCloseTime;
    /** "最后关闭时间（每日）" **/
    @JSONField (format="yyyy-MM-dd HH:mm:ss") 
    private Date latestCloseTime;
    /** "关闭时间" **/
    @JSONField (format="yyyy-MM-dd HH:mm:ss") 
    private Date closeTime;
    /** """触发关闭动作（00：笔数到达上限01：到达每日最后关闭时间02：到达关闭间隔 03：手工关闭）""" **/
    private String closeEvent;
    /** "转账审核人" **/
    private Long tranUser;
    /** 备注 **/
    private String remark;
    /** 关联银行转账流水 **/
    @JSONField (serialize=false) 
    private List<PojoBankTransferData> bankTranDatas;
    @GenericGenerator(name = "id_gen", strategy = "enhanced-table", parameters = {
            @Parameter(name = "table_name", value = "T_C_PRIMAY_KEY"),
            @Parameter(name = "value_column_name", value = "NEXT_ID"),
            @Parameter(name = "segment_column_name", value = "KEY_NAME"),
            @Parameter(name = "segment_value", value = "BANK_TRAN_BATCH_ID"),
            @Parameter(name = "increment_size", value = "1"),
            @Parameter(name = "optimizer", value = "pooled-lo")})
    @Id
    @GeneratedValue(generator = "id_gen")
    @Column(name = "TID")
    public Long getTid() {
        return tid;
    }
    public void setTid(Long tid) {
        this.tid = tid;
    }
    @Column(name = "BANK_TRAN_BATCH_NO")
    public String getBankTranBatchNo() {
        return bankTranBatchNo;
    }
    public void setBankTranBatchNo(String bankTranBatchNo) {
        this.bankTranBatchNo = bankTranBatchNo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BANK_TRAN_CHANNEL_ID")
    public PojoBankTransferChannel getChannel() {
        return channel;
    }
    public void setChannel(PojoBankTransferChannel channel) {
        this.channel = channel;
    }

    @Column(name = "TOTAL_COUNT")
    public Long getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
    @Column(name = "TOTAL_AMT")
    public Long getTotalAmt() {
        return totalAmt;
    }
    public void setTotalAmt(Long totalAmt) {
        this.totalAmt = totalAmt;
    }
    @Column(name = "SUCCESS_COUNT")
    public Long getSuccessCount() {
        return successCount;
    }
    public void setSuccessCount(Long successCount) {
        this.successCount = successCount;
    }
    @Column(name = "SUCCESS_AMT")
    public Long getSuccessAmt() {
        return successAmt;
    }
    public void setSuccessAmt(Long successAmt) {
        this.successAmt = successAmt;
    }
    @Column(name = "FAIL_COUNT")
    public Long getFailCount() {
        return failCount;
    }
    public void setFailCount(Long failCount) {
        this.failCount = failCount;
    }
    @Column(name = "FAIL_AMT")
    public Long getFailAmt() {
        return failAmt;
    }
    public void setFailAmt(Long failAmt) {
        this.failAmt = failAmt;
    }
    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    @Column(name = "OPEN_STATUS")
    public String getOpenStatus() {
        return openStatus;
    }
    public void setOpenStatus(String openStatus) {
        this.openStatus = openStatus;
    }
    @Column(name = "TRAN_STATUS")
    public String getTranStatus() {
        return tranStatus;
    }
    public void setTranStatus(String tranStatus) {
        this.tranStatus = tranStatus;
    }
    @Column(name = "APPLY_TIME")
    public Date getApplyTime() {
        return applyTime;
    }
    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }
    @Column(name = "DEFAULT_CLOSE_TIME")
    public Date getDefaultCloseTime() {
        return defaultCloseTime;
    }
    public void setDefaultCloseTime(Date defaultCloseTime) {
        this.defaultCloseTime = defaultCloseTime;
    }
    @Column(name = "LATEST_CLOSE_TIME")
    public Date getLatestCloseTime() {
        return latestCloseTime;
    }
    public void setLatestCloseTime(Date latestCloseTime) {
        this.latestCloseTime = latestCloseTime;
    }
    @Column(name = "CLOSE_TIME")
    public Date getCloseTime() {
        return closeTime;
    }
    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }
    @Column(name = "CLOSE_EVENT")
    public String getCloseEvent() {
        return closeEvent;
    }
    public void setCloseEvent(String closeEvent) {
        this.closeEvent = closeEvent;
    }
    @Column(name = "TRAN_USER")
    public Long getTranUser() {
        return tranUser;
    }
    public void setTranUser(Long tranUser) {
        this.tranUser = tranUser;
    }
    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "RELA_TB_BTB", joinColumns = {@JoinColumn(name = "BANK_TRAN_BATCH_ID", referencedColumnName = "TID")}, inverseJoinColumns = {@JoinColumn(name = "TRAN_BATCH_ID", referencedColumnName = "TID")})
    @Cascade(value = CascadeType.SAVE_UPDATE)
    public List<PojoTranBatch> getTranBatchs() {
        return tranBatchs;
    }
    public void setTranBatchs(List<PojoTranBatch> tranBatchs) {
        this.tranBatchs = tranBatchs;
    }
    @OneToMany(mappedBy = "bankTranBatch", fetch = FetchType.LAZY)
    @Cascade(value = CascadeType.SAVE_UPDATE)
    public List<PojoBankTransferData> getBankTranDatas() {
        return bankTranDatas;
    }
    public void setBankTranDatas(List<PojoBankTransferData> bankTranDatas) {
        this.bankTranDatas = bankTranDatas;
    }
    
    public void addBankTranDatas(List<PojoBankTransferData> tranDatas){
        for(PojoBankTransferData tranData:tranDatas){
            tranData.setBankTranBatch(this);
        }
        if(this.bankTranDatas==null){
            this.bankTranDatas = tranDatas;
        }else{
            this.bankTranDatas.addAll(tranDatas);
        }
        
    }
    
    public void addTranBatch(PojoTranBatch batch){
        if(tranBatchs==null){
            tranBatchs = new ArrayList<PojoTranBatch>();
        }
        for(PojoTranBatch relatedTranBatch:tranBatchs){
            if(relatedTranBatch.getTid()==batch.getTid()){
                return;
            }
        }
        tranBatchs.add(batch);
    }
}
