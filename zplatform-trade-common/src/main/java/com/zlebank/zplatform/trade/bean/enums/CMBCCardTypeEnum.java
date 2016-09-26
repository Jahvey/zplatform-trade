package com.zlebank.zplatform.trade.bean.enums;

public enum CMBCCardTypeEnum {

    debitCard("0","1"),
    passbook("1",""),
    debitCARD("2","2"),
    companyAccount("3",""),
    UNKNOW("","");
    private String code;
    private String cardType;
    private CMBCCardTypeEnum(String code,String cardType){
        this.code = code;
        this.cardType = cardType;
    }
    
    public static CMBCCardTypeEnum fromValue(String value) {
        for(CMBCCardTypeEnum status:values()){
            if(status.code.equals(value)){
                return status;
            }
        }
        return UNKNOW;
    }
    public static CMBCCardTypeEnum fromCardType(String value){
        for(CMBCCardTypeEnum status:values()){
            if(status.cardType.equals(value)){
                return status;
            }
        }
        return UNKNOW;
    }
    
    
    public String getCode(){
        return code;
    }
    public String getCardType(){
        return cardType;
    }
}
