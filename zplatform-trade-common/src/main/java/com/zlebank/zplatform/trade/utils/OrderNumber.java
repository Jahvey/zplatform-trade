/* 
 * OrderNumber.java  
 * 
 * version TODO
 *
 * 2015年10月13日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.trade.service.ITxnsLogService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年10月13日 下午12:21:55
 * @since 
 */
public class OrderNumber {

    private ITxnsLogService txnsLogService = (ITxnsLogService) SpringContext.getContext().getBean("txnsLogService");

    private static OrderNumber orderNumber;
    
    public static synchronized OrderNumber getInstance(){
        if(orderNumber==null){
            orderNumber = new OrderNumber();
        }
        return orderNumber;
    }
    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    private String generateSerialNumber(String sequences){
        List<Map<String,Object>> resultList = (List<Map<String, Object>>) txnsLogService.queryBySQL("select "+sequences+".NEXTVAL seq from dual", new Object[]{});
        DecimalFormat df = new DecimalFormat("00000000");
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        String seqNo = df.format(resultList.get(0).get("SEQ"));
        return sdf.format(new Date())+seqNo;
    }
    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    private String generateSerialDateNumber(String sequences){
        List<Map<String,Object>> resultList = (List<Map<String, Object>>) txnsLogService.queryBySQL("select "+sequences+".NEXTVAL seq from dual", new Object[]{});
        DecimalFormat df = new DecimalFormat("00000000");
        String seqNo = df.format( resultList.get(0).get("SEQ"));
        return DateUtil.getCurrentDate()+seqNo;
    }
    
    @Transactional(readOnly=true)
    private String generateBatchNo(String sequences){
        List<Map<String,Object>> resultList = (List<Map<String, Object>>) txnsLogService.queryBySQL("select "+sequences+".NEXTVAL seq from dual", new Object[]{});
        DecimalFormat df = new DecimalFormat("000");
        String seqNo = df.format( resultList.get(0).get("SEQ"));
        return seqNo;
    }
    @Transactional(readOnly=true)
    private Long generateID(String sequences){
        List<Map<String,Object>> resultList = (List<Map<String, Object>>) txnsLogService.queryBySQL("select "+sequences+".NEXTVAL seq from dual", new Object[]{});
        String seqNo =  resultList.get(0).get("SEQ")+"";
        return Long.valueOf(seqNo);
    }
    
    
    
    public  String generateZLOrderId(){
        String seqNo=generateSerialNumber("SEQ_ZLPAY_ORDERNO");
        return seqNo.substring(0,6)+"98"+seqNo.substring(6);
    }
    
    public String generateReaPayOrderId(){
        String seqNo=generateSerialNumber("SEQ_REAPAYORDERNO");
        return seqNo.substring(0,6)+"96"+seqNo.substring(6);
    }
    
    public String generateAppOrderNo(){
        String seqNo=generateSerialNumber("SEQ_APPORDERNO");
        return seqNo.substring(0,6)+"95"+seqNo.substring(6);
    }
    
    public String generateTxnseqno(String busiCode){
        String seqNo=generateSerialNumber("SEQ_TXNSEQNO");
        return seqNo.substring(0,6)+"99"+seqNo.substring(6);
    }
    
    public String generateECITIOrderNo(){
        String seqNo=generateSerialNumber("SEQ_ECITIORDERNO");
        return seqNo.substring(0,6)+"97"+seqNo.substring(6);
    }
    
    public Long generateID(){
        return generateID("SEQ_QUICKPAY");
    }
    public String generateTN(String memberId){
        String seqNo=generateSerialNumber("SEQ_ORDERINFO_TN");
        return seqNo.substring(0,6)+memberId.substring(11)+seqNo.substring(6);
    }
    
    public Long generateBindCardID(){
        String seqNo=generateSerialNumber("SEQ_BINDCARD_ID");
        return Long.valueOf(seqNo);
    }
    
    public String generateTradeErrorNo(){
        String seqNo=generateSerialNumber("SEQ_ECITIORDERNO");
        return seqNo.substring(0,6)+"94"+seqNo.substring(6);
    }
    
    public String generateRefundOrderNo(){
        String seqNo=generateSerialNumber("SEQ_REFUND_NO");
        return seqNo.substring(0,6)+"93"+seqNo.substring(6);
    }
    public String generateWithdrawOrderNo(){
        String seqNo=generateSerialNumber("SEQ_WITHDRAW_NO");
        return seqNo.substring(0,6)+"91"+seqNo.substring(6);
    }
    public String generateCMBCQuickOrderNo(){
        String seqNo=generateSerialNumber("SEQ_CMBC_QUICK_NO");
        return seqNo.substring(0,6)+"92"+seqNo.substring(6);
    }
    
    public String generateChanPayOrderNo(){
        String seqNo=generateSerialNumber("SEQ_CHANPAY_ORDER_NO");
        return seqNo.substring(0,6)+"90"+seqNo.substring(6);
    }
    
    
    
    public String generateRealNameOrderNo(){
        return generateSerialDateNumber("SEQ_CMBC_REALNAME_NO");
    }
    public String generateWithholdingOrderNo(){
        return generateSerialDateNumber("SEQ_CMBC_WITHHOLDING_NO");
    }
    public String generateWhiteListOrderNo(){
        return generateSerialDateNumber("SEQ_CMBC_WHITELIST_NO");
    }
    public String generateRealNameQueryOrderNo(){
        return generateSerialDateNumber("SEQ_CMBC_REALNAME_QUERY_NO");
    }
    public String generateWithholdingQueryOrderNo(){
        return generateSerialDateNumber("SEQ_CMBC_WITHHOLDING_QUERY_NO");
    }
    public String generateWhiteListQueryOrderNo(){
        return generateSerialDateNumber("SEQ_CMBC_WHITELIST_QUERY_NO");
    }
    
    public String generateRealTimeInsteadPayOrderNo(){
        return generateSerialDateNumber("SEQ_CMBC_INSTEADPAY_NO");
    }
    public String generateRealTimeInsteadPayQueryOrderNo(){
        return generateSerialDateNumber("SEQ_CMBC_INSTEADPAY_QUERY_NO");
    }
    public String generateRealTimeWithholdingCMBCSelf(){
        return generateSerialDateNumber("SEQ_CMBC_SELF_WITHHOLDING_NO");
    }
    public String generateRealTimeWithholdingQueryCMBCSelf(){
        return generateSerialDateNumber("SEQ_CMBC_SELF_QUERY_NO");
    }
    public String generateCMBCBatchNo(){
        return generateBatchNo("SEQ_CMBC_BATCHNO");
    }
    
    public String generateWeChatOrderNO(){
    	String seqNo=generateSerialNumber("SEQ_WECHAT_NO");
        return seqNo.substring(0,6)+"90"+seqNo.substring(6);
    }
    
    
    private OrderNumber() {
    }
    
    
}
