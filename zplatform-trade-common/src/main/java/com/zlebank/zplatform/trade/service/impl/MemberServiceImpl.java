/* 
 * MemberServiceImpl.java  
 * 
 * version TODO
 *
 * 2015年9月1日 
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

import com.zlebank.zplatform.member.dao.MemberBaseDAO;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.model.MemberBaseModel;
import com.zlebank.zplatform.trade.service.IMemberService;
import com.zlebank.zplatform.trade.service.base.BaseServiceImpl;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月1日 下午5:19:11
 * @since 
 */
@Service("memberService")
public class MemberServiceImpl extends BaseServiceImpl<MemberBaseModel, String> implements IMemberService{
    @Autowired
    private MemberBaseDAO memberBaseDAO;
    /**
     *
     * @return
     */
    @Override
    public Session getSession() {
        return memberBaseDAO.getCurrentSession();
    }
    /**
     *
     * @param merchId
     * @param subMerchId
     * @return
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public ResultBean verifySubMerch(String merchId, String subMerchId) {
        String queryString = "from MemberBaseModel mem where memberid=? and parent=?";
        MemberBaseModel membModel = super.getUniqueByHQL(queryString, new Object[]{subMerchId,merchId});
        if(membModel==null){
            return new ResultBean("GW07","二级商户与一级商户不匹配");
        }
        return new ResultBean(null);
    }
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public MemberBaseModel getMemberByMemberId(String memberId){
        return super.get(memberId);
    }
    
    

}
