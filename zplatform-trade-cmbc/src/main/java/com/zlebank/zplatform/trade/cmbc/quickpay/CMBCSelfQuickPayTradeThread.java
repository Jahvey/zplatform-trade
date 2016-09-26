
package com.zlebank.zplatform.trade.cmbc.quickpay;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.sms.service.ISMSService;
import com.zlebank.zplatform.trade.adapter.quickpay.IQuickPayTrade;
import com.zlebank.zplatform.trade.bean.PayPartyBean;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.enums.ChannelEnmu;
import com.zlebank.zplatform.trade.bean.enums.TradeTypeEnum;
import com.zlebank.zplatform.trade.cmbc.exception.CMBCTradeException;
import com.zlebank.zplatform.trade.cmbc.service.ICMBCQuickPayService;
import com.zlebank.zplatform.trade.cmbc.service.ICMBCTransferService;
import com.zlebank.zplatform.trade.dao.ITxnsOrderinfoDAO;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.model.PojoRealnameAuth;
import com.zlebank.zplatform.trade.model.TxnsWithholdingModel;
import com.zlebank.zplatform.trade.service.IQuickpayCustService;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.ITxnsQuickpayService;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.SMSThreadPool;
import com.zlebank.zplatform.trade.utils.SMSUtil;
import com.zlebank.zplatform.trade.utils.SpringContext;
/**
 * 民生银行代扣快捷支付
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年12月11日 上午11:46:07
 * @since
 */
