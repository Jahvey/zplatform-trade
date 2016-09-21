/** 
 * ChanPayQuickPayServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年6月30日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.chanpay.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.commons.dao.pojo.BusiTypeEnum;
import com.zlebank.zplatform.member.service.MerchMKService;
import com.zlebank.zplatform.sms.service.ISMSService;
import com.zlebank.zplatform.trade.bean.AppPartyBean;
import com.zlebank.zplatform.trade.bean.PayPartyBean;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.enums.ChannelEnmu;
import com.zlebank.zplatform.trade.bean.enums.TradeStatFlagEnum;
import com.zlebank.zplatform.trade.chanpay.bean.cj.G10001Bean;
import com.zlebank.zplatform.trade.chanpay.bean.cj.G60001Bean;
import com.zlebank.zplatform.trade.chanpay.bean.cj.G60003Bean;
import com.zlebank.zplatform.trade.chanpay.service.ChanPayCollectMoneyService;
import com.zlebank.zplatform.trade.chanpay.service.ChanPayQuickPayService;
import com.zlebank.zplatform.trade.dao.ITxnsOrderinfoDAO;
import com.zlebank.zplatform.trade.dao.RealnameAuthDAO;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.factory.AccountingAdapterFactory;
import com.zlebank.zplatform.trade.model.PojoCardBind;
import com.zlebank.zplatform.trade.model.PojoRealnameAuth;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsWithholdingModel;
import com.zlebank.zplatform.trade.service.CardBindService;
import com.zlebank.zplatform.trade.service.IRouteConfigService;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.ITxnsWithholdingService;
import com.zlebank.zplatform.trade.service.TradeNotifyService;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.DateUtil;
import com.zlebank.zplatform.trade.utils.OrderNumber;
import com.zlebank.zplatform.trade.utils.UUIDUtil;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年6月30日 下午4:50:07
 * @since
 */
@Service("chanPayQuickPayService")
public class ChanPayQuickPayServiceImpl implements ChanPayQuickPayService {

