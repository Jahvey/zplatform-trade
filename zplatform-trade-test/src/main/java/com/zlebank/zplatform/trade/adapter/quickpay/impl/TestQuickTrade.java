/* 
 * TestQuickTrade.java  
 * 
 * version TODO
 *
 * 2015年10月19日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.adapter.quickpay.impl;

import java.util.Map;

import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.trade.adapter.quickpay.IQuickPayTrade;
import com.zlebank.zplatform.trade.analyzer.TestTradeAnalyzer;
import com.zlebank.zplatform.trade.bean.ReaPayResultBean;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.enums.TradeTypeEnum;
import com.zlebank.zplatform.trade.bean.reapal.BindBean;
import com.zlebank.zplatform.trade.bean.reapal.CreditBean;
import com.zlebank.zplatform.trade.bean.reapal.DebitBean;
import com.zlebank.zplatform.trade.bean.reapal.PayBean;
import com.zlebank.zplatform.trade.bean.reapal.SMSBean;
import com.zlebank.zplatform.trade.model.TxnsQuickpayModel;
import com.zlebank.zplatform.trade.service.IQuickpayCustService;
import com.zlebank.zplatform.trade.service.IRouteConfigService;
import com.zlebank.zplatform.trade.service.ITestTradeService;
import com.zlebank.zplatform.trade.service.ITxnsQuickpayService;
import com.zlebank.zplatform.trade.utils.SpringContext;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年10月19日 上午10:35:25
 * @since 
 */
public class TestQuickTrade implements IQuickPayTrade{
    private static String PAYINSTID="95000001";
    private ITxnsQuickpayService txnsQuickpayService;
    private ITestTradeService testTradeService;
    private IRouteConfigService routeConfigService;
    private IQuickpayCustService quickpayCustService;
    private TradeTypeEnum tradeType;
    
    public TestQuickTrade(){
        txnsQuickpayService = (ITxnsQuickpayService) SpringContext.getContext().getBean("txnsQuickpayService");
        testTradeService  = (ITestTradeService) SpringContext.getContext().getBean("testTradeService");
        routeConfigService  = (IRouteConfigService) SpringContext.getContext().getBean("routeConfigService");
        quickpayCustService = (IQuickpayCustService) SpringContext.getContext().getBean("testReceiveProcessor");
    }
   
    
    
    /**
     *
     * @param trade
     * @return
     */
    
    public ResultBean sendSms(TradeBean trade) {
        trade.setPayinstiId(PAYINSTID);
        ResultBean resultBean = null;
        if(StringUtil.isEmpty(trade.getValidthru())){//判断有效是是否为空
            trade.setValidthru(trade.getMonth()+trade.getYear());//web收银台使用
        }
        trade.setValidthru(trade.getMonth()+trade.getYear());
        SMSBean smsBean = TestTradeAnalyzer.generateTestSMSBean(trade);
        //记录快捷交易流水
        String payOrderNo =txnsQuickpayService.saveTestSMS(trade, smsBean);
        //发送短信验证码
        resultBean = testTradeService.reSendSms(smsBean);
        txnsQuickpayService.updateReaPaySMS(resultBean,payOrderNo);
        return resultBean;
    }

    /**
     *
     * @param trade
     * @return
     */
    
    public ResultBean marginRegister(TradeBean trade) {
        trade.setPayinstiId(PAYINSTID);
        ResultBean resultBean = null;
        try {
            //已绑卡支付
            if(StringUtil.isNotEmpty(trade.getBindCardId())){
                //使用已绑定的卡进行支付
                BindBean bindBean = TestTradeAnalyzer.generateTestBindBean(trade);
                //记录快捷交易流水
                String payorderno = txnsQuickpayService.saveTestBindSign(trade, bindBean);
                resultBean = testTradeService.bindCard(bindBean);
                //更新快捷交易流水
                txnsQuickpayService.updateTestPaySign(resultBean,payorderno);
                return resultBean;
            }
            Map<String, Object> cardInfoMap = routeConfigService.getCardInfo(trade.getCardNo());
            if(cardInfoMap==null){
                return new ResultBean("RC23", "银行卡验证失败 ");
            }
            trade.setCardType(cardInfoMap.get("TYPE").toString());
            //未绑卡支付
            if(trade.getCardType().equals("1")){//借记卡
                //String accOrderNo = trade.getOrderId();//受理订单号
                DebitBean debitBean = TestTradeAnalyzer.generateTestDebitBean(trade);//生成借记卡签约bean
                //记录快捷交易流水
                TxnsQuickpayModel quickpay = txnsQuickpayService.saveTestDebitSign(trade, debitBean);
                resultBean = testTradeService.debitCardSign(debitBean);
                //更新快捷交易流水
                txnsQuickpayService.updateTestPaySign(resultBean,quickpay.getPayorderno());
                
            }else if(trade.getCardType().equals("2")){//信用卡
                if(StringUtil.isEmpty(trade.getValidthru())){//判断有效是是否为空
                    trade.setValidthru(trade.getMonth()+trade.getYear());//web收银台使用
                }
                CreditBean creditBean = TestTradeAnalyzer.generateTestCreditBean(trade);
                //记录快捷交易流水
                String payorderno = txnsQuickpayService.saveReaPayCreditSign(trade, creditBean);
                resultBean = testTradeService.creditCardSign(creditBean);
                //更新快捷交易流水
                txnsQuickpayService.updateTestSign(resultBean,payorderno);
            }
            ReaPayResultBean bean = (ReaPayResultBean)resultBean.getResultObj();
            trade.setBindCardId(bean.getBind_id());
            //保存商户用户的卡信息
            trade.setPayinstiId("95000001");
            Long bindId=quickpayCustService.saveTestQuickpayCust(trade);
            if(bindId!=null){
                bean.setBind_id(bindId+"");
            }
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            resultBean = new ResultBean("RC99","交易失败");
        }
        
        return resultBean;
    }

    /**
     *
     * @param trade
     * @return
     */
    
    public ResultBean onlineDepositShort(TradeBean trade) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     *
     * @param trade
     * @return
     */
    
    public ResultBean withdrawNotify(TradeBean trade) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     *
     * @param trade
     * @return
     */
    
    public ResultBean submitPay(TradeBean trade) {
        trade.setPayinstiId(PAYINSTID);
        ResultBean resultBean = null;
        PayBean payBean = TestTradeAnalyzer.generatePayBean(trade);
        //记录快捷交易流水
        String payorderno = txnsQuickpayService.saveTestToPay(trade, payBean);
        resultBean = testTradeService.submitPay(payBean);
        //更新快捷交易流水
        txnsQuickpayService.updateTestSign(resultBean,payorderno);
        return resultBean;
    }

    /**
     *
     * @param trade
     * @return
     */
    
    public ResultBean queryTrade(TradeBean trade) {
        // TODO Auto-generated method stub
        return null;
    }
    /**
     *
     * @param trade
     * @return
     */
    public ResultBean bankSign(TradeBean trade) {
        // TODO Auto-generated method stub
        return marginRegister(trade) ;
    }

    /**
     * @return the tradeType
     */
    public TradeTypeEnum getTradeType() {
        return tradeType;
    }

    /**
     * @param tradeType the tradeType to set
     */
    public void setTradeType(TradeTypeEnum tradeType) {
        this.tradeType = tradeType;
    }



    /**
     *
     */
    public void run() {
        // TODO Auto-generated method stub
        
    }



    /**
     *
     * @param tradeBean
     */
    public void setTradeBean(TradeBean tradeBean) {
        // TODO Auto-generated method stub
        
    }
    

}
