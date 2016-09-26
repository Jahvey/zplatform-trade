package com.zlebank.zplatform.trade.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.ITxnsWithholdingDAO;
import com.zlebank.zplatform.trade.model.TxnsWithholdingModel;
@Repository("txnsWithholdingDAO")
public class TxnsWithholdingDAOImpl extends HibernateBaseDAOImpl<TxnsWithholdingModel> implements ITxnsWithholdingDAO{

    public Session getSession() {
        return super.getSession();
    }
}
