/* 
 * IGateOrderService.java  
 * 
 * version TODO
 *
 * 2015年8月27日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.zlebank.zplatform.acc.exception.AbstractBusiAcctException;
import com.zlebank.zplatform.acc.exception.AccBussinessException;
import com.zlebank.zplatform.acc.exception.IllegalEntryRequestException;
import com.zlebank.zplatform.member.bean.enums.MemberType;
import com.zlebank.zplatform.trade.bean.ReaPayResultBean;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.ZLPayResultBean;
import com.zlebank.zplatform.trade.bean.gateway.OrderBean;
import com.zlebank.zplatform.trade.bean.gateway.OrderRespBean;
import com.zlebank.zplatform.trade.bean.gateway.QueryBean;
import com.zlebank.zplatform.trade.bean.gateway.QueryResultBean;
import com.zlebank.zplatform.trade.bean.gateway.RiskRateInfoBean;
import com.zlebank.zplatform.trade.bean.wap.WapCardBean;
import com.zlebank.zplatform.trade.bean.wap.WapDebitCardSingRespBean;
import com.zlebank.zplatform.trade.bean.wap.WapOrderRespBean;
import com.zlebank.zplatform.trade.bean.wap.WapRefundBean;
import com.zlebank.zplatform.trade.bean.wap.WapWithdrawAccBean;
import com.zlebank.zplatform.trade.bean.wap.WapWithdrawBean;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsOrderinfoModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年8月27日 上午10:54:54
 * @since 
 */
public interface IGateWayService extends IBaseService<TxnsOrderinfoModel, Long>{
	
	/**
	 * 订单信息验签
	 * @param order
	 * @return
	 */
    public ResultBean verifyOrder(OrderBean order);
    
    /**
     * 保存订单信息和交易信息
     * @param order
     * @param riskRateInfoBean
     * @return
     * @throws TradeException
     */
    public String dealWithOrder(OrderBean order,RiskRateInfoBean riskRateInfoBean) throws TradeException;
    /**
     * 
     * @param txnseqno
     * @param gateWayOrderNo
     * @param zlPayResultBean
     */
    public void saveSuccessTrade(String txnseqno,String gateWayOrderNo,ZLPayResultBean zlPayResultBean);
    @Deprecated
    public TxnsOrderinfoModel getOrderinfoByOrderNo(String orderNo);
    @Deprecated
    public ResultBean generateRespMessage(String orderNo,String memberId);
    /**
     * 保存证联支付交易失败信息
     * @param txnseqno
     * @param gateWayOrderNo
     * @param zlPayResultBean
     */
    public void saveFailTrade(String txnseqno,String gateWayOrderNo,ZLPayResultBean zlPayResultBean);
    /**
     * 保存融宝交易成功信息
     * @param txnseqno
     * @param gateWayOrderNo
     * @param payResultBean
     */
    public void saveSuccessReaPayTrade(String txnseqno,String gateWayOrderNo,ReaPayResultBean payResultBean);
    /**
     * 保存融宝交易失败信息
     * @param txnseqno
     * @param gateWayOrderNo
     * @param payResultBean
     */
    public void saveFailReaPayTrade(String txnseqno,String gateWayOrderNo,ReaPayResultBean payResultBean);
    @Deprecated
    public ResultBean decryptCustomerInfo(String memberId,String encryptData);
    /**
     * 更新订单状态为交易成功
     * @param txnseqno
     * @throws TradeException
     */
    public void updateOrderToStartPay(String txnseqno) throws TradeException;
    public ResultBean verifyQueryOrder(QueryBean queryBean);
    public ResultBean generateQueryResultBean(QueryResultBean queryResultBean);
    /**
     * 保存交易流水数据和交易订单数据
     * @param order
     * @param riskRateInfoBean
     * @return
     * @throws TradeException
     */
    public String dealWithWebOrder(OrderBean order,RiskRateInfoBean riskRateInfoBean) throws TradeException;
    /**
     * 通过TN获取订单新
     * @param tn
     * @return
     */
    public TxnsOrderinfoModel getOrderinfoByTN(String tn) ;
    /**
     * 保存账务交易流水信息
     * @param txnseqno
     * @param gateWayOrderNo
     * @param resultBean
     */
    public void saveAcctTrade(String txnseqno,String gateWayOrderNo,ResultBean resultBean);
    /**
     * 生成订单并返回TN
     * @param json
     * @return
     * @throws TradeException
     * @throws UnsupportedEncodingException
     */
    public String createWapOrder(String json)throws TradeException, UnsupportedEncodingException;
    public void verifyWapOrder(JSONObject order) throws TradeException;
    public void verifyRepeatWapOrder(String orderNo,String txntime,String amount,String merchId,String memberId) throws TradeException;
    /***
     * 校验web订单是否重复
     * @param orderNo
     * @param txntime
     * @param amount
     * @param merchId
     * @param memberId
     * @throws TradeException
     */
    public void verifyRepeatWebOrder(String orderNo,String txntime,String amount,String merchId,String memberId) throws TradeException;
    public void verifyMerch(String merchId,String subMerchId) throws TradeException;
    public void signWapMessage(WapOrderRespBean orderRespBean);
    /**
     * 银行卡签约
     * @param json
     * @return
     * @throws TradeException
     */
    public String bankCardSign(String json) throws TradeException;
    
