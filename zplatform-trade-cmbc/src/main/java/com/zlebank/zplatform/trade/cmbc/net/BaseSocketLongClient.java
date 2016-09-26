package com.zlebank.zplatform.trade.cmbc.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zlebank.zplatform.trade.cmbc.exception.CMBCTradeException;
import com.zlebank.zplatform.trade.cmbc.net.socket.BaseClient;
import com.zlebank.zplatform.trade.utils.LogUtil;

public class BaseSocketLongClient extends BaseClient implements Client {
	private int timeout;
	private ReceiveProcessor receiveProcessor;
	private static final Log log = LogFactory.getLog(BaseSocketLongClient.class);
	private static final String ENCODING = "UTF-8";
	private boolean running=false;  
    private long lastSendTime;  
    private Socket socket;  
    private String serverIp;  
    private int port;  
    private static BaseSocketLongClient longClient;
	public BaseSocketLongClient(String host,int port,int timeout) {
		this.timeout = timeout;
		this.serverIp = host;
		this.port = port;
	}
	
	public static synchronized BaseSocketLongClient getInstance(String host,int port,int timeout){
	    if(longClient==null){
	        log.info("host:"+host);
	        log.info("port:"+port);
	        longClient = new  BaseSocketLongClient( host, port, timeout) ;
	        try {
                longClient.start();
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
	    }
	    return longClient;
	}
	
	@Override
	public void sendMessage(byte[] data) throws  CMBCTradeException{
		try{
			try {
			    log.info("send message:"+data.toString());
				OutputStream os = socket.getOutputStream();
				os.write(data.toString().getBytes(ENCODING));
			} catch (IOException e) {
				e.printStackTrace();
				log.info("消息发送失败");
			}
			/*try{
			    InputStream is = socket.getInputStream();  
			    byte[] msgLength = new byte[6];
                is.read(msgLength);
                int length = Integer.valueOf(new String(msgLength,"UTF-8"));
                log.info(length);
                //报文长度  服务码    报文体                      密钥识别码
                //定长6位，定长15位  根据实际报文，变长   定长32位
                int total_length = 15+ length +32;
                byte[] buffer = new byte[total_length];
                is.read(buffer);
                log.info("socket recive msg:"+LogUtil.formatLogHex(buffer));
                receiveProcessor.onReceive(buffer);
			} catch (IOException e) {
				e.printStackTrace();
				//消息发送失败
			}*/
		}catch (Exception e) {
			
		}
	}
	public void start() throws UnknownHostException, IOException {  
        if(running){
            System.out.println("running:"+running);
            return;  
        }
        socket = new Socket(serverIp,port);  
        socket.setKeepAlive(true);//开启保持活动状态的套接字
        socket.setSoTimeout(timeout);//设置超时时间
        log.info("本地端口："+socket.getLocalPort());  
        lastSendTime=System.currentTimeMillis();  
        running=true;  
        new Thread(new KeepAliveWatchDog()).start();  
        new Thread(new ReceiveWatchDog()).start();  
    }  
	@Override
	public void shutdown() {
	    if(running)running=false;  
	}
	public void stop(){  
        if(running)running=false;  
    }  
	public void setReceiveProcessor(ReceiveProcessor receiveProcessor){
		this.receiveProcessor = receiveProcessor;
	}
	
	class KeepAliveWatchDog implements Runnable{  
        long checkDelay = 10;  
        long keepAliveDelay = 1000*30;  
        public void run() {  
            while(running){  
                if(System.currentTimeMillis()-lastSendTime>keepAliveDelay){  
                    try {  
                        log.info("send hert messageas");
                        BaseSocketLongClient.this.sendMessage("000000".getBytes(ENCODING));
                    } catch (CMBCTradeException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        BaseSocketLongClient.this.stop();  
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }  
                    lastSendTime = System.currentTimeMillis();  
                }else{  
                    try {  
                        Thread.sleep(checkDelay);  
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                        BaseSocketLongClient.this.stop();  
                    }  
                }  
            }  
        }  
    }  
	class ReceiveWatchDog implements Runnable{  
        public void run() {  
            while(running){  
                try {  
                    InputStream is = socket.getInputStream();  
                    
                    if(is.available()>0){  
                        byte[] msgLength = new byte[8];
                        is.read(msgLength);
                        int length = Integer.valueOf(new String(msgLength,"UTF-8"));
                        log.info(length);
                        //报文长度  服务码    报文体                      密钥识别码
                        //定长6位，定长15位  根据实际报文，变长   定长32位
                        int total_length = 15+ length +32;
                        byte[] buffer = new byte[total_length];
                        is.read(buffer);
                        log.info("socket recive msg:"+LogUtil.formatLogHex(buffer));
                        receiveProcessor.onReceive(buffer);
                    }else{  
                        Thread.sleep(10);  
                    }  
                } catch (Exception e) {  
                    e.printStackTrace();  
                    BaseSocketLongClient.this.stop();  
                }   
            }  
        }  
    }  
}
