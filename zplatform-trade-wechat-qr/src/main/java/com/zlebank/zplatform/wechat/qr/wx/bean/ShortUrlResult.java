package com.zlebank.zplatform.wechat.qr.wx.bean;

public class ShortUrlResult {

	
	/**返回状态码**/
    private String return_code;
    /**返回信息**/
    private String return_msg;
    /**业务结果**/
    private String result_code;
    /**错误码**/
    private String err_code;
    /**URL链接**/
    private String short_url;
    /**签名**/
    private String sign;
    
    
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public String getReturn_msg() {
		return return_msg;
	}
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getErr_code() {
		return err_code;
	}
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	public String getShort_url() {
		return short_url;
	}
	public void setShort_url(String short_url) {
		this.short_url = short_url;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
    
}
