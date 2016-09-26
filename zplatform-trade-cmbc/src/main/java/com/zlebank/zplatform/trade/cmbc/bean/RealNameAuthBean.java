/* 
 * RealNameAuthBean.java  
 * 
 * version TODO
 *
 * 2015年11月23日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.cmbc.bean;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.trade.cmbc.bean.gateway.CardMessageBean;
import com.zlebank.zplatform.trade.utils.ConsUtil;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月23日 上午11:17:18
 * @since
 */
@XStreamAlias("Req")
public class RealNameAuthBean implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    public static final String XMLHEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    // 版本号
    @XStreamAlias("Version")
    private String version;
    // 交易日期
    @XStreamAlias("TransDate")
    private String transdate;
    // 交易时间
    @XStreamAlias("TransTime")
    private String transtime;
    // 流水号
    @XStreamAlias("SerialNo")
    private String serialno;
    // 商户号
    @XStreamAlias("MerId")
    private String merid;
    // 商户名（机构名称）
    @XStreamAlias("MerName")
    private String mername;
    // 卡折标志private String
    @XStreamAlias("CardType")
    private String cardtype;
    // 账户号
    @XStreamAlias("AccNo")
    private String accno;
    // 账户名
    @XStreamAlias("AccName")
    private String accname;
    // 证件类型
    @XStreamAlias("CertType")
    private String certtype;
    // 证件号码
    @XStreamAlias("CertNo")
    private String certno;
    // 手机号码
    @XStreamAlias("Phone")
    private String phone;
    // 付款行银联机构号
    @XStreamAlias("PayerBankInsCode")
    private String payerbankinscode;
    // 付款行所在省份编码
    @XStreamAlias("ProvNo")
    private String provno;
    // 备用域
    @XStreamAlias("Resv")
    private String resv;
    
    public RealNameAuthBean(CardMessageBean card){
        this.version = ConsUtil.getInstance().cons.getCmbc_version();
        this.transdate = DateUtil.getCurrentDate();
        this.transtime = DateUtil.getCurrentTime();
        this.serialno = card.getWithholding().getSerialno();
        this.merid = ConsUtil.getInstance().cons.getCmbc_merid();
        this.mername = ConsUtil.getInstance().cons.getCmbc_mername();
        this.cardtype = card.getCardType();
        this.accno = card.getCardNo();
        this.accname = card.getCustomerNm();
        this.certtype = card.getCertifTp();
        this.certno = card.getCertifId();
        this.phone = card.getPhoneNo();
    }
    
    /**
     * @param version
     * @param transdate
     * @param transtime
     * @param serialno
     * @param merid
     * @param mername
     * @param cardtype
     * @param accno
     * @param accname
     * @param certtype
     * @param certno
     * @param phone
     * @param payerbankinscode
     * @param provno
     * @param resv
     */
    public RealNameAuthBean(String version, String transdate, String transtime,
            String serialno, String merid, String mername, String cardtype,
            String accno, String accname, String certtype, String certno,
            String phone, String payerbankinscode, String provno, String resv) {
        super();
        this.version = version;
        this.transdate = transdate;
        this.transtime = transtime;
        this.serialno = serialno;
        this.merid = merid;
        this.mername = mername;
        this.cardtype = cardtype;
        this.accno = accno;
        this.accname = accname;
        this.certtype = certtype;
        this.certno = certno;
        this.phone = phone;
        this.payerbankinscode = payerbankinscode;
        this.provno = provno;
        this.resv = resv;
    }

    public RealNameAuthBean(){
        
    }
    
    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }
    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }
    /**
     * @return the transdate
     */
    public String getTransdate() {
        return transdate;
    }
    /**
     * @param transdate the transdate to set
     */
    public void setTransdate(String transdate) {
        this.transdate = transdate;
    }
    /**
     * @return the transtime
     */
    public String getTranstime() {
        return transtime;
    }
    /**
     * @param transtime the transtime to set
     */
    public void setTranstime(String transtime) {
        this.transtime = transtime;
    }
    /**
     * @return the serialno
     */
    public String getSerialno() {
        return serialno;
    }
    /**
     * @param serialno the serialno to set
     */
    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }
    /**
     * @return the merid
     */
    public String getMerid() {
        return merid;
    }
    /**
     * @param merid the merid to set
     */
    public void setMerid(String merid) {
        this.merid = merid;
    }
    /**
     * @return the mername
     */
    public String getMername() {
        return mername;
    }
    /**
     * @param mername the mername to set
     */
    public void setMername(String mername) {
        this.mername = mername;
    }
    /**
     * @return the cardtype
     */
    public String getCardtype() {
        return cardtype;
    }
    /**
     * @param cardtype the cardtype to set
     */
    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }
    /**
     * @return the accno
     */
    public String getAccno() {
        return accno;
    }
    /**
     * @param accno the accno to set
     */
    public void setAccno(String accno) {
        this.accno = accno;
    }
    /**
     * @return the accname
     */
    public String getAccname() {
        return accname;
    }
    /**
     * @param accname the accname to set
     */
    public void setAccname(String accname) {
        this.accname = accname;
    }
    /**
     * @return the certtype
     */
    public String getCerttype() {
        return certtype;
    }
    /**
     * @param certtype the certtype to set
     */
    public void setCerttype(String certtype) {
        this.certtype = certtype;
    }
    /**
     * @return the certno
     */
    public String getCertno() {
        return certno;
    }
    /**
     * @param certno the certno to set
     */
    public void setCertno(String certno) {
        this.certno = certno;
    }
    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }
    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * @return the payerbankinscode
     */
    public String getPayerbankinscode() {
        return payerbankinscode;
    }
    /**
     * @param payerbankinscode the payerbankinscode to set
     */
    public void setPayerbankinscode(String payerbankinscode) {
        this.payerbankinscode = payerbankinscode;
    }
    /**
     * @return the provno
     */
    public String getProvno() {
        return provno;
    }
    /**
     * @param provno the provno to set
     */
    public void setProvno(String provno) {
        this.provno = provno;
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
    
    public synchronized String toXML(){
        XStream xstream = new XStream(new DomDriver(null,new XmlFriendlyNameCoder("_-", "_")));  
        //xstream.processAnnotations(RealTimePayBean.class);
        xstream.autodetectAnnotations(true);
        String xml = XMLHEAD+xstream.toXML(this);
        Pattern p = Pattern.compile("\\s{2,}|\t|\r|\n");
        Matcher m = p.matcher(xml);
        xml=m.replaceAll("") ;
        return  xml;
    }
}
