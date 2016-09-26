/* 
 * ITxnsGatewaypayService.java  
 * 
 * version TODO
 *
 * 2015年9月7日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.chanpay.ChanPayOrderBean;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.model.TxnsGatewaypayModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月7日 下午7:16:14
 * @since 
 */
public interface ITxnsGatewaypayService extends IBaseService<TxnsGatewaypayModel, Long>{
	/**
	 * 保存网关交易流水
	 * @param txnsGateway
	 * @return
	 */
    public ResultBean saveGateWay(TxnsGatewaypayModel txnsGateway);
    /**
     * 更新网关交易流水
     * @param txnsGateway
     * @return
     */
    public ResultBean updateGateWay(TxnsGatewaypayModel txnsGateway);
    /**
     * 通过订单号获取网关交易流水数据
     * @param orderNo
     * @return
     */
    public TxnsGatewaypayModel getOrderByOrderNo(String orderNo);
    
    /**
     * 保存畅捷网关交易的流水
     * @param orderBean
     * @throws TradeException
     */
    public void saveChanPayGateWay(ChanPayOrderBean orderBean) throws TradeException;
   
}
