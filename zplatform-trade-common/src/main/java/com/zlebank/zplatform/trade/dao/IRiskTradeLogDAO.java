package com.zlebank.zplatform.trade.dao;

import org.hibernate.Session;

import com.zlebank.zplatform.commons.dao.BaseDAO;
import com.zlebank.zplatform.trade.model.RiskTradeLogModel;

public interface IRiskTradeLogDAO extends BaseDAO<RiskTradeLogModel>{

    public Session getSession();
}
