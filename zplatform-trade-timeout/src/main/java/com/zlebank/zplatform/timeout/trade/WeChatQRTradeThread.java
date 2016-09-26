/* 
 * WeChatTradeThread.java  
 * 
 * version TODO
 *
 * 2016年7月22日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.timeout.trade;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.timeout.service.TradeCompleteProcessingService;
import com.zlebank.zplatform.timeout.service.TradeQueueQuery;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.queue.TradeQueueBean;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.service.TradeQueueService;
import com.zlebank.zplatform.trade.utils.SpringContext;
import com.zlebank.zplatform.wechat.qr.service.WeChatQRService;
import com.zlebank.zplatform.wechat.service.WeChatService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年7月22日 下午2:31:20
 * @since
 */
public class WeChatQRTradeThread implements TradeQueueQuery {

	private static final Log log = LogFactory.getLog(WeChatQRTradeThread.class);
	private TradeQueueBean tradeQueueBean;
	private TradeCompleteProcessingService completeProcessingService = (TradeCompleteProcessingService) SpringContext
			.getContext().getBean("tradeCompleteProcessingService");
	private TradeQueueService tradeQueueService = (TradeQueueService) SpringContext
			.getContext().getBean("tradeQueueService");
	private WeChatQRService weChatQRService = (WeChatQRService) SpringContext
			.getContext().getBean("weChatQRService");

	/**
	 *
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		log.info("【微信交易线程查询开始】"+tradeQueueBean.getTxnseqno());
		ResultBean resultBean = weChatQRService.queryOrder(tradeQueueBean.getTxnseqno());
		log.info("【微信交易线程查询结果】"+JSON.toJSONString(resultBean));
		if (resultBean.isResultBool()) {
			if ("PROCESSING".equals(resultBean.getErrCode()) || "USERPAYING".equals(resultBean.getErrCode())) {// 处理中，或者用户支付中，重回队列，等待下次查询
				tradeQueueService.addTradeQueue(tradeQueueBean);
				return;
			}
			completeProcessingService.weChatQRCompleteTrade(tradeQueueBean.getTxnseqno(), resultBean);
		}else{
			tradeQueueService.addTradeQueue(tradeQueueBean);
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

}
