/* 
 * BossInsteadPayServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年4月21日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bosspay.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.UpdateData;
import com.zlebank.zplatform.trade.bean.enums.InsteadPayTypeEnum;
import com.zlebank.zplatform.trade.bosspay.bean.BtchpmtItemBean;
import com.zlebank.zplatform.trade.bosspay.bean.BtchpmtRequestBean;
import com.zlebank.zplatform.trade.bosspay.bean.BtchpmtResponseBean;
import com.zlebank.zplatform.trade.bosspay.bean.BtchpmtResultBean;
import com.zlebank.zplatform.trade.bosspay.enums.StatusEnum;
import com.zlebank.zplatform.trade.bosspay.service.BossInsteadPayService;
import com.zlebank.zplatform.trade.bosspay.service.BossPayService;
import com.zlebank.zplatform.trade.dao.BankTransferBatchDAO;
import com.zlebank.zplatform.trade.dao.BankTransferDataDAO;
import com.zlebank.zplatform.trade.dao.ITxnsInsteadPayDAO;
import com.zlebank.zplatform.trade.dao.TransferDataDAO;
import com.zlebank.zplatform.trade.model.PojoBankTransferBatch;
import com.zlebank.zplatform.trade.model.PojoBankTransferData;
import com.zlebank.zplatform.trade.model.PojoTranData;
import com.zlebank.zplatform.trade.model.PojoTxnsInsteadPay;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.ObserverListService;
import com.zlebank.zplatform.trade.utils.ConsUtil;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年4月21日 下午2:54:57
 * @since 
 */
@Service("bossInsteadPayService")
public class BossPayInsteadPayServiceImpl implements BossInsteadPayService{

	private final static Log log = LogFactory.getLog(BossPayInsteadPayServiceImpl.class);
	@Autowired
	private BossPayService bossPayService;
	@Autowired
	private BankTransferBatchDAO bankTransferBatchDAO;
	@Autowired
	private ITxnsInsteadPayDAO txnsInsteadPayDAO;
	@Autowired
	private BankTransferDataDAO bankTransferDataDAO;
	@Autowired
	private ITxnsLogService txnsLogService;
	@Autowired
	private TransferDataDAO transferDataDAO;
	
