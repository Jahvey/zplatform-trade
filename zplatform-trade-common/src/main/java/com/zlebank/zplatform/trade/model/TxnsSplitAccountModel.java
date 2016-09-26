package com.zlebank.zplatform.trade.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.zlebank.zplatform.trade.bean.gateway.SplitAcctBean;
import com.zlebank.zplatform.trade.utils.OrderNumber;

/**
 * TTxnsSplitAccount entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_TXNS_SPLIT_ACCOUNT")
public class TxnsSplitAccountModel implements java.io.Serializable {

	// Fields

	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2260616046518563950L;
    private Long tid;
	private String memberid;
	private Long amount;
	private Long commamt;
	private String txnseqno;
	private String remarks;
	private String notes;

	// Constructors

	/** default constructor */
	public TxnsSplitAccountModel() {
	}

	/** minimal constructor */
	public TxnsSplitAccountModel(Long tid) {
		this.tid = tid;
	}

	/** full constructor */
	public TxnsSplitAccountModel(Long tid, String memberid, Long amount,
			String txnseqno, String remarks, String notes) {
		this.tid = tid;
		this.memberid = memberid;
		this.amount = amount;
		this.txnseqno = txnseqno;
		this.remarks = remarks;
		this.notes = notes;
	}
	public TxnsSplitAccountModel(SplitAcctBean bean,String txnseqno) {
        this.tid = OrderNumber.getInstance().generateID();
        this.memberid = bean.getMerId();
        this.amount = Long.valueOf(bean.getTxnAmt());
        this.commamt = Long.valueOf(bean.getCommAmt());
        this.txnseqno = txnseqno;
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

	@Column(name = "MEMBERID", length = 15)
	public String getMemberid() {
		return this.memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	@Column(name = "AMOUNT", precision = 12, scale = 0)
	public Long getAmount() {
		return this.amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	@Column(name = "TXNSEQNO", length = 16)
	public String getTxnseqno() {
		return this.txnseqno;
	}

	public void setTxnseqno(String txnseqno) {
		this.txnseqno = txnseqno;
	}

	@Column(name = "REMARKS", length = 256)
	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "NOTES", length = 256)
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

    /**
     * @return the commamt
     */
	@Column(name = "COMMAMT", precision = 12, scale = 0)
    public Long getCommamt() {
        return commamt;
    }

    /**
     * @param commamt the commamt to set
     */
    public void setCommamt(Long commamt) {
        this.commamt = commamt;
    }

}