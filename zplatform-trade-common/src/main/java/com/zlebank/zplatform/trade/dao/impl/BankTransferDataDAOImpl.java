package com.zlebank.zplatform.trade.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.commons.bean.TransferDataQuery;
import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.trade.bean.cmbc.ReexchangeBean;
import com.zlebank.zplatform.trade.bean.enums.InsteadPayTypeEnum;
import com.zlebank.zplatform.trade.bean.page.QueryTransferBean;
import com.zlebank.zplatform.trade.dao.BankTransferDataDAO;
import com.zlebank.zplatform.trade.dao.TranBatchDAO;
import com.zlebank.zplatform.trade.dao.TranDataDAO;
import com.zlebank.zplatform.trade.model.PojoBankTransferData;
import com.zlebank.zplatform.trade.model.PojoTranData;
@Repository("bankTransferDataDAO")
public class BankTransferDataDAOImpl  extends
HibernateBaseDAOImpl<PojoBankTransferData>
        implements
        BankTransferDataDAO {
    private static final Log log = LogFactory.getLog(BankTransferDataDAOImpl.class);

    @Autowired 
    private TranBatchDAO tranBatchDAO;
    @Autowired
    private TranDataDAO tranDataDAO;
    
    /**
     * 通过批次号查找划拨数据
     * 
     * @param batchNo
     * @return
     */
    @SuppressWarnings("unchecked")
	@Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public List<PojoBankTransferData> findTransDataByBatchNo(Long tid) {
        List<PojoBankTransferData> result = null;
        String queryString = " from PojoBankTransferData where bankTranBatch.tid = ?";
        try {
            log.info("queryString:" + queryString);
            Query query = getSession().createQuery(queryString);
            query.setLong(0, tid);
            result = query.list();
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 通过批次号更新划拨数据
     * @param batchNo
     * @param payType
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateTransDataStatusByBatchNo(Long tid,InsteadPayTypeEnum payType) {
        try {
            String hql = "update PojoBankTransferData set status = ? where bankTranBatch.tid = ?";
            Session session = getSession();
            Query query = session.createQuery(hql);
            query.setParameter(0, payType.getCode());
            query.setParameter(1, tid);
            query.executeUpdate();
            session.clear();
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public List<PojoBankTransferData> findTransDataByBatchNoAndAccstatus(String batchNo) {
        List<PojoBankTransferData> result = null;
        String queryString = "from PojoBankTransferData where batchno = ? and accstatus = ?";
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

    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void batchUpdateTransData(List<PojoBankTransferData> transferDataList) {
        StringBuffer hqlBuffer = new StringBuffer();
        hqlBuffer.append("update PojoBankTransferData set bankTranResNo = ?, ");
        hqlBuffer.append("resType = ?,");
        hqlBuffer.append("resCode = ?,");
        hqlBuffer.append("resInfo = ?,");
        hqlBuffer.append("status = ? ");
        hqlBuffer.append("where bankTranDataSeqNo = ? ");
        Session session = getSession();
        for (PojoBankTransferData data : transferDataList) {
            Query query = session.createQuery(hqlBuffer.toString());
            PojoBankTransferData data_old = getTransferDataByTranId(data.getBankTranDataSeqNo());
            if(data_old==null){
            	continue;
            }
            query.setParameter(0, data.getBankTranResNo());
            query.setParameter(1, data.getResType());
            query.setParameter(2, data.getResCode());
            query.setParameter(3, data.getResInfo());
            query.setParameter(4, "S".equalsIgnoreCase(data.getResType())? "00": "05");
            query.setParameter(5, data.getBankTranDataSeqNo());
            query.executeUpdate();
            
            
            query = session.createQuery("update TxnsLogModel set payretcode=?,payretinfo=?,payordfintime=?,payrettsnseqno=?,retcode=?,retinfo=?, tradeseltxn=?,accordcommitime=?,retdatetime=?,relate=?,tradetxnflag=? where txnseqno=?");
            if("S".equalsIgnoreCase(data.getResType())){
                query.setParameter(0, "000000");
                query.setParameter(1, "交易成功");
                query.setParameter(4, "0000");
                query.setParameter(5, "交易成功");
            }else{
                query.setParameter(0, data.getResCode());
                query.setParameter(1, data.getResInfo());
                query.setParameter(4, data.getResCode());
                query.setParameter(5, data.getResInfo());
            }
            query.setParameter(2, DateUtil.getCurrentDateTime());
            query.setParameter(3, data.getBankTranResNo());
            query.setParameter(6, UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
            query.setParameter(7, DateUtil.getCurrentDateTime());
            query.setParameter(8, DateUtil.getCurrentDateTime());
            query.setParameter(9, "10000000");
            query.setParameter(10, "10000000");
            
            query.setParameter(11, data_old.getTranData().getTxnseqno());
            query.executeUpdate();
            
            //更新划拨数据
            PojoTranData tranData = data_old.getTranData();
            tranData.setStatus("S".equalsIgnoreCase(data.getResType())? "02": "03");
            tranDataDAO.update(tranData);
            //更新划拨批次信息
            tranBatchDAO.updateBankTransferResult(data_old.getTranData().getTranBatch().getTid());
        }

    }
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void batchUpdateReexchangeTransData(List<ReexchangeBean> reexchangeList) {
    	 StringBuffer hqlBuffer = new StringBuffer();
         hqlBuffer.append("update PojoBankTransferData set bankTranResNo = ?, ");
         hqlBuffer.append("resType = ?,");
         hqlBuffer.append("resCode = ?,");
         hqlBuffer.append("resInfo = ?,");
         hqlBuffer.append("status = ? ");
         hqlBuffer.append("where bankTranDataSeqNo = ? ");
         Session session = getSession();
         for (ReexchangeBean data : reexchangeList) {
             Query query = session.createQuery(hqlBuffer.toString());
             PojoBankTransferData data_old = getTransferDataByTranId(data.getTranId());
             if(data_old==null){
             	continue;
             }
             query.setParameter(0, data.getBankTranId());
             query.setParameter(1, data.getRespType());
             query.setParameter(2, data.getRespCode());
             query.setParameter(3, data.getRespMsg());
             query.setParameter(4, "S".equalsIgnoreCase(data.getRespType())? "00": "05");
             query.setParameter(5, data.getTranId());
             query.executeUpdate();
             
             
             query = session.createQuery("update TxnsLogModel set payretcode=?,payretinfo=?,payordfintime=?,payrettsnseqno=?,retcode=?,retinfo=?, tradeseltxn=?,accordcommitime=?,retdatetime=?,relate=?,tradetxnflag=? where txnseqno=?");
             if("S".equalsIgnoreCase(data.getRespType())){
                 query.setParameter(0, "000000");
                 query.setParameter(1, "交易成功");
                 query.setParameter(4, "0000");
                 query.setParameter(5, "交易成功");
             }else{
                 query.setParameter(0, data.getRespCode());
                 query.setParameter(1, data.getRespMsg());
                 query.setParameter(4, "3299");
                 query.setParameter(5, "交易失败");
             }
             query.setParameter(2, DateUtil.getCurrentDateTime());
             query.setParameter(3, data.getBankTranId());
             query.setParameter(6, UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
             query.setParameter(7, DateUtil.getCurrentDateTime());
             query.setParameter(8, DateUtil.getCurrentDateTime());
             query.setParameter(9, "10000000");
             query.setParameter(10, "10000000");
             
             query.setParameter(11, data_old.getTranData().getTxnseqno());
             query.executeUpdate();
             
             //更新划拨数据
             PojoTranData tranData = data_old.getTranData();
             tranData.setStatus("S".equalsIgnoreCase(data.getRespType())? "02": "03");
             tranDataDAO.update(tranData);
             //更新划拨批次信息
             tranBatchDAO.updateBankTransferResult(data_old.getTranData().getTranBatch().getTid());
         }
        
    }
    
    
    
    
    
    @SuppressWarnings("unchecked")
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void batchUpdateBossPayTransData(List<PojoBankTransferData> transferDataList) {
        Session session = getSession();
        for (PojoBankTransferData data : transferDataList) {
            Query query = session.createQuery("from PojoBankTransferData where accNo = ? and accName = ? and tranAmt = ? and status = ?");
            query.setParameter(0, data.getAccNo());
            query.setParameter(1, data.getAccName());
            query.setParameter(2, data.getTranAmt());
            query.setParameter(3, "03");
			List<PojoBankTransferData> tranferDataList = query.list();
            if(tranferDataList.size()>0){
            	PojoBankTransferData data_old = tranferDataList.get(0);
            	data_old.setResInfo(data.getResInfo());
            	data_old.setResCode(data.getResCode().equals("PR02")? "00":"03");
            	data_old.setBankTranResNo(data.getBankTranResNo());
            	this.update(data_old);
                query = session.createQuery("update TxnsLogModel set payretcode=?,payretinfo=?,payordfintime=?,payrettsnseqno=?,retcode=?,retinfo=?, tradeseltxn=? where txnseqno=?");
                if(data.getResCode().equals("PR02")){
                    query.setParameter(0, "000000");
                    query.setParameter(1, "交易成功");
                    query.setParameter(4, "0000");
                    query.setParameter(5, "交易成功");
                }else{
                    query.setParameter(0, data.getResCode());
                    query.setParameter(1, data.getResInfo());
                    query.setParameter(4, data.getResCode());
                    query.setParameter(5, data.getResInfo());
                }
                query.setParameter(2, DateUtil.getCurrentDateTime());
                query.setParameter(3, "");
                query.setParameter(6, UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
                query.setParameter(7, data_old.getTranData().getTxnseqno());
                query.executeUpdate();
                
                //更新划拨数据
                PojoTranData tranData = data_old.getTranData();
                tranData.setStatus(data.getResCode().equals("PR02")? "02": "03");
                tranDataDAO.update(tranData);
                //更新划拨批次信息
                tranBatchDAO.updateBankTransferResult(data_old.getTranData().getTranBatch().getTid());
            }
            
        }

    }

    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void batchUpdateTransDataAccStatus(List<PojoBankTransferData> transferDataList) {
        // TODO Auto-generated method stub
        /*StringBuffer hqlBuffer = new StringBuffer();
        hqlBuffer.append("update PojoBankTransferData set ");
        hqlBuffer.append("accstatus = ?,");
        hqlBuffer.append("accinfo = ?");
        hqlBuffer.append("where tranid = ? ");
        Session session = getSession();
        for(PojoBankTransferData data : transferDataList) {
            Query query = session.createQuery(hqlBuffer.toString());
            query.setParameter(0, data.getAccstatus());
            query.setParameter(1, data.getAccinfo());
            query.setParameter(2, data.getTranid());
            query.executeUpdate();
        }*/
    }

    /**
    *
    * @param e
    * @return
    */
   
   protected Criteria buildCriteria(TransferDataQuery e) {
       Criteria crite = this.getSession().createCriteria(
               PojoBankTransferData.class);

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
    public PojoBankTransferData getTransferDataByTranId(String tranid,String status) {
        Criteria crite = this.getSession().createCriteria(
                PojoBankTransferData.class);
        crite.add(Restrictions.eq("tranid", tranid));
        crite.add(Restrictions.eq("status", status));
       return  (PojoBankTransferData) crite.uniqueResult();
    }

    public PojoBankTransferData getTransferDataByTranId(String bankTranDataSeqNo) {
        Criteria crite = this.getSession().createCriteria(
                PojoBankTransferData.class);
        crite.add(Restrictions.eq("bankTranDataSeqNo", bankTranDataSeqNo));
        
       return  (PojoBankTransferData) crite.uniqueResult();
    }
    
    public static void main(String[] args) {
    	Object object = null;
        System.out.println((String)object);
    }
    
    /**
     * 根据划拨流水ID取出转账ID
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PojoBankTransferData> getByTranDataId(Long id) {
        Criteria crite= this.getSession().createCriteria(PojoBankTransferData.class);
        crite.add(Restrictions.eq("tranData.id", id));
        return crite.list();
    }

	@Override
	@Transactional(readOnly=true)
	public Map<String, Object> queryBankTransferDataByPage(
			QueryTransferBean queryTransferBean, int page, int pageSize) {
		StringBuffer sqlBuffer = new StringBuffer("from PojoBankTransferData where 1=1 ");
		StringBuffer sqlCountBuffer = new StringBuffer("select count(*) from PojoBankTransferData where 1=1 ");
		List<Object> parameterList = new ArrayList<Object>();
		if(queryTransferBean!=null){
			if(queryTransferBean.getTid()!=0){
				sqlBuffer.append(" and bankTranBatch.tid = ? ");
				sqlCountBuffer.append(" and bankTranBatch.tid = ? ");
				parameterList.add(queryTransferBean.getTid());
			}
			if(StringUtil.isNotEmpty(queryTransferBean.getStatus())){
				sqlBuffer.append(" and status = ? ");
				sqlCountBuffer.append(" and status = ? ");
				parameterList.add(queryTransferBean.getStatus());
			}
		}
		Query query = getSession().createQuery(sqlBuffer.toString());
		Query countQuery = getSession().createQuery(sqlCountBuffer.toString());
		int i = 0;
		for(Object parameter : parameterList){
			query.setParameter(i, parameter);
			countQuery.setParameter(i, parameter);
			i++;
		}
		query.setFirstResult((pageSize)*((page==0?1:page)-1));
    	query.setMaxResults(pageSize);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//System.out.println(JSON.toJSONString(countQuery.list()));
		resultMap.put("total", countQuery.uniqueResult());
		resultMap.put("rows", query.list());
		return resultMap;
	}

	@Override
	public List<PojoBankTransferData> findBankTransferDataByBankTranBatchId(
			Long bankTranBatchId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void updateWaitBankTransferStatus(String bankTranBatchId,
			String status) {
		
		 try {
	            String hql = "update PojoBankTransferData set status = ? where bankTranBatch.tid = ? and status = ?";
	            Session session = getSession();
	            Query query = session.createQuery(hql);
	            query.setParameter(0, status);
	            query.setParameter(1, Long.valueOf(bankTranBatchId));
	            query.setParameter(2, "01");
	            query.executeUpdate();
	        } catch (HibernateException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	}
	
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void updateBankTransferStatus(String bankTranBatchId,String status) {
		
		 try {
	            String hql = "update PojoBankTransferData set status = ? where bankTranBatch.tid = ?";
	            Session session = getSession();
	            Query query = session.createQuery(hql);
	            query.setParameter(0, status);
	            query.setParameter(1, Long.valueOf(bankTranBatchId));
	            query.executeUpdate();
	        } catch (HibernateException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	}
	
	
	
}
