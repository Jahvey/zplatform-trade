/* 
 * BankTransferBatchTranStatusEnum.java  
 * 
 * version TODO
 *
 * 2016年3月9日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean.enums;

/**
 * 转账批次转账状态
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年3月9日 上午10:15:58
 * @since 
 */
public enum BankTransferBatchTranStatusEnum {
    /**等待转账**/
    WAIT_TRAN("01"),
    /**部分转账成功**/
    PART_TRAN_SUCCESS("02"),
    /**全部转账成功**/
    ALL_TRAN_SUCCESS("03"),
    /**全部转账失败**/
    ALL_TRAN_FAILED("04"),
    /**未知代码**/
    UNKNOW("99");
    private String code;
    
    private BankTransferBatchTranStatusEnum(String code){
        this.code = code;
    }
    
    
    public static BankTransferBatchTranStatusEnum fromValue(String value) {
        for(BankTransferBatchTranStatusEnum status:values()){
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
