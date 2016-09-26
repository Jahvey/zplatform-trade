/* 
 * TransferData.java  
 * 
 * version TODO
 *
 * 2016年3月3日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.batch.spliter.impl;

import com.zlebank.zplatform.trade.model.PojoTranData;

/**
 * Class Description
 *
 * @author Luxiaoshuai
 * @version
 * @date 2016年3月3日 下午5:41:43
 * @since 
 */
public class TransferData extends PojoTranData{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6938007028762712840L;
    private String channelCode;

    public String getChannelCode() {
        return channelCode;
    }
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }
}
