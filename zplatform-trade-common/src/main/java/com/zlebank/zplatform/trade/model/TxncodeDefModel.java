package com.zlebank.zplatform.trade.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TTxncodeDef entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_TXNCODE_DEF")
public class TxncodeDefModel implements java.io.Serializable {

	// Fields

	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6912653347490462796L;
    private Long txnid;
	private String apptype;
	private String txntype;
	private String status;
	private String busicode;
	private String businame;
	private String notes;
	private String txnsubtype;
	private String biztype;
	private String busitype;
	// Constructors

	/** default constructor */
	public TxncodeDefModel() {
	}

	/** minimal constructor */
	public TxncodeDefModel(Long txnid) {
		this.txnid = txnid;
	}

	/** full constructor */
	public TxncodeDefModel(Long txnid, String apptype, String txntype,
			String status, String busicode, String businame, String notes,
			String txnsubtype, String biztype) {
		this.txnid = txnid;
		this.apptype = apptype;
		this.txntype = txntype;
		this.status = status;
		this.busicode = busicode;
		this.businame = businame;
		this.notes = notes;
		this.txnsubtype = txnsubtype;
		this.biztype = biztype;
	}

	// Property accessors
	@Id
	@Column(name = "TXNID", unique = true, nullable = false, precision = 12, scale = 0)
	public Long getTxnid() {
		return this.txnid;
	}

	public void setTxnid(Long txnid) {
		this.txnid = txnid;
	}

	@Column(name = "APPTYPE", length = 4)
	public String getApptype() {
		return this.apptype;
	}

	public void setApptype(String apptype) {
		this.apptype = apptype;
	}

	@Column(name = "TXNTYPE", length = 4)
	public String getTxntype() {
		return this.txntype;
	}

	public void setTxntype(String txntype) {
		this.txntype = txntype;
	}

	@Column(name = "STATUS", length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "BUSICODE", length = 8)
	public String getBusicode() {
		return this.busicode;
	}

	public void setBusicode(String busicode) {
		this.busicode = busicode;
	}

	@Column(name = "BUSINAME", length = 8)
	public String getBusiname() {
		return this.businame;
	}

	public void setBusiname(String businame) {
		this.businame = businame;
	}

	@Column(name = "NOTES", length = 256)
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Column(name = "TXNSUBTYPE", length = 2)
	public String getTxnsubtype() {
		return this.txnsubtype;
	}

	public void setTxnsubtype(String txnsubtype) {
		this.txnsubtype = txnsubtype;
	}

	@Column(name = "BIZTYPE", length = 6)
	public String getBiztype() {
		return this.biztype;
	}

	public void setBiztype(String biztype) {
		this.biztype = biztype;
	}

    /**
     * @return the busitype
     */
	@Column(name = "BUSITYPE", length = 4)
    public String getBusitype() {
        return busitype;
    }

    /**
     * @param busitype the busitype to set
     */
    public void setBusitype(String busitype) {
        this.busitype = busitype;
    }

}