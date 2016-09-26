/* 
 * SplitTest.java  
 * 
 * version TODO
 *
 * 2016年3月9日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade;

import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.trade.batch.spliter.BatchSpliter;
import com.zlebank.zplatform.trade.bean.enums.SeqNoEnum;
import com.zlebank.zplatform.trade.dao.TranDataDAO;
import com.zlebank.zplatform.trade.exception.RecordsAlreadyExistsException;
import com.zlebank.zplatform.trade.model.PojoTranData;
import com.zlebank.zplatform.trade.service.SeqNoService;

/**
 * Class Description
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年3月9日 下午2:34:46
 * @since 
 */
@Transactional
public class SplitTest {
    static ApplicationContext context = null;
    static BatchSpliter service = null;
    static SeqNoService seqNoService = null;
    static TranDataDAO tranDataDAO = null;
    static {
        context = ApplicationContextUtil.get();
        service =  (BatchSpliter) context.getBean("transferBatchSpliter");
        seqNoService =  (SeqNoService) context.getBean("seqNoServiceImpl");
        tranDataDAO =  (TranDataDAO) context.getBean("tranDataDAOImpl");
    }

    @Test   
    @Transactional
    public void testSplitTest() {
        // 创建划拨
        PojoTranData cpojo = createTranData();
        cpojo = tranDataDAO.merge(cpojo);
        
        // 批次算法
        PojoTranData pojo = tranDataDAO.get(117);
        PojoTranData pojo2 = tranDataDAO.get(118);
//        PojoTranData pojo3 = tranDataDAO.get(1135);
        PojoTranData[] datas = new PojoTranData[2];
        datas[0] = pojo;
        datas[1] = pojo2;
//        datas[2] = pojo3;
        try {
            service.split(datas );
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof RecordsAlreadyExistsException) 
                System.out.println("已经存在相应的记录");
        }
    }

    /**
     * @return
     */
    private PojoTranData createTranData() {
        PojoTranData pojo = new PojoTranData();
        pojo.setTranDataSeqNo(seqNoService.getBatchNo(SeqNoEnum.TRAN_DATA_NO));// "划拨流水序列号"

        //pojo.setTranBatch(new PojoTranBatch());// "划拨批次序列号"
        pojo.setBusiDataId(1003L);// "代付流水号"

        pojo.setAccType("0");// "账户类型(0:对私账户1：对公账户)"
        pojo.setAccNo("6225010100010001");// "账户号"
        pojo.setAccName("习远平");// "账户名"
        pojo.setBankNo("10010");// "银行代码"
        pojo.setBankName("国资银行");// "银行名称"
        pojo.setTranAmt(0L);// "划拨金额"
        pojo.setRemark("");// "备注"
        pojo.setStatus("01");// "状态(01:未审核00：审核通过09：审核拒绝)"
        pojo.setApplyTime(new Date());// "申请时间"
        pojo.setApproveTime(new Date());// "通过时间"
        return pojo;
    }
}
