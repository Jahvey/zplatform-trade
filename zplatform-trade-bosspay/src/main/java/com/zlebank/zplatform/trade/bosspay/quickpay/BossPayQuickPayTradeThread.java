
package com.zlebank.zplatform.trade.bosspay.quickpay;

import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.commons.dao.pojo.BusiTypeEnum;
import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.commons.utils.RSAUtils;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.member.bean.enums.TerminalAccessType;
import com.zlebank.zplatform.member.service.CoopInstiService;
import com.zlebank.zplatform.member.service.MerchMKService;
import com.zlebank.zplatform.sms.service.ISMSService;
import com.zlebank.zplatform.trade.adapter.accounting.IAccounting;
import com.zlebank.zplatform.trade.adapter.quickpay.IQuickPayTrade;
import com.zlebank.zplatform.trade.bean.AppPartyBean;
import com.zlebank.zplatform.trade.bean.PayPartyBean;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.enums.ChnlTypeEnum;
import com.zlebank.zplatform.trade.bean.enums.TradeTypeEnum;
import com.zlebank.zplatform.trade.bean.gateway.OrderAsynRespBean;
import com.zlebank.zplatform.trade.bosspay.bean.RealtmcolltnQueryResponseBean;
import com.zlebank.zplatform.trade.bosspay.enums.StatusEnum;
import com.zlebank.zplatform.trade.bosspay.service.BossPayService;
import com.zlebank.zplatform.trade.dao.ITxnsOrderinfoDAO;
import com.zlebank.zplatform.trade.dao.RspmsgDAO;
import com.zlebank.zplatform.trade.factory.AccountingAdapterFactory;
import com.zlebank.zplatform.trade.model.PojoRspmsg;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsOrderinfoModel;
import com.zlebank.zplatform.trade.service.IQuickpayCustService;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.ITxnsQuickpayService;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.ObjectDynamic;
import com.zlebank.zplatform.trade.utils.SMSThreadPool;
import com.zlebank.zplatform.trade.utils.SMSUtil;
import com.zlebank.zplatform.trade.utils.SpringContext;
import com.zlebank.zplatform.trade.utils.SynHttpRequestThread;
import com.zlebank.zplatform.trade.utils.UUIDUtil;

/**
 * 民生银行代扣快捷支付 Class Description
 *
 * @author guojia
 * @version
 * @date 2015年12月11日 上午11:46:07
 * @since
 */
public class BossPayQuickPayTradeThread implements IQuickPayTrade {
	private static final Log log = LogFactory
			.getLog(BossPayQuickPayTradeThread.class);
	private static final String PAYINSTID = "92000001";
	private TradeBean tradeBean;
	private TradeTypeEnum tradeType;
	private ITxnsQuickpayService txnsQuickpayService;
	private ITxnsLogService txnsLogService;
	private ITxnsOrderinfoDAO txnsOrderinfoDAO;
	private IQuickpayCustService quickpayCustService;
	private ISMSService smsService;
	private BossPayService bossPayService;
	private MerchMKService merchMKService;
	private CoopInstiService coopInstiService;
	private RspmsgDAO rspmsgDAO;
	
	public BossPayQuickPayTradeThread() {
		txnsQuickpayService = (ITxnsQuickpayService) SpringContext.getContext().getBean("txnsQuickpayService");
		txnsLogService = (ITxnsLogService) SpringContext.getContext().getBean("txnsLogService");
		txnsOrderinfoDAO = (ITxnsOrderinfoDAO) SpringContext.getContext().getBean("txnsOrderinfo");
		quickpayCustService = (IQuickpayCustService) SpringContext.getContext().getBean("quickpayCustService");
		smsService = (ISMSService) SpringContext.getContext().getBean("smsService");
		bossPayService = (BossPayService) SpringContext.getContext().getBean("bossPayService");
		merchMKService = (MerchMKService) SpringContext.getContext().getBean("merchMKService");
		coopInstiService = (CoopInstiService) SpringContext.getContext().getBean("coopInstiService");
		rspmsgDAO = (RspmsgDAO) SpringContext.getContext().getBean("rspmsgDAO");
	}

