package com.zlebank.zplatform.trade.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TRspmsg entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_RSPMSG")
public class PojoRspmsg implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 857572510389451791L;
	private String rspid;
	private String webrspcode;
	private String waprspcode;
	private String kind;
	private String reason;
	private String chnltype;
	private String chnlrspcode;
	private String rspinfo;
	private String notes;

	// Constructors

	/** default constructor */
	public PojoRspmsg() {
	}

	/** minimal constructor */
	public PojoRspmsg(String rspid) {
		this.rspid = rspid;
	}

	/** full constructor */
	public PojoRspmsg(String rspid, String webrspcode, String waprspcode,
			String kind, String reason, String chnltype, String chnlrspcode,
			String rspinfo, String notes) {
		this.rspid = rspid;
		this.webrspcode = webrspcode;
		this.waprspcode = waprspcode;
		this.kind = kind;
		this.reason = reason;
		this.chnltype = chnltype;
		this.chnlrspcode = chnlrspcode;
		this.rspinfo = rspinfo;
		this.notes = notes;
	}

	// Property accessors
	@Id
	@Column(name = "RSPID", unique = true, nullable = false, length = 8)
	public String getRspid() {
		return this.rspid;
	}

	public void setRspid(String rspid) {
		this.rspid = rspid;
	}

	@Column(name = "WEBRSPCODE", length = 4)
	public String getWebrspcode() {
		return this.webrspcode;
	}

	public void setWebrspcode(String webrspcode) {
		this.webrspcode = webrspcode;
	}

	@Column(name = "WAPRSPCODE", length = 2)
	public String getWaprspcode() {
		return this.waprspcode;
	}

	public void setWaprspcode(String waprspcode) {
		this.waprspcode = waprspcode;
	}

	@Column(name = "KIND", length = 2)
	public String getKind() {
		return this.kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	@Column(name = "REASON", length = 200)
	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name = "CHNLTYPE", length = 4)
	public String getChnltype() {
		return this.chnltype;
	}

	public void setChnltype(String chnltype) {
		this.chnltype = chnltype;
	}

	@Column(name = "CHNLRSPCODE", length = 8)
	public String getChnlrspcode() {
		return this.chnlrspcode;
	}

	public void setChnlrspcode(String chnlrspcode) {
		this.chnlrspcode = chnlrspcode;
	}

	@Column(name = "RSPINFO", length = 200)
	public String getRspinfo() {
		return this.rspinfo;
	}

	public void setRspinfo(String rspinfo) {
		this.rspinfo = rspinfo;
	}

	@Column(name = "NOTES", length = 256)
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}