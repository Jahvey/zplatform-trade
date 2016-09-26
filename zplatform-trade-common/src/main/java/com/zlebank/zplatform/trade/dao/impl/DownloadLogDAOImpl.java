/* 
 * DownloadLogDAOImpl.java  
 * 
 * version TODO
 *
 * 2015年11月10日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.DownloadLogDAO;
import com.zlebank.zplatform.trade.model.PojoDownloadLog;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月10日 上午11:22:09
 * @since 
 */
@Repository("downloadLogDAO")
public class DownloadLogDAOImpl extends HibernateBaseDAOImpl<PojoDownloadLog> implements DownloadLogDAO{

    @Override
    public PojoDownloadLog getLogByFileName(String filename) {
        try {
            String hql = "from PojoDownloadLog where fileurl = ?";
            Session session = getSession();
            Query query = session.createQuery(hql);
            query.setString(0, filename);
            @SuppressWarnings("unchecked")
			List<PojoDownloadLog> logList = query.list();
            if(logList.size()>0){
                return logList.get(0);
            }
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
