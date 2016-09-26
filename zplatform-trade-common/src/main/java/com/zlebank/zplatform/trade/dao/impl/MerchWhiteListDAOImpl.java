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

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.trade.dao.MerchWhiteListDAO;
import com.zlebank.zplatform.trade.model.PojoMerchWhiteList;


/**
 * 商户白名单DAO
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月24日 下午12:34:32
 * @since 
 */
@Repository
public class MerchWhiteListDAOImpl extends HibernateBaseDAOImpl<PojoMerchWhiteList> implements MerchWhiteListDAO {

    /**
     * 得到指定的白名单信息
     * @param merId
     * @param accNo
     * @param accName
     * @return
     */
    @Override
    public PojoMerchWhiteList getWhiteListByCardNoAndName(String merId, String accNo, String accName) {
        Criteria crite= this.getSession().createCriteria(PojoMerchWhiteList.class);
        if (StringUtil.isNotEmpty(merId))
            crite.add(Restrictions.eq("merchId", merId));
        if (StringUtil.isNotEmpty(accNo))
            crite.add(Restrictions.eq("cardNo", accNo));
        if (StringUtil.isNotEmpty(accName))
            crite.add(Restrictions.eq("accName", accName));
        crite.add(Restrictions.in("status", new String[]{"0", "1"}));
        return (PojoMerchWhiteList) crite.uniqueResult();
    }

	/**
	 *
	 * @param id
	 * @return
	 */
	@Override
	public PojoMerchWhiteList getMerchWhiteListById(Long id) {
		Criteria crite= this.getSession().createCriteria(PojoMerchWhiteList.class);
		crite.add(Restrictions.eq("id", id));
		return (PojoMerchWhiteList) crite.uniqueResult();
	}
}
