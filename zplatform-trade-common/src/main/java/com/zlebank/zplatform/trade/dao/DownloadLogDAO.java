/* 
 * DownloadLogDAO.java  
 * 
 * version TODO
 *
 * 2015年11月10日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao;

import com.zlebank.zplatform.commons.dao.BaseDAO;
import com.zlebank.zplatform.trade.model.PojoDownloadLog;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月10日 上午11:21:18
 * @since 
 */
public interface DownloadLogDAO extends BaseDAO<PojoDownloadLog>{
    public PojoDownloadLog getLogByFileName(String filename);
}
