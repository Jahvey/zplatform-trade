/* 
 * OrderAsynRespBean.java  
 * 
 * version TODO
 *
 * 2015年10月26日 
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

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年10月26日 下午3:36:46
 * @since
 */
public class OrderAsynRespBean implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -8230086188659146183L;
    private String version;// 网关版本
    private String encoding;// 编码方式
    private String certId;// 证书 ID
    private String signature;// 签名
    private String signMethod;// 签名方法
    private String merId;// 商户代码
    private String orderId;// 商户订单号
    private String txnType;// 交易类型
    private String txnSubType;// 交易子类
    private String bizType;// 产品类型
    private String accessType;// 接入类型
    private String txnTime;// 订单发送时间
    private String txnAmt;// 交易金额
    private String currencyCode;// 交易币种
    private String reqReserved;// 请求方保留域
    private String reserved;// 保留域
    private String queryId;// 交易查询流水号
    private String respCode;// 响应码
    private String respMsg;// 应答信息
    private String settleAmt;// 清算金额
    private String settleCurrencyCode;// 清算币种
    private String settleDate;// 清算日期
    private String traceNo;// 系统跟踪号
    private String traceTime;// 交易传输时间
    private String exchangeDate;// 兑换日期
    private String exchangeRate;// 汇率
    private String accNo;// 账号
    private String payCardType;// 支付卡类型
    private String payType;// 支付方式
    private String payCardNo;// 支付卡标识
    private String payCardIssueName;// 支付卡名称
    private String bindId;// 绑定标识号
    
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
     * @return the settleAmt
     */
    public String getSettleAmt() {
        return settleAmt;
    }
    /**
     * @param settleAmt the settleAmt to set
     */
    public void setSettleAmt(String settleAmt) {
        this.settleAmt = settleAmt;
    }
    /**
     * @return the settleCurrencyCode
     */
    public String getSettleCurrencyCode() {
        return settleCurrencyCode;
    }
    /**
     * @param settleCurrencyCode the settleCurrencyCode to set
     */
    public void setSettleCurrencyCode(String settleCurrencyCode) {
        this.settleCurrencyCode = settleCurrencyCode;
    }
    /**
     * @return the settleDate
     */
    public String getSettleDate() {
        return settleDate;
    }
    /**
     * @param settleDate the settleDate to set
     */
    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }
    /**
     * @return the traceNo
     */
    public String getTraceNo() {
        return traceNo;
    }
    /**
     * @param traceNo the traceNo to set
     */
    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }
    /**
     * @return the traceTime
     */
    public String getTraceTime() {
        return traceTime;
    }
    /**
     * @param traceTime the traceTime to set
     */
    public void setTraceTime(String traceTime) {
        this.traceTime = traceTime;
    }
    /**
     * @return the exchangeDate
     */
    public String getExchangeDate() {
        return exchangeDate;
    }
    /**
     * @param exchangeDate the exchangeDate to set
     */
    public void setExchangeDate(String exchangeDate) {
        this.exchangeDate = exchangeDate;
    }
    /**
     * @return the exchangeRate
     */
    public String getExchangeRate() {
        return exchangeRate;
    }
    /**
     * @param exchangeRate the exchangeRate to set
     */
    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
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
     * @return the payCardType
     */
    public String getPayCardType() {
        return payCardType;
    }
    /**
     * @param payCardType the payCardType to set
     */
    public void setPayCardType(String payCardType) {
        this.payCardType = payCardType;
    }
    /**
     * @return the payType
     */
    public String getPayType() {
        return payType;
    }
    /**
     * @param payType the payType to set
     */
    public void setPayType(String payType) {
        this.payType = payType;
    }
    /**
     * @return the payCardNo
     */
    public String getPayCardNo() {
        return payCardNo;
    }
    /**
     * @param payCardNo the payCardNo to set
     */
    public void setPayCardNo(String payCardNo) {
        this.payCardNo = payCardNo;
    }
    /**
     * @return the payCardIssueName
     */
    public String getPayCardIssueName() {
        return payCardIssueName;
    }
    /**
     * @param payCardIssueName the payCardIssueName to set
     */
    public void setPayCardIssueName(String payCardIssueName) {
        this.payCardIssueName = payCardIssueName;
    }
    /**
     * @return the bindId
     */
    public String getBindId() {
        return bindId;
    }
    /**
     * @param bindId the bindId to set
     */
    public void setBindId(String bindId) {
        this.bindId = bindId;
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
                new BasicNameValuePair("settleAmt",settleAmt),
                new BasicNameValuePair("settleCurrencyCode",settleCurrencyCode),
                new BasicNameValuePair("settleDate",settleDate),
                new BasicNameValuePair("traceNo",traceNo),
                new BasicNameValuePair("traceTime",traceTime),
                new BasicNameValuePair("exchangeDate",exchangeDate),
                new BasicNameValuePair("exchangeRate",exchangeDate),
                new BasicNameValuePair("accNo",accNo),
                new BasicNameValuePair("payCardType",payCardType),
                new BasicNameValuePair("payType",payType),
                new BasicNameValuePair("payCardNo",payCardNo),
                new BasicNameValuePair("payCardIssueName",payCardIssueName),
                new BasicNameValuePair("bindId",bindId),
                new BasicNameValuePair("signature",signature)};
        List<NameValuePair> qparams = new ArrayList<NameValuePair>();
        for(int i=0;i<pairs.length;i++){
            qparams.add(pairs[i]);
        }

        return qparams;
    }
    /**
     * @param version
     * @param encoding
     * @param certId
     * @param signature
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
     * @param settleAmt
     * @param settleCurrencyCode
     * @param settleDate
     * @param traceNo
     * @param traceTime
     * @param exchangeDate
     * @param exchangeRate
     * @param accNo
     * @param payCardType
     * @param payType
     * @param payCardNo
     * @param payCardIssueName
     * @param bindId
     */
    public OrderAsynRespBean(String version, String encoding, String certId,
            String signature, String signMethod, String merId, String orderId,
            String txnType, String txnSubType, String bizType,
            String accessType, String txnTime, String txnAmt,
            String currencyCode, String reqReserved, String reserved,
            String queryId, String respCode, String respMsg, String settleAmt,
            String settleCurrencyCode, String settleDate, String traceNo,
            String traceTime, String exchangeDate, String exchangeRate,
            String accNo, String payCardType, String payType, String payCardNo,
            String payCardIssueName, String bindId) {
        super();
        this.version = version;
        this.encoding = encoding;
        this.certId = certId;
        this.signature = signature;
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
        this.settleAmt = settleAmt;
        this.settleCurrencyCode = settleCurrencyCode;
        this.settleDate = settleDate;
        this.traceNo = traceNo;
        this.traceTime = traceTime;
        this.exchangeDate = exchangeDate;
        this.exchangeRate = exchangeRate;
        this.accNo = accNo;
        this.payCardType = payCardType;
        this.payType = payType;
        this.payCardNo = payCardNo;
        this.payCardIssueName = payCardIssueName;
        this.bindId = bindId;
    }
    
}
