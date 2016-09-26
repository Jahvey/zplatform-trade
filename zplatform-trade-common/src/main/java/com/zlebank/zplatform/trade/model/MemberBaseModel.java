package com.zlebank.zplatform.trade.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TMemberBase entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_MEMBER_BASE")
public class MemberBaseModel implements java.io.Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2938471201057517436L;
    private String memberid;
    private String merchname;
    private Byte merchtype;
    private Long setlcycle;
    private String prdtver;
    private String feever;
    private String spiltver;
    private String riskver;
    private String routver;
    private String cashver;
    private String parent;
    private String notes;
    private String remarks;
    private String merchinsti;
    // Constructors

    /** default constructor */
    public MemberBaseModel() {
    }

    /** minimal constructor */
    public MemberBaseModel(String memberid, String merchname) {
        this.memberid = memberid;
        this.merchname = merchname;
    }

    /** full constructor */
    public MemberBaseModel(String memberid, String merchname, Byte merchtype,
            Long setlcycle, String prdtver, String feever, String spiltver,
            String riskver, String routver, String cashver, String parent,
            String notes, String remarks) {
        this.memberid = memberid;
        this.merchname = merchname;
        this.merchtype = merchtype;
        this.setlcycle = setlcycle;
        this.prdtver = prdtver;
        this.feever = feever;
        this.spiltver = spiltver;
        this.riskver = riskver;
        this.routver = routver;
        this.cashver = cashver;
        this.parent = parent;
        this.notes = notes;
        this.remarks = remarks;
    }

    // Property accessors
    @Id
    @Column(name = "MEMBERID", nullable = false, length = 15)
    public String getMemberid() {
        return this.memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    @Column(name = "MERCHNAME", nullable = false, length = 50)
    public String getMerchname() {
        return this.merchname;
    }

    public void setMerchname(String merchname) {
        this.merchname = merchname;
    }

    @Column(name = "MERCHTYPE", precision = 2, scale = 0)
    public Byte getMerchtype() {
        return this.merchtype;
    }

    public void setMerchtype(Byte merchtype) {
        this.merchtype = merchtype;
    }

    @Column(name = "SETLCYCLE", precision = 10, scale = 0)
    public Long getSetlcycle() {
        return this.setlcycle;
    }

    public void setSetlcycle(Long setlcycle) {
        this.setlcycle = setlcycle;
    }

    @Column(name = "PRDTVER", length = 8)
    public String getPrdtver() {
        return this.prdtver;
    }

    public void setPrdtver(String prdtver) {
        this.prdtver = prdtver;
    }

    @Column(name = "FEEVER", length = 8)
    public String getFeever() {
        return this.feever;
    }

    public void setFeever(String feever) {
        this.feever = feever;
    }

    @Column(name = "SPILTVER", length = 8)
    public String getSpiltver() {
        return this.spiltver;
    }

    public void setSpiltver(String spiltver) {
        this.spiltver = spiltver;
    }

    @Column(name = "RISKVER", length = 8)
    public String getRiskver() {
        return this.riskver;
    }

    public void setRiskver(String riskver) {
        this.riskver = riskver;
    }

    @Column(name = "ROUTVER", length = 8)
    public String getRoutver() {
        return this.routver;
    }

    public void setRoutver(String routver) {
        this.routver = routver;
    }

    @Column(name = "CASHVER", length = 8)
    public String getCashver() {
        return this.cashver;
    }

    public void setCashver(String cashver) {
        this.cashver = cashver;
    }

    @Column(name = "PARENT", length = 11)
    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
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
     * @return the merchinsti
     */
    @Column(name = "MERCHINSTI", length = 15)
    public String getMerchinsti() {
        return merchinsti;
    }

    /**
     * @param merchinsti the merchinsti to set
     */
    public void setMerchinsti(String merchinsti) {
        this.merchinsti = merchinsti;
    }
    
}