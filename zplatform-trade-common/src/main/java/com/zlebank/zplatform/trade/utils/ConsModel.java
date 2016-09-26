
package com.zlebank.zplatform.trade.utils;

public class ConsModel {
	private String zlrt_url="";
	private String instuId="";
	private String keyStorePath = "";
	private String cerPath = "";
	
	private String keyStorePath_zlrt = "";
	private String cerPath_zlrt = "";
	
	private String sendMarginSms_url = "";
	private String marginRegisterReq_url="";
	private String onlineDepositShort_url="";
	private String offlineDepositNotifyReq_url="";
	private String withdrawNotifyReq_url="";
	private String querytranstatus_url="";
	
	private String rout_switch="";
	private String eciti_chnl_code="";
	private String eciti_insti_code="";
	private String eciti_safe_no="";
	private String zlpay_chnl_code="";
	private String zlpay_chnl_merch_name="";
	
	private String reapay_quickpay_version="";
	private String reapay_quickpay_url="";
	private String reapay_quickpay_debit_url="";
	private String reapay_quickpay_merchant_id="";
	private String reapay_quickpay_seller_email="";
	private String reapay_quickpay_notify_url="";
	private String reapay_quickpay_key="";
	private String reapay_quickpay_pubkey="";
	private String reapay_quickpay_prikey="";
	private String reapay_quickpay_sms_url = "";
	private String reapay_quickpay_credit_url="";
	private String reapay_quickpay_pay_url="";
	private String reapay_quickpay_prikey_pwd="";
	private String reapay_quickpay_query_url="";
	private String reapay_chnl_code="";
	private String reapay_quickpay_bind_url="";
	/**会员支付密码MD5生成用的KEY**/
	private String member_pay_password_key="";
	
	private String order_number_file_path="";
	
	//民生银行参数
	private String cmbc_version="1.0";
	private String cmbc_merid="";
	private String cmbc_mername="";
	private String cmbc_withholding_chnl_code="";
	private String cmbc_withholding_public_key="";
	private String cmbc_withholding_private_key="";
	private String cmbc_withholding_ip="";
	private int cmbc_withholding_port=0;
	private int cmbc_insteadpay_port = 0;
	private String cmbc_insteadpay_merid="";
	private String cmbc_self_withholding_ip="";
	private int cmbc_self_withholding_port=0;
	private String cmbc_secretfilepath="";
	private String cmbc_insteadpay_batch_md5="";
	private String cmbc_download_file_path="";
	private String cmbc_plainFilePath="";
	private String cmbc_self_merid="";
	private String cmbc_self_merchant="";
	private boolean sms_send_flag = false;
	private String sms_username="";
	private String sms_pwd="";
	private String sms_url="";
	
	private String cmbc_withholding_self_public_key="";
	private String cmbc_withholding_self_private_key="";
	private String cmbc_withholding_self_chnl_code="";
	
	private String cmbc_insteadpay_ftp_ip="";
	private String cmbc_insteadpay_ftp_user="";
	private String cmbc_insteadpay_ftp_pwd="";
	private int cmbc_insteadpay_ftp_port=22;
	private String cmbc_insteadpay_sign_md5="";
	private String cmbc_insteadpay_ip = "";
	
	/**博士金电***/
	private String bosspay_agreement_id="";
	private String bosspay_user_id="";
	private String bosspay_user_key="";
	private String bosspay_bank_account="";
	private String bosspay_bank_number="";
	private String bosspay_bank_account_name="";
	private int bosspay_test_flag=1;//测试开关默认打开
	private String bosspay_userId="";
	
	/**畅捷支付网关**/
	private String chanpay_partner_id="";
	private String chanpay_url="";
	private String chanpay_private_key="";
	private String chanpay_public_key="";
	private String chanpay_version="";
	private String chanpay_input_charset="";
	private String chanpay_sign_type="";
	private String chanpay_channel_code="";
	private String chanpay_front_url="";
	private String chanpay_back_url="";
	private String chanpay_partner_name="";
	private String chanpay_refund_url="";
	
	/**微信支付参数**/
	private String wechat_create_order_url="";
	private String wechat_down_load_bill_url="";
	private String wechat_refund_url="";
	private String wechat_refund_query_url="";
	private String wechat_query_trade_url="";
	private String wechat_key="";
	private String wechat_appID="";
	private String wechat_mchID="";
	private String wechat_cerUrl="";
	private String wechat_notify_url="";
	
	private int is_junit=0;
	private int refund_day=180;
	private String chanpay_cj_merchant_id="";
	private String chanpay_cj_merchant_name="";
	private String chanpay_cj_business_code="";
	private String chanpay_cj_account_no="";
	private String chanpay_cj_product_no="";
	
	/***标准收银台**/
	private String web_cash_url="";
	
	

	/**微信扫描支付参数**/
	private String wechat_qr_create_order_url="";
	private String wechat_qr_down_load_bill_url="";
	private String wechat_qr_refund_url="";
	private String wechat_qr_refund_query_url="";
	private String wechat_qr_query_trade_url="";
	private String wechat_qr_key="";
	private String wechat_qr_appID="";
	private String wechat_qr_mchID="";
	private String wechat_qr_cerUrl="";
	private String wechat_qr_notify_url="";
	private int wechat_qr_overtime=60000;
	private String wechat_qr_close_order_url="";
	private String wechat_qr_short_url="";
	
	
	private String zlebank_coopinsti_code;
	
	
	public String getWeb_cash_url() {
		return web_cash_url;
	}

	public void setWeb_cash_url(String web_cash_url) {
		this.web_cash_url = web_cash_url;
	}

	public String getZlrt_url() {
		return zlrt_url;
	}
	
	public void setZlrt_url(String zlrtUrl) {
		zlrt_url = zlrtUrl;
	}
	
	public String getInstuId() {
		return instuId;
	}
	
	public void setInstuId(String instuId) {
		this.instuId = instuId;
	}

