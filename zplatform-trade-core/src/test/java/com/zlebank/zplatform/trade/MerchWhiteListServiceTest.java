package com.zlebank.zplatform.trade;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.zlebank.zplatform.trade.service.MerchWhiteListService;

public class MerchWhiteListServiceTest {
	private MerchWhiteListService  merchWhiteListService;
	
	    @Before
	    public void init() {
	        ApplicationContext context = ApplicationContextUtil.get();
	        merchWhiteListService = (MerchWhiteListService) context
	                .getBean("merchWhiteListService");
	    }
	    
	    @Test
	    public void checkMerchWhiteList(){
	    	String result =this.merchWhiteListService.checkMerchWhiteList("11", "22", "33");
	    	 System.out.println("------checkWhiteList"+result);
	    }
}
