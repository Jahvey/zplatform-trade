package com.zlebank.zplatform.trade.exception;

import java.util.ResourceBundle;

import com.zlebank.zplatform.commons.exception.AbstractDescribeException;

public abstract class AbstractTradeDescribeException extends AbstractDescribeException{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -332189039978220348L;
    
    private static final  ResourceBundle RESOURCE = ResourceBundle.getBundle("exception_des");
    
    public ResourceBundle getResourceBundle() {
        return RESOURCE;
    }
}
