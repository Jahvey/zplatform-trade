/* 
 * TradeTypeEnum.java  
 * 
 * version TODO
 *
 * 2015年11月17日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean.enums;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月17日 上午10:58:54
 * @since 
 */
public enum TradeTypeEnum {
    SENDSMS(0),
    SUBMITPAY(1),
    QUERYTRADE(2),
    BANKSIGN(3),
    ACCOUNTING(4),
    UNKNOW(99);
   private int tradeType;
    
    private TradeTypeEnum(int tradeType){
        this.tradeType = tradeType;
    }
    
    public static TradeTypeEnum fromValue(int value) {
        for(TradeTypeEnum status:values()){
            if(status.tradeType==value){
                return status;
            }
        }
        return UNKNOW;
    }
    
    public int getTradeType(){
        return tradeType;
    }
}
