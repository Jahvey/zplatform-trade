/* 
 * WhiteListMessageBean.java  
 * 
 * version TODO
 *
 * 2015年11月25日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.cmbc.bean.gateway;

import java.io.Serializable;

import com.zlebank.zplatform.trade.model.TxnsWithholdingModel;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月25日 下午2:00:59
 * @since 
 */
public class WhiteListMessageBean implements Serializable{
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8455881070190652189L;
	private String bankinscode;//银联机构号   
    private String bankname ;//开户行名称   
    private String bankaccno;//银行账号
    private String bankaccname;//银行户名
    private String bankacctype;//账号类型
    private String certtype;//证件类型
    private String certno;//证件号码
    private String mobile;//联系电话
    private String address;//通讯地址
    private String email;//电子邮箱
    
    private TxnsWithholdingModel withholding;
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
    public WhiteListMessageBean(String text){
        this.bankinscode = "01050000";
        this.bankname = "建设银行";
        this.bankaccno = "6217000010053213727";
        this.bankaccname = "喻磊";
        this.bankacctype = "0";
        this.certtype = "ZR01";
        this.certno = "61230119880402031X";
        this.mobile = "13141464942";
        this.address = "";
        this.email = "";
    }
    public WhiteListMessageBean(String bankinscode, String bankname,
            String bankaccno, String bankaccname, String bankacctype,
            String certtype, String certno, String mobile, String address,
            String email) {
        super();
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
    public TxnsWithholdingModel getWithholding() {
        return withholding;
    }
    public void setWithholding(TxnsWithholdingModel withholding) {
        this.withholding = withholding;
    }
    
    

}
