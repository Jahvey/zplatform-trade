/* 
 * InstiMkDAOImpl.java  
 * 
 * version TODO
 *
 * 2015年9月7日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.IInstiMkDAO;
import com.zlebank.zplatform.trade.model.InstiMkModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月7日 下午8:09:30
 * @since 
 */
@Repository("instiMkDAO")
public class InstiMkDAOImpl extends HibernateBaseDAOImpl<InstiMkModel> implements IInstiMkDAO{

    public Session getSession(){
        return super.getSession();
    }
}
