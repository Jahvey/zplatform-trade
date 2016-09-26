/* 
 * CMBCTransferServiceImpl.java  
 * 
 * version TODO
 *
 * 2015年11月30日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.cmbc.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.enums.CMBCCardTypeEnum;
import com.zlebank.zplatform.trade.cmbc.bean.enmus.TransferTypeEnmu;
import com.zlebank.zplatform.trade.cmbc.bean.gateway.CardMessageBean;
import com.zlebank.zplatform.trade.cmbc.bean.gateway.WhiteListMessageBean;
import com.zlebank.zplatform.trade.cmbc.bean.gateway.WithholdingMessageBean;
import com.zlebank.zplatform.trade.cmbc.exception.CMBCTradeException;
import com.zlebank.zplatform.trade.cmbc.service.ICMBCTransferService;
import com.zlebank.zplatform.trade.cmbc.service.IInsteadPayService;
import com.zlebank.zplatform.trade.cmbc.service.IWithholdingService;
import com.zlebank.zplatform.trade.dao.BankTransferBatchDAO;
import com.zlebank.zplatform.trade.dao.BankTransferChannelDAO;
import com.zlebank.zplatform.trade.dao.BankTransferDataDAO;
import com.zlebank.zplatform.trade.dao.ITxnsInsteadPayDAO;
import com.zlebank.zplatform.trade.dao.RealnameAuthDAO;
import com.zlebank.zplatform.trade.dao.RspmsgDAO;
import com.zlebank.zplatform.trade.dao.TransferBatchDAO;
import com.zlebank.zplatform.trade.dao.TransferDataDAO;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.model.PojoBankTransferBatch;
import com.zlebank.zplatform.trade.model.PojoBankTransferData;
import com.zlebank.zplatform.trade.model.PojoRealnameAuth;
import com.zlebank.zplatform.trade.model.TxnsWhiteListModel;
import com.zlebank.zplatform.trade.model.TxnsWithholdingModel;
import com.zlebank.zplatform.trade.service.IRouteConfigService;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.ITxnsWhiteListService;
import com.zlebank.zplatform.trade.service.ITxnsWithholdingService;
import com.zlebank.zplatform.trade.utils.ValidateLocator;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月30日 上午10:46:55
 * @since 
 */
@Service("cmbcTransferService")
public class CMBCTransferServiceImpl implements ICMBCTransferService{

    private static final Log log = LogFactory.getLog(CMBCTransferServiceImpl.class);
    @Autowired
    private IRouteConfigService routeConfigService;
    @Autowired
    private RealnameAuthDAO realnameAuthDAO;
    @Autowired
    private IWithholdingService withholdingService;
    @Autowired
    private ITxnsWithholdingService txnsWithholdingService;
    @Autowired
    private TransferBatchDAO transferBatchDAO;
    @Autowired 
    private IInsteadPayService insteadPayService;
    @Autowired
    private ITxnsWhiteListService txnsWhiteListService;
    @Autowired
    private TransferDataDAO transferDataDAO;
    @Autowired
    private ITxnsLogService txnsLogService;
    @Autowired
    private BankTransferBatchDAO bankTransferBatchDAO;
    @Autowired
    private BankTransferDataDAO bankTransferDataDAO;
    @Autowired
    private BankTransferChannelDAO bankTransferChannelDAO;
    @Autowired
    private RspmsgDAO rspmsgDAO;
    @Autowired
    private ITxnsInsteadPayDAO txnsInsteadPayDAO;
    /**
     * 实名认证
     * @param card
     * @return
     * @throws TradeException 
     * @throws CMBCTradeException 
     */
    @Override
    public ResultBean realNameAuth(PojoRealnameAuth realnameAuth) throws TradeException, CMBCTradeException {
        ResultBean resultBean = null;
        TxnsWithholdingModel withholding = null;
        try {
            CardMessageBean card = new CardMessageBean(realnameAuth);
            resultBean = validateCardMessage(card);
            if(!resultBean.isResultBool()){
                return resultBean;
            }
            PojoRealnameAuth realNameAuth = realnameAuthDAO.getByCardInfo(realnameAuth);
            if(realNameAuth!=null){
                if("00".equals(realNameAuth.getStatus())){//实名认证已经完成，加入绑卡信息中
                    
                    return new ResultBean("RN00");
                }
            }
            withholding = new TxnsWithholdingModel(realnameAuth);
            //保存实名认证流水
            txnsWithholdingService.saveWithholdingLog(withholding);
            card.setWithholding(withholding);
            //保存实名认证数据
            //realnameAuthDAO.saveRealNameAuth(realnameAuth);
            //民生渠道跨行实名认证
            withholdingService.realNameAuthentication(card);
            //查询实名认证结果
            resultBean = queryResult(withholding.getSerialno());
            log.info("realNameAuth result:"+JSON.toJSONString(resultBean));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            resultBean = new ResultBean("", "实名认证失败");
            System.out.println(e.getMessage());
            withholding.setExecmsg(e.getMessage());
            txnsWithholdingService.updateWithholdingLogError(withholding);
            realnameAuth.setStatus("02");
            realnameAuthDAO.updateRealNameStatus(realnameAuth);
        }
        return resultBean;
    }
    
