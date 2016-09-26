/* 
 * BodyRetEnmu.java  
 * 
 * version TODO
 *
 * 2016年6月30日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.chanpay.enums;


/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年6月30日 下午2:52:13
 * @since 
 */
public enum BodyRetEnmu {

	SUCCESS("0000","处理成功"),
	ACCEPTED("0001","代理系统受理成功"),
	PROCESSING("0002","提交银行成功，等待查询结果"),
	NOAUTH("2013","收款行未开通业务"),
	FAILED("3999","其他错误"),
	UNKNOW("99","未知状态");
	private String code;
    private String messsage;
    
    private BodyRetEnmu(String code,String message){
        this.code = code;
        this.messsage = message;
    }
    
    public static BodyRetEnmu fromValue(String value) {
        for(BodyRetEnmu status:values()){
            if(status.code.equals(value)){
                return status;
            }
        }
        return UNKNOW;
    }
    
    public String getCode(){
        return code;
    }

	/**
	 * @return the messsage
	 */
	public String getMesssage() {
		return messsage;
	}
}
