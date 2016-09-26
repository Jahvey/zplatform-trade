/* 
 * IQuickpayCust.java  
 * 
 * version TODO
 *
 * 2015年9月21日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao;



import org.hibernate.Session;

import com.zlebank.zplatform.commons.dao.BaseDAO;
import com.zlebank.zplatform.trade.model.QuickpayCustModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月21日 上午11:19:29
 * @since 
 */
public interface IQuickpayCustDAO extends BaseDAO<QuickpayCustModel>{
    Session getSession();
}
