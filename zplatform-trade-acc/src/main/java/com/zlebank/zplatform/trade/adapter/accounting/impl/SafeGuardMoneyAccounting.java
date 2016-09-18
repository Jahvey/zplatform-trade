/* 
 * SafeGuardMoneyAccounting.java  
 * 
 * version TODO
 *
 * 2015年9月10日 
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

import com.zlebank.zplatform.acc.bean.TradeInfo;
import com.zlebank.zplatform.acc.exception.AbstractBusiAcctException;
import com.zlebank.zplatform.acc.exception.AccBussinessException;
import com.zlebank.zplatform.acc.exception.IllegalEntryRequestException;
import com.zlebank.zplatform.acc.service.AccEntryService;
import com.zlebank.zplatform.acc.service.entry.EntryEvent;
import com.zlebank.zplatform.commons.dao.pojo.AccStatusEnum;
import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.trade.adapter.accounting.IAccounting;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.enums.BusinessEnum;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.utils.SpringContext;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月10日 下午6:28:23
 * @since 
 */
public class SafeGuardMoneyAccounting implements IAccounting{
    private static final Log log = LogFactory.getLog(SafeGuardMoneyAccounting.class);
    private ITxnsLogService txnsLogService;
    private AccEntryService accEntryService;
    /**
     *
     * @param txnseqno
     * @return
     */
    @Transactional(propagation=Propagation.REQUIRED)
    public ResultBean accountedFor(String txnseqno) {
    	 ResultBean resultBean = null;
         log.info("交易:"+txnseqno+"保证金账务处理开始");
        TxnsLogModel txnsLog = null;
            try {
                txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
                /**支付订单号**/
                String payordno = txnsLog.getPayordno();
                /**交易类型**/
                String busiCode = txnsLog.getBusicode();
                /**付款方会员ID**/
                String payMemberId = txnsLog.getAccsecmerno().toString();
                /**收款方会员ID**/
                String payToMemberId = txnsLog.getAccsecmerno().toString();
                /**收款方父级会员ID**/
                String payToParentMemberId="" ;
                /**渠道**/
                String channelId = txnsLog.getPayinst();//支付机构代码
                /**产品id**/
                String productId = "";
                /**交易金额**/
                BigDecimal amount = new BigDecimal(txnsLog.getAmount());
                /**佣金**/
                BigDecimal commission = new BigDecimal(txnsLog.getTradcomm());
                /**手续费**/
                BigDecimal charge = new BigDecimal(txnsLog.getTxnfee());
                /**金额D**/
                BigDecimal amountD = new BigDecimal(0);
                /**金额E**/
                BigDecimal amountE = new BigDecimal(0);
                
                TradeInfo tradeInfo = new TradeInfo();
                tradeInfo.setPayordno(payordno);
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
                tradeInfo.setProductId(productId);
                accEntryService.accEntryProcess(tradeInfo,EntryEvent.TRADE_SUCCESS);
                resultBean = new ResultBean("success");
                if(busiCode.equals(BusinessEnum.BAIL_RECHARGE.getBusiCode())){
                	log.info("保障金充值入账成功");
                }else if(busiCode.equals(BusinessEnum.BAIL_WITHDRAWALS.getBusiCode())){
                	log.info("保障金体取成功");
                }
            } catch (AccBussinessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                resultBean = new ResultBean(e.getCode(), e.getMessage());
            } catch (AbstractBusiAcctException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                resultBean = new ResultBean(e.getCode(), e.getMessage());
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                resultBean = new ResultBean("T099", e.getMessage());
            } catch (IllegalEntryRequestException e) {
				// TODO Auto-generated catch block
            	resultBean = new ResultBean(e.getCode(), e.getMessage());
			}
            //处理账务
   		 String retCode="";
   		 String retInfo="";
           if(resultBean.isResultBool()){
   			 retCode="0000";
   			 retInfo="交易成功";
          }else{
          	 retCode="0099";
          	 retInfo=resultBean.getErrMsg();
          }
           txnsLog.setPayordfintime(DateUtil.getCurrentDateTime());
           txnsLog.setAccordfintime(DateUtil.getCurrentDateTime());
           txnsLog.setPayretcode(retCode);
           txnsLog.setPayretinfo(retInfo);
           txnsLog.setAppordcommitime(DateUtil.getCurrentDateTime());
           txnsLog.setAppinst("000000000000");//没实际意义，可以为空
           if("0000".equals(retCode)){
        	   if(txnsLog.getBusicode().equals(BusinessEnum.BAIL_RECHARGE.getBusiCode())){
        		   txnsLog.setApporderinfo("保证金充值账务成功");
               }else if(txnsLog.getBusicode().equals(BusinessEnum.BAIL_WITHDRAWALS.getBusiCode())){
            	   txnsLog.setApporderinfo("保证金提取账务成功");
               }
                txnsLog.setApporderstatus(AccStatusEnum.Finish.getCode());
           }else{
           	txnsLog.setApporderinfo(retInfo);
               txnsLog.setApporderstatus(AccStatusEnum.AccountingFail.getCode());
           }
           txnsLog.setAppordfintime(DateUtil.getCurrentDateTime());
           txnsLog.setRetcode(retCode);
           txnsLog.setRetinfo(retInfo);
           //支付定单完成时间
           this.txnsLogService.updateTxnsLog(txnsLog);
           log.info("交易:"+txnseqno+"保证金交易处理成功");
   		return resultBean;
    }

    /**
     * 
     */
    public SafeGuardMoneyAccounting() {
        txnsLogService = (ITxnsLogService) SpringContext.getContext().getBean("txnsLogService");
        accEntryService = (AccEntryService) SpringContext.getContext().getBean("accEntryServiceImpl");
    }

    @Override
    public ResultBean accountedForInsteadPay(String batchno) {
        // TODO Auto-generated method stub
        return null;
    }

}