	/**
	 * @return the keyStorePath
	 */
	public String getKeyStorePath() {
		return keyStorePath;
	}

	/**
	 * @param keyStorePath the keyStorePath to set
	 */
	public void setKeyStorePath(String keyStorePath) {
		this.keyStorePath = keyStorePath;
	}

	/**
	 * @return the cerPath
	 */
	public String getCerPath() {
		return cerPath;
	}

	/**
	 * @param cerPath the cerPath to set
	 */
	public void setCerPath(String cerPath) {
		this.cerPath = cerPath;
	}

	/**
	 * @return the keyStorePath_zlrt
	 */
	public String getKeyStorePath_zlrt() {
		return keyStorePath_zlrt;
	}

	/**
	 * @param keyStorePathZlrt the keyStorePath_zlrt to set
	 */
	public void setKeyStorePath_zlrt(String keyStorePathZlrt) {
		keyStorePath_zlrt = keyStorePathZlrt;
	}

	/**
	 * @return the cerPath_zlrt
	 */
	public String getCerPath_zlrt() {
		return cerPath_zlrt;
	}

	/**
	 * @param cerPathZlrt the cerPath_zlrt to set
	 */
	public void setCerPath_zlrt(String cerPathZlrt) {
		cerPath_zlrt = cerPathZlrt;
	}

	/**
	 * @return the sendMarginSms_url
	 */
	public String getSendMarginSms_url() {
		return sendMarginSms_url;
	}

	/**
	 * @param sendMarginSmsUrl the sendMarginSms_url to set
	 */
	public void setSendMarginSms_url(String sendMarginSmsUrl) {
		sendMarginSms_url = sendMarginSmsUrl;
	}

	/**
	 * @return the marginRegisterReq_url
	 */
	public String getMarginRegisterReq_url() {
		return marginRegisterReq_url;
	}

	/**
	 * @param marginRegisterReqUrl the marginRegisterReq_url to set
	 */
	public void setMarginRegisterReq_url(String marginRegisterReqUrl) {
		marginRegisterReq_url = marginRegisterReqUrl;
	}

	/**
	 * @return the onlineDepositShort_url
	 */
	public String getOnlineDepositShort_url() {
		return onlineDepositShort_url;
	}

	/**
	 * @param onlineDepositShortUrl the onlineDepositShort_url to set
	 */
	public void setOnlineDepositShort_url(String onlineDepositShortUrl) {
		onlineDepositShort_url = onlineDepositShortUrl;
	}

	/**
	 * @return the offlineDepositNotifyReq_url
	 */
	public String getOfflineDepositNotifyReq_url() {
		return offlineDepositNotifyReq_url;
	}

	/**
	 * @param offlineDepositNotifyReqUrl the offlineDepositNotifyReq_url to set
	 */
	public void setOfflineDepositNotifyReq_url(String offlineDepositNotifyReqUrl) {
		offlineDepositNotifyReq_url = offlineDepositNotifyReqUrl;
	}

	/**
	 * @return the withdrawNotifyReq_url
	 */
	public String getWithdrawNotifyReq_url() {
		return withdrawNotifyReq_url;
	}

	/**
	 * @param withdrawNotifyReqUrl the withdrawNotifyReq_url to set
	 */
	public void setWithdrawNotifyReq_url(String withdrawNotifyReqUrl) {
		withdrawNotifyReq_url = withdrawNotifyReqUrl;
	}

	/**
	 * @return the querytranstatus_url
	 */
	public String getQuerytranstatus_url() {
		return querytranstatus_url;
	}

	/**
	 * @param querytranstatusUrl the querytranstatus_url to set
	 */
	public void setQuerytranstatus_url(String querytranstatusUrl) {
		querytranstatus_url = querytranstatusUrl;
	}

	/**
	 * @return the rout_switch
	 */
	public String getRout_switch() {
		return rout_switch;
	}

	/**
	 * @param routSwitch the rout_switch to set
	 */
	public void setRout_switch(String routSwitch) {
		rout_switch = routSwitch;
	}

    /**
     * @return the eciti_chnl_code
     */
    public String getEciti_chnl_code() {
        return eciti_chnl_code;
    }

    /**
     * @param eciti_chnl_code the eciti_chnl_code to set
     */
    public void setEciti_chnl_code(String eciti_chnl_code) {
        this.eciti_chnl_code = eciti_chnl_code;
    }

    /**
     * @return the eciti_insti_code
     */
    public String getEciti_insti_code() {
        return eciti_insti_code;
    }

    /**
     * @param eciti_insti_code the eciti_insti_code to set
     */
    public void setEciti_insti_code(String eciti_insti_code) {
        this.eciti_insti_code = eciti_insti_code;
    }

    /**
     * @return the eciti_safe_no
     */
    public String getEciti_safe_no() {
        return eciti_safe_no;
    }

    /**
     * @param eciti_safe_no the eciti_safe_no to set
     */
    public void setEciti_safe_no(String eciti_safe_no) {
        this.eciti_safe_no = eciti_safe_no;
    }

    /**
     * @return the zlpay_chnl_code
     */
    public String getZlpay_chnl_code() {
        return zlpay_chnl_code;
    }

    /**
     * @param zlpay_chnl_code the zlpay_chnl_code to set
     */
    public void setZlpay_chnl_code(String zlpay_chnl_code) {
        this.zlpay_chnl_code = zlpay_chnl_code;
    }

    /**
     * @return the zlpay_chnl_merch_name
     */
    public String getZlpay_chnl_merch_name() {
        return zlpay_chnl_merch_name;
    }

    /**
     * @param zlpay_chnl_merch_name the zlpay_chnl_merch_name to set
     */
    public void setZlpay_chnl_merch_name(String zlpay_chnl_merch_name) {
        this.zlpay_chnl_merch_name = zlpay_chnl_merch_name;
    }

    /**
     * @return the reapay_quickpay_version
     */
    public String getReapay_quickpay_version() {
        return reapay_quickpay_version;
    }

