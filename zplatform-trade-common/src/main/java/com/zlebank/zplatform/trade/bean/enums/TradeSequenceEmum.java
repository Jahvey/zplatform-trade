/* 
 * TradeSequenceEmum.java  
 * 
 * version TODO
 *
 * 2016年8月5日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean.enums;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年8月5日 下午3:13:15
 * @since 
 */
public enum TradeSequenceEmum {

	SEQ_ZLPAY_ORDERNO("SEQ_ZLPAY_ORDERNO"),
	UNKNOW("");
	private String name;
	private TradeSequenceEmum(String name){
		this.name = name;
	}
	
	public static TradeSequenceEmum fromValue(String name){
		for(TradeSequenceEmum emum : values()){
			if(emum.name.equals(name)){
				return emum;
			}
		}
		return UNKNOW;
	}
	
	public String getName(){
		return this.name;
	}
}
