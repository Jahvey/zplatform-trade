package org.zplatform.cmbc;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zlebank.zplatform.trade.cmbc.job.InsteadPayAccountingJob;
import com.zlebank.zplatform.trade.cmbc.job.ReciveCurrentDateCMBCFileJob;

public class ReciveCurrentDateCMBCFileJobTest {
    
    private ApplicationContext context;
    private ReciveCurrentDateCMBCFileJob reciveCurrentDateCMBCFileJob;
    private InsteadPayAccountingJob insteadPayAccountingJob;
    @Before
    public void init(){
        context = new ClassPathXmlApplicationContext("CmbcContextTest.xml");
        reciveCurrentDateCMBCFileJob = (ReciveCurrentDateCMBCFileJob) context.getBean("reciveCurrentDateCMBCFileJob");
        // insteadPayAccountingJob = (InsteadPayAccountingJob) context.getBean("insteadPayAccountingJob");
    }
    @Test
    public void testJob() {
        try {
            reciveCurrentDateCMBCFileJob.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void testAccountJob(){
        init();
        insteadPayAccountingJob.execute();
    }
    
    /*public static void main(String[] args) {
        String fileName = "res/20151209/res_20151209_010.txt";
        String[] files =fileName.split("/");
        System.out.println(files.length);
        System.out.println(files[files.length-1]);
    }*/
}
