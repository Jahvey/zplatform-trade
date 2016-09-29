package com.zlebank.zplatform.trade.service;

import com.zlebank.zplatform.trade.bean.gateway.TransferOrderBean;
import com.zlebank.zplatform.trade.exception.CommonException;

public interface ICreditAccoutService {
	
	public String creditAccountRecharge(TransferOrderBean order) throws CommonException;
	
	
	
	
	
	
	
	
	
}
