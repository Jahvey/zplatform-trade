package com.zlebank.zplatform.trade.dao;

import java.util.List;

import com.zlebank.zplatform.commons.bean.TransferBatchQuery;
import com.zlebank.zplatform.commons.dao.BasePagedQueryDAO;
import com.zlebank.zplatform.commons.dao.pojo.AccStatusEnum;
import com.zlebank.zplatform.trade.bean.enums.TransferBatchStatusEnum;
import com.zlebank.zplatform.trade.model.PojoTranBatch;

public interface TranBatchDAO extends BasePagedQueryDAO<PojoTranBatch,TransferBatchQuery>{

    /**
     * 通过批次号查找批次信息
     * @param batchno
     * @return
     */
    public PojoTranBatch getByBatchNo(Long tid) ;
    
    /**
     * 更新批次信息
     * @param transferBatch
     */
    public void updateBatchToTransfer(PojoTranBatch transferBatch);
    
    /**
     * 通过上传文件名称取得批次信息
     * @param fileName
     * @return
     */
    public PojoTranBatch getByReqestFileName(String fileName);
    
    /**
     * 通过回盘文件名称取得批次信息
     * @param fileName
     * @return
     */
    public PojoTranBatch getByResponseFileName(String fileName);
    /**
     * 更新批次数据
     * @param transferBatch
     */
    public void updateTransferBatch(PojoTranBatch transferBatch);
    
    /**
     * 获取待账务处理的批次数据
     * @return
     */
    public List<PojoTranBatch> findWaitAccountingTransferBatch();
    
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
    public void updateAccountingResult(String batchno,AccStatusEnum accStatus);
    
    /**
     * 通过代付批次号查找批次数据
     * @param insteadpaybatchno
     * @return
     */
    public List<PojoTranBatch> findByInsteadpaybatchno(String insteadpaybatchno);
    
    /**
     * 获取回盘文件后，更新划拨批次数据
     * @param tid
     */
    public void updateBankTransferResult(Long tid);
    /**
     * 根据代付批次和审核状态查询划拨批次
     * @param insteadBatchNo
     * @param status
     * @return
     */
    public List<PojoTranBatch> getByInsteadPayBatchandStaus(Long id, List<String> status);
}
