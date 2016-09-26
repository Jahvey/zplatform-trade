package com.zlebank.zplatform.trade.dao.impl;

import java.awt.color.CMMException;
import java.util.Date;
import java.util.List;

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
import com.zlebank.zplatform.commons.dao.pojo.AccStatusEnum;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.trade.bean.enums.TransferBatchStatusEnum;
import com.zlebank.zplatform.trade.dao.TranBatchDAO;
import com.zlebank.zplatform.trade.model.PojoBankTransferBatch;
import com.zlebank.zplatform.trade.model.PojoTranBatch;

@Repository
public class TranBatchDAOImpl extends
		AbstractPagedQueryDAOImpl<PojoTranBatch, TransferBatchQuery>
		implements TranBatchDAO {
	private static final Log log = LogFactory
			.getLog(TranBatchDAOImpl.class);

	/**
	 * 通过批次号查找批次信息
	 * 
	 * @param batchno
	 * @return
	 */
	@Override
	@Transactional
	public PojoTranBatch getByBatchNo(Long tid) {
		String queryString = "from PojoTranBatch where tid = ? ";
		try {
			log.info("queryString:" + queryString);
			Query query = getSession().createQuery(queryString);
			query.setParameter(0, tid);
			return (PojoTranBatch) query.uniqueResult();

		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CMMException("M001");
		}
	}

	@Override
	@Transactional
	public void updateBatchToTransfer(PojoTranBatch transferBatch) {
		// TODO Auto-generated method stub
		try {
			String hql = "update PojoTranBatch set status = ?,transfertime = ?,requestfilename = ?,responsefilename = ? where batchno= ? ";
			Session session = getSession();
			Query query = session.createQuery(hql);
//			query.setParameter(0, transferBatch.getStatus());
//			query.setParameter(1, transferBatch.getTransfertime());
//			query.setParameter(2, transferBatch.getRequestfilename());
//			query.setParameter(3, transferBatch.getResponsefilename());
//			query.setParameter(4, transferBatch.getBatchno());
			query.executeUpdate();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CMMException("M002");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public PojoTranBatch getByReqestFileName(String fileName) {
		List<PojoTranBatch> result = null;
		String queryString = "from PojoTranBatch where requestfilename = ? ";
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
	@Transactional
	public void updateTransferBatch(PojoTranBatch transferBatch) {
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
	public List<PojoTranBatch> findWaitAccountingTransferBatch() {
		try {
			String hql = "from PojoTranBatch where accstatus = ? ";
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
			String hql = "from PojoTranBatch where status <> ? and insteadpaybatchno = ? ";
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
	public void updateAccountingResult(String batchno, AccStatusEnum accStatus) {
		try {
			String hql = "update PojoTranBatch set accstatus = ? where batchno = ? ";
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
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public List<PojoTranBatch> findByInsteadpaybatchno(
			String insteadpaybatchno) {
		try {
			String hql = "from PojoTranBatch where insteadpaybatchno = ? ";
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
				PojoTranBatch.class);
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
	public PojoTranBatch getByResponseFileName(String fileName) {
		List<PojoTranBatch> result = null;
		String queryString = "from PojoTranBatch where responsefilename = ? ";
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
	
	public Long getPayingTransferCount(Long tid){
		String queryString = "select count(*) from PojoTranData where tranBatch.tid = ? and status = ?";
		Query query = getSession().createQuery(queryString);
		query.setParameter(0, tid);
		query.setParameter(1, "00");
		return (Long) query.uniqueResult();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateBankTransferResult(Long tid) {
		Long count = getPayingTransferCount(tid);
		if(count==0){//没有审核完成的划拨数据时
			PojoTranBatch tranBatch =	getByBatchNo(tid);
			tranBatch.setStatus("00");
			tranBatch.setFinishTime(new Date());
			update(tranBatch);
		}
		
		
		
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)
	public List<PojoTranBatch> getByInsteadPayBatchandStaus(
			Long id, List<String> status) {
		Criteria criteria = getSession().createCriteria(PojoTranBatch.class);
		criteria.add(Restrictions.eq("insteadPayBatch.id", id));
        criteria.createAlias("insteadPayBatch","insteadPayBatch").add(Restrictions.in("status", status));
        return (List<PojoTranBatch>)criteria.list();
	}
	
}
