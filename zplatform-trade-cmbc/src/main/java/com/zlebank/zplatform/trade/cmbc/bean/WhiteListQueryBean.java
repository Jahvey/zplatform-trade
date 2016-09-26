/* 
 * WhiteListQueryBean.java  
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
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.OrderNumber;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月25日 下午5:21:41
 * @since 
 */
@XStreamAlias("Req")
public class WhiteListQueryBean implements Serializable{
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3022313794727746603L;
	public static final String XMLHEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    @XStreamAlias("Version")
    private String version;//版本号 
    @XStreamAlias("TransDate")
    private String transdate ;//交易日期
    @XStreamAlias("TransTime")
    private String transtime ;//交易时间
    @XStreamAlias("SerialNo")
    private String serialno ;//流水号
    @XStreamAlias("MerId")
    private String merid;//商户号
    @XStreamAlias("BankAccNo")
    private String bankaccno;//银行账号
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
     * 
     */
    public WhiteListQueryBean() {
        super();
    }
    /**
     * @param version
     * @param transdate
     * @param transtime
     * @param serialno
     * @param merid
     * @param bankaccno
     */
    public WhiteListQueryBean(String version, String transdate,
            String transtime, String serialno, String merid, String bankaccno) {
        super();
        this.version = version;
        this.transdate = transdate;
        this.transtime = transtime;
        this.serialno = serialno;
        this.merid = merid;
        this.bankaccno = bankaccno;
    }
    
    public WhiteListQueryBean(String bankaccno) {
        super();
        this.version = ConsUtil.getInstance().cons.getCmbc_version();
        this.transdate = DateUtil.getCurrentDate();
        this.transtime = DateUtil.getCurrentTime();
        this.serialno = OrderNumber.getInstance().generateWhiteListQueryOrderNo();
        this.merid = ConsUtil.getInstance().cons.getCmbc_merid();
        this.bankaccno = bankaccno;
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
    
}