    /**
     * @param reapay_quickpay_version the reapay_quickpay_version to set
     */
    public void setReapay_quickpay_version(String reapay_quickpay_version) {
        this.reapay_quickpay_version = reapay_quickpay_version;
    }

    /**
     * @return the reapay_quickpay_debit_url
     */
    public String getReapay_quickpay_debit_url() {
        return reapay_quickpay_debit_url;
    }

    /**
     * @param reapay_quickpay_debit_url the reapay_quickpay_debit_url to set
     */
    public void setReapay_quickpay_debit_url(String reapay_quickpay_debit_url) {
        this.reapay_quickpay_debit_url = reapay_quickpay_debit_url;
    }

    /**
     * @return the reapay_quickpay_merchant_id
     */
    public String getReapay_quickpay_merchant_id() {
        return reapay_quickpay_merchant_id;
    }

    /**
     * @param reapay_quickpay_merchant_id the reapay_quickpay_merchant_id to set
     */
    public void setReapay_quickpay_merchant_id(String reapay_quickpay_merchant_id) {
        this.reapay_quickpay_merchant_id = reapay_quickpay_merchant_id;
    }

    /**
     * @return the reapay_quickpay_seller_email
     */
    public String getReapay_quickpay_seller_email() {
        return reapay_quickpay_seller_email;
    }

    /**
     * @param reapay_quickpay_seller_email the reapay_quickpay_seller_email to set
     */
    public void setReapay_quickpay_seller_email(String reapay_quickpay_seller_email) {
        this.reapay_quickpay_seller_email = reapay_quickpay_seller_email;
    }

    /**
     * @return the reapay_quickpay_notify_url
     */
    public String getReapay_quickpay_notify_url() {
        return reapay_quickpay_notify_url;
    }

    /**
     * @param reapay_quickpay_notify_url the reapay_quickpay_notify_url to set
     */
    public void setReapay_quickpay_notify_url(String reapay_quickpay_notify_url) {
        this.reapay_quickpay_notify_url = reapay_quickpay_notify_url;
    }

    /**
     * @return the reapay_quickpay_url
     */
    public String getReapay_quickpay_url() {
        return reapay_quickpay_url;
    }

    /**
     * @param reapay_quickpay_url the reapay_quickpay_url to set
     */
    public void setReapay_quickpay_url(String reapay_quickpay_url) {
        this.reapay_quickpay_url = reapay_quickpay_url;
    }

    /**
     * @return the reapay_quickpay_key
     */
    public String getReapay_quickpay_key() {
        return reapay_quickpay_key;
    }

    /**
     * @param reapay_quickpay_key the reapay_quickpay_key to set
     */
    public void setReapay_quickpay_key(String reapay_quickpay_key) {
        this.reapay_quickpay_key = reapay_quickpay_key;
    }

    /**
     * @return the reapay_quickpay_pubkey
     */
    public String getReapay_quickpay_pubkey() {
        return reapay_quickpay_pubkey;
    }

    /**
     * @param reapay_quickpay_pubkey the reapay_quickpay_pubkey to set
     */
    public void setReapay_quickpay_pubkey(String reapay_quickpay_pubkey) {
        this.reapay_quickpay_pubkey = reapay_quickpay_pubkey;
    }

    /**
     * @return the reapay_quickpay_prikey
     */
    public String getReapay_quickpay_prikey() {
        return reapay_quickpay_prikey;
    }

    /**
     * @param reapay_quickpay_prikey the reapay_quickpay_prikey to set
     */
    public void setReapay_quickpay_prikey(String reapay_quickpay_prikey) {
        this.reapay_quickpay_prikey = reapay_quickpay_prikey;
    }

    /**
     * @return the reapay_quickpay_sms_url
     */
    public String getReapay_quickpay_sms_url() {
        return reapay_quickpay_sms_url;
    }

    /**
     * @param reapay_quickpay_sms_url the reapay_quickpay_sms_url to set
     */
    public void setReapay_quickpay_sms_url(String reapay_quickpay_sms_url) {
        this.reapay_quickpay_sms_url = reapay_quickpay_sms_url;
    }

    /**
     * @return the reapay_quickpay_credit_url
     */
    public String getReapay_quickpay_credit_url() {
        return reapay_quickpay_credit_url;
    }

    /**
     * @param reapay_quickpay_credit_url the reapay_quickpay_credit_url to set
     */
    public void setReapay_quickpay_credit_url(String reapay_quickpay_credit_url) {
        this.reapay_quickpay_credit_url = reapay_quickpay_credit_url;
    }

    /**
     * @return the reapay_quickpay_pay_url
     */
    public String getReapay_quickpay_pay_url() {
        return reapay_quickpay_pay_url;
    }

    /**
     * @param reapay_quickpay_pay_url the reapay_quickpay_pay_url to set
     */
    public void setReapay_quickpay_pay_url(String reapay_quickpay_pay_url) {
        this.reapay_quickpay_pay_url = reapay_quickpay_pay_url;
    }

    /**
     * @return the reapay_quickpay_prikey_pwd
     */
    public String getReapay_quickpay_prikey_pwd() {
        return reapay_quickpay_prikey_pwd;
    }

    /**
     * @param reapay_quickpay_prikey_pwd the reapay_quickpay_prikey_pwd to set
     */
    public void setReapay_quickpay_prikey_pwd(String reapay_quickpay_prikey_pwd) {
        this.reapay_quickpay_prikey_pwd = reapay_quickpay_prikey_pwd;
    }

    /**
     * @return the reapay_quickpay_query_url
     */
    public String getReapay_quickpay_query_url() {
        return reapay_quickpay_query_url;
    }

    /**
     * @param reapay_quickpay_query_url the reapay_quickpay_query_url to set
     */
    public void setReapay_quickpay_query_url(String reapay_quickpay_query_url) {
        this.reapay_quickpay_query_url = reapay_quickpay_query_url;
    }

    /**
     * @return the reapay_chnl_code
     */
    public String getReapay_chnl_code() {
        return reapay_chnl_code;
    }

