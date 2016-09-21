/* 
 * ProdCaseServiceImpl.java  
 * 
 * version TODO
 *
 * 2015年9月11日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.acc.bean.enums.AcctStatusType;
import com.zlebank.zplatform.acc.bean.enums.Usage;
import com.zlebank.zplatform.commons.dao.pojo.BusiTypeEnum;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.member.bean.FinanceProductAccountBean;
import com.zlebank.zplatform.member.bean.FinanceProductQueryBean;
import com.zlebank.zplatform.member.pojo.PojoMerchDeta;
import com.zlebank.zplatform.member.service.FinanceProductAccountService;
import com.zlebank.zplatform.member.service.MerchService;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.enums.BusinessEnum;
import com.zlebank.zplatform.trade.bean.gateway.OrderBean;
import com.zlebank.zplatform.trade.bean.wap.WapOrderBean;
import com.zlebank.zplatform.trade.dao.IProdCaseDAO;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.model.MemberBaseModel;
import com.zlebank.zplatform.trade.model.ProdCaseModel;
import com.zlebank.zplatform.trade.model.TxncodeDefModel;
import com.zlebank.zplatform.trade.service.IMemberService;
import com.zlebank.zplatform.trade.service.IProdCaseService;
import com.zlebank.zplatform.trade.service.ITxncodeDefService;
import com.zlebank.zplatform.trade.service.base.BaseServiceImpl;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月11日 下午5:28:40
 * @since 
 */
@Service("prodCaseService")
public class ProdCaseServiceImpl extends BaseServiceImpl<ProdCaseModel, Long> implements IProdCaseService{

    @Autowired
    private IProdCaseDAO prodCaseDAO;
    @Autowired
    private IMemberService memberService;
    @Autowired
    private ITxncodeDefService txncodeDefService;
    @Autowired
    private MerchService merchService;
    @Autowired
    private FinanceProductAccountService financeProductAccountService;
    /**
     *
     * @return
     */
    @Override
    public Session getSession() {
        // TODO Auto-generated method stub
        return prodCaseDAO.getSession();
    }
    /**
     *
     * @param order
     * @return
     */
    @Override
    public ResultBean verifyBusiness(OrderBean order) {
        ResultBean resultBean = null;
        //PojoMerchDeta member = null;
        TxncodeDefModel busiModel = txncodeDefService.getBusiCode(order.getTxnType(), order.getTxnSubType(), order.getBizType());
        /*if(StringUtil.isNotEmpty(order.getMerId())){
        	member = merchService.getMerchBymemberId(order.getMerId());//memberService.getMemberByMemberId(order.getMerId());
        	ProdCaseModel prodCase= prodCaseDAO.getMerchProd(member.getPrdtVer(),busiModel.getBusicode());
            if(prodCase==null){
                resultBean = new ResultBean("GW26", "商户未开通此业务");
            }else {
                resultBean = new ResultBean("success");
            }
        }else{
        	BusiType busiType = BusiType.fromValue(busiModel.getBusitype());
            if(busiType==BusiType.CASH||busiType==BusiType.REPAIDP){
            	resultBean = new ResultBean("success");
            }else{
            	resultBean = new ResultBean("GW26", "个人用户未开通此业务");
            }
        }*/
        BusiTypeEnum busiTypeEnum = BusiTypeEnum.fromValue(busiModel.getBusitype());
        if(busiTypeEnum==BusiTypeEnum.consumption){//消费
        	BusinessEnum businessEnum = BusinessEnum.fromValue(busiModel.getBusicode());
        	if(StringUtil.isEmpty(order.getMerId())){
        		 //throw new CommonException("GW26", "商户号为空");
        		 resultBean = new ResultBean("GW26", "商户号为空");
        	}
        	PojoMerchDeta member = merchService.getMerchBymemberId(order.getMerId());//memberService.getMemberByMemberId(order.getMerId());.java
        	ProdCaseModel prodCase= prodCaseDAO.getMerchProd(member.getPrdtVer(),busiModel.getBusicode());
            if(prodCase==null){
               // throw new CommonException("GW26", "商户未开通此业务");
                resultBean = new ResultBean("GW26", "商户未开通此业务");
            }
            if(BusinessEnum.CONSUMEQUICK_PRODUCT==businessEnum){//产品消费业务
            	FinanceProductQueryBean financeProductQueryBean = new FinanceProductQueryBean();
            	financeProductQueryBean.setProductCode(order.getProductcode());
            	try {
					FinanceProductAccountBean productAccountBean = financeProductAccountService.queryBalance(financeProductQueryBean, Usage.BASICPAY);
					if(AcctStatusType.NORMAL!=AcctStatusType.fromValue(productAccountBean.getStatus())){
						//throw new CommonException("", "产品异常，请联系客服");
						resultBean = new ResultBean("T000", "产品异常，请联系客服");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//throw new CommonException("", "产品不存在");
					resultBean = new ResultBean("T000", "产品不存在");
				}
            }
            resultBean = new ResultBean("success");
        }else if(busiTypeEnum==BusiTypeEnum.charge){//充值
        	/*if (StringUtil.isEmpty(order.getMemberId()) || "999999999999999".equals(order.getMemberId())) {
				throw new CommonException("GW19", "会员不存在无法进行充值");
			}*/
        	resultBean = new ResultBean("success");
        }else if(busiTypeEnum==BusiTypeEnum.withdrawal){//提现
        	/*if (StringUtil.isEmpty(orderBean.getMemberId()) || "999999999999999".equals(orderBean.getMemberId())) {
				throw new CommonException("GW19", "会员不存在无法进行充值");
			}*/
        	resultBean = new ResultBean("success");
        }
        return resultBean;
    }
    
    public ResultBean verifyMerchBusiness(OrderBean order) {
        ResultBean resultBean = null;
        PojoMerchDeta member = null;
        TxncodeDefModel busiModel = txncodeDefService.getBusiCode(order.getTxnType(), order.getTxnSubType(), order.getBizType());
        if(StringUtil.isNotEmpty(order.getMerId())){//商户号不为空
        	member = merchService.getMerchBymemberId(order.getMerId());
        	ProdCaseModel prodCase= getMerchProd(member.getPrdtVer(),busiModel.getBusicode());
            if(prodCase==null){
                resultBean = new ResultBean("GW26", "商户未开通此业务");
            }else {
                resultBean = new ResultBean("success");
            }
        }else{
        	resultBean = new ResultBean("RC02", "商户不存在");
        }
        
        return resultBean;
    }
    
   
    public ProdCaseModel getMerchProd(String prdtver,String busicode){
    	Criteria criteria = prodCaseDAO.getSession().createCriteria(ProdCaseModel.class);
    	criteria.add(Restrictions.eq("prdtver", prdtver));
    	criteria.add(Restrictions.eq("busicode", busicode));
        return (ProdCaseModel) criteria.uniqueResult();
    }

    public void verifyWapBusiness(WapOrderBean order) throws TradeException {
        MemberBaseModel member = memberService.get(order.getMerId());
        TxncodeDefModel busiModel = txncodeDefService.getBusiCode(order.getTxnType(), order.getTxnSubType(), order.getBizType());
        if(busiModel==null){
            throw new TradeException("GW02");
        }
        ProdCaseModel prodCase= super.getUniqueByHQL("from ProdCaseModel where prdtver=? and busicode=?", new Object[]{member.getPrdtver(),busiModel.getBusicode()});
        if(prodCase==null){
             throw new TradeException("GW03");
        }
    }
}
