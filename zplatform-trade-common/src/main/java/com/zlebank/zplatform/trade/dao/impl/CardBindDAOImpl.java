/* 
 * CardBindDAOImpl.java  
 * 
 * version TODO
 *
 * 2016年6月22日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.CardBindDAO;
import com.zlebank.zplatform.trade.model.PojoCardBind;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年6月22日 下午4:17:55
 * @since 
 */
@Repository("cardBindDAO")
public class CardBindDAOImpl extends HibernateBaseDAOImpl<PojoCardBind> implements CardBindDAO{

	public Session getSession(){
		return super.getSession();
	}
	
	
	
}
