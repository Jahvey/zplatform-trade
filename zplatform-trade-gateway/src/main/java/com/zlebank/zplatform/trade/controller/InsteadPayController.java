package com.zlebank.zplatform.trade.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zlebank.zplatform.commons.utils.Base64Utils;
import com.zlebank.zplatform.commons.utils.BeanCopyUtil;
import com.zlebank.zplatform.commons.utils.RSAUtils;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.member.bean.MerchMK;
import com.zlebank.zplatform.member.service.MerchMKService;
//import com.zlebank.zplatform.specification.utils.Base64Utils;
import com.zlebank.zplatform.trade.bean.enums.InsteadPayImportTypeEnum;
import com.zlebank.zplatform.trade.exception.BalanceNotEnoughException;
import com.zlebank.zplatform.trade.exception.DuplicateOrderIdException;
import com.zlebank.zplatform.trade.exception.FailToGetAccountInfoException;
import com.zlebank.zplatform.trade.exception.FailToInsertAccEntryException;
import com.zlebank.zplatform.trade.exception.FailToInsertFeeException;
import com.zlebank.zplatform.trade.exception.InconsistentMerchNoException;
import com.zlebank.zplatform.trade.exception.InvalidCardException;
import com.zlebank.zplatform.trade.exception.InvalidInsteadPayDataException;
import com.zlebank.zplatform.trade.exception.MerchWhiteListCheckFailException;
import com.zlebank.zplatform.trade.exception.MessageDecryptFailException;
import com.zlebank.zplatform.trade.exception.NotInsteadPayWorkTimeException;
import com.zlebank.zplatform.trade.exception.RealNameAuthFailException;
import com.zlebank.zplatform.trade.exception.RealNameCheckFailException;
import com.zlebank.zplatform.trade.insteadPay.message.BaseMessage;
import com.zlebank.zplatform.trade.insteadPay.message.InsteadPayFile;
import com.zlebank.zplatform.trade.insteadPay.message.InsteadPayQuery_Request;
import com.zlebank.zplatform.trade.insteadPay.message.InsteadPayQuery_Response;
import com.zlebank.zplatform.trade.insteadPay.message.InsteadPay_Request;
import com.zlebank.zplatform.trade.insteadPay.message.InsteadPay_Response;
import com.zlebank.zplatform.trade.insteadPay.message.MerWhiteList_Request;
import com.zlebank.zplatform.trade.insteadPay.message.MerWhiteList_Response;
import com.zlebank.zplatform.trade.insteadPay.message.RealnameAuthFile;
import com.zlebank.zplatform.trade.insteadPay.message.RealnameAuthQuery_Request;
import com.zlebank.zplatform.trade.insteadPay.message.RealnameAuthQuery_Response;
import com.zlebank.zplatform.trade.insteadPay.message.RealnameAuth_Request;
import com.zlebank.zplatform.trade.insteadPay.message.RealnameAuth_Response;
import com.zlebank.zplatform.trade.message.ResultMessage;
import com.zlebank.zplatform.trade.service.IRouteConfigService;
import com.zlebank.zplatform.trade.service.InsteadPayService;
import com.zlebank.zplatform.trade.service.MerchWhiteListService;
import com.zlebank.zplatform.trade.service.RealNameAuthService;
import com.zlebank.zplatform.trade.utils.HibernateValidatorUtil;

/**
 * 
 * 代付接口
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月20日 下午12:24:50
 * @since
 */
@Controller
@RequestMapping("interface")
public class InsteadPayController {

    private static final Log log = LogFactory.getLog(InsteadPayController.class);

    private static final String ERROR_CODE="09";
    private static final String SUCCESS_CODE="00";
    private static final String SUCCESS_MESSAGE="成功！";

    @Autowired
    private MerchMKService merchMKService;
    
    @Autowired
    private InsteadPayService insteadPayService;
    
    @Autowired
    private RealNameAuthService realNameAuthService;
    
    @Autowired
    private MerchWhiteListService merchWhiteListService;
    
    @Autowired
	private IRouteConfigService routeConfigService;
    
