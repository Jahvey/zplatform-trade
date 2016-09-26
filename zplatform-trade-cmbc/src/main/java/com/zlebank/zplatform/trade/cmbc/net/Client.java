package com.zlebank.zplatform.trade.cmbc.net;

import com.zlebank.zplatform.trade.cmbc.exception.CMBCTradeException;

public interface Client {
    /**
     * 发送
     * @param data 报文
     * @throws CMBCTradeException
     */
	public void sendMessage(byte[] data) throws CMBCTradeException;
	
	public void shutdown();
}
