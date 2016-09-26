/* 
 * InsteadPay_Response.java  
 * 
 * version TODO
 *
 * 2015年11月24日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.insteadPay.message;

/**
 * 代付【应答报文】
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月24日 下午2:53:59
 * @since 
 */
public class InsteadPay_Response  extends BaseMessage {

    /**批次号**/
    private String batchNo;
    /**总笔数**/
    private String totalQty;
    /**总金额**/
    private String totalAmt;
    /**响应码**/
    private String respCode;
    /**应答信息**/
    private String respMsg;

    public String getBatchNo() {
        return batchNo;
    }
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }
    public String getTotalQty() {
        return totalQty;
    }
    public void setTotalQty(String totalQty) {
        this.totalQty = totalQty;
    }
    public String getTotalAmt() {
        return totalAmt;
    }
    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt;
    }
    public String getRespCode() {
        return respCode;
    }
    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }
    public String getRespMsg() {
        return respMsg;
    }
    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }
    
}
