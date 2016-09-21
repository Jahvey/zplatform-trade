/* 
 * EnterpriseTradeServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年8月22日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.acc.bean.FinanceProductBean;
import com.zlebank.zplatform.acc.bean.TradeInfo;
import com.zlebank.zplatform.acc.bean.enums.TradeType;
import com.zlebank.zplatform.acc.bean.enums.Usage;
import com.zlebank.zplatform.acc.service.AccEntryService;
import com.zlebank.zplatform.acc.service.FinanceProductService;
import com.zlebank.zplatform.acc.service.entry.EntryEvent;
import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.member.bean.EnterpriseBean;
import com.zlebank.zplatform.member.bean.EnterpriseRealNameBean;
import com.zlebank.zplatform.member.bean.EnterpriseRealNameConfirmBean;
import com.zlebank.zplatform.member.bean.FinanceProductAccountBean;
import com.zlebank.zplatform.member.bean.FinanceProductQueryBean;
import com.zlebank.zplatform.member.exception.DataCheckFailedException;
import com.zlebank.zplatform.member.exception.GetAccountFailedException;
import com.zlebank.zplatform.member.exception.InvalidMemberDataException;
import com.zlebank.zplatform.member.pojo.PojoEnterpriseRealnameApply;
import com.zlebank.zplatform.member.pojo.PojoMember;
import com.zlebank.zplatform.member.pojo.PojoMerchDeta;
import com.zlebank.zplatform.member.service.CoopInstiService;
import com.zlebank.zplatform.member.service.EnterpriseRealnameApplyService;
import com.zlebank.zplatform.member.service.EnterpriseService;
import com.zlebank.zplatform.member.service.FinanceProductAccountService;
import com.zlebank.zplatform.member.service.MemberService;
import com.zlebank.zplatform.member.service.MerchService;
import com.zlebank.zplatform.sms.service.ISMSService;
import com.zlebank.zplatform.trade.bean.FinancierReimbursementBean;
import com.zlebank.zplatform.trade.bean.MerchantReimbursementBean;
import com.zlebank.zplatform.trade.bean.OffLineChargeBean;
import com.zlebank.zplatform.trade.bean.RaiseMoneyTransferBean;
import com.zlebank.zplatform.trade.bean.ReimbursementDetailBean;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.enums.ChannelEnmu;
import com.zlebank.zplatform.trade.bean.enums.TradeStatFlagEnum;
import com.zlebank.zplatform.trade.dao.ITxnsOrderinfoDAO;
import com.zlebank.zplatform.trade.dao.MerchReimburseBatchDAO;
import com.zlebank.zplatform.trade.dao.MerchReimburseDetaDAO;
import com.zlebank.zplatform.trade.dao.RaisemoneyApplyDAO;
import com.zlebank.zplatform.trade.dao.TxnsChargeDAO;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.model.PojoMerchReimburseBatch;
import com.zlebank.zplatform.trade.model.PojoMerchReimburseDeta;
import com.zlebank.zplatform.trade.model.PojoRaisemoneyApply;
import com.zlebank.zplatform.trade.model.PojoTxnsCharge;
import com.zlebank.zplatform.trade.model.TxncodeDefModel;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsOrderinfoModel;
import com.zlebank.zplatform.trade.service.EnterpriseTradeService;
import com.zlebank.zplatform.trade.service.ITxncodeDefService;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.OrderNumber;
import com.zlebank.zplatform.trade.utils.UUIDUtil;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年8月22日 上午11:02:13
 * @since 
 */
@Service("enterpriseTradeService")
public class EnterpriseTradeServiceImpl implements EnterpriseTradeService{

	private static final Log log = LogFactory.getLog(EnterpriseTradeServiceImpl.class);
	@Autowired
	private EnterpriseService enterpriseService;
	@Autowired
	private ITxnsOrderinfoDAO txnsOrderinfoDAO; 
	@Autowired
	private CoopInstiService coopInstiService;
	@Autowired
	private ITxncodeDefService txncodeDefService;
	@Autowired
	private ITxnsLogService txnsLogService;
	@Autowired
	private ISMSService smsService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private TxnsChargeDAO txnsChargeDAO;
	@Autowired
	private AccEntryService accEntryService;
	@Autowired
	private RaisemoneyApplyDAO raisemoneyApplyDAO;
	@Autowired
	private MerchReimburseBatchDAO merchReimburseBatchDAO;
	@Autowired
	private MerchReimburseDetaDAO merchReimburseDetaDAO;
	@Autowired
	private MerchService merchService;
	@Autowired
	private FinanceProductAccountService financeProductAccountService;
	@Autowired
	private FinanceProductService financeProductService;
	@Autowired
	private EnterpriseRealnameApplyService enterpriseRealnameApplyService;
	
