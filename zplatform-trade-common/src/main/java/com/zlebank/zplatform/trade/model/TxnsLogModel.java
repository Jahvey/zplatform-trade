package com.zlebank.zplatform.trade.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TTxnsLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_TXNS_LOG")
public class TxnsLogModel implements java.io.Serializable {

 // Fields

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 294757514028433150L;
	private String txnseqno;
    private String txndate;
    private String txntime;
    private String apptype;
    private String busitype;
    private String busicode;
    private Long amount;
    private Long tradcomm;
    private Long txnfee;
    private String riskver;
    private String splitver;
    private String feever;
    private String prdtver;
    private String checkstandver;
    private String routver;
    private String pan;
    private String cardtype;
    private String cardinstino;
    private String inpan;
    private String incardtype;
    private String incardinstino;
    private String accordno;
    private String accordinst;
    private String accsecmerno;
    private String accfirmerno;
    private String accsettledate;
    private String accordcommitime;
    private String accordfintime;
    private String paytype;
    private String payordno;
    private String payinst;
    private String payfirmerno;
    private String paysecmerno;
    private String payordcomtime;
    private String payordfintime;
    private String payrettsnseqno;
    private String payretcode;
    private String payretinfo;
    private String appordno;
    private String appinst;
    private String appordcommitime;
    private String appordfintime;
    private String tradeseltxn;
    private String retcode;
    private String retinfo;
    private String tradestatflag;
    private String tradetxnflag;
    private String txncode;
    private String cashcode;
    private String relate;
    private String retdatetime;
    private String txnseqnoOg;
    private String notes;
    private String remarks;

    private String accmemberid;
    private String apporderstatus;
    private String apporderinfo;
    private String accbusicode;
    private String acccoopinstino;
    private String panName;
    private String productcode;
    // Constructors

    /** default constructor */
    public TxnsLogModel() {
    }

    /** minimal constructor */
    public TxnsLogModel(String txnseqno) {
        this.txnseqno = txnseqno;
    }

    /** full constructor */
    public TxnsLogModel(String txnseqno, String txndate, String txntime,
            String apptype, String busitype, String busicode, Long amount,
            Long tradcomm, Long txnfee, String riskver, String splitver,
            String feever, String prdtver, String checkstandver,
            String routver, String pan, String cardtype, String cardinstino,
            String inpan, String incardtype, String incardinstino,
            String accordno, String accordinst, String accsecmerno,
            String accfirmerno, String accsettledate, String accordcommitime,
            String accordfintime, String paytype, String payordno,
            String payinst, String payfirmerno, String paysecmerno,
            String payordcomtime, String payordfintime, String payrettsnseqno,
            String payretcode, String payretinfo, String appordno,
            String appinst, String appordcommitime, String appordfintime,
            String tradeseltxn, String retcode, String retinfo,
            String tradestatflag, String tradetxnflag, String txncode,
            String cashcode, String relate, String retdatetime,
            String txnseqnoOg, String notes, String remarks) {
        this.txnseqno = txnseqno;
        this.txndate = txndate;
        this.txntime = txntime;
        this.apptype = apptype;
        this.busitype = busitype;
        this.busicode = busicode;
        this.amount = amount;
        this.tradcomm = tradcomm;
        this.txnfee = txnfee;
        this.riskver = riskver;
        this.splitver = splitver;
        this.feever = feever;
        this.prdtver = prdtver;
        this.checkstandver = checkstandver;
        this.routver = routver;
        this.pan = pan;
        this.cardtype = cardtype;
        this.cardinstino = cardinstino;
        this.inpan = inpan;
        this.incardtype = incardtype;
        this.incardinstino = incardinstino;
        this.accordno = accordno;
        this.accordinst = accordinst;
        this.accsecmerno = accsecmerno;
        this.accfirmerno = accfirmerno;
        this.accsettledate = accsettledate;
        this.accordcommitime = accordcommitime;
        this.accordfintime = accordfintime;
        this.paytype = paytype;
        this.payordno = payordno;
        this.payinst = payinst;
        this.payfirmerno = payfirmerno;
        this.paysecmerno = paysecmerno;
        this.payordcomtime = payordcomtime;
        this.payordfintime = payordfintime;
        this.payrettsnseqno = payrettsnseqno;
        this.payretcode = payretcode;
        this.payretinfo = payretinfo;
        this.appordno = appordno;
        this.appinst = appinst;
        this.appordcommitime = appordcommitime;
        this.appordfintime = appordfintime;
        this.tradeseltxn = tradeseltxn;
        this.retcode = retcode;
        this.retinfo = retinfo;
        this.tradestatflag = tradestatflag;
        this.tradetxnflag = tradetxnflag;
        this.txncode = txncode;
        this.cashcode = cashcode;
        this.relate = relate;
        this.retdatetime = retdatetime;
        this.txnseqnoOg = txnseqnoOg;
        this.notes = notes;
        this.remarks = remarks;
    }
    
    
    

    
    // Property accessors
    @Id
    @Column(name = "TXNSEQNO", unique = true, nullable = false, length = 16)
    public String getTxnseqno() {
        return this.txnseqno;
    }

