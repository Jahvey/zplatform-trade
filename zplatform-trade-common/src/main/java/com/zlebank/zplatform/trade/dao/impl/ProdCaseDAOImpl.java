/* 
 * ProdCaseDAOImpl.java  
 * 
 * version TODO
 *
 * 2015年9月11日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.IProdCaseDAO;
import com.zlebank.zplatform.trade.model.ProdCaseModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月11日 下午4:55:07
 * @since 
 */
@Repository("prodCaseDAO")
public class ProdCaseDAOImpl extends HibernateBaseDAOImpl<ProdCaseModel> implements IProdCaseDAO{

	
    public Session getSession(){
        return super.getSession();
    }
    @Transactional
    public ProdCaseModel getMerchProd(String prdtver,String busicode){
    	Criteria criteria = getSession().createCriteria(ProdCaseModel.class);
    	criteria.add(Restrictions.eq("prdtver", prdtver));
    	criteria.add(Restrictions.eq("busicode", busicode));
        return (ProdCaseModel) criteria.uniqueResult();
    }
}
