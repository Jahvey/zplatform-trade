/* 
 * MerchReimburseBatchDAOImpl.java  
 * 
 * version TODO
 *
 * 2016年8月26日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.MerchReimburseBatchDAO;
import com.zlebank.zplatform.trade.model.PojoMerchReimburseBatch;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年8月26日 上午11:09:46
 * @since 
 */
@Repository("merchReimburseBatchDAO")
public class MerchReimburseBatchDAOImpl extends HibernateBaseDAOImpl<PojoMerchReimburseBatch> implements MerchReimburseBatchDAO{

	/**
	 *
	 * @param batchno
	 * @return
	 */
	@Override
	public PojoMerchReimburseBatch getBatchInfoByBatchNo(String batchno) {
		Criteria criteria = getSession().createCriteria(PojoMerchReimburseBatch.class);
		criteria.add(Restrictions.eq("batchNo", batchno));
		return (PojoMerchReimburseBatch) criteria.uniqueResult();
	}

}
