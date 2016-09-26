/* 
 * QuerytranstatusBean.java  
 * 
 * version TODO
 *
 * 2015年8月21日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean.zlpay;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年8月21日 下午1:30:31
 * @since 
 */
public class QuerytranstatusBean {

    String instuId;
    String merchantDate;
    String merchantTime;
    String merchantSeqId;
    String merchantLiqDate;
    String tradeType;
    String tiedCardType;
    String resv;
    /**
     * @param instuId
     * @param merchantDate
     * @param merchantTime
     * @param merchantSeqId
     * @param merchantLiqDate
     * @param tradeType
     * @param tiedCardType
     * @param resv
     */
    public QuerytranstatusBean(String instuId, String merchantDate,
            String merchantTime, String merchantSeqId, String merchantLiqDate,
            String tradeType, String tiedCardType, String resv) {
        super();
        this.instuId = instuId;
        this.merchantDate = merchantDate;
        this.merchantTime = merchantTime;
        this.merchantSeqId = merchantSeqId;
        this.merchantLiqDate = merchantLiqDate;
        this.tradeType = tradeType;
        this.tiedCardType = tiedCardType;
        this.resv = resv;
    }
    /**
     * @return the instuId
     */
    public String getInstuId() {
        return instuId;
    }
    /**
     * @param instuId the instuId to set
     */
    public void setInstuId(String instuId) {
        this.instuId = instuId;
    }
    /**
     * @return the merchantDate
     */
    public String getMerchantDate() {
        return merchantDate;
    }
    /**
     * @param merchantDate the merchantDate to set
     */
    public void setMerchantDate(String merchantDate) {
        this.merchantDate = merchantDate;
    }
    /**
     * @return the merchantTime
     */
    public String getMerchantTime() {
        return merchantTime;
    }
    /**
     * @param merchantTime the merchantTime to set
     */
    public void setMerchantTime(String merchantTime) {
        this.merchantTime = merchantTime;
    }
    /**
     * @return the merchantSeqId
     */
    public String getMerchantSeqId() {
        return merchantSeqId;
    }
    /**
     * @param merchantSeqId the merchantSeqId to set
     */
    public void setMerchantSeqId(String merchantSeqId) {
        this.merchantSeqId = merchantSeqId;
    }
    /**
     * @return the merchantLiqDate
     */
    public String getMerchantLiqDate() {
        return merchantLiqDate;
    }
    /**
     * @param merchantLiqDate the merchantLiqDate to set
     */
    public void setMerchantLiqDate(String merchantLiqDate) {
        this.merchantLiqDate = merchantLiqDate;
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
     * @return the tiedCardType
     */
    public String getTiedCardType() {
        return tiedCardType;
    }
    /**
     * @param tiedCardType the tiedCardType to set
     */
    public void setTiedCardType(String tiedCardType) {
        this.tiedCardType = tiedCardType;
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
    
    
}
