/* 
 * WithdrawBatchServiceImpl.java  
 * 
 * version TODO
 *
 * 2015年10月26日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.trade.dao.IWithdrawBatchDAO;
import com.zlebank.zplatform.trade.model.WithdrawBatchModel;
import com.zlebank.zplatform.trade.service.IWithdrawBatchService;
import com.zlebank.zplatform.trade.service.base.BaseServiceImpl;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年10月26日 下午2:34:37
 * @since 
 */
@Service("withdrawBatchService")
public class WithdrawBatchServiceImpl extends BaseServiceImpl<WithdrawBatchModel, Long> implements IWithdrawBatchService{

    @Autowired
    private IWithdrawBatchDAO withdrawBatchDAO;
    
    /**
     *
     * @return
     */
    @Override
    public Session getSession() {
        // TODO Auto-generated method stub
        return withdrawBatchDAO.getSession();
    }

}
