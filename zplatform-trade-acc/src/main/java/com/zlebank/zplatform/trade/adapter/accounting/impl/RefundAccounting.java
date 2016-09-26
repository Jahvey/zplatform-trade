package com.zlebank.zplatform.trade.adapter.accounting.impl;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.acc.bean.TradeInfo;
import com.zlebank.zplatform.acc.exception.AbstractBusiAcctException;
import com.zlebank.zplatform.acc.exception.AccBussinessException;
import com.zlebank.zplatform.acc.exception.IllegalEntryRequestException;
import com.zlebank.zplatform.acc.service.AccEntryService;
import com.zlebank.zplatform.acc.service.entry.EntryEvent;
import com.zlebank.zplatform.commons.dao.pojo.AccStatusEnum;
import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.trade.adapter.accounting.IAccounting;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.enums.BusinessEnum;
import com.zlebank.zplatform.trade.bean.enums.InsteadPayDetailStatusEnum;
import com.zlebank.zplatform.trade.bean.enums.RefundStatusEnum;
import com.zlebank.zplatform.trade.bean.enums.TradeStatFlagEnum;
import com.zlebank.zplatform.trade.dao.ITxnsOrderinfoDAO;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsOrderinfoModel;
import com.zlebank.zplatform.trade.model.TxnsRefundModel;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.ITxnsRefundService;
import com.zlebank.zplatform.trade.utils.SpringContext;
/**
 * 
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年12月8日 下午4:00:46
 * @since
 */
public class RefundAccounting implements IAccounting{
    private static final Log log = LogFactory.getLog(RefundAccounting.class);
    private AccEntryService accEntryService;
    private ITxnsRefundService txnsRefundService;
    private ITxnsLogService txnsLogService;
    private ITxnsOrderinfoDAO txnsOrderinfoDAO;
    public RefundAccounting(){
        accEntryService = (AccEntryService) SpringContext.getContext().getBean("accEntryServiceImpl");
        txnsRefundService = (ITxnsRefundService) SpringContext.getContext().getBean("txnsRefundService");
        txnsLogService = (ITxnsLogService) SpringContext.getContext().getBean("txnsLogService");
        txnsOrderinfoDAO = (ITxnsOrderinfoDAO) SpringContext.getContext().getBean("txnsOrderinfo");;
    }
    
    

    @Override
    public ResultBean accountedFor(String txnseqno) {
    	TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
		TxnsRefundModel refund = txnsRefundService.getRefundByTxnseqno(txnseqno);
		TxnsOrderinfoModel order = txnsOrderinfoDAO.getOrderByTxnseqno(txnseqno);
		if (refund == null) {
            log.error("没有找到需要记账的流水");
            return null;
        }
		if ("0000".equals(txnsLog.getRetcode())) {
			refund.setStatus(InsteadPayDetailStatusEnum.TRAN_FINISH.getCode());
			order.setStatus("00");
        } else{
            refund.setStatus(RefundStatusEnum.FAILED.getCode());
            order.setStatus("03");
        }
        
        TradeInfo tradeInfo = new TradeInfo();
        tradeInfo.setPayMemberId(txnsLog.getAccmemberid());
        tradeInfo.setPayToMemberId(txnsLog.getAccsecmerno());
        tradeInfo.setAmount(new BigDecimal(txnsLog.getAmount()));
        tradeInfo.setCharge(new BigDecimal(txnsLog.getTxnfee()));
        tradeInfo.setTxnseqno(txnsLog.getTxnseqno());
        tradeInfo.setCoopInstCode(txnsLog.getAccfirmerno());
        tradeInfo.setChannelId(txnsLog.getPayinst());
        tradeInfo.setBusiCode(BusinessEnum.REFUND_BANK.getBusiCode());
        EntryEvent entryEvent = null;
        if ("0000".equals(txnsLog.getRetcode())) {
            tradeInfo.setChannelId(txnsLog.getPayinst());
            entryEvent = EntryEvent.TRADE_SUCCESS;
            log.info("退款交易成功，交易序列号:"+txnseqno);
            txnsLog.setApporderinfo("退款账务成功(交易成功)");
        } else {
        	tradeInfo.setChannelId(txnsLog.getPayinst());
            entryEvent = EntryEvent.TRADE_FAIL;
            log.info("退款交易失败，交易序列号:"+txnseqno);
            txnsLog.setApporderinfo("退款账务成功(交易失败)");
        }
       
        try {
        	log.info("账务处理数据:"+ JSON.toJSONString(tradeInfo));
        	txnsLog.setAppordcommitime(DateUtil.getCurrentDateTime());
        	txnsLog.setAppinst("000000000000");
            accEntryService.accEntryProcess(tradeInfo, entryEvent);
            txnsRefundService.updateRefund(refund);
            txnsOrderinfoDAO.updateOrderinfo(order);
            txnsLog.setApporderstatus(AccStatusEnum.Finish.getCode());
            txnsLog.setAppordfintime(DateUtil.getCurrentDateTime());
        }catch (AccBussinessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			txnsLog.setApporderstatus(AccStatusEnum.AccountingFail.getCode());
            txnsLog.setApporderinfo(e1.getMessage());
		} catch (AbstractBusiAcctException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			txnsLog.setApporderstatus(AccStatusEnum.AccountingFail.getCode());
            txnsLog.setApporderinfo(e1.getMessage());
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			txnsLog.setApporderstatus(AccStatusEnum.AccountingFail.getCode());
            txnsLog.setApporderinfo(e1.getMessage());
		} catch (IllegalEntryRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(txnsLog!=null){
				txnsLog.setApporderstatus(AccStatusEnum.AccountingFail.getCode());
				txnsLog.setApporderinfo(e.getMessage());
			}
		}
        //更新交易流水应用方信息
        txnsLogService.updateAppStatus(txnseqno, txnsLog.getApporderstatus(), txnsLog.getApporderinfo());
        txnsLog.setAccbusicode(BusinessEnum.REFUND_BANK.getBusiCode());
        txnsLog.setAccordfintime(DateUtil.getCurrentDateTime());
        txnsLogService.updateTxnsLog(txnsLog);
        txnsLogService.updateTradeStatFlag(txnseqno, TradeStatFlagEnum.FINISH_ACCOUNTING);
        log.info("退款账务结束，交易序列号:"+txnseqno);
        return null;
    }



	/**
	 *
	 * @param batchno
	 * @return
	 */
	@Override
	public ResultBean accountedForInsteadPay(String batchno) {
		// TODO Auto-generated method stub
		return null;
	}
}