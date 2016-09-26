/* 
 * ReaPayTradeAnalyzer.java  
 * 
 * version TODO
 *
 * 2015年9月16日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.analyzer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.trade.bean.ReaPayResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.reapal.BindBean;
import com.zlebank.zplatform.trade.bean.reapal.CreditBean;
import com.zlebank.zplatform.trade.bean.reapal.DebitBean;
import com.zlebank.zplatform.trade.bean.reapal.PayBean;
import com.zlebank.zplatform.trade.bean.reapal.QueryBean;
import com.zlebank.zplatform.trade.bean.reapal.SMSBean;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.DateUtil;
import com.zlebank.zplatform.trade.utils.UUIDUtil;

/**
 * Class Description
 * 融宝支付交易数据分析器
 * @author guojia
 * @version
 * @date 2015年9月16日 上午10:43:34
 * @since 
 */
public class ReaPayTradeAnalyzer {
    
    public static DebitBean generateDebitBean(TradeBean trade){
        String merchant_id = ConsUtil.getInstance().cons.getReapay_quickpay_merchant_id();
        String version = ConsUtil.getInstance().cons.getReapay_quickpay_version();
        String card_no = trade.getCardNo();
        String owner = trade.getAcctName();
        String cert_type = "01"; // 默认
        String cert_no = trade.getCertId();
        String phone = trade.getMobile();
        String order_no = trade.getReaPayOrderNo();
        String transtime = DateUtil.getTimeStamp();
                
        String currency = "156"; // 默认
        String title = StringUtil.isEmpty(trade.getGoodsName())?"no title":trade.getGoodsName();
        String body = StringUtil.isEmpty(trade.getGoodsDesc())?"no body":trade.getGoodsDesc();
        String member_id = trade.getMerUserId();// 用户id
        String terminal_type = "mobile";// 终端类型
        String terminal_info = "djddh"; // 终端标识
        String notify_url = ConsUtil.getInstance().cons.getReapay_quickpay_notify_url();
        String member_ip = StringUtil.isEmpty(trade.getMemberIP())?"0.0.0.0":trade.getMemberIP();// 用户IP
        String seller_email = ConsUtil.getInstance().cons.getReapay_quickpay_seller_email();
        // 金额
        String total_fee = trade.getAmount();
        String token_id = UUIDUtil.uuid();
        DebitBean credit = new DebitBean(merchant_id, card_no, owner, cert_type, cert_no, phone, order_no, transtime, currency, total_fee, title, body, member_id, terminal_type, terminal_info, member_ip, seller_email, notify_url, token_id, version, "MD5", "");
        return credit;
    }
    
    public static DebitBean generateTestDebitBean(TradeBean trade){
        String merchant_id = ConsUtil.getInstance().cons.getReapay_quickpay_merchant_id();
        String version = ConsUtil.getInstance().cons.getReapay_quickpay_version();
        String card_no = trade.getCardNo();
        String owner = trade.getAcctName();
        String cert_type = "01"; // 默认
        String cert_no = trade.getCertId();
        String phone = trade.getMobile();
        String order_no = trade.getReaPayOrderNo();
        String transtime = DateUtil.getTimeStamp();
                
        String currency = "156"; // 默认
        String title = StringUtil.isEmpty(trade.getGoodsName())?"title":trade.getGoodsName();
        String body = StringUtil.isEmpty(trade.getGoodsDesc())?"body":trade.getGoodsDesc();
        String member_id = trade.getMerUserId();// 用户id
        String terminal_type = "mobile";// 终端类型
        String terminal_info = "djddh"; // 终端标识
        String notify_url = ConsUtil.getInstance().cons.getReapay_quickpay_notify_url();
        String member_ip = "member_ip";// 用户IP
        String seller_email = ConsUtil.getInstance().cons.getReapay_quickpay_seller_email();
        // 金额
        String total_fee = trade.getAmount();
        String token_id = UUIDUtil.uuid();
        DebitBean credit = new DebitBean(merchant_id, card_no, owner, cert_type, cert_no, phone, order_no, transtime, currency, total_fee, title, body, member_id, terminal_type, terminal_info, member_ip, seller_email, notify_url, token_id, version, "MD5", "");
        return credit;
    }
    
