/* 
 * TxnsWithdrawServiceImpl.java  
 * 
 * version TODO
 *
 * 2015年10月22日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.trade.dao.ITxnsWithdrawDAO;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsWithdrawModel;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.ITxnsWithdrawService;
import com.zlebank.zplatform.trade.service.base.BaseServiceImpl;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年10月22日 下午5:47:27
 * @since 
 */
@Service("txnsWithdrawService")
public class TxnsWithdrawServiceImpl extends BaseServiceImpl<TxnsWithdrawModel, Long> implements ITxnsWithdrawService{

    @Autowired
    private ITxnsWithdrawDAO txnsWithdrawDAO;
    @Autowired
    private ITxnsLogService txnsLogService;
    
    /**
     *
     * @return
     */
    @Override
    public Session getSession() {
        // TODO Auto-generated method stub
        return txnsWithdrawDAO.getSession();
    }
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void saveInitiativeSettlWithdraw(List<Map<String, Object>> resultList){
        if(resultList!=null){
            for(int i=0;i<resultList.size();i++){
                Map<String, Object> valueMap = resultList.get(i);
                TxnsWithdrawModel withdraw = new TxnsWithdrawModel();
            }
        }
    }
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void saveWithdraw(TxnsWithdrawModel withdraw){
    	String txnseqno = withdraw.getTexnseqno();
    	TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
    	txnsLog.setTxnfee(txnsLogService.getTxnFee(txnsLog));
    	txnsLogService.update(txnsLog);
    	withdraw.setFee(txnsLog.getTxnfee());
        super.save(withdraw);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateWithdrawResult(TxnsWithdrawModel withdraw){
        String hql = "update TxnsWithdrawModel set finishtime = ?,status = ?,retcode = ?,retinfo = ? where withdraworderno = ? and memberid = ? ";
        super.updateByHQL(hql, new Object[]{withdraw.getFinishtime(),withdraw.getStatus(),withdraw.getRetcode(),withdraw.getRetinfo(),withdraw.getWithdraworderno(),withdraw.getMemberid()});
    }
}
