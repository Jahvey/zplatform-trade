/* 
 * eserved.java  
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
 * 签约信息
 *
 * @author yangpeng
 * @version
 * @date 2015年10月9日 下午1:36:26
 * @since 
 */
public class Sign {
    /**绑定标识号**/
    private String bindId;
    /**银行卡号后4位 **/
    private String    cardNo;
    /**所属银行编号**/
    private String bankCode;
    /**所属银行名称**/
    private String bankName;
    /**银行卡类型**/
    private String cardType;
    
    /**电话**/
    private String bindPhone;
    
    
    public String getBindPhone() {
        return bindPhone;
    }
    public String getBindId() {
        return bindId;
    }
    public String getCardNo() {
        return cardNo;
    }
    public String getBankCode() {
        return bankCode;
    }
    public String getBankName() {
        return bankName;
    }
    public String getCardType() {
        return cardType;
    }
    public void setBindId(String bindId) {
        this.bindId = bindId;
    }
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
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
    
    public void setBindPhone(String bindPhone) {
        this.bindPhone = bindPhone;
    }
    
    
    
}
