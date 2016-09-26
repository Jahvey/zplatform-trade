package com.zlebank.zplatform.trade.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.ITxnsWhiteListDAO;
import com.zlebank.zplatform.trade.model.TxnsWhiteListModel;
@Repository("txnsWhiteListDAO")
public class TxnsWhiteListDAOImpl extends HibernateBaseDAOImpl<TxnsWhiteListModel> implements ITxnsWhiteListDAO{

    public Session getSession(){
        return super.getSession();
    }
}