    // Mock 地址
    @RequestMapping("/insteadPayMock.htm")
    public ModelAndView mock(String data, HttpSession httpSession, HttpServletResponse response,HttpServletRequest request) throws HttpException, IOException{
        if (data == null) 
            return new ModelAndView("insteadMock", null);
        String head = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        String rtnJson = "";
        BufferedReader br = null;
        PostMethod post = null;
        post = new PostMethod(head+"/zplatform-trade/interface/insteadPayIndex.htm");
        post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        post.addParameter("data", data);
        post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");  
        HttpClient client = new HttpClient();
        client.getHttpConnectionManager().getParams().setSoTimeout(60000);
        int status = client.executeMethod(post);
        // 成功接收
        if (status == 200) {
            br = new BufferedReader(new InputStreamReader(post.getResponseBodyAsStream()));
            String line = br.readLine();
            StringBuffer jsonStr = new StringBuffer();
            while (line != null) {
                jsonStr.append(line);
                line = br.readLine();
            }
            String rtnData = URLDecoder.decode(jsonStr.toString(), "UTF-8")
                    .replace("data=", "");
            JSONObject json = JSONObject.fromObject(URLDecoder.decode(rtnData, "UTF-8").trim());
            rtnJson = json.toString().replace(",", ",<br/>").replace("respCode", "<font color=red>respCode</font>");
        } else {
            System.out.println("【失败】状态返回不是200，-->"+status);
        }
        Map<String,String> rtn = new HashMap<String,String>();
        rtn.put("data", rtnJson);
        return new ModelAndView("result", rtn);
    }

    // 地址路由
    @RequestMapping("/insteadPayIndex.htm")
    public ModelAndView index(String data, HttpSession httpSession, HttpServletResponse response){
        PrintWriter responseStream = getPrintWriter(response);
        if (log.isDebugEnabled()) {
            log.debug("原报文数据："+data);
        }
        InsteadPay_Response responseBean = new InsteadPay_Response();
        
        if (StringUtil.isEmpty(data)) {
            responseBean.setRespCode("10");
            responseBean.setRespMsg("收到数据为空");
            responseData(responseStream, responseBean);
            return null;
        }
        // 接收元数据
        JSONObject jsonObject = null;
        try {
            jsonObject = JSONObject.fromObject( data);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            responseBean.setRespCode(ERROR_CODE);
            responseBean.setRespMsg("无效的Json数据");
            responseData(responseStream, responseBean);
            return null;
        }
        
        // 请求报文
        BaseMessage requestBean = new BaseMessage();
        requestBean.setTxnType((String)jsonObject.get("txnType"));

        if("21".equals(requestBean.getTxnType())) {
            // 代付交易
            batchInsteadPay(data, httpSession, response);
        } else if("22".equals(requestBean.getTxnType())||"02".equals(requestBean.getTxnType())) {
            // 代付交易查询
            batchInsteadPayStatusQuery(data, httpSession, response);
        } else if("72".equals(requestBean.getTxnType())) {
            // 实名认证
            realNameAuth(data, httpSession, response);
        }else if("73".equals(requestBean.getTxnType())) {
            // 实名认证查询
            realNameAuthQuery(data, httpSession, response);
        }else if("74".equals(requestBean.getTxnType())) {
            // 商户白名单添加
            merWhiteList(data, httpSession, response);
        } else {
            responseBean.setRespCode("12");
            responseBean.setRespMsg("不是合法的交易类型");
            responseData(responseStream, responseBean);
            return null;
        }

        return null;
    }

