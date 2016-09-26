package com.zlebank.zplatform.trade.service;

import com.zlebank.zplatform.trade.model.TxnsSmsModel;
/**
 * 
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年12月15日 下午2:13:23
 * @since
 */
public interface ITxnsSMSService extends IBaseService<TxnsSmsModel, Long>{

    /**
     * 保存短信信息
     * @param sms
     */
    public void saveSMS(TxnsSmsModel sms);
    
    /**
     * 通过TN获取最新的短信信息
     * @param tn
     * @return
     */
    public TxnsSmsModel getLastSMSByTN(String tn);
    
    /**
     * 更新短信发送结果
     * @param sms
     */
    public void updateSMSResult(TxnsSmsModel sms);
}
