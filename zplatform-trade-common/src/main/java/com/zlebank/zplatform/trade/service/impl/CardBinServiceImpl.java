/* 
 * CardBinServiceImpl.java  
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
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.commons.bean.CardBin;
import com.zlebank.zplatform.commons.dao.CardBinDao;
import com.zlebank.zplatform.commons.utils.BeanCopyUtil;
import com.zlebank.zplatform.trade.bean.CardBinBean;
import com.zlebank.zplatform.trade.service.CardBinService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年8月17日 下午2:18:01
 * @since 
 */
@Service("cardBinService")
public class CardBinServiceImpl implements CardBinService{

	@Autowired
	private CardBinDao cardBinDao;

	/**
	 *
	 * @param cardNo
	 * @return
	 */
	@Override
	@Transactional(readOnly=true)
	public CardBinBean getCard(String cardNo) {
		// TODO Auto-generated method stub
		CardBin cardBin = cardBinDao.getCard(cardNo);
		if(cardBin!=null){
			return BeanCopyUtil.copyBean(CardBinBean.class, cardBin);
		}
		return null;
	}
	
	
}
