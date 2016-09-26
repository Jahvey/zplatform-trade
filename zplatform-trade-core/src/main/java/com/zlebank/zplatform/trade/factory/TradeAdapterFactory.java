/* 
 * TradeAdapterFactory.java  
 * 
 * version TODO
 *
 * 2015年9月6日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.factory;

import org.springframework.beans.factory.annotation.Autowired;

import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.trade.adapter.insteadpay.IInsteadPayTrade;
import com.zlebank.zplatform.trade.adapter.quickpay.IQuickPayTrade;
import com.zlebank.zplatform.trade.adapter.quickpay.IRefundTrade;
import com.zlebank.zplatform.trade.adapter.quickpay.impl.ReaPayTradeThreadPool;
import com.zlebank.zplatform.trade.adapter.quickpay.impl.TestTradeThreadPool;
import com.zlebank.zplatform.trade.chanpay.quickpay.ChanPayTradeThreadPool;
import com.zlebank.zplatform.trade.cmbc.quickpay.CMBCTradeThreadPool;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.model.ChnlDetaModel;
import com.zlebank.zplatform.trade.pool.IThreadPool;
import com.zlebank.zplatform.trade.service.IChnlDetaService;
import com.zlebank.zplatform.trade.utils.SpringContext;


/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月6日 下午2:58:12
 * @since 
 */

public class TradeAdapterFactory {
   
   

    private static TradeAdapterFactory tradeAdapterFactory;
    @Autowired
    private IChnlDetaService chnlDetaService;
    private TradeAdapterFactory(){
        chnlDetaService = (IChnlDetaService) SpringContext.getContext().getBean("chnlDetaService");
    }
    
    public synchronized static TradeAdapterFactory getInstance(){
        if(tradeAdapterFactory==null){
            tradeAdapterFactory = new TradeAdapterFactory();
        }
        return tradeAdapterFactory;
    } 
    
    
    public IQuickPayTrade getQuickPayTrade(String chnlcode) throws TradeException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        ChnlDetaModel chnlDetaModel = chnlDetaService.getChannelByCode(chnlcode);
        IQuickPayTrade  quickPayTrade = null;
        if(chnlDetaModel!=null){
            if(StringUtil.isNotEmpty(chnlDetaModel.getImpl())){
                 quickPayTrade =(IQuickPayTrade) Class.forName(chnlDetaModel.getImpl()).newInstance();
            }
        }else{
            throw new TradeException("");
        }
       /* if(chnlcode.equals("98000001")){
            ZLQuickPayTrade quickPayTrade=new ZLQuickPayTrade();
            //return quickPayTrade;
            
        }else if (chnlcode.equals("96000001")) {
            ReaPayQuickTrade quickPayTrade = new ReaPayQuickTrade();
            return quickPayTrade;
        }else if (chnlcode.equals("95000001")) {
            TestQuickTrade quickPayTrade = new TestQuickTrade();
            return quickPayTrade;
        }*/
        return quickPayTrade;
    }
    
    public IThreadPool getThreadPool(String chnlcode) {
        IThreadPool threadPool = null;  
       if(chnlcode.equals("98000001")){
        }else if (chnlcode.equals("96000001")) {
            threadPool = ReaPayTradeThreadPool.getInstance();
        }else if (chnlcode.equals("95000001")) {
            threadPool = TestTradeThreadPool.getInstance();
        }else if(chnlcode.equals("93000002")){
            threadPool = CMBCTradeThreadPool.getInstance();
        }else if(chnlcode.equals("93000003")){
            threadPool = CMBCTradeThreadPool.getInstance();
        }else if(chnlcode.equals("92000001")){
        	//threadPool = BossPayTradeThreadPool.getInstance();
        }else if(chnlcode.equals("90000002")){
        	threadPool = ChanPayTradeThreadPool.getInstance();
        }
        return threadPool;
    }
    
    
    public IInsteadPayTrade getInsteadPayTrade(String chnlcode) throws TradeException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        ChnlDetaModel chnlDetaModel = chnlDetaService.getChannelByCode(chnlcode);
        IInsteadPayTrade  insteadPayTrade = null;
        if(chnlDetaModel!=null){
            if(StringUtil.isNotEmpty(chnlDetaModel.getImpl())){
            	insteadPayTrade =(IInsteadPayTrade) Class.forName(chnlDetaModel.getImpl()).newInstance();
            }
        }else{
            throw new TradeException("");
        }
       /* if(chnlcode.equals("98000001")){
            //ZLQuickPayTrade quickPayTrade=new ZLQuickPayTrade();
            //return quickPayTrade;
            
        }else if (chnlcode.equals("96000001")) {
            ReaPayQuickTrade quickPayTrade = new ReaPayQuickTrade();
            return quickPayTrade;
        }else if (chnlcode.equals("95000001")) {
            TestQuickTrade quickPayTrade = new TestQuickTrade();
            return quickPayTrade;
        }*/
        return insteadPayTrade;
    }
    
    public IRefundTrade getRefundTrade(String chnlcode) throws TradeException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        ChnlDetaModel chnlDetaModel = chnlDetaService.getChannelByCode(chnlcode);
        IRefundTrade  quickPayTrade = null;
        if(chnlDetaModel!=null){
            if(StringUtil.isNotEmpty(chnlDetaModel.getRefundImpl())){
                 quickPayTrade =(IRefundTrade) Class.forName(chnlDetaModel.getRefundImpl()).newInstance();
            }
        }else{
            throw new TradeException("");
        }
       /* if(chnlcode.equals("98000001")){
            ZLQuickPayTrade quickPayTrade=new ZLQuickPayTrade();
            //return quickPayTrade;
            
        }else if (chnlcode.equals("96000001")) {
            ReaPayQuickTrade quickPayTrade = new ReaPayQuickTrade();
            return quickPayTrade;
        }else if (chnlcode.equals("95000001")) {
            TestQuickTrade quickPayTrade = new TestQuickTrade();
            return quickPayTrade;
        }*/
        return quickPayTrade;
    }
    
    
    public static void main(String[] args) {
        try {
            Class.forName("com.zlebank.zplatform.trade.adapter.quickpay.impl.TestQuickTrade");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}