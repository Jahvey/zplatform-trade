/* 
 * WeChatOrderinfoServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年8月8日 
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

import com.zlebank.zplatform.trade.dao.WeChatOrderinfoDAO;
import com.zlebank.zplatform.trade.model.PojoTxnsWechatOrderinfo;
import com.zlebank.zplatform.trade.service.WeChatOrderinfoService;
import com.zlebank.zplatform.trade.service.base.BaseServiceImpl;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年8月8日 下午5:12:39
 * @since 
 */
@Service("weChatOrderinfoService")
public class WeChatOrderinfoServiceImpl extends BaseServiceImpl<PojoTxnsWechatOrderinfo, Long> implements WeChatOrderinfoService{

	@Autowired
	private WeChatOrderinfoDAO weChatOrderinfoDAO;
	/**
	 *
	 * @return
	 */
	@Override
	public Session getSession() {
		// TODO Auto-generated method stub
		return weChatOrderinfoDAO.getSession();
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void saveWeChatOrder(PojoTxnsWechatOrderinfo wechatOrderinfo){
		super.save(wechatOrderinfo);
	}

	/**
	 *
	 * @param out_trade_no
	 * @return
	 */
	@Override
	@Transactional(readOnly=true)
	public PojoTxnsWechatOrderinfo getWechatOrderinfo(String out_trade_no) {
		return super.getUniqueByHQL(" from PojoTxnsWechatOrderinfo where outTradeNo = ?", new Object[]{out_trade_no});
	}

	/**
	 *
	 * @param out_trade_no
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void updateOrderToOverdue(String out_trade_no) {
		// TODO Auto-generated method stub
		super.updateByHQL("update PojoTxnsWechatOrderinfo set status = ? where outTradeNo = ?", new Object[]{"04",out_trade_no});
	}

	/**
	 *
	 * @param out_trade_no
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void updateOrderToSuccess(String out_trade_no) {
		// TODO Auto-generated method stub
		super.updateByHQL("update PojoTxnsWechatOrderinfo set status = ? where outTradeNo = ?", new Object[]{"00",out_trade_no});
	}

	/**
	 *
	 * @param out_trade_no
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void updateOrderToFailure(String out_trade_no) {
		// TODO Auto-generated method stub
		super.updateByHQL("update PojoTxnsWechatOrderinfo set status = ? where outTradeNo = ?", new Object[]{"03",out_trade_no});
	}
	
	

}
