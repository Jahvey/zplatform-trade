/* 
 * TradeBean.java  
 * 
 * version TODO
 *
 * 2015年8月27日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean;

import java.io.Serializable;

import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.utils.AmountUtil;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年8月27日 下午8:25:07
 * @since 
 */
public class TradeBean implements Serializable,Cloneable{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7990669165684148748L;
    /**银行代码**/
    private String bankCode;
    /**商户订单号**/
    private String orderId;
    /**交易金额**/
    private String amount;
    /**银行卡号**/
    private String cardNo;
    /**账户名称**/    
    private String acctName;
    /**证件号**/
    private String certId;
    /**手机号**/
    private String mobile;
    /**短信验证码**/
    private String identifyingCode;
    /**交易类型**/
    private String tradeType;
    /**证件类型**/
    private String certType;
    /**备注**/
    private String resv;
    /**证联支付用户ID**/
    private String userId;
    /**交易序列号**/
    private String txnseqno;
    /**商户号**/
    private String merchId;
    /****/
    private String currentSetp;
    /**商户名称**/
    private String merchName;
    /**子商户号**/
    private String subMerchId="0";
    /**子商户名称**/
    private String subMerchName;
    /****/
    private String cashCode;
    /**业务代码**/
    private String busicode;
    /**业务类型**/
    private String busitype;
    /**银行卡类型**/
    private String cardType;
    /**商品名称**/
    private String goodsName;
    /**商品描述**/
    private String goodsDesc;
    /**cvv2**/
    private String cvv2;
    /**信用卡有效期**/
    private String validthru;
    /**证联金融会员号**/
    private String merUserId;
    /**绑卡标示**/
    private String bindCardId;
    /**支付机构标示**/
    private String payinstiId;
    /**融宝订单号**/
    private String reaPayOrderNo;
    /**有效期-年**/
    private String year;
    /**有效期-月**/
    private String month;
    /**绑卡主键**/
    private Long cardId=0L;
    /**后四位卡号**/
    private String miniCardNo; 
    /**交易金额（元）**/
    private String amount_y;
    /**交易tn号**/
    private String tn;
    /**账户余额**/
    private String balance;
    /**支付密码**/
    private String pay_pwd;
    /**支付会员IP**/
    private String memberIP;
    /**支付标志**/
    private String payFlag;
    /****/
    private String provno;
    /**交易日期 **/
    private String orderDate;
    /****/
    private String bankName;
    
    private String payOrderNo;
    
    private String devId;
    
    
    
