/* 
 * ITxnsNotifyTaskDAO.java  
 * 
 * version TODO
 *
 * 2015年11月9日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao;

import org.hibernate.Session;

import com.zlebank.zplatform.commons.dao.BaseDAO;
import com.zlebank.zplatform.trade.model.TxnsNotifyTaskModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月9日 上午9:49:46
 * @since 
 */
public interface ITxnsNotifyTaskDAO extends BaseDAO<TxnsNotifyTaskModel>{

    public Session getSession();
}
