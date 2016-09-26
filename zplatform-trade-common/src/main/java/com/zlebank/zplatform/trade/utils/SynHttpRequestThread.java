/* 
 * SynHttpRequest.java  
 * 
 * version TODO
 *
 * 2015年8月29日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpException;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.commons.utils.HibernateValidatorUtil;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.queue.NotifyQueueBean;
import com.zlebank.zplatform.trade.model.TxnsNotifyTaskModel;
import com.zlebank.zplatform.trade.service.ITxnsNotifyTaskService;
import com.zlebank.zplatform.trade.service.TradeQueueService;
import com.zlebank.zplatform.trade.service.impl.InsteadPayNotifyTask;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年8月29日 上午9:33:04
 * @since
 */
public class SynHttpRequestThread extends Thread {
	private static final Log log = LogFactory
			.getLog(SynHttpRequestThread.class);
	private static final ThreadLocal<HttpUtils> httpThread = new ThreadLocal<HttpUtils>();
	private String memberId;
	private String txnseqno;
	private String sendUrl;
	private List<NameValuePair> params;
	private ITxnsNotifyTaskService txnsNotifyTaskService;
	private TradeQueueService tradeQueueService = (TradeQueueService) SpringContext
			.getContext().getBean("tradeQueueService");
	private HttpUtils http;

	/**
	 * @param sendUrl
	 * @param list
	 */
	public SynHttpRequestThread(String memberId, String txnseqno,
			String sendUrl, List<NameValuePair> list) {
		super();
		this.memberId = memberId;
		this.txnseqno = txnseqno;
		this.sendUrl = sendUrl;
		this.params = list;
		txnsNotifyTaskService = (ITxnsNotifyTaskService) SpringContext
				.getContext().getBean("txnsNotifyTaskService");
	}

	public SynHttpRequestThread(String memberId, String txnseqno,
			String sendUrl, InsteadPayNotifyTask task) {
		super();
		this.memberId = memberId;
		this.txnseqno = txnseqno;
		this.sendUrl = sendUrl;
		BasicNameValuePair[] pairs = new BasicNameValuePair[] {
				new BasicNameValuePair("data", task.getData()),
				new BasicNameValuePair("addit", task.getAddit()),
				new BasicNameValuePair("sign", task.getSign()) };
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		for (int i = 0; i < pairs.length; i++) {
			qparams.add(pairs[i]);
		}
		this.params = qparams;
		txnsNotifyTaskService = (ITxnsNotifyTaskService) SpringContext
				.getContext().getBean("txnsNotifyTaskService");
	}

