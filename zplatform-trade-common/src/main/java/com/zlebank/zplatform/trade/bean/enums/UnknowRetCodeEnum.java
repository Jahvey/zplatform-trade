/* 
 * UnknowRetCodeEnum.java  
 * 
 * version TODO
 *
 * 2016年7月26日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean.enums;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年7月26日 上午11:05:50
 * @since 
 */
public enum UnknowRetCodeEnum {

	/**
	 *  证联支付	30
		融宝	31
		民生	32
		畅捷网关	33
		微信	34
		博世金电	35
		畅捷代收	36
	 */
	
	ZLPAY("3099","98000001"),
	REAPAY("3199","96000001"),
	CMBC("3299","93000002"),
	CHANPAYGATEWAY("3399","90000001"),
	WECHAT("3499","91000001"),
	BOSSPAY("3599","92000001"),
	CHANPAYCOLLECT("3699","90000002"),
	UNKNOW("9999","");
	private String code;
	private String channl;
	
	private UnknowRetCodeEnum(String code,String channl){
		this.code = code;
		this.channl = channl;
	}
	
	
	public static UnknowRetCodeEnum fromValue(String value){
		for(UnknowRetCodeEnum retCodeEnum:values()){
            if(retCodeEnum.code.equals(value)){
                return retCodeEnum;
            }
        }
        return UNKNOW;
	}
	public static UnknowRetCodeEnum fromChannl(String value){
		for(UnknowRetCodeEnum retCodeEnum:values()){
            if(retCodeEnum.channl.equals(value)){
                return retCodeEnum;
            }
        }
        return UNKNOW;
	}
	
	public String getCode(){
		return this.code;
	}
}
