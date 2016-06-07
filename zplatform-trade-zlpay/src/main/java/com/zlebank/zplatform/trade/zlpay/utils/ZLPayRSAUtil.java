package com.zlebank.zplatform.trade.zlpay.utils;

import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.zlink.zlpay.commonofs.common.exception.MsgException;
import com.zlink.zlpay.commonofs.common.util.base64.Base64;

public class ZLPayRSAUtil {
	private static final Provider provider = new BouncyCastleProvider();

	public static String decryptByPublicKey(String data, PublicKey publicKey)
			throws MsgException {
		byte[] decodedData = Base64.decodeBase64(data);
		byte[] encodedData = com.zlink.zlpay.commonofs.common.util.RSAUtil.decryptByPublicKey(decodedData, publicKey);
		try {
			return new String(encodedData, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// logger.error("编码错误");
		}
		throw new MsgException("编码错误");
	}

	public static boolean verifyByPublicKey(String data, String signData,
			PublicKey publicKey) throws MsgException,
			UnsupportedEncodingException {
		byte[] decodedData = Base64.decodeBase64(signData);
		boolean flag;
		try {
			flag = ZLPayRSAUtil.verifyByPublicKey(data.getBytes("UTF-8"),
					decodedData, publicKey);
		} catch (UnsupportedEncodingException e) {
			// logger.error("编码错误");
			throw new MsgException("编码错误");
		}
		return flag;
		
	}

	public static String verifyData(String signedData, String encryptedData,
			PublicKey publicKey) throws MsgException,
			UnsupportedEncodingException {
		String decryptedData = decryptByPublicKey(encryptedData, publicKey);
		System.out.println(decryptedData);
		
		
		boolean flag = verifyByPublicKey(decryptedData, signedData, publicKey);
		if (!flag) {
			throw new MsgException("数据验签失败");
		}
		return decryptedData;
	}

	public static byte[] signByPrivateKey(byte[] data, PrivateKey privateKey)
			throws MsgException {
		try {
			Signature signature = Signature
					.getInstance("SHA1withRSA", provider);
			signature.initSign(privateKey);
			signature.update(data);
			return signature.sign();
		} catch (Exception e) {
			// logger.info(e.getMessage());
		}
		throw new MsgException("加签失败，请检查");
	}

	public static boolean verifyByPublicKey(byte[] data, byte[] signedData,
			PublicKey publicKey) throws MsgException {
		try {

			Signature signature = Signature
					.getInstance("SHA1withRSA", provider);
			signature.initVerify(publicKey);
			signature.update(data);
			return signature.verify(signedData);
		} catch (Exception e) {
			// logger.info(e.getMessage());
		}
		throw new MsgException("验签失败，请检查");
	}
}
