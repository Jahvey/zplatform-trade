/* 
 * BankTransferBatchOpenStatusEnum.java  
 * 
 * version TODO
 *
 * 2016年3月7日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean.enums;

/**
 * 转账批次打开状态
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年3月7日 下午5:36:59
 * @since 
 */
public enum BankTransferBatchOpenStatusEnum {
    /**开放**/
    OPEN("0"),
    /**关闭**/
    CLOSE("1"),
    /**未知代码**/
    UNKNOW("9");
    private String code;
    
    private BankTransferBatchOpenStatusEnum(String code){
        this.code = code;
    }
    
    
    public static BankTransferBatchOpenStatusEnum fromValue(String value) {
        for(BankTransferBatchOpenStatusEnum status:values()){
            if(status.code.equals(value)){
                return status;
            }
        }
        return UNKNOW;
    }
    
    public String getCode(){
        return code;
    }
}
