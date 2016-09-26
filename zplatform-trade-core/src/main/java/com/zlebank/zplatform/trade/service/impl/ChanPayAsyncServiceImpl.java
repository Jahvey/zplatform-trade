/* 
 * ChanPayAsyncServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年5月10日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.commons.dao.pojo.BusiTypeEnum;
import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.trade.bean.AppPartyBean;
import com.zlebank.zplatform.trade.bean.PayPartyBean;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.chanpay.bean.async.RefundAsyncResultBean;
import com.zlebank.zplatform.trade.chanpay.bean.async.TradeAsyncResultBean;
import com.zlebank.zplatform.trade.chanpay.enums.RefundStatusEnum;
import com.zlebank.zplatform.trade.chanpay.enums.TradeStatusEnum;
import com.zlebank.zplatform.trade.chanpay.service.ChanPayService;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.factory.AccountingAdapterFactory;
import com.zlebank.zplatform.trade.model.TxnsGatewaypayModel;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsOrderinfoModel;
import com.zlebank.zplatform.trade.service.ChanPayAsyncService;
import com.zlebank.zplatform.trade.service.IGateWayService;
import com.zlebank.zplatform.trade.service.ITxnsGatewaypayService;
import com.zlebank.zplatform.trade.service.ITxnsLogService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年5月10日 下午4:47:02
 * @since
 */
@Service("chanPayAsyncService")
public class ChanPayAsyncServiceImpl implements ChanPayAsyncService {

	private static final Log log = LogFactory
			.getLog(ChanPayAsyncServiceImpl.class);
	@Autowired
	private ChanPayService chanPayService;
	@Autowired
	private ITxnsGatewaypayService gatewaypayService;
	@Autowired
	private ITxnsLogService txnsLogService;
	@Autowired
	private IGateWayService gateWayService;

