/* 
 * InsteadPayBatchDAOImpl.java  
 * 
 * version TODO
 *
 * 2015年11月24日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;

import java.sql.Date;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.commons.dao.impl.AbstractPagedQueryDAOImpl;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.trade.bean.InsteadPayBatchQuery;
import com.zlebank.zplatform.trade.dao.InsteadPayBatchDAO;
import com.zlebank.zplatform.trade.model.PojoInsteadPayBatch;

/**
 * Class Description
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月24日 下午1:48:00
 * @since 
 */
@Repository("insteadPayBatchDAO")
public class InsteadPayBatchDAOImpl extends AbstractPagedQueryDAOImpl<PojoInsteadPayBatch, InsteadPayBatchQuery> implements InsteadPayBatchDAO {

    /**
     * 通过批次号得到批次
     * @param batchNo
     * @return
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public PojoInsteadPayBatch getByBatchNo(String batchNo, String txnTime) {
        Criteria crite= this.getSession().createCriteria(PojoInsteadPayBatch.class);
        crite.add(Restrictions.eq("batchNo", Long.parseLong(batchNo)));
        crite.add(Restrictions.eq("txnTime", txnTime));
        return (PojoInsteadPayBatch) crite.uniqueResult();
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Throwable.class)
    public void updateBatchResult(String batchNo, String status) {
        try {
            String hql = " update PojoInsteadPayBatch set status = ? where id = ? ";
            Query query = getSession().createQuery(hql);
            query.setString(0, status);
            query.setString(1, batchNo);
            query.executeUpdate();
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Throwable.class)
    public PojoInsteadPayBatch getInsteadPayBatchByBatchNo(String batchNo,
            String merchId) {
        Criteria crite= this.getSession().createCriteria(PojoInsteadPayBatch.class);
        crite.add(Restrictions.eq("batchNo", Long.parseLong(batchNo)));
        crite.add(Restrictions.eq("merId", merchId));
        return (PojoInsteadPayBatch) crite.uniqueResult();
    }

    /**
     *
     *  代付批次查询
     * @param query
     * @return
     */
    @Override
    protected Criteria buildCriteria(InsteadPayBatchQuery query) {
        Criteria crite= this.getSession().createCriteria(PojoInsteadPayBatch.class);
        if (query != null) {
            if(StringUtil.isNotEmpty(query.getBatchNo())) {
                crite.add(Restrictions.eq("insteadPayBatchSeqNo", query.getBatchNo()));
            }
            if(StringUtil.isNotEmpty(query.getBeginDate())) {
                crite.add(Restrictions.ge("intime", Date.valueOf(query.getBeginDate())));
            }
            if(StringUtil.isNotEmpty(query.getEndDate())) {
                crite.add(Restrictions.le("intime", Date.valueOf(query.getEndDate())));
            }
            if (query.getStatusList() != null
                    && query.getStatusList().size() != 0) {
                crite.add(Restrictions.in("status", query.getStatusList()));
            }
            if(StringUtil.isNotEmpty(query.getOrderNo())){
            	crite.setFetchMode("details",FetchMode.JOIN); 
            	crite.createAlias("details","details").add(Restrictions.eq("details.orderId",query.getOrderNo())); 
            }
        }
        crite.addOrder(Order.desc("intime"));
        return crite;
    }

    /**
     * 通过批次ID得到批次
     * @param batchId
     * @return
     */
    @Override
    public PojoInsteadPayBatch getByBatchId(Long batchId) {
        Criteria crite= this.getSession().createCriteria(PojoInsteadPayBatch.class);
        crite.add(Restrictions.eq("id", batchId));
        return (PojoInsteadPayBatch) crite.uniqueResult();
    }

}
