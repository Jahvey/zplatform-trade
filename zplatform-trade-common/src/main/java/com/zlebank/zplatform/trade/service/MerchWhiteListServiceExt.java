/* 
 * MerchWhiteListService.java  
 * 
 * version TODO
 *
 * 2016年8月17日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import com.zlebank.zplatform.trade.model.PojoMerchWhiteList;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年8月17日 下午2:27:22
 * @since 
 */
public interface MerchWhiteListServiceExt {
	/**
     * 得到指定的白名单信息
     * @param accNo
     * @param accName
     */
    public PojoMerchWhiteList getWhiteListByCardNoAndName(String merId, String accNo, String accName);
    
    /**
     * 通过ID获取商户白名单信息
     * @param id
     * @return
     */
    public PojoMerchWhiteList getMerchWhiteListById(Long id);
    
    
    public void merge(PojoMerchWhiteList merchWhiteList);
}
