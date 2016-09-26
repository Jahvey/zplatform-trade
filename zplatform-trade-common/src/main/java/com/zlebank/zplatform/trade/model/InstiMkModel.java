package com.zlebank.zplatform.trade.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TInstiMk entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_INSTI_MK")
public class InstiMkModel implements java.io.Serializable {

	// Fields

	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = -7980128203828750430L;
    private String instiid;
	private String safeseq;
	private String safetype;
	private String pubkey;
	private String prikey;
	private String bmk;
	private String pin;
	private String mac;
	private String data;
	private String md5;
	private String storgetype;
	private String status;
	private String notes;
	private String remarks;

	// Constructors

	/** default constructor */
	public InstiMkModel() {
	}

	/** minimal constructor */
	public InstiMkModel(String instiid) {
		this.instiid = instiid;
	}

	/** full constructor */
	public InstiMkModel(String instiid, String safeseq, String safetype,
			String pubkey, String prikey, String bmk, String pin, String mac,
			String data, String md5, String storgetype, String status,
			String notes, String remarks) {
		this.instiid = instiid;
		this.safeseq = safeseq;
		this.safetype = safetype;
		this.pubkey = pubkey;
		this.prikey = prikey;
		this.bmk = bmk;
		this.pin = pin;
		this.mac = mac;
		this.data = data;
		this.md5 = md5;
		this.storgetype = storgetype;
		this.status = status;
		this.notes = notes;
		this.remarks = remarks;
	}

	// Property accessors
	@Id
	@Column(name = "INSTIID", unique = true, nullable = false, length = 15)
	public String getInstiid() {
		return this.instiid;
	}

	public void setInstiid(String instiid) {
		this.instiid = instiid;
	}

	@Column(name = "SAFESEQ", length = 32)
	public String getSafeseq() {
		return this.safeseq;
	}

	public void setSafeseq(String safeseq) {
		this.safeseq = safeseq;
	}

	@Column(name = "SAFETYPE", length = 32)
	public String getSafetype() {
		return this.safetype;
	}

	public void setSafetype(String safetype) {
		this.safetype = safetype;
	}

	@Column(name = "PUBKEY", length = 2048)
	public String getPubkey() {
		return this.pubkey;
	}

	public void setPubkey(String pubkey) {
		this.pubkey = pubkey;
	}

	@Column(name = "PRIKEY", length = 1024)
	public String getPrikey() {
		return this.prikey;
	}

	public void setPrikey(String prikey) {
		this.prikey = prikey;
	}

	@Column(name = "BMK", length = 256)
	public String getBmk() {
		return this.bmk;
	}

	public void setBmk(String bmk) {
		this.bmk = bmk;
	}

	@Column(name = "PIN", length = 256)
	public String getPin() {
		return this.pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	@Column(name = "MAC", length = 128)
	public String getMac() {
		return this.mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	@Column(name = "DATA", length = 512)
	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Column(name = "MD5", length = 256)
	public String getMd5() {
		return this.md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	@Column(name = "STORGETYPE", length = 2)
	public String getStorgetype() {
		return this.storgetype;
	}

	public void setStorgetype(String storgetype) {
		this.storgetype = storgetype;
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