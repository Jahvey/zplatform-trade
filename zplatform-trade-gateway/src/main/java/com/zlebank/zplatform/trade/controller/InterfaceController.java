package com.zlebank.zplatform.trade.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.acc.bean.enums.Usage;
import com.zlebank.zplatform.acc.dao.AccountDAO;
import com.zlebank.zplatform.acc.exception.AbstractBusiAcctException;
import com.zlebank.zplatform.acc.exception.AccBussinessException;
import com.zlebank.zplatform.acc.pojo.PojoAccount;
import com.zlebank.zplatform.acc.service.BusiAcctService;
import com.zlebank.zplatform.commons.bean.CardBin;
import com.zlebank.zplatform.commons.dao.CardBinDao;
import com.zlebank.zplatform.commons.utils.Base64Utils;
import com.zlebank.zplatform.commons.utils.BeanCopyUtil;
import com.zlebank.zplatform.commons.utils.Md5;
import com.zlebank.zplatform.commons.utils.RSAUtils;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.member.bean.MerchMK;
import com.zlebank.zplatform.member.dao.PersonDAO;
import com.zlebank.zplatform.member.pojo.PojoPersonDeta;
import com.zlebank.zplatform.member.service.MerchMKService;
/*import com.zlebank.zplatform.specification.utils.Base64Utils;
import com.zlebank.zplatform.specification.utils.RSAUtils;*/
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.gateway.OrderRespBean;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.message.BalancePayment_Request;
import com.zlebank.zplatform.trade.message.BalancePayment_Response;
import com.zlebank.zplatform.trade.message.Bank;
import com.zlebank.zplatform.trade.message.BankCardBin;
import com.zlebank.zplatform.trade.message.BaseMessage;
import com.zlebank.zplatform.trade.message.CreateOrder_Request;
import com.zlebank.zplatform.trade.message.CreateOrder_Response_Sync;
import com.zlebank.zplatform.trade.message.InitMemberPayPassword_Request;
import com.zlebank.zplatform.trade.message.InitMemberPayPassword_Response;
import com.zlebank.zplatform.trade.message.MemberLogin_Request;
import com.zlebank.zplatform.trade.message.MemberLogin_Response;
import com.zlebank.zplatform.trade.message.QueryBankCardBin_Request;
import com.zlebank.zplatform.trade.message.QueryBankCardBin_Response;
import com.zlebank.zplatform.trade.message.QueryMemberBalance_Request;
import com.zlebank.zplatform.trade.message.QueryMemberBalance_Response;
import com.zlebank.zplatform.trade.message.QueryOrderInfo_Request;
import com.zlebank.zplatform.trade.message.QueryOrderInfo_Response;
import com.zlebank.zplatform.trade.message.QuerySignInfo_Request;
import com.zlebank.zplatform.trade.message.QuerySignInfo_Response;
import com.zlebank.zplatform.trade.message.QueryTradeStatus_Request;
import com.zlebank.zplatform.trade.message.QueryTradeStatus_Response;
import com.zlebank.zplatform.trade.message.QueryUsableBankCard_Request;
import com.zlebank.zplatform.trade.message.QueryUsableBankCard_Response;
import com.zlebank.zplatform.trade.message.Refund_Request;
import com.zlebank.zplatform.trade.message.Refund_Response;
import com.zlebank.zplatform.trade.message.SendMessageCaptcha_Request;
import com.zlebank.zplatform.trade.message.SendMessageCaptcha_Response;
import com.zlebank.zplatform.trade.message.SignCreditCard_Request;
import com.zlebank.zplatform.trade.message.SignCreditCard_Response;
import com.zlebank.zplatform.trade.message.SignDebitCard_Request;
import com.zlebank.zplatform.trade.message.SignDebitCard_Response;
import com.zlebank.zplatform.trade.message.SignPay_Request;
import com.zlebank.zplatform.trade.message.SignPay_Response;
import com.zlebank.zplatform.trade.message.Withdraw_Request;
import com.zlebank.zplatform.trade.message.Withdraw_Response;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsOrderinfoModel;
import com.zlebank.zplatform.trade.service.IGateWayService;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.HibernateValidatorUtil;
import com.zlebank.zplatform.trade.utils.ObjectDynamic;

/**
 * 
 * 手机支付接口控制层
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年10月8日 上午10:09:59
 * @since
 */
@Controller
@RequestMapping("interface")
public class InterfaceController {

    private static final String ERROR_CODE="09";
    private static final String SUCCESS_CODE="00";
    private static final String SUCCESS_MESSAGE="成功！";
    
    @Autowired
    private MerchMKService merchMKService;
    
    @Autowired
    private IGateWayService gateWayService;

    @Autowired
    private ITxnsLogService txnsLogService;
    
    @Autowired
    private BusiAcctService busiAcctService;
    
    @Autowired
    private CardBinDao cardBinDao;
    
    @Autowired
    private AccountDAO accountDAO;
    
    @Autowired
    private PersonDAO personDAO;

    private static final Log log = LogFactory.getLog(InterfaceController.class);

