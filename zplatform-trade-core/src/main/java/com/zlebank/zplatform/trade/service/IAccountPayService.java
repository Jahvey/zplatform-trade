/* 
 * IAccountPayService.java  
 * 
 * version TODO
 *
 * 2015年10月10日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import com.zlebank.zplatform.trade.bean.AccountTradeBean;
import com.zlebank.zplatform.trade.exception.TradeException;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年10月10日 上午10:38:52
 * @since 
 */
public interface IAccountPayService{
    public void accountPay(AccountTradeBean accountTrade) throws TradeException;
    public String encryptPWD(String merchId,String pwd) throws TradeException;
    public void mobileAccountPay(AccountTradeBean accountTrade) throws TradeException;
}
