/* 
 * ReaPayQuickPayServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年7月25日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.commons.dao.pojo.BusiTypeEnum;
import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.trade.analyzer.ReaPayTradeAnalyzer;
import com.zlebank.zplatform.trade.bean.AppPartyBean;
import com.zlebank.zplatform.trade.bean.PayPartyBean;
import com.zlebank.zplatform.trade.bean.ReaPayResultBean;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.enums.ChannelEnmu;
import com.zlebank.zplatform.trade.bean.enums.ChnlTypeEnum;
import com.zlebank.zplatform.trade.bean.enums.ReaPayTradeStatusEnmu;
import com.zlebank.zplatform.trade.bean.enums.TradeStatFlagEnum;
import com.zlebank.zplatform.trade.bean.enums.TradeTypeEnum;
import com.zlebank.zplatform.trade.bean.reapal.BindBean;
import com.zlebank.zplatform.trade.bean.reapal.CreditBean;
import com.zlebank.zplatform.trade.bean.reapal.DebitBean;
import com.zlebank.zplatform.trade.bean.reapal.PayBean;
import com.zlebank.zplatform.trade.bean.reapal.QueryBean;
import com.zlebank.zplatform.trade.bean.reapal.SMSBean;
import com.zlebank.zplatform.trade.dao.ITxnsOrderinfoDAO;
import com.zlebank.zplatform.trade.factory.AccountingAdapterFactory;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsQuickpayModel;
import com.zlebank.zplatform.trade.service.IQuickpayCustService;
import com.zlebank.zplatform.trade.service.IReaPayTradeService;
import com.zlebank.zplatform.trade.service.IRouteConfigService;
import com.zlebank.zplatform.trade.service.ITradeReceiveProcessor;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.ITxnsQuickpayService;
import com.zlebank.zplatform.trade.service.ReaPayQuickPayService;
import com.zlebank.zplatform.trade.service.TradeNotifyService;
import com.zlebank.zplatform.trade.utils.ConsUtil;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年7月25日 下午5:37:16
 * @since 
 */
@Service("reaPayQuickPayService")
public class ReaPayQuickPayServiceImpl implements ReaPayQuickPayService{

	private static final Log log = LogFactory.getLog(ReaPayQuickPayServiceImpl.class);
	@Autowired
	private ITxnsQuickpayService txnsQuickpayService;
	@Autowired
    private IReaPayTradeService reaPayTradeService;
	@Autowired
    private IRouteConfigService routeConfigService;
	@Autowired
    private IQuickpayCustService quickpayCustService;
	@Autowired
	@Qualifier("reaPayReceiveProcessor")
    private ITradeReceiveProcessor receiveProcessor;
	@Autowired
	private ITxnsLogService txnsLogService;
	@Autowired
	private ITxnsOrderinfoDAO txnsOrderinfoDAO;
	@Autowired
	private TradeNotifyService tradeNotifyService;
	
	public ResultBean sendSms(TradeBean trade) {
        trade.setPayinstiId(ChannelEnmu.REAPAY.getChnlcode());
        ResultBean resultBean = null;
        trade.setValidthru(trade.getMonth()+trade.getYear());
        SMSBean smsBean = ReaPayTradeAnalyzer.generateSMSBean(trade);
        //记录快捷交易流水
        String payOrderNo =txnsQuickpayService.saveReaPaySMS(trade, smsBean);
        //发送短信验证码
        resultBean = reaPayTradeService.reSendSms(smsBean);
        txnsQuickpayService.updateReaPaySMS(resultBean,payOrderNo);
        return resultBean;
    }
	
	
	public ResultBean bankCardSign(TradeBean trade) {
        log.info("ReaPay bank sign start!");
        if(log.isDebugEnabled()){
                log.debug(JSON.toJSONString(trade));
        }
        trade.setPayinstiId(ChannelEnmu.REAPAY.getChnlcode());
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
            txnsLogService.updateTradeStatFlag(trade.getTxnseqno(), TradeStatFlagEnum.READY);
            log.info("ReaPay bank card sign end!");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            resultBean = new ResultBean("RC99","交易失败");
        }
        
