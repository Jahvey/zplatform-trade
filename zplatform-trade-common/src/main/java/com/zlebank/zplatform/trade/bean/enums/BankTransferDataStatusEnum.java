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
 * 转账流水状态（"状态（01：未审核 02：等待转账 
03：正在转账 00：转账成功 09：转账失败）"
）
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年3月7日 下午5:30:24
 * @since 
 */
public enum BankTransferDataStatusEnum {
    /**未审核**/
    INIT("01"),
    /**等待转账 **/
    WAIT_TRAN("02"),
    /**正在转账**/
    TRAN_ING("03"),
    /**转账完成**/
    TRAN_FINISHED("00"),
    /**转账失败**/
    TRAN_FAILED("09"),
    /**未知代码**/
    UNKNOW("99");
    private String code;
    
    private BankTransferDataStatusEnum(String code){
        this.code = code;
    }
    
    
    public static BankTransferDataStatusEnum fromValue(String value) {
        for(BankTransferDataStatusEnum status:values()){
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
