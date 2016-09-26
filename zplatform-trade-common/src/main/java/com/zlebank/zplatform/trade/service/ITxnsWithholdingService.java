package com.zlebank.zplatform.trade.service;

import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.model.TxnsWithholdingModel;

public interface ITxnsWithholdingService extends IBaseService<TxnsWithholdingModel, Long>{

    /**
     * 保存代扣业务流水
     * @param withholding 代扣交易实体类
     * @throws TradeException
     */
    public void saveWithholdingLog(TxnsWithholdingModel withholding) throws TradeException;
    
    /**
     * 更新实名认证结果
     * @param withholding
     * @throws TradeException
     */
    public void updateRealNameResult(TxnsWithholdingModel withholding)  throws TradeException;
    
    /**
     * 通过流水号获取代扣实体类
     * @param serialno
     * @return
     */
    public TxnsWithholdingModel getWithholdingBySerialNo(String serialno);
    
    /**
     * 更新代扣交易异常信息
     * @param withholding
     */
    public void updateWithholdingLogError(TxnsWithholdingModel withholding);
    
    /**
     * 更新代付交易结果
     * @param withholding
     */
    public void updateWhithholding(TxnsWithholdingModel withholding);
}
