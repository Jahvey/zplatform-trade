/* 
 * UpdateWithdrawServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年3月21日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.acc.bean.TradeInfo;
import com.zlebank.zplatform.acc.exception.AbstractBusiAcctException;
import com.zlebank.zplatform.acc.exception.AccBussinessException;
import com.zlebank.zplatform.acc.exception.IllegalEntryRequestException;
import com.zlebank.zplatform.acc.service.AccEntryService;
import com.zlebank.zplatform.acc.service.entry.EntryEvent;
import com.zlebank.zplatform.commons.dao.pojo.AccStatusEnum;
import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.trade.bean.UpdateData;
import com.zlebank.zplatform.trade.bean.enums.BusinessEnum;
import com.zlebank.zplatform.trade.bean.enums.TransferBusiTypeEnum;
import com.zlebank.zplatform.trade.bean.enums.WithdrawEnum;
import com.zlebank.zplatform.trade.dao.ITxnsOrderinfoDAO;
import com.zlebank.zplatform.trade.dao.ITxnsWithdrawDAO;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsOrderinfoModel;
import com.zlebank.zplatform.trade.model.TxnsWithdrawModel;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.ObserverListService;
import com.zlebank.zplatform.trade.service.UpdateSubject;
import com.zlebank.zplatform.trade.service.UpdateWithdrawService;

/**
 * 提现审核通过，划拨审核拒绝，回退更新。
 *
 * @author houyong
 * @version
 * @date 2016年3月21日 下午4:39:36
 * @since 
 */
@Service("updateWithdrawServiceImpl")
public class UpdateWithdrawServiceImpl implements UpdateWithdrawService,UpdateSubject,ApplicationListener<ContextRefreshedEvent>{

