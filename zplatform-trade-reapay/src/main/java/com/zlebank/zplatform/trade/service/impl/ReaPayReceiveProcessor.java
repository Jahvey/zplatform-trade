/* 
 * ReaPayReceiveProcessor.java  
 * 
 * version TODO
 *
 * 2015年11月17日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.trade.bean.AppPartyBean;
import com.zlebank.zplatform.trade.bean.PayPartyBean;
import com.zlebank.zplatform.trade.bean.ReaPayResultBean;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.enums.TradeStatFlagEnum;
import com.zlebank.zplatform.trade.bean.enums.TradeTypeEnum;
import com.zlebank.zplatform.trade.bean.gateway.OrderAsynRespBean;
import com.zlebank.zplatform.trade.dao.ITxnsOrderinfoDAO;
import com.zlebank.zplatform.trade.model.TxnsOrderinfoModel;
import com.zlebank.zplatform.trade.service.IQuickpayCustService;
import com.zlebank.zplatform.trade.service.ITradeReceiveProcessor;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.DateUtil;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月17日 下午5:09:52
 * @since 
 */
@Service("reaPayReceiveProcessor")
public class ReaPayReceiveProcessor implements ITradeReceiveProcessor{
    @Autowired
    private ITxnsLogService txnsLogService;
    @Autowired
    private IQuickpayCustService quickpayCustService;
    @Autowired
    private ITxnsOrderinfoDAO txnsOrderinfoDAO;
    /**
     *
     * @param resultBean
     * @param tradeBean
     * @param tradeType
     */
    @Override
    public void onReceive(ResultBean resultBean,
            TradeBean tradeBean,
            TradeTypeEnum tradeType) {
        
    	// TODO Auto-generated method stub
        if(tradeType==TradeTypeEnum.SUBMITPAY){//确认支付（第三方快捷支付渠道）
            saveReaPayTradeResult(resultBean,tradeBean);
        }else if(tradeType==TradeTypeEnum.BANKSIGN){//交易查询
            
        }else if(tradeType==TradeTypeEnum.UNKNOW){//银行卡签约
           
        }
    }
    @Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Throwable.class)
    public void saveReaPayTradeResult(ResultBean resultBean,TradeBean tradeBean){
            ReaPayResultBean payResult = (ReaPayResultBean) resultBean.getResultObj();
            if("0000".equals(payResult.getResult_code())||"3006".equals(payResult.getResult_code())||"3053".equals(payResult.getResult_code())||"3054".equals(payResult.getResult_code())||
                    "3056".equals(payResult.getResult_code())||"3083".equals(payResult.getResult_code())||"3081".equals(payResult.getResult_code())){
                //返回这些信息时，表示融宝已经接受到交易请求，但是没有同步处理，等待异步通知
                //对于没有绑定的卡进行绑卡确认，更新状态为00
                quickpayCustService.updateCardStatus(tradeBean.getCardId());
            }else{
                //订单状态更新为失败
                txnsOrderinfoDAO.updateOrderToFail(tradeBean.getTxnseqno());
            }
            //String txnseqno, String paytype, String payordno, String payinst, String payfirmerno, String paysecmerno, String payordcomtime, String payordfintime, String cardNo, String rout, String routlvl
            PayPartyBean payPartyBean = new PayPartyBean(tradeBean.getTxnseqno(),"01", tradeBean.getOrderId(), ConsUtil.getInstance().cons.getReapay_chnl_code(), ConsUtil.getInstance().cons.getReapay_quickpay_merchant_id(), "", DateUtil.getCurrentDateTime(), "",tradeBean.getCardNo());
            payPartyBean.setPanName(tradeBean.getAcctName());
            txnsLogService.updatePayInfo_Fast(payPartyBean);
            txnsLogService.updateReaPayRetInfo(tradeBean.getTxnseqno(), payResult);
            txnsLogService.updateTradeStatFlag(tradeBean.getTxnseqno(), TradeStatFlagEnum.PAYING);
            
    }
	/**
	 *
	 * @param orderNo
	 * @param memberId
	 * @return
	 */
	@Override
	public ResultBean generateAsyncRespMessage(String txnseqno) {
		// TODO Auto-generated method stub
		return null;
	}
}
