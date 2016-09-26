/* 
 * ITxnsLogService.java  
 * 
 * version TODO
 *
 * 2015年8月29日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import java.util.List;
import java.util.Map;

import com.zlebank.zplatform.acc.exception.AbstractBusiAcctException;
import com.zlebank.zplatform.acc.exception.AccBussinessException;
import com.zlebank.zplatform.acc.exception.IllegalEntryRequestException;
import com.zlebank.zplatform.trade.bean.AccountTradeBean;
import com.zlebank.zplatform.trade.bean.AppPartyBean;
import com.zlebank.zplatform.trade.bean.PayPartyBean;
import com.zlebank.zplatform.trade.bean.ReaPayResultBean;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.enums.TradeStatFlagEnum;
import com.zlebank.zplatform.trade.bean.gateway.QueryBean;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.model.PojoBankTransferData;
import com.zlebank.zplatform.trade.model.PojoTranData;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsWithholdingModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年8月29日 下午3:26:46
 * @since 
 */
public interface ITxnsLogService extends IBaseService<TxnsLogModel, String>{
	/**
	 * 校验订单重复性
	 * @param orderId
	 * @return
	 */
    public ResultBean verifyRepeatOrder(String orderId);
    /**
     * 更新支付方信息
     * @param payPartyBean
     * @return
     */
    public ResultBean updatePayInfo_Fast(PayPartyBean payPartyBean);
    /**
     * 更新路由层次信息
     * @param txnseqno
     * @param routId
     * @param currentStep
     * @param cashCode
     * @return
     */
    @Deprecated
    public ResultBean updateRoutInfo(String txnseqno,String routId,String currentStep,String cashCode);
    /**
     * 更新应用方信息（账务信息）
     * @param appParty
     * @return
     */
    public ResultBean updateAppInfo(AppPartyBean appParty);
    
    /**
     * 通过交易流水号获取交易信息
     * @param txnseqno
     * @return
     */
    public TxnsLogModel getTxnsLogByTxnseqno(String txnseqno);
    /**
     * 获取交易手续费
     * @param txnsLog
     * @return
     */
    public Long getTxnFee(TxnsLogModel txnsLog);
    @Deprecated
    public ResultBean updatePayInfo_ecitic(PayPartyBean payPartyBean);
    
    /**
     * 更新融宝交易应答信息
     * @param txnseqno
     * @param payResult
     */
    public void updateReaPayRetInfo(String txnseqno,ReaPayResultBean payResult);
    
    public TxnsLogModel queryTrade(QueryBean queryBean);
    /**
     * 保存账户支付交易数据
     * @param accountTrade
     * @throws TradeException
     */
    public void saveAccountTrade(AccountTradeBean accountTrade) throws TradeException;
    /**
     * 更新账户支付交易结果
     * @param accountTrade
     * @param resultBean
     * @throws TradeException
     */
    public void updateAccountTrade(AccountTradeBean accountTrade,ResultBean resultBean) throws TradeException;
    @Deprecated
    public void updateWapRoutInfo(String txnseqno,String routId) throws TradeException;
    public TxnsLogModel queryLogByTradeseltxn(String queryId);
    /**
     * 更新应用方（账务）处理结果信息
     * @param txnseqno
     * @param appOrderStatus
     * @param appOrderinfo
     */
    public void updateAppStatus(String txnseqno,String appOrderStatus,String appOrderinfo);
    /**
     * 交易风控
     * @param txnseqno
     * @param merchId
     * @param subMerchId
     * @param memberId
     * @param busiCode
     * @param txnAmt
     * @param cardType
     * @param cardNo
     * @throws TradeException
     */
    public void tradeRiskControl(String txnseqno,String merchId,String subMerchId,String memberId,String busiCode,String txnAmt,String cardType,String cardNo)throws TradeException;
    /**
     * 更新实际业务代码
     * @param txnseqno
     * @param busicode
     */
    public void updateAccBusiCode(String txnseqno,String busicode);
    
    /**
     * 初始化支付方和中心应答信息
     * @param txnseqno
     */
    public void initretMsg(String txnseqno)  throws TradeException;
    
    /**
     * 更新民生跨行代扣应答信息
     * @param txnseqno
     * @param withholdin
     */
    public void updateCMBCWithholdingRetInfo(String txnseqno, TxnsWithholdingModel withholdin);
    
    /**
     * 更新支付方返回结果
     * @param txnseqno
     * @param retcode
     * @param retinfo
     */
    public void updatePayInfo_Fast_result(String txnseqno,String retcode,String retinfo);
    
    /**
     * 跟新交易流水
     * @param txnsLog
     */
    public void updateTxnsLog(TxnsLogModel txnsLog);
    
    /**
     * 保存划拨交易流水交易
     * @param transferDataList
     */
    public void saveTransferLogs(List<PojoTranData> transferDataList);
    
    /**
     * 保存划拨交易流水交易
     * @param transferDataList
     */
    public void saveBankTransferLogs(List<PojoBankTransferData> transferDataList);
    
