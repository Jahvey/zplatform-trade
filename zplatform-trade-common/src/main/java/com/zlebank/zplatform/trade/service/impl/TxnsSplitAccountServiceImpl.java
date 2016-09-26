/* 
 * TxnsSplitAccountServiceImpl.java  
 * 
 * version TODO
 *
 * 2015年9月11日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.gateway.SplitAcctBean;
import com.zlebank.zplatform.trade.dao.ITxnsSplitAccountDAO;
import com.zlebank.zplatform.trade.model.TxnsSplitAccountModel;
import com.zlebank.zplatform.trade.service.ITxnsSplitAccountService;
import com.zlebank.zplatform.trade.service.base.BaseServiceImpl;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月11日 下午7:46:59
 * @since 
 */
@Service("txnsSplitAccountService")
public class TxnsSplitAccountServiceImpl extends BaseServiceImpl<TxnsSplitAccountModel, Long> implements ITxnsSplitAccountService{
    @Autowired
    private ITxnsSplitAccountDAO txnsSplitAccountDAO;

    /**
     *
     * @return
     */
    @Override
    public Session getSession() {
        // TODO Auto-generated method stub
        return txnsSplitAccountDAO.getSession();
    }
    
    public List<TxnsSplitAccountModel> getSplitAccountByTxnseqno(String txnseqno){
        return (List<TxnsSplitAccountModel>) super.queryByHQL("from TxnsSplitAccountModel where txnseqno = ?", new Object[]{txnseqno});
    }
    
    @Transactional
    public ResultBean saveTxnsSplitAccount(TxnsSplitAccountModel txnsSplitAccount){
        ResultBean result = null;
        try {
            super.save(txnsSplitAccount);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result = new ResultBean("RC99", "分账信息保存失败");
        }
        return result;
    }
    @Transactional
    public Long saveTxnsSplitAccount(List<SplitAcctBean>  splitAcctList,String txnseqno){
        List<TxnsSplitAccountModel> saveList = new ArrayList<TxnsSplitAccountModel>();
        Long commAmt = 0L;
       for(SplitAcctBean bean : splitAcctList){
           TxnsSplitAccountModel txnsSplitAccount = new TxnsSplitAccountModel(bean, txnseqno);
           saveList.add(txnsSplitAccount);
           commAmt += Long.valueOf(bean.getCommAmt());
           super.save(txnsSplitAccount);
       }
       //super.save(saveList);
       return commAmt;
    }
}
