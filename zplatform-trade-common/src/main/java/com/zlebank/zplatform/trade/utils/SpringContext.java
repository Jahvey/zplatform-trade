/* 
 * AutowireInjector.java  
 * 
 * version TODO
 *
 * 2015年9月6日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月6日 下午5:57:57
 * @since 
 */
public class SpringContext implements ApplicationContextAware{

    protected static ApplicationContext context;
    
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }

}
