/* 
 * TxncodeDefDAOImpl.java  
 * 
 * version TODO
 *
 * 2015年9月10日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.ITxncodeDefDAO;
import com.zlebank.zplatform.trade.model.TxncodeDefModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月10日 下午3:57:27
 * @since 
 */
@Repository("txncodeDefDAO")
public class TxncodeDefDAOImpl extends HibernateBaseDAOImpl<TxncodeDefModel> implements ITxncodeDefDAO{

    public Session getSession(){
        return super.getSession();
    }
}
