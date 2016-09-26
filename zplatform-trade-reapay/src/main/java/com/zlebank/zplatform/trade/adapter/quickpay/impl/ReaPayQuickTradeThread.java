/* 
 * ReaPayQuickTrade.java  
 * 
 * version TODO
 *
 * 2015年9月16日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.adapter.quickpay.impl;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.trade.adapter.quickpay.IQuickPayTrade;
import com.zlebank.zplatform.trade.analyzer.ReaPayTradeAnalyzer;
import com.zlebank.zplatform.trade.bean.ReaPayResultBean;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.enums.TradeTypeEnum;
import com.zlebank.zplatform.trade.bean.reapal.BindBean;
import com.zlebank.zplatform.trade.bean.reapal.CreditBean;
import com.zlebank.zplatform.trade.bean.reapal.DebitBean;
import com.zlebank.zplatform.trade.bean.reapal.PayBean;
import com.zlebank.zplatform.trade.bean.reapal.QueryBean;
import com.zlebank.zplatform.trade.bean.reapal.SMSBean;
import com.zlebank.zplatform.trade.model.TxnsQuickpayModel;
import com.zlebank.zplatform.trade.service.IQuickpayCustService;
import com.zlebank.zplatform.trade.service.IReaPayTradeService;
import com.zlebank.zplatform.trade.service.IRouteConfigService;
import com.zlebank.zplatform.trade.service.ITradeReceiveProcessor;
import com.zlebank.zplatform.trade.service.ITxnsQuickpayService;
import com.zlebank.zplatform.trade.service.ReaPayQuickPayService;
import com.zlebank.zplatform.trade.utils.SpringContext;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月16日 上午10:44:54
 * @since 
 */
public class ReaPayQuickTradeThread implements IQuickPayTrade{
    private static final Log log = LogFactory.getLog(ReaPayQuickTradeThread.class);
//    private static String PAYINSTID="96000001";
   /* private ITxnsQuickpayService txnsQuickpayService;
    private IReaPayTradeService reaPayTradeService;
    private IRouteConfigService routeConfigService;
    private IQuickpayCustService quickpayCustService;
    private ITradeReceiveProcessor receiveProcessor;*/
    private TradeBean tradeBean;
    private TradeTypeEnum tradeType;
    
    private ReaPayQuickPayService reaPayQuickPayService = (ReaPayQuickPayService) SpringContext.getContext().getBean("reaPayQuickPayService");
    public ReaPayQuickTradeThread(){
        /*txnsQuickpayService = (ITxnsQuickpayService) SpringContext.getContext().getBean("txnsQuickpayService");
        reaPayTradeService  = (IReaPayTradeService) SpringContext.getContext().getBean("reaPayTradeService");
        routeConfigService  = (IRouteConfigService) SpringContext.getContext().getBean("routeConfigService");
        quickpayCustService = (IQuickpayCustService) SpringContext.getContext().getBean("quickpayCustService");
        receiveProcessor = (ITradeReceiveProcessor) SpringContext.getContext().getBean("reaPayReceiveProcessor");*/
    }
    
    /**
    *
    */
   @Override
   public void run() {
       // TODO Auto-generated method stub
     
       if(tradeBean==null){
           return;
       }
       switch (tradeType) {
			case SENDSMS:
				sendSms(tradeBean);
				break;
			case SUBMITPAY:
				submitPay(tradeBean);
				break;
			case BANKSIGN:
				bankSign(tradeBean);
				break;
			default:
				break;
       }
   }
    /**
     * 重发短信验证码
     * @param trade
     * @return
     */
    
    public ResultBean sendSms(TradeBean trade) {
        /*trade.setPayinstiId(PAYINSTID);
        ResultBean resultBean = null;
        trade.setValidthru(trade.getMonth()+trade.getYear());
        SMSBean smsBean = ReaPayTradeAnalyzer.generateSMSBean(trade);
        //记录快捷交易流水
        String payOrderNo =txnsQuickpayService.saveReaPaySMS(trade, smsBean);
        //发送短信验证码
        resultBean = reaPayTradeService.reSendSms(smsBean);
        txnsQuickpayService.updateReaPaySMS(resultBean,payOrderNo);*/
        return reaPayQuickPayService.sendSms(trade);
    }

    /**
     * * 融宝借记卡/信用卡签约
     * @param trade
     * @return
     */
    
