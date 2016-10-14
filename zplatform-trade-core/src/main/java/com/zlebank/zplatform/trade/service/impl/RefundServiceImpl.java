/* 
 * RefundServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年5月17日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.acc.bean.TradeInfo;
import com.zlebank.zplatform.acc.bean.enums.AcctStatusType;
import com.zlebank.zplatform.acc.bean.enums.TradeType;
import com.zlebank.zplatform.acc.bean.enums.Usage;
import com.zlebank.zplatform.acc.exception.AbstractBusiAcctException;
import com.zlebank.zplatform.acc.exception.AccBussinessException;
import com.zlebank.zplatform.acc.exception.IllegalEntryRequestException;
import com.zlebank.zplatform.acc.service.AccEntryService;
import com.zlebank.zplatform.acc.service.entry.EntryEvent;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.member.bean.MemberAccountBean;
import com.zlebank.zplatform.member.bean.MemberBean;
import com.zlebank.zplatform.member.bean.enums.MemberType;
import com.zlebank.zplatform.member.exception.DataCheckFailedException;
import com.zlebank.zplatform.member.exception.GetAccountFailedException;
import com.zlebank.zplatform.member.pojo.PojoMember;
import com.zlebank.zplatform.member.pojo.PojoMerchDeta;
import com.zlebank.zplatform.member.service.CoopInstiService;
import com.zlebank.zplatform.member.service.MemberAccountService;
import com.zlebank.zplatform.member.service.MemberService;
import com.zlebank.zplatform.member.service.MerchService;
import com.zlebank.zplatform.trade.adapter.quickpay.IRefundTrade;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.enums.BusinessEnum;
import com.zlebank.zplatform.trade.bean.enums.TradeStatFlagEnum;
import com.zlebank.zplatform.trade.bean.gateway.RefundOrderBean;
import com.zlebank.zplatform.trade.dao.ITxnsOrderinfoDAO;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.factory.TradeAdapterFactory;
import com.zlebank.zplatform.trade.model.TxncodeDefModel;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsOrderinfoModel;
import com.zlebank.zplatform.trade.model.TxnsRefundModel;
import com.zlebank.zplatform.trade.service.ITxncodeDefService;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.ITxnsRefundService;
import com.zlebank.zplatform.trade.service.RefundRouteConfigService;
import com.zlebank.zplatform.trade.service.RefundService;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.DateUtil;
import com.zlebank.zplatform.trade.utils.OrderNumber;
import com.zlebank.zplatform.trade.utils.ValidateLocator;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年5月17日 下午4:25:32
 * @since 
 */
@Service("refundService")
public class RefundServiceImpl implements RefundService{
	
