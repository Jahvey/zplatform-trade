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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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
import com.zlebank.zplatform.trade.chanpay.bean.cj.G40003Bean;
import com.zlebank.zplatform.trade.chanpay.service.ChanPayService;
import com.zlebank.zplatform.trade.chanpay.service.impl.CjMsgSendService;
import com.zlebank.zplatform.trade.chanpay.service.impl.CjMsgSendService.sendAndGetBytes_Response;
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
public class CJGG40003Test {
	public static final Log LOG = LogFactory.getLog(CJGG40003Test.class);
	@Autowired
	private CjMsgSendService cjMsgSendService;
	@Autowired
	private ChanPayService chanPayService;
	
	@Test
	public void sendMessage() {
		G40003Bean data = new G40003Bean();
		data.setMertid("cp2016011227674");
		data.setSubMertid("YS0001");
		data.setBillType("00");
		data.setBillDay("20160630");
		data.setReqSn(U.createUUID());
		buildCjmsgAndSend(data);
		
	}//metho
	
	@Test
	public void sendMessage1() {
		G40003Bean data = new G40003Bean();
		data.setMertid("cp2016011227674");
		data.setSubMertid("YS0001");
		data.setBillType("00");
		data.setBillDay("20160630");
		data.setReqSn(U.createUUID());
		chanPayService.getRecAccFile(data);
		
	}//metho
	
	/** 组织Cj报文，并发送 */
	private void buildCjmsgAndSend(G40003Bean data) {
		 OutputStream os=null;
		try {
			String cjReqmsg = buildCjmsg(data);
			// 签名
			CjSignHelper singHelper = new CjSignHelper();
			String signMsg = singHelper.signXml$Add(cjReqmsg);
			sendAndGetBytes_Response res= cjMsgSendService.sendAndGetBytes(signMsg);
			byte[] array=res.data;
			String localfile = "G://aaa.zip";
            File file = new File(localfile);
            if(!file.exists()){
                file.createNewFile();
            }
            os = new FileOutputStream(file);
             os.write(array);
            os.flush();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}finally{
			if(os!=null){
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}//method
	
	private void parseCjMsgToDto(String cjRespmsg, G40003Bean data) throws Exception {
		Document reqDoc = DocumentHelper.parseText(cjRespmsg);

		Element msgEl = reqDoc.getRootElement();
		Element infoEl = msgEl.element("INFO");

		data.setRetCode(infoEl.elementText("RET_CODE"));
		data.setErrMsg(infoEl.elementText("ERR_MSG"));
		data.setTimestamp(infoEl.elementText("TIMESTAMP"));
		LOG.info("响应信息：retcode[" + data.getRetCode() + "], errmsg[" + data.getErrMsg() + "]");
	}//method

	public String buildCjmsg(G40003Bean data) {
		Document doc = DocumentHelper.createDocument();
		Element msgEl = doc.addElement("MESSAGE");

		Element infoEl = msgEl.addElement("INFO");
		infoEl.addElement("TRX_CODE").setText(Cj.XMLMSG_TRANS_CODE_对账文件下载);
		infoEl.addElement("VERSION").setText(Cj.XMLMSG_VERSION_01);
		infoEl.addElement("MERCHANT_ID").setText(U.nvl(data.getMertid()));
		infoEl.addElement("REQ_SN").setText(U.nvl(data.getReqSn()));
		infoEl.addElement("TIMESTAMP").setText(U.getCurrentTimestamp());
		infoEl.addElement("SIGNED_MSG").setText("");

		Element bodyEl = msgEl.addElement("BODY");
		bodyEl.addElement("BILL_TYPE").setText(U.nvl(data.getBillType()));
		if(data.getBillMonth()!=null){
			bodyEl.addElement("BILL_MONTH").setText(U.nvl(data.getBillMonth()));
		}else if(data.getBillDay()!=null){
			bodyEl.addElement("BILL_DAY").setText(U.nvl(data.getBillDay()));
		}
		
		
		String xml = Cj.formatXml_UTF8(doc);
		LOG.info("产生G40003下载对账单：" + U.substringByByte(xml, 512));
		return xml;
	}//method
}