    public String getDevId() {
		return devId;
	}
	public void setDevId(String devId) {
		this.devId = devId;
	}
	/**
     * @return the memberIP
     */
    public String getMemberIP() {
        return memberIP;
    }
    /**
     * @param memberIP the memberIP to set
     */
    public void setMemberIP(String memberIP) {
        this.memberIP = memberIP;
    }
    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    /**
     * @return the resv
     */
    public String getResv() {
        return resv;
    }
    /**
     * @param resv the resv to set
     */
    public void setResv(String resv) {
        this.resv = resv;
    }
    /**
     * @return the certType
     */
    public String getCertType() {
        return certType;
    }
    /**
     * @param certType the certType to set
     */
    public void setCertType(String certType) {
        this.certType = certType;
    }
    /**
     * @return the bankCode
     */
    public String getBankCode() {
        return bankCode;
    }
    /**
     * @param bankCode the bankCode to set
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
    /**
     * @return the orderId
     */
    public String getOrderId() {
        return orderId;
    }
    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
     * @return the acctName
     */
    public String getAcctName() {
        return acctName;
    }
    /**
     * @param acctName the acctName to set
     */
    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }
    /**
     * @return the mobile
     */
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
     * @return the identifyingCode
     */
    public String getIdentifyingCode() {
        return identifyingCode;
    }
    /**
     * @param identifyingCode the identifyingCode to set
     */
    public void setIdentifyingCode(String identifyingCode) {
        this.identifyingCode = identifyingCode;
    }
    /**
     * @return the tradeType
     */
    public String getTradeType() {
        return tradeType;
    }
    /**
     * @param tradeType the tradeType to set
     */
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }
    /**
     * @return the certId
     */
    public String getCertId() {
        return certId;
    }
    /**
     * @param certId the certId to set
     */
    public void setCertId(String certId) {
        this.certId = certId;
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
     * @return the currentSetp
     */
    public String getCurrentSetp() {
        return currentSetp;
    }
    /**
     * @param currentSetp the currentSetp to set
     */
    public void setCurrentSetp(String currentSetp) {
        this.currentSetp = currentSetp;
    }
    /**
     * @return the merchName
     */
    public String getMerchName() {
        return merchName;
    }
    /**
     * @param merchName the merchName to set
     */
    public void setMerchName(String merchName) {
        this.merchName = merchName;
    }
    /**
     * @return the subMerchId
     */
    public String getSubMerchId() {
        return subMerchId;
    }
    /**
     * @param subMerchId the subMerchId to set
     */
    public void setSubMerchId(String subMerchId) {
        this.subMerchId = subMerchId;
    }
    /**
     * @return the subMerchName
     */
    public String getSubMerchName() {
        return subMerchName;
    }
    /**
     * @param subMerchName the subMerchName to set
     */
    public void setSubMerchName(String subMerchName) {
        this.subMerchName = subMerchName;
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
     * @return the busicode
     */
    public String getBusicode() {
        return busicode;
    }
    /**
     * @param busicode the busicode to set
     */
    public void setBusicode(String busicode) {
        this.busicode = busicode;
    }
    /**
     * @return the cardType
     */
    public String getCardType() {
        return cardType;
    }
    /**
     * @param cardType the cardType to set
     */
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
    /**
     * @return the goodsName
     */
    public String getGoodsName() {
        return goodsName;
    }
    /**
     * @param goodsName the goodsName to set
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
    /**
     * @return the goodsDesc
     */
    public String getGoodsDesc() {
        return goodsDesc;
    }
    /**
     * @param goodsDesc the goodsDesc to set
     */
    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }
    /**
     * @return the cvv2
     */
    public String getCvv2() {
        return cvv2;
    }
    /**
     * @param cvv2 the cvv2 to set
     */
    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }
    /**
     * @return the validthru
     */
    public String getValidthru() {
        return validthru;
    }
    /**
     * @param validthru the validthru to set
     */
    public void setValidthru(String validthru) {
        this.validthru = validthru;
    }
    
    /**
     * @return the merUserId
     */
    public String getMerUserId() {
        return merUserId;
    }
    /**
     * @param merUserId the merUserId to set
     */
    public void setMerUserId(String merUserId) {
        this.merUserId = merUserId;
    }
    /**
     * 
     */
    public TradeBean() {
        super();
        
    }
    
    
    /**
     * @return the bindCardId
     */
    public String getBindCardId() {
        return bindCardId;
    }
    /**
     * @param bindCardId the bindCardId to set
     */
    public void setBindCardId(String bindCardId) {
        this.bindCardId = bindCardId;
    }
    public TradeBean(TxnsLogModel txnsLog) {
        this.bankCode = bankCode;
        this.orderId = txnsLog.getAccordno();
        this.amount = txnsLog.getAmount().toString();
        this.cardNo = cardNo;
        this.acctName = acctName;
        this.certId = certId;
        this.mobile = mobile;
        this.identifyingCode = identifyingCode;
        this.tradeType = tradeType;
        this.certType = certType;
        this.resv = resv;
        this.userId = userId;
        this.txnseqno = txnsLog.getTxnseqno();
        this.merchId = txnsLog.getAccfirmerno();
        this.currentSetp = currentSetp;
        this.merchName = merchName;
        this.subMerchId = subMerchId;
        this.subMerchName = subMerchName;
        this.cashCode = cashCode;
        this.busicode = busicode;
        this.cardType = cardType;
        this.goodsName = goodsName;
        this.goodsDesc = goodsDesc;
        this.cvv2 = cvv2;
        this.validthru = validthru;
    }
    /**
     * @param bankCode
     * @param orderId
     * @param amount
     * @param cardNo
     * @param acctName
     * @param certId
     * @param mobile
     * @param identifyingCode
     * @param tradeType
     * @param certType
     * @param resv
     * @param userId
     * @param txnseqno
     * @param merchId
     * @param currentSetp
     * @param merchName
     * @param subMerchId
     * @param subMerchName
     * @param cashCode
     * @param busicode
     * @param cardType
     * @param goodsName
     * @param goodsDesc
     * @param cvv2
     * @param validthru
     */
    public TradeBean(String bankCode, String orderId, String amount,
            String cardNo, String acctName, String certId, String mobile,
            String identifyingCode, String tradeType, String certType,
            String resv, String userId, String txnseqno, String merchId,
            String currentSetp, String merchName, String subMerchId,
            String subMerchName, String cashCode, String busicode,
            String cardType, String goodsName, String goodsDesc, String cvv2,
            String validthru) {
        super();
        this.bankCode = bankCode;
        this.orderId = orderId;
        this.amount = amount;
        this.cardNo = cardNo;
        this.acctName = acctName;
        this.certId = certId;
        this.mobile = mobile;
        this.identifyingCode = identifyingCode;
        this.tradeType = tradeType;
        this.certType = certType;
        this.resv = resv;
        this.userId = userId;
        this.txnseqno = txnseqno;
        this.merchId = merchId;
        this.currentSetp = currentSetp;
        this.merchName = merchName;
        this.subMerchId = subMerchId;
        this.subMerchName = subMerchName;
        this.cashCode = cashCode;
        this.busicode = busicode;
        this.cardType = cardType;
        this.goodsName = goodsName;
        this.goodsDesc = goodsDesc;
        this.cvv2 = cvv2;
        this.validthru = validthru;
    }
    
    /**
     * @param bankCode
     * @param orderId
     * @param amount
     * @param cardNo
     * @param acctName
     * @param certId
     * @param mobile
     * @param identifyingCode
     * @param tradeType
     * @param certType
     * @param resv
     * @param userId
     * @param txnseqno
     * @param merchId
     * @param currentSetp
     * @param merchName
     * @param subMerchId
     * @param subMerchName
     * @param cashCode
     * @param busicode
     * @param busitype
     * @param cardType
     * @param goodsName
     * @param goodsDesc
     * @param cvv2
     * @param validthru
     * @param merUserId
     * @param bindCardId
     * @param payinstiId
     * @param reaPayOrderNo
     * @param year
     * @param month
     * @param cardId
     * @param miniCardNo
     * @param amount_y
     * @param tn
     * @param balance
     * @param pay_pwd
     */
    public TradeBean(String bankCode, String orderId, String amount,
            String cardNo, String acctName, String certId, String mobile,
            String identifyingCode, String tradeType, String certType,
            String resv, String userId, String txnseqno, String merchId,
            String currentSetp, String merchName, String subMerchId,
            String subMerchName, String cashCode, String busicode,
            String busitype, String cardType, String goodsName,
            String goodsDesc, String cvv2, String validthru, String merUserId,
            String bindCardId, String payinstiId, String reaPayOrderNo,
            String year, String month, Long cardId, String miniCardNo,
            String amount_y, String tn, String balance, String pay_pwd) {
        super();
        this.bankCode = bankCode;
        this.orderId = orderId;
        this.amount = amount;
        this.cardNo = cardNo;
        this.acctName = acctName;
        this.certId = certId;
        this.mobile = mobile;
        this.identifyingCode = identifyingCode;
        this.tradeType = tradeType;
        this.certType = certType;
        this.resv = resv;
        this.userId = userId;
        this.txnseqno = txnseqno;
        this.merchId = merchId;
        this.currentSetp = currentSetp;
        this.merchName = merchName;
        this.subMerchId = subMerchId;
        this.subMerchName = subMerchName;
        this.cashCode = cashCode;
        this.busicode = busicode;
        this.busitype = busitype;
        this.cardType = cardType;
        this.goodsName = goodsName;
        this.goodsDesc = goodsDesc;
        this.cvv2 = cvv2;
        this.validthru = validthru;
        this.merUserId = merUserId;
        this.bindCardId = bindCardId;
        this.payinstiId = payinstiId;
        this.reaPayOrderNo = reaPayOrderNo;
        this.year = year;
        this.month = month;
        this.cardId = cardId;
        this.miniCardNo = miniCardNo;
        this.amount_y = amount_y;
        this.tn = tn;
        this.balance = balance;
        this.pay_pwd = pay_pwd;
    }
    /**
     * @return the payinstiId
     */
    public String getPayinstiId() {
        return payinstiId;
    }
    /**
     * @param payinstiId the payinstiId to set
     */
    public void setPayinstiId(String payinstiId) {
        this.payinstiId = payinstiId;
    }
    /**
     * @return the reaPayOrderNo
     */
    public String getReaPayOrderNo() {
        return reaPayOrderNo;
    }
    /**
     * @param reaPayOrderNo the reaPayOrderNo to set
     */
    public void setReaPayOrderNo(String reaPayOrderNo) {
        this.reaPayOrderNo = reaPayOrderNo;
    }
    /**
     * @return the year
     */
    public String getYear() {
        return year;
    }
    /**
     * @param year the year to set
     */
    public void setYear(String year) {
        this.year = year;
    }
    /**
     * @return the month
     */
    public String getMonth() {
        return month;
    }
    /**
     * @param month the month to set
     */
    public void setMonth(String month) {
        this.month = month;
    }
    /**
     * @return the busitype
     */
    public String getBusitype() {
        return busitype;
    }
    /**
     * @param busitype the busitype to set
     */
    public void setBusitype(String busitype) {
        this.busitype = busitype;
    }
    /**
     * @return the cardId
     */
    public Long getCardId() {
        return cardId;
    }
    /**
     * @param cardId the cardId to set
     */
    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }
    /**
     * @return the miniCardNo
     */
    public String getMiniCardNo() {
    	
    	
        return StringUtil.isNotEmpty(cardNo)?cardNo.substring(cardNo.length()-4):"";
    }
    /**
     * @param miniCardNo the miniCardNo to set
     */
    public void setMiniCardNo(String miniCardNo) {
        this.miniCardNo = miniCardNo;
    }
    /**
     * @return the amount_y
     */
    
    public String getAmount_y() {
        return AmountUtil.numberFormat(Long.valueOf(StringUtil.isNotEmpty(amount)?amount:"0"));
    }
    /**
     * @param amount_y the amount_y to set
     */
    public void setAmount_y(String amount_y) {
        this.amount_y = amount_y;
    }
    /**
     * @return the tn
     */
    public String getTn() {
        return tn;
    }
    /**
     * @param tn the tn to set
     */
    public void setTn(String tn) {
        this.tn = tn;
    }
    /**
     * @return the balance
     */
    public String getBalance() {
        return balance;
    }
    /**
     * @param balance the balance to set
     */
    public void setBalance(String balance) {
        this.balance = balance;
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
    public TradeBean clone(){
        TradeBean o = null; 
        try {
            o = (TradeBean)super.clone();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        return o; 
    }
    /**
     * @return the payFlag
     */
    public String getPayFlag() {
        return payFlag;
    }
    /**
     * @param payFlag the payFlag to set
     */
    public void setPayFlag(String payFlag) {
        this.payFlag = payFlag;
    }
    public String getProvno() {
        return provno;
    }
    public void setProvno(String provno) {
        this.provno = provno;
    }
	/**
	 * @return the orderDate
	 */
	public String getOrderDate() {
		return orderDate;
	}
	/**
	 * @param orderDate the orderDate to set
	 */
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}
	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	/**
	 * @return the payOrderNo
	 */
	public String getPayOrderNo() {
		return payOrderNo;
	}
	/**
	 * @param payOrderNo the payOrderNo to set
	 */
	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo;
	}
    
    
}
