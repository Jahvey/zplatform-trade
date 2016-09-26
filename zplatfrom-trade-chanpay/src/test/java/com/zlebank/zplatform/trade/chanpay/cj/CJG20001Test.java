/* 
 * CJG20001Test.java  
 * 
 * version TODO
 *
 * 2016年5月20日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.chanpay.cj;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zlebank.zplatform.trade.chanpay.bean.cj.G20001Bean;
import com.zlebank.zplatform.trade.chanpay.service.impl.CjMsgSendService;
import com.zlebank.zplatform.trade.chanpay.utils.Cj;
import com.zlebank.zplatform.trade.chanpay.utils.CjSignHelper;
import com.zlebank.zplatform.trade.chanpay.utils.CjSignHelper.VerifyResult;
import com.zlebank.zplatform.trade.chanpay.utils.U;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年5月20日 上午11:27:09
 * @since 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/*.xml")
public class CJG20001Test {
	public static final Log LOG = LogFactory.getLog(CJG10001Test.class);
	@Autowired
	private CjMsgSendService cjMsgSendService;
	
	@Test
	public void sendMessage() {
		G20001Bean data = new G20001Bean();
		data.setMertid("cp2016011227674");
		data.setQryReqSn("35003d8078d1404fbbd5f67456ed9eeb");
		data.setSummary("测试DEMO");
		data.setPostscript("测试DEMO");
		data.setReqSn(U.createUUID());
		buildCjmsgAndSend(data);
	}//method
	
	/** 组织Cj报文，并发送 */
	private void buildCjmsgAndSend(G20001Bean data) {
		try {
			String cjReqmsg = buildCjmsg(data);

			// 签名
			CjSignHelper singHelper = new CjSignHelper();
			String signMsg = singHelper.signXml$Add(cjReqmsg);
			
			// 发送报文
			String cjRespmsg = cjMsgSendService.sendAndGetString(signMsg);

			// 验证签名
			VerifyResult verifyResult = singHelper.verifyCjServerXml(cjRespmsg);
			if (!verifyResult.result) {
				throw new Exception("验证CJ返回数据的签名失败！" + verifyResult.errMsg);
			}
			parseCjMsgToDto(cjRespmsg, data);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}//method
	
	private void parseCjMsgToDto(String cjRespmsg, G20001Bean data) throws Exception {
		Document reqDoc = DocumentHelper.parseText(cjRespmsg);

		Element msgEl = reqDoc.getRootElement();
		Element infoEl = msgEl.element("INFO");

		data.setRetCode(infoEl.elementText("RET_CODE"));
		data.setErrMsg(infoEl.elementText("ERR_MSG"));
		data.setTimestamp(infoEl.elementText("TIMESTAMP"));
		
		Element bodyEl = msgEl.element("BODY");
		if (bodyEl == null) {
			return;
		}
		data.setCharge(StringUtils.isBlank(bodyEl.elementText("CHARGE"))?0:Long.parseLong(bodyEl.elementText("CHARGE")));
		data.setCorpAcctNo(bodyEl.elementText("CORP_ACCT_NO"));
		data.setCorpAcctName(bodyEl.elementText("CORP_ACCT_NAME"));
		data.setAccountNo(bodyEl.elementText("ACCOUNT_NO"));
		data.setAccountName(bodyEl.elementText("ACCOUNT_NAME"));
		data.setAmount(StringUtils.isBlank(bodyEl.elementText("AMOUNT"))?0:Long.parseLong(bodyEl.elementText("AMOUNT")));
		data.setSummary(bodyEl.elementText("SUMMARY"));
		data.setCorpFlowNo(bodyEl.elementText("CORP_FLOW_NO"));
		data.setPostscript(bodyEl.elementText("POSTSCRIPT"));
		LOG.info("响应信息：retcode[" + data.getRetCode() + "], errmsg[" + data.getErrMsg() + "]");
	}//method

	public String buildCjmsg(G20001Bean data) {
		Document doc = DocumentHelper.createDocument();
		Element msgEl = doc.addElement("MESSAGE");

		Element infoEl = msgEl.addElement("INFO");
		infoEl.addElement("TRX_CODE").setText(Cj.XMLMSG_TRANS_CODE_实时交易结果查询);
		infoEl.addElement("VERSION").setText(Cj.XMLMSG_VERSION_01);
		infoEl.addElement("MERCHANT_ID").setText(U.nvl(data.getMertid()));
		infoEl.addElement("REQ_SN").setText(U.nvl(data.getReqSn()));
		infoEl.addElement("TIMESTAMP").setText(U.getCurrentTimestamp());
		infoEl.addElement("SIGNED_MSG").setText("");

		Element bodyEl = msgEl.addElement("BODY");
		bodyEl.addElement("QRY_REQ_SN").setText(U.nvl(data.getQryReqSn()));

		String xml = Cj.formatXml_UTF8(doc);
		LOG.info("产生G20001文件上传：" + U.substringByByte(xml, 512));
		return xml;
	}//method
}
