/* 
 * ChnlDetaDAOImpl.java  
 * 
 * version TODO
 *
 * 2015年9月7日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.IChnlDetaDAO;
import com.zlebank.zplatform.trade.model.ChnlDetaModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月7日 下午8:41:52
 * @since 
 */
@Repository("chnlDetaDAO")
public class ChnlDetaDAOImpl extends HibernateBaseDAOImpl<ChnlDetaModel> implements IChnlDetaDAO{
    public Session getSession(){
        return super.getSession();
    }
}
