/* 
 * RefundTypeEnum.java  
 * 
 * version TODO
 *
 * 2016年6月8日 
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
 * @date 2016年6月8日 上午10:38:34
 * @since 
 */
public enum RefundTypeEnum {

	ORIGINAL("0","ORIGINAL"),
	BALANCE("1","BALANCE"),
	UNKNOW("","");
	private String code;
	private String wechat_code;
	/**
	 * @param code
	 */
	private RefundTypeEnum(String code,String wechat_code) {
		this.code = code;
		this.wechat_code = wechat_code;
	}

	public static RefundTypeEnum fromValue(String value) {
        for(RefundTypeEnum status:values()){
            if(status.code==value){
                return status;
            }
        }
        return UNKNOW;
    }
	
	public static RefundTypeEnum fromWeChatValue(String value) {
        for(RefundTypeEnum status:values()){
            if(value.equals(status.wechat_code)){
                return status;
            }
        }
        return UNKNOW;
    }
    
    public String getCode(){
        return code;
    }

	/**
	 * @return the wechat_code
	 */
	public String getWechat_code() {
		return wechat_code;
	}
    
    
}
