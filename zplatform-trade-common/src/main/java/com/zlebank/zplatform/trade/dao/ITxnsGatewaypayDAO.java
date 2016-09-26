/* 
 * ITxnsGatewaypayDAO.java  
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
import com.zlebank.zplatform.trade.model.TxnsGatewaypayModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月7日 下午7:14:28
 * @since 
 */
public interface ITxnsGatewaypayDAO extends BaseDAO<TxnsGatewaypayModel>{
    Session getSession();
}
