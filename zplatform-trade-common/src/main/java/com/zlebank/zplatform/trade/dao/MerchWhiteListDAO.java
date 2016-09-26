/* 
 * RealnameAuthDAO.java  
 * 
 * version v1.0
 *
 * 2015年11月24日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao;

import com.zlebank.zplatform.commons.dao.BaseDAO;
import com.zlebank.zplatform.trade.model.PojoMerchWhiteList;

/**
 * 商户白名单DAO
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月24日 下午12:30:18
 * @since 
 */
public interface MerchWhiteListDAO extends BaseDAO<PojoMerchWhiteList>{

    /**
     * 得到指定的白名单信息
     * @param accNo
     * @param accName
     */
    public PojoMerchWhiteList getWhiteListByCardNoAndName(String merId, String accNo, String accName);
    
    /**
     * 通过ID获取商户白名单信息
     * @param id
     * @return
     */
    public PojoMerchWhiteList getMerchWhiteListById(Long id);

}
