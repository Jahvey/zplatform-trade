package com.zlebank.zplatform.trade.bean.enums;

public enum CurrencyEnum {
	RMB("156"),//人民币
   
    UNKNOW("");//未知
    private String code;
    
    private CurrencyEnum(String code){
        this.code = code;
    }
    
    public static CurrencyEnum fromValue(String value) {
        for(CurrencyEnum enumCry:values()){
            if(enumCry.getCode().equals(value)){
                return enumCry;
            }
        }
        return UNKNOW;
    }
    
    public String getCode(){
        return code;
    }
}