	public void run() {
		try {
			sendHttpRequest(sendUrl, params);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendHttpRequest(String sendUrl, List<NameValuePair> params)
			throws HttpException {
		TxnsNotifyTaskModel asyncNotifyTask = txnsNotifyTaskService.getAsyncNotifyTask(txnseqno);
		int[] timeSpace = new int[] { 0, 60000 * 1, 60000 * 2, 60000 * 2,60000 * 30, 60000 * 30,60000 * 30,60000 * 30};
		log.info("send notify url:" + sendUrl);
		ResultBean resultBean = null;
		String sendStatus = null;
		try {
			http = httpThread.get();
			if (http == null) {
				http = new HttpUtils();
			}
			http.openConnection();
			resultBean = http.exeHttpPostResultBean(sendUrl, params,"UTF-8");
			sendStatus = "01";
			if (resultBean != null) {
				log.info("response message:" + resultBean.getErrMsg());
				if (resultBean.getErrCode().equals("200")) {
					if ("success".equalsIgnoreCase(resultBean.getErrMsg())) {
						log.info("sync notify success");
						sendStatus = "00";
					} else {
						log.info("sync notify fail");
						sendStatus = "01";
					}
				}
			}
			http.closeConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (asyncNotifyTask == null) {
			asyncNotifyTask = new TxnsNotifyTaskModel(memberId, txnseqno, 1, 5,
					"", sendStatus, resultBean.getErrCode(), sendUrl, "1");
			txnsNotifyTaskService.saveTask(asyncNotifyTask);
			if ("success".equalsIgnoreCase(resultBean.getErrMsg())) {
				log.info("sync notify success complete :" + txnseqno);
			} else {
				Long sendTime = System.currentTimeMillis() + timeSpace[1];
				NotifyQueueBean notifyQueueBean = new NotifyQueueBean();
				notifyQueueBean.setTxnseqno(txnseqno);
				notifyQueueBean.setSendTimes(1);
				notifyQueueBean.setSendDateTime(DateUtil.formatDateTime(
						DateUtil.DEFAULT_DATE_FROMAT, new Date(sendTime)));
				tradeQueueService.addNotifyQueue(notifyQueueBean);
			}
		} else {
			TxnsNotifyTaskModel task = new TxnsNotifyTaskModel();
			task.setTxnseqno(txnseqno);
			task.setMemberId(memberId);
			task.setSendStatus(sendStatus);
			task.setSendTimes(asyncNotifyTask.getSendTimes() + 1);
			boolean flag = txnsNotifyTaskService.updateTask(task);
			if (flag) {
				log.info("sync notify complete :" + txnseqno);
			}
			if ("success".equalsIgnoreCase(resultBean.getErrMsg())) {
				log.info("sync notify success complete :" + txnseqno);
			} else {
				if(task.getSendTimes()<asyncNotifyTask.getMaxSendTimes()){//超过发送最大次数时将不在发送
					Long sendTime = System.currentTimeMillis()
							+ timeSpace[task.getSendTimes()];
					NotifyQueueBean notifyQueueBean = new NotifyQueueBean();
					notifyQueueBean.setTxnseqno(txnseqno);
					notifyQueueBean.setSendTimes(task.getSendTimes());
					notifyQueueBean.setSendDateTime(DateUtil.formatDateTime(
							DateUtil.DEFAULT_DATE_FROMAT, new Date(sendTime)));
					tradeQueueService.addNotifyQueue(notifyQueueBean);
				}
				
			}
		}
	}

	public void syncHttp(String sendUrl, List<NameValuePair> params) {
		if (checkSyncTask()) {
			log.info("checkSyncTask: true");
			return;
		}

		int[] timeSpace = new int[] { 0, 60000 * 2, 60000 * 2, 60000 * 2,
				60000 * 30, 60000 * 30 };
		for (int i = 0; i < 5; i++) {
			try {
				log.info("send " + i + " times url:" + sendUrl);
				http = httpThread.get();
				if (http == null) {
					http = new HttpUtils();
				}

				http.openConnection();
				ResultBean resultBean = http.exeHttpPostResultBean(sendUrl,
						params, "UTF-8");
				String sendStatus = "01";
				if (resultBean != null) {
					log.info("response message:" + resultBean.getErrMsg());
					if (resultBean.getErrCode().equals("200")) {
						if ("success".equalsIgnoreCase(resultBean.getErrMsg())) {
							log.info("sync notify success");
							sendStatus = "00";
						} else {
							log.info("sync notify fail");
							sendStatus = "01";
						}
					}
				}
				http.closeConnection();
				if (i == 0) {
					TxnsNotifyTaskModel task = new TxnsNotifyTaskModel(
							memberId, txnseqno, (i + 1), 5, "", sendStatus,
							resultBean.getErrCode(), sendUrl, "1");
					txnsNotifyTaskService.saveTask(task);
				} else {
					TxnsNotifyTaskModel task = new TxnsNotifyTaskModel();
					task.setTxnseqno(txnseqno);
					task.setMemberId(memberId);
					task.setSendStatus(sendStatus);
					task.setSendTimes(i + 1);
					boolean flag = txnsNotifyTaskService.updateTask(task);
					if (flag) {
						log.info("sync notify complete :" + txnseqno);
						break;
					}
				}
				if ("success".equalsIgnoreCase(resultBean.getErrMsg())) {
					log.info("sync notify success complete :" + txnseqno);
					break;
				}

				log.info("sleep time :" + timeSpace[i]);
				Thread.currentThread().sleep(timeSpace[i]);
			} catch (HttpException e) {
				// TODO Auto-generated catch block
				log.error(e);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				log.error(e);
			}
		}

	}

	private boolean checkSyncTask() {
		List<TxnsNotifyTaskModel> taskList = txnsNotifyTaskService
				.findTaskByTxnseqno(txnseqno, memberId);
		if (taskList == null) {
			return false;
		}

		if (taskList.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return the sendUrl
	 */
	public String getSendUrl() {
		return sendUrl;
	}

	/**
	 * @param sendUrl
	 *            the sendUrl to set
	 */
	public void setSendUrl(String sendUrl) {
		this.sendUrl = sendUrl;
	}

	/**
	 * @return the params
	 */
	public List<NameValuePair> getParams() {
		return params;
	}

	/**
	 * @param params
	 *            the params to set
	 */
	public void setParams(List<NameValuePair> params) {
		this.params = params;
	}

	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId
	 *            the memberId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return the txnseqno
	 */
	public String getTxnseqno() {
		return txnseqno;
	}

	/**
	 * @param txnseqno
	 *            the txnseqno to set
	 */
	public void setTxnseqno(String txnseqno) {
		this.txnseqno = txnseqno;
	}

}
