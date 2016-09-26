package com.zlebank.zplatform.trade.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.zlebank.zplatform.commons.dao.BaseDAO;
import com.zlebank.zplatform.trade.bean.page.QueryTransferBean;
import com.zlebank.zplatform.trade.model.PojoTranBatch;
import com.zlebank.zplatform.trade.model.PojoTranData;

public interface TransferBatchDAO extends BaseDAO<PojoTranBatch>{

    
    
    /**
     * 查询划拨批次数据（分页）
     * @param queryTransferBean
     * @param page
     * @param pageSize
     * @return
     * @throws ParseException 
     */
    public Map<String, Object> queryTransferBatchByPage(QueryTransferBean queryTransferBean,int page,int pageSize) throws ParseException;
    
    /**
     * 更新划拨批次状态
     * @param transferBatch
     */
    public void updateBatchTransferFinish(PojoTranBatch transferBatch);
    
    /**
     * 划拨批次审核
     * @param batchNo
     * @param status
     */
    public void batchTrailTransfer(String batchNo,String status);
    
    /**
     * 获取划拨批次数据
     * @param batchId
     * @return
     */
    public PojoTranBatch getByBatchId(long batchId);
    /**
     * 获取等待审核的划拨数据
     * @param batchId
     * @return
     */
    public List<PojoTranData> queryWaitTrialTranData(long batchId);
}
