/* 
 * BankTransferBatchStatusEnum.java  
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
 * Class Description
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年3月7日 下午5:30:24
 * @since 
 */
public enum BankTransferBatchStatusEnum {
    /**未划拨**/
    INIT("01"),
    /**部分通过**/
    PART_APPROVED("09"),
    /**全部通过**/
    ALL_APPROVED("11"),
    /**全部转账完成**/
    ALL_FINISHED("19"),
    /**未知代码**/
    UNKNOW("99");
    private String code;
    
    private BankTransferBatchStatusEnum(String code){
        this.code = code;
    }
    
    
    public static BankTransferBatchStatusEnum fromValue(String value) {
        for(BankTransferBatchStatusEnum status:values()){
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
