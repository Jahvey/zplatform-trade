/* 
 * CMBCInsteadPayReciveProcessor.java  
 * 
 * version TODO
 *
 * 2015年11月26日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.cmbc.processor;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.zlebank.zplatform.trade.cmbc.bean.RealTimePayResultBean;
import com.zlebank.zplatform.trade.cmbc.bean.RealTimeQueryResultBean;
import com.zlebank.zplatform.trade.cmbc.net.ReceiveProcessor;
import com.zlebank.zplatform.trade.cmbc.security.CMBCAESUtils;
import com.zlebank.zplatform.trade.cmbc.service.socket.withholding.CMBCWithholdingReciveProcessor;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月26日 上午10:26:05
 * @since 
 */
public class CMBCInsteadPayReciveProcessor implements ReceiveProcessor{
    private static final String ENCODING = "UTF-8";
    private static final Log log = LogFactory.getLog(CMBCWithholdingReciveProcessor.class);
    
    private enum MessageType{
        /**
         *  1002    实时代付请求
            3002    账务交易结果查询请求
         */
        REQUEST_INSTEADPAY ("1002"),
        RESULT_INSTEADPAY ("3002"),
        UNKNOW("");
        
        private String code;
        private MessageType(String code) {
            this.code = code;
        }
        public static MessageType fromValue(String value){
            for(MessageType messageType:values()){
                if(messageType.code.equals(value)){
                    return messageType;
                }
            }
            return UNKNOW;
        }
    }
    /**
     *
     * @param data
     */
    @Override
    public void onReceive(Object data) {
        // TODO Auto-generated method stub
        byte[] rawData = (byte[]) data;
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(rawData));
        //服务码      报文体                      密钥识别码
        //定长15位   根据实际报文，变长   定长32位
        byte[] serviceCode = new byte[15];
        byte[] message = new byte[rawData.length-15-32];
        byte[] sign = new byte[32];
        try {
            XStream xstream = new XStream(new DomDriver(null,new XmlFriendlyNameCoder("_-", "_")));
            inputStream.read(serviceCode);
            MessageType messageType = MessageType.fromValue(new String(serviceCode,ENCODING).trim());
            inputStream.read(message);
            inputStream.read(sign);
            String xml = new String(message,ENCODING);
            log.info("recive xml msg:"+xml);
            String sign_xml = new String(sign,ENCODING).toUpperCase();
            log.info("sign_xml:"+sign_xml);
            String sign_ =  CMBCAESUtils.encodeMD5(xml).toUpperCase();;
            log.info("sign_:"+sign_);
            if(sign_xml.equals(sign_)){
                log.info("verfiy result:"+true);
            }else{
                log.info("verfiy result:"+false);
            }
            switch (messageType) {
                case REQUEST_INSTEADPAY :
                    xstream.processAnnotations(RealTimePayResultBean.class);
                    xstream.autodetectAnnotations(true);
                    RealTimePayResultBean realTimePayResultBean = (RealTimePayResultBean) xstream.fromXML(xml);
                    log.info("recive response insteadpay message:"+JSON.toJSONString(realTimePayResultBean));
                    break;
                case RESULT_INSTEADPAY :
                    xstream.processAnnotations(RealTimeQueryResultBean.class);
                    xstream.autodetectAnnotations(true);
                    RealTimeQueryResultBean realTimeQueryResultBean = (RealTimeQueryResultBean) xstream.fromXML(xml);
                    log.info("recive response insteadpay message:"+JSON.toJSONString(realTimeQueryResultBean));
                    break;
				default:
					break;
            }
            
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
