/* 
 * ITxnsSplitAccountService.java  
 * 
 * version TODO
 *
 * 2015年9月11日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import java.util.List;

import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.gateway.SplitAcctBean;
import com.zlebank.zplatform.trade.model.TxnsSplitAccountModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月11日 下午7:46:22
 * @since 
 */
public interface ITxnsSplitAccountService extends IBaseService<TxnsSplitAccountModel, Long>{
    public List<TxnsSplitAccountModel> getSplitAccountByTxnseqno(String txnseqno);
    public ResultBean saveTxnsSplitAccount(TxnsSplitAccountModel txnsSplitAccount);
    public Long saveTxnsSplitAccount(List<SplitAcctBean>  splitAcctList,String txnseqno);
}
