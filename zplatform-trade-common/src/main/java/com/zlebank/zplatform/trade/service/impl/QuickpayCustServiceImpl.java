/* 
 * QuickpayCustServiceImpl.java  
 * 
 * version TODO
 *
 * 2015年9月21日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.member.bean.MerchMK;
import com.zlebank.zplatform.member.service.MerchMKService;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.wap.WapWithdrawAccBean;
import com.zlebank.zplatform.trade.dao.IQuickpayCustDAO;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.model.QuickpayCustModel;
import com.zlebank.zplatform.trade.service.IQuickpayCustService;
import com.zlebank.zplatform.trade.service.IRouteConfigService;
import com.zlebank.zplatform.trade.service.base.BaseServiceImpl;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月21日 上午11:23:52
 * @since 
 */
@Service("quickpayCustService")
public class QuickpayCustServiceImpl extends BaseServiceImpl<QuickpayCustModel, Long> implements IQuickpayCustService{

    @Autowired
    private IQuickpayCustDAO quickpayCustDAO;
    @Autowired 
    private IRouteConfigService routeConfigService;
    @Autowired
    private MerchMKService merchMKService;
    /**
     *
     * @return
     */
    @Override
    public Session getSession() {
        // TODO Auto-generated method stub
        return quickpayCustDAO.getSession();
    }
    @Transactional
    public Long saveQuickpayCust(TradeBean trade){
        if(StringUtil.isEmpty(trade.getMerUserId())){
            return null;
        }
        List<QuickpayCustModel> custList =  (List<QuickpayCustModel>) super.queryByHQL("from QuickpayCustModel where relatememberno=? and cardno = ? and (status = ? or status = ?) ", new Object[]{trade.getMerUserId(),trade.getCardNo(),"00","01"});
        if(custList.size()>0){
            return custList.get(0).getId();
        }else{//没有保存卡信息
            QuickpayCustModel quickpayCust = new QuickpayCustModel(trade);
            quickpayCust.setBindcardid(trade.getBindCardId());
            Map<String, Object> cardMap = routeConfigService.getCardInfo(trade.getCardNo());
            quickpayCust.setBankcode(cardMap.get("BANKCODE").toString());
            quickpayCust.setCardtype(cardMap.get("TYPE").toString());
            //quickpayCust.setInstitution(trade.getPayinstiId());
            quickpayCust.setBankname(cardMap.get("BANKNAME")+"");
            //super.save(quickpayCust);
            quickpayCust  = super.getUniqueByHQL("from QuickpayCustModel where relatememberno=? and cardno = ? and status=?",  new Object[]{trade.getMerUserId(),trade.getCardNo(),"01"});
            return 0L;
        }
    }
    @Transactional
    public Long saveTestQuickpayCust(TradeBean trade){
        //String memberId = trade.getMerUserId();
        //String cardNo = trade.getCardNo();
        if(StringUtil.isEmpty(trade.getMerUserId())){
            return null;
        }
        if(StringUtil.isEmpty(trade.getBindCardId())){
            return null;
        }
        List<QuickpayCustModel> custList =  (List<QuickpayCustModel>) super.queryByHQL("from QuickpayCustModel where relatememberno=? and cardno = ?", new Object[]{trade.getMerUserId(),trade.getCardNo()});
        if(custList.size()>0){
            
        }else{//没有保存卡信息
            QuickpayCustModel quickpayCust = new QuickpayCustModel(trade);
            quickpayCust.setBindcardid(trade.getBindCardId());
            Map<String, Object> cardMap = routeConfigService.getCardInfo(trade.getCardNo());
            quickpayCust.setBankcode(cardMap.get("BANKCODE").toString());
            quickpayCust.setCardtype(cardMap.get("TYPE").toString());
            quickpayCust.setInstitution(trade.getPayinstiId());
            quickpayCust.setBankname(cardMap.get("BANKNAME")+"");
            return super.saveEntity(quickpayCust);
        }
        return null;
    }
    
    
    public List<QuickpayCustModel> querBankCardByMemberId(String memberId,String cardType){
        if(StringUtil.isEmpty(cardType)){
            return (List<QuickpayCustModel>) super.queryByHQL(" from QuickpayCustModel where relatememberno=? and status = ? ", new Object[]{memberId,"00"});
        }else{
            return (List<QuickpayCustModel>) super.queryByHQL(" from QuickpayCustModel where relatememberno=? and status = ? and cardtype=?", new Object[]{memberId,"00",cardType});
        }
    }
    
    public List<QuickpayCustModel> querBankCardByMemberId_Reapay(String memberId,String cardType){
        if(StringUtil.isEmpty(cardType)){
            return (List<QuickpayCustModel>) super.queryByHQL(" from QuickpayCustModel where relatememberno=? and (institution = ? or institution=?) ", new Object[]{memberId,"96000001","95000001"});
        }else{
            return (List<QuickpayCustModel>) super.queryByHQL(" from QuickpayCustModel where relatememberno=? and  (institution = ? or institution=?) and cardtype=?", new Object[]{memberId,"96000001","95000001",cardType});
        }
    }
    
    
    private boolean isBind(String bindCardId){
        List<?> bindCardList = super.queryByHQL(" from QuickpayCustModel where bindcardid=? and status = ?", new Object[]{bindCardId,"00"});
        if(bindCardList.size()>0){
            return true;
        }
        return false;
    }

    
    /**
     * 解密卡信息
     * @param memberId
     * @param encryptData
     * @return
     */
    private ResultBean decryptCustomerInfo(String memberId,String encryptData) {
        ResultBean resultBean = null;
        try {
            
            
            MerchMK merchMk = merchMKService.get(memberId);
            if(merchMk==null){
                resultBean = new ResultBean("RC02","商户不存在");
            }
            String publicKey = "";// 获取公钥方法暂无，商户信息中提供
            if("01".equals(merchMk.getSafeType())){
                publicKey = merchMk.getMemberPubKey();
            }
           /* byte[] decodedData = RSAUtils.decryptByPublicKey(Base64Utils.decode(encryptData), publicKey);
            resultBean = new ResultBean(new String(decodedData));*/
            return resultBean;
        } catch (Exception e) {
            e.printStackTrace();
            resultBean = new ResultBean("RC03", "验签失败");
        }
        return resultBean;
    }

