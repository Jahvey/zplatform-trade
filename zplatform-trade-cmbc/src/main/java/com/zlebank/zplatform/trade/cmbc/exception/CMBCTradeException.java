/* 
 * BaseException.java  
 * 
 * version TODO
 *
 * 2015年9月6日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.cmbc.exception;

import java.util.ResourceBundle;

import com.zlebank.zplatform.commons.exception.AbstractDescribeException;


/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月6日 下午2:43:58
 * @since 
 */
public class CMBCTradeException extends AbstractDescribeException{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3716682104238215841L;
    private static final  ResourceBundle RESOURCE = ResourceBundle.getBundle("exception_des");
    
    public ResourceBundle getResourceBundle() {
        return RESOURCE;
    }
    private String code;
    public CMBCTradeException(String code,Object ...param) {
        this.code=code;
        this.setParams(param);
    }
    public CMBCTradeException(String code) {
        this.code=code;
    }
    /**
     *
     * @return
     */
    @Override
    public String getCode() {
        return code;
    }

}
