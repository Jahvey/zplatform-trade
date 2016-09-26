/* 
 * MerchReimburseDetaDAOImpl.java  
 * 
 * version TODO
 *
 * 2016年8月26日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;

import java.math.BigDecimal;

import javassist.expr.NewArray;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.MerchReimburseDetaDAO;
import com.zlebank.zplatform.trade.model.PojoMerchReimburseDeta;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年8月26日 上午11:10:25
 * @since 
 */
@Repository("MerchReimburseDetaDAO")
public class MerchReimburseDetaDAOImpl extends HibernateBaseDAOImpl<PojoMerchReimburseDeta> implements MerchReimburseDetaDAO{

	/**
	 *
	 * @param tid
	 * @return
	 */
	@Override
	public PojoMerchReimburseDeta getDeta(long tid) {
		Criteria criteria = getSession().createCriteria(PojoMerchReimburseDeta.class);
		criteria.add(Restrictions.eq("tid", new BigDecimal(tid)));
		return (PojoMerchReimburseDeta) criteria.uniqueResult();
	}

}
