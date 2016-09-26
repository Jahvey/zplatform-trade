/* 
 * CMBCResfileLogDAO.java  
 * 
 * version TODO
 *
 * 2016年6月28日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao;

import java.util.List;

import com.zlebank.zplatform.commons.dao.BaseDAO;
import com.zlebank.zplatform.trade.model.PojoCMBCResfileLog;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年6月28日 上午10:59:25
 * @since 
 */
public interface CMBCResfileLogDAO extends BaseDAO<PojoCMBCResfileLog>{
	/**
	 * 保存民生代付回盘文件流水
	 * @param cmbcResfileLogList
	 * @param bankTranBatchNo
	 */
	public void saveCMBCResfileLog(List<PojoCMBCResfileLog> cmbcResfileLogList,String bankTranBatchNo);
}
