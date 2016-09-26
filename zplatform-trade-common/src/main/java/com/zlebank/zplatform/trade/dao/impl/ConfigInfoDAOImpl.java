/* 
 * InsteadPayBatchDAOImpl.java  
 * 
 * version TODO
 *
 * 2015年11月24日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.ConfigInfoDAO;
import com.zlebank.zplatform.trade.model.ConfigInfoModel;

/**
 * 配置DAO
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月24日 下午1:48:00
 * @since 
 */
@Repository
public class ConfigInfoDAOImpl extends HibernateBaseDAOImpl<ConfigInfoModel> implements ConfigInfoDAO {

    /**
     * 根据参数名称得到配置信息
     * @param paraName
     * @return
     */
    @Override
    public ConfigInfoModel getConfigByParaName(String paraName) {
        Criteria crite= this.getSession().createCriteria(ConfigInfoModel.class);
        crite.add(Restrictions.eq("paraname", paraName));
        return (ConfigInfoModel) crite.uniqueResult();
    }
    
    public long getSequence(String sequences){
        String sql = " SELECT "+sequences+".NEXTVAL nextvalue FROM DUAL";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        long maxId = (long)(query.addScalar("nextvalue", StandardBasicTypes.LONG) ).uniqueResult();
         return  maxId;
       }

    /**
     *  根据参数名称得到配置信息列表
     * @param paraName
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ConfigInfoModel> getConfigListByParaName(String paraName) {
        Criteria crite= this.getSession().createCriteria(ConfigInfoModel.class);
        crite.add(Restrictions.eq("paraname", paraName));
        return crite.list();
    }
    
    public Session getSession(){
    	return super.getSession();
    }
}
