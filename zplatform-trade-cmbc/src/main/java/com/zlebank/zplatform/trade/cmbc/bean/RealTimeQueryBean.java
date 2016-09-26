/* 
 * RealTimeQueryBean.java  
 * 
 * version TODO
 *
 * 2015年11月10日 
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
 * @date 2015年11月10日 上午9:09:23
 * @since
 */
@XStreamAlias("TRAN_REQ")
public class RealTimeQueryBean implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3064622210304749848L;
    public static final String XMLHEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    private static final String SERVICECODE = "3002";

    @XStreamAlias("COMPANY_ID")
    private String companyId = "";// 合作方id
    @XStreamAlias("MCHNT_CD")
    private String mchntCd = "";// 商户编号
    @XStreamAlias("TRAN_DATE")
    private String tranDate = "";// 交易日期
    @XStreamAlias("TRAN_TIME")
    private String tranTime = "";// 交易时间
    @XStreamAlias("TRAN_ID")
    private String tranId = "";// 渠道流水号
    @XStreamAlias("ORI_TRAN_DATE")
    private String oriTranDate = "";// 原交易日期
    @XStreamAlias("ORI_TRAN_ID")
    private String oriTranId = "";// 原渠道流水号
    @XStreamAlias("RESV")
    private String resv = "";// 备用域
    
    /**
     * @return the companyId
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * @param companyId the companyId to set
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * @return the mchntCd
     */
    public String getMchntCd() {
        return mchntCd;
    }

    /**
     * @param mchntCd the mchntCd to set
     */
    public void setMchntCd(String mchntCd) {
        this.mchntCd = mchntCd;
    }

    /**
     * @return the tranDate
     */
    public String getTranDate() {
        return tranDate;
    }

    /**
     * @param tranDate the tranDate to set
     */
    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    /**
     * @return the tranTime
     */
    public String getTranTime() {
        return tranTime;
    }

    /**
     * @param tranTime the tranTime to set
     */
    public void setTranTime(String tranTime) {
        this.tranTime = tranTime;
    }

    /**
     * @return the tranId
     */
    public String getTranId() {
        return tranId;
    }

    /**
     * @param tranId the tranId to set
     */
    public void setTranId(String tranId) {
        this.tranId = tranId;
    }

    /**
     * @return the oriTranDate
     */
    public String getOriTranDate() {
        return oriTranDate;
    }

    /**
     * @param oriTranDate the oriTranDate to set
     */
    public void setOriTranDate(String oriTranDate) {
        this.oriTranDate = oriTranDate;
    }

    /**
     * @return the oriTranId
     */
    public String getOriTranId() {
        return oriTranId;
    }

    /**
     * @param oriTranId the oriTranId to set
     */
    public void setOriTranId(String oriTranId) {
        this.oriTranId = oriTranId;
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
     * 
     */
    public RealTimeQueryBean() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @param companyId
     * @param mchntCd
     * @param tranDate
     * @param tranTime
     * @param tranId
     * @param oriTranDate
     * @param oriTranId
     * @param resv
     */
    public RealTimeQueryBean(String companyId, String mchntCd, String tranDate,
            String tranTime, String tranId, String oriTranDate,
            String oriTranId, String resv) {
        super();
        this.companyId = companyId;
        this.mchntCd = mchntCd;
        this.tranDate = tranDate;
        this.tranTime = tranTime;
        this.tranId = tranId;
        this.oriTranDate = oriTranDate;
        this.oriTranId = oriTranId;
        this.resv = resv;
    }
    public RealTimeQueryBean( String oriTranDate,String oriTranId) {
        super();
        this.companyId = ConsUtil.getInstance().cons.getCmbc_insteadpay_merid();
        this.mchntCd = "";
        this.tranDate = DateUtil.getCurrentDate();
        this.tranTime = DateUtil.getCurrentTime();
        this.tranId = OrderNumber.getInstance().generateRealTimeInsteadPayQueryOrderNo();
        this.oriTranDate = oriTranDate;
        this.oriTranId = oriTranId;
        this.resv = "";
    }
    public synchronized String toXML() {
        XStream xstream = new XStream(new DomDriver(null,
                new XmlFriendlyNameCoder("_-", "_")));
        // xstream.processAnnotations(RealTimePayBean.class);
        xstream.autodetectAnnotations(true);
        String xml = XMLHEAD + xstream.toXML(this);
        Pattern p = Pattern.compile("\\s{2,}|\t|\r|\n");
        Matcher m = p.matcher(xml);
        xml = m.replaceAll("");
        int xml_length = xml.getBytes().length;
        DecimalFormat df = new DecimalFormat("000000");
        String msgLength = df.format(xml_length);
        String sign = CMBCAESUtils.encodeMD5(xml).toUpperCase();
        String serviceMsg = SERVICECODE + "           ";
        return msgLength + serviceMsg + xml + sign;
    }
}