    // Mock 地址
    @RequestMapping("/mock.htm")
    public ModelAndView mock(String data, HttpSession httpSession, HttpServletResponse response){
        return new ModelAndView("mock", null);
    }
    // Mock 得到随机订单号
    @RequestMapping("/mockOrder.htm")
    public ModelAndView mockOrder(HttpSession httpSession, HttpServletResponse response) throws Exception{
        PrintWriter responseStream = getPrintWriter(response);
        long orderId = System.currentTimeMillis();
        String beforeSign = "{\"accessType\":\"0\",\"backUrl\":\"http://127.0.0.1:8080/webclient/\",\"bizType\":\"000202\",\"channelType\":\"08\",\"currencyCode\":\"156\",\"customerInfo\":\"\",\"encoding\":\"1\",\"frontUrl\":\"http://127.0.0.1:8080/webclient/\",\"merId\":\"200000000000443\",\"orderDesc\":\"zheshiWEBfaqidedingdan\",\"orderId\":\""+orderId+"\",\"payTimeout\":\"20151009165623\",\"reserved\":\"\",\"riskRateInfo\":\"shippingFlag=000&shippingCountryCode=0&shippingProvinceCode=0&shippingCityCode=0&shippingDistrictCode=0&shippingStreet=0&commodityCategory=0&commodityName=iphone&commodityUrl=0&commodityUnitPrice=0&commodityQty=0&shippingMobile=0&addressModifyTim=0&userRegisterTime=0&orderNameModifyTime=0&userId=0&orderName=0&userFlag=0&mobileModifyTime=0&riskLevel=0&merUserId=100000000000435&merUserRegDt=0&merUserEmail=0\",\"signMethod\":\"01\",\"signature\":\"\",\"txnAmt\":\"150\",\"txnSubType\":\"00\",\"txnTime\":\"20151009165623\",\"txnType\":\"01\",\"version\":\"v1.0\",\"cardNo\":\"1214850217632212\",\"memberId\":\"100000000000416\",\"passWd\":\"luxiaoshuai\",\"tn\":\"10086\",\"virtualId\":\"999999999999999\",\"subMerId\":\"200000000000225\",\"subMerName\":\"999999999999999\",\"subMerAbbr\":\"999999999999999\"}";
        String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAM8h4GBlku6+b7w13caG4obeaSNWWiuMMAI0l9y8OVxhYRC8JCF9ybvY8+Scn7pxB6YSMe+V1AZSsYAgOt/uU1xpAekaUesA+4efnVKBi2JNwEPtupW3jnm/ss71F1jvXgLiceCAz9ewKL3OONj5E0tdSmnq7w/vxK8jvftnb7MDAgMBAAECgYA39RXx1N8KAoabGQ5N+NivyNwsgMKhayD5J44zQuJYM7KOW9WC+sGKLwVMqId6TdjcI6Q77oU2JBbx30MohfEc1CLCxxK0FTnvF9mGg3vUsaCKyTxA3FN/tKiVpjJnqx63aMcp4pLgUfPW9qarXWW/L0boVfUMmZqrorV7ZtFLAQJBAPpXD1BVGih8OU5fGrcFmuePXXcbNF1GGWjT5cviblGua/pjGI6zUcH5b+F4oU80pmAMH8BTUi02D+Ikqvb5FoMCQQDT0Lwt0kKY4GjEL0a95QxGsgs1F8uIWDG4ewWxLh9iaX+E0OaLkf2944ZlM4PKTNCrjCwqYsr7D0p9sIi5gEmBAkEAwsbgHGHKk3tr3wVyNLrAtrA+SYC6MmRrOru7zPGlYhoy20MlBUqSLzlTinugDo9pb8ufHLX3d0PcAdkDyFsv3wJBALy9KYD28yrAA/qUWcfJuhegfIYRruXOGHfW/ypy+7o4YU1ay6OgMearP80bixqPOf0ySQR3xYDSjkDTNfF9U4ECQQCXi6uHdCfOJ0IsYepm/qaO0AGFpk482aojrRzMGtGbd9DAjwFBZQsXpajUnsw4vVAYkICCxBb5IsmjOQVM3p7y";
        String data = beforeSign;
        String signedData = RSAUtils.sign(data.getBytes(), privateKey);
        JSONObject jsonData = JSONObject.fromObject(beforeSign);
        jsonData.put("signature", signedData);
        try {
            responseStream.write(jsonData.toString());
        } finally{
            responseStream.flush();responseStream.close();
        }
        return null;
    }
    // 地址路由
    @RequestMapping("/index.htm")
    public ModelAndView index(String data, HttpSession httpSession, HttpServletResponse response){
        PrintWriter responseStream = getPrintWriter(response);
        if (log.isDebugEnabled()) {
            log.debug("原报文数据："+data);
        }
        CreateOrder_Response_Sync responseBean = new CreateOrder_Response_Sync();
        
        if (StringUtil.isEmpty(data)) {
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg("收到数据为空");
            responseData(responseStream, responseBean);
            return null;
        }
        // 接收元数据
        JSONObject jsonObject = null;
        try {
            jsonObject = JSONObject.fromObject( data);
        } catch (Exception e) {
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg("无效的Json数据");
            responseData(responseStream, responseBean);
            return null;
//            e.printStackTrace();
        }
        
        // 请求报文
        BaseMessage requestBean = null;
        try {
            requestBean = (BaseMessage) JSONObject.toBean( jsonObject , BaseMessage.class);
        } catch (Exception e) {
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg("Json数据不能被正确的转换成报文，请检查Json数据和文档是否一致。");
            responseData(responseStream, responseBean);
            return null;
        }
        if (requestBean == null) {
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg("收到数据为空");
            requestCheck(response, data, responseBean, requestBean);
            responseData(responseStream, responseBean);
            return null;
        }
        if("00".equals(requestBean.getTxnType())) {
            // 交易状态查询交易
            queryTradeStatus(data, httpSession, response);
        } else if("01".equals(requestBean.getTxnType())||"02".equals(requestBean.getTxnType())) {
            // 订单生成
            createOrder(data, httpSession, response);
        } else if("03".equals(requestBean.getTxnType())) {
            // 订单信息查询
            queryOrderInfo(data, httpSession, response);
        } else if("04".equals(requestBean.getTxnType())) {
            // 签约信息查询
            querySignInfo(data, httpSession, response);
        } else if("05".equals(requestBean.getTxnType())) {
            // 可用银行信息查询
            queryUsableBankCard(data, httpSession, response);
        } else if ("06".equals(requestBean.getTxnType())) {
            // 卡BIN查询
            queryCardBin(data, httpSession, response);
        } else if ("07".equals(requestBean.getTxnType())) {
            // 储蓄卡签约
            debitCardSign(data, httpSession, response);
        } else if ("08".equals(requestBean.getTxnType())) {
            // 信用卡签约
            creditCardSign(data, httpSession, response);
        }  else if ("09".equals(requestBean.getTxnType())) {
            // 发送短信验证码
            sendMessageCaptcha(data, httpSession, response);
        } else if ("10".equals(requestBean.getTxnType())) {
            // 签约支付
            signedPay(data, httpSession, response);
        } else if ("11".equals(requestBean.getTxnType())) {
            // 会员登陆
            memberLogin(data, httpSession, response);
        } else if ("12".equals(requestBean.getTxnType())) {
            // 余额支付
            balancePay(data, httpSession, response);
        } else if ("13".equals(requestBean.getTxnType())) {
            // 余额查询
            queryMemberBalance(data, httpSession, response);
        } else if ("14".equals(requestBean.getTxnType())) {
            // 单笔退款
            refund(data, httpSession, response);
        } else if ("15".equals(requestBean.getTxnType())) {
            // 单笔提现
            withdraw(data, httpSession, response);
        } else if ("16".equals(requestBean.getTxnType())) {
            // 支付密码初始设置
            initPayPwd(data, httpSession, response);
        } else {
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg("不是合法的交易类型");
            responseData(responseStream, responseBean);
            return null;
        }

        return null;
    }
    