    public static ReaPayResultBean generateDebitResultBean(JSONObject json){
        String merchant_id=json.getString("merchant_id");
        String order_no=json.getString("order_no");
        String result_code=json.getString("result_code");
        String bind_id=json.getString("bind_id");
        String result_msg=json.getString("result_msg");
        String bank_name=json.getString("bank_name");
        String bank_code=json.getString("bank_code");
        ReaPayResultBean resultBean = new ReaPayResultBean(merchant_id, order_no, result_code, bind_id, result_msg, bank_name, bank_code,"");
        return resultBean;
    }
    
    public static CreditBean generateCreditBean(TradeBean trade){
        
        String merchant_id= ConsUtil.getInstance().cons.getReapay_quickpay_merchant_id();
        String version=  ConsUtil.getInstance().cons.getReapay_quickpay_version();
        String card_no= trade.getCardNo();
        String owner = trade.getAcctName();
        String cert_type= "01"; //默认
        String cert_no = trade.getCertId();
        String phone = trade.getMobile();
        String order_no = trade.getReaPayOrderNo();;
        String transtime = DateUtil.getTimeStamp();
        String cvv2= trade.getCvv2();
        String validthru= trade.getValidthru();
        String currency= "156"; //默认
        String title = StringUtil.isEmpty(trade.getGoodsName())?"no title":trade.getGoodsName();
        String body = StringUtil.isEmpty(trade.getGoodsDesc())?"no body":trade.getGoodsDesc();
        String member_id = trade.getMerUserId();// 用户id
        String terminal_type= "mobile"; //终端类型
        String terminal_info= "ddfa";   //终端标识
        String notify_url = ConsUtil.getInstance().cons.getReapay_quickpay_notify_url();
        String member_ip = StringUtil.isEmpty(trade.getMemberIP())?"0.0.0.0":trade.getMemberIP();// 用户IP
        String seller_email = ConsUtil.getInstance().cons.getReapay_quickpay_seller_email();
        // 金额
        String total_fee = trade.getAmount();
        String token_id = UUIDUtil.uuid();
        CreditBean credit = new CreditBean(merchant_id, card_no, owner, cert_type, cert_no, phone, cvv2, validthru, order_no, transtime, currency, total_fee, title, body, member_id, terminal_type, terminal_info, member_ip, seller_email, notify_url, token_id, version, "MD5", "");
        return credit;
    }
    
public static CreditBean generateTestCreditBean(TradeBean trade){
        
        String merchant_id= ConsUtil.getInstance().cons.getReapay_quickpay_merchant_id();
        String version=  ConsUtil.getInstance().cons.getReapay_quickpay_version();
        String card_no= trade.getCardNo();
        String owner = trade.getAcctName();
        String cert_type= "01"; //默认
        String cert_no = trade.getCertId();
        String phone = trade.getMobile();
        String order_no = trade.getReaPayOrderNo();;
        String transtime = DateUtil.getTimeStamp();
        String cvv2= trade.getCvv2();
        String validthru= trade.getValidthru();
        String currency= "156"; //默认
        String title = StringUtil.isEmpty(trade.getGoodsName())?"title":trade.getGoodsName();
        String body = StringUtil.isEmpty(trade.getGoodsDesc())?"body":trade.getGoodsDesc();
        String member_id = trade.getMerUserId();// 用户id
        String terminal_type= "mobile"; //终端类型
        String terminal_info= "ddfa";   //终端标识
        String notify_url = ConsUtil.getInstance().cons.getReapay_quickpay_notify_url();
        String member_ip= "192.168.1.156"; //用户IP
        String seller_email = ConsUtil.getInstance().cons.getReapay_quickpay_seller_email();
        // 金额
        String total_fee = trade.getAmount();
        String token_id = UUIDUtil.uuid();
        CreditBean credit = new CreditBean(merchant_id, card_no, owner, cert_type, cert_no, phone, cvv2, validthru, order_no, transtime, currency, total_fee, title, body, member_id, terminal_type, terminal_info, member_ip, seller_email, notify_url, token_id, version, "MD5", "");
        return credit;
    }
    
