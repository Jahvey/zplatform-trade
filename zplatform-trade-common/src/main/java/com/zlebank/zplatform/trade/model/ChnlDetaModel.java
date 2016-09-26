package com.zlebank.zplatform.trade.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TChnlDeta entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_CHNL_DETA")
public class ChnlDetaModel implements java.io.Serializable {

	// Fields

	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4499784198716910441L;
    private Long chnlid;
	private String chnlcode;
	private String chnlname;
	private String insticode;
	private String chnltype;
	private String status;
	private String url;
	private String bmk;
	private String mackey;
	private String pinkey;
	private String datakey;
	private String encode;
	private String notes;
	private String remarks;
	private String fronturl;
	private String backurl;
	private String safeurl;
	private String chnlmerchno;
	private String impl;
	private String refundImpl;
	// Constructors

	/** default constructor */
	public ChnlDetaModel() {
	}

	/** minimal constructor */
	public ChnlDetaModel(Long chnlid, String status) {
		this.chnlid = chnlid;
		this.status = status;
	}

	/** full constructor */
	public ChnlDetaModel(Long chnlid, String chnlcode, String chnlname,
			String insticode, String chnltype, String status, String url,
			String bmk, String mackey, String pinkey, String datakey,
			String encode, String notes, String remarks, String fronturl,
			String backurl) {
		this.chnlid = chnlid;
		this.chnlcode = chnlcode;
		this.chnlname = chnlname;
		this.insticode = insticode;
		this.chnltype = chnltype;
		this.status = status;
		this.url = url;
		this.bmk = bmk;
		this.mackey = mackey;
		this.pinkey = pinkey;
		this.datakey = datakey;
		this.encode = encode;
		this.notes = notes;
		this.remarks = remarks;
		this.fronturl = fronturl;
		this.backurl = backurl;
	}

	// Property accessors
	@Id
	@Column(name = "CHNLID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getChnlid() {
		return this.chnlid;
	}

	public void setChnlid(Long chnlid) {
		this.chnlid = chnlid;
	}

	@Column(name = "CHNLCODE", length = 10)
	public String getChnlcode() {
		return this.chnlcode;
	}

	public void setChnlcode(String chnlcode) {
		this.chnlcode = chnlcode;
	}

	@Column(name = "CHNLNAME", length = 64)
	public String getChnlname() {
		return this.chnlname;
	}

	public void setChnlname(String chnlname) {
		this.chnlname = chnlname;
	}

	@Column(name = "INSTICODE", length = 8)
	public String getInsticode() {
		return this.insticode;
	}

	public void setInsticode(String insticode) {
		this.insticode = insticode;
	}

	@Column(name = "CHNLTYPE", length = 4)
	public String getChnltype() {
		return this.chnltype;
	}

	public void setChnltype(String chnltype) {
		this.chnltype = chnltype;
	}

	@Column(name = "STATUS", nullable = false, length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "URL", length = 256)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "BMK", length = 64)
	public String getBmk() {
		return this.bmk;
	}

	public void setBmk(String bmk) {
		this.bmk = bmk;
	}

	@Column(name = "MACKEY", length = 64)
	public String getMackey() {
		return this.mackey;
	}

	public void setMackey(String mackey) {
		this.mackey = mackey;
	}

	@Column(name = "PINKEY", length = 64)
	public String getPinkey() {
		return this.pinkey;
	}

	public void setPinkey(String pinkey) {
		this.pinkey = pinkey;
	}

	@Column(name = "DATAKEY", length = 64)
	public String getDatakey() {
		return this.datakey;
	}

	public void setDatakey(String datakey) {
		this.datakey = datakey;
	}

	@Column(name = "ENCODE", length = 64)
	public String getEncode() {
		return this.encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	@Column(name = "NOTES", length = 256)
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Column(name = "REMARKS", length = 64)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "FRONTURL", length = 256)
	public String getFronturl() {
		return this.fronturl;
	}

	public void setFronturl(String fronturl) {
		this.fronturl = fronturl;
	}

	@Column(name = "BACKURL", length = 256)
	public String getBackurl() {
		return this.backurl;
	}

	public void setBackurl(String backurl) {
		this.backurl = backurl;
	}

    /**
     * @return the safeurl
     */
	@Column(name = "SAFEURL", length = 256)
    public String getSafeurl() {
        return safeurl;
    }

    /**
     * @param safeurl the safeurl to set
     */
    public void setSafeurl(String safeurl) {
        this.safeurl = safeurl;
    }

    /**
     * @return the chnlmerchno
     */
    @Column(name = "CHNLMERCHNO", length = 15)
    public String getChnlmerchno() {
        return chnlmerchno;
    }

    /**
     * @param chnlmerchno the chnlmerchno to set
     */
    public void setChnlmerchno(String chnlmerchno) {
        this.chnlmerchno = chnlmerchno;
    }

    /**
     * @return the impl
     */
    public String getImpl() {
        return impl;
    }

    /**
     * @param impl the impl to set
     */
    @Column(name = "IMPL", length = 512)
    public void setImpl(String impl) {
        this.impl = impl;
    }

	/**
	 * @return the refundImpl
	 */
    @Column(name = "REFUND_IMPL", length = 512)
	public String getRefundImpl() {
		return refundImpl;
	}

	/**
	 * @param refundImpl the refundImpl to set
	 */
	public void setRefundImpl(String refundImpl) {
		this.refundImpl = refundImpl;
	}
	

}