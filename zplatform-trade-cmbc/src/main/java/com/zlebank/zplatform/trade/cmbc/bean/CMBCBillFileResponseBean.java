/* 
 * CMBCBillFileResponseBean.java  
 * 
 * version TODO
 *
 * 2015年11月26日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.cmbc.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月26日 下午5:25:27
 * @since
 */
@XStreamAlias("TRAN_RESP")
public class CMBCBillFileResponseBean {
    @XStreamAlias("RESP_TYPE")
    private String resp_type;// 应答码类型
    @XStreamAlias("RESP_CODE")
    private String resp_code;// 应答码
    @XStreamAlias("RESP_MSG")
    private String resp_msg;// 应答描述
    /**
     * @return the resp_type
     */
    public String getResp_type() {
        return resp_type;
    }
    /**
     * @param resp_type the resp_type to set
     */
    public void setResp_type(String resp_type) {
        this.resp_type = resp_type;
    }
    /**
     * @return the resp_code
     */
    public String getResp_code() {
        return resp_code;
    }
    /**
     * @param resp_code the resp_code to set
     */
    public void setResp_code(String resp_code) {
        this.resp_code = resp_code;
    }
    /**
     * @return the resp_msg
     */
    public String getResp_msg() {
        return resp_msg;
    }
    /**
     * @param resp_msg the resp_msg to set
     */
    public void setResp_msg(String resp_msg) {
        this.resp_msg = resp_msg;
    }

}
