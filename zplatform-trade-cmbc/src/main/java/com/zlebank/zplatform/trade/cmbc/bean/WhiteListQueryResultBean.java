/* 
 * WhiteListQueryResultBean.java  
 * 
 * version TODO
 *
 * 2015年11月25日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.cmbc.bean;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月25日 下午5:32:45
 * @since
 */
@XStreamAlias("Ans")
public class WhiteListQueryResultBean implements Serializable{
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -704084250743205887L;
	@XStreamAlias("Version")
    private String version;// 版本号
    @XStreamAlias("TransDate")
    private String transdate;// 交易日期
    @XStreamAlias("TransTime")
    private String transtime;// 交易时间
    @XStreamAlias("ReqSerialNo")
    private String reqserialno;// 请求流水号
    @XStreamAlias("ExecType")
    private String exectype;// 响应类型
    @XStreamAlias("ExecCode")
    private String execcode;// 响应代码
    @XStreamAlias("ExecMsg")
    private String execmsg;// 响应描述
    @XStreamAlias("Status")
    private String status;// 交易状态
    @XStreamAlias("BankAccNo")
    private String bankaccno;// 银行账号
    /**
     * @param version
     * @param transdate
     * @param transtime
     * @param reqserialno
     * @param exectype
     * @param execcode
     * @param execmsg
     * @param status
     * @param bankaccno
     */
    public WhiteListQueryResultBean(String version, String transdate,
            String transtime, String reqserialno, String exectype,
            String execcode, String execmsg, String status, String bankaccno) {
        super();
        this.version = version;
        this.transdate = transdate;
        this.transtime = transtime;
        this.reqserialno = reqserialno;
        this.exectype = exectype;
        this.execcode = execcode;
        this.execmsg = execmsg;
        this.status = status;
        this.bankaccno = bankaccno;
    }
    
    
}
