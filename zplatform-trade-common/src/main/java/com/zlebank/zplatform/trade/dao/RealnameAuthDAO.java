/* 
 * RealnameAuthDAO.java  
 * 
 * version TODO
 *
 * 2015年11月24日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.dao;

import com.zlebank.zplatform.commons.dao.BaseDAO;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.model.PojoRealnameAuth;

/**
 * 实名认证DAO
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月24日 下午12:30:18
 * @since 
 */
public interface RealnameAuthDAO extends BaseDAO<PojoRealnameAuth>{

    /**
     * 保存实名认证数据
     * @param realnameAuth
     */
    public void saveRealNameAuth(PojoRealnameAuth realnameAuth) throws TradeException;
    
    /**
     * 更新实名认证状态
     * @param realnameAuth
     * @throws TradeException
     */
    public void updateRealNameStatus(PojoRealnameAuth realnameAuth) throws TradeException;
    /**
     * 根据卡号和持卡人姓名得到
     * @param cardNo
     * @param accName
     * @param phoneNo 
     * @param string 
     * @return 
     */
    public PojoRealnameAuth getByCardNoAndName(String cardNo, String accName, String certifId, String phoneNo);
    
    /**
     * 通过卡信息获取实名认证数据
     * @param realnameAuth
     * @return
     */
    public PojoRealnameAuth getByCardInfo(PojoRealnameAuth realnameAuth);
    
   
    

}
