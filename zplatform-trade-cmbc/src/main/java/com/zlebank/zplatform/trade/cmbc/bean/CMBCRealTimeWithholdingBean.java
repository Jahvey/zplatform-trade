/* 
 * CMBCRealTimeInsteadPayBean.java  
 * 
 * version TODO
 *
 * 2015年11月26日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.cmbc.bean;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.trade.cmbc.security.CMBCAESUtils;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.OrderNumber;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月26日 下午2:43:56
 * @since 
 */
@XStreamAlias("TRAN_REQ")
public class CMBCRealTimeWithholdingBean implements Serializable{
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7300191497989671924L;
	public static final String XMLHEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    private static final String SERVICECODE = "130071001";
    @XStreamAlias("COMPANY_ID")
    private String company_id;//合作方id
    @XStreamAlias("MCHNT_CD")
    private String mchnt_cd  ;//  商户编号
    @XStreamAlias("TRAN_DATE")
    private String tran_date   ;//交易日期
    @XStreamAlias("TRAN_TIME")
    private String tran_time   ;//交易时间
    @XStreamAlias("TRAN_ID")
    private String tran_id ;//渠道流水号
    @XStreamAlias("BUSI_TYPE")
    private String busi_type  ;// 业务类型
    @XStreamAlias("BUSI_NO")
    private String busi_no ;//业务号码
    @XStreamAlias("CURRENCY")
    private String currency;//交易币种
    @XStreamAlias("ACC_NO")
    private String acc_no  ;//付款人账户号
    @XStreamAlias("ACC_NAME")
    private String acc_name;//付款人账户名
    @XStreamAlias("BANK_TYPE")
    private String bank_type  ;// 付款人账户行别
    @XStreamAlias("BANK_NAME")
    private String bank_name  ;// 付款人账户开户行名称
    @XStreamAlias("TRANS_AMT")
    private String trans_amt  ;// 交易金额
    @XStreamAlias("CERT_TYPE")
    private String cert_type ;//  证件类型
    @XStreamAlias("CERT_NO")
    private String cert_no ;//证件号码
    @XStreamAlias("CHK_FLAG")
    private String chk_flag ;//   户名、证件检查标志
    @XStreamAlias("REMARK")
    private String remark;//  客户流水摘要
    @XStreamAlias("RESV")
    private String resv;//备用域
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
     * @return the busi_type
     */
    public String getBusi_type() {
        return busi_type;
    }
    /**
     * @param busi_type the busi_type to set
     */
    public void setBusi_type(String busi_type) {
        this.busi_type = busi_type;
    }
    /**
     * @return the busi_no
     */
    public String getBusi_no() {
        return busi_no;
    }
    /**
     * @param busi_no the busi_no to set
     */
    public void setBusi_no(String busi_no) {
        this.busi_no = busi_no;
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
     * @return the cert_type
     */
    public String getCert_type() {
        return cert_type;
    }
    /**
     * @param cert_type the cert_type to set
     */
    public void setCert_type(String cert_type) {
        this.cert_type = cert_type;
    }
    /**
     * @return the cert_no
     */
    public String getCert_no() {
        return cert_no;
    }
    /**
     * @param cert_no the cert_no to set
     */
    public void setCert_no(String cert_no) {
        this.cert_no = cert_no;
    }
    /**
     * @return the chk_flag
     */
    public String getChk_flag() {
        return chk_flag;
    }
    /**
     * @param chk_flag the chk_flag to set
     */
    public void setChk_flag(String chk_flag) {
        this.chk_flag = chk_flag;
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
     * @param busi_type
     * @param busi_no
     * @param currency
     * @param acc_no
     * @param acc_name
     * @param bank_type
     * @param bank_name
     * @param trans_amt
     * @param cert_type
     * @param cert_no
     * @param chk_flag
     * @param remark
     * @param resv
     */
    public CMBCRealTimeWithholdingBean(String company_id, String mchnt_cd,
            String tran_date, String tran_time, String tran_id,
            String busi_type, String busi_no, String currency, String acc_no,
            String acc_name, String bank_type, String bank_name,
            String trans_amt, String cert_type, String cert_no,
            String chk_flag, String remark, String resv) {
        super();
        this.company_id = company_id;
        this.mchnt_cd = mchnt_cd;
        this.tran_date = tran_date;
        this.tran_time = tran_time;
        this.tran_id = tran_id;
        this.busi_type = busi_type;
        this.busi_no = busi_no;
        this.currency = currency;
        this.acc_no = acc_no;
        this.acc_name = acc_name;
        this.bank_type = bank_type;
        this.bank_name = bank_name;
        this.trans_amt = trans_amt;
        this.cert_type = cert_type;
        this.cert_no = cert_no;
        this.chk_flag = chk_flag;
        this.remark = remark;
        this.resv = resv;
    }
    /**
     * 
     */
    public CMBCRealTimeWithholdingBean() {
        super();
        
    }
    public CMBCRealTimeWithholdingBean(String test) {
        super();
        this.company_id = ConsUtil.getInstance().cons.getCmbc_self_merid();
        this.mchnt_cd = ConsUtil.getInstance().cons.getCmbc_self_merchant();
        this.tran_date = DateUtil.getCurrentDate();
        this.tran_time = DateUtil.getCurrentTime();
        this.tran_id = OrderNumber.getInstance().generateRealTimeWithholdingCMBCSelf();
        this.busi_type = "";
        this.busi_no = "";//6226222900069886、测试1153372331、ZR20、idno1153372331
        //6226222900000089 李四 ZR01 350201197905060016   600078596 厦门批量测试 ZC01 66668235-9
        this.currency = "RMB";//6226222900085791、测试1156804026、ZR20、idno1156804026
        this.acc_no = "600078596";
        this.acc_name = "厦门批量测试";
        this.bank_type = "305100001008";
        this.bank_name = "中国民生银行股份有限公司北京方庄支行";
        this.trans_amt = "100";
        this.cert_type = "ZC01";
        this.cert_no = "66668235-9";
        this.chk_flag = "2";
        this.remark = "测试";
        this.resv = "";
    }
    public synchronized String toXML(){
        XStream xstream = new XStream(new DomDriver(null,new XmlFriendlyNameCoder("_-", "_")));  
        xstream.autodetectAnnotations(true);
        String xml = XMLHEAD+xstream.toXML(this);
        Pattern p = Pattern.compile("\\s{2,}|\t|\r|\n");
        Matcher m = p.matcher(xml);
        xml=m.replaceAll("");
        int xml_length = xml.getBytes().length;
        DecimalFormat df = new DecimalFormat("000000");
        String msgLength = df.format(xml_length);
        String sign = CMBCAESUtils.encodeSelfWithholdingMD5(xml).toUpperCase();
        String serviceMsg = SERVICECODE+"      ";
        return  msgLength+serviceMsg+xml+sign;
    }
    

}
