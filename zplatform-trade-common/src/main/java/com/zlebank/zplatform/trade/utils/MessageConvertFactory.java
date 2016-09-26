/**
 *  File: MessageConvertFactory.java
 *  Description:
 *  Copyright © 2004-2013 hnapay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-26   Terry_ma    Create
 *
 */
package com.zlebank.zplatform.trade.utils;

import java.util.ResourceBundle;



/**
 * 
 */
public class MessageConvertFactory {
	   private final static ResourceBundle RESOURCE = ResourceBundle.getBundle("gateway_exception");
	   
	   public static String getMessage(String code) {
	        String message = RESOURCE.getString(code);
	        return message;
	    }
	
	
}
