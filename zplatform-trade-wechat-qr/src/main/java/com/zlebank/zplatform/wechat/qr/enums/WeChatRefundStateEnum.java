/* 
 * RefundStateEnum.java  
 * 
 * version TODO
 *
 * 2016年7月26日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.wechat.qr.enums;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年7月26日 下午5:33:41
 * @since 
 */
public enum WeChatRefundStateEnum {
	/** SUCCESS **/
    SUCCESS("SUCCESS"),
    /** REFUND **/
    FAIL("FAIL"),
    PROCESSING("PROCESSING"),//处理中
    NOTSURE("NOTSURE"),//未确定，需要商户原退款单号重新发起
    CHANGE("CHANGE"),//转入代发，退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，资金回流到商户的现金帐号，需要商户人工干预，通过线下或者财付通转账的方式进行退款。
    UNKNOW("UNKNOW");//未知
   

    private String code;
    
    private WeChatRefundStateEnum(String code){
        this.code = code;
    }
    
    public static WeChatRefundStateEnum fromValue(String value) {
        for(WeChatRefundStateEnum refundStateEnum:values()){
            if( value.equals(refundStateEnum.code)){
                return refundStateEnum;
            }
        }
        return UNKNOW;
    }
    public String getCode(){
        return code;
    }
}
