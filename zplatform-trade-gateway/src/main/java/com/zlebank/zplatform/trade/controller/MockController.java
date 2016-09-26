package com.zlebank.zplatform.trade.controller;

import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.zip.Deflater;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zlebank.zplatform.commons.utils.Base64Utils;
import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.commons.utils.RSAUtils;
import com.zlebank.zplatform.member.service.MerchMKService;
//import com.zlebank.zplatform.specification.utils.Base64Utils;
import com.zlebank.zplatform.trade.insteadPay.message.BaseMessage;
import com.zlebank.zplatform.trade.insteadPay.message.InsteadPayFile;
import com.zlebank.zplatform.trade.insteadPay.message.InsteadPay_Request;
import com.zlebank.zplatform.trade.insteadPay.message.RealnameAuthFile;
import com.zlebank.zplatform.trade.insteadPay.message.RealnameAuth_Request;
import com.zlebank.zplatform.trade.service.InsteadPayService;
import com.zlebank.zplatform.trade.service.MerchWhiteListServiceExt;
import com.zlebank.zplatform.trade.service.RealNameAuthService;

/**
 * 
 * 代付Mock
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月20日 下午12:24:50
 * @since
 */
/*@Controller
@RequestMapping("mock")*/
public class MockController {

    @SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(MockController.class);
    private static ResourceBundle RESOURCE = ResourceBundle.getBundle("test-config");

    ////////////////////178///////////////////////
    /**私钥**/
   public static final String PRIVATE_KEY = RESOURCE.getString("private-key");//"MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIUoFvVggTt5TA4k2cEMDFprtOQggMgLrQLW9n+Pwx/S1DeUzqUStbArtfiNWvWxkjWm/LrOfpLXhG8zSRZBQMEcANmkiJvQDCb8LMsqbGiUSvj2ytIQgAh5IcbOQA8rkzOGJB1XTO15u0odI5nhtXeKGhwEWpetlMboxh/uMqXDAgMBAAECgYAlrN0qGl8WY9CYI+cUzwLjxpvM/EFT2LHgHtBp8VRFOdLf0xPQ8wVDTmrTY7N71v1GfvP9KxX3GFGZYQpp6JaYZGXcSWON8Mzh0Prixr67ShumXY0vxV8kaI3g7yIVLpH8yDdtCYP2mLcopC2FL3LwpeMuaIPu+I8K3sVmFN7CUQJBAMI6WmlQn0M3uyNpEbKgdfOLGTO5v0qm1aZ233H0Cw74Bl9PpMVYeuh2reLEMxuY5057H1fsUjTxmGZHdh6nLbkCQQCvgW5ihp6Y22YCQUN2JZL/apbkeA9MxfFKjYErk4L9yhVcjx8G4DFlG1cHadVmVuCnXQvyaElpsw1n+GB4iw1bAkAw1nlrZ9FUFoxgwAeqMbzW60//+KHIBKFORS+0OJgbQHRhvOYClVf6YfUhQxJSyyTGUCE2e37EP0eB2FA0LvdJAkAM57SJNCLnVIKsucXPIzYq59iOljFx0MBMXhlYbfFc3gYyFygN5mBbceY1WlfhvZOpWtMtEPQM/KiIs5/MXVUvAkBEvwmSnqoHdGpGSmeaxhqFdOQiumJ59EYvVxrvidHEzbT5o2w85GPV04bhLFwgq9ZsmaScrxb7VMh/7OSTXvHn";
   /**平台公钥**/
   public static final String LOCAL_PUB = RESOURCE.getString("local-pub");//"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCADgeUl14xgaVtHOcrO5I8XFxY4+jtS9O/TzYjOrXx4HOqLaOx5iGqDz8UDRu8U7uDHFNHRnnOFcBj4iQKpoM+l2N9ojZp6UC294q2Hw2NncSP1Bq2bpDYsB6gon6Ig6SG+3A3fCPvzCEtp5K4XQgMJWxd/ybXV/U+LbPmCc9EzQIDAQAB";
   /**商户号**/
   public static final String MER_ID = RESOURCE.getString("mer-id");//"200000000000593";

    @Autowired
    private MerchMKService merchMKService;
    
    @Autowired
    private InsteadPayService insteadPayService;
    
