/* 
 * RealnameAuthDAOImpl.java  
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
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.RealnameAuthDAO;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.model.PojoRealnameAuth;


/**
 * Class Description
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月24日 下午12:34:32
 * @since 
 */
@Repository("realnameAuthDAO")
public class RealnameAuthDAOImpl extends HibernateBaseDAOImpl<PojoRealnameAuth> implements RealnameAuthDAO {

    /**
     * 保存实名认证数据
     * @param realnameAuth
     * @throws TradeException
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void saveRealNameAuth(PojoRealnameAuth realnameAuth)
            throws TradeException {
        try {
        	PojoRealnameAuth cardInfo = getByCardInfo(realnameAuth);
        	if(cardInfo==null){
        		getSession().save(realnameAuth);
        	}
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new TradeException("T037");
        }
        
    }
    /**
     * 更新实名认证状态
     * @param realnameAuth
     * @throws TradeException
     */
    @Transactional
    public void updateRealNameStatus(PojoRealnameAuth realnameAuth) throws TradeException{
        StringBuffer querBuffer = new StringBuffer();
        querBuffer.append("update PojoRealnameAuth set status = ? ");
        querBuffer.append(" where cardNo = ? and customerNm = ? and certifId = ? and phoneNo = ?");
        try {
            Session session = getSession();
            Query query = session.createQuery(querBuffer.toString());
            query.setParameter(0, realnameAuth.getStatus());
            query.setParameter(1, realnameAuth.getCardNo());
            query.setParameter(2, realnameAuth.getCustomerNm());
            query.setParameter(3, realnameAuth.getCertifId());
            query.setParameter(4, realnameAuth.getPhoneNo());
            query.executeUpdate();
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new TradeException("T037");
        }
    }
    
    

    /**
     * 根据卡号和持卡人姓名得到
     * @param cardNo
     * @param accName
     * @return
     */
    @Override
    public PojoRealnameAuth getByCardNoAndName(String cardNo, String accName, String certifId, String phoneNo) {
        Criteria crite= this.getSession().createCriteria(PojoRealnameAuth.class);
        crite.add(Restrictions.eq("cardNo", cardNo));
        crite.add(Restrictions.eq("customerNm", accName));
        crite.add(Restrictions.eq("certifId", certifId));
        crite.add(Restrictions.eq("phoneNo", Long.parseLong(phoneNo == null ? "0" : phoneNo)));
        crite.add(Restrictions.eq("status", "00"));
        @SuppressWarnings("unchecked")
        List<PojoRealnameAuth> pojos = crite.list();
        return pojos.size() > 0 ? (PojoRealnameAuth) pojos.get(0) : null;
    }

    
    /**
     * 通过卡信息获取实名认证数据
     * @param realnameAuth
     * @return
     */
    @Override
    @Transactional(readOnly=true)
    public PojoRealnameAuth getByCardInfo(PojoRealnameAuth realnameAuth) {
        Criteria crite= this.getSession().createCriteria(PojoRealnameAuth.class);
        crite.add(Restrictions.eq("cardNo", realnameAuth.getCardNo()));
        crite.add(Restrictions.eq("customerNm", realnameAuth.getCustomerNm()));
        crite.add(Restrictions.eq("certifId", realnameAuth.getCertifId()));
        crite.add(Restrictions.eq("phoneNo", realnameAuth.getPhoneNo()));
        Object uniqueResult = crite.uniqueResult();
        if(uniqueResult!=null){
            return (PojoRealnameAuth)uniqueResult;
        }
        return null;
    }
	
}
