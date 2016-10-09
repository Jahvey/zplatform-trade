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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.member.bean.InduGroupMemberBean;
import com.zlebank.zplatform.member.service.IndustryGroupMemberService;
import com.zlebank.zplatform.trade.adapter.accounting.IAccounting;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.enums.BusinessEnum;
import com.zlebank.zplatform.trade.bean.enums.TradeStatFlagEnum;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.SpringContext;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月7日 上午11:48:09
 * @since 
 */
public class ConsumeAccounting implements IAccounting{
    private static final Logger log = LoggerFactory.getLogger(ConsumeAccounting.class);
    private ITxnsLogService txnsLogService = (ITxnsLogService) SpringContext.getContext().getBean("txnsLogService");
    private AccEntryService accEntryService = (AccEntryService) SpringContext.getContext().getBean("accEntryServiceImpl");
    private IndustryGroupMemberService industryGroupMemberService = (IndustryGroupMemberService) SpringContext.getContext().getBean("industryGroupMemberServiceImpl");
    /**
     *
     * @param txnseqno
     * @return
     */
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public ResultBean accountedFor(String txnseqno) {
        
    	log.info("交易:"+txnseqno+"消费入账开始");
        ResultBean resultBean = null;
        TxnsLogModel txnsLog = null;
        txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
        //这里开始进行划分，分为一般消费账务和产品消费账务
        BusinessEnum businessEnum = BusinessEnum.fromValue(txnsLog.getBusicode());
        
        log.info("交易:{},业务代码:{}",txnseqno,businessEnum.getBusiCode());
        //产品消费账务处理
 		if(businessEnum==BusinessEnum.CONSUMEQUICK_PRODUCT){
 			log.info("交易:"+txnseqno+"开始产品消费账务");
 			resultBean = productTradeAccounting(txnsLog);
 			log.info("交易:"+txnseqno+"结束产品消费账务");
 		}else if(businessEnum==BusinessEnum.CONSUMEQUICK){//一般消费账务处理
 			log.info("交易:"+txnseqno+"开始一般消费账务");
 			resultBean = commonTradeAccounting(txnsLog);
 			log.info("交易:"+txnseqno+"结束一般消费账务");
 		}else if(businessEnum==BusinessEnum.CONSUME_INDUSTRY){
 			log.info("交易:{}开始行业消费账务",txnseqno);
 			resultBean=industryTradeAccounting(txnsLog);
 			log.info("交易:{}结束行业消费账务",txnseqno);
 		}
 		log.info("交易:"+txnseqno+"消费入账结束");
     	return resultBean;
    }

    public ConsumeAccounting(){
       
    }

