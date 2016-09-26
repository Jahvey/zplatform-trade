/* 
 * CMBCCrossLineQuickPayServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年7月21日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.cmbc.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.commons.dao.ProvinceDAO;
import com.zlebank.zplatform.commons.dao.pojo.BusiTypeEnum;
import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.sms.service.ISMSService;
import com.zlebank.zplatform.trade.adapter.accounting.IAccounting;
import com.zlebank.zplatform.trade.bean.AppPartyBean;
import com.zlebank.zplatform.trade.bean.PayPartyBean;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.enums.ChannelEnmu;
import com.zlebank.zplatform.trade.bean.enums.TradeStatFlagEnum;
import com.zlebank.zplatform.trade.cmbc.exception.CMBCTradeException;
import com.zlebank.zplatform.trade.cmbc.service.CMBCCrossLineQuickPayService;
import com.zlebank.zplatform.trade.cmbc.service.ICMBCQuickPayService;
import com.zlebank.zplatform.trade.cmbc.service.ICMBCTransferService;
import com.zlebank.zplatform.trade.dao.ITxnsOrderinfoDAO;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.factory.AccountingAdapterFactory;
import com.zlebank.zplatform.trade.model.PojoRealnameAuth;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsOrderinfoModel;
import com.zlebank.zplatform.trade.model.TxnsWithholdingModel;
import com.zlebank.zplatform.trade.service.IQuickpayCustService;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.ITxnsQuickpayService;
import com.zlebank.zplatform.trade.service.ITxnsWithholdingService;
import com.zlebank.zplatform.trade.service.TradeNotifyService;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.OrderNumber;
import com.zlebank.zplatform.trade.utils.UUIDUtil;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年7月21日 下午2:10:19
 * @since 
 */
@Service("cmbcCrossLineQuickPayService")
public class CMBCCrossLineQuickPayServiceImpl implements CMBCCrossLineQuickPayService{
	
	private static final Log log = LogFactory.getLog(CMBCCrossLineQuickPayServiceImpl.class);
	@Autowired
	private ITxnsQuickpayService txnsQuickpayService;
	@Autowired
	private ICMBCQuickPayService cmbcQuickPayService;
	@Autowired
	private ProvinceDAO provinceDAO;
	@Autowired
	private ITxnsLogService txnsLogService;
	@Autowired
	private ICMBCTransferService cmbcTransferService;
	@Autowired
	private ITxnsOrderinfoDAO txnsOrderinfoDAO;
	@Autowired
	private IQuickpayCustService quickpayCustService;
	@Autowired
	private ISMSService smsService;
	@Autowired
	private ITxnsWithholdingService txnsWithholdingService;
	@Autowired
	private TradeNotifyService tradeNotifyService;
	
	public ResultBean bankSign(TradeBean tradeBean){
		
		ResultBean resultBean = null;
		try {
			// 卡信息进行实名认证
			PojoRealnameAuth realnameAuth = new PojoRealnameAuth(tradeBean);
			// 保存卡信息认证流水
			String payorderNo = txnsQuickpayService.saveCMBCOuterBankCardSign(tradeBean);
			resultBean = cmbcTransferService.realNameAuth(realnameAuth);

			if (resultBean.isResultBool()) {
				txnsQuickpayService.updateCMBCSMSResult(payorderNo, "00",
						"签约成功");
				if (!"01".equals(tradeBean.getTradeType())) {
					// 保存绑卡信息
					quickpayCustService.updateCardStatus(tradeBean.getMerUserId(),
							tradeBean.getCardNo());
				}
			} else {
				txnsQuickpayService.updateCMBCSMSResult(payorderNo,
						resultBean.getErrCode(), resultBean.getErrMsg());
			}
			if("01".equals(tradeBean.getTradeType())) {
				return resultBean;
			}
		} catch (CMBCTradeException e1) {
			// TODO Auto-generated catch block
			resultBean = new ResultBean(e1.getCode(), e1.getMessage());
			e1.printStackTrace();
			return resultBean;
		} catch (TradeException e) {
			// TODO Auto-generated catch block
			resultBean = new ResultBean(e.getCode(), e.getMessage());
			e.printStackTrace();
			return resultBean;
		}
		txnsLogService.updateTradeStatFlag(tradeBean.getTxnseqno(), TradeStatFlagEnum.READY);
		return resultBean;
	}

