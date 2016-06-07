/* 
 * ZlPayTradeAnalyzer.java  
 * 
 * version TODO
 *
 * 2015年8月21日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.zlpay.analyzer;

import java.io.UnsupportedEncodingException;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.ZLPayResultBean;
import com.zlebank.zplatform.trade.bean.zlpay.MarginRegisterBean;
import com.zlebank.zplatform.trade.bean.zlpay.MarginSmsBean;
import com.zlebank.zplatform.trade.bean.zlpay.OfflineDepositNotifyBean;
import com.zlebank.zplatform.trade.bean.zlpay.OnlineDepositShortBean;
import com.zlebank.zplatform.trade.bean.zlpay.QuerytranstatusBean;
import com.zlebank.zplatform.trade.bean.zlpay.WithdrawNotifyBean;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.OrderNumber;
import com.zlebank.zplatform.trade.zlpay.utils.ZLPayRSAUtil;
import com.zlink.zlpay.commonofs.common.exception.MsgException;
import com.zlink.zlpay.commonofs.common.util.CertificateUtil;

/**
 * Class Description 证联支付快捷交易报文解析器
 * 
 * @author guojia
 * @version
 * @date 2015年8月21日 上午11:42:50
 * @since
 */
public class ZlPayTradeAnalyzer {
    private static final Log log = LogFactory.getLog(ZlPayTradeAnalyzer.class);
    private static PublicKey publicKey;

    /**
     * @throws MsgException
     * 
     */
    public ZlPayTradeAnalyzer() throws MsgException {
        publicKey = CertificateUtil
                .getPublicKeyFromCer(ConsUtil.getInstance().cons.getCerPath());
    }

    public MarginSmsBean sendMarginSms(String verNum,
            String sysDateTime,
            String instuId,
            String transType,
            String encMsg,
            String checkValue,
            TradeBean trade) throws MsgException, UnsupportedEncodingException {

        String msg = ZLPayRSAUtil.verifyData(checkValue, encMsg, publicKey);
        log.info("receive message:" + msg);

        instuId = StringUtils.substringBetween(msg, "instuId=", "|");
        String merchantDate = StringUtils.substringBetween(msg,
                "merchantDate=", "|");
        String merchantTime = StringUtils.substringBetween(msg,
                "merchantTime=", "|");
        String merchantSeqId = StringUtils.substringBetween(msg,
                "merchantSeqId=", "|");
        String tradeType = StringUtils.substringBetween(msg, "tradeType=", "|");
        String userId = StringUtils.substringBetween(msg, "userId=", "|");
        String userNameText = StringUtils.substringBetween(msg,
                "userNameText=", "|");
        String mobile = StringUtils.substringBetween(msg, "mobile=", "|");
        String certType = StringUtils.substringBetween(msg, "certType=", "|");
        String certId = StringUtils.substringBetween(msg, "certId=", "|");
        String bankCode = StringUtils.substringBetween(msg, "bankCode=", "|");
        String cardNo = StringUtils.substringBetween(msg, "cardNo=", "|");
        String amt = StringUtils.substringBetween(msg, "amt=", "|");

        String resv = StringUtils.substringBetween(msg, "resv=", "|");

        MarginSmsBean bean = new MarginSmsBean(instuId, merchantDate,
                merchantTime, merchantSeqId, tradeType, userId, userNameText,
                mobile, certType, certId, bankCode, cardNo, amt, resv);

        return bean;
    }

