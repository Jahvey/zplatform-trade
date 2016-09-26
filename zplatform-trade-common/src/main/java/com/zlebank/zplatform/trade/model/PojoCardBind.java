/* 
 * PojoCardBind.java  
 * 
 * version TODO
 *
 * 2016年6月22日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年6月22日 下午4:11:24
 * @since 
 */
@Entity
@Table(name = "T_CARD_BIND")
public class PojoCardBind implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -9056064113431145143L;
	private Long tid;
    private Long cardId;
    private String chnlCode;
    private String bindId;
	/**
	 * @return the tid
	 */
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seq_cardbind_id") 
    @SequenceGenerator(name="seq_cardbind_id",sequenceName="SEQ_T_CARD_BIND_TID",allocationSize=1)
    @Column(name = "TID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getTid() {
		return tid;
	}
	/**
	 * @param tid the tid to set
	 */
	public void setTid(Long tid) {
		this.tid = tid;
	}
	/**
	 * @return the cardId
	 */
	@Column(name = "CARDID")
	public Long getCardId() {
		return cardId;
	}
	/**
	 * @param cardId the cardId to set
	 */
	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}
	/**
	 * @return the chnlCode
	 */
	@Column(name = "CHNLCODE")
	public String getChnlCode() {
		return chnlCode;
	}
	/**
	 * @param chnlCode the chnlCode to set
	 */
	public void setChnlCode(String chnlCode) {
		this.chnlCode = chnlCode;
	}
	/**
	 * @return the bindId
	 */
	@Column(name = "BINDID")
	public String getBindId() {
		return bindId;
	}
	/**
	 * @param bindId the bindId to set
	 */
	public void setBindId(String bindId) {
		this.bindId = bindId;
	}
	
    
    
}
