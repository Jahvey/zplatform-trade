/* 
 * RealnameAuthDAO.java  
 * 
 * version v1.0
 *
 * 2015年11月24日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao;

import com.zlebank.zplatform.commons.dao.BaseDAO;
import com.zlebank.zplatform.trade.model.PojoRealnameAuthOrder;

/**
 * 实名认证订单DAO
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月24日 下午12:30:18
 * @since 
 */
public interface RealnameAuthOrderDAO extends BaseDAO<PojoRealnameAuthOrder>{

    public PojoRealnameAuthOrder get(long id) ;


    /**
     * 根据订单号和和发送时间得到实名认证信息
     * @param orderId
     * @param txnTime
     * @return
     */
    public PojoRealnameAuthOrder getByOrderIdAndTxnTime(String orderId, String txnTime);
    
    public PojoRealnameAuthOrder updateWithCommit(PojoRealnameAuthOrder pojoRealnameAuthOrder);
    /**
     * 指定商户的指定卡号是否已经经过认证
     * @param orderId
     * @param txnTime
     * @return
     */
    public PojoRealnameAuthOrder isRealNameAuth(String merId, String orderId, String txnTime);
}
