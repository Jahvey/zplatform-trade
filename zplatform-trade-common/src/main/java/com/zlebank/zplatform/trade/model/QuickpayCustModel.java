package com.zlebank.zplatform.trade.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.gateway.CustomerInfoBean;
import com.zlebank.zplatform.trade.bean.gateway.EncryptDataBean;
import com.zlebank.zplatform.trade.utils.OrderNumber;

/**
 * TQuickpayCust entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_QUICKPAY_CUST")
public class QuickpayCustModel implements java.io.Serializable {

    // Fields

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6161730384326071922L;
    private Long id;
    private String bindcardid;
    private String institution;
    private String customerno;
    private String cardno;
    private String cardtype;
    private String accname;
    private String phone;
    private String idtype;
    private String idnum;
    private String cvv2;
    private String validtime;
    private String status;
    private String relatememberno;
    private String notes;
    private String remarks;
    private String bankcode;
    private String bankname;
    private String devId;

    // Constructors

    /** default constructor */
    public QuickpayCustModel() {
    }

    /** minimal constructor */
    public QuickpayCustModel(Long id, String status) {
        this.id = id;
        this.status = status;
    }
    
    public QuickpayCustModel(TradeBean trade){
        this.id = OrderNumber.getInstance().generateID();
        this.bindcardid = trade.getBindCardId();
        this.customerno = trade.getMerchId();
        this.cardno = trade.getCardNo();
        this.cardtype = trade.getCardType();
        this.accname = trade.getAcctName();
        this.phone = trade.getMobile();
        this.idtype = "01";
        this.idnum = trade.getCertId();
        this.cvv2 = trade.getCvv2();
        this.validtime = StringUtil.isEmpty(trade.getValidthru())?trade.getMonth()+trade.getYear():trade.getValidthru();
        this.status = "01";
        this.relatememberno = trade.getMerUserId();
       
        this.institution = trade.getPayinstiId();
    }

    /** full constructor */
    public QuickpayCustModel(Long id, String bindcardid, String institution,
            String customerno, String cardno, String cardtype, String accname,
            String phone, String idtype, String idnum, String cvv2,
            String validtime, String status, String relatememberno,
            String notes, String remarks, String bankcode, String bankname) {
        this.id = id;
        this.bindcardid = bindcardid;
        this.institution = institution;
        this.customerno = customerno;
        this.cardno = cardno;
        this.cardtype = cardtype;
        this.accname = accname;
        this.phone = phone;
        this.idtype = idtype;
        this.idnum = idnum;
        this.cvv2 = cvv2;
        this.validtime = validtime;
        this.status = status;
        this.relatememberno = relatememberno;
        this.notes = notes;
        this.remarks = remarks;
        this.bankcode = bankcode;
        this.bankname = bankname;
    }
    
    public QuickpayCustModel(CustomerInfoBean customerInfo,EncryptDataBean encryptDataBean) {
        this.id = 99L;
        this.cardno = encryptDataBean.getCardNo();
        this.accname = customerInfo.getCustomerNm();
        this.phone = encryptDataBean.getPhoneNo();
        this.idtype = customerInfo.getCertifTp();
        this.idnum = customerInfo.getCertifId();
        this.cvv2 = encryptDataBean.getCvn2();
        this.validtime = encryptDataBean.getExpired();
        this.status = "01";
    }
    
    /**
	 * @return the devId
	 */
    @Column(name = "DEVID")
	public String getDevId() {
		return devId;
	}
	/**
	 * @param devId the devId to set
	 */
	public void setDevId(String devId) {
		this.devId = devId;
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

    @Column(name = "BINDCARDID", length = 15)
    public String getBindcardid() {
        return this.bindcardid;
    }

    public void setBindcardid(String bindcardid) {
        this.bindcardid = bindcardid;
    }

    @Column(name = "INSTITUTION", length = 12)
    public String getInstitution() {
        return this.institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    @Column(name = "CUSTOMERNO", length = 15)
    public String getCustomerno() {
        return this.customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno;
    }

    @Column(name = "CARDNO", length = 32)
    public String getCardno() {
        return this.cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    @Column(name = "CARDTYPE", length = 64)
    public String getCardtype() {
        return this.cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    @Column(name = "ACCNAME", length = 32)
    public String getAccname() {
        return this.accname;
    }

    public void setAccname(String accname) {
        this.accname = accname;
    }

    @Column(name = "PHONE", length = 11)
    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "IDTYPE", length = 2)
    public String getIdtype() {
        return this.idtype;
    }

    public void setIdtype(String idtype) {
        this.idtype = idtype;
    }

    @Column(name = "IDNUM", length = 32)
    public String getIdnum() {
        return this.idnum;
    }

    public void setIdnum(String idnum) {
        this.idnum = idnum;
    }

    @Column(name = "CVV2", length = 12)
    public String getCvv2() {
        return this.cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    @Column(name = "VALIDTIME", length = 14)
    public String getValidtime() {
        return this.validtime;
    }

    public void setValidtime(String validtime) {
        this.validtime = validtime;
    }

    @Column(name = "STATUS", nullable = false, length = 2)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "RELATEMEMBERNO", length = 15)
    public String getRelatememberno() {
        return this.relatememberno;
    }

    public void setRelatememberno(String relatememberno) {
        this.relatememberno = relatememberno;
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

    @Column(name = "BANKCODE", length = 8)
    public String getBankcode() {
        return this.bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    @Column(name = "BANKNAME", length = 64)
    public String getBankname() {
        return this.bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }
    
    
    private String miniCardNo;
    

    /**
     * @return the miniCardNo
     */
    @Transient
    public String getMiniCardNo() {
        return cardno.substring(cardno.length()-4);
    }

    /**
     * @param miniCardNo the miniCardNo to set
     */
    public void setMiniCardNo(String miniCardNo) {
        this.miniCardNo = miniCardNo;
    }

    
}