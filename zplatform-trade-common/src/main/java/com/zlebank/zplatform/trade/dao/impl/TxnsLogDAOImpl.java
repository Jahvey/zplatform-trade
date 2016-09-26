/* 
 * TxnsLogDAOImpl.java  
 * 
 * version TODO
 *
 * 2015年8月27日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.ITxnsLogDAO;
import com.zlebank.zplatform.trade.model.TxnsLogModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年8月27日 下午2:20:09
 * @since 
 */
@Repository("txnsLogDAO")
public class TxnsLogDAOImpl extends HibernateBaseDAOImpl<TxnsLogModel> implements ITxnsLogDAO{
    public Session getSession(){
        return  super.getSession();
    }
    
}
