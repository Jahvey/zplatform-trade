package com.zlebank.zplatform.trade.bean.enums;

public enum InsteadPayImportTypeEnum {
    /** API接入 */
    API("00"),
    /** 文件导入 */
    FILE("01"),
    UNKNOW("99");
   private String code;
    
    private InsteadPayImportTypeEnum(String code){
        this.code = code;
    }
    
    public static InsteadPayImportTypeEnum fromValue(String value) {
        for(InsteadPayImportTypeEnum status:values()){
            if(status.code==value){
                return status;
            }
        }
        return UNKNOW;
    }
    
    public String getCode(){
        return code;
    }
}
