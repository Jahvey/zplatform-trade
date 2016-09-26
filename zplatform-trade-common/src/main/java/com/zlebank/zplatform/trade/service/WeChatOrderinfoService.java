/* 
 * WeChatOrderinfoService.java  
 * 
 * version TODO
 *
 * 2016年8月8日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import com.zlebank.zplatform.trade.model.PojoTxnsWechatOrderinfo;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年8月8日 下午5:11:44
 * @since 
 */
public interface WeChatOrderinfoService extends IBaseService<PojoTxnsWechatOrderinfo,Long>{

	/**
	 * 保存微信订单
	 * @param wechatOrderinfo
	 */
	public void saveWeChatOrder(PojoTxnsWechatOrderinfo wechatOrderinfo);
	
	/**
	 * 通过微信的商户订单号获取微信订单信息
	 * @param out_trade_no
	 * @return
	 */
	public PojoTxnsWechatOrderinfo getWechatOrderinfo(String out_trade_no);
	
	/**
	 * 更新订单为失效状态
	 * @param out_trade_no
	 */
	public void updateOrderToOverdue(String out_trade_no);
	
	/**
	 * 更新订单为成功状态
	 * @param out_trade_no
	 */
	public void updateOrderToSuccess(String out_trade_no);
	
	/**
	 * 更新订单为失败状态
	 * @param out_trade_no
	 */
	public void updateOrderToFailure(String out_trade_no);
}
