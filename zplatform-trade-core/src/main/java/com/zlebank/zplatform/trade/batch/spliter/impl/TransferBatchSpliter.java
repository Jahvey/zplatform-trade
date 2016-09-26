/* 
 * TransferBatchSpliter.java  
 * 
 * version v1.3
 *
 * 2016年3月3日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.batch.spliter.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.commons.bean.CardBin;
import com.zlebank.zplatform.commons.dao.CardBinDao;
import com.zlebank.zplatform.commons.utils.BeanCopyUtil;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.trade.batch.spliter.BatchManager;
import com.zlebank.zplatform.trade.batch.spliter.BatchSpliter;
import com.zlebank.zplatform.trade.batch.spliter.ChannelCondition;
import com.zlebank.zplatform.trade.batch.spliter.ChannelGenerator;
import com.zlebank.zplatform.trade.batch.spliter.TransferDataResult;
import com.zlebank.zplatform.trade.bean.enums.TransferDataStatusEnum;
import com.zlebank.zplatform.trade.dao.BankTransferDataDAO;
import com.zlebank.zplatform.trade.dao.TranDataDAO;
import com.zlebank.zplatform.trade.exception.RecordsAlreadyExistsException;
import com.zlebank.zplatform.trade.model.PojoBankTransferData;
import com.zlebank.zplatform.trade.model.PojoTranData;

/**
 * 批次分流器
 * @see com.zlebank.zplatform.trade.batch.spliter.BatchManager
 * 
 *
 * @author Luxiaoshuai
 * @version 1.3.0
 * @date 2016年3月3日 下午5:41:21
 * @since 
 */
@Service
public class TransferBatchSpliter implements BatchSpliter{

    @Autowired
    private BatchManager batchManager;
    @Autowired
    private TranDataDAO tranDataDAO;
    @Autowired
    BankTransferDataDAO bankTransferDetaDAO;
    @Autowired
    CardBinDao cardBinDao ;

    /**
     * 针对划拨流水进行合并/拆分后，转换为转账流水并保存(N->N)
     *
     * @param datas 多笔划拨流水的Pojo
     * @throws RecordsAlreadyExistsException 
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public void split(PojoTranData[] datas) throws RecordsAlreadyExistsException {
        
        // 循环数据
        for (PojoTranData data : datas) {
            if (data == null) continue;
            // 有效性检查
            checkDetails(data);
            // 合并拆分
            // TODO: 暂时没有
            // 保存转账流水
            TransferData transferData = converToTransferData(data);
            // 获取渠道
            String channel = getChannel(transferData);
            transferData.setChannelCode(channel);
            TransferDataResult result = batchManager.insertDetailBatch(transferData);
            // 更新划拨流水信息
            data.setBankTranData(result.getBankTranDeta());

            //更新划拨状态
            data.setStatus(TransferDataStatusEnum.APPROVED.getCode());
            tranDataDAO.merge(data);
        }
    }

    /**
     * 有效性检查
     * @param data
     * @throws RecordsAlreadyExistsException 
     */
    private void checkDetails(PojoTranData data) throws RecordsAlreadyExistsException {
        // 重复检查
        List<PojoBankTransferData> btd = bankTransferDetaDAO.getByTranDataId(data.getTid());
        if (btd != null && btd.size() > 0)
            throw new RecordsAlreadyExistsException();
    }

    /**
     * 转换数据（1->1）（没有合并拆分的情况）
     * @param data
     * @return
     */
    private TransferData converToTransferData(PojoTranData data) {
        TransferData rtn = BeanCopyUtil.copyBean(TransferData.class, data);
        return rtn;
    }
    
    /**
     * 转账流水
     * @param transferData
     * @return
     */
    private String getChannel(TransferData transferData) {
        ChannelCondition channel = new ChannelCondition();
        CardBin card = cardBinDao.getCard(transferData.getAccNo());
        if (card != null && StringUtil.isNotEmpty(card.getBankCode()) && card.getBankCode().startsWith("0305")) {
            channel.setBankType("01");
        } else {
            channel.setBankType("02");
        }
        return ChannelGenerator.getInstance().getChannel(channel);
    }
}