public class CMBCSelfQuickPayTradeThread implements IQuickPayTrade{
    private static final Log log = LogFactory.getLog(CMBCSelfQuickPayTradeThread.class);
    private static final String PAYINSTID = "93000002";
    private TradeBean tradeBean;
    private TradeTypeEnum tradeType;
    private ITxnsQuickpayService txnsQuickpayService;
    private ISMSService smsService;
    private ICMBCQuickPayService cmbcQuickPayService;
    private ITxnsLogService txnsLogService;
    private ITxnsOrderinfoDAO txnsOrderinfoDAO;
    private ICMBCTransferService cmbcTransferService;
    private IQuickpayCustService quickpayCustService;
   
    
    public CMBCSelfQuickPayTradeThread() {
         txnsQuickpayService = (ITxnsQuickpayService) SpringContext.getContext().getBean("txnsQuickpayService");
         smsService = (ISMSService) SpringContext.getContext().getBean("smsService");
         txnsLogService = (ITxnsLogService) SpringContext.getContext().getBean("txnsLogService");
         cmbcQuickPayService = (ICMBCQuickPayService) SpringContext.getContext().getBean("cmbcQuickPayService");
         txnsOrderinfoDAO = (ITxnsOrderinfoDAO) SpringContext.getContext().getBean("txnsOrderinfo");
         cmbcTransferService = (ICMBCTransferService) SpringContext.getContext().getBean("cmbcTransferService");
         quickpayCustService = (IQuickpayCustService) SpringContext.getContext().getBean("quickpayCustService");
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

    @Override
    public ResultBean sendSms(TradeBean trade) {
        ResultBean resultBean = null;
        try {
        	log.info("cmbc self send sms start!!!!");
            String mobile = trade.getMobile();
            String payorderNo = txnsQuickpayService.saveCMBCOuterBankSign(trade);
            if(ConsUtil.getInstance().cons.getIs_junit()==1){
            	new SMSUtil(mobile,"",trade.getTn(),DateUtil.getCurrentDateTime(),payorderNo,trade.getMiniCardNo(),trade.getAmount_y()).sendSMS(mobile, "");
            }else{
            	SMSThreadPool.getInstance().executeMission(new SMSUtil(mobile,"",trade.getTn(),DateUtil.getCurrentDateTime(),payorderNo,trade.getMiniCardNo(),trade.getAmount_y()));
            }
            resultBean = new ResultBean("success");
            log.info("cmbc self send sms end!!!!");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            resultBean = new ResultBean("0051", "发送短信验证码失败");
        }
        return resultBean;
    }


    @Override
    public ResultBean submitPay(TradeBean trade) {
        ResultBean resultBean = null;
        try {
            log.info("CMBC Self submit Pay start!");
            if(log.isDebugEnabled()){
                try {
                    log.debug(JSON.toJSONString(trade));
                } catch (Exception e) {
                }
            }
            resultBean = null;
            int retCode = smsService.verifyCode(trade.getMobile(), trade.getTn(), trade.getIdentifyingCode());
            if(retCode==2){
                resultBean = new ResultBean("30HK", "交易失败，动态口令或短信验证码校验失败");
            }else if(retCode==3){
                resultBean = new ResultBean("30HK", "交易失败，动态口令或短信验证码校验失败");
            }
            //更新支付方信息
            PayPartyBean payPartyBean = new PayPartyBean(trade.getTxnseqno(),"01", "", ChannelEnmu.CMBCSELFWITHHOLDING.getChnlcode(), ConsUtil.getInstance().cons.getCmbc_merid(), "", DateUtil.getCurrentDateTime(), "",trade.getCardNo());
            payPartyBean.setPanName(trade.getAcctName());
            txnsLogService.updatePayInfo_Fast(payPartyBean);
            if(resultBean!=null){
            	//更新支付方结果 --短信验证码错误
                txnsLogService.updatePayInfo_Fast_result(tradeBean.getTxnseqno(), resultBean.getErrCode(),resultBean.getErrMsg());
                txnsLogService.updateCoreRetResult(tradeBean.getTxnseqno(), resultBean.getErrCode(),resultBean.getErrMsg());
                log.info("短信验证码验证失败:"+JSON.toJSONString(resultBean));
                //TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(tradeBean.getTxnseqno());
                txnsOrderinfoDAO.updateOrderToFail(tradeBean.getTxnseqno());
                return resultBean;
            }
            trade.setPayinstiId(PAYINSTID);
            //记录快捷交易流水
            String payorderno = txnsQuickpayService.saveCMBCOuterWithholding(trade);
            resultBean = cmbcQuickPayService.innerLineWithhold(trade);
            if(resultBean.isResultBool()){
               TxnsWithholdingModel withholding = (TxnsWithholdingModel) resultBean.getResultObj();
               //更新快捷交易流水
               txnsQuickpayService.updateCMBCWithholdingResult(withholding, payorderno);
            }
            
            log.info("CMBC Self submit Pay end!");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            txnsOrderinfoDAO.updateOrderToFail(tradeBean.getTxnseqno());
            resultBean = new ResultBean("T000", "交易失败");
        }
        
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
            //卡信息进行实名认证
            PojoRealnameAuth realnameAuth = new PojoRealnameAuth(trade);
            //保存卡信息认证流水
            String payorderNo = txnsQuickpayService.saveCMBCOuterBankCardSign(trade);
            resultBean = cmbcTransferService.realNameAuth(realnameAuth);
            if(resultBean.isResultBool()){
                txnsQuickpayService.updateCMBCSMSResult(payorderNo, "00", "签约成功");
                if(!"01".equals(trade.getTradeType())){
                	//保存绑卡信息
                    quickpayCustService.updateCardStatus(trade.getMerUserId(), trade.getCardNo());
                }
            }else{
                txnsQuickpayService.updateCMBCSMSResult(payorderNo, resultBean.getErrCode(), resultBean.getErrMsg());
            }
            if("01".equals(trade.getTradeType())){
            	return resultBean;
            }
        } catch (CMBCTradeException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (TradeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        log.info("CMBC Self withholding bank sign start!");
        if(log.isDebugEnabled()){
                log.debug(JSON.toJSONString(trade));
        }
        trade.setPayinstiId(PAYINSTID);
        resultBean = sendSms(trade);
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

}
