/* 
 * RefundStatusEnum.java  
 * 
 * version TODO
 *
 * 2016年6月3日 
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
 * @date 2016年6月3日 下午5:01:11
 * @since
 */
public enum RefundStatusEnum {
	
	/**(01:待初审，09初审未过，11：待复审，19：复审未过，21：等待批处理，29：批处理失败，00：退款成功)**/
	
	/** 等待初审核 */
	FIRST_APPROVE("01"),
	/** 初核拒绝 */
	FIRST_REFUSED("09"),
	/** 待划拨审核 */
	SECOND_APPROVE("11"),
	/** 划拨审核拒绝 */
	SECOND_REFUSE("19"),
	/** 待转账审核 */
	WAIT_BANK_TRAN_APPROVE("21"),
	/** 划拨审核拒绝 */
	TRAN_REFUSE("29"),
	/**转账审核拒绝*/
	BANK_TRAN_REFUSE("39"),
	/**交易失败*/
	FAILED("49"),
	/**交易成功*/
	SUCCESS("00"),
	UNKNOW("99");
	private String code;

	/**
	 * @param code
	 */
	private RefundStatusEnum(String code) {
		this.code = code;
	}

	public static RefundStatusEnum fromValue(String value) {
        for(RefundStatusEnum status:values()){
            if(status.code==value){
                return status;
            }
        }
        return UNKNOW;
    }
    
    public String getCode(){
        return code;
    }
}
