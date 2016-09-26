/* 
 * BossPayServiceTest.java  
 * 
 * version TODO
 *
 * 2016年4月6日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.bosspay;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bosspay.bean.BtchcolltnItemBean;
import com.zlebank.zplatform.trade.bosspay.bean.BtchcolltnRequestBean;
import com.zlebank.zplatform.trade.bosspay.bean.BtchpmtItemBean;
import com.zlebank.zplatform.trade.bosspay.bean.BtchpmtRequestBean;
import com.zlebank.zplatform.trade.bosspay.service.BossPayService;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.DateUtil;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年4月6日 下午2:58:55
 * @since 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/*.xml")
public class BossPayServiceTest {

	@Autowired
	private BossPayService bossPayService;
	@Test
	public void test_BossPayService(){
		TradeBean trade = new TradeBean();
		trade.setCardNo("6217994280006898308");
		trade.setAcctName("黄雯");
		trade.setAmount("100");
		bossPayService.realCollecting(trade);
		
	}
	

	public void test_query(){
		bossPayService.queryRealCollecting("MS04201604200000000000000006");
	}
	
	
	public void test_realInsteadPay(){
		TradeBean trade = new TradeBean();
		trade.setCardNo("6217994280006898308");
		trade.setAcctName("黄雯");
		trade.setAmount("100");
		bossPayService.realInsteadPay(trade);
		//MS04201604210000000000000001
	}
	
	
	public void test_insteadpayquery(){
		bossPayService.queryRealInsteadPay("MS04201604210000000000000015");
	}
	//@Test
	public void test_batchInsteadPay(){
		BtchpmtItemBean itemBean = new BtchpmtItemBean(DateUtil.getCurrentDateTime()+"1",
				"6217994280006898308", 
				"黄雯",
				"313551080046",//bankNumber,
				"中国邮政储蓄银行股份有限公司江西省分行",//bankName, 
				"江西恒邦",//bankProvince, 
				"江西恒邦",//bankCity, 
				"100",
				"09001" );
		BtchpmtItemBean itemBean2 = new BtchpmtItemBean(DateUtil.getCurrentDateTime()+"1",
				"6217994280006898308", 
				"黄雯", 
				"313551080046",//bankNumber,
				"中国邮政储蓄银行股份有限公司江西省分行",//bankName, 
				"江西恒邦",//bankProvince, 
				"江西恒邦",//bankCity, 
				"120",
				"09001" );
		List<BtchpmtItemBean> itemList =new ArrayList<BtchpmtItemBean>();
		itemList.add(itemBean);
		itemList.add(itemBean2);
		BtchpmtRequestBean requestBean = new BtchpmtRequestBean(ConsUtil.getInstance().cons.getBosspay_bank_account(),
				"1000000004", "123","33",
				ConsUtil.getInstance().cons.getBosspay_user_key(),itemList);
		bossPayService.batchInsteadPay(requestBean);
	}
	
	public void test_qeruyBatchInsteadPay(){
		bossPayService.queryBatchInsteadPay("MS04201604210000000000000015");
	}
	
	public void test_batchCollectiong(){
		BtchcolltnItemBean itemBean = new BtchcolltnItemBean(
				ConsUtil.getInstance().cons.getBosspay_agreement_id(),
				"6217994280006898308", 
				"黄雯", 
				"313551080046",//bankNumber,
				"中国邮政储蓄银行股份有限公司江西省分行",//bankName, 
				"江西恒邦",//bankProvince, 
				"江西恒邦",//bankCity, 
				"100",
				"09001" );
		BtchcolltnItemBean itemBean2 = new BtchcolltnItemBean(
				ConsUtil.getInstance().cons.getBosspay_agreement_id(),
				"6217994280006898308", 
				"黄雯", 
				"313551080046",//bankNumber,
				"中国邮政储蓄银行股份有限公司江西省分行",//bankName, 
				"江西恒邦",//bankProvince, 
				"江西恒邦",//bankCity, 
				"120",
				"09001" );
		List<BtchcolltnItemBean> itemList =new ArrayList<BtchcolltnItemBean>();
		itemList.add(itemBean);
		itemList.add(itemBean2);
		BtchcolltnRequestBean requestBean = new BtchcolltnRequestBean(ConsUtil.getInstance().cons.getBosspay_bank_account(),
				 "123","33",
				ConsUtil.getInstance().cons.getBosspay_user_key(),itemList);
		bossPayService.batchCollecting(requestBean);
	}
	
	
	public void test_qeruyBatchCollecting(){
		bossPayService.queryBatchCollection("MS04201604210000000000000015");
	}
	
	
	public void test_json(){
		List<String> list = new ArrayList<String>();
		list.add("123");
		list.add("456");
		String json = JSON.toJSON(list).toString();
		
		List<?> jsonList = JSON.parseArray(json);
		System.out.println(jsonList.get(0));
		System.out.println(jsonList.get(1));
	}
}



