/* 
 * CJG60003Test.java  
 * 
 * version TODO
 *
 * 2016年6月21日 
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

import com.zlebank.zplatform.trade.chanpay.bean.cj.G60003Bean;
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
 * @date 2016年6月21日 下午4:48:55
 * @since 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/*.xml")
public class CJG60003Test {

	public static final Log LOG = LogFactory.getLog(CJG60001Test.class);
	@Autowired
	private CjMsgSendService cjMsgSendService;
	
	@Test
	public void sendMessage() {
		try {
			G60003Bean data = new G60003Bean();
			data.setReqSn(U.createUUID());
			
			data.setMertid("cp2016011227674");
			data.setCorpAccountNo("05101");
			data.setBusinessCode("610001");
			data.setAlterType("0");
			data.setProtocolType("0");
			data.setSn("UUID");
			data.setProtocolNo("123456");
			data.setBankName("中国建设银行广州东山广场分理处");
			data.setBankCode("710584000001");
			data.setAccountType("00");
			data.setAccountNo("6217000420000275879");
			data.setAccountName("王帅");
			data.setIdType("0");
			data.setId("150223199310160050");
			data.setTel("15101125144");
			data.setBeginDate("06/21/2016");
			data.setEndDate("07/21/2017");
			
			
			buildCjmsgAndSend(data);
			
			/*response.setContentType(ret.contentType);
			response.addHeader("Content-Disposition", ret.contentDisposition);
			response.addHeader(Cj.PROP_NAME_RET_CODE, ret.retcode);
			response.addHeader(Cj.PROP_NAME_ERR_MSG, ret.errmsg);
			response.setCharacterEncoding("utf-8");
			ServletOutputStream out = response.getOutputStream();*/
			/*out.write(ret.data);
			out.flush();
			out.close();*/
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}//method
	/** 组织Cj报文，并发送 */
	private void buildCjmsgAndSend(G60003Bean data) {
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
	
	private void parseCjMsgToDto(String cjRespmsg, G60003Bean data) throws Exception {
		Document reqDoc = DocumentHelper.parseText(cjRespmsg);

		Element msgEl = reqDoc.getRootElement();
		Element infoEl = msgEl.element("INFO");
		
		data.setRetCode(infoEl.elementText("RET_CODE"));
		data.setErrMsg(infoEl.elementText("ERR_MSG"));
		data.setTimestamp(infoEl.elementText("TIMESTAMP"));
		LOG.info("响应信息：retcode[" + data.getRetCode() + "], errmsg[" + data.getErrMsg() + "]");
	}//method
	
	public String buildCjmsg(G60003Bean data){
		Document doc = DocumentHelper.createDocument();
		Element msgEl = doc.addElement("MESSAGE");

		Element infoEl = msgEl.addElement("INFO");
		infoEl.addElement("TRX_CODE").setText(Cj.XMLMSG_TRANS_CODE_协议签约);
		infoEl.addElement("VERSION").setText(Cj.XMLMSG_VERSION_01);
		infoEl.addElement("MERCHANT_ID").setText(U.nvl(data.getMertid()));
		infoEl.addElement("REQ_SN").setText(U.nvl(data.getReqSn()));
		infoEl.addElement("TIMESTAMP").setText(U.getCurrentTimestamp());
		infoEl.addElement("SIGNED_MSG").setText("");

		Element bodyEl = msgEl.addElement("BODY");

		Element batch = bodyEl.addElement("BATCH");
		batch.addElement("CORP_ACCT_NO").setText(U.nvl(data.getCorpAccountNo()));
		batch.addElement("BUSINESS_CODE").setText(U.nvl(data.getBusinessCode()));
		batch.addElement("ALTER_TYPE").setText(U.nvl(data.getAlterType()));
		batch.addElement("PROTOCOL_TYPE").setText(U.nvl(data.getProtocolType()));
		
		Element details = bodyEl.addElement("TRANS_DETAILS");
		Element dtl = details.addElement("DTL");
		dtl.addElement("SN").setText(U.nvl(data.getSn()));
		dtl.addElement("PROTOCOL_NO").setText(U.nvl(data.getProtocolNo()));
		dtl.addElement("BANK_NAME").setText(U.nvl(data.getBankName()));
		dtl.addElement("BANK_CODE").setText(U.nvl(data.getBankCode()));
		dtl.addElement("ACCOUNT_TYPE").setText(U.nvl(data.getAccountType()));
		dtl.addElement("ACCOUNT_NO").setText(U.nvl(data.getAccountNo()));
		dtl.addElement("ACCOUNT_NAME").setText(U.nvl(data.getAccountName()));
		dtl.addElement("ID_TYPE").setText(U.nvl(data.getIdType()));
		dtl.addElement("ID").setText(U.nvl(data.getId()));
		dtl.addElement("BEGIN_DATE").setText(U.nvl(Cj.formatDate_string(Cj.parseDate(data.getBeginDate()))));	
		dtl.addElement("END_DATE").setText(U.nvl(data.getEndDate()));
		dtl.addElement("TEL").setText(U.nvl(data.getTel()));
		
		String xml = Cj.formatXml_UTF8(doc);
		LOG.info("产生G60003协议签约：" + U.substringByByte(xml, 1024));
		return xml;
	}

}
