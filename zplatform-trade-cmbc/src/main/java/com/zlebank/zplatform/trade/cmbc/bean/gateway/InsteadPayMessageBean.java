/* 
 * InsteadPayMessageBean.java  
 * 
 * version TODO
 *
 * 2015年11月26日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.cmbc.bean.gateway;


/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月26日 上午11:20:47
 * @since 
 */
public class InsteadPayMessageBean {
    private String acc_no="";// 收款人账户号
    private String acc_name="";// 收款人账户名
    private String bank_type="";// 收款人账户联行号
    private String bank_name="";// 收款人账户开户行名称
    private String trans_amt="";// 交易金额
    private String remark="";// 客户流水摘要
    
    /**
     * @return the acc_no
     */
    public String getAcc_no() {
        return acc_no;
    }

    /**
     * @param acc_no the acc_no to set
     */
    public void setAcc_no(String acc_no) {
        this.acc_no = acc_no;
    }

    /**
     * @return the acc_name
     */
    public String getAcc_name() {
        return acc_name;
    }

    /**
     * @param acc_name the acc_name to set
     */
    public void setAcc_name(String acc_name) {
        this.acc_name = acc_name;
    }

    /**
     * @return the bank_type
     */
    public String getBank_type() {
        return bank_type;
    }

    /**
     * @param bank_type the bank_type to set
     */
    public void setBank_type(String bank_type) {
        this.bank_type = bank_type;
    }

    /**
     * @return the bank_name
     */
    public String getBank_name() {
        return bank_name;
    }

    /**
     * @param bank_name the bank_name to set
     */
    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    /**
     * @return the trans_amt
     */
    public String getTrans_amt() {
        return trans_amt;
    }

    /**
     * @param trans_amt the trans_amt to set
     */
    public void setTrans_amt(String trans_amt) {
        this.trans_amt = trans_amt;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @param acc_no
     * @param acc_name
     * @param bank_type
     * @param bank_name
     * @param trans_amt
     * @param remark
     */
    public InsteadPayMessageBean(String acc_no, String acc_name,
            String bank_type, String bank_name, String trans_amt, String remark) {
        super();
        this.acc_no = acc_no;
        this.acc_name = acc_name;
        this.bank_type = bank_type;
        this.bank_name = bank_name;
        this.trans_amt = trans_amt;
        this.remark = remark;
    }
    public InsteadPayMessageBean(String text) {
        this.acc_no = "6228480018543668976";
        this.acc_name = "郭佳";
        this.bank_type = "";
        this.bank_name = "";
        this.trans_amt = "100";
        this.remark = "测试";
    }
    
}
