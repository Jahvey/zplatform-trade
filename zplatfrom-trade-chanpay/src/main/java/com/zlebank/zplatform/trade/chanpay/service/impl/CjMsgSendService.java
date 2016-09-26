package com.zlebank.zplatform.trade.chanpay.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.trade.chanpay.utils.Cj;
import com.zlebank.zplatform.trade.chanpay.utils.HttpPostBodyMethod;
import com.zlebank.zplatform.trade.chanpay.utils.S;

/**
 * 向畅捷支付前置发送报文的服务类
 * @author SongCheng
 * 2015年9月19日下午7:24:18
 */
@Service
public class CjMsgSendService {
	public static final Log LOG = LogFactory.getLog(CjMsgSendService.class);

	public String sendAndGetString(String message) throws HttpException, IOException   {
		HttpClient client = new HttpClient();
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, S.ENCODING_utf8);
		client.getParams().setSoTimeout(10 * 60 * 1000);

		URL url = new URL(Cj.CJPARAM_cjGatewayUrl);
		String urlstr = url.toString();
		LOG.info("提交地址" + urlstr);
		HttpPostBodyMethod post = new HttpPostBodyMethod(urlstr, message);

		int statusCode = client.executeMethod(post);
		if (statusCode != HttpStatus.SC_OK) {
			String err = "访问失败！！HTTP_STATUS=" + statusCode;
			LOG.error(err);
			LOG.error("返回内容为：" + post.getResponseBodyAsString());
			throw new HttpException(err);
		}//if

		String respData = post.getResponseBodyAsString();
		LOG.info("收到响应报文：" + respData);
		return respData;
	}//method

	public sendAndGetBytes_Response sendAndGetBytes(String message) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, S.ENCODING_utf8);
		client.getParams().setSoTimeout(10 * 60 * 1000);

		URL url = new URL(Cj.CJPARAM_cjGatewayUrl);
		String urlstr = url.toString();
		LOG.info("提交地址" + urlstr);
		HttpPostBodyMethod post = new HttpPostBodyMethod(urlstr, message);

		int statusCode = client.executeMethod(post);
		if (statusCode != HttpStatus.SC_OK) {
			String err = "访问失败！！HTTP_STATUS=" + statusCode;
			LOG.error(err);
			LOG.error("返回内容为：" + post.getResponseBodyAsString());
			throw new HttpException(err);
		}//if

		sendAndGetBytes_Response ret = new sendAndGetBytes_Response();

		ret.contentType = findHeaderAttr(post.getResponseHeader("Content-Type"));
		LOG.info("收到响应类型contentType：" + ret.contentType);

		ret.data = post.getResponseBody();
		ret.contentDisposition = findHeaderAttr(post.getResponseHeader("Content-Disposition"));
		ret.retcode = findHeaderAttr(post.getResponseHeader(Cj.PROP_NAME_RET_CODE));
		ret.errmsg = findHeaderAttr(post.getResponseHeader(Cj.PROP_NAME_ERR_MSG));
		//TODO 需要字符集转义
		return ret;
	}//method

	public class sendAndGetBytes_Response {
		public String retcode = "";
		public String errmsg = "";
		public byte[] data = {};
		public String contentType = "";
		public String contentDisposition = "";
	}//class

	private String findHeaderAttr(Header head) throws UnsupportedEncodingException {
		if (head == null)
			return "";
		String val = head.getValue();
		if (StringUtils.isBlank(val)) {
			return "";
		}
		String msg = URLDecoder.decode(val, S.ENCODING_utf8);
		LOG.info("收到HTTP-HEAD属性[" + head.getName() + "]=" + msg);
		return msg;
	}//method

}//class
