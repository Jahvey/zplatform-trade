package com.zlebank.zplatform.trade.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TTxnsOrderinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_TXNS_ORDERINFO")
public class TxnsOrderinfoModel implements java.io.Serializable {

 // Fields

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2364932640720308188L;
    private Long id;
    private String institution;
    private String orderno;
    private Long orderamt;
    private Long ordercomm;
    private Long orderfee;
    private String ordercommitime;
    private String firmemberno;
    private String firmembername;
    private String firmembershortname;
    private String secmemberno;
    private String secmembername;
    private String secmembershortname;
    private String goodsname;
    private Long goodsnum;
    private String goodscode;
    private String goodsdescr;
    private String goodstype;
    private Long goodsprice;
    private String fronturl;
    private String backurl;
    private String relatetradetxn;
    private String orderfinshtime;
    private String status;
    private String notes;
    private String remarks;
    private String txntype;
    private String txnsubtype;
    private String biztype;
    //private String merchorderno;
    private String certid;
    private String reqreserved;
    private String reserved;
    private String customerInfo;
    private String tn;
    private String orderdesc;
    private String paytimeout;
    private String payerip;
    private String syncnotify="01";
    private String accesstype;
    private String currencycode="156";
    private String memberid;
    private String productcode;
    private String groupcode;
    
    // Constructors

    /** default constructor */
    public TxnsOrderinfoModel() {
    }

    /** minimal constructor */
    public TxnsOrderinfoModel(Long id) {
        this.id = id;
    }

