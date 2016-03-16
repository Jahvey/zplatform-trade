/* 
 * InsteadPayServiceImpl.java  
 * 
 * version v1.0
 *
 * 2015年11月25日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.acc.bean.TradeInfo;
import com.zlebank.zplatform.acc.service.AccEntryService;
import com.zlebank.zplatform.commons.service.impl.AbstractBasePageService;
import com.zlebank.zplatform.commons.utils.BeanCopyUtil;
import com.zlebank.zplatform.trade.bean.InsteadPayBatchBean;
import com.zlebank.zplatform.trade.bean.InsteadPayBatchQuery;
import com.zlebank.zplatform.trade.bean.UpdateData;
import com.zlebank.zplatform.trade.bean.enums.InsteadPayBatchStatusEnum;
import com.zlebank.zplatform.trade.bean.enums.InsteadPayDetailStatusEnum;
import com.zlebank.zplatform.trade.bean.enums.TransferBusiTypeEnum;
import com.zlebank.zplatform.trade.dao.InsteadPayBatchDAO;
import com.zlebank.zplatform.trade.dao.InsteadPayDetailDAO;
import com.zlebank.zplatform.trade.exception.FailToInsertAccEntryException;
import com.zlebank.zplatform.trade.exception.InvalidAuditDataException;
import com.zlebank.zplatform.trade.exception.RecordsAlreadyExistsException;
import com.zlebank.zplatform.trade.model.PojoInsteadPayBatch;
import com.zlebank.zplatform.trade.model.PojoInsteadPayDetail;
import com.zlebank.zplatform.trade.model.PojoTranData;
import com.zlebank.zplatform.trade.service.InsteadBatchService;
import com.zlebank.zplatform.trade.service.TransferDataService;
import com.zlebank.zplatform.trade.service.UpdateSubject;

/**
 * 代付业务
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月25日 上午10:48:24
 * @since 
 */
@Service
public class InsteadBatchServiceImpl extends AbstractBasePageService<InsteadPayBatchQuery, InsteadPayBatchBean> implements InsteadBatchService, UpdateSubject{
    
    private static final Log log = LogFactory.getLog(InsteadBatchServiceImpl.class);
  
    @Autowired
    private InsteadPayBatchDAO insteadPayBatchDAO;
    
    @Autowired
    private InsteadPayDetailDAO insteadPayDetailDAO;
    
    @Autowired
    private TransferDataService transferDataService;
    
    @Autowired
    private AccEntryService accEntryService;


    /**
     *
     * @param example
     * @return
     */
    @Override
    protected long getTotal(InsteadPayBatchQuery example) {
    return     insteadPayBatchDAO.count(example);
    }


    /**
     *
     * @param offset
     * @param pageSize
     * @param example
     * @return
     */
    @Override
    protected List<InsteadPayBatchBean> getItem(int offset,
            int pageSize,
            InsteadPayBatchQuery example) {
      List<PojoInsteadPayBatch> insteadBatch=insteadPayBatchDAO.getListByQuery(offset, pageSize, example);
      List<InsteadPayBatchBean> li=new ArrayList<InsteadPayBatchBean>();
      for(PojoInsteadPayBatch pojoinstead:insteadBatch){
          InsteadPayBatchBean insteadBean= BeanCopyUtil.copyBean(InsteadPayBatchBean.class, pojoinstead);
          li.add(insteadBean);
      }
          return li;
    }


    /**
     * 多个批次的批量审核（将该批次号下的所有未审核的明细改为已审核状态）
     * @param batchId
     * @throws RecordsAlreadyExistsException 
     * @throws FailToInsertAccEntryException 
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void batchAudit(List<Long> batchIds, boolean pass) throws RecordsAlreadyExistsException, FailToInsertAccEntryException {
        
        // 一次审核多个批次
        for (Long batch : batchIds) {
            processSingleBatch(batch, pass);
        }
        
    } 
    
    /**
     * 多条明细的审核
     * @param detailIds
     * @throws RecordsAlreadyExistsException 
     * @throws FailToInsertAccEntryException 
     * @throws InvalidAuditDataException 
     */
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void detailsAudit(List<Long> detailIds, boolean pass) throws RecordsAlreadyExistsException, FailToInsertAccEntryException, InvalidAuditDataException {
        // 取出明细数据
        List<PojoInsteadPayDetail> details = insteadPayDetailDAO.getBatchDetailByIds(detailIds);

        if (details == null || details.size() != detailIds.size()) 
            throw new InvalidAuditDataException();
        
        // 取批次ID
        Long batchId = details.get(0).getInsteadPayBatch().getId();

        // 审核处理
        processAudit(batchId, details , pass);
    }
    
    /**
     * 审核单个批次
     * @param batchId
     * @throws RecordsAlreadyExistsException 
     * @throws FailToInsertAccEntryException 
     */
    private void processSingleBatch(Long batchId, boolean pass) throws RecordsAlreadyExistsException, FailToInsertAccEntryException {
        // 取出明细数据
        List<PojoInsteadPayDetail> details = insteadPayDetailDAO.getBatchDetailByBatchId(batchId);
        if (details == null || details.size() == 0) return;
        
        // 审核处理
        processAudit(batchId, details , pass);
    }
    
