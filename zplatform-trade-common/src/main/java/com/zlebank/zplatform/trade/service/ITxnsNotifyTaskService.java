/* 
 * ITxnsNotifyTaskService.java  
 * 
 * version TODO
 *
 * 2015年11月9日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import java.util.List;

import com.zlebank.zplatform.trade.model.TxnsNotifyTaskModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月9日 上午9:52:48
 * @since 
 */
public interface ITxnsNotifyTaskService extends IBaseService<TxnsNotifyTaskModel, Long>{

	/**
	 * 保存异步通知信息
	 * @param task
	 */
    public void saveTask(TxnsNotifyTaskModel task);
    /**
     * 更新异步通知信息
     * @param task
     * @return
     */
    public boolean updateTask(TxnsNotifyTaskModel task);
    /**
     * 通过交易序列号查询获取异步通知结果集
     * @param txnseqno
     * @param memberId
     * @return
     */
    public List<TxnsNotifyTaskModel> findTaskByTxnseqno(String txnseqno,String memberId);
    
    /**
     * 取得异步 通知类型为异步的异步通知交易
     * @param txnseqno
     * @return
     */
    public TxnsNotifyTaskModel getAsyncNotifyTask(String txnseqno);
}
