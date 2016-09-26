/* 
 * CardBindService.java  
 * 
 * version TODO
 *
 * 2016年6月22日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import com.zlebank.zplatform.trade.model.PojoCardBind;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年6月22日 下午4:27:03
 * @since 
 */
public interface CardBindService extends IBaseService<PojoCardBind, Long>{

	public void save(PojoCardBind cardBind);
	public PojoCardBind getCardBind(Long cardId,String chnlCode);
}
