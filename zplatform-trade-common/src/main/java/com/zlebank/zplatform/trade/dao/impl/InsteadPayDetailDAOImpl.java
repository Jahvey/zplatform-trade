/* 
 * InsteadPayDetailDAOImpl.java  
 * 
 * version TODO
 *
 * 2015年11月24日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.commons.dao.impl.AbstractPagedQueryDAOImpl;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.trade.bean.InsteadPayDetailQuery;
import com.zlebank.zplatform.trade.bean.enums.InsteadPayDetailStatusEnum;
import com.zlebank.zplatform.trade.dao.InsteadPayDetailDAO;
import com.zlebank.zplatform.trade.model.PojoInsteadPayDetail;

/**
 * Class Description
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月24日 下午1:49:02
 * @since
 */
@Repository("insteadPayDetailDAO")
public class InsteadPayDetailDAOImpl
        extends
            AbstractPagedQueryDAOImpl<PojoInsteadPayDetail, InsteadPayDetailQuery>
        implements
            InsteadPayDetailDAO {

    /**
     * 通过批次号得到批次明细
     * 
     * @param batchNo
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PojoInsteadPayDetail> getByBatchDetail(String batchNo) {
        Criteria crite = this.getSession().createCriteria(
                PojoInsteadPayDetail.class);
        crite.add(Restrictions.eq("batchNo", batchNo));
        return crite.list();
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void updateBatchDetailResult(PojoInsteadPayDetail insteadPayDetail) {
        // TODO Auto-generated method stub
        try {
            String hql = "update PojoInsteadPayDetail set status = ?,channelCode = ?,batchFileNo = ?,respCode = ?,respMsg = ? where merId = ? and orderId = ?";
            Session session = getSession();
            Query query = session.createQuery(hql);
            query.setString(0, insteadPayDetail.getStatus());
            query.setString(1, insteadPayDetail.getChannelCode());
            query.setString(2, insteadPayDetail.getBatchFileNo());
            query.setString(3, insteadPayDetail.getRespCode());
            query.setString(4, insteadPayDetail.getRespMsg());
            query.setString(5, insteadPayDetail.getMerId());
            query.setString(6, insteadPayDetail.getOrderId());
            query.executeUpdate();
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     *
     * @param e
     * @return
     */
    @Override
    protected Criteria buildCriteria(InsteadPayDetailQuery e) {
        Criteria crite = this.getSession().createCriteria(
                PojoInsteadPayDetail.class);
        crite.setFetchMode("insteadPayBatch", FetchMode.JOIN);
        if (e != null) {
            if (StringUtil.isNotEmpty(e.getAccNo())) {
                crite.add(Restrictions.eq("accNo", e.getAccNo()));
            }
            if (StringUtil.isNotEmpty(e.getAccType())) {
                crite.add(Restrictions.eq("accType", e.getAccType()));
            }
            if (StringUtil.isNotEmpty(e.getMerId())) {
                crite.add(Restrictions.eq("merId", e.getMerId()));
            }
            if (StringUtil.isNotEmpty(e.getOrderId())) {
                crite.add(Restrictions.eq("orderId", e.getOrderId()));
            }
            if (StringUtil.isNotEmpty(e.getBatchNo())) {
                crite.add(Restrictions.eq("batchNo",   Long.valueOf(e.getBatchNo())));
            }
            if (StringUtil.isNotEmpty(e.getInsteadPayBatchSeqNo())) {
                crite.add(Restrictions.eq("insteadPayBatch.insteadPayBatchSeqNo",  e.getInsteadPayBatchSeqNo()));
            }
            if (StringUtil.isNotEmpty(e.getStatus())) {
                crite.add(Restrictions.eq("status",e.getStatus()));
            }
            if (StringUtil.isNotEmpty(e.getBatchId())) {
                crite.add(Restrictions.eq("insteadPayBatch.id", Long.parseLong(e.getBatchId())));
            }
            if (StringUtil.isNotEmpty(e.getImFileName())) {
                crite.add(Restrictions.eq("insteadPayBatch.originalFileName", e.getImFileName()));
            }
            if (e.getStatusList() != null
                    && e.getStatusList().size() != 0) {
                crite.add(Restrictions.in("status", e.getStatusList()));
            }
            if (StringUtil.isNotEmpty(e.getInsteadPayDataSeqNo())) {
                crite.add(Restrictions.eq("insteadPayDataSeqNo", e.getInsteadPayDataSeqNo()));
            }
        }
        crite.addOrder(Order.desc("intime"));
        return crite;
        }

    /**
     *
     * @param orderId
     * @return
     */
    @Override
    public PojoInsteadPayDetail getDetailByTxnseqno(String txnseqno,String status) {
        Criteria crite = this.getSession().createCriteria(
                PojoInsteadPayDetail.class);
        crite.add(Restrictions.eq("txnseqno", txnseqno));
        crite.add(Restrictions.eq("status", status));
        return (PojoInsteadPayDetail)crite.uniqueResult();
    }
    /**
     *
     * @param orderId
     * @return
     */
    @Override
    public PojoInsteadPayDetail getDetailByTxnseqno(String txnseqno) {
        Criteria crite = this.getSession().createCriteria(
                PojoInsteadPayDetail.class);
        crite.add(Restrictions.eq("txnseqno", txnseqno));
        return (PojoInsteadPayDetail)crite.uniqueResult();
    }

        /**
         * 通过批次ID得到批次明细
         * @param batchId
         * @return List
         */
        @SuppressWarnings("unchecked")
        @Override
        public List<PojoInsteadPayDetail> getBatchDetailByBatchId(Long batchId) {
            Criteria crite = this.getSession().createCriteria(
                    PojoInsteadPayDetail.class);
            crite.add(Restrictions.eq("insteadPayBatch.id", batchId));
            crite.add(Restrictions.eq("status", InsteadPayDetailStatusEnum.WAIT_INSTEAD_APPROVE.getCode()));
            return crite.list();
        }

        /**
         * 通过代付流水ID得到代付明细
         * @param ids
         * @return
         */
        @SuppressWarnings("unchecked")
        @Override
        public List<PojoInsteadPayDetail> getBatchDetailByIds(List<Long> ids) {
            Criteria crite = this.getSession().createCriteria(
                    PojoInsteadPayDetail.class);
            crite.add(Restrictions.in("id", ids));
            crite.add(Restrictions.eq("status", InsteadPayDetailStatusEnum.WAIT_INSTEAD_APPROVE.getCode()));
            return crite.list();
        }

        /**
         *通过代付判断是否已经处理完毕
         * @param batchId
         * @return
         */
        @Override
        public boolean isBatchProcessFinished(Long batchId) {
            Criteria crite = this.getSession().createCriteria(
                    PojoInsteadPayDetail.class);
            crite.add(Restrictions.eq("insteadPayBatch.id", batchId));
            String[] codes = new String[]{"00", "01","02","03","04","09"};
            crite.add(Restrictions.not(Restrictions.in("respCode", codes)));
            crite.setProjection(Projections.rowCount());  
            int count = Integer.parseInt(crite.uniqueResult().toString());
            return count > 0 ? false : true;
        }
 }