    /**
     * @param reapay_chnl_code the reapay_chnl_code to set
     */
    public void setReapay_chnl_code(String reapay_chnl_code) {
        this.reapay_chnl_code = reapay_chnl_code;
    }

    /**
     * @return the reapay_quickpay_bind_url
     */
    public String getReapay_quickpay_bind_url() {
        return reapay_quickpay_bind_url;
    }

    /**
     * @param reapay_quickpay_bind_url the reapay_quickpay_bind_url to set
     */
    public void setReapay_quickpay_bind_url(String reapay_quickpay_bind_url) {
        this.reapay_quickpay_bind_url = reapay_quickpay_bind_url;
    }

    /**
     * @return the order_number_file_path
     */
    public String getOrder_number_file_path() {
        return order_number_file_path;
    }

    /**
     * @param order_number_file_path the order_number_file_path to set
     */
    public void setOrder_number_file_path(String order_number_file_path) {
        this.order_number_file_path = order_number_file_path;
    }

    public String getMember_pay_password_key() {
        return member_pay_password_key;
    }

    public void setMember_pay_password_key(String member_pay_password_key) {
        this.member_pay_password_key = member_pay_password_key;
    }

    /**
     * @return the cmbc_version
     */
    public String getCmbc_version() {
        return cmbc_version;
    }

    /**
     * @param cmbc_version the cmbc_version to set
     */
    public void setCmbc_version(String cmbc_version) {
        this.cmbc_version = cmbc_version;
    }

    /**
     * @return the cmbc_merid
     */
    public String getCmbc_merid() {
        return cmbc_merid;
    }

    /**
     * @param cmbc_merid the cmbc_merid to set
     */
    public void setCmbc_merid(String cmbc_merid) {
        this.cmbc_merid = cmbc_merid;
    }

    /**
     * @return the cmbc_mername
     */
    public String getCmbc_mername() {
        return cmbc_mername;
    }

    /**
     * @param cmbc_mername the cmbc_mername to set
     */
    public void setCmbc_mername(String cmbc_mername) {
        this.cmbc_mername = cmbc_mername;
    }

    /**
     * @return the cmbc_withholding_chnl_code
     */
    public String getCmbc_withholding_chnl_code() {
        return cmbc_withholding_chnl_code;
    }

    /**
     * @param cmbc_withholding_chnl_code the cmbc_withholding_chnl_code to set
     */
    public void setCmbc_withholding_chnl_code(String cmbc_withholding_chnl_code) {
        this.cmbc_withholding_chnl_code = cmbc_withholding_chnl_code;
    }

    /**
     * @return the cmbc_withholding_public_key
     */
    public String getCmbc_withholding_public_key() {
        return cmbc_withholding_public_key;
    }

    /**
     * @param cmbc_withholding_public_key the cmbc_withholding_public_key to set
     */
    public void setCmbc_withholding_public_key(String cmbc_withholding_public_key) {
        this.cmbc_withholding_public_key = cmbc_withholding_public_key;
    }

    /**
     * @return the cmbc_withholding_private_key
     */
    public String getCmbc_withholding_private_key() {
        return cmbc_withholding_private_key;
    }

    /**
     * @param cmbc_withholding_private_key the cmbc_withholding_private_key to set
     */
    public void setCmbc_withholding_private_key(String cmbc_withholding_private_key) {
        this.cmbc_withholding_private_key = cmbc_withholding_private_key;
    }

    /**
     * @return the cmbc_withholding_ip
     */
    public String getCmbc_withholding_ip() {
        return cmbc_withholding_ip;
    }

    /**
     * @param cmbc_withholding_ip the cmbc_withholding_ip to set
     */
    public void setCmbc_withholding_ip(String cmbc_withholding_ip) {
        this.cmbc_withholding_ip = cmbc_withholding_ip;
    }

    /**
     * @return the cmbc_withholding_port
     */
    public int getCmbc_withholding_port() {
        return cmbc_withholding_port;
    }

    /**
     * @param cmbc_withholding_port the cmbc_withholding_port to set
     */
    public void setCmbc_withholding_port(int cmbc_withholding_port) {
        this.cmbc_withholding_port = cmbc_withholding_port;
    }

    /**
     * @return the cmbc_insteadpay_port
     */
    public int getCmbc_insteadpay_port() {
        return cmbc_insteadpay_port;
    }

    /**
     * @param cmbc_insteadpay_port the cmbc_insteadpay_port to set
     */
    public void setCmbc_insteadpay_port(int cmbc_insteadpay_port) {
        this.cmbc_insteadpay_port = cmbc_insteadpay_port;
    }

    /**
     * @return the cmbc_insteadpay_merid
     */
    public String getCmbc_insteadpay_merid() {
        return cmbc_insteadpay_merid;
    }

    /**
     * @param cmbc_insteadpay_merid the cmbc_insteadpay_merid to set
     */
    public void setCmbc_insteadpay_merid(String cmbc_insteadpay_merid) {
        this.cmbc_insteadpay_merid = cmbc_insteadpay_merid;
    }

    /**
     * @return the cmbc_self_withholding_ip
     */
    public String getCmbc_self_withholding_ip() {
        return cmbc_self_withholding_ip;
    }

    /**
     * @param cmbc_self_withholding_ip the cmbc_self_withholding_ip to set
     */
    public void setCmbc_self_withholding_ip(String cmbc_self_withholding_ip) {
        this.cmbc_self_withholding_ip = cmbc_self_withholding_ip;
    }

    /**
     * @return the cmbc_self_withholding_port
     */
    public int getCmbc_self_withholding_port() {
        return cmbc_self_withholding_port;
    }

    /**
     * @param cmbc_self_withholding_port the cmbc_self_withholding_port to set
     */
    public void setCmbc_self_withholding_port(int cmbc_self_withholding_port) {
        this.cmbc_self_withholding_port = cmbc_self_withholding_port;
    }

