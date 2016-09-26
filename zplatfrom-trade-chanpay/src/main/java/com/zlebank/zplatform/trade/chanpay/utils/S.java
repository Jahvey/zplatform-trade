/**
 * 文件名： S.java
 * 建立时间： 2012-3-21 上午09:27:42
 * 创建人： SongCheng
 */
package com.zlebank.zplatform.trade.chanpay.utils;

import java.io.File;
import java.math.BigDecimal;

/**
 * 项目用常量配置信息，加载 config/cfg.ini 文件中的配置
 * 取配置信息可以通过 S.INI.get(....) 方法取值
 *
 * @author SongCheng
 */
public class S {

	public static InIFile INI;

	/////////////////////////[ INI 组名称列表 ]/////////////////////////
	public static final String INI_GROUP_ = "";

	/////////////////////////[ 字符集编码 ]/////////////////////////
	public static final String ENCODING_gbk = "GBK";
	public static final String ENCODING_iso88591 = "ISO8859-1";
	public static final String ENCODING_utf8 = "UTF-8";

	/////////////////////////[ HTTP请求类型 ]/////////////////////////
	public static final String REQUEST_METHOD_GET = "get";
	public static final String REQUEST_METHOD_POST = "post";

	/////////////////////////[ request中常用属性名 ]/////////////////////////
	public static final String REQUEST_ATTR_NAME_ = "";
	public static final String REQUEST_ATTR_NAME_errorMessage = "REQUEST_ATTR_NAME_errorMessage";
	public static final String REQUEST_ATTR_NAME_infoMessage = "REQUEST_ATTR_NAME_infoMessage";
	public static final String REQUEST_ATTR_NAME_jsonMessage = "REQUEST_ATTR_NAME_jsonMessage";
	public static final String REQUEST_ATTR_NAME_params = "REQUEST_ATTR_NAME_params";
	public static final String REQUEST_ATTR_NAME_url = "REQUEST_ATTR_NAME_url";

	/////////////////////////[ struts中常用属性定义 ]/////////////////////////
	/** Struts全局跳转页面命名 */
	public static final String FORWARD_NAME_errorPage = "errorPage";
	public static final String FORWARD_NAME_infoPage = "infoPage";
	public static final String FORWARD_NAME_jsonPage = "jsonPage";
	public static final String FORWARD_NAME_autoSubmitForm = "autoSubmitForm";

	/////////////////////////[ SPRING中常用属性定义 ]/////////////////////////
	public static final String[] SPRING_XML_FILE_PATH = { "classpath:/spring/applicationContext.xml", "classpath:/spring/daoContext.xml",
			"classpath:/spring/serviceContext-table.xml", "file:./WebRoot/WEB-INF/action-servlet-table.xml", "file:./WebRoot/WEB-INF/action-servlet.xml" };

	public static final String BEAN_NAME_jdbcTemplate = "jdbcTemplate";
	public static final String BEAN_NAME_simpleJdbcTemplate = "simpleJdbcTemplate";
	public static final String BEAN_NAME_hibernateTemplate = "hibernateTemplate";
	public static final String BEAN_NAME_transactionTemplate = "transactionTemplate";

	public static final String BEAN_NAME_mailService = "mailService";

	public static final String BEAN_NAME_Action_login01 = "/login01";
	public static final String BEAN_NAME_Action_appsys01 = "/appsys01";

	/////////////////////////[ 其他常用属性定义 ]/////////////////////////
	/** 错误信息最大长度 */
	public static final int ERRMSG_maxLength = 2000;
	/** 模拟测试环境模式 */
	public static boolean MOCK_ENV_MODE = false;
	/** WEB程序上下文路径 */
	public static String WEB_CONTEXT_PATH;
	/** 默认用户登录密码 */
	public static final String DEFAULT_USER_PASSWORD = "000000";
	/** BigDecimal 100 */
	public static final BigDecimal BIG_DECIMAL_100 = new BigDecimal(100);
	/** WEB上下文URL前置地址 */
	public static String WEB_BASE_URL;

	/////////////////////////[ 网关中 SESSION 的常用属性定义 ]/////////////////////////
	public static final String SESSION_ATTR_NAME_ = "";
	public static final String SESSION_ATTR_NAME_userCode = "SESSION_ATTR_NAME_userCode";
	public static final String SESSION_ATTR_NAME_userinfo = "userinfo";
	public static final String SESSION_ATTR_NAME_loginInfo = "loginInfo";

	/** 保存动态图片认证码 */
	public static final String SESSION_ATTR_NAME_makeCodePicture = "GW_com.util.MakeCode";

	/////////////////////////[ 证书系统常用属性定义 ]/////////////////////////
	public static final String RA_SERVICE_URL = "http://172.20.6.143:8081/TopCA/services/userAPI?wsdl";
	public static final String RA_organuzation = "畅捷通技术有限公司";
	public static final String RA_orgunit = "技术研发部RA";
	public static final String RA_account_hash = "2B3F460DCFEE9BD9D963975D6389E18C";
	public static final String RA_enroll_model = "AA";
	public static final String RA_renew_model = "AA";
	public static final boolean RA_isKMC = false;
	public static final String RA_aapassword = "password";
	public static final int RA_validity = 730;
	public static final String RA_cvm_config = "D:/95-work/330-yonyon_wksp2/topca/WebContent/WEB-INF/cvm.xml";

	/////////////////////////[ 初始化配置文件 ]/////////////////////////
	static {
		String path = "/home/web/trade/chanpay/";
		File file = new File(path+ "cfg.ini");
		if(!file.exists()){
			INI = new InIFile("classpath:/cfg.ini");
		    file = null;
		}else{
			INI = new InIFile(path+ "cfg.ini");
		}
		//------------------------------------------

		// [default]
		WEB_CONTEXT_PATH = INI.get("WEB_CONTEXT_PATH");
		WEB_BASE_URL = INI.get("WEB_BASE_URL");
		//------------------------------------------
	}//static

}//class
