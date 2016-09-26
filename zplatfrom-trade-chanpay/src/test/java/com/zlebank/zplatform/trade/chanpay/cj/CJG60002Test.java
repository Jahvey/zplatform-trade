/* 
 * CJG60002Test.java  
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

import com.zlebank.zplatform.trade.chanpay.bean.cj.G60002Bean;
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
 * @date 2016年6月21日 下午4:41:49
 * @since 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/*.xml")
public class CJG60002Test {
	public static final Log LOG = LogFactory.getLog(CJG60001Test.class);
	@Autowired
	private CjMsgSendService cjMsgSendService;
	@Test
	public void sendMessage() {
		try {
			G60002Bean data = new G60002Bean();
			data.setReqSn(U.createUUID());
			
			data.setMertid("cp2016011227674");
			data.setQryReqSn("5c87e4ad26a14842868cdd0ee3c8da4f");
			
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
	private void buildCjmsgAndSend(G60002Bean data) {
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
	
	private void parseCjMsgToDto(String cjRespmsg, G60002Bean data) throws Exception {
		Document reqDoc = DocumentHelper.parseText(cjRespmsg);

		Element msgEl = reqDoc.getRootElement();
		Element infoEl = msgEl.element("INFO");

		data.setRetCode(infoEl.elementText("RET_CODE"));
		data.setErrMsg(infoEl.elementText("ERR_MSG"));
		data.setTimestamp(infoEl.elementText("TIMESTAMP"));
		LOG.info("响应信息：retcode[" + data.getRetCode() + "], errmsg[" + data.getErrMsg() + "]");
		
		Element bodyEl = msgEl.element("BODY");
		if (bodyEl == null) {
			return;
		}
		Element batchEl = bodyEl.element("BATCH");
		if(batchEl == null){
			return;
		}
		data.setBatchRryReqSn(batchEl.elementText("QRY_REQ_SN"));
		data.setBatchRetCode(batchEl.elementText("RET_CODE"));
		data.setBatchErrMsg(batchEl.elementText("ERR_MSG"));
		
		Element transEl = bodyEl.element("TRANS_DETAILS");
		if(transEl == null){
			return;
		}
		Element dtlEl = transEl.element("DTL");
		if(dtlEl == null){
			return;
		}
		data.setDtlsn(dtlEl.elementText("SN"));
		data.setDtlRetCode(dtlEl.elementText("RET_CODE"));
		data.setDtlErrMsg(dtlEl.elementText("ERR_MSG"));
		data.setDtlaccountNo(dtlEl.elementText("ACCOUNT_NO"));
		data.setDtlaccountName(dtlEl.elementText("ACCOUNT_NAME"));
		
	}//method
	
	public String buildCjmsg(G60002Bean data){
		Document doc = DocumentHelper.createDocument();
		Element msgEl = doc.addElement("MESSAGE");

		Element infoEl = msgEl.addElement("INFO");
		infoEl.addElement("TRX_CODE").setText(Cj.XMLMSG_TRANS_CODE_实名认证结果查询);
		infoEl.addElement("VERSION").setText(Cj.XMLMSG_VERSION_01);
		infoEl.addElement("MERCHANT_ID").setText(U.nvl(data.getMertid()));
		infoEl.addElement("REQ_SN").setText(U.nvl(data.getReqSn()));
		infoEl.addElement("TIMESTAMP").setText(U.getCurrentTimestamp());
		infoEl.addElement("SIGNED_MSG").setText("");

		Element bodyEl = msgEl.addElement("BODY");
		bodyEl.addElement("QRY_REQ_SN").setText(U.nvl(data.getQryReqSn()));		
		
		String xml = Cj.formatXml_UTF8(doc);
		LOG.info("产生G60002实名认证结果查询：" + U.substringByByte(xml, 1024));
		return xml;
	}
}
