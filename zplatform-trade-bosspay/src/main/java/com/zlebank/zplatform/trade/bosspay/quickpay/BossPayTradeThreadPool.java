/* 
 * TestTradeThreadPool.java  
 * 
 * version TODO
 *
 * 2015年11月17日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bosspay.quickpay;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zlebank.zplatform.trade.pool.IThreadPool;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月17日 上午11:10:11
 * @since 
 */
public class BossPayTradeThreadPool implements IThreadPool{
    private static final Log log = LogFactory.getLog(BossPayTradeThreadPool.class);
    private static ExecutorService executorService;
    private static BossPayTradeThreadPool pool;
    private BossPayTradeThreadPool(){
        if(executorService==null){
            executorService = Executors.newCachedThreadPool();
        }else{
            if(executorService.isShutdown()||executorService.isTerminated()){
                executorService = Executors.newCachedThreadPool();
            }
            
        }
    }
    
    public static synchronized BossPayTradeThreadPool getInstance(){
        if(pool==null){
            pool = new BossPayTradeThreadPool();
        }
        return pool;
    }
    
    public void executeMission(Runnable runnable){
        if(executorService.isShutdown()||executorService.isTerminated()){
            executorService = Executors.newCachedThreadPool();
        }
        log.info("thread pool :"+executorService);
        
        executorService.execute(runnable);
        log.info("thread pool :"+executorService);
        //runnable.run();
    }
}
