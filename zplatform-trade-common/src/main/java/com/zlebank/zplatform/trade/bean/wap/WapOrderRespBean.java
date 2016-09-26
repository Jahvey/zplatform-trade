/* 
 * WapOrderRespBean.java  
 * 
 * version TODO
 *
 * 2015年10月12日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean.wap;

import java.io.Serializable;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年10月12日 下午1:24:14
 * @since 
 */
//@JSONType(orders={"version","encoding","signature","signMethod","merId","txnType","txnSubType","bizType","orderId","txnTime","reqReserved","respCode","respMsg","tn"})
//@JSONType(orders=order)
public class WapOrderRespBean implements Serializable{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /**版本号*/
    private String version="";
    /**编码方式*/
    private String encoding="";
    /**签名*/
    private String signature="";
    /**签名方法*/
    private String signMethod="";
    /**一级商户代码*/
    private String merId="";
    /**交易类型*/
    private String txnType="";
    /**交易子类*/
    private String txnSubType="";
    /**产品类型*/
    private String bizType="";
    /**商户订单号*/
    private String orderId="";
    /**订单发送时间*/
    private String txnTime="";
    /**请求方保留域*/
    private String reqReserved="";
    /**响应码*/
    private String respCode="";
    /**应答信息*/
    private String respMsg="";
    /**受理订单号*/
    private String tn="";
    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }
    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }
    /**
     * @return the encoding
     */
    public String getEncoding() {
        return encoding;
    }
    /**
     * @param encoding the encoding to set
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
    /**
     * @return the signature
     */
    public String getSignature() {
        return signature;
    }
    /**
     * @param signature the signature to set
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }
    /**
     * @return the signMethod
     */
    public String getSignMethod() {
        return signMethod;
    }
    /**
     * @param signMethod the signMethod to set
     */
    public void setSignMethod(String signMethod) {
        this.signMethod = signMethod;
    }
    /**
     * @return the merId
     */
    public String getMerId() {
        return merId;
    }
    /**
     * @param merId the merId to set
     */
    public void setMerId(String merId) {
        this.merId = merId;
    }
    /**
     * @return the txnType
     */
    public String getTxnType() {
        return txnType;
    }
    /**
     * @param txnType the txnType to set
     */
    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }
    /**
     * @return the txnSubType
     */
    public String getTxnSubType() {
        return txnSubType;
    }
    /**
     * @param txnSubType the txnSubType to set
     */
    public void setTxnSubType(String txnSubType) {
        this.txnSubType = txnSubType;
    }
    /**
     * @return the bizType
     */
    public String getBizType() {
        return bizType;
    }
    /**
     * @param bizType the bizType to set
     */
    public void setBizType(String bizType) {
        this.bizType = bizType;
    }
    /**
     * @return the orderId
     */
    public String getOrderId() {
        return orderId;
    }
    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    /**
     * @return the txnTime
     */
    public String getTxnTime() {
        return txnTime;
    }
    /**
     * @param txnTime the txnTime to set
     */
    public void setTxnTime(String txnTime) {
        this.txnTime = txnTime;
    }
    /**
     * @return the reqReserved
     */
    public String getReqReserved() {
        return reqReserved;
    }
    /**
     * @param reqReserved the reqReserved to set
     */
    public void setReqReserved(String reqReserved) {
        this.reqReserved = reqReserved;
    }
    /**
     * @return the respCode
     */
    public String getRespCode() {
        return respCode;
    }
    /**
     * @param respCode the respCode to set
     */
    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }
    /**
     * @return the respMsg
     */
    public String getRespMsg() {
        return respMsg;
    }
    /**
     * @param respMsg the respMsg to set
     */
    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }
    /**
     * @return the tn
     */
    public String getTn() {
        return tn;
    }
    /**
     * @param tn the tn to set
     */
    public void setTn(String tn) {
        this.tn = tn;
    }
    /**
     * @param version
     * @param encoding
     * @param signature
     * @param signMethod
     * @param merId
     * @param txnType
     * @param txnSubType
     * @param bizType
     * @param orderId
     * @param txnTime
     * @param reqReserved
     * @param respCode
     * @param respMsg
     * @param tn
     */
    public WapOrderRespBean(String version, String encoding, String signature,
            String signMethod, String merId, String txnType, String txnSubType,
            String bizType, String orderId, String txnTime, String reqReserved,
            String respCode, String respMsg, String tn) {
        super();
        this.version = version;
        this.encoding = encoding;
        this.signature = signature;
        this.signMethod = signMethod;
        this.merId = merId;
        this.txnType = txnType;
        this.txnSubType = txnSubType;
        this.bizType = bizType;
        this.orderId = orderId;
        this.txnTime = txnTime;
        this.reqReserved = reqReserved;
        this.respCode = respCode;
        this.respMsg = respMsg;
        this.tn = tn;
    }
    /**
     * 
     */
    public WapOrderRespBean() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
   
    
    
    
    
    
    
}