    public ResultBean marginRegister(TradeBean trade) {
        /*log.info("ReaPay bank sign start!");
        if(log.isDebugEnabled()){
                log.debug(JSON.toJSONString(trade));
        }
        trade.setPayinstiId(PAYINSTID);
        ResultBean resultBean = null;
        try {
            //已绑卡支付
            if(StringUtil.isNotEmpty(trade.getBindCardId())){
                log.info("ReaPay bindcard sign start!");
                //使用已绑定的卡进行支付
                BindBean bindBean = ReaPayTradeAnalyzer.generateBindBean(trade);
                bindBean.setMember_ip("219.234.83.141");
                if(log.isDebugEnabled()){
                    log.debug(JSON.toJSONString(bindBean));
                }
                //记录快捷交易流水
                String payorderno = txnsQuickpayService.saveReaPayBindSign(trade, bindBean);
                resultBean = reaPayTradeService.bindCard(bindBean);
                //更新快捷交易流水
                txnsQuickpayService.updateReaPaySign(resultBean,payorderno);
                return resultBean;
            }
            Map<String, Object> cardInfoMap = routeConfigService.getCardInfo(trade.getCardNo());
            if(cardInfoMap==null){
                return new ResultBean("RC23", "银行卡验证失败 ");
            }
            trade.setCardType(cardInfoMap.get("TYPE").toString());
            //未绑卡支付
            if(trade.getCardType().equals("1")){//借记卡
                log.info("ReaPay debitcard sign start!");
                //String accOrderNo = trade.getOrderId();//受理订单号
                DebitBean debitBean = ReaPayTradeAnalyzer.generateDebitBean(trade);//生成借记卡签约bean
                debitBean.setMember_ip("219.234.83.141");
                if(log.isDebugEnabled()){
                    log.debug(JSON.toJSONString(debitBean));
                }
                //记录快捷交易流水
                TxnsQuickpayModel quickpay = txnsQuickpayService.saveReaPayDebitSign(trade, debitBean);
                resultBean = reaPayTradeService.debitCardSign(debitBean);
                //更新快捷交易流水
                txnsQuickpayService.updateReaPaySign(resultBean,quickpay.getPayorderno());
                log.info("ReaPay debitcard sign end!");
            }else if(trade.getCardType().equals("2")){//信用卡
                log.info("ReaPay creditcard sign start!");
                if(StringUtil.isEmpty(trade.getValidthru())){//判断有效是是否为空
                    trade.setValidthru(trade.getMonth()+trade.getYear());//web收银台使用
                }
                CreditBean creditBean = ReaPayTradeAnalyzer.generateCreditBean(trade);
                creditBean.setMember_ip("219.234.83.141");
                if(log.isDebugEnabled()){
                    log.debug(JSON.toJSONString(creditBean));
                }
                //记录快捷交易流水
                String payorderno = txnsQuickpayService.saveReaPayCreditSign(trade, creditBean);
                resultBean = reaPayTradeService.creditCardSign(creditBean);
                //更新快捷交易流水
                txnsQuickpayService.updateReaPaySign(resultBean,payorderno);
                log.info("ReaPay creditcard sign end!");
            }
            ReaPayResultBean bean = (ReaPayResultBean)resultBean.getResultObj();
            trade.setBindCardId(bean.getBind_id());
            if(StringUtil.isNotEmpty(bean.getBind_id())){
              //保存商户用户的卡信息
                Long bindId=trade.getCardId();
                if(bindId!=null){
                    quickpayCustService.updateBindCardId(bindId, bean.getBind_id());
                    trade.setBindCardId(bindId+"");
                    bean.setBind_id(bindId+"");
                }else{
                    trade.setBindCardId("");
                }
            }else{
                //quickpayCustService.deleteCard(trade.getCardId());
            }
            log.info("ReaPay bank card sign end!");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            resultBean = new ResultBean("RC99","交易失败");
        }
        */
        return null;
    }

    
    /**
     *
     * @param trade
     * @return
     */
    
    public ResultBean submitPay(TradeBean trade) {
        /*log.info("ReaPay submit Pay start!");
        if(log.isDebugEnabled()){
            try {
                log.debug(JSON.toJSONString(trade));
            } catch (Exception e) {
            }
        }
        trade.setPayinstiId(PAYINSTID);
        ResultBean resultBean = null;
        PayBean payBean = ReaPayTradeAnalyzer.generatePayBean(trade);
        if(log.isDebugEnabled()){
            log.debug(JSON.toJSONString(payBean));
        }
        //记录快捷交易流水
        String payorderno = txnsQuickpayService.saveReaPayToPay(trade, payBean);
        resultBean = reaPayTradeService.submitPay(payBean);
        //更新快捷交易流水
        txnsQuickpayService.updateReaPaySign(resultBean,payorderno);
        log.info("ReaPay submit Pay end!");
        receiveProcessor.onReceive(resultBean,trade,TradeTypeEnum.SUBMITPAY);*/
        return reaPayQuickPayService.submitPay(trade);
    }
    /**
     *
     * @param trade
     * @return
     */
    public ResultBean queryTrade(TradeBean trade) {
        /*ResultBean resultBean = null;
        QueryBean queryBean = ReaPayTradeAnalyzer.generateQueryBean(trade);
        //记录快捷交易流水
        String payorderno = txnsQuickpayService.saveReaPayQuery(trade, queryBean);
        resultBean = reaPayTradeService.searchPayResult(queryBean);
        //更新快捷交易流水
        txnsQuickpayService.updateReaPayQuery(resultBean,payorderno);*/
        return reaPayQuickPayService.queryTrade(trade);
    }
    /**
     * 银行卡签约
     * @param trade
     * @return
     */
    public ResultBean bankSign(TradeBean trade) {
        // TODO Auto-generated method stub
        return reaPayQuickPayService.bankCardSign(trade);
    }
   
    /**
     *
     * @param tradeType
     */
    @Override
    public void setTradeType(TradeTypeEnum tradeType) {
        // TODO Auto-generated method stub
        this.tradeType = tradeType;
    }
    /**
     *
     * @param tradeBean
     */
    @Override
    public void setTradeBean(TradeBean tradeBean) {
        // TODO Auto-generated method stub
        this.tradeBean = tradeBean;
    }

}
