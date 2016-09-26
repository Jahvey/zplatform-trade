/* 
 * IWithholdingService.java  
 * 
 * version TODO
 *
 * 2015年11月23日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.cmbc.service;

import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.cmbc.bean.gateway.CardMessageBean;
import com.zlebank.zplatform.trade.cmbc.bean.gateway.WhiteListMessageBean;
import com.zlebank.zplatform.trade.cmbc.bean.gateway.WithholdingMessageBean;
import com.zlebank.zplatform.trade.cmbc.exception.CMBCTradeException;
import com.zlebank.zplatform.trade.model.TxnsWithholdingModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月23日 下午2:28:36
 * @since 
 */
public interface IWithholdingService{
    public ResultBean realNameAuthentication(String json) throws CMBCTradeException;
    public ResultBean realNameAuthentication(CardMessageBean card )
            throws CMBCTradeException;
    public ResultBean realNameAuthQuery( String oritransdate, String orireqserialno )
            throws CMBCTradeException;
    public ResultBean realTimeWitholding(WithholdingMessageBean withholdingMsg)
            throws CMBCTradeException;
    public ResultBean realTimeWitholdinghQuery( String oritransdate, String orireqserialno )
            throws CMBCTradeException;
    public ResultBean realTimeWitholdinghQuery(TxnsWithholdingModel withholding)
            throws CMBCTradeException;
    public ResultBean whiteListCollection(WhiteListMessageBean whiteListMsg)
            throws CMBCTradeException;
    public ResultBean whiteListCollectionQuery(String bankaccno)
            throws CMBCTradeException;
    /**
     * 民生本行实时代扣 
     *
     * @param withholdingMsg
     * @return
     * @throws CMBCTradeException
     */
    public ResultBean realTimeWitholdingSelf(WithholdingMessageBean withholdingMsg)
            throws CMBCTradeException;
    
    /**
     * 民生本行代扣结果查询
     * @param oritransdate
     * @param orireqserialno
     * @return
     * @throws CMBCTradeException
     */
    public ResultBean realTimeSelfWithholdinghQuery(String oritransdate,
            String orireqserialno) throws CMBCTradeException;
    /**
     * 查询民生本行代扣结果并处理返回类型为R的交易
     * @param oritransdate
     * @param orireqserialno
     * @return
     * @throws CMBCTradeException
     */
    public ResultBean realTimeSelfWithholdinghResult(TxnsWithholdingModel withholding) throws CMBCTradeException;
    
    public ResultBean realTimeWitholdinghResult(TxnsWithholdingModel withholding) throws CMBCTradeException;
}
