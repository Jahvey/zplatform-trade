package com.zlebank.zplatform.trade.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TProdCase entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_PROD_CASE")
public class ProdCaseModel implements java.io.Serializable {

	// Fields

	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3692222731872281016L;
    private Long caseid;
	private String prdtver;
	private String busicode;
	private String businame;
	private String status;
	private Long inuser;
	private Date intime;
	private Long upuser;
	private Date uptime;
	private String notes;
	private String remarks;

	// Constructors

	/** default constructor */
	public ProdCaseModel() {
	}

	/** minimal constructor */
	public ProdCaseModel(Long caseid, String prdtver, String busicode,
			String businame, String status, Date intime) {
		this.caseid = caseid;
		this.prdtver = prdtver;
		this.busicode = busicode;
		this.businame = businame;
		this.status = status;
		this.intime = intime;
	}

	/** full constructor */
	public ProdCaseModel(Long caseid, String prdtver, String busicode,
			String businame, String status, Long inuser, Date intime,
			Long upuser, Date uptime, String notes, String remarks) {
		this.caseid = caseid;
		this.prdtver = prdtver;
		this.busicode = busicode;
		this.businame = businame;
		this.status = status;
		this.inuser = inuser;
		this.intime = intime;
		this.upuser = upuser;
		this.uptime = uptime;
		this.notes = notes;
		this.remarks = remarks;
	}

	// Property accessors
	@Id
	@Column(name = "CASEID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getCaseid() {
		return this.caseid;
	}

	public void setCaseid(Long caseid) {
		this.caseid = caseid;
	}

	@Column(name = "PRDTVER", nullable = false, length = 8)
	public String getPrdtver() {
		return this.prdtver;
	}

	public void setPrdtver(String prdtver) {
		this.prdtver = prdtver;
	}

	@Column(name = "BUSICODE", nullable = false, length = 8)
	public String getBusicode() {
		return this.busicode;
	}

	public void setBusicode(String busicode) {
		this.busicode = busicode;
	}

	@Column(name = "BUSINAME", nullable = false, length = 64)
	public String getBusiname() {
		return this.businame;
	}

	public void setBusiname(String businame) {
		this.businame = businame;
	}

	@Column(name = "STATUS", nullable = false, length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "INUSER", precision = 10, scale = 0)
	public Long getInuser() {
		return this.inuser;
	}

	public void setInuser(Long inuser) {
		this.inuser = inuser;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "INTIME", nullable = false, length = 7)
	public Date getIntime() {
		return this.intime;
	}

	public void setIntime(Date intime) {
		this.intime = intime;
	}

	@Column(name = "UPUSER", precision = 10, scale = 0)
	public Long getUpuser() {
		return this.upuser;
	}

	public void setUpuser(Long upuser) {
		this.upuser = upuser;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "UPTIME", length = 7)
	public Date getUptime() {
		return this.uptime;
	}

	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}

	@Column(name = "NOTES", length = 128)
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Column(name = "REMARKS", length = 128)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}