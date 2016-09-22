/* 
 * ICashBankDAO.java  
 * 
 * version TODO
 *
 * 2015年10月14日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao;

import org.hibernate.Session;

import com.zlebank.zplatform.commons.dao.BaseDAO;
import com.zlebank.zplatform.trade.model.CashBankModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年10月14日 下午2:50:53
 * @since 
 */
public interface ICashBankDAO extends BaseDAO<CashBankModel>{
    Session getSession();
    /**
 	 * 获取银行图标
 	 * @param bankCode
 	 * @return
 	 */
 	public CashBankModel getBankICON(String bankCode);
}
