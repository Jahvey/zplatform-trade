/* 
 * ChannelCondition.java  
 * 
 * version v1.3
 *
 * 2016年3月7日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.batch.spliter;

/**
 * 渠道条件（用于渠道生成器）
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年3月7日 上午10:29:14
 * @since 
 */
public class ChannelCondition {
    /** 银行代码 **/
    private String bankCode;
    /** 01：行内，02：行外 */
    private String bankType;

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }
    
}
