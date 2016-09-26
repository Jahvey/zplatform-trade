/* 
 * AESUtil.java  
 * 
 * version v1.4
 *
 * 2016年4月20日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.wechat.qr.security;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * AES工具包
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年4月20日 下午3:53:15
 * @since 
 */
public class AESUtil {
    
    private static final Log log = LogFactory.getLog(AESUtil.class);

    private static final String AES = "AES";
    
    private static final String AES_CIPHER = "AES/ECB/PKCS5Padding";

    /**
     * 得到128Bit 的 AES密钥
     * @return AES密钥（32位的16进制字符串）
     * @throws Exception
     */
    public static String getAESKey() {
        try {
            final SecureRandom random = new SecureRandom(String.valueOf(System.nanoTime()).getBytes());
            final KeyGenerator generate;
            generate = KeyGenerator.getInstance(AES);
            generate.init(128, random);
            byte[] keyBytes = generate.generateKey().getEncoded();
            String keyStr = new String(Hex.encodeHex(keyBytes));
            return keyStr;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 加密
     * 
     * @param content  需要加密的内容
     * @param keyStr   AES解密密钥（32位的16进制字符串）
     * @return 加密后的字节数组
     */
    public static byte[] encrypt(String content, String keyStr) {
        try {
            SecretKeySpec key = new SecretKeySpec(Hex.decodeHex(keyStr.toCharArray()), AES);
            Cipher cipher = Cipher.getInstance(AES_CIPHER); // 创建密码器
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content.getBytes("utf-8"));
            return result; // 加密
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     * 
     * @param content 待解密内容
     * @param keyStr AES解密密钥（32位的16进制字符串）
     * @return  解密后的内容字节数组
     */
    public static byte[] decrypt(byte[] content, String keyStr) {
        try {
            SecretKeySpec key = new SecretKeySpec(Hex.decodeHex(keyStr.toCharArray()), AES);
            Cipher cipher = Cipher.getInstance(AES_CIPHER);
            // 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 加密
            byte[] result = cipher.doFinal(content);
            return result;
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  测试工具
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String key = getAESKey();
        
        String name = "鲁晓帅";
        byte[] enByte = encrypt(name, key);
        byte[] decrypt = decrypt(enByte, key);
        System.out.println(new String (decrypt, "UTF-8"));
    }
}
