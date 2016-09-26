/* 
 * InsteadPayLongSocketClient.java  
 * 
 * version TODO
 *
 * 2015年11月27日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.cmbc.service.socket.withholding;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zlebank.zplatform.trade.cmbc.exception.CMBCTradeException;
import com.zlebank.zplatform.trade.cmbc.net.Client;
import com.zlebank.zplatform.trade.cmbc.net.ReceiveProcessor;
import com.zlebank.zplatform.trade.cmbc.net.socket.BaseClient;
import com.zlebank.zplatform.trade.utils.LogUtil;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月27日 下午2:43:28
 * @since
 */
public class WithholdingLongSocketClient extends BaseClient implements Client {

    private static final Log log = LogFactory
            .getLog(WithholdingLongSocketClient.class);
    private static final String ENCODING = "UTF-8";
    private ReceiveProcessor receiveProcessor;
    private int timeout;
    private boolean running = false;
    private long lastSendTime;
    private Socket socket;
    private String serverIp;
    private int port;
    private static WithholdingLongSocketClient longClient;

    private WithholdingLongSocketClient(String host, int port, int timeout) {
        this.timeout = timeout;
        this.serverIp = host;
        this.port = port;
    }

    public static synchronized WithholdingLongSocketClient getInstance(String host,int port,int timeout) {
        if (longClient == null) {
            log.info("host:" + host);
            log.info("port:" + port);
            longClient = new WithholdingLongSocketClient(host, port, timeout);
            try {
                longClient.start();
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else{
            log.info("host:" + host);
            log.info("port:" + port);
            if(longClient.socket==null){
                try {
                    longClient.start();
                } catch (UnknownHostException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else {
                if(longClient.socket.isClosed()){
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
            }
            
        }
        return longClient;
    }

    public void start() throws UnknownHostException, IOException {
        if (running) {
            log.info("running:" + running);
            return;
        }
        socket = new Socket(serverIp, port);
        socket.setKeepAlive(true);// 开启保持活动状态的套接字
        socket.setSoTimeout(timeout);// 设置超时时间
        log.info("本地端口：" + socket.getLocalPort());
        lastSendTime = System.currentTimeMillis();
        running = true;
        new Thread(new KeepAliveWatchDog()).start();
        new Thread(new ReceiveWatchDog()).start();
    }

    /**
     *
     * @param data
     * @throws CMBCTradeException
     */
    @Override
    public void sendMessage(byte[] data) throws CMBCTradeException {
        try {
            log.info("socket send msg/byte:"
                    + LogUtil.formatLogHex(data));
            if(socket.isConnected()){
                OutputStream os = socket.getOutputStream();
                os.write(data);
            }else{
                log.info("the socket is died");
                shutdown();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
            log.info("消息发送失败");
            shutdown();
        }
    }

    /**
     *
     */
    @Override
    public void shutdown() {
        log.info("running:"+running);
        if (running){
            running = false;
            if(socket.isConnected()){
                try {
                    log.info("socket is closing");
                    socket.close();
                    log.info("socket is closed");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *
     * @param receiveProcessor
     */
    @Override
    public void setReceiveProcessor(ReceiveProcessor receiveProcessor) {
        this.receiveProcessor = receiveProcessor;
    }
    class KeepAliveWatchDog implements Runnable {
        public KeepAliveWatchDog(){
            Thread.currentThread().setName("Withholding KeepAliveWatchDog Thread");
        }
        long checkDelay = 0;
        long keepAliveDelay = 1000 * 25;
        public void run() {
            while (running) {
                if (System.currentTimeMillis() - lastSendTime > keepAliveDelay) {
                    try {
                        log.info("send hert messageas");
                        WithholdingLongSocketClient.this.sendMessage("00000000".getBytes(ENCODING));
                    } catch (CMBCTradeException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        WithholdingLongSocketClient.this.shutdown();
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    lastSendTime = System.currentTimeMillis();
                } else {
                    try {
                        Thread.sleep(checkDelay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        WithholdingLongSocketClient.this.shutdown();
                    }
                }
            }
            log.info("KeepAliveWatchDog running:"+running);
        }
    }
    class ReceiveWatchDog implements Runnable {
        public ReceiveWatchDog(){
            Thread.currentThread().setName("Withholding ReceiveWatchDog Thread");
        }
        public void run() {
            while (running) {
                try {
                    InputStream is = socket.getInputStream();
                    if (is.available() > 0) {
                        byte[] msgLength = new byte[8];
                        is.read(msgLength);
                        int length = Integer.valueOf(new String(msgLength,ENCODING));
                        log.info(length);
                        byte[] buffer = new byte[length];
                        is.read(buffer);
                        log.info("socket recive msg:"+LogUtil.formatLogHex(buffer));
                        receiveProcessor.onReceive(buffer);
                    } else {
                        Thread.sleep(10);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    WithholdingLongSocketClient.this.shutdown();
                    break;
                }
            }
            
            log.info("ReceiveWatchDog running:"+running);
        }
    }
}
