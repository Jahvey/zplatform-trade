package com.zlebank.zplatform.trade.bean.gateway;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class BailRechargeOrderBean  implements Serializable {
	private static final long serialVersionUID = 1L;
	
		@NotEmpty(message="param.empty.coopInstiId")
        @Length(max=15,message="param.error.coopInstiId")
	    private String coopInstiId="";
	    @Length(max=15,message="param.error.fromMerId")
	    private String fromMerId="";
	    @Length(max=32,message="param.error.orderId")
	    private String orderId="";
	    @NotEmpty(message="param.empty.txnType")
	    @Length(max=2,message="param.error.txnType")
	    private String txnType="";
	    @NotEmpty(message="param.empty.txnSubType")
	    @Length(max=2,message="param.error.txnSubType")
	    private String txnSubType="";
	    @NotEmpty(message="param.empty.bizType")
	    @Length(max=6,message="param.error.bizType")
	    private String bizType="";
	    @NotEmpty(message="param.empty.txnAmt")
	    @Length(max=12,message="param.error.txnAmt")
	    private String txnAmt="";
	    @NotEmpty(message="param.empty.currencyCode")
	    @Length(max=3,message="param.error.currencyCode")
	    private String currencyCode="";
	    @Length(max=32,message="param.error.orderDesc")
	    private String orderDesc="";
	    @NotEmpty(message="param.empty.orderTimeout")
	    @Length(max=10,message="param.error.orderTimeout")
	    private String orderTimeout="";
	    @Length(max=14,message="param.error.txnTime")
	    private String txnTime="";
	    @Length(max=256,message="param.error.frontUrl")
	    private String frontUrl="";
	    @Length(max=256,message="param.error.backUrl")
	    private String backUrl="";
	    @Length(max=40,message="param.error.customerIp")
	    private String customerIp="";
	    @NotEmpty(message="param.empty.acctNo")
        @Length(max=60,message="param.error.acctNo")
	    private String acctNo="";
	    @NotEmpty(message="param.empty.accName")
        @Length(max=32,message="param.error.accName")
	    private String accName="";
	    @NotEmpty(message="param.empty.bankAccNo")
        @Length(max=22,message="param.error.bankAccNo")
	    private String bankAccNo="";
	    @NotEmpty(message="param.empty.corporateName")
        @Length(max=32,message="param.error.corporateName")
	    private String corporateName="";
	    @NotEmpty(message="param.empty.corporateCertType")
        @Length(max=2,message="param.error.corporateCertType")
	    private String corporateCertType="";
	    @NotEmpty(message="param.empty.corporateCertId")
        @Length(max=18,message="param.error.corporateCertId")
	    private String corporateCertId="";
	    
	    
		public String getCustomerIp() {
			return customerIp;
		}
		public void setCustomerIp(String customerIp) {
			this.customerIp = customerIp;
		}
		
		public String getFrontUrl() {
			return frontUrl;
		}
		public void setFrontUrl(String frontUrl) {
			this.frontUrl = frontUrl;
		}
		public String getBackUrl() {
			return backUrl;
		}
		public void setBackUrl(String backUrl) {
			this.backUrl = backUrl;
		}
		public String getCoopInstiId() {
			return coopInstiId;
		}
		public void setCoopInstiId(String coopInstiId) {
			this.coopInstiId = coopInstiId;
		}
		public String getFromMerId() {
			return fromMerId;
		}
		public void setFromMerId(String fromMerId) {
			this.fromMerId = fromMerId;
		}
		public String getOrderId() {
			return orderId;
		}
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
		public String getTxnType() {
			return txnType;
		}
		public void setTxnType(String txnType) {
			this.txnType = txnType;
		}
		public String getTxnSubType() {
			return txnSubType;
		}
		public void setTxnSubType(String txnSubType) {
			this.txnSubType = txnSubType;
		}
		public String getBizType() {
			return bizType;
		}
		public void setBizType(String bizType) {
			this.bizType = bizType;
		}
		public String getTxnAmt() {
			return txnAmt;
		}
		public void setTxnAmt(String txnAmt) {
			this.txnAmt = txnAmt;
		}
		public String getCurrencyCode() {
			return currencyCode;
		}
		public void setCurrencyCode(String currencyCode) {
			this.currencyCode = currencyCode;
		}
		public String getOrderDesc() {
			return orderDesc;
		}
		public void setOrderDesc(String orderDesc) {
			this.orderDesc = orderDesc;
		}
		public String getOrderTimeout() {
			return orderTimeout;
		}
		public void setOrderTimeout(String orderTimeout) {
			this.orderTimeout = orderTimeout;
		}
		public String getTxnTime() {
			return txnTime;
		}
		public void setTxnTime(String txnTime) {
			this.txnTime = txnTime;
		}
		public String getAcctNo() {
			return acctNo;
		}
		public void setAcctNo(String acctNo) {
			this.acctNo = acctNo;
		}
		public String getAccName() {
			return accName;
		}
		public void setAccName(String accName) {
			this.accName = accName;
		}
		public String getBankAccNo() {
			return bankAccNo;
		}
		public void setBankAccNo(String bankAccNo) {
			this.bankAccNo = bankAccNo;
		}
		public String getCorporateName() {
			return corporateName;
		}
		public void setCorporateName(String corporateName) {
			this.corporateName = corporateName;
		}
		public String getCorporateCertType() {
			return corporateCertType;
		}
		public void setCorporateCertType(String corporateCertType) {
			this.corporateCertType = corporateCertType;
		}
		public String getCorporateCertId() {
			return corporateCertId;
		}
		public void setCorporateCertId(String corporateCertId) {
			this.corporateCertId = corporateCertId;
		}
		
	    
}
