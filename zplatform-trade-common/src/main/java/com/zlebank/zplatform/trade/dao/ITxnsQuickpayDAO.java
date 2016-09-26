/* 
 * ITxnsQuickpayDAO.java  
 * 
 * version TODO
 *
 * 2015年8月31日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao;

import org.hibernate.Session;

import com.zlebank.zplatform.commons.dao.BaseDAO;
import com.zlebank.zplatform.trade.model.TxnsQuickpayModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年8月31日 上午10:10:33
 * @since 
 */
public interface ITxnsQuickpayDAO extends BaseDAO<TxnsQuickpayModel>{
    Session getSession();
}
