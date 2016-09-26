/* 
 * SeqNoServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年3月8日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.trade.bean.enums.SeqNoEnum;
import com.zlebank.zplatform.trade.bean.enums.TradeSequenceEmum;
import com.zlebank.zplatform.trade.dao.ConfigInfoDAO;
import com.zlebank.zplatform.trade.service.SeqNoService;

/**
 * 序列号生成服务
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年3月8日 下午5:28:10
 * @since 
 */
@Service
public class SeqNoServiceImpl  implements SeqNoService{

	private static final String NAMESPACE="SEQ:";
    @Autowired
    private ConfigInfoDAO configInfoDAO;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    /**
     * 生成指定的序列号
     * @param type
     * @return
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public String getBatchNo(SeqNoEnum type) {
        String rtnNo = null;
        switch (type) {
            case INSTEAD_PAY_BATCH_NO:
                rtnNo = "W01"+DateUtil.getCurrentDate()+String.format("%05d", configInfoDAO.getSequence("SEQ_T_INSTEAD_PAY_BATCH_NO"));
                break;
            case WITHDRAW_BATCH_NO:
                rtnNo = "W02"+DateUtil.getCurrentDate()+String.format("%05d", configInfoDAO.getSequence("SEQ_T_TXNS_WITHDRAW_BATCH_NO"));
                break;
            case REFUND_BATCH_NO:
                rtnNo = "W03"+DateUtil.getCurrentDate()+String.format("%05d", configInfoDAO.getSequence("SEQ_T_TXNS_REFUND_BATCH_NO"));
                break;
            case TRAN_BATCH_NO:
                rtnNo = "T00"+DateUtil.getCurrentDate()+String.format("%05d", configInfoDAO.getSequence("SEQ_T_TRAN_BATCH_NO"));
                break;
            case BANK_TRAN_BATCH_NO:
                rtnNo = "B00"+DateUtil.getCurrentDate()+String.format("%05d", configInfoDAO.getSequence("SEQ_T_BANK_TRAN_BATCH_NO"));
                break;
            case INSTEAD_PAY_DATA_NO:
                rtnNo = "W01"+DateUtil.getCurrentDateTime()+String.format("%07d", configInfoDAO.getSequence("SEQ_DETAIL_NO"));
                break;
            case WITHDRAW_DATA_NO:
                rtnNo = "W02"+DateUtil.getCurrentDateTime()+String.format("%07d", configInfoDAO.getSequence("SEQ_DETAIL_NO"));
                break;
            case REFUND_DATA_NO:
                rtnNo = "W03"+DateUtil.getCurrentDateTime()+String.format("%07d", configInfoDAO.getSequence("SEQ_DETAIL_NO"));
                break;
            case TRAN_DATA_NO:
                rtnNo = "T00"+DateUtil.getCurrentDateTime()+String.format("%07d", configInfoDAO.getSequence("SEQ_DETAIL_NO"));
                break;
            case BANK_TRAN_DATA_NO:
                rtnNo = "B00"+DateUtil.getCurrentDateTime()+String.format("%07d", configInfoDAO.getSequence("SEQ_DETAIL_NO"));
                break;
            default :
                break;
        }
        return rtnNo;
    }
    
    public Long getSeqNumber(TradeSequenceEmum tradeSequenceEmum){
    	
    	return redisTemplate.opsForValue().increment(NAMESPACE+tradeSequenceEmum.getName(), 1);
    }

}