    @Autowired
    private RealNameAuthService realNameAuthService;
    
    @Autowired
    private MerchWhiteListServiceExt merchWhiteListService;
    
    // Mock 地址
    @RequestMapping("/index.htm")
    public ModelAndView mock(String data, HttpSession httpSession, HttpServletResponse response,HttpServletRequest request) throws HttpException, IOException{
            return new ModelAndView("mockPage", null);
    }


    /**
     * 生成代付
     * @param httpSession
     * @param req
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/21")
    @ResponseBody
    private Map<String, Object> createInsteadPay(HttpSession httpSession, HttpServletRequest req , HttpServletResponse response) throws Exception {
        InsteadPay_Request request = new InsteadPay_Request();
        commSetter(request, "21");
        request.setChannelType("00");
//        request.setAccessType("0");
        request.setBatchNo(DateUtil.getCurrentTime().substring(2));
        request.setTxnTime(DateUtil.getCurrentDateTime());
        request.setTotalQty("2");
        request.setTotalAmt("200");
        // 设定明细文件
        InsteadPayFile file1 = createPayFile("20");
        InsteadPayFile file2 = createPayFile("180");
        List<InsteadPayFile> files = new ArrayList<InsteadPayFile>();
        files.add(file1);
        files.add(file2);
        //////////////////////////////////////////////////////
        // 转为JSON数组
        JSONArray jsonContent = JSONArray.fromObject(files);
//        System.out.println(jsonContent.toString());
        // 压缩前字节
        byte[] input =jsonContent.toString().getBytes("UTF-8");
        // 压缩后字节初始化
        byte[] output = new byte[10000];
        Deflater compresser = new Deflater();
        compresser.setInput(input);
        compresser.finish();
        // 压缩并返回压缩后的字节数组长度
        int compressedDataLength = compresser.deflate(output);// asdfasddddddddsdddddddddddddddddddddddddddddddddddddddddddddddddddddddd
        // 压缩后实际字节数组初始化
        byte[] convert = new byte[compressedDataLength];
        // 字节数组截取
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(output));
        inputStream.read(convert);
        // 字节数组->BASE64编码
        String fileContent = Base64Utils.encode(convert);
//        System.out.println("DEFLATE："+ fileContent);
        //////////////////////////////////////////////////////

//        request.setFileContent(fileContent);

        JSONObject jsonData = JSONObject.fromObject(request);
        jsonData.put("fileContent", fileContent);
        String beforeSign = jsonData.toString();
        String signedData = RSAUtils.sign(beforeSign.getBytes(), PRIVATE_KEY);
//        System.out.println("加签前："+beforeSign);
        jsonData.put("signature", signedData);
//        System.out.println("加签结果："+signedData);
        System.out.println("完整json："+jsonData.toString());
        
        Map<String, Object> rtn  = sendData(jsonData.toString(), req);
        return rtn;
    }
    
    /**
     * 生成实名认证
     * @throws Exception 
     */
    @RequestMapping("/72")
    @ResponseBody
    private Map<String, Object> createRealNameAuth(RealnameAuth_Request param,  RealnameAuthFile authFile, HttpSession httpSession, HttpServletRequest req , HttpServletResponse response) throws Exception {
        RealnameAuth_Request request = new RealnameAuth_Request();
        commSetter(request, "72");
        request.setChannelType("00");
        request.setAccessType("0");
        request.setOrderId(String.valueOf(System.currentTimeMillis()));
        request.setTxnTime("20151126121212");
        // 加密信息
        RealnameAuthFile file = new RealnameAuthFile();
        file.setCardNo("6226091212413805");
        file.setCardType("1");
        file.setCertifId("13112219880904243X");
        file.setCertifTp("01");
        file.setCustomerNm("鲁晓帅");
        file.setCvn2("");
        file.setExpired("");
        file.setPhoneNo("13426342943");
        JSONObject encyptData = JSONObject.fromObject(file);
        byte[] strs = RSAUtils.encryptByPublicKey(encyptData.toString().getBytes(), LOCAL_PUB);

        // 加签生成
        JSONObject jsonData = JSONObject.fromObject(request);
        jsonData.put("encryptData", Base64Utils.encode(strs));
        String beforeSign = jsonData.toString();
        String signedData = RSAUtils.sign(beforeSign.getBytes(), PRIVATE_KEY);
        jsonData.put("signature", signedData);
        System.out.println("完整json："+jsonData.toString());
        
        Map<String, Object> rtn  = sendData(jsonData.toString(), req);
        return rtn;
        
        // 复制到ClipBoard
//        sysc.setContents(new StringSelection(jsonData.toString()), null);
    }