    // 批量代付类交易
    @RequestMapping("/batchInsteadPay.htm")
    public ModelAndView batchInsteadPay(String data, HttpSession httpSession, HttpServletResponse response){
    	PrintWriter responseStream = getPrintWriter(response);
        if (log.isDebugEnabled()) {
            log.debug("原报文数据："+data);
        }

        // 接收元数据
        JSONObject jsonObject = JSONObject.fromObject( data);

        // 解压缩数据
        String compressMsg=null;
        String compressData = (String) jsonObject.get("fileContent");
        try {
            // Base64解码
            byte[]  decodeBase64Data = Base64Utils.decode(compressData);
            // DEFLATE 算法压缩初始化
            Inflater decompresser = new Inflater();
            decompresser.setInput(decodeBase64Data, 0, decodeBase64Data.length);
            // 解压缩后最大支持10M（10485760 byte）
            byte[] result = new byte[10485760];
            // 解压并返回实际报文长度
            int resultLength = decompresser.inflate(result);
            decompresser.end();
            // 转换后放入文件内容域
            String unzipData = new String(result, 0, resultLength, "UTF-8");
            jsonObject.put("fileContent", unzipData);
        } catch (DataFormatException e1) {
            log.error(e1.getMessage(),e1);
            compressMsg = "数据解密或解压缩出现错误";
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(),e);
            compressMsg = "数据解密或解压缩出现错误";
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            compressMsg = "数据解密或解压缩出现错误";
        }
        if (StringUtil.isNotEmpty(compressMsg)) {
            InsteadPay_Response responseBean = BeanCopyUtil.copyBean(InsteadPay_Response.class, JSONObject.toBean(jsonObject));
            responseBean.setRespCode("21");
            responseBean.setRespMsg(compressMsg);
            responseData(responseStream, responseBean);
            return null;
        }

        // 请求报文
        @SuppressWarnings("rawtypes")
        Map<String, Class> classMap = new HashMap<String, Class>();  
        classMap.put("fileContent", InsteadPayFile.class);
        InsteadPay_Request requestBean = (InsteadPay_Request) JSONObject.toBean( jsonObject , InsteadPay_Request.class, classMap);

        // 响应报文【同步】
        InsteadPay_Response responseBean = BeanCopyUtil.copyBean(InsteadPay_Response.class, requestBean);
        
        // 共通检查
       ResultMessage errorMsg = requestCheck(response, data, responseBean, requestBean);
       if (errorMsg != null) {
           responseBean.setRespCode(errorMsg.getErrorCode());
           responseBean.setRespMsg(errorMsg.getErrorMessage());
           responseData(responseStream, responseBean);
           return null;
       }
       // 检查代付明细
       StringBuilder errorMsgDetail = new StringBuilder();
       for (InsteadPayFile file : requestBean.getFileContent()) {
           if (StringUtil.isEmpty(file.getBankCode())) {
        	   //如查银行卡号没有，则通过银行卡号获取联行号
        	   String bankNumber = routeConfigService.getCardPBCCode(file.getAccNo()).get("PBC_BANKCODE")+"";
        	   file.setBankCode(bankNumber);
               //file.setBankCode("000000000000");
           }
           // 校验报文bean数据
           String error = HibernateValidatorUtil.validateBeans(file);
           if (StringUtil.isNotEmpty(error)) {
               errorMsgDetail.append(error);
           }
       }

        if (StringUtil.isNotEmpty(errorMsgDetail.toString())) {
            responseBean.setRespCode("13");
            responseBean.setRespMsg(errorMsgDetail.toString() );
            responseData(responseStream, responseBean);
            return null;
        }
        
        // 调用接口
        try {
            insteadPayService.insteadPay(requestBean, InsteadPayImportTypeEnum.API, null);
        } catch (NotInsteadPayWorkTimeException e) {
            errorMsg = new ResultMessage("50", "非代付工作时间");
            log.error(e.getMessage(), e);
        } catch (FailToGetAccountInfoException e) {
            errorMsg = new ResultMessage("51", "获取商户账户信息失败");
            log.error(e.getMessage(), e);
        } catch (BalanceNotEnoughException e) {
            errorMsg = new ResultMessage("52", "余额不足");
            log.error(e.getMessage(), e);
        } catch (DuplicateOrderIdException e) {
            errorMsg = new ResultMessage("53", "订单号重复");
            log.error(e.getMessage(), e);
        }  catch (DataIntegrityViolationException e) {
            errorMsg = new ResultMessage("54", "批次号或订单号重复");
            log.error(e.getMessage(), e);
        }  catch (ConstraintViolationException e) {
            errorMsg = new ResultMessage("54", "批次号或订单号重复");
            log.error(e.getMessage(), e);
        } catch (InvalidCardException e) {
            errorMsg = new ResultMessage("55", "无效卡信息");
            log.error(e.getMessage(), e);
        } catch (FailToInsertAccEntryException e) {
            errorMsg = new ResultMessage("56", "记账时出现异常");
            log.error(e.getMessage(), e);
        } catch (MerchWhiteListCheckFailException e) {
            errorMsg = new ResultMessage("57", "商户白名单检查失败："+e.getMessage());
            log.error(e.getMessage(), e);
        } catch (FailToInsertFeeException e) {
            errorMsg = new ResultMessage("58", "代付商户不存在");
            log.error(e.getMessage(), e);
        } catch (RealNameCheckFailException e) {
            errorMsg = new ResultMessage("5A", e.getMessage());
            log.error(e.getMessage(), e);
        } catch (InconsistentMerchNoException e) {
            errorMsg = new ResultMessage("5B", e.getMessage());
            log.error(e.getMessage(), e);
        } catch (InvalidInsteadPayDataException e) {
            errorMsg = new ResultMessage("5C", e.getMessage());
            log.error(e.getMessage(), e);
        }
        catch (Exception e) {
          errorMsg = new ResultMessage("59", "代付失败");
          log.error(e.getMessage(), e);
        }


