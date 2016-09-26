/* 
 * MemberController.java  
 * 
 * version TODO
 *
 * 2015年9月22日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.controller;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*import com.zlebank.zplatform.specification.RequestType;
import com.zlebank.zplatform.specification.SpecificationProcessor;
import com.zlebank.zplatform.specification.SpecificationProcessorFactory;
import com.zlebank.zplatform.specification.exception.SpecificationException;
import com.zlebank.zplatform.specification.message.Message;
import com.zlebank.zplatform.specification.message.Response;
import com.zlebank.zplatform.specification.parser.SpecificationParser;
import com.zlebank.zplatform.specification.utils.LogUtil;*/
/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月22日 上午11:14:52
 * @since 
 */
/*@Controller*/
public class MemberController {
private final static Log log = LogFactory.getLog(MemberController.class);
    
   /* @Autowired
    private SpecificationParser specificationParser;
    private final static String FILED_REQUEST_TYPE = "requestType";
    private  final static String ENCODE_CHARSET="utf-8";
    @Autowired
    SpecificationProcessorFactory specificationProcessorFactory;
    
    
    @RequestMapping(value="/sign")  
    public void demo(@RequestParam("data") String data,HttpServletResponse res) throws UnsupportedEncodingException {
        Response response;
        try{
            if(log.isInfoEnabled()){
                log.info("revice request:\n"+LogUtil.formatLogHex(data.getBytes()));
            } 
            JSONObject jsonRquest = JSONObject.fromObject(data); ;
            RequestType requestType = RequestType.formatValue(jsonRquest.getJSONObject("head")
                    .getString(FILED_REQUEST_TYPE));
            Message request;

            request = specificationParser.parse(jsonRquest, requestType, true);

            SpecificationProcessor processor = specificationProcessorFactory
                    .getProcessor(requestType);
            response = processor.process(request);
           
        } catch(JSONException e){
            e.printStackTrace();
            response = new Response();
            response.setResCode("10");
            response.setResDes();
        }catch(SpecificationException e){
            e.printStackTrace();
            response = new Response();
            response.setResCode("20");
            response.setResDes( e.getMessage());
        }  catch (Exception e) {
            e.printStackTrace();
            response = new Response();
            response.setResCode("40");
            response.setResDes();
        }
        String resStr = specificationParser.parse(response).toString();
        if(log.isInfoEnabled()){
            log.info("send response:\n"+LogUtil.formatLogHex(resStr.getBytes()));
        }
        resStr = URLEncoder.encode(resStr,ENCODE_CHARSET);
        
        try {
            res.getWriter().write(resStr);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/
}
