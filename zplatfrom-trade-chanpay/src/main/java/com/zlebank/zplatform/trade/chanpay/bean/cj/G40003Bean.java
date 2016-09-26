package com.zlebank.zplatform.trade.chanpay.bean.cj;

/**
 * G40003接口  Bean
 * @author Thinker
 *
 */
public class G40003Bean extends Gw01MsgBase{
	/**账单类型-普通00**/
	public static String BILL_TYPE="00";
	/**状态-成功【000】**/
	public static String STATUS_SUCESS="1000";
	
	private String billType;
	private String billMonth;
	private String billDay;
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getBillMonth() {
		return billMonth;
	}
	public void setBillMonth(String billMonth) {
		this.billMonth = billMonth;
	}
	public String getBillDay() {
		return billDay;
	}
	public void setBillDay(String billDay) {
		this.billDay = billDay;
	}
	
	
}