	private static final Logger logger = LoggerFactory.getLogger(RefundServiceImpl.class);
	@Autowired
	private ITxnsRefundService txnsRefundService;
	@Autowired
	private ITxnsLogService txnsLogService;
	@Autowired
	private RefundRouteConfigService refundRouteConfigService;
	@Autowired
	private ITxncodeDefService txncodeDefService;
	@Autowired
	private MerchService merchService;
	@Autowired
	private ITxnsOrderinfoDAO txnsOrderinfoDAO;
	@Autowired
	private CoopInstiService coopInstiService;
	@Autowired
	private AccEntryService accEntryService;
	@Autowired
	private MemberAccountService memberAccountService;
	@Autowired
	private MemberService memberService;
	/**
	 *
	 * @param refundOrderNo
	 * @return
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public ResultBean execute(String refundOrderNo,String merchNo) {
		ResultBean resultBean = null;
		// TODO Auto-generated method stub
		//退款订单
		TxnsRefundModel refundOrder = txnsRefundService.getRefundByRefundorderNo(refundOrderNo, merchNo);
		if(refundOrder==null){
			return new ResultBean("GW15", "找不到原始订单");
		}
		//原交易流水
		TxnsLogModel txnsLog_old = txnsLogService.getTxnsLogByTxnseqno(refundOrder.getOldtxnseqno());
		//退款的交易流水
		TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(refundOrder.getReltxnseqno());
		//原始的支付方式 （01：快捷，02：网银，03：账户）
		String payType = txnsLog_old.getPaytype();
		//匿名判断
		String payMember = txnsLog_old.getAccmemberid();
		boolean anonFlag = false;
		if("999999999999999".equals(payMember)){
			anonFlag = true;
		}
		//原交易渠道号
		String payChannelCode = txnsLog_old.getPayinst();
		//原交易类型  1000002为账户余额支付
		String accbusicode = txnsLog_old.getAccbusicode();
		//退款路由选择退款渠道或者退款的方式
		ResultBean refundRoutResultBean = refundRouteConfigService.getTransRout(DateUtil.getCurrentDateTime(), txnsLog.getAmount()+"", merchNo, accbusicode, txnsLog_old.getPan(), payChannelCode, anonFlag?"1":"0");
		if(refundRoutResultBean.isResultBool()){
			String refundRout = refundRoutResultBean.getResultObj().toString();
			try {
				IRefundTrade refundTrade = TradeAdapterFactory.getInstance().getRefundTrade(refundRout);
				TradeBean tradeBean = new TradeBean();
				tradeBean.setTxnseqno(txnsLog.getTxnseqno());
				refundTrade.refund(tradeBean);
				resultBean =new ResultBean("success");
			} catch (TradeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resultBean =new ResultBean(e.getCode(),e.getMessage());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resultBean =new ResultBean("","无退款实现类");
				resultBean.setResultBool(false);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				resultBean =new ResultBean("","退款失败");
				resultBean.setResultBool(false);
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				resultBean =new ResultBean("","退款失败");
				resultBean.setResultBool(false);
				e.printStackTrace();
			}
		}else{
			resultBean = new ResultBean("此退款交易无有效路由");
			resultBean.setResultBool(false);
		}
		
		
		return resultBean;
	}

	/**
	 *
	 * @param orderBean
	 * @return
	 * @throws TradeException 
	 */
	@Override
	public String commonRefund(RefundOrderBean orderBean) throws TradeException {
		PojoMerchDeta member = null;
		TxnsLogModel txnsLog = null;
		TxnsOrderinfoModel old_orderInfo  = null;
		TxnsLogModel old_txnsLog = null;
		try {
			old_orderInfo = txnsOrderinfoDAO.getOrderByTN(orderBean.getOrigOrderId());
			old_txnsLog = txnsLogService.getTxnsLogByTxnseqno(old_orderInfo.getRelatetradetxn());
			TxncodeDefModel busiModel = txncodeDefService.getBusiCode(
					orderBean.getTxnType(), orderBean.getTxnSubType(),
					orderBean.getBizType());
			if (busiModel == null) {
				throw new TradeException("");
			}
			// member = memberService.get(refundBean.getCoopInstiId());
			txnsLog = new TxnsLogModel();
			if (StringUtil.isNotEmpty(orderBean.getMerId())) {// 商户为空时，取商户的各个版本信息
				member = merchService.getMerchBymemberId(orderBean.getMerId());
				txnsLog.setRiskver(member.getRiskVer());
				txnsLog.setSplitver(member.getSpiltVer());
				txnsLog.setFeever(member.getFeeVer());
				txnsLog.setPrdtver(member.getPrdtVer());
				// txnsLog.setCheckstandver(member.getCashver());
				txnsLog.setRoutver(member.getRoutVer());
				txnsLog.setAccordinst(member.getParent() + "");
				txnsLog.setAccsettledate(DateUtil.getSettleDate(Integer
						.valueOf(member.getSetlCycle().toString())));
			} else {
				txnsLog.setRiskver(txnsLogService.getDefaultVerInfo(
						orderBean.getCoopInstiId(), busiModel.getBusicode(),
						13));
				txnsLog.setSplitver(txnsLogService.getDefaultVerInfo(
						orderBean.getCoopInstiId(), busiModel.getBusicode(),
						12));
				txnsLog.setFeever(txnsLogService.getDefaultVerInfo(
						orderBean.getCoopInstiId(), busiModel.getBusicode(),
						11));
				txnsLog.setPrdtver(txnsLogService.getDefaultVerInfo(
						orderBean.getCoopInstiId(), busiModel.getBusicode(),
						10));
				txnsLog.setRoutver(txnsLogService.getDefaultVerInfo(
						orderBean.getCoopInstiId(), busiModel.getBusicode(),
						20));
				txnsLog.setAccsettledate(DateUtil.getSettleDate(1));
			}

			txnsLog.setTxndate(DateUtil.getCurrentDate());
			txnsLog.setTxntime(DateUtil.getCurrentTime());
			txnsLog.setBusicode(busiModel.getBusicode());
			txnsLog.setBusitype(busiModel.getBusitype());
			// 核心交易流水号，交易时间（yymmdd）+业务代码+6位流水号（每日从0开始）
			txnsLog.setTxnseqno(OrderNumber.getInstance().generateTxnseqno(
					txnsLog.getBusicode()));
			txnsLog.setAmount(Long.valueOf(orderBean.getTxnAmt()));
			txnsLog.setAccordno(orderBean.getOrderId());
			txnsLog.setAccfirmerno(orderBean.getCoopInstiId());
			txnsLog.setAccsecmerno(orderBean.getMerId());
			txnsLog.setAcccoopinstino(ConsUtil.getInstance().cons.getZlebank_coopinsti_code());
			txnsLog.setTxnseqnoOg(old_txnsLog.getTxnseqno());
			txnsLog.setAccordcommitime(DateUtil.getCurrentDateTime());
			txnsLog.setTradestatflag("00000000");// 交易初始状态
			txnsLog.setAccsettledate(DateUtil.getSettleDate(Integer
					.valueOf(member.getSetlCycle().toString())));
			txnsLog.setAccmemberid(orderBean.getMemberId());

			// 匿名判断
			String payMember = old_txnsLog.getAccmemberid();
			boolean anonFlag = false;
			if ("999999999999999".equals(payMember)) {
				anonFlag = true;
			}
			// 原交易渠道号
			String payChannelCode = old_txnsLog.getPayinst();
			// 原交易类型 1000002为账户余额支付
			String accbusicode = old_txnsLog.getAccbusicode();
			// 退款路由选择退款渠道或者退款的方式
			ResultBean refundRoutResultBean = refundRouteConfigService
					.getTransRout(DateUtil.getCurrentDateTime(),
							txnsLog.getAmount() + "", "", accbusicode, txnsLog
									.getPan(), payChannelCode, anonFlag ? "1"
									: "0");
			if (refundRoutResultBean.isResultBool()) {
				
				String refundRout = refundRoutResultBean.getResultObj()
						.toString();
				if ("99999999".equals(refundRout)) {
					txnsLog.setBusicode(BusinessEnum.REFUND_ACCOUNT
							.getBusiCode());
				} else {
					txnsLog.setBusicode(BusinessEnum.REFUND_BANK.getBusiCode());
				}
			}
			txnsLog.setTxnfee(txnsLogService.getTxnFee(txnsLog));
			txnsLog.setTradcomm(0L);
			txnsLogService.saveTxnsLog(txnsLog);
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new TradeException("T016");
		} 

		String tn = "";
		TxnsOrderinfoModel orderinfo = null;
		try {
			// 保存订单信息
			orderinfo = new TxnsOrderinfoModel();
			orderinfo.setId(OrderNumber.getInstance().generateID());
			// orderinfo.setInstitution(member.getMerchinsti());
			orderinfo.setOrderno(orderBean.getOrderId());// 商户提交的订单号
			orderinfo.setOrderamt(Long.valueOf(orderBean.getTxnAmt()));
			orderinfo.setOrdercommitime(orderBean.getTxnTime());
			orderinfo.setRelatetradetxn(txnsLog.getTxnseqno());// 关联的交易流水表中的交易序列号
			orderinfo.setFirmemberno(orderBean.getCoopInstiId());
			orderinfo.setFirmembername(coopInstiService.getInstiByInstiCode(
					orderBean.getCoopInstiId()).getInstiName());
			orderinfo.setSecmemberno(orderBean.getMerId());
			orderinfo.setSecmembername(member == null ? "" : member
					.getAccName());
			orderinfo.setBackurl(orderBean.getBackUrl());
			orderinfo.setTxntype(orderBean.getTxnType());
			orderinfo.setTxnsubtype(orderBean.getTxnSubType());
			orderinfo.setBiztype(orderBean.getBizType());
			//orderinfo.setReqreserved(orderBean.getReqReserved());
			orderinfo.setOrderdesc(orderBean.getOrderDesc());
			//orderinfo.setAccesstype(orderBean.getAccessType());
			orderinfo.setTn(OrderNumber.getInstance().generateTN(
					txnsLog.getAccfirmerno()));
			orderinfo.setStatus("02");
			orderinfo.setMemberid(orderBean.getMemberId());
			orderinfo.setCurrencycode("156");
			
			txnsLogService.tradeRiskControl(txnsLog.getTxnseqno(),txnsLog.getAccfirmerno(),txnsLog.getAccsecmerno(),txnsLog.getAccmemberid(),txnsLog.getBusicode(),txnsLog.getAmount()+"","1","");
			
			// 退款账务处理
			TradeInfo tradeInfo = new TradeInfo();
			tradeInfo.setPayMemberId(orderBean.getMemberId());
			tradeInfo.setPayToMemberId(orderBean.getMerId());
			tradeInfo.setAmount(new BigDecimal(orderBean.getTxnAmt()));
			tradeInfo.setCharge(new BigDecimal(txnsLogService
					.getTxnFee(txnsLog)));
			tradeInfo.setTxnseqno(txnsLog.getTxnseqno());
			tradeInfo.setCoopInstCode(txnsLog.getAccfirmerno());
			tradeInfo.setBusiCode(txnsLog.getBusicode());
			// 记录分录流水
			accEntryService.accEntryProcess(tradeInfo, EntryEvent.AUDIT_APPLY);
			txnsOrderinfoDAO.saveOrderInfo(orderinfo);
			tn = orderinfo.getTn();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new TradeException("T020");
		}catch (AccBussinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new TradeException("T000", "账务异常:"+e.getMessage());
		} catch (AbstractBusiAcctException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new TradeException("T000", "账务异常:"+e.getMessage());
		} catch (IllegalEntryRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new TradeException("T000", "账务异常:"+e.getMessage());
		} catch (TradeException e) {
			orderinfo.setStatus("03");
			txnsOrderinfoDAO.saveOrderInfo(orderinfo);
			return null;
			
		}

		try {
			// 无异常时保存退款交易流水表，以便于以后退款审核操作
			TxnsRefundModel refundOrder = new TxnsRefundModel();
			refundOrder.setRefundorderno(OrderNumber.getInstance().generateRefundOrderNo());
			refundOrder.setOldorderno(orderBean.getOrigOrderId());
			refundOrder.setOldtxnseqno(old_txnsLog.getTxnseqno());
			refundOrder.setMerchno(orderBean.getCoopInstiId());
			refundOrder.setSubmerchno(orderBean.getMerId());
			refundOrder.setMemberid(orderBean.getMemberId());
			refundOrder.setAmount(Long.valueOf(orderBean.getTxnAmt()));
			refundOrder.setOldamount(old_orderInfo.getOrderamt());   
			refundOrder.setRefundtype(orderBean.getRefundType());
			refundOrder.setRefunddesc(orderBean.getOrderDesc()); 
			refundOrder.setReltxnseqno(txnsLog.getTxnseqno());  
			refundOrder.setTxntime(DateUtil.getCurrentDateTime());
			refundOrder.setStatus("01");
			refundOrder.setRelorderno(orderBean.getOrderId());
			txnsRefundService.saveRefundOrder(refundOrder);
			return tn;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new TradeException("T022");
		}
		
	}

