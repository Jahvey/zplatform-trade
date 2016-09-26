/* 
 * WithholdingMessageBean.java  
 * 
 * version TODO
 *
 * 2015年11月25日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.cmbc.bean.gateway;

import com.zlebank.zplatform.trade.model.TxnsWithholdingModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月25日 上午10:13:52
 * @since 
 */
public class WithholdingMessageBean {
    private String biztype;//业务类型
    private String bizno;//业务号码
    private String bizobjtype;//业务对象类型  
    private String payeracc;//付款人账号   
    private String payername;//付款人名称   
    private String cardtype;//卡折标志
    private String payerbankname;//付款行名称   
    private String payerbankinscode;//付款行银联机构号
    private String payerbanksettleno;//付款行清算联行号
    private String payerbankswitchno;//付款行同城交换号
    private String payerphone;//付款人手机号码 
    private String tranamt;//交易金额
    private String currency;//交易币种
    private String certtype;//开户证件类型  
    private String certno;//证件号码
    private String provno;//付款省份编码  
    private String cityno;//付款城市编码 
    private String agtno;//协议编号
    private String purpose;//用途说明   
    private String postscript;//附言说明
    
    private TxnsWithholdingModel withholding;
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
    /**
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
    public WithholdingMessageBean(String biztype, String bizno,
            String bizobjtype, String payeracc, String payername,
            String cardtype, String payerbankname, String payerbankinscode,
            String payerbanksettleno, String payerbankswitchno,
            String payerphone, String tranamt, String currency,
            String certtype, String certno, String provno, String cityno,
            String agtno, String purpose, String postscript) {
        super();
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
    public WithholdingMessageBean() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public WithholdingMessageBean(String text) {
        this.biztype = "14900";
        this.bizno = "";
        this.bizobjtype = "00";
        this.payeracc = "6217000010053213727";
        this.payername = "喻磊";
        this.cardtype = "0";
        this.payerbankname = "建设银行";
        this.payerbankinscode = "01050000";
        this.payerbanksettleno = "";
        this.payerbankswitchno = "";
        this.payerphone = "13141464942";
        this.tranamt = "111";
        this.currency = "RMB";
        this.certtype = "ZR01";
        this.certno = "61230119880402031X";
        this.provno = "110000";
        this.cityno = "110105";
        this.agtno = "";
        this.purpose = "测试";
        this.postscript = "";
    }
    
    public WithholdingMessageBean(TxnsWithholdingModel withholding){
        this.biztype = withholding.getBiztype();
        this.bizno = withholding.getBizno();
        this.bizobjtype = withholding.getBizobjtype();
        this.payeracc = withholding.getAccno();
        this.payername = withholding.getAccname();
        this.cardtype = withholding.getCardtype();
        this.payerbankname = withholding.getPayerbankname();
        this.payerbankinscode = withholding.getPayerbankinscode();
        this.payerbanksettleno = "";
        this.payerbankswitchno = "";
        this.payerphone = withholding.getPhone();
        this.tranamt = withholding.getTranamt().longValue()+"";
        this.currency = withholding.getCurrency();
        this.certtype = withholding.getCerttype();
        this.certno = withholding.getCertno();
        this.provno = withholding.getProvno();
        this.cityno = withholding.getCityno();
        this.agtno = "";
        this.purpose = withholding.getPurpose();
        this.postscript = "";
    }
    public TxnsWithholdingModel getWithholding() {
        return withholding;
    }
    public void setWithholding(TxnsWithholdingModel withholding) {
        this.withholding = withholding;
    }
    
}
