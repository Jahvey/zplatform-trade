/* 
 * ChanPayCollectMoneyServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年6月29日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.chanpay.service.impl;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlebank.zplatform.commons.utils.DateUtil;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bean.enums.TradeStatFlagEnum;
import com.zlebank.zplatform.trade.chanpay.bean.cj.G10001Bean;
import com.zlebank.zplatform.trade.chanpay.bean.cj.G20001Bean;
import com.zlebank.zplatform.trade.chanpay.bean.cj.G60001Bean;
import com.zlebank.zplatform.trade.chanpay.bean.cj.G60002Bean;
import com.zlebank.zplatform.trade.chanpay.bean.cj.G60003Bean;
import com.zlebank.zplatform.trade.chanpay.bean.cj.G60004Bean;
import com.zlebank.zplatform.trade.chanpay.enums.BodyRetEnmu;
import com.zlebank.zplatform.trade.chanpay.service.ChanPayCollectMoneyService;
import com.zlebank.zplatform.trade.chanpay.utils.Cj;
import com.zlebank.zplatform.trade.chanpay.utils.CjSignHelper;
import com.zlebank.zplatform.trade.chanpay.utils.CjSignHelper.VerifyResult;
import com.zlebank.zplatform.trade.chanpay.utils.U;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.service.IRouteConfigService;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.OrderNumber;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年6月29日 下午3:28:45
 * @since 
 */
