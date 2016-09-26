/* 
 * ConfigInfoServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年8月17日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.trade.dao.ConfigInfoDAO;
import com.zlebank.zplatform.trade.model.ConfigInfoModel;
import com.zlebank.zplatform.trade.service.ConfigInfoService;
import com.zlebank.zplatform.trade.service.base.BaseServiceImpl;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年8月17日 下午2:03:11
 * @since 
 */
@Service("configInfoService")
public class ConfigInfoServiceImpl extends BaseServiceImpl<ConfigInfoModel, String> implements ConfigInfoService{

	@Autowired
	private ConfigInfoDAO configInfoDAO;
	/**
	 *
	 * @return
	 */
	@Override
	public Session getSession() {
		// TODO Auto-generated method stub
		return configInfoDAO.getSession();
	}

	/**
     * 根据参数名称得到配置信息
     * @param paraName
     * @return
     */
    public ConfigInfoModel getConfigByParaName(String paraName){
    	return configInfoDAO.getConfigByParaName(paraName);
    }
    /**
     * 根据参数名称得到配置信息列表
     * @param paraName
     * @return
     */
    public List<ConfigInfoModel> getConfigListByParaName(String paraName){
    	return configInfoDAO.getConfigListByParaName(paraName);
    }
    
    /**
     * 得到指定序列
     * @param sequences
     * @return
     */
    public long getSequence(String sequences){
    	return configInfoDAO.getSequence(sequences);
    }

}
