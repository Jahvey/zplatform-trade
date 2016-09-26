/* 
 * ZLPayTest.java  
 * 
 * version TODO
 *
 * 2016年6月22日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.zlpay;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.zlpay.MarginSmsBean;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.DateUtil;
import com.zlebank.zplatform.trade.utils.OrderNumber;
import com.zlebank.zplatform.trade.zlpay.service.IZlTradeService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年6月22日 上午11:46:35
 * @since 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/*.xml")
public class ZLPayTest {
	private static final Log log = LogFactory.getLog(ZLPayTest.class);
	
	@Autowired
	private IZlTradeService zlTradeService;
	
	@Test
	public void test_sendMarginSms(){
		
		Date currentDate = new Date();
        String instuId = ConsUtil.getInstance().cons.getInstuId();
        String merchantDate = new SimpleDateFormat("yyyyMMdd").format(currentDate);
        String merchantTime = new SimpleDateFormat("HHmmss").format(currentDate);
        String merchantSeqId = DateUtil.getCurrentDateTime();
        String tradeType = "01";
        String userId = "";
        String userNameText = "郭佳";
        String mobile = "18600806796";
        String certType = "00";
        String certId = "110105198610094112";
        String bankCode = "01030000";
        String cardNo = "6228480018543668976";
        String amt = "0";
        String resv = "代收业务";
        MarginSmsBean marginSmsBean = new MarginSmsBean(instuId, merchantDate, merchantTime,
                merchantSeqId, tradeType, userId, userNameText, mobile,
                certType, certId, bankCode, cardNo, amt, resv);
		
		ResultBean sendMarginSms = zlTradeService.sendMarginSms(marginSmsBean);
		log.info("sendMarginSms:"+JSON.toJSONString(sendMarginSms));
	}
	
	
}
