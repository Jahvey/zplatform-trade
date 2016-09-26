/* 
 * CMBCResfileLogDAOImpl.java  
 * 
 * version TODO
 *
 * 2016年6月28日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.CMBCResfileLogDAO;
import com.zlebank.zplatform.trade.model.PojoCMBCResfileLog;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年6月28日 上午11:00:01
 * @since 
 */
@Repository("cmbcResfileLogDAO")
public class CMBCResfileLogDAOImpl extends HibernateBaseDAOImpl<PojoCMBCResfileLog> implements CMBCResfileLogDAO{

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void saveCMBCResfileLog(List<PojoCMBCResfileLog> cmbcResfileLogList,String bankTranBatchNo){
		for(PojoCMBCResfileLog cmbcResfileLog : cmbcResfileLogList){
			cmbcResfileLog.setBankTranBatchNo(bankTranBatchNo);
			super.merge(cmbcResfileLog);
		}
	}
}
