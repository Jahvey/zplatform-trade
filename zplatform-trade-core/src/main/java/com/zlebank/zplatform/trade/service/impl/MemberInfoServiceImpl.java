/* 
 * MemberInfoServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年1月18日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.zlebank.zplatform.member.service.MemberService;
import com.zlebank.zplatform.trade.bean.wap.WapCardBean;
import com.zlebank.zplatform.trade.service.IMemberInfoService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年1月18日 下午2:34:57
 * @since 
 */
public class MemberInfoServiceImpl implements IMemberInfoService{

	@Autowired
	private MemberService memberService;
	/**
	 *
	 * @param loginId
	 * @return
	 */
	@Override
	public Object queryMemberId(String loginId) {
		
		return null;
	}

	/**
	 *
	 * @param loginId
	 * @param phoneNo
	 * @param smsCode
	 * @param passWd
	 * @return
	 */
	@Override
	public Object registerMember(String loginId, String phoneNo,
			String smsCode, String passWd) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 *
	 * @param loginId
	 * @param passWd
	 * @return
	 */
	@Override
	public Object loginMember(String loginId, String passWd) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 *
	 * @param memberId
	 * @param personMemberId
	 * @param cardBean
	 * @return
	 */
	@Override
	public Object realNameBankCard(String memberId, String personMemberId,
			WapCardBean cardBean) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 *
	 * @param memberId
	 * @param passwdType
	 * @param passWd
	 * @return
	 */
	@Override
	public Object verfiyPassWord(String memberId, String passwdType,
			String passWd) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 *
	 * @param memberId
	 * @param passwdType
	 * @param oldPassWd
	 * @param newPassWd
	 * @return
	 */
	@Override
	public Object modifyPassWord(String memberId, String passwdType,
			String oldPassWd, String newPassWd) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 *
	 * @param memberId
	 * @param phoneNo
	 * @param smsCode
	 * @param passWd
	 * @return
	 */
	@Override
	public Object resetLoginPassWord(String memberId, String phoneNo,
			String smsCode, String passWd) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 *
	 * @return
	 */
	@Override
	public String queryUsableBankCard() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 *
	 * @param cardNo
	 * @return
	 */
	@Override
	public Object queryCardBin(String cardNo) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 *
	 * @return
	 */
	@Override
	public Object bindBankCard() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 *
	 * @return
	 */
	@Override
	public Object unbindBankCard() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 *
	 * @param json
	 * @return
	 */
	@Override
	public String queryMemberSignInfo(String json) {
		// TODO Auto-generated method stub
		return null;
	}

}
