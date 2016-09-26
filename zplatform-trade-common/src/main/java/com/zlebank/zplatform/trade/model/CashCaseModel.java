package com.zlebank.zplatform.trade.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TCashCase entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_CASH_CASE")
public class CashCaseModel implements java.io.Serializable {

	// Fields

	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = -240036586606309538L;
    private Long id;
	private String cashver;
	private String cashcode;
	private String cashname;
	private String cashdesc;
	private String status;
	private String notes;
	private String remarks;

	// Constructors

	/** default constructor */
	public CashCaseModel() {
	}

	/** minimal constructor */
	public CashCaseModel(Long id) {
		this.id = id;
	}

	/** full constructor */
	public CashCaseModel(Long id, String cashver, String cashcode, String cashname,
			String cashdesc, String status, String notes, String remarks) {
		this.id = id;
		this.cashver = cashver;
		this.cashcode = cashcode;
		this.cashname = cashname;
		this.cashdesc = cashdesc;
		this.status = status;
		this.notes = notes;
		this.remarks = remarks;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 11, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CASHVER", length = 8)
	public String getCashver() {
		return this.cashver;
	}

	public void setCashver(String cashver) {
		this.cashver = cashver;
	}

	@Column(name = "CASHCODE", length = 8)
	public String getCashcode() {
		return this.cashcode;
	}

	public void setCashcode(String cashcode) {
		this.cashcode = cashcode;
	}

	@Column(name = "CASHNAME", length = 32)
	public String getCashname() {
		return this.cashname;
	}

	public void setCashname(String cashname) {
		this.cashname = cashname;
	}

	@Column(name = "CASHDESC", length = 64)
	public String getCashdesc() {
		return this.cashdesc;
	}

	public void setCashdesc(String cashdesc) {
		this.cashdesc = cashdesc;
	}

	@Column(name = "STATUS", length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "NOTES", length = 256)
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Column(name = "REMARKS", length = 256)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}