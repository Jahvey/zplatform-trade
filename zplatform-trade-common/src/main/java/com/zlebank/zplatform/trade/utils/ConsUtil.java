
package com.zlebank.zplatform.trade.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConsUtil {
	private static ConsUtil util;
	public ConsModel cons;
	public static final String REALNAMEAUTH = "1004";
    public static final String REALNAMEAUTHQUERY = "3004";
    public static final String WITHHOLDING = "1003";
    public static final String WITHHOLDINGQUERY = "3003";
    public static final String WHITELIST = "1007";
    public static final String WHITELISTQUERY = "3007";
    public static final String WITHHOLDINGSELF = "1009";
    public static final String WITHHOLDINGSELFQUERY = "3009";
    public static final String REALNAMEAUTH_CHANPAY = "G60001";
    public static final String WITHHOLDING_CHANPAY = "G10001";
    public static final String PROTOCOLSIGN_CHANPAY = "G60003";
	private ConsUtil(){
		try {
			cons = new ConsModel();
			String path = "/home/web/trade/";
			File file = new File(path+ "zlrt.properties");
			if(!file.exists()){
			    path = getClass().getResource("/").getPath();
			    file = null;
			}
			Properties prop = new Properties();
			InputStream stream = null;
			stream = new BufferedInputStream(new FileInputStream(new File(path+ "zlrt.properties")));
			prop.load(stream);
			String url=prop.getProperty("zlrt_url");
			cons.setZlrt_url(url);
			cons.setInstuId(prop.getProperty("instuId"));
			cons.setKeyStorePath(path+prop.getProperty("keyStorePath"));
			cons.setCerPath(path+prop.getProperty("cerPath"));
			cons.setKeyStorePath_zlrt(path+prop.getProperty("keyStorePath_zlrt"));
			cons.setCerPath_zlrt(path+prop.getProperty("cerPath_zlrt"));
			
			cons.setSendMarginSms_url(url+prop.getProperty("sendMarginSms_url"));
			cons.setMarginRegisterReq_url(url+prop.getProperty("marginRegisterReq_url"));
			cons.setOnlineDepositShort_url(url+prop.getProperty("onlineDepositShort_url"));
			cons.setWithdrawNotifyReq_url(url+prop.getProperty("withdrawNotifyReq_url"));
			cons.setQuerytranstatus_url(url+prop.getProperty("querytranstatus_url"));
			cons.setRout_switch(prop.getProperty("rout_switch"));
			
			cons.setEciti_chnl_code(prop.getProperty("eciti_chnl_code"));
			cons.setEciti_insti_code(prop.getProperty("eciti_insti_code"));
			cons.setEciti_safe_no(prop.getProperty("eciti_safe_no"));
			
			cons.setZlpay_chnl_code(prop.getProperty("zlpay_chnl_code"));
			cons.setZlpay_chnl_merch_name(prop.getProperty("zlpay_chnl_merch_name"));
			
			cons.setReapay_quickpay_version(prop.getProperty("reapay_quickpay_version"));
			cons.setReapay_quickpay_url(prop.getProperty("reapay_quickpay_url"));
			cons.setReapay_quickpay_debit_url(prop.getProperty("reapay_quickpay_debit_url"));
			cons.setReapay_quickpay_merchant_id(prop.getProperty("reapay_quickpay_merchant_id"));
			cons.setReapay_quickpay_seller_email(prop.getProperty("reapay_quickpay_seller_email"));
			cons.setReapay_quickpay_notify_url(prop.getProperty("reapay_quickpay_notify_url"));
			cons.setReapay_quickpay_key(prop.getProperty("reapay_quickpay_key"));
			cons.setReapay_quickpay_pubkey(prop.getProperty("reapay_quickpay_pubkey"));
			cons.setReapay_quickpay_prikey(prop.getProperty("reapay_quickpay_prikey"));
			cons.setReapay_quickpay_sms_url(prop.getProperty("reapay_quickpay_sms_url"));
			cons.setReapay_quickpay_credit_url(prop.getProperty("reapay_quickpay_credit_url"));
			cons.setReapay_quickpay_pay_url(prop.getProperty("reapay_quickpay_pay_url"));
			cons.setReapay_quickpay_prikey_pwd(prop.getProperty("reapay_quickpay_prikey_pwd"));
			cons.setReapay_quickpay_query_url(prop.getProperty("reapay_quickpay_query_url"));
			cons.setReapay_chnl_code(prop.getProperty("reapay_chnl_code"));
			cons.setReapay_quickpay_bind_url(prop.getProperty("reapay_quickpay_bind_url"));
			cons.setOrder_number_file_path(prop.getProperty("order_number_file_path"));
			cons.setMember_pay_password_key(prop.getProperty("member_pay_password_key"));
			
			cons.setCmbc_version(prop.getProperty("cmbc_version"));
			cons.setCmbc_merid(prop.getProperty("cmbc_merid"));
			cons.setCmbc_mername(prop.getProperty("cmbc_mername"));
			cons.setCmbc_withholding_chnl_code(prop.getProperty("cmbc_withholding_chnl_code"));
			cons.setCmbc_withholding_public_key(prop.getProperty("cmbc_withholding_public_key"));
			cons.setCmbc_withholding_private_key(prop.getProperty("cmbc_withholding_private_key"));
			cons.setCmbc_withholding_ip(prop.getProperty("cmbc_withholding_ip"));
			cons.setCmbc_withholding_port(Integer.valueOf(prop.getProperty("cmbc_withholding_port")));
			cons.setCmbc_insteadpay_port(Integer.valueOf(prop.getProperty("cmbc_insteadpay_port")));
			cons.setCmbc_insteadpay_merid(prop.getProperty("cmbc_insteadpay_merid"));
			cons.setCmbc_self_withholding_ip(prop.getProperty("cmbc_self_withholding_ip"));
			cons.setCmbc_self_withholding_port(Integer.valueOf(prop.getProperty("cmbc_self_withholding_port")));
			cons.setCmbc_insteadpay_batch_md5(prop.getProperty("cmbc_insteadpay_batch_md5"));
			cons.setCmbc_secretfilepath(prop.getProperty("cmbc_secretfilepath"));
			cons.setCmbc_download_file_path(prop.getProperty("cmbc_download_file_path"));
			cons.setCmbc_plainFilePath(prop.getProperty("cmbc_plainFilePath"));
			cons.setCmbc_self_merid(prop.getProperty("cmbc_self_merid"));
			cons.setCmbc_self_merchant(prop.getProperty("cmbc_self_merchant"));
			cons.setSms_send_flag(prop.get("sms_send_flag").equals("1"));
			cons.setSms_username(prop.getProperty("sms_username"));
			cons.setSms_pwd(prop.getProperty("sms_pwd"));
			cons.setSms_url(prop.getProperty("sms_url"));
			cons.setCmbc_withholding_self_public_key(prop.getProperty("cmbc_withholding_self_public_key"));
			cons.setCmbc_withholding_self_private_key(prop.getProperty("cmbc_withholding_self_private_key"));
			cons.setCmbc_withholding_self_chnl_code(prop.getProperty("cmbc_withholding_self_chnl_code"));
			
			cons.setCmbc_insteadpay_ftp_ip(prop.getProperty("cmbc_insteadpay_ftp_ip"));
			cons.setCmbc_insteadpay_ftp_pwd(prop.getProperty("cmbc_insteadpay_ftp_pwd"));
			cons.setCmbc_insteadpay_ftp_user(prop.getProperty("cmbc_insteadpay_ftp_user"));
			cons.setCmbc_insteadpay_ftp_port(Integer.valueOf(prop.getProperty("cmbc_insteadpay_ftp_port")));
			cons.setCmbc_insteadpay_sign_md5(prop.getProperty("cmbc_insteadpay_sign_md5"));
			
			cons.setBosspay_agreement_id(prop.getProperty("bosspay_agreement_id"));
			cons.setBosspay_bank_account(prop.getProperty("bosspay_bank_account"));
			cons.setBosspay_bank_account_name(prop.getProperty("bosspay_bank_account_name"));
			cons.setBosspay_bank_number(prop.getProperty("bosspay_bank_number"));
			cons.setBosspay_user_id(prop.getProperty("bosspay_user_id"));
			cons.setBosspay_user_key(prop.getProperty("bosspay_user_key"));
			cons.setBosspay_test_flag(Integer.valueOf(prop.getProperty("bosspay_test_flag","1")));
			cons.setBosspay_userId(prop.getProperty("bosspay_userId"));
			
			
			cons.setChanpay_partner_id(prop.getProperty("chanpay_partner_id"));
			cons.setChanpay_private_key(prop.getProperty("chanpay_private_key"));
			cons.setChanpay_url(prop.getProperty("chanpay_url"));
			cons.setChanpay_public_key(prop.getProperty("chanpay_public_key"));
			cons.setChanpay_input_charset(prop.getProperty("chanpay_input_charset","UTF-8"));
			cons.setChanpay_version(prop.getProperty("chanpay_version","1.0"));
			cons.setChanpay_sign_type(prop.getProperty("chanpay_sign_type"));
			cons.setChanpay_back_url(prop.getProperty("chanpay_back_url"));
			cons.setChanpay_front_url(prop.getProperty("chanpay_front_url"));
			cons.setChanpay_channel_code(prop.getProperty("chanpay_channel_code"));
			cons.setChanpay_partner_name(prop.getProperty("chanpay_partner_name"));
			cons.setChanpay_refund_url(prop.getProperty("chanpay_refund_url"));
			
			
			cons.setWechat_appID(prop.getProperty("wechat_appID"));
			cons.setWechat_cerUrl(prop.getProperty("wechat_cerUrl"));
			cons.setWechat_create_order_url(prop.getProperty("wechat_create_order_url"));
			cons.setWechat_down_load_bill_url(prop.getProperty("wechat_down_load_bill_url"));
			cons.setWechat_key(prop.getProperty("wechat_key"));
			cons.setWechat_mchID(prop.getProperty("wechat_mchID"));
			cons.setWechat_query_trade_url(prop.getProperty("wechat_query_trade_url"));
			cons.setWechat_refund_query_url(prop.getProperty("wechat_refund_query_url"));
			cons.setWechat_refund_url(prop.getProperty("wechat_refund_url"));
			cons.setWechat_notify_url(prop.getProperty("wechat_notify_url"));
			
			cons.setIs_junit(Integer.valueOf(prop.getProperty("is_junit", "0")));
			
			cons.setRefund_day(Integer.valueOf(prop.getProperty("refund_day", "180")));
			cons.setChanpay_cj_account_no(prop.getProperty("chanpay_cj_account_no", ""));
			cons.setChanpay_cj_business_code(prop.getProperty("chanpay_cj_business_code", ""));
			cons.setChanpay_cj_merchant_id(prop.getProperty("chanpay_cj_merchant_id", ""));
			cons.setChanpay_cj_merchant_name(prop.getProperty("chanpay_cj_merchant_name", ""));
			cons.setChanpay_cj_product_no(prop.getProperty("chanpay_cj_product_no", ""));
			

			
			cons.setWechat_qr_appID(prop.getProperty("wechat_qr_appID"));
			cons.setWechat_qr_cerUrl(prop.getProperty("wechat_qr_cerUrl"));
			cons.setWechat_qr_create_order_url(prop.getProperty("wechat_qr_create_order_url"));
			cons.setWechat_qr_down_load_bill_url(prop.getProperty("wechat_qr_down_load_bill_url"));
			cons.setWechat_qr_key(prop.getProperty("wechat_qr_key"));
			cons.setWechat_qr_mchID(prop.getProperty("wechat_qr_mchID"));
			cons.setWechat_qr_query_trade_url(prop.getProperty("wechat_qr_query_trade_url"));
			cons.setWechat_qr_refund_query_url(prop.getProperty("wechat_qr_refund_query_url"));
			cons.setWechat_qr_refund_url(prop.getProperty("wechat_qr_refund_url"));
			cons.setWechat_qr_notify_url(prop.getProperty("wechat_qr_notify_url"));
			cons.setWechat_qr_overtime(Integer.valueOf(prop.getProperty("wechat_qr_overtime", "60000")));
			cons.setWechat_qr_close_order_url(prop.getProperty("wechat_qr_close_order_url"));
			cons.setWechat_qr_short_url(prop.getProperty("wechat_qr_short_url"));
			
			cons.setWeb_cash_url(prop.getProperty("web_cash_url"));
			cons.setZlebank_coopinsti_code(prop.getProperty("zlebank_coopinsti_code"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static synchronized ConsUtil getInstance(){
		if(util==null){
			util = new ConsUtil();
		}
		return util;
	}
	
}
