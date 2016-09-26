/* 
 * MerchMkServiceImpl.java  
 * 
 * version TODO
 *
 * 2015年9月1日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.trade.dao.IMerchMkDAO;
import com.zlebank.zplatform.trade.model.MerchMkModel;
import com.zlebank.zplatform.trade.service.IMerchMkService;
import com.zlebank.zplatform.trade.service.base.BaseServiceImpl;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月1日 下午5:55:07
 * @since 
 */
@Service("merchMkService")
public class MerchMkServiceImpl extends BaseServiceImpl<MerchMkModel, String> implements IMerchMkService{

    @Autowired
    private IMerchMkDAO merchMkDAO;
    /**
     *
     * @return
     */
    @Override
    public Session getSession() {
        // TODO Auto-generated method stub
        return merchMkDAO.getSession();
    }
    
    
}
