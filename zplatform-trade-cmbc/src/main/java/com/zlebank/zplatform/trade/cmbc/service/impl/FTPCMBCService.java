/* 
 * FTPService.java  
 * 
 * version TODO
 *
 * 2015年11月3日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.cmbc.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.trade.cmbc.bean.enmus.FileTypeEnmu;
import com.zlebank.zplatform.trade.cmbc.service.IFTPCMBCService;
import com.zlebank.zplatform.trade.utils.ConsUtil;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月3日 下午3:35:04
 * @since 
 */
@Service("ftpcmbcService")
public class FTPCMBCService implements IFTPCMBCService {
    private static final Log log = LogFactory.getLog(FTPCMBCService.class);
    private final static String FTPIP = ConsUtil.getInstance().cons.getCmbc_insteadpay_ftp_ip();
    private final static String USER = ConsUtil.getInstance().cons.getCmbc_insteadpay_ftp_user();
    private final static String PWD = ConsUtil.getInstance().cons.getCmbc_insteadpay_ftp_pwd();
    private final static int port = ConsUtil.getInstance().cons.getCmbc_insteadpay_ftp_port();
    private final static String ENCODE = "GBK";
    private FTPClient ftpClient;
    
    public FTPCMBCService(){
        ftpClient=new FTPClient();
    }
    
    public void uploadOuterCMBCFile(File srcFile,String fileName){
        uploadCMBCFile(srcFile,fileName,"reqouter");
    }
    
    public void uploadInnerCMBCFile(File srcFile,String fileName){
        uploadCMBCFile(srcFile,fileName,"req");
    }
    
    /**
     * FTP上传单个文件测试
     */
    public void uploadCMBCFile(File srcFile,String fileName,String path) {
        FileInputStream fis = null;
        try {
            //ftpClient.connect(FTPIP);
            ftpClient.connect(FTPIP, port);
            
            log.info("login result:"+ftpClient.login(USER, PWD));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String directory = sdf.format(new Date());
            fis = new FileInputStream(srcFile);
            ftpClient.makeDirectory(path);
            ftpClient.changeWorkingDirectory(path);
            ftpClient.makeDirectory(directory);
            // 设置上传目录
            ftpClient.changeWorkingDirectory(directory);
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding(ENCODE);
            //设置为被动模式，不然非21端口会无法上传文件
            ftpClient.enterLocalPassiveMode();
            // 设置文件类型（二进制）
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            boolean upload_flag = ftpClient.storeUniqueFile(fileName, fis);
            if(!upload_flag){
            	throw new RuntimeException("FTP上传文件失败!");
            }
            //System.out.println(ftpClient.storeFile(fileName,fis));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("FTP客户端出错！", e);
        } finally {
            IOUtils.closeQuietly(fis);
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭FTP连接发生异常！", e);
            }
        }
    }
    
    public boolean createDirectory(String fileName) {
        try {
            return ftpClient.makeDirectory(fileName);//创建文件夹，如果文件夹已经存在返回false，创建成功返回true
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 扫描民生回盘文件
     * @param path 扫描路径  
     * @return 返回文件名称集合
     */
    public List<String> scanningCMBCFile(String path){
        FTPClient ftpClient = new FTPClient();
        FileOutputStream fos = null;
        try {
            ftpClient.connect(FTPIP, port);
            ftpClient.login(USER,PWD);
            ftpClient.setBufferSize(1024);
            // 设置文件类型（二进制）
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.changeWorkingDirectory(path);
            //设置为被动模式，不然非21端口会无法上传文件
            ftpClient.enterLocalPassiveMode();
            FTPFile[] ftpFiles =  ftpClient.listFiles();
            List<String> fileNameList = new ArrayList<String>();
            for(int i=0;i<ftpFiles.length;i++){
                log.info("file name:"+path+"/"+ftpFiles[i].getName());
                if(ftpFiles[i].getName().indexOf("res")>=0){
                    fileNameList.add(path+"/"+ftpFiles[i].getName());
                }
                
            }
            return fileNameList;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("FTP客户端出错！", e);
        } finally {
            IOUtils.closeQuietly(fos);
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭FTP连接发生异常！", e);
            }
        }
       
    }
    
   /**
    * 扫描民生银行回盘文件
    * @param path
    * @param date
    * @param fileType
    * @return
    */
    public List<String> scanningCMBCRESFile(String path,String date,FileTypeEnmu fileType){
        FTPClient ftpClient = new FTPClient();
        FileOutputStream fos = null;
        try {
            ftpClient.connect(FTPIP, port);
            ftpClient.login(USER,PWD);
            ftpClient.setBufferSize(1024);
            // 设置文件类型（二进制）
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.changeWorkingDirectory(path);
            //设置为被动模式，不然非21端口会无法上传文件
            ftpClient.enterLocalPassiveMode();
            FTPFile[] ftpFiles =  ftpClient.listFiles();
            List<String> fileNameList = new ArrayList<String>();
            for(int i=0;i<ftpFiles.length;i++){
                log.info("file name:"+path+"/"+ftpFiles[i].getName());
                switch (fileType) {
                    case RES :
                        if(ftpFiles[i].getName().indexOf("res_"+date)>=0){
                            fileNameList.add(path+"/"+ftpFiles[i].getName());
                        }
                        break;
                    case RESOUTER :
                        if(ftpFiles[i].getName().indexOf("res_outer_"+date)>=0){
                            fileNameList.add(path+"/"+ftpFiles[i].getName());
                        }
                        break;
                    case REEXCHANGE:
                        if(ftpFiles[i].getName().indexOf("reexchange_")>=0){
                            fileNameList.add(path+"/"+ftpFiles[i].getName());
                        }
                        break;
					case UNKNOW:
						break;
					default:
						break;
                }
            }
            return fileNameList;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("FTP客户端出错！", e);
        } finally {
            IOUtils.closeQuietly(fos);
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭FTP连接发生异常！", e);
            }
        }
       
    }
    

    /**
     * FTP下载单个文件测试
     */
    public void downloadCMBCFile(String filePath,String targetPath) {
        FTPClient ftpClient = new FTPClient();
        FileOutputStream fos = null;
        try {
            log.info("filePath:"+filePath);
            log.info("targetPath:"+targetPath);
            ftpClient.connect(FTPIP, port);
            ftpClient.login(USER, PWD);
            fos = new FileOutputStream(targetPath);
            ftpClient.setBufferSize(1024);
            //设置为被动模式，不然非21端口会无法上传文件
            ftpClient.enterLocalPassiveMode();
            // 设置文件类型（二进制）
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.retrieveFile(filePath, fos);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("FTP客户端出错！", e);
        } finally {
            IOUtils.closeQuietly(fos);
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭FTP连接发生异常！", e);
            }
        }
    }
    
    public static void main(String[] args) {
       
            /*FTPClient ftpClient=new FTPClient();
            ftpClient.connect("192.168.13.106", 21);
            boolean loginFlag =ftpClient.login("webftp", "webftp");
            System.out.println(loginFlag);*/
        	
        	new FTPCMBCService().uploadCMBCFile(new File("D:\\cmbc\\down.txt"), "1234.txt","");
        
    }
}
