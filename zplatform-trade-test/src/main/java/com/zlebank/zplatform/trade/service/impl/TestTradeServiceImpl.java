/* 
 * TestTradeServiceImpl.java  
 * 
 * version TODO
 *
 * 2015年10月19日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.trade.bean.ReaPayResultBean;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.reapal.BindBean;
import com.zlebank.zplatform.trade.bean.reapal.CreditBean;
import com.zlebank.zplatform.trade.bean.reapal.DebitBean;
import com.zlebank.zplatform.trade.bean.reapal.PayBean;
import com.zlebank.zplatform.trade.bean.reapal.QueryBean;
import com.zlebank.zplatform.trade.bean.reapal.SMSBean;
import com.zlebank.zplatform.trade.service.ITestTradeService;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.ObjectDynamic;
import com.zlebank.zplatform.trade.utils.OrderNumber;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年10月19日 上午11:56:36
 * @since 
 */
@Service("testTradeService")
public class TestTradeServiceImpl implements ITestTradeService {

  private static final Log log = LogFactory.getLog(TestTradeServiceImpl.class);
    
    /**
     *
     * @return
     */
    @Transactional
    public ResultBean debitCardSign(DebitBean debitBean) {
        ResultBean resultBean = null;
        try {
            String url = ConsUtil.getInstance().cons.getReapay_quickpay_url()+ConsUtil.getInstance().cons.getReapay_quickpay_debit_url();
            String[] unParamstring = {"sign","sign_type"};
            Map<String, String> map = ObjectDynamic.generateMap(debitBean, false, unParamstring);
            //String post = buildSubmit(map,url);
            //String res = Decipher.decryptData(post);
            //log.info("recive reapay decryptData message:"+res);
            //ReaPayResultBean reaPayResultBean = ReaPayTradeAnalyzer.generateDebitResultBean(JSON.parseObject(res));
            ReaPayResultBean reaPayResultBean = new ReaPayResultBean(debitBean.getMerchant_id(),debitBean.getOrder_no(), "0000", OrderNumber.getInstance().generateBindCardID()+"", "签约成功", "", "","");
            resultBean = new ResultBean(reaPayResultBean);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.error(e);
            resultBean = new ResultBean("RC99", "交易失败");
        }
        return resultBean;
    }
    
    

    /**
     *
     * @return
     */
    
    public ResultBean creditCardSign(CreditBean creditBean) {
        ResultBean resultBean = null;
        try {
            String url = ConsUtil.getInstance().cons.getReapay_quickpay_url()+ConsUtil.getInstance().cons.getReapay_quickpay_credit_url();
            String[] unParamstring = {"sign","sign_type"};
            Map<String, String> map = ObjectDynamic.generateMap(creditBean, false, unParamstring);
           // String post = buildSubmit(map,url);
            //String res = Decipher.decryptData(post);
           // log.info("recive reapay decryptData message:"+res);
            //ReaPayResultBean reaPayResultBean = ReaPayTradeAnalyzer.generateCreditResultBean(JSON.parseObject(res));
            ReaPayResultBean reaPayResultBean = new ReaPayResultBean(creditBean.getMerchant_id(), creditBean.getOrder_no(), "0000", OrderNumber.getInstance().generateBindCardID()+"", "签约成功", "", "","");
            resultBean = new ResultBean(reaPayResultBean);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.error(e);
            resultBean = new ResultBean("RC99", "交易失败");
        }
        return resultBean;
    }

    /**
     *
     * @return
     */
    
    public ResultBean bindListQuery() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     *
     * @return
     */
    
