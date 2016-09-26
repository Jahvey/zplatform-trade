/* 
 * BatchSpliter.java  
 * 
 * version TODO
 *
 * 2016年3月3日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.batch.spliter;

import com.zlebank.zplatform.trade.exception.RecordsAlreadyExistsException;
import com.zlebank.zplatform.trade.model.PojoTranData;

/**
 * 批次分流器
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年3月3日 下午5:40:55
 * @since 
 */
public interface BatchSpliter {
    /**
     * 针对划拨流水进行合并/拆分后，转换为转账流水并保存(N->N)<p/>
     * 完成后更新划拨流水
     * <p/>
     * 关于如何操作转账批次参照：
     * @see com.zlebank.zplatform.trade.batch.spliter.BatchManager
     *
     * @param datas 多笔划拨流水的Pojo
     * @throws RecordsAlreadyExistsException 
     */
    public void split(PojoTranData[] datas) throws RecordsAlreadyExistsException;
}
