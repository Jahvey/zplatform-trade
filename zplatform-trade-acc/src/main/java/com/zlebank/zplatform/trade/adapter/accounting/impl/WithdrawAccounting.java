
package com.zlebank.zplatform.trade.adapter.accounting.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.trade.adapter.accounting.IAccounting;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.model.PojoTranBatch;
import com.zlebank.zplatform.trade.model.PojoTranData;
/**
 * 
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年12月8日 下午3:04:51
 * @since
 */
public class WithdrawAccounting implements IAccounting{
    /*private TransferBatchDAO transferBatchDAO;
    private TransferDataDAO transferDataDAO;
    private AccEntryService accEntryService;
    private ITxnsWithdrawService txnsWithdrawService;
    private ITxnsLogService txnsLogService;
    
    public WithdrawAccounting(){
        transferBatchDAO    =  (TransferBatchDAO) SpringContext.getContext().getBean("transferBatchDAO");
        transferDataDAO     =  (TransferDataDAO) SpringContext.getContext().getBean("transferDataDAO");
        accEntryService     =  (AccEntryService) SpringContext.getContext().getBean("accEntryServiceImpl");
        txnsWithdrawService =  (ITxnsWithdrawService) SpringContext.getContext().getBean("txnsWithdrawService");
        txnsLogService = (ITxnsLogService) SpringContext.getContext().getBean("txnsLogService");
    }
    */
    
    @Override
    public ResultBean accountedFor(String txnseqno) {
        
        return null;
    }

    @Override
    public ResultBean accountedForInsteadPay(String batchno) {
        //取得所有代付批次数据
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
                // TODO Auto-generated catch block cwk552 tbb533
                e.printStackTrace();
                errorMsg=e.getMessage();
            }
            // 应用方信息
            AppPartyBean appParty = new AppPartyBean("000000","99999999", commiteTime,DateUtil.getCurrentDateTime(), transferData.getTxnseqno(), "");
            txnsLogService.updateAppInfo(appParty);
            if(StringUtil.isEmpty(errorMsg)){//错误信息为空，入账成功
                transferData.setAccstatus(AccStatusEnum.Finish.getCode());
                transferData.setAccinfo("账务处理成功");
                txnsLogService.updateAppStatus(transferData.getTxnseqno(), AccStatusEnum.Finish.getCode(), "提现账务成功");
                //更新原始订单信息
                updateWithdrawOrder(transferData, batchno, transferBatch);
            }else{
                transferData.setAccstatus(AccStatusEnum.AccountingFail.getCode());
                transferData.setAccinfo(errorMsg);
                txnsLogService.updateAppStatus(transferData.getTxnseqno(), AccStatusEnum.AccountingFail.getCode(), errorMsg);
            }
        }
        transferDataDAO.batchUpdateTransDataAccStatus(transferDataList);*/
        return null;
    }
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void updateWithdrawOrder(PojoTranData transferData,String batchno,PojoTranBatch transferBatch){
        /*TxnsWithdrawModel txnsWithdraw = new TxnsWithdrawModel();
        txnsWithdraw.setStatus(transferData.getStatus());
        txnsWithdraw.setWithdrawinstid(transferBatch.getChnlcode());
        
        if(transferData.getResptype().equalsIgnoreCase("S")){
            txnsWithdraw.setRetcode("00");
            txnsWithdraw.setRetinfo("交易成功");
        }else{
            txnsWithdraw.setRetcode(transferData.getRespcode());
            txnsWithdraw.setRetinfo(transferData.getRespmsg());
        }
        
        txnsWithdraw.setMemberid(transferData.getMemberid());
        txnsWithdraw.setWithdraworderno(transferData.getRelatedorderno());
        txnsWithdrawService.updateWithdrawResult(txnsWithdraw);*/
    }
    
    
    //private TradeInfo initAccountingData(PojoTranData transferData,PojoTranBatch transferBatch){
        /*String txnseqno = transferData.getTxnseqno();
        *//**支付订单号**//*
        String payordno = transferData.getRelatedorderno();
        *//**交易类型 - 根据交易结果进行判断，**//*
        String busiCode = "";
        if(transferData.getBusicode().equals("30000001")){
            if("S".equalsIgnoreCase(transferData.getResptype())){//交易成功
                busiCode = "30000002";
            }else if("F".equalsIgnoreCase(transferData.getResptype())){//交易失败
                busiCode = "30000003";
            }
        }else{
            throw new AbortException("账务处理失败，批次业务代码和明细业务代码不一致");
        }
        
        *//**付款方会员ID**//*
        String payMemberId = transferData.getMemberid();
        *//**收款方会员ID**//*
        String payToMemberId = transferData.getMemberid();
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
   // }
}
