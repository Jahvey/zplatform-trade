/* 
 * TxnsWeChatOrderinfoDAO.java  
 * 
 * version TODO
 *
 * 2016年8月8日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao;

import org.hibernate.Session;

import com.zlebank.zplatform.commons.dao.BaseDAO;
import com.zlebank.zplatform.trade.model.PojoTxnsWechatOrderinfo;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年8月8日 下午5:06:43
 * @since 
 */
public interface WeChatOrderinfoDAO extends BaseDAO<PojoTxnsWechatOrderinfo>{
	public Session getSession();
}
