/* 
 * TxncodeDefServiceImpl.java  
 * 
 * version TODO
 *
 * 2015年9月10日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.type.TrueFalseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.trade.dao.ITxncodeDefDAO;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.model.TxncodeDefModel;
import com.zlebank.zplatform.trade.service.ITxncodeDefService;
import com.zlebank.zplatform.trade.service.base.BaseServiceImpl;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月10日 下午3:59:56
 * @since 
 */
@Service("txncodeDefService")
public class TxncodeDefServiceImpl extends BaseServiceImpl<TxncodeDefModel, Long> implements ITxncodeDefService{

    @Autowired
    private ITxncodeDefDAO txncodeDefDAO;
    /**
     *
     * @return
     */
    @Override
    public Session getSession() {
        // TODO Auto-generated method stub
        return txncodeDefDAO.getSession();
    }
    @Transactional(readOnly=true)
    public TxncodeDefModel getBusiCode(String txntype,String txnsubtype,String biztype){
        return super.getUniqueByHQL("from TxncodeDefModel where txntype=? and txnsubtype=? and biztype=? ", new Object[]{txntype,txnsubtype,biztype});
    }

    @Transactional(readOnly=true)
	public String getDefaultVerInfo(String instiCode, String busicode,
			int verType) throws TradeException {
		List<Map<String, Object>> resultList = (List<Map<String, Object>>) super
				.queryBySQL(
						"select COOP_INSTI_CODE,BUSI_CODE,VER_TYPE,VER_VALUE from T_NONMER_DEFAULT_CONFIG where COOP_INSTI_CODE=? and BUSI_CODE=? and VER_TYPE=?",
						new Object[] { instiCode, busicode, verType + "" });
		if (resultList.size() > 0) {
			Map<String, Object> valueMap = resultList.get(0);
			return valueMap.get("VER_VALUE").toString();
		}
		throw new TradeException("GW03");
		// return null;
	}
}
