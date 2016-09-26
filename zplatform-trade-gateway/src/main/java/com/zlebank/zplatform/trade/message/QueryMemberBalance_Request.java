/* 
 * QueryMemberBalance_Request.java  
 * 
 * version TODO
 *
 * 2015年10月12日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.message;

import com.zlebank.zplatform.trade.validator.An;
import com.zlebank.zplatform.trade.validator.N;

/**
 * 余额查询【请求报文】
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年10月12日 下午2:06:18
 * @since 
 */
public class QueryMemberBalance_Request extends BaseMessage{

    /**渠道类型**/
    @N(max=2,isNull=false)
    private String channelType;
    /**接入类型**/
    @N(max=1,isNull=false)
    private String accessType;
    /**会员ID**/
    @An(max=15,isNull=false)
    private String memberId;

    public String getChannelType() {
        return channelType;
    }
    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }
    public String getAccessType() {
        return accessType;
    }
    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }
    public String getMemberId() {
        return memberId;
    }
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
