/* 
 * Test.java  
 * 
 * version v1.0
 *
 * 2015年10月9日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.controller;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.zlebank.zplatform.commons.utils.Base64Utils;
import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.commons.utils.RSAUtils;
import com.zlebank.zplatform.trade.insteadPay.message.BaseMessage;
import com.zlebank.zplatform.trade.insteadPay.message.InsteadPayFile;
import com.zlebank.zplatform.trade.insteadPay.message.InsteadPayQuery_Request;
import com.zlebank.zplatform.trade.insteadPay.message.InsteadPay_Request;
import com.zlebank.zplatform.trade.insteadPay.message.MerWhiteList_Request;
import com.zlebank.zplatform.trade.insteadPay.message.RealnameAuthFile;
import com.zlebank.zplatform.trade.insteadPay.message.RealnameAuth_Request;


/**
 * 
 * 代付报文生成
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月26日 上午10:06:15
 * @since
 */
public class CreateInsteadPayMassage {
    
    private Clipboard sysc = Toolkit.getDefaultToolkit().getSystemClipboard();
    
    private static ResourceBundle RESOURCE = ResourceBundle.getBundle("test-config");
    
    ////////////////////245///////////////////////
    /**私钥**/
   public static final String PRIVATE_KEY = RESOURCE.getString("private-key");//"MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIUoFvVggTt5TA4k2cEMDFprtOQggMgLrQLW9n+Pwx/S1DeUzqUStbArtfiNWvWxkjWm/LrOfpLXhG8zSRZBQMEcANmkiJvQDCb8LMsqbGiUSvj2ytIQgAh5IcbOQA8rkzOGJB1XTO15u0odI5nhtXeKGhwEWpetlMboxh/uMqXDAgMBAAECgYAlrN0qGl8WY9CYI+cUzwLjxpvM/EFT2LHgHtBp8VRFOdLf0xPQ8wVDTmrTY7N71v1GfvP9KxX3GFGZYQpp6JaYZGXcSWON8Mzh0Prixr67ShumXY0vxV8kaI3g7yIVLpH8yDdtCYP2mLcopC2FL3LwpeMuaIPu+I8K3sVmFN7CUQJBAMI6WmlQn0M3uyNpEbKgdfOLGTO5v0qm1aZ233H0Cw74Bl9PpMVYeuh2reLEMxuY5057H1fsUjTxmGZHdh6nLbkCQQCvgW5ihp6Y22YCQUN2JZL/apbkeA9MxfFKjYErk4L9yhVcjx8G4DFlG1cHadVmVuCnXQvyaElpsw1n+GB4iw1bAkAw1nlrZ9FUFoxgwAeqMbzW60//+KHIBKFORS+0OJgbQHRhvOYClVf6YfUhQxJSyyTGUCE2e37EP0eB2FA0LvdJAkAM57SJNCLnVIKsucXPIzYq59iOljFx0MBMXhlYbfFc3gYyFygN5mBbceY1WlfhvZOpWtMtEPQM/KiIs5/MXVUvAkBEvwmSnqoHdGpGSmeaxhqFdOQiumJ59EYvVxrvidHEzbT5o2w85GPV04bhLFwgq9ZsmaScrxb7VMh/7OSTXvHn";
   /**平台公钥**/
   public static final String LOCAL_PUB = RESOURCE.getString("local-pub");//"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCADgeUl14xgaVtHOcrO5I8XFxY4+jtS9O/TzYjOrXx4HOqLaOx5iGqDz8UDRu8U7uDHFNHRnnOFcBj4iQKpoM+l2N9ojZp6UC294q2Hw2NncSP1Bq2bpDYsB6gon6Ig6SG+3A3fCPvzCEtp5K4XQgMJWxd/ybXV/U+LbPmCc9EzQIDAQAB";
   /**商户号**/
   public static final String MER_ID = RESOURCE.getString("mer-id");//"200000000000593";
    
