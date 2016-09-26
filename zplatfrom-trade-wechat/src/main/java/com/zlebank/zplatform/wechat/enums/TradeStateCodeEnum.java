package com.zlebank.zplatform.wechat.enums;

public enum TradeStateCodeEnum {
	/** 支付成功 **/
	SUCCESS("SUCCESS"),
	/** 转入退款 **/
	REFUND("REFUND"),
	/*** 未支付 **/
	NOTPAY("NOTPAY"),
	/** 已关闭 **/
	CLOSED("CLOSED"),
	/** 已撤销（刷卡支付） **/
	REVOKED("REVOKED"),
	/** 用户支付中 **/
	USERPAYING("USERPAYING"),
	/** 支付失败 **/
	PAYERROR("PAYERROR"),
	//未知
	UNKNOW("UNKNOW");
	private String code;
	
	private TradeStateCodeEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	public static TradeStateCodeEnum fromValue(String value) {
        for(TradeStateCodeEnum busi:values()){
            if( value.equals(busi.getCode())){
                return busi;
            }
        }
        return UNKNOW;
    }
	
	
}
