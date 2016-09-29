/* 
 * IndustryAccountTradeService.java  
 * 
 * version TODO
 *
 * 2016年9月28日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.gateway.ExtractOrderBean;
import com.zlebank.zplatform.trade.bean.gateway.OrderBean;
import com.zlebank.zplatform.trade.bean.gateway.RefundOrderBean;
import com.zlebank.zplatform.trade.bean.gateway.TransferOrderBean;
import com.zlebank.zplatform.trade.exception.TradeException;

/**
 * 行业账户交易处理类，处理行业转账，行业提取，行业退款三个业务
 *
 * @author guojia
 * @version
 * @date 2016年9月28日 上午10:41:44
 * @since 
 */
public interface IndustryAccountTradeService {

	/**
	 * 行业专户转账
	 * @param industryCode 行业代码
	 * @param orderBean 转账订单信息
	 * @return ResultBean 返回交易结果（错误信息）和受理订单号
	 * @throws TradeException
	 */
	public ResultBean transferIndustry(final String industryCode,TransferOrderBean orderBean) throws TradeException; 
	
	/**
	 * 行业专户提取
	 * @param industryCode 行业代码
	 * @param orderBean 提取订单信息
	 * @return ResultBean 返回交易结果（错误信息）和受理订单号
	 * @throws TradeException
	 */
	public ResultBean extractIndustry(final String industryCode,ExtractOrderBean orderBean) throws TradeException;
	
	/**
	 * 行业专户退款-退款订单的创建和初审申请
	 * @param industryCode 行业代码
	 * @param orderBean 退款订单信息
	 * @return tn 受理订单号
	 * @throws TradeException
	 */
	public String refundIndustry(final String industryCode,RefundOrderBean orderBean) throws TradeException;
}
