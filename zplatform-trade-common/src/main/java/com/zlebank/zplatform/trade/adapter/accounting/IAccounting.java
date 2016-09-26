/* 
 * IAccounting.java  
 * 
 * version TODO
 *
 * 2015年9月7日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.adapter.accounting;

import com.zlebank.zplatform.trade.bean.ResultBean;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月7日 上午10:58:35
 * @since 
 */
public interface IAccounting {

    static final String AnonymousMember="999999999999999";
    /**
     * 交易账务处理
     * @param txnseqno
     * @return
     */
    ResultBean accountedFor(String txnseqno);
    
    /**
     * 代付账务账务处理
     * @param batchno
     * @return
     */
    ResultBean accountedForInsteadPay(String batchno);
}
