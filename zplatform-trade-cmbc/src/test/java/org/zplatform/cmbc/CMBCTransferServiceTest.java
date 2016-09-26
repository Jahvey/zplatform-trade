package org.zplatform.cmbc;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.cmbc.service.ICMBCTransferService;
import com.zlebank.zplatform.trade.model.PojoRealnameAuth;
import com.zlebank.zplatform.trade.service.ITradeReceiveProcessor;

public class CMBCTransferServiceTest {
    
    private ApplicationContext context;
    private ICMBCTransferService cmbcTransferService;
    private ITradeReceiveProcessor tradeReceiveProcessor;
    public void init(){
        context = new ClassPathXmlApplicationContext("CmbcContextTest.xml");
        cmbcTransferService = (ICMBCTransferService) context.getBean("cmbcTransferService");
        tradeReceiveProcessor = (ITradeReceiveProcessor) context.getBean("cmbcQuickReceiveProcessor");
    }
    
    public void testJob() { 
        init();
        try {
            PojoRealnameAuth realnameAuth = new PojoRealnameAuth();
            realnameAuth.setCardNo("6228480018543668976");
            realnameAuth.setCardType("1");
            realnameAuth.setCustomerNm("郭佳");
            realnameAuth.setCertifTp("01");
            realnameAuth.setCertifId("110105198610094112");
            realnameAuth.setPhoneNo(18600806796L);
            realnameAuth.setCvn2("");
            realnameAuth.setExpired("");
            ResultBean resultBean = cmbcTransferService.realNameAuth(realnameAuth);
            System.out.println(resultBean.getErrMsg());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
   
    public void testInnerPay(){
        init();
        ResultBean resultBean = cmbcTransferService.batchTransfer(52L);
        JSON.toJSONString(resultBean);
    }
    @Test
    public void test_async(){
    	init();
    	//200000000000685
    	//GoodBrother20160513185623
    	//tradeReceiveProcessor.generateAsyncRespMessage("GoodBrother20160513185623", "300000000000004");
    }
}
