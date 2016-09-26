/* 
 * TxnsGatewaypayServiceImpl.java  
 * 
 * version TODO
 *
 * 2015年9月7日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.acc.pojo.Money;
import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.chanpay.ChanPayOrderBean;
import com.zlebank.zplatform.trade.dao.ITxnsGatewaypayDAO;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.model.TxnsGatewaypayModel;
import com.zlebank.zplatform.trade.service.ITxnsGatewaypayService;
import com.zlebank.zplatform.trade.service.base.BaseServiceImpl;
import com.zlebank.zplatform.trade.utils.ConsUtil;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月7日 下午7:17:27
 * @since 
 */
@Service("txnsGatewaypayService")
public class TxnsGatewaypayServiceImpl extends BaseServiceImpl<TxnsGatewaypayModel,Long> implements ITxnsGatewaypayService{
    
    @Autowired
    private ITxnsGatewaypayDAO txnsGatewaypayDAO;
    
    
    /**
     *
     * @return
     */
    @Override
    public Session getSession() {
        // TODO Auto-generated method stub
        return txnsGatewaypayDAO.getSession();
    }
    
    @Transactional
    public ResultBean saveGateWay(TxnsGatewaypayModel txnsGateway){
        super.save(txnsGateway);
        
        return null;
    }
    @Transactional
    public ResultBean updateGateWay(TxnsGatewaypayModel txnsGateway){
        super.update(txnsGateway);
        return null;
    }

    /**
     *
     * @param orderNo
     * @return
     */
    @Override
    public TxnsGatewaypayModel getOrderByOrderNo(String orderNo) {
        return super.getUniqueByHQL("from TxnsGatewaypayModel where payorderno = ?", new Object[]{orderNo});
    }

	/**
	 *
	 * @param orderBean
	 * @throws TradeException
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void saveChanPayGateWay(ChanPayOrderBean orderBean)
			throws TradeException {
		TxnsGatewaypayModel txnsGateway = new  TxnsGatewaypayModel();
		txnsGateway.setId(1L);
		txnsGateway.setInstitution(ConsUtil.getInstance().cons.getChanpay_channel_code());
		txnsGateway.setPayorderno(orderBean.getOut_trade_no());
		txnsGateway.setPayamt(Money.yuanValueOf(Double.valueOf(orderBean.getTrade_amount())).getAmount().longValue());
		txnsGateway.setPaycommtime(DateUtil.getCurrentDateTime());
		txnsGateway.setRelatetradetxn(orderBean.getTxnseqno());
		txnsGateway.setFirmemberno(ConsUtil.getInstance().cons.getChanpay_partner_id());
		txnsGateway.setFirmembername(ConsUtil.getInstance().cons.getChanpay_partner_name());
		txnsGateway.setPaynum(1L);
		txnsGateway.setPaycode(orderBean.getPay_method());
		txnsGateway.setPaytype(orderBean.getPay_type());
		txnsGateway.setBankcode(orderBean.getBank_code());
		txnsGateway.setStatus("01");//支付中
		txnsGateway.setBankcode(orderBean.getBank_code());
		save(txnsGateway);
	}
	
	
    
    

}
