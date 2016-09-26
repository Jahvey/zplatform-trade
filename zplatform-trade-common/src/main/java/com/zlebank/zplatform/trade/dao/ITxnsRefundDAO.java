/* 
 * ITxnsRefundDAO.java  
 * 
 * version TODO
 *
 * 2015年10月22日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao;

import org.hibernate.Session;

import com.zlebank.zplatform.commons.dao.BaseDAO;
import com.zlebank.zplatform.trade.model.TxnsRefundModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年10月22日 下午3:45:20
 * @since 
 */
public interface ITxnsRefundDAO extends BaseDAO<TxnsRefundModel>{

    public Session getSession();

    public void updateRefund(TxnsRefundModel refundOrder);
}
