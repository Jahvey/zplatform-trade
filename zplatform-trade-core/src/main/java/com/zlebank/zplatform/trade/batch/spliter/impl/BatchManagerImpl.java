/* 
 * BatchManagerImpl.java  
 * 
 * version TODO
 *
 * 2016年3月7日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.batch.spliter.impl;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.commons.bean.CardBin;
import com.zlebank.zplatform.commons.dao.CardBinDao;
import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.trade.batch.spliter.BatchManager;
import com.zlebank.zplatform.trade.batch.spliter.TransferDataResult;
import com.zlebank.zplatform.trade.bean.enums.BankTransferBatchOpenStatusEnum;
import com.zlebank.zplatform.trade.bean.enums.BankTransferBatchStatusEnum;
import com.zlebank.zplatform.trade.bean.enums.BankTransferBatchTranStatusEnum;
import com.zlebank.zplatform.trade.bean.enums.BankTransferDataStatusEnum;
import com.zlebank.zplatform.trade.bean.enums.SeqNoEnum;
import com.zlebank.zplatform.trade.dao.BankTransferBatchDAO;
import com.zlebank.zplatform.trade.dao.BankTransferChannelDAO;
import com.zlebank.zplatform.trade.dao.BankTransferDataDAO;
import com.zlebank.zplatform.trade.model.PojoBankTransferBatch;
import com.zlebank.zplatform.trade.model.PojoBankTransferChannel;
import com.zlebank.zplatform.trade.model.PojoBankTransferData;
import com.zlebank.zplatform.trade.service.SeqNoService;


/**
 * 批次管理器
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年3月7日 上午11:08:21
 * @since 
 */
@Service
public class BatchManagerImpl implements BatchManager {
    
    private static final Log log = LogFactory.getLog(BatchManagerImpl.class);
    
    @Autowired
    BankTransferBatchDAO bankTransferBatchDAO;
    @Autowired
    BankTransferDataDAO bankTransferDetaDAO;
    @Autowired
    BankTransferChannelDAO bankTransferChannelDAO;
    @Autowired
    SeqNoService seqNoService;
    @Autowired
    CardBinDao cardBinDao ;

    /**
     * 创建批次
     * @param channel
     * @return 
     */ 
    @Override
    public PojoBankTransferBatch createBatch(String channel) {
        PojoBankTransferChannel channelPojo = bankTransferChannelDAO.getByChannelCode(channel);
        PojoBankTransferBatch newBatch = new PojoBankTransferBatch();
        newBatch.setBankTranBatchNo(seqNoService.getBatchNo(SeqNoEnum.BANK_TRAN_BATCH_NO));
        newBatch.setChannel(channelPojo);
        newBatch.setTotalCount(0L);
        newBatch.setTotalAmt(0L);
        newBatch.setSuccessCount(0L);
        newBatch.setSuccessAmt(0L);
        newBatch.setFailCount(0L);
        newBatch.setFailAmt(0L);
        newBatch.setStatus(BankTransferBatchStatusEnum.INIT.getCode());
        newBatch.setTranStatus(BankTransferBatchTranStatusEnum.WAIT_TRAN.getCode());
        Date current = new Date();
        newBatch.setApplyTime(current);
        // 定时发送
        if (channelPojo != null && channelPojo.getScheduleDeliver() != 0) {
            newBatch.setDefaultCloseTime(DateUtil.addMin(current, channelPojo.getScheduleDeliver()));    
        }
        // 最后关闭时间
        if (StringUtil.isNotEmpty(channelPojo.getFinalDeliverTime())) {
            try {
                newBatch.setLatestCloseTime(DateUtil.convertToDate(DateUtil.getCurrentDate()+channelPojo.getFinalDeliverTime(),"yyyyMMddHHmmss"));
            } catch (ParseException e) {
                log.error(e.getMessage(), e);
                log.error("设置转账批次关闭时间时出错");
            }
        }
        newBatch.setOpenStatus(BankTransferBatchOpenStatusEnum.OPEN.getCode());
        newBatch = bankTransferBatchDAO.merge(newBatch);
        return newBatch;
    }

