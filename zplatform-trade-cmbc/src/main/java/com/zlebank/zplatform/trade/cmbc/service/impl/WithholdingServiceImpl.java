/* 
 * WithholdingServiceImpl.java  
 * 
 * version TODO
 *
 * 2015年11月23日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.cmbc.service.impl;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.cmbc.bean.CMBCBillFileRequestBean;
import com.zlebank.zplatform.trade.cmbc.bean.CMBCRealTimeWithholdingBean;
import com.zlebank.zplatform.trade.cmbc.bean.CMBCRealTimeWithholdingQueryBean;
import com.zlebank.zplatform.trade.cmbc.bean.RealNameAuthBean;
import com.zlebank.zplatform.trade.cmbc.bean.RealNameAuthQueryBean;
import com.zlebank.zplatform.trade.cmbc.bean.RealTimeSelfWithholdingBean;
import com.zlebank.zplatform.trade.cmbc.bean.RealTimeSelfWithholdingQueryBean;
import com.zlebank.zplatform.trade.cmbc.bean.RealTimeWithholdingBean;
import com.zlebank.zplatform.trade.cmbc.bean.RealTimeWithholdingQueryBean;
import com.zlebank.zplatform.trade.cmbc.bean.WhiteListBean;
import com.zlebank.zplatform.trade.cmbc.bean.WhiteListQueryBean;
import com.zlebank.zplatform.trade.cmbc.bean.gateway.CardMessageBean;
import com.zlebank.zplatform.trade.cmbc.bean.gateway.WhiteListMessageBean;
import com.zlebank.zplatform.trade.cmbc.bean.gateway.WithholdingMessageBean;
import com.zlebank.zplatform.trade.cmbc.exception.CMBCTradeException;
import com.zlebank.zplatform.trade.cmbc.net.CMBCWithholdingSocketShortClient;
import com.zlebank.zplatform.trade.cmbc.processor.CMBCSelfWithholdingReciveProcessor;
import com.zlebank.zplatform.trade.cmbc.security.RSAHelper;
import com.zlebank.zplatform.trade.cmbc.service.IWithholdingService;
import com.zlebank.zplatform.trade.cmbc.service.socket.selfwithholding.WithholdingSelfLongSocketClient;
import com.zlebank.zplatform.trade.cmbc.service.socket.selfwithholding.WithholdingSelfReciveProcessor;
import com.zlebank.zplatform.trade.cmbc.service.socket.selfwithholding.WithholdingSelfResultProcessor;
import com.zlebank.zplatform.trade.cmbc.service.socket.withholding.CMBCWithholdingReciveProcessor;
import com.zlebank.zplatform.trade.cmbc.service.socket.withholding.CMBCWithholdingResultReciveProcessor;
import com.zlebank.zplatform.trade.cmbc.service.socket.withholding.WithholdingLongSocketClient;
import com.zlebank.zplatform.trade.model.TxnsWithholdingModel;
import com.zlebank.zplatform.trade.service.IChnlDetaService;
import com.zlebank.zplatform.trade.utils.ConsUtil;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月23日 下午2:28:54
 * @since
 */
@Service("withholdingService")
public class WithholdingServiceImpl implements IWithholdingService {
    private static final String ENCODING = "UTF-8";
    private static final Log log = LogFactory.getLog(WithholdingServiceImpl.class);
    public static final String REALNAMEAUTH = "1004    ";
    public static final String REALNAMEAUTHQUERY = "3004    ";
    public static final String WITHHOLDING = "1003    ";
    public static final String WITHHOLDINGQUERY = "3003    ";
    public static final String WHITELIST = "1007    ";
    public static final String WHITELISTQUERY = "3007    ";
    public static final String WITHHOLDINGSELF = "1009    ";
    public static final String WITHHOLDINGQUERYSELF = "3009    ";
    
