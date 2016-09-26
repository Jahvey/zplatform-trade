/* 
 * WithdrawBatchDAOImpl.java  
 * 
 * version TODO
 *
 * 2015年10月26日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.IWithdrawBatchDAO;
import com.zlebank.zplatform.trade.model.WithdrawBatchModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年10月26日 下午2:26:25
 * @since 
 */
@Repository("withdrawBatchDAO")
public class WithdrawBatchDAOImpl extends HibernateBaseDAOImpl<WithdrawBatchModel> implements IWithdrawBatchDAO{

    public Session getSession(){
        return super.getSession();
    }
}
