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

import com.zlebank.zplatform.trade.common.validator.Ans;
import com.zlebank.zplatform.trade.common.validator.N;

/**
 * 实名认证查询【请求报文】
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月24日 下午4:42:14
 * @since 
 */
public class MerWhiteList_Request extends BaseMessage{
    /**账号**/
    @N(min=12,max=60)
    private String accNo;
    /**户名**/
    @Ans(max=32)
    private String accName;
    public String getAccNo() {
        return accNo;
    }
    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }
    public String getAccName() {
        return accName;
    }
    public void setAccName(String accName) {
        this.accName = accName;
    }
}
