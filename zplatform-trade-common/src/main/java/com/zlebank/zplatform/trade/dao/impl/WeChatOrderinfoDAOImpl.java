/* 
 * TxnsWeChatOrderinfoDAOImpl.java  
 * 
 * version TODO
 *
 * 2016年8月8日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.WeChatOrderinfoDAO;
import com.zlebank.zplatform.trade.model.PojoTxnsWechatOrderinfo;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年8月8日 下午5:08:34
 * @since 
 */
@Repository("weChatOrderinfoDAO")
public class WeChatOrderinfoDAOImpl extends HibernateBaseDAOImpl<PojoTxnsWechatOrderinfo> implements WeChatOrderinfoDAO{

	public Session getSession(){
		return super.getSession();
	}
	
	
	
	
}