    /**
     * 获取风控策略
     * @param orders
     * @return
     */
    public List<Map<String,String>> getRiskStrategy(int orders);
    
    /**
     * 保存交易流水日志
     * @param txnsLogModel
     * @throws TradeException
     */
    public void saveTxnsLog(TxnsLogModel txnsLogModel) throws TradeException;
    
    /**
     * 更新中心应答信息
     * @param txnseqno
     * @param retcode
     * @param retinfo
     */
    public void updateCoreRetResult(String txnseqno,String retcode,String retinfo);
    
    public List<?> getAllMemberByDate(String date);
    public List<?> getAllMemberByDateByCharge(String date);
    public List<?> getSumExpense(String memberId,String date);
    public List<?> getAllMemberDetailedByDate(String memberId,String date);
    public List<?> getSumRefund(String memberId,String date);
    public List<?> getCountSpendingAccount(String memberId,String date);
    public List<?> getCountHandPay(String memberId,String date);
    
    /**
     * 保存博士金电交易流水
     * @param transferDataList
     */
    public void saveBossPayBankTransferLogs(List<PojoBankTransferData> transferDataList);
    
    /**

     * 更新网关支付结果
     * @param payPartyBean
     * @return
     */
    public ResultBean updateGateWayPayResult(PayPartyBean payPartyBean);
    /**
     * 对账任务
     */
    public void excuteRecon();

    
    /**
     * <p>
     * 获取发生了代付业务的商户列表
     * </p>
     * <p>
     * 商户对账文件生成程序需要重构，这个接口包含在内，暂时先用
     * </p>
     * 
     * @param date
     * @return
     */
    public List<?> getInsteadMemberByDate(String date);
    /**
     * <p>
     * 获取商户代付业务对账汇总信息
     * </p>
     * <p>
     * 商户对账文件生成程序需要重构，这个接口包含在内，暂时先用
     * </p>
     * 
     * @param date
     * @return
     */
    public List<?> getSumInstead(String memberId, String date);
    /**
     * <p>
     *  获取商户代付业务对账明细信息
     * </p>
     * <p>
     * 商户对账文件生成程序需要重构，这个接口包含在内，暂时先用
     * </p>
     * 
     * @param date
     * @return
     */
    public List<?> getInsteadMerchantDetailedByDate(String memberId, String date);
    
    /**
     * 通过支付订单号获取微信交易流水
     * @param payOrderNo 支付订单号
     * @return
     */
    public TxnsLogModel getTxnsLogByPayOrderNo(String payOrderNo);
    
    /**
     * 更新交易支付方信息
     * @param txnseqno
     * @param payrettsnseqno
     * @param retcode
     * @param retinfo
     */
    public void updatePayInfo_Fast_result(String txnseqno,String payrettsnseqno,String retcode,String retinfo);
    
    /**
     * 定时处理资金结算任务
     */
    public void excuteSetted() throws AccBussinessException, AbstractBusiAcctException, NumberFormatException, IllegalEntryRequestException;
    /**
     * 
     * @param txnseqno
     * @param payrettsnseqno
     * @param retcode
     * @param retinfo
     */
    public void updateWeChatRefundResult(String txnseqno,String payrettsnseqno,String retcode,String retinfo);
    
   /***
    * 查询微信退款申请成功的订单
    * @param refundtype
    * @param mins
    * @return
    */
	public List<?> getRefundOrderInfo(String refundtype,int mins);
	
	/***
	 * 查询交易日志
	 * @param map
	 * @return
	 */
	public List<Object> queryTxnsLog(Map<String,Object> map);
	

	/**
	 * 更新匿名下单，登陆支付
	 * @param txnseqno
	 * @param memberId
	 */
	public void updateAnonOrderToMemberOrder(String txnseqno,String memberId);
	
	/**
	 * 更新交易状态标记位
	 * @param txnseqno
	 * @param tradeStatFlagEnum
	 */
	public void updateTradeStatFlag(String txnseqno,TradeStatFlagEnum tradeStatFlagEnum);
	
	/**
	 * 更新民生核心交易数据
	 * @param txnseqno
	 */
	public void updateCMBCTradeData(PayPartyBean payPartyBean);
	
	/**
	 * 更新短信验证码错误交易数据
	 * @param txnseqno
	 * @param retcode
	 * @param retinfo
	 */
	public void updateSMSErrorData(String txnseqno,String retcode,String retinfo);
	
	/**
	 * 更新微信核心交易数据
	 * @param payPartyBean
	 */
	public void updateWeChatTradeData(PayPartyBean payPartyBean);
	
	/**
	 * 更新交易失败时的数据
	 * @param payPartyBean payinst必须有否则无法更新成功
	 */
	public void updateTradeFailed(PayPartyBean payPartyBean);
	
	/**
	 * 更新交易核心数据和支付方数据
	 * @param payPartyBean
	 */
	public void updateTradeData(PayPartyBean payPartyBean);
}
