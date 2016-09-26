/* 
 * CreateOrderTest.java  
 * 
 * version TODO
 *
 * 2016年7月25日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatfrom.wechat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zlebank.zplatform.wechat.service.WeChatService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年7月25日 下午3:04:32
 * @since 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/*.xml")
public class CreateOrderTest {

	@Autowired
	private WeChatService weChatService;
	
	@Test
	public void test_createorder(){
		weChatService.creatOrder("160725001400054239");
	}
	
	
}
