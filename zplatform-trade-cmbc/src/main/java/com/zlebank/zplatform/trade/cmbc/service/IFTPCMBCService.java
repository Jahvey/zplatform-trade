/* 
 * IFTPService.java  
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
import java.util.List;

import com.zlebank.zplatform.trade.cmbc.bean.enmus.FileTypeEnmu;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月3日 下午4:06:21
 * @since 
 */
public interface IFTPCMBCService {
    /**
     * 上传民生报盘文件
     * @param srcFile
     * @param fileName
     */
    public void uploadCMBCFile(File srcFile,String fileName,String path) ;
    /**
     * 下载民生回盘文件
     * @param filePath
     */
    public void downloadCMBCFile(String filePath,String targetPath);

    /**
     * 扫描民生回盘文件
     * @param path 扫描路径  
     * @return 返回文件名称集合
     */
    public List<String> scanningCMBCFile(String path);
    
    /**
     * 上传民生跨行代付文件
     * @param srcFile
     * @param fileName
     */
    public void uploadOuterCMBCFile(File srcFile,String fileName);
    
    /**
     * 上传民生本行代付文件
     * @param srcFile
     * @param fileName
     */
    public void uploadInnerCMBCFile(File srcFile,String fileName);
    
    /**
     * 扫描民生银行回盘文件
     * @param path 路径
     * @param date 时间
     * @param fileType 回盘文件类型
     * @return
     */
    public List<String> scanningCMBCRESFile(String path,String date,FileTypeEnmu fileType);
}
