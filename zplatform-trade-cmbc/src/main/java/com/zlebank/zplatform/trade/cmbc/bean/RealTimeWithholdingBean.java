/* 
 * RealTimeWithholdingBean.java  
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
import com.zlebank.zplatform.trade.cmbc.bean.gateway.WithholdingMessageBean;
import com.zlebank.zplatform.trade.utils.ConsUtil;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月25日 上午9:52:25
 * @since
 */
@XStreamAlias("Req")
public class RealTimeWithholdingBean implements Serializable {
    
    public static final String XMLHEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4879231816747512788L;
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
    @XStreamAlias("BizType")
    private String biztype;// 业务类型
    @XStreamAlias("BizNo")
    private String bizno;// 业务号码
    @XStreamAlias("BizObjType")
    private String bizobjtype;// 业务对象类型
    @XStreamAlias("PayerAcc")
    private String payeracc;// 付款人账号
    @XStreamAlias("PayerName")
    private String payername;// 付款人名称
    @XStreamAlias("CardType")
    private String cardtype;// 卡折标志
    @XStreamAlias("PayerBankName")
    private String payerbankname;// 付款行名称
    @XStreamAlias("PayerBankInsCode")
    private String payerbankinscode;// 付款行银联机构号
    @XStreamAlias("PayerBankSettleNo")
    private String payerbanksettleno;// 付款行清算联行号
    @XStreamAlias("PayerBankSwitchNo")
    private String payerbankswitchno;// 付款行同城交换号
    @XStreamAlias("PayerPhone")
    private String payerphone;// 付款人手机号码
    @XStreamAlias("TranAmt")
    private String tranamt;// 交易金额
    @XStreamAlias("Currency")
    private String currency;// 交易币种
    @XStreamAlias("CertType")
    private String certtype;// 开户证件类型
    @XStreamAlias("CertNo")
    private String certno;// 证件号码
    @XStreamAlias("ProvNo")
    private String provno;// 付款省份编码
    @XStreamAlias("CityNo")
    private String cityno;// 付款城市编码
    @XStreamAlias("AgtNo")
    private String agtno;// 协议编号
    @XStreamAlias("Purpose")
    private String purpose;// 用途说明
    @XStreamAlias("Postscript")
    private String postscript;// 附言说明
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
     * @return the biztype
     */
    public String getBiztype() {
        return biztype;
    }
    /**
     * @param biztype the biztype to set
     */
    public void setBiztype(String biztype) {
        this.biztype = biztype;
    }
    /**
     * @return the bizno
     */
    public String getBizno() {
        return bizno;
    }
    /**
     * @param bizno the bizno to set
     */
    public void setBizno(String bizno) {
        this.bizno = bizno;
    }
    /**
     * @return the bizobjtype
     */
    public String getBizobjtype() {
        return bizobjtype;
    }
    /**
     * @param bizobjtype the bizobjtype to set
     */
    public void setBizobjtype(String bizobjtype) {
        this.bizobjtype = bizobjtype;
    }
    /**
     * @return the payeracc
     */
    public String getPayeracc() {
        return payeracc;
    }
    /**
     * @param payeracc the payeracc to set
     */
    public void setPayeracc(String payeracc) {
        this.payeracc = payeracc;
    }
    /**
     * @return the payername
     */
    public String getPayername() {
        return payername;
    }
    /**
     * @param payername the payername to set
     */
    public void setPayername(String payername) {
        this.payername = payername;
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
     * @return the payerbankname
     */
    public String getPayerbankname() {
        return payerbankname;
    }
    /**
     * @param payerbankname the payerbankname to set
     */
    public void setPayerbankname(String payerbankname) {
        this.payerbankname = payerbankname;
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
     * @return the payerbanksettleno
     */
    public String getPayerbanksettleno() {
        return payerbanksettleno;
    }
    /**
     * @param payerbanksettleno the payerbanksettleno to set
     */
    public void setPayerbanksettleno(String payerbanksettleno) {
        this.payerbanksettleno = payerbanksettleno;
    }
    /**
     * @return the payerbankswitchno
     */
    public String getPayerbankswitchno() {
        return payerbankswitchno;
    }
    /**
     * @param payerbankswitchno the payerbankswitchno to set
     */
    public void setPayerbankswitchno(String payerbankswitchno) {
        this.payerbankswitchno = payerbankswitchno;
    }
    /**
     * @return the payerphone
     */
    public String getPayerphone() {
        return payerphone;
    }
    /**
     * @param payerphone the payerphone to set
     */
    public void setPayerphone(String payerphone) {
        this.payerphone = payerphone;
    }
    /**
     * @return the tranamt
     */
    public String getTranamt() {
        return tranamt;
    }
    /**
     * @param tranamt the tranamt to set
     */
    public void setTranamt(String tranamt) {
        this.tranamt = tranamt;
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
     * @return the cityno
     */
    public String getCityno() {
        return cityno;
    }
    /**
     * @param cityno the cityno to set
     */
    public void setCityno(String cityno) {
        this.cityno = cityno;
    }
    /**
     * @return the agtno
     */
    public String getAgtno() {
        return agtno;
    }
    /**
     * @param agtno the agtno to set
     */
    public void setAgtno(String agtno) {
        this.agtno = agtno;
    }
    /**
     * @return the purpose
     */
    public String getPurpose() {
        return purpose;
    }
    /**
     * @param purpose the purpose to set
     */
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
    /**
     * @return the postscript
     */
    public String getPostscript() {
        return postscript;
    }
    /**
     * @param postscript the postscript to set
     */
    public void setPostscript(String postscript) {
        this.postscript = postscript;
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
    /**
     * @param version
     * @param transdate
     * @param transtime
     * @param serialno
     * @param merid
     * @param mername
     * @param biztype
     * @param bizno
     * @param bizobjtype
     * @param payeracc
     * @param payername
     * @param cardtype
     * @param payerbankname
     * @param payerbankinscode
     * @param payerbanksettleno
     * @param payerbankswitchno
     * @param payerphone
     * @param tranamt
     * @param currency
     * @param certtype
     * @param certno
     * @param provno
     * @param cityno
     * @param agtno
     * @param purpose
     * @param postscript
     */
    public RealTimeWithholdingBean(String version, String transdate,
            String transtime, String serialno, String merid, String mername,
            String biztype, String bizno, String bizobjtype, String payeracc,
            String payername, String cardtype, String payerbankname,
            String payerbankinscode, String payerbanksettleno,
            String payerbankswitchno, String payerphone, String tranamt,
            String currency, String certtype, String certno, String provno,
            String cityno, String agtno, String purpose, String postscript) {
        super();
        this.version = version;
        this.transdate = transdate;
        this.transtime = transtime;
        this.serialno = serialno;
        this.merid = merid;
        this.mername = mername;
        this.biztype = biztype;
        this.bizno = bizno;
        this.bizobjtype = bizobjtype;
        this.payeracc = payeracc;
        this.payername = payername;
        this.cardtype = cardtype;
        this.payerbankname = payerbankname;
        this.payerbankinscode = payerbankinscode;
        this.payerbanksettleno = payerbanksettleno;
        this.payerbankswitchno = payerbankswitchno;
        this.payerphone = payerphone;
        this.tranamt = tranamt;
        this.currency = currency;
        this.certtype = certtype;
        this.certno = certno;
        this.provno = provno;
        this.cityno = cityno;
        this.agtno = agtno;
        this.purpose = purpose;
        this.postscript = postscript;
    }
    /**
     * 
     */
    public RealTimeWithholdingBean() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public RealTimeWithholdingBean(WithholdingMessageBean withholdingMsg){
        this.version = ConsUtil.getInstance().cons.getCmbc_version();
        this.transdate = withholdingMsg.getWithholding().getTransdate();
        this.transtime = withholdingMsg.getWithholding().getTranstime();
        this.serialno = withholdingMsg.getWithholding().getSerialno();
        this.merid = ConsUtil.getInstance().cons.getCmbc_merid();
        this.mername = ConsUtil.getInstance().cons.getCmbc_mername();
        this.biztype = withholdingMsg.getBiztype();
        this.bizno = withholdingMsg.getBizno();
        this.bizobjtype = withholdingMsg.getBizobjtype();
        this.payeracc = withholdingMsg.getPayeracc();
        this.payername = withholdingMsg.getPayername();
        this.cardtype = withholdingMsg.getCardtype();
        this.payerbankname = withholdingMsg.getPayerbankname();
        this.payerbankinscode = withholdingMsg.getPayerbankinscode();
        this.payerbanksettleno = withholdingMsg.getPayerbanksettleno();
        this.payerbankswitchno = withholdingMsg.getPayerbankswitchno();
        this.payerphone = withholdingMsg.getPayerphone();
        this.tranamt = withholdingMsg.getTranamt();
        this.currency = withholdingMsg.getCurrency();
        this.certtype = withholdingMsg.getCerttype();
        this.certno = withholdingMsg.getCertno();
        this.provno = withholdingMsg.getProvno();
        this.cityno = withholdingMsg.getCityno();
        this.agtno = withholdingMsg.getAgtno();
        this.purpose = withholdingMsg.getPurpose();
        this.postscript = withholdingMsg.getPostscript();
    }
}
