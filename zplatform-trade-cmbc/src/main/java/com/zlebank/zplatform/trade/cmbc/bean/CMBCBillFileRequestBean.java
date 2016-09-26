/* 
 * CMBCBillFileRequestBean.java  
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
import com.zlebank.zplatform.trade.cmbc.security.CMBCAESUtils;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月26日 下午5:18:33
 * @since 
 */
@XStreamAlias("TRAN_REQ")
public class CMBCBillFileRequestBean implements Serializable{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 8683725297011920703L;
    public static final String XMLHEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    private static final String SERVICECODE = "130073003";
    @XStreamAlias("COMPANY_ID")
    private String company_id  ;//合作方id
    @XStreamAlias("REC_DATE")
    private String rec_date    ;//对账日期
    
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
     * @return the rec_date
     */
    public String getRec_date() {
        return rec_date;
    }

    /**
     * @param rec_date the rec_date to set
     */
    public void setRec_date(String rec_date) {
        this.rec_date = rec_date;
    }

    /**
     * @param company_id
     * @param rec_date
     */
    public CMBCBillFileRequestBean(String company_id, String rec_date) {
        super();
        this.company_id = company_id;
        this.rec_date = rec_date;
    }

    /**
     * 
     */
    public CMBCBillFileRequestBean() {
        super();
        // TODO Auto-generated constructor stub
    }

    public synchronized String toXML(){
        XStream xstream = new XStream(new DomDriver(null,new XmlFriendlyNameCoder("_-", "_")));  
        xstream.autodetectAnnotations(true);
        String xml = XMLHEAD+xstream.toXML(this);
        Pattern p = Pattern.compile("\\s{2,}|\t|\r|\n");
        Matcher m = p.matcher(xml);
        xml=m.replaceAll("") ;
        int xml_length = xml.getBytes().length;
        DecimalFormat df = new DecimalFormat("000000");
        String msgLength = df.format(xml_length);
        String sign = CMBCAESUtils.encodeSelfWithholdingMD5(xml).toUpperCase();
        String serviceMsg = SERVICECODE+"      ";
        return  msgLength+serviceMsg+xml+sign;
    }
}
