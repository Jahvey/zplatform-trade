package com.zlebank.zplatform.trade.bean.gateway;

import java.io.Serializable;


public class QueryAccResultBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//资金余额
	private String balance;
	//可用余额
	private String  avaiableBalance;
    //冻结金额
	private String frozenAmount;
	//状态
	private String status;
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getAvaiableBalance() {
		return avaiableBalance;
	}
	public void setAvaiableBalance(String avaiableBalance) {
		this.avaiableBalance = avaiableBalance;
	}
	public String getFrozenAmount() {
		return frozenAmount;
	}
	public void setFrozenAmount(String frozenAmount) {
		this.frozenAmount = frozenAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	  
	    
	    
		
		
	    
}