    @Autowired
    private IChnlDetaService chnlDetaService;
    /**
     *
     * @param json
     * @return
     * @throws CMBCTradeException
     */
    @Override
    public ResultBean realNameAuthentication(String json)
            throws CMBCTradeException {
        // TODO Auto-generated method stub
       /* CardMessageBean card = null;
        try {
            card = JSON.parseObject(json, CardMessageBean.class);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw new CMBCTradeException("");
        }*/
        //RealNameAuthBean realNameAuthBean = new RealNameAuthBean(card);

        return null;
    }
    /**
     *
     * @param json
     * @return
     * @throws CMBCTradeException
     */
    @Override
    public ResultBean realNameAuthentication(CardMessageBean card)
            throws CMBCTradeException {
        RealNameAuthBean realNameAuthBean = new RealNameAuthBean(card);
        String message = realNameAuthBean.toXML();
        log.info("send realNameAuth msg xml:"+message);
        byte[] signMsg = null;
        int signMsg_length = 0;
        String df_signMsg_length = "";
        try {
            signMsg = signMsg(ConsUtil.getInstance().cons.getCmbc_withholding_private_key(),ConsUtil.getInstance().cons.getCmbc_withholding_public_key(), message);
            signMsg_length = signMsg.length;
            DecimalFormat df = new DecimalFormat("0000");
            df_signMsg_length = df.format(signMsg_length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        byte[] cryptedBytes = null;
        int cryptedMsg_length = 0;
        try {
            cryptedBytes = encryptMsg(
                    ConsUtil.getInstance().cons
                    .getCmbc_withholding_private_key(),
            ConsUtil.getInstance().cons
                    .getCmbc_withholding_public_key(), message);
            cryptedMsg_length = cryptedBytes.length;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String merId = getCMBCMerId();
        int totalLenght = merId.length()+REALNAMEAUTH.length()+df_signMsg_length.length()+signMsg_length+cryptedMsg_length;
        DecimalFormat df = new DecimalFormat("00000000");
        String df_totalLenght =df.format(totalLenght);

        String headMsg = df_totalLenght+merId+REALNAMEAUTH+df_signMsg_length;
        log.info("head msg :"+headMsg);
        try {
            byte[] sendBytes =  byteMerger(byteMerger(headMsg.getBytes(ENCODING),signMsg),cryptedBytes);
            WithholdingLongSocketClient client = WithholdingLongSocketClient.getInstance(ConsUtil.getInstance().cons.getCmbc_withholding_ip(), ConsUtil.getInstance().cons.getCmbc_withholding_port(), 30000);
            client.setReceiveProcessor(new CMBCWithholdingReciveProcessor());
            client.sendMessage(sendBytes);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    
    
    
    
    /**
     * 
     * @param priKey
     *            私钥
     * @param message
     *            报文
     * @return
     * @throws Exception
     */
    private byte[] signMsg(String priKey, String pubKey, String message)
            throws Exception {
        if(log.isDebugEnabled()){
            log.debug("priKey:"+priKey);
            log.debug("pubKey:"+pubKey);
            log.debug("message:"+message);
        }
        RSAHelper cipher = new RSAHelper();
        cipher.initKey(priKey, pubKey, 2048);
        byte[] signBytes = cipher.signRSA(message.getBytes(ENCODING), false,ENCODING);
        return signBytes;
    }
    private byte[] encryptMsg(String priKey, String pubKey, String message) throws Exception{
        RSAHelper cipher = new RSAHelper();
        cipher.initKey(priKey, pubKey, 2048);
        byte[] cryptedBytes = cipher.encryptRSA(message.getBytes(ENCODING),
                false, ENCODING);
        return cryptedBytes;
    }
    
    private String getCMBCMerId(){
        String merId = ConsUtil.getInstance().cons.getCmbc_merid();
        int length = 8-merId.length();
        for(int i=0;i<length;i++){
            merId+=" ";
        }
        return merId;
    }
    
    private String getCMBCSelfMerId(){
        String merId = ConsUtil.getInstance().cons.getCmbc_self_merid();
        int length = 15-merId.length();
        for(int i=0;i<length;i++){
            merId+=" ";
        }
        return merId;
    }
   
    public  byte[] byteMerger(byte[] byte_1, byte[] byte_2){  
        byte[] byte_3 = new byte[byte_1.length+byte_2.length];  
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);  
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);  
        return byte_3;  
    }  
    
    
    /**
     *
     * @param withholdingMsg
     * @return
     * @throws CMBCTradeException
     */
    @Override
    public ResultBean realTimeWitholding(WithholdingMessageBean withholdingMsg) throws CMBCTradeException {
    	ResultBean resultBean = null;
        RealTimeWithholdingBean realNameAuthBean = new RealTimeWithholdingBean(withholdingMsg);
        String message = realNameAuthBean.toXML();
        log.info("send realTimeWitholding msg xml :"+message);
        byte[] signMsg = null;
        int signMsg_length = 0;
        String df_signMsg_length = "";
        try {
            signMsg = signMsg(
                    ConsUtil.getInstance().cons
                            .getCmbc_withholding_private_key(),
                    ConsUtil.getInstance().cons
                            .getCmbc_withholding_public_key(), message);
            signMsg_length = signMsg.length;
            DecimalFormat df = new DecimalFormat("0000");
            df_signMsg_length = df.format(signMsg_length);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        byte[] cryptedBytes = null;
        int cryptedMsg_length = 0;
        try {
            cryptedBytes = encryptMsg(
                    ConsUtil.getInstance().cons
                    .getCmbc_withholding_private_key(),
            ConsUtil.getInstance().cons
                    .getCmbc_withholding_public_key(), message);
            cryptedMsg_length = cryptedBytes.length;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String merId = getCMBCMerId();
        int totalLenght = merId.length()+WITHHOLDING.length()+df_signMsg_length.length()+signMsg_length+cryptedMsg_length;
        DecimalFormat df = new DecimalFormat("00000000");
        String df_totalLenght =df.format(totalLenght);

        String headMsg = df_totalLenght+merId+WITHHOLDING+df_signMsg_length;
        log.info("head msg :"+headMsg);
        try {
            byte[] sendBytes =  byteMerger(byteMerger(headMsg.getBytes(ENCODING),signMsg),cryptedBytes);
            log.info("send org msg bytes:"+sendBytes);
            WithholdingLongSocketClient client =  WithholdingLongSocketClient.getInstance(ConsUtil.getInstance().cons.getCmbc_withholding_ip(), ConsUtil.getInstance().cons.getCmbc_withholding_port(), 30000);
            client.setReceiveProcessor(new CMBCWithholdingReciveProcessor());
            client.sendMessage(sendBytes);
            resultBean = new ResultBean("success");
            
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            resultBean = new ResultBean("09", e.getMessage());
        }catch (Exception e) {
        	e.printStackTrace();
        	resultBean = new ResultBean("09", e.getMessage());
		}
        return resultBean;
    }
    /**
     *
     * @param whiteListMsg
     * @return
     * @throws CMBCTradeException
     */
    @Override
    public ResultBean whiteListCollection(WhiteListMessageBean whiteListMsg)
            throws CMBCTradeException {
        WhiteListBean whiteListBean = new WhiteListBean(whiteListMsg);
        String message = whiteListBean.toXML();
        log.info("send whiteListCollection msg xml:"+message);
        byte[] signMsg = null;
        int signMsg_length = 0;
        String df_signMsg_length = "";
        try {
            signMsg = signMsg(
                    ConsUtil.getInstance().cons
                            .getCmbc_withholding_private_key(),
                    ConsUtil.getInstance().cons
                            .getCmbc_withholding_public_key(), message);
            signMsg_length = signMsg.length;
            DecimalFormat df = new DecimalFormat("0000");
            df_signMsg_length = df.format(signMsg_length);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        byte[] cryptedBytes = null;
        int cryptedMsg_length = 0;
        try {
            cryptedBytes = encryptMsg(
                    ConsUtil.getInstance().cons
                    .getCmbc_withholding_private_key(),
            ConsUtil.getInstance().cons
                    .getCmbc_withholding_public_key(), message);
            cryptedMsg_length = cryptedBytes.length;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String merId = getCMBCMerId();
        int totalLenght = merId.length()+WHITELIST.length()+df_signMsg_length.length()+signMsg_length+cryptedMsg_length;
        DecimalFormat df = new DecimalFormat("00000000");
        String df_totalLenght =df.format(totalLenght);

        String headMsg = df_totalLenght+merId+WHITELIST+df_signMsg_length;
        log.info("head msg :"+headMsg);
        try {
            byte[] sendBytes =  byteMerger(byteMerger(headMsg.getBytes(ENCODING),signMsg),cryptedBytes);
            WithholdingLongSocketClient client = WithholdingLongSocketClient.getInstance(ConsUtil.getInstance().cons.getCmbc_withholding_ip(), ConsUtil.getInstance().cons.getCmbc_withholding_port(), 30000);
            client.setReceiveProcessor(new CMBCWithholdingReciveProcessor());
            client.sendMessage(sendBytes);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    /**
     *
     * @param oritransdate
     * @param orireqserialno
     * @return
     * @throws CMBCTradeException
     */
    @Override
    public ResultBean realNameAuthQuery(String oritransdate,
            String orireqserialno) throws CMBCTradeException {
        RealNameAuthQueryBean realNameAuthQueryBean = new RealNameAuthQueryBean(oritransdate, orireqserialno);
        String message = realNameAuthQueryBean.toXML();
        log.info("send msg :"+message);
        byte[] signMsg = null;
        int signMsg_length = 0;
        String df_signMsg_length = "";
        try {
            signMsg = signMsg(
                    ConsUtil.getInstance().cons
                            .getCmbc_withholding_private_key(),
                    ConsUtil.getInstance().cons
                            .getCmbc_withholding_public_key(), message);
            signMsg_length = signMsg.length;
            DecimalFormat df = new DecimalFormat("0000");
            df_signMsg_length = df.format(signMsg_length);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        byte[] cryptedBytes = null;
        int cryptedMsg_length = 0;
        try {
            cryptedBytes = encryptMsg(
                    ConsUtil.getInstance().cons
                    .getCmbc_withholding_private_key(),
            ConsUtil.getInstance().cons
                    .getCmbc_withholding_public_key(), message);
            cryptedMsg_length = cryptedBytes.length;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String merId = getCMBCMerId();
        int totalLenght = merId.length()+REALNAMEAUTHQUERY.length()+df_signMsg_length.length()+signMsg_length+cryptedMsg_length;
        DecimalFormat df = new DecimalFormat("00000000");
        String df_totalLenght =df.format(totalLenght);

        String headMsg = df_totalLenght+merId+REALNAMEAUTHQUERY+df_signMsg_length;
        log.info("head msg :"+headMsg);
        try {
            byte[] sendBytes =  byteMerger(byteMerger(headMsg.getBytes(ENCODING),signMsg),cryptedBytes);
            WithholdingLongSocketClient client = WithholdingLongSocketClient.getInstance(ConsUtil.getInstance().cons.getCmbc_withholding_ip(), ConsUtil.getInstance().cons.getCmbc_withholding_port(), 30000);
            client.setReceiveProcessor(new CMBCWithholdingReciveProcessor());
            client.sendMessage(sendBytes);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    /**
     *
     * @param oritransdate
     * @param orireqserialno
     * @return
     * @throws CMBCTradeException
     */
    @Override
    public ResultBean realTimeWitholdinghQuery(String oritransdate,
            String orireqserialno) throws CMBCTradeException {
        RealTimeWithholdingQueryBean realNameAuthQueryBean = new RealTimeWithholdingQueryBean(oritransdate, orireqserialno);
        String message = realNameAuthQueryBean.toXML();
        log.info("send msg :"+message);
        byte[] signMsg = null;
        int signMsg_length = 0;
        String df_signMsg_length = "";
        try {
            signMsg = signMsg(
                    ConsUtil.getInstance().cons
                            .getCmbc_withholding_private_key(),
                    ConsUtil.getInstance().cons
                            .getCmbc_withholding_public_key(), message);
            signMsg_length = signMsg.length;
            DecimalFormat df = new DecimalFormat("0000");
            df_signMsg_length = df.format(signMsg_length);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        byte[] cryptedBytes = null;
        int cryptedMsg_length = 0;
        try {
            cryptedBytes = encryptMsg(
                    ConsUtil.getInstance().cons
                    .getCmbc_withholding_private_key(),
            ConsUtil.getInstance().cons
                    .getCmbc_withholding_public_key(), message);
            cryptedMsg_length = cryptedBytes.length;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String merId = getCMBCMerId();
        int totalLenght = merId.length()+WITHHOLDINGQUERY.length()+df_signMsg_length.length()+signMsg_length+cryptedMsg_length;
        DecimalFormat df = new DecimalFormat("00000000");
        String df_totalLenght =df.format(totalLenght);

        String headMsg = df_totalLenght+merId+WITHHOLDINGQUERY+df_signMsg_length;
        log.info("head msg :"+headMsg);
        try {
            byte[] sendBytes =  byteMerger(byteMerger(headMsg.getBytes(ENCODING),signMsg),cryptedBytes);
            WithholdingLongSocketClient client = WithholdingLongSocketClient.getInstance(ConsUtil.getInstance().cons.getCmbc_withholding_ip(), ConsUtil.getInstance().cons.getCmbc_withholding_port(), 30000);
            client.setReceiveProcessor(new CMBCWithholdingReciveProcessor());
            client.sendMessage(sendBytes);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    /**
     *
     * @param bankaccno
     * @return
     * @throws CMBCTradeException
     */
    @Override
    public ResultBean whiteListCollectionQuery(String bankaccno)
            throws CMBCTradeException {
        WhiteListQueryBean realNameAuthQueryBean = new WhiteListQueryBean(bankaccno);
        String message = realNameAuthQueryBean.toXML();
        log.info("send msg :"+message);
        byte[] signMsg = null;
        int signMsg_length = 0;
        String df_signMsg_length = "";
        try {
            signMsg = signMsg(
                    ConsUtil.getInstance().cons
                            .getCmbc_withholding_private_key(),
                    ConsUtil.getInstance().cons
                            .getCmbc_withholding_public_key(), message);
            signMsg_length = signMsg.length;
            DecimalFormat df = new DecimalFormat("0000");
            df_signMsg_length = df.format(signMsg_length);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        byte[] cryptedBytes = null;
        int cryptedMsg_length = 0;
        try {
            cryptedBytes = encryptMsg(
                    ConsUtil.getInstance().cons
                    .getCmbc_withholding_private_key(),
            ConsUtil.getInstance().cons
                    .getCmbc_withholding_public_key(), message);
            cryptedMsg_length = cryptedBytes.length;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String merId = getCMBCMerId();
        int totalLenght = merId.length()+WHITELISTQUERY.length()+df_signMsg_length.length()+signMsg_length+cryptedMsg_length;
        DecimalFormat df = new DecimalFormat("00000000");
        String df_totalLenght =df.format(totalLenght);

        String headMsg = df_totalLenght+merId+WHITELISTQUERY+df_signMsg_length;
        log.info("head msg :"+headMsg);
        try {
            byte[] sendBytes =  byteMerger(byteMerger(headMsg.getBytes(ENCODING),signMsg),cryptedBytes);
            WithholdingLongSocketClient client = WithholdingLongSocketClient.getInstance(ConsUtil.getInstance().cons.getCmbc_withholding_ip(), ConsUtil.getInstance().cons.getCmbc_withholding_port(), 30000);
            client.setReceiveProcessor(new CMBCWithholdingReciveProcessor());
            client.sendMessage(sendBytes);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    public ResultBean selfWithholding(){
        CMBCRealTimeWithholdingBean realTimePayBean = new CMBCRealTimeWithholdingBean("");
        String sendMsg = realTimePayBean.toXML();
        log.info("selfWithholding send msg:"+sendMsg);
        CMBCWithholdingSocketShortClient client =  new CMBCWithholdingSocketShortClient(ConsUtil.getInstance().cons.getCmbc_self_withholding_ip(), ConsUtil.getInstance().cons.getCmbc_self_withholding_port(), 90000);
         try {
            client.setReceiveProcessor(new CMBCSelfWithholdingReciveProcessor());
            client.sendMessage(sendMsg.getBytes(ENCODING));
        } catch (CMBCTradeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    public ResultBean selfWithholdingQuery(String ori_tran_date, String ori_tran_id){
        CMBCRealTimeWithholdingQueryBean realTimePayBean = new CMBCRealTimeWithholdingQueryBean(ori_tran_date, ori_tran_id);
        String sendMsg = realTimePayBean.toXML();
        log.info("selfWithholdingQuery send msg:"+sendMsg);
        CMBCWithholdingSocketShortClient client =  new CMBCWithholdingSocketShortClient(ConsUtil.getInstance().cons.getCmbc_self_withholding_ip(), ConsUtil.getInstance().cons.getCmbc_self_withholding_port(), 90000);
         try {
            client.setReceiveProcessor(new CMBCSelfWithholdingReciveProcessor());
            client.sendMessage(sendMsg.getBytes());
        } catch (CMBCTradeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    public ResultBean selfBillFile(String recDate){
        CMBCBillFileRequestBean realTimePayBean = new CMBCBillFileRequestBean(ConsUtil.getInstance().cons.getCmbc_self_merid(),recDate);
        String sendMsg = realTimePayBean.toXML();
        log.info("selfBillFile send msg:"+sendMsg);
        CMBCWithholdingSocketShortClient client =  new CMBCWithholdingSocketShortClient(ConsUtil.getInstance().cons.getCmbc_self_withholding_ip(), ConsUtil.getInstance().cons.getCmbc_self_withholding_port(), 90000);
         try {
            client.setReceiveProcessor(new CMBCSelfWithholdingReciveProcessor());
            client.sendMessage(sendMsg.getBytes());
        } catch (CMBCTradeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public ResultBean realTimeWitholdinghQuery(TxnsWithholdingModel withholding)
            throws CMBCTradeException {
        RealTimeWithholdingQueryBean realNameAuthQueryBean = new RealTimeWithholdingQueryBean(withholding);
        String message = realNameAuthQueryBean.toXML();
        log.info("send msg :"+message);
        byte[] signMsg = null;
        int signMsg_length = 0;
        String df_signMsg_length = "";
        try {
            signMsg = signMsg(
                    ConsUtil.getInstance().cons
                            .getCmbc_withholding_private_key(),
                    ConsUtil.getInstance().cons
                            .getCmbc_withholding_public_key(), message);
            signMsg_length = signMsg.length;
            DecimalFormat df = new DecimalFormat("0000");
            df_signMsg_length = df.format(signMsg_length);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        byte[] cryptedBytes = null;
        int cryptedMsg_length = 0;
        try {
            cryptedBytes = encryptMsg(
                    ConsUtil.getInstance().cons
                    .getCmbc_withholding_private_key(),
            ConsUtil.getInstance().cons
                    .getCmbc_withholding_public_key(), message);
            cryptedMsg_length = cryptedBytes.length;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String merId = getCMBCMerId();
        int totalLenght = merId.length()+WITHHOLDINGQUERY.length()+df_signMsg_length.length()+signMsg_length+cryptedMsg_length;
        DecimalFormat df = new DecimalFormat("00000000");
        String df_totalLenght =df.format(totalLenght);

        String headMsg = df_totalLenght+merId+WITHHOLDINGQUERY+df_signMsg_length;
        log.info("head msg :"+headMsg);
        try {
            byte[] sendBytes =  byteMerger(byteMerger(headMsg.getBytes(ENCODING),signMsg),cryptedBytes);
            WithholdingLongSocketClient client = WithholdingLongSocketClient.getInstance(ConsUtil.getInstance().cons.getCmbc_withholding_ip(), ConsUtil.getInstance().cons.getCmbc_withholding_port(), 30000);
            client.setReceiveProcessor(new CMBCWithholdingReciveProcessor());
            client.sendMessage(sendBytes);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 民生本行实时代扣 
     *
     * @param withholdingMsg
     * @return
     * @throws CMBCTradeException
     */
    @Override
    public ResultBean realTimeWitholdingSelf(WithholdingMessageBean withholdingMsg) throws CMBCTradeException {
        RealTimeSelfWithholdingBean selfWithholding = new RealTimeSelfWithholdingBean(withholdingMsg);
        String message = selfWithholding.toXML();
        log.info("send msg :"+message);
        byte[] signMsg = null;
        int signMsg_length = 0;
        String df_signMsg_length = "";
        try {
            signMsg = signMsg(
                    ConsUtil.getInstance().cons
                            .getCmbc_withholding_self_private_key(),
                    ConsUtil.getInstance().cons
                            .getCmbc_withholding_self_public_key(), message);
            signMsg_length = signMsg.length;
            DecimalFormat df = new DecimalFormat("0000");
            df_signMsg_length = df.format(signMsg_length);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        byte[] cryptedBytes = null;
        int cryptedMsg_length = 0;
        try {
            cryptedBytes = encryptMsg(
                    ConsUtil.getInstance().cons
                    .getCmbc_withholding_self_private_key(),
            ConsUtil.getInstance().cons
                    .getCmbc_withholding_self_public_key(), message);
            cryptedMsg_length = cryptedBytes.length;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String merId = getCMBCSelfMerId();
        int totalLenght = merId.length()+WITHHOLDINGSELF.length()+df_signMsg_length.length()+signMsg_length+cryptedMsg_length;
        DecimalFormat df = new DecimalFormat("00000000");
        String df_totalLenght =df.format(totalLenght);

        String headMsg = df_totalLenght+merId+WITHHOLDINGSELF+df_signMsg_length;
        log.info("head msg :"+headMsg);
        try {
            byte[] sendBytes =  byteMerger(byteMerger(headMsg.getBytes(ENCODING),signMsg),cryptedBytes);
            WithholdingSelfLongSocketClient client = WithholdingSelfLongSocketClient.getInstance(ConsUtil.getInstance().cons.getCmbc_self_withholding_ip(), ConsUtil.getInstance().cons.getCmbc_self_withholding_port(), 30000);
            client.setReceiveProcessor(new WithholdingSelfReciveProcessor());
            client.sendMessage(sendBytes);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 民生本行代扣结果查询
     * @param oritransdate
     * @param orireqserialno
     * @return
     * @throws CMBCTradeException
     */
    public ResultBean realTimeSelfWithholdinghQuery(String oritransdate,
            String orireqserialno) throws CMBCTradeException {
        RealTimeSelfWithholdingQueryBean realTimeSelfWithholdingQueryBean = new RealTimeSelfWithholdingQueryBean(oritransdate, orireqserialno);
        String message = realTimeSelfWithholdingQueryBean.toXML();
        log.info("send msg :"+message);
        byte[] signMsg = null;
        int signMsg_length = 0;
        String df_signMsg_length = "";
        try {
            signMsg = signMsg(
                    ConsUtil.getInstance().cons
                            .getCmbc_withholding_self_private_key(),
                    ConsUtil.getInstance().cons
                            .getCmbc_withholding_self_public_key(), message);
            signMsg_length = signMsg.length;
            DecimalFormat df = new DecimalFormat("0000");
            df_signMsg_length = df.format(signMsg_length);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        byte[] cryptedBytes = null;
        int cryptedMsg_length = 0;
        try {
            cryptedBytes = encryptMsg(
                    ConsUtil.getInstance().cons
                    .getCmbc_withholding_self_private_key(),
            ConsUtil.getInstance().cons
                    .getCmbc_withholding_self_public_key(), message);
            cryptedMsg_length = cryptedBytes.length;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String merId = getCMBCSelfMerId();
        int totalLenght = merId.length()+WITHHOLDINGQUERYSELF.length()+df_signMsg_length.length()+signMsg_length+cryptedMsg_length;
        DecimalFormat df = new DecimalFormat("00000000");
        String df_totalLenght =df.format(totalLenght);

        String headMsg = df_totalLenght+merId+WITHHOLDINGQUERYSELF+df_signMsg_length;
        log.info("head msg :"+headMsg);
        try {
            byte[] sendBytes =  byteMerger(byteMerger(headMsg.getBytes(ENCODING),signMsg),cryptedBytes);
            WithholdingSelfLongSocketClient client = WithholdingSelfLongSocketClient.getInstance(ConsUtil.getInstance().cons.getCmbc_self_withholding_ip(), ConsUtil.getInstance().cons.getCmbc_self_withholding_port(), 30000);
            client.setReceiveProcessor(new WithholdingSelfReciveProcessor());
            client.sendMessage(sendBytes);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public ResultBean realTimeSelfWithholdinghResult(TxnsWithholdingModel withholding) throws CMBCTradeException {
        RealTimeSelfWithholdingQueryBean realTimeSelfWithholdingQueryBean = new RealTimeSelfWithholdingQueryBean(withholding);
        String message = realTimeSelfWithholdingQueryBean.toXML();
        log.info("send msg :"+message);
        byte[] signMsg = null;
        int signMsg_length = 0;
        String df_signMsg_length = "";
        try {
            signMsg = signMsg(
                    ConsUtil.getInstance().cons
                            .getCmbc_withholding_self_private_key(),
                    ConsUtil.getInstance().cons
                            .getCmbc_withholding_self_public_key(), message);
            signMsg_length = signMsg.length;
            DecimalFormat df = new DecimalFormat("0000");
            df_signMsg_length = df.format(signMsg_length);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        byte[] cryptedBytes = null;
        int cryptedMsg_length = 0;
        try {
            cryptedBytes = encryptMsg(
                    ConsUtil.getInstance().cons
                    .getCmbc_withholding_self_private_key(),
            ConsUtil.getInstance().cons
                    .getCmbc_withholding_self_public_key(), message);
            cryptedMsg_length = cryptedBytes.length;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String merId = getCMBCSelfMerId();
        int totalLenght = merId.length()+WITHHOLDINGQUERYSELF.length()+df_signMsg_length.length()+signMsg_length+cryptedMsg_length;
        DecimalFormat df = new DecimalFormat("00000000");
        String df_totalLenght =df.format(totalLenght);

        String headMsg = df_totalLenght+merId+WITHHOLDINGQUERYSELF+df_signMsg_length;
        log.info("head msg :"+headMsg);
        try {
            byte[] sendBytes =  byteMerger(byteMerger(headMsg.getBytes(ENCODING),signMsg),cryptedBytes);
            WithholdingSelfLongSocketClient client = WithholdingSelfLongSocketClient.getInstance(ConsUtil.getInstance().cons.getCmbc_self_withholding_ip(), ConsUtil.getInstance().cons.getCmbc_self_withholding_port(), 30000);
            client.setReceiveProcessor(new WithholdingSelfResultProcessor());
            client.sendMessage(sendBytes);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
   
    
    public ResultBean realTimeWitholdinghResult(TxnsWithholdingModel withholding) throws CMBCTradeException {
        RealTimeWithholdingQueryBean realTimeWithholdingQueryBean = new RealTimeWithholdingQueryBean(withholding);
        String message = realTimeWithholdingQueryBean.toXML();
        log.info("send msg :"+message);
        byte[] signMsg = null;
        int signMsg_length = 0;
        String df_signMsg_length = "";
        try {
            signMsg = signMsg(
                    ConsUtil.getInstance().cons
                            .getCmbc_withholding_private_key(),
                    ConsUtil.getInstance().cons
                            .getCmbc_withholding_public_key(), message);
            signMsg_length = signMsg.length;
            DecimalFormat df = new DecimalFormat("0000");
            df_signMsg_length = df.format(signMsg_length);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        byte[] cryptedBytes = null;
        int cryptedMsg_length = 0;
        try {
            cryptedBytes = encryptMsg(
                    ConsUtil.getInstance().cons
                    .getCmbc_withholding_private_key(),
            ConsUtil.getInstance().cons
                    .getCmbc_withholding_public_key(), message);
            cryptedMsg_length = cryptedBytes.length;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String merId = getCMBCMerId();
        int totalLenght = merId.length()+WITHHOLDINGQUERY.length()+df_signMsg_length.length()+signMsg_length+cryptedMsg_length;
        DecimalFormat df = new DecimalFormat("00000000");
        String df_totalLenght =df.format(totalLenght);

        String headMsg = df_totalLenght+merId+WITHHOLDINGQUERY+df_signMsg_length;
        log.info("head msg :"+headMsg);
        try {
            byte[] sendBytes =  byteMerger(byteMerger(headMsg.getBytes(ENCODING),signMsg),cryptedBytes);
            WithholdingLongSocketClient client = WithholdingLongSocketClient.getInstance(ConsUtil.getInstance().cons.getCmbc_withholding_ip(), ConsUtil.getInstance().cons.getCmbc_withholding_port(), 30000);
            client.setReceiveProcessor(new CMBCWithholdingResultReciveProcessor());
            client.sendMessage(sendBytes);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
