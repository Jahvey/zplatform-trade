/* 
 * RealnameAuthDAOImpl.java  
 * 
 * version v1.0
 *
 * 2015年11月24日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.MerchInstpayConfDAO;
import com.zlebank.zplatform.trade.model.PojoMerchInstpayConf;


/**
 * 实名认证订单DAO
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月24日 下午12:34:32
 * @since 
 */
@Repository
public class MerchInstpayConfDAOImpl extends HibernateBaseDAOImpl<PojoMerchInstpayConf> implements MerchInstpayConfDAO {

    /**
     * 根据商户ID得到配置
     * @param memberId
     * @return
     */
    @Override
    public PojoMerchInstpayConf getByMemberId(String memberId) {
        Criteria crite= this.getSession().createCriteria(PojoMerchInstpayConf.class);
        crite.add(Restrictions.eq("memberId", memberId));
        @SuppressWarnings("unchecked")
        List<PojoMerchInstpayConf> rtnList = crite.list();
        if (rtnList != null && rtnList.size() == 1)
            return rtnList.get(0);
        else
            return null;
    }
}