    public static ReaPayResultBean generateCreditResultBean(JSONObject json){
        String merchant_id=json.getString("merchant_id");
        String order_no=json.getString("order_no");
        String result_code=json.getString("result_code");
        String bind_id=json.getString("bind_id");
        String result_msg=json.getString("result_msg");
        String bank_name=json.getString("bank_name");
        String bank_code=json.getString("bank_code");
        ReaPayResultBean resultBean = new ReaPayResultBean(merchant_id, order_no, result_code, bind_id, result_msg, bank_name, bank_code,"");
        return resultBean;
    }
    
    public static PayBean generatePayBean(TradeBean trade){
        String merchant_id= ConsUtil.getInstance().cons.getReapay_quickpay_merchant_id();
        String order_no = trade.getReaPayOrderNo();;
        String check_code = trade.getIdentifyingCode();
        String version=  ConsUtil.getInstance().cons.getReapay_quickpay_version();
        String sign_type ="MD5";
        String sign="";
        PayBean payBean = new PayBean(merchant_id, order_no, check_code, version, sign_type, sign);
        return payBean;
    }
    
    public static ReaPayResultBean generatePayResultBean(JSONObject json){
        String merchant_id=json.getString("merchant_id");
        String order_no=json.getString("order_no");
        String result_code=json.getString("result_code");
        String bind_id=json.getString("bind_id");
        String result_msg=json.getString("result_msg");
        String bank_name=json.getString("bank_name");
        String bank_code=json.getString("bank_code");
        String trade_no = json.getString("trade_no");
        String bank_card_type = json.getString("bank_card_type");
        String card_last = json.getString("card_last");
        String phone = json.getString("phone");
        String total_fee = json.getString("total_fee");
        String status = json.getString("status");
        String timestamp = json.getString("timestamp");
        
        ReaPayResultBean resultBean = new ReaPayResultBean(merchant_id, order_no, result_code, bind_id, result_msg, bank_name, bank_code, phone, trade_no, bank_card_type, card_last, total_fee, status, timestamp);
        return resultBean;
    }
    
    
    
    
    
    public static SMSBean generateSMSBean(TradeBean tradeBean){
     
         String merchant_id = ConsUtil.getInstance().cons.getReapay_quickpay_merchant_id();;
         String order_no = tradeBean.getReaPayOrderNo();//使用受理订单号，保证唯一性
         String version = ConsUtil.getInstance().cons.getReapay_quickpay_version();;
         String sign_type="MD5";
         String sign="";;
         return new SMSBean(merchant_id, order_no, version, sign_type, sign);
    }
    
    public static SMSBean generateTestSMSBean(TradeBean tradeBean){
        
        String merchant_id = ConsUtil.getInstance().cons.getReapay_quickpay_merchant_id();;
        String order_no = tradeBean.getReaPayOrderNo();//使用受理订单号，保证唯一性
        String version = ConsUtil.getInstance().cons.getReapay_quickpay_version();;
        String sign_type="MD5";
        String sign="";;
        return new SMSBean(merchant_id, order_no, version, sign_type, sign);
   }
    
    public static ReaPayResultBean generateSMSResultBean(JSONObject json){
        String merchant_id=json.getString("merchant_id");
        String order_no=json.getString("order_no");
        String result_code=json.getString("result_code");
        String result_msg=json.getString("result_msg");
        String phone = json.getString("phone");
        
        ReaPayResultBean resultBean = new ReaPayResultBean(merchant_id, order_no, result_code, "", result_msg, "", "",phone);
        return resultBean;
    }
    
    public static QueryBean generateQueryBean(TradeBean tradeBean){
        
        String merchant_id = ConsUtil.getInstance().cons.getReapay_quickpay_merchant_id();;
        String order_no = tradeBean.getReaPayOrderNo();//使用受理订单号，保证唯一性
        String version = ConsUtil.getInstance().cons.getReapay_quickpay_version();;
        String sign_type="MD5";
        String sign="";
        return new QueryBean(merchant_id, order_no, version, sign_type, sign);
   }
   public static ReaPayResultBean generateQueryResultBean(JSONObject json){
       String merchant_id=json.getString("merchant_id");
       String order_no=json.getString("order_no");
       String result_code=json.getString("result_code");
       String result_msg=json.getString("result_msg");
       String trade_no = json.getString("trade_no");
       String total_fee = json.getString("total_fee");
       String status = json.getString("status");
       String timestamp = json.getString("timestamp");
       ReaPayResultBean resultBean = new ReaPayResultBean(merchant_id, order_no, result_code, "", result_msg, "", "", "", trade_no, "", "", total_fee, status, timestamp);
       return resultBean;
   }
   
   
   
