/* 
 * TradeQueueScanServiceImp.java  
 * 
 * version TODO
 *
 * 2016年7月19日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.timeout.service.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.timeout.pool.TradeQueueThreadPool;
import com.zlebank.zplatform.timeout.service.TradeQueueQuery;
import com.zlebank.zplatform.timeout.service.TradeQueueScanService;
import com.zlebank.zplatform.timeout.trade.CMBCTradeThread;
import com.zlebank.zplatform.timeout.trade.ChanPayTradeThread;
import com.zlebank.zplatform.timeout.trade.ReaPayTradeThread;
import com.zlebank.zplatform.timeout.trade.WeChatQRTradeThread;
import com.zlebank.zplatform.timeout.trade.WeChatTradeThread;
import com.zlebank.zplatform.trade.bean.ReaPayResultBean;
import com.zlebank.zplatform.trade.bean.enums.ChannelEnmu;
import com.zlebank.zplatform.trade.bean.enums.TradeQueueEnum;
import com.zlebank.zplatform.trade.bean.enums.TradeStatFlagEnum;
import com.zlebank.zplatform.trade.bean.queue.TradeQueueBean;
import com.zlebank.zplatform.trade.model.ChnlDetaModel;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.service.IChnlDetaService;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.TradeQueueService;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.DateUtil;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年7月19日 下午5:28:00
 * @since 
 */
@Service("tradeQueueScanService")
public class TradeQueueScanServiceImpl implements TradeQueueScanService{

	private static final Log log = LogFactory.getLog(TradeQueueScanServiceImpl.class);
	@Autowired
	private TradeQueueService tradeQueueService;
	@Autowired
	private IChnlDetaService chnlDetaService;
	@Autowired
	private ITxnsLogService txnsLogService;
	
	
	public void scanTradeQueue(){
		log.info("【开始交易队列扫描任务】");
		long queueSize = tradeQueueService.getQueueSize(TradeQueueEnum.TRADEQUEUE);
		log.info("【交易队列大小】"+queueSize);
		if(queueSize>0){
			for(int i=0;i<queueSize;i++){
				TradeQueueBean tradeQueueBean = tradeQueueService.tradeQueuePop();
				log.info("【交易队列中第"+i+"位】"+JSON.toJSONString(tradeQueueBean));
				if(tradeQueueBean==null){//此处为空时很可能是队列中已经没有交易了，跳出循环
					break;
				}
				if(isTimeOut(tradeQueueBean.getTxnDateTime())){//判断叫时间是否超时，超过一个小时的交易为超时
					if(ConsUtil.getInstance().cons.getIs_junit()!=1){
						log.info("【交易队列中"+tradeQueueBean.getTxnseqno()+"超时】交易时间:"+tradeQueueBean.getTxnDateTime());
						break;
					}
					//若果是退款交易移入退款交易队列中
					if("4000".equals(tradeQueueBean.getBusiType())){
						tradeQueueService.addRefundQueue(tradeQueueBean);
					}
				}
				//判断交易是否已经完成，账务也处理完成
				TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(tradeQueueBean.getTxnseqno());
				if(txnsLog==null){
					tradeQueueService.addTradeQueue(tradeQueueBean);
				}
				log.info("【交易队列中"+tradeQueueBean.getTxnseqno()+"交易数据】"+JSON.toJSONString(txnsLog));
				TradeStatFlagEnum tradeStatFlagEnum = TradeStatFlagEnum.fromValue(txnsLog.getTradestatflag());
				if(tradeStatFlagEnum==TradeStatFlagEnum.PAYING||tradeStatFlagEnum==TradeStatFlagEnum.OVERTIME){//交易支付中或者交易超时
					ChnlDetaModel chnlDetaModel = chnlDetaService.getChannelByCode(tradeQueueBean.getPayInsti());
					//交易具体的java实现
					tradeQueueBean.setImpl(chnlDetaModel.getImpl());
					TradeQueueQuery tradeQueueQuery = null;
					ChannelEnmu channelEnmu = ChannelEnmu.fromValue(tradeQueueBean.getPayInsti());
					if(ChannelEnmu.CHANPAYCOLLECTMONEY==channelEnmu){
						tradeQueueQuery = new ChanPayTradeThread();
						tradeQueueQuery.setTradeQueueBean(tradeQueueBean);
						log.info("【交易队列中"+tradeQueueBean.getTxnseqno()+"交易为畅捷代收渠道】");
					}else if (ChannelEnmu.CMBCWITHHOLDING==channelEnmu) {
						tradeQueueQuery = new CMBCTradeThread();
						tradeQueueQuery.setTradeQueueBean(tradeQueueBean);
						log.info("【交易队列中"+tradeQueueBean.getTxnseqno()+"交易为民生代扣渠道】");
					}else if(ChannelEnmu.WEBCHAT==channelEnmu){
						tradeQueueQuery = new WeChatTradeThread();
						tradeQueueQuery.setTradeQueueBean(tradeQueueBean);
						log.info("【交易队列中"+tradeQueueBean.getTxnseqno()+"交易为微信渠道】");
					}else if(ChannelEnmu.REAPAY==channelEnmu){
						tradeQueueQuery = new ReaPayTradeThread();
						tradeQueueQuery.setTradeQueueBean(tradeQueueBean);
						log.info("【交易队列中"+tradeQueueBean.getTxnseqno()+"交易为融宝快捷支付渠道】");
					}else if(ChannelEnmu.WEBCHAT_QR==channelEnmu){
						tradeQueueQuery = new WeChatQRTradeThread();
						tradeQueueQuery.setTradeQueueBean(tradeQueueBean);
						log.info("【交易队列中"+tradeQueueBean.getTxnseqno()+"交易为融宝快捷支付渠道】");
					}else{
						log.info("【无可用的渠道】");
						continue;
					}
					if(ConsUtil.getInstance().cons.getIs_junit()==1){
						tradeQueueQuery.run();
					}else{
						TradeQueueThreadPool.getInstance().executeMission(tradeQueueQuery);
					}
				}else{
					continue;
				}
				
				
			}
			
		}
		log.info("【交易队列扫描任务结束】");
	}


