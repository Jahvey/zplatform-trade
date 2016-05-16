/* 
 * GateWayServiceImpl.java  
 * 
 * version TODO
 *
 * 2015年8月27日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zlebank.zplatform.acc.bean.BusiAcct;
import com.zlebank.zplatform.acc.bean.BusiAcctQuery;
import com.zlebank.zplatform.acc.bean.TradeInfo;
import com.zlebank.zplatform.acc.bean.enums.AcctStatusType;
import com.zlebank.zplatform.acc.bean.enums.Usage;
import com.zlebank.zplatform.acc.exception.AbstractBusiAcctException;
import com.zlebank.zplatform.acc.exception.AccBussinessException;
import com.zlebank.zplatform.acc.service.AccEntryService;
import com.zlebank.zplatform.acc.service.AccountQueryService;
import com.zlebank.zplatform.commons.bean.PagedResult;
import com.zlebank.zplatform.commons.enums.BusinessCodeEnum;
import com.zlebank.zplatform.commons.utils.Base64Utils;
import com.zlebank.zplatform.commons.utils.RSAUtils;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.member.bean.CoopInstiMK;
import com.zlebank.zplatform.member.bean.MemberBean;
import com.zlebank.zplatform.member.bean.MerchMK;
import com.zlebank.zplatform.member.bean.PersonManager;
import com.zlebank.zplatform.member.bean.QuickpayCustBean;
import com.zlebank.zplatform.member.bean.enums.MemberType;
import com.zlebank.zplatform.member.bean.enums.TerminalAccessType;
import com.zlebank.zplatform.member.dao.MemberDAO;
import com.zlebank.zplatform.member.dao.QuickpayCustDAO;
import com.zlebank.zplatform.member.exception.DataCheckFailedException;
import com.zlebank.zplatform.member.pojo.PojoMember;
import com.zlebank.zplatform.member.pojo.PojoMerchDeta;
import com.zlebank.zplatform.member.pojo.PojoQuickpayCust;
import com.zlebank.zplatform.member.service.CoopInstiService;
import com.zlebank.zplatform.member.service.MemberBankCardService;
import com.zlebank.zplatform.member.service.MemberOperationService;
import com.zlebank.zplatform.member.service.MemberService;
import com.zlebank.zplatform.member.service.MerchMKService;
import com.zlebank.zplatform.member.service.MerchService;
import com.zlebank.zplatform.member.service.PersonService;
import com.zlebank.zplatform.trade.adapter.quickpay.IQuickPayTrade;
import com.zlebank.zplatform.trade.analyzer.GateWayTradeAnalyzer;
import com.zlebank.zplatform.trade.bean.AccountTradeBean;
import com.zlebank.zplatform.trade.bean.ReaPayResultBean;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.ZLPayResultBean;
import com.zlebank.zplatform.trade.bean.enums.BusinessEnum;
import com.zlebank.zplatform.trade.bean.enums.ChannelEnmu;
import com.zlebank.zplatform.trade.bean.enums.ChnlTypeEnum;
import com.zlebank.zplatform.trade.bean.enums.TradeTypeEnum;
import com.zlebank.zplatform.trade.bean.gateway.OrderAsynRespBean;
import com.zlebank.zplatform.trade.bean.gateway.OrderBean;
import com.zlebank.zplatform.trade.bean.gateway.OrderRespBean;
import com.zlebank.zplatform.trade.bean.gateway.QueryBean;
import com.zlebank.zplatform.trade.bean.gateway.QueryResultBean;
import com.zlebank.zplatform.trade.bean.gateway.RiskRateInfoBean;
import com.zlebank.zplatform.trade.bean.gateway.SplitAcctBean;
import com.zlebank.zplatform.trade.bean.wap.WapAcctPayBean;
import com.zlebank.zplatform.trade.bean.wap.WapCardBean;
import com.zlebank.zplatform.trade.bean.wap.WapDebitCardSignBean;
import com.zlebank.zplatform.trade.bean.wap.WapDebitCardSingRespBean;
import com.zlebank.zplatform.trade.bean.wap.WapOrderBean;
import com.zlebank.zplatform.trade.bean.wap.WapOrderRespBean;
import com.zlebank.zplatform.trade.bean.wap.WapQuerySignCardBean;
import com.zlebank.zplatform.trade.bean.wap.WapRefundBean;
import com.zlebank.zplatform.trade.bean.wap.WapSMSMessageBean;
import com.zlebank.zplatform.trade.bean.wap.WapSubmitPayBean;
import com.zlebank.zplatform.trade.bean.wap.WapUsableBankCardBean;
import com.zlebank.zplatform.trade.bean.wap.WapWithdrawAccBean;
import com.zlebank.zplatform.trade.bean.wap.WapWithdrawBean;
import com.zlebank.zplatform.trade.cmbc.service.ICMBCTransferService;
import com.zlebank.zplatform.trade.dao.ITxnsOrderinfoDAO;
import com.zlebank.zplatform.trade.dao.RspmsgDAO;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.factory.TradeAdapterFactory;
import com.zlebank.zplatform.trade.model.CashBankModel;
import com.zlebank.zplatform.trade.model.PojoRspmsg;
import com.zlebank.zplatform.trade.model.TxncodeDefModel;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsOrderinfoModel;
import com.zlebank.zplatform.trade.model.TxnsRefundModel;
import com.zlebank.zplatform.trade.model.TxnsWithdrawModel;
import com.zlebank.zplatform.trade.service.IAccountPayService;
import com.zlebank.zplatform.trade.service.ICashBankService;
import com.zlebank.zplatform.trade.service.IGateWayService;
import com.zlebank.zplatform.trade.service.IProdCaseService;
import com.zlebank.zplatform.trade.service.IRouteConfigService;
import com.zlebank.zplatform.trade.service.ITxncodeDefService;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.ITxnsQuickpayService;
import com.zlebank.zplatform.trade.service.ITxnsRefundService;
import com.zlebank.zplatform.trade.service.ITxnsSplitAccountService;
import com.zlebank.zplatform.trade.service.ITxnsWithdrawService;
import com.zlebank.zplatform.trade.service.ITxnsWithholdingService;
import com.zlebank.zplatform.trade.service.base.BaseServiceImpl;
import com.zlebank.zplatform.trade.utils.DateUtil;
import com.zlebank.zplatform.trade.utils.OrderNumber;
import com.zlebank.zplatform.trade.utils.SynHttpRequestThread;
import com.zlebank.zplatform.trade.utils.UUIDUtil;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年8月27日 上午10:55:36
 * @since
 */
