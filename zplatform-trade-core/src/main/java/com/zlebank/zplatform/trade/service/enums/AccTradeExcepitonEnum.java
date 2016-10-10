/* 
 * ExcepitonTypeEnum.java  
 * 
 * version TODO
 *
 * 2016年9月5日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.enums;


/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年9月5日 下午2:53:51
 * @since 
 */
public enum AccTradeExcepitonEnum {
	UNKNOWN("UNKNOWN","未知"),
	
	AQ00("AQ00","字段校验非法"),
	AQ01("AQ01","查询余额失败"),
	AQ02("AQ02","账户用途不存在"),
	
	TE00("TE00","非法字段"),
	TE01("TE01","订单会员信息有误"),
	TE02("TE02","不存在此交易"),
	TE03("TE03","只处理转账业务"),
	TE04("TE04","付款方不存在"),
	TE05("TE05","收款方不存在"),
	TE06("TE06","付款方未开通基本账户"),
	TE07("TE07","付款方余额不足"),
	TE08("TE08","收款方未开通基本账户"),
	TE09("TE09","订单保存失败"),
	
	BC00("BC00","字段校验非法"),
	BC01("BC01","订单会员信息有误"),
	BC02("BC02","不存在此交易"),
	BC03("BC03","只处理保证金业务"),
	BC04("BC04","充值会员不存在"),
	BC05("BC05","未开通保证金账户"),
	BC06("BC06","企业信息不存在"),
	BC07("BC07","法人信息有误"),
	BC08("BC08","此业务只允许企业会员"),
	BC09("BC09","订单保存失败"),
	
	
	BW00("BW00","字段校验非法"),
	BW01("BW01","订单会员信息有误"),
	BW02("BW02","不存在此交易"),
	BW03("BW03","只允许保证金提取业务"),
	BW04("BW04","提取会员不存在"),
	BW05("BW05","请检查提取会员资金账户是否正常"),
	BW06("BW06","请检查提取会员保证金账户是否正常"),
	BW07("BW07","保证金余额不足"),
	BW08("BW08","此业务只允许企业会员"),
	BW09("BW09","订单保存失败"),
	
	
	CURRENCY("CURRENCY","未找到此币钟"),
	PAYPWD_EMPTY("PAYPWD_EMPTY","请先设支付密码"),
	PAYPWD_ERROR("PAYPWD_ERROR","支付密码错识"),
	GW03("GW03","获取商户版本信息错误");
	;

	private String errorCode; // 错误码
	private String errorMsg; // 错误信息

	private AccTradeExcepitonEnum(String errorCode, String errorMsg) {
		this.errorCode =  errorCode;
		this.errorMsg = errorMsg;
	}

	public static String  getMsg(String code){
        AccTradeExcepitonEnum message=AccTradeExcepitonEnum.valueOf(code);
       return message.getErrorMsg();
	};
	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	
	
	
}
