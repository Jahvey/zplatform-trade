/* 
 * PojoMerchWhiteList.java  
 * 
 * version v1.0
 *
 * 2015年12月14日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * Class Description
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年12月14日 下午12:11:28
 * @since 
 */
@Entity
@Table(name="T_MERCH_WHITE_LIST")
public class PojoMerchWhiteList {
    /**标识**/
    private Long id;
    /**商户会员号**/
    private String merchId;
    /**银行卡号**/
    private String cardNo;
    /**持卡人姓名**/
    private String accName;
    /**0:未通过实名认证（默认状态）1:通过实名认证9:无效信息**/
    private String status;
    /**创建人**/
    private Long inuser;
    /**创建时间**/
    private Date intime;
    /**修改人**/
    private Long upuser;
    /**修改时间**/
    private Date uptime;
    /**备注**/
    private String notes;
    @GenericGenerator(name = "id_gen", strategy = "enhanced-table", parameters = {
            @Parameter(name = "table_name", value = "T_C_PRIMAY_KEY"),
            @Parameter(name = "value_column_name", value = "NEXT_ID"),
            @Parameter(name = "segment_column_name", value = "KEY_NAME"),
            @Parameter(name = "segment_value", value = "MERCH_WHITE_LIST_ID"),
            @Parameter(name = "increment_size", value = "1"),
            @Parameter(name = "optimizer", value = "pooled-lo") })
    @Id
    @GeneratedValue(generator = "id_gen")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Column(name = "MERCH_ID")
    public String getMerchId() {
        return merchId;
    }
    public void setMerchId(String merchId) {
        this.merchId = merchId;
    }
    @Column(name = "CARD_NO")
    public String getCardNo() {
        return cardNo;
    }
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
    @Column(name = "ACC_NAME")
    public String getAccName() {
        return accName;
    }
    public void setAccName(String accName) {
        this.accName = accName;
    }
    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    @Column(name = "INUSER")
    public Long getInuser() {
        return inuser;
    }
    public void setInuser(Long inuser) {
        this.inuser = inuser;
    }
    @Column(name = "INTIME")
    public Date getIntime() {
        return intime;
    }
    public void setIntime(Date intime) {
        this.intime = intime;
    }
    @Column(name = "UPUSER")
    public Long getUpuser() {
        return upuser;
    }
    public void setUpuser(Long upuser) {
        this.upuser = upuser;
    }
    @Column(name = "UPTIME")
    public Date getUptime() {
        return uptime;
    }
    public void setUptime(Date uptime) {
        this.uptime = uptime;
    }
    @Column(name = "NOTES")
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

}
