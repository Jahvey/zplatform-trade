/* 
 * PojoAppUpdate.java  
 * 
 * version TODO
 *
 * 2016年6月24日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.model;

import java.io.Serializable;

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
 * @author guojia
 * @version
 * @date 2016年6月24日 下午4:07:00
 * @since 
 */
@Entity
@Table(name = "T_APP_UPDATE")
public class PojoAppUpdate implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6511221159252877212L;
	private Long id;
	private String appVersion;
	private String appChannelId;
	private String forceUpdate;
	private String dlUrl;
	private String reviewVersion;
	private String reviewUrl;
	private String note;
	/**
	 * @return the id
	 */
	 @GenericGenerator(name = "id_gen", strategy = "enhanced-table", parameters = {
	            @Parameter(name = "table_name", value = "T_C_PRIMAY_KEY"),
	            @Parameter(name = "value_column_name", value = "NEXT_ID"),
	            @Parameter(name = "segment_column_name", value = "KEY_NAME"),
	            @Parameter(name = "segment_value", value = "APP_UPDATE_ID"),
	            @Parameter(name = "increment_size", value = "1"),
	            @Parameter(name = "optimizer", value = "pooled-lo") })
    @Id
    @GeneratedValue(generator = "id_gen")
    @Column(name = "ID")
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the appVersion
	 */
	@Column(name = "APP_VERSION")
	public String getAppVersion() {
		return appVersion;
	}
	/**
	 * @param appVersion the appVersion to set
	 */
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	/**
	 * @return the appChannelId
	 */
	@Column(name = "APP_CHANNEL_ID")
	public String getAppChannelId() {
		return appChannelId;
	}
	/**
	 * @param appChannelId the appChannelId to set
	 */
	public void setAppChannelId(String appChannelId) {
		this.appChannelId = appChannelId;
	}
	/**
	 * @return the forceUpdate
	 */
	@Column(name = "FORCE_UPDATE")
	public String getForceUpdate() {
		return forceUpdate;
	}
	/**
	 * @param forceUpdate the forceUpdate to set
	 */
	public void setForceUpdate(String forceUpdate) {
		this.forceUpdate = forceUpdate;
	}
	/**
	 * @return the dlUrl
	 */
	@Column(name = "DL_URL")
	public String getDlUrl() {
		return dlUrl;
	}
	/**
	 * @param dlUrl the dlUrl to set
	 */
	public void setDlUrl(String dlUrl) {
		this.dlUrl = dlUrl;
	}
	/**
	 * @return the reviewVersion
	 */
	@Column(name = "REVIEW_VERSION")
	public String getReviewVersion() {
		return reviewVersion;
	}
	/**
	 * @param reviewVersion the reviewVersion to set
	 */
	public void setReviewVersion(String reviewVersion) {
		this.reviewVersion = reviewVersion;
	}
	/**
	 * @return the reviewUrl
	 */
	@Column(name = "REVIEW_URL")
	public String getReviewUrl() {
		return reviewUrl;
	}
	/**
	 * @param reviewUrl the reviewUrl to set
	 */
	public void setReviewUrl(String reviewUrl) {
		this.reviewUrl = reviewUrl;
	}
	/**
	 * @return the note
	 */
	@Column(name = "NOTE")
	public String getNote() {
		return note;
	}
	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
	
	
	

}
