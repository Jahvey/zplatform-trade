package com.zlebank.zplatform.trade.bean.enums;

public enum InsteadPayTypeEnum {
    INITI("01"),
    WaritPaying("02"),
    Paying("03"),
    finish("00"),
    fail("04"),
    UNKNOW("99");
   private String code;
    
    private InsteadPayTypeEnum(String code){
        this.code = code;
    }
    
    public static InsteadPayTypeEnum fromValue(String value) {
        for(InsteadPayTypeEnum status:values()){
            if(value.equals(status.code)){
                return status;
            }
        }
        return UNKNOW;
    }
    
    public String getCode(){
        return code;
    }
}
