package org.zplatform.cmbc;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zlebank.zplatform.trade.dao.TransferBatchDAO;

public class TransferBatchDAOTest {
    private ApplicationContext context;
    private TransferBatchDAO transferBatchDAO;
    //private TransferDataDAO transferDataDAO;
    //private IInsteadPayService insteadPayService;
    public void init(){
        context = new ClassPathXmlApplicationContext("CmbcContextTest.xml");
        transferBatchDAO = (TransferBatchDAO) context.getBean("transferBatchDAO");
        //transferDataDAO = (TransferDataDAO) context.getBean("transferDataDAO");
        //insteadPayService = (IInsteadPayService) context.getBean("insteadPayService");
    }
    @Test
    public void testJob() { 
        init();
        try {
            //PojoTransferBatch transferBatch = transferBatchDAO.getByBatchNo("000000000000064");
            //System.out.println(JSON.toJSONString(transferBatch));
            //List<PojoTransferData> transferDatas = transferDataDAO.findTransDataByBatchNo("000000000000064");
            //System.out.println(JSON.toJSONString(transferDatas));
            //insteadPayService.batchOuterPay("000000000000065"); 001
           // PojoTransferBatch transferBatch = transferBatchDAO.getByBatchNo("001");
            
           // System.out.println(JSON.toJSONString(transferBatch));
            /*transferBatch.setStatus("02");
            transferBatch.setRequestfilename("fileName");
            transferBatch.setResponsefilename("resFileName");
            transferBatch.setTransfertime(DateUtil.getCurrentDateTime());
            transferBatchDAO.updateBatchToTransfer(transferBatch);*/
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         
    }
}
