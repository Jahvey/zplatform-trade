/* 
 * IRouteConfigService.java  
 * 
 * version TODO
 *
 * 2015年9月2日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import java.util.Map;

import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.model.RouteConfigModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月2日 下午12:10:13
 * @since 
 */
public interface IRouteConfigService extends IBaseService<RouteConfigModel, Long>{
    public ResultBean getTransRout(String transTime,String transAmt,String memberId,String busiCode,String cardNo,String cashCode);
    public Map<String, Object> getCardInfo(String cardNo);
    public ResultBean getWapTransRout(String transTime,String transAmt,String memberId,String busiCode,String cardNo);
    /**
     * 通过卡号获取人行的联行号
     * @param cardNo
     * @return
     */
    public Map<String, Object> getCardPBCCode(String cardNo);
    
    /**
     * 获取银行通用名称，
     * @param bankCode 总行的联行号
     * @return
     */
    public String getBankName(String bankCode);
}
