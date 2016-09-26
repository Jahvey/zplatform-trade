/* 
 * CMCBCRefundTrade.java  
 * 
 * version TODO
 *
 * 2016年5月19日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.cmbc.refund;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.trade.adapter.quickpay.IRefundTrade;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.enums.InsteadPayDetailStatusEnum;
import com.zlebank.zplatform.trade.bean.enums.SeqNoEnum;
import com.zlebank.zplatform.trade.bean.enums.TransferBatchStatusEnum;
import com.zlebank.zplatform.trade.bean.enums.TransferBusiTypeEnum;
import com.zlebank.zplatform.trade.bean.enums.TransferDataStatusEnum;
import com.zlebank.zplatform.trade.dao.TranBatchDAO;
import com.zlebank.zplatform.trade.dao.TranDataDAO;
import com.zlebank.zplatform.trade.exception.RecordsAlreadyExistsException;
import com.zlebank.zplatform.trade.model.PojoTranBatch;
import com.zlebank.zplatform.trade.model.PojoTranData;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsRefundModel;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.ITxnsRefundService;
import com.zlebank.zplatform.trade.service.SeqNoService;
import com.zlebank.zplatform.trade.utils.SpringContext;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年5月19日 上午8:35:22
 * @since 
 */

public class CMCBCRefundTrade implements IRefundTrade {

	@Autowired
	private ITxnsLogService txnsLogService;
	@Autowired
	private ITxnsRefundService txnsRefundService;
	@Autowired
    private TranDataDAO tranDataDAO;
    @Autowired
    private TranBatchDAO tranBatchDAO;
    @Autowired
    private SeqNoService seqNoService;
    
    
    public CMCBCRefundTrade(){
    	txnsLogService = (ITxnsLogService) SpringContext.getContext().getBean("txnsLogService");
    	txnsRefundService = (ITxnsRefundService) SpringContext.getContext().getBean("txnsRefundService");
    	tranDataDAO = (TranDataDAO) SpringContext.getContext().getBean("tranDataDAOImpl");
    	tranBatchDAO = (TranBatchDAO) SpringContext.getContext().getBean("tranBatchDAOImpl");
    	seqNoService = (SeqNoService) SpringContext.getContext().getBean("seqNoServiceImpl");
    }
	
	/**
	 *
	 * @param tradeBean
	 * @return
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Throwable.class)
	public ResultBean refund(TradeBean tradeBean) {
		TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(tradeBean.getTxnseqno());
		TxnsRefundModel refund = txnsRefundService.getRefundByTxnseqno(tradeBean.getTxnseqno());
		TxnsLogModel txnsLog_old = txnsLogService.getTxnsLogByTxnseqno(txnsLog.getTxnseqnoOg());
		
		//民生代扣走代付流程
		PojoTranData pojoTranData = new PojoTranData();
        List<PojoTranData> pojoTranDataList = new ArrayList<PojoTranData>();
        // 划拨批次号
        pojoTranData.setTranDataSeqNo(String.valueOf(System
                .currentTimeMillis()));
        // pojoTranData.setTranBatch(tranBatch);
        pojoTranData.setAccType("00");
        pojoTranData.setAccNo(txnsLog_old.getPan());
        pojoTranData.setAccName(txnsLog_old.getPanName());
        // 划拨金额
        pojoTranData.setTranAmt(refund.getAmount());
        // pojoTranData.setBusiDataId("11111111111111");
        // pojoTranDataList.add(pojoTranData);

        // pojoTranData.setTxnseqno();
        pojoTranData.setStatus(InsteadPayDetailStatusEnum.WAIT_TRAN_APPROVE
                .getCode());
        // PojoTranData tmp = BeanCopyUtil.copyBean(PojoTranData.class,
        // pojoTranData);
        // pojoTranData.setTranAmt(detail.getAmt());
        // /** "业务流水号" **/
        pojoTranData.setBusiType("02");
        pojoTranData.setBusiDataId(refund.getId());
        pojoTranData.setMemberId(refund.getSubmerchno());
        // 交易手续费0
        pojoTranData.setTranFee(txnsLogService.getTxnFee(txnsLog));
        pojoTranData.setBusiType(TransferBusiTypeEnum.INSTEAD.getCode());
        pojoTranData.setBusiSeqNo(refund.getRefundorderno());
        pojoTranData.setTxnseqno(tradeBean.getTxnseqno());
        //pojoTranData.setBankNo(refund.get);
        //pojoTranData.setBankName(job.get("ACCORDNO").toString());
        pojoTranDataList.add(pojoTranData);

        try {
            saveTransferData(
                    TransferBusiTypeEnum.REFUND, 1L, pojoTranDataList);
           
        } catch (RecordsAlreadyExistsException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return null;
	}
	
	
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Throwable.class)
    public long saveTransferData(TransferBusiTypeEnum type, Long busiBatchId, List<PojoTranData> datas) throws RecordsAlreadyExistsException {
		
        if (datas == null || datas.size() ==0) return 0;
        
        PojoTranBatch batch = new PojoTranBatch();
        // 保存划拨流水信息
        batch.setApplyTime(new Date());
        batch.setBusiType(type.getCode());
        batch.setBusiBatchId(busiBatchId);
        batch.setStatus(TransferBatchStatusEnum.INIT.getCode());
        batch.setTranBatchNo(seqNoService.getBatchNo(SeqNoEnum.TRAN_BATCH_NO));
        // 保存划拨批次统计数据
        batch.setTotalCount(0L);
        batch.setTotalAmt(0L);
        batch.setWaitApproveCount(0L);
        batch.setWaitApproveAmt(0L);
        batch.setRefuseCount(0L);
        batch.setRefuseAmt(0L);
        batch.setApproveCount(0L);
        batch.setApproveAmt(0L);
        batch = tranBatchDAO.merge(batch);
        // 循环数据
        for (PojoTranData data : datas) {
            if (data == null) continue;
            
            data.setBusiType(type.getCode());
            // 有效性检查
            checkDetails(data);
            // 保存划拨流水
            data.setTranDataSeqNo(seqNoService.getBatchNo(SeqNoEnum.TRAN_DATA_NO));
            data.setApplyTime(new Date());
            data.setStatus(TransferDataStatusEnum.INIT.getCode());
            data.setTranBatch(batch);
            
            data = tranDataDAO.merge(data);
            // 保存划拨批次统计数据
            batch.setTotalCount(addOne(batch.getTotalCount()));
            batch.setTotalAmt(addAmount(batch.getTotalAmt(), data.getTranAmt()));

            batch.setWaitApproveCount(addOne(batch.getWaitApproveCount()));
            batch.setWaitApproveAmt(addAmount(batch.getWaitApproveAmt(), data.getTranAmt()));
        }
        // 保存划拨批次
        //tranBatchDAO.saveA(batch);
        batch = tranBatchDAO.merge(batch);
        
        return batch.getTid().longValue();
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
     * 有效性检查
     * @param data
     * @throws RecordsAlreadyExistsException 
     */
    private void checkDetails(PojoTranData data) throws RecordsAlreadyExistsException {
        // 重复检查
        int count = tranDataDAO.getCountByInsteadDataId(data.getBusiDataId(),data.getBusiType());
        if (count > 0)
            throw new RecordsAlreadyExistsException();
    }
    
    
}