    // 订单生成【商家版】【trade】
    @RequestMapping("/createOrder.htm")
    public ModelAndView createOrder(String data, HttpSession httpSession, HttpServletResponse response){
        PrintWriter responseStream = getPrintWriter(response);
        if (log.isDebugEnabled()) {
            log.debug("原报文数据："+data);
        }

        // 接收元数据
        JSONObject jsonObject = JSONObject.fromObject( data);

        // 请求报文
        CreateOrder_Request requestBean = (CreateOrder_Request) JSONObject.toBean( jsonObject , CreateOrder_Request.class);

        // 响应报文【同步】
        CreateOrder_Response_Sync responseBean = BeanCopyUtil.copyBean(CreateOrder_Response_Sync.class, requestBean);

        // 商户版还是研发版
        if (StringUtil.isEmpty(requestBean.getVirtualId()) && StringUtil.isEmpty(requestBean.getMerId())) {
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg("虚拟代码 和一级商户代码不可同时为空！");
            responseData(responseStream, responseBean);
            return null;
        }
        // 商户版-->研发版(虚拟代码设值)
        if (StringUtil.isEmpty(requestBean.getVirtualId())) {
            requestBean.setVirtualId(requestBean.getMerId());
        }
        
        // 共通检查
       String errorMsg = requestCheck(response, data, responseBean, requestBean);
        if (StringUtil.isNotEmpty(errorMsg)) {
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg(errorMsg);
            responseData(responseStream, responseBean);
            return null;
        }
        // 调用公共接口【生成订单号】
        String tn = null;
        try {
            tn = gateWayService.createWapOrder(data);
        } catch (Exception e) { 
            responseBean.setRespCode(ERROR_CODE);   
            responseBean.setRespMsg("生成订单号失败，错误信息："+e.getMessage());
            responseData(responseStream, responseBean);
            return null;
        }

        if (StringUtil.isEmpty(tn)) {
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg("生成订单号失败：");
            responseData(responseStream, responseBean);
            return null;
        }
        
        // 返回正常结果
        responseBean.setRespCode(SUCCESS_CODE);
        responseBean.setRespMsg(SUCCESS_MESSAGE);
        responseBean.setTn(tn);
        responseData(responseStream, responseBean);

        return null;
    }
    // 订单信息查询
    @RequestMapping("/queryOrderInfo.htm")
    public ModelAndView queryOrderInfo(String data, HttpSession httpSession, HttpServletResponse response){
        PrintWriter responseStream = getPrintWriter(response);
        // 接收元数据
        JSONObject jsonObject = JSONObject.fromObject( data);

        // 将请求数据转化为bean
        QueryOrderInfo_Request requestBean = (QueryOrderInfo_Request) JSONObject.toBean( jsonObject , QueryOrderInfo_Request.class);

        // 返回bean数据准备
        QueryOrderInfo_Response responseBean  = BeanCopyUtil.copyBean(QueryOrderInfo_Response.class, requestBean);

        // 共通检查
        String errorMsg = requestCheck(response, data, responseBean, requestBean);
        if (StringUtil.isNotEmpty(errorMsg)) {
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg(errorMsg);
            responseData(responseStream, responseBean);
            return null;
        }

        // 业务处理
        TxnsOrderinfoModel orderInfo = gateWayService.getUniqueByHQL("from TxnsOrderinfoModel where tn = ?", new Object[]{requestBean.getTn()});
        if (orderInfo == null) {
            // 返回正常结果
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg("订单不存在！");
            responseData(responseStream, responseBean);
            return null;
        }
        
        TxnsLogModel txnsLog = txnsLogService.getUniqueByHQL("from TxnsLogModel txn where accordno = ?",  new Object[]{orderInfo.getOrderno()});

        // 商户代码
        responseBean.setMerId(orderInfo.getFirmemberno());
        // 二级商户代码
        responseBean.setSubMerId(orderInfo.getSecmemberno());
        // 二级商户全称
        responseBean.setSubMerName(orderInfo.getSecmembername());
        // 二级商户简称
        responseBean.setSubMerAbbr(orderInfo.getSecmembershortname());
        // 商户订单号
        responseBean.setOrderId(orderInfo.getOrderno());
        // 订单发送时间
        responseBean.setTxnTime(orderInfo.getOrdercommitime());
        // 交易金额
        responseBean.setTxnAmt(String.valueOf(orderInfo.getOrderamt()));
        // 交易币种
        responseBean.setCurrencyCode("156");
        // 订单描述
        responseBean.setOrderDesc(orderInfo.getOrderdesc());
        // 订单状态
        responseBean.setOrderStatus(orderInfo.getStatus());
        
        // 支付会员号(如果是匿名支付返回空串)
        if (log.isDebugEnabled()) {
            log.debug("订单对应的MemberId"+txnsLog.getAccmemberid());
        }
        if ("999999999999999".equals(txnsLog.getAccmemberid())) {
            responseBean.setPayMemberId("");
            responseBean.setIsSetPayPwd("");
        } else {
            responseBean.setPayMemberId(txnsLog.getAccmemberid());
            // 默认为已经设置
            responseBean.setIsSetPayPwd("0");
            try {
                PojoPersonDeta person = personDAO.getPersonByMemberId(txnsLog.getAccmemberid());
                if (person != null && StringUtil.isEmpty(person.getPayPwd())) {
                    responseBean.setIsSetPayPwd("1");
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e);
            }
        }
        

        // 返回正常结果
        responseBean.setRespCode(SUCCESS_CODE);
        responseBean.setRespMsg(SUCCESS_MESSAGE);
        responseData(responseStream, responseBean);

        return null;
    }
    // 用户签约信息查询
    @RequestMapping("/querySignInfo.htm")
    public ModelAndView querySignInfo(String data, HttpSession httpSession, HttpServletResponse response){
        PrintWriter responseStream = getPrintWriter(response);
        // 接收元数据
        JSONObject jsonObject = JSONObject.fromObject( data);

        // 将请求数据转化为bean
        QuerySignInfo_Request requestBean = (QuerySignInfo_Request) JSONObject.toBean( jsonObject , QuerySignInfo_Request.class);

        // 返回bean数据准备
        QuerySignInfo_Response responseBean  = BeanCopyUtil.copyBean(QuerySignInfo_Response.class, requestBean);

        // 共通检查
        String errorMsg = requestCheck(response, data, responseBean, requestBean);
        if (StringUtil.isNotEmpty(errorMsg)) {
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg(errorMsg);
            responseData(responseStream, responseBean);
            return null;
        }

        // 业务处理
        String jsonResult=null;
        try {
            jsonResult = gateWayService.querySignInfo(data);
        } catch (TradeException e) {
            log.error("用户签约信息查询时发生错误，请求数据："+data);
            log.error("错误信息："+e.getMessage());
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg("用户签约信息查询时发生错误");
            responseData(responseStream, responseBean);
            return null;
        }
        if (log.isDebugEnabled()) {
            log.debug("明文签约信息 : "+jsonResult);
        }
        // 返回正常结果
        responseBean.setRespCode(SUCCESS_CODE);
        responseBean.setRespMsg(SUCCESS_MESSAGE);
        
        // 商户公钥加密
        MerchMK merchMk = merchMKService.get(requestBean.getVirtualId());
        String key=merchMk.getMemberPubKey();
        byte[] decodedData = null;
        try {
            decodedData = RSAUtils.encryptByPublicKey(jsonResult.getBytes(), key);
            responseBean.setReserved(Base64Utils.encode(decodedData));
        } catch (Exception e) {
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg("用户签约信息查询时发生错误(加密时发生错误)");
            responseData(responseStream, responseBean);
            return null;
        }

        responseData(responseStream, responseBean);
        return null;
    }
    // 可受理银行卡信息
    @RequestMapping("/queryUsableBankCard.htm")
    public ModelAndView queryUsableBankCard(String data, HttpSession httpSession, HttpServletResponse response){
        PrintWriter responseStream = getPrintWriter(response);
        // 接收元数据
        JSONObject jsonObject = JSONObject.fromObject( data);

        // 将请求数据转化为bean
        QueryUsableBankCard_Request requestBean = (QueryUsableBankCard_Request) JSONObject.toBean( jsonObject , QueryUsableBankCard_Request.class);

        // 返回bean数据准备
        QueryUsableBankCard_Response responseBean  = BeanCopyUtil.copyBean(QueryUsableBankCard_Response.class, requestBean);

        // 共通检查
        String errorMsg = requestCheck(response, data, responseBean, requestBean);
        if (StringUtil.isNotEmpty(errorMsg)) {
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg(errorMsg);
            responseData(responseStream, responseBean);
            return null;
        }
        
        //可受理银行卡
        String json=gateWayService.queryUsableBankCard(data);
        
        // 业务处理(可受理银行卡信息列表)
        List<Bank> banks= JSON.parseArray(json, Bank.class);

        responseBean.setReserved(banks);

        // 返回正常结果
        responseBean.setRespCode(SUCCESS_CODE);
        responseBean.setRespMsg(SUCCESS_MESSAGE);
        responseData(responseStream, responseBean);
        
        return null;
    }
    // 银行卡卡BIN查询
    @RequestMapping("/queryCardBin.htm")
    public ModelAndView queryCardBin(String data, HttpSession httpSession, HttpServletResponse response){
        PrintWriter responseStream = getPrintWriter(response);
        /**注意：bank code 取的是前四位**/
        // 接收元数据
        JSONObject jsonObject = JSONObject.fromObject( data);

        // 将请求数据转化为bean
        QueryBankCardBin_Request requestBean = (QueryBankCardBin_Request) JSONObject.toBean( jsonObject , QueryBankCardBin_Request.class);

        // 返回bean数据准备
        QueryBankCardBin_Response responseBean  = BeanCopyUtil.copyBean(QueryBankCardBin_Response.class, requestBean);

        // 共通检查
        String errorMsg = requestCheck(response, data, responseBean, requestBean);
        if (StringUtil.isNotEmpty(errorMsg)) {
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg(errorMsg);
            responseData(responseStream, responseBean);
            return null;
        }
        // 业务处理
        String cardNo = requestBean.getCardNo();
        CardBin cardBin = cardBinDao.getCard(cardNo);
        if (cardBin!=null) {
            BankCardBin responseCardBin = new BankCardBin();
            responseCardBin.setBankCode(cardBin.getBankCode());
            responseCardBin.setBankName(cardBin.getBankName());
            responseCardBin.setCardType(cardBin.getType());
            responseBean.setReserved(responseCardBin);
        } else {
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg("没找到合适的卡BIN信息");
            responseData(responseStream, responseBean);
            return null;
        }

        // 返回正常结果
        responseBean.setRespCode(SUCCESS_CODE);
        responseBean.setRespMsg(SUCCESS_MESSAGE);
        responseData(responseStream, responseBean);
        
        return null;
    }
    // 储蓄卡签约【trade】
    @RequestMapping("/debitCardSign.htm")
    public ModelAndView debitCardSign(String data, HttpSession httpSession, HttpServletResponse response){
        PrintWriter responseStream = getPrintWriter(response);
        // 接收元数据
        JSONObject jsonObject = JSONObject.fromObject( data);

        // 将请求数据转化为bean
        SignDebitCard_Request requestBean = (SignDebitCard_Request) JSONObject.toBean( jsonObject , SignDebitCard_Request.class);

        // 返回bean数据准备
        SignDebitCard_Response responseBean  = BeanCopyUtil.copyBean(SignDebitCard_Response.class, requestBean);

        // 共通检查
        String errorMsg = requestCheck(response, data, responseBean, requestBean);
        if (StringUtil.isNotEmpty(errorMsg)) {
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg(errorMsg);
            responseData(responseStream, responseBean);
            return null;
        }
        if (log.isDebugEnabled()) {
            log.debug("共通检查：通过");
        }
        String bindId = null;
        try {
            bindId = gateWayService.bankCardSign(data);
            if (log.isDebugEnabled()) {
                log.debug("调用交易共通处理接口：通过");
                log.debug("调用交易共通处理接口返回值："+bindId);
            }
        } catch (TradeException e) {
            log.error(e.getMessage(),e); 
            log.error("储蓄卡签约发生错误，请求数据："+data);
            log.error("错误信息："+e.getMessage());
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg(e.getMessage());
            responseData(responseStream, responseBean);
            return null;
        } catch (Exception e) {
            log.error("储蓄卡签约发生未知错误");
            log.error(e.getMessage(),e); 
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg("储蓄卡签约发生错误");
            responseData(responseStream, responseBean);
            return null;
        }
        if (StringUtil.isEmpty(bindId)) {
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg("储蓄卡签约发生错误");
            responseData(responseStream, responseBean);
            return null;
        }

        // 返回正常结果
        responseBean.setRespCode(SUCCESS_CODE);
        responseBean.setRespMsg(SUCCESS_MESSAGE);
        responseBean.setBindId(bindId);
        responseData(responseStream, responseBean);

        return null;
    }
    // 信用卡签约【trade】
    @RequestMapping("/creditCardSign.htm")
    public ModelAndView creditCardSign(String data, HttpSession httpSession, HttpServletResponse response){
        PrintWriter responseStream = getPrintWriter(response);
        // 接收元数据
        JSONObject jsonObject = JSONObject.fromObject( data);

        // 将请求数据转化为bean
        SignCreditCard_Request requestBean = (SignCreditCard_Request) JSONObject.toBean( jsonObject , SignCreditCard_Request.class);

        // 返回bean数据准备
        SignCreditCard_Response responseBean  = BeanCopyUtil.copyBean(SignCreditCard_Response.class, requestBean);

        // 共通检查
        String errorMsg = requestCheck(response, data, responseBean, requestBean);
        if (StringUtil.isNotEmpty(errorMsg)) {
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg(errorMsg);
            responseData(responseStream, responseBean);
            return null;
        }
        if (log.isDebugEnabled()) {
            log.debug("共通检查：通过");
        }
        String bindId = null;
        try {
            bindId = gateWayService.bankCardSign(data);
            if (log.isDebugEnabled()) {
                log.debug("调用交易共通处理接口：通过");
                log.debug("调用交易共通处理接口返回值："+bindId);
            }
        } catch (TradeException e) {
            log.error("信用卡签约发生错误，请求数据："+data);
            log.error("错误信息："+e.getMessage());
            log.error(e.getMessage(),e); 
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg(e.getMessage());
            responseData(responseStream, responseBean);
            return null;
        }
        if (StringUtil.isEmpty(bindId)) {
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg("信用卡签约发生错误");
            responseData(responseStream, responseBean);
            return null;
        }

        // 返回正常结果
        responseBean.setRespCode(SUCCESS_CODE);
        responseBean.setRespMsg(SUCCESS_MESSAGE);
        responseBean.setBindId(bindId);
        responseData(responseStream, responseBean);

        return null;
    }
    // 发送短信验证码【trade】
    @RequestMapping("/sendMessageCaptcha.htm")
    public ModelAndView sendMessageCaptcha(String data, HttpSession httpSession, HttpServletResponse response){
        PrintWriter responseStream = getPrintWriter(response);
        // 接收元数据
        JSONObject jsonObject = JSONObject.fromObject( data);

        // 将请求数据转化为bean
        SendMessageCaptcha_Request requestBean = (SendMessageCaptcha_Request) JSONObject.toBean( jsonObject , SendMessageCaptcha_Request.class);

        // 返回bean数据准备
        SendMessageCaptcha_Response responseBean  = BeanCopyUtil.copyBean(SendMessageCaptcha_Response.class, requestBean);

        // 共通检查
        String errorMsg = requestCheck(response, data, responseBean, requestBean);
        if (StringUtil.isNotEmpty(errorMsg)) {
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg(errorMsg);
            responseData(responseStream, responseBean);
            return null;
        }
        
        try {
            gateWayService.sendSMSMessage(data);
        } catch (TradeException e) {
            log.error("发送验证码时发生错误，请求数据："+data);
            log.error("错误信息："+e.getMessage());
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg(e.getMessage());
            responseData(responseStream, responseBean);
            return null;
        }
        // 返回正常结果
        responseBean.setRespCode(SUCCESS_CODE);
        responseBean.setRespMsg(SUCCESS_MESSAGE);
        responseData(responseStream, responseBean);

        return null;
    }
    // 签约支付【trade】
    @RequestMapping("/signedPay.htm")
    public ModelAndView signedPay(String data, HttpSession httpSession, HttpServletResponse response){
        PrintWriter responseStream = getPrintWriter(response);
        // 接收元数据
        JSONObject jsonObject = JSONObject.fromObject( data);

        // 将请求数据转化为bean
        SignPay_Request requestBean = (SignPay_Request) JSONObject.toBean( jsonObject , SignPay_Request.class);

        // 返回bean数据准备
        SignPay_Response responseBean  = BeanCopyUtil.copyBean(SignPay_Response.class, requestBean);

        // 共通检查
        String errorMsg = requestCheck(response, data, responseBean, requestBean);
        if (StringUtil.isNotEmpty(errorMsg)) {
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg(errorMsg);
            responseData(responseStream, responseBean);
            return null;
        }
        try {
            gateWayService.submitPay(data);
        } catch (TradeException e) {
            log.error("签约支付时发生错误，请求数据："+data);
            log.error("错误信息："+e.getMessage());
            
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg(e.getMessage());
            responseData(responseStream, responseBean);
            e.printStackTrace();
            return null;
        }

        String frontURL = getFrontUrl(requestBean.getTn());

        // 返回正常结果
        responseBean.setRespCode(SUCCESS_CODE);
        responseBean.setRespMsg(SUCCESS_MESSAGE);
        responseBean.setFrontUrl(frontURL);
        responseData(responseStream, responseBean);
        
        return null;
    }
    // 会员登录
    @RequestMapping("/memberLogin.htm")
    public ModelAndView memberLogin(String data, HttpSession httpSession, HttpServletResponse response){
        PrintWriter responseStream = getPrintWriter(response);
        // 接收元数据
        JSONObject jsonObject = JSONObject.fromObject( data);

        // 将请求数据转化为bean
        MemberLogin_Request requestBean = (MemberLogin_Request) JSONObject.toBean( jsonObject , MemberLogin_Request.class);

        // 返回bean数据准备
        MemberLogin_Response responseBean  = BeanCopyUtil.copyBean(MemberLogin_Response.class, requestBean);

        // 共通检查
        String errorMsg = requestCheck(response, data, responseBean, requestBean);
        if (StringUtil.isNotEmpty(errorMsg)) {
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg(errorMsg);
            responseData(responseStream, responseBean);
            return null;
        }

        // 业务处理
        try {
            // 平台私钥解密
            MerchMK merchMk = merchMKService.get(requestBean.getVirtualId());
            byte[] decodedData = RSAUtils.decryptByPrivateKey(Base64Utils.decode(requestBean.getPassWd()), merchMk.getLocalPriKey());;
            String passWd = StringUtil.getUTF8(decodedData);

            // 密码信息对比
            String dataEncode = Md5.getInstance().md5s(passWd);
            PojoPersonDeta person = personDAO.personLoninByMid(requestBean.getMemberId(), dataEncode);
            if (person == null) {
                responseBean.setRespCode(ERROR_CODE);
                responseBean.setRespMsg("会员不存在或密码错误！");
                responseData(responseStream, responseBean);
                return null;
            }
        } catch (Exception e) {
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg("会员不存在或密码错误！");
            responseData(responseStream, responseBean);
            return null;
        }
        // 返回正常结果
        responseBean.setRespCode(SUCCESS_CODE);
        responseBean.setRespMsg(SUCCESS_MESSAGE);
        responseBean.setAccessToken(String.valueOf(System.currentTimeMillis()));

        responseData(responseStream, responseBean);
        return null;
    }
    // 余额支付【trade】
    @RequestMapping("/balancePay.htm")
    public ModelAndView balancePay(String data, HttpSession httpSession, HttpServletResponse response){
        PrintWriter responseStream = getPrintWriter(response);
        // 接收元数据
        JSONObject jsonObject = JSONObject.fromObject( data);

        // 将请求数据转化为bean
        BalancePayment_Request requestBean = (BalancePayment_Request) JSONObject.toBean( jsonObject , BalancePayment_Request.class);

        // 返回bean数据准备
        BalancePayment_Response responseBean  = BeanCopyUtil.copyBean(BalancePayment_Response.class, requestBean);

        // 共通检查
        String errorMsg = requestCheck(response, data, responseBean, requestBean);
        if (StringUtil.isNotEmpty(errorMsg)) {
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg(errorMsg);
            responseData(responseStream, responseBean);
            return null;
        }

        //账户支付
        try {
            gateWayService.accountPay(data);
        } catch (TradeException e) {
            log.error(e.getMessage(),e); 
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg(e.getMessage());
            responseData(responseStream, responseBean);
            return null;
        } catch (Exception e) {
            log.error(e.getMessage(),e); 
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg("余额支付失败");
            responseData(responseStream, responseBean);
            return null;
        }

        String frontURL = getFrontUrl(requestBean.getTn());
        
        // 返回正常结果
        responseBean.setRespCode(SUCCESS_CODE);
        responseBean.setRespMsg(SUCCESS_MESSAGE);
        responseBean.setFrontUrl(frontURL);
        responseData(responseStream, responseBean);
        
        return null;
    }
    // 余额查询
    @RequestMapping("/queryMemberBalance.htm")
    public ModelAndView queryMemberBalance(String data, HttpSession httpSession, HttpServletResponse response){
        PrintWriter responseStream = getPrintWriter(response);
        // 接收元数据
        JSONObject jsonObject = JSONObject.fromObject( data);

        // 将请求数据转化为bean
        QueryMemberBalance_Request requestBean = (QueryMemberBalance_Request) JSONObject.toBean( jsonObject , QueryMemberBalance_Request.class);

        // 返回bean数据准备
        QueryMemberBalance_Response responseBean  = BeanCopyUtil.copyBean(QueryMemberBalance_Response.class, requestBean);

        // 共通检查
        String errorMsg = requestCheck(response, data, responseBean, requestBean);
        if (StringUtil.isNotEmpty(errorMsg)) {
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg(errorMsg);
            responseData(responseStream, responseBean);
            return null;
        }
        // 业务处理
        try {
            String account = busiAcctService.getAccount(Usage.BASICPAY, requestBean.getMemberId());
            PojoAccount accountPojo = accountDAO.getByAcctCode(account);
            if (accountPojo == null) {
                throw new AccBussinessException("E000014",new Object[]{account});
            }
            responseBean.setBalance(accountPojo.getBalance().getAmount().toPlainString());
        } catch (AbstractBusiAcctException e) {
            log.error("获取默认资金账户失败，会员ID:"+requestBean.getMemberId());
            log.error(e.getMessage(),e); 
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg(e.getMessage());
            responseData(responseStream, responseBean);
            return null;
        } catch (AccBussinessException e) {
            log.error(e.getMessage());
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg(e.getMessage());
            responseData(responseStream, responseBean);
            return null;
        }

        // 返回正常结果
        responseBean.setRespCode(SUCCESS_CODE);
        responseBean.setRespMsg(SUCCESS_MESSAGE);
        responseData(responseStream, responseBean);

        return null;
    }
    // 交易状态查询交易【商家版】【trade】
    @RequestMapping("/queryTradeStatus.htm")
    public ModelAndView queryTradeStatus(String data, HttpSession httpSession, HttpServletResponse response){
        PrintWriter responseStream = getPrintWriter(response);
        // 接收元数据
        JSONObject jsonObject = JSONObject.fromObject( data);

        // 将请求数据转化为bean
        QueryTradeStatus_Request requestBean = (QueryTradeStatus_Request) JSONObject.toBean( jsonObject , QueryTradeStatus_Request.class);

        // 返回bean数据准备
        QueryTradeStatus_Response responseBean  = BeanCopyUtil.copyBean(QueryTradeStatus_Response.class, requestBean);

        // 共通检查
        String errorMsg = requestCheck(response, data, responseBean, requestBean);
        if (StringUtil.isNotEmpty(errorMsg)) {
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg(errorMsg);
            responseData(responseStream, responseBean);
            return null;
        }
        // 业务处理
        TxnsLogModel txnsLog = null;
        TxnsOrderinfoModel orderInfo = null;
        if (StringUtil.isEmpty(requestBean.getQueryId())) {
            if (StringUtil.isEmpty(requestBean.getTxnTime()) || StringUtil.isEmpty(requestBean.getOrderId())) {
                    responseBean.setRespCode(ERROR_CODE);
                    responseBean.setRespMsg("如果交易查询流水号不送时，商户订单号和订单发送时间必须发送。");
                    responseData(responseStream, responseBean);
                    return null;
            } else {
                // 通过 订单发送时间 和 商户订单号 查询
                orderInfo = gateWayService.getOrderinfoByOrderNo(requestBean.getOrderId());
                String queryString = "from TxnsLogModel txn where accordno = ?";
                if (orderInfo != null) {
                    txnsLog = txnsLogService.getUniqueByHQL(queryString, new Object[]{orderInfo.getOrderno()});
                }
            }
        } else {
            // 通过 交易查询流水号 查询
            String queryString = "from TxnsLogModel txn where tradeseltxn = ?";
            // 得到交易流水表信息
            txnsLog = txnsLogService.getUniqueByHQL(queryString, new Object[]{requestBean.getQueryId()});
            // 得到订单表信息
            if (txnsLog != null)
                orderInfo = gateWayService.getOrderinfoByOrderNo(txnsLog.getAccordno());
        }
        if (txnsLog == null) {
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg("找不到相应的交易流水信息。交易查询流水号："+requestBean.getQueryId());
            responseData(responseStream, responseBean);
            return null;
        }
        if (orderInfo == null) {
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg("找不到相应的订单信息。订单号："+txnsLog.getAccordno());
            responseData(responseStream, responseBean);
            return null;
        }
        
        // 查询成功
        responseBean.setTraceTime(txnsLog.getPayordfintime());// 交易传输时间
        responseBean.setSettleDate(txnsLog.getAccsettledate());// 清算日期
        responseBean.setSettleCurrencyCode("156");// 清算币种
        responseBean.setOrigRespCode(orderInfo.getStatus());// 原交易应答码
        
         // 01:初始，订单提交成功，但未支付；02：支付中；03：支付失败；00：支付成功，04：订单失效
        // 原交易应答信息
        responseBean.setOrigRespMsg(statusToMessage(orderInfo.getStatus()));

        // 返回正常结果
        responseBean.setRespCode(SUCCESS_CODE);
        responseBean.setRespMsg(SUCCESS_MESSAGE);
        responseData(responseStream, responseBean);

        return null;
    }
    // 支付密码初始设置
    @RequestMapping("/initPayPwd.htm")
    public ModelAndView initPayPwd(String data, HttpSession httpSession, HttpServletResponse response){
        PrintWriter responseStream = getPrintWriter(response);
        // 接收元数据
        JSONObject jsonObject = JSONObject.fromObject( data);

        // 将请求数据转化为bean
        InitMemberPayPassword_Request requestBean = (InitMemberPayPassword_Request) JSONObject.toBean( jsonObject , InitMemberPayPassword_Request.class);

        // 返回bean数据准备
        InitMemberPayPassword_Response responseBean  = BeanCopyUtil.copyBean(InitMemberPayPassword_Response.class, requestBean);

        // 共通检查
        String errorMsg = requestCheck(response, data, responseBean, requestBean);
        if (StringUtil.isNotEmpty(errorMsg)) {
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg(errorMsg);
            responseData(responseStream, responseBean);
            return null;
        }
        // 业务处理
        try {
            // 平台私钥解密【登陆密码】
            MerchMK merchMk = merchMKService.get(requestBean.getVirtualId());
            byte[] decodedData = RSAUtils.decryptByPrivateKey(Base64Utils.decode(requestBean.getPassWd()), merchMk.getLocalPriKey());
            byte[] payDecodedData = RSAUtils.decryptByPrivateKey(Base64Utils.decode(requestBean.getPayPwd()), merchMk.getLocalPriKey());
            
            String payPassWd=StringUtil.getUTF8(payDecodedData);
            String passWd = StringUtil.getUTF8(decodedData);
            String dataEncode = Md5.getInstance().md5s(passWd);
            if (log.isDebugEnabled()) {
                log.debug("支付密码："+payPassWd);
                log.debug("登陆密码："+passWd);
                log.debug("加密后数据："+dataEncode);
            }
            PojoPersonDeta person = personDAO.personLoninByMid(requestBean.getMemberId(), dataEncode);
            if (person == null) {
                responseBean.setRespCode(ERROR_CODE);
                responseBean.setRespMsg("会员不存在或登陆密码错误！");
                responseData(responseStream, responseBean);
                return null;
            }
            // 判断是否已经设置过支付密码了
            if (StringUtil.isNotEmpty(person.getPayPwd())) {
                responseBean.setRespCode(ERROR_CODE);
                responseBean.setRespMsg("该会员已经设置过支付密码了！");
                responseData(responseStream, responseBean);
                return null;
            }
            String passwordKey = ConsUtil.getInstance().cons.getMember_pay_password_key();
            String payPassword = Md5.getInstance().md5s(payPassWd+passwordKey);
            person.setPayPwd(payPassword);
            personDAO.saveA(person);
        } catch (Exception e) {
            log.error(e.getMessage(),e); 
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg("初始化支付密码失败！");
            responseData(responseStream, responseBean);
            return null;
        }

        // 返回正常结果
        responseBean.setRespCode(SUCCESS_CODE);
        responseBean.setRespMsg(SUCCESS_MESSAGE);
        responseData(responseStream, responseBean);

        return null;
    }
    // 单笔退款
    @RequestMapping("/refund.htm")
    private ModelAndView refund(String data,  HttpSession httpSession, HttpServletResponse response) {
        PrintWriter responseStream = getPrintWriter(response);
        // 接收元数据
        JSONObject jsonObject = JSONObject.fromObject( data);

        // 将请求数据转化为bean
        Refund_Request requestBean = (Refund_Request) JSONObject.toBean( jsonObject , Refund_Request.class);

        // 返回bean数据准备
        Refund_Response responseBean  = BeanCopyUtil.copyBean(Refund_Response.class, requestBean);

        // 共通检查
        String errorMsg = requestCheck(response, data, responseBean, requestBean);
        if (StringUtil.isNotEmpty(errorMsg)) {
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg(errorMsg);
            responseData(responseStream, responseBean);
            return null;
        }
        String tn="";
        // 业务处理
        try {
            tn = gateWayService.refund(data);
        } catch (Exception e) {
            log.error(e.getMessage(),e); 
           responseBean.setRespCode(ERROR_CODE);
           responseBean.setRespMsg("单笔退款失败！");
           responseData(responseStream, responseBean);
            // TODO:完成之后放开
            return null;
        }

        // 返回正常结果
        responseBean.setRespCode(SUCCESS_CODE);
        responseBean.setRespMsg(SUCCESS_MESSAGE);
        responseBean.setTn(tn);
        responseData(responseStream, responseBean);

        return null;
        
    }
    // 单笔提现
    @RequestMapping("/withdraw.htm")
    private ModelAndView withdraw(String data, HttpSession httpSession,  HttpServletResponse response) {
        PrintWriter responseStream = getPrintWriter(response);
        // 接收元数据
        JSONObject jsonObject = JSONObject.fromObject( data);

        // 将请求数据转化为bean
        Withdraw_Request requestBean = (Withdraw_Request) JSONObject.toBean( jsonObject , Withdraw_Request.class);

        // 返回bean数据准备
        Withdraw_Response responseBean  = BeanCopyUtil.copyBean(Withdraw_Response.class, requestBean);

        // 共通检查
        String errorMsg = requestCheck(response, data, responseBean, requestBean);
        if (StringUtil.isNotEmpty(errorMsg)) {
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg(errorMsg);
            responseData(responseStream, responseBean);
            return null;
        }

        // 业务处理
        String tn="";
        try {
            tn = gateWayService.withdraw(data);
        } catch (Exception e) {
            log.error(e.getMessage(),e); 
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg("提现失败！");
            responseData(responseStream, responseBean);
            // TODO:完成之后放开
            return null;
        }

        // 返回正常结果
        responseBean.setRespCode(SUCCESS_CODE);
        responseBean.setRespMsg(SUCCESS_MESSAGE);
        responseBean.setTn(tn);
        responseData(responseStream, responseBean);

        return null;
        
    }
    
