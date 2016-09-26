package com.zlebank.zplatform.trade.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TRouteConfig entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_ROUTE_CONFIG")
public class RouteConfigModel implements java.io.Serializable {

 // Fields

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 56898438589616289L;
    private Long rid;
    private String cashcode;
    private String stime;
    private String etime;
    private Long minamt;
    private Long maxamt;
    private String bankcode;
    private String busicode;
    private String cardtype;
    private String routver;
    private String status;
    private Date intime;
    private Long inuser;
    private Date uptime;
    private Long upuser;
    private Long ordertype;
    private Long orders;
    private String isdef;
    private String notes;
    private String remarks;
    private String merchRoutver;
    // Constructors

    /** default constructor */
    public RouteConfigModel() {
    }

    /** minimal constructor */
    public RouteConfigModel(Long rid, String cashcode, String stime, String etime,
            Long minamt, Long maxamt, String routver) {
        this.rid = rid;
        this.cashcode = cashcode;
        this.stime = stime;
        this.etime = etime;
        this.minamt = minamt;
        this.maxamt = maxamt;
        this.routver = routver;
    }

    /** full constructor */
    public RouteConfigModel(Long rid, String cashcode, String stime, String etime,
            Long minamt, Long maxamt, String bankcode, String busicode,
            String cardtype, String routver, String status, Date intime,
            Long inuser, Date uptime, Long upuser, Long ordertype, Long orders,
            String isdef, String notes, String remarks) {
        this.rid = rid;
        this.cashcode = cashcode;
        this.stime = stime;
        this.etime = etime;
        this.minamt = minamt;
        this.maxamt = maxamt;
        this.bankcode = bankcode;
        this.busicode = busicode;
        this.cardtype = cardtype;
        this.routver = routver;
        this.status = status;
        this.intime = intime;
        this.inuser = inuser;
        this.uptime = uptime;
        this.upuser = upuser;
        this.ordertype = ordertype;
        this.orders = orders;
        this.isdef = isdef;
        this.notes = notes;
        this.remarks = remarks;
    }

    // Property accessors
    @Id
    @Column(name = "RID", unique = true, nullable = false, precision = 10, scale = 0)
    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    @Column(name = "CASHCODE", nullable = false, length = 8)
    public String getCashcode() {
        return this.cashcode;
    }

    public void setCashcode(String cashcode) {
        this.cashcode = cashcode;
    }

    @Column(name = "STIME", nullable = false, length = 6)
    public String getStime() {
        return this.stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    @Column(name = "ETIME", nullable = false, length = 6)
    public String getEtime() {
        return this.etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    @Column(name = "MINAMT", nullable = false, precision = 12, scale = 0)
    public Long getMinamt() {
        return this.minamt;
    }

    public void setMinamt(Long minamt) {
        this.minamt = minamt;
    }

    @Column(name = "MAXAMT", nullable = false, precision = 12, scale = 0)
    public Long getMaxamt() {
        return this.maxamt;
    }

    public void setMaxamt(Long maxamt) {
        this.maxamt = maxamt;
    }

    @Column(name = "BANKCODE", length = 256)
    public String getBankcode() {
        return this.bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    @Column(name = "BUSICODE", length = 256)
    public String getBusicode() {
        return this.busicode;
    }

    public void setBusicode(String busicode) {
        this.busicode = busicode;
    }

    @Column(name = "CARDTYPE", length = 1)
    public String getCardtype() {
        return this.cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    @Column(name = "ROUTVER", nullable = false, length = 8)
    public String getRoutver() {
        return this.routver;
    }

    public void setRoutver(String routver) {
        this.routver = routver;
    }

    @Column(name = "STATUS", length = 2)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "INTIME", length = 7)
    public Date getIntime() {
        return this.intime;
    }

    public void setIntime(Date intime) {
        this.intime = intime;
    }

    @Column(name = "INUSER", precision = 10, scale = 0)
    public Long getInuser() {
        return this.inuser;
    }

    public void setInuser(Long inuser) {
        this.inuser = inuser;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "UPTIME", length = 7)
    public Date getUptime() {
        return this.uptime;
    }

    public void setUptime(Date uptime) {
        this.uptime = uptime;
    }

    @Column(name = "UPUSER", precision = 10, scale = 0)
    public Long getUpuser() {
        return this.upuser;
    }

    public void setUpuser(Long upuser) {
        this.upuser = upuser;
    }

    @Column(name = "ORDERTYPE", precision = 10, scale = 0)
    public Long getOrdertype() {
        return this.ordertype;
    }

    public void setOrdertype(Long ordertype) {
        this.ordertype = ordertype;
    }

    @Column(name = "ORDERS", precision = 10, scale = 0)
    public Long getOrders() {
        return this.orders;
    }

    public void setOrders(Long orders) {
        this.orders = orders;
    }

    @Column(name = "ISDEF", length = 1)
    public String getIsdef() {
        return this.isdef;
    }

    public void setIsdef(String isdef) {
        this.isdef = isdef;
    }

    @Column(name = "NOTES", length = 128)
    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Column(name = "REMARKS", length = 128)
    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return the merchRoutver
     */
    @Column(name = "MERCHROUTVER", length = 8)
    public String getMerchRoutver() {
        return merchRoutver;
    }

    /**
     * @param merchRoutver the merchRoutver to set
     */
    public void setMerchRoutver(String merchRoutver) {
        this.merchRoutver = merchRoutver;
    }

}