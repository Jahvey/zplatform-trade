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
import com.zlebank.zplatform.trade.utils.DateUtil;

/**
 * 
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年12月21日 下午12:04:53
 * @since
 */
@XStreamAlias("TRAN_REQ")
public class RealTimeSelfWithholdingBean implements Serializable{
   
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 672914196972492716L;
    public static final String XMLHEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    @XStreamAlias("VERSION")
    private String version;// 版本号
    @XStreamAlias("MCHNT_CD")
    private String mchntCd;// 商户编号
    @XStreamAlias("MCHNT_NAME")
    private String mchntName;// 商户名称
    @XStreamAlias("TRAN_DATE")
    private String tranDate;// 交易日期
    @XStreamAlias("TRAN_TIME")
    private String tranTime;// 交易时间
    @XStreamAlias("TRAN_ID")
    private String tranId;// 合作方流水号
    @XStreamAlias("BUSI_TYPE")
    private String busiType;// 业务类型
    @XStreamAlias("BUSI_NO")
    private String busiNo;// 业务号码
    @XStreamAlias("CURRENCY")
    private String currency;// 交易币种
    @XStreamAlias("ACC_NO")
    private String accNo;// 付款人账户号
    @XStreamAlias("ACC_NAME")
    private String accName;// 付款人账户名
    @XStreamAlias("PAYER_PHONE")
    private String payerPhone;// 付款人手机号
    @XStreamAlias("TRANS_AMT")
    private String transAmt;// 交易金额
    @XStreamAlias("CHK_FLAG")
    private String chkFlag;// 认证检查标志
    @XStreamAlias("CERT_TYPE")
    private String certType;// 证件类型
    @XStreamAlias("CERT_NO")
    private String certNo;// 证件号码
    @XStreamAlias("REMARK")
    private String remark;// 客户流水摘要
    @XStreamAlias("RESV")
    private String resv;// 备用域
    
    
    
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getMchntCd() {
        return mchntCd;
    }
    public void setMchntCd(String mchntCd) {
        this.mchntCd = mchntCd;
    }
    public String getMchntName() {
        return mchntName;
    }
    public void setMchntName(String mchntName) {
        this.mchntName = mchntName;
    }
    public String getTranDate() {
        return tranDate;
    }
    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }
    public String getTranTime() {
        return tranTime;
    }
    public void setTranTime(String tranTime) {
        this.tranTime = tranTime;
    }
    public String getTranId() {
        return tranId;
    }
    public void setTranId(String tranId) {
        this.tranId = tranId;
    }
    public String getBusiType() {
        return busiType;
    }
    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }
    public String getBusiNo() {
        return busiNo;
    }
    public void setBusiNo(String busiNo) {
        this.busiNo = busiNo;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public String getAccNo() {
        return accNo;
    }
    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }
    public String getAccName() {
        return accName;
    }
    public void setAccName(String accName) {
        this.accName = accName;
    }
    public String getPayerPhone() {
        return payerPhone;
    }
    public void setPayerPhone(String payerPhone) {
        this.payerPhone = payerPhone;
    }
    public String getTransAmt() {
        return transAmt;
    }
    public void setTransAmt(String transAmt) {
        this.transAmt = transAmt;
    }
    public String getChkFlag() {
        return chkFlag;
    }
    public void setChkFlag(String chkFlag) {
        this.chkFlag = chkFlag;
    }
    public String getCertType() {
        return certType;
    }
    public void setCertType(String certType) {
        this.certType = certType;
    }
    public String getCertNo() {
        return certNo;
    }
    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getResv() {
        return resv;
    }
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
    public RealTimeSelfWithholdingBean(WithholdingMessageBean withholdingMsg) {
        super();
        this.version = ConsUtil.getInstance().cons.getCmbc_version();
        this.mchntCd = ConsUtil.getInstance().cons.getCmbc_self_merchant();
        this.mchntName = "";
        this.tranDate = DateUtil.getCurrentDate();
        this.tranTime = DateUtil.getCurrentTime();
        this.tranId = withholdingMsg.getWithholding().getSerialno();
        this.busiType = "";
        this.busiNo = "";
        this.currency = "RMB";
        this.accNo = withholdingMsg.getPayeracc();
        this.accName = withholdingMsg.getPayername();
        this.payerPhone = withholdingMsg.getPayerphone();
        this.transAmt = withholdingMsg.getTranamt();
        this.chkFlag = "2";
        this.certType = withholdingMsg.getCerttype();
        this.certNo = withholdingMsg.getCertno();
        this.remark = "代收业务";
        this.resv = "";
    }
    public RealTimeSelfWithholdingBean() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
}
