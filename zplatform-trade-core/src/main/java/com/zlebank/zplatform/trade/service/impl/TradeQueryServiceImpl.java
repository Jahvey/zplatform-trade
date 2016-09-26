/* 
 * TradeQueryServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年8月24日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.trade.adapter.quickpay.IQuickPayTrade;
import com.zlebank.zplatform.trade.bean.ReaPayResultBean;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.enums.ChannelEnmu;
import com.zlebank.zplatform.trade.bean.enums.OrderStatusEnum;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.factory.TradeAdapterFactory;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.ITxnsQuickpayService;
import com.zlebank.zplatform.trade.service.TradeQueryService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年8月24日 下午2:33:47
 * @since 
 */
@Service("tradeQueryService")
public class TradeQueryServiceImpl implements TradeQueryService{

	@Autowired
	private ITxnsQuickpayService txnsQuickpayService;
	@Autowired
	private ITxnsLogService txnsLogService;
	
	/**
	 *
	 * @param txnseqno
	 * @return
	 */
	@Override
	public OrderStatusEnum queryTradeResult(String txnseqno) {
		//调用融宝查询方法
		IQuickPayTrade quickPayTrade = null;
        try {
            quickPayTrade = TradeAdapterFactory.getInstance()
                    .getQuickPayTrade(ChannelEnmu.REAPAY.getChnlcode());
        } catch (TradeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
        String reapayOrderNo = txnsQuickpayService.getReapayOrderNo(txnseqno);
        TradeBean trade = new TradeBean();
        trade.setReaPayOrderNo(reapayOrderNo);
        ResultBean queryResultBean = null;
        ReaPayResultBean payResult = null;
        TxnsLogModel txnsLog = null;
        for (int i = 0; i < 5; i++) {
        	txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
        	if("0000".equals(txnsLog.getPayretcode())||"3006".equals(txnsLog.getPayretcode())||"3053".equals(txnsLog.getPayretcode())||"3054".equals(txnsLog.getPayretcode())||
                    "3056".equals(txnsLog.getPayretcode())||"3083".equals(txnsLog.getPayretcode())||"3081".equals(txnsLog.getPayretcode())){
                //返回这些信息时，表示融宝已经接受到交易请求，但是没有同步处理，等待异步通知
        		
        		try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                queryResultBean = quickPayTrade.queryTrade(trade);
                payResult = (ReaPayResultBean) queryResultBean.getResultObj();
                if ("completed".equalsIgnoreCase(payResult.getStatus())) {//交易完成
                	return OrderStatusEnum.SUCCESS;
                }
                if ("failed".equalsIgnoreCase(payResult.getStatus())) {//交易失败
                	return OrderStatusEnum.FAILED;
                }
                if ("wait".equalsIgnoreCase(payResult.getStatus())) {//等待支付，也就是未支付，比如验证码错误，或者交易金额超限等错误，此时状态为支付失败
                	return OrderStatusEnum.FAILED;
                }
                if ("processing".equalsIgnoreCase(payResult.getStatus())) {
                    
                }
            }else{
                //订单状态更新为失败
            	break;
            }
            
        }
		return null;
	}

	
}
