/* 
 * ZlTradeServicImpl.java  
 * 
 * version TODO
 *
 * 2015年8月21日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.zlpay.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpException;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.zlpay.MarginRegisterBean;
import com.zlebank.zplatform.trade.bean.zlpay.MarginSmsBean;
import com.zlebank.zplatform.trade.bean.zlpay.OfflineDepositNotifyBean;
import com.zlebank.zplatform.trade.bean.zlpay.OnlineDepositShortBean;
import com.zlebank.zplatform.trade.bean.zlpay.QuerytranstatusBean;
import com.zlebank.zplatform.trade.bean.zlpay.WithdrawNotifyBean;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.HttpRequestParam;
import com.zlebank.zplatform.trade.utils.HttpUtils;
import com.zlebank.zplatform.trade.utils.ObjectDynamic;
import com.zlebank.zplatform.trade.zlpay.analyzer.ZlPayTradeAnalyzer;
import com.zlebank.zplatform.trade.zlpay.service.IZlTradeService;
import com.zlink.zlpay.commonofs.common.ZlpayUtil;
import com.zlink.zlpay.commonofs.common.exception.MsgException;
import com.zlink.zlpay.commonofs.common.util.Constants;


/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年8月21日 上午11:27:09
 * @since 
 */
@Service("zlTradeService")
public class ZlTradeServicImpl implements IZlTradeService{
    private static final Log log = LogFactory.getLog(ZlTradeServicImpl.class);
   
