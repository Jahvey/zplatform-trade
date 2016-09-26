/* 
 * QuickpayCustDAOImpl.java  
 * 
 * version TODO
 *
 * 2015年9月21日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;



import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.IQuickpayCustDAO;
import com.zlebank.zplatform.trade.model.QuickpayCustModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月21日 上午11:20:14
 * @since 
 */
@Repository("quickpayCustDAO")
public class QuickpayCustDAOImpl extends HibernateBaseDAOImpl<QuickpayCustModel> implements IQuickpayCustDAO{
    public Session getSession(){
        return super.getSession();
    }
}
