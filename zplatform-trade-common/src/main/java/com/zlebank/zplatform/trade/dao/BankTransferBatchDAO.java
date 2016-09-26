package com.zlebank.zplatform.trade.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.zlebank.zplatform.commons.bean.TransferBatchQuery;
import com.zlebank.zplatform.commons.dao.BasePagedQueryDAO;
import com.zlebank.zplatform.commons.dao.pojo.AccStatusEnum;
import com.zlebank.zplatform.trade.bean.enums.BankTransferBatchOpenStatusEnum;
import com.zlebank.zplatform.trade.bean.page.QueryTransferBean;
import com.zlebank.zplatform.trade.model.PojoBankTransferBatch;

public interface BankTransferBatchDAO extends BasePagedQueryDAO<PojoBankTransferBatch,TransferBatchQuery>{

    /**
     * 通过批次号查找批次信息
     * @param batchno
     * @return
     */
    public PojoBankTransferBatch getByBatchNo(String batchno);
    
    /**
     * 更新批次信息
     * @param transferBatch
     */
    public void updateBatchToTransfer(PojoBankTransferBatch transferBatch);
    
    /**
     * 通过上传文件名称取得批次信息
     * @param fileName
     * @return
     */
    public PojoBankTransferBatch getByReqestFileName(String fileName);
    
    /**
     * 通过回盘文件名称取得批次信息
     * @param fileName
     * @return
     */
    public PojoBankTransferBatch getByResponseFileName(String fileName);
    /**
     * 更新批次数据
     * @param transferBatch
     */
    public void updateTransferBatch(PojoBankTransferBatch transferBatch);
    
    /**
     * 获取待账务处理的批次数据
     * @return
     */
    public List<PojoBankTransferBatch> findWaitAccountingTransferBatch();
    
    /**
     * 校验代付批次中的划拨批次是否都已完成
     * @param insteadpaybatchno
     * @return 0-划拨完成，其他划拨未完成
     */
    public int validateBatchResult(String insteadpaybatchno);
    /**
     * 更新批次账务状态
     * @param batchno 批次号
     * @param accStatus 账务状态
     */
    public void updateBatchTranStatus(Long tid, String tranStatus);
    
    /**
     * 通过代付批次号查找批次数据
     * @param insteadpaybatchno
     * @return
     */
    public List<PojoBankTransferBatch> findByInsteadpaybatchno(String insteadpaybatchno);
    
    /**
     * 通过渠道返回相应的批次号
     * @param long1 渠道号
     * @return
     */
    public PojoBankTransferBatch getByChannelId(Long long1);
    
    /**
     * 分页查询转账批次数据
     * @param queryTransferBean
     * @param page
     * @param pageSize
     * @return
     * @throws ParseException 
     */
    public Map<String, Object> queryBankTransferByPage(QueryTransferBean queryTransferBean,int page,int pageSize) throws ParseException;
    
    
    /**
     * 根据转账批次号获取批次数据
     * @param bankTranBatchNo
     * @return
     */
    public PojoBankTransferBatch getByBankTranBatchNo(Long tid);

    /**
     * 根据转账批次标示获取批次数据
     * @param bankTranBatchNo
     * @return
     */
    public PojoBankTransferBatch getById(Long tid);
    
    /**
     * 根据划拨批次和开放状态查询转账批次列表
     * @param bankTranBatchNo
     * @return
     */
    public List<PojoBankTransferBatch> getByTranBatchAndOpenStatus(long tranBatchId,BankTransferBatchOpenStatusEnum openStatus);
    
    /**
     * 关闭事件
     * @param tid
     * @return
     */
    public boolean closeBankTransferBatch(Long tid);
    
    /**
     * 转账失败，回滚批次数据为未审核状态
     * @param tid
     */
    public void rollbackBankTrans(Long tid);
}

