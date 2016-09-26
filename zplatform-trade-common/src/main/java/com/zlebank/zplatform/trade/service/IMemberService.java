/* 
 * IMemberService.java  
 * 
 * version TODO
 *
 * 2015年9月1日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.model.MemberBaseModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月1日 下午5:18:10
 * @since 
 */
public interface IMemberService extends IBaseService<MemberBaseModel, String>{
    /**
     * 校验子商户从属关系
     * @param merchId
     * @param subMerchId
     * @return
     */
	@Deprecated
    public ResultBean verifySubMerch(String merchId,String subMerchId);
    
    /**
     * 通过会员标示获取会员信息
     * @param memberId
     * @return
     */
	@Deprecated
    public MemberBaseModel getMemberByMemberId(String memberId);
}
