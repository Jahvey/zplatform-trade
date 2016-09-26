/* 
 * RealNameAuthProcessor.java  
 * 
 * version TODO
 *
 * 2015年11月23日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.cmbc.service.socket.withholding;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.enums.ChnlTypeEnum;
import com.zlebank.zplatform.trade.cmbc.bean.RealNameAuthQueryResultBean;
import com.zlebank.zplatform.trade.cmbc.bean.RealTimeWithholdingQueryResultBean;
import com.zlebank.zplatform.trade.cmbc.bean.WhiteListQueryResultBean;
import com.zlebank.zplatform.trade.cmbc.net.ReceiveProcessor;
import com.zlebank.zplatform.trade.cmbc.quickpay.CMBCQueryTradeThread;
import com.zlebank.zplatform.trade.cmbc.quickpay.CMBCQueryTradeThreadPool;
import com.zlebank.zplatform.trade.cmbc.security.RSAHelper;
import com.zlebank.zplatform.trade.cmbc.service.CMBCCrossLineQuickPayService;
import com.zlebank.zplatform.trade.dao.ITxnsOrderinfoDAO;
import com.zlebank.zplatform.trade.dao.RspmsgDAO;
import com.zlebank.zplatform.trade.model.PojoRspmsg;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsWithholdingModel;
import com.zlebank.zplatform.trade.service.ITradeReceiveProcessor;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.ITxnsWithholdingService;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.DateUtil;
import com.zlebank.zplatform.trade.utils.SpringContext;
import com.zlebank.zplatform.trade.utils.UUIDUtil;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月23日 下午5:31:36
 * @since 
 */
public class CMBCWithholdingResultReciveProcessor implements ReceiveProcessor{
    private static final String ENCODING = "UTF-8";
    private static final Log log = LogFactory.getLog(CMBCWithholdingResultReciveProcessor.class);
    private enum MessageType{
        /**
         *  1003    实时代扣请求
            3003    实时跨行代扣结果查询
            1004    实名身份认证
            3004    实名身份认证结果查询
            1007    白名单采集
            3007    白名单查询
         */
        
        RESULT_WITHHOLDING ("3003"),
        
        RESULT_REALNAME ("3004"),
        
