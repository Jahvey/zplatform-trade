package com.zlebank.zplatform.trade.cmbc.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zlebank.zplatform.trade.cmbc.exception.CMBCTradeException;
import com.zlebank.zplatform.trade.cmbc.net.socket.BaseClient;
import com.zlebank.zplatform.trade.utils.LogUtil;

public class CMBCWithholdingSocketShortClient extends BaseClient implements Client {
	private InetSocketAddress hostAddress;
	private int timeout;
	private ReceiveProcessor receiveProcessor;
//	private ThreadPoolExecutor executor;
	private static final String ENCODING = "UTF-8";
	private static final Log logger = LogFactory.getLog(CMBCWithholdingSocketShortClient.class);

	public CMBCWithholdingSocketShortClient(String host,int port,int timeout) {
		this.hostAddress = new InetSocketAddress(host, port);
		this.timeout = timeout;
//		executor = new ThreadPoolExecutor(1, 10, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
	}
	
	@Override
	public void sendMessage(byte[] data) throws  CMBCTradeException{
		Socket s = new Socket();
		try{
			try{
				s.connect(hostAddress,timeout);
			} catch (IOException e) {
				e.printStackTrace();
				logger.debug("对端链接失败");
			}
			try {
			    logger.debug("send message :"+data);
				OutputStream os = s.getOutputStream();
				os.write(data.toString().getBytes(ENCODING));
			} catch (IOException e) {
				e.printStackTrace();
				logger.debug("消息发送失败");
				//TODO 抛异常：消息发送失败
			}
			try{
				s.setSoTimeout(timeout);
				InputStream is = s.getInputStream();
				byte[] msgLength = new byte[6];
                is.read(msgLength);
                int length = Integer.valueOf(new String(msgLength,"UTF-8"));
                logger.info(length);
                //报文长度  服务码    报文体                      密钥识别码
                //定长6位，定长15位  根据实际报文，变长   定长32位
                int total_length = 15+ length +32;
                byte[] buffer = new byte[total_length];
                is.read(buffer);
                logger.info("socket recive msg:"+LogUtil.formatLogHex(buffer));
                receiveProcessor.onReceive(buffer);
			} catch (IOException e) {
				e.printStackTrace();
				//消息发送失败
			}
		}finally{
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void shutdown() {

	}
	
	public void setReceiveProcessor(ReceiveProcessor receiveProcessor){
		this.receiveProcessor = receiveProcessor;
	}
}
