/* 
 * ITxncodeDefDAO.java  
 * 
 * version TODO
 *
 * 2015年9月10日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao;

import org.hibernate.Session;

import com.zlebank.zplatform.commons.dao.BaseDAO;
import com.zlebank.zplatform.trade.model.TxncodeDefModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月10日 下午3:56:43
 * @since 
 */
public interface ITxncodeDefDAO extends BaseDAO<TxncodeDefModel>{
    Session getSession();
}
