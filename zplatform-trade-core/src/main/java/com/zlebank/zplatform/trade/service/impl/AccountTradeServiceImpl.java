package com.zlebank.zplatform.trade.service.impl;

import java.math.BigDecimal;
import java.nio.channels.Channel;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.acc.bean.BusiAcctQuery;
import com.zlebank.zplatform.acc.bean.enums.AcctStatusType;
import com.zlebank.zplatform.acc.bean.enums.BusiType;
import com.zlebank.zplatform.acc.bean.enums.Usage;
import com.zlebank.zplatform.acc.service.AccountQueryService;
import com.zlebank.zplatform.commons.dao.pojo.AccStatusEnum;
import com.zlebank.zplatform.commons.dao.pojo.BusiTypeEnum;
import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.member.bean.EnterpriseBean;
import com.zlebank.zplatform.member.bean.MemberAccountBean;
import com.zlebank.zplatform.member.bean.MemberBean;
import com.zlebank.zplatform.member.bean.enums.MemberStatusType;
import com.zlebank.zplatform.member.bean.enums.MemberType;
import com.zlebank.zplatform.member.exception.DataCheckFailedException;
import com.zlebank.zplatform.member.exception.GetAccountFailedException;
import com.zlebank.zplatform.member.pojo.PojoMember;
import com.zlebank.zplatform.member.pojo.PojoMerchDeta;
import com.zlebank.zplatform.member.service.CoopInstiService;
import com.zlebank.zplatform.member.service.EnterpriseService;
import com.zlebank.zplatform.member.service.MemberAccountService;
import com.zlebank.zplatform.member.service.MemberService;
import com.zlebank.zplatform.member.service.MerchService;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.enums.BusinessEnum;
import com.zlebank.zplatform.trade.bean.enums.ChannelEnmu;
import com.zlebank.zplatform.trade.bean.enums.TradeStatFlagEnum;
import com.zlebank.zplatform.trade.bean.gateway.BailRechargeOrderBean;
import com.zlebank.zplatform.trade.bean.gateway.BailWithdrawOrderBean;
import com.zlebank.zplatform.trade.bean.gateway.QueryAccBean;
import com.zlebank.zplatform.trade.bean.gateway.QueryAccResultBean;
import com.zlebank.zplatform.trade.bean.gateway.TransferOrderBean;
import com.zlebank.zplatform.trade.dao.ITxnsOrderinfoDAO;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.factory.AccountingAdapterFactory;
import com.zlebank.zplatform.trade.model.TxncodeDefModel;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsOrderinfoModel;
import com.zlebank.zplatform.trade.service.IAccoutTradeService;
import com.zlebank.zplatform.trade.service.IGateWayService;
import com.zlebank.zplatform.trade.service.ITxncodeDefService;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.TradeNotifyService;
import com.zlebank.zplatform.trade.service.base.BaseServiceImpl;
import com.zlebank.zplatform.trade.utils.OrderNumber;
import com.zlebank.zplatform.trade.utils.UUIDUtil;
import com.zlebank.zplatform.trade.utils.ValidateLocator;

