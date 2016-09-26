/* 
 * BossInsteadPayServiceTest.java  
 * 
 * version TODO
 *
 * 2016年4月22日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.bosspay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.trade.bosspay.bean.BtchpmtResultBean;
import com.zlebank.zplatform.trade.bosspay.insteadpay.BossPayInsteadPayThreadPool;
import com.zlebank.zplatform.trade.bosspay.service.BossInsteadPayService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年4月22日 下午1:07:11
 * @since 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/*.xml")
public class BossInsteadPayServiceTest {
	
	@Autowired
	private BossInsteadPayService bossInsteadPayService;
	
	
	@Transactional
	public void test_batchInsteadPay(){
		new BossPayInsteadPayThreadPool().batchPay("166");
	}
	
	
	@Test
	@Transactional
	public void test_dealwithResult(){
		List<BtchpmtResultBean> resultList = new ArrayList<BtchpmtResultBean>();
		BtchpmtResultBean itemBean = new BtchpmtResultBean();
		itemBean.setSerialNum("111111");
        itemBean.setBankAccount("6226091212413805");
        itemBean.setBankAccountName("鲁晓帅");
        itemBean.setAmount("100");
        itemBean.setStatus("PR02");
		
        BtchpmtResultBean itemBean2 = new BtchpmtResultBean();
        itemBean2.setSerialNum("222222");
        itemBean2.setBankAccount("6226091212413805");
        itemBean2.setBankAccountName("鲁晓帅");
        itemBean2.setAmount("20");
        itemBean2.setStatus("PR02");
        resultList.add(itemBean);
        resultList.add(itemBean2);
		bossInsteadPayService.dealWithResult("123456789", resultList);
	}
	
	public static void main(String[] args) {
		Map<String, Object> value = new HashMap<String, Object>();
		Long channelFee = Long.valueOf(StringUtil.isEmpty(value.get("CFEE")+"")?"0":value.get("CFEE")+"")+Long.valueOf(StringUtil.isEmpty(value.get("DFEE")+"")?"0":value.get("DFEE")+"");
		System.out.println(channelFee);
	}
}
