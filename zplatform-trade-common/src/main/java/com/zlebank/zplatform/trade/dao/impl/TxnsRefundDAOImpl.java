/* 
 * TxnsRefundDAOImpl.java  
 * 
 * version TODO
 *
 * 2015年10月22日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.ITxnsRefundDAO;
import com.zlebank.zplatform.trade.model.TxnsRefundModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年10月22日 下午3:47:30
 * @since 
 */
@Repository("txnsRefundDAO")
public class TxnsRefundDAOImpl extends HibernateBaseDAOImpl<TxnsRefundModel> implements ITxnsRefundDAO{

    public Session getSession(){
        return super.getSession();
    }
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateRefund(TxnsRefundModel refundOrder) {
        // TODO Auto-generated method stub
        String sql = "update TxnsRefundModel set status = ? where refundorderno = ? ";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, refundOrder.getStatus());
        query.setParameter(1, refundOrder.getRefundorderno());
        query.executeUpdate();
    }
    
}
