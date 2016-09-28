/* 
 * IndustryAccountTradeServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年9月28日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import java.nio.channels.Channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.acc.bean.enums.AcctStatusType;
import com.zlebank.zplatform.acc.bean.enums.Usage;
import com.zlebank.zplatform.commons.dao.pojo.BusiTypeEnum;
import com.zlebank.zplatform.commons.utils.DateUtil;
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
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.enums.BusinessEnum;
import com.zlebank.zplatform.trade.bean.enums.ChannelEnmu;
import com.zlebank.zplatform.trade.bean.enums.TradeStatFlagEnum;
import com.zlebank.zplatform.trade.bean.gateway.OrderBean;
import com.zlebank.zplatform.trade.bean.gateway.TransferOrderBean;
import com.zlebank.zplatform.trade.dao.ITxnsOrderinfoDAO;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.factory.AccountingAdapterFactory;
import com.zlebank.zplatform.trade.model.TxncodeDefModel;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsOrderinfoModel;
import com.zlebank.zplatform.trade.service.ITxncodeDefService;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.IndustryAccountTradeService;
import com.zlebank.zplatform.trade.service.enums.AccTradeExcepitonEnum;
import com.zlebank.zplatform.trade.utils.OrderNumber;
import com.zlebank.zplatform.trade.utils.UUIDUtil;
import com.zlebank.zplatform.trade.utils.ValidateLocator;

/**
 * 行业专户交易，行业转账，行业提取，行业退款业务实现类
 *
 * @author guojia
 * @version
 * @date 2016年9月28日 上午11:07:21
 * @since 
 */
@Service("industryAccountTradeService")
public class IndustryAccountTradeServiceImpl implements IndustryAccountTradeService{

	private static final Logger logger = LoggerFactory.getLogger(IndustryAccountTradeServiceImpl.class);
	@Autowired
	private ITxnsOrderinfoDAO txnsOrderinfoDAO;
	@Autowired
	private ITxncodeDefService txncodeDefService;
	@Autowired
	private MemberAccountService memberAccountService;
	@Autowired
	private CoopInstiService coopInstiService;
	@Autowired
	private MerchService merchService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private ITxnsLogService txnsLogService;
	/**
	 *
	 * @param orderBean
	 * @return
	 * @throws TradeException 
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public ResultBean transferIndustry(String industryCode,TransferOrderBean orderBean) throws TradeException {
		/**
		 * 1.对转账订单进行数据校验，未通过拒绝交易，并返回错误信息，
		 * 2.订单唯一性检查
		 * 3.订单对外业务代码转换为内部业务代码
		 * 4.行业专户业务检查,非行业专户转账业务，拒绝交易
		 * 5.检查付款方和收款方是否在同一行业内，不在同一行业内拒绝交易,(暂无)
		 * 6.检查收付款方会员的账户状态，付款方账户状态不可以是冻结或者止出，收款方会员账户状态不可以是冻结或者止入
		 * 7.赋值交易订单数据和交易流水数据
		 * 8.账务处理
		 * 9.根据账务处理结果
		 */
		ResultBean resultBean = ValidateLocator.validateBeans(orderBean);
        if(!resultBean.isResultBool()){
        	 throw new TradeException(AccTradeExcepitonEnum.TE00.getErrorCode());
        }
        
        
        TxnsOrderinfoModel orderinfo = txnsOrderinfoDAO.getOrderinfoByOrderNoAndMemberId(orderBean.getOrderId(),orderBean.getFromMerId());
        if(orderinfo!=null){
        	 throw new TradeException(AccTradeExcepitonEnum.TE00.getErrorCode());
        }
        TxncodeDefModel busiModel = txncodeDefService.getBusiCode(orderBean.getTxnType(), orderBean.getTxnSubType(), orderBean.getBizType());
        BusinessEnum businessEnum = BusinessEnum.fromValue(busiModel.getBusicode());
        
        
        if(businessEnum!=BusinessEnum.TRANSFER_INDUSTRY){
        	throw new TradeException(AccTradeExcepitonEnum.TE00.getErrorCode());
        }
        
        
        //缺少行业判断
		
        
        
        try {
			MemberBean member = new MemberBean();
			member.setMemberId(orderBean.getFromMerId());
			MemberAccountBean fromMemberAccount = memberAccountService.queryBalance(null, member, Usage.BASICPAY);
			AcctStatusType acctStatusType = AcctStatusType.fromValue(fromMemberAccount.getStatus());
			if(acctStatusType==AcctStatusType.FREEZE||acctStatusType==AcctStatusType.STOP_OUT){
				throw new TradeException(AccTradeExcepitonEnum.TE00.getErrorCode());
			}
		} catch (DataCheckFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new TradeException(AccTradeExcepitonEnum.TE00.getErrorCode());
		} catch (GetAccountFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new TradeException(AccTradeExcepitonEnum.TE00.getErrorCode());
		}
        try {
			MemberBean member = new MemberBean();
			member.setMemberId(orderBean.getToMerId());
			MemberAccountBean fromMemberAccount = memberAccountService.queryBalance(null, member, Usage.BASICPAY);
			AcctStatusType acctStatusType = AcctStatusType.fromValue(fromMemberAccount.getStatus());
			if(acctStatusType==AcctStatusType.FREEZE||acctStatusType==AcctStatusType.STOP_IN){
				throw new TradeException(AccTradeExcepitonEnum.TE00.getErrorCode());
			}
		} catch (DataCheckFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new TradeException(AccTradeExcepitonEnum.TE00.getErrorCode());
		} catch (GetAccountFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new TradeException(AccTradeExcepitonEnum.TE00.getErrorCode());
		}
        
        
        String tn = OrderNumber.getInstance().generateTN(orderBean.getFromMerId());
        String txnseqno = OrderNumber.getInstance().generateTxnseqno(businessEnum.getBusiCode());
        
