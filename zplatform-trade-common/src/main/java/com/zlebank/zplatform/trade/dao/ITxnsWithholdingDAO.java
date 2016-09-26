package com.zlebank.zplatform.trade.dao;

import org.hibernate.Session;

import com.zlebank.zplatform.commons.dao.BaseDAO;
import com.zlebank.zplatform.trade.model.TxnsWithholdingModel;

public interface ITxnsWithholdingDAO extends BaseDAO<TxnsWithholdingModel>{

    public Session getSession();
}
