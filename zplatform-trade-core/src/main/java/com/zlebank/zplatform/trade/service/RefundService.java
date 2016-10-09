/* 
 * RefundService.java  
 * 
 * version TODO
 *
 * 2016年5月17日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.gateway.RefundOrderBean;
import com.zlebank.zplatform.trade.exception.TradeException;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年5月17日 下午4:25:11
 * @since 
 */
public interface RefundService {

	/**
	 * 退款处理（退款审核通过后）
	 * @param refundOrderNo
	 * @param merchNo
	 * @return
	 */
	public ResultBean execute(String refundOrderNo,String merchNo);
	
	/**
	 * 一般消费（账户/快捷）退款申请
	 * @param orderBean
	 * @return
	 */
	public String commonRefund(RefundOrderBean orderBean) throws TradeException;
	
	/**
	 * 产品消费退款申请
	 * @param orderBean
	 * @return
	 */
	public String productRefund(RefundOrderBean orderBean) throws TradeException;
	
	/**
	 * 行业专户退款申请
	 * @param orderBean
	 * @return
	 */
	public String industryRefund(RefundOrderBean orderBean) throws TradeException;
}