    public MarginRegisterBean marginRegisterReq(String encMsg, String checkValue)
            throws MsgException, UnsupportedEncodingException {

        String msg = ZLPayRSAUtil.verifyData(checkValue, encMsg, publicKey);
        log.info("receive message:" + msg);

        String instuId = StringUtils.substringBetween(msg, "instuId=", "|");
        String fundDate = StringUtils.substringBetween(msg, "fundDate=", "|");
        String fundTime = StringUtils.substringBetween(msg, "fundTime=", "|");
        String fundSeqId = StringUtils.substringBetween(msg, "fundSeqId=", "|");
        String counterNo = StringUtils.substringBetween(msg, "counterNo=", "|");
        String custType = StringUtils.substringBetween(msg, "custType=", "|");
        String userNameText = StringUtils.substringBetween(msg,
                "userNameText=", "|");
        String certType = StringUtils.substringBetween(msg, "certType=", "|");
        String certId = StringUtils.substringBetween(msg, "certId=", "|");
        String orgId = StringUtils.substringBetween(msg, "orgId=", "|");
        String bankCode = StringUtils.substringBetween(msg, "bankCode=", "|");
        String bankProvinceCode = StringUtils.substringBetween(msg,
                "bankProvinceCode=", "|");
        String bankRegionCode = StringUtils.substringBetween(msg,
                "bankRegionCode=", "|");
        String cardNo = StringUtils.substringBetween(msg, "cardNo=", "|");
        String payPassWord = StringUtils.substringBetween(msg, "payPassWord=",
                "|");
        String mobile = StringUtils.substringBetween(msg, "mobile=", "|");
        String identifyingCode = StringUtils.substringBetween(msg,
                "identifyingCode=", "|");
        String resv = StringUtils.substringBetween(msg, "resv=", "|");

        MarginRegisterBean bean = new MarginRegisterBean(instuId, fundDate,
                fundTime, fundSeqId, counterNo, custType, userNameText,
                certType, certId, orgId, bankCode, bankProvinceCode,
                bankRegionCode, cardNo, payPassWord, mobile, identifyingCode,
                resv);

        return bean;

    }

    public OnlineDepositShortBean onlineDepositShort(String verNum,
            String sysDateTime,
            String instuId,
            String transType,
            String encMsg,
            String checkValue) throws MsgException,
            UnsupportedEncodingException {

        String msg = ZLPayRSAUtil.verifyData(checkValue, encMsg, publicKey);
        log.info("receive message:" + msg);

        instuId = StringUtils.substringBetween(msg, "instuId=", "|");
        String merchantDate = StringUtils.substringBetween(msg,
                "merchantDate=", "|");
        String merchantTime = StringUtils.substringBetween(msg,
                "merchantTime=", "|");
        String merchantSeqId = StringUtils.substringBetween(msg,
                "merchantSeqId=", "|");
        String userId = StringUtils.substringBetween(msg, "userId=", "|");
        String userNameText = StringUtils.substringBetween(msg,
                "userNameText=", "|");
        String certType = StringUtils.substringBetween(msg, "certType=", "|");
        String certId = StringUtils.substringBetween(msg, "certId=", "|");
        String bankCode = StringUtils.substringBetween(msg, "bankCode=", "|");
        String cardNo = StringUtils.substringBetween(msg, "cardNo=", "|");
        String transAmt = StringUtils.substringBetween(msg, "transAmt=", "|");
        String accountPsw = StringUtils.substringBetween(msg, "accountPsw=",
                "|");
        String pgRecvUrl = StringUtils.substringBetween(msg, "pgRecvUrl=", "|");
        String bgRecvUrl = StringUtils.substringBetween(msg, "bgRecvUrl=", "|");
        String resv = StringUtils.substringBetween(msg, "resv=", "|");

        OnlineDepositShortBean bean = new OnlineDepositShortBean(instuId,
                merchantDate, merchantTime, merchantSeqId, userId,
                userNameText, certType, certId, bankCode, cardNo, transAmt,
                accountPsw, pgRecvUrl, bgRecvUrl, resv);

        return bean;
    }

