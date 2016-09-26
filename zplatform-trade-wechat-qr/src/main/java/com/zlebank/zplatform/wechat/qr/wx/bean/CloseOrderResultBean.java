package com.zlebank.zplatform.wechat.qr.wx.bean;

public class CloseOrderResultBean {

	/**返回状态码**/
    private String return_code;
    /**返回信息**/
    private String return_msg;
    /**业务结果**/
    private String result_code;
    /**业务结果描述**/
    private String result_msg;
    /**错误代码**/
    private String err_code;
    /**错误代码描述**/
    private String err_code_des;
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
	public String getResult_msg() {
		return result_msg;
	}
	public void setResult_msg(String result_msg) {
		this.result_msg = result_msg;
	}
	public String getErr_code() {
		return err_code;
	}
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	public String getErr_code_des() {
		return err_code_des;
	}
	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
    
    
}
