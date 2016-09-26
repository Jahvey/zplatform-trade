/* 
 * TxnsGatewaypayDAOImpl.java  
 * 
 * version TODO
 *
 * 2015年9月7日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.ITxnsGatewaypayDAO;
import com.zlebank.zplatform.trade.model.TxnsGatewaypayModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月7日 下午7:14:55
 * @since 
 */
@Repository("txnsGatewaypayDAO")
public class TxnsGatewaypayDAOImpl extends HibernateBaseDAOImpl<TxnsGatewaypayModel> implements ITxnsGatewaypayDAO{

    public Session getSession(){
        return super.getSession();
    }
}
