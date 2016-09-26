/* 
 * ConsumeOrderBean.java  
 * 
 * version TODO
 *
 * 2015年8月24日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean.ecitic;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年8月24日 上午10:16:07
 * @since
 */
public class ConsumeOrderBean extends BaseBean {
    @NotEmpty(message="param.empty")
    private String orderamt;
    @NotEmpty(message="param.empty")
    private String ordercur="156";
    @NotEmpty(message="param.empty")
    private String orderdate;
    @NotEmpty(message="param.empty")
    private String ordertime;
    @NotEmpty(message="param.empty")
    private String invalidtime;
    @NotEmpty(message="param.empty")
    private String supptcardtype;
    @NotEmpty(message="param.empty")
    private String risklevel;
    @NotEmpty(message="param.empty")
    private String rsptype;
    @NotEmpty(message="param.empty")
    private String backurl;
    private String orderbnf;
    private String safeurl;
    private String fronturl;
    private String shpflg;
    @NotEmpty(message="param.empty")
    private String commodityno;
    @NotEmpty(message="param.empty")
    private String commoditycategory;
    @NotEmpty(message="param.empty")
    private String commodityname;
    private String commodityurl;
    @NotEmpty(message="param.empty")
    private String commodityunitprice;
    @NotEmpty(message="param.empty")
    private String commodityqty;
    private String shpcrycode;
    private String shpprvcode;
    private String shpcitycode;
    private String shpdstcode;
    private String shpaddr;
    private String mernote;
    private String reqreserved;
    private String sysreserved;
    
