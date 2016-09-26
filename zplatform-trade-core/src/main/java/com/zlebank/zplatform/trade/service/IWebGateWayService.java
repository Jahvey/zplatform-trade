/* 
 * IWebGateService.java  
 * 
 * version TODO
 *
 * 2015年11月17日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import java.util.Map;

import com.zlebank.zplatform.acc.exception.AbstractBusiAcctException;
import com.zlebank.zplatform.acc.exception.AccBussinessException;
import com.zlebank.zplatform.acc.exception.IllegalEntryRequestException;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.model.TxnsOrderinfoModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月17日 下午2:04:14
 * @since 
 */
public interface IWebGateWayService extends IBaseService<TxnsOrderinfoModel, Long>{
	/**
	 * 确认支付
	 * @param tradeBean
	 * @throws TradeException
	 */
    public void submitPay(TradeBean tradeBean) throws TradeException;
    /**
     * 绑定支付
     * @param trade
     * @throws TradeException
     */
    public void bindPay(TradeBean trade)throws TradeException;
    /**
     * 银行卡签约
     * @param trade
     * @throws TradeException
     */
    public void bankCardSign(TradeBean trade) throws TradeException;
    /**
     * 初始化会员支付密码
     * @param memberId
     * @param pwd
     * @throws TradeException
     */
    public void initMemeberPwd(String memberId,String pwd) throws TradeException;
    /**
     * 账户支付
     * @param tradeBean
     * @return
     * @throws TradeException
     * @throws Exception
     */
    public String accountPay(TradeBean tradeBean) throws TradeException,Exception;
    /**
     * 提现
     * @param tradeBean
     * @return
     */
    public Map<String, Object> Withdraw(TradeBean tradeBean) throws AccBussinessException, IllegalEntryRequestException, AbstractBusiAcctException, NumberFormatException,TradeException;
    
}
