/* 
 * ChanPayServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年4月29日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.chanpay.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.trade.chanpay.bean.ReturnMessageBean;
import com.zlebank.zplatform.trade.chanpay.bean.async.RefundAsyncResultBean;
import com.zlebank.zplatform.trade.chanpay.bean.async.TradeAsyncResultBean;
import com.zlebank.zplatform.trade.chanpay.bean.cj.G40003Bean;
import com.zlebank.zplatform.trade.chanpay.bean.order.RefundOrderBean;
import com.zlebank.zplatform.trade.chanpay.bean.query.BankItemBean;
import com.zlebank.zplatform.trade.chanpay.bean.query.QueryBankBean;
import com.zlebank.zplatform.trade.chanpay.bean.query.QueryBankResultBean;
import com.zlebank.zplatform.trade.chanpay.bean.query.QueryTradeBean;
import com.zlebank.zplatform.trade.chanpay.service.ChanPayService;
import com.zlebank.zplatform.trade.chanpay.service.impl.CjMsgSendService.sendAndGetBytes_Response;
import com.zlebank.zplatform.trade.chanpay.utils.Cj;
import com.zlebank.zplatform.trade.chanpay.utils.CjSignHelper;
import com.zlebank.zplatform.trade.chanpay.utils.HttpProtocolHandler;
import com.zlebank.zplatform.trade.chanpay.utils.HttpRequest;
import com.zlebank.zplatform.trade.chanpay.utils.HttpResponse;
import com.zlebank.zplatform.trade.chanpay.utils.HttpResultType;
import com.zlebank.zplatform.trade.chanpay.utils.RSA;
import com.zlebank.zplatform.trade.chanpay.utils.U;
import com.zlebank.zplatform.trade.utils.ConsUtil;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年4月29日 下午1:23:37
 * @since
 */
@Service("chanPayService")
public class ChanPayServiceImpl implements ChanPayService {

	private static final Log log = LogFactory.getLog(ChanPayServiceImpl.class);
	