    ////////////////////178///////////////////////
//    /**私钥**/
//   public static final String PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIUoFvVggTt5TA4k2cEMDFprtOQggMgLrQLW9n+Pwx/S1DeUzqUStbArtfiNWvWxkjWm/LrOfpLXhG8zSRZBQMEcANmkiJvQDCb8LMsqbGiUSvj2ytIQgAh5IcbOQA8rkzOGJB1XTO15u0odI5nhtXeKGhwEWpetlMboxh/uMqXDAgMBAAECgYAlrN0qGl8WY9CYI+cUzwLjxpvM/EFT2LHgHtBp8VRFOdLf0xPQ8wVDTmrTY7N71v1GfvP9KxX3GFGZYQpp6JaYZGXcSWON8Mzh0Prixr67ShumXY0vxV8kaI3g7yIVLpH8yDdtCYP2mLcopC2FL3LwpeMuaIPu+I8K3sVmFN7CUQJBAMI6WmlQn0M3uyNpEbKgdfOLGTO5v0qm1aZ233H0Cw74Bl9PpMVYeuh2reLEMxuY5057H1fsUjTxmGZHdh6nLbkCQQCvgW5ihp6Y22YCQUN2JZL/apbkeA9MxfFKjYErk4L9yhVcjx8G4DFlG1cHadVmVuCnXQvyaElpsw1n+GB4iw1bAkAw1nlrZ9FUFoxgwAeqMbzW60//+KHIBKFORS+0OJgbQHRhvOYClVf6YfUhQxJSyyTGUCE2e37EP0eB2FA0LvdJAkAM57SJNCLnVIKsucXPIzYq59iOljFx0MBMXhlYbfFc3gYyFygN5mBbceY1WlfhvZOpWtMtEPQM/KiIs5/MXVUvAkBEvwmSnqoHdGpGSmeaxhqFdOQiumJ59EYvVxrvidHEzbT5o2w85GPV04bhLFwgq9ZsmaScrxb7VMh/7OSTXvHn";
//   /**平台公钥**/
//   public static final String LOCAL_PUB = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCADgeUl14xgaVtHOcrO5I8XFxY4+jtS9O/TzYjOrXx4HOqLaOx5iGqDz8UDRu8U7uDHFNHRnnOFcBj4iQKpoM+l2N9ojZp6UC294q2Hw2NncSP1Bq2bpDYsB6gon6Ig6SG+3A3fCPvzCEtp5K4XQgMJWxd/ybXV/U+LbPmCc9EzQIDAQAB";
//   /**商户号**/
//   public static final String MER_ID = "200000000000593";

