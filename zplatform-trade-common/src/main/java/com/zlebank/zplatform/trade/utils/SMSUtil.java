package com.zlebank.zplatform.trade.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zlebank.zplatform.sms.pojo.enums.ModuleTypeEnum;
import com.zlebank.zplatform.sms.service.ISMSService;
import com.zlebank.zplatform.trade.service.ITxnsQuickpayService;

public class SMSUtil implements Runnable{
    private static final Log log = LogFactory.getLog(SMSUtil.class);
    private static final String ENCODE="UTF-8";
    private static final String CorpID=ConsUtil.getInstance().cons.getSms_username();//账户名
    private static final String Pwd=ConsUtil.getInstance().cons.getSms_pwd();//密码
    private static final String smsURL = ConsUtil.getInstance().cons.getSms_url();
    
    private String mobile;
    private String content;
    private String tn;
    private String sendtime;
    private String payorderno;
    private String miniCardNo;
    private String amount;
    private ISMSService smsService;
    private ITxnsQuickpayService txnsQuickpayService;
    /**
     * 发送短信
     * @param mobile
     * @param content
     * @return
     */
    public int sendSMS(String mobile,String content){
        int inputLine = 999;
        try {
            //验证码:%s(为了资金安全,切勿将验证码泄露于他人),请在10分钟内按页面提示提交验证码,您正使用尾号%s进行支付,支付金额%s元。
            inputLine = smsService.sendSMS(ModuleTypeEnum.PAY, mobile, tn, miniCardNo,amount);
            String retInfo="";
            switch (inputLine) {
                case 100 :
                    retInfo="短信发送成功";
                    break;
                case 101 :
                    retInfo="账号密码不能为空";
                    break;
                case 102 :
                    retInfo="手机号,短信内容均不能为空";
                    break;
                case 103 :
                    retInfo="数据库连接失败";
                    break;
                case 104 :
                    retInfo="账号密码错误";
                    break;
                case 105 :
                    retInfo="短信发送成功,等待审核!";
                    break;
                case 106 :
                    retInfo="短信发送失败";
                    break;
                case 999 :
                    retInfo="未知错误";
                    break;
            }
            
           
            txnsQuickpayService.updateCMBCSMSResult(payorderno, inputLine+"", retInfo);
            log.info("end send sms, response content is :"+inputLine);
        }catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return inputLine;
       
    }
    
    /**
     * 设置请求参数
     * @param
     * @return
     */
    private List<HttpRequestParam> setHttpParams(Map<String, String> paramMap) {
        List<HttpRequestParam> formparams = new ArrayList<HttpRequestParam>();
        Set<Map.Entry<String, String>> set = paramMap.entrySet();
        for (Map.Entry<String, String> entry : set) {
            formparams.add(new HttpRequestParam(entry.getKey(), entry.getValue()));
        }
        return formparams;
    }

    @Override
    public void run() {
        sendSMS(mobile, content);
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SMSUtil(String mobile, String content,String tn,String sendtime,String payorderno) {
        super();
        this.mobile = mobile;
        this.content = content;
        this.tn = tn;
        this.sendtime = sendtime;
        this.payorderno = payorderno;
        smsService = (ISMSService) SpringContext.getContext().getBean("smsService");
        txnsQuickpayService = (ITxnsQuickpayService) SpringContext.getContext().getBean("txnsQuickpayService");
    }
    
    public SMSUtil(String mobile, String content,String tn,String sendtime,String payorderno,String miniCardNo,String amount) {
        super();
        this.mobile = mobile;
        this.content = content;
        this.tn = tn;
        this.sendtime = sendtime;
        this.payorderno = payorderno;
        this.miniCardNo = miniCardNo;
        this.amount = amount;
        smsService = (ISMSService) SpringContext.getContext().getBean("smsService");
        txnsQuickpayService = (ITxnsQuickpayService) SpringContext.getContext().getBean("txnsQuickpayService");
    }

    public String getTn() {
        return tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getPayorderno() {
        return payorderno;
    }

    public void setPayorderno(String payorderno) {
        this.payorderno = payorderno;
    }

	/**
	 * @return the miniCardNo
	 */
	public String getMiniCardNo() {
		return miniCardNo;
	}

	/**
	 * @param miniCardNo the miniCardNo to set
	 */
	public void setMiniCardNo(String miniCardNo) {
		this.miniCardNo = miniCardNo;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
    
    
    
}
