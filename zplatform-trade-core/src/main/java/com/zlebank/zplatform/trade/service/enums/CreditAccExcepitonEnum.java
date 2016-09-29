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
	
	CR00("CR00","字段校验非法"),
	CR01("CR01","订单会员信息有误"),
	CR02("CR02","不存在此交易"),
	CR03("CR03","只处理授信账户充值业务"),
	CR04("CR04","只能是合作机构才能授理"),
	CR05("CR05","此合作机构不存在"),
	CR06("CR06","正常状态的合作机构才能处理此业务"),
	CR07("CR07","充值会员不存在"),
	CR08("CR08","会员所在的合作机构不一致"),
	CR09("CR09","合作机构未开通授信账户"),
	CR10("CR10","合作机构余额不足"),
	CR11("CR11","收款方未开通授信账户"),
	CR12("CR12","订单保存失败"),
	
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