    /**
     * @return the orderamt
     */
    public String getOrderamt() {
        return orderamt;
    }
    /**
     * @param orderamt the orderamt to set
     */
    public void setOrderamt(String orderamt) {
        this.orderamt = orderamt;
    }
    /**
     * @return the ordercur
     */
    public String getOrdercur() {
        return ordercur;
    }
    /**
     * @param ordercur the ordercur to set
     */
    public void setOrdercur(String ordercur) {
        this.ordercur = ordercur;
    }
    /**
     * @return the orderdate
     */
    public String getOrderdate() {
        return orderdate;
    }
    /**
     * @param orderdate the orderdate to set
     */
    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }
    /**
     * @return the ordertime
     */
    public String getOrdertime() {
        return ordertime;
    }
    /**
     * @param ordertime the ordertime to set
     */
    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }
    /**
     * @return the invalidtime
     */
    public String getInvalidtime() {
        return invalidtime;
    }
    /**
     * @param invalidtime the invalidtime to set
     */
    public void setInvalidtime(String invalidtime) {
        this.invalidtime = invalidtime;
    }
    /**
     * @return the supptcardtype
     */
    public String getSupptcardtype() {
        return supptcardtype;
    }
    /**
     * @param supptcardtype the supptcardtype to set
     */
    public void setSupptcardtype(String supptcardtype) {
        this.supptcardtype = supptcardtype;
    }
    /**
     * @return the risklevel
     */
    public String getRisklevel() {
        return risklevel;
    }
    /**
     * @param risklevel the risklevel to set
     */
    public void setRisklevel(String risklevel) {
        this.risklevel = risklevel;
    }
    /**
     * @return the rsptype
     */
    public String getRsptype() {
        return rsptype;
    }
    /**
     * @param rsptype the rsptype to set
     */
    public void setRsptype(String rsptype) {
        this.rsptype = rsptype;
    }
    /**
     * @return the backurl
     */
    public String getBackurl() {
        return backurl;
    }
    /**
     * @param backurl the backurl to set
     */
    public void setBackurl(String backurl) {
        this.backurl = backurl;
    }
    /**
     * @return the orderbnf
     */
    public String getOrderbnf() {
        return orderbnf;
    }
    /**
     * @param orderbnf the orderbnf to set
     */
    public void setOrderbnf(String orderbnf) {
        this.orderbnf = orderbnf;
    }
    /**
     * @return the safeurl
     */
    public String getSafeurl() {
        return safeurl;
    }
    /**
     * @param safeurl the safeurl to set
     */
    public void setSafeurl(String safeurl) {
        this.safeurl = safeurl;
    }
    /**
     * @return the fronturl
     */
    public String getFronturl() {
        return fronturl;
    }
    /**
     * @param fronturl the fronturl to set
     */
    public void setFronturl(String fronturl) {
        this.fronturl = fronturl;
    }
    /**
     * @return the shpflg
     */
    public String getShpflg() {
        return shpflg;
    }
    /**
     * @param shpflg the shpflg to set
     */
    public void setShpflg(String shpflg) {
        this.shpflg = shpflg;
    }
    /**
     * @return the commodityno
     */
    public String getCommodityno() {
        return commodityno;
    }
    /**
     * @param commodityno the commodityno to set
     */
    public void setCommodityno(String commodityno) {
        this.commodityno = commodityno;
    }
    /**
     * @return the commoditycategory
     */
    public String getCommoditycategory() {
        return commoditycategory;
    }
    /**
     * @param commoditycategory the commoditycategory to set
     */
    public void setCommoditycategory(String commoditycategory) {
        this.commoditycategory = commoditycategory;
    }
    /**
     * @return the commodityname
     */
    public String getCommodityname() {
        return commodityname;
    }
    /**
     * @param commodityname the commodityname to set
     */
    public void setCommodityname(String commodityname) {
        this.commodityname = commodityname;
    }
    /**
     * @return the commodityurl
     */
    public String getCommodityurl() {
        return commodityurl;
    }
    /**
     * @param commodityurl the commodityurl to set
     */
    public void setCommodityurl(String commodityurl) {
        this.commodityurl = commodityurl;
    }
    /**
     * @return the commodityunitprice
     */
    public String getCommodityunitprice() {
        return commodityunitprice;
    }
    /**
     * @param commodityunitprice the commodityunitprice to set
     */
    public void setCommodityunitprice(String commodityunitprice) {
        this.commodityunitprice = commodityunitprice;
    }
    /**
     * @return the commodityqty
     */
    public String getCommodityqty() {
        return commodityqty;
    }
    /**
     * @param commodityqty the commodityqty to set
     */
    public void setCommodityqty(String commodityqty) {
        this.commodityqty = commodityqty;
    }
    /**
     * @return the shpcrycode
     */
    public String getShpcrycode() {
        return shpcrycode;
    }
    /**
     * @param shpcrycode the shpcrycode to set
     */
    public void setShpcrycode(String shpcrycode) {
        this.shpcrycode = shpcrycode;
    }
    /**
     * @return the shpprvcode
     */
    public String getShpprvcode() {
        return shpprvcode;
    }
    /**
     * @param shpprvcode the shpprvcode to set
     */
    public void setShpprvcode(String shpprvcode) {
        this.shpprvcode = shpprvcode;
    }
    /**
     * @return the shpcitycode
     */
    public String getShpcitycode() {
        return shpcitycode;
    }
    /**
     * @param shpcitycode the shpcitycode to set
     */
    public void setShpcitycode(String shpcitycode) {
        this.shpcitycode = shpcitycode;
    }
    /**
     * @return the shpdstcode
     */
    public String getShpdstcode() {
        return shpdstcode;
    }
    /**
     * @param shpdstcode the shpdstcode to set
     */
    public void setShpdstcode(String shpdstcode) {
        this.shpdstcode = shpdstcode;
    }
    /**
     * @return the shpaddr
     */
    public String getShpaddr() {
        return shpaddr;
    }
    /**
     * @param shpaddr the shpaddr to set
     */
    public void setShpaddr(String shpaddr) {
        this.shpaddr = shpaddr;
    }
    /**
     * @return the mernote
     */
    public String getMernote() {
        return mernote;
    }
    /**
     * @param mernote the mernote to set
     */
    public void setMernote(String mernote) {
        this.mernote = mernote;
    }
    /**
     * @return the reqreserved
     */
    public String getReqreserved() {
        return reqreserved;
    }
    /**
     * @param reqreserved the reqreserved to set
     */
    public void setReqreserved(String reqreserved) {
        this.reqreserved = reqreserved;
    }
    /**
     * @return the sysreserved
     */
    public String getSysreserved() {
        return sysreserved;
    }
    /**
     * @param sysreserved the sysreserved to set
     */
    public void setSysreserved(String sysreserved) {
        this.sysreserved = sysreserved;
    }
    
    

}
