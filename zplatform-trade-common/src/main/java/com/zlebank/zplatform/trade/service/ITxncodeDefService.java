/* 
 * ITxncodeDefService.java  
 * 
 * version TODO
 *
 * 2015年9月10日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.model.TxncodeDefModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月10日 下午3:58:56
 * @since 
 */
public interface ITxncodeDefService extends IBaseService<TxncodeDefModel, Long>{
	/**
	 * 获取内部业务代码实体类
	 * @param txntype
	 * @param txnsubtype
	 * @param biztype
	 * @return
	 */
    public TxncodeDefModel getBusiCode(String txntype,String txnsubtype,String biztype);

    /**
     * 取得默认业务版本
     * @param instiCode
     * @param busicode
     * @param verType
     * @return
     * @throws TradeException
     */
	public String getDefaultVerInfo(String instiCode, String busicode,int verType) throws TradeException;
}
