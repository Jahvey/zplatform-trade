package com.zlebank.zplatform.trade.cmbc.bean.enmus;

public enum TransferTypeEnmu {
    /**民生本行**/
    INNERBANK("01"),
    /**其他银行**/
    OUTERBANK("02"),
    /**未知状态**/
    UNKNOW("99");

 private String code;
    
    private TransferTypeEnmu(String code){
        this.code = code;
    }
    
    public static TransferTypeEnmu fromValue(String value) {
        for(TransferTypeEnmu status:values()){
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
