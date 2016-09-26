package com.zlebank.zplatform.trade.service.impl;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.trade.dao.ITxnsWithholdingDAO;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.model.TxnsWithholdingModel;
import com.zlebank.zplatform.trade.service.ITxnsWithholdingService;
import com.zlebank.zplatform.trade.service.base.BaseServiceImpl;
@Service("txnsWithholdingService")
public class TxnsWithholdingServiceImpl extends BaseServiceImpl<TxnsWithholdingModel,Long> implements ITxnsWithholdingService{

    @Autowired
    private ITxnsWithholdingDAO txnsWithholdingDAO;
    @Override
    public Session getSession() {
        // TODO Auto-generated method stub
        return txnsWithholdingDAO.getSession();
    }
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void saveWithholdingLog(TxnsWithholdingModel withholding)
            throws TradeException {
        try {
            getSession().save(withholding);
        } catch (Exception e) {
            // TODO: handle exception
            throw new TradeException("");
        }
        
    }
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateRealNameResult(TxnsWithholdingModel withholding) throws TradeException {
        StringBuffer updateHQL = new StringBuffer("update TxnsWithholdingModel set ");
        updateHQL.append("exectype = ?,");
        updateHQL.append("execcode = ?,");
        updateHQL.append("execmsg = ?,");
        updateHQL.append("validatestatus = ?,");
        updateHQL.append("payserialno = ?");
        updateHQL.append("where serialno = ? ");
        updateByHQL(updateHQL.toString(), new Object[]{withholding.getExectype(),withholding.getExeccode(),withholding.getExecmsg(),withholding.getValidatestatus(),withholding.getPayserialno(),withholding.getSerialno()});
    }
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateWhithholding(TxnsWithholdingModel withholding){
        super.update(withholding);
    }
    
    @Transactional(readOnly=true)
    public TxnsWithholdingModel getWithholdingBySerialNo(String serialno){
        return super.getUniqueByHQL("from TxnsWithholdingModel where serialno = ?", new Object[]{serialno});
    }
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateWithholdingLogError(TxnsWithholdingModel withholding) {
        StringBuffer updateHQL = new StringBuffer("update TxnsWithholdingModel set ");
        updateHQL.append("execmsg = ?,");
        updateHQL.append("validatestatus = ?");
        updateHQL.append("where serialno = ? ");
        updateByHQL(updateHQL.toString(), new Object[]{withholding.getExecmsg(),"11",withholding.getSerialno()});
        
    }
}
