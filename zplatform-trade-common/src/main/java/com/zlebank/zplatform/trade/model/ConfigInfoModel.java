package com.zlebank.zplatform.trade.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TConfigInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_CONFIG_INFO")
public class ConfigInfoModel implements java.io.Serializable {

	// Fields

	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6214615116334705954L;
    private Long id;
	private String paraname;
	private String para;
	private String busicode;
	private String businame;
	private String status;
	private Date creatime;
	private Date uptime;
	private String notes;
	private String remarks;

	// Constructors

	/** default constructor */
	public ConfigInfoModel() {
	}

	/** minimal constructor */
	public ConfigInfoModel(Long id) {
		this.id = id;
	}

	/** full constructor */
	public ConfigInfoModel(Long id, String paraname, String para, String busicode,
			String businame, String status, Date creatime, Date uptime,
			String notes, String remarks) {
		this.id = id;
		this.paraname = paraname;
		this.para = para;
		this.busicode = busicode;
		this.businame = businame;
		this.status = status;
		this.creatime = creatime;
		this.uptime = uptime;
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

	@Column(name = "PARANAME", length = 32)
	public String getParaname() {
		return this.paraname;
	}

	public void setParaname(String paraname) {
		this.paraname = paraname;
	}

	@Column(name = "PARA", length = 32)
	public String getPara() {
		return this.para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	@Column(name = "BUSICODE", length = 8)
	public String getBusicode() {
		return this.busicode;
	}

	public void setBusicode(String busicode) {
		this.busicode = busicode;
	}

	@Column(name = "BUSINAME", length = 32)
	public String getBusiname() {
		return this.businame;
	}

	public void setBusiname(String businame) {
		this.businame = businame;
	}

	@Column(name = "STATUS", length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATIME", length = 7)
	public Date getCreatime() {
		return this.creatime;
	}

	public void setCreatime(Date creatime) {
		this.creatime = creatime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "UPTIME", length = 7)
	public Date getUptime() {
		return this.uptime;
	}

	public void setUptime(Date uptime) {
		this.uptime = uptime;
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