/* 
 * AccountRefundTrade.java  
 * 
 * version TODO
 *
 * 2016年5月19日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.accounting.refund;

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
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.member.service.IndustryGroupMemberService;
import com.zlebank.zplatform.trade.adapter.accounting.impl.ChargeAccounting;
import com.zlebank.zplatform.trade.adapter.quickpay.IRefundTrade;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.enums.BusinessEnum;
import com.zlebank.zplatform.trade.dao.ITxnsOrderinfoDAO;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsOrderinfoModel;
import com.zlebank.zplatform.trade.model.TxnsRefundModel;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.ITxnsRefundService;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.DateUtil;
import com.zlebank.zplatform.trade.utils.OrderNumber;
import com.zlebank.zplatform.trade.utils.SpringContext;
import com.zlebank.zplatform.trade.utils.UUIDUtil;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年5月19日 上午8:36:35
 * @since
 */
public class AccountRefundTrade implements IRefundTrade {
	private static final Log log = LogFactory.getLog(ChargeAccounting.class);
	
	private AccEntryService accEntryService;
	private ITxnsRefundService txnsRefundService;
	private ITxnsLogService txnsLogService;
	private ITxnsOrderinfoDAO txnsOrderinfoDAO;
	private IndustryGroupMemberService industryGroupMemberService = (IndustryGroupMemberService)SpringContext.getContext().getBean("industryGroupMemberServiceImpl");
	public AccountRefundTrade() {
		accEntryService = (AccEntryService) SpringContext.getContext().getBean(
				"accEntryServiceImpl");
		txnsRefundService = (ITxnsRefundService) SpringContext.getContext()
				.getBean("txnsRefundService");
		txnsLogService = (ITxnsLogService) SpringContext.getContext().getBean(
				"txnsLogService");
		
		txnsOrderinfoDAO = (ITxnsOrderinfoDAO) SpringContext.getContext().getBean(
				"txnsOrderinfo");
	}

	/**
	 *
	 * @param tradeBean
	 * @return
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Throwable.class)
	public ResultBean refund(TradeBean tradeBean) {
		log.info("交易:"+tradeBean.getTxnseqno()+"退款账务处理开始");
		ResultBean resultBean = null;
		TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(tradeBean.getTxnseqno());
		TxnsOrderinfoModel order = txnsOrderinfoDAO.getOrderByTxnseqno(tradeBean.getTxnseqno());
		BusinessEnum businessEnum = BusinessEnum.fromValue(txnsLog.getBusicode());
		if(businessEnum == BusinessEnum.REFUND_ACCOUNT){
			//记录退款的流水
			String payordno = updateRefund(tradeBean.getTxnseqno(),txnsLog.getAccmemberid());
			/**支付订单号**/
	        
	        /**交易类型**/
	        String busiCode = txnsLog.getBusicode();
	        /**付款方会员ID**/
	        String payMemberId =  txnsLog.getAccmemberid();
	        /**收款方会员ID**/
	        String payToMemberId = txnsLog.getAccsecmerno();
	        /**收款方父级会员ID**/
	        String payToParentMemberId="" ;
	        /**渠道**/
	        String channelId = "99999999";//支付机构代码
	        /**产品id**/
	        String productId = "";
	        /**交易金额**/
	        BigDecimal amount = new BigDecimal(txnsLog.getAmount());
	        /**佣金**/
	        BigDecimal commission = new BigDecimal(StringUtil.isNotEmpty(txnsLog.getTradcomm()+"")?txnsLog.getTradcomm():0);
	        /**手续费**/
	        BigDecimal charge = new BigDecimal(StringUtil.isNotEmpty(txnsLog.getTxnfee()+"")?txnsLog.getTxnfee():0L);
	        /**金额D**/
	        BigDecimal amountD = new BigDecimal(0);
	        /**金额E**/
	        BigDecimal amountE = new BigDecimal(0);
	        
	        TradeInfo tradeInfo = new TradeInfo(txnsLog.getTxnseqno(), payordno, busiCode, payMemberId, payToMemberId, payToParentMemberId, channelId, productId, amount, commission, charge, amountD, amountE, false);
	        tradeInfo.setCoopInstCode(txnsLog.getAccfirmerno());
	        
