/* 
 * SeqNoService.java  
 * 
 * version TODO
 *
 * 2016年3月8日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import com.zlebank.zplatform.trade.bean.enums.SeqNoEnum;
import com.zlebank.zplatform.trade.bean.enums.TradeSequenceEmum;

/**
 * 各种序列号得到
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年3月8日 下午5:27:36
 * @since 
 */
public interface SeqNoService {
    String getBatchNo(SeqNoEnum type);
    public Long getSeqNumber(TradeSequenceEmum tradeSequenceEmum);
}
