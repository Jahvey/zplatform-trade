/* 
 * UpdateData.java  
 * 
 * version TODO
 *
 * 2016年3月16日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean;

import java.math.BigDecimal;

/**
 * Class Description
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年3月16日 上午11:09:54
 * @since 
 */
public class UpdateData {
	/**
	 * 交易序列号
	 */
    private String txnSeqNo;
    /**
     * 应答码
     */
    private String resultCode;
    /**
     * 应答信息
     */
    private String resultMessage;
    /**
     * 渠道代码
     */
    private String channelCode;
    /**
     * 渠道手续费
     */
    private BigDecimal channelFee;

    public String getTxnSeqNo() {
        return txnSeqNo;
    }
    public void setTxnSeqNo(String txnSeqNo) {
        this.txnSeqNo = txnSeqNo;
    }
    public String getResultCode() {
        return resultCode;
    }
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
    public String getResultMessage() {
        return resultMessage;
    }
    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
    public String getChannelCode() {
        return channelCode;
    }
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }
	/**
	 * @return the channelFee
	 */
	public BigDecimal getChannelFee() {
		return channelFee;
	}
	/**
	 * @param channelFee the channelFee to set
	 */
	public void setChannelFee(BigDecimal channelFee) {
		this.channelFee = channelFee;
	}
    
}