    public ResultBean bindCard(BindBean bindBean) {
        ResultBean resultBean = null;
        try {
            String url = ConsUtil.getInstance().cons.getReapay_quickpay_url()+ConsUtil.getInstance().cons.getReapay_quickpay_bind_url();
            String[] unParamstring = {"sign","sign_type"};
            Map<String, String> map = ObjectDynamic.generateMap(bindBean, false, unParamstring);
            //String post = buildSubmit(map,url);
            //String res = Decipher.decryptData(post);
            //log.info("recive reapay decryptData message:"+res);
            ReaPayResultBean reaPayResultBean = new ReaPayResultBean(bindBean.getMerchant_id(), bindBean.getOrder_no() , "0000", "", "签约成功", "", "","");
            //ReaPayResultBean reaPayResultBean = ReaPayTradeAnalyzer.generateBindResultBean(JSON.parseObject(res));
            resultBean = new ResultBean(reaPayResultBean);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            resultBean = new ResultBean("RC99", "交易失败");
            log.error(e);
        }
        return resultBean;
    }

    /**
     *
     * @return
     */
    
    public ResultBean reSendSms(SMSBean smsBean) {
        ResultBean resultBean = null;
        try {
            String url = ConsUtil.getInstance().cons.getReapay_quickpay_url()+ConsUtil.getInstance().cons.getReapay_quickpay_sms_url();
            String[] unParamstring = {"sign","sign_type"};
            Map<String, String> map = ObjectDynamic.generateMap(smsBean, false, unParamstring);
            //String post = buildSubmit(map,url);
            //String res = Decipher.decryptData(post);
            //log.info("recive reapay decryptData message:"+res);
            //ReaPayResultBean reaPayResultBean = ReaPayTradeAnalyzer.generateSMSResultBean(JSON.parseObject(res));
            ReaPayResultBean reaPayResultBean = new ReaPayResultBean(smsBean.getMerchant_id(), smsBean.getOrder_no(), "0000", "", "发送成功", "", "","");
            resultBean = new ResultBean(reaPayResultBean);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.error(e);
            resultBean = new ResultBean("RC99","发送失败");
        }
        return resultBean;
    }

    /**
     *
     * @return
     */
    
    public ResultBean submitPay(PayBean payBean) {
        ResultBean resultBean = null;
        try {
            String url = ConsUtil.getInstance().cons.getReapay_quickpay_url()+ConsUtil.getInstance().cons.getReapay_quickpay_pay_url();
            String[] unParamstring = {"sign","sign_type"};
            Map<String, String> map = ObjectDynamic.generateMap(payBean, false, unParamstring);
            //String post = buildSubmit(map,url);
           // String res = Decipher.decryptData(post);
            //log.info("recive reapay decryptData message:"+res);
           // ReaPayResultBean reaPayResultBean = ReaPayTradeAnalyzer.generatePayResultBean(JSON.parseObject(res));
            ReaPayResultBean reaPayResultBean = new ReaPayResultBean(payBean.getMerchant_id(), payBean.getOrder_no(), "0000", "", "交易成功", "", "", "", "", "", "", "", "","");
            resultBean = new ResultBean(reaPayResultBean);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.error(e);
            resultBean = new ResultBean("RC99", "交易失败");
        }
        return resultBean;
    }

    /**
     *
     * @return
     */
    
    public ResultBean searchPayResult(QueryBean queryBean) {
        ResultBean resultBean = null;
        try {
            String url = ConsUtil.getInstance().cons.getReapay_quickpay_url()+ConsUtil.getInstance().cons.getReapay_quickpay_query_url();
            String[] unParamstring = {"sign","sign_type"};
            Map<String, String> map = ObjectDynamic.generateMap(queryBean, false, unParamstring);
            //String post = buildSubmit(map,url);
            //String res = Decipher.decryptData(post);
            //log.info("recive reapay decryptData message:"+res);
            ReaPayResultBean reaPayResultBean = new ReaPayResultBean();
            resultBean = new ResultBean(reaPayResultBean);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.error(e);
            resultBean = new ResultBean("RC99", "交易失败");
        }
        return resultBean;
    }

    /**
     *
     * @return
     */
    
    public ResultBean cannelCard() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     *
     * @return
     */
    
    public ResultBean refund() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     *
     * @return
     */
    
    public ResultBean queryBankCard() {
        // TODO Auto-generated method stub
        return null;
    }
   
}
