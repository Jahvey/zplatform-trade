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
import com.zlebank.zplatform.trade.model.PojoMerchInstpayConf;

/**
 * 
 * 商户代付配置表
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年4月11日 下午4:22:26
 * @since
 */
public interface MerchInstpayConfDAO extends BaseDAO<PojoMerchInstpayConf>{

    /**
     * 根据商户ID得到配置
     * @param memberId
     * @return
     */
    public PojoMerchInstpayConf getByMemberId(String memberId);
}
