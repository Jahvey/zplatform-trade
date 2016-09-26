/* 
 * MerchCashController.java  
 * 
 * version TODO
 *
 * 2016年3月31日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.acc.bean.BusiAcct;
import com.zlebank.zplatform.acc.bean.BusiAcctQuery;
import com.zlebank.zplatform.acc.bean.enums.Usage;
import com.zlebank.zplatform.acc.exception.AbstractBusiAcctException;
import com.zlebank.zplatform.acc.exception.AccBussinessException;
import com.zlebank.zplatform.acc.exception.IllegalEntryRequestException;
import com.zlebank.zplatform.acc.service.AccEntryService;
import com.zlebank.zplatform.acc.service.AccountQueryService;
import com.zlebank.zplatform.commons.dao.BankInfoDAO;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.member.bean.CoopInsti;
import com.zlebank.zplatform.member.bean.EnterpriseBean;
import com.zlebank.zplatform.member.bean.QuickpayCustBean;
import com.zlebank.zplatform.member.dao.CoopInstiDAO;
import com.zlebank.zplatform.member.dao.EnterpriseDAO;
import com.zlebank.zplatform.member.pojo.PojoCoopInsti;
import com.zlebank.zplatform.member.pojo.PojoEnterpriseDeta;
import com.zlebank.zplatform.member.pojo.PojoMember;
import com.zlebank.zplatform.member.pojo.PojoMerchDeta;
import com.zlebank.zplatform.member.service.CoopInstiService;
import com.zlebank.zplatform.member.service.EnterpriseService;
import com.zlebank.zplatform.member.service.MemberBankCardService;
import com.zlebank.zplatform.member.service.MemberService;
import com.zlebank.zplatform.member.service.MerchService;
import com.zlebank.zplatform.trade.analyzer.GateWayTradeAnalyzer;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.enums.ChannelEnmu;
import com.zlebank.zplatform.trade.bean.gateway.OrderBean;
import com.zlebank.zplatform.trade.bean.gateway.OrderRespBean;
import com.zlebank.zplatform.trade.dao.RspmsgDAO;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.model.PojoRspmsg;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsNotifyTaskModel;
import com.zlebank.zplatform.trade.model.TxnsOrderinfoModel;
import com.zlebank.zplatform.trade.model.TxnsWithholdingModel;
import com.zlebank.zplatform.trade.service.IGateWayService;
import com.zlebank.zplatform.trade.service.IProdCaseService;
import com.zlebank.zplatform.trade.service.IRouteConfigService;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.ITxnsNotifyTaskService;
import com.zlebank.zplatform.trade.service.ITxnsWithdrawService;
import com.zlebank.zplatform.trade.service.ITxnsWithholdingService;
import com.zlebank.zplatform.trade.service.IWebGateWayService;
import com.zlebank.zplatform.trade.utils.AmountUtil;
import com.zlebank.zplatform.trade.utils.DateUtil;
import com.zlebank.zplatform.trade.utils.ObjectDynamic;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年3月31日 下午3:06:08
 * @since
 */
