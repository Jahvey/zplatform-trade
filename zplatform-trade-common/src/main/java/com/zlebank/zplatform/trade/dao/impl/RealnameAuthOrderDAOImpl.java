/* 
 * RealnameAuthDAOImpl.java  
 * 
 * version v1.0
 *
 * 2015年11月24日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.RealnameAuthOrderDAO;
import com.zlebank.zplatform.trade.model.PojoRealnameAuthOrder;


/**
 * 实名认证订单DAO
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月24日 下午12:34:32
 * @since 
 */
@Repository
public class RealnameAuthOrderDAOImpl extends HibernateBaseDAOImpl<PojoRealnameAuthOrder> implements RealnameAuthOrderDAO {

    public PojoRealnameAuthOrder get(long id) {
        return (PojoRealnameAuthOrder) getSession().get(PojoRealnameAuthOrder.class, id);
    }

    /**
     * 根据订单号和和发送时间得到实名认证信息
     * @param orderId
     * @param txnTime
     * @return
     */
    @Override
    public PojoRealnameAuthOrder getByOrderIdAndTxnTime(String orderId, String txnTime) {
        Criteria crite= this.getSession().createCriteria(PojoRealnameAuthOrder.class);
        crite.add(Restrictions.eq("orderId", orderId));
        crite.add(Restrictions.eq("txnTime", txnTime));
        crite.add(Restrictions.eq("status", "00"));
        return (PojoRealnameAuthOrder) crite.uniqueResult();
    }

    @Override
    public PojoRealnameAuthOrder updateWithCommit(PojoRealnameAuthOrder t) {
        getSession().setFlushMode(FlushMode.MANUAL);
        Transaction transaction = getSession().beginTransaction();
        getSession().update(t);
        transaction.commit();
        getSession().flush();
        return t;
    }

    /**
     *
     * @param merId
     * @param orderId
     * @param txnTime
     * @return
     */
    @Override
    public PojoRealnameAuthOrder isRealNameAuth(String merId, String accNo, String accName) {
        Criteria crite= this.getSession().createCriteria(PojoRealnameAuthOrder.class);
        crite.add(Restrictions.eq("merId", merId));
        crite.add(Restrictions.eq("cardNo", accNo));
        crite.add(Restrictions.eq("customerNm", accName));
        crite.add(Restrictions.eq("status", "00"));
        List<PojoRealnameAuthOrder> orders = (List<PojoRealnameAuthOrder>) crite.list();
        if (orders == null || orders.size() == 0) {
            return null;
        } else  {
            return orders.get(0);
        }
    }
}
