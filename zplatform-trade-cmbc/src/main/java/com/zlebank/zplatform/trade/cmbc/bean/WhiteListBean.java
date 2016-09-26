/* 
 * WhiteListBean.java  
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.trade.cmbc.bean.gateway.WhiteListMessageBean;
import com.zlebank.zplatform.trade.utils.ConsUtil;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月25日 下午1:31:57
 * @since
 */
@XStreamAlias("Req")
public class WhiteListBean implements Serializable{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2287968533367603425L;
    public static final String XMLHEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    @XStreamAlias("Version")
    private String version;// 版本号
    @XStreamAlias("TransDate")
    private String transdate;// 交易日期
    @XStreamAlias("TransTime")
    private String transtime;// 交易时间
    @XStreamAlias("SerialNo")
    private String serialno;// 流水号
    @XStreamAlias("MerId")
    private String merid;// 商户号
    @XStreamAlias("MerName")
    private String mername;// 商户名（机构名称）
    @XStreamAlias("BankInsCode")
    private String bankinscode;// 银联机构号
    @XStreamAlias("BankName")
    private String bankname;// 开户行名称
    @XStreamAlias("BankAccNo")
    private String bankaccno;// 银行账号
    @XStreamAlias("BankAccName")
    private String bankaccname;// 银行户名
    @XStreamAlias("BankAccType")
    private String bankacctype;// 账号类型
    @XStreamAlias("CertType")
    private String certtype;// 证件类型
    @XStreamAlias("CertNo")
    private String certno;// 证件号码
    @XStreamAlias("Mobile")
    private String mobile;// 联系电话
    @XStreamAlias("Address")
    private String address;// 通讯地址
    @XStreamAlias("Email")
    private String email;// 电子邮箱
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
     * @return the bankinscode
     */
    public String getBankinscode() {
        return bankinscode;
    }
    /**
     * @param bankinscode the bankinscode to set
     */
    public void setBankinscode(String bankinscode) {
        this.bankinscode = bankinscode;
    }
    /**
     * @return the bankname
     */
    public String getBankname() {
        return bankname;
    }
    /**
     * @param bankname the bankname to set
     */
    public void setBankname(String bankname) {
        this.bankname = bankname;
    }
    /**
     * @return the bankaccno
     */
    public String getBankaccno() {
        return bankaccno;
    }
    /**
     * @param bankaccno the bankaccno to set
     */
    public void setBankaccno(String bankaccno) {
        this.bankaccno = bankaccno;
    }
    /**
     * @return the bankaccname
     */
    public String getBankaccname() {
        return bankaccname;
    }
    /**
     * @param bankaccname the bankaccname to set
     */
    public void setBankaccname(String bankaccname) {
        this.bankaccname = bankaccname;
    }
    /**
     * @return the bankacctype
     */
    public String getBankacctype() {
        return bankacctype;
    }
    /**
     * @param bankacctype the bankacctype to set
     */
    public void setBankacctype(String bankacctype) {
        this.bankacctype = bankacctype;
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
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }
    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }
    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    public synchronized String toXML(){
        XStream xstream = new XStream(new DomDriver(null,new XmlFriendlyNameCoder("_-", "_")));  
        xstream.autodetectAnnotations(true);
        String xml = XMLHEAD+xstream.toXML(this);
        Pattern p = Pattern.compile("\\s{2,}|\t|\r|\n");
        Matcher m = p.matcher(xml);
        xml=m.replaceAll("") ;
        return  xml;
    }
    /**
     * @param version
     * @param transdate
     * @param transtime
     * @param serialno
     * @param merid
     * @param mername
     * @param bankinscode
     * @param bankname
     * @param bankaccno
     * @param bankaccname
     * @param bankacctype
     * @param certtype
     * @param certno
     * @param mobile
     * @param address
     * @param email
     */
    public WhiteListBean(String version, String transdate, String transtime,
            String serialno, String merid, String mername, String bankinscode,
            String bankname, String bankaccno, String bankaccname,
            String bankacctype, String certtype, String certno, String mobile,
            String address, String email) {
        super();
        this.version = version;
        this.transdate = transdate;
        this.transtime = transtime;
        this.serialno = serialno;
        this.merid = merid;
        this.mername = mername;
        this.bankinscode = bankinscode;
        this.bankname = bankname;
        this.bankaccno = bankaccno;
        this.bankaccname = bankaccname;
        this.bankacctype = bankacctype;
        this.certtype = certtype;
        this.certno = certno;
        this.mobile = mobile;
        this.address = address;
        this.email = email;
    }
    /**
     * 
     */
    public WhiteListBean() {
        super();
    }
    
    public WhiteListBean(WhiteListMessageBean whiteListMsg){
        this.version = ConsUtil.getInstance().cons.getCmbc_version();
        this.transdate = DateUtil.getCurrentDate();
        this.transtime = DateUtil.getCurrentTime();
        this.serialno = whiteListMsg.getWithholding().getSerialno();
        this.merid = ConsUtil.getInstance().cons.getCmbc_merid();
        this.mername =  ConsUtil.getInstance().cons.getCmbc_mername();
        this.bankinscode = whiteListMsg.getBankinscode();
        this.bankname = whiteListMsg.getBankname();
        this.bankaccno = whiteListMsg.getBankaccno();
        this.bankaccname = whiteListMsg.getBankaccname();
        this.bankacctype = whiteListMsg.getBankacctype();
        this.certtype = whiteListMsg.getCerttype();
        this.certno = whiteListMsg.getCertno();
        this.mobile = whiteListMsg.getMobile();
        this.address = whiteListMsg.getAddress();
        this.email = whiteListMsg.getEmail();
    }
    
}
