/* 
 * RiskRateInfoBean.java  
 * 
 * version TODO
 *
 * 2015年9月1日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean.gateway;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年9月1日 上午9:37:20
 * @since 
 */
public class RiskRateInfoBean implements Serializable{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3336383937034478490L;
    @Length(max=3,message="param.error")
    private String shippingFlag;
    @Length(max=6,message="param.error")
    private String shippingCountryCode;
    @Length(max=6,message="param.error")
    private String shippingProvinceCode;
    @Length(max=6,message="param.error")
    private String shippingCityCode;
    @Length(max=6,message="param.error")
    private String shippingDistrictCode;
    @Length(max=256,message="param.error")
    private String shippingStreet;
    @Length(max=4,message="param.error")
    private String commodityCategory;
    @Length(max=256,message="param.error")
    private String commodityName;
    @Length(max=1024,message="param.error")
    private String commodityUrl;
    @Length(max=12,message="param.error")
    private String commodityUnitPrice;
    @Length(max=10,message="param.error")
    private String commodityQty;
    @Length(max=20,message="param.error")
    private String shippingMobile;
    @Length(max=14,message="param.error")
    private String addressModifyTim;
    @Length(max=14,message="param.error")
    private String userRegisterTime;
    @Length(max=14,message="param.error")
    private String orderNameModifyTime;
    @Length(max=128,message="param.error")
    private String userId;
    @Length(max=32,message="param.error")
    private String orderName;
    @Length(max=1,message="param.error")
    private String userFlag;
    @Length(max=14,message="param.error")
    private String mobileModifyTime;
    @Length(max=1,message="param.error")
    private String riskLevel;
    @Length(max=64,message="param.error.merUserId")
    @NotEmpty(message="param.empty.merUserId")
    private String merUserId;
    @Length(max=8,message="param.error")
    private String merUserRegDt;
    @Length(max=256,message="param.error")
    private String merUserEmail;
    /**
     * @return the shippingFlag
     */
    public String getShippingFlag() {
        return shippingFlag;
    }
    /**
     * @param shippingFlag the shippingFlag to set
     */
    public void setShippingFlag(String shippingFlag) {
        this.shippingFlag = shippingFlag;
    }
    /**
     * @return the shippingCountryCode
     */
    public String getShippingCountryCode() {
        return shippingCountryCode;
    }
    /**
     * @param shippingCountryCode the shippingCountryCode to set
     */
    public void setShippingCountryCode(String shippingCountryCode) {
        this.shippingCountryCode = shippingCountryCode;
    }
    /**
     * @return the shippingProvinceCode
     */
    public String getShippingProvinceCode() {
        return shippingProvinceCode;
    }
    /**
     * @param shippingProvinceCode the shippingProvinceCode to set
     */
    public void setShippingProvinceCode(String shippingProvinceCode) {
        this.shippingProvinceCode = shippingProvinceCode;
    }
    /**
     * @return the shippingCityCode
     */
    public String getShippingCityCode() {
        return shippingCityCode;
    }
    /**
     * @param shippingCityCode the shippingCityCode to set
     */
    public void setShippingCityCode(String shippingCityCode) {
        this.shippingCityCode = shippingCityCode;
    }
    /**
     * @return the shippingDistrictCode
     */
    public String getShippingDistrictCode() {
        return shippingDistrictCode;
    }
    /**
     * @param shippingDistrictCode the shippingDistrictCode to set
     */
    public void setShippingDistrictCode(String shippingDistrictCode) {
        this.shippingDistrictCode = shippingDistrictCode;
    }
    /**
     * @return the shippingStreet
     */
    public String getShippingStreet() {
        return shippingStreet;
    }
    /**
     * @param shippingStreet the shippingStreet to set
     */
    public void setShippingStreet(String shippingStreet) {
        this.shippingStreet = shippingStreet;
    }
    /**
     * @return the commodityCategory
     */
    public String getCommodityCategory() {
        return commodityCategory;
    }
    /**
     * @param commodityCategory the commodityCategory to set
     */
    public void setCommodityCategory(String commodityCategory) {
        this.commodityCategory = commodityCategory;
    }
    /**
     * @return the commodityName
     */
    public String getCommodityName() {
        return commodityName;
    }
    /**
     * @param commodityName the commodityName to set
     */
    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }
    /**
     * @return the commodityUrl
     */
    public String getCommodityUrl() {
        return commodityUrl;
    }
    /**
     * @param commodityUrl the commodityUrl to set
     */
    public void setCommodityUrl(String commodityUrl) {
        this.commodityUrl = commodityUrl;
    }
    /**
     * @return the commodityUnitPrice
     */
    public String getCommodityUnitPrice() {
        return commodityUnitPrice;
    }
    /**
     * @param commodityUnitPrice the commodityUnitPrice to set
     */
    public void setCommodityUnitPrice(String commodityUnitPrice) {
        this.commodityUnitPrice = commodityUnitPrice;
    }
    /**
     * @return the commodityQty
     */
    public String getCommodityQty() {
        return commodityQty;
    }
    /**
     * @param commodityQty the commodityQty to set
     */
    public void setCommodityQty(String commodityQty) {
        this.commodityQty = commodityQty;
    }
    /**
     * @return the shippingMobile
     */
    public String getShippingMobile() {
        return shippingMobile;
    }
    /**
     * @param shippingMobile the shippingMobile to set
     */
    public void setShippingMobile(String shippingMobile) {
        this.shippingMobile = shippingMobile;
    }
    /**
     * @return the addressModifyTim
     */
    public String getAddressModifyTim() {
        return addressModifyTim;
    }
    /**
     * @param addressModifyTim the addressModifyTim to set
     */
    public void setAddressModifyTim(String addressModifyTim) {
        this.addressModifyTim = addressModifyTim;
    }
    /**
     * @return the userRegisterTime
     */
    public String getUserRegisterTime() {
        return userRegisterTime;
    }
    /**
     * @param userRegisterTime the userRegisterTime to set
     */
    public void setUserRegisterTime(String userRegisterTime) {
        this.userRegisterTime = userRegisterTime;
    }
    /**
     * @return the orderNameModifyTime
     */
    public String getOrderNameModifyTime() {
        return orderNameModifyTime;
    }
    /**
     * @param orderNameModifyTime the orderNameModifyTime to set
     */
    public void setOrderNameModifyTime(String orderNameModifyTime) {
        this.orderNameModifyTime = orderNameModifyTime;
    }
    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    /**
     * @return the orderName
     */
    public String getOrderName() {
        return orderName;
    }
    /**
     * @param orderName the orderName to set
     */
    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }
    /**
     * @return the userFlag
     */
    public String getUserFlag() {
        return userFlag;
    }
    /**
     * @param userFlag the userFlag to set
     */
    public void setUserFlag(String userFlag) {
        this.userFlag = userFlag;
    }
    /**
     * @return the mobileModifyTime
     */
    public String getMobileModifyTime() {
        return mobileModifyTime;
    }
    /**
     * @param mobileModifyTime the mobileModifyTime to set
     */
    public void setMobileModifyTime(String mobileModifyTime) {
        this.mobileModifyTime = mobileModifyTime;
    }
    /**
     * @return the riskLevel
     */
    public String getRiskLevel() {
        return riskLevel;
    }
    /**
     * @param riskLevel the riskLevel to set
     */
    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }
    /**
     * @return the merUserId
     */
    public String getMerUserId() {
        return merUserId;
    }
    /**
     * @param merUserId the merUserId to set
     */
    public void setMerUserId(String merUserId) {
        this.merUserId = merUserId;
    }
    /**
     * @return the merUserRegDt
     */
    public String getMerUserRegDt() {
        return merUserRegDt;
    }
    /**
     * @param merUserRegDt the merUserRegDt to set
     */
    public void setMerUserRegDt(String merUserRegDt) {
        this.merUserRegDt = merUserRegDt;
    }
    /**
     * @return the merUserEmail
     */
    public String getMerUserEmail() {
        return merUserEmail;
    }
    /**
     * @param merUserEmail the merUserEmail to set
     */
    public void setMerUserEmail(String merUserEmail) {
        this.merUserEmail = merUserEmail;
    }
    /**
     * @param shippingFlag
     * @param shippingCountryCode
     * @param shippingProvinceCode
     * @param shippingCityCode
     * @param shippingDistrictCode
     * @param shippingStreet
     * @param commodityCategory
     * @param commodityName
     * @param commodityUrl
     * @param commodityUnitPrice
     * @param commodityQty
     * @param shippingMobile
     * @param addressModifyTim
     * @param userRegisterTime
     * @param orderNameModifyTime
     * @param userId
     * @param orderName
     * @param userFlag
     * @param mobileModifyTime
     * @param riskLevel
     * @param merUserId
     * @param merUserRegDt
     * @param merUserEmail
     */
    public RiskRateInfoBean(String shippingFlag, String shippingCountryCode,
            String shippingProvinceCode, String shippingCityCode,
            String shippingDistrictCode, String shippingStreet,
            String commodityCategory, String commodityName,
            String commodityUrl, String commodityUnitPrice,
            String commodityQty, String shippingMobile,
            String addressModifyTim, String userRegisterTime,
            String orderNameModifyTime, String userId, String orderName,
            String userFlag, String mobileModifyTime, String riskLevel,
            String merUserId, String merUserRegDt, String merUserEmail) {
        super();
        this.shippingFlag = shippingFlag;
        this.shippingCountryCode = shippingCountryCode;
        this.shippingProvinceCode = shippingProvinceCode;
        this.shippingCityCode = shippingCityCode;
        this.shippingDistrictCode = shippingDistrictCode;
        this.shippingStreet = shippingStreet;
        this.commodityCategory = commodityCategory;
        this.commodityName = commodityName;
        this.commodityUrl = commodityUrl;
        this.commodityUnitPrice = commodityUnitPrice;
        this.commodityQty = commodityQty;
        this.shippingMobile = shippingMobile;
        this.addressModifyTim = addressModifyTim;
        this.userRegisterTime = userRegisterTime;
        this.orderNameModifyTime = orderNameModifyTime;
        this.userId = userId;
        this.orderName = orderName;
        this.userFlag = userFlag;
        this.mobileModifyTime = mobileModifyTime;
        this.riskLevel = riskLevel;
        this.merUserId = merUserId;
        this.merUserRegDt = merUserRegDt;
        this.merUserEmail = merUserEmail;
    }
    /**
     * 
     */
    public RiskRateInfoBean() {
        
    }
   
    
    
}


