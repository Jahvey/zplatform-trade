/* 
 * MerchanReimbursementBean.java  
 * 
 * version TODO
 *
 * 2016年8月25日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean;

import java.io.Serializable;
import java.util.List;


/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年8月25日 上午10:53:54
 * @since 
 */
public class MerchantReimbursementBean implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7763297324225522282L;
	
	private String memberId;//	商户号
	private String batchNo	;//还款批次号
	private String totalAmt	;//还款总金额
	private String productCode	;//产品代码
	private String coopInsti; //合作机构
	private List<ReimbursementDetailBean> detaiList;//还款明细列表
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
	 * @return the batchNo
	 */
	public String getBatchNo() {
		return batchNo;
	}
	/**
	 * @param batchNo the batchNo to set
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	/**
	 * @return the totalAmt
	 */
	public String getTotalAmt() {
		return totalAmt;
	}
	/**
	 * @param totalAmt the totalAmt to set
	 */
	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}
	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}
	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	/**
	 * @return the detaiList
	 */
	public List<ReimbursementDetailBean> getDetaiList() {
		return detaiList;
	}
	/**
	 * @param detaiList the detaiList to set
	 */
	public void setDetaiList(List<ReimbursementDetailBean> detaiList) {
		this.detaiList = detaiList;
	}
	/**
	 * @return the coopInsti
	 */
	public String getCoopInsti() {
		return coopInsti;
	}
	/**
	 * @param coopInsti the coopInsti to set
	 */
	public void setCoopInsti(String coopInsti) {
		this.coopInsti = coopInsti;
	}
	
	
	
}