    /**
     * 返回数据（同步response）
     * @param responseStream
     * @param bean
     */
    @SuppressWarnings("unchecked")
    private void responseData(PrintWriter responseStream, BaseMessage bean) {
        JSONObject jsonData = JSONObject.fromObject(bean);
        // 整理返回的JSON串
        sortResponseData(jsonData);
        jsonData.put("signature", "");
        // 排序
        Map<String, Object> map = new TreeMap<String, Object>();
        map =(Map<String, Object>) JSONObject.toBean(jsonData, TreeMap.class);
        jsonData = JSONObject.fromObject(map);

        // 加签名
        if (log.isDebugEnabled()) {
            log.debug("【应答报文】加签用memberId：" + bean.getVirtualId());
            log.debug("【应答报文】加签原数据：" + jsonData.toString());
        }
        String signature  = merchMKService.sign(bean.getVirtualId(), jsonData.toString());
        jsonData.put("signature", signature);
        // 返回数据
        String rtnData = "data="+jsonData.toString();
        if (log.isDebugEnabled()) {
            log.debug("【应答报文】返回数据："+rtnData);
        }
        try {
            responseStream.write(URLEncoder.encode(rtnData, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally{
            responseStream.flush();responseStream.close();
        }
    }
    
    /**
     * 整理返回的JSON串（去除基类多余的字段）
     * @param jsonData
     */
    private void sortResponseData(JSONObject jsonData) {
        if (jsonData.get("txnType") == null) {
            return;
        }
        // 得到交易类型
        String txnType = String.valueOf(jsonData.get("txnType"));
        if("00".equals(txnType)) {
        } else if("01".equals(txnType)) {
        } else if("03".equals(txnType)) {
        } else if("04".equals(txnType)) {
            jsonData.remove("merId");
        } else if("05".equals(txnType)) {
            jsonData.remove("merId");
        } else if ("06".equals(txnType)) {
            jsonData.remove("merId");
        } else if ("07".equals(txnType)) {
            jsonData.remove("merId");
        } else if ("08".equals(txnType)) {
            jsonData.remove("merId");
        }  else if ("09".equals(txnType)) {
            jsonData.remove("merId");
        } else if ("10".equals(txnType)) {
            jsonData.remove("merId");
        } else if ("11".equals(txnType)) {
            jsonData.remove("merId");
        } else if ("12".equals(txnType)) {
            jsonData.remove("merId");
        } else if ("13".equals(txnType)) {
            jsonData.remove("merId");
        } else if ("14".equals(txnType)) {
            jsonData.remove("merId");
        } else if ("15".equals(txnType)) {
            jsonData.remove("merId");
        } else if ("16".equals(txnType)) {
            jsonData.remove("merId");
        }
    }
    /**
     * 共通项目检查
     * @param response 返回流
     * @param data 原data数据
     * @param responseBean 返回bean
     * @param requestBean 请求bean
     * @return 错误信息，如果没有返回 null
     */
    private String  requestCheck(HttpServletResponse response, String data, BaseMessage responseBean, BaseMessage requestBean) {

        JSONObject verifyData = JSONObject.fromObject(data);
        if (verifyData.isNullObject())
            return "无效Json数据";
        verifyData.put("signature", "");
        if (log.isDebugEnabled()) {
            log.debug("验签用原数据："+verifyData.toString());
        }
        // 验签
        boolean isOk= merchMKService.verify(requestBean.getVirtualId(), requestBean.getSignature(), verifyData.toString());
        if (log.isDebugEnabled()) {
            log.debug("【验签结果】："+isOk);
        }
        if (!isOk) {
            return "验签失败";
        }

        // 校验报文bean数据
        String errorMsg = HibernateValidatorUtil.validateBeans(requestBean);
        if (StringUtil.isNotEmpty(errorMsg)) {
            return "报文内容校验失败："+errorMsg;
        }

        return null;
    }
    
    /**
     * 订单状态转换为描述信息
     * @param status
     * @return
     */
    private String statusToMessage(String status) {
        if ("00".equals(status)) {
            return "支付成功";
        } else if ("01".equals(status)) {
            return "初始，订单提交成功，但未支付";
        } else if ("02".equals(status)) {
            return "支付中";
        } else if ("03".equals(status)) {
            return "支付失败";
        } else if ("04".equals(status)) {
            return "订单失效";
        }
        return "未知状态";
    }
    
    /**
     * 得到前台跳转地址
     * @param tn
     * @return
     */
    private String getFrontUrl(String tn) {
        if (log.isDebugEnabled()) {
            log.debug("getFrontUrl："+tn);
        }
        // 取得订单信息
        TxnsOrderinfoModel gatewayOrderBean = gateWayService.getOrderinfoByTN(tn);
        ResultBean orderResp = gateWayService.generateRespMessage(gatewayOrderBean.getOrderno(),gatewayOrderBean.getFirmemberno());
        OrderRespBean respBean = (OrderRespBean) orderResp.getResultObj();
        String frontUrl="";
        try {
            frontUrl = gatewayOrderBean.getFronturl()+"?"+ObjectDynamic.generateReturnParamer(respBean, false, null);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return frontUrl;
    }
    /**
     * 得到输出流
     * @param response
     */
    private PrintWriter getPrintWriter(HttpServletResponse response) {
        PrintWriter responseStream = null;
        response.setContentType("textml;charset=UTF-8");
        try {
            responseStream = response.getWriter();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return responseStream;
    }
}
