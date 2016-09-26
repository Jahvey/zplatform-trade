/* 
 * TradeCompleteProcessingServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年7月20日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.timeout.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.commons.dao.pojo.BusiTypeEnum;
import com.zlebank.zplatform.timeout.service.TradeCompleteProcessingService;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.enums.TradeStatFlagEnum;
import com.zlebank.zplatform.trade.chanpay.bean.cj.G10001Bean;
import com.zlebank.zplatform.trade.chanpay.bean.cj.G20001Bean;
import com.zlebank.zplatform.trade.chanpay.service.ChanPayQuickPayService;
import com.zlebank.zplatform.trade.cmbc.service.CMBCCrossLineQuickPayService;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsWithholdingModel;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.ReaPayQuickPayService;
import com.zlebank.zplatform.wechat.qr.service.WeChatQRService;
import com.zlebank.zplatform.wechat.service.WeChatService;
import com.zlebank.zplatform.wechat.wx.bean.QueryOrderResultBean;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年7月20日 上午11:43:58
 * @since 
 */
@Service("tradeCompleteProcessingService")
public class TradeCompleteProcessingServiceImpl implements TradeCompleteProcessingService{

	private static final Log log = LogFactory.getLog(TradeCompleteProcessingServiceImpl.class);
	@Autowired
	private ITxnsLogService txnsLogService;
	@Autowired
	private ChanPayQuickPayService chanPayQuickPayService;
	@Autowired
	private CMBCCrossLineQuickPayService cmbcCrossLineQuickPayService;
	@Autowired
	private WeChatService weChatService;
	@Autowired
	private ReaPayQuickPayService reaPayQuickPayService;
	@Autowired
	private WeChatQRService weChatQRService;
	
	/**
	 *
	 * @param txnseqno
	 * @param resultBean
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void chanPayCollectMoneyCompleteTrade(String txnseqno,ResultBean resultBean) {
		TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
		TradeStatFlagEnum tradeStatFlagEnum = TradeStatFlagEnum.fromValue(txnsLog.getTradestatflag());
		if(tradeStatFlagEnum==TradeStatFlagEnum.PAYING||tradeStatFlagEnum==TradeStatFlagEnum.OVERTIME){//交易支付中或者交易超时
			G20001Bean data = (G20001Bean) resultBean.getResultObj();
			log.info("接收畅捷应答数据：" + JSON.toJSONString(data));
			txnsLogService.updatePayInfo_Fast_result(txnseqno,data.getRetCode(), data.getErrMsg());
			if (!"0000".equals(data.getRetCode())) {// 交易失败
				txnsLogService.updateCoreRetResult(txnseqno, "3699",data.getErrMsg());
			} else {//交易成功
				G10001Bean bean = new G10001Bean();
				bean.setRetCode(data.getRetCode());
				bean.setErrMsg(data.getErrMsg());
				resultBean.setResultObj(bean);
				chanPayQuickPayService.dealWithAccounting(resultBean, txnseqno);
			}
		}
	}
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void cmbcCrossLineCompleteTrade(String txnseqno,ResultBean resultBean) {
		TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
		TradeStatFlagEnum tradeStatFlagEnum = TradeStatFlagEnum.fromValue(txnsLog.getTradestatflag());
		if(tradeStatFlagEnum==TradeStatFlagEnum.PAYING||tradeStatFlagEnum==TradeStatFlagEnum.OVERTIME){//交易支付中或者交易超时
			TxnsWithholdingModel withholding = (TxnsWithholdingModel) resultBean.getResultObj();
			log.info("接收民生交易查询流水数据：" + JSON.toJSONString(withholding));
			txnsLogService.updateCMBCWithholdingRetInfo(txnseqno,withholding);
			if ("000000".equals(withholding.getOriexeccode())) {// 交易成功
				cmbcCrossLineQuickPayService.dealWithAccounting(txnseqno, resultBean);
			}
		}
	}
	/**
	 *
	 * @param txnseqno
	 * @param resultBean
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void weChatCompleteTrade(String txnseqno, ResultBean resultBean) {
		// TODO Auto-generated method stub
		TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
		TradeStatFlagEnum tradeStatFlagEnum = TradeStatFlagEnum.fromValue(txnsLog.getTradestatflag());
		if(tradeStatFlagEnum==TradeStatFlagEnum.PAYING||tradeStatFlagEnum==TradeStatFlagEnum.OVERTIME){//交易支付中或者交易超时
			log.info("接收微信交易查询应答数据：" + JSON.toJSONString(resultBean.getResultObj()));
			weChatService.dealWithAccounting(txnseqno, resultBean);
			
		}
	}
	/**
	 *
	 * @param txnseqno
	 * @param resultBean
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void reaPayCompleteTrade(String txnseqno,ResultBean resultBean) {
		TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
		TradeStatFlagEnum tradeStatFlagEnum = TradeStatFlagEnum.fromValue(txnsLog.getTradestatflag());
		if(tradeStatFlagEnum==TradeStatFlagEnum.PAYING||tradeStatFlagEnum==TradeStatFlagEnum.OVERTIME){//交易支付中或者交易超时
			log.info("接收融宝交易查询流水数据：" + JSON.toJSONString(resultBean.getResultObj()));
			reaPayQuickPayService.dealWithAccounting(txnseqno, resultBean);
		}
	}
	/**
	 *
	 * @param txnseqno
	 * @param resultBean
	 */
	@Override
	public void weChatQRCompleteTrade(String txnseqno, ResultBean resultBean) {
		TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
		TradeStatFlagEnum tradeStatFlagEnum = TradeStatFlagEnum.fromValue(txnsLog.getTradestatflag());
		if(tradeStatFlagEnum==TradeStatFlagEnum.PAYING||tradeStatFlagEnum==TradeStatFlagEnum.OVERTIME){//交易支付中或者交易超时
			log.info("接收微信交易查询应答数据：" + JSON.toJSONString(resultBean.getResultObj()));
			weChatQRService.dealWithAccounting(txnseqno, resultBean);
			
		}
		
	}

}
