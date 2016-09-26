/* 
 * MerchReimburseBatchDAO.java  
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
import com.zlebank.zplatform.trade.model.PojoMerchReimburseBatch;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年8月26日 上午11:06:34
 * @since 
 */
public interface MerchReimburseBatchDAO extends BaseDAO<PojoMerchReimburseBatch>{

	
	public PojoMerchReimburseBatch getBatchInfoByBatchNo(String batchno);
}
