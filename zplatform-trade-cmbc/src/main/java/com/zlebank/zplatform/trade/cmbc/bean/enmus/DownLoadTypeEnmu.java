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
public enum DownLoadTypeEnmu {
    /**成功**/
    SUCCESS("00"),
    /**下载失败**/
    FAILED("01"),
    /**FTP下载文件失败**/
    FTPFAILED("02"),
    /**未知状态**/
    UNKNOW("99");

 private String code;
    
    private DownLoadTypeEnmu(String code){
        this.code = code;
    }
    
    public static DownLoadTypeEnmu fromValue(String value) {
        for(DownLoadTypeEnmu status:values()){
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
