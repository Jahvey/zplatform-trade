/* 
 * CardBindDAO.java  
 * 
 * version TODO
 *
 * 2016年6月22日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao;

import org.hibernate.Session;

import com.zlebank.zplatform.commons.dao.BaseDAO;
import com.zlebank.zplatform.trade.model.PojoCardBind;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年6月22日 下午4:17:26
 * @since 
 */
public interface CardBindDAO extends BaseDAO<PojoCardBind>{

	public Session getSession();
}
