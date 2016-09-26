/* 
 * TxnsQuickpayDAOImpl.java  
 * 
 * version TODO
 *
 * 2015年8月31日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.ITxnsQuickpayDAO;
import com.zlebank.zplatform.trade.model.TxnsQuickpayModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年8月31日 上午10:11:10
 * @since 
 */
@Repository("txnsQuickpayDAO")
public class TxnsQuickpayDAOImpl extends HibernateBaseDAOImpl<TxnsQuickpayModel> implements ITxnsQuickpayDAO{
    public Session getSession(){
        return super.getSession();
    }
}
