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
 * 转账流水状态   状态（01：未审核 02：部分审核通过 03：全部审核通过 00：全部转账成功）"
status:
INIT
PART_APPROVED
ALL_APPROVED
ALL_FINISHED
）
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年3月7日 下午5:30:24
 * @since 
 */
public enum TransferBatchStatusEnum {
    /**未审核**/
    INIT("01"),
    /**部分审核通过 **/
    PART_APPROVED("02"),
    /**全部审核通过**/
    ALL_APPROVED("03"),
    /**全部转账成功**/
    ALL_FINISHED("00"),
    /**未知代码**/
    UNKNOW("99");
    private String code;
    
    private TransferBatchStatusEnum(String code){
        this.code = code;
    }
    
    
    public static TransferBatchStatusEnum fromValue(String value) {
        for(TransferBatchStatusEnum status:values()){
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
