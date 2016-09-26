/* 
 * ContractPaymentRequest.java  
 * 
 * version TODO
 *
 * 2015年10月9日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.message;

import com.zlebank.zplatform.trade.validator.An;
import com.zlebank.zplatform.trade.validator.N;

/**
 * 签约支付【请求报文】
 *
 * @author yangpeng
 * @version
 * @date 2015年10月9日 下午3:25:40
 * @since 
 */
public class SignPay_Request  extends BaseMessage {

    /**会员ID **/
    @N(max=15,isNull=false)
    private String memberId;

    /**渠道类型**/
    @N(max=2,isNull=false)
    private String channelType;
    /**接入类型**/
    @An(max=1,isNull=false)
    private String accessType;
    /**受理订单号**/
    @An
    private String tn;
    /**交易金额**/
    @An(max=12,isNull=false)
    private String txnAmt ;
    /**交易币种**/
    @N(max=3,isNull=false)
    private String currencyCode ;
    /**短信验证码**/
    @An
    private String smsCode;
    /**绑卡标识**/
    @An
    private String bindId;
    

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
    public String getSmsCode() {
        return smsCode;
    }
    public String getBindId() {
        return bindId;
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
    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
    public void setBindId(String bindId) {
        this.bindId = bindId;
    }
    
    

}
