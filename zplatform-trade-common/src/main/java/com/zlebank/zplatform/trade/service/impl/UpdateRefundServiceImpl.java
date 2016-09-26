/* 
haod	支付			
姓名	郭佳			
日期	2016年5月23日			
				
当日工作计划				
序号	时长(时)	工作描述	优先级	备注
1	7	集成畅捷支付代付代码	高	
				
当日工作总结				
序号	时长(时)	工作描述	优先级	备注
1	7.5	开发退款功能，测试退款，修改生成退款订单的方法，修改测试中出现的bug	中	
				
				
问题情况				
序号	问题描述		优先级	备注
				
次日工作计划				
序号	时长(时)	工作描述	优先级	备注
1	8	集成畅捷支付代付代码编写各个接口的单元测试程序	高	
 * UpdateInsteadServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年3月16日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import java.math.BigDecimal;
import java.util.List;

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
import com.zlebank.zplatform.trade.bean.enums.InsteadPayDetailStatusEnum;
import com.zlebank.zplatform.trade.bean.enums.RefundStatusEnum;
import com.zlebank.zplatform.trade.bean.enums.TransferBusiTypeEnum;
import com.zlebank.zplatform.trade.dao.ITxnsOrderinfoDAO;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsOrderinfoModel;
import com.zlebank.zplatform.trade.model.TxnsRefundModel;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.ITxnsRefundService;
import com.zlebank.zplatform.trade.service.ObserverListService;
import com.zlebank.zplatform.trade.service.UpdateRefundService;
import com.zlebank.zplatform.trade.service.UpdateSubject;

/**
 * Class Description
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年3月16日 下午3:59:29
 * @since 
 */
@Service
public class UpdateRefundServiceImpl implements UpdateRefundService, UpdateSubject ,ApplicationListener<ContextRefreshedEvent> {
    
    private static final Log log = LogFactory.getLog(UpdateRefundServiceImpl.class);
   
    
    @Autowired
    private AccEntryService accEntryService;
    
    @Autowired
    private ITxnsLogService txnsLogService;
    
    @Autowired
    private ITxnsRefundService txnsRefundService;
    
    @Autowired
    private ITxnsOrderinfoDAO txnsOrderinfoDAO;
    /**
     *  更新状态和记账
     * @param data
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Throwable.class)
    public void update(UpdateData data) {
    	log.info("退款账务处理开始，交易序列号:"+data.getTxnSeqNo());
        List<UpdateSubject> observerList = ObserverListService.getInstance().getObserverList();
        for (UpdateSubject subject : observerList) {
            log.info(subject.getBusiCode());
        }
        TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(data.getTxnSeqNo());
		TxnsRefundModel refund = txnsRefundService.getRefundByTxnseqno(data.getTxnSeqNo());
		TxnsOrderinfoModel order = txnsOrderinfoDAO.getOrderByTxnseqno(data.getTxnSeqNo());
		if (refund == null) {
            log.error("没有找到需要记账的流水");
            return;
        }
		if ("00".equals(data.getResultCode())) {
			refund.setStatus(InsteadPayDetailStatusEnum.TRAN_FINISH.getCode());
			order.setStatus("00");
        } else if("01".equals(data.getResultCode())) {//划拨拒绝
            refund.setStatus(RefundStatusEnum.TRAN_REFUSE.getCode());
            order.setStatus("03");
        }else if("02".equals(data.getResultCode())){//
        	refund.setStatus(RefundStatusEnum.BANK_TRAN_REFUSE.getCode());
            order.setStatus("03");
        }else if("03".equals(data.getResultCode())){
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
        tradeInfo.setBusiCode(BusinessEnum.REFUND_BANK.getBusiCode());
        EntryEvent entryEvent = null;
        if ("00".equals(data.getResultCode())) {
            tradeInfo.setChannelId(txnsLog.getPayinst());
            entryEvent = EntryEvent.TRADE_SUCCESS;
            log.info("退款交易成功，交易序列号:"+data.getTxnSeqNo());
            txnsLog.setApporderinfo("退款账务成功(交易成功)");
        } else if("04".equals(data.getResultCode())){
        	tradeInfo.setChannelId(txnsLog.getPayinst());
        	tradeInfo.setChannelFee(data.getChannelFee());
            entryEvent = EntryEvent.REFUND_EXCHANGE;
            log.info("退款交易成功，交易序列号:"+data.getTxnSeqNo());
            txnsLog.setApporderinfo("退款账务成功(代付退汇)");
        }else {
            entryEvent = EntryEvent.TRADE_FAIL;
            log.info("退款交易失败，交易序列号:"+data.getTxnSeqNo());
            txnsLog.setApporderinfo("退款账务成功(交易失败)");
        }
       
        try {
        	log.info("账务处理数据:"+ JSON.toJSONString(tradeInfo));
        	txnsLog.setAppordcommitime(DateUtil.getCurrentDateTime());
        	txnsLog.setAppinst("000000000000");
        	
            accEntryService.accEntryProcess(tradeInfo, entryEvent);
            if ("00".equals(data.getResultCode())) {
            	tradeInfo.setChannelFee(new BigDecimal(0));
            	accEntryService.accEntryProcess(tradeInfo, EntryEvent.RECON_SUCCESS);
            }
            txnsRefundService.update(refund);
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
        txnsLog.setAccbusicode(BusinessEnum.REFUND_BANK.getBusiCode());
        txnsLog.setAccordfintime(DateUtil.getCurrentDateTime());
        txnsLogService.update(txnsLog);
        log.info("退款账务结束，交易序列号:"+data.getTxnSeqNo());
    }


    /**
     * 得到业务代码
     */
    @Override
    public String getBusiCode() {
        return TransferBusiTypeEnum.REFUND.getCode();
    }


    /**
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
    	if (event.getApplicationContext().getParent() == null) {
            ObserverListService.getInstance().add(this);
        }
    }
}
