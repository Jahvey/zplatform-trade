package com.zlebank.zplatform.trade.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TTxnsGatewaypay entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_TXNS_GATEWAYPAY")
public class TxnsGatewaypayModel implements java.io.Serializable {

	// Fields

	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = 5136085938296304922L;
    private Long id;
	private String institution;
	private String payorderno;
	private Long payamt;
	private String paycommtime;
	private String relatetradetxn;
	private String firmemberno;
	private String firmembername;
	private String firmembershortname;
	private String secmemberno;
	private String secmembername;
	private String secmembershortname;
	private String payname;
	private Long paynum;
	private String paycode;
	private String paydescr;
	private String paytype;
	private String status;
	private String notes;
	private String remarks;
	private String payfinshtime;
	
	private String payretcode;
	private String payretinfo;
	private String payrettxnseqno;
	private String bankcode;
	private String closetime;
	
	// Constructors

	/** default constructor */
	public TxnsGatewaypayModel() {
	}

	/** minimal constructor */
	public TxnsGatewaypayModel(Long id) {
		this.id = id;
	}

	/** full constructor */
	public TxnsGatewaypayModel(Long id, String institution, String payorderno,
			Long payamt, String paycommtime, String relatetradetxn,
			String firmemberno, String firmembername, String firmembershortname,
			String secmemberno, String secmembername, String secmembershortname,
			String payname, Long paynum, String paycode, String paydescr,
			String paytype, String status, String notes, String remarks) {
		this.id = id;
		this.institution = institution;
		this.payorderno = payorderno;
		this.payamt = payamt;
		this.paycommtime = paycommtime;
		this.relatetradetxn = relatetradetxn;
		this.firmemberno = firmemberno;
		this.firmembername = firmembername;
		this.firmembershortname = firmembershortname;
		this.secmemberno = secmemberno;
		this.secmembername = secmembername;
		this.secmembershortname = secmembershortname;
		this.payname = payname;
		this.paynum = paynum;
		this.paycode = paycode;
		this.paydescr = paydescr;
		this.paytype = paytype;
		this.status = status;
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

	@Column(name = "INSTITUTION", length = 12)
	public String getInstitution() {
		return this.institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	@Column(name = "PAYORDERNO", length = 40)
	public String getPayorderno() {
		return this.payorderno;
	}

	public void setPayorderno(String payorderno) {
		this.payorderno = payorderno;
	}

	@Column(name = "PAYAMT", precision = 12, scale = 0)
	public Long getPayamt() {
		return this.payamt;
	}

	public void setPayamt(Long payamt) {
		this.payamt = payamt;
	}

	@Column(name = "PAYCOMMTIME", length = 14)
	public String getPaycommtime() {
		return this.paycommtime;
	}

	public void setPaycommtime(String paycommtime) {
		this.paycommtime = paycommtime;
	}

	@Column(name = "RELATETRADETXN", length = 12)
	public String getRelatetradetxn() {
		return this.relatetradetxn;
	}

	public void setRelatetradetxn(String relatetradetxn) {
		this.relatetradetxn = relatetradetxn;
	}

	@Column(name = "FIRMEMBERNO", length = 15)
	public String getFirmemberno() {
		return this.firmemberno;
	}

	public void setFirmemberno(String firmemberno) {
		this.firmemberno = firmemberno;
	}

	@Column(name = "FIRMEMBERNAME", length = 64)
	public String getFirmembername() {
		return this.firmembername;
	}

	public void setFirmembername(String firmembername) {
		this.firmembername = firmembername;
	}

	@Column(name = "FIRMEMBERSHORTNAME", length = 64)
	public String getFirmembershortname() {
		return this.firmembershortname;
	}

	public void setFirmembershortname(String firmembershortname) {
		this.firmembershortname = firmembershortname;
	}

	@Column(name = "SECMEMBERNO", length = 15)
	public String getSecmemberno() {
		return this.secmemberno;
	}

	public void setSecmemberno(String secmemberno) {
		this.secmemberno = secmemberno;
	}

	@Column(name = "SECMEMBERNAME", length = 64)
	public String getSecmembername() {
		return this.secmembername;
	}

	public void setSecmembername(String secmembername) {
		this.secmembername = secmembername;
	}

	@Column(name = "SECMEMBERSHORTNAME", length = 64)
	public String getSecmembershortname() {
		return this.secmembershortname;
	}

	public void setSecmembershortname(String secmembershortname) {
		this.secmembershortname = secmembershortname;
	}

	@Column(name = "PAYNAME", length = 64)
	public String getPayname() {
		return this.payname;
	}

	public void setPayname(String payname) {
		this.payname = payname;
	}

	@Column(name = "PAYNUM", precision = 12, scale = 0)
	public Long getPaynum() {
		return this.paynum;
	}

	public void setPaynum(Long paynum) {
		this.paynum = paynum;
	}

	@Column(name = "PAYCODE", length = 12)
	public String getPaycode() {
		return this.paycode;
	}

	public void setPaycode(String paycode) {
		this.paycode = paycode;
	}

	@Column(name = "PAYDESCR", length = 256)
	public String getPaydescr() {
		return this.paydescr;
	}

	public void setPaydescr(String paydescr) {
		this.paydescr = paydescr;
	}

	@Column(name = "PAYTYPE", length = 12)
	public String getPaytype() {
		return this.paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
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

    /**
     * @return the payfinshtime
     */
     @Column(name = "PAYFINSHTIME", length = 14)
    public String getPayfinshtime() {
        return payfinshtime;
    }

    /**
     * @param payfinshtime the payfinshtime to set
     */
    public void setPayfinshtime(String payfinshtime) {
        this.payfinshtime = payfinshtime;
    }

    /**
     * @return the payretcode
     */
    @Column(name = "PAYRETCODE", length = 8)
    public String getPayretcode() {
        return payretcode;
    }

    /**
     * @param payretcode the payretcode to set
     */
    public void setPayretcode(String payretcode) {
        this.payretcode = payretcode;
    }

    /**
     * @return the payretinfo
     */
    @Column(name = "PAYRETINFO", length = 256)
    public String getPayretinfo() {
        return payretinfo;
    }

    /**
     * @param payretinfo the payretinfo to set
     */
    public void setPayretinfo(String payretinfo) {
        this.payretinfo = payretinfo;
    }

    /**
     * @return the payrettxnseqno
     */
    @Column(name = "PAYRETTXNSEQNO", length = 32)
    public String getPayrettxnseqno() {
        return payrettxnseqno;
    }

    /**
     * @param payrettxnseqno the payrettxnseqno to set
     */
    public void setPayrettxnseqno(String payrettxnseqno) {
        this.payrettxnseqno = payrettxnseqno;
    }

	/**
	 * @return the bankcode
	 */
	public String getBankcode() {
		return bankcode;
	}

	/**
	 * @param bankcode the bankcode to set
	 */
	@Column(name = "BANKCODE")
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}

	/**
	 * @return the closetime
	 */
	public String getClosetime() {
		return closetime;
	}

	/**
	 * @param closetime the closetime to set
	 */
	@Column(name = "CLOSETIME")
	public void setClosetime(String closetime) {
		this.closetime = closetime;
	}

	
}