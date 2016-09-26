/* 
 * SeqNoEnum.java  
 * 
 * version TODO
 *
 * 2016年3月8日 
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
 * @date 2016年3月8日 下午5:30:45
 * @since 
 */
public enum SeqNoEnum {
    /**代付批次号**/
    INSTEAD_PAY_BATCH_NO("01"),
    /**提现批次号**/
    WITHDRAW_BATCH_NO("02"),
    /**退款批次号**/
    REFUND_BATCH_NO("03"),
    /**划拨批次号**/
    TRAN_BATCH_NO("04"),
    /**转账批次号**/
    BANK_TRAN_BATCH_NO("05"),
    /**代付流水号**/
    INSTEAD_PAY_DATA_NO("11"),
    /**提现流水号**/
    WITHDRAW_DATA_NO("12"),
    /**退款流水号**/
    REFUND_DATA_NO("13"),
    /**划拨流水号**/
    TRAN_DATA_NO("14"),
    /**转账流水号**/
    BANK_TRAN_DATA_NO("15"),
    /**未知代码**/
    UNKNOW("99");
    private String code;
    
    private SeqNoEnum(String code){
        this.code = code;
    }
    
    
    public static SeqNoEnum fromValue(String value) {
        for(SeqNoEnum status:values()){
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
