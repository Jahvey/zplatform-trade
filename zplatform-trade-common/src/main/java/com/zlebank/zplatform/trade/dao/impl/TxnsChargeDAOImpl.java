/* 
 * TxnsChargeDAOImpl.java  
 * 
 * version TODO
 *
 * 2016年8月24日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.TxnsChargeDAO;
import com.zlebank.zplatform.trade.model.PojoTxnsCharge;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年8月24日 上午10:16:19
 * @since 
 */
@Repository("txnsChargeDAO")
public class TxnsChargeDAOImpl extends HibernateBaseDAOImpl<PojoTxnsCharge> implements TxnsChargeDAO{

	
	public Session getSession(){
		return super.getSession();
	}
	
	
}
