/* 
 * TxnsNotifyTaskDAOImpl.java  
 * 
 * version TODO
 *
 * 2015年11月9日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.ITxnsNotifyTaskDAO;
import com.zlebank.zplatform.trade.model.TxnsNotifyTaskModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月9日 上午9:50:27
 * @since 
 */
@Repository("txnsNotifyTaskDAO")
public class TxnsNotifyTaskDAOImpl extends HibernateBaseDAOImpl<TxnsNotifyTaskModel> implements ITxnsNotifyTaskDAO{

    public Session getSession(){
       return super.getSession();
    }

   
}