    /**
     *
     * @param bindId
     * @return
     */
    @Override
    @Transactional(readOnly=true)
    public QuickpayCustModel getCardByBindId(String bindId) {
        List<QuickpayCustModel> bindCardList = (List<QuickpayCustModel>) super.queryByHQL(" from QuickpayCustModel where id=? and status <> ?", new Object[]{Long.valueOf(bindId),"02"});
        if(bindCardList.size()>0){
            return bindCardList.get(0);
        }
        return null;
    }
    @Transactional(readOnly=true)
    public void updateCardStatus(String memberId,String cardNo){
        List<QuickpayCustModel> bindCardList = (List<QuickpayCustModel>) super.queryByHQL(" from QuickpayCustModel where relatememberno=? and cardno = ? and status = ?", new Object[]{memberId,cardNo,"01"});
        if(bindCardList.size()>0){
            QuickpayCustModel custCard = bindCardList.get(0);
            custCard.setStatus("00");
            super.update(custCard);
        }
    }
    
    @Transactional
    public void updateCardStatus(Long bindCardId){
        QuickpayCustModel custCard = super.get(bindCardId);
        if(custCard!=null){
            if("01".equals(custCard.getStatus())){
                custCard.setStatus("00");
                super.update(custCard);
            }
        }
    }
    
    public WapWithdrawAccBean getWithdrawAccBeanById(String Id) throws TradeException{
        QuickpayCustModel custCard = super.get(Long.valueOf(Id));
        if(custCard==null){
            throw new TradeException("GW13");
        }
        WapWithdrawAccBean withdrawAccBean = new WapWithdrawAccBean(custCard);
        return withdrawAccBean;
    }
    /**
     *
     * @param cardBean
     * @return
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public Long saveBindCard(TradeBean trade) {
      //String memberId = trade.getMerUserId();
        //String cardNo = trade.getCardNo();
        if(StringUtil.isEmpty(trade.getMerUserId())){
            return null;
        }
        if(StringUtil.isEmpty(trade.getBindCardId())){
            return null;
        }
        
        List<QuickpayCustModel> custList =  (List<QuickpayCustModel>) super.queryByHQL("from QuickpayCustModel where relatememberno=? and cardno = ? ", new Object[]{trade.getMerUserId(),trade.getCardNo()});
        if(custList.size()>0){
            return custList.get(0).getId();
        }else{//没有保存卡信息
            QuickpayCustModel quickpayCust = new QuickpayCustModel(trade);
            quickpayCust.setBindcardid(trade.getBindCardId());
            Map<String, Object> cardMap = routeConfigService.getCardInfo(trade.getCardNo());
            quickpayCust.setBankcode(cardMap.get("BANKCODE").toString());
            quickpayCust.setCardtype(cardMap.get("TYPE").toString());
            quickpayCust.setInstitution(trade.getPayinstiId());
            quickpayCust.setBankname(cardMap.get("BANKNAME")+"");
            super.saveEntity(quickpayCust);
            quickpayCust  = super.getUniqueByHQL("from QuickpayCustModel where relatememberno=? and cardno = ? ",  new Object[]{trade.getMerUserId(),trade.getCardNo()});
            return quickpayCust.getId();
        }
    }
    
    @Transactional
    public void updateBindCardId(Long id,String bindCardId){
        if(StringUtil.isEmpty(bindCardId)){
            //super.delete(super.get(id));
        }else{
            super.updateByHQL("update QuickpayCustModel set bindcardid = ? where id = ?", new Object[]{bindCardId,id});
        }
        
    }
    /**
     *
     * @param id
     */
    @Override
    @Transactional
    public void deleteCard(Long id) {
        QuickpayCustModel card = super.get(id);
        if(StringUtil.isEmpty(card.getBindcardid())){
            //super.delete(card);
        }
        
    }
	@Override
	@Transactional
	public List<QuickpayCustModel> getCardList(String cardNo, String accName,
			String phone, String cerId, String memberId) {
		 List<QuickpayCustModel> cardList = (List<QuickpayCustModel>) super.queryByHQL("from QuickpayCustModel where cardno=? and accname = ? and phone = ? and idnum = ? and relatememberno = ? and status = ?", new Object[]{cardNo,accName,phone,cerId,memberId,"00"});
		return cardList;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void deleteUnBindCard(Long id){
		QuickpayCustModel card = super.get(id);
        if(card!=null){
            super.delete(card);
        }
	}
	@Override
	@Transactional
	public List<QuickpayCustModel> getCardList(String cardNo, String accName,
			String phone, String cerId, String memberId, String devId) {
		 List<QuickpayCustModel> cardList = (List<QuickpayCustModel>) super.queryByHQL("from QuickpayCustModel where cardno=? and accname = ? and phone = ? and idnum = ? and relatememberno = ? and devId=?  and  status = ?", new Object[]{cardNo,accName,phone,cerId,memberId,devId,"00"});
			return cardList;
	}

}
