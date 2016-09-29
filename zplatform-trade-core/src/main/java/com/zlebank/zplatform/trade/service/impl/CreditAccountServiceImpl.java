package com.zlebank.zplatform.trade.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.acc.bean.enums.BusiType;
import com.zlebank.zplatform.acc.bean.enums.Usage;
import com.zlebank.zplatform.acc.service.AccountQueryService;
import com.zlebank.zplatform.commons.dao.pojo.BusiTypeEnum;
import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.member.bean.CoopInsti;
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
import com.zlebank.zplatform.trade.bean.enums.TradeStatFlagEnum;
import com.zlebank.zplatform.trade.bean.gateway.TransferOrderBean;
import com.zlebank.zplatform.trade.dao.ITxnsOrderinfoDAO;
import com.zlebank.zplatform.trade.exception.CommonException;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.factory.AccountingAdapterFactory;
import com.zlebank.zplatform.trade.model.TxncodeDefModel;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsOrderinfoModel;
import com.zlebank.zplatform.trade.service.ICreditAccoutService;
import com.zlebank.zplatform.trade.service.IGateWayService;
import com.zlebank.zplatform.trade.service.ITxncodeDefService;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.TradeNotifyService;
import com.zlebank.zplatform.trade.service.base.BaseServiceImpl;
import com.zlebank.zplatform.trade.service.enums.AccTradeExcepitonEnum;
import com.zlebank.zplatform.trade.service.enums.CreditAccExcepitonEnum;
import com.zlebank.zplatform.trade.utils.OrderNumber;
import com.zlebank.zplatform.trade.utils.UUIDUtil;
import com.zlebank.zplatform.trade.utils.ValidateLocator;
@Service
public class CreditAccountServiceImpl extends
BaseServiceImpl<TxnsOrderinfoModel, Long>implements ICreditAccoutService {

	private static final Log log = LogFactory.getLog(CreditAccountServiceImpl.class);
	
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
	public String creditAccountRecharge(TransferOrderBean order)
			throws CommonException {
		 log.info("授信账户充值开始"+order.getOrderId());
         log.info(JSONObject.fromObject(order));
         ResultBean resultBean = ValidateLocator.validateBeans(order);
         if(!resultBean.isResultBool()){
        	 throw  new CommonException(CreditAccExcepitonEnum.CR00.getErrorCode());
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
				throw new CommonException(CreditAccExcepitonEnum.CR01.getErrorCode());//会员信息有误
			}
			return orderinfoList.get(0).getRelatetradetxn();
		}
		//1.校验交易类型是否定
		TxncodeDefModel busiModel = txncodeDefService.getBusiCode(
				order.getTxnType(), order.getTxnSubType(), order.getBizType());
		if(busiModel==null){
			throw new CommonException(CreditAccExcepitonEnum.CR02.getErrorCode());//交易类型有误
		}
		//充值业务
		if(!busiModel.getBusicode().equals(BusinessEnum.CREDIT_RECHARGE.getBusiCode())){
			throw new CommonException(CreditAccExcepitonEnum.CR03.getErrorCode());//只处理授信账户充值才能处理
		}
		//判断双方会员的基本账户状态是否正常 
		CoopInsti inst = null;
		if(!order.getFromMerId().startsWith("3")){
			throw new CommonException(CreditAccExcepitonEnum.CR04.getErrorCode());//付款会员只能是合作机构
		}
		inst = this.coopInstiService.getInstiByInstiCode(order.getFromMerId());
		//合作机构不存
		if(inst==null){
			throw new CommonException(CreditAccExcepitonEnum.CR05.getErrorCode());
		}
		//合作机构状态为00正常
		if(!inst.getStatus().equals("00")){
			throw new CommonException(CreditAccExcepitonEnum.CR06.getErrorCode());
		}
		PojoMember toMember =this.getMemberInfo(order.getToMerId());
		if(toMember ==null){
			throw new CommonException(CreditAccExcepitonEnum.CR07.getErrorCode());//收款方不存在
		}
		//校验会员是合作机构
		if(!toMember.getInstiId().equals(inst.getId())){
			throw new CommonException(CreditAccExcepitonEnum.CR08.getErrorCode());//会员所在的合作机构不一致
		}
		
		//检查合作机构的账户金额
		String  accinfo =this.checkMemberAccInfo(order.getFromMerId(), order.getTxnAmt());
		if(StringUtil.isNotEmpty(accinfo)){
			if(accinfo.equals("account")){
				throw new CommonException(CreditAccExcepitonEnum.CR09.getErrorCode());//合作机构未开通授信账户
			}else if(accinfo.equals("balance")){
				throw new CommonException(CreditAccExcepitonEnum.CR10.getErrorCode());//余额不足
			}
		}
		String  toAccinfo =this.checkMemberAccInfo(order.getToMerId(), null);
		if(StringUtil.isNotEmpty(toAccinfo)){
			if(toAccinfo.equals("account")){
				throw new CommonException(CreditAccExcepitonEnum.CR11.getErrorCode());//收款方未开通授信账户
			}
		}
		/*******保存订单的日志*******/
		TxnsLogModel txnsLog = new TxnsLogModel();
		//合作机构没有版本信息
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
		orderinfo.setSecmembername(inst.getInstiName());
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
		try {
			txnsLogService.saveTxnsLog(txnsLog);
		} catch (TradeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			log.error(e1.getMessage());
			throw new CommonException(CreditAccExcepitonEnum.CR12.getErrorCode());
		}
		saveOrderInfo(orderinfo);
		log.info("授信账户充值订单保存结束"+txnsLog.getTxnseqno());
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
		log.info("授信账户充值处理结束"+txnsLog.getTxnseqno());
		 /**账务处理结束 **/
		/**异步通知处理开始  **/
		if(StringUtil.isNotEmpty(orderinfo.getBackurl()) ){
			 tradeNotifyService.notifyExt(txnsLog.getTxnseqno());
		}
        /**异步通知处理结束 **/
		return txnsLog.getTxnseqno();
	}
	
	
	private String checkMemberAccInfo(String memberId, String txnAmt) {
		MemberBean query = new MemberBean();
		query.setMemberId(memberId);
		try {
			MemberAccountBean balance= memberAccountService.queryBalance(null, query, Usage.GRANTCREDIT);
			if(StringUtil.isNotEmpty(txnAmt)){
				  //账户余额
		    	  BigDecimal amount = balance.getBalance();
		    	  //消费金额
		    	  BigDecimal txnamtBig = new BigDecimal(txnAmt);
		    	  if(amount.compareTo(txnamtBig)<0){
		    		   return "banlance";
		    	  }
			}
			
		} catch (DataCheckFailedException e) {
			log.error("查询授信账户余额失败"+e.getMessage());
			e.printStackTrace();
			return "account";
		} catch (GetAccountFailedException e) {
			log.error("查询授信账户余额失败"+e.getMessage());
			e.printStackTrace();
			return "account";
		}
		return "";
	}
	private PojoMember getMemberInfo(String memberId) {
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
	 * @throws CommonException 
	 */
	@SuppressWarnings("unchecked")
	private String getDefaultVerInfo(String instiCode, String busicode,int verType) throws CommonException {
		List<Map<String, Object>> resultList = (List<Map<String, Object>>) super
				.queryBySQL(
						"select COOP_INSTI_CODE,BUSI_CODE,VER_TYPE,VER_VALUE from T_NONMER_DEFAULT_CONFIG where COOP_INSTI_CODE=? and BUSI_CODE=? and VER_TYPE=?",
						new Object[] { instiCode, busicode, verType + "" });
		if (resultList.size() > 0) {
			Map<String, Object> valueMap = resultList.get(0);
			return valueMap.get("VER_VALUE").toString();
		}
		throw new CommonException(AccTradeExcepitonEnum.GW03.getErrorCode());
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
