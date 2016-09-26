package com.zlebank.zplatform.trade.chanpay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.httpclient.NameValuePair;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.trade.chanpay.bean.order.OrderItemBean;
import com.zlebank.zplatform.trade.chanpay.bean.order.SingleOrderBean;
import com.zlebank.zplatform.trade.chanpay.utils.ChanPayUtil;
import com.zlebank.zplatform.trade.chanpay.utils.HttpProtocolHandler;
import com.zlebank.zplatform.trade.chanpay.utils.HttpRequest;
import com.zlebank.zplatform.trade.chanpay.utils.HttpResponse;
import com.zlebank.zplatform.trade.chanpay.utils.HttpResultType;
import com.zlebank.zplatform.trade.chanpay.utils.MD5;
import com.zlebank.zplatform.trade.chanpay.utils.RSA;
import com.zlebank.zplatform.trade.utils.ConsUtil;



public class TempTest {

	public void sm() throws Exception {
		String MERCHANT_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKHbwQ85YEd2IhKifQEzJNHAYDj29148+6lNg81mDOJ3s8F9xmThA8e7KFKYe0seUVal8+3VXe+XOHTKUgqhrmpRDc+DzRmK1QdeBFTliiryuG3ZIeaRITx9jLViEEPCXDbmSn5lNLeBjx5i1XBlpORQTrSAlXx7/R4axTp2lEMjAgMBAAECgYABlys6fxHXIe4LyNT5ogsGlKFdbe/YWTkP3NciuZH+17ZIfHfqndtvpwMqbJ0pi864z0CqYaJerFm9rA9KU3RnSwx0H9aPQAeTlW378pMy4+qLCq9YHCNHXemKKPW4KD1ExrBqsUl5raeZz4m1DNcPcuQtWr5/T7kFf0MxPsEEYQJBAMwskKFVQUVjSCQgyTvS61vYJb3/DN9iSmKNsZZZSmFze00c39HeOd2dq6R7FMmcA6hkYU/i7+UKAGO/ZyMjdwUCQQDK8XmZ2TN3M+NlyY6e7LpgPoJzOy4sbHWbbkpa3kgaYSyCcXFgY59gpu2FTWdRQgFFeuxD1k2x2TCuzGGoZ5oHAkAts/QUCQ95RsYJQEWLXKVOg82+/+6Tul7IPMt5yjb6JW1+T25SfhoZ34diZCK9Fm1DLmUSCsyESn7X1SpzFSc5AkEAj3wrfZsTyDPnkw/uxm6ZV3LayJ4PB1mnzT0tVRHT6NLLpW6Pupa1GKDtTlJrugfw3i8K3OuoAxaMVQosAeU+AQJATuPYAz1UPBhEVLlXANsSLixcHFOh3LjXhYsB9V8kwHH8BKn5enaALeyyHMEQ5HPa+hCRxu/gI4E7+TFjHjvfSQ==";

		String charset = "UTF-8";
		Map<String, String> origMap = new HashMap<String, String>();
		origMap.put("version", "1.0");
		origMap.put("partner_id", "200000200008"); // 畅捷支付分配的商户号
		origMap.put("_input_charset", charset);
		origMap.put("is_anonymous", "Y");
		origMap.put("bank_code", "TESTBANK");
		origMap.put("out_trade_no", (UUID.randomUUID().toString()).replace("-", ""));
		origMap.put("pay_method", "1");
		origMap.put("pay_type", "C,DC");
		origMap.put("service", "cjt_create_instant_trade");
		origMap.put("trade_amount", "20.00");
		origMap.put("return_url", "http://58.30.231.28:8083/zplatform-trade/gateway/reciveSyncReaPay");
		/*List<OrderItemBean> itemList = new ArrayList<OrderItemBean>();
		OrderItemBean orderItem = new OrderItemBean();
		orderItem.setOut_trade_no((UUID.randomUUID().toString()).replace("-", ""));
		orderItem.setOrder_amount("10.00");
		orderItem.setSell_id_type("MEMBER_ID");
		OrderItemBean orderItem1 = new OrderItemBean();
		orderItem1.setOut_trade_no((UUID.randomUUID().toString()).replace("-", ""));
		orderItem1.setOrder_amount("10.00");
		orderItem1.setSell_id_type("MEMBER_ID");
		itemList.add(orderItem);
		itemList.add(orderItem1);
		origMap.put("prodInfo_list", JSON.toJSONString(itemList));*/
		
		
		try {
			String urlStr = "http://tpay.chanpay.com/mag/gateway/receiveOrder.do?"; 
			String resultString = this.buildRequest(origMap, "RSA", MERCHANT_PRIVATE_KEY, charset, urlStr);
			System.out.println(resultString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
     * 建立请求，以模拟远程HTTP的POST请求方式构造并获取钱包的处理结果
     * 如果接口中没有上传文件参数，那么strParaFileName与strFilePath设置为空值 如：buildRequest("",
     * "",sParaTemp)
     *
     * @param strParaFileName
     *            文件类型的参数名
     * @param strFilePath
     *            文件路径
     * @param sParaTemp
     *            请求参数数组
     * @return 钱包处理结果
     * @throws Exception
     */
    public static String buildRequest(Map<String, String> sParaTemp, String signType, String key,
                                      String inputCharset, String gatewayUrl) throws Exception {
        // 待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp, signType, key, inputCharset);

        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        // 设置编码集
        request.setCharset(inputCharset);

        request.setMethod(HttpRequest.METHOD_POST);

        request.setParameters(generatNameValuePair(sPara, inputCharset));
        request.setUrl(gatewayUrl);

        HttpResponse response = httpProtocolHandler.execute(request, null, null);
        if (response == null) {
            return null;
        }

        String strResult = response.getStringResult();

        return strResult;
    }
    
    /**
     * MAP类型数组转换成NameValuePair类型
     *
     * @param properties
     *            MAP类型数组
     * @return NameValuePair类型数组
     */
    private static NameValuePair[] generatNameValuePair(Map<String, String> properties, String charset) throws Exception{
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
//            nameValuePair[i++] = new NameValuePair(entry.getKey(), URLEncoder.encode(entry.getValue(),charset));
            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }

        return nameValuePair;
    }



	/**
     * 生成要请求给钱包的参数数组
     *
     * @param sParaTemp         请求前的参数数组
     * @return                  要请求的参数数组
     */
    public static Map<String, String> buildRequestPara(Map<String, String> sParaTemp,
                                                        String signType, String key,
                                                        String inputCharset) throws Exception {
        // 除去数组中的空值和签名参数
        Map<String, String> sPara = paraFilter(sParaTemp);
        // 生成签名结果
        String mysign = "";
        if ("MD5".equalsIgnoreCase(signType)) {
            mysign = buildRequestByMD5(sPara, key, inputCharset);
        } else if("RSA".equalsIgnoreCase(signType)){
            mysign = buildRequestByRSA(sPara, key, inputCharset);
        }

        // 签名结果与签名方式加入请求提交参数组中
        sPara.put("sign", mysign);
        sPara.put("sign_type", signType);

        return sPara;
    }
    
    /**
     * 生成MD5签名结果
     *
     * @param sPara
     *            要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestByMD5(Map<String, String> sPara, String key,
                                           String inputCharset) throws Exception {
        String prestr = createLinkString(sPara, false); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        mysign = MD5.sign(prestr, key, inputCharset);
        return mysign;
    }
	
	 /**
     * 生成RSA签名结果
     *
     * @param sPara
     *            要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestByRSA(Map<String, String> sPara, String privateKey,
                                           String inputCharset) throws Exception {
        String prestr = createLinkString(sPara, false); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        System.out.println("prestr:"+prestr);
        String mysign = "";
        mysign = RSA.sign(prestr, privateKey, inputCharset);
        return mysign;
    }
    
    
    
    
    
    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params
     *            需要排序并参与字符拼接的参数组
     * @param encode 是否需要urlEncode
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params, boolean encode) {

        //        params = paraFilter(params);

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        String charset = params.get("_input_charset");
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (encode) {
                try {
                    value = URLEncoder.encode(value, charset);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }
	
	/**
     * 除去数组中的空值和签名参数
     *
     * @param sArray
     *            签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            //            try {
            //                value = URLEncoder.encode(value,charset);
            //            } catch (UnsupportedEncodingException e) {
            //                e.printStackTrace();
            //            }
            result.put(key, value);
        }

        return result;
    }
    
    public static void main(String[] args) throws Exception {
		TempTest test = new TempTest();
		test.sm();
	}
}