    public String getCmbc_insteadpay_batch_md5() {
        return cmbc_insteadpay_batch_md5;
    }

    public void setCmbc_insteadpay_batch_md5(String cmbc_insteadpay_batch_md5) {
        this.cmbc_insteadpay_batch_md5 = cmbc_insteadpay_batch_md5;
    }

    public String getCmbc_secretfilepath() {
        return cmbc_secretfilepath;
    }

    public void setCmbc_secretfilepath(String cmbc_secretfilepath) {
        this.cmbc_secretfilepath = cmbc_secretfilepath;
    }

    public String getCmbc_download_file_path() {
        return cmbc_download_file_path;
    }

    public void setCmbc_download_file_path(String cmbc_download_file_path) {
        this.cmbc_download_file_path = cmbc_download_file_path;
    }

    public String getCmbc_plainFilePath() {
        return cmbc_plainFilePath;
    }

    public void setCmbc_plainFilePath(String cmbc_plainFilePath) {
        this.cmbc_plainFilePath = cmbc_plainFilePath;
    }

    public String getCmbc_self_merid() {
        return cmbc_self_merid;
    }

    public void setCmbc_self_merid(String cmbc_self_merid) {
        this.cmbc_self_merid = cmbc_self_merid;
    }

    public String getCmbc_self_merchant() {
        return cmbc_self_merchant;
    }

    public void setCmbc_self_merchant(String cmbc_self_merchant) {
        this.cmbc_self_merchant = cmbc_self_merchant;
    }

    public boolean isSms_send_flag() {
        return sms_send_flag;
    }

    public void setSms_send_flag(boolean sms_send_flag) {
        this.sms_send_flag = sms_send_flag;
    }

    public String getSms_username() {
        return sms_username;
    }

    public void setSms_username(String sms_username) {
        this.sms_username = sms_username;
    }

    public String getSms_pwd() {
        return sms_pwd;
    }

    public void setSms_pwd(String sms_pwd) {
        this.sms_pwd = sms_pwd;
    }

    public String getSms_url() {
        return sms_url;
    }

    public void setSms_url(String sms_url) {
        this.sms_url = sms_url;
    }

    public String getCmbc_withholding_self_public_key() {
        return cmbc_withholding_self_public_key;
    }

    public void setCmbc_withholding_self_public_key(String cmbc_withholding_self_public_key) {
        this.cmbc_withholding_self_public_key = cmbc_withholding_self_public_key;
    }

    public String getCmbc_withholding_self_private_key() {
        return cmbc_withholding_self_private_key;
    }

    public void setCmbc_withholding_self_private_key(String cmbc_withholding_self_private_key) {
        this.cmbc_withholding_self_private_key = cmbc_withholding_self_private_key;
    }

    public String getCmbc_withholding_self_chnl_code() {
        return cmbc_withholding_self_chnl_code;
    }

    public void setCmbc_withholding_self_chnl_code(String cmbc_withholding_self_chnl_code) {
        this.cmbc_withholding_self_chnl_code = cmbc_withholding_self_chnl_code;
    }

    public String getCmbc_insteadpay_ftp_ip() {
        return cmbc_insteadpay_ftp_ip;
    }

    public void setCmbc_insteadpay_ftp_ip(String cmbc_insteadpay_ftp_ip) {
        this.cmbc_insteadpay_ftp_ip = cmbc_insteadpay_ftp_ip;
    }

    public String getCmbc_insteadpay_ftp_user() {
        return cmbc_insteadpay_ftp_user;
    }

    public void setCmbc_insteadpay_ftp_user(String cmbc_insteadpay_ftp_user) {
        this.cmbc_insteadpay_ftp_user = cmbc_insteadpay_ftp_user;
    }

    public String getCmbc_insteadpay_ftp_pwd() {
        return cmbc_insteadpay_ftp_pwd;
    }

    public void setCmbc_insteadpay_ftp_pwd(String cmbc_insteadpay_ftp_pwd) {
        this.cmbc_insteadpay_ftp_pwd = cmbc_insteadpay_ftp_pwd;
    }

    public int getCmbc_insteadpay_ftp_port() {
        return cmbc_insteadpay_ftp_port;
    }

    public void setCmbc_insteadpay_ftp_port(int cmbc_insteadpay_ftp_port) {
        this.cmbc_insteadpay_ftp_port = cmbc_insteadpay_ftp_port;
    }

	public String getCmbc_insteadpay_sign_md5() {
		return cmbc_insteadpay_sign_md5;
	}

	public void setCmbc_insteadpay_sign_md5(String cmbc_insteadpay_sign_md5) {
		this.cmbc_insteadpay_sign_md5 = cmbc_insteadpay_sign_md5;
	}

	public String getCmbc_insteadpay_ip() {
		return cmbc_insteadpay_ip;
	}

	public void setCmbc_insteadpay_ip(String cmbc_insteadpay_ip) {
		this.cmbc_insteadpay_ip = cmbc_insteadpay_ip;
	}

	/**
	 * @return the bosspay_agreement_id
	 */
	public String getBosspay_agreement_id() {
		return bosspay_agreement_id;
	}

	/**
	 * @param bosspay_agreement_id the bosspay_agreement_id to set
	 */
	public void setBosspay_agreement_id(String bosspay_agreement_id) {
		this.bosspay_agreement_id = bosspay_agreement_id;
	}

	/**
	 * @return the bosspay_user_id
	 */
	public String getBosspay_user_id() {
		return bosspay_user_id;
	}

	/**
	 * @param bosspay_user_id the bosspay_user_id to set
	 */
	public void setBosspay_user_id(String bosspay_user_id) {
		this.bosspay_user_id = bosspay_user_id;
	}

	/**
	 * @return the bosspay_user_key
	 */
	public String getBosspay_user_key() {
		return bosspay_user_key;
	}

	/**
	 * @param bosspay_user_key the bosspay_user_key to set
	 */
	public void setBosspay_user_key(String bosspay_user_key) {
		this.bosspay_user_key = bosspay_user_key;
	}

