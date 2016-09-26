package com.zlebank.zplatform.trade.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.IRiskTradeLogDAO;
import com.zlebank.zplatform.trade.model.RiskTradeLogModel;
@Repository("riskTradeLogDAO")
public class RiskTradeLogDAOImpl extends HibernateBaseDAOImpl<RiskTradeLogModel> implements IRiskTradeLogDAO{

    public Session getSession(){
        return super.getSession();
    }
}
