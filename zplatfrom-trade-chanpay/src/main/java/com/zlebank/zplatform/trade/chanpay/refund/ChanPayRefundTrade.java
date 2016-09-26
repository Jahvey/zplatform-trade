/* 
 * ChanPayRefundTrade.java  
 * 
 * version TODO
 *
 * 2016年5月20日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.chanpay.refund;

import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.acc.pojo.Money;
import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.trade.adapter.quickpay.IRefundTrade;
import com.zlebank.zplatform.trade.bean.PayPartyBean;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.chanpay.bean.ReturnMessageBean;
import com.zlebank.zplatform.trade.chanpay.bean.order.RefundOrderBean;
import com.zlebank.zplatform.trade.chanpay.service.ChanPayService;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsRefundModel;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.ITxnsRefundService;
import com.zlebank.zplatform.trade.utils.ConsUtil;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年5月20日 下午4:19:03
 * @since 
 */
public class ChanPayRefundTrade implements IRefundTrade{
	
	private static final Log log = LogFactory.getLog(ChanPayRefundTrade.class);
	@Autowired
	private ITxnsLogService txnsLogService;
	@Autowired
	private ITxnsRefundService txnsRefundService;
	@Autowired
	private ChanPayService chanPayService;
	/**
	 *
	 * @param tradeBean
	 * @return
	 */
	@Override
	public ResultBean refund(TradeBean tradeBean) {
		TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(tradeBean.getTxnseqno());
		TxnsRefundModel refund = txnsRefundService.getRefundByTxnseqno(tradeBean.getTxnseqno());
		TxnsLogModel txnsLog_old = txnsLogService.getTxnsLogByTxnseqno(txnsLog.getTxnseqnoOg());
		
		try {
			String payOrderNo = (UUID.randomUUID().toString()).replace("-", "");
					refundToChanPay(txnsLog_old.getPayordno(),Money.valueOf(refund.getAmount()),payOrderNo);
		} catch (TradeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			PayPartyBean payPartyBean = new PayPartyBean(tradeBean.getTxnseqno(),
					"07", "", "92000001",
					ConsUtil.getInstance().cons.getCmbc_merid(), "",
					DateUtil.getCurrentDateTime(), "", txnsLog_old.getPan());
			payPartyBean.setPanName(txnsLog_old.getPanName());
			payPartyBean.setPayretcode("REFUND_FAIL");
			payPartyBean.setPayretinfo(e.getMessage());
			txnsLogService.updatePayInfo_Fast(payPartyBean);
			return new ResultBean("0097", e.getMessage());
		}
		//记录支付流水信息
		PayPartyBean payPartyBean = new PayPartyBean(tradeBean.getTxnseqno(),
				"07", "", "92000001",
				ConsUtil.getInstance().cons.getCmbc_merid(), "",
				DateUtil.getCurrentDateTime(), "", txnsLog_old.getPan());
		payPartyBean.setPanName(txnsLog_old.getPanName());
		txnsLogService.updatePayInfo_Fast(payPartyBean);
		return new ResultBean("success");
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void refundToChanPay(String orig_order_no,Money refundAmt,String outer_trade_no) throws TradeException{
		RefundOrderBean refundOrderBean = new RefundOrderBean();
		refundOrderBean.setVersion(ConsUtil.getInstance().cons.getChanpay_version());
		refundOrderBean.setPartner_id(ConsUtil.getInstance().cons.getChanpay_partner_id());
		refundOrderBean.set_input_charset(ConsUtil.getInstance().cons.getChanpay_input_charset());
		refundOrderBean.setOuter_trade_no(outer_trade_no);
		refundOrderBean.setOrig_outer_trade_no("151e2f0b7a5d489e95ddf328b04d285b");
		refundOrderBean.setService("cjt_create_refund");
		refundOrderBean.setNotify_url(ConsUtil.getInstance().cons.getChanpay_refund_url());
		refundOrderBean.setRefund_amount(refundAmt.toYuan());
		ReturnMessageBean refund = chanPayService.refund(refundOrderBean);
		if("REFUND_SUCCESS".equals(refund.getError_code())){
			
		}else{
			throw new TradeException("T000",refund.getError_message()+","+refund.getMemo());
		}	
	}

}
