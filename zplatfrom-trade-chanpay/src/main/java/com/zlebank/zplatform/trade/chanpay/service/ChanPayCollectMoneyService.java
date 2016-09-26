/* 
 * ChanPayCollectMoneyService.java  
 * 
 * version TODO
 *
 * 2016年6月29日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.chanpay.service;

import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.chanpay.bean.cj.G60001Bean;

/**
 * 畅捷代收接口
 *
 * @author guojia
 * @version
 * @date 2016年6月29日 下午3:17:42
 * @since 
 */
public interface ChanPayCollectMoneyService {

	/**
	 * 实名认证
	 * @param tradeBean
	 * @return
	 */
	public ResultBean realNameAuth(TradeBean tradeBean);
	
	/**
	 * 查询实名认证结果
	 * @param qry_req_sn
	 * @return
	 */
	public ResultBean queryRealNameAuth(String qry_req_sn);
	
	/**
	 * 协议签约
	 * @param tradeBean
	 * @return
	 */
	public ResultBean protocolSign(TradeBean tradeBean);
	
	/**
	 * 查询协议签约结果
	 * @param qry_req_sn
	 * @return
	 */
	public ResultBean queryProtocolSign(String qry_req_sn);
	
	/**
	 * 实时代收
	 * @param tradeBean
	 * @return
	 */
	public ResultBean collectMoney(TradeBean tradeBean);
	
	/**
	 * 查询代收交易结果
	 * @param qry_req_sn
	 * @return
	 */
	public ResultBean queryCollectMoney(String qry_req_sn);
}
