/* 
 * MemberMkDAOImpl.java  
 * 
 * version TODO
 *
 * 2015年9月1日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.IMerchMkDAO;
import com.zlebank.zplatform.trade.model.MerchMkModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月1日 下午5:45:35
 * @since 
 */
@Repository("merchMkDAO")
public class MerchMkDAOImpl extends HibernateBaseDAOImpl<MerchMkModel> implements IMerchMkDAO{
    public Session getSession(){
        return super.getSession();
    }
}