	/**
	 * @return the bosspay_bank_account
	 */
	public String getBosspay_bank_account() {
		return bosspay_bank_account;
	}

	/**
	 * @param bosspay_bank_account the bosspay_bank_account to set
	 */
	public void setBosspay_bank_account(String bosspay_bank_account) {
		this.bosspay_bank_account = bosspay_bank_account;
	}

	/**
	 * @return the bosspay_bank_number
	 */
	public String getBosspay_bank_number() {
		return bosspay_bank_number;
	}

	/**
	 * @param bosspay_bank_number the bosspay_bank_number to set
	 */
	public void setBosspay_bank_number(String bosspay_bank_number) {
		this.bosspay_bank_number = bosspay_bank_number;
	}

	/**
	 * @return the bosspay_bank_account_name
	 */
	public String getBosspay_bank_account_name() {
		return bosspay_bank_account_name;
	}

	/**
	 * @param bosspay_bank_account_name the bosspay_bank_account_name to set
	 */
	public void setBosspay_bank_account_name(String bosspay_bank_account_name) {
		this.bosspay_bank_account_name = bosspay_bank_account_name;
	}

	/**
	 * @return the bosspay_test_flag
	 */
	public int getBosspay_test_flag() {
		return bosspay_test_flag;
	}

	/**
	 * @param bosspay_test_flag the bosspay_test_flag to set
	 */
	public void setBosspay_test_flag(int bosspay_test_flag) {
		this.bosspay_test_flag = bosspay_test_flag;
	}

	/**
	 * @return the bosspay_userId
	 */
	public String getBosspay_userId() {
		return bosspay_userId;
	}

	/**
	 * @param bosspay_userId the bosspay_userId to set
	 */
	public void setBosspay_userId(String bosspay_userId) {
		this.bosspay_userId = bosspay_userId;
	}

	/**
	 * @return the chanpay_partner_id
	 */
	public String getChanpay_partner_id() {
		return chanpay_partner_id;
	}

	/**
	 * @param chanpay_partner_id the chanpay_partner_id to set
	 */
	public void setChanpay_partner_id(String chanpay_partner_id) {
		this.chanpay_partner_id = chanpay_partner_id;
	}

	/**
	 * @return the chanpay_url
	 */
	public String getChanpay_url() {
		return chanpay_url;
	}

	/**
	 * @param chanpay_url the chanpay_url to set
	 */
	public void setChanpay_url(String chanpay_url) {
		this.chanpay_url = chanpay_url;
	}

	/**
	 * @return the chanpay_private_key
	 */
	public String getChanpay_private_key() {
		return chanpay_private_key;
	}

	/**
	 * @param chanpay_private_key the chanpay_private_key to set
	 */
	public void setChanpay_private_key(String chanpay_private_key) {
		this.chanpay_private_key = chanpay_private_key;
	}

	/**
	 * @return the chanpay_public_key
	 */
	public String getChanpay_public_key() {
		return chanpay_public_key;
	}

	/**
	 * @param chanpay_public_key the chanpay_public_key to set
	 */
	public void setChanpay_public_key(String chanpay_public_key) {
		this.chanpay_public_key = chanpay_public_key;
	}

	/**
	 * @return the chanpay_version
	 */
	public String getChanpay_version() {
		return chanpay_version;
	}

	/**
	 * @param chanpay_version the chanpay_version to set
	 */
	public void setChanpay_version(String chanpay_version) {
		this.chanpay_version = chanpay_version;
	}

	/**
	 * @return the chanpay_input_charset
	 */
	public String getChanpay_input_charset() {
		return chanpay_input_charset;
	}

	/**
	 * @param chanpay_input_charset the chanpay_input_charset to set
	 */
	public void setChanpay_input_charset(String chanpay_input_charset) {
		this.chanpay_input_charset = chanpay_input_charset;
	}

	/**
	 * @return the chanpay_sign_type
	 */
	public String getChanpay_sign_type() {
		return chanpay_sign_type;
	}

	/**
	 * @param chanpay_sign_type the chanpay_sign_type to set
	 */
	public void setChanpay_sign_type(String chanpay_sign_type) {
		this.chanpay_sign_type = chanpay_sign_type;
	}

	/**
	 * @return the chanpay_channel_code
	 */
	public String getChanpay_channel_code() {
		return chanpay_channel_code;
	}

	/**
	 * @param chanpay_channel_code the chanpay_channel_code to set
	 */
	public void setChanpay_channel_code(String chanpay_channel_code) {
		this.chanpay_channel_code = chanpay_channel_code;
	}

	/**
	 * @return the chanpay_front_url
	 */
	public String getChanpay_front_url() {
		return chanpay_front_url;
	}

	/**
	 * @param chanpay_front_url the chanpay_front_url to set
	 */
	public void setChanpay_front_url(String chanpay_front_url) {
		this.chanpay_front_url = chanpay_front_url;
	}

	/**
	 * @return the chanpay_back_url
	 */
	public String getChanpay_back_url() {
		return chanpay_back_url;
	}

	/**
	 * @param chanpay_back_url the chanpay_back_url to set
	 */
	public void setChanpay_back_url(String chanpay_back_url) {
		this.chanpay_back_url = chanpay_back_url;
	}

	/**
	 * @return the chanpay_partner_name
	 */
	public String getChanpay_partner_name() {
		return chanpay_partner_name;
	}

	/**
	 * @param chanpay_partner_name the chanpay_partner_name to set
	 */
	public void setChanpay_partner_name(String chanpay_partner_name) {
		this.chanpay_partner_name = chanpay_partner_name;
	}

	/**
	 * @return the chanpay_refund_url
	 */
	public String getChanpay_refund_url() {
		return chanpay_refund_url;
	}

	/**
	 * @param chanpay_refund_url the chanpay_refund_url to set
	 */
	public void setChanpay_refund_url(String chanpay_refund_url) {
		this.chanpay_refund_url = chanpay_refund_url;
	}

