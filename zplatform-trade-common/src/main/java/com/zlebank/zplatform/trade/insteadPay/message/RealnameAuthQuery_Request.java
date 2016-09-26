/* 
 * RealnameAuth_Request.java  
 * 
 * version v1.0
 *
 * 2015年11月24日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.insteadPay.message;

import com.zlebank.zplatform.trade.common.validator.An;
import com.zlebank.zplatform.trade.common.validator.N;

/**
 * 实名认证查询【请求报文】
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月24日 下午4:42:14
 * @since 
 */
public class RealnameAuthQuery_Request extends BaseMessage{

    /**商户订单号**/
    @An(min=12,max=32)
    private String orderId;
    /**订单发送时间**/
    @N(max=14,min=14)
    private String txnTime;

    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getTxnTime() {
        return txnTime;
    }
    public void setTxnTime(String txnTime) {
        this.txnTime = txnTime;
    }

}
