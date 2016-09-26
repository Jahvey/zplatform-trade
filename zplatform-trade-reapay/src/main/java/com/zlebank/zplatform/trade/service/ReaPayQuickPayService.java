/* 
 * ReaPayQuickPayService.java  
 * 
 * version TODO
 *
 * 2016年7月25日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年7月25日 下午5:37:02
 * @since 
 */
public interface ReaPayQuickPayService {

	/**
	 * 发送短信验证码
	 * @param trade
	 * @return
	 */
	public ResultBean sendSms(TradeBean trade);
	/**
	 * 银行卡签约
	 * @param trade
	 * @return
	 */
	public ResultBean bankCardSign(TradeBean trade);
	
	/**
	 * 确认支付
	 * @param trade
	 * @return
	 */
	public ResultBean submitPay(TradeBean trade);
	
	/**
	 * 交易查询
	 * @param trade
	 * @return
	 */
	public ResultBean queryTrade(TradeBean trade);
	
	/**
	 * 账务处理
	 * @param trade
	 * @return
	 */
	public ResultBean dealWithAccounting(String txnseqno,ResultBean resultBean);
}
