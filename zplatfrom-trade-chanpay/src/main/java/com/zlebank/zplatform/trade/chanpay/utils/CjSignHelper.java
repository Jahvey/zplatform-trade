package com.zlebank.zplatform.trade.chanpay.utils;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.SignerInfoGenerator;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationVerifier;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.encoders.Base64;

public class CjSignHelper {
	public static final Log LOG = LogFactory.getLog(CjSignHelper.class);

	public static final String TAG_SIGNED_PREFIX = "<SIGNED_MSG>";
	public static final String TAG_SIGNED_SUFFIX = "</SIGNED_MSG>";
	public static final String TAG_INFO_SUFFIX = "</INFO>";

	private static final String SIGNATUREALGO = "SHA1withRSA";
	private static final String BouncyCastleProvider_NAME = "BC";

	private CMSSignedDataGenerator cmsSignedDataGenerator = null;
	
	private PrivateKey privateKey = null;
	private X509Certificate publicKey = null;
	private X509Certificate cjServerPublicKey = null;

	public CjSignHelper() {
		try {
			init();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}//constructor

	public void init() throws Exception {

		String pfxPath = Cj.CJPARAM_ysPfxPath;
		String pfxPasswd = Cj.CJPARAM_ysPfxPasswd;
		String CjCertPath = Cj.CJPARAM_cjCertPath;
		//------------------------------------------------------------

		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		FileInputStream pfxInputStream = new FileInputStream(pfxPath);
		keyStore.load(pfxInputStream, pfxPasswd.toCharArray());

		Enumeration aliasesEnum = keyStore.aliases();
		aliasesEnum.hasMoreElements();
		String alias = (String) aliasesEnum.nextElement();

		privateKey = (PrivateKey) keyStore.getKey(alias, pfxPasswd.toCharArray());
		LOG.info("加载商户私钥：" + U.substringByByte(privateKey + "", 128));

		publicKey = (X509Certificate) keyStore.getCertificate(alias);
		pfxInputStream.close();
		LOG.info("加载商户证书：" + publicKey.getSerialNumber().toString(16).toUpperCase());
		LOG.info("加载商户证书：" + publicKey.getSubjectDN());

		//------------------------------------------------------------

		CertificateFactory factory = CertificateFactory.getInstance("X509");
		FileInputStream cjCertInputStream = new FileInputStream(CjCertPath);
		cjServerPublicKey = (X509Certificate) factory.generateCertificate(cjCertInputStream);
		cjCertInputStream.close();
		LOG.info("加载CJ证书：" + cjServerPublicKey.getSerialNumber().toString(16).toUpperCase());
		LOG.info("加载CJ证书：" + cjServerPublicKey.getSubjectDN());
		//------------------------------------------------------------

		cmsSignedDataGenerator = buildCmsSignedDataGenerator();
	}//method

	public CMSSignedDataGenerator buildCmsSignedDataGenerator() throws Exception {
		BouncyCastleProvider bouncyCastlePd = new BouncyCastleProvider();
		Security.addProvider(bouncyCastlePd);
		LOG.info("设置加密提供者：" + bouncyCastlePd.getName());

		
		JcaContentSignerBuilder jcaContentSignerBuilder = new JcaContentSignerBuilder(SIGNATUREALGO).setProvider(bouncyCastlePd.getName());
		ContentSigner signer = jcaContentSignerBuilder.build(privateKey);

		DigestCalculatorProvider digestCalculatorProvider = new JcaDigestCalculatorProviderBuilder().setProvider(bouncyCastlePd.getName()).build();
		JcaSignerInfoGeneratorBuilder jcaBuilder = new JcaSignerInfoGeneratorBuilder(digestCalculatorProvider);
		SignerInfoGenerator signGen = jcaBuilder.build(signer, publicKey);

		CMSSignedDataGenerator generator = new CMSSignedDataGenerator();
		generator.addSignerInfoGenerator(signGen);

		List<Certificate> certChainList = new LinkedList<Certificate>();
		certChainList.add(publicKey);
		Store certstore = new JcaCertStore(certChainList);
		generator.addCertificates(certstore);

		return generator;
	}//method

	/**
	 * 数字签名 xml 并返回加密后的报文
	 */
	public String signXml$Add(String xml) {
		String sign = signString(xml);

		int beginIndex = xml.indexOf(TAG_SIGNED_SUFFIX);
		beginIndex = beginIndex < 0 ? 0 : beginIndex;

		StringBuilder out = new StringBuilder();
		out.append(xml, 0, beginIndex);
		out.append(sign);
		out.append(xml, beginIndex, xml.length());

		return out.toString();
	}//method

	public VerifyResult verifyCjServerXml(String xml) {
		//取得明文
		int beginIndex = xml.indexOf(TAG_SIGNED_PREFIX) + TAG_SIGNED_PREFIX.length();
		int endIndex = xml.indexOf(TAG_SIGNED_SUFFIX);
		StringBuilder out = new StringBuilder();
		out.append(xml, 0, beginIndex).append(xml, endIndex, xml.length());
		String plain = out.toString();
		LOG.info("plain=" + U.substringByByte(plain, 512));

		//取得签名
		String sign = xml.substring(beginIndex, endIndex);
		LOG.info("sign=" + sign);

		VerifyResult rs = verifyCjServerString(plain, sign);
		return rs;
	}//method

	public class VerifyResult {
		public boolean result = false;
		public String errMsg = "";

		public String toString() {
			return U.build2StringShortStyle(this);
		}
	}//class

	public VerifyResult verifyCjServerString(String plain, String signed) {
		VerifyResult rs = new VerifyResult();
		try {
			byte[] signedBs = Base64.decode(signed);//pkcs7

			byte[] plainBs = plain.getBytes(S.ENCODING_utf8);
			byte[] sha1Hash = MessageDigest.getInstance("SHA1").digest(plainBs);
			Map<String, Object> hashes = new HashMap<String, Object>();
			hashes.put("1.3.14.3.2.26", sha1Hash);
			hashes.put("1.2.156.10197.1.410", sha1Hash);

			CMSSignedData sign = new CMSSignedData(hashes, signedBs);

			//假定只有一个证书签名者
			SignerInformation signer = (SignerInformation) sign.getSignerInfos().getSigners().iterator().next();
			SignerInformationVerifier signerInformationVerifier = new JcaSimpleSignerInfoVerifierBuilder().setProvider(BouncyCastleProvider_NAME).build(
					this.cjServerPublicKey);

			//使用本地证书验证签名
			LOG.info("使用CJ服务器证书验证：" + cjServerPublicKey.getSerialNumber().toString(16).toUpperCase());
			if (signer.verify(signerInformationVerifier)) {
				LOG.info("pkcs7 验签成功！");
				rs.result = true;
			} else {
				rs.errMsg = "pkcs7 验签失败！";
			}
		} catch (Exception e) {
			String err = "pkcs7 验签失败！" + e.getMessage();
			LOG.error(err, e);
			rs.errMsg = err;
		}
		return rs;
	}//method

	public VerifyResult verifyString(String plain, String signed) {
		VerifyResult rs = new VerifyResult();
		try {
			byte[] signedBs = Base64.decode(signed);//pkcs7

			byte[] plainBs = plain.getBytes(S.ENCODING_utf8);
			byte[] sha1Hash = MessageDigest.getInstance("SHA1").digest(plainBs);
			Map<String, Object> hashes = new HashMap<String, Object>();
			hashes.put("1.3.14.3.2.26", sha1Hash);
			hashes.put("1.2.156.10197.1.410", sha1Hash);

			CMSSignedData sign = new CMSSignedData(hashes, signedBs);

			//假定只有一个证书签名者
			Store store = sign.getCertificates();
			SignerInformation signer = (SignerInformation) sign.getSignerInfos().getSigners().iterator().next();

			X509CertificateHolder certHolder = (X509CertificateHolder) store.getMatches(signer.getSID()).iterator().next();
			X509Certificate cert = new JcaX509CertificateConverter().setProvider(BouncyCastleProvider_NAME).getCertificate(certHolder);
			SignerInformationVerifier signerInformationVerifier = new JcaSimpleSignerInfoVerifierBuilder().setProvider(BouncyCastleProvider_NAME).build(cert);
			LOG.info("签名信息中的证书：" + cert.getSerialNumber().toString(16).toUpperCase());
			LOG.info("签名信息中的证书：" + cert.getSubjectDN());

			//取出签名信息
			byte[] data = signer.getSignature();
			LOG.info("签名信息长度：" + data.length);

			//使用本地证书验证签名
			if (signer.verify(signerInformationVerifier)) {
				LOG.info("pkcs7 验签成功！");
				rs.result = true;
			} else {
				rs.errMsg = "pkcs7 验签失败！";
			}
		} catch (Exception e) {
			String err = "pkcs7 验签失败！" + e.getMessage();
			LOG.error(err, e);
			rs.errMsg = err;
		}
		return rs;
	}//method

	public String signString(String plain) {
		try {
			byte[] plainBs = plain.getBytes(S.ENCODING_utf8);
			CMSTypedData cmsdata = new CMSProcessableByteArray(plainBs);
			CMSSignedData signeddata = cmsSignedDataGenerator.generate(cmsdata, true);

			byte[] signBs = signeddata.getEncoded();

			String sign = new String(Base64.encode(signBs));
			return sign;
		} catch (Exception e) {
			String err = "签名失败：" + e.getMessage();
			LOG.error(err, e);
			throw new RuntimeException(err, e);
		}
	}//method

}//class