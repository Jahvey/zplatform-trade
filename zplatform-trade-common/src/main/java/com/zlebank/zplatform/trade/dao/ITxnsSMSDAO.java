package com.zlebank.zplatform.trade.dao;

import org.hibernate.Session;

import com.zlebank.zplatform.commons.dao.BaseDAO;
import com.zlebank.zplatform.trade.model.TxnsSmsModel;
/**
 * 
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年12月15日 下午2:04:09
 * @since
 */
public interface ITxnsSMSDAO extends BaseDAO<TxnsSmsModel>{
    public Session getSession();
}