@Service("accountTradeService")
public class AccountTradeServiceImpl extends
BaseServiceImpl<TxnsOrderinfoModel, Long>implements IAccoutTradeService {
	private static final Log log = LogFactory.getLog(AccountTradeServiceImpl.class);
	@Autowired
	private ITxncodeDefService txncodeDefService;
	@Autowired
	private MerchService merchService;
	@Autowired
	private IGateWayService gateWayService;
	@Autowired
	private CoopInstiService coopInstiService;
	@Autowired
	private ITxnsOrderinfoDAO txnsOrderinfoDAO;
	@Autowired
	private ITxnsLogService txnsLogService;
    @Autowired
	private MemberService memberService;
    @Autowired
    private AccountQueryService accountQueryService;
    @Autowired
    private  MemberAccountService memberAccountService;
    @Autowired
	private TradeNotifyService tradeNotifyService;
    @Autowired
    private EnterpriseService  enterpriseService;
  
    @Override
	@Transactional
	public QueryAccResultBean queryMemberBalance(QueryAccBean query) throws TradeException {
    	QueryAccResultBean result =new QueryAccResultBean();
    	ResultBean valBean = ValidateLocator.validateBeans(query);
    	if(!valBean.isResultBool()){
    		throw  new TradeException("QA00");
    	} 
    	//判断双方会员的基本账户状态是否正常 
		PojoMember member = this.memberService.getMbmberByMemberId(query.getMemberId(), null);
    	if(member==null){
    		return result;
    	}
    	Usage usage=Usage.fromValue(query.getAccoutType());
    	MemberBean queryBean = new MemberBean();
    	queryBean.setMemberId(query.getMemberId());
    	try {
			MemberAccountBean accResult= memberAccountService.queryBalance(member.getMemberType(), queryBean, usage);
			result.setAvaiableBalance(accResult.getTotalBalance().subtract(accResult.getFrozenBalance())+"");
			result.setBalance(accResult.getTotalBalance()+"");
			result.setFrozenAmount(accResult.getFrozenBalance()+"");
			result.setStatus(accResult.getStatus());
		} catch (DataCheckFailedException | GetAccountFailedException e) {
			e.printStackTrace();
			log.error("查询余额失败:"+e.getMessage());
			throw new TradeException("QA01");
		}
		return result;
	}
	@Override
	@Transactional
	public String transfer(TransferOrderBean order) throws TradeException {
		 log.info("转账处理开始");
         log.info(JSONObject.fromObject(order));
         ResultBean resultBean = ValidateLocator.validateBeans(order);
         if(!resultBean.isResultBool()){
        	 throw  new TradeException("TE00");
         }
		/*******保存订单的日志*******/
		//基本信息
		//判断订单是否存在
		List<TxnsOrderinfoModel> orderinfoList = this.getOrderList(
				order.getOrderId(), order.getTxnTime(),order.getFromMerId());
		if (orderinfoList.size() == 1) {
			TxnsLogModel txnsLog = txnsLogService
					.getTxnsLogByTxnseqno(orderinfoList.get(0)
							.getRelatetradetxn());
			String orign_memberId = txnsLog.getAccmemberid();
			String new_memberId = order.getToMerId();
			if (!orign_memberId.equals(new_memberId)) {
				throw new TradeException("TE01");//会员信息有误
			}
			return orderinfoList.get(0).getRelatetradetxn();
		}
		//1.校验交易类型是否定
		TxncodeDefModel busiModel = txncodeDefService.getBusiCode(
				order.getTxnType(), order.getTxnSubType(), order.getBizType());
		if(busiModel==null){
			throw new TradeException("TE02");//交易类型有误
		}
		//转账业务
		if(!busiModel.getBusitype().equals(BusiType.TRANSFER.getCode())){
			throw new TradeException("TE03");//只有转账业务才能处理
		}
		//判断双方会员的基本账户状态是否正常 
		PojoMember fromMember =this.getMemberInfo(order.getFromMerId());
		if(fromMember == null){
			throw new TradeException("TE04");//付款方不存在
		}
		PojoMember toMember =this.getMemberInfo(order.getToMerId());
		if(toMember ==null){
			throw new TradeException("TE05");//收款方不存在
		}
		String  accinfo =this.checkMemberAccInfo(order.getFromMerId(), order.getTxnAmt());
		if(StringUtil.isNotEmpty(accinfo)){
			if(accinfo.equals("account")){
				throw new TradeException("TE06");//付款方未开通基本账户
			}else if(accinfo.equals("balance")){
				throw new TradeException("TE07");//余额不足
			}
		}
		String  toAccinfo =this.checkMemberAccInfo(order.getToMerId(), null);
		if(StringUtil.isNotEmpty(toAccinfo)){
			if(toAccinfo.equals("account")){
				throw new TradeException("TE08");//收款方未开通基本账户
			}
		}
		/*******保存订单的日志*******/
		TxnsLogModel txnsLog = new TxnsLogModel();
		//获取付款人的信息
		//如果是企业会员
		MemberType type= fromMember.getMemberType();
		if(type.equals(MemberType.ENTERPRISE)){
			PojoMerchDeta member = merchService.getMerchBymemberId(order.getFromMerId());
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
			txnsLog.setRiskver(getDefaultVerInfo(order.getCoopInstiId(),
					busiModel.getBusicode(), 13));
			txnsLog.setSplitver(getDefaultVerInfo(order.getCoopInstiId(),
					busiModel.getBusicode(), 12));
			txnsLog.setFeever(getDefaultVerInfo(order.getCoopInstiId(),
					busiModel.getBusicode(), 11));
			txnsLog.setPrdtver(getDefaultVerInfo(order.getCoopInstiId(),
					busiModel.getBusicode(), 10));
			txnsLog.setRoutver(getDefaultVerInfo(order.getCoopInstiId(),
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
		txnsLog.setAmount(Long.valueOf(order.getTxnAmt()));
		txnsLog.setAccordno(order.getOrderId());
		txnsLog.setAccfirmerno(order.getCoopInstiId());
		txnsLog.setAcccoopinstino(order.getCoopInstiId());
		
		//付款方
		txnsLog.setAccsecmerno(order.getFromMerId());
		txnsLog.setAccordcommitime(DateUtil.getCurrentDateTime());
		txnsLog.setTradestatflag(TradeStatFlagEnum.INITIAL.getStatus());// 交易初始状态
		txnsLog.setAccmemberid(order.getToMerId());
		//佣金
		txnsLog.setTradcomm(0L);
		//计算手续费
		txnsLog.setTxnfee(this.getTxnFee(txnsLog));
		
		//订单表
		// 记录订单流水
		TxnsOrderinfoModel orderinfo = new TxnsOrderinfoModel();
		orderinfo.setId(1L);
		orderinfo.setOrderno(order.getOrderId());// 商户提交的订单号
		orderinfo.setOrderamt(Long.valueOf(order.getTxnAmt()));
		orderinfo.setOrderfee(txnsLog.getTxnfee());
		orderinfo.setOrdercommitime(order.getTxnTime());
		orderinfo.setRelatetradetxn(txnsLog.getTxnseqno());// 关联的交易流水表中的交易序列号
		//合作机构
		orderinfo.setFirmemberno(order.getCoopInstiId());
		orderinfo.setFirmembername(coopInstiService.getInstiByInstiCode(
				order.getCoopInstiId()).getInstiName());
		//二级商户
		orderinfo.setSecmemberno(order.getFromMerId());
		orderinfo.setSecmembername(fromMember.getMemberName());
		orderinfo.setFronturl(order.getFrontUrl());
		orderinfo.setBackurl(order.getBackUrl());

		orderinfo.setTxntype(order.getTxnType());
		orderinfo.setTxnsubtype(order.getTxnSubType());
		orderinfo.setBiztype(order.getBizType());
		orderinfo.setOrderdesc(order.getOrderDesc());
		orderinfo.setPaytimeout(order.getOrderTimeout());
		orderinfo.setTn(OrderNumber.getInstance().generateTN(
				order.getCoopInstiId()));
		orderinfo.setMemberid(order.getToMerId());
		orderinfo.setCurrencycode("156");
		orderinfo.setPayerip(order.getCustomerIp());
		txnsLogService.saveTxnsLog(txnsLog);
		saveOrderInfo(orderinfo);
		log.info("转账订单保存结束"+txnsLog.getTxnseqno());
		/***********账户账务**********************/
		//保存支付流水
		this.updatePayInfo(txnsLog.getTxnseqno()+"",txnsLog.getAccsecmerno(),
				txnsLog.getAccmemberid(),null,BusinessEnum.TRANSFER.getBusiCode());
		//处理账务
		try {
			ResultBean accountResultBean =
					AccountingAdapterFactory.getInstance()
					.getAccounting(BusiTypeEnum.fromValue(txnsLog.getBusitype()))
					.accountedFor(txnsLog.getTxnseqno());
			 if(accountResultBean.isResultBool()){
	            txnsOrderinfoDAO.updateOrderToSuccess(txnsLog.getTxnseqno());
	        }else{
	            txnsOrderinfoDAO.updateOrderToFail(txnsLog.getTxnseqno());
	        }
		} catch (Exception e) {
			e.printStackTrace();
			 txnsOrderinfoDAO.updateOrderToFail(txnsLog.getTxnseqno());
		}
		log.info("转账账务处理结束"+txnsLog.getTxnseqno());
		 /**账务处理结束 **/
		/**异步通知处理开始  **/
		if(StringUtil.isNotEmpty(orderinfo.getBackurl()) && fromMember.getMemberType().equals(MemberType.ENTERPRISE) ){
			 tradeNotifyService.notifyExt(txnsLog.getTxnseqno());
		}
        /**异步通知处理结束 **/
		return txnsLog.getTxnseqno();
	}
	
	@Override
	@Transactional
	public String bailAccountRecharge(BailRechargeOrderBean order) throws TradeException {
		log.info("保证金充值处理开始");
        log.info(JSONObject.fromObject(order));
		/*******保存订单的日志*******/
        ResultBean resultBean = ValidateLocator.validateBeans(order);
        if(!resultBean.isResultBool()){
        	 throw  new TradeException("BC00");
        }
		//基本信息
		//判断订单是否存在
		List<TxnsOrderinfoModel> orderinfoList = this.getOrderList(
				order.getOrderId(), order.getTxnTime(),order.getFromMerId());
		if (orderinfoList.size() == 1) {
			TxnsLogModel txnsLog = txnsLogService
					.getTxnsLogByTxnseqno(orderinfoList.get(0)
							.getRelatetradetxn());
			String orign_memberId = txnsLog.getAccmemberid();
			String new_memberId = order.getFromMerId();
			if (!orign_memberId.equals(new_memberId)) {
				throw new TradeException("BC01");//会员信息有误
			}
			return orderinfoList.get(0).getRelatetradetxn();
		}
		//1.校验交易类型是否定
		TxncodeDefModel busiModel = txncodeDefService.getBusiCode(
				order.getTxnType(), order.getTxnSubType(), order.getBizType());
		if(busiModel==null){
			throw new TradeException("BC02");//交易类型有误
		}
		//保证金业务
		if(!busiModel.getBusicode().equals(BusinessEnum.BAIL_RECHARGE.getBusiCode())){
			throw new TradeException("BC04");//只有充值业务才能处理
		}
		//判断双方会员的基本账户状态是否正常 
		PojoMember fromMember =this.getMemberInfo(order.getFromMerId());
		if(fromMember == null){
			throw new TradeException("BC05");//付款方不存在
		}
		String  toAccinfo =this.checkMarginAccInfo(order.getFromMerId(), null);
		if(StringUtil.isNotEmpty(toAccinfo)){
			if(toAccinfo.equals("account")){
				throw new TradeException("BC06");//未开通保证金账户
			}
		}
		//校验法人信息
		EnterpriseBean enterprise =this.enterpriseService.getEnterpriseByMemberId(order.getFromMerId());
		if(enterprise==null){
			throw new TradeException("BC07");//企业信息不存在
		}
		if(!enterprise.getCorporation().equals(order.getCorporateName())||!enterprise.getCorpNo().equals(order.getCorporateCertId())){
			throw new TradeException("BC08");//法人信息有误
		}
		
		/*******保存订单的日志*******/
		TxnsLogModel txnsLog = new TxnsLogModel();
		//获取付款人的信息
		//如果是企业会员
		MemberType type= fromMember.getMemberType();
		if(type.equals(MemberType.ENTERPRISE)){
			PojoMerchDeta member = merchService.getMerchBymemberId(order.getFromMerId());
			txnsLog.setRiskver(member.getRiskVer());
			txnsLog.setSplitver(member.getSpiltVer());
			txnsLog.setFeever(member.getFeeVer());
			txnsLog.setPrdtver(member.getPrdtVer());
			txnsLog.setRoutver(member.getRoutVer());
			txnsLog.setAccsettledate(DateUtil.getSettleDate(Integer
					.valueOf(member.getSetlCycle().toString())));
		//如果是普通会员
		}else{
			throw new TradeException("BC09");//只有企业会员才有保证金账户
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
		txnsLog.setAmount(Long.valueOf(order.getTxnAmt()));
		txnsLog.setAccordno(order.getOrderId());
		txnsLog.setAccfirmerno(order.getCoopInstiId());
		txnsLog.setAcccoopinstino(order.getCoopInstiId());
		//付款方
		txnsLog.setAccsecmerno(order.getFromMerId());
		txnsLog.setAccordcommitime(DateUtil.getCurrentDateTime());
		txnsLog.setTradestatflag(TradeStatFlagEnum.INITIAL.getStatus());// 交易初始状态
		txnsLog.setAccmemberid(order.getFromMerId());
		//转出账号
		txnsLog.setPan(order.getAcctNo());
		//转出账户名
		txnsLog.setPanName(order.getAccName());
		//人行号
		txnsLog.setCardinstino(order.getBankAccNo());
		//佣金
		txnsLog.setTradcomm(0L);
		//计算手续费
		txnsLog.setTxnfee(this.getTxnFee(txnsLog));
		//订单表
		// 记录订单流水
		TxnsOrderinfoModel orderinfo = new TxnsOrderinfoModel();
		orderinfo.setId(1L);
		orderinfo.setOrderno(order.getOrderId());// 商户提交的订单号
		orderinfo.setOrderamt(Long.valueOf(order.getTxnAmt()));
		orderinfo.setOrderfee(txnsLog.getTxnfee());
		orderinfo.setOrdercommitime(order.getTxnTime());
		orderinfo.setRelatetradetxn(txnsLog.getTxnseqno());// 关联的交易流水表中的交易序列号
		//合作机构
		orderinfo.setFirmemberno(order.getCoopInstiId());
		orderinfo.setFirmembername(coopInstiService.getInstiByInstiCode(
				order.getCoopInstiId()).getInstiName());
		//二级商户
		orderinfo.setSecmemberno(order.getFromMerId());
		orderinfo.setSecmembername(fromMember.getMemberName());
		orderinfo.setFronturl(order.getFrontUrl());
		orderinfo.setBackurl(order.getBackUrl());
		orderinfo.setTxntype(order.getTxnType());
		orderinfo.setTxnsubtype(order.getTxnSubType());
		orderinfo.setBiztype(order.getBizType());
		orderinfo.setOrderdesc(order.getOrderDesc());
		orderinfo.setPaytimeout(order.getOrderTimeout());
		orderinfo.setTn(OrderNumber.getInstance().generateTN(
				order.getCoopInstiId()));
		orderinfo.setMemberid(order.getFromMerId());
		orderinfo.setCurrencycode("156");
		txnsLogService.saveTxnsLog(txnsLog);
		saveOrderInfo(orderinfo);
		log.info("保证金充值订单保存结束"+txnsLog.getTxnseqno());
		/***************调渠道对对公账户进行充值******************/
		//TODO调渠道对对公账户进行充值
		
		//账务处理
		/***********账户账务**********************/
		//保存支付流水
		this.updatePayInfo(txnsLog.getTxnseqno()+"",txnsLog.getAccsecmerno(), 
				txnsLog.getAccmemberid(),ChannelEnmu.CMBCWITHHOLDING.getChnlcode(),
				BusinessEnum.BAIL_RECHARGE.getBusiCode());
		//处理账务
		try {
			ResultBean accountResultBean =
					AccountingAdapterFactory.getInstance()
					.getAccounting(BusiTypeEnum.fromValue(txnsLog.getBusitype()))
					.accountedFor(txnsLog.getTxnseqno());
			 if(accountResultBean.isResultBool()){
	 			this.updateAccountResult(txnsLog, "0000", "交易成功","保证金充值账务成功");
	            txnsOrderinfoDAO.updateOrderToSuccess(txnsLog.getTxnseqno());
	        }else{
	        	this.updateAccountResult(txnsLog, "0099", resultBean.getErrMsg(),"保证金充值账务失败");
	            txnsOrderinfoDAO.updateOrderToFail(txnsLog.getTxnseqno());
	        }
		} catch (Exception e) {
			e.printStackTrace();
			 txnsOrderinfoDAO.updateOrderToFail(txnsLog.getTxnseqno());
		}
        
         //支付定单完成时间
         this.txnsLogService.updateTxnsLog(txnsLog);
		log.info("保证金充值账务处理结束"+txnsLog.getTxnseqno());
		/***********异步通知**********************/
		/**异步通知处理开始  **/
		if(StringUtil.isNotEmpty(orderinfo.getBackurl())){
			 tradeNotifyService.notifyExt(txnsLog.getTxnseqno());
		}
        /**异步通知处理结束 **/
		return txnsLog.getTxnseqno();
	}

	public void  updateAccountResult(TxnsLogModel txnsLog,String retCode, String retInfo, String appinfo){
		 txnsLog.setPayordfintime(DateUtil.getCurrentDateTime());
         txnsLog.setAccordfintime(DateUtil.getCurrentDateTime());
         txnsLog.setPayretcode(retCode);
         txnsLog.setPayretinfo(retInfo);
         txnsLog.setAppordcommitime(DateUtil.getCurrentDateTime());
         txnsLog.setAppinst("000000000000");//没实际意义，可以为空
         if("0000".equals(retCode)){
        	  txnsLog.setApporderinfo(appinfo);
              txnsLog.setApporderstatus(AccStatusEnum.Finish.getCode());
         }else{
         	txnsLog.setApporderinfo(retInfo);
             txnsLog.setApporderstatus(AccStatusEnum.AccountingFail.getCode());
         }
         txnsLog.setAppordfintime(DateUtil.getCurrentDateTime());
         txnsLog.setRetcode(retCode);
         txnsLog.setRetinfo(retInfo);
	}
	

	@Override
	@Transactional
	public String bailAccountWithdraw(BailWithdrawOrderBean order) throws TradeException {
		log.info("保证金提取处理开始");
        log.info(JSONObject.fromObject(order));
		/*******保存订单的日志*******/
        ResultBean resultBean = ValidateLocator.validateBeans(order);
        if(!resultBean.isResultBool()){
        	 throw  new TradeException("BW00");
        }
		//基本信息
		//判断订单是否存在
		List<TxnsOrderinfoModel> orderinfoList = this.getOrderList(
				order.getOrderId(), order.getTxnTime(),order.getFromMerId());
		if (orderinfoList.size() == 1) {
			TxnsLogModel txnsLog = txnsLogService
					.getTxnsLogByTxnseqno(orderinfoList.get(0)
							.getRelatetradetxn());
			String orign_memberId = txnsLog.getAccmemberid();
			String new_memberId = order.getFromMerId();
			if (!orign_memberId.equals(new_memberId)) {
				throw new TradeException("BW01");//会员信息有误
			}
			return orderinfoList.get(0).getRelatetradetxn();
		}
		//1.校验交易类型是否定
		TxncodeDefModel busiModel = txncodeDefService.getBusiCode(
				order.getTxnType(), order.getTxnSubType(), order.getBizType());
		if(busiModel==null){
			throw new TradeException("BW02");//交易类型有误
		}
		//保证金业务
		if(!busiModel.getBusicode().equals(BusinessEnum.BAIL_WITHDRAWALS.getBusiCode())){
			throw new TradeException("BW03");//保证金业务
		}
		//判断双方会员的基本账户状态是否正常 
		PojoMember fromMember =this.getMemberInfo(order.getFromMerId());
		if(fromMember == null){
			throw new TradeException("BW04");//付款方不存在
		}
		//检查基本账户
		String basicAcc = this.checkMemberAccInfo(order.getFromMerId(), null);
		if(StringUtil.isNotEmpty(basicAcc)){
			if(basicAcc.equals("account")){
				throw new TradeException("BW05");//请检查资金账户是否正常 
			}
		}
		//检查保证金账户
		String  toAccinfo =this.checkMarginAccInfo(order.getFromMerId(), order.getTxnAmt());
		if(StringUtil.isNotEmpty(toAccinfo)){
			if(toAccinfo.equals("account")){
				throw new TradeException("BW06");//请检查资金账户是否正常 
			}else if(toAccinfo.equals("banlance")){
				throw new TradeException("BW07");//提取保证金额不足
			}
		}
		/*******保存订单的日志*******/
		TxnsLogModel txnsLog = new TxnsLogModel();
		//获取付款人的信息
		//如果是企业会员
		MemberType type= fromMember.getMemberType();
		if(type.equals(MemberType.ENTERPRISE)){
			PojoMerchDeta member = merchService.getMerchBymemberId(order.getFromMerId());
			txnsLog.setRiskver(member.getRiskVer());
			txnsLog.setSplitver(member.getSpiltVer());
			txnsLog.setFeever(member.getFeeVer());
			txnsLog.setPrdtver(member.getPrdtVer());
			txnsLog.setRoutver(member.getRoutVer());
			txnsLog.setAccsettledate(DateUtil.getSettleDate(Integer
					.valueOf(member.getSetlCycle().toString())));
		//如果是普通会员
		}else{
			throw new TradeException("BW08");//此业务只允许是企业会员
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
		txnsLog.setAmount(Long.valueOf(order.getTxnAmt()));
		txnsLog.setAccordno(order.getOrderId());
		txnsLog.setAccfirmerno(order.getCoopInstiId());
		txnsLog.setAcccoopinstino(order.getCoopInstiId());
		//付款方
		txnsLog.setAccsecmerno(order.getFromMerId());
		txnsLog.setAccordcommitime(DateUtil.getCurrentDateTime());
		txnsLog.setTradestatflag(TradeStatFlagEnum.INITIAL.getStatus());// 交易初始状态
		txnsLog.setAccmemberid(order.getFromMerId());
		//佣金
		txnsLog.setTradcomm(0L);
		//计算手续费
		txnsLog.setTxnfee(this.getTxnFee(txnsLog));
		//订单表
		// 记录订单流水
		TxnsOrderinfoModel orderinfo = new TxnsOrderinfoModel();
		orderinfo.setId(1L);
		orderinfo.setOrderno(order.getOrderId());// 商户提交的订单号
		orderinfo.setOrderamt(Long.valueOf(order.getTxnAmt()));
		orderinfo.setOrderfee(txnsLog.getTxnfee());
		orderinfo.setOrdercommitime(order.getTxnTime());
		orderinfo.setRelatetradetxn(txnsLog.getTxnseqno());// 关联的交易流水表中的交易序列号
		//合作机构
		orderinfo.setFirmemberno(order.getCoopInstiId());
		orderinfo.setFirmembername(coopInstiService.getInstiByInstiCode(
				order.getCoopInstiId()).getInstiName());
		//二级商户
		orderinfo.setSecmemberno(order.getFromMerId());
		orderinfo.setSecmembername(fromMember.getMemberName());
		orderinfo.setFronturl(order.getFrontUrl());
		orderinfo.setBackurl(order.getBackUrl());
		
		orderinfo.setTxntype(order.getTxnType());
		orderinfo.setTxnsubtype(order.getTxnSubType());
		orderinfo.setBiztype(order.getBizType());
		orderinfo.setOrderdesc(order.getOrderDesc());
		orderinfo.setPaytimeout(order.getOrderTimeout());
		orderinfo.setTn(OrderNumber.getInstance().generateTN(
				order.getCoopInstiId()));
		orderinfo.setMemberid(order.getFromMerId());
		orderinfo.setCurrencycode("156");
		txnsLogService.saveTxnsLog(txnsLog);
		saveOrderInfo(orderinfo);
		log.info("保证金提取订单保存结束"+txnsLog.getTxnseqno());
		//账务处理
		/***********账户账务**********************/
		//保存支付流水
		this.updatePayInfo(txnsLog.getTxnseqno()+"",txnsLog.getAccsecmerno(),
				txnsLog.getAccmemberid(),null,BusinessEnum.BAIL_WITHDRAWALS.getBusiCode());
		//处理账务
		try {
			ResultBean accountResultBean =
					AccountingAdapterFactory.getInstance()
					.getAccounting(BusiTypeEnum.fromValue(txnsLog.getBusitype()))
					.accountedFor(txnsLog.getTxnseqno());
			 if(accountResultBean.isResultBool()){
				 this.updateAccountResult(txnsLog, "0000", "交易成功","保证金提取账务成功");
	            txnsOrderinfoDAO.updateOrderToSuccess(txnsLog.getTxnseqno());
	        }else{
	        	this.updateAccountResult(txnsLog, "0099", resultBean.getErrMsg(),"保证金提取账务失败");
	            txnsOrderinfoDAO.updateOrderToFail(txnsLog.getTxnseqno());
	        }
		} catch (Exception e) {
			e.printStackTrace();
			 txnsOrderinfoDAO.updateOrderToFail(txnsLog.getTxnseqno());
		}
		log.info("保证金提取账务处理结束"+txnsLog.getTxnseqno());
		/***********异步通知**********************/
		/**异步通知处理开始  **/
		if(StringUtil.isNotEmpty(orderinfo.getBackurl())){
			 tradeNotifyService.notifyExt(txnsLog.getTxnseqno());
		}
        /**异步通知处理结束 **/
		return txnsLog.getTxnseqno();
	}
	
	
	
	/***
	 * 记录支付流水
	 * @param txnseqno
	 * @param fromMember
	 * @param toMember
	 * @return
	 */
	private String updatePayInfo(String txnseqno, String fromMember, String toMember,String channelId,String busicode) {
		TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
        txnsLog.setPaytype("08"); //支付类型（01：快捷，02：网银，03：账户,07：退款，08：财务类）
        txnsLog.setPayordno(OrderNumber.getInstance().generateAppOrderNo());//支付定单号
         //渠道号
      	txnsLog.setPayinst(channelId);
        txnsLog.setPayfirmerno(fromMember);//支付一级商户号-个人会员
        txnsLog.setPaysecmerno(toMember);//支付二级商户号
        txnsLog.setPayordcomtime(DateUtil.getCurrentDateTime());//支付定单提交时间
        //更新交易流水表交易位
        txnsLog.setRelate("01000000");
        txnsLog.setTradetxnflag("01000000");
        txnsLog.setTradestatflag("00000001");
        txnsLog.setRetdatetime(DateUtil.getCurrentDateTime());
        txnsLog.setAccbusicode(busicode);
        txnsLog.setTradeseltxn(UUIDUtil.uuid());
        //支付定单完成时间
        txnsLogService.update(txnsLog);
        return txnsLog.getPayordno();
	}
	/***
	 * 查询订单
	 * @param orderno
	 * @param ordercommitime
	 * @param memberno
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TxnsOrderinfoModel> getOrderList(String orderno,
			String ordercommitime, String memberno) {
		return (List<TxnsOrderinfoModel>) super
				.queryByHQL(
						"from TxnsOrderinfoModel where orderno = ? and ordercommitime = ? and  secmemberno=?",
						new Object[] { orderno, ordercommitime, memberno });
	}
	/***
	 * 校验会员信息
	 * @param memberId
	 * @return
	 */
	public PojoMember getMemberInfo(String memberId){
		//判断双方会员的基本账户状态是否正常 
		PojoMember fromMember = this.memberService.getMbmberByMemberId(memberId, null);
		//付款会员ID不正确
		if(fromMember==null){
			return null;
		}
		//只有正常状态才能处理
		if(!fromMember.getStatus().equals(MemberStatusType.NORMAL)){
			return null;
		}
		return fromMember;
	}
	/****
	 * 校验账户信息
	 * @param memberId
	 * @param txnamt
	 * @return
	 */
	public String checkMemberAccInfo(String memberId, String txnamt){
		//获得资金账户状态
        List<BusiAcctQuery> busiAcctList = this.accountQueryService
                .getAllBusiByMId(memberId);
        BusiAcctQuery basicFund = null;
        for (BusiAcctQuery busiAcct : busiAcctList) {
            if (busiAcct.getUsage() == Usage.BASICPAY) {
                basicFund = busiAcct;
                break;
            }
        }
        if (basicFund == null
        	|| !basicFund.getStatus().equals(AcctStatusType.NORMAL)) {// 资金账户不存在
        	return "account";
        }
        BusiAcctQuery memberAcct = accountQueryService
                .getMemberQueryByID(basicFund.getBusiAcctCode());
       if(memberAcct==null){
    	   return "account";
       }
       if(StringUtil.isNotEmpty(txnamt)){
    	   //账户余额
    	  BigDecimal amount = memberAcct.getBalance().getAmount();
    	  //消费金额
    	  BigDecimal txnamtBig = new BigDecimal(txnamt);
    	  if(amount.compareTo(txnamtBig)<0){
    		   return "banlance";
    	  }
       }
       
       return "true";
	}
	
	/****
	 * 校验保证金账户
	 * @param memberId
	 * @param txnamt
	 * @return
	 */
	public String checkMarginAccInfo(String memberId, String txnamt){
		//获得保证金账户状态
        List<BusiAcctQuery> busiAcctList = this.accountQueryService
                .getAllBusiByMId(memberId);
        BusiAcctQuery basicFund = null;
        for (BusiAcctQuery busiAcct : busiAcctList) {
            if (busiAcct.getUsage() == Usage.BAIL) {
                basicFund = busiAcct;
                break;
            }
        }
        if (basicFund == null
        	|| !basicFund.getStatus().equals(AcctStatusType.NORMAL)) {// 账户状态不存在
        	return "account";
        }
        BusiAcctQuery memberAcct = accountQueryService
                .getMemberQueryByID(basicFund.getBusiAcctCode());
       if(memberAcct==null){
    	   return "account";
       }
       if(StringUtil.isNotEmpty(txnamt)){
    	   //账户余额
    	  BigDecimal amount = memberAcct.getBalance().getAmount();
    	  //消费金额
    	  BigDecimal txnamtBig = new BigDecimal(txnamt);
    	  if(amount.compareTo(txnamtBig)<0){
    		   return "banlance";
    	  }
       }
       
       return "true";
	}
	/***
	 * 保存认单信息
	 * @param orderinfo
	 */
	private void saveOrderInfo(TxnsOrderinfoModel orderinfo) {
		super.save(orderinfo);
		
	}
	/***
	 * 获得版本信息
	 * @param coopInstiId
	 * @param busicode
	 * @param i
	 * @return
	 * @throws TradeException 
	 */
	@SuppressWarnings("unchecked")
	private String getDefaultVerInfo(String instiCode, String busicode,int verType) throws TradeException {
		List<Map<String, Object>> resultList = (List<Map<String, Object>>) super
				.queryBySQL(
						"select COOP_INSTI_CODE,BUSI_CODE,VER_TYPE,VER_VALUE from T_NONMER_DEFAULT_CONFIG where COOP_INSTI_CODE=? and BUSI_CODE=? and VER_TYPE=?",
						new Object[] { instiCode, busicode, verType + "" });
		if (resultList.size() > 0) {
			Map<String, Object> valueMap = resultList.get(0);
			return valueMap.get("VER_VALUE").toString();
		}
		throw new TradeException("GW03");
	}
	/***
	 * 获得手续费
	 * @param txnsLog
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Long getTxnFee(TxnsLogModel txnsLog) {
		// 交易序列号，扣率版本，业务类型，交易金额，会员号，原交易序列号，卡类型
		List<Map<String, Object>> feeList = (List<Map<String, Object>>) super
				.queryBySQL(
						"select FNC_GETFEES(?,?,?,?,?,?,?) as fee from dual",
						new Object[] { txnsLog.getTxnseqno(),
								txnsLog.getFeever(), txnsLog.getBusicode(),
								txnsLog.getAmount(), txnsLog.getAccfirmerno(),
								txnsLog.getTxnseqnoOg(), txnsLog.getCardtype() });
		if (feeList.size() > 0) {
			if (StringUtil.isNull(feeList.get(0).get("FEE"))) {
				return 0L;
			} else {
				return Long.valueOf(feeList.get(0).get("FEE") + "");
			}

		}
		return 0L;
	}


	@Override
	public Session getSession() {
		return txnsOrderinfoDAO.getSession();
	}

	





	

}
