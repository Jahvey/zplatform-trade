/* 
 * ICMBCTransferService.java  
 * 
 * version TODO
 *
 * 2015年11月30日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.cmbc.service;

import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.cmbc.bean.gateway.WithholdingMessageBean;
import com.zlebank.zplatform.trade.cmbc.exception.CMBCTradeException;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.model.PojoRealnameAuth;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月30日 上午10:36:42
 * @since 
 */
public interface ICMBCTransferService {

    /**
     * 实名认证
     * @param  realnameAuth 实名认证信息表实体类
     * @return
     * @throws TradeException 
     * @throws CMBCTradeException 
     */
    public ResultBean realNameAuth(PojoRealnameAuth realnameAuth) throws TradeException, CMBCTradeException;
    /**
     * 批量划拨
     * @param tid 转账批次标示
     * @return
     */
    public ResultBean batchTransfer(Long tid);
    /**
     * 单笔划拨
     * @param withholdingMsg 单笔划拨实体bean
     * @return
     */
    public ResultBean singleTransfer(WithholdingMessageBean withholdingMsg);
    
    /**
     * 白名单采集
     * @param bankaccno
     * @param bankaccname
     * @param certno
     * @param mobile
     * @param certtype
     * @return
     */
    public ResultBean whiteListCollection(String bankaccno, String bankaccname,String certno, String mobile,String certtype);
}
