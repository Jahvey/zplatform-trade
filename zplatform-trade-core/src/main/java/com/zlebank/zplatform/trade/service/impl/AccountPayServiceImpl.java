/* 
 * AccountPayServiceImpl.java  
 * 
 * version TODO
 *
 * 2015年10月10日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.acc.bean.BusiAcct;
import com.zlebank.zplatform.acc.bean.BusiAcctQuery;
import com.zlebank.zplatform.acc.bean.enums.Usage;
import com.zlebank.zplatform.acc.pojo.Money;
import com.zlebank.zplatform.acc.service.AccEntryService;
import com.zlebank.zplatform.acc.service.AccountQueryService;
import com.zlebank.zplatform.commons.dao.pojo.BusiTypeEnum;
import com.zlebank.zplatform.commons.utils.Base64Utils;
import com.zlebank.zplatform.commons.utils.RSAUtils;
import com.zlebank.zplatform.member.bean.CoopInstiMK;
import com.zlebank.zplatform.member.bean.MemberBean;
import com.zlebank.zplatform.member.bean.enums.MemberType;
import com.zlebank.zplatform.member.bean.enums.TerminalAccessType;
import com.zlebank.zplatform.member.dao.CoopInstiDAO;
import com.zlebank.zplatform.member.dao.MemberDAO;
import com.zlebank.zplatform.member.exception.DataCheckFailedException;
import com.zlebank.zplatform.member.pojo.PojoMember;
import com.zlebank.zplatform.member.service.CoopInstiService;
import com.zlebank.zplatform.member.service.MemberOperationService;
import com.zlebank.zplatform.member.service.MerchMKService;
import com.zlebank.zplatform.member.service.PersonService;
import com.zlebank.zplatform.trade.adapter.accounting.impl.ConsumeAccounting;
import com.zlebank.zplatform.trade.bean.AccountTradeBean;
import com.zlebank.zplatform.trade.bean.AppPartyBean;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.factory.AccountingAdapterFactory;
import com.zlebank.zplatform.trade.service.IAccountPayService;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.utils.DateUtil;
import com.zlebank.zplatform.trade.utils.OrderNumber;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年10月10日 上午10:39:37
 * @since 
 */
@Service("accountPayService")
public class AccountPayServiceImpl implements IAccountPayService{
    @Autowired
    private PersonService personService;
    @Autowired
    private MerchMKService merchMKService;
    @Autowired
    private ITxnsLogService txnsLogService;
    @Autowired
    private AccEntryService accEntryService;
    @Autowired
    private MemberOperationService memberOperationServiceImpl;
    @Autowired
    private AccountQueryService accountQueryService;
    @Autowired
    private MemberDAO memberDAO;
    @Autowired
    private CoopInstiService coopInstiService;
    @Autowired
    private CoopInstiDAO coopInstiDAO;
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void accountPay(AccountTradeBean accountTrade) throws TradeException{
        if(validateBalance(accountTrade.getMemberId(),Long.valueOf(accountTrade.getAmount()))<0){
            throw new TradeException("T025");
        }
        if(validatePayPWD(accountTrade)){
	        //记录账户支付流水
	        txnsLogService.saveAccountTrade(accountTrade);
	        try {
	            String commiteTime = DateUtil.getCurrentDateTime();
	            //开始账户余额支付
	            ResultBean resultBean = AccountingAdapterFactory.getInstance().getAccounting(BusiTypeEnum.consumption).accountedFor(accountTrade.getTxnseqno());
	            //ConsumeAccounting accounting = new ConsumeAccounting();
	            //ResultBean resultBean =accounting.accountedFor();
	            if(!resultBean.isResultBool()){
	                throw new TradeException("AP05");
	            }
	            //更新账户支付信息
	            txnsLogService.updateAccountTrade(accountTrade, resultBean);
	            //应用方信息
	            AppPartyBean appParty = new AppPartyBean("", "000000000000", commiteTime, DateUtil.getCurrentDateTime(), accountTrade.getTxnseqno(),"");
	            txnsLogService.updateAppInfo(appParty);
	            if(!resultBean.isResultBool()){
	                throw new TradeException("AP05");
	            }
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            throw new TradeException("AP05");
	        }
        }else{
            throw new TradeException("AP04");
        }
    }
    
