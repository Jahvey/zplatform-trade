/* 
 * AppUpdateDAO.java  
 * 
 * version TODO
 *
 * 2016年6月24日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao;

import org.hibernate.Session;

import com.zlebank.zplatform.commons.dao.BaseDAO;
import com.zlebank.zplatform.trade.model.PojoAppUpdate;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年6月24日 下午4:20:03
 * @since 
 */
public interface AppUpdateDAO extends BaseDAO<PojoAppUpdate>{

	public Session getSession();
	public PojoAppUpdate getAppUpdate(String appVersion,String appChannelId);
}
