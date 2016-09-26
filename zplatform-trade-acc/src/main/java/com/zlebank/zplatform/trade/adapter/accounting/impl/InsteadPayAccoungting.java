
package com.zlebank.zplatform.trade.adapter.accounting.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.trade.adapter.accounting.IAccounting;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.model.PojoTranBatch;
import com.zlebank.zplatform.trade.model.PojoTranData;


public class InsteadPayAccoungting implements IAccounting{
    
    /*private TransferBatchDAO transferBatchDAO;
    private TransferDataDAO transferDataDAO;
    private AccEntryService accEntryService;
    private InsteadPayDetailDAO insteadPayDetailDAO;
    private ITxnsLogService txnsLogService;
    
    public InsteadPayAccoungting() {
        transferBatchDAO = (TransferBatchDAO) SpringContext.getContext().getBean("transferBatchDAO");
        transferDataDAO = (TransferDataDAO) SpringContext.getContext().getBean("transferDataDAO");
        accEntryService=(AccEntryService) SpringContext.getContext().getBean("accEntryServiceImpl");
        insteadPayDetailDAO = (InsteadPayDetailDAO) SpringContext.getContext().getBean("insteadPayDetailDAO");
        txnsLogService = (ITxnsLogService) SpringContext.getContext().getBean("txnsLogService");
    }
*/
    @Override
    public ResultBean accountedFor(String txnseqno) {
        return null;
    }

    @Override
    public ResultBean accountedForInsteadPay(String batchno) {
        //取得代付批次数据
        /*PojoTransferBatch transferBatch = transferBatchDAO.getByBatchNo(batchno);
        List<PojoTransferData> transferDataList = transferDataDAO.findTransDataByBatchNoAndAccstatus(batchno);
        for(PojoTransferData transferData : transferDataList){
            String commiteTime = DateUtil.getCurrentDateTime();
            String errorMsg = "";
            try {
                TradeInfo tradeInfo = initAccountingData(transferData, transferBatch);
                accEntryService.accEntryProcess(tradeInfo);
            } catch (AccBussinessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                errorMsg=e.getMessage();
            } catch (AbstractBusiAcctException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                errorMsg=e.getMessage();
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                errorMsg=e.getMessage();
            }
            // 应用方信息
            AppPartyBean appParty = new AppPartyBean("000000",
                    "99999999", commiteTime,
                    DateUtil.getCurrentDateTime(), transferData.getTxnseqno(), "");
            txnsLogService.updateAppInfo(appParty);
            if(StringUtil.isEmpty(errorMsg)){//错误信息为空，入账成功
                transferData.setAccstatus(AccStatusEnum.Finish.getCode());
                transferData.setAccinfo("账务处理成功");
                txnsLogService.updateAppStatus(transferData.getTxnseqno(), AccStatusEnum.Finish.getCode(), "代付账务成功");
            }else{
                transferData.setAccstatus(AccStatusEnum.AccountingFail.getCode());
                transferData.setAccinfo(errorMsg);
                txnsLogService.updateAppStatus(transferData.getTxnseqno(), AccStatusEnum.AccountingFail.getCode(), errorMsg);
            }
            //更新原始订单信息
            updateInsteadPayOrder(transferData, batchno, transferBatch);
        }
        transferDataDAO.batchUpdateTransDataAccStatus(transferDataList);*/
        return null;
    }
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void updateInsteadPayOrder(PojoTranData transferData,String batchno,PojoTranBatch transferBatch){
        /*PojoInsteadPayDetail insteadPayDetail = new PojoInsteadPayDetail();
        insteadPayDetail.setStatus(transferData.getStatus());
        insteadPayDetail.setChannelCode(transferBatch.getChnlcode());
        insteadPayDetail.setBatchFileNo(transferBatch.getRequestfilename());
        if(transferData.getResptype().equalsIgnoreCase("S")){
            insteadPayDetail.setRespCode("00");
            insteadPayDetail.setRespMsg("交易成功");
        }else{
            insteadPayDetail.setRespCode(transferData.getRespcode());
            insteadPayDetail.setRespMsg(transferData.getRespmsg());
        }
        insteadPayDetail.setMerId(transferData.getMemberid());
        insteadPayDetail.setOrderId(transferData.getRelatedorderno());
        insteadPayDetailDAO.updateBatchDetailResult(insteadPayDetail);*/
    }
    
    
    //private TradeInfo initAccountingData(PojoTranData transferData,PojoTranBatch transferBatch){
       /* String txnseqno = transferData.getTxnseqno();
        *//**支付订单号**//*
        String payordno = transferData.getRelatedorderno();
        *//**交易类型 - 根据交易结果进行判断，**//*
        String busiCode = "";
        if (transferData.getBusicode().equals("70000001")) {
            if("S".equalsIgnoreCase(transferData.getResptype())){//交易成功
                busiCode = "70000002";
            }else if("F".equalsIgnoreCase(transferData.getResptype())){//交易失败
                busiCode = "70000003";
            }
        }else{
            throw new AbortException("账务处理失败，批次业务代码和明细业务代码不一致");
        }
        
        *//**付款方会员ID**//*
        String payMemberId = transferData.getMemberid();
        *//**收款方会员ID**//*
        String payToMemberId = AnonymousMember;
        *//**收款方父级会员ID**//*
        String payToParentMemberId="";
        *//**渠道**//*
        String channelId = transferBatch.getChnlcode();//支付机构代码
        
        *//**产品id**//*
        String productId = "";
        *//**交易金额**//*
        BigDecimal amount = new BigDecimal(transferData.getTransamt());
        *//**佣金**//*
        BigDecimal commission = new BigDecimal(0);
        *//**手续费**//*
        long txnfee = 0;
        BigDecimal charge = new BigDecimal(txnfee);
        *//**金额D**//*
        BigDecimal amountD = new BigDecimal(0);
        *//**金额E**//*
        BigDecimal amountE = new BigDecimal(0);
        txnsLogService.updateAccBusiCode(txnseqno, busiCode);
       
        TradeInfo tradeInfo = new TradeInfo(txnseqno, payordno, busiCode, payMemberId, payToMemberId, payToParentMemberId, channelId, productId, amount, commission, charge, amountD, amountE, false);
        return tradeInfo;*/
    	//return null;
    //}

}