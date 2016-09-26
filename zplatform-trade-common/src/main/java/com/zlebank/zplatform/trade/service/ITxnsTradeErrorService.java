/* 
 * ITxnsTradeErrorService.java  
 * 
 * version TODO
 *
 * 2015年10月21日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import com.zlebank.zplatform.trade.model.TxnsTraderrModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年10月21日 下午5:00:50
 * @since 
 */
public interface ITxnsTradeErrorService {
    public void saveErrorTrade(TxnsTraderrModel tradeError);
}
