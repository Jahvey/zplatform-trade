
package com.zlebank.zplatform.trade.cmbc.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zlebank.zplatform.trade.dao.TransferBatchDAO;

/**
 * 代付账务处理定时任务
 *
 * @author guojia
 * @version
 * @date 2015年11月3日 下午5:49:29
 * @since 
 */
public class InsteadPayAccountingJob {
    private static final Log log = LogFactory.getLog(InsteadPayAccountingJob.class);

    @Autowired
    private TransferBatchDAO transferBatchDAO;
    
    /**
     * 定时获取划拨批次数据，划拨完成，但账务未处理的
     */
    public void execute() {
        log.info("start InsteadPayAccountingJob");
        //查找等待账务处理的批次信息
        /*List<PojoTransferBatch> transferBatchList = transferBatchDAO.findWaitAccountingTransferBatch();
        for(PojoTransferBatch transferBatch:transferBatchList){
            try {
                String busitype = transferBatch.getBusitype();//取得业务代码
                IAccounting accounting = AccountingAdapterFactory.getInstance().getAccounting(BusiTypeEnum.fromValue(busitype));
                log.info(accounting);
                if(accounting!=null){
                    accounting.accountedForInsteadPay(transferBatch.getBatchno());
                    transferBatchDAO.updateAccountingResult(transferBatch.getBatchno(), AccStatusEnum.Finish);
                }
            }catch(Exception e) {
                transferBatchDAO.updateAccountingResult(transferBatch.getBatchno(), AccStatusEnum.AccountingFail);
                e.printStackTrace();
            }
            
        }*/
        log.info("end InsteadPayAccountingJob");
    }
    
    public TransferBatchDAO getTransferBatchDAO() {
        return transferBatchDAO;
    }
    public void setTransferBatchDAO(TransferBatchDAO transferBatchDAO) {
        this.transferBatchDAO = transferBatchDAO;
    }
    
   
    
    
}
