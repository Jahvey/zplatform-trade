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
 * 
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年12月21日 下午12:17:59
 * @since
 */
@XStreamAlias("TRAN_REQ")
public class RealTimeSelfWithholdingQueryBean implements Serializable {
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1535787419070561583L;
    public static final String XMLHEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    @XStreamAlias("VERSION")
    private String version;// 版本号
    @XStreamAlias("MCHNT_CD")
    private String mchntCd;// 商户编号
    @XStreamAlias("TRAN_DATE")
    private String tranDate;// 交易日期
    @XStreamAlias("TRAN_TIME")
    private String tranTime;// 交易时间
    @XStreamAlias("TRAN_ID")
    private String tranId;// 合作方流水号
    @XStreamAlias("ORI_TRAN_DATE")
    private String oriTranDate;// 原交易日期
    @XStreamAlias("ORI_TRAN_ID")
    private String oriTranId;// 原合作方流水号
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
    public String getOriTranDate() {
        return oriTranDate;
    }
    public void setOriTranDate(String oriTranDate) {
        this.oriTranDate = oriTranDate;
    }
    public String getOriTranId() {
        return oriTranId;
    }
    public void setOriTranId(String oriTranId) {
        this.oriTranId = oriTranId;
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
    
    public RealTimeSelfWithholdingQueryBean(
            String oritransdate, String orireqserialno) {
        super();
        this.version = ConsUtil.getInstance().cons.getCmbc_version();
        this.tranDate = DateUtil.getCurrentDate();
        this.tranTime = DateUtil.getCurrentTime();
        this.tranId = OrderNumber.getInstance().generateWithholdingQueryOrderNo();
        this.mchntCd = ConsUtil.getInstance().cons.getCmbc_self_merchant();
        this.oriTranDate = oritransdate;
        this.oriTranId = orireqserialno;
    }
    public RealTimeSelfWithholdingQueryBean(
           TxnsWithholdingModel withholding) {
        super();
        this.version = ConsUtil.getInstance().cons.getCmbc_version();
        this.tranDate = DateUtil.getCurrentDate();
        this.tranTime = DateUtil.getCurrentTime();
        this.tranId = withholding.getSerialno();
        this.mchntCd = ConsUtil.getInstance().cons.getCmbc_self_merchant();
        this.oriTranDate = withholding.getOritransdate();
        this.oriTranId = withholding.getOrireqserialno();
    }
}
