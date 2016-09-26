/* 
 * PojoRealnameAuth.java
 * 
 * version v1.0
 *
 * 2015年11月24日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * 实名认证类
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月24日 上午11:51:30
 * @since 
 */
@Entity
@Table(name="T_REALNAME_AUTH_ORDER")
public class PojoRealnameAuthOrder {
    /**标识**/
    private Long id;
    /**商户代码**/
    private String merId;
    /**商户订单号**/
    private String orderId;
    /**订单发送时间(yyyyMMddhhmmss)**/
    private String txnTime;
    /**银行卡号**/
    private String cardNo;
    /**卡类型**/
    private String cardType;
    /**持卡人姓名**/
    private String customerNm;
    /**证件类型**/
    private String certifTp;
    /**证件号**/
    private String certifId;
    /**手机号**/
    private Long phoneNo;
    /**cvn2**/
    private String cvn2;
    /**卡有效期**/
    private String expired;
    /**状态(00:已认证01:认证)**/
    private String status;
    /**创建人**/
    private Long inuser;
    /**创建时间**/
    private Date intime;
    /**修改人**/
    private Long upuser;
    /**修改时间**/
    private Date uptime;
    /**备注**/
    private String notes;
    /**交易流水号**/
    private String txnseqno;
    @GenericGenerator(name = "id_gen", strategy = "enhanced-table", parameters = {
    @Parameter(name = "table_name", value = "T_C_PRIMAY_KEY"),
    @Parameter(name = "value_column_name", value = "NEXT_ID"),
    @Parameter(name = "segment_column_name", value = "KEY_NAME"),
    @Parameter(name = "segment_value", value = "REALNAME_AUTH_ORDER_ID"),
    @Parameter(name = "increment_size", value = "1"),
    @Parameter(name = "optimizer", value = "pooled-lo") })
    @Id
    @GeneratedValue(generator = "id_gen")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Column(name = "MER_ID")
    public String getMerId() {
        return merId;
    }
    public void setMerId(String merId) {
        this.merId = merId;
    }
    @Column(name = "ORDER_ID")
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    @Column(name = "TXN_TIME")
    public String getTxnTime() {
        return txnTime;
    }
    public void setTxnTime(String txnTime) {
        this.txnTime = txnTime;
    }
    @Column(name = "CARD_NO")
    public String getCardNo() {
        return cardNo;
    }
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
    @Column(name = "CARD_TYPE")
    public String getCardType() {
        return cardType;
    }
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
    @Column(name = "CUSTOMER_NM")
    public String getCustomerNm() {
        return customerNm;
    }
    public void setCustomerNm(String customerNm) {
        this.customerNm = customerNm;
    }
    @Column(name = "CERTIF_TP")
    public String getCertifTp() {
        return certifTp;
    }
    public void setCertifTp(String certifTp) {
        this.certifTp = certifTp;
    }
    @Column(name = "CERTIF_ID")
    public String getCertifId() {
        return certifId;
    }
    public void setCertifId(String certifId) {
        this.certifId = certifId;
    }
    @Column(name = "PHONE_NO")
    public Long getPhoneNo() {
        return phoneNo;
    }
    public void setPhoneNo(Long phoneNo) {
        this.phoneNo = phoneNo;
    }
    @Column(name = "CVN2")
    public String getCvn2() {
        return cvn2;
    }
    public void setCvn2(String cvn2) {
        this.cvn2 = cvn2;
    }
    @Column(name = "EXPIRED")
    public String getExpired() {
        return expired;
    }
    public void setExpired(String expired) {
        this.expired = expired;
    }
    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    @Column(name = "INUSER")
    public Long getInuser() {
        return inuser;
    }
    public void setInuser(Long inuser) {
        this.inuser = inuser;
    }
    @Column(name = "INTIME")
    public Date getIntime() {
        return intime;
    }
    public void setIntime(Date intime) {
        this.intime = intime;
    }
    @Column(name = "UPUSER")
    public Long getUpuser() {
        return upuser;
    }
    public void setUpuser(Long upuser) {
        this.upuser = upuser;
    }
    @Column(name = "UPTIME")
    public Date getUptime() {
        return uptime;
    }
    public void setUptime(Date uptime) {
        this.uptime = uptime;
    }
    @Column(name = "NOTES")
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    @Column(name = "TXNSEQNO")
    public String getTxnseqno() {
        return txnseqno;
    }
    public void setTxnseqno(String txnseqno) {
        this.txnseqno = txnseqno;
    }

}
