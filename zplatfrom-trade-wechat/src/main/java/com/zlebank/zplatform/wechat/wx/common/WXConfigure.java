package com.zlebank.zplatform.wechat.wx.common;

import java.net.InetAddress;

import com.zlebank.zplatform.trade.utils.ConsUtil;


public class WXConfigure {
    
    /**退款URL**/
    public static final String REFUND_URL= ConsUtil.getInstance().cons.getWechat_refund_url();//"https://api.mch.weixin.qq.com/secapi/pay/refund";
    
    /**退款查询URL**/
    public static final String REFUND_QUERY_URL= ConsUtil.getInstance().cons.getWechat_refund_query_url();//"https://api.mch.weixin.qq.com/pay/refundquery";
    
    // 微信分配的KEY
	private static String key = ConsUtil.getInstance().cons.getWechat_key();//"1198aa343b3fb1690fd7ce703784113d";

	//微信分配的公众号ID（开通公众号之后可以获取到）
	private static String appID = ConsUtil.getInstance().cons.getWechat_appID();//"wx16a0b09dbf94f380";

	//微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
	private static String mchID = ConsUtil.getInstance().cons.getWechat_mchID();//"1345867901";
	
	// 证书地址  // TODO: linux下要修改
	private static String cerUrl = ConsUtil.getInstance().cons.getWechat_cerUrl();//"D:/10016225.p12";


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
	  * 得到交易类型
	  * @return
	  */
	 public static String getTradeType() {
	     return "APP";
	 }
	 
	 public static String getCerUrl() {
	     return cerUrl;
	 }
}
