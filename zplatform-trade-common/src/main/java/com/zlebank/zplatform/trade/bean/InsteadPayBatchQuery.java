/* 
 * InsteadPayBatchQuery.java  
 * 
 * version TODO
 *
 * 2015年12月21日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean;

import java.util.List;

import com.zlebank.zplatform.commons.bean.Bean;

/**
 * 
 * 代付批次查询条件
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年3月11日 下午5:38:51
 * @since
 */
public class InsteadPayBatchQuery implements Bean{

	/**代付批次**/
	private Long id;
    /**划拨批次号*/
    private String batchNo;
    /**开始时间*/
    private String beginDate;
    /**结束时间*/
    private String endDate;
    /*** 划拨状态*/
    private String status;
    /** 状态列表 **/
    private List<String> statusList;
    private String orderNo;
    
    
    
    public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBatchNo() {
        return batchNo;
    }
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }
    public String getBeginDate() {
        return beginDate;
    }
    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public List<String> getStatusList() {
        return statusList;
    }
    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }
}
