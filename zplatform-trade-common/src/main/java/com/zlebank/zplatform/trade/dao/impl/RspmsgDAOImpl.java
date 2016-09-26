/* 
 * RspmsgDAOImpl.java  
 * 
 * version TODO
 *
 * 2015年10月22日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.bean.enums.ChnlTypeEnum;
import com.zlebank.zplatform.trade.dao.RspmsgDAO;
import com.zlebank.zplatform.trade.model.PojoRspmsg;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年10月22日 下午1:45:26
 * @since 
 */
@Repository("rspmsgDAO")
public class RspmsgDAOImpl  extends HibernateBaseDAOImpl<PojoRspmsg> implements RspmsgDAO{
    private static final Log log=LogFactory.getLog(RspmsgDAOImpl.class);
    /**
     *
     * @param t
     * @return
     */
    @Override
    public PojoRspmsg merge(PojoRspmsg t) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     *
     * @param t
     * @return
     */
    @Override
    public PojoRspmsg update(PojoRspmsg t) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     *
     * @param t
     */
    @Override
    public void saveA(PojoRspmsg t) {
        // TODO Auto-generated method stub
        
    }

    /**
     *
     * @param rspid
     * @return
     */
    @Override
    public PojoRspmsg get(String rspid) {
        PojoRspmsg rspmsg = (PojoRspmsg) getSession().get(PojoRspmsg.class, rspid);
        if(rspmsg!=null){
            getSession().evict(rspmsg);
        }
        return rspmsg;
    }

    @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRES_NEW)
    public PojoRspmsg getRspmsgByChnlCode(ChnlTypeEnum chnlType,String retCode) {
        List<PojoRspmsg> result = null;
        String  queryString = null;
        if(chnlType==null){
        	queryString = "from PojoRspmsg where chnlrspcode=?";
        }else{
        	queryString = "from PojoRspmsg where chnltype=? and chnlrspcode=?";
        }
        try {
            log.info("getRspmsgByChnlCode() queryString:"+queryString);
            Query query = getSession().createQuery(queryString);
            if(chnlType==null){
            	query.setParameter(0, retCode);
            }else{
            	query.setParameter(0,chnlType.getTradeType());
                query.setParameter(1, retCode);
            }
            result = query.list();
            if(result.size()>0){
                return result.get(0);
            }
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

}
