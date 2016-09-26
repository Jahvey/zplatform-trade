package com.zlebank.zplatform.trade.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.trade.bean.wap.WapRefundBean;
import com.zlebank.zplatform.trade.utils.OrderNumber;

/**
 * TTxnsRefund entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_TXNS_REFUND")
public class TxnsRefundModel implements java.io.Serializable {

	// Fields

	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4768281398006737392L;
    private Long id;
	private String refundorderno;
	private String oldorderno;
	private String oldtxnseqno;
	private String merchno;
	private String submerchno;
	private String memberid;
	private Long amount;
	private Long oldamount;
	private String refundtype;
	private String refunddesc;
	private String reltxnseqno;
	private String txntime;
	private String finishtime;
	private String refundinstid;
	private String status;
	private String retcode;
	private String retinfo;
	private String notes;
	private String remarks;
	private String stexaopt;
	private String relorderno;
	  /**通过还是拒绝*/
   private String flag; 
	 //Constructors

	/** default constructor */
	public TxnsRefundModel() {
    }
	
	public TxnsRefundModel(WapRefundBean refundBean,String oldtxnseqno,String oldamount,String reltxnseqno) {
	    this.id = OrderNumber.getInstance().generateID();
        this.refundorderno = OrderNumber.getInstance().generateRefundOrderNo();
        this.oldorderno = refundBean.getOrigOrderId();
        this.oldtxnseqno = oldtxnseqno;
        this.merchno = refundBean.getCoopInstiId();
        this.submerchno = refundBean.getMerId();
        this.memberid = refundBean.getMemberId();
        this.amount = Long.valueOf(refundBean.getTxnAmt());
        this.oldamount = Long.valueOf(oldamount);
        this.refundtype =  refundBean.getRefundType();
        this.refunddesc =  refundBean.getOrderDesc();
        this.reltxnseqno = reltxnseqno;
        this.txntime = DateUtil.getCurrentDateTime();
        
        this.status = "01";
        
	}
	
	/** minimal constructor */
	public TxnsRefundModel(Long id, String refundorderno, String oldorderno,
			String oldtxnseqno, Long amount, Long oldamount, String reltxnseqno) {
		this.id = id;
		this.refundorderno = refundorderno;
		this.oldorderno = oldorderno;
		this.oldtxnseqno = oldtxnseqno;
		this.amount = amount;
		this.oldamount = oldamount;
		this.reltxnseqno = reltxnseqno;
	}

	/** full constructor */
	public TxnsRefundModel(Long id, String refundorderno, String oldorderno,
			String oldtxnseqno, String merchno, String submerchno,
			String memberid, Long amount, Long oldamount, String refundtype,
			String refunddesc, String reltxnseqno, String txntime,
			String finishtime, String refundinstid, String status,
			String retcode, String retinfo, String notes, String remarks) {
		this.id = id;
		this.refundorderno = refundorderno;
		this.oldorderno = oldorderno;
		this.oldtxnseqno = oldtxnseqno;
		this.merchno = merchno;
		this.submerchno = submerchno;
		this.memberid = memberid;
		this.amount = amount;
		this.oldamount = oldamount;
		this.refundtype = refundtype;
		this.refunddesc = refunddesc;
		this.reltxnseqno = reltxnseqno;
		this.txntime = txntime;
		this.finishtime = finishtime;
		this.refundinstid = refundinstid;
		this.status = status;
		this.retcode = retcode;
		this.retinfo = retinfo;
		this.notes = notes;
		this.remarks = remarks;
	}

	// Property accessors
	@GenericGenerator(name = "id_gen", strategy = "enhanced-table", parameters = {
            @Parameter(name = "table_name", value = "T_C_PRIMAY_KEY"),
            @Parameter(name = "value_column_name", value = "NEXT_ID"),
            @Parameter(name = "segment_column_name", value = "KEY_NAME"),
            @Parameter(name = "segment_value", value = "T_TXNS_REFUSE_ID"),
            @Parameter(name = "increment_size", value = "1"),
            @Parameter(name = "optimizer", value = "pooled-lo") })
    @Id
    @GeneratedValue(generator = "id_gen")
	@Column(name = "ID", unique = true, nullable = false, precision = 12, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "REFUNDORDERNO", nullable = false, length = 32)
	public String getRefundorderno() {
		return this.refundorderno;
	}

	public void setRefundorderno(String refundorderno) {
		this.refundorderno = refundorderno;
	}

	@Column(name = "OLDORDERNO", nullable = false, length = 32)
	public String getOldorderno() {
		return this.oldorderno;
	}

	public void setOldorderno(String oldorderno) {
		this.oldorderno = oldorderno;
	}

	@Column(name = "OLDTXNSEQNO", nullable = false, length = 16)
	public String getOldtxnseqno() {
		return this.oldtxnseqno;
	}

	public void setOldtxnseqno(String oldtxnseqno) {
		this.oldtxnseqno = oldtxnseqno;
	}

	@Column(name = "MERCHNO", length = 15)
	public String getMerchno() {
		return this.merchno;
	}

	public void setMerchno(String merchno) {
		this.merchno = merchno;
	}

	@Column(name = "SUBMERCHNO", length = 15)
	public String getSubmerchno() {
		return this.submerchno;
	}

	public void setSubmerchno(String submerchno) {
		this.submerchno = submerchno;
	}

	@Column(name = "MEMBERID", length = 15)
	public String getMemberid() {
		return this.memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	@Column(name = "AMOUNT", nullable = false, precision = 12, scale = 0)
	public Long getAmount() {
		return this.amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	@Column(name = "OLDAMOUNT", nullable = false, precision = 12, scale = 0)
	public Long getOldamount() {
		return this.oldamount;
	}

	public void setOldamount(Long oldamount) {
		this.oldamount = oldamount;
	}

	@Column(name = "REFUNDTYPE", length = 1)
	public String getRefundtype() {
		return this.refundtype;
	}

	public void setRefundtype(String refundtype) {
		this.refundtype = refundtype;
	}

	@Column(name = "REFUNDDESC", length = 256)
	public String getRefunddesc() {
		return this.refunddesc;
	}

	public void setRefunddesc(String refunddesc) {
		this.refunddesc = refunddesc;
	}

	@Column(name = "RELTXNSEQNO", nullable = false, length = 16)
	public String getReltxnseqno() {
		return this.reltxnseqno;
	}

	public void setReltxnseqno(String reltxnseqno) {
		this.reltxnseqno = reltxnseqno;
	}

	@Column(name = "TXNTIME", length = 14)
	public String getTxntime() {
		return this.txntime;
	}

	public void setTxntime(String txntime) {
		this.txntime = txntime;
	}

	@Column(name = "FINISHTIME", length = 14)
	public String getFinishtime() {
		return this.finishtime;
	}

	public void setFinishtime(String finishtime) {
		this.finishtime = finishtime;
	}

	@Column(name = "REFUNDINSTID", length = 8)
	public String getRefundinstid() {
		return this.refundinstid;
	}

	public void setRefundinstid(String refundinstid) {
		this.refundinstid = refundinstid;
	}

	@Column(name = "STATUS", length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "RETCODE", length = 8)
	public String getRetcode() {
		return this.retcode;
	}

	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}

	@Column(name = "RETINFO", length = 256)
	public String getRetinfo() {
		return this.retinfo;
	}

	public void setRetinfo(String retinfo) {
		this.retinfo = retinfo;
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
	@Column(name = "STEXAOPT", length = 256)
    public String getStexaopt() {
        return stexaopt;
    }

    public void setStexaopt(String stexaopt) {
        this.stexaopt = stexaopt;
    }
    @Transient
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

	/**
	 * @return the relorderno
	 */
    @Column(name = "RELORDERNO")
	public String getRelorderno() {
		return relorderno;
	}

	/**
	 * @param relorderno the relorderno to set
	 */
	public void setRelorderno(String relorderno) {
		this.relorderno = relorderno;
	}
    

}