	/**
	 *
	 * @param orderBean
	 * @return
	 */
	@Override
	public String productRefund(RefundOrderBean orderBean) throws TradeException{
		PojoMerchDeta member = null;
		TxnsLogModel txnsLog = null;
		TxnsOrderinfoModel old_orderInfo  = null;
		TxnsLogModel old_txnsLog = null;
		try {
			old_orderInfo = txnsOrderinfoDAO.getOrderByTN(orderBean.getOrigOrderId());
			old_txnsLog = txnsLogService.getTxnsLogByTxnseqno(old_orderInfo.getRelatetradetxn());
			TxncodeDefModel busiModel = txncodeDefService.getBusiCode(
					orderBean.getTxnType(), orderBean.getTxnSubType(),
					orderBean.getBizType());
			if (busiModel == null) {
				throw new TradeException("");
			}
			// member = memberService.get(refundBean.getCoopInstiId());
			txnsLog = new TxnsLogModel();
			if (StringUtil.isNotEmpty(orderBean.getMerId())) {// 商户为空时，取商户的各个版本信息
				member = merchService.getMerchBymemberId(orderBean.getMerId());
				txnsLog.setRiskver(member.getRiskVer());
				txnsLog.setSplitver(member.getSpiltVer());
				txnsLog.setFeever(member.getFeeVer());
				txnsLog.setPrdtver(member.getPrdtVer());
				// txnsLog.setCheckstandver(member.getCashver());
				txnsLog.setRoutver(member.getRoutVer());
				txnsLog.setAccordinst(member.getParent() + "");
				txnsLog.setAccsettledate(DateUtil.getSettleDate(Integer
						.valueOf(member.getSetlCycle().toString())));
			} else {
				txnsLog.setRiskver(txnsLogService.getDefaultVerInfo(
						orderBean.getCoopInstiId(), busiModel.getBusicode(),
						13));
				txnsLog.setSplitver(txnsLogService.getDefaultVerInfo(
						orderBean.getCoopInstiId(), busiModel.getBusicode(),
						12));
				txnsLog.setFeever(txnsLogService.getDefaultVerInfo(
						orderBean.getCoopInstiId(), busiModel.getBusicode(),
						11));
				txnsLog.setPrdtver(txnsLogService.getDefaultVerInfo(
						orderBean.getCoopInstiId(), busiModel.getBusicode(),
						10));
				txnsLog.setRoutver(txnsLogService.getDefaultVerInfo(
						orderBean.getCoopInstiId(), busiModel.getBusicode(),
						20));
				txnsLog.setAccsettledate(DateUtil.getSettleDate(1));
			}

			txnsLog.setTxndate(DateUtil.getCurrentDate());
			txnsLog.setTxntime(DateUtil.getCurrentTime());
			txnsLog.setBusicode(busiModel.getBusicode());
			txnsLog.setBusitype(busiModel.getBusitype());
			// 核心交易流水号，交易时间（yymmdd）+业务代码+6位流水号（每日从0开始）
			txnsLog.setTxnseqno(OrderNumber.getInstance().generateTxnseqno(
					txnsLog.getBusicode()));
			txnsLog.setAmount(Long.valueOf(orderBean.getTxnAmt()));
			txnsLog.setAccordno(orderBean.getOrderId());
			txnsLog.setAccfirmerno(orderBean.getCoopInstiId());
			txnsLog.setAccsecmerno(orderBean.getMerId());
			txnsLog.setAcccoopinstino(ConsUtil.getInstance().cons.getZlebank_coopinsti_code());
			txnsLog.setTxnseqnoOg(old_txnsLog.getTxnseqno());
			txnsLog.setAccordcommitime(DateUtil.getCurrentDateTime());
			txnsLog.setTradestatflag("00000000");// 交易初始状态
			txnsLog.setAccsettledate(DateUtil.getSettleDate(Integer
					.valueOf(member.getSetlCycle().toString())));
			txnsLog.setAccmemberid(orderBean.getMemberId());

			// 匿名判断
			String payMember = old_txnsLog.getAccmemberid();
			boolean anonFlag = false;
			if ("999999999999999".equals(payMember)) {
				anonFlag = true;
			}
			// 原交易渠道号
			String payChannelCode = old_txnsLog.getPayinst();
			// 原交易类型 1000002为账户余额支付
			String accbusicode = old_txnsLog.getAccbusicode();
			// 退款路由选择退款渠道或者退款的方式
			ResultBean refundRoutResultBean = refundRouteConfigService
					.getTransRout(DateUtil.getCurrentDateTime(),
							txnsLog.getAmount() + "", "", accbusicode, txnsLog
									.getPan(), payChannelCode, anonFlag ? "1"
									: "0");
			if (refundRoutResultBean.isResultBool()) {
				
				String refundRout = refundRoutResultBean.getResultObj()
						.toString();
				if ("99999999".equals(refundRout)) {
					txnsLog.setBusicode(BusinessEnum.REFUND_ACCOUNT
							.getBusiCode());
				} else {
					txnsLog.setBusicode(BusinessEnum.REFUND_BANK.getBusiCode());
				}
			}
			txnsLog.setTxnfee(txnsLogService.getTxnFee(txnsLog));
			txnsLog.setTradcomm(0L);
			txnsLogService.saveTxnsLog(txnsLog);
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new TradeException("T016");
		} 

		String tn = "";
		TxnsOrderinfoModel orderinfo = null;
		try {
			// 保存订单信息
			orderinfo = new TxnsOrderinfoModel();
			orderinfo.setId(OrderNumber.getInstance().generateID());
			// orderinfo.setInstitution(member.getMerchinsti());
			orderinfo.setOrderno(orderBean.getOrderId());// 商户提交的订单号
			orderinfo.setOrderamt(Long.valueOf(orderBean.getTxnAmt()));
			orderinfo.setOrdercommitime(orderBean.getTxnTime());
			orderinfo.setRelatetradetxn(txnsLog.getTxnseqno());// 关联的交易流水表中的交易序列号
			orderinfo.setFirmemberno(orderBean.getCoopInstiId());
			orderinfo.setFirmembername(coopInstiService.getInstiByInstiCode(
					orderBean.getCoopInstiId()).getInstiName());
			orderinfo.setSecmemberno(orderBean.getMerId());
			orderinfo.setSecmembername(member == null ? "" : member
					.getAccName());
			orderinfo.setBackurl(orderBean.getBackUrl());
			orderinfo.setTxntype(orderBean.getTxnType());
			orderinfo.setTxnsubtype(orderBean.getTxnSubType());
			orderinfo.setBiztype(orderBean.getBizType());
			//orderinfo.setReqreserved(orderBean.getReqReserved());
			orderinfo.setOrderdesc(orderBean.getOrderDesc());
			//orderinfo.setAccesstype(orderBean.getAccessType());
			orderinfo.setTn(OrderNumber.getInstance().generateTN(
					txnsLog.getAccfirmerno()));
			orderinfo.setStatus("02");
			orderinfo.setMemberid(orderBean.getMemberId());
			orderinfo.setCurrencycode("156");
			
			txnsLogService.tradeRiskControl(txnsLog.getTxnseqno(),txnsLog.getAccfirmerno(),txnsLog.getAccsecmerno(),txnsLog.getAccmemberid(),txnsLog.getBusicode(),txnsLog.getAmount()+"","1","");
			
			// 退款账务处理
			TradeInfo tradeInfo = new TradeInfo();
			tradeInfo.setPayMemberId(orderBean.getMemberId());
			tradeInfo.setPayToMemberId(orderBean.getMerId());
			tradeInfo.setAmount(new BigDecimal(orderBean.getTxnAmt()));
			tradeInfo.setCharge(new BigDecimal(txnsLogService
					.getTxnFee(txnsLog)));
			tradeInfo.setTxnseqno(txnsLog.getTxnseqno());
			tradeInfo.setCoopInstCode(txnsLog.getAccfirmerno());
			tradeInfo.setBusiCode(TradeType.PRODUCT_CAPITAL_REFUND.getCode());
			tradeInfo.setProductId(txnsLog.getProductcode());
			tradeInfo.setAccess_coopInstCode(txnsLog.getAccfirmerno());
			tradeInfo.setCoopInstCode(ConsUtil.getInstance().cons.getZlebank_coopinsti_code());
			tradeInfo.setProductId(old_txnsLog.getProductcode());
			// 记录分录流水
			accEntryService.accEntryProcess(tradeInfo, EntryEvent.AUDIT_APPLY);
			txnsOrderinfoDAO.saveOrderInfo(orderinfo);
			tn = orderinfo.getTn();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new TradeException("T020");
		}catch (AccBussinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new TradeException("T000", "账务异常:"+e.getMessage());
		} catch (AbstractBusiAcctException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new TradeException("T000", "账务异常:"+e.getMessage());
		} catch (IllegalEntryRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new TradeException("T000", "账务异常:"+e.getMessage());
		} catch (TradeException e) {
			orderinfo.setStatus("03");
			txnsOrderinfoDAO.saveOrderInfo(orderinfo);
			return null;
			
		}

		try {
			// 无异常时保存退款交易流水表，以便于以后退款审核操作
			TxnsRefundModel refundOrder = new TxnsRefundModel();
			refundOrder.setRefundorderno(OrderNumber.getInstance().generateRefundOrderNo());
			refundOrder.setOldorderno(orderBean.getOrigOrderId());
			refundOrder.setOldtxnseqno(old_txnsLog.getTxnseqno());
			refundOrder.setMerchno(orderBean.getCoopInstiId());
			refundOrder.setSubmerchno(orderBean.getMerId());
			refundOrder.setMemberid(orderBean.getMemberId());
			refundOrder.setAmount(Long.valueOf(orderBean.getTxnAmt()));
			refundOrder.setOldamount(old_orderInfo.getOrderamt());   
			refundOrder.setRefundtype(orderBean.getRefundType());
			refundOrder.setRefunddesc(orderBean.getOrderDesc()); 
			refundOrder.setReltxnseqno(txnsLog.getTxnseqno());  
			refundOrder.setTxntime(DateUtil.getCurrentDateTime());
			refundOrder.setStatus("01");
			refundOrder.setRelorderno(orderBean.getOrderId());
			txnsRefundService.saveRefundOrder(refundOrder);
			return tn;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new TradeException("T022");
		}
		
	}

