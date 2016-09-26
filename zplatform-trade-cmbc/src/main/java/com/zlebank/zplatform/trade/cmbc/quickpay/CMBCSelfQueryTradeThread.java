package com.zlebank.zplatform.trade.cmbc.quickpay;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.trade.bean.enums.ChannelEnmu;
import com.zlebank.zplatform.trade.cmbc.exception.CMBCTradeException;
import com.zlebank.zplatform.trade.cmbc.service.IWithholdingService;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.model.TxnsWithholdingModel;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.ITxnsWithholdingService;
import com.zlebank.zplatform.trade.utils.SpringContext;

public class CMBCSelfQueryTradeThread implements Runnable{

    private String tranDate;
    private String serialno;
    private String txnseqno;
    @Override
    public void run() {
        queryTrade(tranDate, serialno, txnseqno);
    }
    private IWithholdingService withholdingService;
    private ITxnsWithholdingService txnsWithholdingService;
    private ITxnsLogService txnsLogService;
   
    public void queryTrade(String tranDate,String serialno,String txnseqno){
        try {
            Thread.sleep(10*1000);
            TxnsWithholdingModel withholding = new TxnsWithholdingModel(tranDate, serialno, txnseqno,ChannelEnmu.CMBCSELFWITHHOLDING);
            withholding.setSerialno(generateSerialDateNumber("SEQ_CMBC_REALNAME_NO"));
            txnsWithholdingService.saveWithholdingLog(withholding);
            withholdingService.realTimeSelfWithholdinghResult(withholding);
        } catch (CMBCTradeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TradeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public CMBCSelfQueryTradeThread(String tranDate,String serialno,String txnseqno) {
        withholdingService = (IWithholdingService) SpringContext.getContext().getBean("withholdingService");
        txnsWithholdingService = (ITxnsWithholdingService) SpringContext.getContext().getBean("txnsWithholdingService");
        txnsLogService = (ITxnsLogService) SpringContext.getContext().getBean("txnsLogService");
        this.tranDate = tranDate;
        this.serialno= serialno;
        this.txnseqno = txnseqno;
    }
    
    @SuppressWarnings("unchecked")
	public String generateSerialDateNumber(String sequences){
        List<Map<String,Object>> resultList = (List<Map<String, Object>>) txnsLogService.queryBySQL("select "+sequences+".NEXTVAL seq from dual", new Object[]{});
        DecimalFormat df = new DecimalFormat("00000000");
        String seqNo = df.format( resultList.get(0).get("SEQ"));
        return DateUtil.getCurrentDate()+seqNo;
    }
    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }

    public String getTxnseqno() {
        return txnseqno;
    }

    public void setTxnseqno(String txnseqno) {
        this.txnseqno = txnseqno;
    }
    
    
}
