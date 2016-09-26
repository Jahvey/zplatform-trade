/* 
 * ReaPayTradeThread.java  
 * 
 * version TODO
 *
 * 2016年7月25日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.timeout.trade;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.timeout.service.TradeCompleteProcessingService;
import com.zlebank.zplatform.timeout.service.TradeQueueQuery;
import com.zlebank.zplatform.trade.adapter.quickpay.IQuickPayTrade;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.queue.TradeQueueBean;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.service.ITxnsQuickpayService;
import com.zlebank.zplatform.trade.service.TradeQueueService;
import com.zlebank.zplatform.trade.utils.SpringContext;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年7月25日 下午5:25:42
 * @since 
 */
public class ReaPayTradeThread implements TradeQueueQuery{
	private static final Log log = LogFactory.getLog(CMBCTradeThread.class);
	private TradeQueueBean tradeQueueBean;
	private TradeCompleteProcessingService completeProcessingService = (TradeCompleteProcessingService) SpringContext.getContext().getBean("tradeCompleteProcessingService");
	private TradeQueueService tradeQueueService = (TradeQueueService) SpringContext.getContext().getBean("tradeQueueService");
	private ITxnsQuickpayService txnsQuickpayService = (ITxnsQuickpayService) SpringContext.getContext().getBean("txnsQuickpayService");;
	
	/**
	 *
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		log.info("【融宝交易线程查询开始】"+tradeQueueBean.getTxnseqno());
		String reapayOrderNo = txnsQuickpayService.getReapayOrderNo(tradeQueueBean.getTxnseqno());
		log.info("【融宝交易线程，融宝订单号】"+reapayOrderNo);
		TradeBean trade = new TradeBean();
		trade.setReaPayOrderNo(reapayOrderNo);
		trade.setTxnseqno(tradeQueueBean.getTxnseqno());
		try {
			ResultBean resultBean = getTradeQuery().queryTrade(trade);
			log.info("【融宝交易线程查询结果】"+JSON.toJSONString(resultBean));
			if(resultBean.isResultBool()){
				if("processing".equals(resultBean.getErrCode())){//支付中，重回队列，等待下次查询
					tradeQueueService.addTradeQueue(tradeQueueBean);
					return ;
				}
				completeProcessingService.reaPayCompleteTrade(trade.getTxnseqno(), resultBean);
			}else{
				tradeQueueService.addTradeQueue(tradeQueueBean);
			}
			
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
	}

	/**
	 *
	 * @param tradeQueueBean
	 */
	@Override
	public void setTradeQueueBean(TradeQueueBean tradeQueueBean) {
		// TODO Auto-generated method stub
		this.tradeQueueBean = tradeQueueBean;
	}
	public IQuickPayTrade getTradeQuery() throws TradeException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		IQuickPayTrade  quickPayTrade = null;
       if(StringUtil.isNotEmpty(tradeQueueBean.getImpl())){
            quickPayTrade =(IQuickPayTrade) Class.forName(tradeQueueBean.getImpl()).newInstance();
       }
       return quickPayTrade;
   }
}
