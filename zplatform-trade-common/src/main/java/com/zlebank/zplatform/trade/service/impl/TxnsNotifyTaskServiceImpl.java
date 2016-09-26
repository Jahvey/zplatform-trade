/* 
 * TxnsNotifyTaskServiceImpl.java  
 * 
 * version TODO
 *
 * 2015年11月9日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.trade.dao.ITxnsNotifyTaskDAO;
import com.zlebank.zplatform.trade.dao.ITxnsOrderinfoDAO;
import com.zlebank.zplatform.trade.model.TxnsNotifyTaskModel;
import com.zlebank.zplatform.trade.service.ITxnsNotifyTaskService;
import com.zlebank.zplatform.trade.service.base.BaseServiceImpl;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月9日 上午9:53:36
 * @since 
 */
@Service("txnsNotifyTaskService")
public class TxnsNotifyTaskServiceImpl extends BaseServiceImpl<TxnsNotifyTaskModel, Long> implements ITxnsNotifyTaskService{

    @Autowired
    private ITxnsNotifyTaskDAO txnsNotifyTaskDAO;
    @Autowired
    private ITxnsOrderinfoDAO txnsOrderinfoDAO;
    /**
     *
     * @return
     */
    @Override
    public Session getSession() {
        // TODO Auto-generated method stub
        return txnsNotifyTaskDAO.getSession();
    }
    
    
    
    /**
     *
     * @param task
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class) 
    public void saveTask(TxnsNotifyTaskModel task) {
        // TODO Auto-generated method stub
    	TxnsNotifyTaskModel asyncNotifyTask = getAsyncNotifyTask(task.getTxnseqno());
    	if(asyncNotifyTask!=null){
    		if("00".equals(task.getSendStatus())){
                updateOrderSync(task.getTxnseqno());
            }
    	}else{
    		super.save(task);
    	}
    }
    /**
     *
     * @param task
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class) 
    public boolean updateTask(TxnsNotifyTaskModel task) {
        // TODO Auto-generated method stub
        TxnsNotifyTaskModel  taskModel = super.getUniqueByHQL("from TxnsNotifyTaskModel where txnseqno=? and memberId=? and taskType=?", new Object[]{task.getTxnseqno(),task.getMemberId(),"1"});
        taskModel.setSendStatus(task.getSendStatus());
        taskModel.setSendTimes(task.getSendTimes());
        if("00".equals(task.getSendStatus())){
            updateOrderSync(task.getTxnseqno());
        }
        super.update(taskModel);
        if(taskModel.getMaxSendTimes()==taskModel.getSendTimes()){
            return true;
        }
        return false;
    }
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class) 
    public List<TxnsNotifyTaskModel> findTaskByTxnseqno(String txnseqno,String memberId){
        return (List<TxnsNotifyTaskModel>) super.queryByHQL("from TxnsNotifyTaskModel where txnseqno=? and memberId=? and taskType=?", new Object[]{txnseqno,memberId,"1"});
    }
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class) 
    public void updateOrderSync(String txnseqno){
        super.updateByHQL("update TxnsOrderinfoModel set syncnotify=? where relatetradetxn = ? ", new Object[]{"00",txnseqno});
    }



	/**
	 *
	 * @param txnseqno
	 * @return
	 */
	@Override
	@Transactional(readOnly=true)
	public TxnsNotifyTaskModel getAsyncNotifyTask(String txnseqno) {
		return super.getUniqueByHQL("from TxnsNotifyTaskModel where txnseqno=? and taskType=?", new Object[]{txnseqno,"1"});
	}

}
