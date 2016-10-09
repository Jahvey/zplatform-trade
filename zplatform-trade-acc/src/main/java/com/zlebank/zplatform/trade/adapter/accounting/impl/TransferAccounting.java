/* 
 * ConsumeAccounting.java  
 * 
 * version TODO
 *
 * 2015年9月7日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.adapter.accounting.impl;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.acc.bean.TradeInfo;
import com.zlebank.zplatform.acc.exception.AbstractBusiAcctException;
import com.zlebank.zplatform.acc.exception.AccBussinessException;
import com.zlebank.zplatform.acc.exception.IllegalEntryRequestException;
import com.zlebank.zplatform.acc.service.AccEntryService;
import com.zlebank.zplatform.acc.service.entry.EntryEvent;
import com.zlebank.zplatform.commons.dao.pojo.AccStatusEnum;
import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.member.bean.InduGroupMemberBean;
import com.zlebank.zplatform.member.service.IndustryGroupMemberService;
import com.zlebank.zplatform.trade.adapter.accounting.IAccounting;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.enums.BusinessEnum;
import com.zlebank.zplatform.trade.bean.enums.TradeStatFlagEnum;
import com.zlebank.zplatform.trade.dao.ITxnsOrderinfoDAO;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.SpringContext;
import com.zlebank.zplatform.trade.utils.UUIDUtil;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月7日 上午11:48:09
 * @since
 */
public class TransferAccounting implements IAccounting {
	private static final Log log = LogFactory.getLog(TransferAccounting.class);
	private ITxnsLogService txnsLogService;
	private AccEntryService accEntryService;
	private IndustryGroupMemberService industryGroupMemberService = (IndustryGroupMemberService) SpringContext.getContext().getBean("industryGroupMemberServiceImpl");

	private ITxnsOrderinfoDAO txnsOrderinfoDAO = (ITxnsOrderinfoDAO)SpringContext.getContext().getBean("txnsOrderinfoDAO");
	public TransferAccounting() {
		txnsLogService = (ITxnsLogService) SpringContext.getContext().getBean(
				"txnsLogService");
		accEntryService = (AccEntryService) SpringContext.getContext().getBean(
				"accEntryServiceImpl");
	}

	/**
	 *
	 * @param txnseqno
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public ResultBean accountedFor(String txnseqno) {
		log.info("交易:" + txnseqno + "转账账务处理开始");
		ResultBean resultBean = null;
		TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
		BusinessEnum businessEnum = BusinessEnum.fromValue(txnsLog
				.getBusicode());
		if (businessEnum == BusinessEnum.TRANSFER) {
			resultBean = basicTransfer(txnsLog);
		}else if(businessEnum == BusinessEnum.TRANSFER_INDUSTRY){
			resultBean = industryTransferAccounting(txnsLog);
		}else if (businessEnum == BusinessEnum.EXTRACT_INDUSTRY) {
			resultBean = industryExtractAccounting(txnsLog);
		}

		return resultBean;
	}

	public ResultBean basicTransfer(TxnsLogModel txnsLog) {
		// 记录转账的流水
		ResultBean resultBean = null;
		/** 支付订单号 **/
		/** 交易类型 **/
		String busiCode = txnsLog.getBusicode(); // 转账的业务类型
		/** 付款方会员ID **/
		String payMemberId = txnsLog.getAccsecmerno();
		/** 收款方会员ID **/
		String payToMemberId = txnsLog.getAccmemberid();
		/** 收款方父级会员ID **/
		String payToParentMemberId = "";
		/** 渠道 **/
		String channelId = "";// 转账没有渠道，支付机构代码
		/** 产品id **/
		String productId = "";
		/** 交易金额 **/
		BigDecimal amount = new BigDecimal(txnsLog.getAmount());
		/** 佣金 **/
		BigDecimal commission = new BigDecimal(StringUtil.isNotEmpty(txnsLog
				.getTradcomm() + "") ? txnsLog.getTradcomm() : 0);
		/** 手续费 **/
		BigDecimal charge = new BigDecimal(StringUtil.isNotEmpty(txnsLog
				.getTxnfee() + "") ? txnsLog.getTxnfee() : 0L);
		/** 金额D **/
		BigDecimal amountD = new BigDecimal(0);
		/** 金额E **/
		BigDecimal amountE = new BigDecimal(0);

		TradeInfo tradeInfo = new TradeInfo(txnsLog.getTxnseqno(),
				txnsLog.getPayordno(), busiCode, payMemberId, payToMemberId,
				payToParentMemberId, channelId, productId, amount, commission,
				charge, amountD, amountE, false);
		tradeInfo.setCoopInstCode(txnsLog.getAccfirmerno());

