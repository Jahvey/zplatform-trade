/* 
 * CashCaseServiceImpl.java  
 * 
 * version TODO
 *
 * 2015年9月10日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.trade.dao.ICashCaseDAO;
import com.zlebank.zplatform.trade.model.CashCaseModel;
import com.zlebank.zplatform.trade.service.ICashCaseServcie;
import com.zlebank.zplatform.trade.service.base.BaseServiceImpl;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月10日 下午1:34:16
 * @since 
 */
@Service("cashCaseService")
public class CashCaseServiceImpl extends BaseServiceImpl<CashCaseModel, Long> implements ICashCaseServcie{

    @Autowired
    private ICashCaseDAO cashCaseDAO;
    /**
     *
     * @return
     */
    @Override
    public Session getSession() {
        // TODO Auto-generated method stub
        return cashCaseDAO.getSession();
    }
    
    
    public List<CashCaseModel> getCashCaseByCashver(String cashver){
        Map<String, Object> variable = new HashMap<String, Object>();
        variable.put("cashver", cashver);
        variable.put("status", "00");
        return super.findByProperty(variable);
    }
    
}
