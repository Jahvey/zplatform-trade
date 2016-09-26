/* 
 * TransferResult.java  
 * 
 * version TODO
 *
 * 2016年3月7日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.batch.spliter;

import com.zlebank.zplatform.trade.model.PojoBankTransferBatch;
import com.zlebank.zplatform.trade.model.PojoBankTransferData;

/**
 * 返回结果
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年3月7日 下午6:02:07
 * @since 
 */
public class TransferDataResult {
    /**转账批次*/
    private PojoBankTransferBatch bankTranBatch;
    /**转账明细*/
    private PojoBankTransferData bankTranDeta;
    

    public PojoBankTransferBatch getBankTranBatch() {
        return bankTranBatch;
    }
    public void setBankTranBatch(PojoBankTransferBatch bankTranBatch) {
        this.bankTranBatch = bankTranBatch;
    }
    public PojoBankTransferData getBankTranDeta() {
        return bankTranDeta;
    }
    public void setBankTranDeta(PojoBankTransferData bankTranDeta) {
        this.bankTranDeta = bankTranDeta;
    }

}