	/**
	 *
	 * @return
	 * @throws Exception 
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public String createEnterpriseRealNameOrder(EnterpriseRealNameBean enterpriseRealNameBean) throws Exception {
		EnterpriseBean enterpriseBean = enterpriseService.getEnterpriseByMemberId(enterpriseRealNameBean.getMemberId());
		checkRealNameBean(enterpriseRealNameBean,enterpriseBean);
		//创建交易流水
		TxnsLogModel txnsLog = new TxnsLogModel();
		//由于企业会员 没有扣率，风控，路由版本，所以使用合作机构的
		TxncodeDefModel busiModel = txncodeDefService.getBusiCode(
				enterpriseRealNameBean.getTxnType(), enterpriseRealNameBean.getTxnSubType(), enterpriseRealNameBean.getBizType());
		
		// 10-产品版本,11-扣率版本,12-分润版本,13-风控版本,20-路由版本
		//txnsLog.setRiskver(txncodeDefService.getDefaultVerInfo(enterpriseRealNameBean.getCoopInsti(),busiModel.getBusicode(), 13));
		//txnsLog.setSplitver(txncodeDefService.getDefaultVerInfo(enterpriseRealNameBean.getCoopInsti(),busiModel.getBusicode(), 12));
		//txnsLog.setFeever(txncodeDefService.getDefaultVerInfo(enterpriseRealNameBean.getCoopInsti(),busiModel.getBusicode(), 11));
		//txnsLog.setPrdtver(txncodeDefService.getDefaultVerInfo(enterpriseRealNameBean.getCoopInsti(),busiModel.getBusicode(), 10));
		//txnsLog.setRoutver(txncodeDefService.getDefaultVerInfo(enterpriseRealNameBean.getCoopInsti(),busiModel.getBusicode(), 20));
		txnsLog.setAccsettledate(DateUtil.getSettleDate(1));
		txnsLog.setTxndate(DateUtil.getCurrentDate());
		txnsLog.setTxntime(DateUtil.getCurrentTime());
		txnsLog.setBusicode(busiModel.getBusicode());
		txnsLog.setBusitype(busiModel.getBusitype());
		// 核心交易流水号，交易时间（yymmdd）+业务代码+6位流水号（每日从0开始）
		txnsLog.setTxnseqno(OrderNumber.getInstance().generateTxnseqno(txnsLog.getBusicode()));
		txnsLog.setAmount(0L);
		txnsLog.setAccordno("E"+DateUtil.getCurrentDateTime());// 由于企业实名认证没有订单号，使用时间戳作为订单号
		txnsLog.setAccfirmerno(enterpriseRealNameBean.getCoopInsti());
		txnsLog.setAcccoopinstino(enterpriseRealNameBean.getCoopInsti());
		txnsLog.setAccsecmerno(enterpriseRealNameBean.getMemberId());

		txnsLog.setAccordcommitime(DateUtil.getCurrentDateTime());
		txnsLog.setTradestatflag(TradeStatFlagEnum.INITIAL.getStatus());// 交易初始状态
		txnsLog.setAccmemberid(enterpriseRealNameBean.getMemberId());
		
		//创建企业实名认证订单
		TxnsOrderinfoModel orderinfo = new TxnsOrderinfoModel();
		orderinfo.setId(-1L);
		orderinfo.setOrderno(txnsLog.getAccordno());// 由于企业实名认证没有订单号，使用时间戳作为订单号
		orderinfo.setOrderamt(0L);
		orderinfo.setOrderfee(0L);
		orderinfo.setOrdercommitime(DateUtil.getCurrentDateTime());
		orderinfo.setRelatetradetxn(txnsLog.getTxnseqno());// 关联的交易流水表中的交易序列号
		orderinfo.setFirmemberno(enterpriseRealNameBean.getCoopInsti());
		orderinfo.setFirmembername(coopInstiService.getInstiByInstiCode(
		enterpriseRealNameBean.getCoopInsti()).getInstiName());
		
		orderinfo.setSecmemberno(enterpriseRealNameBean.getMemberId());//企业会员号
		orderinfo.setSecmembername(enterpriseRealNameBean.getEnterpriseName());
		

		orderinfo.setSecmembershortname(enterpriseRealNameBean.getEnterpriseName());
		orderinfo.setFronturl(enterpriseRealNameBean.getFrontURL());
		orderinfo.setTxntype(enterpriseRealNameBean.getTxnType());
		orderinfo.setTxnsubtype(enterpriseRealNameBean.getTxnSubType());
		orderinfo.setBiztype(enterpriseRealNameBean.getBizType());
		orderinfo.setPaytimeout(DateUtil.formatDateTime(DateUtil.skipDateTime(new Date(), 7)));
		orderinfo.setTn(OrderNumber.getInstance().generateTN(enterpriseRealNameBean.getCoopInsti()));
		orderinfo.setMemberid(enterpriseRealNameBean.getMemberId());
		orderinfo.setCurrencycode("156");
		orderinfo.setStatus("01");
		//////////////////////////////////订单和交易日志保存完毕///////////////////////////////////////////
		txnsLogService.saveTxnsLog(txnsLog);
		txnsOrderinfoDAO.saveA(orderinfo);
		enterpriseRealNameBean.setTn(orderinfo.getTn());
		
		try {
			enterpriseService.realNameApply(enterpriseRealNameBean);
		} catch (InvalidMemberDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		
		return orderinfo.getTn();
	}
	
	private void checkRealNameBean(EnterpriseRealNameBean enterpriseRealNameBean,EnterpriseBean enterpriseBean) throws InvalidMemberDataException{
		if(StringUtil.isEmpty(enterpriseRealNameBean.getAccName())){
			throw new InvalidMemberDataException("银行账户不能为空");
		}
		if(StringUtil.isEmpty(enterpriseRealNameBean.getAccNum())){
			throw new InvalidMemberDataException("银行账号不能为空");
		}
		if(StringUtil.isEmpty(enterpriseRealNameBean.getBizType())){
			throw new InvalidMemberDataException("产品类型不能为空");
		}
		if(StringUtil.isEmpty(enterpriseRealNameBean.getChannelType())){
			throw new InvalidMemberDataException("渠道类型不能为空");
		}
		if(StringUtil.isEmpty(enterpriseRealNameBean.getCoopInsti())){
			throw new InvalidMemberDataException("合作机构不能为空");
		}
		if(StringUtil.isEmpty(enterpriseRealNameBean.getFrontURL())){
			throw new InvalidMemberDataException("前台通知地址不能为空");
		}
		if(StringUtil.isEmpty(enterpriseRealNameBean.getMemberId())){
			throw new InvalidMemberDataException("企业会员ID不能为空");
		}
		if(StringUtil.isEmpty(enterpriseRealNameBean.getTxnSubType())){
			throw new InvalidMemberDataException("交易子类不能为空");
		}
		if(StringUtil.isEmpty(enterpriseRealNameBean.getTxnType())){
			throw new InvalidMemberDataException("交易类型不能为空");
		}
		
		
		/*if(StringUtil.isEmpty(enterpriseRealNameBean.getContactsTelNo())){
			throw new InvalidMemberDataException("企业联系人电话不能为空");
		}*/
		if(!enterpriseRealNameBean.getContactsTelNo().equals(enterpriseBean.getContPhone())){
			throw new InvalidMemberDataException("企业联系人电话错误");
		}
		
