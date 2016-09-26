/* 
 * MemberDAOImpl.java  
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
import com.zlebank.zplatform.trade.dao.IMemberDAO;
import com.zlebank.zplatform.trade.model.MemberBaseModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月1日 下午5:15:41
 * @since 
 */
@Repository("memberDAO")
public class MemberDAOImpl extends HibernateBaseDAOImpl<MemberBaseModel> implements IMemberDAO{
    public Session getSession(){
        return super.getSession();
    }
}
