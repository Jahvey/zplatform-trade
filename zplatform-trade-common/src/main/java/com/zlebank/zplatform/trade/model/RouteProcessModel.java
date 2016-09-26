package com.zlebank.zplatform.trade.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TRouteProcess entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_ROUTE_PROCESS")
public class RouteProcessModel implements java.io.Serializable {

 // Fields

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2264447916680379662L;
    private Long rid;
    private String routver;
    private String laststep;
    private String nowstep;
    private String nextstep;
    private String contrary;
    private String contflag;
    private String status;
    private String notes;
    private String remarks;
    private String routname;
    private String chnlcode;
    // Constructors

    /** default constructor */
    public RouteProcessModel() {
    }

    /** minimal constructor */
    public RouteProcessModel(Long rid, String routver, String laststep,
            String nowstep, String nextstep, String contflag, String status) {
        this.rid = rid;
        this.routver = routver;
        this.laststep = laststep;
        this.nowstep = nowstep;
        this.nextstep = nextstep;
        this.contflag = contflag;
        this.status = status;
    }

    /** full constructor */
    public RouteProcessModel(Long rid, String routver, String laststep,
            String nowstep, String nextstep, String contrary, String contflag,
            String status, String notes, String remarks, String routname) {
        this.rid = rid;
        this.routver = routver;
        this.laststep = laststep;
        this.nowstep = nowstep;
        this.nextstep = nextstep;
        this.contrary = contrary;
        this.contflag = contflag;
        this.status = status;
        this.notes = notes;
        this.remarks = remarks;
        this.routname = routname;
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

    @Column(name = "ROUTVER", nullable = false, length = 8)
    public String getRoutver() {
        return this.routver;
    }

    public void setRoutver(String routver) {
        this.routver = routver;
    }

    @Column(name = "LASTSTEP", nullable = false, length = 8)
    public String getLaststep() {
        return this.laststep;
    }

    public void setLaststep(String laststep) {
        this.laststep = laststep;
    }

    @Column(name = "NOWSTEP", nullable = false, length = 8)
    public String getNowstep() {
        return this.nowstep;
    }

    public void setNowstep(String nowstep) {
        this.nowstep = nowstep;
    }

    @Column(name = "NEXTSTEP", nullable = false, length = 8)
    public String getNextstep() {
        return this.nextstep;
    }

    public void setNextstep(String nextstep) {
        this.nextstep = nextstep;
    }

    @Column(name = "CONTRARY", length = 8)
    public String getContrary() {
        return this.contrary;
    }

    public void setContrary(String contrary) {
        this.contrary = contrary;
    }

    @Column(name = "CONTFLAG", nullable = false, length = 1)
    public String getContflag() {
        return this.contflag;
    }

    public void setContflag(String contflag) {
        this.contflag = contflag;
    }

    @Column(name = "STATUS", nullable = false, length = 2)
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

    @Column(name = "ROUTNAME", length = 64)
    public String getRoutname() {
        return this.routname;
    }

    public void setRoutname(String routname) {
        this.routname = routname;
    }

    /**
     * @return the chnlcode
     */
    @Column(name = "CHNLCODE", length = 8)
    public String getChnlcode() {
        return chnlcode;
    }

    /**
     * @param chnlcode the chnlcode to set
     */
    public void setChnlcode(String chnlcode) {
        this.chnlcode = chnlcode;
    }

    
}