/* 
 * BankBinRequest.java  
 * 
 * version TODO
 *
 * 2015年10月9日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.message;

import com.zlebank.zplatform.trade.validator.An;
import com.zlebank.zplatform.trade.validator.N;

/**
 * 银行卡卡BIN查询【请求报文】
 *
 * @author yangpeng
 * @version
 * @date 2015年10月9日 下午2:31:25
 * @since 
 */
public class QueryBankCardBin_Request  extends BaseMessage {
    /**渠道类型**/
    @N(max=2,isNull=false)
    private String channelType;
    /**接入类型**/
    @An(max=1,isNull=false)
    private String accessType;
    /**银行卡号**/
    @N
    private String cardNo;
    

    public String getChannelType() {
        return channelType;
    }
    public String getAccessType() {
        return accessType;
    }
    public String getCardNo() {
        return cardNo;
    }
    
    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }
    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
    
    

}
