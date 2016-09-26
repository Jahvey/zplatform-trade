package com.zlebank.zplatform.trade.service;

import com.zlebank.zplatform.trade.model.TxnsWhiteListModel;

public interface ITxnsWhiteListService extends IBaseService<TxnsWhiteListModel, Long>{

    /**
     * 保存白名单数据
     * @param whiteList
     */
    public void saveWhiteList(TxnsWhiteListModel whiteList);
    
    /**
     * 通过卡信息获取白名单数据
     * @param whiteList
     * @return
     */
    public TxnsWhiteListModel getByCardInfo(TxnsWhiteListModel whiteList);
}
