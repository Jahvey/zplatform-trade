/* 
 * MerchWhiteListServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年8月17日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.trade.dao.MerchWhiteListDAO;
import com.zlebank.zplatform.trade.model.PojoMerchWhiteList;
import com.zlebank.zplatform.trade.service.MerchWhiteListServiceExt;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年8月17日 下午2:27:52
 * @since 
 */
@Service("merchWhiteListServiceExt")
public class MerchWhiteListServiceExtImpl implements MerchWhiteListServiceExt{

	@Autowired
	private MerchWhiteListDAO merchWhiteListDAO;

	/**
	 *
	 * @param merId
	 * @param accNo
	 * @param accName
	 * @return
	 */
	@Override
	@Transactional(readOnly=true)
	public PojoMerchWhiteList getWhiteListByCardNoAndName(String merId,
			String accNo, String accName) {
		// TODO Auto-generated method stub
		return merchWhiteListDAO.getWhiteListByCardNoAndName(merId, accNo, accName);
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	@Override
	@Transactional(readOnly=true)
	public PojoMerchWhiteList getMerchWhiteListById(Long id) {
		// TODO Auto-generated method stub
		return merchWhiteListDAO.getMerchWhiteListById(id);
	}

	/**
	 *
	 * @param merchWhiteList
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void merge(PojoMerchWhiteList merchWhiteList) {
		
		merchWhiteListDAO.merge(merchWhiteList);
	}
	
}
