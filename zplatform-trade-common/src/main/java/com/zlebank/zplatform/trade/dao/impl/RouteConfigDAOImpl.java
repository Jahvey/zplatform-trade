/* 
 * RouteConfigDAOImpl.java  
 * 
 * version TODO
 *
 * 2015年9月2日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.IRouteConfigDAO;
import com.zlebank.zplatform.trade.model.RouteConfigModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月2日 下午12:08:17
 * @since 
 */
@Repository("routeConfigDAO")
public class RouteConfigDAOImpl extends HibernateBaseDAOImpl<RouteConfigModel> implements IRouteConfigDAO{
    
    public Session getSession(){
        return super.getSession();
    }
}