@Service("chanPayCollectMoneyService")
public class ChanPayCollectMoneyServiceImpl implements ChanPayCollectMoneyService{

	
	private static final Log log = LogFactory.getLog(ChanPayCollectMoneyServiceImpl.class);
	@Autowired
	private IRouteConfigService routeConfigService;
	@Autowired
	private ITxnsLogService txnsLogService;
	@Autowired
	private CjMsgSendService cjMsgSendService;
	/**
	 *
	 * @param tradeBean
	 * @return
	 * @throws InterruptedException 
	 */
	@Override
	public ResultBean realNameAuth(TradeBean tradeBean) {
		ResultBean resultBean = null;
		String bankNumber = routeConfigService.getCardPBCCode(tradeBean.getCardNo()).get("PBC_BANKCODE")+"";
		String subBankName = routeConfigService.getCardPBCCode(tradeBean.getCardNo()).get("BANKNAME")+"";
		String bankName = routeConfigService.getBankName(bankNumber);
		if(StringUtil.isEmpty(bankNumber)){
			resultBean = new ResultBean("T000", "无法获取有效的人行联行号");
			return resultBean;
		}
		G60001Bean data = null;
		try {
			data = new G60001Bean();
			data.setReqSn(tradeBean.getPayOrderNo());
			data.setMertid(ConsUtil.getInstance().cons.getChanpay_cj_merchant_id());
			data.setBankGeneralName(bankName);
			data.setSn(U.createUUID());
			data.setBankName(subBankName);
			data.setBankCode(bankNumber);
			data.setAccountType("00");
			data.setAccountNo(tradeBean.getCardNo());
			data.setAccountName(tradeBean.getAcctName());
			data.setIdType("0");
			data.setId(tradeBean.getCertId());
			data.setTel(tradeBean.getMobile());
			buildCjmsgAndSend(data);
		} catch (TradeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultBean = new ResultBean(e.getCode(), e.getMessage());
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultBean = new ResultBean("T000", e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultBean = new ResultBean("T000", e.getMessage());
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultBean = new ResultBean("T000", e.getMessage());
		}
		
		if("0000".equals(data.getRetCode())){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for(int i = 0;i<5;i++){
				ResultBean queryResultBean = queryRealNameAuth(data.getReqSn());
				if(queryResultBean.isResultBool()){
					G60002Bean bean = (G60002Bean) queryResultBean.getResultObj();
					
					BodyRetEnmu bodyRetEnmu = BodyRetEnmu.fromValue(bean.getDtlRetCode());
					if(bodyRetEnmu==BodyRetEnmu.ACCEPTED||bodyRetEnmu==BodyRetEnmu.PROCESSING){
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						continue;
					}else if(bodyRetEnmu==BodyRetEnmu.SUCCESS){//交易成功
						data.setRetCode(bean.getDtlRetCode());
						data.setErrMsg(bean.getDtlErrMsg());
						resultBean = new ResultBean(data);
						break;
					}else if(bodyRetEnmu==BodyRetEnmu.FAILED){//交易失败
						data.setRetCode(bean.getDtlRetCode());
						data.setErrMsg(bean.getDtlErrMsg());
						resultBean = new ResultBean(data);
						resultBean.setResultBool(false);
						break;
					}else{//未知原因导致的失败
						data.setRetCode(bean.getDtlRetCode());
						data.setErrMsg(bean.getDtlErrMsg());
						resultBean = new ResultBean(data);
						resultBean.setResultBool(false);
						break;
					}
					
				}
				
			}
		}
		return resultBean;
	}
	
	/** 组织Cj报文，并发送 
	 * @throws IOException 
	 * @throws HttpException 
	 * @throws TradeException 
	 * @throws DocumentException */
	private void buildCjmsgAndSend(G60001Bean data) throws HttpException, IOException, TradeException, DocumentException {
		
			String cjReqmsg = buildCjmsg(data);
			// 签名
			CjSignHelper singHelper = new CjSignHelper();
			String signMsg = singHelper.signXml$Add(cjReqmsg);
			// 发送报文
			String cjRespmsg = cjMsgSendService.sendAndGetString(signMsg);
			// 验证签名
			VerifyResult verifyResult = singHelper.verifyCjServerXml(cjRespmsg);
			if (!verifyResult.result) {
				throw new TradeException("GW09");
			}
			parseCjMsgToDto(cjRespmsg, data);
		
	}
	
	private void parseCjMsgToDto(String cjRespmsg, G60001Bean data) throws DocumentException  {
		Document reqDoc = DocumentHelper.parseText(cjRespmsg);
		Element msgEl = reqDoc.getRootElement();
		Element infoEl = msgEl.element("INFO");
		data.setRetCode(infoEl.elementText("RET_CODE"));
		data.setErrMsg(infoEl.elementText("ERR_MSG"));
		data.setTimestamp(infoEl.elementText("TIMESTAMP"));
		log.info("响应信息：retcode[" + data.getRetCode() + "], errmsg[" + data.getErrMsg() + "]");
	}//method
	
	public String buildCjmsg(G60001Bean data){
		Document doc = DocumentHelper.createDocument();
		Element msgEl = doc.addElement("MESSAGE");

		Element infoEl = msgEl.addElement("INFO");
		infoEl.addElement("TRX_CODE").setText(Cj.XMLMSG_TRANS_CODE_实名认证);
		infoEl.addElement("VERSION").setText(Cj.XMLMSG_VERSION_01);
		infoEl.addElement("MERCHANT_ID").setText(U.nvl(data.getMertid()));
		infoEl.addElement("REQ_SN").setText(U.nvl(data.getReqSn()));
		infoEl.addElement("TIMESTAMP").setText(U.getCurrentTimestamp());
		infoEl.addElement("SIGNED_MSG").setText("");

		Element bodyEl = msgEl.addElement("BODY");
		Element batch = bodyEl.addElement("BATCH");
		batch.addElement("VALIDATE_MODE").setText("V002");
		
		Element details = bodyEl.addElement("TRANS_DETAILS");
		Element dtl = details.addElement("DTL");
		dtl.addElement("SN").setText(U.createUUID());
		dtl.addElement("BANK_GENERAL_NAME").setText(U.nvl(data.getBankGeneralName()));
		dtl.addElement("BANK_NAME").setText(U.nvl(data.getBankName()));
		dtl.addElement("BANK_CODE").setText(U.nvl(data.getBankCode()));
		dtl.addElement("ACCOUNT_TYPE").setText(U.nvl(data.getAccountType()));
		dtl.addElement("ACCOUNT_NO").setText(U.nvl(data.getAccountNo()));
		dtl.addElement("ACCOUNT_NAME").setText(U.nvl(data.getAccountName()));
		dtl.addElement("ID_TYPE").setText(U.nvl(data.getIdType()));
		dtl.addElement("ID").setText(U.nvl(data.getId()));
		dtl.addElement("TEL").setText(U.nvl(data.getTel()));			
		
		String xml = Cj.formatXml_UTF8(doc);
		log.info("产生G60001实名认证：" + U.substringByByte(xml, 1024));
		return xml;
	}

	/**
	 *
	 * @param qry_req_sn
	 * @return
	 */
	@Override
	public ResultBean queryRealNameAuth(String qry_req_sn) {
		ResultBean resultBean = null;
		try {
			G60002Bean data = new G60002Bean();
			data.setReqSn(U.createUUID());
			data.setMertid(ConsUtil.getInstance().cons.getChanpay_cj_merchant_id());
			data.setQryReqSn(qry_req_sn);
			buildCjmsgAndSend(data);
			resultBean = new ResultBean(data);
		} catch (TradeException e) {
			// TODO Auto-generated catch block
			resultBean = new ResultBean(e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			resultBean = new ResultBean("T000", e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			resultBean = new ResultBean("T000", e.getMessage());
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			resultBean = new ResultBean("T000", e.getMessage());
			e.printStackTrace();
		}
		
		
		return resultBean;
	}
	private void buildCjmsgAndSend(G60002Bean data) throws HttpException, IOException, TradeException, DocumentException {
		
			String cjReqmsg = buildCjmsg(data);

			// 签名
			CjSignHelper singHelper = new CjSignHelper();
			String signMsg = singHelper.signXml$Add(cjReqmsg);
			// 发送报文
			String cjRespmsg = cjMsgSendService.sendAndGetString(signMsg);
			// 验证签名
			VerifyResult verifyResult = singHelper.verifyCjServerXml(cjRespmsg);
			if (!verifyResult.result) {
				throw new TradeException("GW09");
			}
			parseCjMsgToDto(cjRespmsg, data);
		
	}
	
	private void parseCjMsgToDto(String cjRespmsg, G60002Bean data) throws DocumentException {
		Document reqDoc = DocumentHelper.parseText(cjRespmsg);

		Element msgEl = reqDoc.getRootElement();
		Element infoEl = msgEl.element("INFO");

		data.setRetCode(infoEl.elementText("RET_CODE"));
		data.setErrMsg(infoEl.elementText("ERR_MSG"));
		data.setTimestamp(infoEl.elementText("TIMESTAMP"));
		log.info("响应信息：retcode[" + data.getRetCode() + "], errmsg[" + data.getErrMsg() + "]");
		
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
		log.info("产生G60002实名认证结果查询：" + U.substringByByte(xml, 1024));
		return xml;
	}

	/**
	 *
	 * @param tradeBean
	 * @return
	 */
	@Override
	public ResultBean protocolSign(TradeBean tradeBean) {
		ResultBean resultBean = null;
		String bankNumber = routeConfigService.getCardPBCCode(tradeBean.getCardNo()).get("PBC_BANKCODE")+"";
		String subBankName = routeConfigService.getCardPBCCode(tradeBean.getCardNo()).get("BANKNAME")+"";
		//String bankName = routeConfigService.getBankName(bankNumber);
		if(StringUtil.isEmpty(bankNumber)){
			resultBean = new ResultBean("T000", "无法获取有效的人行联行号");
			return resultBean;
		}
		G60003Bean data = null;
		try {
			data = new G60003Bean();
			data.setReqSn(U.createUUID());
			data.setMertid(ConsUtil.getInstance().cons.getChanpay_cj_merchant_id());
			data.setCorpAccountNo(ConsUtil.getInstance().cons.getChanpay_cj_account_no());
			data.setBusinessCode(ConsUtil.getInstance().cons.getChanpay_cj_business_code());
			data.setAlterType("0");
			data.setProtocolType("0");
			data.setSn(U.createUUID());
			data.setProtocolNo(OrderNumber.getInstance().generateBindCardID()+"");
			data.setBankName(subBankName);
			data.setBankCode(bankNumber);
			data.setAccountType("00");
			data.setAccountNo(tradeBean.getCardNo());
			data.setAccountName(tradeBean.getAcctName());
			data.setIdType("0");
			data.setId(tradeBean.getCertId());
			data.setTel(tradeBean.getMobile());
			data.setBeginDate(DateUtil.getCurrentDate());
			data.setEndDate(DateUtil.formatDateTime(DateUtil.SIMPLE_DATE_FROMAT, DateUtil.skipDateTime(new Date(), 36000)));
			buildCjmsgAndSend(data);
			resultBean = new ResultBean(data);
		} catch (TradeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultBean = new ResultBean(e.getCode(), e.getMessage());
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultBean = new ResultBean("T000", e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultBean = new ResultBean("T000", e.getMessage());
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultBean = new ResultBean("T000", e.getMessage());
		}
		if("0000".equals(data.getRetCode())){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int i = 0;i<5;i++){
				ResultBean queryResultBean = queryProtocolSign(data.getReqSn());
				if(queryResultBean.isResultBool()){
					G60004Bean bean = (G60004Bean) queryResultBean.getResultObj();
					
					BodyRetEnmu bodyRetEnmu = BodyRetEnmu.fromValue(bean.getDtlRetCode());
					if(bodyRetEnmu==BodyRetEnmu.ACCEPTED||bodyRetEnmu==BodyRetEnmu.PROCESSING){
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						continue;
					}else if(bodyRetEnmu==BodyRetEnmu.SUCCESS){//交易成功
						data.setRetCode(bean.getDtlRetCode());
						data.setErrMsg(bean.getDtlErrMsg());
						resultBean = new ResultBean(data);
						break;
					}else if(bodyRetEnmu==BodyRetEnmu.FAILED){//交易失败
						data.setRetCode(bean.getDtlRetCode());
						data.setErrMsg(bean.getDtlErrMsg());
						resultBean = new ResultBean(data);
						resultBean.setResultBool(false);
						break;
					}else{//未知原因导致的失败
						data.setRetCode(bean.getDtlRetCode());
						data.setErrMsg(bean.getDtlErrMsg());
						resultBean = new ResultBean(data);
						resultBean.setResultBool(false);
						break;
					}
					
				}
				
			}
		}
		return resultBean;
	}
	
	/** 组织Cj报文，并发送 
	 * @throws IOException 
	 * @throws HttpException 
	 * @throws TradeException 
	 * @throws DocumentException 
	 **/
	private void buildCjmsgAndSend(G60003Bean data) throws HttpException, IOException, TradeException, DocumentException {
		String cjReqmsg = buildCjmsg(data);
		// 签名
		CjSignHelper singHelper = new CjSignHelper();
		String signMsg = singHelper.signXml$Add(cjReqmsg);
		// 发送报文
		String cjRespmsg = cjMsgSendService.sendAndGetString(signMsg);
		// 验证签名
		VerifyResult verifyResult = singHelper.verifyCjServerXml(cjRespmsg);
		if (!verifyResult.result) {
			throw new TradeException("GW09");
		}
		parseCjMsgToDto(cjRespmsg, data);
		
	}//method
	
	private void parseCjMsgToDto(String cjRespmsg, G60003Bean data) throws DocumentException {
		Document reqDoc = DocumentHelper.parseText(cjRespmsg);

		Element msgEl = reqDoc.getRootElement();
		Element infoEl = msgEl.element("INFO");
		
		data.setRetCode(infoEl.elementText("RET_CODE"));
		data.setErrMsg(infoEl.elementText("ERR_MSG"));
		data.setTimestamp(infoEl.elementText("TIMESTAMP"));
		log.info("响应信息：retcode[" + data.getRetCode() + "], errmsg[" + data.getErrMsg() + "]");
	}
	
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
		dtl.addElement("BEGIN_DATE").setText(U.nvl(data.getBeginDate()));	
		dtl.addElement("END_DATE").setText(U.nvl(data.getEndDate()));
		dtl.addElement("TEL").setText(U.nvl(data.getTel()));
		
		String xml = Cj.formatXml_UTF8(doc);
		log.info("产生G60003协议签约：" + U.substringByByte(xml, 1024));
		return xml;
	}
	
	
	

	/**
	 *
	 * @param qry_req_sn
	 * @return
	 */
	@Override
	public ResultBean queryProtocolSign(String qry_req_sn) {
		ResultBean resultBean = null;
		G60004Bean data = new G60004Bean();
		try {
			data.setReqSn(U.createUUID());
			data.setMertid(ConsUtil.getInstance().cons.getChanpay_cj_merchant_id());
			data.setQryReqSn(qry_req_sn);
			buildCjmsgAndSend(data);
			resultBean = new ResultBean(data);
		} catch (TradeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultBean = new ResultBean(e.getCode(), e.getMessage());
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultBean = new ResultBean("T000", e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultBean = new ResultBean("T000", e.getMessage());
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultBean = new ResultBean("T000", e.getMessage());
		}
		return resultBean;
	}
	
	/** 组织Cj报文，并发送 
	 * @throws IOException 
	 * @throws HttpException 
	 * @throws TradeException 
	 * @throws DocumentException */
	private void buildCjmsgAndSend(G60004Bean data) throws HttpException, IOException, TradeException, DocumentException {
		String cjReqmsg = buildCjmsg(data);
		// 签名
		CjSignHelper singHelper = new CjSignHelper();
		String signMsg = singHelper.signXml$Add(cjReqmsg);
		// 发送报文
		String cjRespmsg = cjMsgSendService.sendAndGetString(signMsg);
		// 验证签名
		VerifyResult verifyResult = singHelper.verifyCjServerXml(cjRespmsg);
		if (!verifyResult.result) {
			throw new TradeException("GW09");
		}
		parseCjMsgToDto(cjRespmsg, data);
		
	}//method
	
	private void parseCjMsgToDto(String cjRespmsg, G60004Bean data) throws DocumentException {
		Document reqDoc = DocumentHelper.parseText(cjRespmsg);

		Element msgEl = reqDoc.getRootElement();
		Element infoEl = msgEl.element("INFO");

		data.setRetCode(infoEl.elementText("RET_CODE"));
		data.setErrMsg(infoEl.elementText("ERR_MSG"));
		data.setTimestamp(infoEl.elementText("TIMESTAMP"));
		log.info("响应信息：retcode[" + data.getRetCode() + "], errmsg[" + data.getErrMsg() + "]");
		
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
		data.setDtlRpoNo(dtlEl.elementText("RPOTOCOL_NO"));
		data.setDtlRetCode(dtlEl.elementText("RET_CODE"));
		data.setDtlErrMsg(dtlEl.elementText("ERR_MSG"));
		data.setDtlaccountNo(dtlEl.elementText("ACCOUNT_NO"));
		data.setDtlaccountName(dtlEl.elementText("ACCOUNT_NAME"));
		data.setDtlBeginDate(dtlEl.elementText("BEGIN_DATE"));
		data.setDtlEndDate(dtlEl.elementText("END_DATE"));
		
		
	}//method
	
	public String buildCjmsg(G60004Bean data){
		Document doc = DocumentHelper.createDocument();
		Element msgEl = doc.addElement("MESSAGE");

		Element infoEl = msgEl.addElement("INFO");
		infoEl.addElement("TRX_CODE").setText(Cj.XMLMSG_TRANS_CODE_协议签约结果查询);
		infoEl.addElement("VERSION").setText(Cj.XMLMSG_VERSION_01);
		infoEl.addElement("MERCHANT_ID").setText(U.nvl(data.getMertid()));
		infoEl.addElement("REQ_SN").setText(U.nvl(data.getReqSn()));
		infoEl.addElement("TIMESTAMP").setText(U.getCurrentTimestamp());
		infoEl.addElement("SIGNED_MSG").setText("");

		Element bodyEl = msgEl.addElement("BODY");
		bodyEl.addElement("QRY_REQ_SN").setText(U.nvl(data.getQryReqSn()));		
		
		String xml = Cj.formatXml_UTF8(doc);
		log.info("产生G60004协议签约结果查询：" + U.substringByByte(xml, 1024));
		return xml;
	}
	

	/**
	 *
	 * @param tradeBean
	 * @return
	 */
	@Override
	public ResultBean collectMoney(TradeBean tradeBean) {
		ResultBean resultBean = null;
		String bankNumber = routeConfigService.getCardPBCCode(tradeBean.getCardNo()).get("PBC_BANKCODE")+"";
		String subBankName = routeConfigService.getCardPBCCode(tradeBean.getCardNo()).get("BANKNAME")+"";
		String bankName = routeConfigService.getBankName(bankNumber);
		G10001Bean data = new G10001Bean();
		try {
			data.setMertid(ConsUtil.getInstance().cons.getChanpay_cj_merchant_id());
			data.setBusinessCode(ConsUtil.getInstance().cons.getChanpay_cj_business_code());
			data.setCorpAccNo(ConsUtil.getInstance().cons.getChanpay_cj_account_no());
			data.setProductCode(ConsUtil.getInstance().cons.getChanpay_cj_product_no());
			data.setSubMertid("");
			data.setBankGeneralName(bankName);
			data.setAccountNo(tradeBean.getCardNo());
			data.setAccountName(tradeBean.getAcctName());
			data.setProvince("");
			data.setCity("");
			data.setBankName(subBankName);
			data.setBankCode(bankNumber);
			data.setDrctBankCode(bankNumber);
			data.setProtocolNo(tradeBean.getBindCardId());
			data.setCurrency("CNY");
			data.setAmount(Long.valueOf(tradeBean.getAmount()));
			data.setIdType("0");
			data.setId(tradeBean.getCertId());
			data.setTel(tradeBean.getMobile());
			data.setCorpFlowNo(tradeBean.getOrderId());
			data.setSummary("代收业务");
			data.setPostscript("代收业务");
			data.setAccountProp("0");
			data.setAccountType("00");
			data.setReqSn(tradeBean.getPayOrderNo());
			buildCjmsgAndSend(data);
		} catch (TradeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultBean = new ResultBean(e.getCode(), e.getMessage());
			txnsLogService.updateTradeStatFlag(tradeBean.getTxnseqno(), TradeStatFlagEnum.OVERTIME);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultBean = new ResultBean("T000", e.getMessage());
			txnsLogService.updateTradeStatFlag(tradeBean.getTxnseqno(), TradeStatFlagEnum.OVERTIME);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultBean = new ResultBean("T000", e.getMessage());
			txnsLogService.updateTradeStatFlag(tradeBean.getTxnseqno(), TradeStatFlagEnum.OVERTIME);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultBean = new ResultBean("T000", e.getMessage());
			txnsLogService.updateTradeStatFlag(tradeBean.getTxnseqno(), TradeStatFlagEnum.OVERTIME);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			txnsLogService.updateTradeStatFlag(tradeBean.getTxnseqno(), TradeStatFlagEnum.OVERTIME);
		}
		
		if("0000".equals(data.getRetCode())){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int i = 0;i<20;i++){
				ResultBean queryResultBean = queryCollectMoney(data.getReqSn());
				if(queryResultBean.isResultBool()){
					G20001Bean bean = (G20001Bean) queryResultBean.getResultObj();
					BodyRetEnmu bodyRetEnmu = BodyRetEnmu.fromValue(bean.getRetCode());
					if(bodyRetEnmu==BodyRetEnmu.ACCEPTED||bodyRetEnmu==BodyRetEnmu.PROCESSING){
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						continue;
					}else if(bodyRetEnmu==BodyRetEnmu.SUCCESS){//交易成功
						data.setRetCode(bean.getRetCode());
						data.setErrMsg(bean.getErrMsg());
						resultBean = new ResultBean(data);
						break;
					}else if(bodyRetEnmu==BodyRetEnmu.FAILED){//交易失败
						data.setRetCode(bean.getRetCode());
						data.setErrMsg(bean.getErrMsg());
						resultBean = new ResultBean(data);
						resultBean.setResultBool(false);
						break;
					}else{//未知原因导致的失败
						data.setRetCode(bean.getRetCode());
						data.setErrMsg(bean.getErrMsg());
						resultBean = new ResultBean(data);
						resultBean.setResultBool(false);
						break;
					}
					
				}
				
			}
		}else{
			resultBean = new ResultBean("T000","交易失败");
		}
		return resultBean;
	}
	/** 组织Cj报文，并发送 
	 * @throws IOException 
	 * @throws HttpException 
	 * @throws TradeException 
	 * @throws DocumentException */
	private void buildCjmsgAndSend(G10001Bean data) throws HttpException, IOException, TradeException, DocumentException {
		
			String cjReqmsg = buildCjmsg(data);
			// 签名
			CjSignHelper singHelper = new CjSignHelper();
			String signMsg = singHelper.signXml$Add(cjReqmsg);
			// 发送报文
			String cjRespmsg = cjMsgSendService.sendAndGetString(signMsg);
			// 验证签名
			VerifyResult verifyResult = singHelper.verifyCjServerXml(cjRespmsg);
			if (!verifyResult.result) {
				throw new TradeException("GW09");
			}
			parseCjMsgToDto(cjRespmsg, data);
		
	}
	
	private void parseCjMsgToDto(String cjRespmsg, G10001Bean data) throws DocumentException {
		Document reqDoc = DocumentHelper.parseText(cjRespmsg);

		Element msgEl = reqDoc.getRootElement();
		Element infoEl = msgEl.element("INFO");

		data.setRetCode(infoEl.elementText("RET_CODE"));
		data.setErrMsg(infoEl.elementText("ERR_MSG"));
		data.setTimestamp(infoEl.elementText("TIMESTAMP"));
		log.info("响应信息：retcode[" + data.getRetCode() + "], errmsg[" + data.getErrMsg() + "]");
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
		log.info("产生G10001单笔实时收款：" + xml);
		return xml;
	}
	/**
	 *
	 * @param qry_req_sn
	 * @return
	 */
	@Override
	public ResultBean queryCollectMoney(String qry_req_sn) {
		ResultBean resultBean = null;
		G20001Bean data = new G20001Bean();
		try {
			data.setMertid(ConsUtil.getInstance().cons.getChanpay_cj_merchant_id());
			data.setQryReqSn(qry_req_sn);
			data.setSummary("");
			data.setPostscript("");
			data.setReqSn(U.createUUID());
			buildCjmsgAndSend(data);
			resultBean = new ResultBean(data);
			resultBean.setErrCode(data.getRetCode());
		} catch (TradeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultBean;
	}
	/** 组织Cj报文，并发送 
	 * @throws IOException 
	 * @throws HttpException 
	 * @throws TradeException 
	 * @throws DocumentException */
	private void buildCjmsgAndSend(G20001Bean data) throws HttpException, IOException, TradeException, DocumentException {
		String cjReqmsg = buildCjmsg(data);
		// 签名
		CjSignHelper singHelper = new CjSignHelper();
		String signMsg = singHelper.signXml$Add(cjReqmsg);
		// 发送报文
		String cjRespmsg = cjMsgSendService.sendAndGetString(signMsg);
		// 验证签名
		VerifyResult verifyResult = singHelper.verifyCjServerXml(cjRespmsg);
		if (!verifyResult.result) {
			throw new TradeException("GW09");
		}
		parseCjMsgToDto(cjRespmsg, data);
	}
	
	private void parseCjMsgToDto(String cjRespmsg, G20001Bean data) throws DocumentException  {
		Document reqDoc = DocumentHelper.parseText(cjRespmsg);

		Element msgEl = reqDoc.getRootElement();
		Element infoEl = msgEl.element("INFO");

		
		data.setTimestamp(infoEl.elementText("TIMESTAMP"));
		
		Element bodyEl = msgEl.element("BODY");
		if (bodyEl == null) {
			return;
		}
		data.setRetCode(bodyEl.elementText("RET_CODE"));
		data.setErrMsg(bodyEl.elementText("ERR_MSG"));
		data.setCharge(StringUtils.isBlank(bodyEl.elementText("CHARGE"))?0:Long.parseLong(bodyEl.elementText("CHARGE")));
		data.setCorpAcctNo(bodyEl.elementText("CORP_ACCT_NO"));
		data.setCorpAcctName(bodyEl.elementText("CORP_ACCT_NAME"));
		data.setAccountNo(bodyEl.elementText("ACCOUNT_NO"));
		data.setAccountName(bodyEl.elementText("ACCOUNT_NAME"));
		data.setAmount(StringUtils.isBlank(bodyEl.elementText("AMOUNT"))?0:Long.parseLong(bodyEl.elementText("AMOUNT")));
		data.setSummary(bodyEl.elementText("SUMMARY"));
		data.setCorpFlowNo(bodyEl.elementText("CORP_FLOW_NO"));
		data.setPostscript(bodyEl.elementText("POSTSCRIPT"));
		log.info("响应信息：retcode[" + data.getRetCode() + "], errmsg[" + data.getErrMsg() + "]");
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
		log.info("产生G20001文件上传：" + xml);
		return xml;
	}
}
