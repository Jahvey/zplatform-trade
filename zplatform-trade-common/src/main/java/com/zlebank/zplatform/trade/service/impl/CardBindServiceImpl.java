/* 
 * CardBindServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年6月22日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.trade.dao.CardBindDAO;
import com.zlebank.zplatform.trade.model.PojoCardBind;
import com.zlebank.zplatform.trade.service.CardBindService;
import com.zlebank.zplatform.trade.service.base.BaseServiceImpl;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年6月22日 下午4:27:55
 * @since 
 */
@Service("cardBindService")
public class CardBindServiceImpl extends BaseServiceImpl<PojoCardBind, Long> implements CardBindService{

	@Autowired
	private CardBindDAO cardBindDAO;
	/**
	 *
	 * @return
	 */
	@Override
	public Session getSession() {
		// TODO Auto-generated method stub
		return cardBindDAO.getSession();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void save(PojoCardBind cardBind){
		if(getCardBind(cardBind.getCardId(), cardBind.getChnlCode())==null){
			super.saveOrUpdate(cardBind);
		}
	}
	public PojoCardBind getCardBind(Long cardId,String chnlCode){
		String hql = " from PojoCardBind where cardId = ? and chnlCode = ?";
		PojoCardBind cardBind = getUniqueByHQL(hql, new Object[]{cardId,chnlCode});
		return cardBind;
	}
}
