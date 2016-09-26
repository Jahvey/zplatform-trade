/* 
 * IInstiMkDAO.java  
 * 
 * version TODO
 *
 * 2015年9月7日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao;

import org.hibernate.Session;

import com.zlebank.zplatform.commons.dao.BaseDAO;
import com.zlebank.zplatform.trade.model.InstiMkModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月7日 下午8:08:49
 * @since 
 */
public interface IInstiMkDAO extends BaseDAO<InstiMkModel>{
    Session getSession();
}