    public void setTxnseqno(String txnseqno) {
        this.txnseqno = txnseqno;
    }

    @Column(name = "TXNDATE", length = 8)
    public String getTxndate() {
        return this.txndate;
    }

    public void setTxndate(String txndate) {
        this.txndate = txndate;
    }

    @Column(name = "TXNTIME", length = 6)
    public String getTxntime() {
        return this.txntime;
    }

    public void setTxntime(String txntime) {
        this.txntime = txntime;
    }

    @Column(name = "APPTYPE", length = 4)
    public String getApptype() {
        return this.apptype;
    }

    public void setApptype(String apptype) {
        this.apptype = apptype;
    }

    @Column(name = "BUSITYPE", length = 4)
    public String getBusitype() {
        return this.busitype;
    }

    public void setBusitype(String busitype) {
        this.busitype = busitype;
    }

    @Column(name = "BUSICODE", length = 8)
    public String getBusicode() {
        return this.busicode;
    }

    public void setBusicode(String busicode) {
        this.busicode = busicode;
    }

    @Column(name = "AMOUNT", precision = 12, scale = 0)
    public Long getAmount() {
        return this.amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @Column(name = "TRADCOMM", precision = 10, scale = 0)
    public Long getTradcomm() {
        return this.tradcomm;
    }

    public void setTradcomm(Long tradcomm) {
        this.tradcomm = tradcomm;
    }

    @Column(name = "TXNFEE", precision = 10, scale = 0)
    public Long getTxnfee() {
        return this.txnfee;
    }

    public void setTxnfee(Long txnfee) {
        this.txnfee = txnfee;
    }

    @Column(name = "RISKVER", length = 8)
    public String getRiskver() {
        return this.riskver;
    }

    public void setRiskver(String riskver) {
        this.riskver = riskver;
    }

    @Column(name = "SPLITVER", length = 8)
    public String getSplitver() {
        return this.splitver;
    }

    public void setSplitver(String splitver) {
        this.splitver = splitver;
    }

    @Column(name = "FEEVER", length = 8)
    public String getFeever() {
        return this.feever;
    }

    public void setFeever(String feever) {
        this.feever = feever;
    }

    @Column(name = "PRDTVER", length = 8)
    public String getPrdtver() {
        return this.prdtver;
    }

    public void setPrdtver(String prdtver) {
        this.prdtver = prdtver;
    }

    @Column(name = "CHECKSTANDVER", length = 8)
    public String getCheckstandver() {
        return this.checkstandver;
    }

    public void setCheckstandver(String checkstandver) {
        this.checkstandver = checkstandver;
    }

    @Column(name = "ROUTVER", length = 8)
    public String getRoutver() {
        return this.routver;
    }

    public void setRoutver(String routver) {
        this.routver = routver;
    }

    @Column(name = "PAN", length = 40)
    public String getPan() {
        return this.pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    @Column(name = "CARDTYPE", length = 1)
    public String getCardtype() {
        return this.cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    @Column(name = "CARDINSTINO", length = 12)
    public String getCardinstino() {
        return this.cardinstino;
    }

    public void setCardinstino(String cardinstino) {
        this.cardinstino = cardinstino;
    }

    @Column(name = "INPAN", length = 40)
    public String getInpan() {
        return this.inpan;
    }

    public void setInpan(String inpan) {
        this.inpan = inpan;
    }

    @Column(name = "INCARDTYPE", length = 1)
    public String getIncardtype() {
        return this.incardtype;
    }

    public void setIncardtype(String incardtype) {
        this.incardtype = incardtype;
    }

    @Column(name = "INCARDINSTINO", length = 12)
    public String getIncardinstino() {
        return this.incardinstino;
    }

    public void setIncardinstino(String incardinstino) {
        this.incardinstino = incardinstino;
    }

    @Column(name = "ACCORDNO", length = 40)
    public String getAccordno() {
        return this.accordno;
    }

    public void setAccordno(String accordno) {
        this.accordno = accordno;
    }

    @Column(name = "ACCORDINST", length = 12)
    public String getAccordinst() {
        return this.accordinst;
    }

    public void setAccordinst(String accordinst) {
        this.accordinst = accordinst;
    }

    @Column(name = "ACCSECMERNO", length = 15)
    public String getAccsecmerno() {
        return this.accsecmerno;
    }

    public void setAccsecmerno(String accsecmerno) {
        this.accsecmerno = accsecmerno;
    }

    @Column(name = "ACCFIRMERNO", length = 15)
    public String getAccfirmerno() {
        return this.accfirmerno;
    }

    public void setAccfirmerno(String accfirmerno) {
        this.accfirmerno = accfirmerno;
    }

    @Column(name = "ACCSETTLEDATE", length = 8)
    public String getAccsettledate() {
        return this.accsettledate;
    }

    public void setAccsettledate(String accsettledate) {
        this.accsettledate = accsettledate;
    }

    @Column(name = "ACCORDCOMMITIME", length = 14)
    public String getAccordcommitime() {
        return this.accordcommitime;
    }

    public void setAccordcommitime(String accordcommitime) {
        this.accordcommitime = accordcommitime;
    }

    @Column(name = "ACCORDFINTIME", length = 14)
    public String getAccordfintime() {
        return this.accordfintime;
    }

    public void setAccordfintime(String accordfintime) {
        this.accordfintime = accordfintime;
    }

    @Column(name = "PAYTYPE", length = 12)
    public String getPaytype() {
        return this.paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    @Column(name = "PAYORDNO", length = 40)
    public String getPayordno() {
        return this.payordno;
    }

    public void setPayordno(String payordno) {
        this.payordno = payordno;
    }

    @Column(name = "PAYINST", length = 12)
    public String getPayinst() {
        return this.payinst;
    }

    public void setPayinst(String payinst) {
        this.payinst = payinst;
    }

    @Column(name = "PAYFIRMERNO", length = 15)
    public String getPayfirmerno() {
        return this.payfirmerno;
    }

    public void setPayfirmerno(String payfirmerno) {
        this.payfirmerno = payfirmerno;
    }

    @Column(name = "PAYSECMERNO", length = 15)
    public String getPaysecmerno() {
        return this.paysecmerno;
    }

    public void setPaysecmerno(String paysecmerno) {
        this.paysecmerno = paysecmerno;
    }

    @Column(name = "PAYORDCOMTIME", length = 14)
    public String getPayordcomtime() {
        return this.payordcomtime;
    }

    public void setPayordcomtime(String payordcomtime) {
        this.payordcomtime = payordcomtime;
    }

    @Column(name = "PAYORDFINTIME", length = 14)
    public String getPayordfintime() {
        return this.payordfintime;
    }

    public void setPayordfintime(String payordfintime) {
        this.payordfintime = payordfintime;
    }

    @Column(name = "PAYRETTSNSEQNO", length = 32)
    public String getPayrettsnseqno() {
        return this.payrettsnseqno;
    }

    public void setPayrettsnseqno(String payrettsnseqno) {
        this.payrettsnseqno = payrettsnseqno;
    }

    @Column(name = "PAYRETCODE", length = 8)
    public String getPayretcode() {
        return this.payretcode;
    }

    public void setPayretcode(String payretcode) {
        this.payretcode = payretcode;
    }

    @Column(name = "PAYRETINFO", length = 256)
    public String getPayretinfo() {
        return this.payretinfo;
    }

    public void setPayretinfo(String payretinfo) {
        this.payretinfo = payretinfo;
    }

    @Column(name = "APPORDNO", length = 40)
    public String getAppordno() {
        return this.appordno;
    }

    public void setAppordno(String appordno) {
        this.appordno = appordno;
    }

    @Column(name = "APPINST", length = 12)
    public String getAppinst() {
        return this.appinst;
    }

    public void setAppinst(String appinst) {
        this.appinst = appinst;
    }

    @Column(name = "APPORDCOMMITIME", length = 14)
    public String getAppordcommitime() {
        return this.appordcommitime;
    }

    public void setAppordcommitime(String appordcommitime) {
        this.appordcommitime = appordcommitime;
    }

    @Column(name = "APPORDFINTIME", length = 14)
    public String getAppordfintime() {
        return this.appordfintime;
    }

    public void setAppordfintime(String appordfintime) {
        this.appordfintime = appordfintime;
    }

    @Column(name = "TRADESELTXN", length = 32)
    public String getTradeseltxn() {
        return this.tradeseltxn;         
    }

    public void setTradeseltxn(String tradeseltxn) {
        this.tradeseltxn = tradeseltxn;
    }

    @Column(name = "RETCODE", length = 4)
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

    @Column(name = "TRADESTATFLAG", length = 12)
    public String getTradestatflag() {
        return this.tradestatflag;
    }

    public void setTradestatflag(String tradestatflag) {
        this.tradestatflag = tradestatflag;
    }

    @Column(name = "TRADETXNFLAG", length = 12)
    public String getTradetxnflag() {
        return this.tradetxnflag;
    }

    public void setTradetxnflag(String tradetxnflag) {
        this.tradetxnflag = tradetxnflag;
    }

    @Column(name = "TXNCODE", length = 8)
    public String getTxncode() {
        return this.txncode;
    }

    public void setTxncode(String txncode) {
        this.txncode = txncode;
    }

    @Column(name = "CASHCODE", length = 8)
    public String getCashcode() {
        return this.cashcode;
    }

    public void setCashcode(String cashcode) {
        this.cashcode = cashcode;
    }

    @Column(name = "RELATE", length = 32)
    public String getRelate() {
        return this.relate;
    }

    public void setRelate(String relate) {
        this.relate = relate;
    }

    @Column(name = "RETDATETIME", length = 14)
    public String getRetdatetime() {
        return this.retdatetime;
    }

    public void setRetdatetime(String retdatetime) {
        this.retdatetime = retdatetime;
    }

    @Column(name = "TXNSEQNO_OG", length = 16)
    public String getTxnseqnoOg() {
        return this.txnseqnoOg;
    }

    public void setTxnseqnoOg(String txnseqnoOg) {
        this.txnseqnoOg = txnseqnoOg;
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
     * @return the accmemberid
     */
    @Column(name = "ACCMEMBERID", length = 15)
    public String getAccmemberid() {
        return accmemberid;
    }

    /**
     * @param accmemberid the accmemberid to set
     */
    public void setAccmemberid(String accmemberid) {
        this.accmemberid = accmemberid;
    }

    /**
     * @return the apporderstatus
     */
    @Column(name = "APPORDERSTATUS", length = 2)
    public String getApporderstatus() {
        return apporderstatus;
    }

    /**
     * @param apporderstatus the apporderstatus to set
     */
    public void setApporderstatus(String apporderstatus) {
        this.apporderstatus = apporderstatus;
    }

    /**
     * @return the apporderinfo
     */
    @Column(name = "APPORDERINFO", length = 256)
    public String getApporderinfo() {
        return apporderinfo;
    }

    /**
     * @param apporderinfo the apporderinfo to set
     */
    public void setApporderinfo(String apporderinfo) {
        this.apporderinfo = apporderinfo;
    }
    @Column(name = "ACCBUSICODE", length = 8)
    public String getAccbusicode() {
        return accbusicode;
    }

    public void setAccbusicode(String accbusicode) {
        this.accbusicode = accbusicode;
    }

	/**
	 * @return the acccoopinstino
	 */
    @Column(name = "ACCCOOPINSTINO", length = 15)
	public String getAcccoopinstino() {
		return acccoopinstino;
	}

	/**
	 * @param acccoopinstino the acccoopinstino to set
	 */
	public void setAcccoopinstino(String acccoopinstino) {
		this.acccoopinstino = acccoopinstino;
	}

	/**
	 * @return the panName
	 */
	@Column(name = "PAN_NAME")
	public String getPanName() {
		return panName;
	}

	/**
	 * @param panName the panName to set
	 */
	public void setPanName(String panName) {
		this.panName = panName;
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
	
    
}