    private static final Log log = LogFactory.getLog(UpdateWithdrawServiceImpl.class);
    @Autowired
    private ITxnsWithdrawDAO withdrawDao;
    @Autowired
    private AccEntryService accEntryService;
    @Autowired
    private ITxnsOrderinfoDAO txnsOrderinfoDAO;
    @Autowired
    private ITxnsLogService txnsLogService;
    /**
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ObserverListService.getInstance().add(this);
    }

    /**
     *提现更新状态并记账
     * @param data
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Throwable.class)
    public void update(UpdateData data) {
    	log.info("提现交易账务处理开始，交易序列号:"+data.getTxnSeqNo());
        TxnsWithdrawModel withdrawModel=withdrawDao.getWithdrawBySeqNo(data.getTxnSeqNo());
        if (withdrawModel==null) {
            log.error("不存在的提现信息,交易序列号："+data.getTxnSeqNo());
            return;
        }
        TxnsOrderinfoModel orderinfo = txnsOrderinfoDAO.getOrderByTxnseqno(data.getTxnSeqNo());
        BusinessEnum businessEnum = null;
        
        EntryEvent entryEvent = null;
        TradeInfo tradeInfo=new TradeInfo();
        if("00".equals(data.getResultCode())){
        	businessEnum = BusinessEnum.WITHDRAWALS;
        	orderinfo.setStatus("00");
        	entryEvent = EntryEvent.TRADE_SUCCESS;
        	withdrawModel.setStatus(WithdrawEnum.SUCCESS.getCode());
        	log.info("提现交易成功，交易序列号:"+data.getTxnSeqNo());
        }else if("01".equals(data.getResultCode())){
        	businessEnum = BusinessEnum.WITHDRAWALS;
        	orderinfo.setStatus("03");
        	entryEvent = EntryEvent.TRADE_FAIL;
        	withdrawModel.setStatus(WithdrawEnum.TRAN_REFUSED.getCode());
        	log.info("提现交易划拨拒绝，交易序列号:"+data.getTxnSeqNo());
        }else if("02".equals(data.getResultCode())){
        	businessEnum = BusinessEnum.WITHDRAWALS;
        	orderinfo.setStatus("03");
        	entryEvent = EntryEvent.TRADE_FAIL;
        	withdrawModel.setStatus(WithdrawEnum.BANK_TRAN_REFUSED.getCode());
        	log.info("提现交易转账拒绝，交易序列号:"+data.getTxnSeqNo());
        }else if("03".equals(data.getResultCode())){
        	businessEnum = BusinessEnum.WITHDRAWALS;
        	orderinfo.setStatus("03");
        	entryEvent = EntryEvent.TRADE_FAIL;
        	withdrawModel.setStatus(WithdrawEnum.BATCHFAILURE.getCode());
        }else if("04".equals(data.getResultCode())){
        	businessEnum = BusinessEnum.WITHDRAWALS;
        	orderinfo.setStatus("03");
        	entryEvent = EntryEvent.REFUND_EXCHANGE;
        	withdrawModel.setStatus(WithdrawEnum.BATCHFAILURE.getCode());
        	tradeInfo.setChannelFee(data.getChannelFee());
        }
        
        withdrawModel.setTxntime(DateUtil.getCurrentDateTime());
        withdrawModel.setFinishtime(DateUtil.getCurrentDateTime());
        withdrawModel.setRetcode(data.getResultCode());
        withdrawModel.setRetinfo(data.getResultMessage());
        withdrawModel.setWithdrawinstid(data.getChannelCode());
        withdrawDao.merge(withdrawModel);
        
        TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(data.getTxnSeqNo());
        //更新订单信息
        txnsOrderinfoDAO.update(orderinfo);
        tradeInfo.setPayMemberId(withdrawModel.getMemberid());
        tradeInfo.setAmount(new BigDecimal(withdrawModel.getAmount()));
        tradeInfo.setCharge(new BigDecimal(withdrawModel.getFee()));
        tradeInfo.setTxnseqno(withdrawModel.getTexnseqno());
        tradeInfo.setBusiCode(businessEnum.getBusiCode());
        tradeInfo.setChannelId(data.getChannelCode());
        tradeInfo.setCoopInstCode(txnsLog.getAccfirmerno());
        try {
        	log.info("提现账务数据："+JSON.toJSONString(tradeInfo));
        	txnsLog.setAppordcommitime(DateUtil.getCurrentDateTime());
        	txnsLog.setAppinst("000000000000");
            accEntryService.accEntryProcess(tradeInfo,entryEvent);
            if ("00".equals(data.getResultCode())) {
            	tradeInfo.setChannelFee(new BigDecimal(0));
            	accEntryService.accEntryProcess(tradeInfo, EntryEvent.RECON_SUCCESS);
            }
            txnsLog.setApporderstatus("00");
            if("04".equals(data.getResultCode())){
            	txnsLog.setApporderinfo("提现账务处理成功（退汇）");
            }else if("03".equals(data.getResultCode())){
            	txnsLog.setApporderinfo("提现账务处理成功（交易失败）");
            }else{
            	txnsLog.setApporderinfo("提现账务处理成功");
            }
            
        } catch (AccBussinessException e1) {
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
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			if(txnsLog!=null){
				txnsLog.setApporderstatus(AccStatusEnum.AccountingFail.getCode());
				txnsLog.setApporderinfo(e.getMessage());
			}
		}
        //更新交易流水应用方信息
        txnsLogService.updateAppStatus(data.getTxnSeqNo(), txnsLog.getApporderstatus(), txnsLog.getApporderinfo());
        txnsLog.setAppordfintime(DateUtil.getCurrentDateTime());
        txnsLog.setAccordfintime(DateUtil.getCurrentDateTime());
        txnsLog.setAccbusicode(BusinessEnum.WITHDRAWALS.getBusiCode());
        txnsLogService.update(txnsLog);
        log.info("提现交易账务处理结束，交易序列号:"+data.getTxnSeqNo());
    }

    /**
     *
     * @return
     */
    @Override
    public String getBusiCode() {
       return TransferBusiTypeEnum.WITHDRAW.getCode();
    }

}
