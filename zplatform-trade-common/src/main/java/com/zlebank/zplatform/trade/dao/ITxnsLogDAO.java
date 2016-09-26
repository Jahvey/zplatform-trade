/* 
 * ITxnsLogDAO.java  
 * 
 * version TODO
 *
 * 2015年8月27日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao;

import org.hibernate.Session;

import com.zlebank.zplatform.commons.dao.BaseDAO;
import com.zlebank.zplatform.trade.model.TxnsLogModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年8月27日 下午2:20:50
 * @since 
 */
public interface ITxnsLogDAO extends BaseDAO<TxnsLogModel>{
    Session getSession();
}