    public void mobileAccountPay(AccountTradeBean accountTrade) throws TradeException{
        if(validateBalance(accountTrade.getMemberId(),Long.valueOf(accountTrade.getAmount()))<0){
            throw new TradeException("T025");
        }
        //记录账户支付流水
        txnsLogService.saveAccountTrade(accountTrade);
        try {
            String commiteTime = DateUtil.getCurrentDateTime();
            //开始账户余额支付
            ResultBean resultBean = AccountingAdapterFactory.getInstance().getAccounting(BusiTypeEnum.consumption).accountedFor(accountTrade.getTxnseqno());
            if(!resultBean.isResultBool()){
            	throw new TradeException("AP05");
            }
            //更新账户支付信息
            txnsLogService.updateAccountTrade(accountTrade, resultBean);
            //应用方信息
            AppPartyBean appParty = new AppPartyBean("", "000000000000", commiteTime, DateUtil.getCurrentDateTime(), accountTrade.getTxnseqno(),"");
            txnsLogService.updateAppInfo(appParty);
            if(!resultBean.isResultBool()){
                throw new TradeException("AP05");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new TradeException("AP05");
        }
    }
    
    private boolean validatePayPWD(AccountTradeBean accountTrade) throws TradeException{
    	
    	try {
			PojoMember pojo =memberDAO.getMemberByMemberId(accountTrade.getMemberId(), MemberType.INDIVIDUAL);
			MemberBean memberBean = new MemberBean();
			memberBean.setLoginName(pojo.getLoginName());
			memberBean.setInstiId(pojo.getInstiId());
			memberBean.setPaypwd(accountTrade.getPay_pwd());
			// 校验支付密码
			if (memberOperationServiceImpl.verifyPayPwd(MemberType.INDIVIDUAL,
			        memberBean)) {
				 throw new TradeException("AP05");
			}
		} catch (DataCheckFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
    }
    
    /*private boolean validatePayPWD(AccountTradeBean accountTrade) throws TradeException{
        try {
            PojoPersonDeta person =personService.getPersonByMemberId(accountTrade.getMemberId());
            
            // 平台私钥解密
            MerchMK merchMk = merchMKService.get(accountTrade.getMerchId());
            byte[] decodedData = RSAUtils.decryptByPrivateKey(Base64Utils.decode(accountTrade.getPay_pwd()), merchMk.getLocalPriKey());
            String payPassWd = StringUtil.getUTF8(decodedData);

            // 支付密码验证
            String passwordKey = ConsUtil.getInstance().cons.getMember_pay_password_key();
            String password = Md5.getInstance().md5s(payPassWd+passwordKey);
            if(!password.equals(person.getPayPwd())){
                return false;
            }else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new TradeException("AP02");
        }
    }*/
    
    public String encryptPWD(String merchId,String pwd) throws TradeException{
        try {
            //平台公钥加密
        	CoopInstiMK merchMk	= coopInstiService.getCoopInstiMK(merchId, TerminalAccessType.INVPORTAL);
            //MerchMK merchMk = merchMKService.get(merchId);
            byte[] decodedData = RSAUtils.encryptByPublicKey(pwd.getBytes(), merchMk.getZplatformPubKey());
            return Base64Utils.encode(decodedData);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new TradeException("AP02");
        }
    }
    private int validateBalance(String memberId,Long amt){
        List<BusiAcct> busiAccList =  accountQueryService.getBusiACCByMid(memberId);
        BusiAcct fundAcct = null;
        //取得资金账户
        for(BusiAcct busiAcct : busiAccList){
            if(Usage.BASICPAY==busiAcct.getUsage()){
                fundAcct = busiAcct;
            }
        }
        BusiAcctQuery memberAcct = accountQueryService.getMemberQueryByID(fundAcct.getBusiAcctCode());
        //会员资金账户余额
        return memberAcct.getBalance().compareTo(Money.valueOf(new BigDecimal(amt)));
    }
}
