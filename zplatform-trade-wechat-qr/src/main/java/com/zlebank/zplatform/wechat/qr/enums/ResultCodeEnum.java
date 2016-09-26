/* 
 * ResultCodeEnum.java  
 * 
 * version TODO
 *
 * 2016年5月25日 
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
 * @date 2016年5月25日 下午6:30:22
 * @since 
 */
public enum ResultCodeEnum {
	
    /** SUCCESS **/
    SUCCESS("SUCCESS"),
    /** REFUND **/
    FAIL("FAIL"),
    UNKNOW("UNKNOW");//未知

    private String billType;
    
    private ResultCodeEnum(String billType){
        this.billType = billType;
    }
    
    public static ResultCodeEnum fromValue(String value) {
        for(ResultCodeEnum busi:values()){
            if( value.equals(busi.billType)){
                return busi;
            }
        }
        return UNKNOW;
    }
    public String getCode(){
        return billType;
    }
}