@Controller
@RequestMapping("merch")
public class MerchCashController {
	private static final Log log = LogFactory.getLog(MerchCashController.class);
	@Autowired
	private IGateWayService gateWayService;
	@Autowired
	private RspmsgDAO rspmsgDAO;
	@Autowired
	private IProdCaseService prodCaseService;
	@Autowired
	private MerchService merchService;
	@Autowired
    private ITxnsLogService txnsLogService;
	@Autowired
    private CoopInstiService coopInstiService;
	@Autowired
    private MemberBankCardService memberBankCardService;
	@Autowired
	private EnterpriseService enterpriseService;
	@Autowired
    private IWebGateWayService webGateWayService;
	@Autowired
	private IRouteConfigService routeConfigService;
	@Autowired
    private AccEntryService accEntryService;
	@Autowired
    private ITxnsWithdrawService txnsWithdrawService;
	@Autowired
	private AccountQueryService accountQueryService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private BankInfoDAO bankInfoDAO;
	@Autowired
	private MemberBankCardService bankCardService;
	@Autowired
	private EnterpriseDAO enterpriseDAO;
	@Autowired
	private ITxnsWithholdingService txnsWithholdingService;
	@Autowired
	private ITxnsNotifyTaskService txnsNotifyTaskService;
	@Autowired
	private CoopInstiDAO coopInstiDAO;
	
	
	
	
	
	
	@RequestMapping("/coporder.htm")
	public ModelAndView pay(OrderBean order, HttpSession httpSession,
			HttpServletRequest request) {
		log.info("receive web message(json):" + JSON.toJSONString(order));
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			if (!validateOrder(order, model)) {
				return new ModelAndView("/erro_gw", model);
			}
			// 记录付款人的IP
			if (StringUtil.isEmpty(order.getCustomerIp())) {
				order.setCustomerIp(getIpAddr(request));
			}
			// 订单记录和业务逻辑处理,取得商户信息，记录交易数据（核心）和订单详细信息，分析交易所属业务
			String txnseqno = gateWayService.dealWithMerchOrder(order);
			return new ModelAndView("redirect:/merch/cash.htm?txnseqno="+ txnseqno);
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errMsg", "订单信息错误，请重新提交");
			model.put("errCode", "RC99");
		}
		return new ModelAndView("/erro_gw", model);
	}
	
	
	@RequestMapping(value = "/cash.htm", method = RequestMethod.GET)
    public ModelAndView cashIndex(@RequestParam("txnseqno") String txnseqno,HttpServletRequest request) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            if (StringUtil.isEmpty(txnseqno)) {
                model.put("errMsg", "交易失败，查无此交易");
                return new ModelAndView("/erro_gw", model);
            }
            //交易流水数据
            TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
            //合作机构
            CoopInsti coopInsti = coopInstiService.getInstiByInstiCode(txnsLog.getAccfirmerno());
            EnterpriseBean enterprise = enterpriseService.getEnterpriseByMemberId(txnsLog.getAccsecmerno());
            TxnsOrderinfoModel orderInfo = gateWayService.getOrderinfoByOrderNoAndMemberId(txnsLog.getAccordno(),txnsLog.getAccfirmerno());
            if ("00".equals(orderInfo.getStatus())) {// 支付成功的订单不可重复支付
                model.put("errMsg", "交易成功，请不要重复支付");
                model.put("errCode", "RC10");
                return new ModelAndView("/erro_gw", model);
            }
            model.put("merchName", coopInsti.getInstiName());//合作机构名称
            model.put("merchId", txnsLog.getAccfirmerno());
            model.put("subMerchId", txnsLog.getAccsecmerno());
            model.put("goodsName", orderInfo.getGoodsname());
            model.put("txnseqno", txnseqno);
            model.put("memberId", txnsLog.getAccmemberid());
            model.put("busicode", txnsLog.getBusicode());
            model.put("busitype", txnsLog.getBusitype());
            model.put("orderId", txnsLog.getAccordno());
            model.put("subMerName",enterprise.getEnterpriseName());//商户名称
            model.put("txnTime",DateUtil.formatDateTime(DateUtil.parse(DateUtil.DEFAULT_DATE_FROMAT,orderInfo.getOrdercommitime())));
            model.put("txnAmt", txnsLog.getAmount());
            model.put("orderDesc", orderInfo.getOrderdesc());
            model.put("tn", orderInfo.getTn());
            model.put("amount_y", AmountUtil.numberFormat(txnsLog.getAmount()));
            if (StringUtil.isNotEmpty(orderInfo.getSecmemberno())) {
            	
            	PojoMerchDeta merch = merchService.getMerchBymemberId(txnsLog.getAccsecmerno());
            	
                /*// 获取会员已绑定的银行卡
                PagedResult<QuickpayCustBean> bindCardList =  memberBankCardService.queryMemberBankCard(orderInfo.getSecmemberno(), "0",null, 0, 10);
                model.put("bindCardList", bindCardList.getPagedResult());
                String cardType = "1";
                // 显示会员已绑定的银行卡
                PagedResult<QuickpayCustBean> cardPageList = memberBankCardService.queryMemberBankCard(orderInfo.getSecmemberno(), cardType,null, 0, 10);
                List<QuickpayCustBean> cardList = cardPageList.getPagedResult();*/
            	List<QuickpayCustBean> cardList = new ArrayList<QuickpayCustBean>();
            	QuickpayCustBean quickpayCustBean = new QuickpayCustBean();
            	quickpayCustBean.setAccname(merch.getAccName());
            	quickpayCustBean.setCardno(merch.getAccNum());
            	cardList.add(quickpayCustBean);
                model.put("cardList", cardList);
                //获取请求方IP
                model.put("memberIP", orderInfo.getPayerip());
            }

            if ("3000".equals(txnsLog.getBusitype())) {// 提现交易 借记卡
                return new ModelAndView("/withdraw_merch", model);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            model.put("errMsg", "交易失败，系统忙，请稍后再试！");
            model.put("errCode", "RC10");
            return new ModelAndView("/erro_gw", model);
        }
        return new ModelAndView("/index_merch", model);
    }

	private boolean validateOrder(OrderBean order, Map<String, Object> model) {
		// 验证订单数据有效性，用hibernate validator处理
		ResultBean resultBean = GateWayTradeAnalyzer.validateOrder(order);
		if (!resultBean.isResultBool()) {
			// 订单信息长度和非空验证未通过
			model.put("errMsg", resultBean.getErrMsg());
			model.put("errCode", resultBean.getErrCode());
			return false;
		}
		// 检验风控信息是否符合要求
		ResultBean signResultBean = gateWayService.verifyOrder(order);
		if (!signResultBean.isResultBool()) {
			// 订单信息验签未通过
			model.put("errMsg", signResultBean.getErrMsg());
			model.put("errCode", signResultBean.getErrCode());
			return false;
		}

		// 验证订单号是否重复
		try {
			gateWayService.verifyRepeatWebOrder(order.getOrderId(),
					order.getTxnTime(), order.getTxnAmt(),
					order.getCoopInstiId(), order.getMerId());
		} catch (TradeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			PojoRspmsg msg = rspmsgDAO.get(e.getCode());
			model.put("errMsg", msg.getRspinfo());
			model.put("errCode", msg.getWebrspcode());
			return false;
		}

		// 验证商户产品版本中是否有对应的业务
		ResultBean busiResultBean = prodCaseService.verifyMerchBusiness(order);
		if (!busiResultBean.isResultBool()) {
			PojoRspmsg msg = rspmsgDAO.get(busiResultBean.getErrCode());
			model.put("errMsg", msg.getRspinfo());
			model.put("errCode", msg.getWebrspcode());
			return false;
		}

		// 检验一级商户和二级商户有效性
		if (StringUtil.isNotEmpty(order.getMerId())) {
			PojoMerchDeta subMember = merchService.getMerchBymemberId(order
					.getMerId());
			if (subMember == null) {
				PojoRspmsg msg = rspmsgDAO.get("GW05");
				model.put("errMsg", msg.getRspinfo());
				model.put("errCode", msg.getWebrspcode());
				return false;
			}
			PojoMember pojoMember = memberService.getMbmberByMemberId(order.getMerId(), null);
        	PojoCoopInsti pojoCoopInsti = coopInstiDAO.get(pojoMember.getInstiId());
            if (!order.getCoopInstiId().equals(pojoCoopInsti.getInstiCode())) {
            	PojoRspmsg msg = rspmsgDAO.get("GW07");
            	model.put("errMsg", msg.getRspinfo());
                model.put("errCode", msg.getWebrspcode());
                return false;
            }

		}

		/*try {
			gateWayService.checkBusiAcct(order.getMerId(),null);
		} catch (TradeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			PojoRspmsg msg = rspmsgDAO.get(e.getCode());
			model.put("errMsg", msg.getRspinfo());
			model.put("errCode", msg.getWebrspcode());
		}*/
		return true;
	}
	
	
	@RequestMapping("/toFastPay.htm")
    public ModelAndView toFastPay(TradeBean trade, @RequestParam("txnseqno_")String txnseqno_) {
        if (StringUtil.isEmpty(trade.getTxnseqno())) {
            return new ModelAndView("redirect:/merch/cash.htm?txnseqno="+ txnseqno_);
        }
        Map<String, Object> model = new HashMap<String, Object>();
        trade.setBindCardId("");
        model.put("trade", trade);
        return new ModelAndView("/fastpay/fastCardInfo_merch", model);
    }
	
 	@RequestMapping("/toPay.htm")
    public ModelAndView toPay(TradeBean trade, @RequestParam("txnseqno_")String txnseqno_) {
        if (StringUtil.isEmpty(trade.getTxnseqno())) {
            return new ModelAndView("redirect:/merch/cash.htm?txnseqno=" + txnseqno_);
        }
        Map<String, Object> model = new HashMap<String, Object>();
        
        try {
            webGateWayService.bankCardSign(trade);
        } catch (TradeException e1) {
            // TODO Auto-generated catch block
            model.put("errMsg", e1.getMessage());
            model.put("errCode", e1.getCode());
            model.put("txnseqno", trade.getTxnseqno());
            return new ModelAndView("/erro", model);
        }
        model.put("trade", trade);
        return new ModelAndView("/fastpay/pay_merch", model);
    }
 	@RequestMapping("/bindPay.htm")
	public ModelAndView toBindPay(TradeBean trade, @RequestParam("txnseqno_")String txnseqno_) {
	    if (StringUtil.isEmpty(trade.getTxnseqno())) {
	        return new ModelAndView("redirect:/merch/cash.htm?txnseqno="
	                + txnseqno_);
	    }
	    
	    Map<String, Object> model = new HashMap<String, Object>();
	    TradeBean tradeBean2 = trade.clone();
	    TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno_);
	    PojoMerchDeta merch = merchService.getMerchBymemberId(txnsLog.getAccsecmerno());
	    PojoEnterpriseDeta enterprise = enterpriseDAO.getEnterpriseByMemberId(txnsLog.getAccsecmerno());
	    PojoMember member = memberService.getMbmberByMemberId(txnsLog.getAccsecmerno(), null);
	    QuickpayCustBean bean = new QuickpayCustBean();
	    bean.setCustomerno(txnsLog.getAccfirmerno());
	    bean.setCardno(merch.getAccNum());
	    bean.setAccname(merch.getAccName());
	    bean.setIdnum(enterprise.getCorpNo());
	    bean.setIdtype("01");
	    bean.setPhone(member.getPhone());
	    bean.setRelatememberno(txnsLog.getAccsecmerno());
	    bean.setCardtype("1");
	    Map<String, Object> cardInfo = routeConfigService.getCardInfo(merch.getAccNum());
	    if(cardInfo==null){//商户收银台测试个人银行卡时，没有获取到卡bin信息，则跳转至宜昌页面
	    	model.put("errMsg", "银行卡信息错误");
	        model.put("txnseqno", txnseqno_);
	        return new ModelAndView("/erro", model);
	    }
	    bean.setBankcode(cardInfo.get("BANKCODE")+"");
	    bean.setBankname(cardInfo.get("BANKNAME")+"");
	    long bindId = bankCardService.saveQuickPayCust(bean);
	    trade.setBindCardId(bindId+"");
	    try {
	        webGateWayService.bindPay(trade);
	    } catch (TradeException e) {
	        // TODO Auto-generated catch block
	        model.put("errMsg", e.getMessage());
	        model.put("txnseqno", txnseqno_);
	        return new ModelAndView("/erro", model);
	    }
	    tradeBean2.setCardNo(trade.getCardNo());
	    tradeBean2.setReaPayOrderNo(trade.getReaPayOrderNo());
	    tradeBean2.setCardNo(trade.getCardNo());
	    tradeBean2.setMobile(trade.getMobile());
	    tradeBean2.setAcctName(trade.getAcctName());
	    tradeBean2.setCertId(trade.getCertId());
	    tradeBean2.setValidthru(trade.getValidthru());// web收银台使用
	    tradeBean2.setCvv2(trade.getCvv2());
	    tradeBean2.setBankCode(bean.getBankcode());
	    tradeBean2.setCardId(bindId);
	    model.put("trade", tradeBean2);
	    return new ModelAndView("/fastpay/pay_merch", model);
	}
 	
 	@RequestMapping("/toBankPay.htm")
    public ModelAndView toSubmitPay(TradeBean trade, @RequestParam("txnseqno_")String txnseqno_) {
        // t_txns_orderinfo的订单号

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("txnseqno", trade.getTxnseqno());
        try {
            String gateWayOrderId = trade.getOrderId();
            TxnsOrderinfoModel orderinfo = gateWayService
                    .getOrderinfoByOrderNoAndMemberId(gateWayOrderId,
                            trade.getMerchId());
            if (orderinfo == null) {
                model.put("errMsg", "无效订单信息");
                model.put("respCode", "RC99");
                model.put("trade", trade);
                model.put("txnseqno", txnseqno_);

                return new ModelAndView("/erro", model);
            }
            if ("00".equals(orderinfo.getStatus())) {
                model.put("errMsg", "订单已经支付成功，请不要重复支付");
                model.put("respCode", "RC99");
                model.put("trade", trade);
                model.put("txnseqno", txnseqno_);
                return new ModelAndView("/erro_merch", model);
            }

            //获取路由信息
            ResultBean routResultBean = routeConfigService.getWapTransRout(
                    DateUtil.getCurrentDateTime(), trade.getAmount(),
                    StringUtil.isNotEmpty(trade.getMerchId())?trade.getMerchId():trade.getSubMerchId(), trade.getBusicode(), trade.getCardNo());
            if (routResultBean.isResultBool()) {
                String routId = routResultBean.getResultObj().toString();
                ChannelEnmu channel = ChannelEnmu.fromValue(routId);
                trade.setPayinstiId(channel.getChnlcode());
                webGateWayService.submitPay(trade);
                model.put("trade", trade);
                switch (channel) {
                    case ZLPAY :
                        //zlpayChannel(trade, routId, model);
                        break;
                    case REAPAY :
                        return new ModelAndView("redirect:/gateway/waiting.htm?txnseqno="+ trade.getTxnseqno()+"&reapayOrderNo="+trade.getReaPayOrderNo()+"&orderNo="+trade.getOrderId());
                    case TEST:
                        TxnsOrderinfoModel gatewayOrderBean = gateWayService.getOrderinfoByOrderNoAndMemberId(gateWayOrderId,trade.getMerchId());
                        ResultBean orderResp = gateWayService.generateRespMessage(
                                gatewayOrderBean.getOrderno(), trade.getMerchId());
                        OrderRespBean respBean = (OrderRespBean) orderResp.getResultObj();
                        model.put("suburl",gatewayOrderBean.getFronturl()+ "?"+ ObjectDynamic.generateReturnParamer(respBean, false, null));
                        model.put("errMsg", "交易成功");
                        model.put("respCode", "0000");
                        return new ModelAndView("/fastpay/success", model);
                    case CMBCWITHHOLDING:
                        return new ModelAndView("redirect:/merch/payment.htm?txnseqno="+ trade.getTxnseqno()+"&reapayOrderNo="+trade.getReaPayOrderNo()+"&orderNo="+trade.getOrderId());
                    case CMBCSELFWITHHOLDING:
                        return new ModelAndView("redirect:/merch/payment.htm?txnseqno="+ trade.getTxnseqno()+"&reapayOrderNo="+trade.getReaPayOrderNo()+"&orderNo="+trade.getOrderId());
                    case BOSSPAYCOLLECTION:
                    	return new ModelAndView("redirect:/merch/pay/payment.htm?txnseqno="+ trade.getTxnseqno()+"&reapayOrderNo="+trade.getReaPayOrderNo()+"&orderNo="+trade.getOrderId());
                    	
                    default:
                    	break;
                    
                }
                model.put("trade", trade);
            }
        }catch (TradeException e) {
            e.printStackTrace();
            model.put("trade", trade);
            model.put("errMsg", e.getMessage());
            model.put("respCode", "RC99");
            model.put("txnseqno", txnseqno_);
            return new ModelAndView("/erro_merch", model);
        }catch (Exception e) {
            e.printStackTrace();
            model.put("trade", trade);
            model.put("errMsg", "订单信息错误，请重新提交");
            model.put("respCode", "RC99");
            model.put("txnseqno", txnseqno_);
            return new ModelAndView("/erro_merch", model);
        }
        model.put("txnseqno", trade.getTxnseqno());
        return new ModelAndView("/fastpay/result_merch", model);
    }
 	
 	@RequestMapping("/payment.htm")
    public ModelAndView payJumpCMBC(String orderNo, String txnseqno,String reapayOrderNo ){
        Map<String, Object> model = new HashMap<String, Object>();
        TradeBean tradeBean = new TradeBean();
        tradeBean.setTxnseqno(txnseqno);
        tradeBean.setOrderId(orderNo);
        tradeBean.setReaPayOrderNo(reapayOrderNo);
        model.put("trade", tradeBean);
        return new ModelAndView("/fastpay/pay_jump_cmbc_merch", model);
    }
 	
 	@RequestMapping("/pay/payment.htm")
    public ModelAndView payJumpBossPay(String orderNo, String txnseqno,String reapayOrderNo ){
        Map<String, Object> model = new HashMap<String, Object>();
        TradeBean tradeBean = new TradeBean();
        tradeBean.setTxnseqno(txnseqno);
        tradeBean.setOrderId(orderNo);
        tradeBean.setReaPayOrderNo(reapayOrderNo);
        model.put("trade", tradeBean);
        return new ModelAndView("/fastpay/pay_jump_bosspay", model);
    }
 	
 	@RequestMapping("/withdraw.htm")
    public ModelAndView withdraw(TradeBean tradeBean) {

        Map<String, Object> model = null;
		try {
			model = webGateWayService.Withdraw(tradeBean);
		} catch (AccBussinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ModelAndView("/erro_merch_withdraw", model);
		} catch (IllegalEntryRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ModelAndView("/erro_merch_withdraw", model);
		} catch (AbstractBusiAcctException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ModelAndView("/erro_merch_withdraw", model);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ModelAndView("/erro_merch_withdraw", model);
		} catch (TradeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ModelAndView("/erro_merch_withdraw", model);
		}
        return new ModelAndView(model.get("url")+"", model);
    }
 	
 	@RequestMapping("/showAccount")
    @ResponseBody
    public Object showMemberAccount(String memberId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<BusiAcct> busiAccList = accountQueryService
                .getBusiACCByMid(memberId);
        BusiAcct fundAcct = null;
        // 取得资金账户
        for (BusiAcct busiAcct : busiAccList) {
            if (Usage.BASICPAY == busiAcct.getUsage()) {
                fundAcct = busiAcct;
            }
        }
        BusiAcctQuery memberAcct = accountQueryService
                .getMemberQueryByID(fundAcct.getBusiAcctCode());
        
        PojoMember person = memberService.getMbmberByMemberId(memberId, null);
        
        if(StringUtil.isEmpty(person.getPayPwd())){
            resultMap.put("paypwd", "none");
        }else{
            resultMap.put("paypwd", "ok");
        }
        resultMap.put("money", memberAcct.getBalance().toYuan());
        // 会员资金账户余额
        
        log.info(JSON.toJSONString(resultMap));
        return resultMap;
    }
 	
 	@RequestMapping("/refund")
 	@ResponseBody
 	public Object refund(@RequestParam("merchNo")String merchNo,@RequestParam("orderNo")String orderNo,@RequestParam("txnAmt")String txnAmt){
 		try {
			//TxnsOrderinfoModel orderinfo = gateWayService.getOrderinfoByOrderNoAndMemberId(orderNo, merchNo);
			gateWayService.dealWithRefundOrder(merchNo,orderNo,txnAmt);
		} catch (TradeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "failed";
		}
 		
 		return "success";
 	}
 	
 	
 	///////
 	@RequestMapping("/success.htm")
    public ModelAndView toSuccessPage(String orderNo, String txnseqno)
            throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        TxnsLogModel txnsLog = txnsLogService.getTxnsLogByTxnseqno(txnseqno);
        TxnsOrderinfoModel gatewayOrderBean = gateWayService
                .getOrderinfoByOrderNoAndMemberId(txnsLog.getAccordno(),txnsLog.getAccfirmerno());
        ResultBean orderResp = gateWayService.generateRespMessage(orderNo,txnsLog.getAccfirmerno());
        OrderRespBean respBean = (OrderRespBean) orderResp.getResultObj();
        model.put("suburl", gatewayOrderBean.getFronturl() + "?"+ ObjectDynamic.generateReturnParamer(respBean, false, null));
        model.put("errMsg", "交易成功");
        model.put("respCode", "0000");
        model.put("txnseqno", txnseqno);
        // 记录同步发送的日志
        TxnsNotifyTaskModel task = new TxnsNotifyTaskModel(
                gatewayOrderBean.getFirmemberno(),
                gatewayOrderBean.getRelatetradetxn(), 1, 1,
                ObjectDynamic.generateReturnParamer(respBean, false, null),
                "00", "200", gatewayOrderBean.getFronturl(), "2");
        txnsNotifyTaskService.saveTask(task);
        return new ModelAndView("/fastpay/success", model);
    }
    @RequestMapping("/fail.htm")
    public ModelAndView toFailPage(String orderNo, String txnseqno) {
        Map<String, Object> model = new HashMap<String, Object>();
        // TxnsOrderinfoModel gatewayOrderBean =
        // gateWayService.getOrderinfoByOrderNo(orderNo);
        gateWayService.updateOrderToFail(txnseqno);
        TxnsLogModel txnsLog = txnsLogService.get(txnseqno);
        model.put("errMsg", txnsLog.getRetinfo());
        model.put("respCode", txnsLog.getRetcode());
        model.put("txnseqno", txnseqno);;
        return new ModelAndView("/erro_merch", model);
    }

    @RequestMapping("/processing.htm")
    public ModelAndView toProcessingPage(String orderNo, String txnseqno) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("errMsg", "交易正在处理中，请稍后查询");
        model.put("respCode", "ZL34");
        model.put("txnseqno", txnseqno);
        return new ModelAndView("/fastpay/result_merch", model);
    }

    @RequestMapping("/wait.htm")
    public ModelAndView toWaitPage(String orderNo, String txnseqno,String reapayOrderNo) {
        Map<String, Object> model = new HashMap<String, Object>();
        TxnsLogModel txnsLog = null;
        int[] timeArray = new int[]{1000, 2000, 8000, 16000, 32000};
        try {
            for (int i = 0; i < 5; i++) {
                txnsLog=txnsLogService.getTxnsLogByTxnseqno(txnseqno);
                if(StringUtil.isNotEmpty(txnsLog.getPayretcode())){
                    ChannelEnmu channel = ChannelEnmu.fromValue(txnsLog.getPayinst());
                    switch (channel) {
                        case REAPAY :
                            if ("0000".equals(txnsLog.getPayretcode())
                                    || "3006".equals(txnsLog.getPayretcode())
                                    || "3053".equals(txnsLog.getPayretcode())
                                    || "3054".equals(txnsLog.getPayretcode())
                                    || "3056".equals(txnsLog.getPayretcode())
                                    || "3083".equals(txnsLog.getPayretcode())
                                    || "3081".equals(txnsLog.getPayretcode())) {
                                //return new ModelAndView("redirect:/gateway/cash.htm?txnseqno=" + txnseqno);
                                TradeBean tradeBean = new TradeBean();
                                tradeBean.setTxnseqno(txnseqno);
                                tradeBean.setOrderId(orderNo);
                                tradeBean.setReaPayOrderNo(reapayOrderNo);
                                model.put("trade", tradeBean);
                                return new ModelAndView("/fastpay/pay_jump", model);
                            }else{
                                model.put("errMsg", txnsLog.getPayretinfo());
                                model.put("respCode", txnsLog.getPayretcode());
                                TradeBean tradeBean = new TradeBean();
                                tradeBean.setTxnseqno(txnseqno);
                                model.put("trade", tradeBean);
                                model.put("txnseqno", txnseqno);
                                return new ModelAndView("/erro_merch", model);
                            }

                        case CMBCWITHHOLDING :
                            String serialno = txnsLog.getPayordno();
                            if(!txnsLog.getPayretcode().equals("000000")){
                                model.put("errMsg", txnsLog.getRetinfo());
                                model.put("respCode", txnsLog.getRetcode());
                                TradeBean tradeBean = new TradeBean();
                                tradeBean.setTxnseqno(txnseqno);
                                model.put("trade", tradeBean);
                                model.put("txnseqno", txnseqno);
                                return new ModelAndView("redirect:/merch/fail.htm?txnseqno="+ txnseqno+"&orderNo="+txnsLog.getAccordno());
                            }
                            TxnsWithholdingModel withholding = txnsWithholdingService.getWithholdingBySerialNo(serialno);
                            if(!withholding.getExectype().equalsIgnoreCase("R")){
                                model.put("errMsg", txnsLog.getPayretinfo());
                                model.put("respCode", txnsLog.getPayretcode());
                                TradeBean tradeBean = new TradeBean();
                                tradeBean.setTxnseqno(txnseqno);
                                model.put("trade", tradeBean);
                                model.put("txnseqno", txnseqno);
                                if("S".equalsIgnoreCase(withholding.getExectype())){
                                    return new ModelAndView("redirect:/merch/success.htm?txnseqno="+ txnseqno+"&orderNo="+txnsLog.getAccordno());
                                }else{
                                    return new ModelAndView("redirect:/merch/fail.htm?txnseqno="+ txnseqno+"&orderNo="+txnsLog.getAccordno());
                                }
                            }
                            break;
                        case CMBCSELFWITHHOLDING :
                            String tranId = txnsLog.getPayordno();
                            if(!txnsLog.getPayretcode().equals("000000")){
                                model.put("errMsg", txnsLog.getRetinfo());
                                model.put("respCode", txnsLog.getRetcode());
                                TradeBean tradeBean = new TradeBean();
                                tradeBean.setTxnseqno(txnseqno);
                                model.put("trade", tradeBean);
                                model.put("txnseqno", txnseqno);
                                return new ModelAndView("redirect:/merch/fail.htm?txnseqno="+ txnseqno+"&orderNo="+txnsLog.getAccordno());
                            }
                            TxnsWithholdingModel withholdingSelf = txnsWithholdingService.getWithholdingBySerialNo(tranId);
                            if(!withholdingSelf.getExectype().equalsIgnoreCase("R")){
                                model.put("errMsg", txnsLog.getPayretinfo());
                                model.put("respCode", txnsLog.getPayretcode());
                                TradeBean tradeBean = new TradeBean();
                                tradeBean.setTxnseqno(txnseqno);
                                model.put("trade", tradeBean);
                                model.put("txnseqno", txnseqno);
                                if("S".equalsIgnoreCase(withholdingSelf.getExectype())){
                                    return new ModelAndView("redirect:/merch/success.htm?txnseqno="+ txnseqno+"&orderNo="+txnsLog.getAccordno());
                                }else{
                                    return new ModelAndView("redirect:/merch/fail.htm?txnseqno="+ txnseqno+"&orderNo="+txnsLog.getAccordno());
                                }
                            }
                            break;
					default:
						break;
                    }
                    
                   
                }
                Thread.sleep(timeArray[i]);
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        model.put("errMsg", txnsLog.getPayretinfo());
        model.put("respCode", txnsLog.getPayretcode());
        TradeBean tradeBean = new TradeBean();
        tradeBean.setTxnseqno(txnseqno);
        model.put("trade", tradeBean);
        model.put("txnseqno", txnseqno);
        return new ModelAndView("/fastpay/result", model);
    }
    @RequestMapping("/error.htm")
    public ModelAndView toExceptionPage(String orderNo, String txnseqno) {
        Map<String, Object> model = new HashMap<String, Object>();
        gateWayService.updateOrderToFail(txnseqno);
        model.put("errMsg", "系统异常,请联系证联金融客服");
        model.put("respCode", "ZL34");
        model.put("txnseqno", txnseqno);
        return new ModelAndView("/fastpay/result_merch", model);
    }
 	
 	
 	
 	
 	

	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
