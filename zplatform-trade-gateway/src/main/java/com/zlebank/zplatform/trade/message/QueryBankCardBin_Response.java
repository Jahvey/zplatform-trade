/* 
 * BankBinResponse.java  
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

/**
 * 银行卡卡BIN查询【应答报文】
 *
 * @author yangpeng
 * @version
 * @date 2015年10月9日 下午2:32:47
 * @since 
 */
public class QueryBankCardBin_Response  extends BaseMessage {

    /**银行卡号**/
    @An
    private String cardNo;
    /**保留域**/
    private BankCardBin reserved;
    /**响应码**/
    @An(max=2,isNull=false)
    private String respCode;
    /**应答信息**/
    @An(max=256,isNull=false)
    private String respMsg;

    
    public String getCardNo() {
        return cardNo;
    }
    public BankCardBin getReserved() {
        return reserved;
    }
    public String getRespCode() {
        return respCode;
    }
    public String getRespMsg() {
        return respMsg;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
    public void setReserved(BankCardBin reserved) {
        this.reserved = reserved;
    }
    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }
    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }
    
    
}