	        log.info(JSON.toJSONString(tradeInfo));
	        try {
				accEntryService.accEntryProcess(tradeInfo,EntryEvent.AUDIT_PASS);
				resultBean = new ResultBean("success");
			}  catch (AccBussinessException e) {
	            resultBean = new ResultBean(e.getCode(), e.getMessage());
	            e.printStackTrace();
	        } catch (AbstractBusiAcctException e) {
	            resultBean = new ResultBean(e.getCode(), e.getMessage());
	            e.printStackTrace();
	        } catch (NumberFormatException e) {
	            resultBean = new ResultBean("T099", e.getMessage());
	            e.printStackTrace();
	        } catch (IllegalEntryRequestException e) {
				// TODO Auto-generated catch block
	        	resultBean = new ResultBean(e.getCode(), e.getMessage());
				e.printStackTrace();
			}
	        
	        if(resultBean.isResultBool()){
	        	updateRefundResult( txnsLog.getTxnseqno(),"","0000","交易成功");
	            txnsLog.setApporderstatus(AccStatusEnum.Finish.getCode());
	            txnsLog.setApporderinfo("退款账务成功");
	            order.setStatus("00");
	            TxnsRefundModel refund = txnsRefundService.getRefundByTxnseqno(tradeBean.getTxnseqno());
	            refund.setStatus("00");
	            txnsRefundService.update(refund);
	        }else{
	        	updateRefundResult( txnsLog.getTxnseqno(),"","0099",resultBean.getErrMsg());
	            txnsLog.setApporderstatus(AccStatusEnum.AccountingFail.getCode());
	            txnsLog.setApporderinfo(resultBean.getErrMsg());
	        }
	        txnsLogService.updateAppStatus(tradeBean.getTxnseqno(), txnsLog.getApporderstatus(), txnsLog.getApporderinfo());
	        txnsOrderinfoDAO.update(order);
	        log.info("交易:"+tradeBean.getTxnseqno()+"退款账务处理成功");
		}else if(businessEnum == BusinessEnum.REFUND_INDUSTRY){
			
			//记录退款的流水
			String payordno = updateRefund(tradeBean.getTxnseqno(),txnsLog.getAccmemberid());
			/**支付订单号**/
	        
	        /**交易类型**/
	        String busiCode = txnsLog.getBusicode();
	        /**付款方会员ID**/
	        String payMemberId =  txnsLog.getAccmemberid();
	        /**收款方会员ID**/
	        String payToMemberId = txnsLog.getAccsecmerno();
	        /**收款方父级会员ID**/
	        String payToParentMemberId="" ;
	        /**渠道**/
	        String channelId = "99999999";//支付机构代码
	        /**产品id**/
	        String productId = "";
	        /**交易金额**/
	        BigDecimal amount = new BigDecimal(txnsLog.getAmount());
	        /**佣金**/
	        BigDecimal commission = new BigDecimal(StringUtil.isNotEmpty(txnsLog.getTradcomm()+"")?txnsLog.getTradcomm():0);
	        /**手续费**/
	        BigDecimal charge = new BigDecimal(StringUtil.isNotEmpty(txnsLog.getTxnfee()+"")?txnsLog.getTxnfee():0L);
	        /**金额D**/
	        BigDecimal amountD = new BigDecimal(0);
	        /**金额E**/
	        BigDecimal amountE = new BigDecimal(0);
	        TradeInfo tradeInfo = new TradeInfo(txnsLog.getTxnseqno(), payordno, busiCode, payMemberId, payToMemberId, payToParentMemberId, channelId, productId, amount, commission, charge, amountD, amountE, false);
	        tradeInfo.setCoopInstCode(ConsUtil.getInstance().cons.getZlebank_coopinsti_code());
	        tradeInfo.setAccess_coopInstCode(txnsLog.getAccfirmerno());
	        tradeInfo.setIndustry_group_member_tag(industryGroupMemberService.getGroupMemberByMemberIdAndGroupCode(txnsLog.getAccmemberid(), txnsLog.getGroupcode()).getUniqueTag());
	        log.info(JSON.toJSONString(tradeInfo));
	        try {
				accEntryService.accEntryProcess(tradeInfo,EntryEvent.AUDIT_PASS);
				resultBean = new ResultBean("success");
			}  catch (AccBussinessException e) {
	            resultBean = new ResultBean(e.getCode(), e.getMessage());
	            e.printStackTrace();
	        } catch (AbstractBusiAcctException e) {
	            resultBean = new ResultBean(e.getCode(), e.getMessage());
	            e.printStackTrace();
	        } catch (NumberFormatException e) {
	            resultBean = new ResultBean("T099", e.getMessage());
	            e.printStackTrace();
	        } catch (IllegalEntryRequestException e) {
				// TODO Auto-generated catch block
	        	resultBean = new ResultBean(e.getCode(), e.getMessage());
				e.printStackTrace();
			}
	        
	        if(resultBean.isResultBool()){
	        	updateRefundResult( txnsLog.getTxnseqno(),"","0000","交易成功");
	            txnsLog.setApporderstatus(AccStatusEnum.Finish.getCode());
	            txnsLog.setApporderinfo("退款账务成功");
	            order.setStatus("00");
	            TxnsRefundModel refund = txnsRefundService.getRefundByTxnseqno(tradeBean.getTxnseqno());
	            refund.setStatus("00");
	            txnsRefundService.update(refund);
	        }else{
	        	updateRefundResult( txnsLog.getTxnseqno(),"","0099",resultBean.getErrMsg());
	            txnsLog.setApporderstatus(AccStatusEnum.AccountingFail.getCode());
	            txnsLog.setApporderinfo(resultBean.getErrMsg());
	        }
	        txnsLogService.updateAppStatus(tradeBean.getTxnseqno(), txnsLog.getApporderstatus(), txnsLog.getApporderinfo());
	        txnsOrderinfoDAO.update(order);
	        log.info("交易:"+tradeBean.getTxnseqno()+"退款账务处理成功");
		}
		
		
		return resultBean;
	}
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Throwable.class)
	public String updateRefund(String txnseqno,String memberId){
		TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
        txnsLog.setPaytype("07"); //支付类型（01：快捷，02：网银，03：账户,07：退款）
        txnsLog.setPayordno(OrderNumber.getInstance().generateAppOrderNo());//支付定单号
        txnsLog.setPayinst("99999999");//支付所属机构
        txnsLog.setPayfirmerno(memberId);//支付一级商户号-个人会员
        //txnsLog.setPaysecmerno(payPartyBean.getPaysecmerno());//支付二级商户号
        txnsLog.setPayordcomtime(DateUtil.getCurrentDateTime());//支付定单提交时间
        
        //更新交易流水表交易位
        txnsLog.setRelate("01000000");
        txnsLog.setTradetxnflag("01000000");
        txnsLog.setTradestatflag("00000001");
        txnsLog.setRetdatetime(DateUtil.getCurrentDateTime());
        txnsLog.setAccbusicode(BusinessEnum.REFUND_ACCOUNT.getBusiCode());
        txnsLog.setTradeseltxn(UUIDUtil.uuid());
        //支付定单完成时间
        txnsLogService.update(txnsLog);
        return txnsLog.getPayordno();
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Throwable.class)
	public void updateRefundResult(String txnseqno,String memberId,String retCode,String retInfo){
		TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
        txnsLog.setPayordfintime(DateUtil.getCurrentDateTime());
        txnsLog.setAccordfintime(DateUtil.getCurrentDateTime());
        txnsLog.setPayretcode(retCode);
        txnsLog.setPayretinfo(retInfo);
        txnsLog.setAppordcommitime(DateUtil.getCurrentDateTime());
        txnsLog.setAppinst("000000000000");
        if("0000".equals(retCode)){
        	 txnsLog.setApporderinfo("退款账务成功");
             txnsLog.setApporderstatus("00");
        }else{
        	txnsLog.setApporderinfo(retInfo);
            txnsLog.setApporderstatus("09");
        }
        txnsLog.setAppordfintime(DateUtil.getCurrentDateTime());
        txnsLog.setRetcode(retCode);
        txnsLog.setRetinfo(retInfo);
        //支付定单完成时间
        txnsLogService.update(txnsLog);
	}
}
