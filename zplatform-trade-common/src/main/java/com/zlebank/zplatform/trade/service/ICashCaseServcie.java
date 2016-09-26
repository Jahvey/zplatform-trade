/* 
 * ICashCaseServcie.java  
 * 
 * version TODO
 *
 * 2015年9月10日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import java.util.List;

import com.zlebank.zplatform.trade.model.CashCaseModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月10日 下午1:33:36
 * @since 
 */
public interface ICashCaseServcie extends IBaseService<CashCaseModel, Long>{
    public List<CashCaseModel> getCashCaseByCashver(String cashver);
}
