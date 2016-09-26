/* 
 * IReaPayTradeService.java  
 * 
 * version TODO
 *
 * 2015年9月16日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.reapal.BindBean;
import com.zlebank.zplatform.trade.bean.reapal.CreditBean;
import com.zlebank.zplatform.trade.bean.reapal.DebitBean;
import com.zlebank.zplatform.trade.bean.reapal.PayBean;
import com.zlebank.zplatform.trade.bean.reapal.QueryBean;
import com.zlebank.zplatform.trade.bean.reapal.SMSBean;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月16日 下午12:31:00
 * @since 
 */
public interface IReaPayTradeService {
    /**
     * 借记卡签约
     * @return
     */
    ResultBean debitCardSign(DebitBean debitBean);
    
    /**
     * 信用卡签约
     * @return
     */
    ResultBean creditCardSign(CreditBean creditBean);
    
    /**
     * 查询绑卡列表
     * @return
     */
    ResultBean bindListQuery();
    
    /**
     * 已经绑卡的银行卡签约
     * @return
     */
    ResultBean bindCard(BindBean bindBean);
    
    /**
     * 重发短信验证码
     * @return
     */
    ResultBean reSendSms(SMSBean smsBean);
    
    /**
     * 确认支付接口
     * @return
     */
    ResultBean submitPay(PayBean payBean);
    
    /**
     * 支付结果查询接口
     * @return
     */
    ResultBean searchPayResult(QueryBean queryBean);
    
    /**
     * 解绑卡接口
     * @return
     */
    ResultBean cannelCard();
    
    /**
     * 退款
     * @return
     */
    ResultBean refund();
    
    /**
     * 卡信息查询接口
     * @return
     */
    ResultBean queryBankCard();
}
