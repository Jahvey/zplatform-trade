/* 
 * IMemberInfoService.java  
 * 
 * version TODO
 *
 * 2016年1月18日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import com.zlebank.zplatform.trade.bean.wap.WapCardBean;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年1月18日 下午1:38:20
 * @since 
 */
public interface IMemberInfoService {

	//会员号查询
	public Object queryMemberId(String loginId);
	//会员号注册
	public Object registerMember(String loginId,String phoneNo,String smsCode,String passWd);
	//会员登陆
	public Object loginMember(String loginId,String passWd);
	//实名认证
	public Object realNameBankCard(String memberId,String personMemberId,WapCardBean cardBean);
	//验证密码
	public Object verfiyPassWord(String memberId,String passwdType,String passWd);
	//修改密码
	public Object modifyPassWord(String memberId,String passwdType,String oldPassWd,String newPassWd);
	//重置登录密码
	public Object resetLoginPassWord(String memberId,String phoneNo,String smsCode,String passWd);
	//可受理银行卡信息
	public String queryUsableBankCard();
	//银行卡卡BIN查询
	public Object queryCardBin(String cardNo);
	//绑定银行卡
	public Object bindBankCard();
	//解绑解行卡
	public Object unbindBankCard();
	//用户签约信息查询
	public String queryMemberSignInfo(String json);
	
}
