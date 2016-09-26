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

import java.util.List;

/**
 * 代付查询【应答报文】
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月24日 下午2:53:59
 * @since 
 */
public class InsteadPayQuery_Response  extends BaseMessage {

    /**批次号**/
    private String batchNo;
    /**订单发送时间**/
    private String txnTime;
    /**文件内容**/
    private List<InsteadPayQueryFile> fileContent;
    /**成功总笔数**/
    private String totalQty ;
    /**成功总金额**/
    private String totalAmt ;
    /**成功总笔数**/
    private String waitTotalQty ;
    /**成功总金额**/
    private String waitTotalAmt ;
    /**成功总笔数**/
    private String succTotalQty ;
    /**成功总金额**/
    private String succTotalAmt ;
    /**失败总笔数**/
    private String failTotalQty ;
    /**失败总金额**/
    private String failTotalAmt ;
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
    public String getTxnTime() {
        return txnTime;
    }
    public void setTxnTime(String txnTime) {
        this.txnTime = txnTime;
    }
    public List<InsteadPayQueryFile> getFileContent() {
        return fileContent;
    }
    public void setFileContent(List<InsteadPayQueryFile> fileContent) {
        this.fileContent = fileContent;
    }
    public String getSuccTotalQty() {
        return succTotalQty;
    }
    public void setSuccTotalQty(String succTotalQty) {
        this.succTotalQty = succTotalQty;
    }
    public String getSuccTotalAmt() {
        return succTotalAmt;
    }
    public void setSuccTotalAmt(String succTotalAmt) {
        this.succTotalAmt = succTotalAmt;
    }
    public String getFailTotalQty() {
        return failTotalQty;
    }
    public void setFailTotalQty(String failTotalQty) {
        this.failTotalQty = failTotalQty;
    }
    public String getFailTotalAmt() {
        return failTotalAmt;
    }
    public void setFailTotalAmt(String failTotalAmt) {
        this.failTotalAmt = failTotalAmt;
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
    public String getWaitTotalQty() {
        return waitTotalQty;
    }
    public void setWaitTotalQty(String waitTotalQty) {
        this.waitTotalQty = waitTotalQty;
    }
    public String getWaitTotalAmt() {
        return waitTotalAmt;
    }
    public void setWaitTotalAmt(String waitTotalAmt) {
        this.waitTotalAmt = waitTotalAmt;
    }
}
