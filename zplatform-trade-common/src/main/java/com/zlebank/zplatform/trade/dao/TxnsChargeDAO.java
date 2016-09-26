/* 
 * TxnsChargeDAO.java  
 * 
 * version TODO
 *
 * 2016年8月24日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao;

import org.hibernate.Session;

import com.zlebank.zplatform.commons.dao.BaseDAO;
import com.zlebank.zplatform.trade.model.PojoTxnsCharge;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年8月24日 上午10:15:39
 * @since 
 */
public interface TxnsChargeDAO extends BaseDAO<PojoTxnsCharge>{

	public Session getSession();
}
