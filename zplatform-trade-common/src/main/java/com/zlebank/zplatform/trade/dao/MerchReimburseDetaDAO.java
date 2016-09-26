/* 
 * MerchReimburseDetaDAO.java  
 * 
 * version TODO
 *
 * 2016年8月26日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao;

import com.zlebank.zplatform.commons.dao.BaseDAO;
import com.zlebank.zplatform.trade.model.PojoMerchReimburseDeta;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年8月26日 上午11:07:29
 * @since 
 */
public interface MerchReimburseDetaDAO extends BaseDAO<PojoMerchReimburseDeta>{

	/**
	 * 获取商户还款数据
	 * @param tid
	 * @return
	 */
	public PojoMerchReimburseDeta getDeta(long tid);
}
