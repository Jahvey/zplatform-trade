/* 
 * MerchWhiteListService.java  
 * 
 * version TODO
 *
 * 2015年12月14日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import com.zlebank.zplatform.trade.insteadPay.message.MerWhiteList_Request;

/**
 * 商户白名单业务
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年12月14日 下午12:20:43
 * @since 
 */
public interface MerchWhiteListService {

    /**
     *  添加白名单处理
     * @param request 
     * @return 错误代码，如果没有错误则返回NULL
     */
    public String addMerchWhiteListService(MerWhiteList_Request request) ;

    /**
     *  检查是否在白名单范围
     * @param request 
     * @return 错误代码，如果没有错误则返回NULL
     */
    public String checkMerchWhiteList(String merId, String accName, String accNo) ;
    
    
}
