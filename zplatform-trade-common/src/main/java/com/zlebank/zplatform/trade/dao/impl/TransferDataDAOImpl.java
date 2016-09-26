package com.zlebank.zplatform.trade.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.acc.exception.AbstractBusiAcctException;
import com.zlebank.zplatform.acc.exception.AccBussinessException;
import com.zlebank.zplatform.acc.service.AccEntryService;
import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.trade.bean.page.QueryTransferBean;
import com.zlebank.zplatform.trade.dao.TransferBatchDAO;
import com.zlebank.zplatform.trade.dao.TransferDataDAO;
import com.zlebank.zplatform.trade.model.PojoTranBatch;
import com.zlebank.zplatform.trade.model.PojoTranData;
@Repository("transferDataDAO")
public class TransferDataDAOImpl  extends HibernateBaseDAOImpl<PojoTranData> implements TransferDataDAO {
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(TransferDataDAOImpl.class);

    @Autowired
    private TransferBatchDAO transferBatchDAO;
    @Autowired
    private AccEntryService accEntryService;
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public Map<String, Object> queryTranfersDetaByPage(QueryTransferBean queryTransferBean,int page,int pageSize){
    	StringBuffer sqlBuffer = new StringBuffer("from PojoTranData where 1=1 ");
		StringBuffer sqlCountBuffer = new StringBuffer("select count(*) from PojoTranData where 1=1 ");
		List<Object> parameterList = new ArrayList<Object>();
		if(queryTransferBean!=null){
			
			if(queryTransferBean.getTid()!=0){
				sqlBuffer.append(" and tranBatch.tid = ? ");
				sqlCountBuffer.append(" and tranBatch.tid = ? ");
				parameterList.add(queryTransferBean.getTid());
			}
			if(StringUtil.isNotEmpty(queryTransferBean.getStatus())){
				if("02".equals(queryTransferBean.getStatus())){
					sqlBuffer.append(" and (status = ? or status = ?) ");
					sqlCountBuffer.append(" and (status = ? or status = ?) ");
					parameterList.add("00");
					parameterList.add("09");
				}else{
					sqlBuffer.append(" and status = ? ");
					sqlCountBuffer.append(" and status = ? ");
					parameterList.add(queryTransferBean.getStatus());
				}
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
		resultMap.put("total", ((Long) countQuery.uniqueResult()).longValue());
		resultMap.put("rows", query.list());
		return resultMap;
    }
    
    @Transactional(readOnly=true)
    public PojoTranData queryTransferData(Long tid){
    	StringBuffer sqlBuffer = new StringBuffer("from PojoTranData where 1=1 and tid = ?");
    	Query query = getSession().createQuery(sqlBuffer.toString());
    	query.setParameter(0, tid);
    	return (PojoTranData) query.uniqueResult();
    }
    @Transactional(readOnly=true)
    public List<Long> getBatchIDByMerchOrderNo(String merchOrderNo){
    	StringBuffer sqlBuffer = new StringBuffer("from PojoTranData where 1=1 and merchOrderNo = ?");
    	Query query = getSession().createQuery(sqlBuffer.toString());
    	query.setParameter(0, merchOrderNo);
    	List<PojoTranData> list = query.list();
    	if(list.size()>0){
    		List<Long> batchIdList = new ArrayList<Long>();
    		for(PojoTranData tranData : list){
    			batchIdList.add(tranData.getTranBatch().getTid());
    		}
    		return batchIdList;
    	}
    	
    	
    	return null;
    }
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void singleTrailTransfer(Long tid,String status) throws AccBussinessException, AbstractBusiAcctException, NumberFormatException{
    	
    	PojoTranData transferData = queryTransferData(tid);
    	//统计审核通过和不同过的数据，笔数和金额
		long approveCount = 0L;
		long approveAmount = 0L;
		long unApproveCount = 0L;
		long unApproveAmount = 0L;
		//判断划拨明细数据状态

    	PojoTranBatch transferBatch = transferBatchDAO.getByBatchId(transferData.getTranBatch().getTid());

    	//PojoTranBatch transferBatch = transferBatchDAO.getByBatchNo(transferData.getTranBatch().getTid()+"");

    	if("00".equals(status)){//审核通过的执行分批算法
    		//PojoTranData[] pojoTransferDatas = new PojoTranData[]{transferData};
    		//
			if("00".equals(transferData.getStatus())){
				approveCount++;
				approveAmount+=transferData.getTranAmt().longValue();
			}else{
				unApproveCount++;
				unApproveAmount+=transferData.getTranAmt().longValue();
			}
    		transferBatch.setApproveAmt(transferBatch.getApproveAmt()+approveAmount);
    		transferBatch.setApproveCount(approveCount+transferBatch.getApproveCount());
    		transferBatch.setRefuseAmt(transferBatch.getRefuseAmt()+unApproveAmount);
    		transferBatch.setRefuseCount(unApproveCount+transferBatch.getRefuseCount());
    		
    	}else{
			unApproveCount++;
			unApproveAmount+=transferData.getTranAmt().longValue();
			transferData.setStatus(status);
			this.update(transferData);
    		transferBatch.setRefuseAmt(transferBatch.getRefuseAmt()+unApproveAmount);
    		transferBatch.setRefuseCount(unApproveCount+transferBatch.getRefuseCount());
    	}
    	updateBatchTransferSingle(transferBatch);
    }
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateBatchTransferSingle(PojoTranBatch transferBatch){
    	StringBuffer sqlBuffer = new StringBuffer("select count(*) from PojoTranData where 1=1 and tranBatch.tid = ? and status = ?");
    	Query query = getSession().createQuery(sqlBuffer.toString());
    	query.setParameter(0, transferBatch.getTid());
    	query.setParameter(1, "01");
    	Long count = ((Long) query.uniqueResult()).longValue();
    	if(count==0){
    		transferBatch.setStatus("03");
    		transferBatch.setWaitApproveCount(0L);
    		transferBatch.setWaitApproveAmt(0L);
    		transferBatch.setApproveFinishTime(new Date());
    	}else{
    		//查询未审核的总金额
    		sqlBuffer = new StringBuffer("select sum(tranAmt) from PojoTranData where 1=1 and tranBatch.tid = ? and status = ?");
    		query = getSession().createQuery(sqlBuffer.toString());
    		query.setParameter(0, transferBatch.getTid());
        	query.setParameter(1, "01");
        	Long amount = ((Long) query.uniqueResult()).longValue();
    		transferBatch.setWaitApproveCount(count);
    		transferBatch.setWaitApproveAmt(amount);
    		transferBatch.setStatus("02");
    	}
    	transferBatchDAO.updateBatchTransferFinish(transferBatch);
	}

	@Override
	public List<PojoTranData> findTransDataByBatchNoAndAccstatus(String batchNo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void updateTransferDataStatus(Long tid, String status) {
		// TODO Auto-generated method stub
		String sql = "update PojoTranData set status= ? where tid = ?";
		Query query = getSession().createQuery(sql);
		query.setParameter(0, status);
		query.setParameter(1, tid);
		query.executeUpdate();
	}

	@Override
	public Long queryWaritTransferCount(Long tranBatchId) {
		String sql = "select count(*) from PojoTranData  where tranBatch.tid = ? and status not in (?,?,?)";
		Query query = getSession().createQuery(sql);
		query.setParameter(0, tranBatchId);
		query.setParameter(1, "02");
		query.setParameter(2, "03");
		query.setParameter(3, "09");
		return ((Long)query.uniqueResult()).longValue();
	}
	@SuppressWarnings("unchecked")
    @Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public List<PojoTranData> queryWaritTransferData(Long tranBatchId) {
		String sql = "from PojoTranData  where tranBatchId = ? and status= ?";
		Query query = getSession().createQuery(sql);
		query.setParameter(0, tranBatchId);
		query.setParameter(1, "02");
		return query.list();
	}
}

