package com.zlebank.zplatform.trade.service.enums;
/**
 * 账户改造错误码
 *
 * @author liusm
 * @version
 * @date 2016年9月5日 下午2:53:51
 * @since 
 */
public enum CreditAccExcepitonEnum {
	UNKNOWN("UNKNOWN","未知"),
	
	CA00("CA00","不存在此会员"),
	CA01("CA01","不存在此账户用途"),
	CA02("CA02","账户开户失败"),
	
	MG00("MA00","不存在此会员"),
	MG01("MA01","不存在此群组"),
	MG02("MA02","该行业应用组已禁用"),
	MG03("MA03","该会员已在群组"),
	MG04("MA04","该会员加入群组失败"),
	
	
	CR00("CR00","字段校验非法"),
	CR01("CR01","会员校验失败"),
	CR02("CR02","不存在此交易"),
	CR03("CR03","只授理授信账户充值业务"),
	CR04("CR04","只能是合作机构才能授理"),
	CR05("CR05","此合作机构不存在"),
	CR06("CR06","正常状态的合作机构才能处理此业务"),
	CR07("CR07","充值会员不存在"),
	CR08("CR08","会员所在的合作机构不一致"),
	CR09("CR09","合作机构未开通授信账户"),
	CR10("CR10","合作机构余额不足"),
	CR11("CR11","收款方未开通授信账户"),
	CR12("CR12","订单保存失败"),
	
	CM00("CM00","字段校验非法"),
	CM01("CM01","会员校验失败"),
	CM02("CM02","不存在此交易"),
	CM03("CM03","只授理授信账户消费业务"),
	CM04("CM04","充值会员不存在"),
	CM05("CM05","商户不存在"),
	CM06("CM06","请检查会员是否属于合作机构"),
	CM07("CM07","充值会员未开通授信账户"),
	CM08("CM08","余额不足"),
	CM09("CM09","找不到行业应用组"),
	CM10("CM10","该行业应用组已禁用"),
	CM11("CM11","充值会员的授信账户未加入行业应用组"),
	CM12("CM12","订单保存失败"),
	
	CF00("CF00","字段校验非法"),
	CF01("CF01","会员校验失败"),
	CF02("CF02","不存在此交易"),
	CF03("CF03","只授理授信账户退款业务"),
	CF04("CF04","商户不存在"),
	CF05("CF05","会员不存在"),
	CF06("CF06","找不到原订单"),
	CF07("CF07","找不到原交易流水"),
	CF08("CF08","退款交易时间已过期"),
	CF09("CF09","退款金额不能大于交易金额"),
	CF10("CF10","退款金额之和大于原始订单金额"),
	CF11("CF11","订单保存失败"),
	CF12("CF12","保存交易风控信息失败"),
	
	
	CURRENCY("CURRENCY","未找到此币钟"),
	GW03("GW03","获取商户版本信息错误");
	
	;

	private String errorCode; // 错误码
	private String errorMsg; // 错误信息

	private CreditAccExcepitonEnum(String errorCode, String errorMsg) {
		this.errorCode =  errorCode;
		this.errorMsg = errorMsg;
	}

	public static String  getMsg(String code){
        CreditAccExcepitonEnum message=CreditAccExcepitonEnum.valueOf(code);
        if(message==null){
        	message=UNKNOWN;
        }
       return message.getErrorMsg();
	};
	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	
	
	
}

