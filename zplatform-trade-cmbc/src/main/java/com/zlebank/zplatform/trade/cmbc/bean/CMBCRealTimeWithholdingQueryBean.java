/* 
 * CMBCRealTimeWithholdingQueryBean.java  
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
 * @date 2015年11月26日 下午4:47:11
 * @since
 */
@XStreamAlias("TRAN_RESP")
public class CMBCRealTimeWithholdingQueryBean implements Serializable {
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1056827190970564322L;
	public static final String XMLHEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    private static final String SERVICECODE = "130073001";
    @XStreamAlias("COMPANY_ID")
    private String company_id;// 合作方id
    @XStreamAlias("MCHNT_CD")
    private String mchnt_cd;// 商户编号
    @XStreamAlias("TRAN_DATE")
    private String tran_date;// 交易日期
    @XStreamAlias("TRAN_TIME")
    private String tran_time;// 交易时间
    @XStreamAlias("TRAN_ID")
    private String tran_id;// 渠道流水号
    @XStreamAlias("ORI_TRAN_DATE")
    private String ori_tran_date;// 原交易日期
    @XStreamAlias("ORI_TRAN_ID")
    private String ori_tran_id;// 原渠道流水号
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
     * @return the ori_tran_date
     */
    public String getOri_tran_date() {
        return ori_tran_date;
    }
    /**
     * @param ori_tran_date the ori_tran_date to set
     */
    public void setOri_tran_date(String ori_tran_date) {
        this.ori_tran_date = ori_tran_date;
    }
    /**
     * @return the ori_tran_id
     */
    public String getOri_tran_id() {
        return ori_tran_id;
    }
    /**
     * @param ori_tran_id the ori_tran_id to set
     */
    public void setOri_tran_id(String ori_tran_id) {
        this.ori_tran_id = ori_tran_id;
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
    public CMBCRealTimeWithholdingQueryBean() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * @param company_id
     * @param mchnt_cd
     * @param tran_date
     * @param tran_time
     * @param tran_id
     * @param ori_tran_date
     * @param ori_tran_id
     * @param resv
     */
    public CMBCRealTimeWithholdingQueryBean(String company_id, String mchnt_cd,
            String tran_date, String tran_time, String tran_id,
            String ori_tran_date, String ori_tran_id, String resv) {
        super();
        this.company_id = company_id;
        this.mchnt_cd = mchnt_cd;
        this.tran_date = tran_date;
        this.tran_time = tran_time;
        this.tran_id = tran_id;
        this.ori_tran_date = ori_tran_date;
        this.ori_tran_id = ori_tran_id;
        this.resv = resv;
    }
    public CMBCRealTimeWithholdingQueryBean(String ori_tran_date, String ori_tran_id) {
        super();
        this.company_id = ConsUtil.getInstance().cons.getCmbc_self_merid();
        this.mchnt_cd = ConsUtil.getInstance().cons.getCmbc_self_merchant();
        this.tran_date = DateUtil.getCurrentDate();
        this.tran_time = DateUtil.getCurrentTime();
        this.tran_id = OrderNumber.getInstance().generateRealTimeWithholdingQueryCMBCSelf();
        this.ori_tran_date = ori_tran_date;
        this.ori_tran_id = ori_tran_id;
        this.resv = "";
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
