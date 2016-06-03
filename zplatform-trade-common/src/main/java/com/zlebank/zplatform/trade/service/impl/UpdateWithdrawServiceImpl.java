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
@Service(value="UpdateWithdrawServiceImpl")
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
        WithdrawEnum wdEnum=WithdrawEnum.fromValue(data.getResultCode());
        BusinessEnum businessEnum = null;
        
        if (wdEnum.getCode().equals(data.getResultCode())) {
            withdrawModel.setStatus(wdEnum.getCode());
        }else{
            withdrawModel.setStatus(WithdrawEnum.STOP.getCode());
        }
        withdrawModel.setTxntime(DateUtil.getCurrentDateTime());
        withdrawModel.setFinishtime(DateUtil.getCurrentDateTime());
        withdrawModel.setRetcode(data.getResultCode());
        withdrawModel.setRetinfo(data.getResultMessage());
        withdrawModel.setWithdrawinstid(data.getChannelCode());
        withdrawDao.merge(withdrawModel);
        EntryEvent entryEvent = null;
        if("00".equals(data.getResultCode())){
        	businessEnum = BusinessEnum.WITHDRAWALS;
        	orderinfo.setStatus("00");
        	entryEvent = EntryEvent.TRADE_SUCCESS;
        	log.info("提现交易成功，交易序列号:"+data.getTxnSeqNo());
        }else{
        	businessEnum = BusinessEnum.WITHDRAWALS;
        	orderinfo.setStatus("03");
        	entryEvent = EntryEvent.TRADE_FAIL;
        	log.info("提现交易失败，交易序列号:"+data.getTxnSeqNo());
        }
        TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(data.getTxnSeqNo());
        //更新订单信息
        txnsOrderinfoDAO.update(orderinfo);
        TradeInfo tradeInfo=new TradeInfo();
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
            txnsLog.setApporderstatus("00");
            txnsLog.setApporderinfo("提现账务处理成功");
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
		}
        //更新交易流水应用方信息
        txnsLogService.updateAppStatus(data.getTxnSeqNo(), txnsLog.getApporderstatus(), txnsLog.getApporderinfo());
        txnsLog.setAppordfintime(DateUtil.getCurrentDateTime());
        txnsLog.setAccordfintime(DateUtil.getCurrentDateTime());
        txnsLog.setAccbusicode(BusinessEnum.WITHDRAWALS.getBusiCode());
        txnsLogService.update(txnsLog);
        log.info("提现交易账务处理开始，交易序列号:"+data.getTxnSeqNo());
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
