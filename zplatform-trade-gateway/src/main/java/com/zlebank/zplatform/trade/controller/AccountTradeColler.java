package com.zlebank.zplatform.trade.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.trade.bean.gateway.BailRechargeOrderBean;
import com.zlebank.zplatform.trade.bean.gateway.BailWithdrawOrderBean;
import com.zlebank.zplatform.trade.bean.gateway.TransferOrderBean;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.service.IAccoutTradeService;

@Controller
@RequestMapping("/accountTrade")
public class AccountTradeColler {
	private static final Log log = LogFactory.getLog(AccountTradeColler.class);
	@Autowired
	private IAccoutTradeService accountTradeService;
	/***
     * 收银台生成订单
     * @param order
     * @param httpSession
     * @param request
     * @return
     */
    @RequestMapping("/order.htm")
    public ModelAndView pay(TransferOrderBean order,HttpSession httpSession,HttpServletRequest request) {
        log.info("receive web message(json):" + JSON.toJSONString(order));
        Map<String, Object> model = new HashMap<String, Object>();
        try {
        	this.accountTradeService.transfer(order);
        } catch (Exception e) {
            e.printStackTrace();
            model.put("errMsg", "订单信息错误，请重新提交");
            model.put("errCode", "RC99");
        }
        return new ModelAndView("/erro_gw", model);
    }
    
    /***
     * 收银台生成订单
     * @param order
     * @param httpSession
     * @param request
     * @return
     */
    @RequestMapping("/bailRechargeOrder.htm")
    public ModelAndView bailRechargeOrder(BailRechargeOrderBean order,HttpSession httpSession,HttpServletRequest request) {
        log.info("receive web message(json):" + JSON.toJSONString(order));
        Map<String, Object> model = new HashMap<String, Object>();
        try {
        	this.accountTradeService.bailAccountRecharge(order);
        } catch (Exception e) {
            e.printStackTrace();
            model.put("errMsg", "订单信息错误，请重新提交");
            model.put("errCode", "RC99");
        }
        return new ModelAndView("/erro_gw", model);
    }
    
    
    /***
     * 收银台生成订单
     * @param order
     * @param httpSession
     * @param request
     * @return
     */
    @RequestMapping("/bailWidthdrawOrder.htm")
    public ModelAndView bailWidthdrawOrder(BailWithdrawOrderBean order,HttpSession httpSession,HttpServletRequest request) {
        log.info("receive web message(json):" + JSON.toJSONString(order));
        Map<String, Object> model = new HashMap<String, Object>();
        try {
        	this.accountTradeService.bailAccountWithdraw(order);
        } catch (Exception e) {
            e.printStackTrace();
            model.put("errMsg", "订单信息错误，请重新提交");
            model.put("errCode", "RC99");
        }
        return new ModelAndView("/erro_gw", model);
    }
}
