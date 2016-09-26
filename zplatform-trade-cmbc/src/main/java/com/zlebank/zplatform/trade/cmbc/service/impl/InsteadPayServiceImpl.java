/* 
 * InsteadPayServiceImpl.java  
 * 
 * version TODO
 *
 * 2015年11月3日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.cmbc.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.trade.bean.UpdateData;
import com.zlebank.zplatform.trade.bean.cmbc.ReexchangeBean;
import com.zlebank.zplatform.trade.bean.enums.InsteadPayTypeEnum;
import com.zlebank.zplatform.trade.cmbc.bean.InsteadPayBean;
import com.zlebank.zplatform.trade.cmbc.bean.RealTimePayBean;
import com.zlebank.zplatform.trade.cmbc.bean.RealTimeQueryBean;
import com.zlebank.zplatform.trade.cmbc.bean.enmus.FileTypeEnmu;
import com.zlebank.zplatform.trade.cmbc.bean.gateway.InsteadPayMessageBean;
import com.zlebank.zplatform.trade.cmbc.exception.CMBCTradeException;
import com.zlebank.zplatform.trade.cmbc.net.BaseSocketLongClient;
import com.zlebank.zplatform.trade.cmbc.processor.CMBCInsteadPayReciveProcessor;
import com.zlebank.zplatform.trade.cmbc.security.CMBCAESUtils;
import com.zlebank.zplatform.trade.cmbc.service.IFTPCMBCService;
import com.zlebank.zplatform.trade.cmbc.service.IInsteadPayService;
import com.zlebank.zplatform.trade.dao.BankTransferBatchDAO;
import com.zlebank.zplatform.trade.dao.BankTransferDataDAO;
import com.zlebank.zplatform.trade.dao.CMBCResfileLogDAO;
import com.zlebank.zplatform.trade.dao.ITxnsInsteadPayDAO;
import com.zlebank.zplatform.trade.dao.TransferBatchDAO;
import com.zlebank.zplatform.trade.dao.TransferDataDAO;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.model.PojoBankTransferBatch;
import com.zlebank.zplatform.trade.model.PojoBankTransferData;
import com.zlebank.zplatform.trade.model.PojoCMBCResfileLog;
import com.zlebank.zplatform.trade.model.PojoTranData;
import com.zlebank.zplatform.trade.model.PojoTxnsInsteadPay;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.ObserverListService;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.OrderNumber;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月3日 上午11:53:14
 * @since
 */
@Service("insteadPayService")
public class InsteadPayServiceImpl implements IInsteadPayService {
    private static final Log log = LogFactory.getLog(InsteadPayServiceImpl.class);
    private static final String Key = ConsUtil.getInstance().cons.getCmbc_insteadpay_batch_md5(); //"1234567887654321";
    private static String SecretFilePath = ConsUtil.getInstance().cons.getCmbc_secretfilepath();//"D:\\cmbc\\secret.txt";
    private static final String ENCODE = "GBK";
    @Autowired
    private IFTPCMBCService ftpcmbcService;
    @Autowired
    private TransferDataDAO transferDataDAO;
    @Autowired
    private TransferBatchDAO transferBatchDAO;
    @Autowired
    private ITxnsLogService txnsLogService;
    
    @Autowired
    private BankTransferBatchDAO bankTransferBatchDAO;
    @Autowired
    private BankTransferDataDAO bankTransferDataDAO;
    @Autowired
    private ITxnsInsteadPayDAO txnsInsteadPayDAO;
    @Autowired
    private CMBCResfileLogDAO cmbcResfileLogDAO;
    
