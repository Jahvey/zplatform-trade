package com.zlebank.zplatform.trade.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.ITxnsSMSDAO;
import com.zlebank.zplatform.trade.model.TxnsSmsModel;
/**
 * 
 * Class Description
 * @author guojia
 * @version
 * @date 2015年12月15日 下午2:03:16
 * @since
 */
@Repository("txnsSMSDAO")
public class TxnsSMSDAOImpl extends HibernateBaseDAOImpl<TxnsSmsModel> implements ITxnsSMSDAO{

    public Session getSession(){
        return super.getSession();
    }
}
