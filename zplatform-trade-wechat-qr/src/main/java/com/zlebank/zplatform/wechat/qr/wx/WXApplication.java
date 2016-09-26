/* 
 * WeChatFactory.java  
 * 
 * version TODO
 *
 * 2016年5月13日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.wechat.qr.wx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.ParserConfigurationException;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.xml.sax.SAXException;

import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.trade.service.TradeLogService;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.SpringContext;
import com.zlebank.zplatform.wechat.qr.enums.BillTypeEnum;
import com.zlebank.zplatform.wechat.qr.exception.WXVerifySignFailedException;
import com.zlebank.zplatform.wechat.qr.security.AESUtil;
import com.zlebank.zplatform.wechat.qr.wx.bean.CloseOrderResultBean;
import com.zlebank.zplatform.wechat.qr.wx.bean.PayResultBean;
import com.zlebank.zplatform.wechat.qr.wx.bean.QueryBillBean;
import com.zlebank.zplatform.wechat.qr.wx.bean.QueryOrderBean;
import com.zlebank.zplatform.wechat.qr.wx.bean.QueryOrderResultBean;
import com.zlebank.zplatform.wechat.qr.wx.bean.QueryRefundBean;
import com.zlebank.zplatform.wechat.qr.wx.bean.QueryRefundResultBean;
import com.zlebank.zplatform.wechat.qr.wx.bean.RefundBean;
import com.zlebank.zplatform.wechat.qr.wx.bean.RefundResultBean;
import com.zlebank.zplatform.wechat.qr.wx.bean.RefundSubInfo;
import com.zlebank.zplatform.wechat.qr.wx.bean.ShortUrlResult;
import com.zlebank.zplatform.wechat.qr.wx.bean.WXOrderBean;
import com.zlebank.zplatform.wechat.qr.wx.common.SignUtil;
import com.zlebank.zplatform.wechat.qr.wx.common.WXConfigure;
import com.zlebank.zplatform.wechat.qr.wx.common.XMLParser;

/**
 * 微信应用
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年5月13日 下午7:20:33
 * @since
 */
public class WXApplication {

	// private static final String CREATE_ORDER_URL=
	
	/**下订单URL**/
    private static final String CREATE_ORDER_URL= ConsUtil.getInstance().cons.getWechat_qr_create_order_url();//"https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**下载对账单URL**/
    private static final String DOWN_LOAD_BILL_URL= ConsUtil.getInstance().cons.getWechat_qr_down_load_bill_url() ;//"https://api.mch.weixin.qq.com/pay/downloadbill";
    /**查询订单**/
    private static final String QUERY_TRADE_URL= ConsUtil.getInstance().cons.getWechat_qr_query_trade_url();//"https://api.mch.weixin.qq.com/pay/orderquery";
    
	/** 关闭订单 **/
	private static final String CLOSE_ORDER_URL = ConsUtil.getInstance().cons.getWechat_qr_close_order_url();//"https://api.mch.weixin.qq.com/pay/closeorder";
	/** 转换短链接 **/
	private static final String SHORT_URL = ConsUtil.getInstance().cons.getWechat_qr_short_url();//"https://api.mch.weixin.qq.com/tools/shorturl";

	/**
	 * SUCCESS
	 */
	private static final String SUCCESS = "SUCCESS";
	private static final Log log = LogFactory.getLog(WXApplication.class);
	private TradeLogService tradeLogService = (TradeLogService) SpringContext.getContext().getBean("tradeLogService");

