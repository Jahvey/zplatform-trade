/* 
 * QueryBatchInsteadPayJob.java  
 * 
 * version TODO
 *
 * 2016年4月22日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bosspay.job;

import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bosspay.bean.BtchpmtQueryResponseBean;
import com.zlebank.zplatform.trade.bosspay.bean.BtchpmtResultBean;
import com.zlebank.zplatform.trade.bosspay.service.BossInsteadPayService;
import com.zlebank.zplatform.trade.bosspay.service.BossPayService;
import com.zlebank.zplatform.trade.dao.ITxnsInsteadPayDAO;
import com.zlebank.zplatform.trade.model.PojoTxnsInsteadPay;

/**
 * Class Description
 * 批量代付结果查询任务10分钟一次
 * @author guojia
 * @version
 * @date 2016年4月22日 下午4:05:16
 * @since
 */
public class QueryBatchInsteadPayJob {
	@SuppressWarnings("unused")
	private static final Log log = LogFactory
			.getLog(QueryBatchInsteadPayJob.class);

	@Autowired
	private ITxnsInsteadPayDAO insteadPayDAO;
	@Autowired
	private BossPayService bossPayService;
	@Autowired
	private BossInsteadPayService bossInsteadPayService;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void execute() throws IOException {
		List<PojoTxnsInsteadPay> bossPayList = insteadPayDAO.queryBossPayNoResult();
		for(PojoTxnsInsteadPay txnsInsteadPay : bossPayList){
			String serialNum = txnsInsteadPay.getResFile();
			ResultBean resultBean = bossPayService.queryRealInsteadPay(serialNum);
			if(resultBean.isResultBool()){
				BtchpmtQueryResponseBean responseBean = (BtchpmtQueryResponseBean) resultBean.getResultObj();
				List<BtchpmtResultBean> resultList =  JSON.parseArray(responseBean.getResult(), BtchpmtResultBean.class);
				bossInsteadPayService.dealWithResult(serialNum, resultList);
				
			}
		}
	}
}
