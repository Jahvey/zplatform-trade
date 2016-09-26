/* 
 * TxnsQuickpayServiceImpl.java  
 * 
 * version TODO
 *
 * 2015年8月31日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.trade.bean.ReaPayResultBean;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.ZLPayResultBean;
import com.zlebank.zplatform.trade.bean.enums.TradeTypeEnum;
import com.zlebank.zplatform.trade.bean.reapal.BindBean;
import com.zlebank.zplatform.trade.bean.reapal.CreditBean;
import com.zlebank.zplatform.trade.bean.reapal.DebitBean;
import com.zlebank.zplatform.trade.bean.reapal.PayBean;
import com.zlebank.zplatform.trade.bean.reapal.QueryBean;
import com.zlebank.zplatform.trade.bean.reapal.SMSBean;
import com.zlebank.zplatform.trade.bean.zlpay.MarginRegisterBean;
import com.zlebank.zplatform.trade.bean.zlpay.MarginSmsBean;
import com.zlebank.zplatform.trade.bean.zlpay.OnlineDepositShortBean;
import com.zlebank.zplatform.trade.dao.ITxnsQuickpayDAO;
import com.zlebank.zplatform.trade.model.TxnsQuickpayModel;
import com.zlebank.zplatform.trade.model.TxnsWithholdingModel;
import com.zlebank.zplatform.trade.service.ITxnsQuickpayService;
import com.zlebank.zplatform.trade.service.base.BaseServiceImpl;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.DateUtil;
import com.zlebank.zplatform.trade.utils.OrderNumber;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年8月31日 上午10:16:08
 * @since 
 */
