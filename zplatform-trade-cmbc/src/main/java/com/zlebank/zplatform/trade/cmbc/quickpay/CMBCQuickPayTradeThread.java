package com.zlebank.zplatform.trade.cmbc.quickpay;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.commons.dao.ProvinceDAO;
import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.sms.service.ISMSService;
import com.zlebank.zplatform.trade.adapter.quickpay.IQuickPayTrade;
import com.zlebank.zplatform.trade.bean.PayPartyBean;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.enums.TradeTypeEnum;
import com.zlebank.zplatform.trade.cmbc.exception.CMBCTradeException;
import com.zlebank.zplatform.trade.cmbc.service.ICMBCQuickPayService;
import com.zlebank.zplatform.trade.cmbc.service.ICMBCTransferService;
import com.zlebank.zplatform.trade.dao.ITxnsOrderinfoDAO;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.model.PojoRealnameAuth;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsWithholdingModel;
import com.zlebank.zplatform.trade.service.IQuickpayCustService;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.ITxnsQuickpayService;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.SMSThreadPool;
import com.zlebank.zplatform.trade.utils.SMSUtil;
import com.zlebank.zplatform.trade.utils.SpringContext;

/**
 * 民生银行代扣快捷支付 Class Description
 *
 * @author guojia
 * @version
 * @date 2015年12月11日 上午11:46:07
 * @since
 */
public class CMBCQuickPayTradeThread implements IQuickPayTrade {
	private static final Log log = LogFactory
			.getLog(CMBCQuickPayTradeThread.class);
	private static final String PAYINSTID = "93000002";
	private TradeBean tradeBean;
	private TradeTypeEnum tradeType;

	private ITxnsQuickpayService txnsQuickpayService;

	private ICMBCQuickPayService cmbcQuickPayService;
	private ProvinceDAO provinceDAO;
	private ITxnsLogService txnsLogService;
	private ICMBCTransferService cmbcTransferService;
	private ITxnsOrderinfoDAO txnsOrderinfoDAO;
	private IQuickpayCustService quickpayCustService;
	private ISMSService smsService;

	public CMBCQuickPayTradeThread() {
		txnsQuickpayService = (ITxnsQuickpayService) SpringContext.getContext()
				.getBean("txnsQuickpayService");
		provinceDAO = (ProvinceDAO) SpringContext.getContext().getBean(
				"provinceDAO");
		txnsLogService = (ITxnsLogService) SpringContext.getContext().getBean(
				"txnsLogService");
		;
		cmbcQuickPayService = (ICMBCQuickPayService) SpringContext.getContext()
				.getBean("cmbcQuickPayService");
		cmbcTransferService = (ICMBCTransferService) SpringContext.getContext()
				.getBean("cmbcTransferService");
		txnsOrderinfoDAO = (ITxnsOrderinfoDAO) SpringContext.getContext()
				.getBean("txnsOrderinfo");
		quickpayCustService = (IQuickpayCustService) SpringContext.getContext()
				.getBean("quickpayCustService");
		smsService = (ISMSService) SpringContext.getContext().getBean(
				"smsService");
	}

