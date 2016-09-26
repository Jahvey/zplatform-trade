/* 
 * IProdCaseDAO.java  
 * 
 * version TODO
 *
 * 2015年9月11日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao;

import org.hibernate.Session;

import com.zlebank.zplatform.commons.dao.BaseDAO;
import com.zlebank.zplatform.trade.model.ProdCaseModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月11日 下午4:54:19
 * @since 
 */
public interface IProdCaseDAO extends BaseDAO<ProdCaseModel>{
    Session getSession();
    public ProdCaseModel getMerchProd(String prdtver,String busicode);
}
