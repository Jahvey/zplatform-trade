package com.zlebank.zplatform.wechat.qr.wx.common;

import java.net.InetAddress;



public class WXConfigure {
    
    /**退款URL**/
										  //https://api.mch.weixin.qq.com/secapi/pay/refund
    public static final String REFUND_URL= "https://api.mch.weixin.qq.com/secapi/pay/refund";
    
    /**退款查询URL**/                              //https://api.mch.weixin.qq.com/pay/refundquery
    public static final String REFUND_QUERY_URL= "https://api.mch.weixin.qq.com/pay/refundquery";
    
    // 微信分配的KEY
	private static String key = "147852369987456321liuliushuaishu";

	//微信分配的公众号ID（开通公众号之后可以获取到）
	private static String appID = "wx88ad6d2878c15e87";

	//微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
	private static String mchID = "1375204802";
	
	// 证书地址  // TODO: linux下要修改
	private static String cerUrl = "D:\\cert\\wechat_QR\\apiclient_cert_qr.p12";


	public static void setKey(String key) {
		WXConfigure.key = key;
	}

	public static void setAppID(String appID) {
		WXConfigure.appID = appID;
	}

	public static void setMchID(String mchID) {
		WXConfigure.mchID = mchID;
	}

	public static String getKey(){
		return key;
	}
	
	public static String getAppid(){
		return appID;
	}
	
	public static String getMchid(){
		return mchID;
	}
	/**
	 * 得到IP地址
	 * @return
	 */
	public static String getIp() {
        try {
            InetAddress ia=InetAddress.getLocalHost();
            return ia.getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}
	/**
	 * 得到货币类型
	 * @return
	 */
	public static String getFeeType() {
	    return "CNY";
	}
	/**
	 * 得到设备号
	 */
	 public static String getDeviceInfo() {
	      return "WEB";
	  }
	 /**
	  * 得到交易类型,原生扫码支付
	  * @return
	  */
	 public static String getTradeType() {
	     return "NATIVE";
	 }
	 
	 public static String getCerUrl() {
	     return cerUrl;
	 }
}
