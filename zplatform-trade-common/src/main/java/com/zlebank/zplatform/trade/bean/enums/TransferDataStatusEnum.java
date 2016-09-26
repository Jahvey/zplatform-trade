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
public enum TransferDataStatusEnum {
    /**未审核**/
    INIT("01"),
    /**审核通过 **/
    APPROVED("00"),
    /**审核拒绝**/
    FAILED("09"),
    /**未知代码**/
    UNKNOW("99");
    private String code;
    
    private TransferDataStatusEnum(String code){
        this.code = code;
    }
    
    
    public static TransferDataStatusEnum fromValue(String value) {
        for(TransferDataStatusEnum status:values()){
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
