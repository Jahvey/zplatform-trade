/* 
 * TxnsRefundServiceImpl.java  
 * 
 * version TODO
 *
 * 2015年10月22日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.trade.dao.ITxnsRefundDAO;
import com.zlebank.zplatform.trade.model.TxnsRefundModel;
import com.zlebank.zplatform.trade.service.ITxnsRefundService;
import com.zlebank.zplatform.trade.service.base.BaseServiceImpl;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年10月22日 下午4:48:00
 * @since 
 */
@Service("txnsRefundService")
public class TxnsRefundServiceImpl extends BaseServiceImpl<TxnsRefundModel,Long> implements ITxnsRefundService{

    @Autowired
    private ITxnsRefundDAO txnsRefundDAO;
    /**
     *
     * @return
     */
    @Override
    public Session getSession() {
        // TODO Auto-generated method stub
        return txnsRefundDAO.getSession();
    }
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public Long saveRefundOrder(TxnsRefundModel refundOrder){
        return super.saveEntity(refundOrder);
    }
    
    public TxnsRefundModel getRefundByRefundorderNo(String refundorderno,String merchno) {
        String hql = " from TxnsRefundModel where refundorderno=? and submerchno=?";
        return getUniqueByHQL(hql, new Object[]{refundorderno,merchno});
    }
    
    public TxnsRefundModel getRefundByOldTxnSeqno(String txnseqno_old,String merchno) {
        String hql = " from TxnsRefundModel c where c.oldtxnseqno=? and c.status in (01,21,00)";
        return super.getUniqueByHQL(hql, new Object[]{txnseqno_old});
    }
    public TxnsRefundModel getRefundByRefundor(String refundorderno) {
        String hql = " from TxnsRefundModel where refundorderno=? ";
        return super.getUniqueByHQL(hql, new Object[]{refundorderno});
    }
    
    public Long getSumAmtByOldTxnseqno(String txnseqno_old){
    	String sql = "select nvl(sum(t.amount),0) totalamt from t_txns_refund t where t.oldtxnseqno=? and t.status not in(?,?,?) ";
    	List<Map<String, Object>> queryBySQL = (List<Map<String, Object>>) queryBySQL(sql, new Object[]{txnseqno_old,"09","19","29"});
    	return  Long.valueOf(queryBySQL.get(0).get("TOTALAMT")+"");
    }
    
   

    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateRefundResult(TxnsRefundModel refundOrder) {
        // TODO Auto-generated method stub
        String hql = "update TxnsWithdrawModel set finishtime = ?,status = ?,retcode = ?,retinfo = ? where refundorderno = ? and memberid = ? ";
        super.updateByHQL(hql, new Object[]{refundOrder.getFinishtime(),refundOrder.getStatus(),refundOrder.getRetcode(),refundOrder.getRetinfo(),refundOrder.getRefundorderno(),refundOrder.getMemberid()});
    }
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateRefund(TxnsRefundModel refundOrder) {
        txnsRefundDAO.updateRefund(refundOrder);
        // TODO Auto-generated method stub
    }
	/**
	 *
	 * @param txnseqno
	 * @return
	 */
	@Override
	@Transactional(readOnly=true)
	public TxnsRefundModel getRefundByTxnseqno(String txnseqno) {
		String hql = " from TxnsRefundModel c where c.reltxnseqno = ? ";
        return super.getUniqueByHQL(hql, new Object[]{txnseqno});
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void updateToSuccess(String txnseqno){
		String hql = "update TxnsRefundModel set status = ? where reltxnseqno = ?";
		super.updateByHQL(hql, new Object[]{"00",txnseqno});
	}
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void updateToFailed(String txnseqno){
		String hql = "update TxnsRefundModel set status = ? where reltxnseqno = ?";
		super.updateByHQL(hql, new Object[]{"49",txnseqno});
	}
    
}