    /**
     *
     * @param marginSmsBean
     * @return
     */
    @Override
    public ResultBean sendMarginSms(MarginSmsBean marginSmsBean) {
        ResultBean resultBean = null;
        try {
            String sendMessage = ObjectDynamic.generateZLPayParamer(marginSmsBean, false);
            log.info("send date:" + sendMessage);
            // 对数据加密
            String encryptData = ZlpayUtil.encryptData(sendMessage, ConsUtil.getInstance().cons.getKeyStorePath_zlrt());
            log.info(encryptData);
            // 对数据加签
            String signData = ZlpayUtil.signData(sendMessage, ConsUtil.getInstance().cons.getKeyStorePath_zlrt());
            //log.info(signData);
            String responseMsg = sendMsg(ConsUtil.getInstance().cons.getInstuId(), "2305", encryptData,signData, ConsUtil.getInstance().cons.getSendMarginSms_url());
            log.info("responseMsg:"+responseMsg);
            // 解析响应信息
            String responseStr = ZlpayUtil.parseResponse(responseMsg,ConsUtil.getInstance().cons.getCerPath_zlrt());
            log.info("receive message:"+responseStr);
            resultBean = ZlPayTradeAnalyzer.generateResultBean(responseStr);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultBean;
    }

    /**
     *
     * @param marginRegisterBean
     * @return
     */
    @Override
    public ResultBean marginRegisterReq(MarginRegisterBean marginRegisterBean) {
        ResultBean resultBean = null;
        
        try {
            String sendMessage = ObjectDynamic.generateZLPayParamer(marginRegisterBean, false);
            log.info("send date:" + sendMessage);
            // 对数据加密
            String encryptData = ZlpayUtil.encryptData(sendMessage, ConsUtil.getInstance().cons.getKeyStorePath_zlrt());
            log.info(encryptData);
            // 对数据加签
            String signData = ZlpayUtil.signData(sendMessage, ConsUtil.getInstance().cons.getKeyStorePath_zlrt());
            //log.info(signData);
             String responseMsg = sendMsg(ConsUtil.getInstance().cons.getInstuId(), "108", encryptData,signData, ConsUtil.getInstance().cons.getMarginRegisterReq_url());
             log.info("responseMsg:"+responseMsg);
            // 解析响应信息
            String responseStr = ZlpayUtil.parseResponse(responseMsg,ConsUtil.getInstance().cons.getCerPath_zlrt());
            log.info("receive message:"+responseStr);
            resultBean = ZlPayTradeAnalyzer.generateResultBean(responseStr);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return resultBean;
    }

    /**
     *
     * @param onlineDepositShortBean
     * @return
     */
    @Override
    public ResultBean onlineDepositShort(OnlineDepositShortBean onlineDepositShortBean) {
        ResultBean resultBean = null;
        try {
            String sendMessage = ObjectDynamic.generateZLPayParamer(onlineDepositShortBean, false);
            log.info("send date:" + sendMessage);
            // 对数据加密
            String encryptData = ZlpayUtil.encryptData(sendMessage, ConsUtil.getInstance().cons.getKeyStorePath_zlrt());
            log.info(encryptData);
            // 对数据加签
            String signData = ZlpayUtil.signData(sendMessage, ConsUtil.getInstance().cons.getKeyStorePath_zlrt());
            //log.info(signData);
             String responseMsg = sendMsg(ConsUtil.getInstance().cons.getInstuId(), "2104", encryptData,signData, ConsUtil.getInstance().cons.getOnlineDepositShort_url());
             log.info("responseMsg:"+responseMsg);
            // 解析响应信息
            String responseStr = ZlpayUtil.parseResponse(responseMsg,ConsUtil.getInstance().cons.getCerPath_zlrt());
            log.info("receive message:"+responseStr);
            resultBean = ZlPayTradeAnalyzer.generateResultBean(responseStr);
        } catch (Exception e) {
            log.error(e);
        }
        return resultBean;
    }

    /**
     *
     * @param offlineDepositNotifyBean
     * @return
     */
    @Override
    public ResultBean offlineDepositNotifyReq(OfflineDepositNotifyBean offlineDepositNotifyBean) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     *
     * @param withdrawNotifyBean
     * @return
     */
    @Override
    public ResultBean withdrawNotifyReq(WithdrawNotifyBean withdrawNotifyBean) {
    	ResultBean resultBean = null;
        try {
            String sendMessage = ObjectDynamic.generateZLPayParamer(withdrawNotifyBean, false);
            log.info("ZLPAY send date:" + sendMessage);
            // 对数据加密
            String encryptData = ZlpayUtil.encryptData(sendMessage, ConsUtil.getInstance().cons.getKeyStorePath_zlrt());
            log.info(encryptData);
            // 对数据加签
            String signData = ZlpayUtil.signData(sendMessage, ConsUtil.getInstance().cons.getKeyStorePath_zlrt());
            //log.info(signData);
            String responseMsg = sendMsg(ConsUtil.getInstance().cons.getInstuId(), "2106", encryptData,signData, ConsUtil.getInstance().cons.getOnlineDepositShort_url());
            log.info("ZLPAY responseMsg:"+responseMsg);
            // 解析响应信息
            String responseStr = ZlpayUtil.parseResponse(responseMsg,ConsUtil.getInstance().cons.getCerPath_zlrt());
            log.info("ZLPAY receive message:"+responseStr);
            resultBean = ZlPayTradeAnalyzer.generateResultBean(responseStr);
        } catch (Exception e) {
            log.error(e);
        }
        return resultBean;
    }

    /**
     *
     * @param querytranstatusBean
     * @return
     */
    @Override
    public ResultBean querytranstatus(QuerytranstatusBean querytranstatusBean) {
    	ResultBean resultBean = null;
        try {
            String sendMessage = ObjectDynamic.generateZLPayParamer(querytranstatusBean, false);
            log.info("ZLPAY send date:" + sendMessage);
            // 对数据加密
            String encryptData = ZlpayUtil.encryptData(sendMessage, ConsUtil.getInstance().cons.getKeyStorePath_zlrt());
            log.info(encryptData);
            // 对数据加签
            String signData = ZlpayUtil.signData(sendMessage, ConsUtil.getInstance().cons.getKeyStorePath_zlrt());
            //log.info(signData);
            String responseMsg = sendMsg(ConsUtil.getInstance().cons.getInstuId(), "2304", encryptData,signData, ConsUtil.getInstance().cons.getOnlineDepositShort_url());
            log.info("ZLPAY responseMsg:"+responseMsg);
            // 解析响应信息
            String responseStr = ZlpayUtil.parseResponse(responseMsg,ConsUtil.getInstance().cons.getCerPath_zlrt());
            log.info("ZLPAY receive message:"+responseStr);
            resultBean = ZlPayTradeAnalyzer.generateResultBean(responseStr);
        } catch (Exception e) {
            log.error(e);
        }
        return resultBean;
    }

    /**
     * @description 发送请求业务并返回响应
     * @param instuId
     * @param transType
     * @param encMsg
     * @param checkValue
     * @return
     * @throws MsgException
     * @author ZhangDM(Mingly)
     * @date 2012-10-22
     */
    @SuppressWarnings("unused")
    private String sendMsg(String instuId, String transType, String encMsg,
            String checkValue, String sendUrl) throws MsgException {

        try {
            log.info("sendUrl:"+sendUrl);
            List<HttpRequestParam> params = bulidParam(instuId, transType,
                    URLEncoder.encode(encMsg, "UTF-8"), URLEncoder.encode(
                            checkValue, "UTF-8"));
            HttpUtils http = new HttpUtils();
            http.openConnection();
            String responseMsg = http.executeHttpPost(sendUrl, params,
                    Constants.ENCODING);
            log.info("response message:" + responseMsg);
            http.closeConnection();
            if ("".equals(responseMsg) || responseMsg == null) {
                log.info("连接网络超时！请稍后查询交易记录");
                throw new MsgException(Constants.ERROR_SENDFAIL);
            }
            return responseMsg;
        } catch (HttpException e) {
            log.info(e.getMessage()+",URL="+sendUrl);
            throw new MsgException(Constants.ERROR_SENDFAIL);
        } catch (UnsupportedEncodingException e) {
            log.info(e.getMessage());
            throw new MsgException(Constants.ERROR_SENDFAIL);
        }

    }

    /**
     * @description 组装发送参数
     * @param instuId
     * @param transType
     * @param encMsg
     * @param checkValue
     * @return
     * @author ZhangDM(Mingly)
     * @date 2012-10-21
     */
    private List<HttpRequestParam> bulidParam(String instuId, String transType,
            String encMsg, String checkValue) {

        HttpRequestParam verNumParam = new HttpRequestParam();
        verNumParam.setParaName(Constants.VERNUM);
        verNumParam.setParaValue(Constants.VERNUM_VALUE);
        log.info("verNum:" + Constants.VERNUM_VALUE);
        HttpRequestParam sysDateTimeParam = new HttpRequestParam();
        sysDateTimeParam.setParaName(Constants.SYSDATETIME);
        String currentDate = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date());
        sysDateTimeParam.setParaValue(currentDate);
        log.info("sysDateTime:" + currentDate);
        HttpRequestParam instuIdParam = new HttpRequestParam();
        instuIdParam.setParaName(Constants.INSTUID);
        instuIdParam.setParaValue(instuId);
        log.info("instuId:" + instuId);
        HttpRequestParam transTypeParam = new HttpRequestParam();
        transTypeParam.setParaName(Constants.TRANSTYPE);
        transTypeParam.setParaValue(transType);
        log.info("transType:" + transType);
        HttpRequestParam encMsgParam = new HttpRequestParam();
        encMsgParam.setParaName(Constants.ENCMSG);
        encMsgParam.setParaValue(encMsg);
        log.info("encMsg:" + encMsg);
        HttpRequestParam checkValueParam = new HttpRequestParam();
        checkValueParam.setParaName(Constants.CHECKVALUE);
        checkValueParam.setParaValue(checkValue);
        log.info("checkValue:" + checkValue);

        List<HttpRequestParam> params = new ArrayList<HttpRequestParam>();
        params.add(verNumParam);
        params.add(sysDateTimeParam);
        params.add(instuIdParam);
        params.add(transTypeParam);
        params.add(encMsgParam);
        params.add(checkValueParam);

        return params;
    }

	
}
