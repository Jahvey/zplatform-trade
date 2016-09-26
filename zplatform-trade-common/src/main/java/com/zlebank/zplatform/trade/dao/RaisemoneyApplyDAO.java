/* 
 * RaisemoneyApplyDAO.java  
 * 
 * version TODO
 *
 * 2016年8月25日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao;

import com.zlebank.zplatform.commons.dao.BaseDAO;
import com.zlebank.zplatform.trade.model.PojoRaisemoneyApply;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年8月25日 下午5:49:19
 * @since 
 */
public interface RaisemoneyApplyDAO extends BaseDAO<PojoRaisemoneyApply>{

	/**
	 * 获取募集款划转数据
	 * @param tid
	 * @return
	 */
	public PojoRaisemoneyApply getApply(long tid);
}