    /**
     * 总指挥
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        CreateInsteadPayMassage run = new CreateInsteadPayMassage();
        // 生成代付报文
     //   run.createInsteadPay();
        // 生成代付查询结果      
        run.createInsteadPayQuery();

//        run.createInsteadPay();


        // 生成代付查询结果

//        run.createInsteadPayQuery();


//        run.createInsteadPayQuery();

        // 生成实名认证 
        //run.createRealNameAuth();
        // 生成实名认证查询
//        run.createRealNameAuthQuery();
        // 生成白名单添加
        //run.createMerchWhiteList();
        // 加密
//        run.compressionByDeflated();
        
//        run.test();
        
//        run.privateDecyptData();
    }
    
    private void privateDecyptData() throws Exception {
//        String encryptedData = "QAdIWj2gJ/HRMPup4RfZreSIImsqYvCY35U4pg7MA3OBWo9hMYR7NmDk6ERXaNMVgIQxSqmCW4rn1OeYuBqaRVTANOoxaCRpD0VkaCrUJNE9jUTnIrPXidfWeLp3Z6tXYSRcQYTYVj95yVyxYV/TVLDJH4LSr7mIstYP4TAjuZUGzLgfKz0//byrLYOlioThD04CRfNAntTq5JzGyluybxvJyodK9gPC+me9IeWH6wkHQ0aw4F8UBUyYnH59uOPaUasS0bABbaGJYd5VnF/VLI1A2o5prq6xduvqiBkIb/Y3S4nf/pKBTcxD85+Fb3jm/c3vkA9TDehEfA7j1NaPclOuBsRGjkdPfzA1r5k59rTiq9tjWo83hFsv37rNykjFa6wXuOSTCh+x4dOGlwcpPzqrqKRd8Qdb0vawW1SOw5keb1IWDuZ48lbyArRwTzishXLTtTn8jiREmU242j2oYmX+NSp96SVVnTrNltRlBDd4cmrLUL0BWrjIlLxGSgpBdMLVAcYdlGm3sHq7go40RboGipa90eV2+j5DH+PI7nmOOHTEV8LsoNtKXclREWtcqUcIlz0EtY2fzhpnKMQfU8FZu9DdAaG/sKZdgamZcnQyhyQDNxb4a9KueAUyN9p1VkEy+CdJJqPe8u21l+hIMskJbfyjPNekFwW2Dxe0Pg8xOS0fsdFx3czVEp1So9kp7xrIYh8te7dpzauzY3FGf6Nta4T8fOlSmnRGpPXG0B+giMULNzAWVBfpKHkmhgLwmlO2GbKWE6Vy4OvOwnAFh+hcwbJkAaE3eZ9Ay11KOcsRqP9n8eLNMPNWRJr3EUNWIlbu9dH4cGRjIqHuja3bFw==";
        String encryptedData = "vNNDpuaSkwOYmZc4a5rmURON8KD2/qbAcy7bJ5e6bWhO0nSkXIFRKARkXn5IJ1wSxjcZgucwu5Khgkfq3H1M5xI3lajC64twb8K5Lh9qt4vxvuQ+Y24bQMYYU0jDbPUQkZKck2sPp0JVhREd0fQMKd+m+tx37XPoP5PuxaLd7/A=";
       String privateKey="MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMQBn5RqS9/oWvaMJqceX/SrVwT1hZaPxIx7glgo2MBnlJLbSxVnsxGXLdzsrCeQdn0zbPfzzZ5X/pse6Uozhb5vLvtb++gWwf5vpFMnj1BCstTaOgeWs9TgIcfoFlOYDEuvVE0Ihse/tZETQZyHUwSd/2ZpgOXbJd3rGLKOYJtJAgMBAAECgYAa08YNwEbGTL02hZQ8x3GGwXTVnu074E2clMMQYfbYztSNDSrHsTvXKoQCevpNJ/6vwDwn69y20YBtpttzZn3lh2uXN332khtyXw26hYK89ZNM1A+3TjTBO3o2V+SFP6JWoCdIBgEffd+0m9Q9QjGZpC7vfElk0ynoGeCek12ByQJBAOJnzMNTCq/DM023RY8u42VAtAmTqPy025ZgyYQH1+y5lYPtS+6eBqCCj1iO+6vxREnggnKw3mmQI/DHPQCZ6T8CQQDdoJQNWlJ4O5GyVVlNy5tC7NY7FkNDSoFqTgqaUG8bftONH+Dtz7YFIxFh28BaL+0HGruwPNjohFab8h/DqBF3AkBTeHVzpdAQ0OS/SExq55yKnfpCdaAd2wgCPxjMhp/C9QD8rKYMCVLkJn525fA/iinlGtK9OhwlyzCU+r+w0w29AkAnLxPeBIEeNtH8qLBgW8Y4n+0Eh1wfEpYs+F1RiTULtHGsut0mzpmM8PVKyvKIP371yZkzgJ6NBZDWg4lwK0/jAkAWIZktGvHqQbWu1XlCVZr3ool6JVJoLIlxTbMmHGMQd/9HGfjIBjd62K26pfm3vJ2gdA0cvAo0HnUmEaw6IF80";
//       privateKey = 
       byte[] strs = RSAUtils.decryptByPrivateKey(Base64Utils.decode(encryptedData), privateKey);
       String words = new String(strs);
       System.out.println("解密后的明文是："+words);
       
    }
    
    /**
     * 生成白名单添加
     * @throws Exception 
     */
    private void createMerchWhiteList() throws Exception {
        MerWhiteList_Request request = new MerWhiteList_Request();
        commSetter(request, "74");
        request.setAccNo("6226091212413805");
        request.setAccName("鲁晓帅");

        // 加签生成
        JSONObject jsonData = JSONObject.fromObject(request);
        String beforeSign = jsonData.toString();
        String signedData = RSAUtils.sign(beforeSign.getBytes("UTF-8"), PRIVATE_KEY);
        jsonData.put("signature", signedData);
        System.out.println("完整json："+jsonData.toString());
        // 复制到ClipBoard
        sysc.setContents(new StringSelection(jsonData.toString()), null);
    }

    /**
     * 
     */
    private void test() {
        String a = null;
        String b="";
        String c = a+b;
        StringBuilder errorMsgDetail = new StringBuilder();
        c=errorMsgDetail.toString();
        System.out.println( c );
        
    }