	/**
	 *
	 * @param orderBean
	 * @return
	 */
	@Override
	public String industryRefund(RefundOrderBean orderBean) throws TradeException{
		/**
		 * 1.对退款订单进行数据校验，未通过拒绝交易，并返回错误信息，
		 * 2.订单唯一性检查,原始订单检查,退款时间不能超过30天，退款金额不能大于原始交易金额
		 * 3.订单对外业务代码转换为内部业务代码
		 * 4.行业专户业务检查,非行业专户转账业务，拒绝交易
		 * 5.检查会员是否在同一行业内，不在同一行业内拒绝交易,
		 * 6.检查会员和的账户状态，商户行业专户状态不可以是冻结或者止出，会员行业账户状态不可以是冻结或者止入
		 * 7.赋值交易订单数据和交易流水数据
		 * 8.账务处理
		 * 9.根据账务处理结果处理交易数据
		 * 10.保存订单数据和交易流水数据。退款申请
		 */
		ResultBean resultBean = ValidateLocator.validateBeans(orderBean);
        if(!resultBean.isResultBool()){
        	throw new TradeException("T000",resultBean.getErrMsg());
        }
        
        
        TxnsOrderinfoModel orderinfo = txnsOrderinfoDAO.getOrderinfoByOrderNoAndMemberId(orderBean.getOrderId(),orderBean.getMerId());
        if(orderinfo!=null){
        	throw new TradeException("T000","订单已存在，请不要重复提交");
        }
        TxnsOrderinfoModel orderinfo_old = txnsOrderinfoDAO.getOrderByTN(orderBean.getOrigOrderId());
        if(orderinfo_old==null){
        	throw new TradeException("T000","原交易订单不存在");
        }
        TxnsLogModel old_txnsLog = txnsLogService.getTxnsLogByTxnseqno(orderinfo_old.getRelatetradetxn());
        
        
        TxncodeDefModel busiModel = txncodeDefService.getBusiCode(orderBean.getTxnType(), orderBean.getTxnSubType(), orderBean.getBizType());
        BusinessEnum businessEnum = BusinessEnum.fromValue(busiModel.getBusicode());
        if(businessEnum!=BusinessEnum.REFUND_INDUSTRY){
        	throw new TradeException("T000","业务类型错误");
        }
        BusinessEnum businessEnum_old = BusinessEnum.fromValue(old_txnsLog.getBusicode());
        if(businessEnum_old!=BusinessEnum.CONSUME_INDUSTRY){
        	throw new TradeException("T000","原订单交易类型错误，非行业消费");
        }
        
        
        try {
			MemberBean member = new MemberBean();
			member.setMemberId(orderBean.getMerId());
			MemberAccountBean fromMemberAccount = memberAccountService.queryBalance(null, member, Usage.BASICPAY);
			AcctStatusType acctStatusType = AcctStatusType.fromValue(fromMemberAccount.getStatus());
			if(acctStatusType==AcctStatusType.FREEZE||acctStatusType==AcctStatusType.STOP_OUT){
				throw new TradeException("T000","商户行业账户状态异常");
			}
		} catch (DataCheckFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new TradeException("T000",e.getMessage());
		} catch (GetAccountFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new TradeException("T000",e.getMessage());
		}
        try {
			MemberBean member = new MemberBean();
			member.setMemberId(orderBean.getMemberId());
			MemberAccountBean fromMemberAccount = memberAccountService.queryBalance(null, member, Usage.BASICPAY);
			AcctStatusType acctStatusType = AcctStatusType.fromValue(fromMemberAccount.getStatus());
			if(acctStatusType==AcctStatusType.FREEZE||acctStatusType==AcctStatusType.STOP_IN){
				throw new TradeException("T000","会员行业账户状态异常");
			}
		} catch (DataCheckFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new TradeException("T000",e.getMessage());
		} catch (GetAccountFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new TradeException("T000",e.getMessage());
		}
        
        
        String tn = OrderNumber.getInstance().generateTN(orderBean.getMerId());
        String txnseqno = OrderNumber.getInstance().generateTxnseqno(businessEnum.getBusiCode());
        
        TxnsLogModel txnsLog = new TxnsLogModel();
		//获取付款人的信息
		//如果是企业会员
        PojoMember pojoMember = memberService.getMbmberByMemberId(orderBean.getMerId(), null);
		MemberType type= pojoMember.getMemberType();
		if(type.equals(MemberType.ENTERPRISE)){
			PojoMerchDeta member = merchService.getMerchBymemberId(orderBean.getMerId());
			txnsLog.setRiskver(member.getRiskVer());
			txnsLog.setSplitver(member.getSpiltVer());
			txnsLog.setFeever(member.getFeeVer());
			txnsLog.setPrdtver(member.getPrdtVer());
			txnsLog.setRoutver(member.getRoutVer());
			txnsLog.setAccsettledate(DateUtil.getSettleDate(Integer
					.valueOf(member.getSetlCycle().toString())));
		//如果是普通会员
		}else{
			// 10-产品版本,11-扣率版本,12-分润版本,13-风控版本,20-路由版本
			txnsLog.setRiskver(txnsLogService.getDefaultVerInfo(orderBean.getCoopInstiId(),
					busiModel.getBusicode(), 13));
			txnsLog.setSplitver(txnsLogService.getDefaultVerInfo(orderBean.getCoopInstiId(),
					busiModel.getBusicode(), 12));
			txnsLog.setFeever(txnsLogService.getDefaultVerInfo(orderBean.getCoopInstiId(),
					busiModel.getBusicode(), 11));
			txnsLog.setPrdtver(txnsLogService.getDefaultVerInfo(orderBean.getCoopInstiId(),
					busiModel.getBusicode(), 10));
			txnsLog.setRoutver(txnsLogService.getDefaultVerInfo(orderBean.getCoopInstiId(),
					busiModel.getBusicode(), 20));
		}
		
		txnsLog.setAccsettledate(DateUtil.getSettleDate(1));
		txnsLog.setTxndate(DateUtil.getCurrentDate());
		txnsLog.setTxntime(DateUtil.getCurrentTime());
		txnsLog.setBusicode(busiModel.getBusicode());
		//5000-转账
		txnsLog.setBusitype(busiModel.getBusitype());
		// 核心交易流水号，交易时间（yymmdd）+业务代码+6位流水号（每日从0开始）
		txnsLog.setTxnseqno(txnseqno);
		txnsLog.setAmount(Long.valueOf(orderBean.getTxnAmt()));
		txnsLog.setAccordno(orderBean.getOrderId());
		txnsLog.setAccfirmerno(orderBean.getCoopInstiId());
		txnsLog.setAcccoopinstino(orderBean.getCoopInstiId());
		//付款方
		txnsLog.setAccsecmerno(orderBean.getMerId());
		txnsLog.setAccordcommitime(DateUtil.getCurrentDateTime());
		txnsLog.setTradestatflag(TradeStatFlagEnum.INITIAL.getStatus());// 交易初始状态
		txnsLog.setAccmemberid(orderBean.getMerId());
		//佣金
		txnsLog.setTradcomm(0L);
		//计算手续费
		txnsLog.setTxnfee(txnsLogService.getTxnFee(txnsLog));
		txnsLog.setTxnseqnoOg(old_txnsLog.getTxnseqno());
		txnsLog.setGroupcode(old_txnsLog.getGroupcode());
		
        orderinfo = new TxnsOrderinfoModel();
		orderinfo.setId(1L);
		orderinfo.setOrderno(orderBean.getOrderId());// 商户提交的订单号
		orderinfo.setOrderamt(Long.valueOf(orderBean.getTxnAmt()));
		orderinfo.setOrderfee(txnsLog.getTxnfee());
		orderinfo.setOrdercommitime(orderBean.getTxnTime());
		orderinfo.setRelatetradetxn(txnseqno);// 关联的交易流水表中的交易序列号
		//合作机构
		orderinfo.setFirmemberno(orderBean.getCoopInstiId());
		orderinfo.setFirmembername(coopInstiService.getInstiByInstiCode(orderBean.getCoopInstiId()).getInstiName());
		//二级商户
		orderinfo.setSecmemberno(orderBean.getMerId());
		orderinfo.setSecmembername(pojoMember.getMemberName());
		orderinfo.setFronturl(orderBean.getFrontUrl());
		orderinfo.setBackurl(orderBean.getBackUrl());
		orderinfo.setTxntype(orderBean.getTxnType());
		orderinfo.setTxnsubtype(orderBean.getTxnSubType());
		orderinfo.setBiztype(orderBean.getBizType());
		orderinfo.setOrderdesc(orderBean.getOrderDesc());
		orderinfo.setPaytimeout(orderBean.getOrderTimeout());
		orderinfo.setTn(tn);
		orderinfo.setMemberid(orderBean.getMerId());
		orderinfo.setCurrencycode("156");
		orderinfo.setPayerip(orderBean.getCustomerIp());
		orderinfo.setGroupcode(old_txnsLog.getGroupcode());
		txnsLogService.tradeRiskControl(txnsLog.getTxnseqno(),txnsLog.getAccfirmerno(),txnsLog.getAccsecmerno(),txnsLog.getAccmemberid(),txnsLog.getBusicode(),txnsLog.getAmount()+"","1","");
		txnsLogService.saveTxnsLog(txnsLog);
		txnsOrderinfoDAO.saveOrderInfo(orderinfo);
		
		//账务处理
		//退款账务处理
		try {
			//InduGroupMemberBean groupMember = industryGroupMemberService.getGroupMemberByMemberIdAndGroupCode(orderBean.getMemberId(), groupCode);
			//InduGroupMemberBean groupMerch = industryGroupMemberService.getGroupMemberByMemberIdAndGroupCode(orderBean.getMerId(), groupCode);
			TradeInfo tradeInfo = new TradeInfo();
			tradeInfo.setPayMemberId(orderBean.getMemberId());
			tradeInfo.setPayToMemberId(orderBean.getMerId());
			tradeInfo.setAmount(new BigDecimal(orderBean.getTxnAmt()));
			tradeInfo.setCharge(new BigDecimal(txnsLogService.getTxnFee(txnsLog)));
			tradeInfo.setTxnseqno(txnsLog.getTxnseqno());
			tradeInfo.setCoopInstCode(txnsLog.getAccfirmerno());
			tradeInfo.setBusiCode(txnsLog.getBusicode());
			tradeInfo.setCoopInstCode(ConsUtil.getInstance().cons.getZlebank_coopinsti_code());
			tradeInfo.setAccess_coopInstCode(txnsLog.getAccfirmerno());
			//tradeInfo.setIndustry_group_member_tag(groupMember.getUniqueTag());
			accEntryService.accEntryProcess(tradeInfo, EntryEvent.AUDIT_APPLY);
		} catch (AccBussinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalEntryRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AbstractBusiAcctException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		TxnsRefundModel refundOrder = new TxnsRefundModel();
		refundOrder.setRefundorderno(OrderNumber.getInstance().generateRefundOrderNo());
		refundOrder.setOldorderno(orderBean.getOrigOrderId());
		refundOrder.setOldtxnseqno(old_txnsLog.getTxnseqno());
		refundOrder.setMerchno(orderBean.getCoopInstiId());
		refundOrder.setSubmerchno(orderBean.getMerId());
		refundOrder.setMemberid(orderBean.getMemberId());
		refundOrder.setAmount(Long.valueOf(orderBean.getTxnAmt()));
		refundOrder.setOldamount(orderinfo_old.getOrderamt());   
		refundOrder.setRefundtype(orderBean.getRefundType());
		refundOrder.setRefunddesc(orderBean.getOrderDesc()); 
		refundOrder.setReltxnseqno(txnseqno);  
		refundOrder.setTxntime(DateUtil.getCurrentDateTime());
		refundOrder.setStatus("01");
		refundOrder.setRelorderno(orderBean.getOrderId());
		txnsRefundService.saveRefundOrder(refundOrder);
		return tn;
	}

}
