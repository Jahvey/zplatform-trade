/* 
 * ConfigInfoService.java  
 * 
 * version TODO
 *
 * 2016年8月17日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import java.util.List;

import com.zlebank.zplatform.trade.model.ConfigInfoModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年8月17日 下午2:01:51
 * @since 
 */
public interface ConfigInfoService extends IBaseService<ConfigInfoModel, String>{

	/**
     * 根据参数名称得到配置信息
     * @param paraName
     * @return
     */
    public ConfigInfoModel getConfigByParaName(String paraName) ;
    /**
     * 根据参数名称得到配置信息列表
     * @param paraName
     * @return
     */
    public List<ConfigInfoModel> getConfigListByParaName(String paraName) ;
    
    /**
     * 得到指定序列
     * @param sequences
     * @return
     */
    public long getSequence(String sequences);
}
