package com.zlebank.zplatform.trade.zlpay.quickpay;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.commons.dao.ProvinceDAO;
import com.zlebank.zplatform.commons.dao.pojo.BusiTypeEnum;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.member.service.MemberBankCardService;
import com.zlebank.zplatform.sms.service.ISMSService;
import com.zlebank.zplatform.trade.adapter.accounting.IAccounting;
import com.zlebank.zplatform.trade.adapter.quickpay.IQuickPayTrade;
import com.zlebank.zplatform.trade.bean.AppPartyBean;
import com.zlebank.zplatform.trade.bean.PayPartyBean;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.ZLPayResultBean;
import com.zlebank.zplatform.trade.bean.enums.ChnlTypeEnum;
import com.zlebank.zplatform.trade.bean.enums.TradeTypeEnum;
import com.zlebank.zplatform.trade.bean.zlpay.MarginRegisterBean;
import com.zlebank.zplatform.trade.bean.zlpay.MarginSmsBean;
import com.zlebank.zplatform.trade.bean.zlpay.OnlineDepositShortBean;
import com.zlebank.zplatform.trade.dao.ITxnsOrderinfoDAO;
import com.zlebank.zplatform.trade.dao.RspmsgDAO;
import com.zlebank.zplatform.trade.factory.AccountingAdapterFactory;
import com.zlebank.zplatform.trade.model.PojoCardBind;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsQuickpayModel;
import com.zlebank.zplatform.trade.service.CardBindService;
import com.zlebank.zplatform.trade.service.IQuickpayCustService;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.ITxnsQuickpayService;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.DateUtil;
import com.zlebank.zplatform.trade.utils.OrderNumber;
import com.zlebank.zplatform.trade.utils.SMSThreadPool;
import com.zlebank.zplatform.trade.utils.SMSUtil;
import com.zlebank.zplatform.trade.utils.SpringContext;
import com.zlebank.zplatform.trade.utils.UUIDUtil;
import com.zlebank.zplatform.trade.zlpay.analyzer.ZlPayTradeAnalyzer;
import com.zlebank.zplatform.trade.zlpay.service.IZlTradeService;
/**
 * 证联支付快捷支付
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年12月11日 上午11:46:07
 * @since
 */
public class ZLPayQuickPayTradeThread implements IQuickPayTrade{
    private static final Log log = LogFactory.getLog(ZLPayQuickPayTradeThread.class);
    private static final String PAYINSTID = "98000001";
    private TradeBean tradeBean;
    private TradeTypeEnum tradeType;
    private ITxnsQuickpayService txnsQuickpayService;
    private ITxnsLogService txnsLogService;
    private ITxnsOrderinfoDAO txnsOrderinfoDAO;
    private IZlTradeService zlTradeService;
    private CardBindService cardBindService;
    private ISMSService smsService;
    private RspmsgDAO rspmsgDAO;
    
    public ZLPayQuickPayTradeThread() {
         txnsQuickpayService = (ITxnsQuickpayService) SpringContext.getContext().getBean("txnsQuickpayService");
         txnsLogService = (ITxnsLogService) SpringContext.getContext().getBean("txnsLogService");;
         txnsOrderinfoDAO = (ITxnsOrderinfoDAO) SpringContext.getContext().getBean("txnsOrderinfo");
         zlTradeService  = (IZlTradeService) SpringContext.getContext().getBean("zlTradeService");
         cardBindService = (CardBindService) SpringContext.getContext().getBean("cardBindService");
         smsService = (ISMSService) SpringContext.getContext().getBean(
 				"smsService");
         rspmsgDAO = (RspmsgDAO) SpringContext.getContext().getBean("rspmsgDAO");
    }
    
