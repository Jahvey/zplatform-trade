/* 
 * BaseBean.java  
 * 
 * version TODO
 *
 * 2015年8月24日 
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
 * @date 2015年8月24日 上午10:08:38
 * @since 
 */
public class BaseBean {
    @NotEmpty(message="param.empty")
    private String merId;
    @NotEmpty(message="param.empty")
    private String orderNo;
    @NotEmpty(message="param.empty")
    @Length(max=15,message="param.error")
    private String merjnlNo;
    /**
     * @return the merId
     */
    public String getMerId() {
        return merId;
    }
    /**
     * @param merId the merId to set
     */
    public void setMerId(String merId) {
        this.merId = merId;
    }
    /**
     * @return the orderNo
     */
    public String getOrderNo() {
        return orderNo;
    }
    /**
     * @param orderNo the orderNo to set
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    /**
     * @return the merjnlNo
     */
    public String getMerjnlNo() {
        return merjnlNo;
    }
    /**
     * @param merjnlNo the merjnlNo to set
     */
    public void setMerjnlNo(String merjnlNo) {
        this.merjnlNo = merjnlNo;
    }
    

}
