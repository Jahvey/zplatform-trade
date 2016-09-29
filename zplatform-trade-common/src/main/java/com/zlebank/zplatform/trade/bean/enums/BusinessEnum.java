/* 
 * BusinessEnum.java  
 * 
 * version TODO
 *
 * 2015年11月27日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean.enums;


/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月27日 上午11:45:57
 * @since 
 */
public enum BusinessEnum {
    CONSUMEQUICK("10000001"),//消费-快捷
    CONSUMEACCOUNT("10000002"),//消费-账户
    CONSUMESPLIT("10000004"),//消费-分账
    CREDIT_CONSUME("10000006"),//消费-授信
    
    RECHARGE("20000001"),//充值
    BAIL_RECHARGE("20000002"),//保证金线上充值
    BAIL_RECHARGE_OFFLINE("20000003"),//保证金线下充值
    CREDIT_RECHARGE("20000004"),//授信账户充值
    
    WITHDRAWALS("30000001"),//提现
    REFUND_BANK("40000001"),//退款-银行卡
    INSTEADPAY("70000001"),//代付
    INSTEADPAY_REFUND("70000001"),//代付退汇
    WITHDRAWALS_REFUND("30000003"),//提现退汇
    REFUND_REFUND("40000003"),//退款退汇
    CREDIT_REFUND("40000004"),//授信账户退款
    
    INSTEADPAY_SUCCESS("70000001"),//代付退汇
    WITHDRAWALS_SUCCESS("30000001"),//提现退汇
    REFUND_ACCOUNT("40000002"),//退款-账户
    CHARGE_OFFLINE("90000001"),//手工充值
    
    CONSUMEQUICK_PRODUCT("11000001"),//产品-消费-快捷
    CONSUMEACCOUNT_PRODUCT("11000002"),//产品-消费-账户
    
    TRANSFER("50000001"),//转账
    BAIL_WITHDRAWALS("50000002"),//保证金线提取
    
    
    CONSUME_INDUSTRY("10000005"),//消费行业
    CHARGE_INDUSTRY("20000003"),//充值-行业
    TRANSFER_INDUSTRY("50000003"),//转账-行业
    EXTRACT_INDUSTRY("50000004"),//提取-行业
    REFUND_INDUSTRY("40000003"),//退款-行业
    UNKNOW("");//未知
    private String busiCode;
    
    private BusinessEnum(String busiCode){
        this.busiCode = busiCode;
    }
    
    public static BusinessEnum fromValue(String value) {
        for(BusinessEnum busi:values()){
            if(busi.busiCode.equals(value)){
                return busi;
            }
        }
        return UNKNOW;
    }
    
    public String getBusiCode(){
        return busiCode;
    }
}
