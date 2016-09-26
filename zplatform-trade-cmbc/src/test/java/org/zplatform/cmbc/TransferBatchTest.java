package org.zplatform.cmbc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.trade.dao.TransferBatchDAO;

public class TransferBatchTest {
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
        /*try {
            List<PojoTransferBatch> batchList =  transferBatchDAO.findWaitAccountingTransferBatch();
            for(PojoTransferBatch batch:batchList){
                System.out.println(JSON.toJSONString(batch));
                int rows = transferBatchDAO.validateBatchResult(batch.getInsteadpaybatchno());
                System.out.println(rows);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
         
    }
    
    
    public static void main(String[] args) {
   	 SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.SIMPLE_DATE_FROMAT);
   	 Date date;
		try {
			date = DateUtil.convertToDate("2016-07-04", DateUtil.DEFAULT_TIME_STAMP_FROMAT);
			 String str= sdf.format(date);
	         System.out.println(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
        
	}
}
