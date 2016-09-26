/* 
 * ChnlDetaServiceImpl.java  
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
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.trade.dao.IChnlDetaDAO;
import com.zlebank.zplatform.trade.model.ChnlDetaModel;
import com.zlebank.zplatform.trade.service.IChnlDetaService;
import com.zlebank.zplatform.trade.service.base.BaseServiceImpl;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月7日 下午8:45:44
 * @since 
 */
@Service("chnlDetaService")
public class ChnlDetaServiceImpl extends BaseServiceImpl<ChnlDetaModel,Long> implements IChnlDetaService{
    @Autowired
    private IChnlDetaDAO chnlDetaDAO;
    /**
     *
     * @return
     */
    @Override
    public Session getSession() {
        // TODO Auto-generated method stub
        return chnlDetaDAO.getSession();
    }
    @Transactional
    public ChnlDetaModel getChannelByCode(String chnlCode){
        return super.getUniqueByHQL("from ChnlDetaModel where chnlcode = ? and status = ?", new Object[]{chnlCode,"00"});
    }

}
