/* 
 * AppUpdateDAOImpl.java  
 * 
 * version TODO
 *
 * 2016年6月24日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.AppUpdateDAO;
import com.zlebank.zplatform.trade.model.PojoAppUpdate;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年6月24日 下午4:20:47
 * @since 
 */
@Repository("appUpdateDAO")
public class AppUpdateDAOImpl extends HibernateBaseDAOImpl<PojoAppUpdate> implements AppUpdateDAO{

	public Session getSession(){
		return super.getSession();
	}
	@Transactional(readOnly=true)
	public PojoAppUpdate getAppUpdate(String appVersion,String appChannelId){
		Criteria crite= this.getSession().createCriteria(PojoAppUpdate.class);
		crite.add(Restrictions.eq("appVersion", appVersion));
		crite.add(Restrictions.eq("appChannelId", appChannelId));
        return (PojoAppUpdate) crite.uniqueResult();
	}
}