    public OfflineDepositNotifyBean offlineDepositNotifyReq(String verNum,
            String sysDateTime,
            String instuId,
            String transType,
            String encMsg,
            String checkValue) throws MsgException,
            UnsupportedEncodingException {

        String msg = ZLPayRSAUtil.verifyData(checkValue, encMsg, publicKey);
        String[] msgArray = msg.split("\\|", -1);
        log.info("receive message:" + msg);
        instuId = StringUtils.substringBetween(msg, "instuId=", "|");

        String merchantDate = StringUtils.substringBetween(msg,
                "merchantDate=", "|");
        String merchantTime = StringUtils.substringBetween(msg,
                "merchantTime=", "|");
        String merchantSeqId = StringUtils.substringBetween(msg,
                "merchantSeqId=", "|");
        String userId = StringUtils.substringBetween(msg, "userId=", "|");
        String userNameText = StringUtils.substringBetween(msg,
                "userNameText=", "|");
        String certType = StringUtils.substringBetween(msg, "certType=", "|");
        String certId = StringUtils.substringBetween(msg, "certId=", "|");
        String bankCode = StringUtils.substringBetween(msg, "bankCode=", "|");
        String receibankCode = StringUtils.substringBetween(msg,
                "receibankCode=", "|");
        String cardName = StringUtils.substringBetween(msg, "cardName=", "|");
        String cardNo = StringUtils.substringBetween(msg, "cardNo=", "|");
        String transAmt = StringUtils.substringBetween(msg, "transAmt=", "|");
        String resv = StringUtils.substringBetween(msg, "resv=", "|");

        OfflineDepositNotifyBean bean = new OfflineDepositNotifyBean(instuId,
                merchantDate, merchantTime, merchantSeqId, userId,
                userNameText, certType, certId, bankCode, receibankCode,
                cardName, cardNo, transAmt, resv);

        return bean;
    }
    public WithdrawNotifyBean withdrawNotifyReq(String verNum,
            String sysDateTime,
            String instuId,
            String transType,
            String encMsg,
            String checkValue) throws MsgException,
            UnsupportedEncodingException {

        String msg = ZLPayRSAUtil.verifyData(checkValue, encMsg, publicKey);
        log.info("receive message:" + msg);
        String[] msgArray = msg.split("\\|", -1);
        instuId = StringUtils.substringBetween(msg, "instuId=", "|");

        String merchantDate = StringUtils.substringBetween(msg,
                "merchantDate=", "|");
        String merchantTime = StringUtils.substringBetween(msg,
                "merchantTime=", "|");
        String merchantSeqId = StringUtils.substringBetween(msg,
                "merchantSeqId=", "|");
        String orderDate = StringUtils.substringBetween(msg, "orderDate=", "|");
        String userId = StringUtils.substringBetween(msg, "userId=", "|");
        String userNameText = StringUtils.substringBetween(msg,
                "userNameText=", "|");
        String bankCode = StringUtils.substringBetween(msg, "bankCode=", "|");
        String cardName = StringUtils.substringBetween(msg, "cardName=", "|");
        String cardNo = StringUtils.substringBetween(msg, "cardNo=", "|");
        String transAmt = StringUtils.substringBetween(msg, "transAmt=", "|");
        String resv = StringUtils.substringBetween(msg, "resv=", "|");

        WithdrawNotifyBean bean = new WithdrawNotifyBean(instuId, merchantDate,
                merchantTime, merchantSeqId, orderDate, userId, userNameText,
                bankCode, cardName, cardNo, transAmt, resv);

        return bean;
    }

