package com.zlebank.zplatform.trade.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TCashBank entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_CASH_BANK")
public class CashBankModel implements java.io.Serializable {

	// Fields

	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2341789431526414717L;
    private Long tid;
	private String chnlcode;
	private String cashcode;
	private String bankcode;
	private String bankname;
	private String cardtype;
	private String busicode;
	private String ico;
	private String remarks;
	private String notes;
	private String status;
	private String paytype;
	// Constructors

	/** default constructor */
	public CashBankModel() {
	}

	/** minimal constructor */
	public CashBankModel(Long tid) {
		this.tid = tid;
	}

	/** full constructor */
	public CashBankModel(Long tid, String chnlcode, String cashcode,
			String bankcode, String bankname, String cardtype, String busicode,
			String ico, String remarks, String notes, String status) {
		this.tid = tid;
		this.chnlcode = chnlcode;
		this.cashcode = cashcode;
		this.bankcode = bankcode;
		this.bankname = bankname;
		this.cardtype = cardtype;
		this.busicode = busicode;
		this.ico = ico;
		this.remarks = remarks;
		this.notes = notes;
		this.status = status;
	}

	// Property accessors
	@Id
	@Column(name = "TID", unique = true, nullable = false, precision = 12, scale = 0)
	public Long getTid() {
		return this.tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	@Column(name = "CHNLCODE", length = 8)
	public String getChnlcode() {
		return this.chnlcode;
	}

	public void setChnlcode(String chnlcode) {
		this.chnlcode = chnlcode;
	}

	@Column(name = "CASHCODE", length = 8)
	public String getCashcode() {
		return this.cashcode;
	}

	public void setCashcode(String cashcode) {
		this.cashcode = cashcode;
	}

	@Column(name = "BANKCODE", length = 4)
	public String getBankcode() {
		return this.bankcode;
	}

	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}

	@Column(name = "BANKNAME", length = 128)
	public String getBankname() {
		return this.bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	@Column(name = "CARDTYPE", length = 1)
	public String getCardtype() {
		return this.cardtype;
	}

	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}

	@Column(name = "BUSICODE", length = 512)
	public String getBusicode() {
		return this.busicode;
	}

	public void setBusicode(String busicode) {
		this.busicode = busicode;
	}

	@Column(name = "ICO", length = 32)
	public String getIco() {
		return this.ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
	}

	@Column(name = "REMARKS", length = 512)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "NOTES", length = 512)
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Column(name = "STATUS", length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    /**
     * @return the paytype
     */
	@Column(name = "PAYTYPE", length = 2)
    public String getPaytype() {
        return paytype;
    }

    /**
     * @param paytype the paytype to set
     */
    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

}