	/**
	 *
	 * @param tradeBean
	 * @return
	 */
	@Override
	public ResultBean submitPay(TradeBean tradeBean) {
		ResultBean resultBean = null;
		try {
			log.info("CMBC submit Pay start!");
			if (log.isDebugEnabled()) {
				try {
					log.debug(JSON.toJSONString(tradeBean));
				} catch (Exception e) {
				}
			}
			resultBean = null;
			int retCode = smsService.verifyCode(tradeBean.getMobile(),
					tradeBean.getTn(), tradeBean.getIdentifyingCode());
			if (retCode == 2) {
				resultBean = new ResultBean("30HK", "交易失败，动态口令或短信验证码校验失败");
			} else if (retCode == 3) {
				resultBean = new ResultBean("30HK", "交易失败，动态口令或短信验证码校验失败");
			}
			if (resultBean != null) {
				//txnsLogService.updatePayInfo_Fast_result(tradeBean.getTxnseqno(), resultBean.getErrCode(),resultBean.getErrMsg());
				//txnsLogService.updateCoreRetResult(tradeBean.getTxnseqno(),resultBean.getErrCode(), resultBean.getErrMsg());
				txnsLogService.updateSMSErrorData(tradeBean.getTxnseqno(), resultBean.getErrCode(),resultBean.getErrMsg());
                txnsOrderinfoDAO.updateOrderToFail(tradeBean.getTxnseqno());
                PayPartyBean payPartyBean = new PayPartyBean(tradeBean.getTxnseqno(),
    					"01", "", ChannelEnmu.CMBCWITHHOLDING.getChnlcode(),
    					ConsUtil.getInstance().cons.getCmbc_merid(), "",
    					DateUtil.getCurrentDateTime(), "", tradeBean.getCardNo());
    			payPartyBean.setPanName(tradeBean.getAcctName());
    			txnsLogService.updatePayInfo_Fast(payPartyBean);
				return resultBean;
			}
			// 更新支付方信息
			PayPartyBean payPartyBean = new PayPartyBean(tradeBean.getTxnseqno(),
					"01", OrderNumber.getInstance().generateWithholdingOrderNo(), "93000002",
					ConsUtil.getInstance().cons.getCmbc_merid(), "",
					DateUtil.getCurrentDateTime(), "", tradeBean.getCardNo());
			payPartyBean.setPanName(tradeBean.getAcctName());
			txnsLogService.updatePayInfo_Fast(payPartyBean);
			tradeBean.setPayOrderNo(payPartyBean.getPayordno());
			tradeBean.setPayinstiId(ChannelEnmu.CMBCWITHHOLDING.getChnlcode());
			// 获取持卡人所属省份代码
			tradeBean.setProvno(provinceDAO.getProvinceByXZCode(tradeBean.getCertId().substring(0, 2)).getProvinceId()+ "");
			// 记录快捷交易流水
			String payorderno = txnsQuickpayService.saveCMBCOuterWithholding(tradeBean);
			txnsLogService.updateTradeStatFlag(tradeBean.getTxnseqno(), TradeStatFlagEnum.PAYING);
			resultBean = cmbcQuickPayService.crossLineWithhold(tradeBean);
			if(resultBean.isResultBool()) {
				TxnsWithholdingModel withholding = (TxnsWithholdingModel) resultBean.getResultObj();
				// 更新快捷交易流水
				txnsQuickpayService.updateCMBCWithholdingResult(withholding,payorderno);
			} else {// 交易失败
				txnsOrderinfoDAO.updateOrderToFail(tradeBean.getTxnseqno());
				resultBean = new ResultBean("T000", "交易失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			txnsOrderinfoDAO.updateOrderToFail(tradeBean.getTxnseqno());
			resultBean = new ResultBean("T000", "交易失败");
		}

		log.info("CMBC submit Pay end!");
		return resultBean;
	}
	
	public ResultBean dealWithAccounting(String txnseqno,ResultBean resultBean){
		
		TxnsWithholdingModel withholding = (TxnsWithholdingModel) resultBean.getResultObj();
		TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
        PayPartyBean payPartyBean = null;
        if(StringUtil.isNotEmpty(withholding.getOrireqserialno())){
            TxnsWithholdingModel old_withholding = txnsWithholdingService.getWithholdingBySerialNo(withholding.getOrireqserialno());
            //更新支付方信息
            payPartyBean = new PayPartyBean(txnseqno,"01", withholding.getOrireqserialno(), old_withholding.getChnlcode(), ConsUtil.getInstance().cons.getCmbc_merid(), "", DateUtil.getCurrentDateTime(), "",old_withholding.getAccno(),withholding.getPayserialno());
        }else{
            payPartyBean = new PayPartyBean(txnseqno,"01", withholding.getSerialno(), withholding.getChnlcode(), ConsUtil.getInstance().cons.getCmbc_merid(), "", DateUtil.getCurrentDateTime(), "",withholding.getAccno(),withholding.getPayserialno());
        }
        payPartyBean.setPanName(withholding.getAccname());
        payPartyBean.setPayretcode(withholding.getExeccode());
        payPartyBean.setPayretinfo(withholding.getExecmsg());
        txnsLogService.updateCMBCTradeData(payPartyBean);
        //txnsLogService.updatePayInfo_Fast(payPartyBean);
        //更新交易流水中心应答信息
        //txnsLogService.updateCMBCWithholdingRetInfo(txnseqno, withholding);
        //更新核心数据
        //txnsLogService.updateCMBCCoreData(payPartyBean);
        String commiteTime = DateUtil.getCurrentDateTime();
        
        /**账务处理开始 **/
        // 应用方信息
        try {
            AppPartyBean appParty = new AppPartyBean("","000000000000", commiteTime,DateUtil.getCurrentDateTime(), txnseqno, "");
            txnsLogService.updateAppInfo(appParty);
            IAccounting accounting = AccountingAdapterFactory.getInstance().getAccounting(BusiTypeEnum.fromValue(txnsLog.getBusitype()));
            accounting.accountedFor(txnseqno);
            //更新订单状态
            txnsOrderinfoDAO.updateOrderToSuccess(txnseqno);
            tradeNotifyService.notify(txnseqno);
        } catch (Exception e) {
            e.printStackTrace();
            resultBean = new ResultBean("T000", e.getMessage());
        }
		return resultBean;
	}
	
	public ResultBean queryTrade(String txnseqno){
		
		return cmbcQuickPayService.queryCrossLineTrade(txnseqno);
	}
	
}
