/* 
 * RspmsgDAO.java  
 * 
 * version TODO
 *
 * 2015年10月22日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao;

import com.zlebank.zplatform.commons.dao.BaseDAO;
import com.zlebank.zplatform.trade.bean.enums.ChnlTypeEnum;
import com.zlebank.zplatform.trade.model.PojoRspmsg;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年10月22日 下午1:43:04
 * @since 
 */
public interface RspmsgDAO extends BaseDAO<PojoRspmsg>{

    public PojoRspmsg get(String rspid);
    public PojoRspmsg getRspmsgByChnlCode(ChnlTypeEnum chnlType,String retCode) ;
}
