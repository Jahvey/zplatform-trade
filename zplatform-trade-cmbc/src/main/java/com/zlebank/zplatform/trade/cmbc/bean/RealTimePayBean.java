/* 
 * RealTimePayBean.java  
 * 
 * version TODO
 *
 * 2015年11月5日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.cmbc.bean;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.trade.cmbc.bean.gateway.InsteadPayMessageBean;
import com.zlebank.zplatform.trade.cmbc.security.CMBCAESUtils;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.OrderNumber;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月5日 下午1:45:41
 * @since
 */
@XStreamAlias("TRAN_REQ")
public class RealTimePayBean {
    public static final String XMLHEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    private static final String SERVICECODE = "1002";
    @XStreamAlias("COMPANY_ID")
    private String company_id="";// 合作方id
    @XStreamAlias("MCHNT_CD")
    private String mchnt_cd="";// 商户编号
    @XStreamAlias("TRAN_DATE")
    private String tran_date="";// 交易日期
    @XStreamAlias("TRAN_TIME")
    private String tran_time="";// 交易时间
    @XStreamAlias("TRAN_ID")
    private String tran_id="";// 渠道流水号
    @XStreamAlias("CURRENCY")
    private String currency="";// 交易币种
    @XStreamAlias("ACC_NO")
    private String acc_no="";// 收款人账户号
    @XStreamAlias("ACC_NAME")
    private String acc_name="";// 收款人账户名
    @XStreamAlias("BANK_TYPE")
    private String bank_type="";// 收款人账户联行号
    @XStreamAlias("BANK_NAME")
    private String bank_name="";// 收款人账户开户行名称
    @XStreamAlias("TRANS_AMT")
    private String trans_amt="";// 交易金额
    @XStreamAlias("REMARK")
    private String remark="";// 客户流水摘要
    @XStreamAlias("RESV")
    private String resv="";// 备用域
    
    
    /**
     * @return the company_id
     */
    public String getCompany_id() {
        return company_id;
    }
    /**
     * @param company_id the company_id to set
     */
    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }
    /**
     * @return the mchnt_cd
     */
    public String getMchnt_cd() {
        return mchnt_cd;
    }
    /**
     * @param mchnt_cd the mchnt_cd to set
     */
    public void setMchnt_cd(String mchnt_cd) {
        this.mchnt_cd = mchnt_cd;
    }
    /**
     * @return the tran_date
     */
    public String getTran_date() {
        return tran_date;
    }
    /**
     * @param tran_date the tran_date to set
     */
    public void setTran_date(String tran_date) {
        this.tran_date = tran_date;
    }
    /**
     * @return the tran_time
     */
    public String getTran_time() {
        return tran_time;
    }
    /**
     * @param tran_time the tran_time to set
     */
    public void setTran_time(String tran_time) {
        this.tran_time = tran_time;
    }
    /**
     * @return the tran_id
     */
    public String getTran_id() {
        return tran_id;
    }
    /**
     * @param tran_id the tran_id to set
     */
    public void setTran_id(String tran_id) {
        this.tran_id = tran_id;
    }
    /**
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }
    /**
     * @param currency the currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }
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
     * @return the resv
     */
    public String getResv() {
        return resv;
    }
    /**
     * @param resv the resv to set
     */
    public void setResv(String resv) {
        this.resv = resv;
    }
    
    
    /**
     * @param company_id
     * @param mchnt_cd
     * @param tran_date
     * @param tran_time
     * @param tran_id
     * @param currency
     * @param acc_no
     * @param acc_name
     * @param bank_type
     * @param bank_name
     * @param trans_amt
     * @param remark
     * @param resv
     */
    public RealTimePayBean(String company_id, String mchnt_cd,
            String tran_date, String tran_time, String tran_id,
            String currency, String acc_no, String acc_name, String bank_type,
            String bank_name, String trans_amt, String remark, String resv) {
        super();
        this.company_id = company_id;
        this.mchnt_cd = mchnt_cd;
        this.tran_date = tran_date;
        this.tran_time = tran_time;
        this.tran_id = tran_id;
        this.currency = currency;
        this.acc_no = acc_no;
        this.acc_name = acc_name;
        this.bank_type = bank_type;
        this.bank_name = bank_name;
        this.trans_amt = trans_amt;
        this.remark = remark;
        this.resv = resv;
    }
     
     public RealTimePayBean(InsteadPayMessageBean bean) {
         this.company_id = ConsUtil.getInstance().cons.getCmbc_insteadpay_merid();
         this.mchnt_cd = "";
         this.tran_date = DateUtil.getCurrentDate();
         this.tran_time = DateUtil.getCurrentTime();
         this.tran_id = OrderNumber.getInstance().generateRealTimeInsteadPayOrderNo();
         this.currency = "RMB";
         this.acc_no = bean.getAcc_no();
         this.acc_name = bean.getAcc_name();
         this.bank_type = bean.getBank_type();
         this.bank_name = bean.getBank_name();
         this.trans_amt = bean.getTrans_amt();
         this.remark = bean.getRemark();
         this.resv = "test";
     }
     
    public RealTimePayBean() {
        super();
    }
    
    public synchronized String toXML(){
        XStream xstream = new XStream(new DomDriver(null,new XmlFriendlyNameCoder("_-", "_")));  
        //xstream.processAnnotations(RealTimePayBean.class);
        xstream.autodetectAnnotations(true);
        String xml = XMLHEAD+xstream.toXML(this);
        Pattern p = Pattern.compile("\\s{2,}|\t|\r|\n");
        Matcher m = p.matcher(xml);
        xml=m.replaceAll("") ;
        int xml_length = xml.getBytes().length;
        DecimalFormat df = new DecimalFormat("000000");
        String msgLength = df.format(xml_length);
        String sign = CMBCAESUtils.encodeMD5(xml).toUpperCase();
        String serviceMsg = SERVICECODE+"           ";
        return  msgLength+serviceMsg+xml+sign;
    }
}
