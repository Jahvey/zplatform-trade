package com.zlebank.zplatform.trade.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TMerchMk entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_MERCH_MK")
public class MerchMkModel implements java.io.Serializable {

 // Fields

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -408182992724615740L;
    private String memberid;
    private String safeseq;
    private String safetype;
    private String memberpubkey;
    private String memberprikey;
    private String localpubkey;
    private String localprikey;
    private String bmk;
    private String pin;
    private String mac;
    private String data;
    private String md5;
    private String storgetype;
    private String status;
    private String keyflag;
    private String notes;
    private String remarks;

    // Constructors

    /** default constructor */
    public MerchMkModel() {
    }

    /** minimal constructor */
    public MerchMkModel(String memberid, String safeseq, String safetype,
            String memberpubkey, String memberprikey, String localpubkey,
            String localprikey, String status, String keyflag) {
        this.memberid = memberid;
        this.safeseq = safeseq;
        this.safetype = safetype;
        this.memberpubkey = memberpubkey;
        this.memberprikey = memberprikey;
        this.localpubkey = localpubkey;
        this.localprikey = localprikey;
        this.status = status;
        this.keyflag = keyflag;
    }

    /** full constructor */
    public MerchMkModel(String memberid, String safeseq, String safetype,
            String memberpubkey, String memberprikey, String localpubkey,
            String localprikey, String bmk, String pin, String mac,
            String data, String md5, String storgetype, String status,
            String keyflag, String notes, String remarks) {
        this.memberid = memberid;
        this.safeseq = safeseq;
        this.safetype = safetype;
        this.memberpubkey = memberpubkey;
        this.memberprikey = memberprikey;
        this.localpubkey = localpubkey;
        this.localprikey = localprikey;
        this.bmk = bmk;
        this.pin = pin;
        this.mac = mac;
        this.data = data;
        this.md5 = md5;
        this.storgetype = storgetype;
        this.status = status;
        this.keyflag = keyflag;
        this.notes = notes;
        this.remarks = remarks;
    }

    // Property accessors
    @Id
    @Column(name = "MEMBERID", unique = true, nullable = false, precision = 15, scale = 0)
    public String getMemberid() {
        return this.memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    @Column(name = "SAFESEQ", nullable = false, length = 32)
    public String getSafeseq() {
        return this.safeseq;
    }

    public void setSafeseq(String safeseq) {
        this.safeseq = safeseq;
    }

    @Column(name = "SAFETYPE", nullable = false, length = 32)
    public String getSafetype() {
        return this.safetype;
    }

    public void setSafetype(String safetype) {
        this.safetype = safetype;
    }

    @Column(name = "MEMBERPUBKEY", nullable = false, length = 2048)
    public String getMemberpubkey() {
        return this.memberpubkey;
    }

    public void setMemberpubkey(String memberpubkey) {
        this.memberpubkey = memberpubkey;
    }

    @Column(name = "MEMBERPRIKEY", nullable = false, length = 2048)
    public String getMemberprikey() {
        return this.memberprikey;
    }

    public void setMemberprikey(String memberprikey) {
        this.memberprikey = memberprikey;
    }

    @Column(name = "LOCALPUBKEY", nullable = false, length = 2048)
    public String getLocalpubkey() {
        return this.localpubkey;
    }

    public void setLocalpubkey(String localpubkey) {
        this.localpubkey = localpubkey;
    }

    @Column(name = "LOCALPRIKEY", nullable = false, length = 2048)
    public String getLocalprikey() {
        return this.localprikey;
    }

    public void setLocalprikey(String localprikey) {
        this.localprikey = localprikey;
    }

    @Column(name = "BMK", length = 256)
    public String getBmk() {
        return this.bmk;
    }

    public void setBmk(String bmk) {
        this.bmk = bmk;
    }

    @Column(name = "PIN", length = 256)
    public String getPin() {
        return this.pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Column(name = "MAC", length = 128)
    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Column(name = "DATA", length = 512)
    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Column(name = "MD5", length = 256)
    public String getMd5() {
        return this.md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @Column(name = "STORGETYPE", length = 2)
    public String getStorgetype() {
        return this.storgetype;
    }

    public void setStorgetype(String storgetype) {
        this.storgetype = storgetype;
    }

    @Column(name = "STATUS", nullable = false, length = 2)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "KEYFLAG", nullable = false, length = 1)
    public String getKeyflag() {
        return this.keyflag;
    }

    public void setKeyflag(String keyflag) {
        this.keyflag = keyflag;
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

}