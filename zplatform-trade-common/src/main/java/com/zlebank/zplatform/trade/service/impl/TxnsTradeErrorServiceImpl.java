/* 
 * TxnsTradeErrorServiceImpl.java  
 * 
 * version TODO
 *
 * 2015年10月21日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.trade.dao.ITxnsTradeErrorDAO;
import com.zlebank.zplatform.trade.model.TxnsTraderrModel;
import com.zlebank.zplatform.trade.service.ITxnsTradeErrorService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年10月21日 下午5:03:52
 * @since 
 */
@Service("txnsTradeErrorService")
public class TxnsTradeErrorServiceImpl implements ITxnsTradeErrorService{

    @Autowired
    private ITxnsTradeErrorDAO txnsTradeErrorDAO;
    /**
     *
     * @param tradeError
     */
    @Override
    public void saveErrorTrade(TxnsTraderrModel tradeError) {
        // TODO Auto-generated method stub
        txnsTradeErrorDAO.saveA(tradeError);
    }

}