    /**
     * 生成实名认证
     * @throws Exception 
     */
    private void createRealNameAuth() throws Exception {
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
        // 复制到ClipBoard
        sysc.setContents(new StringSelection(jsonData.toString()), null);
    }

    /**
     * 生成实名认证查询
     * @throws Exception 
     */
    private void createRealNameAuthQuery() throws Exception {
        RealnameAuth_Request request = new RealnameAuth_Request();
        commSetter(request, "73");
        request.setChannelType("00");
        request.setAccessType("0");
        request.setOrderId("1000000000023");
        request.setTxnTime("20151126121212");

        // 加签生成
        JSONObject jsonData = JSONObject.fromObject(request);
        String beforeSign = jsonData.toString();
        String signedData = RSAUtils.sign(beforeSign.getBytes(), PRIVATE_KEY);
        jsonData.put("signature", signedData);
        System.out.println("完整json："+jsonData.toString());
        // 复制到ClipBoard
        sysc.setContents(new StringSelection(jsonData.toString()), null);
    }

    private void compressionByDeflated() throws Exception {
        try {
            // Encode a String into bytes
            String inputString = "我是一个大好人999999999999999999999999999999999999999999999999999999999999999999999";
            System.out.println("input size:"+inputString.getBytes().length);
            byte[] input = inputString.getBytes("UTF-8");

            // Compress the bytes
            byte[] output = new byte[10000];
            Deflater compresser = new Deflater();
            compresser.setInput(input);
            compresser.finish();
            int compressedDataLength = compresser.deflate(output);
            byte[] convert = new byte[compressedDataLength];
            
            DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(output));
            inputStream.read(convert);

            String data = Base64Utils.encode(convert);
            System.out.println("convert size:"+ convert.length);
            System.out.println("out put size:"+ data);

            // Decompress the bytes
            Inflater decompresser = new Inflater();
            decompresser.setInput(output, 0, compressedDataLength);
            byte[] result = new byte[1000];
            int resultLength = decompresser.inflate(result);
            decompresser.end();

            // Decode the bytes into a String
            String outputString = new String(result, 0, resultLength, "UTF-8");
            System.out.println(outputString);
            } catch(java.io.UnsupportedEncodingException ex) {
                ex.printStackTrace();
            } catch (java.util.zip.DataFormatException ex) {
                ex.printStackTrace();
            }
    }

    /**
     * 生成代付查询结果
     * @throws Exception 
     */
    private void createInsteadPayQuery() throws Exception {
        InsteadPayQuery_Request request = new InsteadPayQuery_Request();
        commSetter(request, "22");
        request.setChannelType("00");
        //request.setAccessType("0");
        request.setBatchNo("0326");
        request.setTxnTime("20160323160326");
        

        JSONObject jsonData = JSONObject.fromObject(request);
        String beforeSign = jsonData.toString();
        String signedData = RSAUtils.sign(beforeSign.getBytes(), PRIVATE_KEY);
        System.out.println("加签前："+beforeSign);
        jsonData.put("signature", signedData);
        System.out.println("加签结果："+signedData);
        System.out.println("完整json："+jsonData.toString());
        // 复制到ClipBoard
        sysc.setContents(new StringSelection(jsonData.toString()), null);
    }

    /**
     *  生成代付报文
     * @throws Exception 
     */
    private void createInsteadPay() throws Exception {
        InsteadPay_Request request = new InsteadPay_Request();
        commSetter(request, "21");
        request.setChannelType("00");
//        request.setAccessType("0");
        request.setBatchNo(DateUtil.getCurrentTime().substring(2));
        request.setTxnTime(DateUtil.getCurrentDateTime());
        request.setTotalQty("3");
        request.setTotalAmt("300");
        // 设定明细文件
        InsteadPayFile file1 = createPayFile("20");
        InsteadPayFile file2 = createPayFile("180");
        InsteadPayFile file3 = createPayFile("100");
        List<InsteadPayFile> files = new ArrayList<InsteadPayFile>();
        files.add(file1);
        files.add(file2);
        files.add(file3);
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
        int compressedDataLength = compresser.deflate(output);
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
        // 复制到ClipBoard
        sysc.setContents(new StringSelection(jsonData.toString()), null);
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
        file1.setAccName("鲁亨亨");
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
//        request.setCoopInstiId("300000000000004");
    }
}