        return resultBean;
    }
	
	
	public ResultBean submitPay(TradeBean trade) {
        log.info("ReaPay submit Pay start!");
        if(log.isDebugEnabled()){
            try {
                log.debug(JSON.toJSONString(trade));
            } catch (Exception e) {
            }
        }
        trade.setPayinstiId(ChannelEnmu.REAPAY.getChnlcode());
        ResultBean resultBean = null;
        PayBean payBean = ReaPayTradeAnalyzer.generatePayBean(trade);
        if(log.isDebugEnabled()){
            log.debug(JSON.toJSONString(payBean));
        }
        //记录快捷交易流水
        String payorderno = txnsQuickpayService.saveReaPayToPay(trade, payBean);
        
        PayPartyBean payPartyBean = new PayPartyBean(trade.getTxnseqno(),"01", trade.getReaPayOrderNo(), ConsUtil.getInstance().cons.getReapay_chnl_code(), ConsUtil.getInstance().cons.getReapay_quickpay_merchant_id(), "", DateUtil.getCurrentDateTime(), "",trade.getCardNo());
        payPartyBean.setPanName(trade.getAcctName());
        txnsLogService.updatePayInfo_Fast(payPartyBean);
        txnsLogService.updateTradeStatFlag(trade.getTxnseqno(), TradeStatFlagEnum.PAYING);
		resultBean = reaPayTradeService.submitPay(payBean);
		if(!resultBean.isResultBool()){
			txnsLogService.updateTradeStatFlag(trade.getTxnseqno(), TradeStatFlagEnum.OVERTIME);
		}
        //更新快捷交易流水
        txnsQuickpayService.updateReaPaySign(resultBean,payorderno);
        log.info("ReaPay submit Pay end!");
        //receiveProcessor.onReceive(resultBean,trade,TradeTypeEnum.SUBMITPAY);
        ReaPayResultBean payResult = (ReaPayResultBean) resultBean.getResultObj();
        if("0000".equals(payResult.getResult_code())||"3006".equals(payResult.getResult_code())||"3053".equals(payResult.getResult_code())||"3054".equals(payResult.getResult_code())||
                "3056".equals(payResult.getResult_code())||"3083".equals(payResult.getResult_code())||"3081".equals(payResult.getResult_code())){
            //返回这些信息时，表示融宝已经接受到交易请求，但是没有同步处理，等待异步通知
            //对于没有绑定的卡进行绑卡确认，更新状态为00
            quickpayCustService.updateCardStatus(trade.getCardId());
        }else{
            //订单状态更新为失败
            txnsOrderinfoDAO.updateOrderToFail(trade.getTxnseqno());
        }
        txnsLogService.updateReaPayRetInfo(trade.getTxnseqno(), payResult);
        txnsLogService.updateTradeStatFlag(trade.getTxnseqno(), TradeStatFlagEnum.PAYING);
        /*ReaPayResultBean payResult = (ReaPayResultBean) resultBean.getResultObj();
        if("0000".equals(payResult.getResult_code())||"3006".equals(payResult.getResult_code())||"3053".equals(payResult.getResult_code())||"3054".equals(payResult.getResult_code())||
                "3056".equals(payResult.getResult_code())||"3083".equals(payResult.getResult_code())||"3081".equals(payResult.getResult_code())){
            //返回这些信息时，表示融宝已经接受到交易请求，但是没有同步处理，等待异步通知
            //对于没有绑定的卡进行绑卡确认，更新状态为00
            quickpayCustService.updateCardStatus(trade.getCardId());
        }else{
            //订单状态更新为失败
            txnsOrderinfoDAO.updateOrderToFail(trade.getTxnseqno());
        }
        //String txnseqno, String paytype, String payordno, String payinst, String payfirmerno, String paysecmerno, String payordcomtime, String payordfintime, String cardNo, String rout, String routlvl
        PayPartyBean payPartyBean = new PayPartyBean(trade.getTxnseqno(),"01", trade.getOrderId(), ConsUtil.getInstance().cons.getReapay_chnl_code(), ConsUtil.getInstance().cons.getReapay_quickpay_merchant_id(), "", DateUtil.getCurrentDateTime(), "",trade.getCardNo());
        payPartyBean.setPanName(trade.getAcctName());
        txnsLogService.updatePayInfo_Fast(payPartyBean);
        txnsLogService.updateReaPayRetInfo(trade.getTxnseqno(), payResult);
        txnsLogService.updateTradeStatFlag(trade.getTxnseqno(), TradeStatFlagEnum.PAYING);*/
        return resultBean;
    }
	
	public ResultBean queryTrade(TradeBean trade) {
        ResultBean resultBean = null;
        QueryBean queryBean = ReaPayTradeAnalyzer.generateQueryBean(trade);
        //记录快捷交易流水
        String payorderno = txnsQuickpayService.saveReaPayQuery(trade, queryBean);
        resultBean = reaPayTradeService.searchPayResult(queryBean);
        //更新快捷交易流水
        txnsQuickpayService.updateReaPayQuery(resultBean,payorderno);
        return resultBean;
    }


	/**
	 *
	 * @param trade
	 * @return
	 */
	@Override
	public ResultBean dealWithAccounting(String txnseqno,ResultBean resultBean) {
		TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
		ReaPayResultBean reaPayResultBean = (ReaPayResultBean) resultBean.getResultObj();
		String ret_code = reaPayResultBean.getResult_code();
		String ret_info = reaPayResultBean.getResult_msg();
		String payrettsnseqno = reaPayResultBean.getTrade_no();
		
		ReaPayTradeStatusEnmu reaPayTradeStatusEnmu = ReaPayTradeStatusEnmu.fromValue(reaPayResultBean.getStatus());
		
		PayPartyBean payPartyBean = new PayPartyBean();
		payPartyBean.setTxnseqno(txnsLog.getTxnseqno());
		payPartyBean.setChnlTypeEnum(ChnlTypeEnum.WECHAT);
		payPartyBean.setPayinst(ChannelEnmu.REAPAY.getChnlcode());
		if(reaPayTradeStatusEnmu==ReaPayTradeStatusEnmu.COMPLETED){//交易成功
			//成功
			payPartyBean.setPayrettsnseqno(payrettsnseqno);
			payPartyBean.setPayretcode(reaPayTradeStatusEnmu.getStatus());
			payPartyBean.setPayretinfo("交易成功");
			txnsLogService.updateTradeData(payPartyBean);
		    //订单状态为成功
			txnsOrderinfoDAO.updateOrderToSuccess(txnseqno);
			if(reaPayTradeStatusEnmu==ReaPayTradeStatusEnmu.COMPLETED){//交易成功，账务处理
		       	 AppPartyBean appParty = new AppPartyBean("",
		                    "000000000000", DateUtil.getCurrentDateTime(),
		                    DateUtil.getCurrentDateTime(), txnsLog.getTxnseqno(), "");
		       	 txnsLogService.updateAppInfo(appParty);
		         AccountingAdapterFactory.getInstance().getAccounting(BusiTypeEnum.fromValue(txnsLog.getBusitype())).accountedFor(txnseqno);
			}
		}else if(reaPayTradeStatusEnmu==ReaPayTradeStatusEnmu.FAILED){//交易失败
			payPartyBean.setPayretcode(ret_code);
			payPartyBean.setPayretinfo(ret_info);
			txnsLogService.updateTradeFailed(payPartyBean);
			//订单状态为失败
			txnsOrderinfoDAO.updateOrderToFail(txnseqno);
		}else{//未支付，或者支付中
			return null;
		}
		tradeNotifyService.notify(txnseqno);//交易成功或者交易失败都有异步通知结果
		
		return null;
	}
	
	
}