@Service("txnsQuickpayService")
public class TxnsQuickpayServiceImpl extends BaseServiceImpl<TxnsQuickpayModel, Long> implements ITxnsQuickpayService{

    
    @Autowired
    private ITxnsQuickpayDAO txnsQuickpayDAO;
    /**
     *
     * @return
     */
    @Override
    public Session getSession() {
        return txnsQuickpayDAO.getSession();
    }
    /**
     *
     * @param marginSmsBean
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void saveMobileCode(TradeBean trade,MarginSmsBean marginSmsBean) {
        TxnsQuickpayModel txnsQuickpay = new TxnsQuickpayModel(trade,marginSmsBean);
        super.save(txnsQuickpay);
    }
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public String saveReaPaySMS(TradeBean trade,SMSBean smsBean) {
        TxnsQuickpayModel txnsQuickpay = new TxnsQuickpayModel(trade,smsBean);
        
        super.save(txnsQuickpay);
        return txnsQuickpay.getPayorderno();
    }
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public String saveTestSMS(TradeBean trade,SMSBean smsBean) {
        TxnsQuickpayModel txnsQuickpay = new TxnsQuickpayModel(trade,smsBean,"");
        super.save(txnsQuickpay);
        return txnsQuickpay.getPayorderno();
    }
    
    
    /**
     *
     * @param bean
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateMobileCode(ResultBean bean) {
        // TODO Auto-generated method stub
    	if(bean.isResultBool()){
    		ZLPayResultBean zlPayResultBean = (ZLPayResultBean)bean.getResultObj();
            TxnsQuickpayModel txnsQuickpay = super.findByProperty("payorderno", zlPayResultBean.getMerchantSeqId()).get(0);
            if(bean.isResultBool()){
                txnsQuickpay.setStatus("00");
            }else{
                txnsQuickpay.setStatus("02");
            } 
            txnsQuickpay.setRetorderno(zlPayResultBean.getPnrSeqId());
            txnsQuickpay.setPayfinishtime(DateUtil.getCurrentDateTime());
            txnsQuickpay.setPayretcode(zlPayResultBean.getRespCode());
            txnsQuickpay.setPayretinfo(zlPayResultBean.getRespDesc());
            super.update(txnsQuickpay);
    	}else{
    		String merchantseqid = bean.getResultObj()+"";
    		TxnsQuickpayModel txnsQuickpay = super.findByProperty("payorderno",merchantseqid).get(0);
            txnsQuickpay.setStatus("02");
            txnsQuickpay.setPayfinishtime(DateUtil.getCurrentDateTime());
            txnsQuickpay.setPayretcode(bean.getErrCode());
            txnsQuickpay.setPayretinfo(bean.getErrMsg());
            super.update(txnsQuickpay);
    	}
        
        
    }
    /**
     *
     * @param marginRegisterBean
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void saveMarginRegister(TradeBean trade,MarginRegisterBean marginRegisterBean) {
        // TODO Auto-generated method stub
        TxnsQuickpayModel txnsQuickpay = new TxnsQuickpayModel(trade,marginRegisterBean);
        super.save(txnsQuickpay);
    }
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public TxnsQuickpayModel saveReaPayDebitSign(TradeBean trade,DebitBean debitBean){
        TxnsQuickpayModel txnsQuickpay = new TxnsQuickpayModel(trade,debitBean);
        super.save(txnsQuickpay);
        return txnsQuickpay;
    }
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public TxnsQuickpayModel saveTestDebitSign(TradeBean trade,DebitBean debitBean){
        TxnsQuickpayModel txnsQuickpay = new TxnsQuickpayModel(trade,debitBean,"");
        super.save(txnsQuickpay);
        return txnsQuickpay;
    }
    
    
    /**
     *
     * @param bean
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateMarginRegister(ResultBean bean) {
        // TODO Auto-generated method stub
        ZLPayResultBean zlPayResultBean = (ZLPayResultBean)bean.getResultObj();
        TxnsQuickpayModel txnsQuickpay = super.findByProperty("payorderno", zlPayResultBean.getFundSeqId()).get(0);
        if(bean.isResultBool()){
            txnsQuickpay.setStatus("00");
        }else{
            txnsQuickpay.setStatus("02");
        } 
        txnsQuickpay.setRetorderno(zlPayResultBean.getPnrSeqId());
        txnsQuickpay.setPayfinishtime(DateUtil.getCurrentDateTime());
        txnsQuickpay.setPayretcode(zlPayResultBean.getRespCode());
        txnsQuickpay.setPayretinfo(zlPayResultBean.getRespDesc());
        super.update(txnsQuickpay);
    }
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateReaPaySign(ResultBean bean,String payorderno) {
        // TODO Auto-generated method stub
        ReaPayResultBean realPayPayResultBean = (ReaPayResultBean)bean.getResultObj();
        TxnsQuickpayModel txnsQuickpay = super.findByProperty("payorderno", payorderno).get(0);
        if(bean.isResultBool()){
            txnsQuickpay.setStatus("00");
        }else{
            txnsQuickpay.setStatus("02");
        }
        txnsQuickpay.setPayfinishtime(DateUtil.getCurrentDateTime());
        txnsQuickpay.setPayretcode(realPayPayResultBean.getResult_code());
        txnsQuickpay.setPayretinfo(realPayPayResultBean.getResult_msg());
        super.update(txnsQuickpay);
    }
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateCMBCWithholdingResult(TxnsWithholdingModel withholding,String payorderno){
        TxnsQuickpayModel txnsQuickpay = super.findByProperty("payorderno", payorderno).get(0);
        txnsQuickpay.setPayfinishtime(DateUtil.getCurrentDateTime());
        txnsQuickpay.setStatus("00");
        txnsQuickpay.setPayretcode("3083");
        txnsQuickpay.setPayretinfo("接受成功");
        super.update(txnsQuickpay);
    }
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateTestSign(ResultBean bean,String payorderno) {
        // TODO Auto-generated method stub
        ReaPayResultBean realPayPayResultBean = (ReaPayResultBean)bean.getResultObj();
        TxnsQuickpayModel txnsQuickpay = super.findByProperty("payorderno", payorderno).get(0);
        if(bean.isResultBool()){
            txnsQuickpay.setStatus("00");
        }else{
            txnsQuickpay.setStatus("02");
        }
        txnsQuickpay.setPayfinishtime(DateUtil.getCurrentDateTime());
        txnsQuickpay.setPayretcode(realPayPayResultBean.getResult_code());
        txnsQuickpay.setPayretinfo(realPayPayResultBean.getResult_msg());
        super.update(txnsQuickpay);
    }
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateTestPaySign(ResultBean bean,String payorderno) {
        // TODO Auto-generated method stub
        ReaPayResultBean realPayPayResultBean = (ReaPayResultBean)bean.getResultObj();
        TxnsQuickpayModel txnsQuickpay = super.findByProperty("payorderno", payorderno).get(0);
        if(bean.isResultBool()){
            txnsQuickpay.setStatus("00");
        }else{
            txnsQuickpay.setStatus("02");
        }
        txnsQuickpay.setPayfinishtime(DateUtil.getCurrentDateTime());
        txnsQuickpay.setPayretcode(realPayPayResultBean.getResult_code());
        txnsQuickpay.setPayretinfo(realPayPayResultBean.getResult_msg());
        super.update(txnsQuickpay);
    }
    
    
    /**
     *
     * @param onlineDepositShortBean
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void saveOnlineDepositShort(TradeBean trade,OnlineDepositShortBean onlineDepositShortBean) {
        // TODO Auto-generated method stub
        TxnsQuickpayModel txnsQuickpay = new TxnsQuickpayModel(trade,onlineDepositShortBean);
        
        super.save(txnsQuickpay);
    }
    /**
     *
     * @param bean
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateOnlineDepositShort(ResultBean bean) {
        // TODO Auto-generated method stub
        ZLPayResultBean zlPayResultBean = (ZLPayResultBean)bean.getResultObj();
        TxnsQuickpayModel txnsQuickpay = super.findByProperty("payorderno", zlPayResultBean.getMerchantSeqId()).get(0);
        if(bean.isResultBool()){
            txnsQuickpay.setStatus("00");
        }else{
            txnsQuickpay.setStatus("02");
        } 
        txnsQuickpay.setRetorderno(zlPayResultBean.getPnrSeqId());
        txnsQuickpay.setPayfinishtime(DateUtil.getCurrentDateTime());
        txnsQuickpay.setPayretcode(zlPayResultBean.getRespCode());
        txnsQuickpay.setPayretinfo(zlPayResultBean.getRespDesc());
        super.update(txnsQuickpay); 
    }
    /**
     *
     * @param bean
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateReaPaySMS(ResultBean bean,String payorderno) {
        ReaPayResultBean resultBean = (ReaPayResultBean)bean.getResultObj();
        TxnsQuickpayModel txnsQuickpay = super.findByProperty("payorderno", payorderno).get(0);
        if(bean.isResultBool()){
            txnsQuickpay.setStatus("00");
        }else{
            txnsQuickpay.setStatus("02");
        } 
        
        txnsQuickpay.setPayfinishtime(DateUtil.getCurrentDateTime());
        txnsQuickpay.setPayretcode(resultBean.getResult_code());
        txnsQuickpay.setPayretinfo(resultBean.getResult_msg());
        super.update(txnsQuickpay);
        
    }
    /**
     *
     * @param trade
     * @param creditBean
     * @return
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public String saveReaPayCreditSign(TradeBean trade, CreditBean creditBean) {
        TxnsQuickpayModel txnsQuickpay = new TxnsQuickpayModel(trade,creditBean);
        super.save(txnsQuickpay);
        return txnsQuickpay.getPayorderno();
    }
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public String saveTestCreditSign(TradeBean trade, CreditBean creditBean) {
        TxnsQuickpayModel txnsQuickpay = new TxnsQuickpayModel(trade,creditBean);
        super.save(txnsQuickpay);
        return txnsQuickpay.getPayorderno();
    }
    /**
     * 保存民生银行跨行快捷流水
     * @param trade
     * @return
     */
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public String saveCMBCOuterBankSign(TradeBean trade) {
        TxnsQuickpayModel txnsQuickpay = new TxnsQuickpayModel(trade,TradeTypeEnum.BANKSIGN);
        super.save(txnsQuickpay);
        return txnsQuickpay.getPayorderno();
    }
    
