/* 
 * CMBCTestJob.java  
 * 
 * version TODO
 *
 * 2016年2月3日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.cmbc.job;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.cmbc.exception.CMBCTradeException;
import com.zlebank.zplatform.trade.cmbc.service.IWithholdingService;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年2月3日 上午9:29:35
 * @since 
 */
public class CMBCTestJob {
	private static final Log log = LogFactory.getLog(CMBCTestJob.class);
	@Autowired
	private IWithholdingService withholdingService;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void execute() throws IOException {
		try {
			log.info("start CMBCTestJob");
			ResultBean resultBean = withholdingService.realTimeWitholdinghQuery("20151230", "2015123000000054");
			log.info(JSON.toJSONString(resultBean));
			log.info("end CMBCTestJob");
		} catch (CMBCTradeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
