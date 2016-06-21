/* 
 * WebGateServiceImpl.java  
 * 
 * version TODO
 *
 * 2015年11月17日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.trade.dao.ConfigInfoDAO;
import com.zlebank.zplatform.trade.dao.MerchWhiteListDAO;
import com.zlebank.zplatform.trade.dao.RealnameAuthDAO;
import com.zlebank.zplatform.trade.dao.RealnameAuthOrderDAO;
import com.zlebank.zplatform.trade.insteadPay.message.MerWhiteList_Request;
import com.zlebank.zplatform.trade.model.PojoMerchWhiteList;
import com.zlebank.zplatform.trade.service.MerchWhiteListService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月17日 下午2:04:49
 * @since 
 */
@Service("merchWhiteListService")
public class MerchWhiteListServiceImpl implements MerchWhiteListService{

    private static final Log log = LogFactory.getLog(MerchWhiteListServiceImpl.class);

    @Autowired
    private ConfigInfoDAO configInfoDAO;

    @Autowired
    private RealnameAuthDAO realnameAuthDAO;

    @Autowired
    private RealnameAuthOrderDAO realnameAuthOrderDAO;

    @Autowired
    private MerchWhiteListDAO merchWhiteListDAO; 

    /**
     * 添加商户白名单
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String addMerchWhiteListService(MerWhiteList_Request request) {
        if (log.isDebugEnabled()) {
            log.debug("添加商户白名单Service处理开始");
            log.debug(JSONObject.fromObject(request));
        }
        // 判断是否需要实名认证
//        ConfigInfoModel config = configInfoDAO.getConfigByParaName("IS_WHITE_LIST_REALNAME_AUTH");
        // 是否进行实名认证
//        boolean isRealNameAuth = "1".equals(config.getPara());
//        if (isRealNameAuth) {
//            PojoRealnameAuthOrder realNameAuth = realnameAuthOrderDAO.isRealNameAuth(request.getMerId(), request.getAccNo(), request.getAccName());
//            if (realNameAuth == null) {
//                return "该卡没有经过实名认证";
//            }
//        }
        PojoMerchWhiteList oldPojo = merchWhiteListDAO.getWhiteListByCardNoAndName(request.getMerId(), request.getAccNo(), request.getAccName());
        if (oldPojo != null) {
            return "已经存在相应的记录";
        }
        PojoMerchWhiteList whiteList = new PojoMerchWhiteList();
        whiteList.setMerchId(request.getMerId());
        whiteList.setCardNo(request.getAccNo());
        whiteList.setAccName(request.getAccName());
        whiteList.setIntime(new Date());
        whiteList.setUptime(new Date());
        whiteList.setInuser(0L);
        whiteList.setUpuser(0L);
//        if (isRealNameAuth) {
//            whiteList.setStatus("1");
//        } else {
            whiteList.setStatus("0");
//        }
        merchWhiteListDAO.merge(whiteList);
        return null;
    }

    /**
     * 检查是否在白名单范围
     * @param merId
     * @param accName
     * @param accNo
     * @return 
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String checkMerchWhiteList(String merId, String accName, String accNo) {
        String rtn = null;
        PojoMerchWhiteList oldPojo = merchWhiteListDAO.getWhiteListByCardNoAndName(merId, accNo,accName);
        if (oldPojo == null) {
            rtn = String.format("(%s,%s)", accName,accNo);
        }
        return rtn;
    }
}
