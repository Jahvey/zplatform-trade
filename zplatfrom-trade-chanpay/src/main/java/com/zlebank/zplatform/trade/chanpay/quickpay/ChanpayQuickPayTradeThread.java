/* 
 * ChanpayQuickPayTradeThread.java  
 * 
 * version TODO
 *
 * 2016年6月30日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.chanpay.quickpay;

import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.trade.adapter.quickpay.IQuickPayTrade;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.enums.ChannelEnmu;
import com.zlebank.zplatform.trade.bean.enums.TradeTypeEnum;
import com.zlebank.zplatform.trade.chanpay.service.ChanPayQuickPayService;
import com.zlebank.zplatform.trade.dao.RealnameAuthDAO;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.model.PojoCardBind;
import com.zlebank.zplatform.trade.model.PojoRealnameAuth;
import com.zlebank.zplatform.trade.service.CardBindService;
import com.zlebank.zplatform.trade.utils.SMSThreadPool;
import com.zlebank.zplatform.trade.utils.SMSUtil;
import com.zlebank.zplatform.trade.utils.SpringContext;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年6月30日 下午4:19:43
 * @since 
 */
public class ChanpayQuickPayTradeThread implements IQuickPayTrade{
	
	private TradeBean tradeBean;
	private TradeTypeEnum tradeType;
	
	private ChanPayQuickPayService chanPayQuickPayService = (ChanPayQuickPayService) SpringContext.getContext().getBean("chanPayQuickPayService");
	private RealnameAuthDAO realnameAuthDAO  = (RealnameAuthDAO) SpringContext.getContext().getBean("realnameAuthDAO");
	private CardBindService cardBindService = (CardBindService) SpringContext.getContext().getBean("cardBindService");
	
	public ChanpayQuickPayTradeThread(){
		/*chanPayQuickPayService = (ChanPayQuickPayService) SpringContext.getContext().getBean("chanPayQuickPayService");
		realnameAuthDAO = (RealnameAuthDAO) SpringContext.getContext().getBean("realnameAuthDAO");
		cardBindService = (CardBindService) SpringContext.getContext().getBean("cardBindService");*/
	}
	
	/**
	 *
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (tradeBean == null) {
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
	 *
	 * @param trade
	 * @return
	 */
	@Override
	public ResultBean sendSms(TradeBean trade) {
		String mobile = trade.getMobile();
		SMSThreadPool.getInstance().executeMission(
				new SMSUtil(mobile, "", trade.getTn(), DateUtil
						.getCurrentDateTime(), "", trade
						.getMiniCardNo(), trade.getAmount_y()));
		return new ResultBean("success");
	}
	/**
	 *
	 * @param trade
	 * @return
	 */
	@Override
	public ResultBean bankSign(TradeBean trade) {
		ResultBean resultBean = null;
		try {
			//实名认证，因为不确定畅捷哪家银行不需要实名认证，所以全部都进行实名认证
			resultBean = chanPayQuickPayService.realNameAuth(trade);
			if(!resultBean.isResultBool()){
				return resultBean;
			}
			if(trade.getCardId()==0){//未绑定银行卡
		    	resultBean = chanPayQuickPayService.protocolSign(trade);
			}else{//绑定银行卡
				PojoCardBind cardBind = cardBindService.getCardBind(trade.getCardId(), ChannelEnmu.CHANPAYCOLLECTMONEY.getChnlcode());
				if(cardBind==null){//未签署协议
					resultBean = chanPayQuickPayService.protocolSign(trade);
				}else{//已签署协议
					return sendSms(trade);
				}
			}
		} catch (TradeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultBean = new ResultBean(e.getCode(), e.getMessage());
		}
		if(resultBean.isResultBool()){
			resultBean = sendSms(trade);
		}
		return resultBean;
	}
	/**
	 *
	 * @param trade
	 * @return
	 */
	@Override
	public ResultBean submitPay(TradeBean trade) {
		ResultBean resultBean = null;
		try {
			resultBean = chanPayQuickPayService.collectMoney(trade);
			if(resultBean.isResultBool()){
				chanPayQuickPayService.dealWithAccounting(resultBean, trade.getTxnseqno());
			}
		} catch (TradeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultBean = new ResultBean(e.getCode(), e.getMessage());
		}
		
		return resultBean;
	}
	/**
	 *
	 * @param trade
	 * @return
	 */
	@Override
	public ResultBean queryTrade(TradeBean trade) {
		return chanPayQuickPayService.queryTrade(trade.getTxnseqno());
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
