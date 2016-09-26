/* 
 * FileTypeEnmu.java  
 * 
 * version TODO
 *
 * 2015年11月10日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.cmbc.bean.enmus;


/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月10日 下午12:18:34
 * @since 
 */
public enum FileTypeEnmu {
    /**本行回盘文件**/
    RES("00"),
    /**跨行回盘文件**/
    RESOUTER("01"),
    /**退汇文件**/
    REEXCHANGE("02"),
    /**未知状态**/
    UNKNOW("99");

 private String code;
    
    private FileTypeEnmu(String code){
        this.code = code;
    }
    
    public static FileTypeEnmu fromValue(String value) {
        for(FileTypeEnmu status:values()){
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
