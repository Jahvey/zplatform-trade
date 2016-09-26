package com.zlebank.zplatform.trade.dao;

import java.util.List;
import java.util.Map;

import com.zlebank.zplatform.commons.dao.BaseDAO;
import com.zlebank.zplatform.trade.bean.cmbc.ReexchangeBean;
import com.zlebank.zplatform.trade.bean.enums.InsteadPayTypeEnum;
import com.zlebank.zplatform.trade.bean.page.QueryTransferBean;
import com.zlebank.zplatform.trade.model.PojoBankTransferData;

public interface BankTransferDataDAO  extends BaseDAO<PojoBankTransferData>{
	/**
     * 通过批次号查找划拨数据
     * @param batchNo
     * @return
     */
    public List<PojoBankTransferData> findTransDataByBatchNo(Long tid);
    /**
     * 通过批次号更新划拨数据
     * @param tid
     * @param payType
     */
    public void updateTransDataStatusByBatchNo(Long tid,InsteadPayTypeEnum payType);
    
    /**
     * 批量更新划拨数据
     * @param transferDataList
     */
    public void batchUpdateTransData(List<PojoBankTransferData> transferDataList);
    /**
     * 批量更新划拨数据-账务处理信息
     * @param transferDataList
     */
    public void batchUpdateTransDataAccStatus(List<PojoBankTransferData> transferDataList);
    /**
     * 通过Id得到划拨信息
     * @param id
     * @return
     */
    public PojoBankTransferData  getTransferDataByTranId(String tranid,String status);
    
    
    /**
     * 通过批次号和账务状态获取批次明细数据
     * @param batchNo
     * @return
     */
    public List<PojoBankTransferData> findTransDataByBatchNoAndAccstatus(String batchNo);
    
    /**
     * 根据划拨流水ID取出转账ID
     * @param id
     * @return
     */
    public List<PojoBankTransferData> getByTranDataId(Long id);
    
    /**
     * 分页查询转账明细
     * @param queryTransferBean
     * @param page
     * @param pageSize
     * @return
     */
    public Map<String, Object> queryBankTransferDataByPage(QueryTransferBean queryTransferBean,int page,int pageSize);
    
    /**
     * 根据转账批次号好回去转账信息
     * @param bankTranBatchId
     * @return
     */
    public List<PojoBankTransferData> findBankTransferDataByBankTranBatchId(Long bankTranBatchId);
    
    /**
     * 更新待审核批次数据的状态
     * @param bankTranBatchId
     * @param status
     */
    public void updateWaitBankTransferStatus(String bankTranBatchId,String status);
    
    /**
     * 
     * @param bankTranDataSeqNo
     * @return
     */
    public PojoBankTransferData getTransferDataByTranId(String bankTranDataSeqNo);
    
    /***
     * 更新博士金电的批量代付结果
     * @param transferDataList
     */
    public void batchUpdateBossPayTransData(List<PojoBankTransferData> transferDataList) ;
    
    
    /**
     * 更新转账数据的状态
     * @param bankTranBatchId
     * @param status
     */
    public void updateBankTransferStatus(String bankTranBatchId,String status);
    
    /**
     * 更新退汇交易数据
     * @param reexchangeList
     */
    public void batchUpdateReexchangeTransData(List<ReexchangeBean> reexchangeList);
}
