package com.zlebank.zplatform.trade.service;

import com.zlebank.zplatform.trade.model.RiskTradeLogModel;

public interface IRiskTradeLogService extends IBaseService<RiskTradeLogModel, Long>{
    
    /**
     * 保存交易风控日志
     * @param tradeLog
     */
    public void saveTradeLog(RiskTradeLogModel tradeLog);
}