	@Autowired
	private CjMsgSendService cjMsgSendService;
	/**
	 *
	 * @param refundOrderBean
	 * @return
	 */
	@Override
	public ReturnMessageBean refund(RefundOrderBean refundOrderBean) {
		// TODO Auto-generated method stub
		log.info("chanpay refund:"+JSON.toJSONString(refundOrderBean));
		try {
			String sign = RSA.sign(buildParamter(refundOrderBean),
					ConsUtil.getInstance().cons.getChanpay_private_key(),
					ConsUtil.getInstance().cons.getChanpay_input_charset());
			refundOrderBean.setSign(sign);
			refundOrderBean.setSign_type(ConsUtil.getInstance().cons
					.getChanpay_sign_type());
			String resultString = buildRequest(refundOrderBean, "RSA", ConsUtil.getInstance().cons
					.getChanpay_input_charset(), ConsUtil.getInstance().cons
					.getChanpay_url());
			log.info("recevie message:"+resultString);
			return JSON.parseObject(resultString, ReturnMessageBean.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	
	public QueryTradeBean queryTrade(QueryTradeBean queryTradeBean){
		try {
			String sign = RSA.sign(buildParamter(queryTradeBean),
					ConsUtil.getInstance().cons.getChanpay_private_key(),
					ConsUtil.getInstance().cons.getChanpay_input_charset());
			queryTradeBean.setSign(sign);
			queryTradeBean.setSign_type(ConsUtil.getInstance().cons
					.getChanpay_sign_type());
			String resultString = buildRequest(queryTradeBean, "RSA", ConsUtil.getInstance().cons
					.getChanpay_input_charset(), ConsUtil.getInstance().cons
					.getChanpay_url());
			log.info("recevie message:"+resultString);
			return JSON.parseObject(resultString, QueryTradeBean.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<BankItemBean> queryBank(QueryBankBean queryBankBean){
		try {
			String sign = RSA.sign(buildParamter(queryBankBean),
					ConsUtil.getInstance().cons.getChanpay_private_key(),
					ConsUtil.getInstance().cons.getChanpay_input_charset());
			queryBankBean.setSign(sign);
			queryBankBean.setSign_type(ConsUtil.getInstance().cons
					.getChanpay_sign_type());
			String resultString = buildRequest(queryBankBean, "RSA", ConsUtil.getInstance().cons
					.getChanpay_input_charset(), ConsUtil.getInstance().cons
					.getChanpay_url());
			log.info("recevie message:"+resultString);
			 QueryBankResultBean resultBean = JSON.parseObject(resultString, QueryBankResultBean.class);
			 if("T".equals(resultBean.getIs_success())){
				 return JSON.parseArray(resultBean.getPay_inst_list(), BankItemBean.class);
			 }
			 return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean asyncNotifyTrade(TradeAsyncResultBean tradeAsyncResultBean){
		boolean testTrue = false;
		try {
			
			String sign = tradeAsyncResultBean.getSign();
			//String sign_type = tradeAsyncResultBean.getSign_type();
			tradeAsyncResultBean.setSign(null);
			tradeAsyncResultBean.setSign_type(null);
			String msg = buildParamter(tradeAsyncResultBean);
					//ChanPayUtil.generateParamer(buildParamter(tradeAsyncResultBean), true, new String[]{"sign","sign_type"});
			testTrue = RSA.verify(msg, sign, ConsUtil.getInstance().cons.getChanpay_public_key(), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return testTrue;
	}
	
	public boolean asyncNotifyRefund(RefundAsyncResultBean refundAsyncResultBean){
		boolean flag = false;
		try {
			String sign = refundAsyncResultBean.getSign();
			//String sign_type = refundAsyncResultBean.getSign_type();
			refundAsyncResultBean.setSign(null);
			refundAsyncResultBean.setSign_type(null);
			String msg = buildParamter(refundAsyncResultBean);
			flag = RSA.verify(msg, sign, ConsUtil.getInstance().cons.getChanpay_public_key(), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flag;
	}
	

	private String buildParamter(Object orderBean) {
		Map<String, Object> parseObject = JSON.parseObject(JSON.toJSONString(orderBean), Map.class);
		List<String> keys = new ArrayList<String>(parseObject.keySet());
		Collections.sort(keys);
		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = parseObject.get(key).toString();

			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}
		System.out.println("prestr:" + prestr);
		return prestr;
	}

	public static String buildRequest(Object order,
			String signType, String inputCharset, String gatewayUrl)
			throws Exception {
		// 待请求参数数组
		Map<String, String> sPara = JSON.parseObject(JSON.toJSONString(order), Map.class);

		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler
				.getInstance();

		HttpRequest request = new HttpRequest(HttpResultType.BYTES);
		// 设置编码集
		request.setCharset(inputCharset);

		request.setMethod(HttpRequest.METHOD_POST);

		request.setParameters(generatNameValuePair(sPara, inputCharset));
		request.setUrl(gatewayUrl);

		HttpResponse response = httpProtocolHandler
				.execute(request, null, null);
		if (response == null) {
			return null;
		}

		String strResult = response.getStringResult();

		return strResult;
	}
	private static NameValuePair[] generatNameValuePair(Map<String, String> properties, String charset) throws Exception{
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
//            nameValuePair[i++] = new NameValuePair(entry.getKey(), URLEncoder.encode(entry.getValue(),charset));
            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }

        return nameValuePair;
    }


	@Override
	public sendAndGetBytes_Response getRecAccFile(G40003Bean data) {
		sendAndGetBytes_Response res=null;
	       try {
				String cjReqmsg = buildCjmsg(data);
				// 签名
				CjSignHelper singHelper = new CjSignHelper();
				String signMsg = singHelper.signXml$Add(cjReqmsg);
			    res= cjMsgSendService.sendAndGetBytes(signMsg);
			} catch (Exception e) {
				log.error("下载畅捷对账文件报错"+e.getMessage());
				e.printStackTrace();
			}
			return res;
	}

	public String buildCjmsg(G40003Bean data) {
		Document doc = DocumentHelper.createDocument();
		Element msgEl = doc.addElement("MESSAGE");

		Element infoEl = msgEl.addElement("INFO");
		infoEl.addElement("TRX_CODE").setText(Cj.XMLMSG_TRANS_CODE_对账文件下载);
		infoEl.addElement("VERSION").setText(Cj.XMLMSG_VERSION_01);
		infoEl.addElement("MERCHANT_ID").setText(U.nvl(data.getMertid()));
		infoEl.addElement("REQ_SN").setText(U.nvl(data.getReqSn()));
		infoEl.addElement("TIMESTAMP").setText(U.getCurrentTimestamp());
		infoEl.addElement("SIGNED_MSG").setText("");

		Element bodyEl = msgEl.addElement("BODY");
		bodyEl.addElement("BILL_TYPE").setText(U.nvl(data.getBillType()));
		if(data.getBillMonth()!=null){
			bodyEl.addElement("BILL_MONTH").setText(U.nvl(data.getBillMonth()));
		}else if(data.getBillDay()!=null){
			bodyEl.addElement("BILL_DAY").setText(U.nvl(data.getBillDay()));
		}
		String xml = Cj.formatXml_UTF8(doc);
		log.info("产生G40003下载对账单：" + U.substringByByte(xml, 512));
		return xml;
	}
	
	
}