        RESULT_WHITELIST ("3007"),
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
    private ITxnsWithholdingService txnsWithholdingService;
    private ITradeReceiveProcessor tradeReceiveProcessor;
    private ITxnsOrderinfoDAO txnsOrderinfoDAO;
    private ITxnsLogService txnsLogService;
    private RspmsgDAO rspmsgDAO;
    private CMBCCrossLineQuickPayService cmbcCrossLineQuickPayService;
    /** 
     *
     * @param data
     */
    @Override
    public void onReceive(Object data) {
        // 8位合作方编号  8位交易码   4位签名域长度 签名域值    XML报文数据主体密文
        byte[] rawData = (byte[]) data;
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(rawData));
        byte[] merId = new byte[8];
        byte[] serviceCode=new byte[8];
        byte[] sign_length = new byte[4];
        try {
            inputStream.read(merId);
            inputStream.read(serviceCode);
            inputStream.read(sign_length);
            int length = Integer.valueOf(new String(sign_length,ENCODING));
            byte[] sign = new byte[length];
            inputStream.read(sign);
            int msg_length = rawData.length-8-8-4-length;
            byte[] cryptedBytes = new byte[msg_length];
            inputStream.read(cryptedBytes);
            
            MessageType msgType = MessageType.fromValue(new String(serviceCode,ENCODING).trim());
            //解密
            byte[] decryptedBytes = decryptMsg(ConsUtil.getInstance().cons.getCmbc_withholding_private_key(), ConsUtil.getInstance().cons.getCmbc_withholding_public_key(), cryptedBytes);
            String msg = new String(decryptedBytes, ENCODING);
            log.info("recive xml:"+msg);
            XStream xstream = new XStream(new DomDriver(null,new XmlFriendlyNameCoder("_-", "_")));
            boolean verifyFlag=false;
            switch (msgType) {
                case RESULT_REALNAME:
                    xstream.processAnnotations(RealNameAuthQueryResultBean.class);
                    xstream.autodetectAnnotations(true);
                    RealNameAuthQueryResultBean realNameAuthQueryResultBean =  (RealNameAuthQueryResultBean) xstream.fromXML(msg);
                    log.info("receive msg:"+JSON.toJSONString(realNameAuthQueryResultBean));
                    verifyFlag = verifyMsg(ConsUtil.getInstance().cons.getCmbc_withholding_private_key(), ConsUtil.getInstance().cons.getCmbc_withholding_public_key(), msg, sign);
                    log.info("verify result"+verifyFlag);
                    if(verifyFlag){
                        
                    }
                    break;
                case RESULT_WITHHOLDING:
                    xstream.processAnnotations(RealTimeWithholdingQueryResultBean.class);
                    xstream.autodetectAnnotations(true);
                    RealTimeWithholdingQueryResultBean timeWithholdingQueryResultBean =  (RealTimeWithholdingQueryResultBean) xstream.fromXML(msg);
                    log.info("receive msg:"+JSON.toJSONString(timeWithholdingQueryResultBean));
                    verifyFlag = verifyMsg(ConsUtil.getInstance().cons.getCmbc_withholding_private_key(), ConsUtil.getInstance().cons.getCmbc_withholding_public_key(), msg, sign);
                    log.info("verify result"+verifyFlag);
                    if(verifyFlag){
                        //更新交易流水
                        TxnsWithholdingModel withholding = txnsWithholdingService.getWithholdingBySerialNo(timeWithholdingQueryResultBean.getReqserialno());
                        withholding.setExectype(timeWithholdingQueryResultBean.getExectype());
                        withholding.setExeccode(timeWithholdingQueryResultBean.getExeccode());
                        withholding.setExecmsg(timeWithholdingQueryResultBean.getExecmsg());
                        withholding.setSerialno(timeWithholdingQueryResultBean.getReqserialno());
                        withholding.setOriexectype(timeWithholdingQueryResultBean.getOriexectype());
                        withholding.setOriexeccode(timeWithholdingQueryResultBean.getOriexeccode());
                        withholding.setOriexecmsg(timeWithholdingQueryResultBean.getOriexecmsg());
                        txnsWithholdingService.updateWhithholding(withholding);
                        if(withholding.getOriexectype().equals("S")){
                            ResultBean resultBean = new ResultBean(withholding);
                            TradeBean tradeBean = new TradeBean();
                            tradeBean.setTxnseqno(withholding.getTxnseqno());
                            cmbcCrossLineQuickPayService.dealWithAccounting(tradeBean.getTxnseqno(), resultBean);
                            //tradeReceiveProcessor.onReceive(resultBean, tradeBean, TradeTypeEnum.ACCOUNTING);
                        }else if(withholding.getExectype().equals("E")){
                            saveFailedCMBCTrade(withholding);
                        }else{
                            String ori_tran_date = withholding.getTransdate();
                            String ori_tran_id = withholding.getOrireqserialno();
                            String txnseqno = withholding.getTxnseqno();
                            CMBCQueryTradeThreadPool.getInstance().executeMission(new CMBCQueryTradeThread(ori_tran_date, ori_tran_id, txnseqno));
                        }
                        
                        
                    }
                    break;
                case RESULT_WHITELIST :
                    xstream.processAnnotations(WhiteListQueryResultBean.class);
                    xstream.autodetectAnnotations(true);
                    WhiteListQueryResultBean whiteListQueryResultBean =  (WhiteListQueryResultBean) xstream.fromXML(msg);
                    log.info("receive msg:"+JSON.toJSONString(whiteListQueryResultBean));
                    verifyFlag = verifyMsg(ConsUtil.getInstance().cons.getCmbc_withholding_private_key(), ConsUtil.getInstance().cons.getCmbc_withholding_public_key(), msg, sign);
                    log.info("verify result"+verifyFlag);
                    if(verifyFlag){
                        
                    }
                    break;
			default:
				break;
            }
            
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
   
    private boolean verifyMsg(String priKey, String pubKey, String message,byte[] signBytes) throws Exception {
        RSAHelper cipher = new RSAHelper();
        cipher.initKey(priKey, pubKey, 2048);
        boolean isValid = cipher.verifyRSA(message.getBytes("UTF-8"),signBytes, false, "UTF-8");
        return isValid;
    }
    private byte[] decryptMsg(String priKey, String pubKey, byte[] cryptedBytes) throws Exception{
        RSAHelper cipher = new RSAHelper();
        cipher.initKey(priKey, pubKey, 2048);
        byte[] decryptedBytes = cipher.decryptRSA(cryptedBytes, false, "UTF-8");
        return decryptedBytes;
    }


    public CMBCWithholdingResultReciveProcessor() {
        txnsWithholdingService=(ITxnsWithholdingService) SpringContext.getContext().getBean("txnsWithholdingService");
        tradeReceiveProcessor=  (ITradeReceiveProcessor) SpringContext.getContext().getBean("cmbcQuickReceiveProcessor");
        txnsOrderinfoDAO = (ITxnsOrderinfoDAO) SpringContext.getContext().getBean("txnsOrderinfo");
        txnsLogService = (ITxnsLogService) SpringContext.getContext().getBean("txnsLogService");
        rspmsgDAO = (RspmsgDAO) SpringContext.getContext().getBean("rspmsgDAO");
        cmbcCrossLineQuickPayService = (CMBCCrossLineQuickPayService) SpringContext.getContext().getBean("cmbcCrossLineQuickPayService");
    }
    
    public void saveFailedCMBCTrade(TxnsWithholdingModel withholding){
        TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(withholding.getTxnseqno());
        txnsLog.setPayordfintime(DateUtil.getCurrentDateTime());
        PojoRspmsg msg = rspmsgDAO.getRspmsgByChnlCode(ChnlTypeEnum.CMBCWITHHOLDING, withholding.getExeccode());
        if(msg!=null){
            txnsLog.setRetcode(msg.getWebrspcode());
            txnsLog.setRetinfo(msg.getRspinfo());
        }else{
            txnsLog.setRetcode("0052");
            txnsLog.setRetinfo("交易失败，系统忙，请稍后再试！");
        }
        txnsLog.setRetdatetime(DateUtil.getCurrentDateTime());
        txnsLog.setTradestatflag("00000001");
        txnsLog.setTradetxnflag("10000000");
        txnsLog.setRelate("10000000");
        txnsLog.setTradeseltxn(UUIDUtil.uuid());
        txnsLog.setPayrettsnseqno(withholding.getPayserialno());
        txnsLog.setPayretcode(withholding.getExeccode());
        txnsLog.setPayretinfo(withholding.getExecmsg());
        txnsLogService.updateTxnsLog(txnsLog);
        //订单状态更新为失败
        txnsOrderinfoDAO.updateOrderToFail(txnsLog.getAccordno(),txnsLog.getAccfirmerno());
        
    }

}
