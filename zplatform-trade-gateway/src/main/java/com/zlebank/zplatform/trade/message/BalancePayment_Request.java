/* 
 * BalancePaymentRequest.java  
 * 
 * version TODO
 *
 * 2015年10月9日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.message;

import org.hibernate.validator.constraints.NotEmpty;

import com.zlebank.zplatform.trade.validator.An;
import com.zlebank.zplatform.trade.validator.N;

/**
 * 余额支付【请求报文】
 *
 * @author yangpeng
 * @version
 * @date 2015年10月9日 下午4:00:31
 * @since 
 */
public class BalancePayment_Request  extends BaseMessage {

    /**会员ID **/
    @An(max=15,isNull=false)
    private String memberId;
    /**渠道类型**/
    @N(max=2,isNull=false)
    private String channelType;
    /**接入类型**/
    @N(max=1,isNull=false)
    private String accessType;
    /**受理订单号**/
    @An(isNull=false)
    private String tn;
    /**交易金额**/
    @N(max=15,isNull=false)
    private String txnAmt ;
    /**交易币种**/
    @An(max=3,isNull=false)
    private String currencyCode ;
    /**支付密码**/
    @NotEmpty
    private String paypassWd;

    public String getMemberId() {
        return memberId;
    }
    public String getChannelType() {
        return channelType;
    }
    public String getAccessType() {
        return accessType;
    }
    public String getTn() {
        return tn;
    }
    public String getTxnAmt() {
        return txnAmt;
    }
    public String getCurrencyCode() {
        return currencyCode;
    }
    public String getPaypassWd() {
        return paypassWd;
    }
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }
    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }
    public void setTn(String tn) {
        this.tn = tn;
    }
    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
    public void setPaypassWd(String paypassWd) {
        this.paypassWd = paypassWd;
    }
}