    /**
     * 明细的审核处理
     * @param batchId
     * @param details
     * @throws RecordsAlreadyExistsException
     * @throws FailToInsertAccEntryException 
     */
    private void processAudit(Long batchId, List<PojoInsteadPayDetail> details, boolean pass) throws RecordsAlreadyExistsException, FailToInsertAccEntryException {
        if (pass) {
            // 审核通过时
            
            // 插入划拨流水
            List<PojoTranData> tranDatas = new ArrayList<PojoTranData>();
            for (PojoInsteadPayDetail detail : details) {
                // 更新代付状态
                detail.setStatus(InsteadPayDetailStatusEnum.WAIT_TRAN_APPROVE.getCode());
                PojoTranData tmp = BeanCopyUtil.copyBean(PojoTranData.class, detail);
                tmp.setTranAmt(detail.getAmt());
                tmp.setBusiDataId(detail.getId());
                tranDatas.add(tmp);
                insteadPayDetailDAO.merge(detail);
            }
            transferDataService.saveTransferData(TransferBusiTypeEnum.INSTEAD, batchId, tranDatas);
            // 更新批次状态
            PojoInsteadPayBatch batch = insteadPayBatchDAO.getByBatchId(batchId);
            for(PojoInsteadPayDetail detail : details) {
                batch.setApproveCount(addOne(batch.getApproveCount()));
                batch.setApproveAmt(addAmount(batch.getApproveAmt(),detail.getAmt()));
                batch.setUnapproveCount(batch.getUnapproveCount()-1);
                batch.setUnapproveAmt(batch.getUnapproveAmt() - detail.getAmt());
            }
            if (batch.getUnapproveCount() == 0) {
                batch.setStatus(InsteadPayBatchStatusEnum.ALL_APPROVED.getCode());
            } else {
                batch.setStatus(InsteadPayBatchStatusEnum.PART_APPROVED.getCode());
            }
            insteadPayBatchDAO.merge(batch);
        } else {
            // 审核拒绝时

            // 更新代付状态
            for (PojoInsteadPayDetail detail : details) {
                detail.setStatus(InsteadPayDetailStatusEnum.INSTEAD_REFUSE.getCode());
                insteadPayDetailDAO.merge(detail);
                
                 // 账务处理
                TradeInfo tradeInfo = new TradeInfo();
                tradeInfo.setPayMemberId(detail.getMerId());
                tradeInfo.setAmount(new BigDecimal(detail.getAmt()));
                tradeInfo.setCharge(new BigDecimal(detail.getTxnfee()));
                tradeInfo.setTxnseqno(detail.getTxnseqno());
                tradeInfo.setBusiCode("70000003");
                try {
                    accEntryService.accEntryProcess(tradeInfo );
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    throw new FailToInsertAccEntryException();
                }
            }
            // 更新批次状态
            PojoInsteadPayBatch batch = insteadPayBatchDAO.getByBatchId(batchId);
            for(PojoInsteadPayDetail detail : details) {
                batch.setRefuseCount(addOne(batch.getRefuseCount()));
                batch.setRefuseAmt(addAmount(batch.getRefuseAmt(), detail.getAmt()));
                batch.setUnapproveCount(batch.getUnapproveCount()-1);
                batch.setUnapproveAmt(batch.getUnapproveAmt() - detail.getAmt());
            }
            if (batch.getUnapproveCount() == 0) {
                batch.setStatus(InsteadPayBatchStatusEnum.ALL_APPROVED.getCode());
            } else {
                batch.setStatus(InsteadPayBatchStatusEnum.PART_APPROVED.getCode());
            }
            insteadPayBatchDAO.merge(batch);
            

        }
    }
    
    /**
     * 添加指定金额
     * @param totalAmt 总金额
     * @param tranAmt 被添加的金额
     * @return
     */
    private Long addAmount(Long totalAmt, Long tranAmt) {
        return totalAmt == null ? tranAmt : totalAmt + tranAmt;
    }

    /**
     * 将指定的数据加1
     * @param data
     * @return
     */
    private long addOne(Long data) {
        return data == null ? 1 : data.longValue() + 1;
    }


    /**
     *  更新状态和记账
     * @param data
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void update(UpdateData data) {
        PojoInsteadPayDetail detail = insteadPayDetailDAO.getDetailByTxnseqno(data.getTxnSeqNo());
        if (detail == null) {
            log.error("没有找到需要记账的流水");
            return;
        }
        if ("00".equals(data.getResultCode())) {
            detail.setStatus(InsteadPayDetailStatusEnum.TRAN_FINISH.getCode());
        } else {
            detail.setStatus(InsteadPayDetailStatusEnum.TRAN_FAILED.getCode());
        }
        detail.setRespCode(data.getResultCode());
        detail.setRespMsg(data.getResultMessage());
        insteadPayDetailDAO.merge(detail);
        
        TradeInfo tradeInfo = new TradeInfo();
        tradeInfo.setPayMemberId(detail.getMerId());
        tradeInfo.setAmount(new BigDecimal(detail.getAmt()));
        tradeInfo.setCharge(new BigDecimal(detail.getTxnfee()));
        tradeInfo.setTxnseqno(detail.getTxnseqno());
        if ("00".equals(data.getResultCode())) {
            tradeInfo.setBusiCode("70000002");
            tradeInfo.setChannelId(data.getChannelCode());
        } else {
            tradeInfo.setBusiCode("70000003");
        }
        
        try {
            accEntryService.accEntryProcess(tradeInfo );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
//            throw new FailToInsertAccEntryException();
        }
    }


    /**
     * 得到业务代码
     */
    @Override
    public String getBusiCode() {
        return "00";
    }
}