    /**
     * 查询民生银行跨行代扣交易结果
     * @param serialno
     * @return
     */
    private ResultBean queryResult(String serialno) {
        TxnsWithholdingModel withholding = null;
        ResultBean resultBean = null;
        int[] timeArray = new int[]{1000, 2000, 8000, 16000, 32000};
        try {
            for (int i = 0; i < 5; i++) {
                withholding = txnsWithholdingService.getWithholdingBySerialNo(serialno);
                if(!StringUtil.isEmpty(withholding.getExectype())){
                	
                    if("S".equalsIgnoreCase(withholding.getExectype())){
                    	if("00".equals(withholding.getValidatestatus())){
                    		 resultBean = new ResultBean("success");
                    	}else{
                    		resultBean = new ResultBean("0099","实名认证失败");
                    	}
                        break;
                    }else if("E".equalsIgnoreCase(withholding.getExectype())){
                        resultBean = new ResultBean(withholding.getExeccode(),withholding.getExecmsg());
                        break;
                    }
                }
                Thread.sleep(timeArray[i]);
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return resultBean;
    }
    /**
     * 白名单采集
     *
     * @param bankaccno
     * @param bankaccname
     * @param certno
     * @param mobile
     * @param certtype
     * @return
     */
    public ResultBean whiteListCollection(String bankaccno, String bankaccname,String certno, String mobile,String certtype){
        ResultBean resultBean = null;
        TxnsWithholdingModel withholding = null;
        try {
            TxnsWhiteListModel whiteList = new TxnsWhiteListModel(bankaccno, bankaccname, certno, mobile);
            TxnsWhiteListModel bean = txnsWhiteListService.getByCardInfo(whiteList);
            if(bean==null){
                Map<String, Object> cardMap = routeConfigService.getCardInfo(bankaccno);
                String bankinscode=cardMap.get("BANKCODE")+"";
                String bankname=cardMap.get("BANKNAME")+""; 
                String bankacctype=cardMap.get("TYPE")+""; 
                WhiteListMessageBean whiteListMsg = new WhiteListMessageBean(bankinscode, bankname, bankaccno, bankaccname, CMBCCardTypeEnum.fromCardType(bankacctype).getCode(), certtype, certno, mobile, "", "");
                //withholdingService.whiteListCollection(whiteListMsg);
                withholding = new TxnsWithholdingModel(whiteListMsg.getBankinscode(),whiteListMsg.getBankname(),whiteListMsg.getBankaccno(),whiteListMsg.getBankaccname(),whiteListMsg.getBankacctype(),whiteListMsg.getCerttype(),whiteListMsg.getCertno(),whiteListMsg.getMobile());
                whiteListMsg.setWithholding(withholding);
                //保存白名单采集流水
                txnsWithholdingService.saveWithholdingLog(withholding);
                //民生白名单采集
                withholdingService.whiteListCollection(whiteListMsg);
                resultBean = new ResultBean("success");
            }else{
                resultBean = new ResultBean("success");
            }
        } catch (TradeException e) {
            e.printStackTrace();
            withholding.setExecmsg(e.getMessage());
            txnsWithholdingService.updateWithholdingLogError(withholding);
            resultBean = new ResultBean(e.getCode(),e.getMessage());
        } catch (CMBCTradeException e) {
            e.printStackTrace();
            withholding.setExecmsg(e.getMessage());
            txnsWithholdingService.updateWithholdingLogError(withholding);
            resultBean = new ResultBean(e.getCode(),e.getMessage());
        }    
        return resultBean;
    }
    
    
    private ResultBean validateCardMessage(CardMessageBean card){
        ResultBean resultBean = ValidateLocator.validateBeans(card);
        if(!resultBean.isResultBool()){
            return resultBean;
        }
        Map<String, Object>  cardMap = routeConfigService.getCardInfo(card.getCardNo());
        if(cardMap!=null){
            String cardType = cardMap.get("TYPE").toString();
            if(StringUtil.isEmpty(card.getCardType())){//无卡类型
                card.setCardType(cardType);
            }else{
//                if(!cardType.equals(card.getCardType())){
//                    return new ResultBean("","卡类型错误");
//                }
            }
            //检验信用卡信息
            if("2".equals(card.getCardType())){
                if(StringUtil.isEmpty(card.getCvn2())||StringUtil.isEmpty(card.getExpired())){
                    return new ResultBean("","信用卡信息错误");
                }
            }
        }else{
            resultBean = new ResultBean("","卡信息错误");
        }
        return resultBean;
    }

    /**
     * 批量划拨
     * @param batchNo
     * @return
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public ResultBean batchTransfer(Long tid) {
        ResultBean resultBean = null;
        try {
            //检查批次信息是否正确
        	PojoBankTransferBatch bankTransferBatch = bankTransferBatchDAO.getById(tid);
            if(!"01".equals(bankTransferBatch.getTranStatus())&&!"02".equals(bankTransferBatch.getTranStatus())){
                return new ResultBean("", "不可重复转账");
            }
            //判断当前批次是否已经上传成功
            if(txnsInsteadPayDAO.isUpload(tid+"")){
            	return new ResultBean("", "不可重复转账");
            }
            //生成交易日志
            List<PojoBankTransferData> transferDataList = bankTransferDataDAO.findTransDataByBatchNo(tid);
            txnsLogService.saveBankTransferLogs(transferDataList);
            TransferTypeEnmu transferType = TransferTypeEnmu.fromValue(bankTransferBatch.getChannel().getBankType());
            switch (transferType) {
                case INNERBANK :
                    insteadPayService.batchInnerPay(tid);
                    break;
                case OUTERBANK :
                    insteadPayService.batchOuterPay(tid);
                    break;
				default:
					break;
            }
            resultBean = new ResultBean("success");
        } catch (CMBCTradeException e) {
            e.printStackTrace();
            resultBean = new ResultBean(e.getCode(),e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            resultBean = new ResultBean("M005","文件读写异常");
        } catch (TradeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            resultBean = new ResultBean(e.getCode(),e.getMessage());
        }
        return resultBean;
    }

    /**
     *
     * @param withholdingMsg
     * @return
     */
    @Override
    public ResultBean singleTransfer(WithholdingMessageBean withholdingMsg) {
        
        return new ResultBean("success");
    }

}
