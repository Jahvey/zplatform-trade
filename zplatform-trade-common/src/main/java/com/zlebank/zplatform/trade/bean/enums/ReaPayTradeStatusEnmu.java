/* 
 * ReaPayTradeStatusEnmu.java  
 * 
 * version TODO
 *
 * 2016年7月26日 
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
 * @date 2016年7月26日 上午9:04:38
 * @since 
 */
public enum ReaPayTradeStatusEnmu {
	/**交易成功*/
	COMPLETED("completed"),
	/**交易失败*/
	FAILED("failed"),
	/**等待支付*/
	WAIT("wait"),
	/**支付中*/
	PROCESSING("processing"),
	UNKNOW("99");
	private String status;

	/**
	 * @param code
	 */
	private ReaPayTradeStatusEnmu(String status) {
		this.status = status;
	}

	public static ReaPayTradeStatusEnmu fromValue(String value) {
        for(ReaPayTradeStatusEnmu status:values()){
            if(status.status.equals(value)){
                return status;
            }
        }
        return UNKNOW;
    }
    
    public String getStatus(){
        return status;
    }
}
