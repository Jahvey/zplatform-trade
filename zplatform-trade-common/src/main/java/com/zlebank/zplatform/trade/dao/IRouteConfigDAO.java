/* 
 * IRouteConfigDAO.java  
 * 
 * version TODO
 *
 * 2015年9月2日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao;

import org.hibernate.Session;

import com.zlebank.zplatform.commons.dao.BaseDAO;
import com.zlebank.zplatform.trade.model.RouteConfigModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月2日 下午12:07:23
 * @since 
 */
public interface IRouteConfigDAO extends BaseDAO<RouteConfigModel>{
    Session getSession();
}
