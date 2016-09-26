package com.zlebank.zplatform.trade.cmbc.net.socket;

import com.zlebank.zplatform.trade.cmbc.net.Client;
import com.zlebank.zplatform.trade.cmbc.net.ReceiveProcessor;

public abstract class BaseClient implements Client {
	public abstract void setReceiveProcessor(ReceiveProcessor receiveProcessor);
}
