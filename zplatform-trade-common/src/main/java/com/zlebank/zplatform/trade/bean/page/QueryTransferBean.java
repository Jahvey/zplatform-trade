package com.zlebank.zplatform.trade.bean.page;

import java.io.Serializable;
import java.util.Date;

public class QueryTransferBean implements Serializable{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -8870101289238446680L;
    /**划拨批次ID*/
    public long tid;
    /**划拨批次号*/
    public String batchNo;
    /**开始时间*/
    public String beginDate;
    /**结束时间*/
    public String endDate;
    /*** 划拨状态*/
    public String status;
    /**业务类型**/
    private String busiType;
    /**关闭事件*/
    private String openStatus;
    /**转账状态**/
    private String tranStatus;
    /**商户订单号**/
    private String merchOrderNo;
    
    public long getTid() {
        return tid;
    }
    public void setTid(long tid) {
        this.tid = tid;
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
	public String getBusiType() {
		return busiType;
	}
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	public String getOpenStatus() {
		return openStatus;
	}
	public void setOpenStatus(String openStatus) {
		this.openStatus = openStatus;
	}
	public String getTranStatus() {
		return tranStatus;
	}
	public void setTranStatus(String tranStatus) {
		this.tranStatus = tranStatus;
	}
	/**
	 * @return the merchOrderNo
	 */
	public String getMerchOrderNo() {
		return merchOrderNo;
	}
	/**
	 * @param merchOrderNo the merchOrderNo to set
	 */
	public void setMerchOrderNo(String merchOrderNo) {
		this.merchOrderNo = merchOrderNo;
	}
	
    
}
