package com.zlebank.zplatform.trade.service.impl;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.trade.dao.IRiskTradeLogDAO;
import com.zlebank.zplatform.trade.model.RiskTradeLogModel;
import com.zlebank.zplatform.trade.service.IRiskTradeLogService;
import com.zlebank.zplatform.trade.service.base.BaseServiceImpl;

@Service("riskTradeLogService")
public class RiskTradeLogServiceImpl extends BaseServiceImpl<RiskTradeLogModel, Long> implements IRiskTradeLogService{

    @Autowired
    private IRiskTradeLogDAO riskTradeLogDAO;
    
    @Override
    public Session getSession() {
        // TODO Auto-generated method stub
        return riskTradeLogDAO.getSession();
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Throwable.class)
    public void saveTradeLog(RiskTradeLogModel tradeLog){
        super.save(tradeLog);
    }
}
