/* 
 * IWithdrawBatchDAO.java  
 * 
 * version TODO
 *
 * 2015年10月26日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao;

import org.hibernate.Session;

import com.zlebank.zplatform.commons.dao.BaseDAO;
import com.zlebank.zplatform.trade.model.WithdrawBatchModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年10月26日 下午2:25:45
 * @since 
 */
public interface IWithdrawBatchDAO extends BaseDAO<WithdrawBatchModel>{
    Session getSession();
}
