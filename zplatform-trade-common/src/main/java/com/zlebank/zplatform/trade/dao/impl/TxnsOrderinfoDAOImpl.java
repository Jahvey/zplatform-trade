/* 
 * TxnsOrderinfoDAOImpl.java  
 * 
 * version TODO
 *
 * 2015年8月29日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.ITxnsOrderinfoDAO;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.model.TxnsOrderinfoModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年8月29日 下午3:40:27
 * @since 
 */
@Repository("txnsOrderinfoDAO")
public class TxnsOrderinfoDAOImpl extends HibernateBaseDAOImpl<TxnsOrderinfoModel> implements ITxnsOrderinfoDAO{
	
    public Session getSession() {
        // TODO Auto-generated method stub
        return super.getSession();
    }
    @Transactional(propagation=Propagation.REQUIRED)
    public void updateOrderToFail(String orderNo,String merchId) {
        TxnsOrderinfoModel orderinfo = getOrderinfoByOrderNo(orderNo,merchId);
        if("02".equals(orderinfo.getStatus())){
            String hql = "update TxnsOrderinfoModel set status = ? where orderno=? and firmemberno = ? ";
            Session session = getSession();
            Query query = session.createQuery(hql);
            query.setString(0, "03");
            query.setString(1, orderNo);
            query.setString(2, merchId);
            int rows =  query.executeUpdate();
        }
    }
    
    
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateOrderToFail(String txnseqno) {
        //TxnsOrderinfoModel orderinfo = getOrderByTxnseqno(txnseqno);
        //if("02".equals(orderinfo.getStatus())){
            String hql = "update TxnsOrderinfoModel set status = ? where relatetradetxn = ? ";
            Session session = getSession();
            Query query = session.createQuery(hql);
            query.setString(0, "03");
            query.setString(1, txnseqno);
            int rows =  query.executeUpdate();
       // }
    }
    
    @Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Throwable.class)
    public void updateOrderToSuccess(String txnseqno) {
        String hql = "update TxnsOrderinfoModel set status = ? where relatetradetxn = ? ";
        Session session = getSession();
        Query query = session.createQuery(hql);
        query.setString(0, "00");
        query.setString(1, txnseqno);
        int rows =  query.executeUpdate();
    }
    
    @Transactional(propagation=Propagation.REQUIRED)
    public TxnsOrderinfoModel getOrderinfoByOrderNo(String orderNo,String merchId) {
        String hql = "from TxnsOrderinfoModel where orderno = ? and firmemberno = ? ";
        Session session = getSession();
        Query query = session.createQuery(hql);
        query.setString(0, orderNo);
        query.setString(1, merchId);
        return (TxnsOrderinfoModel) query.list().get(0);
    }
    
    @Transactional(propagation=Propagation.REQUIRED)
    public TxnsOrderinfoModel getOrderinfoByOrderNoAndMemberId(String orderNo,String merchId) {
        String hql = "from TxnsOrderinfoModel where orderno = ? and secmemberno = ? ";
        Session session = getSession();
        Query query = session.createQuery(hql);
        query.setString(0, orderNo);
        query.setString(1, merchId);
        return (TxnsOrderinfoModel) query.uniqueResult();
    }
    @Transactional(propagation=Propagation.REQUIRED)
    public void updateOrderinfo(TxnsOrderinfoModel orderinfo){
        getSession().update(orderinfo);
    }
	/**
	 *
	 * @param tn
	 * @return
	 */
	@Override
	@Transactional(readOnly=true)
	public TxnsOrderinfoModel getOrderByTN(String tn) {
		String hql = "from TxnsOrderinfoModel where tn = ? ";
        Session session = getSession();
        Query query = session.createQuery(hql);
        query.setString(0, tn);
        return (TxnsOrderinfoModel) query.uniqueResult();
	}
	/**
	 *
	 * @param txnseqno
	 * @return
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public TxnsOrderinfoModel getOrderByTxnseqno(String txnseqno) {
		String hql = "from TxnsOrderinfoModel where relatetradetxn = ? ";
        Session session = getSession();
        Query query = session.createQuery(hql);
        query.setString(0, txnseqno);
        return (TxnsOrderinfoModel) query.list().get(0);
	}
	/**
	 *
	 * @param tn
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void updateOrderToSuccessByTN(String tn) {
		// TODO Auto-generated method stub
		String hql = "update TxnsOrderinfoModel set status = ? where tn = ? ";
        Session session = getSession();
        Query query = session.createQuery(hql);
        query.setString(0, "00");
        query.setString(1, tn);
        int rows =  query.executeUpdate();
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void saveOrderInfo(TxnsOrderinfoModel orderinfo){
		saveA(orderinfo);
	}
	/**
	 *
	 * @param tn
	 * @throws TradeException 
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void updateOrderToPay(String txnseqno) throws TradeException {
		// TODO Auto-generated method stub
		Session session = getSession();
		Query query = session.createQuery("from TxnsOrderinfoModel where relatetradetxn=? and (status=? or status = ?)");
		query.setString(0, txnseqno);
		query.setString(1, "01");
		query.setString(2, "03" );
		TxnsOrderinfoModel orderinfo = (TxnsOrderinfoModel) query.uniqueResult();
		if (orderinfo == null) {
			throw new TradeException("T010");
		}
		query = null;
		String hql = "update TxnsOrderinfoModel set status = ? where relatetradetxn=? and (status=? or status = ?) ";
        query = session.createQuery(hql);
        query.setString(0, "02");
        query.setString(1, txnseqno);
        query.setString(2, "01");
        query.setString(3, "03");
        int rows = query.executeUpdate();
        if (rows != 1) {
			throw new TradeException("T011");
		}
	}
}