    /**
     *
     * @param trade
     * @param payBean
     * @return
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public String saveReaPayToPay(TradeBean trade, PayBean payBean) {
        TxnsQuickpayModel txnsQuickpay = new TxnsQuickpayModel(trade,payBean);
        super.save(txnsQuickpay);
        return txnsQuickpay.getPayorderno();
    }
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public String saveTestToPay(TradeBean trade, PayBean payBean) {
        TxnsQuickpayModel txnsQuickpay = new TxnsQuickpayModel(trade,payBean,"");
        super.save(txnsQuickpay);
        return txnsQuickpay.getPayorderno();
    }
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateReaPayToPay(ResultBean bean,String payorderno) {
        // TODO Auto-generated method stub
        ReaPayResultBean zlPayResultBean = (ReaPayResultBean)bean.getResultObj();
        TxnsQuickpayModel txnsQuickpay = super.findByProperty("payorderno", payorderno).get(0);
        if(bean.isResultBool()){
            txnsQuickpay.setStatus("00");
        }else{
            txnsQuickpay.setStatus("02");
        }
        txnsQuickpay.setPayfinishtime(DateUtil.getCurrentDateTime());
        txnsQuickpay.setPayretcode(zlPayResultBean.getResult_code());
        txnsQuickpay.setPayretinfo(zlPayResultBean.getResult_msg());
        super.update(txnsQuickpay);
    }
    /**
     *
     * @param trade
     * @param queryBean
     * @return
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public String saveReaPayQuery(TradeBean trade, QueryBean queryBean) {
        TxnsQuickpayModel txnsQuickpay = new TxnsQuickpayModel(trade,queryBean);
        super.save(txnsQuickpay);
        return txnsQuickpay.getPayorderno();
    }
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateReaPayQuery(ResultBean bean,String payorderno) {
        // TODO Auto-generated method stub
        ReaPayResultBean zlPayResultBean = (ReaPayResultBean)bean.getResultObj();
        TxnsQuickpayModel txnsQuickpay = super.findByProperty("payorderno", payorderno).get(0);
        if(bean.isResultBool()){
            txnsQuickpay.setStatus("00");
        }else{
            txnsQuickpay.setStatus("02");
        }
        txnsQuickpay.setPayfinishtime(DateUtil.getCurrentDateTime());
        txnsQuickpay.setPayretcode(zlPayResultBean.getResult_code());
        txnsQuickpay.setPayretinfo(zlPayResultBean.getStatus());
        super.update(txnsQuickpay);
    }
    /**
     *
     * @param trade
     * @param bindBean
     * @return
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public String saveReaPayBindSign(TradeBean trade, BindBean bindBean) {
        TxnsQuickpayModel txnsQuickpay = new TxnsQuickpayModel(trade,bindBean);
        super.save(txnsQuickpay);
        return txnsQuickpay.getPayorderno();
    }
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public String saveTestBindSign(TradeBean trade, BindBean bindBean){
        TxnsQuickpayModel txnsQuickpay = new TxnsQuickpayModel(trade,bindBean,"");
        super.save(txnsQuickpay);
        return txnsQuickpay.getPayorderno();
    }
    
    /**
     *
     * @param orderNo
     * @return
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public List<TxnsQuickpayModel> queryTxnsByOrderNo(String orderNo) {
        return (List<TxnsQuickpayModel>) super.queryByHQL(" from TxnsQuickpayModel where relateorderno = ? and paycode = ? ", new Object[]{orderNo,"1001"});
    }
    /**
     *
     * @param txnseqno
     * @return
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public String getReapayOrderNo(String txnseqno) {
        List<TxnsQuickpayModel> quickpayList =  (List<TxnsQuickpayModel>) super.queryByHQL(" from TxnsQuickpayModel where relatetradetxnseqno = ? order by id desc", new Object[]{txnseqno});
        if(quickpayList.size()>0){
            return quickpayList.get(0).getRelateorderno();
        }
        return null;
    }
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public String saveCMBCOuterWithholding(TradeBean trade){
        TxnsQuickpayModel txnsQuickpay = new TxnsQuickpayModel(trade,TradeTypeEnum.SUBMITPAY);
        super.save(txnsQuickpay);
        return txnsQuickpay.getPayorderno();
    }
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateCMBCSMSResult(String payorder,String retcode,String retinfo) {
        // TODO Auto-generated method stub
        String hql = "update TxnsQuickpayModel set payretcode=?,payretinfo=?,payfinishtime=?,status=? where payorderno=?";
        super.updateByHQL(hql, new Object[]{retcode,retinfo,DateUtil.getCurrentDateTime(),"00",payorder});
    }
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public String saveCMBCOuterBankCardSign(TradeBean trade) {
        TxnsQuickpayModel txnsQuickpay = new TxnsQuickpayModel(trade,TradeTypeEnum.BANKSIGN);
        super.save(txnsQuickpay);
        return txnsQuickpay.getPayorderno();
    }
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public String saveBossBankCardSign(TradeBean trade) {
        TxnsQuickpayModel txnsQuickpay = new TxnsQuickpayModel();
        txnsQuickpay.setId(OrderNumber.getInstance().generateID());
        txnsQuickpay.setPayorderno(OrderNumber.getInstance().generateCMBCQuickOrderNo());//支付订单号不对
        txnsQuickpay.setPayamt(Long.valueOf(trade.getAmount()));
        txnsQuickpay.setPaycommitime(DateUtil.getCurrentDateTime());
        txnsQuickpay.setRelatetradetxnseqno( trade.getTxnseqno());
        txnsQuickpay.setFirmemberno( ConsUtil.getInstance().cons.getBosspay_user_id());
        txnsQuickpay.setFirmembername(ConsUtil.getInstance().cons.getZlpay_chnl_merch_name());
        txnsQuickpay.setPayname(trade.getAcctName());
        txnsQuickpay.setPaynum(1L);
        txnsQuickpay.setPaycode("1001");
        txnsQuickpay.setPaydescr("博士金电-银行卡签约");
        txnsQuickpay.setPaytype("1000");
        txnsQuickpay.setStatus("01");
        txnsQuickpay.setMobile(trade.getMobile());
        txnsQuickpay.setInstitution(trade.getPayinstiId());
        super.save(txnsQuickpay);
        return txnsQuickpay.getPayorderno();
    }
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateBossBankCardSign(String payorderno,String retCode,String retInfo) {
    	 TxnsQuickpayModel txnsQuickpay = super.findByProperty("payorderno", payorderno).get(0);
         txnsQuickpay.setPayfinishtime(DateUtil.getCurrentDateTime());
         txnsQuickpay.setStatus("00");
         txnsQuickpay.setPayretcode(retCode);
         txnsQuickpay.setPayretinfo(retInfo);
         super.update(txnsQuickpay);
    }
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public String saveBossPay(TradeBean trade){
        TxnsQuickpayModel txnsQuickpay = new TxnsQuickpayModel();
        txnsQuickpay.setId(OrderNumber.getInstance().generateID());
        txnsQuickpay.setPayorderno(OrderNumber.getInstance().generateCMBCQuickOrderNo());//支付订单号不对
        txnsQuickpay.setPayamt(Long.valueOf(trade.getAmount()));
        txnsQuickpay.setPaycommitime(DateUtil.getCurrentDateTime());
        txnsQuickpay.setRelatetradetxnseqno( trade.getTxnseqno());
        txnsQuickpay.setFirmemberno( ConsUtil.getInstance().cons.getBosspay_user_id());
        txnsQuickpay.setFirmembername(ConsUtil.getInstance().cons.getZlpay_chnl_merch_name());
        txnsQuickpay.setPayname(trade.getAcctName());
        txnsQuickpay.setPaynum(1L);
        txnsQuickpay.setPaycode("1005");
        txnsQuickpay.setPaydescr("博士金电-实时代收");
        txnsQuickpay.setPaytype("1000");
        txnsQuickpay.setStatus("01");
        txnsQuickpay.setMobile(trade.getMobile());
        txnsQuickpay.setInstitution(trade.getPayinstiId());
        super.save(txnsQuickpay);
        return txnsQuickpay.getPayorderno();
    }
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateBossPayResult(String payorderno,String retCode,String retInfo,String retorderno){
        TxnsQuickpayModel txnsQuickpay = super.findByProperty("payorderno", payorderno).get(0);
        txnsQuickpay.setRetorderno(retorderno);
        txnsQuickpay.setPayfinishtime(DateUtil.getCurrentDateTime());
        txnsQuickpay.setStatus("00");
        txnsQuickpay.setPayretcode(retCode);
        txnsQuickpay.setPayretinfo(retInfo);
        super.update(txnsQuickpay);
    }
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public String saveBossPaySMS(TradeBean trade) {
    	 TxnsQuickpayModel txnsQuickpay = new TxnsQuickpayModel();
         txnsQuickpay.setId(OrderNumber.getInstance().generateID());
         txnsQuickpay.setPayorderno(OrderNumber.getInstance().generateCMBCQuickOrderNo());//支付订单号不对
         txnsQuickpay.setPayamt(Long.valueOf(trade.getAmount()));
         txnsQuickpay.setPaycommitime(DateUtil.getCurrentDateTime());
         txnsQuickpay.setRelatetradetxnseqno( trade.getTxnseqno());
         txnsQuickpay.setFirmemberno( ConsUtil.getInstance().cons.getBosspay_user_id());
         txnsQuickpay.setFirmembername(ConsUtil.getInstance().cons.getZlpay_chnl_merch_name());
         txnsQuickpay.setPayname(trade.getAcctName());
         txnsQuickpay.setPaynum(1L);
         txnsQuickpay.setPaycode("1004");
         txnsQuickpay.setPaydescr("博士金电-短信验证码");
         txnsQuickpay.setPaytype("1000");
         txnsQuickpay.setStatus("00");
         txnsQuickpay.setMobile(trade.getMobile());
         txnsQuickpay.setInstitution(trade.getPayinstiId());
         super.save(txnsQuickpay);
         return txnsQuickpay.getPayorderno();
    }
}
