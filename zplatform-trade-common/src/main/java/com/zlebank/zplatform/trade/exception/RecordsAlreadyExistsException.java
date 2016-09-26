/* 
 * NotInsteadWorkTimeException.java  
 * 
 * version v1.0
 *
 * 2015年11月25日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.exception;


/**
 * 记录已经存在
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月25日 下午2:10:30
 * @since 
 */
public class RecordsAlreadyExistsException extends TradeException {



    /**
     * @param code
     */
    public RecordsAlreadyExistsException() {
        super("ETSBT0001");
    }

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6063350038657235076L;

}
