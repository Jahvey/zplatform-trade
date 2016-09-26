package com.zlebank.zplatform.trade.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TTxnsTraderr entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_TXNS_TRADERR", schema = "PAYSERVICE")
public class TxnsTraderrModel implements java.io.Serializable {

	// Fields

	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = 8858835786639231935L;
    private Long id;
	private String traderrno;
	private String routlvl;
	private String status;
	private String relatetradetxn;
	private String tradeflag;
	private Date creatime;
	private Date dealstartime;
	private Date dealdendtime;
	private String notes;
	private String remarks;

	// Constructors

	/** default constructor */
	public TxnsTraderrModel() {
	}

	/** minimal constructor */
	public TxnsTraderrModel(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TxnsTraderrModel(Long id, String traderrno, String routlvl,
			String status, String relatetradetxn, String tradeflag,
			Date creatime, Date dealstartime, Date dealdendtime, String notes,
			String remarks) {
		this.id = id;
		this.traderrno = traderrno;
		this.routlvl = routlvl;
		this.status = status;
		this.relatetradetxn = relatetradetxn;
		this.tradeflag = tradeflag;
		this.creatime = creatime;
		this.dealstartime = dealstartime;
		this.dealdendtime = dealdendtime;
		this.notes = notes;
		this.remarks = remarks;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 15, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "TRADERRNO", length = 16)
	public String getTraderrno() {
		return this.traderrno;
	}

	public void setTraderrno(String traderrno) {
		this.traderrno = traderrno;
	}

	@Column(name = "ROUTLVL", length = 8)
	public String getRoutlvl() {
		return this.routlvl;
	}

	public void setRoutlvl(String routlvl) {
		this.routlvl = routlvl;
	}

	@Column(name = "STATUS", length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "RELATETRADETXN", length = 16)
	public String getRelatetradetxn() {
		return this.relatetradetxn;
	}

	public void setRelatetradetxn(String relatetradetxn) {
		this.relatetradetxn = relatetradetxn;
	}

	@Column(name = "TRADEFLAG", length = 12)
	public String getTradeflag() {
		return this.tradeflag;
	}

	public void setTradeflag(String tradeflag) {
		this.tradeflag = tradeflag;
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
	@Column(name = "DEALSTARTIME", length = 7)
	public Date getDealstartime() {
		return this.dealstartime;
	}

	public void setDealstartime(Date dealstartime) {
		this.dealstartime = dealstartime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DEALDENDTIME", length = 7)
	public Date getDealdendtime() {
		return this.dealdendtime;
	}

	public void setDealdendtime(Date dealdendtime) {
		this.dealdendtime = dealdendtime;
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