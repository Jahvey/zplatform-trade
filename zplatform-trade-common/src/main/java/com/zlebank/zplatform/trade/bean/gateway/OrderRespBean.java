/* 
 * OrderRespBean.java  
 * 
 * version TODO
 *
 * 2015年8月29日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean.gateway;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.zlebank.zplatform.commons.utils.DateUtil;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年8月29日 上午9:55:23
 * @since 
 */
public class OrderRespBean implements Serializable{
    
    private String version;
    private String encoding;
    private String certId;
    private String signMethod;
    private String merId;
    private String orderId;
    private String txnType;
    private String txnSubType;
    private String bizType;
    private String accessType;
    private String txnTime;
    private String txnAmt;
    private String currencyCode;
    private String reqReserved;
    private String reserved;
    private String queryId;
    private String respCode;
    private String respMsg;
    private String accNo;
    private String defaultPayType;
    private String supPayType;
    private String tn;
    private String signature;
    
    public void generateDefaultRespBean(){
        String version = "v1.0";
        String encoding="1";
        String certId="";
        String signMethod="01";
        String merId="";
        String orderId="";
        String txnType="";
        String txnSubType="";
        String bizType="";
        String accessType="0";
        String txnTime=DateUtil.getCurrentDateTime();
        String txnAmt="";
        String currencyCode="156";
        String reqReserved="";
        String reserved="";
        String queryId="";
        String respCode="";
        String respMsg="";
        String accNo="";
        String defaultPayType="0201";
        String supPayType="0201";
        String tn="";
        
        this.version = version;
        this.encoding=encoding;
        this.certId=certId;
        this.signMethod=signMethod;
        this.merId=merId;
        this.orderId=orderId;
        this.txnType=txnType;
        this.txnSubType=txnSubType;
        this.bizType=bizType;
        this.accessType=accessType;
        this.txnTime=txnTime;
        this.txnAmt=txnAmt;
        this.currencyCode=currencyCode;
        this.reqReserved=reqReserved;
        this.reserved=reserved;
        this.queryId=queryId;
        this.respCode=respCode;
        this.respMsg=respMsg;
        this.accNo=accNo;
        this.defaultPayType=defaultPayType;
        this.supPayType=supPayType;
        this.tn=tn;
    }
    
    
    
    
    /**
     * 
     */
    public OrderRespBean() {
       
    }




    /**
     * @param version
     * @param encoding
     * @param certId
     * @param signMethod
     * @param merId
     * @param orderId
     * @param txnType
     * @param txnSubType
     * @param bizType
     * @param accessType
     * @param txnTime
     * @param txnAmt
     * @param currencyCode
     * @param reqReserved
     * @param reserved
     * @param queryId
     * @param respCode
     * @param respMsg
     * @param accNo
     * @param defaultPayType
     * @param supPayType
     * @param tn
     */
    public OrderRespBean(String version, String encoding, String certId,
            String signMethod, String merId, String orderId, String txnType,
            String txnSubType, String bizType, String accessType,
            String txnTime, String txnAmt, String currencyCode,
            String reqReserved, String reserved, String queryId,
            String respCode, String respMsg, String accNo,
            String defaultPayType, String supPayType, String tn) {
        super();
        this.version = version;
        this.encoding = encoding;
        this.certId = certId;
        this.signMethod = signMethod;
        this.merId = merId;
        this.orderId = orderId;
        this.txnType = txnType;
        this.txnSubType = txnSubType;
        this.bizType = bizType;
        this.accessType = accessType;
        this.txnTime = txnTime;
        this.txnAmt = txnAmt;
        this.currencyCode = currencyCode;
        this.reqReserved = reqReserved;
        this.reserved = reserved;
        this.queryId = queryId;
        this.respCode = respCode;
        this.respMsg = respMsg;
        this.accNo = accNo;
        this.defaultPayType = defaultPayType;
        this.supPayType = supPayType;
        this.tn = tn;
    }
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
     * @return the certId
     */
    public String getCertId() {
        return certId;
    }
    /**
     * @param certId the certId to set
     */
    public void setCertId(String certId) {
        this.certId = certId;
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
     * @return the accessType
     */
    public String getAccessType() {
        return accessType;
    }
    /**
     * @param accessType the accessType to set
     */
    public void setAccessType(String accessType) {
        this.accessType = accessType;
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
     * @return the txnAmt
     */
    public String getTxnAmt() {
        return txnAmt;
    }
    /**
     * @param txnAmt the txnAmt to set
     */
    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }
    /**
     * @return the currencyCode
     */
    public String getCurrencyCode() {
        return currencyCode;
    }
    /**
     * @param currencyCode the currencyCode to set
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
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
     * @return the reserved
     */
    public String getReserved() {
        return reserved;
    }
    /**
     * @param reserved the reserved to set
     */
    public void setReserved(String reserved) {
        this.reserved = reserved;
    }
    /**
     * @return the queryId
     */
    public String getQueryId() {
        return queryId;
    }
    /**
     * @param queryId the queryId to set
     */
    public void setQueryId(String queryId) {
        this.queryId = queryId;
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
     * @return the accNo
     */
    public String getAccNo() {
        return accNo;
    }
    /**
     * @param accNo the accNo to set
     */
    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }
    /**
     * @return the defaultPayType
     */
    public String getDefaultPayType() {
        return defaultPayType;
    }
    /**
     * @param defaultPayType the defaultPayType to set
     */
    public void setDefaultPayType(String defaultPayType) {
        this.defaultPayType = defaultPayType;
    }
    /**
     * @return the supPayType
     */
    public String getSupPayType() {
        return supPayType;
    }
    /**
     * @param supPayType the supPayType to set
     */
    public void setSupPayType(String supPayType) {
        this.supPayType = supPayType;
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
    
    public List<NameValuePair> getNotifyParam() {
        BasicNameValuePair[] pairs =  new BasicNameValuePair[] { 
                new BasicNameValuePair("version",version),
                new BasicNameValuePair("encoding",encoding),
                new BasicNameValuePair("certId",certId),
                new BasicNameValuePair("signMethod",signMethod),
                new BasicNameValuePair("merId",merId),
                new BasicNameValuePair("orderId",orderId),
                new BasicNameValuePair("txnType",txnType),
                new BasicNameValuePair("txnSubType",txnSubType),
                new BasicNameValuePair("bizType",bizType),
                new BasicNameValuePair("accessType",accessType),
                new BasicNameValuePair("txnTime",txnTime),
                new BasicNameValuePair("txnAmt",txnAmt),
                new BasicNameValuePair("currencyCode",currencyCode),
                new BasicNameValuePair("reqReserved",reqReserved),
                new BasicNameValuePair("reserved",reserved),
                
                new BasicNameValuePair("queryId",queryId),
                new BasicNameValuePair("respCode",respCode),
                new BasicNameValuePair("respMsg",respMsg),
                new BasicNameValuePair("accNo",accNo),
                new BasicNameValuePair("defaultPayType",defaultPayType),
                
                new BasicNameValuePair("supPayType",supPayType),
                new BasicNameValuePair("tn",tn),
                new BasicNameValuePair("signature",signature)};
        
        
       
        
                
        List<NameValuePair> qparams = new ArrayList<NameValuePair>();
        for(int i=0;i<pairs.length;i++){
            qparams.add(pairs[i]);
        }

        return qparams;
    }
    
    
}
