/* 
 * InstiMkServiceImpl.java  
 * 
 * version TODO
 *
 * 2015年9月7日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.trade.dao.IInstiMkDAO;
import com.zlebank.zplatform.trade.model.InstiMkModel;
import com.zlebank.zplatform.trade.service.IInstiMkService;
import com.zlebank.zplatform.trade.service.base.BaseServiceImpl;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月7日 下午8:11:51
 * @since 
 */
@Service("instiMkService")
public class InstiMkServiceImpl extends BaseServiceImpl<InstiMkModel, String> implements IInstiMkService{

    @Autowired
    private IInstiMkDAO instiMkDAO;
    /**
     *
     * @return
     */
    @Override
    public Session getSession() {
        return instiMkDAO.getSession();
    }
    
    public InstiMkModel getMKbySafeseq(String safeseq){
        return super.getUniqueByHQL("from InstiMkModel where safeseq=? and status =? ", new Object[]{safeseq,"00"});
    }

}
