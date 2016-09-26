
package com.zlebank.zplatform.trade.dao.impl;

import java.awt.color.CMMException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.trade.bean.page.QueryTransferBean;
import com.zlebank.zplatform.trade.dao.TransferBatchDAO;
import com.zlebank.zplatform.trade.dao.TransferDataDAO;
import com.zlebank.zplatform.trade.model.PojoTranBatch;
import com.zlebank.zplatform.trade.model.PojoTranData;

@Repository("transferBatchDAO")
public class TransferBatchDAOImpl extends HibernateBaseDAOImpl<PojoTranBatch>
        implements
            TransferBatchDAO {
    private static final Log log = LogFactory
            .getLog(TransferBatchDAOImpl.class);

    @Autowired
    private TransferDataDAO transferDataDAO;

    @Transactional(readOnly = true)
    public Map<String, Object> queryTransferBatchByPage(QueryTransferBean queryTransferBean,
            int page,
            int pageSize) throws ParseException {
        StringBuffer sqlBuffer = new StringBuffer(
                "from PojoTranBatch where  (status = ? or status = ?)");
        StringBuffer sqlCountBuffer = new StringBuffer(
                "select count(*) from PojoTranBatch  where (status = ? or status = ?)");
        List<Object> parameterList = new ArrayList<Object>();
        List<Long> batchIdList = null;
        parameterList.add("01");
        parameterList.add("00");
        if (queryTransferBean != null) {
            if (StringUtil.isNotEmpty(queryTransferBean.getBatchNo())) {
                sqlBuffer.append(" and tranBatchNo = ? ");
                sqlCountBuffer.append(" and tranBatchNo = ? ");
                parameterList.add(queryTransferBean.getBatchNo());
            }
            if (StringUtil.isNotEmpty(queryTransferBean.getBeginDate())) {
                sqlBuffer.append(" and applyTime >= ? ");
                sqlCountBuffer.append(" and applyTime >= ? ");
                parameterList.add(DateUtil.convertToDate(queryTransferBean.getBeginDate(), "yyyy-MM-dd"));
            }
            if (StringUtil.isNotEmpty(queryTransferBean.getEndDate())) {
                sqlBuffer.append(" and applyTime <= ? ");
                sqlCountBuffer.append(" and applyTime <= ? ");
                parameterList.add(DateUtil.convertToDate(queryTransferBean.getEndDate(), "yyyy-MM-dd"));
            }
            if(StringUtil.isNotEmpty(queryTransferBean.getBusiType())){
            	sqlBuffer.append(" and busiType = ? ");
                sqlCountBuffer.append(" and busiType = ? ");
                parameterList.add(queryTransferBean.getBusiType());
            }
            
            if(StringUtil.isNotEmpty(queryTransferBean.getMerchOrderNo())){
				sqlBuffer.append(" and tid in (:alist) ");
				sqlCountBuffer.append(" and tid in (:alist)");
				batchIdList = transferDataDAO.getBatchIDByMerchOrderNo(queryTransferBean.getMerchOrderNo());
			}
        }
        sqlBuffer.append(" order by applyTime desc");
        Query query = getSession().createQuery(sqlBuffer.toString());
        Query countQuery =
        getSession().createQuery(sqlCountBuffer.toString());
        int i = 0;
        for (Object parameter : parameterList) {
            query.setParameter(i, parameter);
            countQuery.setParameter(i, parameter);
            i++;
        }
        if(batchIdList!=null){
			query.setParameterList("alist", batchIdList);
			countQuery.setParameterList("alist", batchIdList);
		}
        query.setFirstResult((pageSize) * ((page == 0 ? 1 : page) - 1));
        query.setMaxResults(pageSize);

         
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("total", countQuery.uniqueResult());
        resultMap.put("rows", query.list());
        return resultMap;
    }
   
    @Transactional
    public void updateBatchTransferFinish(PojoTranBatch transferBatch) {
        // TODO Auto-generated method stub
        try {
            this.update(transferBatch);
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new CMMException("M002");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(readOnly = true)
    public List<PojoTranData> queryWaitTrialTranData(long tranBatchId) {
        StringBuffer sqlBuffer = new StringBuffer(
                "from PojoTranData where 1=1 and tranBatch.tid = ? and status = ?");
        Query query = getSession().createQuery(sqlBuffer.toString());
        query.setParameter(0, tranBatchId);
        query.setParameter(1, "01");
        return query.list();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public void batchTrailTransfer(String batchNo, String status) {

    }

    public PojoTranBatch getByBatchId(long batchId) {
        String queryString = "from PojoTranBatch where tid = ? ";
        try {
            if (log.isDebugEnabled()) {
                log.info("queryString:" + queryString);
            }
            Query query = getSession().createQuery(queryString);
            query.setParameter(0, batchId);
            return (PojoTranBatch) query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new CMMException("M001");
        }
    }

}