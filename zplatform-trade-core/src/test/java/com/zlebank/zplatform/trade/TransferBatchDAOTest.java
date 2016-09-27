package com.zlebank.zplatform.trade;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.trade.bean.enums.BankTransferBatchOpenStatusEnum;
import com.zlebank.zplatform.trade.chanpay.bean.async.TradeAsyncResultBean;
import com.zlebank.zplatform.trade.dao.BankTransferBatchDAO;
import com.zlebank.zplatform.trade.dao.TransferBatchDAO;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.model.PojoTranData;
import com.zlebank.zplatform.trade.service.ChanPayAsyncService;

public class TransferBatchDAOTest {
    private TransferBatchDAO transferBatchDAO;
    private BankTransferBatchDAO bankTransferBatchDAO;
    private ChanPayAsyncService chanPayAsyncService;

    @Before
    public void init() {
        ApplicationContext context = ApplicationContextUtil.get();
        transferBatchDAO = (TransferBatchDAO) context
                .getBean("transferBatchDAO");
        bankTransferBatchDAO = (BankTransferBatchDAO) context
                .getBean("bankTransferBatchDAO");
        chanPayAsyncService = (ChanPayAsyncService) context
                .getBean("chanPayAsyncService");
    }

    @Test
    public void testQueryWaitTrialTranData() {
        List<PojoTranData> tranDatas = transferBatchDAO
                .queryWaitTrialTranData(11);
        Assert.assertEquals(10, tranDatas.size());
    }

    @Test
    @Ignore
    public void testGetByTranBatchAndOpenStatus() {
        bankTransferBatchDAO.getByTranBatchAndOpenStatus(447,
                BankTransferBatchOpenStatusEnum.OPEN);

    }
    
    @Test
    public void test_chanpay(){
    	String json = "{\"gmt_create\":\"20160513142734\",\"gmt_payment\":\"20160513142734\",\"inner_trade_no\":\"101146312084999870441\",\"notify_id\":\"baf99670cdd444e682f26a8669067875\",\"notify_time\":\"20160513145200\",\"notify_type\":\"trade_status_sync\",\"outer_trade_no\":\"328e56a73652478b943db268021426a1\",\"sign\":\"DIcUEBR5YcLHDmK9l6Ec91M7soe8dP0PWhqOqg1jpfRkDGzrplustP6a8K+gy2Q30zBVZQUtr0vrI0zwNe421oob41ErN26YgS77UugFFAd7L84YhnyJjYRmXu6wVcStTn0NFzN0jE4WqubpjwxZL5NYn3fIg1r0UWH4rMD/V/8=\",\"sign_type\":\"RSA\",\"trade_amount\":\"0.01\",\"trade_status\":\"TRADE_FINISHED\",\"version\":\"1.0\"}";
		
    	TradeAsyncResultBean tradeAsyncResultBean = JSON.parseObject(json, TradeAsyncResultBean.class);
    	try {
			chanPayAsyncService.dealWithTradeAsync(tradeAsyncResultBean);
		} catch (TradeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
