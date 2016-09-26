/* 
 * GatewayOrderBean.java  
 * 
 * version TODO
 *
 * 2015年8月21日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean.ecitic;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年8月21日 下午5:17:04
 * @since 
 */
public class GatewayOrderBean {
    @Length(max=2,message="param.error")
    private String inputCharset="1";
    
    @NotEmpty(message="param.empty")
    @Length(max=10,message="param.error")
    private String version="v2.0";
    
    @NotEmpty(message="param.empty")
    @Length(max=2,message="param.error")
    private String language="1";
    
    @NotEmpty(message="param.empty")
    @Length(max=2,message="param.error")
    private String signType="1";
    
    @Length(max=32,message="param.error")
    private String payerName;
    
    @Length(max=2,message="param.error")
    private String payerContactType="1";
    
    @Length(max=50,message="param.error")
    private String payerContact;
    
    @NotEmpty(message="param.empty")
    @Length(max=25,message="param.error")
    private String orderId;
    
    @NotEmpty(message="param.empty")
    @Length(max=2,message="param.error")
    private String orderType="00";
    
    @NotEmpty(message="param.empty")
    @Length(max=12,message="param.error")
    private String orderAmount;
    
    @NotEmpty(message="param.empty")
    @Length(max=14,message="param.error")
    private String orderTime;
    
    @Length(max=14,message="param.error")
    private String orderExpTime;
    
    @Length(max=256,message="param.error")
    private String productName;
    
    @Length(max=8,message="param.error")
    private String productNum;
    
    @Length(max=50,message="param.error")
    private String productId;
    
    @Length(max=400,message="param.error")
    private String productDesc;
    
    @NotEmpty(message="param.empty")
    @Length(max=30,message="param.error")
    private String merchantAcctId;
    
    @Length(max=30,message="param.error")
    private String purchaserId;
    
    @NotEmpty(message="param.empty")
    @Length(max=1,message="param.error")
    private String redoFlag="1";
    
    @Length(max=256,message="param.error")
    private String pageUrl;
    
    @Length(max=256,message="param.error")
    private String bgUrl;
    
    @Length(max=10,message="param.error")
    private String bankId;
    
    @NotEmpty(message="param.empty")
    @Length(max=1,message="param.error")
    private String pid="1";
    
    @NotEmpty(message="param.empty")
    @Length(max=2,message="param.error")
    private String payType="1";
    
    @Length(max=128,message="param.error")
    private String ext1;
    
    @Length(max=128,message="param.error")
    private String ext2;
    
    @NotEmpty(message="param.empty")
    @Length(max=15,message="param.error")
    private String merchantAcctIp;
    
    @NotEmpty(message="param.empty")
    @Length(max=256,message="param.error")
    private String signMsg;
    
   
}