   public static BindBean generateBindBean(TradeBean trade){
       String merchant_id = ConsUtil.getInstance().cons.getReapay_quickpay_merchant_id();
       String member_id = trade.getMerUserId();// 用户id
       String version = ConsUtil.getInstance().cons.getReapay_quickpay_version();
       String bind_id = trade.getBindCardId(); 
       String order_no = trade.getReaPayOrderNo();;
       String transtime = DateUtil.getTimeStamp();
       String currency = "156"; // 默认
       // 金额
       String total_fee = trade.getAmount();
       String title = StringUtil.isEmpty(trade.getGoodsName())?"no title":trade.getGoodsName();
       String body = StringUtil.isEmpty(trade.getGoodsDesc())?"no body":trade.getGoodsDesc();
       String terminal_type = "mobile";// 终端类型
       String terminal_info = "djddh"; // 终端标识
       String member_ip = StringUtil.isEmpty(trade.getMemberIP())?"0.0.0.0":"0.0.0.0";// 用户IP
       String seller_email = ConsUtil.getInstance().cons.getReapay_quickpay_seller_email();
       String notify_url = ConsUtil.getInstance().cons.getReapay_quickpay_notify_url();
       String token_id = UUIDUtil.uuid();
       BindBean bindBean = new BindBean(merchant_id, member_id, bind_id, order_no, transtime, currency, total_fee, title, body, terminal_type, terminal_info, member_ip, seller_email, notify_url, token_id, version, "MD5", "");
       return bindBean;
   }
   
   public static BindBean generateTestBindBean(TradeBean trade){
       String merchant_id = ConsUtil.getInstance().cons.getReapay_quickpay_merchant_id();
       String member_id = trade.getMerUserId();// 用户id
       String version = ConsUtil.getInstance().cons.getReapay_quickpay_version();
       String bind_id = trade.getBindCardId(); 
       String order_no = trade.getReaPayOrderNo();;
       String transtime = DateUtil.getTimeStamp();
       String currency = "156"; // 默认
       // 金额
       String total_fee = trade.getAmount();
       String title = trade.getGoodsName();
       String body = StringUtil.isEmpty(trade.getGoodsDesc())?"body":trade.getGoodsDesc();
       String terminal_type = "";// 终端类型
       String terminal_info = ""; // 终端标识
       String member_ip = "";// 用户IP
       String seller_email = ConsUtil.getInstance().cons.getReapay_quickpay_seller_email();
       String notify_url = ConsUtil.getInstance().cons.getReapay_quickpay_notify_url();
       String token_id = UUIDUtil.uuid();
       BindBean bindBean = new BindBean(merchant_id, member_id, bind_id, order_no, transtime, currency, total_fee, title, body, terminal_type, terminal_info, member_ip, seller_email, notify_url, token_id, version, "MD5", "");
       return bindBean;
   }
   
   public static ReaPayResultBean generateBindResultBean(JSONObject json){
       String merchant_id=json.getString("merchant_id");
       String order_no=json.getString("order_no");
       String result_code=json.getString("result_code");
       String result_msg=json.getString("result_msg");
       String bank_name=json.getString("bank_name");
       String bank_code=json.getString("bank_code");
       ReaPayResultBean resultBean = new ReaPayResultBean(merchant_id, order_no, result_code, "", result_msg, bank_name, bank_code,"");
       return resultBean;
   }
   
   public static ReaPayResultBean generateAsyncResultBean(String respMsg){
       JSONObject json= JSON.parseObject(respMsg);
       String merchant_id= json.getString("merchant_id");
       String order_no   = json.getString("order_no");
       String result_code= json.getString("result_code");
       String result_msg = json.getString("result_msg");
       String trade_no   = json.getString("trade_no");
       String total_fee  = json.getString("total_fee");
       String status     = json.getString("status");
       ReaPayResultBean resultBean = new ReaPayResultBean(merchant_id, order_no, result_code, "", result_msg, "", "", "", trade_no, "", "", total_fee, status, "");
       
       return resultBean;
   }
}