	/**
	 * @return the wechat_create_order_url
	 */
	public String getWechat_create_order_url() {
		return wechat_create_order_url;
	}

	/**
	 * @param wechat_create_order_url the wechat_create_order_url to set
	 */
	public void setWechat_create_order_url(String wechat_create_order_url) {
		this.wechat_create_order_url = wechat_create_order_url;
	}

	/**
	 * @return the wechat_down_load_bill_url
	 */
	public String getWechat_down_load_bill_url() {
		return wechat_down_load_bill_url;
	}

	/**
	 * @param wechat_down_load_bill_url the wechat_down_load_bill_url to set
	 */
	public void setWechat_down_load_bill_url(String wechat_down_load_bill_url) {
		this.wechat_down_load_bill_url = wechat_down_load_bill_url;
	}

	/**
	 * @return the wechat_refund_url
	 */
	public String getWechat_refund_url() {
		return wechat_refund_url;
	}

	/**
	 * @param wechat_refund_url the wechat_refund_url to set
	 */
	public void setWechat_refund_url(String wechat_refund_url) {
		this.wechat_refund_url = wechat_refund_url;
	}

	/**
	 * @return the wechat_refund_query_url
	 */
	public String getWechat_refund_query_url() {
		return wechat_refund_query_url;
	}

	/**
	 * @param wechat_refund_query_url the wechat_refund_query_url to set
	 */
	public void setWechat_refund_query_url(String wechat_refund_query_url) {
		this.wechat_refund_query_url = wechat_refund_query_url;
	}

	/**
	 * @return the wechat_query_trade_url
	 */
	public String getWechat_query_trade_url() {
		return wechat_query_trade_url;
	}

	/**
	 * @param wechat_query_trade_url the wechat_query_trade_url to set
	 */
	public void setWechat_query_trade_url(String wechat_query_trade_url) {
		this.wechat_query_trade_url = wechat_query_trade_url;
	}

	/**
	 * @return the wechat_key
	 */
	public String getWechat_key() {
		return wechat_key;
	}

	/**
	 * @param wechat_key the wechat_key to set
	 */
	public void setWechat_key(String wechat_key) {
		this.wechat_key = wechat_key;
	}

	/**
	 * @return the wechat_appID
	 */
	public String getWechat_appID() {
		return wechat_appID;
	}

	/**
	 * @param wechat_appID the wechat_appID to set
	 */
	public void setWechat_appID(String wechat_appID) {
		this.wechat_appID = wechat_appID;
	}

	/**
	 * @return the wechat_mchID
	 */
	public String getWechat_mchID() {
		return wechat_mchID;
	}

	/**
	 * @param wechat_mchID the wechat_mchID to set
	 */
	public void setWechat_mchID(String wechat_mchID) {
		this.wechat_mchID = wechat_mchID;
	}

	/**
	 * @return the wechat_cerUrl
	 */
	public String getWechat_cerUrl() {
		return wechat_cerUrl;
	}

	/**
	 * @param wechat_cerUrl the wechat_cerUrl to set
	 */
	public void setWechat_cerUrl(String wechat_cerUrl) {
		this.wechat_cerUrl = wechat_cerUrl;
	}

	/**
	 * @return the wechat_notify_url
	 */
	public String getWechat_notify_url() {
		return wechat_notify_url;
	}

	/**
	 * @param wechat_notify_url the wechat_notify_url to set
	 */
	public void setWechat_notify_url(String wechat_notify_url) {
		this.wechat_notify_url = wechat_notify_url;
	}

	/**
	 * @return the is_junit
	 */
	public int getIs_junit() {
		return is_junit;
	}

	/**
	 * @param is_junit the is_junit to set
	 */
	public void setIs_junit(int is_junit) {
		this.is_junit = is_junit;
	}

	/**
	 * @return the refund_day
	 */
	public int getRefund_day() {
		return refund_day;
	}

	/**
	 * @param refund_day the refund_day to set
	 */
	public void setRefund_day(int refund_day) {
		this.refund_day = refund_day;
	}

	/**
	 * @return the chanpay_cj_merchant_id
	 */
	public String getChanpay_cj_merchant_id() {
		return chanpay_cj_merchant_id;
	}

	/**
	 * @param chanpay_cj_merchant_id the chanpay_cj_merchant_id to set
	 */
	public void setChanpay_cj_merchant_id(String chanpay_cj_merchant_id) {
		this.chanpay_cj_merchant_id = chanpay_cj_merchant_id;
	}

	/**
	 * @return the chanpay_cj_merchant_name
	 */
	public String getChanpay_cj_merchant_name() {
		return chanpay_cj_merchant_name;
	}

	/**
	 * @param chanpay_cj_merchant_name the chanpay_cj_merchant_name to set
	 */
	public void setChanpay_cj_merchant_name(String chanpay_cj_merchant_name) {
		this.chanpay_cj_merchant_name = chanpay_cj_merchant_name;
	}

	/**
	 * @return the chanpay_cj_business_code
	 */
	public String getChanpay_cj_business_code() {
		return chanpay_cj_business_code;
	}

	/**
	 * @param chanpay_cj_business_code the chanpay_cj_business_code to set
	 */
	public void setChanpay_cj_business_code(String chanpay_cj_business_code) {
		this.chanpay_cj_business_code = chanpay_cj_business_code;
	}

	/**
	 * @return the chanpay_cj_account_no
	 */
	public String getChanpay_cj_account_no() {
		return chanpay_cj_account_no;
	}

	/**
	 * @param chanpay_cj_account_no the chanpay_cj_account_no to set
	 */
	public void setChanpay_cj_account_no(String chanpay_cj_account_no) {
		this.chanpay_cj_account_no = chanpay_cj_account_no;
	}

	/**
	 * @return the chanpay_cj_product_no
	 */
	public String getChanpay_cj_product_no() {
		return chanpay_cj_product_no;
	}

