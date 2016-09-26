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
 * 转账的业务类型
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年3月7日 下午5:36:59
 * @since 
 */
public enum TransferBusiTypeEnum {
    /**代付**/
    INSTEAD("00"),
    /**提现**/
    WITHDRAW("01"),
    /**退款**/
    REFUND("02"),
    /**未知代码**/
    UNKNOW("9");
    private String code;
    
    private TransferBusiTypeEnum(String code){
        this.code = code;
    }
    
    
    public static TransferBusiTypeEnum fromValue(String value) {
        for(TransferBusiTypeEnum status:values()){
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
