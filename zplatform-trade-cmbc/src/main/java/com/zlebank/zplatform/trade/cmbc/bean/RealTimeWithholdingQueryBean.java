/* 
 * RealNameAuthQueryBean.java  
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
import com.zlebank.zplatform.trade.model.TxnsWithholdingModel;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.OrderNumber;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月25日 下午3:24:14
 * @since 
 */
@XStreamAlias("Req")
public class RealTimeWithholdingQueryBean implements Serializable{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3874847700977242862L;
    public static final String XMLHEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    @XStreamAlias("Version")
    private String version ;//版本号 
    @XStreamAlias("TransDate")
    private String transdate;//交易日期
    @XStreamAlias("TransTime")
    private String transtime  ;//交易时间
    @XStreamAlias("SerialNo")
    private String serialno;//流水号 
    @XStreamAlias("MerId")
    private String merid ;//商户号 
    @XStreamAlias("OriTransDate")
    private String oritransdate;//原交易日期   
    @XStreamAlias("OriReqSerialNo")
    private String orireqserialno ;//原交易流水号  
    @XStreamAlias("Resv")
    private String resv ;//备用域 
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
     * @return the oritransdate
     */
    public String getOritransdate() {
        return oritransdate;
    }
    /**
     * @param oritransdate the oritransdate to set
     */
    public void setOritransdate(String oritransdate) {
        this.oritransdate = oritransdate;
    }
    /**
     * @return the orireqserialno
     */
    public String getOrireqserialno() {
        return orireqserialno;
    }
    /**
     * @param orireqserialno the orireqserialno to set
     */
    public void setOrireqserialno(String orireqserialno) {
        this.orireqserialno = orireqserialno;
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
     * @param version
     * @param transdate
     * @param transtime
     * @param serialno
     * @param merid
     * @param oritransdate
     * @param orireqserialno
     * @param resv
     */
    public RealTimeWithholdingQueryBean(
            String oritransdate, String orireqserialno) {
        super();
        this.version = ConsUtil.getInstance().cons.getCmbc_version();
        this.transdate = DateUtil.getCurrentDate();
        this.transtime = DateUtil.getCurrentTime();
        this.serialno = OrderNumber.getInstance().generateWithholdingQueryOrderNo();
        this.merid = ConsUtil.getInstance().cons.getCmbc_merid();
        this.oritransdate = oritransdate;
        this.orireqserialno = orireqserialno;
    }
    public RealTimeWithholdingQueryBean(TxnsWithholdingModel withholding) {
        super();
        this.version = ConsUtil.getInstance().cons.getCmbc_version();
        this.transdate = withholding.getTransdate();
        this.transtime = withholding.getTranstime();
        this.serialno = withholding.getSerialno();
        this.merid = ConsUtil.getInstance().cons.getCmbc_merid();
        this.oritransdate = withholding.getOritransdate();
        this.orireqserialno = withholding.getOrireqserialno();
    }
    /**
     * 
     */
    public RealTimeWithholdingQueryBean() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * @param version
     * @param transdate
     * @param transtime
     * @param serialno
     * @param merid
     * @param oritransdate
     * @param orireqserialno
     * @param resv
     */
    public RealTimeWithholdingQueryBean(String version, String transdate,
            String transtime, String serialno, String merid,
            String oritransdate, String orireqserialno, String resv) {
        super();
        this.version = version;
        this.transdate = transdate;
        this.transtime = transtime;
        this.serialno = serialno;
        this.merid = merid;
        this.oritransdate = oritransdate;
        this.orireqserialno = orireqserialno;
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
