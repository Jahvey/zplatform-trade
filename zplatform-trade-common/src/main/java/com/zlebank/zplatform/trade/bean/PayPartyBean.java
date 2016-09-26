/* 
 * PayPartyBean.java  
 * 
 * version TODO
 *
 * 2015年9月2日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean;

import java.io.Serializable;

import com.zlebank.zplatform.trade.bean.enums.ChnlTypeEnum;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月2日 下午4:15:34
 * @since 
 */
public class PayPartyBean implements Serializable{
    
    private String txnseqno;//交易序列号
    private String paytype;//支付类型（01：快捷，02：网银，03：账户）
    private String payordno;//支付定单号
    private String payinst;//支付所属机构
    private String payfirmerno;//支付一级商户号
    private String paysecmerno;//支付二级商户号
    private String payordcomtime;//支付定单提交时间
    private String payordfintime;//支付定单完成时间
    private String cardNo;//交易卡号
    
    private String rout;//路由
    private String routlvl;//路由版本
    
    private String payrettsnseqno;
    private String payretcode;
    private String payretinfo;
    private String cashCode;
    
    private String panName;
    
    private ChnlTypeEnum chnlTypeEnum;
    /**
     * @return the paytype
     */
    public String getPaytype() {
        return paytype;
    }
    /**
     * @param paytype the paytype to set
     */
    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }
    /**
     * @return the payordno
     */
    public String getPayordno() {
        return payordno;
    }
    /**
     * @param payordno the payordno to set
     */
    public void setPayordno(String payordno) {
        this.payordno = payordno;
    }
    /**
     * @return the payinst
     */
    public String getPayinst() {
        return payinst;
    }
    /**
     * @param payinst the payinst to set
     */
    public void setPayinst(String payinst) {
        this.payinst = payinst;
    }
    /**
     * @return the payfirmerno
     */
    public String getPayfirmerno() {
        return payfirmerno;
    }
    /**
     * @param payfirmerno the payfirmerno to set
     */
    public void setPayfirmerno(String payfirmerno) {
        this.payfirmerno = payfirmerno;
    }
    /**
     * @return the paysecmerno
     */
    public String getPaysecmerno() {
        return paysecmerno;
    }
    /**
     * @param paysecmerno the paysecmerno to set
     */
    public void setPaysecmerno(String paysecmerno) {
        this.paysecmerno = paysecmerno;
    }
    /**
     * @return the payordcomtime
     */
    public String getPayordcomtime() {
        return payordcomtime;
    }
    /**
     * @param payordcomtime the payordcomtime to set
     */
    public void setPayordcomtime(String payordcomtime) {
        this.payordcomtime = payordcomtime;
    }
    /**
     * @return the payordfintime
     */
    public String getPayordfintime() {
        return payordfintime;
    }
    /**
     * @param payordfintime the payordfintime to set
     */
    public void setPayordfintime(String payordfintime) {
        this.payordfintime = payordfintime;
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
     * @return the cardNo
     */
    public String getCardNo() {
        return cardNo;
    }
    /**
     * @param cardNo the cardNo to set
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
    
    
    /**
     * @return the rout
     */
    public String getRout() {
        return rout;
    }
    /**
     * @param rout the rout to set
     */
    public void setRout(String rout) {
        this.rout = rout;
    }
    /**
     * @return the routlvl
     */
    public String getRoutlvl() {
        return routlvl;
    }
    /**
     * @param routlvl the routlvl to set
     */
    public void setRoutlvl(String routlvl) {
        this.routlvl = routlvl;
    }
    /**
     * @param txnseqno
     * @param paytype
     * @param payordno
     * @param payinst
     * @param payfirmerno
     * @param paysecmerno
     * @param payordcomtime
     * @param payordfintime
     * @param cardNo
     * @param rout
     * @param routlvl
     */
    public PayPartyBean(String txnseqno, String paytype, String payordno,
            String payinst, String payfirmerno, String paysecmerno,
            String payordcomtime, String payordfintime, String cardNo
            ) {
        super();
        this.txnseqno = txnseqno;
        this.paytype = paytype;
        this.payordno = payordno;
        this.payinst = payinst;
        this.payfirmerno = payfirmerno;
        this.paysecmerno = paysecmerno;
        this.payordcomtime = payordcomtime;
        this.payordfintime = payordfintime;
        this.cardNo = cardNo;
        
    }
    public PayPartyBean(String txnseqno, String paytype, String payordno,
            String payinst, String payfirmerno, String paysecmerno,
            String payordcomtime, String payordfintime, String cardNo,String payrettsnseqno
            ) {
        super();
        this.txnseqno = txnseqno;
        this.paytype = paytype;
        this.payordno = payordno;
        this.payinst = payinst;
        this.payfirmerno = payfirmerno;
        this.paysecmerno = paysecmerno;
        this.payordcomtime = payordcomtime;
        this.payordfintime = payordfintime;
        this.cardNo = cardNo;
        this.payrettsnseqno = payrettsnseqno;
        
    }
    /**
     * @return the payrettsnseqno
     */
    public String getPayrettsnseqno() {
        return payrettsnseqno;
    }
    /**
     * @param payrettsnseqno the payrettsnseqno to set
     */
    public void setPayrettsnseqno(String payrettsnseqno) {
        this.payrettsnseqno = payrettsnseqno;
    }
    /**
     * @return the payretcode
     */
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
    public String getPayretinfo() {
        return payretinfo;
    }
    /**
     * @param payretinfo the payretinfo to set
     */
    public void setPayretinfo(String payretinfo) {
        this.payretinfo = payretinfo;
    }
    /**
     * @return the cashCode
     */
    public String getCashCode() {
        return cashCode;
    }
    /**
     * @param cashCode the cashCode to set
     */
    public void setCashCode(String cashCode) {
        this.cashCode = cashCode;
    }
	/**

	 * 
	 */
	public PayPartyBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	 
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
	 * @return the chnlTypeEnum
	 */
	public ChnlTypeEnum getChnlTypeEnum() {
		return chnlTypeEnum;
	}
	/**
	 * @param chnlTypeEnum the chnlTypeEnum to set
	 */
	public void setChnlTypeEnum(ChnlTypeEnum chnlTypeEnum) {
		this.chnlTypeEnum = chnlTypeEnum;
	}
    
    
    

}
