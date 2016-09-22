/* 
 * CashBankServiceImpl.java  
 * 
 * version TODO
 *
 * 2015年10月14日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.TrueFalseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.trade.common.page.PageVo;
import com.zlebank.zplatform.trade.dao.ICashBankDAO;
import com.zlebank.zplatform.trade.model.CashBankModel;
import com.zlebank.zplatform.trade.service.ICashBankService;
import com.zlebank.zplatform.trade.service.base.BaseServiceImpl;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年10月14日 下午3:07:10
 * @since 
 */
@Service("cashBankService")
public class CashBankServiceImpl extends BaseServiceImpl<CashBankModel, Long> implements ICashBankService{

    @Autowired
    private ICashBankDAO cashBankDAO;
    /**
     *
     * @return
     */
    @Override
    public Session getSession() {
        // TODO Auto-generated method stub
        return cashBankDAO.getSession();
    }
    
    @Transactional(readOnly=true)
    public List<CashBankModel> findBankByCashCode(String cashCode){
        return (List<CashBankModel>) super.queryByHQL(" from CashBankModel where cashcode = ? and status = ? ", new Object[]{cashCode,"00"});
    }
    @Transactional(readOnly=true)
    public List<CashBankModel> findBankByPaytype(String payType){
        return (List<CashBankModel>) super.queryByHQL(" from CashBankModel where paytype = ? and status = ? ", new Object[]{payType,"00"});
    }
    
    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
	public List<CashBankModel> findBankPage(int page,int pageSize){
    	Query query = cashBankDAO.getSession().createQuery("from CashBankModel where paytype = ? and status = ? order by  tid asc");
    	query.setString(0, "01");
    	query.setString(1, "00");
    	query.setFirstResult((pageSize)*((page==0?1:page)-1));
    	query.setMaxResults(pageSize);
		return query.list();
    }
    @Transactional(readOnly=true)
    public long findBankCount(){
    	Query query = cashBankDAO.getSession().createSQLQuery("select count(1) as total from T_CASH_BANK where paytype = ? and status = ?").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
    	query.setString(0, "01");
    	query.setString(1, "00");
    	
    	Map<String, BigDecimal> valueMap = (Map<String, BigDecimal>) query.uniqueResult();
    	return valueMap.get("TOTAL").longValue();
    }
    
	@Override
	@Transactional(readOnly=true)
	public PageVo<CashBankModel> getCardList(Map<String, Object> map,
			Integer pageNo, Integer pageSize) {
		PageVo<CashBankModel> pageVo = new PageVo<CashBankModel>();
		StringBuffer hq = new StringBuffer();
		if(map!=null){
			//卡类型 1-借记卡 2=贷记卡
			String cardType = map.get("cardtype")==null ?"":map.get("cardtype").toString();
			//类型 01：快捷 02：网关
			String payType = map.get("paytype")==null ?"" :map.get("paytype").toString();
			//状态 00-在用
			String status = map.get("status")==null ?"00":map.get("status").toString();
			//支持业务类型
			String busiCode = map.get("busiCode")==null ?"" : map.get("busiCode").toString();
			//银行卡号
			String bankCode = map.get("bankCode") ==null?"": map.get("bankCode").toString();
			if(StringUtils.isNotEmpty(cardType)){
				hq.append("and cardtype=:cardtype ");
			}
			if(StringUtils.isNotEmpty(payType)){
				hq.append(" and paytype=:paytype ");
			}
			if(StringUtils.isNotEmpty(status)){
				hq.append(" and status =:status");
			}
			if(StringUtils.isNotEmpty(busiCode)){
				hq.append(" and buicode =:busicode ");
			}
			if(StringUtils.isNotEmpty(bankCode)){
				hq.append(" and bankcode=:bankcode ");
			}
			
			//查询列表
			StringBuffer listHql= new StringBuffer(" from  CashBankModel where 1=1 ").append(hq);
			//查询总条数
			StringBuffer totalHql = new StringBuffer(" select count(*) from  CashBankModel where 1=1 ").append(hq);
	        
			Query query = cashBankDAO.getSession().createQuery(listHql.toString());
	        
			Query tquery = cashBankDAO.getSession().createQuery(totalHql.toString());
			
			if(StringUtils.isNotEmpty(cardType)){
				query.setParameter("cardtype", cardType);
				tquery.setParameter("cardtype", cardType);
			}
			if(StringUtils.isNotEmpty(payType)){
				query.setParameter("paytype", payType);
				tquery.setParameter("paytype", payType);
			}
			if(StringUtils.isNotEmpty(status)){
				query.setParameter("status", status);
				tquery.setParameter("status", status);
			}
			if(StringUtils.isNotEmpty(busiCode)){
				query.setParameter("busiCode", busiCode);
				tquery.setParameter("busiCode", busiCode);
			}
			if(StringUtils.isNotEmpty(bankCode)){
				query.setParameter("bankcode", bankCode);
				tquery.setParameter("bankcode", bankCode);
			}
			if(pageSize!= null && pageNo !=null){
				query.setFirstResult(((pageNo==0?1:pageNo)-1)*pageSize);
		    	query.setMaxResults(pageSize);
			}
			List<CashBankModel> list= query.list();
			pageVo.setList(list);
			int total =((Number) tquery.iterate().next()).intValue();
			pageVo.setTotal(total);
		}
		return pageVo;
	}

	/**
	 *
	 * @param bankCode
	 * @return
	 */
	@Override
	@Transactional(readOnly=true)
	public CashBankModel getBankICON(String bankCode) {
		return cashBankDAO.getBankICON(bankCode);
	}

}
