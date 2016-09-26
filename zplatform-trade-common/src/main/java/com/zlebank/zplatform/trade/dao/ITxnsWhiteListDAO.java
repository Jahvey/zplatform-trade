package com.zlebank.zplatform.trade.dao;

import org.hibernate.Session;

import com.zlebank.zplatform.commons.dao.BaseDAO;
import com.zlebank.zplatform.trade.model.TxnsWhiteListModel;

public interface ITxnsWhiteListDAO extends BaseDAO<TxnsWhiteListModel>{
    public Session getSession();
}
