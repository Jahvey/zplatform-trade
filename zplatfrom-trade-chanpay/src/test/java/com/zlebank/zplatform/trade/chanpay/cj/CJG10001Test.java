/* 
 * CJTest.java  
 * 
 * version TODO
 *
 * 2016年5月19日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.chanpay.cj;

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

import com.zlebank.zplatform.trade.chanpay.bean.cj.G10001Bean;
import com.zlebank.zplatform.trade.chanpay.service.impl.CjMsgSendService;
import com.zlebank.zplatform.trade.chanpay.utils.Cj;
import com.zlebank.zplatform.trade.chanpay.utils.CjSignHelper;
import com.zlebank.zplatform.trade.chanpay.utils.CjSignHelper.VerifyResult;
import com.zlebank.zplatform.trade.chanpay.utils.U;
import com.zlebank.zplatform.trade.service.NotifyInsteadURLService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年5月19日 下午12:53:38
 * @since 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/*.xml")
public class CJG10001Test {
	public static final Log LOG = LogFactory.getLog(CJG10001Test.class);
	@Autowired
	private CjMsgSendService cjMsgSendService;
	
	@Test
	public void sendMessage() {
		G10001Bean data = new G10001Bean();
		data.setMertid("cp2016011227674");
		data.setBusinessCode("05101");
		data.setCorpAccNo("201407301524");
		data.setProductCode("50110002");
		data.setSubMertid("YS0001");
		data.setBankGeneralName("中国工商银行");
		data.setAccountNo("201407301524");
		data.setAccountName("畅捷通信息技术股份有限公司");
		data.setProvince("北京");
		data.setCity("北京");
		data.setBankName("中国工商银行北京海淀中关村分理处");
		data.setBankCode("710584000001");
		data.setDrctBankCode("710584000001");
		data.setProtocolNo("201407301524");
		data.setCurrency("CNY");
		data.setAmount(99L);
		data.setIdType("0");
		data.setId("1234567890");
		data.setTel("1234567890");
		data.setCorpFlowNo("1234567890");
		data.setSummary("测试DEMO");
		data.setPostscript("测试DEMO");
		data.setAccountProp("0");
		data.setAccountType("00");
		data.setReqSn(U.createUUID());
		buildCjmsgAndSend(data);
		
	}//metho
	
	/** 组织Cj报文，并发送 */
	private void buildCjmsgAndSend(G10001Bean data) {
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
	
	private void parseCjMsgToDto(String cjRespmsg, G10001Bean data) throws Exception {
		Document reqDoc = DocumentHelper.parseText(cjRespmsg);

		Element msgEl = reqDoc.getRootElement();
		Element infoEl = msgEl.element("INFO");

		data.setRetCode(infoEl.elementText("RET_CODE"));
		data.setErrMsg(infoEl.elementText("ERR_MSG"));
		data.setTimestamp(infoEl.elementText("TIMESTAMP"));
		LOG.info("响应信息：retcode[" + data.getRetCode() + "], errmsg[" + data.getErrMsg() + "]");
	}//method

	public String buildCjmsg(G10001Bean data) {
		Document doc = DocumentHelper.createDocument();
		Element msgEl = doc.addElement("MESSAGE");

		Element infoEl = msgEl.addElement("INFO");
		infoEl.addElement("TRX_CODE").setText(Cj.XMLMSG_TRANS_CODE_单笔实时收款);
		infoEl.addElement("VERSION").setText(Cj.XMLMSG_VERSION_01);
		infoEl.addElement("MERCHANT_ID").setText(U.nvl(data.getMertid()));
		infoEl.addElement("REQ_SN").setText(U.nvl(data.getReqSn()));
		infoEl.addElement("TIMESTAMP").setText(U.getCurrentTimestamp());
		infoEl.addElement("SIGNED_MSG").setText("");

		Element bodyEl = msgEl.addElement("BODY");
		bodyEl.addElement("BUSINESS_CODE").setText(U.nvl(data.getBusinessCode()));
		bodyEl.addElement("CORP_ACCT_NO").setText(U.nvl(data.getCorpAccNo()));
		bodyEl.addElement("PRODUCT_CODE").setText(U.nvl(data.getProductCode()));
		bodyEl.addElement("ACCOUNT_PROP").setText(U.nvl(data.getAccountProp()));
		bodyEl.addElement("SUB_MERCHANT_ID").setText(U.nvl(data.getSubMertid()));
		bodyEl.addElement("BANK_GENERAL_NAME").setText(U.nvl(data.getBankGeneralName()));
		bodyEl.addElement("ACCOUNT_TYPE").setText(U.nvl(data.getAccountType()));
		bodyEl.addElement("ACCOUNT_NO").setText(U.nvl(data.getAccountNo()));
		bodyEl.addElement("ACCOUNT_NAME").setText(U.nvl(data.getAccountName()));
		bodyEl.addElement("PROVINCE").setText(U.nvl(data.getProvince()));
		bodyEl.addElement("CITY").setText(U.nvl(data.getCity()));
		bodyEl.addElement("BANK_NAME").setText(U.nvl(data.getBankName()));
		bodyEl.addElement("BANK_CODE").setText(U.nvl(data.getBankCode()));
		bodyEl.addElement("DRCT_BANK_CODE").setText(U.nvl(data.getDrctBankCode()));
		bodyEl.addElement("PROTOCOL_NO").setText(U.nvl(data.getProtocolNo()));
		bodyEl.addElement("CURRENCY").setText(U.nvl(data.getCurrency()));
		bodyEl.addElement("AMOUNT").setText(U.nvl(data.getAmount()));
		bodyEl.addElement("ID_TYPE").setText(U.nvl(data.getIdType()));
		bodyEl.addElement("ID").setText(U.nvl(data.getId()));
		bodyEl.addElement("TEL").setText(U.nvl(data.getTel()));
		bodyEl.addElement("CORP_FLOW_NO").setText(U.nvl(data.getCorpFlowNo()));
		bodyEl.addElement("SUMMARY").setText(U.nvl(data.getSummary()));
		bodyEl.addElement("POSTSCRIPT").setText(U.nvl(data.getPostscript()));
		String xml = Cj.formatXml_UTF8(doc);
		LOG.info("产生G10001单笔实时收款：" + U.substringByByte(xml, 512));
		return xml;
	}//method
}