    public QuerytranstatusBean querytranstatus(String verNum,
            String sysDateTime,
            String instuId,
            String transType,
            String encMsg,
            String checkValue) throws MsgException,
            UnsupportedEncodingException {

        String msg = ZLPayRSAUtil.verifyData(checkValue, encMsg, publicKey);
        log.info("receive message:" + msg);
        String[] msgArray = msg.split("\\|", -1);
        instuId = StringUtils.substringBetween(msg, "instuId=", "|");
        String merchantDate = StringUtils.substringBetween(msg,
                "merchantDate=", "|");
        String merchantTime = StringUtils.substringBetween(msg,
                "merchantTime=", "|");
        String merchantSeqId = StringUtils.substringBetween(msg,
                "merchantSeqId=", "|");
        String merchantLiqDate = StringUtils.substringBetween(msg,
                "merchantLiqDate=", "|");
        String tradeType = StringUtils.substringBetween(msg,
                "tradeTypetradeType=", "|");
        String tiedCardType = StringUtils.substringBetween(msg,
                "tiedCardType=", "|");
        String resv = StringUtils.substringBetween(msg, "resv=", "|");
        QuerytranstatusBean bean = new QuerytranstatusBean(instuId,
                merchantDate, merchantTime, merchantSeqId, merchantLiqDate,
                tradeType, tiedCardType, resv);
        return bean;
    }

    public static ResultBean generateResultBean(String msg) {
        String instuId = StringUtils.substringBetween(msg, "instuId=", "|");
        String merchantDate = StringUtils.substringBetween(msg,
                "merchantDate=", "|");
        String merchantTime = StringUtils.substringBetween(msg,
                "merchantTime=", "|");
        String merchantSeqId = StringUtils.substringBetween(msg,
                "merchantSeqId=", "|");
        String pnrDate = StringUtils.substringBetween(msg, "pnrDate=", "|");
        String pnrTime = StringUtils.substringBetween(msg, "pnrTime=", "|");
        String pnrSeqId = StringUtils.substringBetween(msg, "pnrSeqId=", "|");
        String respCode = StringUtils.substringBetween(msg, "respCode=", "|");
        String respDesc = StringUtils.substringBetween(msg, "respDesc=", "|");
        String resv = StringUtils.substringBetween(msg, "resv=", "|");
        String fundDate = StringUtils.substringBetween(msg, "fundDate=", "|");
        String fundTime = StringUtils.substringBetween(msg, "fundTime=", "|");
        String fundSeqId = StringUtils.substringBetween(msg, "fundSeqId=", "|");
        String userId = StringUtils.substringBetween(msg, "userId=", "|");
        String counterNo = StringUtils.substringBetween(msg, "counterNo=", "|");
        String userNameText = StringUtils.substringBetween(msg,
                "userNameText=", "|");
        String certType = StringUtils.substringBetween(msg, "certType=", "|");
        String certId = StringUtils.substringBetween(msg, "certId=", "|");
        String bankCode = StringUtils.substringBetween(msg, "bankCode=", "|");
        String bankProvinceCode = StringUtils.substringBetween(msg,
                "bankProvinceCode=", "|");
        String bankRegionCode = StringUtils.substringBetween(msg,
                "bankRegionCode=", "|");
        String cardNo = StringUtils.substringBetween(msg, "cardNo=", "|");
        String transAmt = StringUtils.substringBetween(msg, "transAmt=", "|");
        ZLPayResultBean zlPayResultBean = new ZLPayResultBean(instuId, merchantDate,
                merchantTime, merchantSeqId, pnrDate, pnrTime, pnrSeqId,
                respCode, respDesc, resv, fundDate, fundTime, fundSeqId,
                userId, counterNo, userNameText, certType, certId, bankCode,
                bankProvinceCode, bankRegionCode, cardNo, transAmt);
        ResultBean resultBean = new ResultBean(zlPayResultBean);
        if(!"RC00".equals(respCode)){
            resultBean.setResultBool(false);
        }
        resultBean.setErrCode(respCode);
        resultBean.setErrMsg(respDesc);
        
        return resultBean;
    }