	/**
	 *
	 * @param insteadPaySeqNo
	 * @return
	 */
	@Override
	public ResultBean realTimePay(String insteadPaySeqNo) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 *
	 * @param batchNo
	 * @return
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public ResultBean batchPay(String batchNo) {
		ResultBean resultBean = null;
        try {
            //检查批次信息是否正确
        	PojoBankTransferBatch bankTransferBatch = bankTransferBatchDAO.getById(Long.valueOf(batchNo));
            if(!"01".equals(bankTransferBatch.getTranStatus())&&!"02".equals(bankTransferBatch.getTranStatus())){
                return new ResultBean("", "不可重复转账");
            }
            //判断当前批次是否已经上传成功
            if(txnsInsteadPayDAO.isUpload(batchNo+"")){
            	return new ResultBean("", "不可重复转账");
            }
            //生成交易日志
            List<PojoBankTransferData> transferDataList = bankTransferDataDAO.findTransDataByBatchNo(Long.valueOf(batchNo));
            txnsLogService.saveBossPayBankTransferLogs(transferDataList);
            
            /////////////////处理代付报文////////////////////////
            
            //PojoBankTransferBatch transferBatch = bankTransferBatchDAO.getByBankTranBatchNo(Long.valueOf(batchNo));
        	//PojoTransferBatch transferBatch = transferBatchDAO.getByBatchNo(batchNo);
            //List<PojoBankTransferData> transferDataList =  bankTransferDataDAO.findTransDataByBatchNo(Long.valueOf(batchNo));
            //List<InsteadPayBean> payList = initBatchData();
            Long sumAmt = 0L;
            Long sumItem = 0L;
            List<BtchpmtItemBean> itemList =new ArrayList<BtchpmtItemBean>();
    		
            for (PojoBankTransferData bean : transferDataList) {
            	 //第三方流水号|帐号|户名|支付行号|开户行名称|金额|摘要|备注
                
            	BtchpmtItemBean itemBean = new BtchpmtItemBean("",
            			bean.getAccNo(), 
            			bean.getAccName(), 
            			bean.getAccBankNo(),//bankNumber,
            			bean.getAccBankName(),//bankName, 
        				"江西恒邦",//bankProvince, 
        				"江西恒邦",//bankCity, 
        				bean.getTranAmt().longValue()+"",
        				"09001" );
            	itemList.add(itemBean);
                sumAmt += bean.getTranAmt().longValue();
                sumItem++;
                PojoTranData tranData = transferDataDAO.queryTransferData(bean.getTranData().getTid());
                TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(tranData.getTxnseqno());
                //风控
                txnsLogService.tradeRiskControl(txnsLog.getTxnseqno(),txnsLog.getAccfirmerno(),txnsLog.getAccsecmerno(),txnsLog.getAccmemberid(),txnsLog.getBusicode(),txnsLog.getAmount()+"",bean.getAccType(),bean.getAccNo());
            }
            
            log.info("cmbc Inner send msg:\r\n"+JSON.toJSONString(itemList));
          
            BtchpmtRequestBean requestBean = new BtchpmtRequestBean(ConsUtil.getInstance().cons.getBosspay_bank_account(),
    				"1000000004", "","33",
    				ConsUtil.getInstance().cons.getBosspay_user_key(),itemList);
            resultBean = bossPayService.batchInsteadPay(requestBean);
            if(resultBean.isResultBool()){
            	//修改批次状态为正在支付，修改每笔划拨的状态为正在支付
                bankTransferDataDAO.updateTransDataStatusByBatchNo(Long.valueOf(batchNo), InsteadPayTypeEnum.Paying);
                //更新批次信息
                bankTransferBatchDAO.updateBatchTranStatus(Long.valueOf(batchNo), "05");
                
                //记录转账批次号和渠道方返回批次号
                BtchpmtResponseBean responseBean = (BtchpmtResponseBean) resultBean.getResultObj();
                PojoTxnsInsteadPay txnsInsteadPay = new PojoTxnsInsteadPay(batchNo+"", "92000002", "02", batchNo+"", responseBean.getSerialNum(), new Date(), null, "01", "");
                txnsInsteadPayDAO.saveA(txnsInsteadPay);
            }else{
            	//交易失败全部回滚
            	bankTransferDataDAO.updateTransDataStatusByBatchNo(Long.valueOf(batchNo), InsteadPayTypeEnum.INITI);
            	//更新批次的状态
            	bankTransferBatchDAO.rollbackBankTrans(Long.valueOf(batchNo));
            	
            }
            
            
            
            
            
            
            
            resultBean = new ResultBean("success");
        }  catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
           
        }
        return resultBean;
	}

	/**
	 *
	 * @param resultList
	 * @return
	 */
	@SuppressWarnings("unused")
	@Override
	@Transactional
	public boolean dealWithResult(String serialNum,List<BtchpmtResultBean> resultList) {
		 int i = 0;
         long sumItems = 0;//总笔数
         long succItems = 0;//成功笔数
         long sumAmt = 0;//总金额
         long succAmt = 0;//成功金额
         List<PojoBankTransferData> resultLists = new ArrayList<PojoBankTransferData>();
         for(BtchpmtResultBean itemBean : resultList){
             PojoBankTransferData resultBean = new PojoBankTransferData();
             resultBean.setBankTranResNo(itemBean.getSerialNum());
             resultBean.setAccNo(itemBean.getBankAccount());
             resultBean.setAccName(itemBean.getBankAccountName());
             resultBean.setTranAmt(Long.valueOf(itemBean.getAmount()));
             resultBean.setResCode(itemBean.getStatus());
             resultBean.setResInfo(StatusEnum.fromValue(itemBean.getStatus()).getMessage());
             resultLists.add(resultBean);
         }
         
         
         long sumItems_self = 0;//总笔数
         long succItems_self = 0;//成功笔数
         long sumAmt_self = 0;//总金额
         long succAmt_self = 0;//成功金额
         long failItems = 0;
         long failAmt = 0;
         for(PojoBankTransferData bean : resultLists){
        	 if(StatusEnum.PR02==StatusEnum.fromValue(bean.getResCode())){
        		 succItems_self++;
                 succAmt_self+=bean.getTranAmt();
        	 }else{
        		 failItems++;
                 failAmt+=bean.getTranAmt();
        	 }
             sumItems_self++;
             sumAmt_self += bean.getTranAmt();
         }
        
         //数据没有问题,批量更新划拨明细数据
         //更新转账明细数据
         bankTransferDataDAO.batchUpdateBossPayTransData(resultLists);
         //更新对应业务的数据，调用业务处理接口
         dealWithBusiData(resultLists);
         PojoTxnsInsteadPay txnsInsteadPay = txnsInsteadPayDAO.getByResponseFileName(serialNum);
         //更新转账批次数据
         PojoBankTransferBatch transferBatch = bankTransferBatchDAO.getByBankTranBatchNo(Long.valueOf(txnsInsteadPay.getInsteadPayNo()));
         if(transferBatch!=null){
             transferBatch.setSuccessCount(succItems);
             transferBatch.setSuccessAmt(succAmt);
             transferBatch.setFailCount(failItems);
             transferBatch.setFailAmt(failAmt);
             if(failItems>0){
             	transferBatch.setTranStatus("02");
             }else if(succItems==0){
             	transferBatch.setTranStatus("04");
             }else{
             	transferBatch.setTranStatus("03");
             }
             bankTransferBatchDAO.update(transferBatch);
         }
         txnsInsteadPay.setStatus("00");
         txnsInsteadPayDAO.update(txnsInsteadPay);
		return false;
	}

	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void dealWithBusiData(List<PojoBankTransferData> transferDataList){
    	for (PojoBankTransferData data : transferDataList) {
            PojoBankTransferData transferData = bankTransferDataDAO.getTransferDataByTranId(data.getBankTranResNo());
            UpdateData updateData = new UpdateData();
            updateData.setTxnSeqNo(transferData.getTranData().getTxnseqno());
            updateData.setResultCode("S".equalsIgnoreCase(data.getResType())? "00": "03");
            updateData.setResultMessage("S".equalsIgnoreCase(data.getResType())? "交易成功": transferData.getResInfo());
            updateData.setChannelCode(transferData.getBankTranBatch().getChannel().getBankChannelCode());
            ObserverListService service  = ObserverListService.getInstance();
            service.notify(updateData, transferData.getTranData().getBusiType());
    	}
    }
	

}