	@Override
	public void run() {
		if (tradeBean == null) {
			return;
		}
		switch (tradeType) {
			case SENDSMS:
				sendSms(tradeBean);
				break;
			case SUBMITPAY:
				submitPay(tradeBean);
				break;
			case BANKSIGN:
				bankSign(tradeBean);
				break;
			default:
				break;
		}

	}

	@Override
	public ResultBean sendSms(TradeBean trade) {
		String mobile = trade.getMobile();
		String payorderNo = txnsQuickpayService.saveBossPaySMS(trade);
		SMSThreadPool.getInstance().executeMission(
				new SMSUtil(mobile, "", trade.getTn(), DateUtil
						.getCurrentDateTime(), payorderNo, trade
						.getMiniCardNo(), trade.getAmount_y()));
		return null;
	}

	

	@Override
	public ResultBean submitPay(TradeBean trade) {
		ResultBean resultBean = null;
		try {
			log.info("boss submit Pay start!");
			if (log.isDebugEnabled()) {
				try {
					log.debug(JSON.toJSONString(trade));
				} catch (Exception e) {
				}
			}
			resultBean = null;
			int retCode = smsService.verifyCode(trade.getMobile(),
					trade.getTn(), trade.getIdentifyingCode());
			if (retCode == 2) {
				resultBean = new ResultBean("30HK", "交易失败，动态口令或短信验证码校验失败");
			} else if (retCode == 3) {
				resultBean = new ResultBean("30HK", "交易失败，动态口令或短信验证码校验失败");
			}
			// 更新支付方信息
			PayPartyBean payPartyBean = new PayPartyBean(trade.getTxnseqno(),
					"01", "", PAYINSTID,
					ConsUtil.getInstance().cons.getBosspay_user_id(), "",
					DateUtil.getCurrentDateTime(), "", trade.getCardNo());
			payPartyBean.setPanName(trade.getAcctName());
			txnsLogService.updatePayInfo_Fast(payPartyBean);
			
			if (resultBean != null) {
				txnsLogService.updatePayInfo_Fast_result(
						tradeBean.getTxnseqno(), resultBean.getErrCode(),
						resultBean.getErrMsg());
				txnsLogService.updateCoreRetResult(tradeBean.getTxnseqno(),
						resultBean.getErrCode(), resultBean.getErrMsg());
				log.info(JSON.toJSONString(resultBean));
				//TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(tradeBean.getTxnseqno());
                txnsOrderinfoDAO.updateOrderToFail(tradeBean.getTxnseqno());
				return resultBean;
			}
			trade.setPayinstiId(PAYINSTID);
			
			// 记录快捷交易流水
			String payorderno = txnsQuickpayService.saveBossPay(trade);
			//10~20秒会有交易结果
			resultBean = bossPayService.realCollecting(trade);
			RealtmcolltnQueryResponseBean queryResponseBean = (RealtmcolltnQueryResponseBean)resultBean.getResultObj();
			List<String> retList = JSON.parseArray(queryResponseBean.getStatus(), String.class);
			String retCode_ = retList.get(0);
			String recInfo = retList.get(1);
			if (resultBean.isResultBool()) {
				
				// 更新快捷交易流水
				txnsQuickpayService.updateBossPayResult(payorderno,retCode_,recInfo,queryResponseBean.getSerialNum());
				//保存交易成功数据并进行账务处理
				dealWithSuccessTrade(tradeBean.getTxnseqno(),queryResponseBean);
			} else {// 交易失败
				txnsQuickpayService.updateBossPayResult(payorderno,retCode_,recInfo,queryResponseBean.getSerialNum());
				//保存交易失败数据
				saveFailedBossTrade(trade.getTxnseqno(),queryResponseBean);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			txnsOrderinfoDAO.updateOrderToFail(tradeBean.getTxnseqno());
			resultBean = new ResultBean("T000", "交易失败");
		}

		log.info("CMBC submit Pay end!");

		return resultBean;
	}
	
	public void saveFailedBossTrade(String txnseqno,RealtmcolltnQueryResponseBean queryResponseBean){
        TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
        txnsLog.setPayordfintime(DateUtil.getCurrentDateTime());
        PojoRspmsg msg = rspmsgDAO.getRspmsgByChnlCode(ChnlTypeEnum.BOSSPAY, queryResponseBean.getStatus());
        if(msg!=null){
            txnsLog.setRetcode(msg.getWebrspcode());
            txnsLog.setRetinfo(msg.getRspinfo());
        }else{
            txnsLog.setRetcode("0052");
            txnsLog.setRetinfo("交易失败，系统忙，请稍后再试！");
        }
        txnsLog.setRetdatetime(DateUtil.getCurrentDateTime());
        txnsLog.setTradestatflag("00000001");
        txnsLog.setTradetxnflag("10000000");
        txnsLog.setRelate("10000000");
        txnsLog.setTradeseltxn(UUIDUtil.uuid());
        txnsLog.setPayrettsnseqno(queryResponseBean.getSerialNum());
        txnsLog.setPayretcode(queryResponseBean.getStatus());
        txnsLog.setPayretinfo(StatusEnum.fromValue(queryResponseBean.getStatus()).getMessage());
        txnsLogService.updateTxnsLog(txnsLog);
        //订单状态更新为失败
        txnsOrderinfoDAO.updateOrderToFail(txnseqno);
        
    }
	

	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
    public void dealWithSuccessTrade(String txnseqno,RealtmcolltnQueryResponseBean responseBean){
		
        TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
        PayPartyBean payPartyBean = null;
        if(StringUtil.isNotEmpty(responseBean.getSerialNum())){
            //更新支付方信息
            payPartyBean = new PayPartyBean(txnseqno,"01", responseBean.getSerialNum(), PAYINSTID, ConsUtil.getInstance().cons.getBosspay_user_id(), "", DateUtil.getCurrentDateTime(), "",responseBean.getBankAccount());
        }else{
            payPartyBean = new PayPartyBean(txnseqno,"01", responseBean.getSerialNum(),PAYINSTID, ConsUtil.getInstance().cons.getBosspay_user_id(), "", DateUtil.getCurrentDateTime(), "",responseBean.getBankAccount());
        }
        payPartyBean.setPanName(responseBean.getAccountName());
        txnsLogService.updatePayInfo_Fast(payPartyBean);
        
        
        //更新交易流水中心应答信息
      
        saveSuccessBossTrade(txnseqno,txnsLog.getAccordno(), txnsLog.getAccfirmerno(),responseBean.getSerialNum(),responseBean.getStatus());
        String commiteTime = DateUtil.getCurrentDateTime();
       
        
        // 处理同步通知和异步通知
        // 根据原始订单拼接应答报文，异步通知商户
        TxnsOrderinfoModel gatewayOrderBean = txnsOrderinfoDAO.getOrderByTxnseqno(txnseqno);//getOrderinfoByOrderNo(txnsLog.getAccordno(),txnsLog.getAccfirmerno());
        /**账务处理开始 **/
        // 应用方信息
        try {
            AppPartyBean appParty = new AppPartyBean("","000000000000", commiteTime,DateUtil.getCurrentDateTime(), txnseqno, "");
            txnsLogService.updateAppInfo(appParty);
            IAccounting accounting = AccountingAdapterFactory.getInstance().getAccounting(BusiTypeEnum.fromValue(txnsLog.getBusitype()));
            accounting.accountedFor(txnseqno);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**账务处理结束 **/
        
        /**异步通知处理开始 **/
        try {
			ResultBean orderResp = 
			        generateAsyncRespMessage(txnseqno);
			if (orderResp.isResultBool()) {
			    OrderAsynRespBean respBean = (OrderAsynRespBean) orderResp
			            .getResultObj();
			    new SynHttpRequestThread(
			            gatewayOrderBean.getFirmemberno(),
			            gatewayOrderBean.getRelatetradetxn(),
			            gatewayOrderBean.getBackurl(),
			            respBean.getNotifyParam()).start();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public ResultBean generateAsyncRespMessage(String txnseqno){
        ResultBean resultBean = null;
        try {
            TxnsOrderinfoModel orderinfo = txnsOrderinfoDAO.getOrderByTxnseqno(txnseqno);
            if(StringUtil.isEmpty(orderinfo.getBackurl())){
            	return new ResultBean("09", "no need async");
            }else {
				if(orderinfo.getBackurl().indexOf("http")<0){
					return new ResultBean("09", "no need async");
				}
			}
            
            
            TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(orderinfo.getRelatetradetxn());
             String version="v1.0";// 网关版本
             String encoding="1";// 编码方式
             String certId="";// 证书 ID
             String signature="";// 签名
             String signMethod="01";// 签名方法
             String merId=txnsLog.getAccfirmerno();// 商户代码
             String orderId=txnsLog.getAccordno();// 商户订单号
             String txnType=orderinfo.getTxntype();// 交易类型
             String txnSubType=orderinfo.getTxnsubtype();// 交易子类
             String bizType=orderinfo.getBiztype();// 产品类型
             String accessType="2";// 接入类型
             String txnTime=orderinfo.getOrdercommitime();// 订单发送时间
             String txnAmt=orderinfo.getOrderamt()+"";// 交易金额
             String currencyCode="156";// 交易币种
             String reqReserved=orderinfo.getReqreserved();// 请求方保留域
             String reserved="";// 保留域
             String queryId=txnsLog.getTradeseltxn();// 交易查询流水号
             String respCode=txnsLog.getRetcode();// 响应码
             String respMsg=txnsLog.getRetinfo();// 应答信息
             String settleAmt="";// 清算金额
             String settleCurrencyCode="";// 清算币种
             String settleDate=txnsLog.getAccsettledate();// 清算日期
             String traceNo=txnsLog.getTradeseltxn();// 系统跟踪号
             String traceTime=DateUtil.getCurrentDateTime();// 交易传输时间
             String exchangeDate="";// 兑换日期
             String exchangeRate="";// 汇率
             String accNo="";// 账号
             String payCardType="";// 支付卡类型
             String payType="";// 支付方式
             String payCardNo="";// 支付卡标识
             String payCardIssueName="";// 支付卡名称
             String bindId="";// 绑定标识号
            
             OrderAsynRespBean orderRespBean = new OrderAsynRespBean(version, encoding, certId, signature, signMethod, merId, orderId, txnType, txnSubType, bizType, accessType, txnTime, txnAmt, currencyCode, reqReserved, reserved, queryId, respCode, respMsg, settleAmt, settleCurrencyCode, settleDate, traceNo, traceTime, exchangeDate, exchangeRate, accNo, payCardType, payType, payCardNo, payCardIssueName, bindId);
             String privateKey= "";
             if("000204".equals(orderinfo.getBiztype())){
            	 privateKey = coopInstiService.getCoopInstiMK(orderinfo.getFirmemberno(), TerminalAccessType.WIRELESS).getZplatformPriKey();
             }else if("000201".equals(orderinfo.getBiztype())){
            	 if("0".equals(orderinfo.getAccesstype())){
                 	privateKey = merchMKService.get(orderinfo.getSecmemberno()).getLocalPriKey().trim();
                 }else if("2".equals(orderinfo.getAccesstype())){
                 	privateKey = coopInstiService.getCoopInstiMK(orderinfo.getFirmemberno(), TerminalAccessType.MERPORTAL).getZplatformPriKey();
                 }
            	 //privateKey = merchMKService.get(orderinfo.getFirmemberno()).getLocalPriKey();
             }
            
            resultBean = new ResultBean(generateAsyncOrderResult(orderRespBean, privateKey.trim()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            resultBean = new ResultBean("RC99", "系统异常");
        }
        return resultBean;
    }
    public OrderAsynRespBean generateAsyncOrderResult(OrderAsynRespBean orderAsyncRespBean,String privateKey) throws Exception{   
        String[] unParamstring = {"signature"};
        String dataMsg = ObjectDynamic.generateParamer(orderAsyncRespBean, false, unParamstring).trim();
        byte[] data =  URLEncoder.encode(dataMsg,"utf-8").getBytes();
        orderAsyncRespBean.setSignature(URLEncoder.encode(RSAUtils.sign(data, privateKey),"utf-8"));
        return orderAsyncRespBean;
    }
    
	
	public void saveSuccessBossTrade(String txnseqno,String gateWayOrderNo,String merchId,String serialNum,String status){
        TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
        //txnsLog.setAccordfintime(DateUtil.getCurrentDateTime());
        txnsLog.setPayordfintime(DateUtil.getCurrentDateTime());
        txnsLog.setRetdatetime(DateUtil.getCurrentDateTime());
        txnsLog.setTradestatflag("00000001");//交易完成结束位
        txnsLog.setTradetxnflag("10000000");//证联支付快捷（基金交易）
        txnsLog.setRelate("10000000");
        txnsLog.setTradeseltxn(UUIDUtil.uuid());
        txnsLog.setPayrettsnseqno(serialNum);
        List<String> retList = JSON.parseArray(status, String.class);
		String retCode = retList.get(0);
		String retInfo = retList.get(1);
        txnsLog.setPayretcode(retCode);
        txnsLog.setPayretinfo(retInfo);
        txnsLog.setRetcode("0000");
        txnsLog.setRetinfo("交易成功");
        txnsLogService.updateTxnsLog(txnsLog);
        TxnsOrderinfoModel orderinfo = txnsOrderinfoDAO.getOrderByTxnseqno(txnseqno);
        orderinfo.setStatus("00");
        orderinfo.setOrderfinshtime(DateUtil.getCurrentDateTime());
        txnsOrderinfoDAO.updateOrderinfo(orderinfo);
        
    }
	
	@Override
	public ResultBean queryTrade(TradeBean trade) {
		bossPayService.queryRealCollecting(trade.getOrderId());
		return null;
	}

	@Override
	public ResultBean bankSign(TradeBean trade) {
		// TODO Auto-generated method stub
		ResultBean resultBean = null;
		try {
			//渠道博士金电没有银行卡认证接口，暂时为直接绑卡
			
			//记录快捷交易流水
			txnsQuickpayService.saveBossBankCardSign(trade);
            if (!"01".equals(trade.getTradeType())) {
				// 保存绑卡信息
				quickpayCustService.updateCardStatus(trade.getMerUserId(),trade.getCardNo());
			}
			
			if ("01".equals(trade.getTradeType())) {
				resultBean = new ResultBean("success");
				return resultBean;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 民生银行的实名认证和白名单采集已经做完，这里只发送短信验证码发送短信验证码
		log.info("CMBC withholding bank sign start!");
		if (log.isDebugEnabled()) {
			log.debug(JSON.toJSONString(trade));
		}
		trade.setPayinstiId(PAYINSTID);
		sendSms(trade);
		resultBean = new ResultBean("success");
		log.info("Boss bank sign end!");
		return resultBean;
	}

	/**
	 *
	 * @param tradeType
	 */
	@Override
	public void setTradeType(TradeTypeEnum tradeType) {
		// TODO Auto-generated method stub
		this.tradeType = tradeType;
	}

	/**
	 *
	 * @param tradeBean
	 */
	@Override
	public void setTradeBean(TradeBean tradeBean) {
		// TODO Auto-generated method stub
		this.tradeBean = tradeBean;
	}
	
	public static void main(String[] args) {
		System.out.println(StatusEnum.fromValue("PR10").getMessage());
	}
}