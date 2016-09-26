/* 
 * Bank.java  
 * 
 * version TODO
 *
 * 2015年10月9日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.message;

/**
 * 银行报文
 *
 * @author yangpeng
 * @version
 * @date 2015年10月9日 下午2:16:05
 * @since 
 */
public class Bank {
    /**所属银行编号**/
    private String bankCode;
    /**所属银行名称**/
    private String bankName;
    /**银行卡类型**/
    private String cardType;
    
    public String getBankCode() {
        return bankCode;
    }
    public String getBankName() {
        return bankName;
    }
    public String getCardType() {
        return cardType;
    }
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
    
    

}
