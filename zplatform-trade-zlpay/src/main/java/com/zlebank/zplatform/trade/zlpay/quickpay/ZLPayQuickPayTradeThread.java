package com.zlebank.zplatform.trade.zlpay.quickpay;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zlebank.zplatform.commons.dao.ProvinceDAO;
import com.zlebank.zplatform.commons.dao.pojo.BusiTypeEnum;
import com.zlebank.zplatform.trade.adapter.accounting.IAccounting;
import com.zlebank.zplatform.trade.adapter.quickpay.IQuickPayTrade;
import com.zlebank.zplatform.trade.bean.PayPartyBean;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.ZLPayResultBean;
import com.zlebank.zplatform.trade.bean.enums.TradeTypeEnum;
import com.zlebank.zplatform.trade.bean.zlpay.MarginRegisterBean;
import com.zlebank.zplatform.trade.bean.zlpay.MarginSmsBean;
import com.zlebank.zplatform.trade.bean.zlpay.OnlineDepositShortBean;
import com.zlebank.zplatform.trade.dao.ITxnsOrderinfoDAO;
import com.zlebank.zplatform.trade.factory.AccountingAdapterFactory;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsQuickpayModel;
import com.zlebank.zplatform.trade.service.IQuickpayCustService;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.ITxnsQuickpayService;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.DateUtil;
import com.zlebank.zplatform.trade.utils.OrderNumber;
import com.zlebank.zplatform.trade.utils.SpringContext;
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
    private ProvinceDAO provinceDAO;
    private ITxnsLogService txnsLogService;
    private ITxnsOrderinfoDAO txnsOrderinfoDAO;
    private IQuickpayCustService quickpayCustService;
    private IZlTradeService zlTradeService;
    
    public ZLPayQuickPayTradeThread() {
         txnsQuickpayService = (ITxnsQuickpayService) SpringContext.getContext().getBean("txnsQuickpayService");
         provinceDAO = (ProvinceDAO) SpringContext.getContext().getBean("provinceDAO");
         txnsLogService = (ITxnsLogService) SpringContext.getContext().getBean("txnsLogService");;
         txnsOrderinfoDAO = (ITxnsOrderinfoDAO) SpringContext.getContext().getBean("txnsOrderinfo");
         quickpayCustService = (IQuickpayCustService) SpringContext.getContext().getBean("quickpayCustService");
         zlTradeService  = (IZlTradeService) SpringContext.getContext().getBean("zlTradeService");
    }
    
    @Override
    public void run() {
        if(tradeBean==null){
            return;
        }
        switch (tradeType) {
			case ACCOUNTING:
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
    	trade.setOrderId(OrderNumber.getInstance().generateZLOrderId());
        MarginSmsBean marginSmsBean =ZlPayTradeAnalyzer.generateSendMargin(trade);
        //记录快捷交易验证码流水
        txnsQuickpayService.saveMobileCode(trade,marginSmsBean);
        //获取证联支付短信验证码
        ResultBean resultBean = zlTradeService.sendMarginSms(marginSmsBean);
        //更新快捷交易验证码流水
        txnsQuickpayService.updateMobileCode(resultBean);
        return resultBean;
    }

   

    @Override
    public ResultBean submitPay(TradeBean trade) {
    	log.info("ZLPAY submit Pay start!");
    	TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(trade.getTxnseqno());
    	trade.setOrderId(txnsLog.getPayordno());
        OnlineDepositShortBean onlineDepositShortBean = ZlPayTradeAnalyzer
                .generateOnlineDepositShort(trade);
        //记录快捷交易入金流水
        txnsQuickpayService.saveOnlineDepositShort(trade, onlineDepositShortBean);
        ResultBean resultBean = zlTradeService.onlineDepositShort(onlineDepositShortBean);
        //更新快捷交易入金流水
        txnsQuickpayService.updateOnlineDepositShort(resultBean);
        
        
        
        log.info("ZLPAY submit Pay end!");
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
        
        return sendSms(trade);
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
        }else{//交易失败
        	txnsOrderinfoDAO.updateOrderToFail(txnseqno);
        } 
        //更新交易支付方信息
        txnsLogService.updatePayInfo_Fast_result(txnseqno, payrettsnseqno, retcode, retinfo);
        //账务处理
        AccountingAdapterFactory.getInstance().getAccounting(BusiTypeEnum.fromValue(txnsLog.getBusitype())).accountedFor(txnseqno);
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
