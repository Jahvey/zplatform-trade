/* 
 * IQuickpayCustService.java  
 * 
 * version TODO
 *
 * 2015年9月21日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import java.util.List;

import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.wap.WapWithdrawAccBean;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.model.QuickpayCustModel;

/**
 * 快捷支付-绑卡信息服务层
 *
 * @author guojia
 * @version
 * @date 2015年9月21日 上午11:23:16
 * @since 
 */
public interface IQuickpayCustService extends IBaseService<QuickpayCustModel, Long>{
    /**
     * 保存绑卡信息
     * @param trade
     * @return 绑卡标示
     */
    public Long saveQuickpayCust(TradeBean trade);
    /**
     * 获取当前会员绑卡信息列表
     * @param memberId 会员标示
     * @param cardType
     * @return
     */
    public List<QuickpayCustModel> querBankCardByMemberId(String memberId,String cardType);
    
    
    public QuickpayCustModel getCardByBindId(String bindId);
    /**
     * 更新绑卡状态
     * @param memberId
     * @param cardNo
     */
    public void updateCardStatus(String memberId,String cardNo);
    /**
     * 更新绑卡状态
     * @param bindCardId
     */
    public void updateCardStatus(Long bindCardId);
    
    /**
     * 保存测试渠道绑卡信息
     * @param trade
     * @return
     */
    public Long saveTestQuickpayCust(TradeBean trade);
    /**
     * 通过绑卡标示，获取提现卡信息
     * @param Id
     * @return
     * @throws TradeException
     */
    public WapWithdrawAccBean getWithdrawAccBeanById(String Id) throws TradeException;
    
    /**
     * 通过会员号和卡类型，获取绑卡信息
     * @param memberId
     * @param cardType
     * @return
     */
    public List<QuickpayCustModel> querBankCardByMemberId_Reapay(String memberId,String cardType);
    /**
     * 保存绑卡信息
     * @param trade
     * @return 绑卡标示
     */
    public Long saveBindCard(TradeBean trade);
    /**
     * 更新绑卡状态
     * @param memberId
     * @param cardNo
     */
    public void updateBindCardId(Long id,String bindCardId);
    /**
     * 解绑卡，并删除卡信息
     * @param id
     */
    public void deleteCard(Long id);
    /**
     * 查询绑卡列表
     * @param cardNo
     * @param accName
     * @param phone
     * @param cerId
     * @param memberId
     * @return
     */
    public List<QuickpayCustModel> getCardList(String cardNo ,String accName, String phone, String cerId, String memberId);
    
    public void deleteUnBindCard(Long id);
    
    
    /***
     * 查询绑卡信息，增加设备ID
     * @param cardNo
     * @param accName
     * @param phone
     * @param cerId
     * @param memberId
     * @param devId
     * @return
     */
    public List<QuickpayCustModel>getCardList(String cardNo ,String accName, String phone, String cerId, String memberId,String devId);
}
