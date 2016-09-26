/* 
 * IRouteProcessDAO.java  
 * 
 * version TODO
 *
 * 2015年9月6日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao;

import org.hibernate.Session;

import com.zlebank.zplatform.commons.dao.BaseDAO;
import com.zlebank.zplatform.trade.model.RouteProcessModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月6日 上午10:32:01
 * @since 
 */
public interface IRouteProcessDAO extends BaseDAO<RouteProcessModel>{
    Session getSession();
}