    /**
     * 批量代付(跨行)
     * 
     * @param batchNo
     *            批次号
     * @throws IOException 
     * @throws TradeException 
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void batchOuterPay(Long batchNo) throws CMBCTradeException, IOException, TradeException {
        //List<InsteadPayBean> payList = initBatchData();
    	PojoBankTransferBatch transferBatch = bankTransferBatchDAO.getByBankTranBatchNo(batchNo);
     	//PojoTransferBatch transferBatch = transferBatchDAO.getByBatchNo(batchNo);
        List<PojoBankTransferData> transferDataList =  bankTransferDataDAO.findTransDataByBatchNo(batchNo);
        //判断代付交易流水中师傅已经上传过文件
        if(txnsInsteadPayDAO.isUpload(batchNo+"")){
        	throw new TradeException("T000","代付流水文件已上传");
        }
        Long sumAmt = 0L;
        Long sumItem = 0L;
        StringBuffer bodyMsg = new StringBuffer();
        for (PojoBankTransferData bean : transferDataList) {
        	if(!bean.getStatus().equals("02")){//不是等待转账状态的，不宜与代付
        		continue;
        	}
            //第三方流水号|帐号|户名|支付行号|开户行名称|金额|摘要|备注
            bodyMsg.append(bean.getBankTranDataSeqNo());
            bodyMsg.append("|");
            bodyMsg.append(bean.getAccNo());
            bodyMsg.append("|");
            bodyMsg.append(bean.getAccName());
            bodyMsg.append("|");
            bodyMsg.append(StringUtil.isEmpty(bean.getAccBankNo())?"":bean.getAccBankNo());
            bodyMsg.append("|");
            bodyMsg.append(StringUtil.isEmpty(bean.getAccBankName())?"":bean.getAccBankName());
            bodyMsg.append("|");
            bodyMsg.append(bean.getTranAmt().longValue());
            bodyMsg.append("|");
            bodyMsg.append("");
            bodyMsg.append("|");
            bodyMsg.append("");
            bodyMsg.append("\r\n");
            sumAmt += bean.getTranAmt().longValue();
            sumItem++;
            //PojoTranData tranData = transferDataDAO.queryTransferData(bean.getTranData().getTid());
            //TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(bean.getTranData().getTxnseqno());
            //风控
            //txnsLogService.tradeRiskControl(txnsLog.getTxnseqno(),txnsLog.getAccfirmerno(),txnsLog.getAccsecmerno(),txnsLog.getAccmemberid(),txnsLog.getBusicode(),txnsLog.getAmount()+"",bean.getAccType(),bean.getAccNo());
        }
        // PO|总笔数|总金额
        StringBuffer headMsg = new StringBuffer();
        headMsg.append("PO");
        headMsg.append("|");
        headMsg.append(sumItem);
        headMsg.append("|");
        headMsg.append(sumAmt);
        headMsg.append("\r\n");
        String msg = headMsg.append(bodyMsg).append("########").toString();
        log.info("cmbc outer send msg:\r\n"+msg);
        String offerBatchNo = OrderNumber.getInstance().generateCMBCBatchNo();
        String fileName = "req_outer_"
                + DateUtil.formatDateTime("yyyyMMdd", new Date()) + "_"
                + offerBatchNo + ".txt";
        String resFileName = "res_outer_"
                + DateUtil.formatDateTime("yyyyMMdd", new Date()) + "_"
                + offerBatchNo + ".txt";

        File batchPayFile = null;
       
        batchPayFile = writeFile(msg);
        
        // 加密后的文件
        File secretFile = new File(SecretFilePath);
        // 文件加密
        try {
            CMBCAESUtils.encodeAESFile(Key.getBytes(), batchPayFile, secretFile);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new CMBCTradeException("M004");
        }
        //ftpcmbcService = new FTPCMBCService();
        
        try {
			ftpcmbcService.uploadOuterCMBCFile(secretFile, fileName);
		} catch (Exception e) {//上传失败
			// TODO Auto-generated catch block
			e.printStackTrace();
			PojoTxnsInsteadPay txnsInsteadPay = new PojoTxnsInsteadPay(batchNo+"", transferBatch.getChannel().getBankChannelCode(), "01", fileName, resFileName, new Date(), null, "01", "");
	        txnsInsteadPayDAO.saveA(txnsInsteadPay);
	        throw new TradeException("T000","FTP客户连接失败");
		}
        //修改批次状态为正在支付
        bankTransferBatchDAO.updateBatchTranStatus(batchNo, "05");
        //修改每笔划拨的状态为正在支付
        bankTransferDataDAO.updateTransDataStatusByBatchNo(batchNo, InsteadPayTypeEnum.Paying);
        //写代付交易流水
        PojoTxnsInsteadPay txnsInsteadPay = new PojoTxnsInsteadPay(batchNo+"", transferBatch.getChannel().getBankChannelCode(), "02", fileName, resFileName, new Date(), null, "01", "");
        txnsInsteadPayDAO.saveA(txnsInsteadPay);
    }

    private File writeFile(String msg) throws CMBCTradeException,
            IOException {
        File payFile = new File(SecretFilePath);
        if (payFile.exists()) {
            FileOutputStream out = new FileOutputStream(payFile, false);
            out.write(msg.getBytes(ENCODE));// 注意需要转换对应的字符集
            out.close();
            return payFile;
        } else {
            throw new CMBCTradeException("M003");
        }
    }

    /**
     * 单笔代付
     * 
     * @param insteadPayBean
     *            单笔代付类
     */
    @Override
    public void singlePay(InsteadPayBean insteadPayBean) {
        
    }
    