	private static final Log log = LogFactory
			.getLog(ChanPayQuickPayServiceImpl.class);
	@Autowired
	private ChanPayCollectMoneyService chanPayCollectMoneyService;
	@Autowired
	private ITxnsWithholdingService txnsWithholdingService;
	@Autowired
	private CardBindService cardBindService;
	@Autowired
	private ITxnsLogService txnsLogService;
	@Autowired
	private ITxnsOrderinfoDAO txnsOrderinfoDAO;
	@Autowired
	private RealnameAuthDAO realnameAuthDAO;
	@Autowired
	private ISMSService smsService;
	@Autowired
	private IRouteConfigService routeConfigService;
	@Autowired
	private MerchMKService merchMKService;
	@Autowired
	private TradeNotifyService tradeNotifyService;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public ResultBean realNameAuth(TradeBean tradeBean) throws TradeException {
		log.info("开始畅捷实名认证,txnseqno:" + tradeBean.getTxnseqno());
		PojoRealnameAuth pojoRealnameAuth = new PojoRealnameAuth(tradeBean);
		TxnsWithholdingModel txnsWithholdingModel = new TxnsWithholdingModel(
				pojoRealnameAuth, ChannelEnmu.CHANPAYCOLLECTMONEY);
		tradeBean.setPayOrderNo(txnsWithholdingModel.getSerialno());
		txnsWithholdingService.saveWithholdingLog(txnsWithholdingModel);
		ResultBean resultBean = chanPayCollectMoneyService
				.realNameAuth(tradeBean);
		if (resultBean.isResultBool()) {
			G60001Bean data = (G60001Bean) resultBean.getResultObj();
			txnsWithholdingModel.setExeccode(data.getRetCode());
			txnsWithholdingModel.setExecmsg(data.getErrMsg());
			txnsWithholdingService.updateRealNameResult(txnsWithholdingModel);
			// 保存实名认证信息
			pojoRealnameAuth.setStatus("00");
			realnameAuthDAO.saveRealNameAuth(pojoRealnameAuth);
		} else {
			txnsWithholdingModel.setExeccode(resultBean.getErrCode());
			txnsWithholdingModel.setExecmsg(resultBean.getErrMsg());
			txnsWithholdingService.updateRealNameResult(txnsWithholdingModel);
		}
		log.info("畅捷实名认证结束,txnseqno:" + tradeBean.getTxnseqno());
		txnsLogService.updateTradeStatFlag(tradeBean.getTxnseqno(), TradeStatFlagEnum.READY);
		return resultBean;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public ResultBean protocolSign(TradeBean tradeBean) throws TradeException {
		log.info("开始畅捷协议签约,txnseqno:" + tradeBean.getTxnseqno());
		// 记录畅捷实名认证流水
		TxnsWithholdingModel txnsWithholdingModel = new TxnsWithholdingModel(
				tradeBean.getBankCode(), tradeBean.getBankName(),
				tradeBean.getCardNo(), tradeBean.getAcctName(),
				tradeBean.getCardType(), tradeBean.getCertType(),
				tradeBean.getCertId(), tradeBean.getMobile(),
				ChannelEnmu.CHANPAYCOLLECTMONEY);
		txnsWithholdingService.saveWithholdingLog(txnsWithholdingModel);
		tradeBean.setPayOrderNo(txnsWithholdingModel.getSerialno());
		// 畅捷协议签约
		ResultBean resultBean = chanPayCollectMoneyService
				.protocolSign(tradeBean);
		if (resultBean.isResultBool()) {
			G60003Bean data = (G60003Bean) resultBean.getResultObj();
			txnsWithholdingModel.setExeccode(data.getRetCode());
			txnsWithholdingModel.setExecmsg(data.getErrMsg());
			txnsWithholdingService.updateRealNameResult(txnsWithholdingModel);
			// 新增渠道银行卡表
			PojoCardBind cardBind = new PojoCardBind();
			cardBind.setBindId(data.getProtocolNo());
			cardBind.setCardId(tradeBean.getCardId());
			cardBind.setChnlCode(ChannelEnmu.CHANPAYCOLLECTMONEY.getChnlcode());
			cardBindService.save(cardBind);
		} else {
			txnsWithholdingModel.setExeccode(resultBean.getErrCode());
			txnsWithholdingModel.setExecmsg(resultBean.getErrMsg());
			txnsWithholdingService.updateRealNameResult(txnsWithholdingModel);
		}
		log.info("畅捷协议签约结束,txnseqno:" + tradeBean.getTxnseqno());
		return resultBean;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public ResultBean collectMoney(TradeBean tradeBean) throws TradeException {
		log.info("开始畅捷代收,txnseqno:" + tradeBean.getTxnseqno());
		ResultBean resultBean = null;
		// 校验短信验证码
		int retCode = smsService.verifyCode(tradeBean.getMobile(),
				tradeBean.getTn(), tradeBean.getIdentifyingCode());
		if (retCode == 2) {
			resultBean = new ResultBean("30HK", "交易失败，动态口令或短信验证码校验失败");
		} else if (retCode == 3) {
			resultBean = new ResultBean("30HK", "交易失败，动态口令或短信验证码校验失败");
		}

		// 获取渠道对应的绑卡标示
		PojoCardBind cardBind = cardBindService.getCardBind(
				tradeBean.getCardId(),
				ChannelEnmu.CHANPAYCOLLECTMONEY.getChnlcode());
		if (cardBind == null) {
			throw new TradeException("GW13");
		}
		tradeBean.setBindCardId(cardBind.getBindId());
		String bankNumber = routeConfigService.getCardPBCCode(
				tradeBean.getCardNo()).get("PBC_BANKCODE")
				+ "";
		tradeBean.setBankCode(bankNumber);
		TxnsWithholdingModel txnsWithholdingModel = new TxnsWithholdingModel(
				tradeBean, ChannelEnmu.CHANPAYCOLLECTMONEY);
		txnsWithholdingModel.setSerialno(OrderNumber.getInstance()
				.generateChanPayOrderNo());
		txnsWithholdingService.saveWithholdingLog(txnsWithholdingModel);
		tradeBean.setPayOrderNo(txnsWithholdingModel.getSerialno());

		PayPartyBean payPartyBean = new PayPartyBean(tradeBean.getTxnseqno(),
				"01", tradeBean.getPayOrderNo(),
				ChannelEnmu.CHANPAYCOLLECTMONEY.getChnlcode(),
				ConsUtil.getInstance().cons.getChanpay_cj_merchant_id(), "",
				DateUtil.getCurrentDateTime(), "", tradeBean.getCardNo());
		payPartyBean.setPanName(tradeBean.getAcctName());
		txnsLogService.updatePayInfo_Fast(payPartyBean);
		if (resultBean != null) {
			txnsLogService.updatePayInfo_Fast_result(tradeBean.getTxnseqno(),
					resultBean.getErrCode(), resultBean.getErrMsg());
			txnsLogService.updateCoreRetResult(tradeBean.getTxnseqno(),
					resultBean.getErrCode(), resultBean.getErrMsg());
			log.info(JSON.toJSONString(resultBean));
			// TxnsLogModel txnsLog =
			// txnsLogService.getTxnsLogByTxnseqno(tradeBean.getTxnseqno());
			txnsOrderinfoDAO.updateOrderToFail(tradeBean.getTxnseqno());
			return resultBean;
		}
		txnsLogService.updateTradeStatFlag(tradeBean.getTxnseqno(), TradeStatFlagEnum.PAYING);
		try {
			resultBean = chanPayCollectMoneyService.collectMoney(tradeBean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			txnsLogService.updateTradeStatFlag(tradeBean.getTxnseqno(), TradeStatFlagEnum.OVERTIME);
		}
		if(resultBean==null){
			resultBean = new ResultBean("T000", "交易超时请稍后查询");
			log.info("畅捷代收超时,txnseqno:" + tradeBean.getTxnseqno());
			return resultBean;
		}
		G10001Bean data = (G10001Bean) resultBean.getResultObj();
		log.info("接收畅捷应答数据：" + JSON.toJSONString(data));
		if(data==null){
			resultBean = new ResultBean("T000", "交易超时请稍后查询");
			log.info("畅捷代收超时,txnseqno:" + tradeBean.getTxnseqno());
			return resultBean;
		}
		txnsWithholdingModel.setExeccode(data.getRetCode());
		txnsWithholdingModel.setExecmsg(data.getErrMsg());
		txnsWithholdingService.updateRealNameResult(txnsWithholdingModel);
		txnsLogService.updatePayInfo_Fast_result(tradeBean.getTxnseqno(),
				data.getRetCode(), data.getErrMsg());

		if (!"0000".equals(data.getRetCode())) {// 交易失败
			//txnsOrderinfoDAO.updateOrderToFail(tradeBean.getTxnseqno());
			txnsLogService.updateCoreRetResult(tradeBean.getTxnseqno(), "3699",
					data.getErrMsg());
			resultBean = new ResultBean(data.getRetCode(), data.getErrMsg());
			 /*data.setRetCode("0000");
			 resultBean.setSuccessResultDto(data);
			 resultBean.setResultBool(true);*/
		} else {
			//txnsOrderinfoDAO.updateOrderToSuccess(tradeBean.getTxnseqno());
			data.setRetCode("0000");
			resultBean = new ResultBean(data);
		}
		log.info("畅捷代收结束,txnseqno:" + tradeBean.getTxnseqno());
		return resultBean;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public ResultBean dealWithAccounting(ResultBean resultBean, String txnseqno) {
		TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
		G10001Bean bean = (G10001Bean) resultBean.getResultObj();
		String retcode = bean.getRetCode();
		String retinfo = bean.getErrMsg();
		String payrettsnseqno = null;
		if ("0000".equals(retcode)) {// 交易成功
			txnsOrderinfoDAO.updateOrderToSuccess(txnseqno);
			txnsLog.setRetcode("0000");
			txnsLog.setRetinfo("交易成功");
		} else {// 交易失败
			txnsOrderinfoDAO.updateOrderToFail(txnseqno);
			txnsLog.setRetcode("3099");
			txnsLog.setRetinfo("交易失败");
		}
		txnsLog.setTradeseltxn(UUIDUtil.uuid());
		txnsLog.setRelate("10000000");
		txnsLog.setTradestatflag(TradeStatFlagEnum.FINISH_SUCCESS.getStatus());
		txnsLog.setRetdatetime(DateUtil.getCurrentDateTime());
		txnsLog.setTradetxnflag("10000000");
		txnsLog.setAccordfintime(DateUtil.getCurrentDateTime());
		txnsLogService.updateTxnsLog(txnsLog);
		// 更新交易支付方信息
		txnsLogService.updatePayInfo_Fast_result(txnseqno, payrettsnseqno,retcode, retinfo);
		String commiteTime = DateUtil.getCurrentDateTime();
		if ("0000".equals(retcode)) {
			// 账务处理
			AccountingAdapterFactory.getInstance()
					.getAccounting(BusiTypeEnum.fromValue(txnsLog.getBusitype()))
					.accountedFor(txnseqno);
			AppPartyBean appParty = new AppPartyBean("", "000000000000",
					commiteTime, DateUtil.getCurrentDateTime(), txnseqno, "");
			txnsLogService.updateAppInfo(appParty);
		}
		tradeNotifyService.notify(txnseqno);
		return null;
	}
	
	
	public ResultBean queryTrade(String txnseqno){
		TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
		TradeStatFlagEnum tradeStatFlagEnum = TradeStatFlagEnum.fromValue(txnsLog.getTradestatflag());
		if(tradeStatFlagEnum==TradeStatFlagEnum.PAYING||tradeStatFlagEnum==TradeStatFlagEnum.OVERTIME){
			return chanPayCollectMoneyService.queryCollectMoney(txnsLog.getPayordno());
		}else {
			return null;
		}
		
	}
}
