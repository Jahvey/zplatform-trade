/* 
 * CardBinService.java  
 * 
 * version TODO
 *
 * 2016年8月17日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import com.zlebank.zplatform.trade.bean.CardBinBean;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年8月17日 下午2:16:01
 * @since
 */
public interface CardBinService {

	/**
	 * 根据卡号得到卡bin信息
	 * 
	 * @param cardNo
	 * @return
	 */
	public CardBinBean getCard(String cardNo);
}