	/**
	 *
	 * @param tradeAsyncResultBean
	 * @return
	 * @throws TradeException
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public ResultBean dealWithTradeAsync(
			TradeAsyncResultBean tradeAsyncResultBean) throws TradeException {
		ResultBean resultBean = null;
		// 验签
		// 更新数据 网关流水
		boolean flag = chanPayService.asyncNotifyTrade(tradeAsyncResultBean);
		log.info("verfy data result:" + flag);
		if (!flag) {
			throw new TradeException("T000","验签失败");// 验签失败
		}
		try {
			String out_trade_no = tradeAsyncResultBean.getOuter_trade_no();
			TradeStatusEnum tradeStatusEnum = TradeStatusEnum.fromValue(tradeAsyncResultBean.getTrade_status());
			if(tradeStatusEnum == TradeStatusEnum.TRADE_SUCCESS){//交易成功
				//交易网关流水数据
				TxnsGatewaypayModel gatewayOrder = gatewaypayService.getOrderByOrderNo(out_trade_no);
				String txnseqno = gatewayOrder.getRelatetradetxn();
				//交易流水数据
				TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
				if(StringUtil.isNotEmpty(txnsLog.getApporderstatus())&&StringUtil.isNotEmpty(txnsLog.getApporderinfo())){//交易账务信息都不为空，说明账务已完成
					return new ResultBean("success");
				}
				
				//交易订单数据
				TxnsOrderinfoModel orderinfo = gateWayService.getOrderByTxnseqno(txnseqno);
				gatewayOrder.setClosetime(tradeAsyncResultBean.getGmt_close());
				gatewayOrder.setPayfinshtime(tradeAsyncResultBean.getGmt_payment());
				gatewayOrder
						.setPayrettxnseqno(tradeAsyncResultBean.getInner_trade_no());
				gatewayOrder.setPayretcode(tradeStatusEnum.getCode());
				gatewayOrder.setPayretinfo(tradeStatusEnum.getMesssage());
				if (tradeStatusEnum == TradeStatusEnum.PAY_FINISHED
						|| tradeStatusEnum == TradeStatusEnum.TRADE_FINISHED
						|| tradeStatusEnum == TradeStatusEnum.TRADE_SUCCESS) {
					gatewayOrder.setStatus("00");// 交易成功
					orderinfo.setStatus("00");
				} else {
					gatewayOrder.setStatus("03");
					orderinfo.setStatus("03");
				}
				gatewaypayService.update(gatewayOrder);
				// 更新数据 交易流水


				PayPartyBean payPartyBean = new PayPartyBean();
				payPartyBean.setPayordfintime(tradeAsyncResultBean.getGmt_payment());
				payPartyBean
						.setPayrettsnseqno(tradeAsyncResultBean.getInner_trade_no());
				payPartyBean.setPayretcode(tradeStatusEnum.getCode());
				payPartyBean.setPayretinfo(tradeStatusEnum.getMesssage());
				payPartyBean.setTxnseqno(txnseqno);
				txnsLogService.updateGateWayPayResult(payPartyBean);
				// 更新数据 交易订单
				orderinfo.setOrderfinshtime(DateUtil.getCurrentDateTime());
				gatewaypayService.update(gatewayOrder);
				gateWayService.update(orderinfo);
				//更新应用方信息
				AppPartyBean appParty = new AppPartyBean("","000000000000", DateUtil.getCurrentDateTime(),DateUtil.getCurrentDateTime(), txnseqno, "");
				txnsLogService.updateAppInfo(appParty);
				// 处理账务
				AccountingAdapterFactory.getInstance().getAccounting(BusiTypeEnum.fromValue(txnsLog.getBusitype())).accountedFor(txnseqno);
				resultBean = new ResultBean("success");
			}else if(tradeStatusEnum == TradeStatusEnum.TRADE_FINISHED){
				resultBean = new ResultBean("success");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultBean = new ResultBean("T000", e.getMessage());
		}
		log.info("chanpay notify result:"+JSON.toJSONString(resultBean));
		return resultBean;
	}

	/**
	 *
	 * @param refundAsyncResultBean
	 * @return
	 * @throws TradeException
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public ResultBean dealWithRefundAsync(
			RefundAsyncResultBean refundAsyncResultBean) throws TradeException {
		// 验签
		boolean flag = chanPayService.asyncNotifyRefund(refundAsyncResultBean);
		log.info("verfy data result:" + flag);
		if (!flag) {
			throw new TradeException("T000","异步通知信息验签失败");// 验签失败
		}
		// 更新数据 网关流水
		String out_trade_no = refundAsyncResultBean.getOuter_trade_no();
		RefundStatusEnum tradeStatusEnum = RefundStatusEnum
				.fromValue(refundAsyncResultBean.getRefund_status());
		TxnsGatewaypayModel gatewayOrder = gatewaypayService
				.getOrderByOrderNo(out_trade_no);
		String txnseqno = gatewayOrder.getRelatetradetxn();
		TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
		TxnsOrderinfoModel orderinfo = gateWayService
				.getOrderinfoByOrderNoAndMemberId(txnsLog.getAccordno(),
						txnsLog.getAccsecmerno());
		gatewayOrder.setPayfinshtime(DateUtil.getCurrentDateTime());
		gatewayOrder.setPayorderno(refundAsyncResultBean.getInner_trade_no());
		gatewayOrder.setPayretcode(tradeStatusEnum.getCode());
		gatewayOrder.setPayretinfo(tradeStatusEnum.getMesssage());
		if (tradeStatusEnum == RefundStatusEnum.REFUND_SUCCESS) {
			gatewayOrder.setStatus("00");// 交易成功
			orderinfo.setStatus("00");
		} else {
			gatewayOrder.setStatus("03");
			orderinfo.setStatus("03");
		}
		gatewaypayService.update(gatewayOrder);
		// 更新数据 交易流水
		PayPartyBean payPartyBean = new PayPartyBean();
		payPartyBean.setPayordfintime(DateUtil.getCurrentDateTime());
		payPartyBean.setPayrettsnseqno(refundAsyncResultBean
				.getInner_trade_no());
		payPartyBean.setPayretcode(tradeStatusEnum.getCode());
		payPartyBean.setPayretinfo(tradeStatusEnum.getMesssage());
		txnsLogService.updateGateWayPayResult(payPartyBean);
		// 更新数据 交易订单
		orderinfo.setOrderfinshtime(DateUtil.getCurrentDateTime());
		gatewaypayService.update(gatewayOrder);
		// 处理账务
		AccountingAdapterFactory.getInstance()
				.getAccounting(BusiTypeEnum.fromValue(txnsLog.getBusicode()))
				.accountedFor(txnseqno);
		return null;
	}

}
