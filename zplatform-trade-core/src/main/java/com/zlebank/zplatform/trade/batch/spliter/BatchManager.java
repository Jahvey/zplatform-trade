/* 
 * BatchManager.java  
 * 
 * version TODO
 *
 * 2016年3月7日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.batch.spliter;

import com.zlebank.zplatform.trade.batch.spliter.impl.TransferData;
import com.zlebank.zplatform.trade.model.PojoBankTransferBatch;

/**
 * 批次管理类
 *
 * @author Luxiaoshuai
 * @version v1.3
 * @date 2016年3月7日 上午11:08:02
 * @since 
 */
public interface BatchManager {
    /**
     * 根据渠道创建批次
     * @param channel 批次渠道号
     * @return 
     */ 
    PojoBankTransferBatch createBatch(String channel);
    /**
     * 关闭指定批次
     * @param transferData 转账批次的Pojo
     * @return 
     */ 
    void closeBatch(PojoBankTransferBatch transferData);
    /**
     * 插入转账明细
     *
     * @param transferData 转账的Bean
     */
    TransferDataResult insertDetailBatch(TransferData transferData);
    /**
     * 取得批次号<p/>
     * 如果该通道没有批次或者最新的批次状态是关闭，则新建一个批次
     * @param channel 银行划拨渠道号
     * @return
     */
    PojoBankTransferBatch getBatchNoByChannel(String channel);
}