        TxnsLogModel txnsLog = new TxnsLogModel();
		//获取付款人的信息
		//如果是企业会员
        PojoMember pojoMember = memberService.getMbmberByMemberId(orderBean.getFromMerId(), null);
		MemberType type= pojoMember.getMemberType();
		if(type.equals(MemberType.ENTERPRISE)){
			PojoMerchDeta member = merchService.getMerchBymemberId(orderBean.getFromMerId());
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
		txnsLog.setTxnseqno(OrderNumber.getInstance().generateTxnseqno(
				txnsLog.getBusicode()));
		txnsLog.setAmount(Long.valueOf(orderBean.getTxnAmt()));
		txnsLog.setAccordno(orderBean.getOrderId());
		txnsLog.setAccfirmerno(orderBean.getCoopInstiId());
		txnsLog.setAcccoopinstino(orderBean.getCoopInstiId());
		//付款方
		txnsLog.setAccsecmerno(orderBean.getFromMerId());
		txnsLog.setAccordcommitime(DateUtil.getCurrentDateTime());
		txnsLog.setTradestatflag(TradeStatFlagEnum.INITIAL.getStatus());// 交易初始状态
		txnsLog.setAccmemberid(orderBean.getToMerId());
		//佣金
		txnsLog.setTradcomm(0L);
		//计算手续费
		txnsLog.setTxnfee(txnsLogService.getTxnFee(txnsLog));
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
		orderinfo.setSecmemberno(orderBean.getFromMerId());
		orderinfo.setSecmembername(pojoMember.getMemberName());
		orderinfo.setFronturl(orderBean.getFrontUrl());
		orderinfo.setBackurl(orderBean.getBackUrl());
		orderinfo.setTxntype(orderBean.getTxnType());
		orderinfo.setTxnsubtype(orderBean.getTxnSubType());
		orderinfo.setBiztype(orderBean.getBizType());
		orderinfo.setOrderdesc(orderBean.getOrderDesc());
		orderinfo.setPaytimeout(orderBean.getOrderTimeout());
		orderinfo.setTn(tn);
		orderinfo.setMemberid(orderBean.getToMerId());
		orderinfo.setCurrencycode("156");
		orderinfo.setPayerip(orderBean.getCustomerIp());
		txnsLog.setPaytype("08"); //支付类型（01：快捷，02：网银，03：账户,07：退款，08：财务类）
        txnsLog.setPayordno(OrderNumber.getInstance().generateAppOrderNo());//支付定单号
      	txnsLog.setPayinst(ChannelEnmu.INNERCHANNEL.getChnlcode()); //渠道号
        txnsLog.setPayfirmerno(orderBean.getFromMerId());//支付一级商户号-个人会员
        txnsLog.setPaysecmerno(orderBean.getToMerId());//支付二级商户号
        txnsLog.setPayordcomtime(DateUtil.getCurrentDateTime());//支付定单提交时间
        
        
		ResultBean accountResultBean = AccountingAdapterFactory.getInstance().getAccounting(BusiTypeEnum.fromValue(txnsLog.getBusitype())).accountedFor(txnsLog.getTxnseqno());
		accountResultBean.setResultObj(tn);
		if(accountResultBean.isResultBool()){
        	orderinfo.setStatus("00");
        	//更新交易流水表交易位
	        txnsLog.setRelate("01000000");
	        txnsLog.setTradetxnflag("01000000");
	        txnsLog.setTradestatflag(TradeStatFlagEnum.FINISH_ACCOUNTING.getStatus());
	        txnsLog.setRetdatetime(DateUtil.getCurrentDateTime());
	        txnsLog.setAccbusicode(businessEnum.getBusiCode());
	        txnsLog.setTradeseltxn(UUIDUtil.uuid());
        }else{
        	txnsLog.setTradetxnflag(TradeStatFlagEnum.FINISH_SUCCESS.getStatus());
        	orderinfo.setStatus("03");
        }
		txnsOrderinfoDAO.saveOrderInfo(orderinfo);
		txnsLogService.saveTxnsLog(txnsLog);
		return accountResultBean;
	}

	/**
	 *
	 * @param orderBean
	 * @return
	 */
	@Override
	public ResultBean extractIndustry(String industryCode,OrderBean orderBean) {
		/**
		 * 
		 */
		return null;
	}

	/**
	 *
	 * @param orderBean
	 * @return
	 */
	@Override
	public String refundIndustry(String industryCode,OrderBean orderBean) {
		/**
		 * 
		 */
		return null;
	}

	
}
