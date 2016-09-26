package com.zlebank.zplatform.wechat.qr.job;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.wechat.qr.service.WeChatQRService;
@Service("refundRqJob")
public class RefundJob {
	private static final Log log = LogFactory.getLog(RefundJob.class);
	@Autowired
	private WeChatQRService weChatService;
	/***
	 * 跑批-查询支付的退款
	 * @throws IOException
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void execute(){
		log.info("start RefundRqJob");
		weChatService.dealRefundBatch();
		log.info("end RefundRqJob");
		
	}
	/***
	 * 跑批-查询已支付未收到异步通知的订单
	 */
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void queryOrder(){
		log.info("start queryRqOrder");
		weChatService.dealAnsyOrder();
		log.info("end queryRqOrder");
	}
	
}
