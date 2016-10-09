package com.zlebank.zplatform.trade.accounting.credit;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.acc.bean.TradeInfo;
import com.zlebank.zplatform.acc.exception.AbstractBusiAcctException;
import com.zlebank.zplatform.acc.exception.AccBussinessException;
import com.zlebank.zplatform.acc.exception.IllegalEntryRequestException;
import com.zlebank.zplatform.acc.service.AccEntryService;
import com.zlebank.zplatform.acc.service.entry.EntryEvent;
import com.zlebank.zplatform.commons.dao.pojo.AccStatusEnum;
import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.trade.adapter.accounting.IAccounting;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.SpringContext;

public class CreditRefundAccounting implements IAccounting {
	private static final Log log = LogFactory.getLog(CreditRechargeAccounting.class);
	
    private ITxnsLogService txnsLogService;
    private AccEntryService accEntryService;
	    
    public CreditRefundAccounting(){
        txnsLogService = (ITxnsLogService) SpringContext.getContext().getBean("txnsLogService");
        accEntryService = (AccEntryService) SpringContext.getContext().getBean("accEntryServiceImpl");
    }
	
	@Override
	public ResultBean accountedFor(String txnseqno) {
		log.info("交易:"+txnseqno+"授信账户退款处理开始");
		ResultBean resultBean = null;
		TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
		//记录转账的流水
		/**支付订单号**/
        /**交易类型**/
        String busiCode = txnsLog.getBusicode(); //转账的业务类型
        /**付款方会员ID**/
        String payMemberId =  txnsLog.getAccmemberid();
        /**收款方会员ID**/
        String payToMemberId = txnsLog.getAccsecmerno();
        /**收款方父级会员ID**/
        String payToParentMemberId="" ;
        /**渠道**/
        String channelId = "";//转账没有渠道，支付机构代码
        /**产品id**/
        String productId = "";
        /**交易金额**/
        BigDecimal amount = new BigDecimal(txnsLog.getAmount());
        /**佣金**/
        BigDecimal commission = new BigDecimal(StringUtil.isNotEmpty(txnsLog.getTradcomm()+"")?txnsLog.getTradcomm():0);
        /**手续费**/
        BigDecimal charge = new BigDecimal(StringUtil.isNotEmpty(txnsLog.getTxnfee()+"")?txnsLog.getTxnfee():0L);
        /**金额D**/
        BigDecimal amountD = new BigDecimal(0);
        /**金额E**/
        BigDecimal amountE = new BigDecimal(0);
        String coopInstCode= ConsUtil.getInstance().cons.getZlebank_coopinsti_code();
        TradeInfo tradeInfo = new TradeInfo(txnsLog.getTxnseqno(), txnsLog.getPayordno(), busiCode, payMemberId, payToMemberId, payToParentMemberId, channelId, productId, amount, commission, charge, amountD, amountE, false);
        tradeInfo.setCoopInstCode(coopInstCode);
       tradeInfo.setAccess_coopInstCode(txnsLog.getAccfirmerno());
        
        log.info(JSON.toJSONString(tradeInfo));
        try {
			accEntryService.accEntryProcess(tradeInfo,EntryEvent.AUDIT_APPLY);
			resultBean = new ResultBean("success");
		}  catch (AccBussinessException e) {
            resultBean = new ResultBean(e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (AbstractBusiAcctException e) {
            resultBean = new ResultBean(e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            resultBean = new ResultBean("AE099", e.getMessage());
            e.printStackTrace();
        } catch (IllegalEntryRequestException e) {
			// TODO Auto-generated catch block
        	resultBean = new ResultBean(e.getCode(), e.getMessage());
			e.printStackTrace();
		}catch(Exception e){
			resultBean = new ResultBean("",e.getMessage());
			e.printStackTrace();
		}
         //处理账务
		 String retCode="";
		 String retInfo="";
        if(resultBean.isResultBool()){
			 retCode="0000";
			 retInfo="交易成功";
	     }else{
	       	 retCode="0099";
	       	 retInfo=resultBean.getErrMsg();
	     }
        txnsLog.setPayordfintime(DateUtil.getCurrentDateTime());
        txnsLog.setAccordfintime(DateUtil.getCurrentDateTime());
        txnsLog.setPayretcode(retCode);
        txnsLog.setPayretinfo(retInfo);
        txnsLog.setAppordcommitime(DateUtil.getCurrentDateTime());
        txnsLog.setAppinst("000000000000");//没实际意义，可以为空
        if("0000".equals(retCode)){
        	 txnsLog.setApporderinfo("授信账户退款账务成功");
             txnsLog.setApporderstatus(AccStatusEnum.Finish.getCode());
        }else{
        	txnsLog.setApporderinfo(retInfo);
            txnsLog.setApporderstatus(AccStatusEnum.AccountingFail.getCode());
        }
        txnsLog.setAppordfintime(DateUtil.getCurrentDateTime());
        txnsLog.setRetcode(retCode);
        txnsLog.setRetinfo(retInfo);
        //支付定单完成时间
        this.txnsLogService.updateTxnsLog(txnsLog);
        log.info("交易:"+txnseqno+"授信账户退款处理成功");
		return resultBean;
	}

	@Override
	public ResultBean accountedForInsteadPay(String batchno) {
		// TODO Auto-generated method stub
		return null;
	}

}
