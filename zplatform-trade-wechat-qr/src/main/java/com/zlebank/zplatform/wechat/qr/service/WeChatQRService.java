/* 
 * WeChatService.java  
 * 
 * version TODO
 *
 * 2016年5月25日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.wechat.qr.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.wechat.qr.wx.bean.PayResultBean;
import com.zlebank.zplatform.wechat.qr.wx.bean.QueryBillBean;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年5月25日 下午1:56:18
 * @since 
 */
public interface WeChatQRService {

	/**
	 * 创建微信支付订单
	 * @param tn
	 * @return
	 */
	public JSONObject creatOrder(String tn) throws TradeException;
	
	/**
	 * 处理微信订单异步通知，更新交易结果
	 * @param result
	 */
	public void asyncTradeResult(PayResultBean result);
	
	/**
	 * 下载微信对账单
	 * @param queryBillBean
	 * @return
	 */
	public List<String[]> dowanWeChatBill(QueryBillBean queryBillBean );
	
	/***
	 * 退款查询定时任务跑批
	 */
	public void dealRefundBatch();
	/**
	 *订单查询跑批
	 *将已支付，
	 *但没收到异步通知的订单进行主动请求 
	 */
	public void dealAnsyOrder();
	
	/***
	 * 查询微信订单
	 * @param map
	 * @return
	 */
	public ResultBean queryWechatOrder(TradeBean trade);
	
	/**
	 * 查询微信交易订单和退款订单结果
	 * @param txnseqno
	 * @return
	 */
	public ResultBean queryOrder(String txnseqno);
	
	/**
	 * 处理交易查询后账务数据
	 * @param txnseqno
	 * @param resultBean
	 * @return
	 */
	public ResultBean dealWithAccounting(String txnseqno,ResultBean resultBean);
}
