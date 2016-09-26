/* 
 * PojoCMBCResfileLog.java  
 * 
 * version TODO
 *
 * 2016年6月28日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年6月28日 上午10:38:13
 * @since
 */
@Entity
@Table(name = "T_CMBC_RESFILE_LOG")
public class PojoCMBCResfileLog implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private Long tid;
	private String bankTranDataSeqNo;
	private String bankTranResNo;
	private String accNo;
	private String accName;
	private Long tranAmt;
	private String resType;
	private String resCode;
	private String resInfo;
	private String payDate;
	private String payDatetime;
	private String bankTranBatchNo;
	
	

	/**
	 * 
	 */
	public PojoCMBCResfileLog() {
		super();
	}

	/**
	 * @param body
	 
	 */
	public PojoCMBCResfileLog(String[] body) {
		super();
		this.bankTranDataSeqNo = body[0];
		this.bankTranResNo = body[1];
		this.accNo = body[2];
		this.accName = body[3];
		this.tranAmt = Long.valueOf(body[4]);
		this.resType = body[5];
		this.resCode = body[6];
		this.resInfo = body[7];
		this.payDate = body[8];
		this.payDatetime = body[9];
	}

	/**
	 * @return the tid
	 */
	 @GenericGenerator(name = "id_gen", strategy = "enhanced-table", parameters = {
	            @Parameter(name = "table_name", value = "T_C_PRIMAY_KEY"),
	            @Parameter(name = "value_column_name", value = "NEXT_ID"),
	            @Parameter(name = "segment_column_name", value = "KEY_NAME"),
	            @Parameter(name = "segment_value", value = "T_CMBC_RESFILE_LOG"),
	            @Parameter(name = "increment_size", value = "1"),
	            @Parameter(name = "optimizer", value = "pooled-lo") })
	    @Id
	    @GeneratedValue(generator = "id_gen")
	    @Column(name = "TID")
	public Long getTid() {
		return tid;
	}

	/**
	 * @param tid
	 *            the tid to set
	 */
	public void setTid(Long tid) {
		this.tid = tid;
	}

	/**
	 * @return the bankTranDataSeqNo
	 */
	@Column(name = "BANKTRANDATASEQNO")
	public String getBankTranDataSeqNo() {
		return bankTranDataSeqNo;
	}

	/**
	 * @param bankTranDataSeqNo
	 *            the bankTranDataSeqNo to set
	 */
	public void setBankTranDataSeqNo(String bankTranDataSeqNo) {
		this.bankTranDataSeqNo = bankTranDataSeqNo;
	}

	/**
	 * @return the bankTranResNo
	 */
	@Column(name = "BANKTRANRESNO")
	public String getBankTranResNo() {
		return bankTranResNo;
	}

	/**
	 * @param bankTranResNo
	 *            the bankTranResNo to set
	 */
	public void setBankTranResNo(String bankTranResNo) {
		this.bankTranResNo = bankTranResNo;
	}

	/**
	 * @return the accNo
	 */
	@Column(name = "ACCNO")
	public String getAccNo() {
		return accNo;
	}

	/**
	 * @param accNo
	 *            the accNo to set
	 */
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	/**
	 * @return the accName
	 */
	@Column(name = "ACCNAME")
	public String getAccName() {
		return accName;
	}

	/**
	 * @param accName
	 *            the accName to set
	 */
	public void setAccName(String accName) {
		this.accName = accName;
	}

	/**
	 * @return the tranAmt
	 */
	@Column(name = "TRANAMT")
	public Long getTranAmt() {
		return tranAmt;
	}

	/**
	 * @param tranAmt
	 *            the tranAmt to set
	 */
	public void setTranAmt(Long tranAmt) {
		this.tranAmt = tranAmt;
	}

	/**
	 * @return the resType
	 */
	@Column(name = "RESTYPE")	
	public String getResType() {
		return resType;
	}

	/**
	 * @param resType
	 *            the resType to set
	 */
	public void setResType(String resType) {
		this.resType = resType;
	}

	/**
	 * @return the resCode
	 */
	@Column(name = "RESCODE")	
	public String getResCode() {
		return resCode;
	}

	/**
	 * @param resCode
	 *            the resCode to set
	 */
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	/**
	 * @return the resInfo
	 */
	@Column(name = "RESINFO")	
	public String getResInfo() {
		return resInfo;
	}

	/**
	 * @param resInfo
	 *            the resInfo to set
	 */
	public void setResInfo(String resInfo) {
		this.resInfo = resInfo;
	}

	/**
	 * @return the payDate
	 */
	@Column(name = "PAYDATE")
	public String getPayDate() {
		return payDate;
	}

	/**
	 * @param payDate
	 *            the payDate to set
	 */
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	/**
	 * @return the payDatetime
	 */
	@Column(name = "PAYDATETIME")
	public String getPayDatetime() {
		return payDatetime;
	}

	/**
	 * @param payDatetime
	 *            the payDatetime to set
	 */
	public void setPayDatetime(String payDatetime) {
		this.payDatetime = payDatetime;
	}

	/**
	 * @return the bankTranBatchNo
	 */
	@Column(name = "BANKTRANBATCHNO")
	public String getBankTranBatchNo() {
		return bankTranBatchNo;
	}

	/**
	 * @param bankTranBatchNo
	 *            the bankTranBatchNo to set
	 */
	public void setBankTranBatchNo(String bankTranBatchNo) {
		this.bankTranBatchNo = bankTranBatchNo;
	}

}