		if(!enterpriseRealNameBean.getContact().equals(enterpriseBean.getContact())){
			throw new InvalidMemberDataException("企业联系人姓名错误");
		}
		
		if(StringUtil.isEmpty(enterpriseRealNameBean.getCorpNo())){
			throw new InvalidMemberDataException("法人代表身份证号不能为空");
		}
		if(!enterpriseRealNameBean.getCorpNo().equals(enterpriseBean.getCorpNo())){
			throw new InvalidMemberDataException("法人代表身份证号错误");
		}
		
		
		if(StringUtil.isEmpty(enterpriseRealNameBean.getCorporation())){
			throw new InvalidMemberDataException("法人姓名不能为空");
		}
		if(!enterpriseRealNameBean.getCorporation().equals(enterpriseBean.getCorporation())){
			throw new InvalidMemberDataException("法人姓名错误");
		}
		
		
		if(StringUtil.isEmpty(enterpriseRealNameBean.getEnterpriseName())){
			throw new InvalidMemberDataException("企业名称不能为空");
		}
		if(!enterpriseRealNameBean.getEnterpriseName().equals(enterpriseBean.getEnterpriseName())){
			throw new InvalidMemberDataException("企业名称错误");
		}
		
		
		if(StringUtil.isEmpty(enterpriseRealNameBean.getLicenceNo())){
			throw new InvalidMemberDataException("工商营业执照号不能为空");
		}
		if(!enterpriseRealNameBean.getLicenceNo().equals(enterpriseBean.getLicenceNo())){
			throw new InvalidMemberDataException("工商营业执照号错误");
		}
		
		
		if(StringUtil.isEmpty(enterpriseRealNameBean.getOrgCode())){
			throw new InvalidMemberDataException("组织机构代码不能为空");
		}
		if(!enterpriseRealNameBean.getOrgCode().equals(enterpriseBean.getOrgCode())){
			throw new InvalidMemberDataException("组织机构代码错误");
		}
		
		
		if(StringUtil.isEmpty(enterpriseRealNameBean.getTaxNo())){
			throw new InvalidMemberDataException("企业税务登记号不能为空");
		}
		if(!enterpriseRealNameBean.getTaxNo().equals(enterpriseBean.getTaxNo())){
			throw new InvalidMemberDataException("企业税务登记号错误");
		}
		
	}

	@Override
	public void realNameConfirm(EnterpriseRealNameConfirmBean enterpriseRealNameConfirmBean) throws InvalidMemberDataException, DataCheckFailedException, TradeException{
		TxnsOrderinfoModel orderinfo = txnsOrderinfoDAO.getOrderByTN(enterpriseRealNameConfirmBean.getTn());
		if("00".equals(orderinfo.getStatus())){
			throw new TradeException("T000","企业实名认证已完成，请不要重复认证");
		}
		//企业实名认证确认
		enterpriseService.realNameConfirm(enterpriseRealNameConfirmBean);
		//更新交易流水数据-因为是线下业务所以没有账务，没有记录支付方信息，更新交易金额以及中心应答码
		TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(orderinfo.getRelatetradetxn());
		txnsLog.setAmount(Long.valueOf(enterpriseRealNameConfirmBean.getTxnamt()));
		txnsLog.setRetcode("0000");
		txnsLog.setRetcode("交易成功");
		txnsLog.setRetdatetime(DateUtil.getCurrentDateTime());
		txnsLog.setAccordfintime(DateUtil.getCurrentDateTime());
		txnsLogService.updateTxnsLog(txnsLog);
		//更新实名认证订单成功
		txnsOrderinfoDAO.updateOrderToSuccessByTN(enterpriseRealNameConfirmBean.getTn());
		
	}
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public String offLineCharge(OffLineChargeBean offLineChargeBean){
		String tn = OrderNumber.getInstance().generateTN(offLineChargeBean.getCoopInsti());
		//保存线下充值订单
		PojoTxnsCharge charge = new PojoTxnsCharge();
		PojoMember member = memberService.getMbmberByMemberId(offLineChargeBean.getMemberId(), null);
		charge.setMemberid(member.getMemId()+"");
		charge.setAmount(Long.valueOf(offLineChargeBean.getTxnAmt()));
		charge.setChargecode(offLineChargeBean.getChargeCode());
		charge.setChargeno(offLineChargeBean.getOrderId());		
		charge.setChargenoinstid("99999999");//内部调账渠道
		charge.setChargetype("02");//商户充值
		charge.setStatus("01");//等待初审
		charge.setIntime(new Date());
		charge.setInuser(0L);
		charge.setTn(tn);
		txnsChargeDAO.saveA(charge);
		//保存订单数据
		TxnsOrderinfoModel orderinfo = new TxnsOrderinfoModel();
		orderinfo.setId(-1L);
		orderinfo.setOrderno(offLineChargeBean.getOrderId());//由于企业实名认证没有订单号，使用时间戳作为订单号
		orderinfo.setOrderamt(Long.valueOf(offLineChargeBean.getTxnAmt()));
		orderinfo.setOrderfee(0L);
		orderinfo.setOrdercommitime(DateUtil.getCurrentDateTime());
		orderinfo.setFirmemberno(offLineChargeBean.getCoopInsti());
		orderinfo.setFirmembername(coopInstiService.getInstiByInstiCode(offLineChargeBean.getCoopInsti()).getInstiName());
		EnterpriseBean enterprise = enterpriseService.getEnterpriseByMemberId(offLineChargeBean.getMemberId());
		orderinfo.setSecmemberno(offLineChargeBean.getMemberId());//企业会员号
		orderinfo.setSecmembername(enterprise.getEnterpriseName());
		orderinfo.setSecmembershortname(enterprise.getEnterpriseName());
		orderinfo.setBackurl(offLineChargeBean.getBackURL());
		orderinfo.setTxntype(offLineChargeBean.getTxnType());
		orderinfo.setTxnsubtype(offLineChargeBean.getTxnSubType());
		orderinfo.setBiztype(offLineChargeBean.getBizType());
		orderinfo.setPaytimeout(DateUtil.formatDateTime(DateUtil.skipDateTime(new Date(), 7)));
		orderinfo.setTn(tn);
		orderinfo.setMemberid(offLineChargeBean.getMemberId());
		orderinfo.setCurrencycode("156");
		orderinfo.setStatus("01");
		txnsOrderinfoDAO.saveA(orderinfo);
		return tn;
	}
	
	/**
	 * 
	 * @return
	 * @throws TradeException 
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public String createFinancierOrder(FinancierReimbursementBean bean) throws TradeException{
		//校验会员是否存在
		PojoMember member_check = memberService.getMbmberByMemberId(bean.getMemberId(), null);
		if(member_check==null){
			throw new TradeException("T000","【"+bean.getMemberId()+"】会员不存在");
		}
		//检查当前产品是否为当前融资人的产品
		FinanceProductBean productBean = financeProductService.getProductByCode(bean.getProductCode());
		if(productBean==null){
			throw new TradeException("T000","无法找到["+bean.getProductCode()+"]金融产品");
		}
		if(!productBean.getFinancier().equals(bean.getMemberId())){
			throw new TradeException("T000","该融资人无权管理此金融产品");
		}
		
		/**
		 * 1 先写交易订单和交易流水表
		 * 2在进行账务分录流水的处理
		 * 3更新交易状态
		 */
		TxnsLogModel txnsLog = new TxnsLogModel();
		PojoMerchDeta member = null;
		member = merchService.getMerchBymemberId(bean.getMemberId());
		txnsLog.setRiskver(member.getRiskVer());
		txnsLog.setSplitver(member.getSpiltVer());
		txnsLog.setFeever(member.getFeeVer());
		txnsLog.setPrdtver(member.getPrdtVer());
		txnsLog.setRoutver(member.getRoutVer());
		txnsLog.setAccsettledate(DateUtil.getSettleDate(Integer.valueOf(member.getSetlCycle().toString())));
		txnsLog.setTxndate(DateUtil.getCurrentDate());
		txnsLog.setTxntime(DateUtil.getCurrentTime());
		txnsLog.setBusicode(TradeType.PRODUCT_FINANCIERS_REFUND.getCode());
		txnsLog.setBusitype("6000");
		// 核心交易流水号，交易时间（yymmdd）+业务代码+6位流水号（每日从0开始）
		txnsLog.setTxnseqno(OrderNumber.getInstance().generateTxnseqno(txnsLog.getBusicode()));
		txnsLog.setAmount(Long.valueOf(bean.getTotalAmt()));
		txnsLog.setAccordno(bean.getOrderId());
		txnsLog.setAccfirmerno(bean.getCoopInsti());
		txnsLog.setAcccoopinstino(bean.getCoopInsti());
		txnsLog.setAccsecmerno(bean.getMemberId());
		txnsLog.setAccordcommitime(DateUtil.getCurrentDateTime());
		
		//支付方的信息
		txnsLog.setPaytype("08");
		txnsLog.setPayordno("");
		txnsLog.setPayinst(ChannelEnmu.INNERCHANNEL.getChnlcode());
		txnsLog.setPayfirmerno(bean.getCoopInsti());
		txnsLog.setPaysecmerno(bean.getMemberId());
		txnsLog.setPayordcomtime(DateUtil.getCurrentDateTime());
		txnsLog.setProductcode(bean.getProductCode());
		
		
		
		
		
		
		TxnsOrderinfoModel orderinfo = new TxnsOrderinfoModel();
		orderinfo.setId(-1L);
		orderinfo.setOrderno(bean.getOrderId());
		orderinfo.setOrderamt(Long.valueOf(bean.getTotalAmt()));
		orderinfo.setOrderfee(0L);
		orderinfo.setOrdercommitime(DateUtil.getCurrentDateTime());
		orderinfo.setFirmemberno(bean.getCoopInsti());
		orderinfo.setFirmembername(coopInstiService.getInstiByInstiCode(bean.getCoopInsti()).getInstiName());
		EnterpriseBean enterprise = enterpriseService.getEnterpriseByMemberId(bean.getMemberId());
		orderinfo.setSecmemberno(bean.getMemberId());//企业会员号
		orderinfo.setSecmembername(enterprise.getEnterpriseName());
		orderinfo.setSecmembershortname(enterprise.getEnterpriseName());
		orderinfo.setTxntype(bean.getTxnType());
		orderinfo.setTxnsubtype(bean.getTxnSubType());
		orderinfo.setBiztype(bean.getBizType());
		orderinfo.setPaytimeout(DateUtil.formatDateTime(DateUtil.skipDateTime(new Date(), 7)));
		orderinfo.setTn(OrderNumber.getInstance().generateTN(bean.getCoopInsti()));
		orderinfo.setMemberid(bean.getMemberId());
		orderinfo.setCurrencycode("156");
		orderinfo.setProductcode(bean.getProductCode());
		orderinfo.setRelatetradetxn(txnsLog.getTxnseqno());
		
		
		/**交易序列号**/
		String txnseqno = txnsLog.getTxnseqno();
		/**支付订单号**/
        String payordno = "";
        /**交易类型**/
        String busiCode = TradeType.PRODUCT_FINANCIERS_REFUND.getCode();
        /**付款方会员ID**/
        String payMemberId = bean.getMemberId();//融资人
        /**收款方会员ID**/
        String payToMemberId ="";//产品
        /**收款方父级会员ID**/
        String payToParentMemberId="" ;
        /**渠道**/
        String channelId = "";//支付机构代码
        /**产品id**/
        String productId = bean.getProductCode();
        /**交易金额**/
        BigDecimal amount = new BigDecimal(Long.valueOf(bean.getTotalAmt()));
        /**佣金**/
        BigDecimal commission = new BigDecimal(0);
        /**手续费**/
        BigDecimal charge = new BigDecimal(0);
        /**金额D**/
        BigDecimal amountD = new BigDecimal(0);
        /**金额E**/
        BigDecimal amountE = new BigDecimal(0);
        
		try {
			TradeInfo tradeInfo = new TradeInfo(txnseqno, payordno, busiCode, payMemberId, payToMemberId, payToParentMemberId, channelId, productId, amount, commission, charge, amountD, amountE, false);
            tradeInfo.setCoopInstCode(bean.getCoopInsti());
            log.info("【账务分录流水入参】"+JSON.toJSONString(tradeInfo));
			accEntryService.accEntryProcess(tradeInfo, EntryEvent.TRADE_SUCCESS);
			//支付方交易成功信息
			txnsLog.setPayordfintime(DateUtil.getCurrentDateTime());
	        txnsLog.setPayretcode("0000");
	        txnsLog.setPayretinfo("交易成功");
	        txnsLog.setRetcode("0000");
	        txnsLog.setRetinfo("交易成功");
	        txnsLog.setAppinst("000000000000");
	        txnsLog.setAppordcommitime(DateUtil.getCurrentDateTime());
	        txnsLog.setAppordfintime(DateUtil.getCurrentDateTime());
	        txnsLog.setTradeseltxn(UUIDUtil.uuid().replaceAll("-", ""));
	        txnsLog.setRetdatetime(DateUtil.getCurrentDateTime());
	        txnsLog.setApporderstatus("00");
	        txnsLog.setApporderinfo("融资人还款账务处理成功");
	        txnsLog.setAccbusicode(TradeType.PRODUCT_FINANCIERS_REFUND.getCode());
	        txnsLog.setTradetxnflag("10000000");
	        txnsLog.setRelate("10000000");
	        txnsLog.setTxnfee(0L);
	        txnsLog.setAccmemberid(bean.getMemberId());
	        txnsLog.setAccordfintime(DateUtil.getCurrentDateTime());
	        txnsLog.setTradestatflag(TradeStatFlagEnum.FINISH_ACCOUNTING.getStatus());// 交易初始状态
	        orderinfo.setStatus("00");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			orderinfo.setStatus("03");
			txnsLog.setPayordfintime(DateUtil.getCurrentDateTime());
	        txnsLog.setPayretcode("0099");
	        txnsLog.setPayretinfo(e.getMessage());
	        txnsLog.setRetcode("0099");
	        txnsLog.setRetinfo("交易失败");
	        txnsLog.setAppinst("000000000000");
	        txnsLog.setAppordcommitime(DateUtil.getCurrentDateTime());
	        txnsLog.setAppordfintime(DateUtil.getCurrentDateTime());
	        txnsLog.setApporderstatus("00");
	        txnsLog.setApporderinfo("融资人还款账务处理失败");
	        txnsLog.setTxnfee(0L);
	        txnsLog.setAccmemberid(bean.getMemberId());
	        txnsLog.setAccordfintime(DateUtil.getCurrentDateTime());
			txnsLog.setTradestatflag(TradeStatFlagEnum.FINISH_FAILED.getStatus());// 交易初始状态
		}
		txnsLogService.saveTxnsLog(txnsLog);
		txnsOrderinfoDAO.saveA(orderinfo);
		return orderinfo.getTn();
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public String raiseMoneyTransfer(RaiseMoneyTransferBean bean) throws TradeException{
	
		//检查当前产品是否为当前融资人的产品
		FinanceProductBean productBean = financeProductService.getProductByCode(bean.getProductCode());
		if(productBean==null){
			throw new TradeException("T000","无法找到["+bean.getProductCode()+"]金融产品");
		}
		if(!productBean.getFinancier().equals(bean.getFinancingId())){
			throw new TradeException("T000","该融资人无权管理此金融产品");
		}
		if(!productBean.getFundManager().equals(bean.getFinancingId())){
			throw new TradeException("T000","该商户无权管理此金融产品");
		}
		
		String txnseqno = OrderNumber.getInstance().generateTxnseqno(TradeType.PRODUCT_CAPITAL_TRANSFER.getCode());
		TxnsOrderinfoModel orderinfo = new TxnsOrderinfoModel();
		//保存审核数据
		PojoRaisemoneyApply apply = new PojoRaisemoneyApply();
		apply.setMemberid(bean.getMemberId());
		apply.setFinancingid(bean.getFinancingId());
		apply.setProductcode(bean.getProductCode());
		apply.setOrderid(bean.getOrderId());
		String tn =  OrderNumber.getInstance().generateTN(bean.getCoopInsti());
		apply.setTn(tn);
		apply.setStatus("01");
		apply.setInTime(new Date());
		apply.setInUser(0L);
		raisemoneyApplyDAO.saveA(apply);
		orderinfo.setId(-1L);
		orderinfo.setOrderno(bean.getOrderId());
		orderinfo.setOrderamt(0L);
		orderinfo.setOrderfee(0L);
		orderinfo.setOrdercommitime(DateUtil.getCurrentDateTime());
		orderinfo.setFirmemberno(bean.getCoopInsti());
		orderinfo.setFirmembername(coopInstiService.getInstiByInstiCode(bean.getCoopInsti()).getInstiName());
		EnterpriseBean enterprise = enterpriseService.getEnterpriseByMemberId(bean.getMemberId());
		orderinfo.setSecmemberno(bean.getMemberId());//企业会员号
		orderinfo.setSecmembername(enterprise.getEnterpriseName());
		orderinfo.setSecmembershortname(enterprise.getEnterpriseName());
		orderinfo.setBackurl("");
		orderinfo.setTxntype(bean.getTxnType());
		orderinfo.setTxnsubtype(bean.getTxnSubType());
		orderinfo.setBiztype(bean.getBizType());
		orderinfo.setPaytimeout(DateUtil.formatDateTime(DateUtil.skipDateTime(new Date(), 7)));
		orderinfo.setTn(tn);
		orderinfo.setMemberid(bean.getMemberId());
		orderinfo.setCurrencycode("156");
		orderinfo.setProductcode(bean.getProductCode());
		orderinfo.setRelatetradetxn(txnseqno);
		txnsOrderinfoDAO.saveA(orderinfo);
		
		TxnsLogModel txnsLog = new TxnsLogModel();
		PojoMerchDeta member = null;
		member = merchService.getMerchBymemberId(bean.getMemberId());
		txnsLog.setRiskver(member.getRiskVer());
		txnsLog.setSplitver(member.getSpiltVer());
		txnsLog.setFeever(member.getFeeVer());
		txnsLog.setPrdtver(member.getPrdtVer());
		txnsLog.setRoutver(member.getRoutVer());
		txnsLog.setAccsettledate(DateUtil.getSettleDate(Integer.valueOf(member.getSetlCycle().toString())));
		txnsLog.setTxndate(DateUtil.getCurrentDate());
		txnsLog.setTxntime(DateUtil.getCurrentTime());
		txnsLog.setBusicode(TradeType.PRODUCT_CAPITAL_TRANSFER.getCode());
		txnsLog.setBusitype("7000");
		// 核心交易流水号，交易时间（yymmdd）+业务代码+6位流水号（每日从0开始）
		txnsLog.setTxnseqno(txnseqno);
		txnsLog.setAmount(0L);
		txnsLog.setAccordno(bean.getOrderId());
		txnsLog.setAccfirmerno(orderinfo.getFirmemberno());
		txnsLog.setAccsecmerno(orderinfo.getSecmemberno());
		txnsLog.setAcccoopinstino(ConsUtil.getInstance().cons.getZlebank_coopinsti_code());
		txnsLog.setAccordcommitime(DateUtil.getCurrentDateTime());
		//支付方的信息
		txnsLog.setPaytype("08");
		txnsLog.setPayordno("");
		txnsLog.setPayinst(ChannelEnmu.INNERCHANNEL.getChnlcode());
		txnsLog.setPayfirmerno(orderinfo.getFirmemberno());
		txnsLog.setPaysecmerno(orderinfo.getSecmemberno());
		txnsLog.setPayordcomtime(DateUtil.getCurrentDateTime());
		txnsLog.setProductcode(bean.getProductCode());
		try {
			txnsLogService.saveTxnsLog(txnsLog);
		} catch (TradeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return orderinfo.getTn();
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public String merchReimbusement(MerchantReimbursementBean bean) throws Exception{
		FinanceProductBean productBean = financeProductService.getProductByCode(bean.getProductCode());
		if(productBean==null){
			throw new TradeException("T000","无法找到["+bean.getProductCode()+"]金融产品");
		}
		if(!productBean.getFundManager().equals(bean.getMemberId())){
			throw new TradeException("T000","该商户无权管理此金融产品");
		}
		
		PojoMerchReimburseBatch merchReimburseBatch = merchReimburseBatchDAO.getBatchInfoByBatchNo(bean.getBatchNo());
		if(merchReimburseBatch!=null){
			throw new Exception("批次号已经存在");
		}
		//检查批次号是否重复
		List<ReimbursementDetailBean> detaiList = bean.getDetaiList();
		int total = 0;
		PojoMerchReimburseDeta deta = null;
		for(ReimbursementDetailBean detailBean : detaiList){
			total++;
			deta = new PojoMerchReimburseDeta();
			deta.setBatchno(bean.getBatchNo());
			deta.setMerId(bean.getMemberId());
			deta.setInvestors(detailBean.getInvestors());
			deta.setPorductCode(bean.getProductCode());
			deta.setInterset(new BigDecimal(Long.valueOf(detailBean.getInterest())));
			deta.setTxnamt(new BigDecimal(Long.valueOf(detailBean.getTxnAmt())));
			deta.setStatus("01");
			deta.setIntime(new Date());
			deta.setInuser(0L);
			deta.setOrderId(detailBean.getOrderId());
			merchReimburseDetaDAO.saveA(deta);
		}
		
		PojoMerchReimburseBatch batch = new PojoMerchReimburseBatch();
		batch.setBatchNo(bean.getBatchNo());
		batch.setMerId(bean.getMemberId());
		batch.setProdcutcode(bean.getProductCode());
		batch.setTotalAmt(new BigDecimal(Long.valueOf(bean.getTotalAmt())));
		batch.setTotalQty(total);
		batch.setTxnTime(DateUtil.getCurrentDateTime());
		batch.setStatus("01");
		batch.setType("01");
		batch.setIntime(new Date());
		batch.setApplyTime(new Date());
		batch.setInuser(0L);
		batch.setTn(OrderNumber.getInstance().generateTN(bean.getMemberId()));
		batch.setCoopinsticode(bean.getCoopInsti());
		merchReimburseBatchDAO.saveA(batch);
		return batch.getTn();
	}
	
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	@Override
	public void raiseMoneyTransferFinish(Long tid) throws DataCheckFailedException, GetAccountFailedException, TradeException{
		//募集款划转信息
		PojoRaisemoneyApply raisemoney = raisemoneyApplyDAO.getApply(tid);
		if(!"00".equals(raisemoney.getStatus())){
			throw new TradeException("T000","募集款划转未审核完成");
		}
		//产品现金账户余额
		FinanceProductQueryBean financeProductQueryBean = new FinanceProductQueryBean();
		financeProductQueryBean.setProductCode(raisemoney.getProductcode());
		FinanceProductAccountBean accountBalanceBean = financeProductAccountService.queryBalance(financeProductQueryBean, Usage.BASICPAY);
		//交易订单信息
		TxnsOrderinfoModel orderinfo = txnsOrderinfoDAO.getOrderByTN(raisemoney.getTn());
		//交易日志数据
		TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(orderinfo.getRelatetradetxn());
		if("0000".equals(txnsLog.getRetcode())){
			throw new TradeException("T000","募集款划转订单号"+raisemoney.getOrderid()+"交易已经成功，请不要重复提交");
		}
		
		
		txnsLog.setAmount(accountBalanceBean.getBalance().longValue());
		txnsLog.setAcccoopinstino(ConsUtil.getInstance().cons.getZlebank_coopinsti_code());
		/**交易序列号**/
		String txnseqno = txnsLog.getTxnseqno();
		/**支付订单号**/
        String payordno = "";
        /**交易类型**/
        String busiCode = TradeType.PRODUCT_FINANCIERS_REFUND.getCode();
        /**付款方会员ID**/
        String payMemberId = raisemoney.getFinancingid();//融资人
        /**收款方会员ID**/
        String payToMemberId ="";//产品
        /**收款方父级会员ID**/
        String payToParentMemberId="" ;
        /**渠道**/
        String channelId = "";//支付机构代码
        /**产品id**/
        String productId = raisemoney.getProductcode();
        /**交易金额**/
        BigDecimal amount = new BigDecimal(txnsLog.getAmount());
        /**佣金**/
        BigDecimal commission = new BigDecimal(0);
        /**手续费**/
        BigDecimal charge = new BigDecimal(0);
        /**金额D**/
        BigDecimal amountD = new BigDecimal(0);
        /**金额E**/
        BigDecimal amountE = new BigDecimal(0);
        /** 机构 */
        String coopInstCode = txnsLog.getAcccoopinstino();
        /** 接入机构 */
        String access_coopInstCode = txnsLog.getAccfirmerno();
		try {
			TradeInfo tradeInfo = new TradeInfo(txnseqno, payordno, busiCode, payMemberId, payToMemberId, payToParentMemberId, channelId, productId, amount, commission, charge, amountD, amountE, false);
            tradeInfo.setCoopInstCode(coopInstCode);
            tradeInfo.setAccess_coopInstCode(access_coopInstCode);
            log.info("【账务分录流水入参】"+JSON.toJSONString(tradeInfo));
			accEntryService.accEntryProcess(tradeInfo, EntryEvent.TRADE_SUCCESS);
			//支付方交易成功信息
			txnsLog.setPayordfintime(DateUtil.getCurrentDateTime());
	        txnsLog.setPayretcode("0000");
	        txnsLog.setPayretinfo("交易成功");
	        txnsLog.setRetcode("0000");
	        txnsLog.setRetinfo("交易成功");
	        txnsLog.setAppinst("000000000000");
	        txnsLog.setAppordcommitime(DateUtil.getCurrentDateTime());
	        txnsLog.setAppordfintime(DateUtil.getCurrentDateTime());
	        txnsLog.setTradeseltxn(UUIDUtil.uuid().replaceAll("-", ""));
	        txnsLog.setRetdatetime(DateUtil.getCurrentDateTime());
	        txnsLog.setApporderstatus("00");
	        txnsLog.setApporderinfo("募集款划转账务处理成功");
	        txnsLog.setAccbusicode(TradeType.PRODUCT_FINANCIERS_REFUND.getCode());
	        txnsLog.setTradetxnflag("10000000");
	        txnsLog.setRelate("10000000");
	        txnsLog.setTxnfee(0L);
	        txnsLog.setAccmemberid(raisemoney.getMemberid());
	        txnsLog.setAccordfintime(DateUtil.getCurrentDateTime());
	        txnsLog.setTradestatflag(TradeStatFlagEnum.FINISH_ACCOUNTING.getStatus());// 交易初始状态
	        txnsOrderinfoDAO.updateOrderToSuccess(txnseqno);
	        raisemoney.setRetinfo("交易成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			orderinfo.setStatus("03");
			txnsLog.setPayordfintime(DateUtil.getCurrentDateTime());
	        txnsLog.setPayretcode("0099");
	        txnsLog.setPayretinfo(e.getMessage());
	        txnsLog.setRetcode("0099");
	        txnsLog.setRetinfo("交易失败");
	        txnsLog.setAppinst("000000000000");
	        txnsLog.setAppordcommitime(DateUtil.getCurrentDateTime());
	        txnsLog.setAppordfintime(DateUtil.getCurrentDateTime());
	        txnsLog.setApporderstatus("00");
	        txnsLog.setApporderinfo("募集款划转账务处理失败");
	        txnsLog.setTxnfee(0L);
	        txnsLog.setAccmemberid(raisemoney.getMemberid());
	        txnsLog.setAccordfintime(DateUtil.getCurrentDateTime());
			txnsLog.setTradestatflag(TradeStatFlagEnum.FINISH_FAILED.getStatus());// 交易初始状态
			txnsOrderinfoDAO.updateOrderToFail(txnseqno);
			raisemoney.setRetinfo("交易失败");
		}
		raisemoney.setTxnseqno(txnsLog.getTxnseqno());
		txnsLogService.updateTxnsLog(txnsLog);
		raisemoneyApplyDAO.update(raisemoney);
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	@Override
	public void merchReimbusementFinish(long tid) throws TradeException{
		ResultBean resultBean = null;
		if(tid<=0){
			throw new TradeException("T000","未找到商户还款数据（标示不存在）");
		}
		//获取商户还款详细数据
		PojoMerchReimburseDeta deta = merchReimburseDetaDAO.getDeta(tid);
		if(deta==null){
			throw new TradeException("T000","未找到商户还款数据");
		}
		//判断商户还款状态是否审核完成
		if(!"00".equals(deta.getStatus())){
			throw new TradeException("T000","商户还款数据未审核完成");
		}
		//获取商户还款批次数据
		PojoMerchReimburseBatch batchInfo = merchReimburseBatchDAO.getBatchInfoByBatchNo(deta.getBatchno());
		
		TxnsLogModel txnsLog = null;
		//防止重复进行账务
		if(StringUtil.isNotEmpty(deta.getTxnseqno())){
			txnsLog = txnsLogService.getTxnsLogByTxnseqno(deta.getTxnseqno());
			if("0000".equals(txnsLog.getRetcode())){
				throw new TradeException("T000","商户还款订单号"+deta.getOrderId()+"交易成功，请勿重复提交");
			}
			
		}else{
			txnsLog = new TxnsLogModel();
		}
		PojoMerchDeta member = null;
		member = merchService.getMerchBymemberId(batchInfo.getMerId());
		txnsLog.setRiskver(member.getRiskVer());
		txnsLog.setSplitver(member.getSpiltVer());
		txnsLog.setFeever(member.getFeeVer());
		txnsLog.setPrdtver(member.getPrdtVer());
		txnsLog.setRoutver(member.getRoutVer());
		txnsLog.setAccsettledate(DateUtil.getSettleDate(Integer.valueOf(member.getSetlCycle().toString())));
		txnsLog.setTxndate(DateUtil.getCurrentDate());
		txnsLog.setTxntime(DateUtil.getCurrentTime());
		txnsLog.setBusicode(TradeType.PRODUCT_CAPITAL_RANSOM.getCode());
		txnsLog.setBusitype("4000");
		// 核心交易流水号，交易时间（yymmdd）+业务代码+6位流水号（每日从0开始）
		txnsLog.setTxnseqno(OrderNumber.getInstance().generateTxnseqno(txnsLog.getBusicode()));
		txnsLog.setAmount(deta.getTxnamt().longValue()+deta.getInterset().longValue());
		txnsLog.setAccordno(deta.getOrderId());
		txnsLog.setAccfirmerno(batchInfo.getCoopinsticode());
		txnsLog.setAcccoopinstino(ConsUtil.getInstance().cons.getZlebank_coopinsti_code());
		txnsLog.setAccsecmerno(deta.getMerId());
		txnsLog.setAccordcommitime(DateUtil.getCurrentDateTime());
		txnsLog.setAccmemberid(deta.getInvestors());
		//支付方的信息
		txnsLog.setPaytype("08");
		txnsLog.setPayordno("");
		txnsLog.setPayinst(ChannelEnmu.INNERCHANNEL.getChnlcode());
		txnsLog.setPayfirmerno(deta.getMerId());
		txnsLog.setPaysecmerno(deta.getInvestors());
		txnsLog.setPayordcomtime(DateUtil.getCurrentDateTime());
		txnsLog.setProductcode(batchInfo.getProdcutcode());
		/**交易序列号**/
		String txnseqno = txnsLog.getTxnseqno();
		/**支付订单号**/
        String payordno = "";
        /**交易类型**/
        String busiCode = TradeType.PRODUCT_FINANCIERS_REFUND.getCode();
        /**付款方会员ID**/
        String payMemberId = deta.getInvestors();//投资人
        /**收款方会员ID**/
        String payToMemberId ="";//产品
        /**收款方父级会员ID**/
        String payToParentMemberId="" ;
        /**渠道**/
        String channelId = "";//支付机构代码
        /**产品id**/
        String productId = batchInfo.getProdcutcode();
        /**交易金额**/
        BigDecimal amount = new BigDecimal(txnsLog.getAmount());
        /**佣金**/
        BigDecimal commission = new BigDecimal(0);
        /**手续费**/
        BigDecimal charge = new BigDecimal(0);
        /**金额D**/
        BigDecimal amountD = new BigDecimal(0);
        /**金额E**/
        BigDecimal amountE = new BigDecimal(0);
        /** 机构 */
        String coopInstCode = txnsLog.getAcccoopinstino();
        /** 接入机构 */
        String access_coopInstCode = txnsLog.getAccfirmerno();
		try {
			TradeInfo tradeInfo = new TradeInfo(txnseqno, payordno, busiCode, payMemberId, payToMemberId, payToParentMemberId, channelId, productId, amount, commission, charge, amountD, amountE, false);
            tradeInfo.setCoopInstCode(coopInstCode);
            tradeInfo.setAccess_coopInstCode(access_coopInstCode);
            log.info("【账务分录流水入参】"+JSON.toJSONString(tradeInfo));
			accEntryService.accEntryProcess(tradeInfo, EntryEvent.TRADE_SUCCESS);
			//支付方交易成功信息
			txnsLog.setPayordfintime(DateUtil.getCurrentDateTime());
	        txnsLog.setPayretcode("0000");
	        txnsLog.setPayretinfo("交易成功");
	        txnsLog.setRetcode("0000");
	        txnsLog.setRetinfo("交易成功");
	        txnsLog.setAppinst("000000000000");
	        txnsLog.setAppordcommitime(DateUtil.getCurrentDateTime());
	        txnsLog.setAppordfintime(DateUtil.getCurrentDateTime());
	        txnsLog.setTradeseltxn(UUIDUtil.uuid().replaceAll("-", ""));
	        txnsLog.setRetdatetime(DateUtil.getCurrentDateTime());
	        txnsLog.setApporderstatus("00");
	        txnsLog.setApporderinfo("商户还款账务处理成功");
	        txnsLog.setAccbusicode(TradeType.PRODUCT_FINANCIERS_REFUND.getCode());
	        txnsLog.setTradetxnflag("10000000");
	        txnsLog.setRelate("10000000");
	        txnsLog.setTxnfee(0L);
	        txnsLog.setAccordfintime(DateUtil.getCurrentDateTime());
	        txnsLog.setTradestatflag(TradeStatFlagEnum.FINISH_ACCOUNTING.getStatus());// 交易初始状态
	        resultBean = new ResultBean("success");
	        deta.setRetinfo("交易成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			txnsLog.setPayordfintime(DateUtil.getCurrentDateTime());
	        txnsLog.setPayretcode("0099");
	        txnsLog.setPayretinfo(e.getMessage());
	        txnsLog.setRetcode("0099");
	        txnsLog.setRetinfo("交易失败");
	        txnsLog.setAppinst("000000000000");
	        txnsLog.setAppordcommitime(DateUtil.getCurrentDateTime());
	        txnsLog.setAppordfintime(DateUtil.getCurrentDateTime());
	        txnsLog.setApporderstatus("00");
	        txnsLog.setApporderinfo("商户还款账务处理失败");
	        txnsLog.setTxnfee(0L);
	        txnsLog.setAccordfintime(DateUtil.getCurrentDateTime());
			txnsLog.setTradestatflag(TradeStatFlagEnum.FINISH_FAILED.getStatus());// 交易初始状态
			
			resultBean = new ResultBean("",e.getMessage());
			deta.setRetinfo("交易失败");
		}
		try {
			txnsLogService.saveTxnsLog(txnsLog);
			deta.setTxnseqno(txnsLog.getTxnseqno());
			merchReimburseDetaDAO.update(deta);
		} catch (TradeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultBean = new ResultBean("","交易SAVE失败");
		}
	}
}
