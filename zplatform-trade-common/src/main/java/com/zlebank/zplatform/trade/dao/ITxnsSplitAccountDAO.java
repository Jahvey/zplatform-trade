/* 
 * ITxnsSplitAccountDAO.java  
 * 
 * version TODO
 *
 * 2015年9月11日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao;

import org.hibernate.Session;

import com.zlebank.zplatform.commons.dao.BaseDAO;
import com.zlebank.zplatform.trade.model.TxnsSplitAccountModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月11日 下午7:43:16
 * @since 
 */
public interface ITxnsSplitAccountDAO extends BaseDAO<TxnsSplitAccountModel>{
    Session getSession();
}
