/* 
 * CashCaseDAOImpl.java  
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
import com.zlebank.zplatform.trade.dao.ICashCaseDAO;
import com.zlebank.zplatform.trade.model.CashCaseModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月10日 下午1:32:15
 * @since 
 */
@Repository("cashCaseDAO")
public class CashCaseDAOImpl extends HibernateBaseDAOImpl<CashCaseModel> implements ICashCaseDAO{
    public Session getSession(){
        return super.getSession();
    }
}