	/**
	 * 下订单并返回结果
	 * 
	 * @param order
	 *            下订单参数（参照 WXOrderBean）
	 * @return JSONObject SDK端用于调起微信SDK的参数
	 * @throws WXVerifySignFailedException
	 */
	public JSONObject createOrder(WXOrderBean order,String txnseqno)
			throws WXVerifySignFailedException {
		if (log.isDebugEnabled())
			log.debug("【下订单】【开始】" + JSONObject.fromObject(order));
		// 下订单
		String xml = createXML(order);
		tradeLogService.saveRequestLog(xml, txnseqno);
		// 发送订单
		String rtnData = sendXMLData(xml, CREATE_ORDER_URL);
		tradeLogService.saveResponseLog(rtnData, txnseqno);
		// 解析应答报文
		WXOrderResponseBean res = parseOrderXml(rtnData);
		// 验签
		String mySign = null;
		try {
			mySign = SignUtil.getSignFromResponseString(rtnData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		log.info("【本地生成的应答报文签名】" + mySign);
		if (SUCCESS.equals(res.getReturn_code())) {
			if (mySign.equals(res.getResult().getSign())) {

				log.info("【验签成功】");
			} else {

				log.info("【验签失败】");
				throw new WXVerifySignFailedException();
			}
		}
		// 组装调起支付接口报文
		JSONObject rtnMsg = getInvokePayMsg(res.getResult().getPrepay_id(), res
				.getResult().getCode_url());

		if (log.isDebugEnabled())
			log.debug("【下订单】【结束】" + rtnMsg);
		return rtnMsg;
	}

	/**
	 * 组装调起支付接口报文
	 * 
	 * @param prepayid
	 * @return
	 */
	public JSONObject getInvokePayMsg(String prepayid, String code_url) {
		
		log.info("【组装调起支付接口报文】【开始】" + prepayid+code_url);
		Map<String, Object> msgMap = new HashMap<String, Object>();
		// 添加属性
		msgMap.put("appid", WXConfigure.getAppid());// 应用ID【固定】
		msgMap.put("partnerid", WXConfigure.getMchid());// 商户号【固定】
		msgMap.put("prepayid", prepayid);// 预支付交易会话ID
		msgMap.put("package", "Sign=WXPay");// 扩展字段【固定】
		msgMap.put("noncestr", AESUtil.getAESKey().toUpperCase());// 随机字符串
		msgMap.put("timestamp", String.valueOf(TimeUnit.MILLISECONDS
				.toSeconds(System.currentTimeMillis())));// 时间戳
		msgMap.put("code_url", code_url);// 二维码地址
		// 加签
		String sign = SignUtil.getSign(msgMap);
		msgMap.put("sign", sign);
		JSONObject msg = JSONObject.fromObject(msgMap);

		log.info("【组装调起支付接口报文】【返回】" + msg.toString());
		return msg;
	}

	/**
	 * 发送报文
	 * 
	 * @param xml
	 * @param url
	 * @return 返回应答报文
	 */
	private String sendXMLData(String xml, String url) {
		try {
			log.info("【发送给微信的报文为】" + xml);
			// String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
			PostMethod post = null;
			post = new PostMethod(url);
			post.setRequestHeader("Content-Type",
					"application/x-www-form-urlencoded");
			RequestEntity requestEntity = new StringRequestEntity(xml, null,
					null);
			post.setRequestEntity(requestEntity);
			post.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setSoTimeout(10000);// 10秒过期
			int status = client.executeMethod(post);
			// 成功接收
			if (status == 200) {
				BufferedReader br = null;
				br = new BufferedReader(new InputStreamReader(
						post.getResponseBodyAsStream()));
				String line = br.readLine();
				StringBuffer jsonStr = new StringBuffer();
				while (line != null) {
					jsonStr.append(line);
					line = br.readLine();
				}
				log.info("【从微信收到的结果为】" + jsonStr);
				log.info("【成功】状态返回200");
				return jsonStr.toString();
			} else {
				log.info("【失败】状态返回不是200，-->" + status);
			}
		} catch (Exception e) {
			log.error("发送代付通知时发生错误！");
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 生成一个新的文档
	 * 
	 * @return
	 */
	private Document genNewDoc(String rootName) {
		Element root = DocumentHelper.createElement(rootName);
		Document document = DocumentHelper.createDocument(root);
		return document;
	}

	/**
	 * 根据对象生成XML
	 * 
	 * @param string
	 * @return
	 * @throws IOException
	 */
	private String createXML(WXOrderBean order) {
		// 生成新文档（根节点为xml）
		Document invokeAPI = genNewDoc("xml");
		Element root = invokeAPI.getRootElement();
		// 添加属性
		addElement(root, "appid", WXConfigure.getAppid());// 应用ID【固定】
		addElement(root, "attach", order.getAttach());// 附加数据
		addElement(root, "body", order.getBody());// 商品描述
		addElement(root, "detail", order.getDetail());// 商品详情
		addElement(root, "device_info", WXConfigure.getDeviceInfo());// 设备号【固定】
		addElement(root, "fee_type", WXConfigure.getFeeType());// 货币类型【固定】
		addElement(root, "goods_tag", order.getGoods_tag());// 商品标记
		addElement(root, "limit_pay", order.getLimit_pay());// 指定支付方式
		addElement(root, "mch_id", WXConfigure.getMchid());// 商户号【固定】
		addElement(root, "nonce_str", AESUtil.getAESKey().toUpperCase());// 随机字符串
		addElement(root, "notify_url", order.getNotify_url());// 通知地址
		addElement(root, "out_trade_no", order.getOut_trade_no());// 商户订单号
		addElement(root, "spbill_create_ip", WXConfigure.getIp());// 终端IP【自动取值】
		addElement(root, "time_expire", order.getTime_expire());// 交易结束时间
		addElement(root, "time_start", order.getTime_start());// 交易起始时间
		addElement(root, "total_fee", order.getTotal_fee());// 总金额
		addElement(root, "trade_type", WXConfigure.getTradeType());// 交易类型【固定】
		// 加签
		String sign = sign(getStringXML(invokeAPI));
		addElement(root, "sign", sign);// 签名
		String xml = getStringXML(invokeAPI);
		return xml;
	}

	/**
	 * 加签
	 * 
	 * @param order
	 * @return
	 */
	private String sign(String xml) {
		Map<String, Object> map = null;
		try {
			map = XMLParser.getMapFromXML(xml);
			return SignUtil.getSign(map);
		} catch (ParserConfigurationException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (SAXException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 添加一个普通的子元素（非CDATA）
	 * 
	 * @param doc
	 * @param eleKey
	 * @param eleValue
	 */
	private void addElement(Element doc, String eleKey, String eleValue) {
		if (StringUtil.isEmpty(eleKey) || StringUtil.isEmpty(eleValue)) {
			return;
		}
		addElement(doc, eleKey, eleValue, false);
	}

	/**
	 * 添加一个普通的子元素
	 * 
	 * @param doc
	 * @param eleKey
	 * @param eleValue
	 * @param isCDATA
	 *            指定是否是 CDATA数据
	 */
	private void addElement(Element doc, String eleKey, String eleValue,
			boolean isCDATA) {
		Element element = doc.addElement(eleKey);
		if (isCDATA) {
			element.addCDATA(eleValue);
		} else {
			element.setText(eleValue);
		}
	}

	/**
	 * 将文档转换为XML字符串
	 * 
	 * @param document
	 * @return
	 */
	private String getStringXML(Document document) {
		try {
			// 创建字符串缓冲区
			StringWriter stringWriter = new StringWriter();
			// 设置文件编码
			OutputFormat xmlFormat = new OutputFormat();
			xmlFormat.setEncoding("UTF-8");
			// 设置换行
			xmlFormat.setNewlines(true);
			// 生成缩进
			xmlFormat.setIndent(true);
			// 使用4个空格进行缩进, 可以兼容文本编辑器
			xmlFormat.setIndent("    ");
			// 创建写文件方法
			XMLWriter xmlWriter = new XMLWriter(stringWriter, xmlFormat);
			// 写入文件
			xmlWriter.write(document);
			// 关闭
			xmlWriter.close();
			// 输出xml
			// System.out.println(stringWriter.toString());
			return stringWriter.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解析XML(下单应答报文)
	 * 
	 * @param instance
	 * @return
	 */
	private WXOrderResponseBean parseOrderXml(String xml) {
		WXOrderResponseBean response = new WXOrderResponseBean();
		try {
			Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			response.setReturn_code(convertToString(root.element("return_code")));
			response.setReturn_msg(convertToString(root.element("return_msg")));
			if (SUCCESS.equals(response.getReturn_code())) {
				OrderResult result = new OrderResult();
				result.setAppid(convertToString(root.element("appid")));
				result.setMch_id(convertToString(root.element("mch_id")));
				result.setDevice_info(convertToString(root
						.element("device_info")));
				result.setNonce_str(convertToString(root.element("nonce_str")));
				result.setSign(convertToString(root.element("sign")));
				result.setResult_code(convertToString(root
						.element("result_code")));
				result.setErr_code(convertToString(root.element("err_code")));
				result.setErr_code_des(convertToString(root
						.element("err_code_des")));
				if (SUCCESS.equals(result.getResult_code())) {
					result.setTrade_type(convertToString(root
							.element("trade_type")));
					result.setPrepay_id(convertToString(root
							.element("prepay_id")));
					result.setCode_url(convertToString(root.element("code_url")));
				}
				response.setResult(result);
			}
			return response;
		} catch (DocumentException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 转化为字符串
	 * 
	 * @param obj
	 * @return
	 */
	private String convertToString(Element obj) {
		if (obj == null)
			return null;
		else
			return String.valueOf(obj.getData());
	}

	/**
	 * 下载对账单
	 * 
	 * @param bill
	 * @return
	 */
	public PrintBean downLoadBill(QueryBillBean bill) {
		String xml = createDownLoadBillXml(bill);
		String response = sendXMLData(xml, DOWN_LOAD_BILL_URL);
		PrintBean printBean = null;
		// String response =
		// "交易时间,公众账号ID,商户号,子商户号,设备号,微信订单号,商户订单号,用户标识,交易类型,交易状态,付款银行,货币种类,总金额,企业红包金额,微信退款单号,商户退款单号,退款金额,企业红包退款金额,退款类型,退款状态,商品名称,商户数据包,手续费,费率`2016-05-20 10:08:02,`wx16a0b09dbf94f380,`1345867901,`0,`,`4006652001201605206059282375,`160520000400054340,`omBzYwIpGZM3_q740Doggbcw-A_s,`APP,`SUCCESS,`CFT,`CNY,`1.00,`0.00,`0,`0,`0,`0,`,`,`160520000400054340,`支付测试,`0.01000,`0.60%`2016-05-20 17:26:42,`wx16a0b09dbf94f380,`1345867901,`0,`WEB,`4006652001201605206077277148,`160520000400054443,`omBzYwIpGZM3_q740Doggbcw-A_s,`APP,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`0,`0,`0,`0,`,`,`iPad,`北京太阳宫,`0.00000,`0.60%`2016-05-20 17:26:25,`wx16a0b09dbf94f380,`1345867901,`0,`WEB,`4006652001201605206078438536,`160520000400054442,`omBzYwIpGZM3_q740Doggbcw-A_s,`APP,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`0,`0,`0,`0,`,`,`iPad,`北京太阳宫,`0.00000,`0.60%总交易单数,总交易额,总退款金额,总企业红包退款金额,手续费总金额`3,`1.02,`0.00,`0.00,`0.01000";
		if ("ALL".equals(bill.getBill_type())) {
			printBean = parseDownLoadBill(response, BillTypeEnum.ALL);
		}
		if ("SUCCESS".equals(bill.getBill_type())) {
			printBean = parseDownLoadBill(response, BillTypeEnum.SUCCESS);
		}
		if ("REFUND".equals(bill.getBill_type())) {
			printBean = parseDownLoadBill(response, BillTypeEnum.REFUND);
		}

		return printBean;
	}

	/**
	 * 解析对账单
	 * 
	 * @param data
	 * @param type
	 */
	private PrintBean parseDownLoadBill(String data, BillTypeEnum type) {
		if(data.indexOf("No Bill Exist")>0){
    		return null;
    	}
        PrintBean print = null;
		try {
			int index1 = data.indexOf("`");
			int index2 = data.indexOf("总交易单数,总交易额,总退款金额,总企业红包退款金额,手续费总金额");
			String bodyTitle = data.substring(0, index1);
			String bodyDetail = data.substring(index1, index2);
			String footContent = data.substring(index2);
			int index3 = footContent.indexOf("`");
			String footTitle = footContent.substring(0, index3);
			String footDetail = footContent.substring(index3);
			print = new PrintBean(type.getCode(), 6);
			print.addAllTitle(bodyTitle);
			print.addAllContent(bodyDetail);
			print.addFootTitle(footTitle);
			print.addFootContent(footDetail);
			print.print();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
        return print;
	}

	private String createDownLoadBillXml(QueryBillBean bill) {
		// 生成新文档（根节点为xml）
		Document invokeAPI = genNewDoc("xml");
		Element root = invokeAPI.getRootElement();
		// 添加属性
		addElement(root, "appid", WXConfigure.getAppid());// 应用ID【固定】
		addElement(root, "bill_date", bill.getBill_date());// 附加数据
		addElement(root, "bill_type", bill.getBill_type());// 附加数据
		addElement(root, "device_info", WXConfigure.getDeviceInfo());// 设备号【固定】
		addElement(root, "mch_id", WXConfigure.getMchid());// 商户号【固定】
		addElement(root, "nonce_str",
				String.valueOf(System.currentTimeMillis()));// 随机字符串
		// 加签
		String sign = sign(getStringXML(invokeAPI));
		addElement(root, "sign", sign);// 签名
		String xml = getStringXML(invokeAPI);
		return xml;
	}

	public static void main(String[] args) throws WXVerifySignFailedException {

	}

	/**
	 * 解析微信回调通知
	 * 
	 * @param string
	 *            微信回调通知的xml
	 * @return 微信回调通知 Bean
	 */
	public PayResultBean parseResultXml(String xml) {
		PayResultBean response = new PayResultBean();
		try {
			Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			response.setReturn_code(convertToString(root.element("return_code")));
			response.setReturn_msg(convertToString(root.element("return_msg")));
			if (SUCCESS.equals(response.getReturn_code())) {
				response.setAppid(convertToString(root.element("appid")));
				response.setMch_id(convertToString(root.element("mch_id")));
				response.setDevice_info(convertToString(root
						.element("device_info")));
				response.setNonce_str(convertToString(root.element("nonce_str")));
				response.setSign(convertToString(root.element("sign")));
				response.setResult_code(convertToString(root
						.element("result_code")));
				response.setErr_code(convertToString(root.element("err_code")));
				response.setErr_code_des(convertToString(root
						.element("err_code_des")));
				response.setTrade_type(convertToString(root
						.element("trade_type")));
				response.setBank_type(convertToString(root.element("bank_type")));
				response.setTotal_fee(convertToString(root.element("total_fee")));
				response.setSettlement_total_fee(convertToString(root
						.element("settlement_total_fee")));
				response.setFee_type(convertToString(root.element("fee_type")));
				response.setCash_fee(convertToString(root.element("cash_fee")));
				response.setCash_fee_type(convertToString(root
						.element("cash_fee_type")));
				// response.setCoupon_fee(convertToString(root.element("coupon_fee")));
				// //代金券或立减优惠金额
				// response.setBank_type(convertToString(root.element("coupon_count")));//代金券或立减优惠使用数量
				// response.setBank_type(convertToString(root.element("coupon_id_$n")));//代金券或立减优惠优惠ID
				// response.setBank_type(convertToString(root.element("coupon_fee_$n")));//单个代金券或立减优惠支付金额
				response.setTransaction_id(convertToString(root
						.element("transaction_id")));
				response.setOut_trade_no(convertToString(root
						.element("out_trade_no")));
				response.setAttach(convertToString(root.element("attach")));
				response.setTime_end(convertToString(root.element("time_end")));
			}
			return response;
		} catch (DocumentException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 发送回调通知的响应
	 * 
	 * @param string
	 * @param string2
	 */
	public String createResponseResultXml(String code, String msg) {
		// 生成新文档（根节点为xml）
		Document invokeAPI = genNewDoc("xml");
		Element root = invokeAPI.getRootElement();
		// 添加属性
		addElement(root, "return_code", code);// 返回状态码
		addElement(root, "return_msg", msg);// 返回信息
		String xml = getStringXML(invokeAPI);
		return xml;
	}

	/**
	 * 关闭订单
	 * 
	 * @param out_trade_no
	 *            支付订单号
	 * @return
	 * @throws WXVerifySignFailedException
	 */
	public CloseOrderResultBean closeOrder(String out_trade_no)
			throws WXVerifySignFailedException {
		Document invokeAPI = genNewDoc("xml");
		Element root = invokeAPI.getRootElement();

		// 添加属性
		addElement(root, "appid", WXConfigure.getAppid());// 微信开放平台审核通过的应用APPID
		addElement(root, "mch_id", WXConfigure.getMchid());// 微信支付分配的商户号
		addElement(root, "out_trade_no", out_trade_no);// 商户系统内部的订单号
		addElement(root, "nonce_str", AESUtil.getAESKey().toUpperCase());// 随机字符串，不长于32位。推荐随机数生成算法

		// 加签
		String sign = sign(getStringXML(invokeAPI));
		addElement(root, "sign", sign);// 签名
		String xml = getStringXML(invokeAPI);

		// 发送报文
		String rtnXml = sendXMLData(xml, CLOSE_ORDER_URL);

		// 解析报文
		try {
			CloseOrderResultBean result = new CloseOrderResultBean();
			Map<String, Object> map = XMLParser.getMapFromXML(rtnXml);
			BeanUtils.populate(result, map);
			// 验签
			String mySign = null;
			try {
				mySign = SignUtil.getSignFromResponseString(rtnXml);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}

			log.info("【本地生成的应答报文签名】" + mySign);
			if (SUCCESS.equals(result.getReturn_code())) {
				if (mySign.equals(result.getSign())) {
					log.info("【验签成功】");
				} else {
					log.error("【验签失败】");
					return null;
				}
			}
			return result;
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	/**
	 * 转换短链接
	 * 
	 * @param url
	 * @return
	 * @throws WXVerifySignFailedException
	 */
	public ShortUrlResult shortUrl(String url)
			throws WXVerifySignFailedException {
		Document invokeAPI = genNewDoc("xml");
		Element root = invokeAPI.getRootElement();

		// 添加属性
		addElement(root, "appid", WXConfigure.getAppid());// 微信开放平台审核通过的应用APPID
		addElement(root, "mch_id", WXConfigure.getMchid());// 微信支付分配的商户号
		addElement(root, "long_url", url);// 商户系统内部的订单号
		addElement(root, "nonce_str", AESUtil.getAESKey().toUpperCase());// 随机字符串，不长于32位。推荐随机数生成算法

		// 加签
		String sign = sign(getStringXML(invokeAPI));
		addElement(root, "sign", sign);// 签名
		String xml = getStringXML(invokeAPI);

		// 发送报文
		String rtnXml = sendXMLData(xml, SHORT_URL);

		ShortUrlResult result = new ShortUrlResult();
		try {
			Map<String, Object> map = XMLParser.getMapFromXML(rtnXml);
			BeanUtils.populate(result, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 验签
		String mySign = null;
		try {
			mySign = SignUtil.getSignFromResponseString(rtnXml);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (log.isDebugEnabled())
			log.debug("【本地生成的应答报文签名】" + mySign);
		if (SUCCESS.equals(result.getReturn_code())) {
			if (mySign.equals(result.getSign())) {
				if (log.isDebugEnabled())
					log.debug("【验签成功】");
			} else {
				if (log.isDebugEnabled())
					log.debug("【验签失败】");
				throw new WXVerifySignFailedException();
			}
		}
		return result;
	}

	public RefundResultBean refund(RefundBean bean,String txnseqno)
			throws WXVerifySignFailedException {
		// 生成新文档（根节点为xml）
		Document invokeAPI = genNewDoc("xml");
		Element root = invokeAPI.getRootElement();
		// 添加属性
		addElement(root, "appid", WXConfigure.getAppid());// 微信开放平台审核通过的应用APPID
		addElement(root, "device_info", WXConfigure.getDeviceInfo());// 终端设备号
		addElement(root, "mch_id", WXConfigure.getMchid());// 微信支付分配的商户号
		addElement(root, "nonce_str", AESUtil.getAESKey().toUpperCase());// 随机字符串，不长于32位。推荐随机数生成算法
		addElement(root, "op_user_id", WXConfigure.getMchid());// 操作员帐号, 默认为商户号
		addElement(root, "out_refund_no", bean.getOut_refund_no());// 商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
		addElement(root, "out_trade_no", bean.getOut_trade_no());// 商户侧传给微信的订单号
		addElement(root, "refund_fee", bean.getRefund_fee());// 退款总金额，订单总金额，单位为分，只能为整数，详见支付金额
		addElement(root, "refund_fee_type", WXConfigure.getFeeType());// 货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
		addElement(root, "total_fee", bean.getTotal_fee());// 订单总金额，单位为分，只能为整数，详见支付金额
		addElement(root, "transaction_id", bean.getTransaction_id());// 微信生成的订单号，在支付通知中有返回
		// 加签
		String sign = sign(getStringXML(invokeAPI));
		addElement(root, "sign", sign);// 签名
		String xml = getStringXML(invokeAPI);
		tradeLogService.saveRequestLog(xml, txnseqno);
		// 发送报文
		String rtnXml = SSLSender.sendXml(xml);
		//
		tradeLogService.saveResponseLog(rtnXml, txnseqno);
		// 封装成Bean
		RefundResultBean result = new RefundResultBean();
		try {
			Map<String, Object> map = XMLParser.getMapFromXML(rtnXml);
			BeanUtils.populate(result, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 验签
		String mySign = null;
		try {
			mySign = SignUtil.getSignFromResponseString(rtnXml);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		log.info("【本地生成的应答报文签名】" + mySign);
		if (SUCCESS.equals(result.getReturn_code())) {
			if (mySign.equals(result.getSign())) {
				log.info("【验签成功】");
			} else {
				log.info("【验签失败】");
				throw new WXVerifySignFailedException();
			}
		}
		return result;
	}

	/**
	 * 退款查询
	 * 
	 * @param bean
	 * @return 返回查询结果
	 */
	public QueryRefundResultBean refundQuery(QueryRefundBean bean) {
		// 生成新文档（根节点为xml）
		Document invokeAPI = genNewDoc("xml");
		Element root = invokeAPI.getRootElement();
		// 添加属性
		addElement(root, "appid", WXConfigure.getAppid());// 微信开放平台审核通过的应用APPID
		addElement(root, "device_info", WXConfigure.getDeviceInfo());// 终端设备号
		addElement(root, "mch_id", WXConfigure.getMchid());// 微信支付分配的商户号
		addElement(root, "nonce_str", AESUtil.getAESKey().toUpperCase());// 随机字符串，不长于32位。推荐随机数生成算法
		if (StringUtil.isNotEmpty(bean.getOut_refund_no())) {
			addElement(root, "out_refund_no", bean.getOut_refund_no());// 商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
		} else if (StringUtil.isNotEmpty(bean.getOut_trade_no())) {
			addElement(root, "out_trade_no", bean.getOut_trade_no());// 商户侧传给微信的订单号
		} else if (StringUtil.isNotEmpty(bean.getRefund_id())) {
			addElement(root, "refund_id", bean.getRefund_id());// 微信生成的订单号，在支付通知中有返回
		} else if (StringUtil.isNotEmpty(bean.getTransaction_id())) {
			addElement(root, "transaction_id", bean.getTransaction_id());// 微信生成的订单号，在支付通知中有返回
		} else {
			log.error("【微信订单号】【商户订单号】【商户退款单号】【微信退款单号】四选一， 不可同时为空");
			return null;
		}

		// 加签
		String sign = sign(getStringXML(invokeAPI));
		addElement(root, "sign", sign);// 签名
		String xml = getStringXML(invokeAPI);

		// 发送报文
		String rtnXml = sendXMLData(xml, WXConfigure.REFUND_QUERY_URL);

		// 解析报文
		try {
			QueryRefundResultBean result = new QueryRefundResultBean();
			Map<String, Object> map = XMLParser.getMapFromXML(rtnXml);
			BeanUtils.populate(result, map);
			if (SUCCESS.equals(result.getReturn_code())) {
				if (result.getRefundSub() == null) {
					result.setRefundSub(new ArrayList<RefundSubInfo>());
				}
				int size = Integer.parseInt(result.getRefund_count());
				for (int i = 0; i < size; i++) {
					RefundSubInfo sub = new RefundSubInfo();
					sub.setOut_refund_no((String) map.get("out_refund_no_"
							+ String.valueOf(i)));
					sub.setRefund_id((String) map.get("refund_id_"
							+ String.valueOf(i)));
					sub.setRefund_channel((String) map.get("refund_channel_"
							+ String.valueOf(i)));
					sub.setRefund_fee((String) map.get("refund_fee_"
							+ String.valueOf(i)));
					sub.setCoupon_refund_count((String) map
							.get("coupon_refund_count_" + String.valueOf(i)));
					sub.setCoupon_refund_fee((String) map
							.get("coupon_refund_fee_" + String.valueOf(i)));
					sub.setRefund_status((String) map.get("refund_status_"
							+ String.valueOf(i)));
					sub.setRefund_recv_accout((String) map
							.get("refund_recv_accout_" + String.valueOf(i)));
					result.getRefundSub().add(sub);
				}
			}
			// 验签
			String mySign = null;
			try {
				mySign = SignUtil.getSignFromResponseString(rtnXml);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			if (log.isDebugEnabled())
				log.debug("【本地生成的应答报文签名】" + mySign);
			if (SUCCESS.equals(result.getReturn_code())) {
				if (mySign.equals(result.getSign())) {
					if (log.isDebugEnabled())
						log.debug("【验签成功】");
				} else {
					log.error("【验签失败】");
					return null;
				}
			}
			return result;
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	/**
	 * 查询订单
	 * 
	 * @param bean
	 * @return
	 */
	public QueryOrderResultBean queryOrder(QueryOrderBean bean) {
		// 生成新文档（根节点为xml）
		Document invokeAPI = genNewDoc("xml");
		Element root = invokeAPI.getRootElement();
		// 添加属性
		addElement(root, "appid", WXConfigure.getAppid());// 微信开放平台审核通过的应用APPID
		addElement(root, "mch_id", WXConfigure.getMchid());// 微信支付分配的商户号
		addElement(root, "nonce_str", AESUtil.getAESKey().toUpperCase());// 随机字符串，不长于32位。推荐随机数生成算法
		if (StringUtil.isNotEmpty(bean.getOut_trade_no())) {
			addElement(root, "out_trade_no", bean.getOut_trade_no());// 商户侧传给微信的订单号
		} else if (StringUtil.isNotEmpty(bean.getTransaction_id())) {
			addElement(root, "transaction_id", bean.getTransaction_id());// 微信生成的订单号，在支付通知中有返回
		} else {
			log.error("【微信订单号 】【商户订单号】二选一， 不可同时为空");
			return null;
		}

		// 加签
		String sign = sign(getStringXML(invokeAPI));
		addElement(root, "sign", sign);// 签名
		String xml = getStringXML(invokeAPI);

		// 发送报文
		String rtnXml = sendXMLData(xml, QUERY_TRADE_URL);
		// 解析报文
		try {
			QueryOrderResultBean result = new QueryOrderResultBean();
			Map<String, Object> map = XMLParser.getMapFromXML(rtnXml);
			BeanUtils.populate(result, map);
			// 验签
			String mySign = null;
			try {
				mySign = SignUtil.getSignFromResponseString(rtnXml);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			if (log.isDebugEnabled())
				log.debug("【本地生成的应答报文签名】" + mySign);
			if (SUCCESS.equals(result.getReturn_code())) {
				if (mySign.equals(result.getSign())) {
					if (log.isDebugEnabled())
						log.debug("【验签成功】");
				} else {
					log.error("【验签失败】");
					return null;
				}
			}
			return result;
		} catch (Exception e) {
			log.error(e);
		}

		return null;
	}
}
