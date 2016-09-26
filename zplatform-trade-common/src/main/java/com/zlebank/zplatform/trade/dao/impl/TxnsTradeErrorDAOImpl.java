/* 
 * TxnsTradeErrorDAOImpl.java  
 * 
 * version TODO
 *
 * 2015年10月21日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;

import org.springframework.stereotype.Repository;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.ITxnsTradeErrorDAO;
import com.zlebank.zplatform.trade.model.TxnsTraderrModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年10月21日 下午4:58:48
 * @since 
 */
@Repository("txnsTradeErrorDAO")
public class TxnsTradeErrorDAOImpl extends HibernateBaseDAOImpl<TxnsTraderrModel> implements ITxnsTradeErrorDAO{
    
}
