/* 
 * ITxnsQuickpayService.java  
 * 
 * version TODO
 *
 * 2015年8月31日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import java.util.List;

import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.reapal.BindBean;
import com.zlebank.zplatform.trade.bean.reapal.CreditBean;
import com.zlebank.zplatform.trade.bean.reapal.DebitBean;
import com.zlebank.zplatform.trade.bean.reapal.PayBean;
import com.zlebank.zplatform.trade.bean.reapal.QueryBean;
import com.zlebank.zplatform.trade.bean.reapal.SMSBean;
import com.zlebank.zplatform.trade.bean.zlpay.MarginRegisterBean;
import com.zlebank.zplatform.trade.bean.zlpay.MarginSmsBean;
import com.zlebank.zplatform.trade.bean.zlpay.OnlineDepositShortBean;
import com.zlebank.zplatform.trade.model.TxnsQuickpayModel;
import com.zlebank.zplatform.trade.model.TxnsWithholdingModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年8月31日 上午10:15:00
 * @since 
 */
public interface ITxnsQuickpayService extends IBaseService<TxnsQuickpayModel, Long>{
	/**
	 * 保存证联支付发送短信验证码流水
	 * @param trade
	 * @param marginSmsBean
	 */
    public void saveMobileCode(TradeBean trade,MarginSmsBean marginSmsBean);
    /**
     * 更新证联支付发送短信验证流水
     * @param bean
     */
    public void updateMobileCode(ResultBean bean);
    /**
     * 保存同步开户流水
     * @param trade
     * @param marginRegisterBean
     */
    public void saveMarginRegister(TradeBean trade,MarginRegisterBean marginRegisterBean);
    /**
     * 更新同步开会流水 
     * @param bean
     */
    public void updateMarginRegister(ResultBean bean);
    /**
     * 保存在线入金流水
     * @param trade
     * @param onlineDepositShortBean
     */
    public void saveOnlineDepositShort(TradeBean trade,OnlineDepositShortBean onlineDepositShortBean);
    /**
     * 更新在线入金流水
     * @param bean
     */
    public void updateOnlineDepositShort(ResultBean bean);
    /**
     * 保存融宝借记卡签约流水
     * @param trade
     * @param creditBean
     * @return
     */
    public TxnsQuickpayModel saveReaPayDebitSign(TradeBean trade,DebitBean creditBean);
    /**
     * 保存融宝发送短信验证码流水
     * @param trade
     * @param smsBean
     * @return
     */
    public String saveReaPaySMS(TradeBean trade,SMSBean smsBean);
    /**
     * 保存融宝信用卡签约流水
     * @param trade
     * @param creditBean
     * @return
     */
    public String saveReaPayCreditSign(TradeBean trade,CreditBean creditBean);
    /**
     * 保存已绑卡签约流水
     * @param trade
     * @param bindBean
     * @return
     */
    public String saveReaPayBindSign(TradeBean trade,BindBean bindBean);
    /**
     * 保存融宝确认支付流水
     * @param trade
     * @param payBean
     * @return
     */
    public String saveReaPayToPay(TradeBean trade,PayBean payBean);
    /**
     * 保存融宝交易查询流水
     * @param trade
     * @param queryBean
     * @return
     */
    public String saveReaPayQuery(TradeBean trade,QueryBean queryBean);
    /**
     * 更新融宝短信流水
     * @param bean
     * @param payorderno
     */
    public void updateReaPaySMS(ResultBean bean,String payorderno);
    /**
     * 更新融宝支付流水
     * @param bean
     * @param payorderno
     */
    public void updateReaPaySign(ResultBean bean,String payorderno);
    /**
     * 更新融宝交易查询流水
     * @param bean
     * @param payorderno
     */
    public void updateReaPayQuery(ResultBean bean,String payorderno);
    /**
     * 通过订单号查询快捷交易流水
     * @param orderNo
     * @return
     */
    public List<TxnsQuickpayModel> queryTxnsByOrderNo(String orderNo);
    /**
     * 通过交易序列号获取融宝订单号
     * @param txnseqno
     * @return
     */
    public String getReapayOrderNo(String txnseqno);
    /**
     * 保存测试渠道交绑卡签约
     * @param trade
     * @param bindBean
     * @return
     */
    public String saveTestBindSign(TradeBean trade, BindBean bindBean);
    /**
     * 测试渠道
     * @param bean
     * @param payorderno
     */
    public void updateTestPaySign(ResultBean bean,String payorderno) ;
    /**
     * 测试渠道
     * @param trade
     * @param debitBean
     * @return
     */
    public TxnsQuickpayModel saveTestDebitSign(TradeBean trade,DebitBean debitBean);
    /**
     * 测试渠道
     * @param trade
     * @param creditBean
     * @return
     */
    public String saveTestCreditSign(TradeBean trade, CreditBean creditBean);
    /**
     * 测试渠道
     * @param bean
     * @param payorderno
     */
    public void updateTestSign(ResultBean bean,String payorderno);
    /**
     * 测试渠道
     * @param trade
     * @param smsBean
     * @return
     */
    public String saveTestSMS(TradeBean trade,SMSBean smsBean);
    /**
     * 
     * @param trade
     * @param payBean
     * @return
     */
    public String saveTestToPay(TradeBean trade, PayBean payBean);
    
    /**
     * 保存民生银行跨行快捷流水
     * @param trade
     * @return
     */
    public String saveCMBCOuterBankSign(TradeBean trade);
    
    /**
     * 保存民生银行跨行代扣流水
     * @param trade
     */
    public String saveCMBCOuterWithholding(TradeBean trade);
    
    /**
     * 更新民生银行快捷支付结果
     * @param withholding
     * @param payorderno
     */
    public void updateCMBCWithholdingResult(TxnsWithholdingModel withholding,String payorderno);
    
    /**
     * 更新民生银行代扣短信验证交易结果
     * @param payorder
     * @param retcode
     * @param retinfo
     */
    public void updateCMBCSMSResult(String payorder,String retcode,String retinfo);
    
    /**
     * 保存民生银行卡绑卡签约流水
     * @param realnameAuth
     * @return
     */
    public String saveCMBCOuterBankCardSign(TradeBean trade);
    
    /**
     * 保存博士金电银行卡认证记录
     * @param trade
     * @return
     */
    public String saveBossBankCardSign(TradeBean trade);
    
    /**
     * 保存博士金电交易流水
     * @param trade
     * @return
     */
    public String saveBossPay(TradeBean trade);
    
    /**
     * 更新快捷交易结果
     * @param payorderno
     * @param retCode
     * @param retInfo
     */
    public void updateBossPayResult(String payorderno,String retCode,String retInfo,String retorderno);
    
    /**
     * 保存博士金电短信验证码流水
     * @param trade
     * @return
     */
    public String saveBossPaySMS(TradeBean trade);
}
