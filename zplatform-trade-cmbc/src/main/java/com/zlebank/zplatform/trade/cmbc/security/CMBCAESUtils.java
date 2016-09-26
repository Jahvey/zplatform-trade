/* 
 * CMBCAESUtils.java  
 * 
 * version TODO
 *
 * 2015年11月3日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.cmbc.security;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zlebank.zplatform.commons.utils.Md5;
import com.zlebank.zplatform.trade.utils.ConsUtil;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月3日 下午4:22:43
 * @since 
 */
public class CMBCAESUtils {
    private static final Log logger = LogFactory.getLog(CMBCAESUtils.class);
    private static final String MD5KEY="1234567887654321";
    private static final String REALTIMEPAYMD5=ConsUtil.getInstance().cons.getCmbc_insteadpay_sign_md5();
    private static final String SELFWITHHOLDMD5="123456";
    /**
     * AES加密
     * 
     * @param key
     *            密钥信息
     * @param content
     *            待加密信息
     */
    public static byte[] encodeAES(byte[] key, byte[] content) throws Exception {
        // 不是16的倍数的，补足
        int base = 16;
        if (key.length % base != 0) {
            int groups = key.length / base + (key.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(key, 0, temp, 0, key.length);
            key = temp;
        }

        SecretKey secretKey = new SecretKeySpec(key, "AES");
        IvParameterSpec iv = new IvParameterSpec(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        byte[] tgtBytes = cipher.doFinal(content);
        return tgtBytes;
    }

    /**
     * AES解密
     * 
     * @param key
     *            密钥信息
     * @param content
     *            待加密信息
     * @return
     * @throws Exception
     */
    public static byte[] decodeAES(byte[] key, byte[] content) throws Exception {
        // 不是16的倍数的，补足
        int base = 16;
        if (key.length % base != 0) {
            int groups = key.length / base + (key.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(key, 0, temp, 0, key.length);
            key = temp;
        }

        SecretKey secretKey = new SecretKeySpec(key, "AES");
        IvParameterSpec iv = new IvParameterSpec(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte[] tgtBytes = cipher.doFinal(content);
        return tgtBytes;
    }

    /**
     * 加密文件
     * 
     * @param key
     *            密钥
     * @param plainFilePath
     *            明文文件路径路径
     * @param secretFilePath
     *            密文文件路径
     * @throws Exception
     */
    public static void encodeAESFile(byte[] key, String plainFilePath, String secretFilePath) throws Exception {
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;
        FileOutputStream fos = null;
        try {

            fis = new FileInputStream(plainFilePath);
            bos = new ByteArrayOutputStream(fis.available());

            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, count);
            }
            bos.flush();

            byte[] bytes = encodeAES(key, bos.toByteArray());

            fos = new FileOutputStream(secretFilePath);
            fos.write(bytes);
            fos.flush();
        } catch (Exception e) {
            throw e;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                    logger.error(e.getLocalizedMessage(), e);
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception e) {
                    logger.error(e.getLocalizedMessage(), e);
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    logger.error(e.getLocalizedMessage(), e);
                }
            }
        }
    }
    /**
     * 加密文件
     * 
     * @param key
     *            密钥
     * @param plainFilePath
     *            明文文件路径路径
     * @param secretFilePath
     *            密文文件路径
     * @throws Exception
     */
    public static void encodeAESFile(byte[] key, File plainFile, File secretFile) throws Exception {
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;
        FileOutputStream fos = null;
        try {

            fis = new FileInputStream(plainFile);
            bos = new ByteArrayOutputStream(fis.available());

            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, count);
            }
            bos.flush();

            byte[] bytes = encodeAES(key, bos.toByteArray());

            fos = new FileOutputStream(secretFile);
            fos.write(bytes);
            fos.flush();
        } catch (Exception e) {
            throw e;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                    logger.error(e.getLocalizedMessage(), e);
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception e) {
                    logger.error(e.getLocalizedMessage(), e);
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    logger.error(e.getLocalizedMessage(), e);
                }
            }
        }
    }
    /**
     * 解密文件
     * 
     * @param key
     *            密钥
     * @param plainFilePath
     *            明文文件路径路径
     * @param secretFilePath
     *            密文文件路径
     * @throws Exception
     */
    public static void decodeAESFile(byte[] key, String plainFilePath, String secretFilePath) throws Exception {
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(secretFilePath);
            bos = new ByteArrayOutputStream(fis.available());

            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, count);
            }
            bos.flush();

            byte[] bytes = decodeAES(key, bos.toByteArray());

            fos = new FileOutputStream(plainFilePath);
            fos.write(bytes);
            fos.flush();
        } catch (Exception e) {
            throw e;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                    logger.error(e.getLocalizedMessage(), e);
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception e) {
                    logger.error(e.getLocalizedMessage(), e);
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    logger.error(e.getLocalizedMessage(), e);
                }
            }
        }
    }
    
    public static String encodeMD5(String xml){
        return new Md5().md5s(xml+REALTIMEPAYMD5);
    }
    public static String encodeWithholdingMD5(String xml){
        return new Md5().md5s(xml+MD5KEY);
    }
    
    public static String encodeSelfWithholdingMD5(String xml){
        return new Md5().md5s(xml+SELFWITHHOLDMD5);
    }
    public static void main(String[] args) {
        try {
            // 密钥
            byte[] key = "1234567887654321".getBytes();
            // 明文文件路径
            String plainFilePath = "e:/req_plain.txt";
            // 密文文件路径
            String secretFilePath = "e:/req_secret.txt";
            // 加密
            //encodeAESFile(key, plainFilePath, secretFilePath);

            // 解密
            plainFilePath = "d:/cmbc/res_plain.txt";
            secretFilePath = "d:/cmbc/res_20151124_004.txt";
            decodeAESFile(key, plainFilePath, secretFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
