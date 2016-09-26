/* 
 * TxnsSplitAccountDAOImpl.java  
 * 
 * version TODO
 *
 * 2015年9月11日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.ITxnsSplitAccountDAO;
import com.zlebank.zplatform.trade.model.TxnsSplitAccountModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月11日 下午7:44:28
 * @since 
 */
@Repository("txnsSplitAccountDAO")
public class TxnsSplitAccountDAOImpl extends HibernateBaseDAOImpl<TxnsSplitAccountModel> implements ITxnsSplitAccountDAO{

    public Session getSession(){
        return super.getSession();
    }
}
