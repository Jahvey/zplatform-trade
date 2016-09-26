/* 
 * IThreadPool.java  
 * 
 * version TODO
 *
 * 2015年11月18日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.pool;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月18日 上午9:32:58
 * @since 
 */
public interface IThreadPool {
    public void executeMission(Runnable runnable);
}