	/**
	 * @param chanpay_cj_product_no the chanpay_cj_product_no to set
	 */
	public void setChanpay_cj_product_no(String chanpay_cj_product_no) {
		this.chanpay_cj_product_no = chanpay_cj_product_no;
	}

	/**
	 * @return the wechat_qr_create_order_url
	 */
	public String getWechat_qr_create_order_url() {
		return wechat_qr_create_order_url;
	}

	/**
	 * @param wechat_qr_create_order_url the wechat_qr_create_order_url to set
	 */
	public void setWechat_qr_create_order_url(String wechat_qr_create_order_url) {
		this.wechat_qr_create_order_url = wechat_qr_create_order_url;
	}

	/**
	 * @return the wechat_qr_down_load_bill_url
	 */
	public String getWechat_qr_down_load_bill_url() {
		return wechat_qr_down_load_bill_url;
	}

	/**
	 * @param wechat_qr_down_load_bill_url the wechat_qr_down_load_bill_url to set
	 */
	public void setWechat_qr_down_load_bill_url(String wechat_qr_down_load_bill_url) {
		this.wechat_qr_down_load_bill_url = wechat_qr_down_load_bill_url;
	}

	/**
	 * @return the wechat_qr_refund_url
	 */
	public String getWechat_qr_refund_url() {
		return wechat_qr_refund_url;
	}

	/**
	 * @param wechat_qr_refund_url the wechat_qr_refund_url to set
	 */
	public void setWechat_qr_refund_url(String wechat_qr_refund_url) {
		this.wechat_qr_refund_url = wechat_qr_refund_url;
	}

	/**
	 * @return the wechat_qr_refund_query_url
	 */
	public String getWechat_qr_refund_query_url() {
		return wechat_qr_refund_query_url;
	}

	/**
	 * @param wechat_qr_refund_query_url the wechat_qr_refund_query_url to set
	 */
	public void setWechat_qr_refund_query_url(String wechat_qr_refund_query_url) {
		this.wechat_qr_refund_query_url = wechat_qr_refund_query_url;
	}

	/**
	 * @return the wechat_qr_query_trade_url
	 */
	public String getWechat_qr_query_trade_url() {
		return wechat_qr_query_trade_url;
	}

	/**
	 * @param wechat_qr_query_trade_url the wechat_qr_query_trade_url to set
	 */
	public void setWechat_qr_query_trade_url(String wechat_qr_query_trade_url) {
		this.wechat_qr_query_trade_url = wechat_qr_query_trade_url;
	}

	/**
	 * @return the wechat_qr_key
	 */
	public String getWechat_qr_key() {
		return wechat_qr_key;
	}

	/**
	 * @param wechat_qr_key the wechat_qr_key to set
	 */
	public void setWechat_qr_key(String wechat_qr_key) {
		this.wechat_qr_key = wechat_qr_key;
	}

	/**
	 * @return the wechat_qr_appID
	 */
	public String getWechat_qr_appID() {
		return wechat_qr_appID;
	}

	/**
	 * @param wechat_qr_appID the wechat_qr_appID to set
	 */
	public void setWechat_qr_appID(String wechat_qr_appID) {
		this.wechat_qr_appID = wechat_qr_appID;
	}

	/**
	 * @return the wechat_qr_mchID
	 */
	public String getWechat_qr_mchID() {
		return wechat_qr_mchID;
	}

	/**
	 * @param wechat_qr_mchID the wechat_qr_mchID to set
	 */
	public void setWechat_qr_mchID(String wechat_qr_mchID) {
		this.wechat_qr_mchID = wechat_qr_mchID;
	}

	/**
	 * @return the wechat_qr_cerUrl
	 */
	public String getWechat_qr_cerUrl() {
		return wechat_qr_cerUrl;
	}

	/**
	 * @param wechat_qr_cerUrl the wechat_qr_cerUrl to set
	 */
	public void setWechat_qr_cerUrl(String wechat_qr_cerUrl) {
		this.wechat_qr_cerUrl = wechat_qr_cerUrl;
	}

	/**
	 * @return the wechat_qr_notify_url
	 */
	public String getWechat_qr_notify_url() {
		return wechat_qr_notify_url;
	}

	/**
	 * @param wechat_qr_notify_url the wechat_qr_notify_url to set
	 */
	public void setWechat_qr_notify_url(String wechat_qr_notify_url) {
		this.wechat_qr_notify_url = wechat_qr_notify_url;
	}

	/**
	 * @return the wechat_qr_overtime
	 */
	public int getWechat_qr_overtime() {
		return wechat_qr_overtime;
	}

	/**
	 * @param wechat_qr_overtime the wechat_qr_overtime to set
	 */
	public void setWechat_qr_overtime(int wechat_qr_overtime) {
		this.wechat_qr_overtime = wechat_qr_overtime;
	}

	/**
	 * @return the wechat_qr_close_order_url
	 */
	public String getWechat_qr_close_order_url() {
		return wechat_qr_close_order_url;
	}

	/**
	 * @param wechat_qr_close_order_url the wechat_qr_close_order_url to set
	 */
	public void setWechat_qr_close_order_url(String wechat_qr_close_order_url) {
		this.wechat_qr_close_order_url = wechat_qr_close_order_url;
	}

	/**
	 * @return the wechat_qr_short_url
	 */
	public String getWechat_qr_short_url() {
		return wechat_qr_short_url;
	}

	/**
	 * @param wechat_qr_short_url the wechat_qr_short_url to set
	 */
	public void setWechat_qr_short_url(String wechat_qr_short_url) {
		this.wechat_qr_short_url = wechat_qr_short_url;
	}

	/**
	 * @return the zlebank_coopinsti_code
	 */
	public String getZlebank_coopinsti_code() {
		return zlebank_coopinsti_code;
	}

	/**
	 * @param zlebank_coopinsti_code the zlebank_coopinsti_code to set
	 */
	public void setZlebank_coopinsti_code(String zlebank_coopinsti_code) {
		this.zlebank_coopinsti_code = zlebank_coopinsti_code;
	}
	
	
	
}
