/* 
 * IProdCaseService.java  
 * 
 * version TODO
 *
 * 2015年9月11日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.gateway.OrderBean;
import com.zlebank.zplatform.trade.bean.wap.WapOrderBean;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.model.ProdCaseModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月11日 下午5:28:07
 * @since 
 */
public interface IProdCaseService extends IBaseService<ProdCaseModel, Long>{
	/**
	 * 校验业务类型
	 * @param order
	 * @return
	 */
    public ResultBean verifyBusiness(OrderBean order);
    /**
     * 验证商户收银台商户业务版本
     * @param order
     * @return
     */
    @Deprecated
    public ResultBean verifyMerchBusiness(OrderBean order);
    public void verifyWapBusiness(WapOrderBean order) throws TradeException;
}
