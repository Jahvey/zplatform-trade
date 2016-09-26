/* 
 * RouteProcessDAOImpl.java  
 * 
 * version TODO
 *
 * 2015年9月6日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.IRouteProcessDAO;
import com.zlebank.zplatform.trade.model.RouteProcessModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月6日 上午10:32:46
 * @since 
 */
@Repository("routeProcessDAO")
public class RouteProcessDAOImpl extends HibernateBaseDAOImpl<RouteProcessModel> implements IRouteProcessDAO{

    public Session getSession(){
        return super.getSession();
    }
}
