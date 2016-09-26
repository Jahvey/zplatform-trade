/* 
 * MemberLogin.java  
 * 
 * version v1.0
 *
 * 2015年10月9日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.message;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 会员登陆【请求报文】
 *
 * @author yangpeng
 * @version
 * @date 2015年10月9日 下午3:46:01
 * @since 
 */
public class InitMemberPayPassword_Request  extends BaseMessage {
    
    /**会员ID**/
    @NotEmpty
    private String memberId;
    /**渠道类型**/
    private String channelType;
    /**接入类型**/
    private String accessType;
    /**登录密码**/
    @NotEmpty
    private String passWd;
    /**支付密码**/
    @NotEmpty
    private String payPwd;
 
    public String getMemberId() {
        return memberId;
    }

    public String getChannelType() {
        return channelType;
    }
    public String getAccessType() {
        return accessType;
    }
    public String getPassWd() {
        return passWd;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }
    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }
    public void setPassWd(String passWd) {
        this.passWd = passWd;
    }

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
    }
    
    

}