    @Override
    public void run() {
        if(tradeBean==null){
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

    public ResultBean sendSms(TradeBean trade) {
    	log.info("start zlpay sendSms,txnseqno:"+trade.getTxnseqno());
    	trade.setOrderId(OrderNumber.getInstance().generateZLOrderId());
        MarginSmsBean marginSmsBean =ZlPayTradeAnalyzer.generateSendMargin(trade);
        //记录快捷交易验证码流水
        txnsQuickpayService.saveMobileCode(trade,marginSmsBean);
        //获取证联支付短信验证码
        ResultBean resultBean = zlTradeService.sendMarginSms(marginSmsBean);
        if(!resultBean.isResultBool()){
        	resultBean.setResultObj(marginSmsBean.getMerchantSeqId());
        }
        //更新快捷交易验证码流水
        txnsQuickpayService.updateMobileCode(resultBean);
        return resultBean;
    }

   

    @Override
    public ResultBean submitPay(TradeBean trade) {
    	ResultBean resultBean = null;
    	if(StringUtil.isNotEmpty(trade.getCardId()+"")){
    		PojoCardBind cardBind = cardBindService.getCardBind(trade.getCardId(), PAYINSTID);
    		if(cardBind==null){
    			log.info("start zlpay MarginRegister,txnseqno:"+trade.getTxnseqno());
        		MarginRegisterBean marginRegisterBean = ZlPayTradeAnalyzer.generateMarginRegister(trade);
                //支付机构号暂时未定义
                PayPartyBean payPartyBean = new PayPartyBean(trade.getTxnseqno(), "01", marginRegisterBean.getFundSeqId(), PAYINSTID, trade.getMerchId(), "",DateUtil.getCurrentDateTime(), "",trade.getCardNo());
                txnsLogService.updatePayInfo_Fast(payPartyBean);
                //记录同步开户快捷交易流水
                txnsQuickpayService.saveMarginRegister(trade, marginRegisterBean);
                resultBean = zlTradeService.marginRegisterReq(marginRegisterBean);
                //更新同步快捷交易流水,并记录开户的用户id
                txnsQuickpayService.updateMarginRegister(resultBean);
                if(resultBean.isResultBool()){
                	ZLPayResultBean zlPayResultBean = (ZLPayResultBean)resultBean.getResultObj();
                	cardBind = new PojoCardBind();
                	cardBind.setCardId(trade.getCardId());
                	cardBind.setChnlCode(PAYINSTID);
                	cardBind.setBindId(zlPayResultBean.getUserId());
                	cardBindService.save(cardBind);
                    trade.setUserId(zlPayResultBean.getUserId());
                }
                log.info("end zlpay MarginRegister");
                log.info("start zlpay submitpay,txnseqno:"+trade.getTxnseqno());
                PayPartyBean payPartyBean1 = new PayPartyBean(trade.getTxnseqno(),
    					"01", OrderNumber.getInstance().generateZLOrderId(), PAYINSTID,
    					ConsUtil.getInstance().cons.getInstuId(), "",
    					DateUtil.getCurrentDateTime(), "", trade.getCardNo());
    			payPartyBean1.setPanName(trade.getAcctName());
    			txnsLogService.updatePayInfo_Fast(payPartyBean1);
                
    	    	//TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(trade.getTxnseqno());
    	    	trade.setOrderId(payPartyBean1.getPayordno());
    	        OnlineDepositShortBean onlineDepositShortBean = ZlPayTradeAnalyzer
    	                .generateOnlineDepositShort(trade);
    	        //记录快捷交易入金流水
    	        txnsQuickpayService.saveOnlineDepositShort(trade, onlineDepositShortBean);
    	        resultBean = zlTradeService.onlineDepositShort(onlineDepositShortBean);
    	        //更新快捷交易入金流水
    	        txnsQuickpayService.updateOnlineDepositShort(resultBean);
    	        updateTradeResult(resultBean,trade.getTxnseqno());
    	        log.info("ZLPAY submit Pay end!");
                
    		}else{
        		log.info("start zlpay sumbit Pay!");
    			if (log.isDebugEnabled()) {
    				try {
    					log.debug(JSON.toJSONString(trade));
    				} catch (Exception e) {
    				}
    			}
    			
    			int retCode = smsService.verifyCode(trade.getMobile(),
    					trade.getTn(), trade.getIdentifyingCode());
    			if (retCode == 2) {
    				resultBean = new ResultBean("30HK", "交易失败，动态口令或短信验证码校验失败");
    			} else if (retCode == 3) {
    				resultBean = new ResultBean("30HK", "交易失败，动态口令或短信验证码校验失败");
    			}
    			PayPartyBean payPartyBean = new PayPartyBean(trade.getTxnseqno(),
    					"01", OrderNumber.getInstance().generateZLOrderId(), PAYINSTID,
    					ConsUtil.getInstance().cons.getInstuId(), "",
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
    			
    			
    			trade.setUserId(cardBind.getBindId());
    			log.info("start zlpay submitpay,txnseqno:"+trade.getTxnseqno());
    	    	//TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(trade.getTxnseqno());
    	    	trade.setOrderId(payPartyBean.getPayordno());
    	        OnlineDepositShortBean onlineDepositShortBean = ZlPayTradeAnalyzer
    	                .generateOnlineDepositShort(trade);
    	        //记录快捷交易入金流水
    	        txnsQuickpayService.saveOnlineDepositShort(trade, onlineDepositShortBean);
    	        resultBean = zlTradeService.onlineDepositShort(onlineDepositShortBean);
    	        //更新快捷交易入金流水
    	        txnsQuickpayService.updateOnlineDepositShort(resultBean);
    	        
    	        updateTradeResult(resultBean,trade.getTxnseqno());
    	        log.info("ZLPAY submit Pay end!");
    		}
    	}
        return resultBean;
    }

    @Override
    public ResultBean queryTrade(TradeBean trade) {
        return null;
    }

    @Override
    public ResultBean bankSign(TradeBean trade) {
    	
        /*// TODO Auto-generated method stub
        MarginRegisterBean marginRegisterBean = ZlPayTradeAnalyzer.generateMarginRegister(trade);
        //支付机构号暂时未定义
        PayPartyBean payPartyBean = new PayPartyBean(trade.getTxnseqno(), "01", marginRegisterBean.getFundSeqId(), PAYINSTID, trade.getMerchId(), "",DateUtil.getCurrentDateTime(), "",trade.getCardNo());
        txnsLogService.updatePayInfo_Fast(payPartyBean);
        //记录同步开户快捷交易流水
        txnsQuickpayService.saveMarginRegister(trade, marginRegisterBean);
        ResultBean resultBean = zlTradeService.marginRegisterReq(marginRegisterBean);
        //更新同步快捷交易流水,并记录开户的用户id
        txnsQuickpayService.updateMarginRegister(resultBean);
        //更新交易流水表和交易订单表
*/        
    	
    	if(trade.getCardId()!=0L){//用户已经绑卡
    		log.info("证联支付已绑卡签约");
    		PojoCardBind cardBind = cardBindService.getCardBind(trade.getCardId(), PAYINSTID);
    		if(cardBind==null){
    			log.info("证联支付渠道未绑卡，发送证联支付的签约短信");
    			return sendSms(trade);
    		}
    		log.info("已绑定证联支付银行卡，由证联金融发送短信验证码");
    		String mobile = trade.getMobile();
    		String payorderNo = txnsQuickpayService.saveCMBCOuterBankSign(trade);
    		SMSThreadPool.getInstance().executeMission(
    				new SMSUtil(mobile, "", trade.getTn(), DateUtil
    						.getCurrentDateTime(), payorderNo, trade
    						.getMiniCardNo(), trade.getAmount_y()));
    		log.info("证联金融签约短信验证码发送完成");
    		return new ResultBean("success");
    		
    	}else{//用户已绑卡
    		
    		return sendSms(trade);
    	}
    }
    
    public void updateTradeResult(ResultBean resultBean,String txnseqno){
    	TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
    	ZLPayResultBean zlPayResultBean = (ZLPayResultBean)resultBean.getResultObj();
    	String retcode = zlPayResultBean.getRespCode();
    	String retinfo = zlPayResultBean.getRespDesc();
    	String payrettsnseqno = null;
        if(resultBean.isResultBool()){//交易成功
        	payrettsnseqno =zlPayResultBean.getPnrSeqId();//支付方流水号
        	txnsOrderinfoDAO.updateOrderToSuccess(txnseqno);
        	txnsLog.setRetcode("0000");
        	txnsLog.setRetinfo("交易成功");
        }else{//交易失败
        	txnsOrderinfoDAO.updateOrderToFail(txnseqno);
        	txnsLog.setRetcode("3099");
        	txnsLog.setRetinfo("交易失败");
        }
        
        //
        txnsLog.setTradeseltxn(UUIDUtil.uuid());
        txnsLog.setRelate("10000000");
        txnsLog.setTradestatflag("00000001");
        txnsLog.setRetdatetime(DateUtil.getCurrentDateTime());
        txnsLog.setTradetxnflag("10000000");
        txnsLog.setAccordfintime(DateUtil.getCurrentDateTime());
        txnsLogService.updateTxnsLog(txnsLog);
      //更新交易支付方信息
        txnsLogService.updatePayInfo_Fast_result(txnseqno, payrettsnseqno, retcode, retinfo);
        String commiteTime = DateUtil.getCurrentDateTime();
        //账务处理
        AccountingAdapterFactory.getInstance().getAccounting(BusiTypeEnum.fromValue(txnsLog.getBusitype())).accountedFor(txnseqno);
        AppPartyBean appParty = new AppPartyBean("","000000000000", commiteTime,DateUtil.getCurrentDateTime(), txnseqno, "");
        txnsLogService.updateAppInfo(appParty);
        
        
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