        if (errorMsg != null) {
            responseBean.setRespCode(errorMsg.getErrorCode());
            responseBean.setRespMsg(errorMsg.getErrorMessage());
            responseData(responseStream, responseBean);
            return null;
        }

        // 返回正常结果
        responseBean.setRespCode(SUCCESS_CODE);
        responseBean.setRespMsg(SUCCESS_MESSAGE);
        responseData(responseStream, responseBean);
        
        return null;
    }
    // 批量交易状态查询
    @RequestMapping("/batchInsteadPayStatusQuery.htm")
    public ModelAndView batchInsteadPayStatusQuery(String data, HttpSession httpSession, HttpServletResponse response){
        PrintWriter responseStream = getPrintWriter(response);
        if (log.isDebugEnabled()) {
            log.debug("原报文数据："+data);
        }

        // 接收元数据
        JSONObject jsonObject = JSONObject.fromObject( data);

        // 请求报文
        @SuppressWarnings("rawtypes")
        Map<String, Class> classMap = new HashMap<String, Class>();  
        classMap.put("fileContent", InsteadPayFile.class);
        InsteadPayQuery_Request requestBean = (InsteadPayQuery_Request) JSONObject.toBean( jsonObject , InsteadPayQuery_Request.class, classMap);

        // 响应报文【同步】
        InsteadPayQuery_Response responseBean = BeanCopyUtil.copyBean(InsteadPayQuery_Response.class, requestBean);
        
        // 共通检查
       ResultMessage errorMsg = requestCheck(response, data, responseBean, requestBean);
        if (errorMsg != null) {
            responseBean.setRespCode(errorMsg.getErrorCode());
            responseBean.setRespMsg(errorMsg.getErrorMessage());
            responseData(responseStream, responseBean);
            return null;
        }
        
        // 调用接口
        try {
            insteadPayService.insteadPayQuery(requestBean, responseBean);
        }  catch (Exception e) {
            errorMsg = new ResultMessage("60", "代付交易查询失败");
            log.error(e.getMessage(), e);
        }

        if (errorMsg != null) {
            responseBean.setRespCode(errorMsg.getErrorCode());
            responseBean.setRespMsg(errorMsg.getErrorMessage());
            responseData(responseStream, responseBean);
            return null;
        }

        // 返回正常结果
        responseData(responseStream, responseBean);
        
        return null;
    }
    // 实名认证
    @RequestMapping("/realNameAuth.htm")
    public ModelAndView realNameAuth(String data, HttpSession httpSession, HttpServletResponse response){
        PrintWriter responseStream = getPrintWriter(response);
        if (log.isDebugEnabled()) {
            log.debug("原报文数据："+data);
        }

        // 接收元数据
        JSONObject jsonObject = JSONObject.fromObject( data);

        // 请求报文
        RealnameAuth_Request requestBean = (RealnameAuth_Request) JSONObject.toBean( jsonObject , RealnameAuth_Request.class);

        // 响应报文【同步】
        RealnameAuth_Response responseBean = BeanCopyUtil.copyBean(RealnameAuth_Response.class, requestBean);
        
        // 共通检查
       ResultMessage errorMsg = requestCheck(response, data, responseBean, requestBean);
       if (errorMsg != null) {
           responseBean.setRespCode(errorMsg.getErrorCode());
           responseBean.setRespMsg(errorMsg.getErrorMessage());
           responseData(responseStream, responseBean);
           return null;
       }
        // 解密
        RealnameAuthFile realNameAuth = null;
        try {
            // 报文解码（Base64）
            byte[] decodeData = Base64Utils.decode(requestBean.getEncryptData());
            // 报文解密（平台私钥解密）
            MerchMK merchMk = merchMKService.get(requestBean.getMerId());
            byte[] decodedData = RSAUtils.decryptByPrivateKey(decodeData, merchMk.getLocalPriKey());
            JSONObject realNameAuthJson =JSONObject.fromObject(StringUtil.getUTF8(decodedData));
            realNameAuth = (RealnameAuthFile) JSONObject.toBean(realNameAuthJson,RealnameAuthFile.class);
            
            if(log.isDebugEnabled()) {
                log.debug("实名认证报文解密结果：" + realNameAuthJson.toString());
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e); 
            errorMsg = new ResultMessage("21", "报文解密失败");
        }
        if (errorMsg != null) {
            responseBean.setRespCode(errorMsg.getErrorCode());
            responseBean.setRespMsg(errorMsg.getErrorMessage());
            responseData(responseStream, responseBean);
            return null;
        }
        String detailErrorMsg = HibernateValidatorUtil.validateBeans(realNameAuth);
        if (StringUtil.isNotEmpty(detailErrorMsg)) {
            responseBean.setRespCode("13");
            responseBean.setRespMsg(detailErrorMsg);
            responseData(responseStream, responseBean);
            return null;
        }

        // 调用接口
        try {
            Long orderId = realNameAuthService.saveRealNameAuthOrder(requestBean, realNameAuth);
            realNameAuthService.realNameAuth(requestBean,realNameAuth,orderId);
        } catch (MessageDecryptFailException e) {
            errorMsg = new ResultMessage("21", "报文解密失败");
            log.error(e.getMessage(),e); 
        } catch (RealNameAuthFailException e) {
            errorMsg = new ResultMessage("70", "实名认证失败："+e.getMessage());
            log.error(e.getMessage(), e);
        } catch (DuplicateOrderIdException e) {
            errorMsg = new ResultMessage("71", "请不要提交重复的订单");
            log.error(e.getMessage(), e);
        } catch (BalanceNotEnoughException e) {
            errorMsg = new ResultMessage("72", "余额不足");
            log.error(e.getMessage(), e);
        }  catch (Exception e) {
            errorMsg = new ResultMessage("70", "实名认证失败："+e.getMessage());
            log.error(e.getMessage(), e);
        }

        if (errorMsg != null) {
            responseBean.setRespCode(errorMsg.getErrorCode());
            responseBean.setRespMsg(errorMsg.getErrorMessage());
            responseData(responseStream, responseBean);
            return null;
        }

        // 返回正常结果
        responseBean.setRespCode(SUCCESS_CODE);
        responseBean.setRespMsg("认证成功");
        responseData(responseStream, responseBean);

        return null;
    }
    // 实名认证查询
    @RequestMapping("/realNameAuthQuery.htm")
    public ModelAndView realNameAuthQuery(String data, HttpSession httpSession, HttpServletResponse response){
        PrintWriter responseStream = getPrintWriter(response);
        if (log.isDebugEnabled()) {
            log.debug("原报文数据："+data);
        }

        // 接收元数据
        JSONObject jsonObject = JSONObject.fromObject( data);

        // 请求报文
        RealnameAuthQuery_Request requestBean = (RealnameAuthQuery_Request) JSONObject.toBean( jsonObject , RealnameAuthQuery_Request.class);

        // 响应报文【同步】
        RealnameAuthQuery_Response responseBean = BeanCopyUtil.copyBean(RealnameAuthQuery_Response.class, requestBean);

        // 共通检查
       ResultMessage errorMsg = requestCheck(response, data, responseBean, requestBean);
       if (errorMsg != null) {
           responseBean.setRespCode(errorMsg.getErrorCode());
           responseBean.setRespMsg(errorMsg.getErrorMessage());
           responseData(responseStream, responseBean);
           return null;
       }

        // 调用接口
        try {
            realNameAuthService.realNameAuthQuery(requestBean, responseBean);
        } catch (Exception e) {
            errorMsg = new ResultMessage("80", "认证查询失败");
            log.error(e.getMessage(), e);
        }

        if (errorMsg != null) {
            responseBean.setRespCode(errorMsg.getErrorCode());
            responseBean.setRespMsg(errorMsg.getErrorMessage());
            responseData(responseStream, responseBean);
            return null;
        }

        // 返回正常结果
        responseBean.setRespCode(SUCCESS_CODE);
        responseBean.setRespMsg("认证查询成功！");
        responseData(responseStream, responseBean);

        return null;
    }
    // 白名单接口
    @RequestMapping("/merWhiteList.htm")
    public ModelAndView merWhiteList(String data, HttpSession httpSession, HttpServletResponse response){
        PrintWriter responseStream = getPrintWriter(response);
        if (log.isDebugEnabled()) {
            log.debug("原报文数据："+data);
        }

        // 接收元数据
        JSONObject jsonObject = JSONObject.fromObject( data);

        // 请求报文
        MerWhiteList_Request requestBean = (MerWhiteList_Request) JSONObject.toBean( jsonObject , MerWhiteList_Request.class);

        // 响应报文【同步】
        MerWhiteList_Response responseBean = BeanCopyUtil.copyBean(MerWhiteList_Response.class, requestBean);

        // 共通检查
       ResultMessage errorMsg = requestCheck(response, data, responseBean, requestBean);
       if (errorMsg != null) {
           responseBean.setRespCode(errorMsg.getErrorCode());
           responseBean.setRespMsg(errorMsg.getErrorMessage());
           responseData(responseStream, responseBean);
           return null;
       }

        // 调用接口
        try {
            String error = merchWhiteListService.addMerchWhiteListService(requestBean);
            if (StringUtil.isNotEmpty(error)) {
                errorMsg = new ResultMessage("90", error);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            errorMsg = new ResultMessage("90", "添加白名单失败");
        }

        if (errorMsg != null) {
            responseBean.setRespCode(errorMsg.getErrorCode());
            responseBean.setRespMsg(errorMsg.getErrorMessage());
            responseData(responseStream, responseBean);
            return null;
        }

        // 返回正常结果
        responseBean.setRespCode(SUCCESS_CODE);
        responseBean.setRespMsg("添加白名单成功！");
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
        jsonData.put("signature", "");
        // 排序
        Map<String, Object> map = new TreeMap<String, Object>();
        map =(Map<String, Object>) JSONObject.toBean(jsonData, TreeMap.class);
        jsonData = JSONObject.fromObject(map);

        // 加签名
        if (log.isDebugEnabled()) {
            log.debug("【应答报文】加签用memberId：" + bean.getMerId());
            log.debug("【应答报文】加签原数据：" + jsonData.toString());
        }
        String signature  = merchMKService.sign(bean.getMerId(), jsonData.toString());
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
     * 共通项目检查
     * @param response 返回流
     * @param data 原data数据
     * @param responseBean 返回bean
     * @param requestBean 请求bean
     * @return 错误信息，如果没有返回 null
     */
    private ResultMessage  requestCheck(HttpServletResponse response, String data, BaseMessage responseBean, BaseMessage requestBean) {

        JSONObject verifyData = JSONObject.fromObject(data);
        if (verifyData.isNullObject())
            return new ResultMessage("11", "无效Json数据");
        verifyData.put("signature", "");
        if (log.isDebugEnabled()) {
            log.debug("验签用原数据："+verifyData.toString());
        }
        // 验签
        boolean isOk= merchMKService.verify(requestBean.getMerId(), requestBean.getSignature(), verifyData.toString());
        if (log.isDebugEnabled()) {
            log.debug("【验签结果】："+isOk);
        }
        if (!isOk) {
            return new ResultMessage("20", "验签失败");
        }

        // 校验报文bean数据
        String errorMsg = HibernateValidatorUtil.validateBeans(requestBean);
        if (StringUtil.isNotEmpty(errorMsg)) {
            return new ResultMessage("13", "报文内容校验失败："+errorMsg);
        }

        return null;
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
