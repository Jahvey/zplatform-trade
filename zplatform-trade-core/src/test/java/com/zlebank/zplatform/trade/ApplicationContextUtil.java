/* 
 * ApplicationContextUtil.java  
 * 
 * version TODO
 *
 * 2015年8月21日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Class Description
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年8月21日 下午3:18:20
 * @since 
 */
public class ApplicationContextUtil {
    public static ApplicationContext get() {
        return new ClassPathXmlApplicationContext("/AccountContextTest.xml");
    }
}