		log.info(JSON.toJSONString(tradeInfo));
		try {
			accEntryService
					.accEntryProcess(tradeInfo, EntryEvent.TRADE_SUCCESS);
			resultBean = new ResultBean("success");
		} catch (AccBussinessException e) {
			resultBean = new ResultBean(e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (AbstractBusiAcctException e) {
			resultBean = new ResultBean(e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (NumberFormatException e) {
			resultBean = new ResultBean("AE099", e.getMessage());
			e.printStackTrace();
		} catch (IllegalEntryRequestException e) {
			// TODO Auto-generated catch block
			resultBean = new ResultBean(e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			resultBean = new ResultBean("", e.getMessage());
			e.printStackTrace();
		}
		// 处理账务
		String retCode = "";
		String retInfo = "";
		if (resultBean.isResultBool()) {
			retCode = "0000";
			retInfo = "交易成功";
		} else {
			retCode = "0099";
			retInfo = resultBean.getErrMsg();
		}
		txnsLog.setPayordfintime(DateUtil.getCurrentDateTime());
		txnsLog.setAccordfintime(DateUtil.getCurrentDateTime());
		txnsLog.setPayretcode(retCode);
		txnsLog.setPayretinfo(retInfo);
		txnsLog.setAppordcommitime(DateUtil.getCurrentDateTime());
		txnsLog.setAppinst("000000000000");// 没实际意义，可以为空
		if ("0000".equals(retCode)) {
			txnsLog.setApporderinfo("转账账务成功");
			txnsLog.setApporderstatus(AccStatusEnum.Finish.getCode());
		} else {
			txnsLog.setApporderinfo(retInfo);
			txnsLog.setApporderstatus(AccStatusEnum.AccountingFail.getCode());
		}
		txnsLog.setAppordfintime(DateUtil.getCurrentDateTime());
		txnsLog.setRetcode(retCode);
		txnsLog.setRetinfo(retInfo);
		// 支付定单完成时间
		this.txnsLogService.updateTxnsLog(txnsLog);
		log.info("交易:" + txnsLog.getTxnseqno() + "转账账务处理成功");
		return resultBean;
	}
	private void updateAppResult(TxnsLogModel txnsLog, String retCode,
			String retInfo) {
		txnsLog.setPayordfintime(DateUtil.getCurrentDateTime());
		txnsLog.setAccordfintime(DateUtil.getCurrentDateTime());
		txnsLog.setPayretcode(retCode);
		txnsLog.setPayretinfo(retInfo);
		txnsLog.setAppordcommitime(DateUtil.getCurrentDateTime());
		txnsLog.setAppinst("000000000000");// 没实际意义，可以为空
		if ("0000".equals(retCode)) {
			txnsLog.setApporderinfo("转账账务成功");
			txnsLog.setApporderstatus(AccStatusEnum.Finish.getCode());
		} else {
			txnsLog.setApporderinfo(retInfo);
			txnsLog.setApporderstatus(AccStatusEnum.AccountingFail.getCode());
		}
		txnsLog.setAppordfintime(DateUtil.getCurrentDateTime());
		txnsLog.setRetcode(retCode);
		txnsLog.setRetinfo(retInfo);
		// 支付定单完成时间
		this.txnsLogService.update(txnsLog);

	}
	@Transactional(propagation = Propagation.REQUIRED)
	public ResultBean industryTransferAccounting(TxnsLogModel txnsLog){
		ResultBean resultBean = null;
		InduGroupMemberBean toGroupMember = industryGroupMemberService.getGroupMemberByMemberIdAndGroupCode(txnsLog.getAccmemberid(), txnsLog.getGroupcode());
		InduGroupMemberBean fromGroupMember= industryGroupMemberService.getGroupMemberByMemberIdAndGroupCode(txnsLog.getAccsecmerno(), txnsLog.getGroupcode());
		/** 支付订单号 **/
		/** 交易类型 **/
		String busiCode = txnsLog.getBusicode(); // 转账的业务类型
		/** 付款方会员ID **/
		String payMemberId = fromGroupMember.getUniqueTag();
		/** 收款方会员ID **/
		String payToMemberId = toGroupMember.getUniqueTag();
		/** 收款方父级会员ID **/
		String payToParentMemberId = "";
		/** 渠道 **/
		String channelId = "";// 转账没有渠道，支付机构代码
		/** 产品id **/
		String productId = "";
		/** 交易金额 **/
		BigDecimal amount = new BigDecimal(txnsLog.getAmount());
		/** 佣金 **/
		BigDecimal commission = new BigDecimal(StringUtil.isNotEmpty(txnsLog
				.getTradcomm() + "") ? txnsLog.getTradcomm() : 0);
		/** 手续费 **/
		BigDecimal charge = new BigDecimal(StringUtil.isNotEmpty(txnsLog
				.getTxnfee() + "") ? txnsLog.getTxnfee() : 0L);
		/** 金额D **/
		BigDecimal amountD = new BigDecimal(0);
		/** 金额E **/
		BigDecimal amountE = new BigDecimal(0);
		/** 机构 */
        String coopInstCode = ConsUtil.getInstance().cons.getZlebank_coopinsti_code();
        /** 接入机构 */
        String access_coopInstCode = txnsLog.getAccfirmerno();
		TradeInfo tradeInfo = new TradeInfo(txnsLog.getTxnseqno(),
				txnsLog.getPayordno(), busiCode, payMemberId, payToMemberId,
				payToParentMemberId, channelId, productId, amount, commission,
				charge, amountD, amountE, false);
		tradeInfo.setCoopInstCode(coopInstCode);
        tradeInfo.setAccess_coopInstCode(access_coopInstCode);
        tradeInfo.setIndustry_group_member_tag(fromGroupMember.getUniqueTag());
        
		log.info(JSON.toJSONString(tradeInfo));
		try {
			accEntryService.accEntryProcess(tradeInfo, EntryEvent.TRADE_SUCCESS);
			resultBean = new ResultBean("success");
		} catch (AccBussinessException e) {
			resultBean = new ResultBean(e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (AbstractBusiAcctException e) {
			resultBean = new ResultBean(e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (NumberFormatException e) {
			resultBean = new ResultBean("AE099", e.getMessage());
			e.printStackTrace();
		} catch (IllegalEntryRequestException e) {
			// TODO Auto-generated catch block
			resultBean = new ResultBean(e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			resultBean = new ResultBean("", e.getMessage());
			e.printStackTrace();
		}
		// 处理账务
		String retCode = "";
		String retInfo = "";
		if (resultBean.isResultBool()) {
			retCode = "0000";
			retInfo = "交易成功";
		} else {
			retCode = "0099";
			retInfo = resultBean.getErrMsg();
		}
		txnsLog.setPayordfintime(DateUtil.getCurrentDateTime());
		txnsLog.setAccordfintime(DateUtil.getCurrentDateTime());
		txnsLog.setPayretcode(retCode);
		txnsLog.setPayretinfo(retInfo);
		txnsLog.setAppordcommitime(DateUtil.getCurrentDateTime());
		txnsLog.setAppinst("000000000000");// 没实际意义，可以为空
		if ("0000".equals(retCode)) {
			txnsLog.setApporderinfo("转账账务成功");
			txnsLog.setApporderstatus(AccStatusEnum.Finish.getCode());
			txnsLog.setTradetxnflag(TradeStatFlagEnum.FINISH_SUCCESS.getStatus());
			txnsOrderinfoDAO.updateOrderToSuccess(txnsLog.getTxnseqno());
		} else {
			txnsLog.setApporderinfo(retInfo);
			txnsLog.setApporderstatus(AccStatusEnum.AccountingFail.getCode());
			txnsOrderinfoDAO.updateOrderToFail(txnsLog.getTxnseqno());
			txnsLog.setTradetxnflag(TradeStatFlagEnum.FINISH_FAILED.getStatus());
		}
		txnsLog.setAppordfintime(DateUtil.getCurrentDateTime());
		txnsLog.setRetcode(retCode);
		txnsLog.setRetinfo(retInfo);
		//更新交易流水表交易位
        txnsLog.setRelate("01000000");
        txnsLog.setTradetxnflag("01000000");
        txnsLog.setTradestatflag(TradeStatFlagEnum.FINISH_ACCOUNTING.getStatus());
        txnsLog.setRetdatetime(DateUtil.getCurrentDateTime());
        txnsLog.setAccbusicode( BusinessEnum.TRANSFER_INDUSTRY.getBusiCode());
        txnsLog.setTradeseltxn(UUIDUtil.uuid());
       
		// 支付定单完成时间
		this.txnsLogService.updateTxnsLog(txnsLog);
		log.info("交易:" + txnsLog.getTxnseqno() + "转账账务处理成功");
		return resultBean;
	}
	
	public ResultBean industryExtractAccounting(TxnsLogModel txnsLog){
		ResultBean resultBean = null;
		InduGroupMemberBean groupMember = industryGroupMemberService.getGroupMemberByMemberIdAndGroupCode(txnsLog.getAccmemberid(), txnsLog.getGroupcode());
		/** 支付订单号 **/
		/** 交易类型 **/
		String busiCode = txnsLog.getBusicode(); // 转账的业务类型
		/** 付款方会员ID **/
		String payMemberId = txnsLog.getAccmemberid();
		/** 收款方会员ID **/
		String payToMemberId = groupMember.getUniqueTag();
		/** 收款方父级会员ID **/
		String payToParentMemberId = "";
		/** 渠道 **/
		String channelId = "";// 转账没有渠道，支付机构代码
		/** 产品id **/
		String productId = "";
		/** 交易金额 **/
		BigDecimal amount = new BigDecimal(txnsLog.getAmount());
		/** 佣金 **/
		BigDecimal commission = new BigDecimal(StringUtil.isNotEmpty(txnsLog
				.getTradcomm() + "") ? txnsLog.getTradcomm() : 0);
		/** 手续费 **/
		BigDecimal charge = new BigDecimal(StringUtil.isNotEmpty(txnsLog
				.getTxnfee() + "") ? txnsLog.getTxnfee() : 0L);
		/** 金额D **/
		BigDecimal amountD = new BigDecimal(0);
		/** 金额E **/
		BigDecimal amountE = new BigDecimal(0);
		/** 机构 */
        String coopInstCode = ConsUtil.getInstance().cons.getZlebank_coopinsti_code();
        /** 接入机构 */
        String access_coopInstCode = txnsLog.getAccfirmerno();
		TradeInfo tradeInfo = new TradeInfo(txnsLog.getTxnseqno(),
				txnsLog.getPayordno(), busiCode, payMemberId, payToMemberId,
				payToParentMemberId, channelId, productId, amount, commission,
				charge, amountD, amountE, false);
		tradeInfo.setCoopInstCode(coopInstCode);
        tradeInfo.setAccess_coopInstCode(access_coopInstCode);
        tradeInfo.setIndustry_group_member_tag(groupMember.getUniqueTag());
        
		log.info(JSON.toJSONString(tradeInfo));
		try {
			accEntryService.accEntryProcess(tradeInfo, EntryEvent.TRADE_SUCCESS);
			resultBean = new ResultBean("success");
		} catch (AccBussinessException e) {
			resultBean = new ResultBean(e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (AbstractBusiAcctException e) {
			resultBean = new ResultBean(e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (NumberFormatException e) {
			resultBean = new ResultBean("AE099", e.getMessage());
			e.printStackTrace();
		} catch (IllegalEntryRequestException e) {
			// TODO Auto-generated catch block
			resultBean = new ResultBean(e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			resultBean = new ResultBean("", e.getMessage());
			e.printStackTrace();
		}
		// 处理账务
		String retCode = "";
		String retInfo = "";
		if (resultBean.isResultBool()) {
			retCode = "0000";
			retInfo = "交易成功";
		} else {
			retCode = "0099";
			retInfo = resultBean.getErrMsg();
		}
		txnsLog.setPayordfintime(DateUtil.getCurrentDateTime());
		txnsLog.setAccordfintime(DateUtil.getCurrentDateTime());
		txnsLog.setPayretcode(retCode);
		txnsLog.setPayretinfo(retInfo);
		txnsLog.setAppordcommitime(DateUtil.getCurrentDateTime());
		txnsLog.setAppinst("000000000000");// 没实际意义，可以为空
		if ("0000".equals(retCode)) {
			txnsLog.setApporderinfo("转账账务成功");
			txnsLog.setApporderstatus(AccStatusEnum.Finish.getCode());
			txnsLog.setTradetxnflag(TradeStatFlagEnum.FINISH_SUCCESS.getStatus());
			txnsOrderinfoDAO.updateOrderToSuccess(txnsLog.getTxnseqno());
		} else {
			txnsLog.setApporderinfo(retInfo);
			txnsLog.setApporderstatus(AccStatusEnum.AccountingFail.getCode());
			txnsOrderinfoDAO.updateOrderToFail(txnsLog.getTxnseqno());
			 txnsLog.setTradetxnflag(TradeStatFlagEnum.FINISH_FAILED.getStatus());
		}
		txnsLog.setAppordfintime(DateUtil.getCurrentDateTime());
		txnsLog.setRetcode(retCode);
		txnsLog.setRetinfo(retInfo);
		//更新交易流水表交易位
        txnsLog.setRelate("01000000");
        txnsLog.setTradetxnflag("01000000");
        txnsLog.setTradestatflag(TradeStatFlagEnum.FINISH_ACCOUNTING.getStatus());
        txnsLog.setRetdatetime(DateUtil.getCurrentDateTime());
        txnsLog.setAccbusicode( BusinessEnum.TRANSFER_INDUSTRY.getBusiCode());
        txnsLog.setTradeseltxn(UUIDUtil.uuid());
       
		// 支付定单完成时间
		this.txnsLogService.updateTxnsLog(txnsLog);
		log.info("交易:" + txnsLog.getTxnseqno() + "转账账务处理成功");
		return resultBean;
		
	}

	@Override
	public ResultBean accountedForInsteadPay(String batchno) {
		// TODO Auto-generated method stub
		return null;
	}

}