    @Override
    public ResultBean accountedForInsteadPay(String batchno) {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * 一般消费交易账务
     * @param txnsLog
     * @return
     */
    public ResultBean commonTradeAccounting(TxnsLogModel txnsLog){
    	ResultBean resultBean = null;
    	String txnseqno = txnsLog.getTxnseqno();
    	try {
            /**支付订单号**/
            String payordno = txnsLog.getPayordno();
            /**交易类型**/
            String busiCode = txnsLog.getBusicode();
            
            /**付款方会员ID**/
            String payMemberId = StringUtil.isNotEmpty(txnsLog.getAccmemberid())?txnsLog.getAccmemberid():"999999999999999";
            /**收款方会员ID**/
            String payToMemberId = StringUtil.isEmpty(txnsLog.getAccsecmerno())?txnsLog.getAccfirmerno():txnsLog.getAccsecmerno();
            /**收款方父级会员ID**/
            String payToParentMemberId=txnsLog.getAccfirmerno()+"";
            /**渠道**/
            String channelId = txnsLog.getPayinst();//支付机构代码

            /**手续费**/
            long txnfee = 0;
            if("99999999".equals(channelId)){
                busiCode = "10000002";
                payMemberId = txnsLog.getPayfirmerno();
                txnfee = txnsLogService.getTxnFee(txnsLog);
            }else {
                busiCode = "10000001";
                if (txnsLog.getTxnfee() != null) {
                    txnfee = txnsLog.getTxnfee();
                }
            }
            /**产品id**/
            String productId = "";
            /**交易金额**/
            BigDecimal amount = new BigDecimal(txnsLog.getAmount());
            /**佣金**/
            BigDecimal commission = new BigDecimal(txnsLog.getTradcomm());

            BigDecimal charge = new BigDecimal(txnfee);
            /**金额D**/
            BigDecimal amountD = new BigDecimal(0);
            /**金额E**/
            BigDecimal amountE = new BigDecimal(0);
            /** 分账标记**/
            boolean isSplit = false;
            if("10000004".equals(txnsLog.getBusicode())){
                isSplit = true;
            }
            txnsLogService.updateAccBusiCode(txnseqno, busiCode);
            TradeInfo tradeInfo = new TradeInfo(txnseqno, payordno, busiCode, payMemberId, payToMemberId, payToParentMemberId, channelId, productId, amount, commission, charge, amountD, amountE, isSplit);
            tradeInfo.setCoopInstCode(txnsLog.getAccfirmerno());
            /*tradeInfo.setPayordno(payordno);
            tradeInfo.setTxnseqno(txnseqno);
            tradeInfo.setAmount(amount);;
            tradeInfo.setAmountD(amountD);
            tradeInfo.setAmountE(amountE);
            tradeInfo.setBusiCode(busiCode);
            tradeInfo.setChannelId(channelId);
            tradeInfo.setCharge(charge);
            tradeInfo.setCommission(commission);
            tradeInfo.setPayMemberId(payMemberId);
            tradeInfo.setPayToMemberId(payToMemberId);
            tradeInfo.setPayToParentMemberId(payToParentMemberId);
            tradeInfo.setProductId(productId);*/
           
            
            log.info(JSON.toJSONString(tradeInfo));
            accEntryService.accEntryProcess(tradeInfo,EntryEvent.TRADE_SUCCESS);
            resultBean = new ResultBean("00","交易成功");
            resultBean.setResultBool(true);
            log.info("交易:"+txnseqno+"消费入账成功");
        } catch (AccBussinessException e) {
            resultBean = new ResultBean("AP05", e.getMessage());
            e.printStackTrace();
        } catch (AbstractBusiAcctException e) {
            resultBean = new ResultBean("AP05", e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            resultBean = new ResultBean("T099", e.getMessage());
            e.printStackTrace();
        }catch (RuntimeException e) {
			// TODO: handle exception
        	e.printStackTrace();
        	resultBean = new ResultBean("T000", e.getMessage());
		} catch (IllegalEntryRequestException e) {
			// TODO Auto-generated catch block
			resultBean = new ResultBean(e.getCode(), e.getMessage());
        } catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
		}
        if(resultBean.isResultBool()){
            txnsLog.setApporderstatus(AccStatusEnum.Finish.getCode());
            txnsLog.setApporderinfo("消费入账成功");
        }else{
            txnsLog.setApporderstatus(AccStatusEnum.AccountingFail.getCode());
            txnsLog.setApporderinfo(resultBean.getErrMsg());
        }
        txnsLogService.updateAppStatus(txnseqno, txnsLog.getApporderstatus(), txnsLog.getApporderinfo());
        txnsLogService.updateTradeStatFlag(txnseqno, TradeStatFlagEnum.FINISH_ACCOUNTING);
        return resultBean;
    }
    
    /**
     * 产品消费交易账务
     * @param txnsLog
     * @return
     */
    public ResultBean productTradeAccounting(TxnsLogModel txnsLog){
    	ResultBean resultBean = null;
    	String txnseqno = txnsLog.getTxnseqno();
    	try {
            /**支付订单号**/
            String payordno = txnsLog.getPayordno();
            /**交易类型**/
            String busiCode = txnsLog.getBusicode();
            
            /**付款方会员ID**/
            String payMemberId = txnsLog.getAccmemberid();
            /**收款方会员ID**/
            String payToMemberId = "";
            /**收款方父级会员ID**/
            String payToParentMemberId="";
            /**渠道**/
            String channelId = txnsLog.getPayinst();//支付机构代码

            /**手续费**/
            long txnfee = 0;
            if("99999999".equals(channelId)){
                busiCode = BusinessEnum.CONSUMEACCOUNT_PRODUCT.getBusiCode();
                payMemberId = txnsLog.getPayfirmerno();
                txnfee = txnsLogService.getTxnFee(txnsLog);
            }else {
                busiCode = BusinessEnum.CONSUMEQUICK_PRODUCT.getBusiCode();
                if (txnsLog.getTxnfee() != null) {
                    txnfee = txnsLog.getTxnfee();
                }
            }
            /**产品id**/
            String productId = txnsLog.getProductcode();
            /**交易金额**/
            BigDecimal amount = new BigDecimal(txnsLog.getAmount());
            /**佣金**/
            BigDecimal commission = new BigDecimal(txnsLog.getTradcomm());

            BigDecimal charge = new BigDecimal(txnfee);
            /**金额D**/
            BigDecimal amountD = new BigDecimal(0);
            /**金额E**/
            BigDecimal amountE = new BigDecimal(0);
            /** 分账标记**/
            boolean isSplit = false;
            /** 机构 */
            String coopInstCode= ConsUtil.getInstance().cons.getZlebank_coopinsti_code();
            /** 接入机构 */
            String access_coopInstCode=txnsLog.getAccfirmerno();
            
            txnsLogService.updateAccBusiCode(txnseqno, busiCode);
            TradeInfo tradeInfo = new TradeInfo(txnseqno, payordno, busiCode, payMemberId, payToMemberId, payToParentMemberId, channelId, productId, amount, commission, charge, amountD, amountE, isSplit);
            tradeInfo.setCoopInstCode(coopInstCode);
            tradeInfo.setAccess_coopInstCode(access_coopInstCode);
            log.info("【产品消费交易账务，参数】"+JSON.toJSONString(tradeInfo));
            accEntryService.accEntryProcess(tradeInfo,EntryEvent.TRADE_SUCCESS);
            resultBean = new ResultBean("00","交易成功");
            resultBean.setResultBool(true);
            log.info("交易:"+txnseqno+"产品消费入账成功");
        } catch (AccBussinessException e) {
            resultBean = new ResultBean("AP05", e.getMessage());
            e.printStackTrace();
        } catch (AbstractBusiAcctException e) {
            resultBean = new ResultBean("AP05", e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            resultBean = new ResultBean("T099", e.getMessage());
            e.printStackTrace();
        }catch (RuntimeException e) {
			// TODO: handle exception
        	e.printStackTrace();
        	resultBean = new ResultBean("T000", e.getMessage());
		} catch (IllegalEntryRequestException e) {
			// TODO Auto-generated catch block
			resultBean = new ResultBean(e.getCode(), e.getMessage());
        } catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
		}
        if(resultBean.isResultBool()){
            txnsLog.setApporderstatus(AccStatusEnum.Finish.getCode());
            txnsLog.setApporderinfo("产品消费入账成功");
        }else{
            txnsLog.setApporderstatus(AccStatusEnum.AccountingFail.getCode());
            txnsLog.setApporderinfo(resultBean.getErrMsg());
        }
        txnsLogService.updateAppStatus(txnseqno, txnsLog.getApporderstatus(), txnsLog.getApporderinfo());
        txnsLogService.updateTradeStatFlag(txnseqno, TradeStatFlagEnum.FINISH_ACCOUNTING);
        return resultBean;
    }
    
    public ResultBean industryTradeAccounting(TxnsLogModel txnsLog){
    	ResultBean resultBean = null;
    	InduGroupMemberBean groupMember = industryGroupMemberService.getGroupMemberByMemberIdAndGroupCode(txnsLog.getAccmemberid(), txnsLog.getGroupcode());
    	InduGroupMemberBean groupMerch = industryGroupMemberService.getGroupMemberByMemberIdAndGroupCode(txnsLog.getAccsecmerno(), txnsLog.getGroupcode());
    	try {
            /**支付订单号**/
            String payordno = txnsLog.getPayordno();
            /**交易类型**/
            String busiCode = txnsLog.getBusicode();
            
            /**付款方会员ID**/
            String payMemberId = groupMember.getUniqueTag();//行业消费时为群组关系（t_industru_group_member）中的唯一标示
            /**收款方会员ID**/
            String payToMemberId = txnsLog.getAccsecmerno();
            /**收款方父级会员ID**/
            String payToParentMemberId=txnsLog.getAccfirmerno()+"";
            /**渠道**/
            String channelId = txnsLog.getPayinst();//支付机构代码

            /**手续费**/
            long txnfee = txnsLogService.getTxnFee(txnsLog);
            /**产品id**/
            String productId = "";
            /**交易金额**/
            BigDecimal amount = new BigDecimal(txnsLog.getAmount());
            /**佣金**/
            BigDecimal commission = new BigDecimal(txnsLog.getTradcomm());

            BigDecimal charge = new BigDecimal(txnfee);
            /**金额D**/
            BigDecimal amountD = new BigDecimal(0);
            /**金额E**/
            BigDecimal amountE = new BigDecimal(0);
            /** 分账标记**/
            boolean isSplit = false;
            /** 机构 */
            String coopInstCode= ConsUtil.getInstance().cons.getZlebank_coopinsti_code();
            /** 接入机构 */
            String access_coopInstCode=txnsLog.getAccfirmerno();
            
            txnsLogService.updateAccBusiCode(txnsLog.getTxnseqno(), busiCode);
            TradeInfo tradeInfo = new TradeInfo(txnsLog.getTxnseqno(), payordno, busiCode, payMemberId, payToMemberId, payToParentMemberId, channelId, productId, amount, commission, charge, amountD, amountE, isSplit);
            
            tradeInfo.setCoopInstCode(coopInstCode);
            tradeInfo.setAccess_coopInstCode(access_coopInstCode);
            tradeInfo.setIndustry_group_member_tag(groupMember.getUniqueTag());
            /*tradeInfo.setPayordno(payordno);
            tradeInfo.setTxnseqno(txnseqno);
            tradeInfo.setAmount(amount);;
            tradeInfo.setAmountD(amountD);
            tradeInfo.setAmountE(amountE);
            tradeInfo.setBusiCode(busiCode);
            tradeInfo.setChannelId(channelId);
            tradeInfo.setCharge(charge);
            tradeInfo.setCommission(commission);
            tradeInfo.setPayMemberId(payMemberId);
            tradeInfo.setPayToMemberId(payToMemberId);
            tradeInfo.setPayToParentMemberId(payToParentMemberId);
            tradeInfo.setProductId(productId);*/
           
            
            log.info(JSON.toJSONString(tradeInfo));
            accEntryService.accEntryProcess(tradeInfo,EntryEvent.TRADE_SUCCESS);
            resultBean = new ResultBean("00","交易成功");
            resultBean.setResultBool(true);
            log.info("交易:"+txnsLog.getTxnseqno()+"消费入账成功");
        } catch (AccBussinessException e) {
            resultBean = new ResultBean("AP05", e.getMessage());
            e.printStackTrace();
        } catch (AbstractBusiAcctException e) {
            resultBean = new ResultBean("AP05", e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            resultBean = new ResultBean("T099", e.getMessage());
            e.printStackTrace();
        }catch (RuntimeException e) {
			// TODO: handle exception
        	e.printStackTrace();
        	resultBean = new ResultBean("T000", e.getMessage());
		} catch (IllegalEntryRequestException e) {
			// TODO Auto-generated catch block
			resultBean = new ResultBean(e.getCode(), e.getMessage());
        } catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
		}
        if(resultBean.isResultBool()){
            txnsLog.setApporderstatus(AccStatusEnum.Finish.getCode());
            txnsLog.setApporderinfo("消费入账成功");
        }else{
            txnsLog.setApporderstatus(AccStatusEnum.AccountingFail.getCode());
            txnsLog.setApporderinfo(resultBean.getErrMsg());
        }
        txnsLogService.updateAppStatus(txnsLog.getTxnseqno(), txnsLog.getApporderstatus(), txnsLog.getApporderinfo());
        txnsLogService.updateTradeStatFlag(txnsLog.getTxnseqno(), TradeStatFlagEnum.FINISH_ACCOUNTING);
        return resultBean;
    }
    
}
