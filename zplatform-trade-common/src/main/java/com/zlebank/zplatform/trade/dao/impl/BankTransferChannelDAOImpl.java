/* 
 * BankTransferChannelDAOImpl.java  
 * 
 * version TODO
 *
 * 2016年3月7日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.commons.dao.impl.HibernateBaseDAOImpl;
import com.zlebank.zplatform.trade.dao.BankTransferChannelDAO;
import com.zlebank.zplatform.trade.model.PojoBankTransferChannel;

/**
 * Class Description
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年3月7日 下午3:41:07
 * @since 
 */
@Repository
public class BankTransferChannelDAOImpl  extends HibernateBaseDAOImpl<PojoBankTransferChannel> implements BankTransferChannelDAO{

    /**
     * 根据划拨渠道代码取相应的划拨渠道信息
     * @param channelCode
     * @return
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public PojoBankTransferChannel getByChannelCode(String channelCode) {
        Criteria crite= this.getSession().createCriteria(PojoBankTransferChannel.class);
        crite.add(Restrictions.eq("channelCode", channelCode));
        return (PojoBankTransferChannel) crite.uniqueResult();
    }
}

