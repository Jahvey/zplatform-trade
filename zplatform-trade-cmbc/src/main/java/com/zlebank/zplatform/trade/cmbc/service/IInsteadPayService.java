/* 
 * IInsteadPayService.java  
 * 
 * version TODO
 *
 * 2015年11月3日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.cmbc.service;


import java.io.File;
import java.io.IOException;

import com.zlebank.zplatform.trade.cmbc.bean.InsteadPayBean;
import com.zlebank.zplatform.trade.cmbc.bean.enmus.FileTypeEnmu;
import com.zlebank.zplatform.trade.cmbc.exception.CMBCTradeException;
import com.zlebank.zplatform.trade.exception.TradeException;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月3日 上午11:27:31
 * @since 
 */
public interface IInsteadPayService {

    /**
     * 批量代付（跨行）
     * @param batchNo 批次号
     */
    public void batchOuterPay(Long tid)  throws CMBCTradeException, IOException, TradeException;
    /**
     * 批量代付（跨行）
     * @param batchNo 批次号
     */
    public void batchInnerPay(Long tid)  throws CMBCTradeException,IOException, TradeException;
    /**
     * 单笔代付
     * @param insteadPayBean 单笔代付类
     */
    public void singlePay(InsteadPayBean insteadPayBean);
    /**
     * 分析回盘文件
     * @param file 回盘文件
     * @param fileName 回盘文件名称
     * @param fileTypeEnmu 回盘文件类型
     * @throws IOException
     */
    public void analyzeCMBCFile(File file,String fileName,FileTypeEnmu fileTypeEnmu) throws IOException;

}