    /**
     * 代付明细
     * @param amt 
     * @return
     */
    private InsteadPayFile createPayFile( String amt) {
        InsteadPayFile file1 = new InsteadPayFile();
        file1.setMerId(MER_ID);
        Random random = new Random();
        file1.setOrderId("IP"+System.currentTimeMillis()+random.nextInt(100));
        file1.setCurrencyCode("156");
        file1.setAmt(amt);
        file1.setBizType("000001");
        file1.setAccType("01");
        file1.setAccNo("6226091212413805");
        file1.setAccName("鲁晓帅");
        file1.setBankCode("123456789012");
        file1.setIssInsProvince("");
        file1.setIssInsCity("");
        file1.setIssInsName("");
        file1.setCertifTp("01");
        file1.setCertifId("131122198809042433");
        file1.setPhoneNo("13426342943");
        file1.setBillType("01");
        file1.setNotes("这是一个测试");
        return file1;
    }

    /**
     * 私钥解密
     * @throws Exception 
     */
    @SuppressWarnings("unused")
	private void privateOpen() throws Exception {
        
        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOYQmI3QralUVuXxGJmLyvTAtsot2/TWLCIMUa8eG5AvbXBwA8Y+t+oMChL8uZH2/JHg120wTm1jJaEqlh6HK5K0lvGdQYdxYkd1g42cwEzgMr+TENrCONleLHe1AGj3sFOnAexM0m3lUw3KfcIzsjXX2wJB4Yw5zNzLGtehD2flAgMBAAECgYB9fBvZbjLqowfqz4Adb/Q5X0vUJwNMHe1gfNuo6oEaOeQ1acOFQ/xIelycUqBdDxxf7QVTlv+QBn4l0/ahnBSVHYfnQAJuuu8UsOL1Hyw/u2brc+lUOCVRDSMEHHHttHQ4Ke+j5+Yrwl5oB4H7wTfWEOYqin/9nBnSmg1nUb3KAQJBAPa9m1YAKX3kIu1AaDUhhvW2TalWxUHXBbpQ5EkXqTMt+ZBuaXSq36ugP8t1cf5TTaj7ehOqvi4+OeqSImq8o0ECQQDussmYf8+rD68q5C7FcTRLLYvVCdlynuvX2cp0lrugHUD2y1R/XSuIu66oUbm0hXwUfO87VJUJr1TNQT07cW+lAkEA81790owCYPwfPyiiIJXbSozweTDvDAwMyEN1iGrgJ20XldByD/Ni/yPnCMXlFFgSZ7T0KmXlhDM4aRiO/fzmgQJAWgYqDA7gNqbirK2EikiorVXPMBjX3ufbEPx947zZpIiD8NA83RA5lAcc1zSDcncJlfyMiXbcAtwSgpsBSgL9VQJBAOesp37Cybow7cN2KIlkTB3MtbqUXlajOhMTZzfj6PYOe+bL/oNugUbILNlwm7x1J/TGQHGPn2W7qlaTpcF45IE=";
        String encryptedData = "TiVZeHBjqo02VzcqjRzCji5eog5KY11R8J9aRIF/WXZvSRZhe5/PRDao3/JbTJbYht3QicSjsnXltT4puap40DfDxHI3JCTIYwKBD+3Pv2xuxP7PkFL1EY+w72sDYII6vMOVRjPl2vY4BnZPDY6qK4l+MiwGQc4/Z+65TyedZiQIrP+DzUP0e3YAntjHU+GsgAyWRyV5Cf5Bw5VH3aRtnnxUtsyqI3XlZ8H/eQMbS6nsTemnBUNJuGXtf36rwL5obNKHVN2w5qiDc89ZxNWu42co83SLMAsJRZQQJgocoltJUrAeOnLkZkY+RAdkWnfmkO9FqsgO0gOQTt2NVoOkC2r/mV6p/LmNPGdTkVJHMbO+6RgqVcpU59kfZ8YuJauneLu6imII4ZsH2K41KnHQDcTQMK0e+2B92nvuFJEA7+7MVAmh9+5B4gIMMuDJq+/UymY/ncEnTDeDAipqUsy/PTenC9sbDjSvD6nAGw4CGuA2FO/5feDHxJKvnNglDLc=";
        
        byte[] strs = RSAUtils.decryptByPrivateKey(Base64Utils.decode(encryptedData), privateKey);
        String words = new String(strs);
        System.out.println("解密后的明文是："+words);
    }



