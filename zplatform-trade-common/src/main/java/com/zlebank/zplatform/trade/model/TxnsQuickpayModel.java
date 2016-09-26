package com.zlebank.zplatform.trade.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.enums.TradeTypeEnum;
import com.zlebank.zplatform.trade.bean.reapal.BindBean;
import com.zlebank.zplatform.trade.bean.reapal.CreditBean;
import com.zlebank.zplatform.trade.bean.reapal.DebitBean;
import com.zlebank.zplatform.trade.bean.reapal.PayBean;
import com.zlebank.zplatform.trade.bean.reapal.QueryBean;
import com.zlebank.zplatform.trade.bean.reapal.SMSBean;
import com.zlebank.zplatform.trade.bean.zlpay.MarginRegisterBean;
import com.zlebank.zplatform.trade.bean.zlpay.MarginSmsBean;
import com.zlebank.zplatform.trade.bean.zlpay.OnlineDepositShortBean;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.OrderNumber;

/**
 * TTxnsQuickpay entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_TXNS_QUICKPAY")
public class TxnsQuickpayModel implements java.io.Serializable {

 // Fields

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2615255377016234602L;
    private Long id;
    private String institution;
    private String payorderno;
    private Long payamt;
    private String paycommitime;
    private String relatetradetxnseqno;
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
    private String customerno;
    private String status;
    private String notes;
    private String remarks;
    private String retorderno;

    private String payfinishtime;
    private String payretcode;
    private String payretinfo;
    private String txncode;
    private String mobile;
    private String relateorderno;
    // Constructors

    /** default constructor */
    public TxnsQuickpayModel() {
    }

    /** minimal constructor */
    public TxnsQuickpayModel(Long id) {
        this.id = id;
    }

    /** full constructor */
    public TxnsQuickpayModel(Long id, String institution, String payorderno,
            Long payamt, String paycommitime, String relatetradetxnseqno,
            String firmemberno, String firmembername, String firmembershortname,
            String secmemberno, String secmembername, String secmembershortname,
            String payname, Long paynum, String paycode, String paydescr,
            String paytype, String customerno, String status, String notes,
            String remarks, String retorderno) {
        this.id = id;
        this.institution = institution;
        this.payorderno = payorderno;
        this.payamt = payamt;
        this.paycommitime = paycommitime;
        this.relatetradetxnseqno = relatetradetxnseqno;
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
        this.customerno = customerno;
        this.status = status;
        this.notes = notes;
        this.remarks = remarks;
        this.retorderno = retorderno;
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

    @Column(name = "PAYCOMMITIME", length = 14)
    public String getPaycommitime() {
        return this.paycommitime;
    }

    public void setPaycommitime(String paycommitime) {
        this.paycommitime = paycommitime;
    }

    @Column(name = "RELATETRADETXNSEQNO", length = 16)
    public String getRelatetradetxnseqno() {
        return this.relatetradetxnseqno;
    }

    public void setRelatetradetxnseqno(String relatetradetxnseqno) {
        this.relatetradetxnseqno = relatetradetxnseqno;
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

    @Column(name = "PAYNAME", length = 12)
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

    @Column(name = "CUSTOMERNO", length = 15)
    public String getCustomerno() {
        return this.customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno;
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

    @Column(name = "RETORDERNO", length = 32)
    public String getRetorderno() {
        return this.retorderno;
    }

    public void setRetorderno(String retorderno) {
        this.retorderno = retorderno;
    }
    
    

    /**
     * @return the payfinishtime
     */
    @Column(name = "PAYFINISHTIME", length = 14)
    public String getPayfinishtime() {
        return payfinishtime;
    }

    /**
     * @param payfinishtime the payfinishtime to set
     */
    public void setPayfinishtime(String payfinishtime) {
        this.payfinishtime = payfinishtime;
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
     * @return the txncode
     */
    @Column(name = "TXNCODE", length = 8)
    public String getTxncode() {
        return txncode;
    }

    /**
     * @param txncode the txncode to set
     */
    public void setTxncode(String txncode) {
        this.txncode = txncode;
    }

    /**
     * @return the mobile
     */
    @Column(name = "MOBILE", length = 11)
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @param payretinfo the payretinfo to set
     */
    public void setPayretinfo(String payretinfo) {
        this.payretinfo = payretinfo;
    }

    /**
     * @return the relateorderno
     */
    @Column(name = "RELATEORDERNO", length = 32)
    public String getRelateorderno() {
        return relateorderno;
    }

    /**
     * @param relateorderno the relateorderno to set
     */
    public void setRelateorderno(String relateorderno) {
        this.relateorderno = relateorderno;
    }

    public TxnsQuickpayModel(TradeBean trade,MarginSmsBean marginSmsBean){
        this.id=OrderNumber.getInstance().generateID();
        this.payorderno = marginSmsBean.getMerchantSeqId();
        this.payamt = 0L;
        this.paycommitime = marginSmsBean.getMerchantDate()+marginSmsBean.getMerchantTime();
        this.relatetradetxnseqno = trade.getTxnseqno();
        this.firmemberno = marginSmsBean.getInstuId();//marginSmsBean.getInstuId();
        this.firmembername = ConsUtil.getInstance().cons.getZlpay_chnl_merch_name();
        //this.secmemberno = trade.getSubMerchId();
        //this.secmembername=trade.getSubMerchName();
        this.payname = marginSmsBean.getUserNameText();
        this.paynum = 1L;
        this.paycode = "2305";
        this.paydescr = "获取证联支付短信验证码";
        this.paytype = "1000";
        this.status = "01";
        this.txncode = trade.getCurrentSetp();
        this.mobile = trade.getMobile();
       
    }
    
    public TxnsQuickpayModel(TradeBean trade,MarginRegisterBean marginRegisterBean){
        this.id=2L;
        this.payorderno = marginRegisterBean.getFundSeqId();
        this.payamt = 0L;
        this.paycommitime = marginRegisterBean.getFundDate()+marginRegisterBean.getFundTime();
        this.relatetradetxnseqno = trade.getTxnseqno();
        this.firmemberno =   marginRegisterBean.getInstuId();
        this.firmembername = ConsUtil.getInstance().cons.getZlpay_chnl_merch_name();
        //this.secmemberno = trade.getSubMerchId();
        //this.secmembername=trade.getSubMerchName();
        
        
        this.payname = marginRegisterBean.getUserNameText();
        this.paynum = 1L;
        this.paycode = "108";
        this.paydescr = "证联同步开户";
        this.paytype = "1000";
        this.status = "01";
        this.remarks = marginRegisterBean.getIdentifyingCode();
        this.txncode = trade.getCurrentSetp();
        this.mobile = trade.getMobile();
    }
    
    public TxnsQuickpayModel(TradeBean trade,OnlineDepositShortBean onlineDepositShortBean){
        this.id=3L;
        this.payorderno = onlineDepositShortBean.getMerchantSeqId();
        this.payamt = Long.valueOf(onlineDepositShortBean.getTransAmt());
        this.paycommitime = onlineDepositShortBean.getMerchantDate()+onlineDepositShortBean.getMerchantTime();
        this.relatetradetxnseqno = trade.getTxnseqno();
        this.firmemberno =   onlineDepositShortBean.getInstuId();
        this.firmembername = ConsUtil.getInstance().cons.getZlpay_chnl_merch_name();
        //this.secmemberno = trade.getSubMerchId();
        //this.secmembername=trade.getSubMerchName();
        this.payname = onlineDepositShortBean.getUserNameText();
        this.paynum = 1L;
        this.paycode = "2104";
        this.paydescr = "证联在线入金";
        this.paytype = "1000";
        this.status = "01";
        this.txncode = trade.getCurrentSetp();
        this.mobile = trade.getMobile();
    }
    public TxnsQuickpayModel(TradeBean trade,DebitBean creditBean){
        this.id=4L;
        this.payorderno = OrderNumber.getInstance().generateReaPayOrderId();//
        this.payamt = Long.valueOf(creditBean.getTotal_fee());
        this.paycommitime = creditBean.getTranstime().replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
        this.relatetradetxnseqno = trade.getTxnseqno();
        this.firmemberno =   creditBean.getMember_id();
        this.firmembername = ConsUtil.getInstance().cons.getZlpay_chnl_merch_name();
        //this.secmemberno = trade.getSubMerchId();
        //this.secmembername=trade.getSubMerchName();
        this.payname = creditBean.getOwner();
        this.paynum = 1L;
        this.paycode = "1003";
        this.paydescr = "融宝-借记卡签约";
        this.paytype = "1000";
        this.status = "01";
        this.txncode = trade.getCurrentSetp();
        this.mobile = trade.getMobile();
        this.relateorderno=trade.getReaPayOrderNo();
    }
    public TxnsQuickpayModel(TradeBean trade,SMSBean smsBean){
        this.id=5L;
        this.payorderno = OrderNumber.getInstance().generateReaPayOrderId();
        this.payamt = Long.valueOf(trade.getAmount());
        this.paycommitime = DateUtil.getCurrentDateTime();
        this.relatetradetxnseqno = trade.getTxnseqno();
        this.firmemberno =   smsBean.getMerchant_id();
        this.firmembername = ConsUtil.getInstance().cons.getZlpay_chnl_merch_name();
        //this.secmemberno = trade.getSubMerchId();
        //this.secmembername=trade.getSubMerchName();
        this.payname = trade.getAcctName();
        this.paynum = 1L;
        this.paycode = "1002";
        this.paydescr = "融宝-短信验证码";
        this.paytype = "1000";
        this.status = "01";
        this.txncode = trade.getCurrentSetp();
        this.mobile = trade.getMobile();
        this.relateorderno=trade.getReaPayOrderNo();
    }
    
    public TxnsQuickpayModel(TradeBean trade, CreditBean creditBean){
        this.id=6L;
        this.payorderno = OrderNumber.getInstance().generateReaPayOrderId();//
        this.payamt = Long.valueOf(creditBean.getTotal_fee());
        this.paycommitime = creditBean.getTranstime().replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
        this.relatetradetxnseqno = trade.getTxnseqno();
        this.firmemberno =   creditBean.getMember_id();
        this.firmembername = ConsUtil.getInstance().cons.getZlpay_chnl_merch_name();
        this.payname = creditBean.getOwner();
        this.paynum = 1L;
        this.paycode = "1003";
        this.paydescr = "融宝-信用卡签约";
        this.paytype = "1000";
        this.status = "01";
        this.txncode = trade.getCurrentSetp();
        this.mobile = trade.getMobile();
        this.relateorderno=trade.getReaPayOrderNo();
    }
    
    public TxnsQuickpayModel(TradeBean trade, PayBean payBean){
        this.id=6L;
        this.payorderno = OrderNumber.getInstance().generateReaPayOrderId();//
        this.payamt = Long.valueOf(trade.getAmount());
        this.paycommitime = DateUtil.getCurrentDateTime();
        this.relatetradetxnseqno = trade.getTxnseqno();
        this.firmemberno =   payBean.getMerchant_id();
        this.firmembername = ConsUtil.getInstance().cons.getZlpay_chnl_merch_name();
        this.payname = trade.getAcctName();
        this.paynum = 1L;
        this.paycode = "1001";
        this.paydescr = "融宝-确认支付";
        this.paytype = "1000";
        this.status = "01";
        this.txncode = trade.getCurrentSetp();
        this.mobile = trade.getMobile();
        this.relateorderno=trade.getReaPayOrderNo();
        this.remarks = trade.getIdentifyingCode();
    }
    
    public TxnsQuickpayModel(TradeBean trade,QueryBean queryBean){
        this.id=OrderNumber.getInstance().generateID();
        this.payorderno = OrderNumber.getInstance().generateReaPayOrderId();
        this.payamt = 0L;
        this.paycommitime = DateUtil.getCurrentDateTime();
        this.relatetradetxnseqno = trade.getTxnseqno();
        this.firmemberno = queryBean.getMerchant_id();//marginSmsBean.getInstuId();
        this.firmembername = ConsUtil.getInstance().cons.getZlpay_chnl_merch_name();
        //this.secmemberno = trade.getSubMerchId();
        //this.secmembername=trade.getSubMerchName();
        this.payname = trade.getAcctName();
        this.paynum = 1L;
        this.paycode = "1005";
        this.paydescr = "融宝-交易查询";
        this.paytype = "1000";
        this.status = "01";
        this.txncode = trade.getCurrentSetp();
        this.mobile = trade.getMobile();
        this.relateorderno=trade.getReaPayOrderNo();
    }
    
    public TxnsQuickpayModel(TradeBean trade,BindBean bindBean){
        this.id=8L;
        this.payorderno = OrderNumber.getInstance().generateReaPayOrderId();//
        this.payamt = Long.valueOf(bindBean.getTotal_fee());
        this.paycommitime = bindBean.getTranstime().replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
        this.relatetradetxnseqno = trade.getTxnseqno();
        this.firmemberno =   bindBean.getMember_id();
        this.firmembername = ConsUtil.getInstance().cons.getZlpay_chnl_merch_name();
        //this.secmemberno = trade.getSubMerchId();
        //this.secmembername=trade.getSubMerchName();
        this.payname = trade.getAcctName();
        this.paynum = 1L;
        this.paycode = "1003";
        this.paydescr = "融宝-绑卡签约";
        this.paytype = "1000";
        this.status = "01";
        this.txncode = trade.getCurrentSetp();
        this.mobile = trade.getMobile();
        this.relateorderno=trade.getReaPayOrderNo();
        this.remarks= "绑卡ID"+bindBean.getBind_id();
    }
    
    public TxnsQuickpayModel(TradeBean trade,BindBean bindBean,String none){
        this.id=OrderNumber.getInstance().generateID();
        this.payorderno = OrderNumber.getInstance().generateReaPayOrderId();//
        this.payamt = Long.valueOf(bindBean.getTotal_fee());
        this.paycommitime = bindBean.getTranstime().replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
        this.relatetradetxnseqno = trade.getTxnseqno();
        this.firmemberno =   bindBean.getMember_id();
        this.firmembername = ConsUtil.getInstance().cons.getZlpay_chnl_merch_name();
        //this.secmemberno = trade.getSubMerchId();
        //this.secmembername=trade.getSubMerchName();
        this.payname = trade.getAcctName();
        this.paynum = 1L;
        this.paycode = "1003";
        this.paydescr = "测试渠道-绑卡签约";
        this.paytype = "1000";
        this.status = "01";
        this.txncode = trade.getCurrentSetp();
        this.mobile = trade.getMobile();
        this.relateorderno=trade.getReaPayOrderNo();
        this.remarks= bindBean.getBind_id();
    }
    
    public TxnsQuickpayModel(TradeBean trade,DebitBean creditBean,String none){
        this.id=OrderNumber.getInstance().generateID();
        this.payorderno = OrderNumber.getInstance().generateReaPayOrderId();//
        this.payamt = Long.valueOf(creditBean.getTotal_fee());
        this.paycommitime = creditBean.getTranstime().replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
        this.relatetradetxnseqno = trade.getTxnseqno();
        this.firmemberno =   creditBean.getMember_id();
        this.firmembername = ConsUtil.getInstance().cons.getZlpay_chnl_merch_name();
        //this.secmemberno = trade.getSubMerchId();
        //this.secmembername=trade.getSubMerchName();
        this.payname = creditBean.getOwner();
        this.paynum = 1L;
        this.paycode = "1001";
        this.paydescr = "测试渠道-借记卡签约";
        this.paytype = "1000";
        this.status = "01";
        this.txncode = trade.getCurrentSetp();
        this.mobile = trade.getMobile();
        this.relateorderno=trade.getReaPayOrderNo();
    }
    
    public TxnsQuickpayModel(TradeBean trade, CreditBean creditBean,String none){
        this.id=OrderNumber.getInstance().generateID();
        this.payorderno = OrderNumber.getInstance().generateReaPayOrderId();//
        this.payamt = Long.valueOf(creditBean.getTotal_fee());
        this.paycommitime = creditBean.getTranstime().replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
        this.relatetradetxnseqno = trade.getTxnseqno();
        this.firmemberno =   creditBean.getMember_id();
        this.firmembername = ConsUtil.getInstance().cons.getZlpay_chnl_merch_name();
        this.payname = creditBean.getOwner();
        this.paynum = 1L;
        this.paycode = "1002";
        this.paydescr = "测试渠道-信用卡签约";
        this.paytype = "1000";
        this.status = "01";
        this.txncode = trade.getCurrentSetp();
        this.mobile = trade.getMobile();
        this.relateorderno=trade.getReaPayOrderNo();
    }
    
    public TxnsQuickpayModel(TradeBean trade,SMSBean smsBean,String none){
        this.id=OrderNumber.getInstance().generateID();
        this.payorderno = OrderNumber.getInstance().generateReaPayOrderId();
        this.payamt = Long.valueOf(trade.getAmount());
        this.paycommitime = DateUtil.getCurrentDateTime();
        this.relatetradetxnseqno = trade.getTxnseqno();
        this.firmemberno =   smsBean.getMerchant_id();
        this.firmembername = ConsUtil.getInstance().cons.getZlpay_chnl_merch_name();
        //this.secmemberno = trade.getSubMerchId();
        //this.secmembername=trade.getSubMerchName();
        this.payname = trade.getAcctName();
        this.paynum = 1L;
        this.paycode = "1004";
        this.paydescr = "测试渠道-短信验证码";
        this.paytype = "1000";
        this.status = "01";
        this.txncode = trade.getCurrentSetp();
        this.mobile = trade.getMobile();
        this.relateorderno=trade.getReaPayOrderNo();
    }
    public TxnsQuickpayModel(TradeBean trade, PayBean payBean,String none){
        this.id=OrderNumber.getInstance().generateID();
        this.payorderno = OrderNumber.getInstance().generateReaPayOrderId();//
        this.payamt = Long.valueOf(trade.getAmount());
        this.paycommitime = DateUtil.getCurrentDateTime();
        this.relatetradetxnseqno = trade.getTxnseqno();
        this.firmemberno =   payBean.getMerchant_id();
        this.firmembername = ConsUtil.getInstance().cons.getZlpay_chnl_merch_name();
        this.payname = trade.getAcctName();
        this.paynum = 1L;
        this.paycode = "1005";
        this.paydescr = "融宝-确认支付";
        this.paytype = "1000";
        this.status = "01";
        this.txncode = trade.getCurrentSetp();
        this.mobile = trade.getMobile();
        this.relateorderno=trade.getReaPayOrderNo();
        this.remarks=trade.getIdentifyingCode();
    }
    
    public TxnsQuickpayModel(TradeBean trade,TradeTypeEnum tradeType){
        switch (tradeType) {
            case SENDSMS :
                this.id=OrderNumber.getInstance().generateID();
                this.payorderno = OrderNumber.getInstance().generateCMBCQuickOrderNo();
                this.payamt = Long.valueOf(trade.getAmount());
                this.paycommitime = DateUtil.getCurrentDateTime();
                this.relatetradetxnseqno = trade.getTxnseqno();
                this.firmemberno =   ConsUtil.getInstance().cons.getCmbc_merid();
                this.firmembername = ConsUtil.getInstance().cons.getZlpay_chnl_merch_name();
                this.payname = trade.getAcctName();
                this.paynum = 1L;
                this.paycode = "1004";
                this.paydescr = "民生跨行代扣-短信验证码";
                this.paytype = "1000";
                this.status = "00";
                this.mobile = trade.getMobile();
                this.institution = trade.getPayinstiId();
                break;
            case SUBMITPAY :
                this.id=OrderNumber.getInstance().generateID();
                this.payorderno = OrderNumber.getInstance().generateCMBCQuickOrderNo();
                this.payamt = Long.valueOf(trade.getAmount());
                this.paycommitime = DateUtil.getCurrentDateTime();
                this.relatetradetxnseqno = trade.getTxnseqno();
                this.firmemberno =   ConsUtil.getInstance().cons.getCmbc_merid();
                this.firmembername = ConsUtil.getInstance().cons.getZlpay_chnl_merch_name();
                this.payname = trade.getAcctName();
                this.paynum = 1L;
                this.paycode = "1005";
                this.paydescr = "民生跨行代扣-实时代扣";
                this.paytype = "1000";
                this.status = "01";
                this.mobile = trade.getMobile();
                this.institution = trade.getPayinstiId();
                break;
            case BANKSIGN:
                this.id=OrderNumber.getInstance().generateID();
                this.payorderno = OrderNumber.getInstance().generateCMBCQuickOrderNo();
                this.payamt = Long.valueOf(trade.getAmount());
                this.paycommitime = DateUtil.getCurrentDateTime();
                this.relatetradetxnseqno = trade.getTxnseqno();
                this.firmemberno =   ConsUtil.getInstance().cons.getCmbc_merid();
                this.firmembername = ConsUtil.getInstance().cons.getZlpay_chnl_merch_name();
                this.payname = trade.getAcctName();
                this.paynum = 1L;
                this.paycode = "1001";
                this.paydescr = "民生跨行代扣-银行卡签约";
                this.paytype = "1000";
                this.status = "01";
                this.mobile = trade.getMobile();
                this.institution = trade.getPayinstiId();
                break;
		default:
			break;
        }
        
    }
    
    
    
    
}