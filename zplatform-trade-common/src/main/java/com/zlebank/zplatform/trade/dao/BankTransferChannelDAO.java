/* 
 * BankTransferChannelDAO.java  
 * 
 * version TODO
 *
 * 2016年3月7日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao;

import com.zlebank.zplatform.commons.dao.BaseDAO;
import com.zlebank.zplatform.trade.model.PojoBankTransferChannel;

/**
 * Class Description
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年3月7日 下午3:40:37
 * @since 
 */
public interface BankTransferChannelDAO  extends BaseDAO<PojoBankTransferChannel>{
    PojoBankTransferChannel getByChannelCode(String channelCode);
}
