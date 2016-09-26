/* 
 * TxnTypeEnum.java  
 * 
 * version v1.4
 *
 * 2016年1月19日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.wechat.qr.enums;


/**
 * 账单类型
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年1月19日 下午2:19:20
 * @since 
 */
public enum BillTypeEnum {
    /** ALL **/
    ALL(24),
    /** SUCCESS **/
    SUCCESS(18),
    /** REFUND **/
    REFUND(24),
    UNKNOW(99);//未知

    private int billType;
    
    private BillTypeEnum(int billType){
        this.billType = billType;
    }
    
    public static BillTypeEnum fromValue(int value) {
        for(BillTypeEnum busi:values()){
            if(busi.billType==value){
                return busi;
            }
        }
        return UNKNOW;
    }
    public int getCode(){
        return billType;
    }
}
