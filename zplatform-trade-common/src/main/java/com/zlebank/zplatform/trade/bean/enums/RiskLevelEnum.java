/* 
 * RiskLevelEnum.java  
 * 
 * version TODO
 *
 * 2015年11月11日 
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
 * @date 2015年11月11日 下午2:12:02
 * @since 
 */
public enum RiskLevelEnum {
    PASS(0),
    PROMPT(1),
    ATTENTION(2),
    EARLYWARNING(3),
    WARNING(4),
    REFUSE(5),
    UNKNOW(99);
   private int riskLevel;
    
    private RiskLevelEnum(int riskLevel){
        this.riskLevel = riskLevel;
    }
    
    public static RiskLevelEnum fromValue(int value) {
        for(RiskLevelEnum status:values()){
            if(status.riskLevel==value){
                return status;
            }
        }
        return UNKNOW;
    }
    
    public int getRiskLevel(){
        return riskLevel;
    }
}