    /**
     * 私钥加签
     * @throws Exception 
     */
    @SuppressWarnings("unused")
	private void r20151021() throws Exception {
        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKLLsnVlaexD2Xt/7N2EJ6yUNj6Jrv7SZhyrfBoUzX+0LJ62u1Zrok71lBzyY9OscqO+te1anVuKB+1blv4eYuKdIM0vd6AkIYz+AQo+ij4P1uOKIll8wf1Rlshkx5PYAMfM0d6zaqRiLG4pFGn5hLtC/srK40pieX2QLR6Ecgu1AgMBAAECgYBeSKL+otGJ6HHYuOoyEQv5Q413Ar67DpfdGuQxJsMtpugAs8wj7Qp/uHMjoK54NXPfqeArq0NfdL3KljUI0AhsTuU3J83ZVMXKiekXgDzhkZyW9mOwXKMUF0rKlM/yPewQ+tN54Nunew30qMWO5iGg+6gi8uSFNIsWO5Yl/zPisQJBAPDmn59F/JJEb7efD9Hd2IGoaD0pC3fxn33LYXNABs+6kG4e9AjzPPPe84z/mAy7BDaV3Enk9mI0eUhvJPKoaqsCQQCs/9YeZDBvM79Peh1TazsuosFsymyjJ7fklTCA2YuXAKVXmbbBvRZlVgh4x0I9Lj5Il3PB/Q85tIAl4aFCZ2MfAkEAoDAK9oM4Vx7Q1t4bS+dj/5u3bOvtJ13xO1nRPCzYGqupPMCyb30nC9c2RozzU4vMWmu+ZxisSVMxTtwxSnZ5LwJBAKvWf4+2gumiI/BE4qY+iA2durVeKCSzyhRyIDiMXfxCtvlZhM/SC1Hi8A1QFzqAGXuvfFr/C0WNRhDamnxEB48CQGwPxQ91JJkzPXPxlbiIINYNR8R+wJWP3o9MN+aBhMGifjDQUHSDB19ScVAyK0IG0j3YKzUL3v5yAgnfnFb56rM=";
        String strs = RSAUtils.sign("luxiaoshuai".getBytes(), privateKey);
        System.out.println("加签后是："+strs);
    }

    
    /**
     * 共通属性设定
     * @param request
     * @param type
     */
    private void commSetter(BaseMessage request, String type) {
        request.setVersion("v1.0");
        request.setEncoding("01");
        request.setSignature("");
        request.setSignMethod("01");
        request.setTxnType(type);
        request.setTxnSubType("00");
        request.setBizType("000202");
        request.setMerId(MER_ID);
    }
    
    /**
     * 发送数据
     * @param string
     * @param request
     * @return
     * @throws HttpException
     * @throws IOException
     */
    private Map<String, Object> sendData(String string, HttpServletRequest request) throws HttpException, IOException {
        String path = request.getSession().getServletContext().getRealPath(""); 
        System.out.println(path);
//        String head = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        String head = RESOURCE.getString("server-addr");
        String rtnJson = "";
        BufferedReader br = null;
        PostMethod post = null;
        post = new PostMethod(head+"/zplatform-trade/interface/insteadPayIndex.htm");
        post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        post.addParameter("data", string);
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
            rtnJson = json.toString();
//            rtnJson = json.toString().replace(",", ",<br/>").replace("respCode", "<font color=red>respCode</font>");
        } else {
            System.out.println("【失败】状态返回不是200，-->"+status);
        }
        Map<String,Object> rtn = new HashMap<String,Object>();
        rtn.put("data", rtnJson);
        return  rtn;
    }
}