    /**
     * 关闭批次
     * @param transferData
     */
    @Override
    public void closeBatch(PojoBankTransferBatch batch) {
        batch.setOpenStatus(BankTransferBatchOpenStatusEnum.CLOSE.getCode());
        batch.setCloseTime(new Date());
        batch.setCloseEvent("00");
        bankTransferBatchDAO.merge(batch);
    }

    /**
     * 插入转账明细
     *
     * @param transferData
     */
    @Override
    public TransferDataResult insertDetailBatch(TransferData transferData) {
        // 返回值定义
        TransferDataResult result = new TransferDataResult();
        // 取得渠道号
        String channelCode = transferData.getChannelCode();
        // 取得批次
        PojoBankTransferBatch batch = getBatchNoByChannel(channelCode);
        // Bean -> Pojo
        PojoBankTransferData detail = convertToPojo(transferData);
        detail.setBankTranBatch(batch);
        
        // 保存转账流水
        detail = bankTransferDetaDAO.merge(detail);
        // 保存转账批次
        batch.setTotalCount(batch.getTotalCount() + 1);
        batch.setTotalAmt(batch.getTotalAmt() + detail.getTranAmt());
        batch = bankTransferBatchDAO.merge(batch);
        batch.addTranBatch(transferData.getTranBatch());
        // 是否要关闭当前转账批次
        if (isCloseBatch(batch)) {
            closeBatch(batch);
        }
        result.setBankTranBatch(batch);
        result.setBankTranDeta(detail);
        return result;
    }

    /**
     * 是否要关闭当前转账批次
     * @param batch 当前转账批次
     * @return
     */
    private boolean isCloseBatch(PojoBankTransferBatch batch) {
        // 取出当前渠道的配置信息
        PojoBankTransferChannel channelPojo = bankTransferChannelDAO.getByChannelCode(batch.getChannel().getChannelCode());

        // 超过渠道定义的最大笔数的话，就关闭这个批次。
        if (channelPojo.getDetaCount() <= batch.getTotalCount() ) {
            return true;
        }
        return false;
    }

    /**
     * 将Bean 转化为转账明细的Pojo
     * @param transferData
     * @return
     */
    private PojoBankTransferData convertToPojo(TransferData transferData) {
        PojoBankTransferData data = new PojoBankTransferData();
        data.setBankTranDataSeqNo(seqNoService.getBatchNo(SeqNoEnum.BANK_TRAN_DATA_NO));
        data.setTranData(transferData);
        data.setTranAmt(transferData.getTranAmt());
        data.setAccNo(transferData.getAccNo());
        data.setAccName(transferData.getAccName());
        data.setAccBankNo(transferData.getBankNo());
        data.setAccBankName(transferData.getBankName());
        data.setStatus(BankTransferDataStatusEnum.INIT.getCode());
        data.setApplyTime(new Date());
        data.setAccType(transferData.getAccType());
        CardBin card = cardBinDao.getCard(transferData.getAccNo());
	     if (card != null && StringUtil.isNotEmpty(card.getBankCode()) && card.getBankCode().startsWith("0305")) {
	         data.setTransferType("01");
	     } else {
	         data.setTransferType("02");
	     }
        return data;
    }

    /**
     * 取得批次号<p/>
     * 如果该通道没有批次或者最新的批次状态是关闭，则新建一个批次
     * @param channel
     * @return
     */
    @Override
    public PojoBankTransferBatch getBatchNoByChannel(String channel) {
        // 取渠道
        PojoBankTransferChannel channelPojo = bankTransferChannelDAO.getByChannelCode(channel);
        // 判断是否有可用的批次（根据渠道）
        PojoBankTransferBatch batch = bankTransferBatchDAO.getByChannelId(channelPojo.getId());
        if (batch == null || BankTransferBatchOpenStatusEnum.CLOSE.getCode().equals(batch.getOpenStatus())) {
            // 如果该通道没有批次或者最新的批次已经关闭，则新建一个批次
            batch = createBatch(channel);
            return batch;
        } else {
            return batch;
        }
    }
    public static void main(String[] args) {
        try {
            System.out.println(DateUtil.formatDateTime("yyyyMMddHHmmss", DateUtil.convertToDate(DateUtil.getCurrentDate()+"175000","yyyyMMddHHmmss")));
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
            log.error("设置转账批次关闭时间时出错");
        }
    }
}
