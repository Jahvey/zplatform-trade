/* 
 * AccountTradeBean.java  
 * 
 * version TODO
 *
 * 2015年10月10日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean;

import java.io.Serializable;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年10月10日 上午10:44:23
 * @since 
 */
public class AccountTradeBean implements Serializable{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6128924088747482255L;
    /**
     * 会员ID
     */
    private String memberId;
    /**
     * 商户ID 一级商户
     */
    private String merchId;
    /**
     * 支付密码
     */
    private String pay_pwd;
    /**
     * 支付金额
     */
    private String amount;
    /**
     * 交易序列号
     */
    private String txnseqno;
    /**
     * @return the memberId
     */
    public String getMemberId() {
        return memberId;
    }
    /**
     * @param memberId the memberId to set
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    /**
     * @return the pay_pwd
     */
    public String getPay_pwd() {
        return pay_pwd;
    }
    /**
     * @param pay_pwd the pay_pwd to set
     */
    public void setPay_pwd(String pay_pwd) {
        this.pay_pwd = pay_pwd;
    }
    /**
     * @return the amount
     */
    public String getAmount() {
        return amount;
    }
    /**
     * @param amount the amount to set
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }
    /**
     * @return the txnseqno
     */
    public String getTxnseqno() {
        return txnseqno;
    }
    /**
     * @param txnseqno the txnseqno to set
     */
    public void setTxnseqno(String txnseqno) {
        this.txnseqno = txnseqno;
    }
    /**
     * @return the merchId
     */
    public String getMerchId() {
        return merchId;
    }
    /**
     * @param merchId the merchId to set
     */
    public void setMerchId(String merchId) {
        this.merchId = merchId;
    }
    /**
     * @param memberId
     * @param merchId
     * @param pay_pwd
     * @param amount
     * @param txnseqno
     */
    public AccountTradeBean(String memberId, String merchId, String pay_pwd,
            String amount, String txnseqno) {
        super();
        this.memberId = memberId;
        this.merchId = merchId;
        this.pay_pwd = pay_pwd;
        this.amount = amount;
        this.txnseqno = txnseqno;
    }
    
    
}
