/* 
 * RaisemoneyApplyDAOImpl.java  
 * 
 * version TODO
 *
 * 2016年8月25日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.RaisemoneyApplyDAO;
import com.zlebank.zplatform.trade.model.PojoRaisemoneyApply;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年8月25日 下午5:50:03
 * @since 
 */
@Repository("raisemoneyApplyDAO")
public class RaisemoneyApplyDAOImpl extends HibernateBaseDAOImpl<PojoRaisemoneyApply> implements RaisemoneyApplyDAO{

	/**
	 *
	 * @param tid
	 * @return
	 */
	@Override
	public PojoRaisemoneyApply getApply(long tid) {
		Criteria criteria = getSession().createCriteria(PojoRaisemoneyApply.class);
		criteria.add(Restrictions.eq("tid", tid));
		return (PojoRaisemoneyApply) criteria.uniqueResult();
	}

}
