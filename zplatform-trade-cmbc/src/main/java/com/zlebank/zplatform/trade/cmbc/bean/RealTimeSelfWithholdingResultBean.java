package com.zlebank.zplatform.trade.cmbc.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年12月21日 下午12:12:53
 * @since
 */
@XStreamAlias("TRAN_RESP")
public class RealTimeSelfWithholdingResultBean {
    
    @XStreamAlias("RESP_TYPE")
    private String respType;// 应答码类型
    @XStreamAlias("RESP_CODE")
    private String respCode;// 应答码
    @XStreamAlias("RESP_MSG")
    private String respMsg;// 应答描述
    @XStreamAlias("MCHNT_CD")
    private String mchntCd;// 商户编号
    @XStreamAlias("TRAN_DATE")
    private String tranDate;// 交易日期
    @XStreamAlias("TRAN_TIME")
    private String tranTime;// 交易时间
    @XStreamAlias("TRAN_ID")
    private String tranId;// 合作方交易流水号
    @XStreamAlias("BANK_TRAN_ID")
    private String bankTranId;// 银行处理流水号
    @XStreamAlias("BANK_TRAN_DATE")
    private String bankTranDate;// 银行交易日期
    @XStreamAlias("BANK_TRAN_TIME")
    private String bankTranTime;// 银行交易时间
    @XStreamAlias("CHARGE_FEE")
    private String chargeFee;// 手续费
    @XStreamAlias("RESV")
    private String resv;// 备用
    public String getRespType() {
        return respType;
    }
    public void setRespType(String respType) {
        this.respType = respType;
    }
    public String getRespCode() {
        return respCode;
    }
    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }
    public String getRespMsg() {
        return respMsg;
    }
    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
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
    public String getBankTranId() {
        return bankTranId;
    }
    public void setBankTranId(String bankTranId) {
        this.bankTranId = bankTranId;
    }
    public String getBankTranDate() {
        return bankTranDate;
    }
    public void setBankTranDate(String bankTranDate) {
        this.bankTranDate = bankTranDate;
    }
    public String getBankTranTime() {
        return bankTranTime;
    }
    public void setBankTranTime(String bankTranTime) {
        this.bankTranTime = bankTranTime;
    }
    public String getChargeFee() {
        return chargeFee;
    }
    public void setChargeFee(String chargeFee) {
        this.chargeFee = chargeFee;
    }
    public String getResv() {
        return resv;
    }
    public void setResv(String resv) {
        this.resv = resv;
    }
    
    
}
