/* 
 * ITxnsWithdrawService.java  
 * 
 * version TODO
 *
 * 2015年10月22日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import com.zlebank.zplatform.trade.model.TxnsWithdrawModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年10月22日 下午5:46:35
 * @since 
 */
public interface ITxnsWithdrawService extends IBaseService<TxnsWithdrawModel, Long>{
    /**
     * 保存提现流水数据
     * @param withdraw
     */
    public void saveWithdraw(TxnsWithdrawModel withdraw);
    
    /**
     * 更新提现交易结果
     * @param withdraw
     */
    public void updateWithdrawResult(TxnsWithdrawModel withdraw);
    
   
}
