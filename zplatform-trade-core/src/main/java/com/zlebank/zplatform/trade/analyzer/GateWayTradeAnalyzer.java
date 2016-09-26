/* 
 * GateWayTradeAnalyzer.java  
 * 
 * version TODO
 *
 * 2015年8月27日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.analyzer;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.commons.utils.Base64Utils;
import com.zlebank.zplatform.commons.utils.RSAUtils;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.gateway.CustomerInfoBean;
import com.zlebank.zplatform.trade.bean.gateway.EncryptDataBean;
import com.zlebank.zplatform.trade.bean.gateway.OrderAsynRespBean;
import com.zlebank.zplatform.trade.bean.gateway.OrderBean;
import com.zlebank.zplatform.trade.bean.gateway.OrderRespBean;
import com.zlebank.zplatform.trade.bean.gateway.QueryBean;
import com.zlebank.zplatform.trade.bean.gateway.QueryResultBean;
import com.zlebank.zplatform.trade.bean.gateway.RiskRateInfoBean;
import com.zlebank.zplatform.trade.bean.gateway.SplitAcctBean;
import com.zlebank.zplatform.trade.bean.wap.WapOrderBean;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.model.TxnsSplitAccountModel;
import com.zlebank.zplatform.trade.utils.DateUtil;
import com.zlebank.zplatform.trade.utils.MessageConvertFactory;
import com.zlebank.zplatform.trade.utils.ObjectDynamic;
import com.zlebank.zplatform.trade.utils.ValidateLocator;

/**
 * 网关交易解析器
 *
 * @author guojia
 * @version
 * @date 2015年8月27日 上午10:44:44
 * @since
 */
public class GateWayTradeAnalyzer {