    public void realTimePay(InsteadPayMessageBean bean){
        RealTimePayBean realTimePayBean = new RealTimePayBean(bean);
        String sendMsg = realTimePayBean.toXML();
        BaseSocketLongClient client =  BaseSocketLongClient.getInstance(ConsUtil.getInstance().cons.getCmbc_withholding_ip(), ConsUtil.getInstance().cons.getCmbc_insteadpay_port(), 90000);     
         try {
            client.setReceiveProcessor(new CMBCInsteadPayReciveProcessor());
            client.sendMessage(sendMsg.getBytes(ENCODE));
        } catch (CMBCTradeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void realTimePayQuery(String oriTranDate, String oriTranId ){
        RealTimeQueryBean realTimePayBean = new RealTimeQueryBean(oriTranDate, oriTranId);
        String sendMsg = realTimePayBean.toXML();
        BaseSocketLongClient client =  BaseSocketLongClient.getInstance(ConsUtil.getInstance().cons.getCmbc_withholding_ip(), ConsUtil.getInstance().cons.getCmbc_insteadpay_port(), 90000);     
         try {
            client.setReceiveProcessor(new CMBCInsteadPayReciveProcessor());
            client.sendMessage(sendMsg.getBytes(ENCODE));
        } catch (CMBCTradeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    

    private List<InsteadPayBean> initBatchData(){
        List<InsteadPayBean> payList =  new ArrayList<InsteadPayBean>();
        for(int i=0;i<5;i++){
            InsteadPayBean bean = new InsteadPayBean(DateUtil.getCurrentDateTime()+i);
            payList.add(bean);
        }
        JSON.toJSONString(payList);
        return payList;
    }
    
    /**
     *
     * @param batchNo
     * @throws TradeException 
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void batchInnerPay(Long batchNo) throws CMBCTradeException,IOException, TradeException {
        // TODO Auto-generated method stub
        PojoBankTransferBatch transferBatch = bankTransferBatchDAO.getByBankTranBatchNo(batchNo);
    	//PojoTransferBatch transferBatch = transferBatchDAO.getByBatchNo(batchNo);
        List<PojoBankTransferData> transferDataList =  bankTransferDataDAO.findTransDataByBatchNo(batchNo);
        //List<InsteadPayBean> payList = initBatchData();
        Long sumAmt = 0L;
        Long sumItem = 0L;
        StringBuffer bodyMsg = new StringBuffer();
        for (PojoBankTransferData bean : transferDataList) {
            // 第三方流水号|帐号|户名|金额|摘要|备注
            bodyMsg.append(bean.getBankTranDataSeqNo());
            bodyMsg.append("|");
            bodyMsg.append(bean.getAccNo());
            bodyMsg.append("|");
            bodyMsg.append(bean.getAccName());
            bodyMsg.append("|");
            bodyMsg.append(bean.getTranAmt().longValue());
            bodyMsg.append("|");
            bodyMsg.append("");
            bodyMsg.append("|");
            bodyMsg.append("");
            bodyMsg.append("\r\n");
            sumAmt += bean.getTranAmt().longValue();
            sumItem++;
            PojoTranData tranData = transferDataDAO.queryTransferData(bean.getTranData().getTid());
            TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(tranData.getTxnseqno());
            //风控
            txnsLogService.tradeRiskControl(txnsLog.getTxnseqno(),txnsLog.getAccfirmerno(),txnsLog.getAccsecmerno(),txnsLog.getAccmemberid(),txnsLog.getBusicode(),txnsLog.getAmount()+"",bean.getAccType(),bean.getAccNo());
        }
        // PO|总笔数|总金额
        StringBuffer headMsg = new StringBuffer();
        headMsg.append("P");
        headMsg.append("|");
        headMsg.append(sumItem);
        headMsg.append("|");
        headMsg.append(sumAmt);
        headMsg.append("\r\n");
        String msg = headMsg.append(bodyMsg).append("########").toString();
        log.info("cmbc Inner send msg:\r\n"+msg);
        String offerBatchNo = OrderNumber.getInstance().generateCMBCBatchNo();
        String fileName = "req_"
                + DateUtil.formatDateTime("yyyyMMdd", new Date()) + "_"
                + offerBatchNo + ".txt";
        String resFileName = "res_"
                + DateUtil.formatDateTime("yyyyMMdd", new Date()) + "_"
                + offerBatchNo + ".txt";
        File batchPayFile = null;
        batchPayFile = writeFile(msg);
        // 加密后的文件
        File secretFile = new File(SecretFilePath);
        // 文件加密
        try {
            CMBCAESUtils
                    .encodeAESFile(Key.getBytes(), batchPayFile, secretFile);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new CMBCTradeException("M004");
        }
        try {
			ftpcmbcService.uploadInnerCMBCFile(secretFile, fileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//写代付交易流水
	        PojoTxnsInsteadPay txnsInsteadPay = new PojoTxnsInsteadPay(batchNo+"", transferBatch.getChannel().getBankChannelCode(), "01", fileName, resFileName, new Date(), null, "01", "");
	        txnsInsteadPayDAO.merge(txnsInsteadPay);
		}
        //修改批次状态为正在支付，修改每笔划拨的状态为正在支付
        bankTransferDataDAO.updateTransDataStatusByBatchNo(batchNo, InsteadPayTypeEnum.Paying);
        //更新批次信息
        bankTransferBatchDAO.updateBatchTranStatus(batchNo, "05");
        //写代付交易流水
        PojoTxnsInsteadPay txnsInsteadPay = new PojoTxnsInsteadPay(batchNo+"", transferBatch.getChannel().getBankChannelCode(), "02", fileName, resFileName, new Date(), null, "01", "");
        txnsInsteadPayDAO.merge(txnsInsteadPay);
    }

    public void analyzeCMBCFile(File file,String fileName,FileTypeEnmu fileTypeEnmu) throws IOException {
        switch (fileTypeEnmu) {
            case RES ://同行回盘                                             第三方流水号|银行流水号|帐号|户名|金额|付款结果|失败返回码|失败原因|付款日期|付款时间
                analyzeResFile(file,fileName);
                break;
            case RESOUTER://跨行回盘                                第三方流水号|银行流水号|帐号|户名|金额|付款结果|失败返回码|失败原因|付款日期|付款时间
                analyzeResFile(file,fileName);
                break;
            case REEXCHANGE://退汇回盘                         报盘日期|报盘批次|第三方流水号|银行流水号|帐号|户名|金额|付款结果|失败返回码|失败原因|退汇日期
                analyzeReexchangeFile(file);
                break;
		default:
			break;
        }
    }
    
    @SuppressWarnings("resource")
	private void analyzeResFile(File file,String fileName) throws NumberFormatException, IOException{
        if(file.isFile() && file.exists()) { 
            //判断文件是否存在
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), ENCODE);// 考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            int i = 0;
            long sumItems = 0;//总笔数
            long succItems = 0;//成功笔数
            long sumAmt = 0;//总金额
            long succAmt = 0;//成功金额
            List<PojoBankTransferData> resultList = new ArrayList<PojoBankTransferData>();
            List<PojoCMBCResfileLog> cmbcResfileList = new ArrayList<PojoCMBCResfileLog>();
            while ((lineTxt = bufferedReader.readLine()) != null) {
                if(i==0){
                    //0    1        2       3           4
                    //P|总笔数|总金额|成功笔数|成功金额
                    if(StringUtil.isEmpty(lineTxt)){
                        return;
                    }
                    String[] head = lineTxt.split("\\|");
                    sumItems   =  Long.valueOf(head[1]);
                    sumAmt     =  Long.valueOf(head[2]);
                    succItems  =  Long.valueOf(head[3]);
                    succAmt    =  Long.valueOf(head[4]);
                    i++;
                    continue;
                }
                if("########".equals(lineTxt.trim())){
                    break;
                }
                //0                 1              2     3      4      5         6              7            8           9
                //第三方流水号|银行流水号|帐号|户名|金额|付款结果|失败返回码|失败原因|付款日期|付款时间
                String[] body = lineTxt.split("\\|");
                PojoBankTransferData resultBean = new PojoBankTransferData(body);
                PojoCMBCResfileLog cmbcResfileLog = new PojoCMBCResfileLog(body);
                resultList.add(resultBean);
                cmbcResfileList.add(cmbcResfileLog);
                i++;
            }
            log.info("cmbc Back to disk file context:/n/r"+JSON.toJSONString(resultList));
            read.close();
            long sumItems_self = 0;//总笔数
            long succItems_self = 0;//成功笔数
            long sumAmt_self = 0;//总金额
            long succAmt_self = 0;//成功金额
            long failItems = 0;
            long failAmt = 0;
            for(PojoBankTransferData bean : resultList){
                if("S".equalsIgnoreCase(bean.getResType())){//成功
                    succItems_self++;
                    succAmt_self+=bean.getTranAmt();
                }else if("F".equalsIgnoreCase(bean.getResType())){
                    failItems++;
                    failAmt+=bean.getTranAmt();
                }
                sumItems_self++;
                sumAmt_self += bean.getTranAmt();
            }
            if(sumItems_self!=sumItems){//总笔数和实际笔数不一致
                log.info("总笔数和实际笔数不一致:sumItems_self:"+sumItems_self+" / sumItems:"+sumItems);
            }
            if(succItems_self!=succItems){//成功笔数和实际成功笔数不一致
                log.info("成功笔数和实际成功笔数不一致:succItems_self:"+succItems_self+" / succItems:"+succItems);
            }
            if(sumAmt_self!=sumAmt){//总金额和实际总金额不一致
                log.info("总金额和实际总金额不一致:sumAmt_self:"+sumAmt_self+" / sumAmt:"+sumAmt);
            }
            if(succAmt_self!=succAmt){//成功总金额和实际成功总金额不一致
                log.info("总金额和实际总金额不一致:succAmt_self:"+succAmt_self+" / succAmt:"+succAmt);
            }
            //数据没有问题,批量更新划拨明细数据
            //更新转账明细数据
            bankTransferDataDAO.batchUpdateTransData(resultList);
            //更新对应业务的数据，调用业务处理接口
            dealWithBusiData(resultList);
            PojoTxnsInsteadPay txnsInsteadPay = txnsInsteadPayDAO.getByResponseFileName(fileName);
            if(txnsInsteadPay==null){
            	return ;
            }
            //更新转账批次数据
            PojoBankTransferBatch transferBatch = bankTransferBatchDAO.getByBankTranBatchNo(Long.valueOf(txnsInsteadPay.getInsteadPayNo()));
            if(transferBatch!=null){
                transferBatch.setSuccessCount(succItems);
                transferBatch.setSuccessAmt(succAmt);
                transferBatch.setFailCount(failItems);
                transferBatch.setFailAmt(failAmt);
                if(failItems>0&&succItems>0){//失败数大于0 成功数也大于0 部分转账成功
                	transferBatch.setTranStatus("02");
                }else if(succItems==0){//成功数=0
                	transferBatch.setTranStatus("04");
                }else{
                	transferBatch.setTranStatus("03");
                }
                bankTransferBatchDAO.update(transferBatch);
            }
            txnsInsteadPay.setStatus("00");
            txnsInsteadPayDAO.update(txnsInsteadPay);
            //更新划拨明细数据
            
            cmbcResfileLogDAO.saveCMBCResfileLog(cmbcResfileList, transferBatch.getBankTranBatchNo());
        }else{
            
        }
    }
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void dealWithBusiData(List<PojoBankTransferData> transferDataList){
    	for (PojoBankTransferData data : transferDataList) {
            PojoBankTransferData transferData = bankTransferDataDAO.getTransferDataByTranId(data.getBankTranDataSeqNo());
            if(transferData==null){
            	continue;
            }
            UpdateData updateData = new UpdateData();
            updateData.setTxnSeqNo(transferData.getTranData().getTxnseqno());
            updateData.setResultCode("S".equalsIgnoreCase(data.getResType())? "00": "03");
            updateData.setResultMessage("S".equalsIgnoreCase(data.getResType())? "交易成功": transferData.getResInfo());
            updateData.setChannelCode(transferData.getBankTranBatch().getChannel().getBankChannelCode());
            ObserverListService service  = ObserverListService.getInstance();
            service.notify(updateData, transferData.getTranData().getBusiType());
    	}
    }
    
    
    private void analyzeReexchangeFile(File file) throws NumberFormatException, IOException{
        if (file.isFile() && file.exists()) { // 判断文件是否存在
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), ENCODE);// 考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            int i=0;
            long sumItems = 0;//总笔数
            long succItems = 0;//成功笔数
            long sumAmt = 0;//总金额
            long succAmt = 0;//成功金额
            List<ReexchangeBean> resultList = new ArrayList<ReexchangeBean>();
            while ((lineTxt = bufferedReader.readLine()) != null) {
                if(i==0){
                    //0    1        2       3           4
                    //PO|总笔数|总金额|
                    String[] head = lineTxt.split("\\|");
                    sumItems   =  Long.valueOf(head[1]);
                    sumAmt     =  Long.valueOf(head[2]);
                    i++;
                    continue;
                }
                if("########".equals(lineTxt.trim())){
                    break;
                }
                log.info("原始退汇文件内容("+i+"行):"+lineTxt);
                //0     1        2         3              4      5       6         7            8           9     10
                //报盘日期|报盘批次|第三方流水号|银行流水号|帐号|户名|金额|付款结果|失败返回码|失败原因|退汇日期
                String[] body = lineTxt.split("\\|");
                ReexchangeBean resultBean = new ReexchangeBean(body);
                log.info("接收到退汇文件内容("+i+"行):"+JSON.toJSONString(resultBean));
                resultList.add(resultBean);
                i++;
            }
            read.close();
            long sumItems_self = 0;//总笔数
            long succItems_self = 0;//成功笔数
            long sumAmt_self = 0;//总金额
            long succAmt_self = 0;//成功金额
            @SuppressWarnings("unused")
			long failItems = 0;
            @SuppressWarnings("unused")
			long failAmt = 0;
            for(ReexchangeBean bean : resultList){
                if("S".equals(bean.getRespType())){//成功
                    succItems_self++;
                    succAmt_self+=bean.getTransAmt();
                }else if("F".equals(bean.getRespType())){
                    failItems++;
                    failAmt+=bean.getTransAmt();
                }
                sumItems_self++;
                sumAmt_self += bean.getTransAmt();
            }
            if(sumItems_self!=sumItems){//总笔数和实际笔数不一致
                
            }
            if(succItems_self!=succItems){//成功笔数和实际成功笔数不一致
                
            }
            if(sumAmt_self!=sumAmt){//总金额和实际总金额不一致
                
            }
            if(succAmt_self!=succAmt){//成功总金额和实际成功总金额不一致
                
            }
            //更新转账明细数据
            bankTransferDataDAO.batchUpdateReexchangeTransData(resultList);
            //更新对应业务的数据，调用业务处理接口
            dealWithReexchangeBusiData(resultList);
        } else {
            
        }
    }
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void dealWithReexchangeBusiData(List<ReexchangeBean> transferDataList){
    	for (ReexchangeBean data : transferDataList) {
            PojoBankTransferData transferData = bankTransferDataDAO.getTransferDataByTranId(data.getTranId());
            if(transferData==null){
            	continue;
            }
            UpdateData updateData = new UpdateData();
            updateData.setTxnSeqNo(transferData.getTranData().getTxnseqno());
            updateData.setResultCode("S".equalsIgnoreCase(data.getRespType())? "00": "04");
            updateData.setResultMessage("S".equalsIgnoreCase(data.getRespType())? "交易成功": transferData.getResInfo());
            updateData.setChannelCode(transferData.getBankTranBatch().getChannel().getBankChannelCode());
            updateData.setChannelFee(new BigDecimal(Math.abs(data.getTransAmt()-transferData.getTranAmt())));
            ObserverListService service  = ObserverListService.getInstance();
            service.notify(updateData, transferData.getTranData().getBusiType());
            
            PojoBankTransferBatch transferBatch = transferData.getBankTranBatch();
            if(transferBatch!=null){
            	Long succItems = transferBatch.getSuccessCount();
            	Long succAmt  = transferBatch.getSuccessAmt();
            	Long failItems = transferBatch.getFailCount();
            	Long failAmt = transferBatch.getFailAmt();
                transferBatch.setSuccessCount(succItems-1);
                transferBatch.setSuccessAmt(succAmt-data.getTransAmt());
                transferBatch.setFailCount(failItems+1);
                transferBatch.setFailAmt(failAmt+data.getTransAmt());
                if(failItems>0){
                	transferBatch.setTranStatus("02");
                }else if(succItems==0){
                	transferBatch.setTranStatus("04");
                }else{
                	transferBatch.setTranStatus("03");
                }
                bankTransferBatchDAO.update(transferBatch);
            }
    	}
    }


    public static void main(String[] args) {
        InsteadPayServiceImpl impl =new InsteadPayServiceImpl();
        impl.initBatchData();
        
        //System.out.println("P|5|500|5|500".split("\\|")[1]);
        //impl.batchOuterPay("008");
    }
}
