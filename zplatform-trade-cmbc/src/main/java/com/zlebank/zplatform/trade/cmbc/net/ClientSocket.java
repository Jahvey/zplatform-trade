/* 
 * ClientSocket.java  
 * 
 * version TODO
 *
 * 2015年11月2日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.cmbc.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSON;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.zlebank.zplatform.trade.cmbc.bean.RealTimePayBean;
import com.zlebank.zplatform.trade.cmbc.bean.RealTimePayResultBean;
  
/** 
 *  C/S架构的客户端对象，持有该对象，可以随时向服务端发送消息。 
 */  
public class ClientSocket {  
  
    /** 
     * 处理服务端发回的对象，可实现该接口。 
     */  
    public static interface ObjectAction{  
        void doAction(Object obj,ClientSocket client);  
    }  
    public static final class DefaultObjectAction implements ObjectAction{  
        public void doAction(Object obj,ClientSocket client) {  
            System.out.println("处理：\t"+obj.toString());  
        }  
    }  
    public static void main(String[] args) throws UnknownHostException, IOException {  
        RealTimePayBean bean = new RealTimePayBean();
        String serverIp = "127.0.0.1";
        int port = 9008;  
        ClientSocket client = ClientSocket.getInstence(serverIp, port);
        client.start();  
        client.sendObject(bean.toXML());
    }  
      
    private String serverIp;  
    private int port;  
    private Socket socket;  
    private boolean running=false;  
    private long lastSendTime;  
    private static ClientSocket clientSocket;
    @SuppressWarnings("rawtypes")
	private ConcurrentHashMap<Class, ObjectAction> actionMapping = new ConcurrentHashMap<Class,ObjectAction>();  
      
    private  ClientSocket(String serverIp, int port) {  
        this.serverIp=serverIp;this.port=port;  
    }  
      
    public static synchronized ClientSocket getInstence(String serverIp, int port){
        if(clientSocket==null){
            System.out.println("clientSocket:"+clientSocket);
            clientSocket = new ClientSocket( serverIp,  port);
            try {
                clientSocket.start();
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }  
        }
        return clientSocket;
        
    }
    public void start() throws UnknownHostException, IOException {  
        if(running){
            System.out.println("running:"+running);
            return;  
        }
        socket = new Socket(serverIp,port);  
        socket.setKeepAlive(true);//开启保持活动状态的套接字
        socket.setSoTimeout(90000);//设置超时时间
        System.out.println("本地端口："+socket.getLocalPort());  
        lastSendTime=System.currentTimeMillis();  
        running=true;  
        new Thread(new KeepAliveWatchDog()).start();  
        new Thread(new ReceiveWatchDog()).start();  
    }  
      
    public void stop(){  
        if(running)running=false;  
    }  
      
    /** 
     * 添加接收对象的处理对象。 
     * @param cls 待处理的对象，其所属的类。 
     * @param action 处理过程对象。 
     */  
    public void addActionMap(Class<Object> cls,ObjectAction action){  
        actionMapping.put(cls, action);
    }  
  
    public void sendObject(Object obj) throws IOException {  
        OutputStream oos = socket.getOutputStream();  
        //oos.writeObject(obj);  
        oos.write(obj.toString().getBytes("utf-8"));
        System.out.println("发送："+obj);  
        oos.flush();
    }  
      
    class KeepAliveWatchDog implements Runnable{  
        long checkDelay = 10;  
        long keepAliveDelay = 1000*5;  
        public void run() {  
            while(running){  
                if(System.currentTimeMillis()-lastSendTime>keepAliveDelay){  
                    try {  
                        ClientSocket.this.sendObject("000000");  
                    } catch (IOException e) {  
                        e.printStackTrace();  
                        ClientSocket.this.stop();  
                    }  
                    lastSendTime = System.currentTimeMillis();  
                }else{  
                    try {  
                        Thread.sleep(checkDelay);  
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                        ClientSocket.this.stop();  
                    }  
                }  
            }  
        }  
    }  
      
    class ReceiveWatchDog implements Runnable{  
        public void run() {  
            while(running){  
                try {  
                    InputStream in = socket.getInputStream();  
                    
                    if(in.available()>0){  
                        //读取报文长度
                        byte[] head = new byte[6];
                        //int r = in.read(head);
                        System.out.println("接收报文头：\t"+new String(head,"UTF-8"));  
                        byte[] serviceCode = new byte[15];
                        in.read(serviceCode);
                        System.out.println("接收报文头：\t"+new String(serviceCode,"UTF-8").trim());  
                        DecimalFormat df = new DecimalFormat("000000");
                        int length =df.parse(new String(head,"UTF-8")).intValue();
                        System.out.println("length:"+length);
                        byte[] b = new byte[length];
                        in.read(b);
                        byte[] sign = new byte[32];
                        in.read(sign);
                        
                        //ObjectInputStream ois = new ObjectInputStream(in);  
                       // Object obj = ois.readObject();  
                       System.out.println("接收报文："+new String(b,"UTF-8").trim());  
                       System.out.println("签名字符串："+new String(sign,"UTF-8"));
                       
                       String recviveMsg =  new String(b,"UTF-8").trim();
                       XStream xstream = new XStream(new DomDriver(null,new XmlFriendlyNameCoder("_-", "_")));  
                       xstream.processAnnotations(RealTimePayResultBean.class);
                       xstream.autodetectAnnotations(true);
                       RealTimePayResultBean resultBean =  (RealTimePayResultBean) xstream.fromXML(recviveMsg);
                       System.out.println(JSON.toJSONString(resultBean));
                        /*ObjectAction oa = actionMapping.get(obj.getClass());  
                        oa = oa==null?new DefaultObjectAction():oa;  
                        oa.doAction(obj, ClientSocket.this);  */
                    }else{  
                        Thread.sleep(10);  
                    }  
                } catch (Exception e) {  
                    e.printStackTrace();  
                    ClientSocket.this.stop();  
                }   
            }  
        }  
    }  
      
}  