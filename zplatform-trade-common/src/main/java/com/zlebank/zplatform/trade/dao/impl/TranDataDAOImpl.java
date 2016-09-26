package com.zlebank.zplatform.trade.dao.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.commons.bean.TransferDataQuery;
import com.zlebank.zplatform.commons.dao.impl.AbstractPagedQueryDAOImpl;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.trade.bean.enums.InsteadPayTypeEnum;
import com.zlebank.zplatform.trade.dao.TranDataDAO;
import com.zlebank.zplatform.trade.model.PojoTranData;
@Repository
public class TranDataDAOImpl  extends
AbstractPagedQueryDAOImpl<PojoTranData, TransferDataQuery>
        implements
            TranDataDAO {
    private static final Log log = LogFactory.getLog(TranDataDAOImpl.class);
    /**
     * 
     * @param id
     * @return
     */
    @Transactional
    public PojoTranData get(long id) {
        return (PojoTranData) getSession().get(PojoTranData.class, id);
    }
    
    /**
     * 通过批次号查找划拨数据
     * 
     * @param batchNo
     * @return
     */
    @SuppressWarnings("unchecked")
	@Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public List<PojoTranData> findTransDataByBatchNo(String batchNo) {
        List<PojoTranData> result = null;
        String queryString = " from PojoTranData where batchno = ? and status = ?";
        try {
            log.info("queryString:" + queryString);
            Query query = getSession().createQuery(queryString);
            query.setString(0, batchNo);
            query.setString(1, "01");
            result = query.list();
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 通过批次号更新划拨数据
     * 
     * @param batchNo
     * @param payType
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateTransDataStatusByBatchNo(String batchNo,
            InsteadPayTypeEnum payType) {
        try {
            String hql = "update PojoTranData set status = ? where batchno = ?";
            Session session = getSession();
            Query query = session.createQuery(hql);
            query.setParameter(0, payType.getCode());
            query.setParameter(1, batchNo);
            query.executeUpdate();
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public List<PojoTranData> findTransDataByBatchNoAndAccstatus(String batchNo) {
        List<PojoTranData> result = null;
        String queryString = "from PojoTranData where batchno = ? and accstatus = ?";
        try {
            log.info("queryString:" + queryString);
            Query query = getSession().createQuery(queryString);
            query.setString(0, batchNo);
            query.setString(1, "01");
            result = query.list();
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void batchUpdateTransData(List<PojoTranData> transferDataList) {
        StringBuffer hqlBuffer = new StringBuffer();
        hqlBuffer.append("update PojoTranData set banktranid = ?, ");
        hqlBuffer.append("resptype = ?,");
        hqlBuffer.append("respcode = ?,");
        hqlBuffer.append("respmsg = ?,");
        hqlBuffer.append("trandate = ?,");
        hqlBuffer.append("trantime = ?, ");
        hqlBuffer.append("status = ?, ");
        hqlBuffer.append("accstatus = ? ");
        hqlBuffer.append("where tranid = ? ");
//        Session session = getSession();
//        for (PojoTranData data : transferDataList) {
//            PojoTranData data_old = getTransferDataByTranId(data.getTranid());
//            Query query = session.createQuery(hqlBuffer.toString());
//            query.setParameter(0, data.getBanktranid());
//            query.setParameter(1, data.getResptype());
//            query.setParameter(2, data.getRespcode());
//            query.setParameter(3, data.getRespmsg());
//            query.setParameter(4, data.getTrandate());
//            query.setParameter(5, data.getTrantime());
//            query.setParameter(6, "S".equalsIgnoreCase(data.getResptype())? "00": "03");
//            query.setParameter(7, "01");
//            query.setParameter(8, data.getTranid());
//            query.executeUpdate();
            
            
//            query = session.createQuery("update TxnsLogModel set payretcode=?,payretinfo=?,payordfintime=?,payrettsnseqno=?,retcode=?,retinfo=?, tradeseltxn=? where txnseqno=?");
//            if("S".equalsIgnoreCase(data.getResptype())){
//                query.setParameter(0, "000000");
//                query.setParameter(1, "交易成功");
//                query.setParameter(4, "0000");
//                query.setParameter(5, "交易成功");
//            }else{
//                query.setParameter(0, data.getRespcode());
//                query.setParameter(1, data.getRespmsg());
//                query.setParameter(4, data.getRespcode());
//                query.setParameter(5, data.getRespmsg());
//            }
//            query.setParameter(2, DateUtil.getCurrentDateTime());
//            query.setParameter(3, data.getBanktranid());
//            query.setParameter(6, UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
//            query.setParameter(7, data_old.getTxnseqno());
//            query.executeUpdate();
//        }

    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void batchUpdateTransDataAccStatus(List<PojoTranData> transferDataList) {
        // TODO Auto-generated method stub
        StringBuffer hqlBuffer = new StringBuffer();
        hqlBuffer.append("update PojoTranData set ");
        hqlBuffer.append("accstatus = ?,");
        hqlBuffer.append("accinfo = ?");
        hqlBuffer.append("where tranid = ? ");
//        Session session = getSession();
//        for(PojoTranData data : transferDataList) {
//            Query query = session.createQuery(hqlBuffer.toString());
//            query.setParameter(0, data.getAccstatus());
//            query.setParameter(1, data.getAccinfo());
//            query.setParameter(2, data.getTranid());
//            query.executeUpdate();
//        }
    }

    /**
    *
    * @param e
    * @return
    */
   @Override
   protected Criteria buildCriteria(TransferDataQuery e) {
       Criteria crite = this.getSession().createCriteria(
               PojoTranData.class);

       if (e != null) {
           if(StringUtil.isNotEmpty(e.getBusicode())){
           crite.add(Restrictions.eq("busicode", e.getBusicode()));
           
           };
           if(StringUtil.isNotEmpty(e.getbatchno())){
           crite.add(Restrictions.eq("batchno", e.getbatchno()));}
           if(StringUtil.isNotEmpty(e.getTranId())){
               crite.add(Restrictions.eq("tranid", e.getTranId()));
           }
           if(StringUtil.isNotEmpty(e.getAcctType())){
               crite.add(Restrictions.eq("acctype", e.getAcctType()));
               
           }
         if(StringUtil.isNotEmpty( e.getStatus())){
             crite.add(Restrictions.eq("status", e.getStatus()));
             
         }
         
         if(StringUtil.isNotEmpty(e.getRelatedorderno())){
             crite.add(Restrictions.eq("relatedorderno",e.getRelatedorderno()));
             
         }
         
         if(e.getStartTime()!=null){
             crite.add(Restrictions.ge("createtime",e.getStartTime()));
             
         }
         
         if(e.getEndTime()!=null){
             crite.add(Restrictions.le("createtime",e.getEndTime()));
             
         }
         
         if(StringUtil.isNotEmpty(e.getBatch())){
             crite.add(Restrictions.le("associatedBatch",e.getBatch()));
             
         }


       }
       crite.addOrder(Order.asc("createtime"));
       return crite;

   }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public PojoTranData getTransferDataByTranId(String tranid,String status) {
        Criteria crite = this.getSession().createCriteria(
                PojoTranData.class);
        crite.add(Restrictions.eq("tranid", tranid));
        crite.add(Restrictions.eq("status", status));
       return  (PojoTranData) crite.uniqueResult();
    }

    public PojoTranData getTransferDataByTranId(String tranid) {
        Criteria crite = this.getSession().createCriteria(
                PojoTranData.class);
        crite.add(Restrictions.eq("tranid", tranid));
       return  (PojoTranData) crite.uniqueResult();
    }
    
    public static void main(String[] args) {
        System.out.println(UUID.randomUUID());
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public int getCountByInsteadDataId(Long id,String busiTypeCode) {
        Criteria crite = this.getSession().createCriteria(PojoTranData.class);
        crite.add(Restrictions.eq("busiDataId", id));
        crite.add(Restrictions.eq("busiType", busiTypeCode));
        crite.setProjection(Projections.rowCount());  
        int count = Integer.parseInt(crite.uniqueResult().toString());
       return  count;
    }
}
