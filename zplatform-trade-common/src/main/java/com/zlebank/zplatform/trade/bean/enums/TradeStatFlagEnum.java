/* 
 * TradeStatFlagEnum.java  
 * 
 * version TODO
 *
 * 2016年7月15日 
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
 * @date 2016年7月15日 下午3:41:09
 * @since 
 */
public enum TradeStatFlagEnum {

	INITIAL("1000000"),
	READY("0100000"),
	PAYING("0010000"),
	OVERTIME("0000010"),
	FINISH_FAILED("0001001"),
	FINISH_SUCCESS("0000100"),
	FINISH_ACCOUNTING("0000101"),
	FINSH("0000001"),
	UNKNOW("");
	
	private String status;
    
    private TradeStatFlagEnum(String status){
        this.status = status;
    }
    
    public static TradeStatFlagEnum fromValue(String value) {
        for(TradeStatFlagEnum enums:values()){
            if(enums.status.equals(value)){
                return enums;
            }
        }
        return UNKNOW;
    }
    
    public String getStatus(){
        return status;
    }
}
