/* 
 * IChnlDetaService.java  
 * 
 * version TODO
 *
 * 2015年9月7日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import com.zlebank.zplatform.trade.model.ChnlDetaModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月7日 下午8:45:00
 * @since 
 */
public interface IChnlDetaService extends IBaseService<ChnlDetaModel, Long>{
	/**
	 * 通过渠道代码获取渠道信息
	 * @param chnlCode
	 * @return
	 */
    public ChnlDetaModel getChannelByCode(String chnlCode);
}
