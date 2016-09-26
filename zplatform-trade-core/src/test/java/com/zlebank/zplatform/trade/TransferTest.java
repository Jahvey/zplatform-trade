package com.zlebank.zplatform.trade;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.commons.dao.pojo.BusiTypeEnum;
import com.zlebank.zplatform.trade.bean.gateway.TransferOrderBean;
import com.zlebank.zplatform.trade.bean.gateway.QueryAccBean;
import com.zlebank.zplatform.trade.bean.gateway.QueryAccResultBean;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.factory.AccountingAdapterFactory;
import com.zlebank.zplatform.trade.service.IAccoutTradeService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/*.xml")
public class TransferTest {
	private static final Log log = LogFactory.getLog(TransferTest.class);
	@Autowired
	private IAccoutTradeService transferService;
	
	@Test
	public void testOrder(){
		try {
			TransferOrderBean order = new TransferOrderBean();
			//订单信息
			order.setCoopInstiId("300000000000004");
			order.setFromMerId("100000000000640");
			order.setToMerId("100000000000642");
			order.setOrderId("2016090901");
			order.setOrderDesc("转账");
			order.setTxnAmt("100");
			order.setTxnTime("2016090901");
			//业务信息
			order.setTxnType("25");
			order.setTxnSubType("00");
			order.setBizType("000201");
			//异步通知信息
			order.setBackUrl("www.baidu.com");
			order.setFrontUrl("www.baidu");
			//超时信息
			//order.setPayTimeout("");
			order.setOrderTimeout("10000");
			String txnseqno = this.transferService.transfer(order);
			log.info("交易序列号"+txnseqno);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testTransferAccount(){
		try {
			//this.transferService.accountedFor("1609129900058119");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	@Test
	public void testQueryBalance(){
		QueryAccBean query =new QueryAccBean();
		query.setAccoutType("109");
		query.setMemberId("200000000000597");
		try {
			QueryAccResultBean result=this.transferService.queryMemberBalance(query);
			log.info("余额查询结果"+query.getMemberId()+JSON.toJSONString(result));
		} catch (TradeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