    public static byte[] generateOrderParamer(OrderBean order) throws Exception {
        String[] unParamstring = {"signature","productcode"};
        String msg = ObjectDynamic.generateParamer(order, false, unParamstring);
        return URLEncoder.encode(msg,"utf-8").getBytes();
    }
    public static byte[] generateOrderParamer(QueryBean queryBean) throws Exception {
        String[] unParamstring = {"signature","productcode"};
        String msg = ObjectDynamic.generateParamer(queryBean, false, unParamstring);
        return URLEncoder.encode(msg,"utf-8").getBytes();
    }
   /***
    * 校验订单信息
    * @param order
    * @return
    */
    public static ResultBean validateOrder(OrderBean order) {
        ResultBean resultBean = ValidateLocator.validateBeans(order);
        if (resultBean.isResultBool()) {
            if (StringUtils.isEmpty(order.getFrontUrl())
                    && StringUtils.isEmpty(order.getBackUrl())) {
                resultBean.setErrorResultDto(MessageConvertFactory
                        .getMessage("order.pageUrl.error"));
            }
            try {
                Date timeoutDate = DateUtil.parse(DateUtil.DEFAULT_DATE_FROMAT, order.getPayTimeout());
                Date txntimeDate = DateUtil.parse(DateUtil.DEFAULT_DATE_FROMAT, order.getTxnTime());
                long skipDay = (timeoutDate.getTime()-txntimeDate.getTime())/(1000*60*60*24);
                if(skipDay>7){
                    resultBean = new ResultBean("GW16", "订单超时时间超过7天！");
                }
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                resultBean = new ResultBean("GW17", "订单时间格式错误！");
            }
            
            long amount = Long.valueOf(order.getTxnAmt());
                    
            if(amount<=0){
                resultBean = new ResultBean("GW18", "订单金额必须大于0！");
            }
            //验证消费分账交易类型
            if("01".equals(order.getTxnType())&&"99".equals(order.getTxnSubType())){
                String json = order.getReserved();
                if(StringUtil.isNotEmpty(json)){
                    try {
                        List<SplitAcctBean> resultList =  JSON.parseArray(json, SplitAcctBean.class);
                        //验证分账交易金额之和
                        Long sumAmount = 0L;
                        Long sumCommAmt = 0L;
                        for(SplitAcctBean splitAcctBean:resultList){
                            sumCommAmt+=Long.valueOf(StringUtil.isNotEmpty(splitAcctBean.getCommAmt())?splitAcctBean.getCommAmt():"0");
                            sumAmount+= Long.valueOf(StringUtil.isNotEmpty(splitAcctBean.getTxnAmt())?splitAcctBean.getTxnAmt():"0");
                        }
                        
                        if(!sumAmount.equals(Long.valueOf(order.getTxnAmt()))){
                            resultBean = new ResultBean("GW23", "子商户交易金额之和不等于总交易金额！");
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        resultBean = new ResultBean("GW22", "分账JSON字符串错误！");
                    }    
                    
                }
            }else{
                String json = order.getReserved();
                if(StringUtil.isNotEmpty(json)){
                    try {
                        JSON.parseObject(json, SplitAcctBean.class);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        resultBean = new ResultBean("GW24", "佣金JSON字符串错误！");
                    }
                }
            }
            
            
            if(StringUtils.isNotEmpty(order.getCustomerInfo())){
                try {
                    Base64Utils.decode(order.getCustomerInfo());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    resultBean = new ResultBean("RC10", "银行卡验证信息错误，请重新提交订单！");
                }
            }
        }
        return resultBean;
    }
    /***
     * 校验Wap订单
     * @param order
     * @throws TradeException
     */
    public static void validateWapOrder(WapOrderBean order) throws TradeException {
        ResultBean resultBean = ValidateLocator.validateBeans(order);
        if (resultBean.isResultBool()) {
            try {
                Date timeoutDate = DateUtil.parse(DateUtil.DEFAULT_DATE_FROMAT, order.getPayTimeout());
                Date txntimeDate = DateUtil.parse(DateUtil.DEFAULT_DATE_FROMAT, order.getTxnTime());
                long skipDay = (timeoutDate.getTime()-txntimeDate.getTime())/(1000*60*60*24);
                if(skipDay>7){
                    throw new TradeException("GW16");
                }
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                throw new TradeException("GW17");
            }
            
            long amount = Long.valueOf(order.getTxnAmt());
                    
            if(amount<=0){
                resultBean = new ResultBean("GW18", "订单金额必须大于0！");
                throw new TradeException("GW18");
            }
          //验证消费分账交易类型
            if("01".equals(order.getTxnType())&&"99".equals(order.getTxnSubType())){
                String json = order.getReserved().trim();
                if(StringUtil.isNotEmpty(json)){
                    try {
                        List<SplitAcctBean> resultList =  JSON.parseArray(json, SplitAcctBean.class);
                        //验证分账交易金额之和
                        Long sumAmount = 0L;
                        Long sumCommAmt = 0L;
                        for(SplitAcctBean splitAcctBean:resultList){
                            sumCommAmt+=Long.valueOf(StringUtil.isNotEmpty(splitAcctBean.getCommAmt())?splitAcctBean.getCommAmt():"0");
                            sumAmount+= Long.valueOf(StringUtil.isNotEmpty(splitAcctBean.getTxnAmt())?splitAcctBean.getTxnAmt():"0");
                        }
                        
                        if(!sumAmount.equals(Long.valueOf(order.getTxnAmt()))){
                            resultBean = new ResultBean("GW23", "子商户交易金额之和不等于总交易金额！");
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        resultBean = new ResultBean("GW22", "分账JSON字符串错误！");
                    }    
                    
                }
            }else{
                String json = order.getReserved().trim();
                if(StringUtil.isNotEmpty(json)){
                    try {
                        JSON.parseObject(json, SplitAcctBean.class);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        resultBean = new ResultBean("GW24", "佣金JSON字符串错误！");
                    }
                }
            }
            
            if(StringUtils.isNotEmpty(order.getCustomerInfo())){
                try {
                    Base64Utils.decode(order.getCustomerInfo());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    throw new TradeException("GW20");
                }
            }
        }else{
            throw new TradeException("GW21");
        }
        
    }
    
    
    public static ResultBean validateQueryOrder(QueryBean queryBean) {
        ResultBean resultBean = ValidateLocator.validateBeans(queryBean);
        return resultBean;
    }
    
    public static OrderRespBean generateOrderResult(OrderRespBean orderRespBean,String privateKey) throws Exception{   
        String[] unParamstring = {"signature"};
        String dataMsg = ObjectDynamic.generateParamer(orderRespBean, false, unParamstring).trim();
        byte[] data =  URLEncoder.encode(dataMsg,"utf-8").getBytes();
        orderRespBean.setSignature(URLEncoder.encode(RSAUtils.sign(data, privateKey),"utf-8"));
        return orderRespBean;
    }
    
    public static OrderAsynRespBean generateAsyncOrderResult(OrderAsynRespBean orderAsyncRespBean,String privateKey) throws Exception{   
        String[] unParamstring = {"signature"};
        String dataMsg = ObjectDynamic.generateParamer(orderAsyncRespBean, false, unParamstring).trim();
        byte[] data =  URLEncoder.encode(dataMsg,"utf-8").getBytes();
        orderAsyncRespBean.setSignature(URLEncoder.encode(RSAUtils.sign(data, privateKey),"utf-8"));
        return orderAsyncRespBean;
    }
    
    public static QueryResultBean generateOrderResult(QueryResultBean queryResultBean,String privateKey) throws Exception{   
        String[] unParamstring = {"signature"};
        String dataMsg = ObjectDynamic.generateParamer(queryResultBean, false, unParamstring).trim();
        byte[] data = URLEncoder.encode(dataMsg,"utf-8").getBytes();
        queryResultBean.setSignature(RSAUtils.sign(data, privateKey));
        return queryResultBean;
    }
    /***
     * 校验风控信息
     * @param riskRateInfo
     * @return
     */
    public static ResultBean generateRiskBean(String riskRateInfo){
        String shippingFlag=           StringUtils.substringBetween(riskRateInfo, "shippingFlag=", "&");
        String shippingCountryCode=    StringUtils.substringBetween(riskRateInfo, "shippingCountryCode=", "&");
        String shippingProvinceCode=   StringUtils.substringBetween(riskRateInfo, "shippingProvinceCode=", "&");
        String shippingCityCode=       StringUtils.substringBetween(riskRateInfo, "shippingCityCode=", "&");
        String shippingDistrictCode=   StringUtils.substringBetween(riskRateInfo, "shippingDistrictCode=", "&");
        String shippingStreet=         StringUtils.substringBetween(riskRateInfo, "shippingStreet=", "&");
        String commodityCategory=      StringUtils.substringBetween(riskRateInfo, "commodityCategory=", "&");
        String commodityName=          StringUtils.substringBetween(riskRateInfo, "commodityName=", "&");
        String commodityUrl=           StringUtils.substringBetween(riskRateInfo, "commodityUrl=", "&");
        String commodityUnitPrice=     StringUtils.substringBetween(riskRateInfo, "commodityUnitPrice=", "&");
        String commodityQty=           StringUtils.substringBetween(riskRateInfo, "commodityQty=", "&");
        String shippingMobile=         StringUtils.substringBetween(riskRateInfo, "shippingMobile=", "&");
        String addressModifyTim=       StringUtils.substringBetween(riskRateInfo, "addressModifyTim=", "&");
        String userRegisterTime=       StringUtils.substringBetween(riskRateInfo, "userRegisterTime=", "&");
        String orderNameModifyTime=    StringUtils.substringBetween(riskRateInfo, "orderNameModifyTime=", "&");
        String userId=                 StringUtils.substringBetween(riskRateInfo, "userId=", "&");
        String orderName=              StringUtils.substringBetween(riskRateInfo, "orderName=", "&");
        String userFlag=               StringUtils.substringBetween(riskRateInfo, "userFlag=", "&");
        String mobileModifyTime=       StringUtils.substringBetween(riskRateInfo, "mobileModifyTime=", "&");
        String riskLevel=              StringUtils.substringBetween(riskRateInfo, "riskLevel=", "&");
        String merUserId=              StringUtils.substringBetween(riskRateInfo, "merUserId=", "&");
        String merUserRegDt=           StringUtils.substringBetween(riskRateInfo, "merUserRegDt=", "&");
        String merUserEmail=           StringUtils.substringBetween(riskRateInfo, "merUserEmail=", "}");
        if(merUserId.startsWith("2")){
            return new ResultBean("GW27","商户会员不可以做为消费或充值会员");
        }
        RiskRateInfoBean riskRateInfoBean = new RiskRateInfoBean(shippingFlag, shippingCountryCode, shippingProvinceCode, shippingCityCode, shippingDistrictCode, shippingStreet, commodityCategory, commodityName, commodityUrl, commodityUnitPrice, commodityQty, shippingMobile, addressModifyTim, userRegisterTime, orderNameModifyTime, userId, orderName, userFlag, mobileModifyTime, riskLevel, merUserId, merUserRegDt, merUserEmail);
        return ValidateLocator.validateBeans(riskRateInfoBean);
    }
    
    public static List<TxnsSplitAccountModel> generateSplitAccount(String reserved,String txnseqno){
        String merId1  = StringUtils.substringBetween(reserved, "merId1=", "&");
        String txnAmt1 = StringUtils.substringBetween(reserved, "txnAmt1=", "&");
        String merId2  = StringUtils.substringBetween(reserved, "merId2=", "&");
        String txnAmt2 = StringUtils.substringBetween(reserved, "txnAmt2=", "&");
        String merId3  = StringUtils.substringBetween(reserved, "merId3=", "&");
        String txnAmt3 = StringUtils.substringBetween(reserved, "txnAmt3=", "}");
        TxnsSplitAccountModel account1 = new TxnsSplitAccountModel(1L, merId1, Long.valueOf(txnAmt1), txnseqno,"","");
        TxnsSplitAccountModel account2 = new TxnsSplitAccountModel(2L, merId2, Long.valueOf(txnAmt2), txnseqno,"","");
        TxnsSplitAccountModel account3 = new TxnsSplitAccountModel(3L, merId3, Long.valueOf(txnAmt3), txnseqno,"","");
        List<TxnsSplitAccountModel> accountList = new ArrayList<TxnsSplitAccountModel>();
        accountList.add(account1);
        accountList.add(account2);
        accountList.add(account3);
        return accountList;
    }
    
    
    public static Long generateCommAmt(String reserved){
        String commamt = StringUtils.substringBetween(reserved, "commAmt=", "}");
        if(StringUtils.isNotEmpty(commamt)){
            return Long.valueOf(commamt);
        }
        return 0L;
    }
    
    
    public static ResultBean generateCustomerinfo(String customerinfo){
        String certifTp  = StringUtils.substringBetween(customerinfo, "certifTp=", "&");
        String certifId = StringUtils.substringBetween(customerinfo, "certifId=", "&");
        String customerNm = StringUtils.substringBetween(customerinfo, "customerNm=", "&");
        String smsCode  = StringUtils.substringBetween(customerinfo, "smsCode=", "&");
        String pin = StringUtils.substringBetween(customerinfo, "pin=", "&");
        String encryptData  = StringUtils.substringBetween(customerinfo, "encryptData=", "}");
        CustomerInfoBean customerInfo = new CustomerInfoBean(certifTp, certifId, customerNm, smsCode, pin, encryptData);
        ResultBean resultBean = ValidateLocator.validateBeans(customerInfo);
        return resultBean;
    }
    
    public static EncryptDataBean generateEncryDate(String encryptData){
        String cardNo = StringUtils.substringBetween(encryptData, "cardNo=", "&");
        String cvn2 = StringUtils.substringBetween(encryptData, "cvn2=", "&");
        String expired = StringUtils.substringBetween(encryptData, "expired=", "&");
        String phoneNo = StringUtils.substringBetween(encryptData, "phoneNo=", "}");
        EncryptDataBean encryptDataBean = new EncryptDataBean(cardNo, cvn2, expired, phoneNo);
        return encryptDataBean;
    }
    
    
}