    public static MarginSmsBean generateSendMargin(TradeBean trade) {
        Date currentDate = new Date();
        String instuId = ConsUtil.getInstance().cons.getInstuId();
        String merchantDate = new SimpleDateFormat("yyyyMMdd")
                .format(currentDate);
        String merchantTime = new SimpleDateFormat("HHmmss")
                .format(currentDate);
        String merchantSeqId = trade.getOrderId();
        String tradeType = "01";
        String userId = trade.getUserId();
        String userNameText = trade.getAcctName();
        String mobile = trade.getMobile();
        String certType = trade.getCertType();
        String certId = trade.getCertId();
        String bankCode = trade.getBankCode();
        String cardNo = trade.getCardNo();
        String amt = trade.getAmount();
        String resv = trade.getResv();
        return new MarginSmsBean(instuId, merchantDate, merchantTime,
                merchantSeqId, tradeType, userId, userNameText, mobile,
                certType, certId, bankCode, cardNo, amt, resv);
    }

    public static MarginRegisterBean generateMarginRegister(TradeBean trade) {
        Date currentDate = new Date();
        String instuId = ConsUtil.getInstance().cons.getInstuId();;
        String fundDate = new SimpleDateFormat("yyyyMMdd").format(currentDate);
        String fundTime = new SimpleDateFormat("HHmmss").format(currentDate);
        String fundSeqId = OrderNumber.getInstance().generateZLOrderId();
        String counterNo = "123";
        String custType = "0";
        String userNameText = trade.getAcctName();
        String certType = trade.getCertType();
        String certId = trade.getCertId();
        String orgId = "";
        String bankCode = trade.getBankCode();
        String bankProvinceCode = "";
        String bankRegionCode = "";
        String cardNo = trade.getCardNo();
        String payPassWord = "";
        String mobile = trade.getMobile();
        String identifyingCode = trade.getIdentifyingCode();
        String resv = trade.getResv();
        MarginRegisterBean bean = new MarginRegisterBean(instuId, fundDate,
                fundTime, fundSeqId, counterNo, custType, userNameText,
                certType, certId, orgId, bankCode, bankProvinceCode,
                bankRegionCode, cardNo, payPassWord, mobile, identifyingCode,
                resv);
        return bean;
    }

    public static OnlineDepositShortBean generateOnlineDepositShort(TradeBean trade) {
        Date currentDate = new Date();
        String instuId = ConsUtil.getInstance().cons.getInstuId();;
        String merchantDate = new SimpleDateFormat("yyyyMMdd")
                .format(currentDate);
        String merchantTime = new SimpleDateFormat("HHmmss")
                .format(currentDate);
        String merchantSeqId = trade.getOrderId();
        String userId = trade.getUserId();
        String userNameText = trade.getAcctName();
        String certType = trade.getCertType();
        String certId = trade.getCertId();
        String bankCode = trade.getBankCode();
        String cardNo = trade.getCardNo();
        String transAmt = trade.getAmount();
        String accountPsw = "";
        String pgRecvUrl = "";
        String bgRecvUrl = "";
        String resv = trade.getResv();
        OnlineDepositShortBean bean = new OnlineDepositShortBean(instuId,
                merchantDate, merchantTime, merchantSeqId, userId,
                userNameText, certType, certId, bankCode, cardNo, transAmt,
                accountPsw, pgRecvUrl, bgRecvUrl, resv);
        return bean;
    }
    
    public static WithdrawNotifyBean generateWithdrawNotifyBean(TradeBean trade){
    	Date currentDate = new Date();
    	  String instuId = ConsUtil.getInstance().cons.getInstuId();;
          String merchantDate = new SimpleDateFormat("yyyyMMdd")
                  .format(currentDate);
          String merchantTime = new SimpleDateFormat("HHmmss")
                  .format(currentDate);
        String merchantSeqId=trade.getOrderId();
        String orderDate = "";//trade.getOrderDate();
        String userId = trade.getUserId();
        String userNameText = trade.getAcctName();
        String bankCode = trade.getBankCode();
        String cardName= trade.getAcctName();
        String cardNo = trade.getCardNo();
        String transAmt = trade.getAmount();
        String resv = trade.getResv();
        
        return new WithdrawNotifyBean(instuId, merchantDate, merchantTime, merchantSeqId, orderDate, userId, userNameText, bankCode, cardName, cardNo, transAmt, resv);
    }
}
