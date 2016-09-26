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
package com.zlebank.zplatform.wechat.qr;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.wechat.qr.service.WeChatQRService;

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
	private WeChatQRService weChatQRService;
	private static final Log log = LogFactory.getLog(CreateOrderTest.class);
	@Test
	//@Ignore
	public void test_createorder(){
		try {
			JSONObject creatOrder = weChatQRService.creatOrder("160824001400056001");
			log.info(creatOrder.toString());
		} catch (TradeException e) {
			e.printStackTrace();
		}
	}
	//@Test
	public void test_queryOrder(){
		
		ResultBean queryOrder = weChatQRService.queryOrder("160810001400055083");
		log.info("【交易查询结果】"+JSON.toJSONString(queryOrder));
		
	}
	
	/*public void test_jsonToObject(){
		String json = "{\"attach\":\"中少星火\",\"body\":"iphone","detail":"中少星火订单","goods_tag":"WXG","notify_url":"http://114.242.70.196:9086/zplatform-trade/notify/wxResult.htm","out_trade_no":"1608099000000762","time_expire":"20160809121046","time_start":"20160809111046","total_fee":"1"}"
	}*/
}