    /** full constructor */
    public TxnsOrderinfoModel(Long id, String institution, String orderno,
            Long orderamt, Long ordercomm, Long orderfee,
            String ordercommitime, String firmemberno, String firmembername,
            String firmembershortname, String secmemberno, String secmembername,
            String secmembershortname, String goodsname, Long goodsnum,
            String goodscode, String goodsdescr, String goodstype,
            Long goodsprice, String fronturl, String backurl,
            String relatetradetxn, String orderfinshtime, String status,
            String notes, String remarks, String txntype, String txnsubtype,
            String biztype) {
        this.id = id;
        this.institution = institution;
        this.orderno = orderno;
        this.orderamt = orderamt;
        this.ordercomm = ordercomm;
        this.orderfee = orderfee;
        this.ordercommitime = ordercommitime;
        this.firmemberno = firmemberno;
        this.firmembername = firmembername;
        this.firmembershortname = firmembershortname;
        this.secmemberno = secmemberno;
        this.secmembername = secmembername;
        this.secmembershortname = secmembershortname;
        this.goodsname = goodsname;
        this.goodsnum = goodsnum;
        this.goodscode = goodscode;
        this.goodsdescr = goodsdescr;
        this.goodstype = goodstype;
        this.goodsprice = goodsprice;
        this.fronturl = fronturl;
        this.backurl = backurl;
        this.relatetradetxn = relatetradetxn;
        this.orderfinshtime = orderfinshtime;
        this.status = status;
        this.notes = notes;
        this.remarks = remarks;
        this.txntype = txntype;
        this.txnsubtype = txnsubtype;
        this.biztype = biztype;
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

    @Column(name = "ORDERNO", length = 40)
    public String getOrderno() {
        return this.orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    @Column(name = "ORDERAMT", precision = 12, scale = 0)
    public Long getOrderamt() {
        return this.orderamt;
    }

    public void setOrderamt(Long orderamt) {
        this.orderamt = orderamt;
    }

    @Column(name = "ORDERCOMM", precision = 12, scale = 0)
    public Long getOrdercomm() {
        return this.ordercomm;
    }

    public void setOrdercomm(Long ordercomm) {
        this.ordercomm = ordercomm;
    }

    @Column(name = "ORDERFEE", precision = 12, scale = 0)
    public Long getOrderfee() {
        return this.orderfee;
    }

    public void setOrderfee(Long orderfee) {
        this.orderfee = orderfee;
    }

    @Column(name = "ORDERCOMMITIME", length = 14)
    public String getOrdercommitime() {
        return this.ordercommitime;
    }

    public void setOrdercommitime(String ordercommitime) {
        this.ordercommitime = ordercommitime;
    }

    @Column(name = "FIRMEMBERNO", precision = 15, scale = 0)
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

    @Column(name = "SECMEMBERNO", precision = 15, scale = 0)
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

    @Column(name = "GOODSNAME", length = 64)
    public String getGoodsname() {
        return this.goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    @Column(name = "GOODSNUM", precision = 12, scale = 0)
    public Long getGoodsnum() {
        return this.goodsnum;
    }

    public void setGoodsnum(Long goodsnum) {
        this.goodsnum = goodsnum;
    }

    @Column(name = "GOODSCODE", length = 12)
    public String getGoodscode() {
        return this.goodscode;
    }

    public void setGoodscode(String goodscode) {
        this.goodscode = goodscode;
    }

    @Column(name = "GOODSDESCR", length = 256)
    public String getGoodsdescr() {
        return this.goodsdescr;
    }

    public void setGoodsdescr(String goodsdescr) {
        this.goodsdescr = goodsdescr;
    }

    @Column(name = "GOODSTYPE", length = 12)
    public String getGoodstype() {
        return this.goodstype;
    }

    public void setGoodstype(String goodstype) {
        this.goodstype = goodstype;
    }

    @Column(name = "GOODSPRICE", precision = 12, scale = 0)
    public Long getGoodsprice() {
        return this.goodsprice;
    }

    public void setGoodsprice(Long goodsprice) {
        this.goodsprice = goodsprice;
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

    @Column(name = "RELATETRADETXN", length = 16)
    public String getRelatetradetxn() {
        return this.relatetradetxn;
    }

    public void setRelatetradetxn(String relatetradetxn) {
        this.relatetradetxn = relatetradetxn;
    }

    @Column(name = "ORDERFINSHTIME", length = 14)
    public String getOrderfinshtime() {
        return this.orderfinshtime;
    }

    public void setOrderfinshtime(String orderfinshtime) {
        this.orderfinshtime = orderfinshtime;
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

    @Column(name = "TXNTYPE", length = 2)
    public String getTxntype() {
        return this.txntype;
    }

    public void setTxntype(String txntype) {
        this.txntype = txntype;
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
     * @return the certid
     */
    @Column(name = "CERTID", length = 12)
    public String getCertid() {
        return certid;
    }

    /**
     * @param certid the certid to set
     */
    public void setCertid(String certid) {
        this.certid = certid;
    }

    /**
     * @return the reqreserved
     */
    @Column(name = "REQRESERVED", length = 2048)
    public String getReqreserved() {
        return reqreserved;
    }

    /**
     * @param reqreserved the reqreserved to set
     */
    public void setReqreserved(String reqreserved) {
        this.reqreserved = reqreserved;
    }

    /**
     * @return the reserved
     */
    @Column(name = "RESERVED", length = 2048)
    public String getReserved() {
        return reserved;
    }

    /**
     * @param reserved the reserved to set
     */
    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    

    /**
     * @return the customerInfo
     */
    @Column(name = "CUSTOMERINFO", length = 2048)
    public String getCustomerInfo() {
        return customerInfo;
    }

    /**
     * @param customerInfo the customerInfo to set
     */
    public void setCustomerInfo(String customerInfo) {
        this.customerInfo = customerInfo;
    }

    /**
     * @return the tn
     */
    @Column(name = "TN", length = 32)
    public String getTn() {
        return tn;
    }

    /**
     * @param tn the tn to set
     */
    public void setTn(String tn) {
        this.tn = tn;
    }

    /**
     * @return the orderdesc
     */
    @Column(name = "ORDERDESC", length = 128)
    public String getOrderdesc() {
        return orderdesc;
    }

    /**
     * @param orderdesc the orderdesc to set
     */
    public void setOrderdesc(String orderdesc) {
        this.orderdesc = orderdesc;
    }

    /**
     * @return the paytimeout
     */
    @Column(name = "PAYTIMEOUT", length = 14)
    public String getPaytimeout() {
        return paytimeout;
    }

    /**
     * @param paytimeout the paytimeout to set
     */
    public void setPaytimeout(String paytimeout) {
        this.paytimeout = paytimeout;
    }

    /**
     * @return the payerip
     */
    @Column(name = "PAYERIP", length = 32)
    public String getPayerip() {
        return payerip;
    }

    /**
     * @param payerip the payerip to set
     */
    public void setPayerip(String payerip) {
        this.payerip = payerip;
    }

    /**
     * @return the syncnotify
     */
    @Column(name = "SYNCNOTIFY", length = 2)
    public String getSyncnotify() {
        return syncnotify;
    }

    /**
     * @param syncnotify the syncnotify to set
     */
    public void setSyncnotify(String syncnotify) {
        this.syncnotify = syncnotify;
    }

	/**
	 * @return the accesstype
	 */
    @Column(name = "ACCESSTYPE", length = 2)
	public String getAccesstype() {
		return accesstype;
	}

	/**
	 * @param accesstype the accesstype to set
	 */
	public void setAccesstype(String accesstype) {
		this.accesstype = accesstype;
	}

	/**
	 * @return the currencycode
	 */
	@Column(name = "CURRENCYCODE", length = 3)
	public String getCurrencycode() {
		return currencycode;
	}

	/**
	 * @param currencycode the currencycode to set
	 */
	public void setCurrencycode(String currencycode) {
		this.currencycode = currencycode;
	}

	/**
	 * @return the memberid
	 */
	@Column(name = "MEMBERID", length = 15)
	public String getMemberid() {
		return memberid;
	}

	/**
	 * @param memberid the memberid to set
	 */
	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	/**
	 * @return the productcode
	 */
	@Column(name = "PRODUCTCODE")
	public String getProductcode() {
		return productcode;
	}

	/**
	 * @param productcode the productcode to set
	 */
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}

	/**
	 * @return the groupcode
	 */
	@Column(name = "GROUPCODE")
	public String getGroupcode() {
		return groupcode;
	}

	/**
	 * @param groupcode the groupcode to set
	 */
	public void setGroupcode(String groupcode) {
		this.groupcode = groupcode;
	}

	
    
    
   
    
}