@Service("gateWayService")
public class GateWayServiceImpl extends BaseServiceImpl<TxnsOrderinfoModel, Long>
        implements
            IGateWayService {
    private static final Log log = LogFactory.getLog(GateWayServiceImpl.class);
    @Autowired
    private ITxnsLogService txnsLogService;
    @Autowired
    private ITxnsOrderinfoDAO txnsOrderinfoDAO;
    @Autowired
    private IRouteConfigService routeConfigService;
    //@Autowired
    //private IMemberService memberService;
    @Autowired
    private MerchMKService merchMKService;
    @Autowired
    private ITxncodeDefService txncodeDefService;
    @Autowired
    private ITxnsSplitAccountService txnsSplitAccountService;
    //@Autowired
    //private IQuickpayCustService quickpayCustService;
    @Autowired 
    private ITxnsQuickpayService txnsQuickpayService;
    @Autowired
    private IAccountPayService accountPayService;
    @Autowired
    private IProdCaseService prodCaseService;
    @Autowired
    private ICashBankService cashBankService;
    @Autowired
    private ITxnsRefundService txnsRefundService;
    @Autowired
    private ITxnsWithdrawService txnsWithdrawService;
    @Autowired
    private AccountQueryService accountQueryService;
    @Autowired
    private ICMBCTransferService cmbcTransferService;
    @Autowired
    private ITxnsWithholdingService txnsWithholdingService;
    @Autowired
    private CoopInstiService coopInstiService;
    @Autowired
    @Qualifier("memberServiceImpl")
    private MemberService memberService2; 
    @Autowired
    private RspmsgDAO rspmsgDAO;
    @Autowired
    private MemberBankCardService memberBankCardService;
    
    @Autowired
    private MerchService merchService;
    @Autowired
    private AccEntryService accEntryService;
    @Autowired
    private QuickpayCustDAO quickpayCustDAO;
    @Autowired
    private PersonService personService;
    @Autowired
    private MemberOperationService memberOperationService;
    @Autowired
    private MemberDAO memberDAO;
    /**
     *
     * @return
     */
    public Session getSession() {
        return txnsOrderinfoDAO.getSession();
    }
    /**
     * 
     * @param order
     * @return
     */
    public ResultBean verifyOrder(OrderBean order) {
        ResultBean resultBean = null;
        try {
            byte[] paraMsg = GateWayTradeAnalyzer.generateOrderParamer(order);
            MerchMK merchMk = null;
            String accessType = order.getAccessType();
            if(accessType.equals("0")){//普通商户直连接入
            	merchMk = merchMKService.get(order.getMerId());
            	if(merchMk==null){
                    resultBean = new ResultBean("RC02","商户不存在");
                    return resultBean;
                }
            	String publicKey = "";// 获取公钥方法暂无，商户信息中提供
                if("01".equals(merchMk.getSafeType())){
                    publicKey=merchMk.getMemberPubKey();
                }
                if(!RSAUtils.verify(paraMsg, publicKey, order.getSignature())){
                    resultBean = new ResultBean("RC03", "验签失败");
                    return resultBean;
                }else{
                    resultBean = new ResultBean("success");
                }
            }else{
            	TerminalAccessType terminalAccessType = TerminalAccessType.MERPORTAL;
            	CoopInstiMK coopInstiMK = coopInstiService.getCoopInstiMK(order.getCoopInstiId(), terminalAccessType);
            	if(coopInstiMK==null){
                    resultBean = new ResultBean("RC02","商户不存在");
                    return resultBean;
                }
            	String  publicKey=coopInstiMK.getInstiPubKey();
                if(!RSAUtils.verify(paraMsg, publicKey, order.getSignature())){
                    resultBean = new ResultBean("RC03", "验签失败");
                    return resultBean;
                }else{
                    resultBean = new ResultBean("success");
                }
            }
            return resultBean;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            resultBean = new ResultBean("RC03", "验签失败");
        }
        return resultBean;
    }
    
    public void verifyWapOrder(JSONObject order)  throws TradeException{
        try {
            String signature = order.getString("signature");
            //
            MerchMK merchMk = merchMKService.get(order.getString("virtualId"));
            if(merchMk==null){
                throw new TradeException("GW05");
            }
            String publicKey = "";// 获取公钥方法暂无，商户信息中提供
            if("01".equals(merchMk.getSafeType())){
                publicKey=merchMk.getMemberPubKey();
            }
            
            if(!RSAUtils.verify(order.toJSONString().getBytes(), publicKey, signature)){
                throw new TradeException("GW09");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            throw new TradeException("GW10");
        }
       
    }
    public void verifyRepeatWapOrder(String orderNo,String txntime,String amount,String merchId,String memberId) throws TradeException{
        TxnsOrderinfoModel orderInfo = getOrderinfoByOrderNoAndMemberId(orderNo, merchId);
        if(orderInfo!=null){
            TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(orderInfo.getRelatetradetxn());
            if("00".equals(orderInfo.getStatus())){//交易成功订单不可二次支付
                throw new TradeException("T004");
            }
            if("02".equals(orderInfo.getStatus())){
                throw new TradeException("T009");
            }
            if("04".equals(orderInfo.getStatus())){
                throw new TradeException("T012");
            }
            if(!amount.equals(orderInfo.getOrderamt().toString())){
                throw new TradeException("T014");
            }
            if(!txntime.equals(orderInfo.getOrdercommitime())){//订单存在，提交日期也一致，二次支付订单,
                throw new TradeException("T013");
            }
            if(!merchId.equals(orderInfo.getFirmemberno())){
                throw new TradeException("T015");
            }
            if(!"999999999999999".equals(txnsLog.getAccmemberid())){//非匿名支付
               if(!txnsLog.getAccmemberid().equals(memberId)){
                   throw new TradeException("T036");
               }
            }
            
        }
    }
    
    public ResultBean verifyQueryOrder(QueryBean queryBean) {
        ResultBean resultBean = null;
        try {
            byte[] paraMsg = GateWayTradeAnalyzer.generateOrderParamer(queryBean);
            String accessType = queryBean.getAccessType();
            if(accessType.equals("0")){//普通商户直连接入
            	MerchMK merchMk = merchMKService.get(queryBean.getMerId());
                if(merchMk==null){
                    resultBean = new ResultBean("RC02","商户不存在");
                    return resultBean;
                }
                String publicKey = "";// 获取公钥方法暂无，商户信息中提供
                if("01".equals(merchMk.getSafeType())){
                    publicKey=merchMk.getMemberPubKey();
                }
                if(!RSAUtils.verify(paraMsg, publicKey, queryBean.getSignature())){
                    resultBean = new ResultBean("RC03", "验签失败");
                    return resultBean;
                }else{
                    resultBean = new ResultBean("success");
                }
            }else{
            	TerminalAccessType terminalAccessType = TerminalAccessType.MERPORTAL;
            	CoopInstiMK coopInstiMK = coopInstiService.getCoopInstiMK(queryBean.getCoopInstiId(), terminalAccessType);
            	if(coopInstiMK==null){
                    resultBean = new ResultBean("RC02","商户不存在");
                    return resultBean;
                }
            	String  publicKey=coopInstiMK.getInstiPubKey();
                if(!RSAUtils.verify(paraMsg, publicKey, queryBean.getSignature())){
                    resultBean = new ResultBean("RC03", "验签失败");
                    return resultBean;
                }else{
                    resultBean = new ResultBean("success");
                }
            }
            
            return resultBean;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            resultBean = new ResultBean("RC03", "验签失败");
        }
        return resultBean;
    }
    
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public List<TxnsOrderinfoModel> getSecondPayOrder(String orderno,String ordercommitime,String firmemberno){
        return (List<TxnsOrderinfoModel>) super.queryByHQL("from TxnsOrderinfoModel where orderno = ? and ordercommitime = ? and  firmemberno=?", new Object[]{orderno,ordercommitime,firmemberno});
    }
    @Transactional(propagation=Propagation.REQUIRED)
    public String getDefaultVerInfo(String instiCode,String busicode,int verType) throws TradeException{
    	List<Map<String, Object>> resultList = (List<Map<String, Object>>) super.queryBySQL("select COOP_INSTI_CODE,BUSI_CODE,VER_TYPE,VER_VALUE from T_NONMER_DEFAULT_CONFIG where COOP_INSTI_CODE=? and BUSI_CODE=? and VER_TYPE=?", new Object[]{instiCode,busicode,verType+""});
    	if(resultList.size()>0){
    		Map<String, Object> valueMap = resultList.get(0);
    		return valueMap.get("VER_VALUE").toString();
    	}
    	throw new TradeException("GW03");
		//return null;
    }
    
    public String dealWithOrder(OrderBean order,RiskRateInfoBean riskRateInfoBean) throws TradeException{
        List<TxnsOrderinfoModel> orderinfoList =  getSecondPayOrder(order.getOrderId(),order.getTxnTime(),riskRateInfoBean.getMerUserId());
        if(orderinfoList.size()==1){
            TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(orderinfoList.get(0).getRelatetradetxn());
            String orign_memberId = txnsLog.getAccmemberid();
            String new_memberId = riskRateInfoBean.getMerUserId();
            if(!orign_memberId.equals(new_memberId)){
                throw new TradeException("T036");
            }
            return orderinfoList.get(0).getRelatetradetxn();
        }
        
        TxncodeDefModel busiModel = txncodeDefService.getBusiCode(order.getTxnType(), order.getTxnSubType(), order.getBizType());
        if(busiModel==null){
            return "";
        }
        TxnsLogModel txnsLog = new TxnsLogModel();
        PojoMerchDeta member = null;
        if(StringUtil.isNotEmpty(order.getMerId())){//商户为空时，取商户的各个版本信息
        	
        	member =  merchService.getMerchBymemberId(order.getMerId());
        	//member = memberService2.getMbmberByMemberId(order.getMerId(),MemberType.);
        	txnsLog.setRiskver(member.getRiskVer());
            txnsLog.setSplitver(member.getSpiltVer());
            txnsLog.setFeever(member.getFeeVer());
            txnsLog.setPrdtver(member.getPrdtVer());
           
            txnsLog.setRoutver(member.getRoutVer());
            txnsLog.setAccordinst(member.getParent());
            txnsLog.setAccsettledate(DateUtil.getSettleDate(Integer.valueOf(member.getSetlCycle().toString())));
        }else{
        	//10-产品版本,11-扣率版本,12-分润版本,13-风控版本,20-路由版本
        	txnsLog.setRiskver(getDefaultVerInfo(order.getCoopInstiId(),busiModel.getBusicode(),13));
            txnsLog.setSplitver(getDefaultVerInfo(order.getCoopInstiId(),busiModel.getBusicode(),12));
            txnsLog.setFeever(getDefaultVerInfo(order.getCoopInstiId(),busiModel.getBusicode(),11));
            txnsLog.setPrdtver(getDefaultVerInfo(order.getCoopInstiId(),busiModel.getBusicode(),10));
            txnsLog.setRoutver(getDefaultVerInfo(order.getCoopInstiId(),busiModel.getBusicode(),20));
            txnsLog.setAccsettledate(DateUtil.getSettleDate(1));
        }
        txnsLog.setTxndate(DateUtil.getCurrentDate());
        txnsLog.setTxntime(DateUtil.getCurrentTime());
        txnsLog.setBusicode(busiModel.getBusicode());
        txnsLog.setBusitype(busiModel.getBusitype());
        //核心交易流水号，交易时间（yymmdd）+业务代码+6位流水号（每日从0开始）
        txnsLog.setTxnseqno(OrderNumber.getInstance().generateTxnseqno(txnsLog.getBusicode()));
        txnsLog.setAmount(Long.valueOf(order.getTxnAmt()));
        txnsLog.setAccordno(order.getOrderId());
        txnsLog.setAccfirmerno(order.getCoopInstiId());
        txnsLog.setAcccoopinstino(order.getCoopInstiId());
        txnsLog.setAccsecmerno(order.getMerId());
        txnsLog.setAccordcommitime(DateUtil.getCurrentDateTime());
        txnsLog.setTradestatflag("00000000");//交易初始状态
        //txnsLog.setTradcomm(GateWayTradeAnalyzer.generateCommAmt(order.getReserved()));
        if(StringUtil.isNotEmpty(riskRateInfoBean.getMerUserId())){
        	PersonManager personMemeber = personService.getPersonBeanByMemberId(riskRateInfoBean.getMerUserId());
            //PojoMember personMemeber= memberService2.getMbmberByMemberId(riskRateInfoBean.getMerUserId(), MemberType.INDIVIDUAL);
            if(personMemeber!=null){
                txnsLog.setAccmemberid(riskRateInfoBean.getMerUserId());
            }else{
                txnsLog.setAccmemberid("999999999999999");//匿名会员号
            }
        }
        //记录分账信息和交易佣金
        if("01".equals(order.getTxnType())&&"99".equals(order.getTxnSubType())){
            if(StringUtil.isNotEmpty(order.getReserved())){
                List<SplitAcctBean> resultList =  JSON.parseArray(order.getReserved(), SplitAcctBean.class);
                Long tradecomm = txnsSplitAccountService.saveTxnsSplitAccount(resultList, txnsLog.getTxnseqno());
                txnsLog.setTradcomm(tradecomm);//佣金之和
            }
        }else{
            if(StringUtil.isNotEmpty(order.getReserved())){
                SplitAcctBean splitAcctBean = JSON.parseObject(order.getReserved(), SplitAcctBean.class);
                txnsLog.setTradcomm(Long.valueOf(splitAcctBean.getCommAmt()));//单一收款商户佣金
            }else{
                txnsLog.setTradcomm(0L);
            }
        }
        
        
        
        //记录订单流水
        TxnsOrderinfoModel orderinfo = new TxnsOrderinfoModel();
        orderinfo.setId(-1L);
        orderinfo.setOrderno(order.getOrderId());//商户提交的订单号
        orderinfo.setOrderamt(Long.valueOf(order.getTxnAmt()));
        orderinfo.setOrderfee(txnsLog.getTxnfee());
        orderinfo.setOrdercommitime(order.getTxnTime());
        orderinfo.setRelatetradetxn(txnsLog.getTxnseqno());//关联的交易流水表中的交易序列号
        orderinfo.setFirmemberno(order.getCoopInstiId());
        orderinfo.setFirmembername(coopInstiService.getInstiByInstiCode(order.getCoopInstiId()).getInstiName());
        if(StringUtil.isNotEmpty(order.getMerId())){
        	orderinfo.setSecmemberno(order.getMerId());
            orderinfo.setSecmembername(StringUtil.isNotEmpty(order.getMerName())?order.getMerName():member.getAccName());
        }
        
        orderinfo.setSecmembershortname(order.getMerAbbr());
        orderinfo.setPayerip(order.getCustomerIp());
        orderinfo.setAccesstype(order.getAccessType());
        //商品信息
        try {
            if(riskRateInfoBean!=null){
                orderinfo.setGoodsname(riskRateInfoBean.getCommodityName());
                orderinfo.setGoodstype(riskRateInfoBean.getCommodityCategory());
                orderinfo.setGoodsnum(Long.valueOf(riskRateInfoBean.getCommodityQty()));
                orderinfo.setGoodsprice(Long.valueOf(riskRateInfoBean.getCommodityUnitPrice()));
                orderinfo.setStatus("01");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new TradeException("GW08");
        }
        orderinfo.setFronturl(order.getFrontUrl());
        orderinfo.setBackurl(order.getBackUrl());
        
        orderinfo.setTxntype(order.getTxnType());
        orderinfo.setTxnsubtype(order.getTxnSubType());
        orderinfo.setBiztype(order.getBizType());
        orderinfo.setCustomerInfo(order.getCustomerInfo());
        //orderinfo.setTn(OrderNumber.getInstance().generateTN(order.getMerId()));
        orderinfo.setOrderdesc(order.getOrderDesc());
        orderinfo.setReqreserved(order.getReqReserved());
        orderinfo.setReserved(order.getReserved());
        orderinfo.setPaytimeout(order.getPayTimeout());
        orderinfo.setTn(OrderNumber.getInstance().generateTN(order.getCoopInstiId()));
        orderinfo.setMemberid(riskRateInfoBean.getMerUserId());
        orderinfo.setCurrencycode("156");
        saveOrderInfo(orderinfo);
        txnsLogService.saveTxnsLog(txnsLog);
        
        return txnsLog.getTxnseqno();
    }
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public String dealWithMerchOrder(OrderBean order) throws TradeException{
        List<TxnsOrderinfoModel> orderinfoList =  getSecondPayOrder(order.getOrderId(),order.getTxnTime(),order.getMerId());
        if(orderinfoList.size()==1){
            TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(orderinfoList.get(0).getRelatetradetxn());
            String orign_memberId = txnsLog.getAccmemberid();
            String new_memberId = order.getMerId();
            if(!orign_memberId.equals(new_memberId)){
                throw new TradeException("T036");
            }
            return orderinfoList.get(0).getRelatetradetxn();
        }
        
        TxncodeDefModel busiModel = txncodeDefService.getBusiCode(order.getTxnType(), order.getTxnSubType(), order.getBizType());
        if(busiModel==null){
            return "";
        }
        TxnsLogModel txnsLog = new TxnsLogModel();
        PojoMerchDeta member = null;
        if(StringUtil.isNotEmpty(order.getMerId())){//商户为空时，取商户的各个版本信息
        	
        	member =  merchService.getMerchBymemberId(order.getMerId());
        	//member = memberService2.getMbmberByMemberId(order.getMerId(),MemberType.);
        	txnsLog.setRiskver(member.getRiskVer());
            txnsLog.setSplitver(member.getSpiltVer());
            txnsLog.setFeever(member.getFeeVer());
            txnsLog.setPrdtver(member.getPrdtVer());
           
            txnsLog.setRoutver(member.getRoutVer());
            txnsLog.setAccordinst(member.getParent());
            txnsLog.setAccsettledate(DateUtil.getSettleDate(Integer.valueOf(member.getSetlCycle().toString())));
        }else{
        	//10-产品版本,11-扣率版本,12-分润版本,13-风控版本,20-路由版本
        	txnsLog.setRiskver(getDefaultVerInfo(order.getCoopInstiId(),busiModel.getBusicode(),13));
            txnsLog.setSplitver(getDefaultVerInfo(order.getCoopInstiId(),busiModel.getBusicode(),12));
            txnsLog.setFeever(getDefaultVerInfo(order.getCoopInstiId(),busiModel.getBusicode(),11));
            txnsLog.setPrdtver(getDefaultVerInfo(order.getCoopInstiId(),busiModel.getBusicode(),10));
            txnsLog.setRoutver(getDefaultVerInfo(order.getCoopInstiId(),busiModel.getBusicode(),20));
            txnsLog.setAccsettledate(DateUtil.getSettleDate(1));
        }
        txnsLog.setTxndate(DateUtil.getCurrentDate());
        txnsLog.setTxntime(DateUtil.getCurrentTime());
        txnsLog.setBusicode(busiModel.getBusicode());
        txnsLog.setBusitype(busiModel.getBusitype());
        //核心交易流水号，交易时间（yymmdd）+业务代码+6位流水号（每日从0开始）
        txnsLog.setTxnseqno(OrderNumber.getInstance().generateTxnseqno(txnsLog.getBusicode()));
        txnsLog.setAmount(Long.valueOf(order.getTxnAmt()));
        txnsLog.setAccordno(order.getOrderId());
        txnsLog.setAccfirmerno(order.getCoopInstiId());
        txnsLog.setAcccoopinstino(order.getCoopInstiId());
        txnsLog.setAccsecmerno(order.getMerId());
        txnsLog.setAccordcommitime(DateUtil.getCurrentDateTime());
        txnsLog.setTradestatflag("00000000");//交易初始状态
        txnsLog.setAccmemberid(order.getMerId());//商户收银台中，受理会员号为商户号
        //记录分账信息和交易佣金
        if("01".equals(order.getTxnType())&&"99".equals(order.getTxnSubType())){
            if(StringUtil.isNotEmpty(order.getReserved())){
                List<SplitAcctBean> resultList =  JSON.parseArray(order.getReserved(), SplitAcctBean.class);
                Long tradecomm = txnsSplitAccountService.saveTxnsSplitAccount(resultList, txnsLog.getTxnseqno());
                txnsLog.setTradcomm(tradecomm);//佣金之和
            }
        }else{
            if(StringUtil.isNotEmpty(order.getReserved())){
                SplitAcctBean splitAcctBean = JSON.parseObject(order.getReserved(), SplitAcctBean.class);
                txnsLog.setTradcomm(Long.valueOf(splitAcctBean.getCommAmt()));//单一收款商户佣金
            }else{
                txnsLog.setTradcomm(0L);
            }
        }
        
        
        
        //记录订单流水
        TxnsOrderinfoModel orderinfo = new TxnsOrderinfoModel();
        orderinfo.setId(-1L);
        orderinfo.setOrderno(order.getOrderId());//商户提交的订单号
        orderinfo.setOrderamt(Long.valueOf(order.getTxnAmt()));
        orderinfo.setOrderfee(txnsLog.getTxnfee());
        orderinfo.setOrdercommitime(order.getTxnTime());
        orderinfo.setRelatetradetxn(txnsLog.getTxnseqno());//关联的交易流水表中的交易序列号
        orderinfo.setFirmemberno(order.getCoopInstiId());
        orderinfo.setFirmembername(coopInstiService.getInstiByInstiCode(order.getCoopInstiId()).getInstiName());
        if(StringUtil.isNotEmpty(order.getMerId())){
        	orderinfo.setSecmemberno(order.getMerId());
            orderinfo.setSecmembername(StringUtil.isNotEmpty(order.getMerName())?order.getMerName():member.getAccName());
        }
        orderinfo.setStatus("01");
        orderinfo.setSecmembershortname(order.getMerAbbr());
        orderinfo.setPayerip(order.getCustomerIp());
        orderinfo.setAccesstype(order.getAccessType());
        orderinfo.setFronturl(order.getFrontUrl());
        orderinfo.setBackurl(order.getBackUrl());
        
        orderinfo.setTxntype(order.getTxnType());
        orderinfo.setTxnsubtype(order.getTxnSubType());
        orderinfo.setBiztype(order.getBizType());
        orderinfo.setCustomerInfo(order.getCustomerInfo());
        //orderinfo.setTn(OrderNumber.getInstance().generateTN(order.getMerId()));
        orderinfo.setOrderdesc(order.getOrderDesc());
        orderinfo.setReqreserved(order.getReqReserved());
        orderinfo.setReserved(order.getReserved());
        orderinfo.setPaytimeout(order.getPayTimeout());
        orderinfo.setTn(OrderNumber.getInstance().generateTN(order.getCoopInstiId()));
        orderinfo.setMemberid(order.getMerId());
        orderinfo.setCurrencycode("156");
        saveOrderInfo(orderinfo);
        txnsLogService.saveTxnsLog(txnsLog);
        
        return txnsLog.getTxnseqno();
    }
    
    
    
    
    
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void saveOrderInfo(TxnsOrderinfoModel orderinfo) throws TradeException{
        try {
            super.save(orderinfo);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new TradeException("T008");
        }
    }
    
    
    /**
     *
     * @param order 订单信息
     * @param riskRateInfoBean 风控信息
     * @return
     * @throws TradeException 
     */
    @Transactional
    public String dealWithWebOrder(OrderBean order,RiskRateInfoBean riskRateInfoBean) throws TradeException{
        TxncodeDefModel busiModel = txncodeDefService.getBusiCode(order.getTxnType(), order.getTxnSubType(), order.getBizType());
        if(busiModel==null){
            return "";
        }
        
        PojoMerchDeta member = null;
        TxnsLogModel txnsLog = new TxnsLogModel();
        if(StringUtil.isNotEmpty(order.getMerId())){
        	member = merchService.getMerchBymemberId(order.getMerId());
        	txnsLog.setRiskver(member.getRiskVer());
            txnsLog.setSplitver(member.getSpiltVer());
            txnsLog.setFeever(member.getFeeVer());
            txnsLog.setPrdtver(member.getPrdtVer());
            //txnsLog.setCheckstandver(member.getCashver());
            txnsLog.setRoutver(member.getRoutVer());
            txnsLog.setAccordinst(member.getParent());
            txnsLog.setAccsettledate(DateUtil.getSettleDate(Integer.valueOf(member.getSetlCycle().toString())));
        }else{//商户为空时，取默认的各个版本信息
        	txnsLog.setRiskver(getDefaultVerInfo(order.getCoopInstiId(),busiModel.getBusicode(),13));
            txnsLog.setSplitver(getDefaultVerInfo(order.getCoopInstiId(),busiModel.getBusicode(),12));
            txnsLog.setFeever(getDefaultVerInfo(order.getCoopInstiId(),busiModel.getBusicode(),11));
            txnsLog.setPrdtver(getDefaultVerInfo(order.getCoopInstiId(),busiModel.getBusicode(),10));
            txnsLog.setRoutver(getDefaultVerInfo(order.getCoopInstiId(),busiModel.getBusicode(),20));
            txnsLog.setAccsettledate(DateUtil.getSettleDate(1));
        }
        txnsLog.setTxndate(DateUtil.getCurrentDate());
        txnsLog.setTxntime(DateUtil.getCurrentTime());
        txnsLog.setBusicode(busiModel.getBusicode());
        txnsLog.setBusitype(busiModel.getBusitype());
        txnsLog.setTxnseqno(OrderNumber.getInstance().generateTxnseqno(txnsLog.getBusicode()));
        txnsLog.setAmount(Long.valueOf(order.getTxnAmt()));
        txnsLog.setAccordno(order.getOrderId());
        txnsLog.setAccfirmerno(order.getCoopInstiId());
        txnsLog.setAccsecmerno(order.getMerId());
        txnsLog.setAcccoopinstino(order.getCoopInstiId());
        txnsLog.setAccordcommitime(DateUtil.getCurrentDateTime());
        txnsLog.setTradestatflag("00000000");//交易初始状态
        //txnsLog.setTradcomm(GateWayTradeAnalyzer.generateCommAmt(order.getReserved()));
        if(StringUtil.isNotEmpty(riskRateInfoBean.getMerUserId())){
            PojoMember personMemeber= memberService2.getMbmberByMemberId(riskRateInfoBean.getMerUserId(),MemberType.INDIVIDUAL);
            if(personMemeber!=null){
                txnsLog.setAccmemberid(riskRateInfoBean.getMerUserId());
            }else{
                txnsLog.setAccmemberid("999999999999999");//匿名会员号
            }
        }
        //记录分账信息和交易佣金
        if("01".equals(order.getTxnType())&&"99".equals(order.getTxnSubType())){
            if(StringUtil.isNotEmpty(order.getReserved())){
                List<SplitAcctBean> resultList =  JSON.parseArray(order.getReserved(), SplitAcctBean.class);
                Long tradecomm = txnsSplitAccountService.saveTxnsSplitAccount(resultList, txnsLog.getTxnseqno());
                txnsLog.setTradcomm(tradecomm);//佣金之和
            }
        }else{
            if(StringUtil.isNotEmpty(order.getReserved())){
                SplitAcctBean splitAcctBean = JSON.parseObject(order.getReserved(), SplitAcctBean.class);
                txnsLog.setTradcomm(Long.valueOf(splitAcctBean.getCommAmt()));//单一收款商户佣金
            }
        }
        txnsLogService.save(txnsLog);
        
        
        
        //记录订单流水
        
        TxnsOrderinfoModel orderinfo = new TxnsOrderinfoModel();
        orderinfo.setId(OrderNumber.getInstance().generateID());
        //orderinfo.setInstitution(member.getMerchinsti());
        orderinfo.setOrderno(order.getOrderId());//商户提交的订单号
        orderinfo.setOrderamt(Long.valueOf(order.getTxnAmt()));
        orderinfo.setOrderfee(txnsLog.getTxnfee());
        orderinfo.setOrdercommitime(order.getTxnTime());
        orderinfo.setRelatetradetxn(txnsLog.getTxnseqno());//关联的交易流水表中的交易序列号
        orderinfo.setFirmemberno(order.getCoopInstiId());
        orderinfo.setFirmembername(coopInstiService.getInstiByInstiCode(order.getCoopInstiId()).getInstiName());
        if(StringUtil.isNotEmpty(order.getMerId())){
        	orderinfo.setSecmemberno(order.getMerId());
            orderinfo.setSecmembername(StringUtil.isNotEmpty(order.getMerName())?order.getMerName():member.getAccName());
        }
        orderinfo.setSecmembershortname(order.getMerAbbr());
        orderinfo.setPayerip(order.getCustomerIp());
        orderinfo.setAccesstype(order.getAccessType());
        //商品信息
        if(riskRateInfoBean!=null){
            orderinfo.setGoodsname(riskRateInfoBean.getCommodityName());
            orderinfo.setGoodstype(riskRateInfoBean.getCommodityCategory());
            orderinfo.setGoodsnum(Long.valueOf(riskRateInfoBean.getCommodityQty()));
            orderinfo.setGoodsprice(Long.valueOf(riskRateInfoBean.getCommodityUnitPrice()));
            orderinfo.setStatus("01");
        }
        orderinfo.setFronturl(order.getFrontUrl());
        orderinfo.setBackurl(order.getBackUrl());
        
        orderinfo.setTxntype(order.getTxnType());
        orderinfo.setTxnsubtype(order.getTxnSubType());
        orderinfo.setBiztype(order.getBizType());
        orderinfo.setCustomerInfo(order.getCustomerInfo());
        orderinfo.setOrderdesc(order.getOrderDesc());
        orderinfo.setReqreserved(order.getReqReserved());
        orderinfo.setReserved(order.getReserved());
        orderinfo.setPaytimeout(order.getPayTimeout());
        orderinfo.setTn(OrderNumber.getInstance().generateTN(order.getCoopInstiId()));
        orderinfo.setMemberid(riskRateInfoBean.getMerUserId());
        orderinfo.setCurrencycode("156");
        super.save(orderinfo);
        return orderinfo.getTn() ;
    }
    
    /**
     * 
     * @param order
     * @return
     * @throws TradeException 
     */
    public String dealWithWapOrder(OrderBean order) throws TradeException{
        List<TxnsOrderinfoModel> orderinfoList =  (List<TxnsOrderinfoModel>) super.queryByHQL("from TxnsOrderinfoModel where orderno = ? and ordercommitime = ? ", new Object[]{order.getOrderId(),order.getTxnTime()});
        if(orderinfoList.size()==1){
            return orderinfoList.get(0).getTn();
        }
        //记录订单信息
        TxnsOrderinfoModel orderinfo;
        try {
            TxncodeDefModel busiModel = txncodeDefService.getBusiCode(order.getTxnType(), order.getTxnSubType(), order.getBizType());
            RiskRateInfoBean riskRateInfoBean = (RiskRateInfoBean) GateWayTradeAnalyzer.generateRiskBean(order.getRiskRateInfo()).getResultObj();
            //检查商户和会员的资金账户状态
            checkBusiAcct(order.getMerId(), riskRateInfoBean.getMerUserId());
            PojoMerchDeta member = null;
            TxnsLogModel txnsLog = new TxnsLogModel();
            if(StringUtil.isNotEmpty(order.getMerId())){//商户为空时，取商户的各个版本信息
            	member = merchService.getMerchBymemberId(order.getMerId());
            	txnsLog.setRiskver(member.getRiskVer());
                txnsLog.setSplitver(member.getSpiltVer());
                txnsLog.setFeever(member.getFeeVer());
                txnsLog.setPrdtver(member.getPrdtVer());
               // txnsLog.setCheckstandver(member.getCashver());
                txnsLog.setRoutver(member.getRoutVer());
                txnsLog.setAccordinst(member.getParent());
                txnsLog.setAccsettledate(DateUtil.getSettleDate(Integer.valueOf(member.getSetlCycle().toString())));
            }else{
            	txnsLog.setRiskver(getDefaultVerInfo(order.getCoopInstiId(),busiModel.getBusicode(),13));
                txnsLog.setSplitver(getDefaultVerInfo(order.getCoopInstiId(),busiModel.getBusicode(),12));
                txnsLog.setFeever(getDefaultVerInfo(order.getCoopInstiId(),busiModel.getBusicode(),11));
                txnsLog.setPrdtver(getDefaultVerInfo(order.getCoopInstiId(),busiModel.getBusicode(),10));
                txnsLog.setRoutver(getDefaultVerInfo(order.getCoopInstiId(),busiModel.getBusicode(),20));
                txnsLog.setAccsettledate(DateUtil.getSettleDate(1));
            }
            txnsLog.setTxndate(DateUtil.getCurrentDate());
            txnsLog.setTxntime(DateUtil.getCurrentTime());
            txnsLog.setBusicode(busiModel.getBusicode());
            txnsLog.setBusitype(busiModel.getBusitype());
            //核心交易流水号，交易时间（yymmdd）+业务代码+6位流水号（每日从0开始）
            txnsLog.setTxnseqno(OrderNumber.getInstance().generateTxnseqno(txnsLog.getBusicode()));
            txnsLog.setAmount(Long.valueOf(order.getTxnAmt()));
            txnsLog.setAccordno(order.getOrderId());
            txnsLog.setAccfirmerno(order.getCoopInstiId());
            txnsLog.setAccsecmerno(order.getMerId());
            txnsLog.setAcccoopinstino(order.getCoopInstiId());
            txnsLog.setAccordcommitime(DateUtil.getCurrentDateTime());
            txnsLog.setTradestatflag("00000000");//交易初始状态
            
          //txnsLog.setTradcomm(GateWayTradeAnalyzer.generateCommAmt(order.getReserved()));
            if(StringUtil.isNotEmpty(riskRateInfoBean.getMerUserId())){
                PojoMember personMemeber= memberService2.getMbmberByMemberId(riskRateInfoBean.getMerUserId(), MemberType.INDIVIDUAL);
                if(personMemeber!=null){
                    txnsLog.setAccmemberid(riskRateInfoBean.getMerUserId());
                }else{
                    txnsLog.setAccmemberid("999999999999999");//匿名会员号
                }
            }
            //记录分账信息和交易佣金
            if("01".equals(order.getTxnType())&&"99".equals(order.getTxnSubType())){
                if(StringUtil.isNotEmpty(order.getReserved())){
                    List<SplitAcctBean> resultList =  JSON.parseArray(order.getReserved(), SplitAcctBean.class);
                    Long tradecomm = txnsSplitAccountService.saveTxnsSplitAccount(resultList, txnsLog.getTxnseqno());
                    txnsLog.setTradcomm(tradecomm);//佣金之和
                }
            }else{
                if(StringUtil.isNotEmpty(order.getReserved())){
                    SplitAcctBean splitAcctBean = JSON.parseObject(order.getReserved(), SplitAcctBean.class);
                    txnsLog.setTradcomm(Long.valueOf(splitAcctBean.getCommAmt()));//单一收款商户佣金
                }else{
                    txnsLog.setTradcomm(0L);
                }
            }
            txnsLogService.save(txnsLog);
            
            orderinfo = new TxnsOrderinfoModel();
            orderinfo.setId(OrderNumber.getInstance().generateID());
            //orderinfo.setInstitution(member.getMerchinsti());
            orderinfo.setOrderno(order.getOrderId());//商户提交的订单号
            orderinfo.setOrderamt(Long.valueOf(order.getTxnAmt()));
            orderinfo.setOrderfee(txnsLog.getTxnfee());
            orderinfo.setOrdercommitime(order.getTxnTime());
            orderinfo.setRelatetradetxn(txnsLog.getTxnseqno());//关联的交易流水表中的交易序列号
            orderinfo.setFirmemberno(order.getCoopInstiId());
            orderinfo.setFirmembername(coopInstiService.getInstiByInstiCode(order.getCoopInstiId()).getInstiName());
            if(StringUtil.isNotEmpty(order.getMerId())){
            	orderinfo.setSecmemberno(order.getMerId());
                orderinfo.setSecmembername(StringUtil.isNotEmpty(order.getMerName())?order.getMerName():member.getAccName());
            }
            orderinfo.setSecmembershortname(order.getMerAbbr());
            orderinfo.setPayerip(order.getCustomerIp());
            orderinfo.setAccesstype(order.getAccessType());
            //商品信息
            if(riskRateInfoBean!=null){
                orderinfo.setGoodsname(riskRateInfoBean.getCommodityName());
                orderinfo.setGoodstype(riskRateInfoBean.getCommodityCategory());
                orderinfo.setGoodsnum(Long.valueOf(riskRateInfoBean.getCommodityQty()));
                orderinfo.setGoodsprice(Long.valueOf(riskRateInfoBean.getCommodityUnitPrice()));
                orderinfo.setStatus("01");
            }
            orderinfo.setFronturl(order.getFrontUrl());
            orderinfo.setBackurl(order.getBackUrl());
            
            orderinfo.setTxntype(order.getTxnType());
            orderinfo.setTxnsubtype(order.getTxnSubType());
            orderinfo.setBiztype(order.getBizType());
            orderinfo.setCustomerInfo(order.getCustomerInfo());
            orderinfo.setTn(OrderNumber.getInstance().generateTN(order.getCoopInstiId()));
            orderinfo.setReqreserved(order.getReqReserved());
            orderinfo.setReserved(order.getReserved());
            orderinfo.setOrderdesc(order.getOrderDesc());
            orderinfo.setPaytimeout(order.getPayTimeout());
            orderinfo.setMemberid(riskRateInfoBean.getMerUserId());
            orderinfo.setCurrencycode("156");
            super.save(orderinfo);
            return orderinfo.getTn() ;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new TradeException("T008");
        }
    }
    @Transactional
    public String dealWithRefundOrder(WapRefundBean refundBean) throws TradeException {
        TxnsOrderinfoModel withdrawOrderinfo =  super.getUniqueByHQL("from TxnsOrderinfoModel where orderno = ? ", new Object[]{refundBean.getOrderId()});
        if(withdrawOrderinfo!=null){
            if("00".equals(withdrawOrderinfo.getStatus())){//提现成功
                throw new TradeException("T028");
            }else if("02".equals(withdrawOrderinfo.getStatus())){//提现中，审核中
                throw new TradeException("T031");
            }else{
                throw new TradeException("T032");//
            }
        }
        
        
        TxnsLogModel old_txnsLog = txnsLogService.queryLogByTradeseltxn(refundBean.getOrigOrderId());
        if(old_txnsLog==null){
            throw new TradeException("GW14");
        }
        TxnsOrderinfoModel old_orderInfo = getOrderinfoByOrderNo(old_txnsLog.getAccordno());
        if(old_orderInfo==null){
            throw new TradeException("GW15");
        }
        
        try {
            Long old_amount = old_orderInfo.getOrderamt();
            Long refund_amount = Long.valueOf(refundBean.getTxnAmt());
            if(refund_amount>old_amount){
                throw new TradeException("T021");
            }else if(refund_amount==old_amount){//原始订单退款(全额退款)
                //具体的处理方法暂时不明
            }else if(refund_amount<old_amount){//部分退款
               //具体的处理方法暂时不明
            }
        } catch (NumberFormatException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            throw new TradeException("T020");
        }
        
        
        PojoMerchDeta member=null;
        TxnsLogModel txnsLog=null;
        try {
            TxncodeDefModel busiModel = txncodeDefService.getBusiCode(refundBean.getTxnType(), refundBean.getTxnSubType(), refundBean.getBizType());
            if(busiModel==null){
                throw new TradeException("");
            }
            //member = memberService.get(refundBean.getCoopInstiId());
            txnsLog = new TxnsLogModel();
            if(StringUtil.isNotEmpty(refundBean.getMerId())){//商户为空时，取商户的各个版本信息
            	member = merchService.getMerchBymemberId(refundBean.getMerId());
            	txnsLog.setRiskver(member.getRiskVer());
                txnsLog.setSplitver(member.getSpiltVer());
                txnsLog.setFeever(member.getFeeVer());
                txnsLog.setPrdtver(member.getPrdtVer());
                //txnsLog.setCheckstandver(member.getCashver());
                txnsLog.setRoutver(member.getRoutVer());
                txnsLog.setAccordinst(member.getParent()+"");
                txnsLog.setAccsettledate(DateUtil.getSettleDate(Integer.valueOf(member.getSetlCycle().toString())));
            }else{
            	txnsLog.setRiskver(getDefaultVerInfo(refundBean.getCoopInstiId(),busiModel.getBusicode(),13));
                txnsLog.setSplitver(getDefaultVerInfo(refundBean.getCoopInstiId(),busiModel.getBusicode(),12));
                txnsLog.setFeever(getDefaultVerInfo(refundBean.getCoopInstiId(),busiModel.getBusicode(),11));
                txnsLog.setPrdtver(getDefaultVerInfo(refundBean.getCoopInstiId(),busiModel.getBusicode(),10));
                txnsLog.setRoutver(getDefaultVerInfo(refundBean.getCoopInstiId(),busiModel.getBusicode(),20));
                txnsLog.setAccsettledate(DateUtil.getSettleDate(1));
            }
            
            txnsLog.setTxndate(DateUtil.getCurrentDate());
            txnsLog.setTxntime(DateUtil.getCurrentTime());
            txnsLog.setBusicode(busiModel.getBusicode());
            txnsLog.setBusitype(busiModel.getBusitype());
            //核心交易流水号，交易时间（yymmdd）+业务代码+6位流水号（每日从0开始）
            txnsLog.setTxnseqno(OrderNumber.getInstance().generateTxnseqno(txnsLog.getBusicode()));
            txnsLog.setAmount(Long.valueOf(refundBean.getTxnAmt()));
            txnsLog.setAccordno(refundBean.getOrderId());
            txnsLog.setAccfirmerno(refundBean.getCoopInstiId());
            txnsLog.setAccsecmerno(refundBean.getMerId());
            txnsLog.setAcccoopinstino(refundBean.getCoopInstiId());
            txnsLog.setTxnseqnoOg(old_txnsLog.getTxnseqno());
            txnsLog.setAccordcommitime(refundBean.getTxnTime());
            txnsLog.setTradestatflag("00000000");//交易初始状态
            txnsLog.setAccsettledate(DateUtil.getSettleDate(Integer.valueOf(member.getSetlCycle().toString())));
            txnsLog.setAccmemberid(refundBean.getMemberId());
            txnsLogService.save(txnsLog);
            
            //退款账务处理
            TradeInfo tradeInfo = new TradeInfo();
            tradeInfo.setPayMemberId(refundBean.getMerId());
            tradeInfo.setPayToMemberId(refundBean.getMemberId());
            tradeInfo.setAmount(new BigDecimal(refundBean.getTxnAmt()));
            tradeInfo.setCharge(new BigDecimal(txnsLog.getTxnfee()));
            tradeInfo.setTxnseqno(txnsLog.getTxnseqno());
            //记录分录流水
            accEntryService.accEntryProcess(tradeInfo);
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new TradeException("T016");
        } catch (AccBussinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AbstractBusiAcctException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
       String tn = "";
        try {
            //保存订单信息
            TxnsOrderinfoModel orderinfo = new TxnsOrderinfoModel();
            orderinfo.setId(OrderNumber.getInstance().generateID());
           // orderinfo.setInstitution(member.getMerchinsti());
            orderinfo.setOrderno(refundBean.getOrderId());//商户提交的订单号
            orderinfo.setOrderamt(Long.valueOf(refundBean.getTxnAmt()));
            orderinfo.setOrdercommitime(refundBean.getTxnTime());
            orderinfo.setRelatetradetxn(txnsLog.getTxnseqno());//关联的交易流水表中的交易序列号
            orderinfo.setFirmemberno(refundBean.getCoopInstiId());
            orderinfo.setFirmembername(coopInstiService.getInstiByInstiCode(refundBean.getCoopInstiId()).getInstiName());
            orderinfo.setSecmemberno(refundBean.getMerId());
            orderinfo.setSecmembername(member==null?"":member.getAccName());
            orderinfo.setBackurl(refundBean.getBackUrl());
            orderinfo.setTxntype(refundBean.getTxnType());
            orderinfo.setTxnsubtype(refundBean.getTxnSubType());
            orderinfo.setBiztype(refundBean.getBizType());
            orderinfo.setReqreserved(refundBean.getReqReserved());
            orderinfo.setOrderdesc(refundBean.getOrderDesc());
            orderinfo.setAccesstype(refundBean.getAccessType());
            orderinfo.setTn(OrderNumber.getInstance().generateTN(txnsLog.getAccfirmerno()));
            orderinfo.setStatus("02");
            orderinfo.setMemberid(refundBean.getMemberId());
            orderinfo.setCurrencycode("156");
            super.save(orderinfo);
            tn = orderinfo.getTn();
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new TradeException("T020");
        } catch (Exception e) {
            e.printStackTrace();
            throw new TradeException("T018");
        }
        
        try {
            //无异常时保存退款交易流水表，以便于以后退款审核操作
            TxnsRefundModel refundOrder = new TxnsRefundModel(refundBean,old_txnsLog.getTxnseqno(), old_txnsLog.getAmount()+"", txnsLog.getTxnseqno());
            txnsRefundService.saveRefundOrder(refundOrder);
            return tn;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new TradeException("T022");
        }
        
    }
    
    @Transactional
    public String dealWithWithdrawOrder(WapWithdrawBean withdrawBean,WapWithdrawAccBean withdrawAccBean) throws TradeException {
        TxnsOrderinfoModel withdrawOrderinfo =  super.getUniqueByHQL("from TxnsOrderinfoModel where orderno = ? ", new Object[]{withdrawBean.getOrderId()});
        if(withdrawOrderinfo!=null){
            if("00".equals(withdrawOrderinfo.getStatus())){//提现成功
                throw new TradeException("T024");
            }else if("02".equals(withdrawOrderinfo.getStatus())){//提现中，审核中
                throw new TradeException("T027");
            }else{
                throw new TradeException("T023");//
            }
        }
        //判断资金账户余额是否充足
        List<BusiAcct> busiAccList =  accountQueryService.getBusiACCByMid(withdrawBean.getMemberId());
        BusiAcct fundAcct = null;
        //取得资金账户
        for(BusiAcct busiAcct : busiAccList){
            if(Usage.BASICPAY==busiAcct.getUsage()){
                fundAcct = busiAcct;
            }
        }
        BusiAcctQuery memberAcct = accountQueryService.getMemberQueryByID(fundAcct.getBusiAcctCode());
        Long balance=  memberAcct.getBalance().getAmount().longValue();
        Long amount = Long.valueOf( withdrawBean.getAmount());
        if(amount.longValue()>balance.longValue()){
            throw new TradeException("T025");
        }
        
        //记录订单信息
        TxnsOrderinfoModel orderinfo = null;
        
        PojoMerchDeta member = null;
        TxnsLogModel txnsLog = null;
            try {
                TxncodeDefModel busiModel = txncodeDefService.getBusiCode(withdrawBean.getTxnType(), withdrawBean.getTxnSubType(), withdrawBean.getBizType());
                //member = memberService.get(withdrawBean.getCoopInstiId());
                txnsLog = new TxnsLogModel();
                if(StringUtil.isNotEmpty(withdrawBean.getMerId())){//商户为空时，取商户的各个版本信息
                	member = merchService.getMerchBymemberId(withdrawBean.getMerId());
                	txnsLog.setRiskver(member.getRiskVer());
                    txnsLog.setSplitver(member.getSpiltVer());
                    txnsLog.setFeever(member.getFeeVer());
                    txnsLog.setPrdtver(member.getPrdtVer());
                    //txnsLog.setCheckstandver(member.getCashver());
                    txnsLog.setRoutver(member.getRoutVer());
                    txnsLog.setAccordinst(member.getParent());
                    txnsLog.setAccsettledate(DateUtil.getSettleDate(Integer.valueOf(member.getSetlCycle().toString())));
                }else{
                	txnsLog.setRiskver(getDefaultVerInfo(withdrawBean.getCoopInstiId(),busiModel.getBusicode(),13));
                    txnsLog.setSplitver(getDefaultVerInfo(withdrawBean.getCoopInstiId(),busiModel.getBusicode(),12));
                    txnsLog.setFeever(getDefaultVerInfo(withdrawBean.getCoopInstiId(),busiModel.getBusicode(),11));
                    txnsLog.setPrdtver(getDefaultVerInfo(withdrawBean.getCoopInstiId(),busiModel.getBusicode(),10));
                    txnsLog.setRoutver(getDefaultVerInfo(withdrawBean.getCoopInstiId(),busiModel.getBusicode(),20));
                    txnsLog.setAccsettledate(DateUtil.getSettleDate(1));
                }
                
                txnsLog.setTxndate(DateUtil.getCurrentDate());
                txnsLog.setTxntime(DateUtil.getCurrentTime());
                txnsLog.setBusicode(busiModel.getBusicode());
                txnsLog.setBusitype(busiModel.getBusitype());
                txnsLog.setTxnseqno(OrderNumber.getInstance().generateTxnseqno(txnsLog.getBusicode()));
                txnsLog.setAmount(Long.valueOf(withdrawBean.getAmount()));
                txnsLog.setAccordno(withdrawBean.getOrderId());
                txnsLog.setAccfirmerno(withdrawBean.getCoopInstiId());
                txnsLog.setAccsecmerno(withdrawBean.getMerId());
                txnsLog.setAcccoopinstino(withdrawBean.getCoopInstiId());
                txnsLog.setAccordcommitime(withdrawBean.getTxnTime());
                txnsLog.setTradestatflag("00000000");//交易初始状态
                txnsLog.setAccmemberid(withdrawBean.getMemberId());
                txnsLogService.save(txnsLog);
                
                //提现账务处理
                TradeInfo tradeInfo = new TradeInfo();
                tradeInfo.setPayMemberId(txnsLog.getAccmemberid());
                tradeInfo.setPayToMemberId(txnsLog.getAccmemberid());
                tradeInfo.setAmount(new BigDecimal(txnsLog.getAmount()));
                tradeInfo.setCharge(new BigDecimal(txnsLog.getTxnfee() == null ? 0L : txnsLog.getTxnfee()));
                tradeInfo.setTxnseqno(txnsLog.getTxnseqno());
                tradeInfo.setBusiCode(BusinessCodeEnum.WITHDRAWALS.getBusiCode());
                //记录分录流水
                accEntryService.accEntryProcess(tradeInfo);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw new TradeException("T016");
            }
            
            
           try {
            orderinfo = new TxnsOrderinfoModel();
            orderinfo.setId(OrderNumber.getInstance().generateID());
            //orderinfo.setInstitution(member.getMerchinsti());
            orderinfo.setOrderno(withdrawBean.getOrderId());//商户提交的订单号
            orderinfo.setOrderamt(Long.valueOf(withdrawBean.getAmount()));
            orderinfo.setOrderfee(txnsLog.getTxnfee());
            orderinfo.setOrdercommitime(withdrawBean.getTxnTime());
            orderinfo.setRelatetradetxn(txnsLog.getTxnseqno());//关联的交易流水表中的交易序列号
            orderinfo.setFirmemberno(withdrawBean.getCoopInstiId());
            orderinfo.setFirmembername(coopInstiService.getInstiByInstiCode(withdrawBean.getCoopInstiId()).getInstiName());
            
            
            orderinfo.setBackurl(withdrawBean.getBackUrl());
            orderinfo.setTxntype(withdrawBean.getTxnType());
            orderinfo.setTxnsubtype(withdrawBean.getTxnSubType());
            orderinfo.setBiztype(withdrawBean.getBizType());
            orderinfo.setAccesstype(withdrawBean.getAccessType());
            orderinfo.setTn(OrderNumber.getInstance().generateTN(txnsLog.getAccfirmerno()));
            orderinfo.setMemberid(withdrawBean.getMemberId());
            orderinfo.setCurrencycode("156");
            orderinfo.setStatus("02");
            super.save(orderinfo);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new TradeException("T018");
        }
        try {
            TxnsWithdrawModel withdraw =new TxnsWithdrawModel(withdrawBean, withdrawAccBean);
            txnsWithdrawService.saveEntity(withdraw);
            return orderinfo.getTn();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new TradeException("T026");
        }
    }
    
    @Transactional
    public void saveSuccessTrade(String txnseqno,String gateWayOrderNo,ZLPayResultBean zlPayResultBean){
        TxnsLogModel txnsLog = txnsLogService.get(txnseqno);
        //txnsLog.setAccordfintime(DateUtil.getCurrentDateTime());
        txnsLog.setPayordfintime(zlPayResultBean.getPnrDate()+zlPayResultBean.getPnrTime());
        txnsLog.setRetcode("ZL00");
        txnsLog.setRetinfo("交易成功");
        txnsLog.setRetdatetime(DateUtil.getCurrentDateTime());
        txnsLog.setTradestatflag("00000001");//交易完成结束位
        txnsLog.setTradetxnflag("10000000");//证联支付快捷（基金交易）
        txnsLog.setRelate("10000000");
        txnsLog.setTxnfee(getTxnFee(txnsLog));
        txnsLog.setTradeseltxn(UUIDUtil.uuid());
        txnsLog.setPayrettsnseqno(zlPayResultBean.getPnrSeqId());
        txnsLog.setPayretcode(zlPayResultBean.getRespCode());
        txnsLog.setPayretinfo(zlPayResultBean.getRespDesc());
        txnsLogService.update(txnsLog);
        
        
        TxnsOrderinfoModel orderinfo = super.findByProperty("orderno", gateWayOrderNo).get(0);
        orderinfo.setStatus("00");
        orderinfo.setOrderfinshtime(DateUtil.getCurrentDateTime());
        super.update(orderinfo);
        
    }
    @Transactional
    public void saveSuccessReaPayTrade(String txnseqno,String gateWayOrderNo,ReaPayResultBean payResultBean){
        TxnsLogModel txnsLog = txnsLogService.get(txnseqno);
        //txnsLog.setAccordfintime(DateUtil.getCurrentDateTime());
        txnsLog.setPayordfintime(DateUtil.getCurrentDateTime());
        txnsLog.setRetcode("0000");
        txnsLog.setRetinfo("交易成功");
        txnsLog.setRetdatetime(DateUtil.getCurrentDateTime());
        txnsLog.setTradestatflag("00000001");//交易完成结束位
        txnsLog.setTradetxnflag("10000000");//证联支付快捷（基金交易）
        txnsLog.setRelate("10000000");
        txnsLog.setTxnfee(getTxnFee(txnsLog));
        txnsLog.setTradeseltxn(UUIDUtil.uuid());
        txnsLog.setPayrettsnseqno(payResultBean.getTrade_no());
        txnsLog.setPayretcode(payResultBean.getResult_code());
        txnsLog.setPayretinfo(payResultBean.getResult_msg());
        txnsLogService.update(txnsLog);
        
        
        TxnsOrderinfoModel orderinfo = super.findByProperty("orderno", gateWayOrderNo).get(0);
        orderinfo.setStatus("00");
        orderinfo.setOrderfinshtime(DateUtil.getCurrentDateTime());
        super.update(orderinfo);
        
    }
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void updateOrderToStartPay(String orderNo) throws TradeException{
        TxnsOrderinfoModel orderinfo = super.getUniqueByHQL("from TxnsOrderinfoModel where orderno=? and (status=? or status = ?)", new Object[]{orderNo,"01","03"});
        if(orderinfo==null){
            throw new TradeException("T010");
        }else{
           int rows =  super.updateByHQL("update TxnsOrderinfoModel set status = ? where orderno=? and (status=? or status = ?)", new Object[]{"02",orderNo,"01","03"});
           if(rows!=1){
               throw new TradeException("T011");
           }
        }
    }
    @Transactional
    public void updateOrderToFail(String orderNo) {
        TxnsOrderinfoModel orderinfo = getOrderinfoByOrderNo(orderNo);
        if("02".equals(orderinfo.getStatus())){
            int rows =  super.updateByHQL("update TxnsOrderinfoModel set status = ? where orderno=? ", new Object[]{"03",orderNo});
        }
    }
    
    public Object findMemberInfo(String memberId){
        
        return null;
    }
    
    public Long getTxnFee(TxnsLogModel txnsLog){
        //交易序列号，扣率版本，业务类型，交易金额，会员号，原交易序列号，卡类型 
        List<Map<String, Object>> feeList = (List<Map<String, Object>>) super.queryBySQL("select FNC_GETFEES(?,?,?,?,?,?,?) as fee from dual", 
                new Object[]{txnsLog.getTxnseqno(),txnsLog.getFeever(),txnsLog.getBusicode(),txnsLog.getAmount(),txnsLog.getAccfirmerno(),txnsLog.getTxnseqnoOg(),txnsLog.getCardtype()});
        if(feeList.size()>0){
            if(StringUtil.isNull(feeList.get(0).get("FEE"))){
                return 0L;
            }else{
                return Long.valueOf(feeList.get(0).get("FEE")+"");
            }
            
        }
        return 0L;
       
    }
    
    /**
     *
     */
   
    public void saveErrorTrade() {
        // TODO Auto-generated method stub
        
    }
    /**
     *
     * @return
     */
   
    public TxnsOrderinfoModel getOrderinfoByOrderNo(String orderNo) {
        return super.getUniqueByHQL("from TxnsOrderinfoModel where orderno = ? ", new Object[]{orderNo});
    }
    /**
    *
    * @return
    */
   @Transactional(propagation=Propagation.REQUIRES_NEW)
   public TxnsOrderinfoModel getOrderinfoByOrderNoAndMemberId(String orderNo,String memberId) {
       return super.getUniqueByHQL("from TxnsOrderinfoModel where orderno = ? and  firmemberno = ?", new Object[]{orderNo,memberId});
   }
    public TxnsOrderinfoModel getOrderinfoByTN(String tn) {
        return super.getUniqueByHQL("from TxnsOrderinfoModel where tn = ? ", new Object[]{tn});
    }
   @Transactional
    public ResultBean generateAsyncRespMessage(String orderNo,String memberId){
        ResultBean resultBean = null;
        try {
            TxnsOrderinfoModel orderinfo = getOrderinfoByOrderNoAndMemberId(orderNo,memberId);
            
            TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(orderinfo.getRelatetradetxn());
			String version = "v1.0";// 网关版本
			String encoding = "1";// 编码方式
			String certId = "";// 证书 ID
			String signature = "";// 签名
			String signMethod = "01";// 签名方法
			String merId = txnsLog.getAccfirmerno();// 商户代码
			String orderId = txnsLog.getAccordno();// 商户订单号
			String txnType = orderinfo.getTxntype();// 交易类型
			String txnSubType = orderinfo.getTxnsubtype();// 交易子类
			String bizType = orderinfo.getBiztype();// 产品类型
			String accessType = "2";// 接入类型
			String txnTime = orderinfo.getOrdercommitime();// 订单发送时间
			String txnAmt = orderinfo.getOrderamt() + "";// 交易金额
			String currencyCode = "156";// 交易币种
			String reqReserved = orderinfo.getReqreserved();// 请求方保留域
			String reserved = "";// 保留域
			String queryId = txnsLog.getTradeseltxn();// 交易查询流水号
			String respCode = txnsLog.getRetcode().trim();// 响应码
			String respMsg = txnsLog.getRetinfo().trim();// 应答信息
			String settleAmt = "";// 清算金额
			String settleCurrencyCode = "156";// 清算币种
			String settleDate = txnsLog.getAccsettledate();// 清算日期
			String traceNo = txnsLog.getTradeseltxn();// 系统跟踪号
			String traceTime = DateUtil.getCurrentDateTime();// 交易传输时间
			String exchangeDate = "";// 兑换日期
			String exchangeRate = "";// 汇率
			String accNo = "";// 账号
			String payCardType = txnsLog.getCardtype();// 支付卡类型
			String payType = txnsLog.getPaytype();// 支付方式
			String payCardNo = txnsLog.getPan();// 支付卡标识
			String payCardIssueName = "";// 支付卡名称
			String bindId = "";// 绑定标识号

			OrderAsynRespBean orderRespBean = new OrderAsynRespBean(version,
					encoding, certId, signature, signMethod, merId, orderId,
					txnType, txnSubType, bizType, accessType, txnTime, txnAmt,
					currencyCode, reqReserved, reserved, queryId, respCode,
					respMsg, settleAmt, settleCurrencyCode, settleDate,
					traceNo, traceTime, exchangeDate, exchangeRate, accNo,
					payCardType, payType, payCardNo, payCardIssueName, bindId);
			String privateKey = null;
			if (orderinfo.getAccesstype().equals("0")) {
				privateKey = merchMKService.get(orderinfo.getFirmemberno())
						.getLocalPriKey().trim();
			} else {
				//000204 无线接入
				if("000204".equals(orderinfo.getBiztype())){
					privateKey = coopInstiService.getCoopInstiMK(
							txnsLog.getAcccoopinstino(),
							TerminalAccessType.WIRELESS).getZplatformPriKey();
				}else if ("000201".equals(orderinfo.getBiztype())) {
					privateKey = coopInstiService.getCoopInstiMK(
							txnsLog.getAcccoopinstino(),
							TerminalAccessType.MERPORTAL).getZplatformPriKey();
				}else if("000202".equals(orderinfo.getBiztype())||"000203".equals(orderinfo.getBiztype())){
					privateKey = coopInstiService.getCoopInstiMK(
							txnsLog.getAcccoopinstino(),
							TerminalAccessType.WAP).getZplatformPriKey();
				}
				
			}
            resultBean = new ResultBean(GateWayTradeAnalyzer.generateAsyncOrderResult(orderRespBean, privateKey));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            resultBean = new ResultBean("RC99", "系统异常");
        }
        return resultBean;
    }
    
    public ResultBean generateRespMessage(String orderNo,String memberId){
        ResultBean resultBean = null;
        try {
            TxnsOrderinfoModel orderinfo = getOrderinfoByOrderNoAndMemberId(orderNo,memberId);
            OrderRespBean orderRespBean = new OrderRespBean();
            orderRespBean.generateDefaultRespBean();
            TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(orderinfo.getRelatetradetxn());
            String certId=orderinfo.getCertid();
            String merId=orderinfo.getFirmemberno()+"";
            String orderId=orderNo;
            String txnType=orderinfo.getTxntype();
            String txnSubType=orderinfo.getTxnsubtype();
            String bizType=orderinfo.getBiztype();
            String txnAmt=orderinfo.getOrderamt()+"";
            String reqReserved=orderinfo.getReqreserved();
            String reserved="";
            String queryId=txnsLog.getTradeseltxn();
            String respCode=txnsLog.getRetcode();
            String respMsg = txnsLog.getRetinfo();
            String accNo="";
            String tn="";
            orderRespBean.setCertId(certId);
            orderRespBean.setMerId(merId);
            orderRespBean.setOrderId(orderId);
            orderRespBean.setTxnType(txnType);
            orderRespBean.setTxnSubType(txnSubType);
            orderRespBean.setBizType(bizType);
            orderRespBean.setTxnAmt(txnAmt);
            orderRespBean.setReqReserved(reqReserved);
            orderRespBean.setReserved(reserved);
            orderRespBean.setQueryId(queryId);
            orderRespBean.setRespCode(respCode);
            orderRespBean.setRespMsg(respMsg);
            orderRespBean.setAccNo(accNo);
            orderRespBean.setTn(tn);
            String privateKey = "";
            if("0".equals(orderinfo.getAccesstype())){
            	privateKey = merchMKService.get(orderinfo.getSecmemberno()).getLocalPriKey().trim();
            }else if("2".equals(orderinfo.getAccesstype())){
            	privateKey = coopInstiService.getCoopInstiMK(orderinfo.getFirmemberno(), TerminalAccessType.INVPORTAL).getZplatformPriKey().trim();
            }
            resultBean = new ResultBean(GateWayTradeAnalyzer.generateOrderResult(orderRespBean, privateKey));
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            resultBean = new ResultBean("RC99", "系统异常");
        }
        return resultBean;
    }
    
    public OrderRespBean  generateWithdrawRespMessage(String orderNo){
        ResultBean resultBean = null;
        try {
            TxnsOrderinfoModel orderinfo = getOrderinfoByOrderNo(orderNo);
            OrderRespBean orderRespBean = new OrderRespBean();
            orderRespBean.generateDefaultRespBean();
            String certId=orderinfo.getCertid();
            String merId=orderinfo.getFirmemberno()+"";
            String orderId=orderNo;
            String txnType=orderinfo.getTxntype();
            String txnSubType=orderinfo.getTxnsubtype();
            String bizType=orderinfo.getBiztype();
            String txnAmt=orderinfo.getOrderamt()+"";
            String reqReserved=orderinfo.getReqreserved();
            String reserved=orderinfo.getReserved();
            String queryId="";
            String respCode="0000";
            String respMsg = "交易成功";
            String accNo="";
            String tn="";
            orderRespBean.setCertId(certId);
            orderRespBean.setMerId(merId);
            orderRespBean.setOrderId(orderId);
            orderRespBean.setTxnType(txnType);
            orderRespBean.setTxnSubType(txnSubType);
            orderRespBean.setBizType(bizType);
            orderRespBean.setTxnAmt(txnAmt);
            orderRespBean.setReqReserved(reqReserved);
            orderRespBean.setReserved(reserved);
            orderRespBean.setQueryId(queryId);
            orderRespBean.setRespCode(respCode);
            orderRespBean.setRespMsg(respMsg);
            orderRespBean.setAccNo(accNo);
            orderRespBean.setTn(tn);
            String privateKey = "";
            if("0".equals(orderinfo.getAccesstype())){
            	privateKey = merchMKService.get(orderinfo.getSecmemberno()).getLocalPriKey().trim();
            }else if("2".equals(orderinfo.getAccesstype())){
            	privateKey = coopInstiService.getCoopInstiMK(orderinfo.getFirmemberno(), TerminalAccessType.MERPORTAL).getZplatformPriKey().trim();
            }
            return GateWayTradeAnalyzer.generateOrderResult(orderRespBean, privateKey);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            resultBean = new ResultBean("RC99", "系统异常");
        }
        return null;
    }
    
    public ResultBean generateQueryResultBean(QueryResultBean queryResultBean){
        ResultBean resultBean = null;
        try {
            String privateKey = merchMKService.get(queryResultBean.getMerId()).getLocalPriKey().trim();
            GateWayTradeAnalyzer.generateOrderResult(queryResultBean, privateKey);
            resultBean = new ResultBean(GateWayTradeAnalyzer.generateOrderResult(queryResultBean, privateKey));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            resultBean = new ResultBean("RC99", "系统异常");
        }
        return resultBean;
    }
    
    /**
     *
     * @param txnseqno
     * @param gateWayOrderNo
     * @param zlPayResultBean
     */
   
    @Transactional
    public void saveFailTrade(String txnseqno, String gateWayOrderNo, ZLPayResultBean zlPayResultBean) {
        TxnsLogModel txnsLog = txnsLogService.get(txnseqno);
        //txnsLog.setAccordfintime(DateUtil.getCurrentDateTime());
        txnsLog.setPayordfintime(zlPayResultBean.getPnrDate()+zlPayResultBean.getPnrTime());
        txnsLog.setRetcode("ZL"+zlPayResultBean.getRespCode().substring(2));
        txnsLog.setRetinfo(zlPayResultBean.getRespDesc());
        txnsLog.setRetdatetime(DateUtil.getCurrentDateTime());
        txnsLog.setTradestatflag("00000001");//交易完成结束位
        txnsLog.setTradetxnflag("10000000");//证联支付快捷（基金交易）
        txnsLog.setRelate("10000000");
        txnsLog.setPayretcode(zlPayResultBean.getRespCode());
        txnsLog.setPayretinfo(zlPayResultBean.getRespDesc());
        txnsLogService.update(txnsLog);
        TxnsOrderinfoModel orderinfo = super.findByProperty("orderno", gateWayOrderNo).get(0);
        orderinfo.setStatus("02");
        orderinfo.setOrderfinshtime(DateUtil.getCurrentDateTime());
        super.update(orderinfo);
        
    }
    /**
     *
     * @param txnseqno
     * @param gateWayOrderNo
     * @param payResultBean
     */
   
    @Transactional
    public void saveFailReaPayTrade(String txnseqno,
            String gateWayOrderNo,
            ReaPayResultBean payResultBean) {
        TxnsLogModel txnsLog = txnsLogService.get(txnseqno);
        //txnsLog.setAccordfintime(DateUtil.getCurrentDateTime());
        txnsLog.setPayordfintime(DateUtil.getCurrentDateTime());
        PojoRspmsg rspmsg = rspmsgDAO.getRspmsgByChnlCode(ChnlTypeEnum.REAPAY, payResultBean.getResult_code());
        if(rspmsg!=null){
        	txnsLog.setRetcode(rspmsg.getWebrspcode());
            txnsLog.setRetinfo(rspmsg.getRspinfo());
        }else{
        	txnsLog.setRetcode("01HH");
            txnsLog.setRetinfo("交易失败。详情请咨询证联金融客服010-84298418");
        }
        
        txnsLog.setRetdatetime(DateUtil.getCurrentDateTime());
        txnsLog.setTradestatflag("00000001");//交易完成结束位
        txnsLog.setTradetxnflag("10000000");//证联支付快捷（基金交易）
        txnsLog.setRelate("10000000");
        txnsLog.setPayretcode(payResultBean.getResult_code());
        txnsLog.setPayretinfo(payResultBean.getResult_msg());
        txnsLog.setTradeseltxn(UUIDUtil.uuid());
        txnsLogService.update(txnsLog);
        TxnsOrderinfoModel orderinfo = super.findByProperty("orderno", gateWayOrderNo).get(0);
        orderinfo.setStatus("03");
        orderinfo.setOrderfinshtime(DateUtil.getCurrentDateTime());
        super.update(orderinfo);
    }
    
    public ResultBean decryptCustomerInfo(String memberId,String encryptData) {
        ResultBean resultBean = null;
        try {
            
            
            MerchMK merchMk = merchMKService.get(memberId);
            if(merchMk==null){
                resultBean = new ResultBean("RC02","商户不存在");
            }
            String Key = "";// 获取公钥方法暂无，商户信息中提供
            if("01".equals(merchMk.getSafeType())){
                Key=merchMk.getLocalPriKey();
            }
            //byte[] decodedData = RSAUtils.decryptByPublicKey(encryptData.getBytes(), publicKey);
            byte[] decodedData = RSAUtils.decryptByPrivateKey(encryptData.getBytes(), Key);
            resultBean = new ResultBean(new String(decodedData));
            return resultBean;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            resultBean = new ResultBean("RC03", "验签失败");
        }
        return resultBean;
    }
    
    @Transactional
    public void saveAcctTrade(String txnseqno,String gateWayOrderNo,ResultBean resultBean){
        
        log.info("--------saveAcctTrade--------");
        log.info(txnseqno);
        log.info(gateWayOrderNo);
        log.info(JSON.toJSONString(resultBean));
        TxnsLogModel txnsLog = txnsLogService.get(txnseqno);
        log.info(JSON.toJSONString(txnsLog));
        TxnsOrderinfoModel orderinfo = super.findByProperty("orderno", gateWayOrderNo).get(0);
        log.info(JSON.toJSONString(orderinfo));
        
        //txnsLog.setAccordfintime(DateUtil.getCurrentDateTime());
        txnsLog.setPayordfintime(DateUtil.getCurrentDateTime());
        if(resultBean.isResultBool()){
            txnsLog.setRetcode("0000");
            txnsLog.setRetinfo("交易成功");
            orderinfo.setStatus("00");
        }else{
            txnsLog.setRetcode("9999");
            txnsLog.setRetinfo(resultBean.getErrMsg());
            orderinfo.setStatus("03");
        }
        orderinfo.setOrderfinshtime(DateUtil.getCurrentDateTime());
        super.update(orderinfo);
        
        txnsLog.setRetdatetime(DateUtil.getCurrentDateTime());
        txnsLog.setTradestatflag("00000001");//交易完成结束位
        txnsLog.setTradetxnflag("10000000");//证联支付快捷（基金交易）
        txnsLog.setRelate("10000000");
        txnsLog.setTxnfee(getTxnFee(txnsLog));
        txnsLog.setTradeseltxn(UUIDUtil.uuid());
       
        txnsLog.setPayretcode(resultBean.getErrCode());
        txnsLog.setPayretinfo(resultBean.getErrMsg());
        txnsLogService.update(txnsLog);
        log.info(JSON.toJSONString(txnsLog));
        log.info(JSON.toJSONString(orderinfo));
    }
    /**
     *
     * @param merchId
     * @param subMerchId
     */
   
    public void verifyMerch(String merchId, String subMerchId) throws TradeException{
        if(StringUtil.isNotEmpty(subMerchId)){//商户号检查
            PojoMerchDeta merch = merchService.getMerchBymemberId(subMerchId);
            if(merch==null){
                throw new TradeException("GW06");
            }
            if(subMerchId.startsWith("2")){//对于商户会员需要进行检查
                if(merchId.equals(merch.getParent())){
                    throw new TradeException("GW07");
                }
            }
            
        }
        
    }
    /**
     *
     * @param respData
     * @param merchId
     * @return
     * @throws Exception 
     */
   
    public void signWapMessage(WapOrderRespBean orderRespBean){
        try {
            String privateKey = merchMKService.get(orderRespBean.getMerId()).getLocalPriKey().trim();
            String respData = JSON.toJSONString(orderRespBean);
            String signature = RSAUtils.sign(respData.getBytes(), privateKey);
            orderRespBean.setSignature(signature);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private String decryptData(String memberId,String encryptData) throws TradeException, UnsupportedEncodingException {
        if (log.isDebugEnabled()) {
            log.debug("利用平台私钥解密数据：开始");
            log.debug("会员号："+memberId);
            log.debug("加密信息："+encryptData);
        }
        MerchMK merchMk = merchMKService.get(memberId);
        if(merchMk==null){
            throw new TradeException("GW11");
        }
        String Key = "";// 获取公钥方法暂无，商户信息中提供
        if("01".equals(merchMk.getSafeType())){
            Key=merchMk.getLocalPriKey();
        }
        byte[] decodedData = null;
        try {
            decodedData = RSAUtils.decryptByPrivateKey(Base64Utils.decode(encryptData), Key);
        } catch (Exception e) {
            log.error(e.getMessage(),e); 
            throw new TradeException("GW12");
        }
        if (log.isDebugEnabled()) {
            log.debug("利用平台私钥解密数据：结束");
        }
        return new String(decodedData,"utf-8");
    }
    
    
    public void signWapCardSignMessage(WapDebitCardSingRespBean respBean){
        try {
            String privateKey = merchMKService.get(respBean.getVirtualId()).getLocalPriKey().trim();
            String respData = JSON.toJSONString(respBean);
            String signature = RSAUtils.sign(respData.getBytes(), privateKey);
            respBean.setSignature(signature);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * 生成订单信息
     * @param json
     * @return
     * @throws TradeException
     * @throws UnsupportedEncodingException
     */
    @Transactional
    public String createWapOrder(String json) throws TradeException, UnsupportedEncodingException{
        validateOrder(json);
        OrderBean order = null;
        order = JSON.parseObject(URLDecoder.decode(json,ENCODING),OrderBean.class);
        //订单记录和业务逻辑处理,取得商户信息，记录交易数据（核心）和订单详细信息，分析交易所属业务
        String tn =dealWithWapOrder(order);
        return tn;
    } 
    /**
     * 验证订单信息
     * @param data
     * @throws TradeException
     * @throws UnsupportedEncodingException
     */
    public void validateOrder(String data) throws TradeException, UnsupportedEncodingException{
        WapOrderBean order = JSON.parseObject(URLDecoder.decode(data,ENCODING),WapOrderBean.class);
        //非空验证，订单有效期验证
        GateWayTradeAnalyzer.validateWapOrder(order);
      //检验风控信息是否符合要求
        ResultBean riskResultBean = GateWayTradeAnalyzer.generateRiskBean(order.getRiskRateInfo());
        if(!riskResultBean.isResultBool()){//验证风控信息的正确性
            throw new TradeException("GW08");
        }
        //验证订单号是否重复
        verifyRepeatWapOrder(order.getOrderId(),order.getTxnTime(),order.getTxnAmt(),order.getMerId(),((RiskRateInfoBean)riskResultBean.getResultObj()).getMerUserId());
        //验证商户产品版本中是否有对应的业务
        prodCaseService.verifyWapBusiness(order);
        //检验一级商户和二级商户有效性
        verifyMerch(order.getMerId(), order.getSubMerId());
       
        ResultBean memberBusiResultlBean = validateWapMemberBusiness(order,(RiskRateInfoBean)riskResultBean.getResultObj());
        if(!memberBusiResultlBean.isResultBool()){
            throw new TradeException(memberBusiResultlBean.getErrCode());
        }
    }
    
    
    /**
     * 银行卡签约
     *
     * @param json
     * @return 返回绑卡标示，数据库中的主键标示
     * @throws TradeException
     */
    @Transactional
    public String bankCardSign(String json) throws TradeException{
        if (log.isDebugEnabled()) {
            log.debug("银行卡签约：开始");
        }
        WapDebitCardSignBean debitCardSign = null;
        try {
            debitCardSign= JSON.parseObject(json, WapDebitCardSignBean.class);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new TradeException("T030");
        }
        if (log.isDebugEnabled()) {
            log.debug("JSON数据转换后："+JSON.toJSON(debitCardSign));

        }
        TxnsOrderinfoModel orderinfo = getOrderinfoByTN(debitCardSign.getTn());//获取订单信息
        if (log.isDebugEnabled()) {
            log.debug("获取订单信息："+JSON.toJSON(orderinfo));
        }
        TxnsLogModel txnsLog = txnsLogService.get(orderinfo.getRelatetradetxn());//获取交易流水信息
        if (log.isDebugEnabled()) {
            log.debug("获取交易流水信息："+JSON.toJSON(orderinfo));
        }
        if(orderinfo==null){
            throw new TradeException("T003");
        }
        if("00".equals(orderinfo.getStatus())){
            throw new TradeException("T004");
        }    
        if("02".equals(orderinfo.getStatus())){
            throw new TradeException("T009");
        }
        WapCardBean cardBean = null;
        try {
            cardBean = JSON.parseObject(decryptData(debitCardSign.getVirtualId(),debitCardSign.getEncryptData()), WapCardBean.class);//卡信息
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new TradeException("T030");
        }
        if (log.isDebugEnabled()) {
            log.debug("转换后卡信息："+JSON.toJSON(cardBean));
        }
        
        /*try {
            //卡信息进行实名认证
            PojoRealnameAuth realnameAuth = new PojoRealnameAuth(cardBean);
            cmbcTransferService.realNameAuth(realnameAuth);
        } catch (CMBCTradeException e1) {
            e1.printStackTrace();
        }*/
        //获取路由信息
        ResultBean routResultBean = routeConfigService.getWapTransRout(DateUtil.getCurrentDateTime(), orderinfo.getOrderamt()+"", StringUtil.isNotEmpty(orderinfo.getFirmemberno())?orderinfo.getFirmemberno():orderinfo.getSecmemberno(), txnsLog.getBusicode(), cardBean.getCardNo());
        if (log.isDebugEnabled()) {
            log.debug("获取路由信息："+JSON.toJSON(cardBean));
        }
        if(routResultBean.isResultBool()){
            String routId = routResultBean.getResultObj().toString();
            IQuickPayTrade quickPayTrade = null;
            try {
                quickPayTrade = TradeAdapterFactory.getInstance().getQuickPayTrade(routId);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw new TradeException("T001");
            }
            TradeBean trade = new TradeBean("", orderinfo.getOrderno(), txnsLog.getAmount()+"", cardBean.getCardNo(),cardBean.getCustomerNm(),cardBean.getCertifId(), cardBean.getPhoneNo(), "", "", cardBean.getCertifTp(), "", txnsLog.getAccmemberid(), txnsLog.getTxnseqno(), txnsLog.getAccfirmerno(), "", "", txnsLog.getAccsecmerno(), "", txnsLog.getCheckstandver(),txnsLog.getBusicode(), cardBean.getCardType(), "", "", cardBean.getCvn2(), cardBean.getExpired());
            trade.setTn(debitCardSign.getTn());
            trade.setReaPayOrderNo(OrderNumber.getInstance().generateReaPayOrderId());
            trade.setMerUserId(txnsLog.getAccmemberid());
            trade.setPayinstiId(routId);
            quickPayTrade.setTradeBean(trade);
            quickPayTrade.setTradeType(TradeTypeEnum.BANKSIGN);
            QuickpayCustBean bean = new QuickpayCustBean();
            bean.setCardno(cardBean.getCardNo());
            bean.setCardtype(cardBean.getCardType());
            bean.setAccname(cardBean.getCustomerNm());
            bean.setIdtype(cardBean.getCertifTp());
            bean.setIdnum(cardBean.getCertifId());
            Map<String, Object> cardMap = routeConfigService.getCardInfo(cardBean.getCardNo());
            bean.setBankcode(cardMap.get("BANKCODE")+"");
            bean.setBankname(cardMap.get("bankname")+"");
            bean.setPhone(cardBean.getPhoneNo());
            Long bindId=memberBankCardService.saveQuickPayCust(bean);//quickpayCustService.saveQuickpayCust(trade);
            trade.setCardId(bindId);
            ResultBean resultBean = quickPayTrade.bankSign(trade);
            if (resultBean.isResultBool()) {
                if(routId.equals("93000002")||routId.equals("93000003")){
                    
                }else{
                    ReaPayResultBean payResultBean = (ReaPayResultBean) resultBean
                            .getResultObj();
                    if (!"0000".equals(payResultBean.getResult_code())) {
                        throw new TradeException("GW27");
                    }
                }
                return bindId+"";
            }
        }else {
            throw new TradeException("T001");
        }
        return "";
        
    }
    
    /**
     * 发送短信验证码,针对已经绑卡的银行卡，如果没有绑定成功则进入签约模块，如果没有绑定就掉用此方法为异常
     *
     * @param json
     * @throws TradeException
     */
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void sendSMSMessage(String json) throws TradeException{
        //verifyWapOrder(JSON.parseObject(json));
        WapSMSMessageBean smsMessageBean = JSON.parseObject(json, WapSMSMessageBean.class);
        TxnsOrderinfoModel orderinfo = getOrderinfoByTN(smsMessageBean.getTn());
        TxnsLogModel txnsLog = txnsLogService.get(orderinfo.getRelatetradetxn());
        if("00".equals(orderinfo.getStatus())){
            throw new TradeException("T004");
        }  
        
        PojoQuickpayCust  card = quickpayCustDAO.getById(Long.valueOf(smsMessageBean.getBindId()));//quickpayCustService.get(Long.valueOf(smsMessageBean.getBindId()));
        if(card==null){
            throw new TradeException("GW13");
        }else {
            String reaPayOrderNo = OrderNumber.getInstance().generateReaPayOrderId();//融宝订单号
            TradeBean trade = new TradeBean(card.getBankcode(),orderinfo.getOrderno(),orderinfo.getOrderamt()+"", card.getCardno(),card.getAccname(),card.getIdnum(), card.getPhone(), "", "", card.getIdtype(), "", "", txnsLog.getTxnseqno(), txnsLog.getAccfirmerno(), "", "", txnsLog.getAccsecmerno(), "", txnsLog.getCheckstandver(), txnsLog.getBusicode(), txnsLog.getBusitype(), card.getCardtype(), "goods", "gooddesc", card.getCvv2(), card.getValidtime(),txnsLog.getAccmemberid(),card.getBindcardid(), "", reaPayOrderNo, "", "", 0L, "", "", orderinfo.getTn(), "", "");
            String routId = null;
            ResultBean routResultBean = routeConfigService.getWapTransRout(DateUtil.getCurrentDateTime(), orderinfo.getOrderamt()+"", StringUtil.isNotEmpty(orderinfo.getFirmemberno())?orderinfo.getFirmemberno():orderinfo.getSecmemberno(), txnsLog.getBusicode(), card.getCardno());
            routId = routResultBean.getResultObj().toString();
            if(routId==null){
                throw new TradeException("T001");
            }
            trade.setTn(smsMessageBean.getTn());
            trade.setBindCardId(card.getBindcardid());
            trade.setCardId(card.getId());
            IQuickPayTrade quickPayTrade = null;
            try {
                quickPayTrade = TradeAdapterFactory.getInstance().getQuickPayTrade(routId);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw new TradeException("T001");
            }
            quickPayTrade.setTradeBean(trade);
            quickPayTrade.setTradeType(TradeTypeEnum.SENDSMS);
            ResultBean resultBean = quickPayTrade.bankSign(trade);
            if (resultBean.isResultBool()) {
                if(routId.equals("93000002")||routId.equals("93000003")){
                }else{
                    ReaPayResultBean bean = (ReaPayResultBean) resultBean
                            .getResultObj();
                    if (!"0000".equals(bean.getResult_code())) {
                        throw new TradeException("T002",bean.getResult_msg());
                    }
                }
            }
            
        }
    }
    
    /**
     * 确认支付
     *
     * @param json
     * @throws TradeException
     */
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void submitPay(String json) throws TradeException{
        WapSubmitPayBean submitPayBean = JSON.parseObject(json, WapSubmitPayBean.class);
        TxnsOrderinfoModel orderinfo = getOrderinfoByTN(submitPayBean.getTn());//原始订单信息
        TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(orderinfo.getRelatetradetxn());//交易流水信息
        String reapayOrderNo = txnsQuickpayService.getReapayOrderNo(txnsLog.getTxnseqno());
        if(orderinfo==null){
            throw new TradeException("T003");
        }
        if("00".equals(orderinfo.getStatus())){
            throw new TradeException("T004");
        }    
        if("02".equals(orderinfo.getStatus())){
            throw new TradeException("T009");
        }
        if(!orderinfo.getOrderamt().toString().equals(submitPayBean.getTxnAmt())){
            throw new TradeException("T033");
        }
        
        PojoQuickpayCust card = quickpayCustDAO.getById(Long.valueOf(submitPayBean.getBindId()));
        TradeBean trade = new TradeBean(card.getBankcode(),orderinfo.getOrderno(),orderinfo.getOrderamt()+"", card.getCardno(),card.getAccname(),card.getIdnum(), card.getPhone(),submitPayBean.getSmsCode(), "", card.getIdtype(), "", "", txnsLog.getTxnseqno(), txnsLog.getAccfirmerno(), "", "", txnsLog.getAccsecmerno(), "", txnsLog.getCheckstandver(), txnsLog.getBusicode(), txnsLog.getBusitype(), card.getCardtype(), "goods", "gooddesc", card.getCvv2(), card.getValidtime(),txnsLog.getAccmemberid(),card.getBindcardid(), "", reapayOrderNo, "", "", 0L, "", "", orderinfo.getTn(), "", "");
        ResultBean routResultBean = routeConfigService.getWapTransRout(DateUtil.getCurrentDateTime(), trade.getAmount()+"",  StringUtil.isNotEmpty(trade.getMerchId())?trade.getMerchId():trade.getSubMerchId(), trade.getBusicode(), trade.getCardNo());
        String routId = routResultBean.getResultObj().toString();
        ChannelEnmu channel = ChannelEnmu.fromValue(routId);
        if(ChannelEnmu.REAPAY==channel){//融宝渠道需要校验reapayOrderNo
            if(StringUtil.isEmpty(reapayOrderNo)){
                throw new TradeException("T006");
            }
        }
        IQuickPayTrade quickPayTrade = null;
        try {
            quickPayTrade = TradeAdapterFactory.getInstance().getQuickPayTrade(routId);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //风控
        txnsLogService.tradeRiskControl(txnsLog.getTxnseqno(),txnsLog.getAccfirmerno(),txnsLog.getAccsecmerno(),txnsLog.getAccmemberid(),txnsLog.getBusicode(),txnsLog.getAmount()+"",card.getCardtype(),card.getCardno());
        updateOrderToStartPay(orderinfo.getOrderno());
        txnsLogService.initretMsg(txnsLog.getTxnseqno());
        quickPayTrade.setTradeBean(trade);
        quickPayTrade.setTradeType(TradeTypeEnum.SUBMITPAY);
        //TradeAdapterFactory.getInstance().getThreadPool(routId).executeMission(quickPayTrade);
        ResultBean resultBean = quickPayTrade.submitPay(trade);
        if(!resultBean.isResultBool()){
            throw new TradeException("T000",resultBean.getErrCode());
        }
    }
    
    /**
     * 账户支付
     * @param json
     * @throws TradeException
     */
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void accountPay(String json) throws TradeException{
        WapAcctPayBean acctPayBean = JSON.parseObject(json, WapAcctPayBean.class);
        TxnsOrderinfoModel orderinfo = getOrderinfoByTN(acctPayBean.getTn());
        TxnsLogModel txnsLog = txnsLogService.get(orderinfo.getRelatetradetxn());
        if(orderinfo==null){
            throw new TradeException("T003");
        }
        if("00".equals(orderinfo.getStatus())){
            throw new TradeException("T004");
        }    
        if("02".equals(orderinfo.getStatus())){
            throw new TradeException("T009");
        }
        if(!orderinfo.getOrderamt().toString().equals(acctPayBean.getTxnAmt())){
            throw new TradeException("T033");
        }
        if("999999999999999".equals(acctPayBean.getMemberId())){
            throw new TradeException("AP09");
        }
        /*if(!acctPayBean.getMemberId().equals(txnsLog.getAccmemberid())){
            throw new TradeException("AP10");
        }*/
        
        
        ResultBean resultBean = null;
        try {
            AccountTradeBean accountTrade = new AccountTradeBean(acctPayBean.getMemberId(), acctPayBean.getVirtualId(), acctPayBean.getPaypassWd(), acctPayBean.getTxnAmt(), txnsLog.getTxnseqno());
            updateOrderToStartPay(orderinfo.getOrderno());
            accountPayService.accountPay(accountTrade);
            resultBean = new ResultBean("success");
        } catch (TradeException te) {
            te.printStackTrace();
            resultBean = new ResultBean(te.getCode(), te.getMessage());
        }
        saveAcctTrade(txnsLog.getTxnseqno(), orderinfo.getOrderno(), resultBean);
        if(resultBean.isResultBool()){
            ResultBean orderResp = generateRespMessage(orderinfo.getOrderno(),txnsLog.getAccfirmerno());
            if(orderResp.isResultBool()){
                TxnsOrderinfoModel gatewayOrderBean = getOrderinfoByOrderNo(orderinfo.getOrderno());
                OrderRespBean respBean = (OrderRespBean) orderResp.getResultObj();
                new SynHttpRequestThread(gatewayOrderBean.getFirmemberno(),gatewayOrderBean.getRelatetradetxn(),gatewayOrderBean.getBackurl(), respBean.getNotifyParam()).start();
            }else{
                throw new TradeException("AP07");
            }
        }else{
            throw new TradeException("AP05");
        }
    }

    /**
     * 查询会员已绑卡信息
     * @param json
     * @return
     * @throws TradeException
     */
    public String querySignInfo(String json) throws TradeException{
        WapQuerySignCardBean querySignCardBean = JSON.parseObject(json, WapQuerySignCardBean.class);
        if ("0".equals(querySignCardBean.getCardType().trim())) {
            querySignCardBean.setCardType("");
        }
        PagedResult<QuickpayCustBean> pagedResult = memberBankCardService.queryMemberBankCard(querySignCardBean.getMemberId(), querySignCardBean.getCardType(),null, 1, 10);
        //List<QuickpayCustModel> resultList = quickpayCustService.querBankCardByMemberId_Reapay(querySignCardBean.getMemberId(),querySignCardBean.getCardType());
        if(pagedResult.getTotal()==0){
            return "";
        }
        List<Map<String, String>> signCardList = new ArrayList<Map<String,String>>();
        List<QuickpayCustBean> resultList = null;
		try {
			resultList = pagedResult.getPagedResult();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        for(QuickpayCustBean card : resultList){
            Map<String, String> signCardMap = new HashMap<String, String>();
            signCardMap.put("bindId", card.getId()+"");
            signCardMap.put("cardNo", card.getCardno());
            signCardMap.put("bankCode", card.getBankcode().substring(0,4));
            signCardMap.put("bankName", card.getBankname()==null?"":card.getBankname());
            signCardMap.put("cardType", card.getCardtype());
            signCardMap.put("bindPhone", card.getPhone());
            signCardList.add(signCardMap);
        }
        return JSON.toJSONString(signCardList);
    }
    /**
     * 查询支持的银行
     * @param json
     * @return
     */
    public String queryUsableBankCard(String json){
        WapUsableBankCardBean usableBankCardBean = JSON.parseObject(json, WapUsableBankCardBean.class);
        List<CashBankModel> bankList = cashBankService.findBankByPaytype("01");//取得快捷支付支持银行列表
        List<Map<String, String>> returnList = new ArrayList<Map<String,String>>();
        for(CashBankModel bank : bankList){
            Map<String, String> bankMap = new HashMap<String, String>();
            bankMap.put("bankCode", bank.getBankcode());
            bankMap.put("bankName", bank.getBankname());
            bankMap.put("cardType", bank.getCardtype());
            returnList.add(bankMap);
        }
        return JSON.toJSONString(returnList);
    }
    
    /**
     * 退款
     * @param json
     * @return
     * @throws TradeException
     */
    @Transactional
    public String refund(String json) throws TradeException{
        WapRefundBean refundBean = JSON.parseObject(json, WapRefundBean.class);
        //记录退款订单和交易流水 退款流水表
        String tn = dealWithRefundOrder(refundBean);
        //暂时没有退款方案
        
        return tn;
    }
    /**
     * 提现
     * @param json
     * @return
     * @throws TradeException
     */
    @Transactional
    public String withdraw(String json) throws TradeException{
        WapWithdrawBean withdrawBean = JSON.parseObject(json, WapWithdrawBean.class);
        //判断账户余额是否充足
        
        
        
        WapWithdrawAccBean accBean = null;
        if(StringUtil.isNotEmpty(withdrawBean.getBindId())){//使用已绑定的卡进行提现
        	PojoQuickpayCust custCard = quickpayCustDAO.getById(Long.valueOf(withdrawBean.getBindId()));
            if(custCard==null){
                throw new TradeException("GW13");
            }
            accBean = new WapWithdrawAccBean(custCard);
            
        }else{
            try {
                accBean = JSON.parseObject(decryptData(withdrawBean.getMerId(),withdrawBean.getEncryptData()), WapWithdrawAccBean.class);
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw new TradeException("GW14");
            }
        }
        if(accBean==null){
            throw new TradeException("GW14");
        }
        //记录提现订单和交易流水 提现流水表
        String withdrawId =  dealWithWithdrawOrder(withdrawBean, accBean);
        //暂时没有提现方案
        return withdrawId;
    }
    
    public ResultBean validateMemberBusiness(OrderBean orderBean,RiskRateInfoBean rateInfoBean){
        TxncodeDefModel busiModel = txncodeDefService.getBusiCode(orderBean.getTxnType(), orderBean.getTxnSubType(), orderBean.getBizType());
        
        String memberId = rateInfoBean.getMerUserId();
        if("2000".equals(busiModel.getBusitype())){
            if(StringUtil.isEmpty(memberId)||"999999999999999".equals(memberId)){
                return new ResultBean("GW19", "会员不存在无法进行充值");
            }
        }
        
        if("10000004".equals(busiModel.getBusicode())){//消费-分账业务
            //校验分账商户是否存在
            String json = orderBean.getReserved().trim();
            List<SplitAcctBean> resultList =  JSON.parseArray(json, SplitAcctBean.class);
            for(SplitAcctBean splitAcctBean:resultList){
               
                PojoMerchDeta member = merchService.getMerchBymemberId(splitAcctBean.getMerId());
                if(member==null){
                    return new ResultBean("GW24", splitAcctBean.getMerId()+"分账会员不存在");
                }
            }
        }
        return new ResultBean("success");
    }
    
    
    private ResultBean validateWapMemberBusiness(WapOrderBean orderBean,RiskRateInfoBean rateInfoBean){
        TxncodeDefModel busiModel = txncodeDefService.getBusiCode(orderBean.getTxnType(), orderBean.getTxnSubType(), orderBean.getBizType());
        BusinessEnum businessEnum = BusinessEnum.fromValue(busiModel.getBusicode());
        String memberId = rateInfoBean.getMerUserId();
        if(businessEnum==BusinessEnum.RECHARGE){//充值
            if(StringUtil.isEmpty(memberId)||"999999999999999".equals(memberId)){
                return new ResultBean("GW19", "会员不存在无法进行充值");
            }
        }
        if(businessEnum==BusinessEnum.CONSUMESPLIT){//消费-分账业务
            //校验分账商户是否存在
            String json = orderBean.getReserved().trim();
            List<SplitAcctBean> resultList =  JSON.parseArray(json, SplitAcctBean.class);
            for(SplitAcctBean splitAcctBean:resultList){
               
                PojoMerchDeta member = merchService.getMerchBymemberId(splitAcctBean.getMerId());
                if(member==null){
                    return new ResultBean("GW19", splitAcctBean.getMerId()+"分账会员不存在");
                }
            }
        }
        return new ResultBean("success");
    }
    @Override
    public void verifyRepeatWebOrder(String orderNo,
            String txntime,
            String amount,
            String merchId,
            String memberId) throws TradeException {
        // TODO Auto-generated method stub
        TxnsOrderinfoModel orderInfo  = getOrderinfoByOrderNoAndMemberId(orderNo, merchId);
        if(orderInfo!=null){
            TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(orderInfo.getRelatetradetxn());
            if("00".equals(orderInfo.getStatus())){//交易成功订单不可二次支付
                throw new TradeException("T004");
            }
            if("02".equals(orderInfo.getStatus())){
                throw new TradeException("T009");
            }
            if("04".equals(orderInfo.getStatus())){
                throw new TradeException("T012");
            }
            if(!amount.equals(orderInfo.getOrderamt().toString())){
                throw new TradeException("T014");
            }
            if(!txntime.equals(orderInfo.getOrdercommitime())){//订单存在，提交日期也一致，二次支付订单,
                throw new TradeException("T013");
            }
            if(!merchId.equals(orderInfo.getFirmemberno())){
                throw new TradeException("T015");
            }
            if(!"999999999999999".equals(txnsLog.getAccmemberid())){//非匿名支付
               if(!txnsLog.getAccmemberid().equals(memberId)){
                   throw new TradeException("T036");
               }
            }
            
        }
    }
    
    public ResultBean bindingBankCard(String memberId,String personMemberId,WapCardBean cardBean){
    	ResultBean resultBean = null;
    	//获取路由信息
        ResultBean routResultBean = routeConfigService.getWapTransRout(DateUtil.getCurrentDateTime(), "0", memberId, BusinessEnum.RECHARGE.getBusiCode(), cardBean.getCardNo());
        if (log.isDebugEnabled()) {
            log.debug("获取路由信息："+JSON.toJSON(cardBean));
        }
        if(routResultBean.isResultBool()){
            String routId = routResultBean.getResultObj().toString();
            IQuickPayTrade quickPayTrade = null;
            try {
                quickPayTrade = TradeAdapterFactory.getInstance().getQuickPayTrade(routId);
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (TradeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            TradeBean trade = new TradeBean("", "","0", cardBean.getCardNo(),cardBean.getCustomerNm(),cardBean.getCertifId(), cardBean.getPhoneNo(), "", "", cardBean.getCertifTp(), "", personMemberId, "", memberId, "", "", memberId, "","",BusinessEnum.CONSUMEQUICK.getBusiCode(), cardBean.getCardType(), "", "", cardBean.getCvn2(), cardBean.getExpired());
            trade.setTradeType("01");//实名认证交易，不发送绑卡短信
            trade.setMerUserId(personMemberId);
            trade.setPayinstiId(routId);
            quickPayTrade.setTradeBean(trade);
            quickPayTrade.setTradeType(TradeTypeEnum.BANKSIGN);
            //Long bindId=quickpayCustService.saveQuickpayCust(trade);
            //trade.setCardId(bindId);
            resultBean = quickPayTrade.bankSign(trade);
        }
    	return resultBean;
    }
    @Transactional(readOnly=true)
    public List<Map<String, Object>> queryOrderInfo(String memberId,String beginDate,String endDate,int page,int rows){
    	List<String> paraList = new ArrayList<String>();
    	StringBuffer sqlBuffer = new StringBuffer("select orderno,orderamt,ordercommitime,status,orderdesc,tn,CURRENCYCODE ");
    	sqlBuffer.append("from t_txns_orderinfo ");
    	sqlBuffer.append("where 1=1 and ");
    	if(StringUtil.isNotEmpty(beginDate)){
    		sqlBuffer.append("ordercommitime > ? and ");
    		paraList.add(beginDate);
    	}
    	if(StringUtil.isNotEmpty(endDate)){
    		sqlBuffer.append("ordercommitime < ? and ");
    		paraList.add(endDate);
    	}
    	sqlBuffer.append("memberId = ? order by id desc");
    	paraList.add(memberId);
    	SQLQuery query = (SQLQuery) getSession().createSQLQuery(sqlBuffer.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
    	for (int i = 0; i < paraList.size(); i++) {
    		query.setParameter(i, paraList.get(i));
    	}
    	query.setFirstResult((rows)*((page==0?1:page)-1));
    	query.setMaxResults(rows);
    	return query.list();
    }
    @Transactional(readOnly=true)
    public long queryOrderInfoCount(String memberId,String beginDate,String endDate){
    	List<String> paraList = new ArrayList<String>();
    	StringBuffer sqlBuffer = new StringBuffer("select count(1) as total ");
    	sqlBuffer.append("from t_txns_orderinfo ");
    	sqlBuffer.append("where 1=1 and ");
    	if(StringUtil.isNotEmpty(beginDate)){
    		sqlBuffer.append("ordercommitime > ? and ");
    		paraList.add(beginDate);
    	}
    	if(StringUtil.isNotEmpty(endDate)){
    		sqlBuffer.append("ordercommitime < ? and ");
    		paraList.add(endDate);
    	}
    	sqlBuffer.append("memberId = ? order by id desc");
    	paraList.add(memberId);
    	SQLQuery query = (SQLQuery) getSession().createSQLQuery(sqlBuffer.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
    	for (int i = 0; i < paraList.size(); i++) {
    		query.setParameter(i, paraList.get(i));
    	}
    	Map<String, BigDecimal> valueMap = (Map<String, BigDecimal>) query.uniqueResult();
		return valueMap.get("TOTAL").longValue();
    }
	/**
	 *
	 * @param orderNo
	 * @param memberId
	 * @return
	 */
	@Override
	public TxnsOrderinfoModel getPersonOrder(String orderNo, String memberId) {
		return super.getUniqueByHQL("from TxnsOrderinfoModel where orderno = ? and  memberid = ?", new Object[]{orderNo,memberId});
	}
	@Override
	public void checkBusiAcct(String merchNo, String memberId)
			throws TradeException {
		
		if("999999999999999".equals(memberId)){
			return ;
		}
        BusiAcct fundAcct = null;
        List<BusiAcct> busiAccList = null;
        if(StringUtil.isNotEmpty(memberId)){
        	busiAccList = accountQueryService.getBusiACCByMid(memberId);
            // 取得资金账户
            for (BusiAcct busiAcct : busiAccList) {
                if (Usage.BASICPAY == busiAcct.getUsage()) {
                    fundAcct = busiAcct;
                }
            }
            BusiAcctQuery memberAcct = accountQueryService
                    .getMemberQueryByID(fundAcct.getBusiAcctCode());
            if(memberAcct.getStatus()!=AcctStatusType.NORMAL){
            	throw new TradeException("GW19");
            }
        }
        
		
        
        busiAccList = accountQueryService
                .getBusiACCByMid(merchNo);
        // 取得资金账户
        for (BusiAcct busiAcct : busiAccList) {
            if (Usage.BASICPAY == busiAcct.getUsage()) {
                fundAcct = busiAcct;
            }
        }
        BusiAcctQuery merchAcct = accountQueryService
                .getMemberQueryByID(fundAcct.getBusiAcctCode());
        if(merchAcct.getStatus()!=AcctStatusType.NORMAL){
        	throw new TradeException("GW05");
        }
	}
	
	public boolean validatePayPWD(String memberId,String pwd,MemberType memberType) throws TradeException{
    	
    	try {
			PojoMember pojo =memberDAO.getMemberByMemberId(memberId, memberType);
			MemberBean memberBean = new MemberBean();
			memberBean.setLoginName(pojo.getLoginName());
			memberBean.setInstiId(pojo.getInstiId());
			memberBean.setPaypwd(pwd);
			// 校验支付密码
			return memberOperationService.verifyPayPwd(memberType,
			        memberBean);
		} catch (DataCheckFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
    }
	
	
	@Transactional
    public String dealWithRefundOrder(String orderNo,String merchNo,String txnAmt) throws TradeException {
       
        TxnsOrderinfoModel orderinfo_old = this.getOrderinfoByOrderNoAndMemberId(orderNo, merchNo);
        
        TxnsLogModel old_txnsLog = txnsLogService.queryLogByTradeseltxn(orderinfo_old.getRelatetradetxn());
        if(old_txnsLog==null){
            throw new TradeException("GW14");
        }
        TxnsOrderinfoModel old_orderInfo = getOrderinfoByOrderNo(old_txnsLog.getAccordno());
        if(old_orderInfo==null){
            throw new TradeException("GW15");
        }
        
        try {
            Long old_amount = old_orderInfo.getOrderamt();
            Long refund_amount = Long.valueOf(txnAmt);
            if(refund_amount>old_amount){
                throw new TradeException("T021");
            }else if(refund_amount==old_amount){//原始订单退款(全额退款)
                //具体的处理方法暂时不明
            }else if(refund_amount<old_amount){//部分退款
               //具体的处理方法暂时不明
            	throw new TradeException("T021");
            }
        } catch (NumberFormatException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            throw new TradeException("T020");
        }
        
        String  orderId = OrderNumber.getInstance().generateRefundOrderNo();
        PojoMerchDeta member=null;
        TxnsLogModel txnsLog=null;
        try {
            
            //member = memberService.get(refundBean.getCoopInstiId());
            txnsLog = new TxnsLogModel();
            PojoMember mbmberd = memberService2.getMbmberByMemberId(merchNo, null);
           
            member = merchService.getMerchBymemberId(merchNo);
        	txnsLog.setRiskver(member.getRiskVer());
            txnsLog.setSplitver(member.getSpiltVer());
            txnsLog.setFeever(member.getFeeVer());
            txnsLog.setPrdtver(member.getPrdtVer());
            //txnsLog.setCheckstandver(member.getCashver());
            txnsLog.setRoutver(member.getRoutVer());
            txnsLog.setAccordinst(member.getParent()+"");
            txnsLog.setAccsettledate(DateUtil.getSettleDate(Integer.valueOf(member.getSetlCycle().toString())));
            
            
            txnsLog.setTxndate(DateUtil.getCurrentDate());
            txnsLog.setTxntime(DateUtil.getCurrentTime());
            txnsLog.setBusicode("40000001");
            txnsLog.setBusitype("4000");
            //核心交易流水号，交易时间（yymmdd）+业务代码+6位流水号（每日从0开始）
            txnsLog.setTxnseqno(OrderNumber.getInstance().generateTxnseqno(txnsLog.getBusicode()));
            txnsLog.setAmount(Long.valueOf(txnAmt));
            txnsLog.setAccordno(orderId);
            txnsLog.setAccfirmerno(old_txnsLog.getAccfirmerno());
            txnsLog.setAccsecmerno(old_txnsLog.getAccsecmerno());
            txnsLog.setAcccoopinstino(old_txnsLog.getAccfirmerno());
            txnsLog.setTxnseqnoOg(old_txnsLog.getTxnseqno());
            txnsLog.setAccordcommitime(DateUtil.getCurrentDateTime());
            txnsLog.setTradestatflag("00000000");//交易初始状态
            txnsLog.setAccsettledate(DateUtil.getSettleDate(Integer.valueOf(member.getSetlCycle().toString())));
            txnsLog.setAccmemberid(txnsLog.getAccmemberid());
            txnsLogService.save(txnsLog);
            
            //退款账务处理
            TradeInfo tradeInfo = new TradeInfo();
            tradeInfo.setPayMemberId(merchNo);
            tradeInfo.setPayToMemberId(old_txnsLog.getTxnseqno());
            tradeInfo.setAmount(new BigDecimal(txnAmt));
            tradeInfo.setCharge(new BigDecimal(txnsLog.getTxnfee()));
            tradeInfo.setTxnseqno(txnsLog.getTxnseqno());
            //记录分录流水
            accEntryService.accEntryProcess(tradeInfo);
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new TradeException("T016");
        } catch (AccBussinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AbstractBusiAcctException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
       String tn = "";
        try {
            //保存订单信息
            TxnsOrderinfoModel orderinfo = new TxnsOrderinfoModel();
            orderinfo.setId(OrderNumber.getInstance().generateID());
           
            orderinfo.setOrderno(orderId);//商户提交的订单号
            orderinfo.setOrderamt(Long.valueOf(txnAmt));
            orderinfo.setOrdercommitime(DateUtil.getCurrentDateTime());
            orderinfo.setRelatetradetxn(txnsLog.getTxnseqno());//关联的交易流水表中的交易序列号
            orderinfo.setFirmemberno(old_txnsLog.getAccfirmerno());
            orderinfo.setFirmembername(coopInstiService.getInstiByInstiCode(old_txnsLog.getAccfirmerno()).getInstiName());
            orderinfo.setSecmemberno(merchNo);
            orderinfo.setSecmembername(member==null?"":member.getAccName());
            //orderinfo.setBackurl(refundBean.getBackUrl());
            orderinfo.setTxntype("14");
            orderinfo.setTxnsubtype("00");
            orderinfo.setBiztype("000202");
            //orderinfo.setReqreserved(refundBean.getReqReserved());
            //orderinfo.setOrderdesc(refundBean.getOrderDesc());
            orderinfo.setAccesstype("0");
            orderinfo.setTn(OrderNumber.getInstance().generateTN(txnsLog.getAccfirmerno()));
            orderinfo.setStatus("02");
            orderinfo.setMemberid(old_txnsLog.getAccmemberid());
            orderinfo.setCurrencycode("156");
            super.save(orderinfo);
            tn = orderinfo.getTn();
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new TradeException("T020");
        } catch (Exception e) {
            e.printStackTrace();
            throw new TradeException("T018");
        }
        
        try {
            //无异常时保存退款交易流水表，以便于以后退款审核操作
            TxnsRefundModel refundOrder = new TxnsRefundModel();
            refundOrder.setId(OrderNumber.getInstance().generateID());
            refundOrder.setRefundorderno(OrderNumber.getInstance().generateRefundOrderNo());
            refundOrder.setOldorderno(orderNo);
            refundOrder.setOldtxnseqno(old_txnsLog.getTxnseqno());
            refundOrder.setMerchno(merchNo);
            refundOrder.setMemberid(old_txnsLog.getAccmemberid());
            refundOrder.setAmount(Long.valueOf(txnAmt));
            refundOrder.setOldamount(Long.valueOf(txnAmt));
            refundOrder.setRefundtype("");
            refundOrder.setTxntime(DateUtil.getCurrentDateTime());
            refundOrder.setReltxnseqno(txnsLog.getTxnseqno());
            txnsRefundService.saveRefundOrder(refundOrder);
            return tn;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new TradeException("T022");
        }
        
    }
	
}