	/**
	 *
	 */
	@Override
	public void scanOverTimeQueue() {
		// TODO Auto-generated method stub
		log.info("【开始交易超时队列扫描任务】");
		long queueSize = tradeQueueService.getQueueSize(TradeQueueEnum.TIMEOUTQUEUE);
		log.info("【交易超时队列大小】"+queueSize);
		if(queueSize>0){
			for(int i=0;i<queueSize;i++){
				TradeQueueBean tradeQueueBean = tradeQueueService.tradeQueuePop();
				log.info("【超时交易队列中第"+i+"位】"+JSON.toJSONString(tradeQueueBean));
				if(tradeQueueBean==null){//此处为空时很可能是队列中已经没有交易了，跳出循环
					break;
				}
				if(isTimeOut(tradeQueueBean.getTxnDateTime())){//判断叫时间是否超时，超过一个小时的交易为超时
					if(ConsUtil.getInstance().cons.getIs_junit()!=1){
						log.info("【超时交易队列中"+tradeQueueBean.getTxnseqno()+"超时】交易时间:"+tradeQueueBean.getTxnDateTime());
						break;
					}
					//若果是退款交易移入退款交易队列中
					if("4000".equals(tradeQueueBean.getBusiType())){
						tradeQueueService.addRefundQueue(tradeQueueBean);
					}
				}
				//判断交易是否已经完成，账务也处理完成
				TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(tradeQueueBean.getTxnseqno());
				if(txnsLog==null){
					tradeQueueService.addTimeOutQueue(tradeQueueBean);
				}
				log.info("【超时交易队列中"+tradeQueueBean.getTxnseqno()+"交易数据】"+JSON.toJSONString(txnsLog));
				TradeStatFlagEnum tradeStatFlagEnum = TradeStatFlagEnum.fromValue(txnsLog.getTradestatflag());
				if(tradeStatFlagEnum==TradeStatFlagEnum.OVERTIME){//交易支付中或者交易超时
					ChnlDetaModel chnlDetaModel = chnlDetaService.getChannelByCode(tradeQueueBean.getPayInsti());
					//交易具体的java实现
					tradeQueueBean.setImpl(chnlDetaModel.getImpl());
					TradeQueueQuery tradeQueueQuery = null;
					ChannelEnmu channelEnmu = ChannelEnmu.fromValue(tradeQueueBean.getPayInsti());
					if(ChannelEnmu.CHANPAYCOLLECTMONEY==channelEnmu){
						tradeQueueQuery = new ChanPayTradeThread();
						tradeQueueQuery.setTradeQueueBean(tradeQueueBean);
						log.info("【超时交易队列中"+tradeQueueBean.getTxnseqno()+"交易为畅捷代收渠道】");
					}else if (ChannelEnmu.CMBCWITHHOLDING==channelEnmu) {
						tradeQueueQuery = new CMBCTradeThread();
						tradeQueueQuery.setTradeQueueBean(tradeQueueBean);
						log.info("【超时交易队列中"+tradeQueueBean.getTxnseqno()+"交易为民生代扣渠道】");
					}else if(ChannelEnmu.WEBCHAT==channelEnmu){
						tradeQueueQuery = new WeChatTradeThread();
						tradeQueueQuery.setTradeQueueBean(tradeQueueBean);
						log.info("【超时交易队列中"+tradeQueueBean.getTxnseqno()+"交易为微信渠道】");
					}else if(ChannelEnmu.REAPAY==channelEnmu){
						tradeQueueQuery = new ReaPayTradeThread();
						tradeQueueQuery.setTradeQueueBean(tradeQueueBean);
						log.info("【超时交易队列中"+tradeQueueBean.getTxnseqno()+"交易为融宝快捷支付渠道】");
					}else if(ChannelEnmu.WEBCHAT_QR==channelEnmu){
						tradeQueueQuery = new WeChatQRTradeThread();
						tradeQueueQuery.setTradeQueueBean(tradeQueueBean);
						log.info("【交易队列中"+tradeQueueBean.getTxnseqno()+"交易为融宝快捷支付渠道】");
					}else{
						continue;
					}
					if(ConsUtil.getInstance().cons.getIs_junit()==1){
						tradeQueueQuery.run();
					}else{
						TradeQueueThreadPool.getInstance().executeMission(tradeQueueQuery);
					}
				}else{
					continue;
				}
			}
		}
		log.info("【交易队列扫描任务结束】");
	}
	
