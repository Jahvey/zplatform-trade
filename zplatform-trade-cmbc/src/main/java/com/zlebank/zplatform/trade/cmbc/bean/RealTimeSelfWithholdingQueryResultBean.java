package com.zlebank.zplatform.trade.cmbc.bean;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年12月21日 下午12:28:22
 * @since
 */
@XStreamAlias("TRAN_RESP")
public class RealTimeSelfWithholdingQueryResultBean implements Serializable {

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2285631423424550349L;
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
    private String tranId;// 合作方流水号
    @XStreamAlias("ORI_TRAN_DATE")
    private String oriTranDate;// 原交易日期
    @XStreamAlias("ORI_TRAN_ID")
    private String oriTranId;// 原合作方流水号
    @XStreamAlias("ORI_BANK_TRAN_ID")
    private String oriBankTranId;// 银行处理流水号
    @XStreamAlias("ORI_BANK_TRAN_DATE")
    private String oriBankTranDate;// 银行交易日期
    @XStreamAlias("ORI_BANK_TRAN_TIME")
    private String oriBankTranTime;// 银行交易时间
    @XStreamAlias("ORI_TRANS_AMT")
    private String oriTransAmt;// 原交易金额
    @XStreamAlias("ORI_CHARGE_FEE")
    private String oriChargeFee;// 原交易手续费
    @XStreamAlias("ORI_RESP_TYPE")
    private String oriRespType;// 原交易应答类型
    @XStreamAlias("ORI_RESP_CODE")
    private String oriRespCode;// 原交易应答码
    @XStreamAlias("ORI_RESP_MSG")
    private String oriRespMsg;// 原交易应答描述
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
    public String getOriBankTranId() {
        return oriBankTranId;
    }
    public void setOriBankTranId(String oriBankTranId) {
        this.oriBankTranId = oriBankTranId;
    }
    public String getOriBankTranDate() {
        return oriBankTranDate;
    }
    public void setOriBankTranDate(String oriBankTranDate) {
        this.oriBankTranDate = oriBankTranDate;
    }
    public String getOriBankTranTime() {
        return oriBankTranTime;
    }
    public void setOriBankTranTime(String oriBankTranTime) {
        this.oriBankTranTime = oriBankTranTime;
    }
    public String getOriTransAmt() {
        return oriTransAmt;
    }
    public void setOriTransAmt(String oriTransAmt) {
        this.oriTransAmt = oriTransAmt;
    }
    public String getOriChargeFee() {
        return oriChargeFee;
    }
    public void setOriChargeFee(String oriChargeFee) {
        this.oriChargeFee = oriChargeFee;
    }
    public String getOriRespType() {
        return oriRespType;
    }
    public void setOriRespType(String oriRespType) {
        this.oriRespType = oriRespType;
    }
    public String getOriRespCode() {
        return oriRespCode;
    }
    public void setOriRespCode(String oriRespCode) {
        this.oriRespCode = oriRespCode;
    }
    public String getOriRespMsg() {
        return oriRespMsg;
    }
    public void setOriRespMsg(String oriRespMsg) {
        this.oriRespMsg = oriRespMsg;
    }
    public String getResv() {
        return resv;
    }
    public void setResv(String resv) {
        this.resv = resv;
    }
    
    
    
}
