/* 
 * BankCardUtil.java  
 * 
 * version TODO
 *
 * 2015年11月10日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.utils;

import com.zlebank.zplatform.trade.exception.TradeException;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月10日 下午4:27:16
 * @since 
 */
public class BankCardUtil {

    /**
     * 校验银行卡卡号
     * @param cardId
     * @return
     * @throws TradeException 
     */
    public static boolean checkBankCard(String cardId) throws TradeException {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        return cardId.charAt(cardId.length() - 1) == bit;        
    }
     
    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     * @param nonCheckCodeCardId
     * @return
     * @throws TradeException 
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) throws TradeException {
        if(nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            throw new TradeException("GW26");
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if(j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;            
        }
        return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
    }
}
