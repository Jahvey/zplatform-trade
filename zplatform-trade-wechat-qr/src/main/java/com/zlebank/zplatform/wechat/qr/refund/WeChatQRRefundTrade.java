/* 
 * WeChatRefundTrade.java  
 * 
 * version TODO
 *
 * 2016年6月8日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.wechat.qr.refund;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.trade.adapter.quickpay.IRefundTrade;
import com.zlebank.zplatform.trade.bean.PayPartyBean;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.enums.ChannelEnmu;
import com.zlebank.zplatform.trade.bean.enums.OrderStatusEnum;
import com.zlebank.zplatform.trade.bean.enums.RefundTypeEnum;
import com.zlebank.zplatform.trade.bean.enums.TradeStatFlagEnum;
import com.zlebank.zplatform.trade.dao.ITxnsOrderinfoDAO;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsOrderinfoModel;
import com.zlebank.zplatform.trade.model.TxnsRefundModel;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.ITxnsRefundService;
import com.zlebank.zplatform.trade.utils.OrderNumber;
import com.zlebank.zplatform.trade.utils.SpringContext;
import com.zlebank.zplatform.wechat.qr.enums.ResultCodeEnum;
import com.zlebank.zplatform.wechat.qr.exception.WXVerifySignFailedException;
import com.zlebank.zplatform.wechat.qr.wx.WXApplication;
import com.zlebank.zplatform.wechat.qr.wx.bean.RefundBean;
import com.zlebank.zplatform.wechat.qr.wx.bean.RefundResultBean;
import com.zlebank.zplatform.wechat.qr.wx.common.WXConfigure;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年6月8日 上午10:01:16
 * @since
 */
public class WeChatQRRefundTrade implements IRefundTrade {

	private static final Log log = LogFactory.getLog(WeChatQRRefundTrade.class);
		
	private ITxnsLogService txnsLogService;
	private ITxnsRefundService txnsRefundService;
	private ITxnsOrderinfoDAO txnsOrderinfoDAO; 
	
	
	public WeChatQRRefundTrade(){
    	txnsLogService = (ITxnsLogService) SpringContext.getContext().getBean("txnsLogService");
    	txnsRefundService = (ITxnsRefundService) SpringContext.getContext().getBean("txnsRefundService");
    	txnsOrderinfoDAO = (ITxnsOrderinfoDAO) SpringContext.getContext().getBean("txnsOrderinfo");
    }


	/**
	 *
	 * @param tradeBean
	 * @return
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor = Throwable.class)
	public ResultBean refund(TradeBean tradeBean) {
		// TODO Auto-generated method stub
		ResultBean resultBean = null;
		try {
			//退款交易流水
			TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(tradeBean.getTxnseqno());
			//原始交易流水
			TxnsLogModel txnsLog_old = txnsLogService.getTxnsLogByTxnseqno(txnsLog.getTxnseqnoOg());
			
			//获取订单
			TxnsOrderinfoModel order = txnsOrderinfoDAO.getOrderByTxnseqno(tradeBean.getTxnseqno());
			if(order.getStatus().equals(OrderStatusEnum.FAILED.getStatus())
					||order.getStatus().equals(OrderStatusEnum.SUCCESS.getStatus())
					||order.getStatus().equals(OrderStatusEnum.INVALID.getStatus())){
				 resultBean = new ResultBean("", "此订单已处理或订单状态不对！");
			}else{
				//更新支付方信息
				PayPartyBean payPartyBean = new PayPartyBean(txnsLog.getTxnseqno(), 
						"07", 
						OrderNumber.getInstance().generateWeChatOrderNO(),//payordno, 
						ChannelEnmu.WEBCHAT_QR.getChnlcode(), 
						WXConfigure.getMchid(), 
						"", 
						DateUtil.getCurrentDateTime(), 
						"", 
						"");
				txnsLogService.updatePayInfo_Fast(payPartyBean);
				WXApplication instance = new WXApplication();
				RefundBean rb = new RefundBean();
				rb.setOut_refund_no(payPartyBean.getPayordno());// 退款流水号（唯一，可当场生成）
				rb.setOut_trade_no(txnsLog_old.getPayordno());// 原商户号（证联生成的）
				rb.setRefund_fee(txnsLog.getAmount()+"");// 退款金额
				rb.setTotal_fee(txnsLog_old.getAmount()+"");// 总金额
				rb.setTransaction_id(txnsLog_old.getPayrettsnseqno());// 原微信订单号（微信返回的）
				RefundResultBean refund = instance.refund(rb,tradeBean.getTxnseqno()); // 进行退款
				log.info("【退款返回结果】" + JSONObject.fromObject(refund));
				//是否调到微信平台
				if(ResultCodeEnum.SUCCESS.getCode().equals(refund.getReturn_code())){//有业务报文
					//微信平台返回的结果
					if(ResultCodeEnum.SUCCESS.getCode().equals(refund.getResult_code())){
						String retcode = "";
						String retinfo = "";
						String transaction_id = refund.getRefund_id();//微信退款订单号
						RefundTypeEnum refundTypeEnum = RefundTypeEnum.fromWeChatValue(refund.getRefund_channel());//退款渠道
						if(ResultCodeEnum.fromValue(refund.getResult_code())==ResultCodeEnum.SUCCESS){
							retcode=refund.getResult_code();
							retinfo = "退款申请成功";
						}else{
							retcode=refund.getErr_code();
							retinfo = refund.getErr_code_des();
						}
						txnsLogService.updateWeChatRefundResult(tradeBean.getTxnseqno(), transaction_id, retcode, retinfo);
						//更新退款交易流水
						TxnsRefundModel refundLog = txnsRefundService.getRefundByTxnseqno(tradeBean.getTxnseqno());
						refundLog.setRefundtype(refundTypeEnum.getCode());
						txnsRefundService.update(refundLog);
						txnsLogService.updateTradeStatFlag(tradeBean.getTxnseqno(), TradeStatFlagEnum.PAYING);
						resultBean =new ResultBean("success");
					}else{
						resultBean = new ResultBean(refund.getErr_code(), refund.getErr_code_des()); 
					}
				}else{//无业务报文
					resultBean = new ResultBean("T000", refund.getReturn_msg());
				}
			}
		} catch (WXVerifySignFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultBean;
	}

}
