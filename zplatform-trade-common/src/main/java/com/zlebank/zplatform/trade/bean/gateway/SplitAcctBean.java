/* 
 * SplitAcctBean.java  
 * 
 * version TODO
 *
 * 2015年10月30日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean.gateway;

import java.io.Serializable;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年10月30日 上午10:33:33
 * @since 
 */
public class SplitAcctBean implements Serializable{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4848619913167361368L;
    private String merId;// 商户会员号
    private String txnAmt;// 交易金额
    private String  commAmt;//交易佣金
    /**
     * @return the merId
     */
    public String getMerId() {
        return merId;
    }
    /**
     * @param merId the merId to set
     */
    public void setMerId(String merId) {
        this.merId = merId;
    }
    /**
     * @return the txnAmt
     */
    public String getTxnAmt() {
        return txnAmt;
    }
    /**
     * @param txnAmt the txnAmt to set
     */
    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }
    /**
     * @return the commAmt
     */
    public String getCommAmt() {
        return commAmt;
    }
    /**
     * @param commAmt the commAmt to set
     */
    public void setCommAmt(String commAmt) {
        this.commAmt = commAmt;
    }
    /**
     * 
     */
    public SplitAcctBean() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * @param merId
     * @param txnAmt
     * @param commAmt
     */
    public SplitAcctBean(String merId, String txnAmt, String commAmt) {
        super();
        this.merId = merId;
        this.txnAmt = txnAmt;
        this.commAmt = commAmt;
    }
    
    
}