    public void signWapCardSignMessage(WapDebitCardSingRespBean respBean);
    /**
     * 发送短信验证码
     * @param json
     * @throws TradeException
     */
    public void sendSMSMessage(String json) throws TradeException;
    /**
     * 确认支付
     * @param json
     * @throws TradeException
     */
    public void submitPay(String json) throws TradeException;
    /**
	 * 查询会员已绑卡信息
	 * 
	 * @param json
	 * @return
	 * @throws TradeException
	 */
    public String querySignInfo(String json) throws TradeException;
    /**
     * 账户支付
     * @param json
     * @throws TradeException
     */
    public void accountPay(String json) throws TradeException;
    /**
     * 查询支持的银行列表
     * @param json
     * @return
     */
    public String queryUsableBankCard(String json);
    /**
     * 处理退款订单数据
     * @param refundBean
     * @return
     * @throws TradeException
     */
    public String dealWithRefundOrder(WapRefundBean refundBean) throws TradeException;
    /**
     * 处理提现订单数据
     * @param withdrawBean
     * @param withdrawAccBean
     * @return
     * @throws TradeException
     */
    public String dealWithWithdrawOrder(WapWithdrawBean withdrawBean,WapWithdrawAccBean withdrawAccBean) throws TradeException;
    /**
     * 提现
     * @param json
     * @return
     * @throws TradeException
     */
    public String withdraw(String json) throws TradeException;
    /**
     * 退款
     * @param json
     * @return
     * @throws TradeException
     */
    public String refund(String json) throws TradeException;
    @Deprecated
    public ResultBean generateAsyncRespMessage(String orderNo,String memberId);
    /**
     * 生成异步通知报文
     * @param txnseqno
     * @return
     */
    public ResultBean generateAsyncRespMessage(String txnseqno);
    /**
     * 更新交易订单状态为支付失败
     * @param orderNo
     */
    public void updateOrderToFail(String txnseqno);
    public OrderRespBean generateWithdrawRespMessage(String orderNo);
    /**
     * 校验会员业务
     * @param orderBean
     * @param rateInfoBean
     * @return
     */
    public ResultBean validateMemberBusiness(OrderBean orderBean,RiskRateInfoBean rateInfoBean);
    /**
     * 通过订单号和会员号获取交易订单信息
     * @param orderNo
     * @param memberId
     * @return
     */
    public TxnsOrderinfoModel getOrderinfoByOrderNoAndMemberId(String orderNo,String memberId) ;
    /**
     * 获取手续费
     * @param txnsLog
     * @return
     */
    public Long getTxnFee(TxnsLogModel txnsLog);

    /**
     * 绑定银行卡
     * @param memberId 合作机构号/商户号
     * @param personMemberId 个人会员号
     * @param cardBean
     * @return
     */
    public ResultBean bindingBankCard(String memberId,String personMemberId,WapCardBean cardBean);
    
    /**
     * 
     * @param memberId 会员号
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @param page 页数
     * @param rows 行数
     * @return
     */
    public List<Map<String, Object>> queryOrderInfo(String memberId,String beginDate,String endDate,int page,int rows);

    public String dealWithWapOrder(OrderBean order) throws TradeException;
    
    /**
     * 
     * @param orderNo
     * @param memberId
     * @return
     */
    public TxnsOrderinfoModel getPersonOrder(String orderNo,String memberId);
    
    /**
     * 
     * @param memberId
     * @param beginDate
     * @param endDate
     * @return
     */
    public long queryOrderInfoCount(String memberId,String beginDate,String endDate);
    
    /**
     * 检查资金账户状态
     * @param merchNo 商户号
     * @param memberId 会员号
     * @throws TradeException
     */
    public void checkBusiAcct(String merchNo,String memberId) throws TradeException;

    /**
     * 处理商户收银台的订单，保存交易流水数据和交易订单数据
     * @param order
     * @return
     * @throws TradeException
     */
    public String dealWithMerchOrder(OrderBean order) throws TradeException;
    
    /**
     * 验证会员支付密码
     * @param memberId
     * @param pwd
     * @param memberType
     * @return
     * @throws TradeException
     */
    public boolean validatePayPWD(String memberId,String pwd,MemberType memberType) throws TradeException;
    
    public String dealWithRefundOrder(String orderNo,String merchNo,String txnAmt) throws TradeException;
    
    /**
     * 通过交易流水号获取交易订单信息
     * @param txnseqno
     * @return
     */
    public TxnsOrderinfoModel getOrderByTxnseqno(String txnseqno);
    
    /**
     * 获取退款手续费
     * @param txnseqno
     * @param merchNo
     * @param txnAmt
     * @param busicode
     * @return
     */
    public Long getRefundFee(String txnseqno,String merchNo,String txnAmt,String busicode);
    
    public TxnsOrderinfoModel getOrderinfoByOrderNoAndMerch(String orderNo,
			String merchNo);
    /**
     * 个人提现
     * @param tradeBean
     * @return
     * @throws AccBussinessException
     * @throws IllegalEntryRequestException
     * @throws AbstractBusiAcctException
     * @throws NumberFormatException
     * @throws TradeException
     */
    public Map<String, Object> withdraw(TradeBean tradeBean) throws AccBussinessException, IllegalEntryRequestException, AbstractBusiAcctException, NumberFormatException, TradeException;
    
    /**
     * 订单数据校验
     * @param order
     * @return
     */
    public ResultBean validateOrder(OrderBean order);
}
