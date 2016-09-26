/* 
 * NotifyInsteadURLService.java  
 * 
 * version TODO
 *
 * 2016年3月30日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

/**
 * 代付URL通知类
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年3月30日 下午3:35:23
 * @since 
 */
public interface NotifyInsteadURLService {
    /**
     * 增加一个代付任务
     * @param batchId
     */
	public void addInsteadPayTask(Long batchId);
}
