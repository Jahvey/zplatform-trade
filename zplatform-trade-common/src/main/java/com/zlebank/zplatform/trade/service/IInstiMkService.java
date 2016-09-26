/* 
 * IInstiMkService.java  
 * 
 * version TODO
 *
 * 2015年9月7日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import com.zlebank.zplatform.trade.model.InstiMkModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月7日 下午8:11:15
 * @since 
 */
public interface IInstiMkService extends IBaseService<InstiMkModel, String>{
	@Deprecated
    public InstiMkModel getMKbySafeseq(String safeseq);
}
