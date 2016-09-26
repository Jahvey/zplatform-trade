package com.zlebank.zplatform.trade;

import java.util.Map;

import com.zlebank.zplatform.commons.utils.Base64Utils;
import com.zlebank.zplatform.commons.utils.RSAUtils;

public class RSATester {
	static String publicKey;  
    static String privateKey;  
  
    
      
    public static void main(String[] args) throws Exception {  
        Map<String, Object> merch_keyMap = RSAUtils.genKeyPair();  
        Map<String, Object> plath_keyMap = RSAUtils.genKeyPair();
        
        String merch_publicKey = RSAUtils.getPublicKey(plath_keyMap);   
        String merch_privateKey = RSAUtils.getPrivateKey(plath_keyMap);  
        
        
        String plath_publicKey =  RSAUtils.getPublicKey(merch_keyMap);
        String plath_privateKey = RSAUtils.getPrivateKey(merch_keyMap); 
        System.err.println("商户公钥: \n" + merch_publicKey);  
        System.err.println("商户私钥： \n" + merch_privateKey);
        
        System.err.println("平台公钥: \n" + plath_publicKey);  
        System.err.println("平台私钥： \n" + plath_privateKey);
        
        String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALlMzGRa1fr/T0xKcKx7Ewc6eX2s5lXpaRKZkczZmMpiaqNFJOOglOttp/RvSYx3FVauH9PyLAtxsVtIN10KfPQEcaHHr8oFH+N34U7wamyrmUOpYVzaQFiHXwZxTyYXx0MSH2K3NmB/5HaBYKbupxm4y76ZKYWfn+DU+HytYvoXAgMBAAECgYARMkFdptho3Ly9NcNTHnDboN+ZfCw8GNc5dKsFOcRmM8/1qvsLbOGlH54QnytSL9L5IlutzbGstjZhZEhPCCQkUKz0GLOKmZYfBmojxp/wj/6UDbmKJJufJC9KkBLGXyeJlxp3hHWAyPTZuMg1/7yAG41dq3Q2xqcbBpE8p6U7gQJBAOeNzyqnwCoMxN8LGU473A9x29X9zbzDTXiLnkiEgzojJ5DerIqna2v+h5OPYPy7uPvV/YJBSL/ttHMf9d0EHbcCQQDM3OMgtWZ+W+l0gULRa6GzrVajwi+lSQdcrT2W7joPpkPNLuxDv/vLAt0ZAY+w/lNZ0JQEZFBIl8Dk/doztgahAkBWGZvr336L5D2cNjNNrOJx98Nnub0zsGdxKHFNw8BL8OB4dOQxlnlSKc3d55WHxIHPWs2uDnpvq4LKwBqa+TnXAkAlA4/h/AR9thuTGju5XMoFjb8GrXIwWeaDnyVunLJO24qKivzTLN/QiT7rYrXUk875fk8QEBPVWd3XQepnx26hAkBD7KR5IAzqNsC0ewC8SrfrnXoZt5leYhZseILoFtwSKpoyioM1fCZ70YdvXKtmCiVog64jrUFFuX1pQLtbGjCr";
        String publicKey ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC5TMxkWtX6/09MSnCsexMHOnl9rOZV6WkSmZHM2ZjKYmqjRSTjoJTrbaf0b0mMdxVWrh/T8iwLcbFbSDddCnz0BHGhx6/KBR/jd+FO8Gpsq5lDqWFc2kBYh18GcU8mF8dDEh9itzZgf+R2gWCm7qcZuMu+mSmFn5/g1Ph8rWL6FwIDAQAB";
        System.err.println("私钥加密——公钥解密");  
          
        String encryptData = "{cardNo=6228480018543668976&cvn2=&expired=&phoneNo=18600806796}";
        /*String phoneNo = StringUtils.substringBetween(encryptData, "phoneNo=", "}");
        System.out.println(phoneNo);*/
        System.out.println("原文字：\r\n" + encryptData);  
        byte[] data = encryptData.getBytes();  
        byte[] encodedData = RSAUtils.encryptByPrivateKey(data, privateKey);  
        String base64_encodedData = Base64Utils.encode(encodedData);
        System.out.println("加密后：\r\n" + base64_encodedData);  
        String source = "{certifTp=01&certifId=110105198610094112&customerNm=郭佳&smsCode=&pin=&encryptData="+base64_encodedData+"}";
        String base64_source = Base64Utils.encode(source.getBytes());
        System.out.println("数据base64转码：\r\n" + base64_source);  
        System.out.println("数据base64解码：\r\n" + new String(Base64Utils.decode(base64_source)));  
        byte[] decodedData = RSAUtils.decryptByPublicKey(Base64Utils.decode(base64_encodedData), publicKey);  
        String target = new String(decodedData);  
        System.out.println("解密后: \r\n" + target);
        System.err.println("私钥签名——公钥验证签名");  
        String sign = RSAUtils.sign("111111111111111111111111111111".getBytes(), privateKey);  
        System.err.println("签名:\r" + sign);  
        boolean status = RSAUtils.verify("111111111111111111111111111111".getBytes(), publicKey,sign);  
        System.err.println("验证结果:\r" + status);  
    }  
  
    static void test() throws Exception {  
        System.err.println("公钥加密——私钥解密");  
        String source = "111111111111111111111111111111";  
        System.out.println("\r加密前文字：\r\n" + source);  
        byte[] data = source.getBytes();  
        byte[] encodedData = RSAUtils.encryptByPublicKey(data, publicKey);  
        System.out.println("加密后文字：\r\n" + new String(encodedData));  
        byte[] decodedData = RSAUtils.decryptByPrivateKey(encodedData, privateKey);  
        String target = new String(decodedData);  
        System.out.println("解密后文字: \r\n" + target);  
    }  
  
    static void testSign() throws Exception {  
        /*System.err.println("私钥加密——公钥解密");  
        String source = "这是一行测试RSA数字签名的无意义文字";  
        System.out.println("原文字：\r\n" + source);  
        byte[] data = source.getBytes();  
        byte[] encodedData = RSAUtils.encryptByPrivateKey(data, privateKey);  
        System.out.println("加密后：\r\n" + new String(encodedData));  
        byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);  
        String target = new String(decodedData);  
        System.out.println("解密后: \r\n" + target);  */
        System.err.println("私钥签名——公钥验证签名");  
        String sign = RSAUtils.sign("111111111111111111111111111111".getBytes(), privateKey);  
        System.err.println("签名:\r" + sign);  
        boolean status = RSAUtils.verify("111111111111111111111111111111".getBytes(), publicKey, sign);  
        System.err.println("验证结果:\r" + status);  
    } 
}
