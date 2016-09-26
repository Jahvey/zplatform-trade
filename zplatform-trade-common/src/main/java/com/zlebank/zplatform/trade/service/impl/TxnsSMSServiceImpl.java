package com.zlebank.zplatform.trade.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.trade.dao.ITxnsSMSDAO;
import com.zlebank.zplatform.trade.model.TxnsSmsModel;
import com.zlebank.zplatform.trade.service.ITxnsSMSService;
import com.zlebank.zplatform.trade.service.base.BaseServiceImpl;
/**
 * 
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年12月15日 下午2:15:25
 * @since
 */
@Service("txnsSMSService")
public class TxnsSMSServiceImpl extends BaseServiceImpl<TxnsSmsModel, Long> implements ITxnsSMSService{
    
    @Autowired
    private ITxnsSMSDAO txnsSMSDAO;
    
    @Override
    public Session getSession() {
        // TODO Auto-generated method stub
        return txnsSMSDAO.getSession();
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void saveSMS(TxnsSmsModel sms) {
       super.save(sms);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public TxnsSmsModel getLastSMSByTN(String tn) {
        String hql = " from TxnsSmsModel where tn = ? and overduetime > ? order by tid desc";
        List<TxnsSmsModel> smsList =  (List<TxnsSmsModel>) super.queryByHQL(hql, new Object[]{tn,DateUtil.getCurrentDateTime()});
        if(smsList.size()>0){
            return smsList.get(0);
        }
        return null;
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateSMSResult(TxnsSmsModel sms) {
        // TODO Auto-generated method stub
        String hql = " update TxnsSmsModel set retcode = ?,retinfo = ? where tn = ? and sendtime = ?";
        super.updateByHQL(hql, new Object[]{sms.getRetcode(),sms.getRetinfo(),sms.getTn(),sms.getSendtime()});
    }

    
}