	public boolean isTimeOut(String dateTime){
		Date trade_date = DateUtil.parse(DateUtil.DEFAULT_DATE_FROMAT, dateTime);
		long time = System.currentTimeMillis()-trade_date.getTime();//交易时间和当前的时间差
		double hour = time/1000.0/60.0/60.0;//相差的小时
		return hour>=1;
	}
	
	/**
	 *扫描退款交易队列
	 */
	@Override
	public void scanRefundTradeQueue() {
		log.info("【开始退款交易队列扫描任务】");
		long queueSize = tradeQueueService.getQueueSize(TradeQueueEnum.REFUNDQUEUE);
		log.info("【交易退款队列大小】"+queueSize);
		
		if(queueSize>0){
			for(int i=0;i<queueSize;i++){
				TradeQueueBean tradeQueueBean = tradeQueueService.refundQueuePop();
				log.info("【退款交易队列中第"+i+"位】"+JSON.toJSONString(tradeQueueBean));
				if(tradeQueueBean==null){//此处为空时很可能是队列中已经没有交易了，跳出循环
					break;
				}
				//判断交易是否已经完成，账务也处理完成
				TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(tradeQueueBean.getTxnseqno());
				if(txnsLog==null){
					tradeQueueService.addRefundQueue(tradeQueueBean);
				}
				log.info("【退款交易队列中"+tradeQueueBean.getTxnseqno()+"交易数据】"+JSON.toJSONString(txnsLog));
				TradeStatFlagEnum tradeStatFlagEnum = TradeStatFlagEnum.fromValue(txnsLog.getTradestatflag());
				if(tradeStatFlagEnum==TradeStatFlagEnum.PAYING||tradeStatFlagEnum==TradeStatFlagEnum.OVERTIME){//交易支付中或者交易超时
					ChnlDetaModel chnlDetaModel = chnlDetaService.getChannelByCode(tradeQueueBean.getPayInsti());
					//交易具体的java实现
					tradeQueueBean.setImpl(chnlDetaModel.getImpl());
					TradeQueueQuery tradeQueueQuery = null;
					ChannelEnmu channelEnmu = ChannelEnmu.fromValue(tradeQueueBean.getPayInsti());
					if(ChannelEnmu.WEBCHAT==channelEnmu){
						tradeQueueQuery = new WeChatTradeThread();
						tradeQueueQuery.setTradeQueueBean(tradeQueueBean);
						log.info("【交易队列中"+tradeQueueBean.getTxnseqno()+"交易为微信渠道】");
					}else if(ChannelEnmu.WEBCHAT_QR==channelEnmu){
						tradeQueueQuery = new WeChatQRTradeThread();
						tradeQueueQuery.setTradeQueueBean(tradeQueueBean);
						log.info("【交易队列中"+tradeQueueBean.getTxnseqno()+"交易为融宝快捷支付渠道】");
					}else{
						log.info("【无可用的渠道】");
						continue;
					}
					if(ConsUtil.getInstance().cons.getIs_junit()==1){
						tradeQueueQuery.run();
					}else{
						TradeQueueThreadPool.getInstance().executeMission(tradeQueueQuery);
					}
				}else{
					continue;
				}
				
				
			}
			
		}
		log.info("【交易队列扫描任务结束】");
	}
}
