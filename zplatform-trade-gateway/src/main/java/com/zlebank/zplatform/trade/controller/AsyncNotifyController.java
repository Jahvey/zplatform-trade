/* 
 * AsyncNotifyController.java  
 * 
 * version TODO
 *
 * 2016年5月25日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zlebank.zplatform.wechat.qr.service.WeChatQRService;
import com.zlebank.zplatform.wechat.service.WeChatService;
import com.zlebank.zplatform.wechat.wx.WXApplication;
import com.zlebank.zplatform.wechat.wx.bean.PayResultBean;

/**
 * 微信支付完成后异步通知方法
 *
 * @author guojia
 * @version
 * @date 2016年5月25日 下午7:13:11
 * @since 
 */
@Controller
@RequestMapping("/notify")
public class AsyncNotifyController {

	private static final Log log = LogFactory.getLog(AsyncNotifyController.class);
	
	@Autowired
	private WeChatService weChatService; 
	@Autowired
	private WeChatQRService weChatQRService;
	/***
	 * 微信支付完成异步通知方法
	 * @param httpSession
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/wxResult.htm")
    public void wxResult(HttpSession httpSession, HttpServletResponse response,HttpServletRequest request) throws Exception{
        InputStream is = null;
        PrintWriter responseStream= null;
		try {
        	responseStream = getPrintWriter(response);
            StringBuffer sb = new StringBuffer();
            //获取HTTP请求的输入流
            is = request.getInputStream();
            //已HTTP请求输入流建立一个BufferedReader对象
            BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            //1.读取HTTP请求内容
            String buffer = null;
            while ((buffer = br.readLine()) != null) {
                //在页面中显示读取到的请求参数
                sb.append(buffer);
            }
            log.info("【收到微信异步通知XML数据】"+ sb.toString());
            //2.将xml数据进行解析
            WXApplication app = new WXApplication();
            PayResultBean result = app.parseResultXml(sb.toString());
            //3.调wechat接口【异步通知处理】
            weChatService.asyncTradeResult(result);
            log.info("[收到wechat接口asyncTradeResult的结果]"+result.getReturn_code());
            //4.获得异步通知结果
            String responseXml = null;
            if ("SUCCESS".equals(result.getReturn_code()) && "SUCCESS".equals(result.getResult_code())) {
                responseXml = app.createResponseResultXml("SUCCESS","OK");
            } else {
                responseXml = app.createResponseResultXml("FAIL", result.getReturn_code() == null ? result.getResult_code() : result.getReturn_code());
            }
            responseStream.write(responseXml);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally{
        	//关闭流
        	if(is!=null){
        		is.close();
        	}
        	if(responseStream!=null){
        		  responseStream.flush();
        		  responseStream.close();
        	}
          
        }
    }
	
	@RequestMapping("/wechatQRResult.htm")
    public void wechatQRResult(HttpSession httpSession, HttpServletResponse response,HttpServletRequest request) throws Exception{
        InputStream is = null;
        PrintWriter responseStream= null;
		try {
        	responseStream = getPrintWriter(response);
            StringBuffer sb = new StringBuffer();
            //获取HTTP请求的输入流
            is = request.getInputStream();
            //已HTTP请求输入流建立一个BufferedReader对象
            BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            //1.读取HTTP请求内容
            String buffer = null;
            while ((buffer = br.readLine()) != null) {
                //在页面中显示读取到的请求参数
                sb.append(buffer);
            }
            log.info("【收到微信异步通知XML数据】"+ sb.toString());
            //2.将xml数据进行解析
            com.zlebank.zplatform.wechat.qr.wx.WXApplication app = new com.zlebank.zplatform.wechat.qr.wx.WXApplication();
            com.zlebank.zplatform.wechat.qr.wx.bean.PayResultBean result = app.parseResultXml(sb.toString());
            //3.调wechat接口【异步通知处理】
            weChatQRService.asyncTradeResult(result);
            log.info("[收到wechat接口asyncTradeResult的结果]"+result.getReturn_code());
            //4.获得异步通知结果
            String responseXml = null;
            if ("SUCCESS".equals(result.getReturn_code()) && "SUCCESS".equals(result.getResult_code())) {
                responseXml = app.createResponseResultXml("SUCCESS","OK");
            } else {
                responseXml = app.createResponseResultXml("FAIL", result.getReturn_code() == null ? result.getResult_code() : result.getReturn_code());
            }
            responseStream.write(responseXml);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally{
        	//关闭流
        	if(is!=null){
        		is.close();
        	}
        	if(responseStream!=null){
        		  responseStream.flush();
        		  responseStream.close();
        	}
          
        }
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
