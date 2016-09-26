package com.zlebank.zplatform.trade.cmbc.service;

import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;

/**
 * 民生银行快捷支付(代扣)
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年12月16日 下午1:57:21
 * @since
 */
public interface ICMBCQuickPayService {

    /**
     * 跨行代扣
     * @param trade
     * @return
     */
    public ResultBean crossLineWithhold(TradeBean trade);
    
    /**
     * 查询交易结果通过渠道流水
     * @param serialno 渠道流水
     * @return
     */
    public ResultBean queryResult(String serialno);
    
    /**
     * 查询民生跨行代扣交易结果
     * @param txnseqno
     * @return
     */
    public ResultBean queryCrossLineTrade(String txnseqno);
    
    /**
     * 本行代扣
     * @param trade
     * @return
     */
    public ResultBean innerLineWithhold(TradeBean trade);
    
}