	@Override
	public void run() {
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

	@Override
	public ResultBean sendSms(TradeBean trade) {
		String mobile = trade.getMobile();
		String payorderNo = txnsQuickpayService.saveCMBCOuterBankSign(trade);
		SMSThreadPool.getInstance().executeMission(
				new SMSUtil(mobile, "", trade.getTn(), DateUtil
						.getCurrentDateTime(), payorderNo, trade
						.getMiniCardNo(), trade.getAmount_y()));
		return null;
	}

	

	@Override
	public ResultBean submitPay(TradeBean trade) {
		ResultBean resultBean = null;
		try {
			log.info("CMBC submit Pay start!");
			if (log.isDebugEnabled()) {
				try {
					log.debug(JSON.toJSONString(trade));
				} catch (Exception e) {
				}
			}
			resultBean = null;
			int retCode = smsService.verifyCode(trade.getMobile(),
					trade.getTn(), trade.getIdentifyingCode());
			if (retCode == 2) {
				resultBean = new ResultBean("30HK", "交易失败，动态口令或短信验证码校验失败");
			} else if (retCode == 3) {
				resultBean = new ResultBean("30HK", "交易失败，动态口令或短信验证码校验失败");
			}
			// 更新支付方信息
			PayPartyBean payPartyBean = new PayPartyBean(trade.getTxnseqno(),
					"01", "", "93000002",
					ConsUtil.getInstance().cons.getCmbc_merid(), "",
					DateUtil.getCurrentDateTime(), "", trade.getCardNo());
			payPartyBean.setPanName(trade.getAcctName());
			txnsLogService.updatePayInfo_Fast(payPartyBean);
			if (resultBean != null) {
				txnsLogService.updatePayInfo_Fast_result(tradeBean.getTxnseqno(), resultBean.getErrCode(),resultBean.getErrMsg());
				txnsLogService.updateCoreRetResult(tradeBean.getTxnseqno(),resultBean.getErrCode(), resultBean.getErrMsg());
				log.info(JSON.toJSONString(resultBean));
				//TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(tradeBean.getTxnseqno());
                txnsOrderinfoDAO.updateOrderToFail(tradeBean.getTxnseqno());
				return resultBean;
			}
			trade.setPayinstiId(PAYINSTID);

			// 获取持卡人所属省份代码
			trade.setProvno(provinceDAO.getProvinceByXZCode(
					trade.getCertId().substring(0, 2)).getProvinceId()
					+ "");
			// 记录快捷交易流水
			String payorderno = txnsQuickpayService
					.saveCMBCOuterWithholding(trade);
			resultBean = cmbcQuickPayService.crossLineWithhold(trade);
			if (resultBean.isResultBool()) {
				TxnsWithholdingModel withholding = (TxnsWithholdingModel) resultBean
						.getResultObj();
				// 更新快捷交易流水
				txnsQuickpayService.updateCMBCWithholdingResult(withholding,
						payorderno);
			} else {// 交易失败

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

	@Override
	public ResultBean queryTrade(TradeBean trade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultBean bankSign(TradeBean trade) {
		// TODO Auto-generated method stub
		ResultBean resultBean = null;
		try {
			// 卡信息进行实名认证
			PojoRealnameAuth realnameAuth = new PojoRealnameAuth(trade);
			// 保存卡信息认证流水
			String payorderNo = txnsQuickpayService
					.saveCMBCOuterBankCardSign(trade);
			resultBean = cmbcTransferService.realNameAuth(realnameAuth);

			if (resultBean.isResultBool()) {
				txnsQuickpayService.updateCMBCSMSResult(payorderNo, "00",
						"签约成功");
				if (!"01".equals(trade.getTradeType())) {
					// 保存绑卡信息
					quickpayCustService.updateCardStatus(trade.getMerUserId(),
							trade.getCardNo());
				}
			} else {
				txnsQuickpayService.updateCMBCSMSResult(payorderNo,
						resultBean.getErrCode(), resultBean.getErrMsg());
			}
			if ("01".equals(trade.getTradeType())) {
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

		// 民生银行的实名认证和白名单采集已经做完，这里只发送短信验证码发送短信验证码
		log.info("CMBC withholding bank sign start!");
		if (log.isDebugEnabled()) {
			log.debug(JSON.toJSONString(trade));
		}
		trade.setPayinstiId(PAYINSTID);
		sendSms(trade);
		resultBean = new ResultBean("success");
		log.info("CMBC withholding bank sign end!");
		return resultBean;
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

	public static void main(String[] args) {
		System.out.println(DateUtil.formatDateTime(DateUtil.addMin(new Date(),
				10L)));

	}
}
