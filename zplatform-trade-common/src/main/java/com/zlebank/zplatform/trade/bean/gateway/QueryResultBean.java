/* 
 * QueryResultBean.java  
 * 
 * version TODO
 *
 * 2015年9月30日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean.gateway;

import java.io.Serializable;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月30日 下午4:55:56
 * @since 
 */
public class QueryResultBean implements Serializable{
        private String version;
        private String certId;
        private String signature;
        private String encoding;
        private String signMethod;
        private String txnType;
        private String txnSubType;
        private String accessType;
        private String merId;
        private String status;
        private String retinfo;
        private String txnTime;
        private String orderId;
        private String reqReserved;
        private String reserved;
        private String queryId;
        private String traceNo;
        private String traceTime;
        private String settleDate;
        private String settleCurrencyCode;
        
        /**
         * 
         */
        public QueryResultBean(QueryBean queryBean) {
            this.version = queryBean.getVersion();
            this.certId = queryBean.getCertId();
            this.encoding = queryBean.getEncoding();
            this.signMethod = queryBean.getSignMethod();
            this.txnType = queryBean.getTxnType();
            this.txnSubType = queryBean.getTxnSubType();
            this.accessType = queryBean.getAccessType();
            this.merId = queryBean.getMerId();
            this.txnTime = queryBean.getTxnTime();
            this.orderId = queryBean.getOrderId();
            this.reserved = queryBean.getReserved();
            this.queryId = queryBean.getQueryId();
            this.settleCurrencyCode = "156";
            
        }
        /**
         * @param version
         * @param certId
         * @param signature
         * @param encoding
         * @param signMethod
         * @param txnType
         * @param txnSubType
         * @param accessType
         * @param merId
         * @param txnTime
         * @param orderId
         * @param reqReserved
         * @param reserved
         * @param queryId
         * @param traceNo
         * @param traceTime
         * @param settleDate
         * @param settleCurrencyCode
         */
        public QueryResultBean(String version, String certId, String signature,
                String encoding, String signMethod, String txnType,
                String txnSubType, String accessType, String merId,
                String txnTime, String orderId, String reqReserved,
                String reserved, String queryId, String traceNo,
                String traceTime, String settleDate, String settleCurrencyCode) {
            super();
            this.version = version;
            this.certId = certId;
            this.signature = signature;
            this.encoding = encoding;
            this.signMethod = signMethod;
            this.txnType = txnType;
            this.txnSubType = txnSubType;
            this.accessType = accessType;
            this.merId = merId;
            this.txnTime = txnTime;
            this.orderId = orderId;
            this.reqReserved = reqReserved;
            this.reserved = reserved;
            this.queryId = queryId;
            this.traceNo = traceNo;
            this.traceTime = traceTime;
            this.settleDate = settleDate;
            this.settleCurrencyCode = settleCurrencyCode;
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
         * @return the status
         */
        public String getStatus() {
            return status;
        }
        /**
         * @param status the status to set
         */
        public void setStatus(String status) {
            this.status = status;
        }
        /**
         * @return the retinfo
         */
        public String getRetinfo() {
            return retinfo;
        }
        /**
         * @param retinfo the retinfo to set
         */
        public void setRetinfo(String retinfo) {
            this.retinfo = retinfo;
        }
        
        
        
}
