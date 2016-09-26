/* 
 * InsteadPayDetailDAO.java  
 * 
 * version v1.0
 *
 * 2015年11月24日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao;

import java.util.List;

import com.zlebank.zplatform.commons.dao.BasePagedQueryDAO;
import com.zlebank.zplatform.trade.bean.InsteadPayDetailQuery;
import com.zlebank.zplatform.trade.model.PojoInsteadPayDetail;

/**
 * 代付批次明细DAO
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月24日 下午12:29:58
 * @since 
 */
public interface InsteadPayDetailDAO extends   BasePagedQueryDAO<PojoInsteadPayDetail, InsteadPayDetailQuery>{
    /**
     * 通过批次号得到代付明细
     * @param batchNo
     * @return
     */
    public List<PojoInsteadPayDetail> getByBatchDetail(String batchNo);
    /**
     * 通过批次ID得到代付明细
     * @param batchNo
     * @return
     */
    public List<PojoInsteadPayDetail> getBatchDetailByBatchId(Long batchId);
    
    /**
     * 通过代付流水ID得到代付明细
     * @param ids
     * @return
     */
    public List<PojoInsteadPayDetail>  getBatchDetailByIds(List<Long> ids); 
    
    /**
     * 更新代付交易结果
     * @param insteadPayDetail
     */
    public void updateBatchDetailResult(PojoInsteadPayDetail insteadPayDetail);
    
    /**
     * 根据交易序列号取流水
     * @param txnseqno
     * @param status
     * @return
     */
    public PojoInsteadPayDetail getDetailByTxnseqno(String txnseqno,String status);
    
    /**
     * 根据交易序列号取流水
     * @param txnseqno
     * @return
     */
    PojoInsteadPayDetail getDetailByTxnseqno(String txnseqno);
    
    /**
     * 通过代付判断是否已经处理完毕
     * @return
     */
    public boolean  isBatchProcessFinished(Long batchId); 
    
    }
