/* 
 * Order.java  
 * 
 * version TODO
 *
 * 2015年10月8日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.message;
import com.zlebank.zplatform.trade.validator.An;
import com.zlebank.zplatform.trade.validator.N;

/**
 * 订单信息查询【请求报文】
 *
 * @author yangpeng
 * @version
 * @date 2015年10月8日 下午4:42:13
 * @since
 */

public class QueryOrderInfo_Request extends BaseMessage {

    /** 渠道类型 **/
    private String channelType;
    /** 接入类型 **/
    private String accessType;
    /** 受理订单号 **/
    private String tn;

    @N(max = 2, isNull = false)
    public String getChannelType() {
        return channelType;
    }
    @N(max = 1, isNull = false)
    public String getAccessType() {
        return accessType;
    }
    @An
    public String getTn() {
        return tn;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }
    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }
    public void setTn(String tn) {
        this.tn = tn;
    }

}
