package com.zlebank.zplatform.trade.dao.impl;

import java.awt.color.CMMException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.commons.bean.TransferBatchQuery;
import com.zlebank.zplatform.commons.dao.impl.AbstractPagedQueryDAOImpl;
import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.trade.bean.enums.BankTransferBatchOpenStatusEnum;
import com.zlebank.zplatform.trade.bean.page.QueryTransferBean;
import com.zlebank.zplatform.trade.dao.BankTransferBatchDAO;
import com.zlebank.zplatform.trade.model.PojoBankTransferBatch;

@Repository("bankTransferBatchDAO")
public class BankTransferBatchDAOImpl
        extends
            AbstractPagedQueryDAOImpl<PojoBankTransferBatch, TransferBatchQuery>
        implements
            BankTransferBatchDAO {
    private static final Log log = LogFactory
            .getLog(BankTransferBatchDAOImpl.class);

    /**
     * 通过批次号查找批次信息
     * 
     * @param batchno
     * @return
     */
    @Override
    @Transactional
    public PojoBankTransferBatch getByBatchNo(String batchno) {
        String queryString = "from PojoBankTransferBatch where batchno = ? ";
        try {
            log.info("queryString:" + queryString);
            Query query = getSession().createQuery(queryString);
            query.setParameter(0, batchno);
            return (PojoBankTransferBatch) query.uniqueResult();

        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new CMMException("M001");
        }
    }

    @Override
    @Transactional
    public void updateBatchToTransfer(PojoBankTransferBatch transferBatch) {
        // TODO Auto-generated method stub
        try {
            String hql = "update PojoBankTransferBatch set status = ?,transfertime = ?,requestfilename = ?,responsefilename = ? where batchno= ? ";
            Session session = getSession();
            Query query = session.createQuery(hql);
            query.setParameter(0, transferBatch.getStatus());
            /*
             * query.setParameter(1, transferBatch.getTransfertime());
             * query.setParameter(2, transferBatch.getRequestfilename());
             * query.setParameter(3, transferBatch.getResponsefilename());
             * query.setParameter(4, transferBatch.getBatchno());
             */
            query.executeUpdate();
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new CMMException("M002");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public PojoBankTransferBatch getByReqestFileName(String fileName) {
        List<PojoBankTransferBatch> result = null;
        String queryString = "from PojoBankTransferBatch where requestfilename = ? ";
        try {
            log.info("queryString:" + queryString);
            Query query = getSession().createQuery(queryString);
            query.setParameter(0, fileName);
            result = query.list();
            if (result.size() > 0) {
                return result.get(0);
            }
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new CMMException("M001");
        }
        return null;
    }

	/**
	 * 更新批次数据
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateTransferBatch(PojoBankTransferBatch transferBatch) {
		try {
			getSession().update(transferBatch);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CMMException("M002");
		}
	}

    /**
     * 
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<PojoBankTransferBatch> findWaitAccountingTransferBatch() {
        try {
            String hql = "from PojoBankTransferBatch where accstatus = ? ";
            Query query = getSession().createQuery(hql);
            query.setString(0, "01");
            return query.list();
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new CMMException("M001");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public int validateBatchResult(String insteadpaybatchno) {
        try {
            String hql = "from PojoBankTransferBatch where status <> ? and insteadpaybatchno = ? ";
            Query query = getSession().createQuery(hql);
            query.setString(0, "00");
            query.setString(1, insteadpaybatchno);
            return query.list().size();
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new CMMException("M001");
        }
    }


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void updateBatchTranStatus(Long tid, String tranStatus) {
		try {
			String hql = "update PojoBankTransferBatch set tranStatus = ? where tid = ? ";
			Query query = getSession().createQuery(hql);
			query.setParameter(0, tranStatus);
			query.setParameter(1, tid);
			query.executeUpdate();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CMMException("M002");
		}
	}

    /*@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public void updateAccountingResult(String batchno, AccStatusEnum accStatus) {
        try {
            String hql = "update PojoBankTransferBatch set accstatus = ? where batchno = ? ";
            Session session = getSession();
            Query query = session.createQuery(hql);
            query.setParameter(0, accStatus.getCode());
            query.setParameter(1, batchno);
            query.executeUpdate();
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new CMMException("M002");
        }
    }*/


    @SuppressWarnings("unchecked")
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public List<PojoBankTransferBatch> findByInsteadpaybatchno(String insteadpaybatchno) {
        try {
            String hql = "from PojoBankTransferBatch where insteadpaybatchno = ? ";
            Query query = getSession().createQuery(hql);
            query.setString(0, insteadpaybatchno);
            return query.list();
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new CMMException("M001");
        }
    }

    /**
     *
     * @param e
     * @return
     */
    @Override
    protected Criteria buildCriteria(TransferBatchQuery e) {
        Criteria crite = this.getSession().createCriteria(
                PojoBankTransferBatch.class);
        if (e != null) {
            if (StringUtil.isNotEmpty(e.getAccstatus())) {
                crite.add(Restrictions.eq("accstatus", e.getAccstatus()));
            }
            if (StringUtil.isNotEmpty(e.getBatchno())) {
                crite.add(Restrictions.eq("batchno", e.getBatchno()));
            }
            if (StringUtil.isNotEmpty(e.getStatus())) {
                crite.add(Restrictions.eq("status", e.getStatus()));
            }
            if (StringUtil.isNotEmpty(e.getTransfertype())) {
                crite.add(Restrictions.eq("transfertype", e.getTransfertype()));
            }

        }
        crite.addOrder(Order.asc("createtime"));
        return crite;
    }

    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public PojoBankTransferBatch getByResponseFileName(String fileName) {
        List<PojoBankTransferBatch> result = null;
        String queryString = "from PojoBankTransferBatch where responsefilename = ? ";
        try {
            log.info("queryString:" + queryString);
            Query query = getSession().createQuery(queryString);
            query.setParameter(0, fileName);
            result = query.list();
            if (result.size() > 0) {
                return result.get(0);
            }
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new CMMException("M001");
        }
        return null;
    }

    /**
     * 通过渠道返回相应的批次号
     * 
     * @param channelCode
     *            渠道号
     * @return
     */
    @Override
    public PojoBankTransferBatch getByChannelId(Long channelCode) {
        Criteria crite = this.getSession().createCriteria(
                PojoBankTransferBatch.class);
        crite.add(Restrictions.eq("channel.id", channelCode));
        crite.addOrder(Order.desc("tid"));
        crite.setFirstResult(0);
        crite.setMaxResults(1);
        return (PojoBankTransferBatch) crite.uniqueResult();

    }


	@Override
	@Transactional(readOnly=true)
	public Map<String, Object> queryBankTransferByPage(
			QueryTransferBean queryTransferBean, int page, int pageSize) throws ParseException {
		StringBuffer sqlBuffer = new StringBuffer("from PojoBankTransferBatch where 1=1");
		StringBuffer sqlCountBuffer = new StringBuffer("select count(*) from PojoBankTransferBatch where 1=1 ");
		List<Object> parameterList = new ArrayList<Object>();
		
		if(queryTransferBean!=null){
			if(StringUtil.isNotEmpty(queryTransferBean.getBatchNo())){
				sqlBuffer.append(" and bankTranBatchNo = ? ");
				sqlCountBuffer.append(" and bankTranBatchNo = ? ");
				parameterList.add(queryTransferBean.getBatchNo());
			}
			if(StringUtil.isNotEmpty(queryTransferBean.getStatus())){
				sqlBuffer.append(" and status = ? ");
				sqlCountBuffer.append(" and status = ? ");
				parameterList.add(queryTransferBean.getStatus());
			}
			if (StringUtil.isNotEmpty(queryTransferBean.getBeginDate())) {
                sqlBuffer.append(" and applyTime >= ? ");
                sqlCountBuffer.append(" and applyTime >= ? ");
                parameterList.add(DateUtil.convertToDate(queryTransferBean.getBeginDate(), "yyyy-MM-dd"));
            }
            if (StringUtil.isNotEmpty(queryTransferBean.getEndDate())) {
                sqlBuffer.append(" and applyTime <= ? ");
                sqlCountBuffer.append(" and applyTime <= ? ");
                parameterList.add(DateUtil.convertToDate(queryTransferBean.getEndDate(), "yyyy-MM-dd"));
            }
            if(StringUtil.isNotEmpty(queryTransferBean.getOpenStatus())){
            	sqlBuffer.append(" and openStatus = ? ");
                sqlCountBuffer.append(" and openStatus = ? ");
                parameterList.add(queryTransferBean.getOpenStatus());
            }else{
            	sqlBuffer.append(" and openStatus = ? ");
                sqlCountBuffer.append(" and openStatus = ? ");
                parameterList.add("1");
            }
            if(StringUtil.isNotEmpty(queryTransferBean.getTranStatus())){
            	sqlBuffer.append(" and tranStatus = ? ");
                sqlCountBuffer.append(" and tranStatus = ? ");
                parameterList.add(queryTransferBean.getTranStatus());
            }
		}
		sqlBuffer.append(" order by applyTime desc");
		Query query = getSession().createQuery(sqlBuffer.toString());
		Query countQuery = getSession().createQuery(sqlCountBuffer.toString());
		int i = 0;
		for(Object parameter : parameterList){
			query.setParameter(i, parameter);
			countQuery.setParameter(i, parameter);
			i++;
		}
		query.setFirstResult((pageSize)*((page==0?1:page)-1));
    	query.setMaxResults(pageSize);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("total", ((Long) countQuery.uniqueResult()).longValue());
		resultMap.put("rows", query.list());
		return resultMap;
	}

   


	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public PojoBankTransferBatch getByBankTranBatchNo(Long tid) {
		String queryString = "from PojoBankTransferBatch where tid = ? ";
		try {
			log.info("queryString:" + queryString);
			Query query = getSession().createQuery(queryString);
			query.setParameter(0, tid);
			return (PojoBankTransferBatch) query.uniqueResult();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CMMException("M001");
		}
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public PojoBankTransferBatch getById(Long tid) {
		String queryString = "from PojoBankTransferBatch where tid = ? ";
		try {
			log.info("queryString:" + queryString);
			Query query = getSession().createQuery(queryString);
			query.setParameter(0, tid);
			return (PojoBankTransferBatch) query.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new CMMException("M001");
		}
	}

    
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<PojoBankTransferBatch> getByTranBatchAndOpenStatus(long tranBatchId,
            BankTransferBatchOpenStatusEnum openStatus){
        Criteria criteria = getSession().createCriteria(PojoBankTransferBatch.class);
        if(openStatus != null && !(BankTransferBatchOpenStatusEnum.UNKNOW).equals(openStatus)){
        	criteria.add(Restrictions.eq("openStatus", openStatus.getCode()));
        }
        criteria.createAlias("tranBatchs","tranBatchs").add(Restrictions.eq("tranBatchs.tid", tranBatchId));
        return (List<PojoBankTransferBatch>)criteria.list();
    }
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public boolean closeBankTransferBatch(Long tid){
    	String queryString = "update PojoBankTransferBatch set openStatus = ? where tid = ? ";
    	try {
			log.info("queryString:" + queryString);
			Query query = getSession().createQuery(queryString);
			query.setParameter(0, "1");
			query.setParameter(1, tid);
			query.executeUpdate();
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new CMMException("M001");
		}
    }
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void rollbackBankTrans(Long tid){
    	try {
			String hql = "update PojoBankTransferBatch set tranStatus = ?,status = ? where tid = ? ";
			Query query = getSession().createQuery(hql);
			query.setParameter(0, "01");
			query.setParameter(1, "01");
			query.setParameter(2, tid);
			query.executeUpdate();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CMMException("M002");
		}
    }

}
