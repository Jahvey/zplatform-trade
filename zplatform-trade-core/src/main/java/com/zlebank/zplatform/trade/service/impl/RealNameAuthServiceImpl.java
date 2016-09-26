/* 
 * InsteadPayServiceImpl.java  
 * 
 * version v1.0
 *
 * 2015年11月25日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.acc.bean.TradeInfo;
import com.zlebank.zplatform.acc.exception.AbstractBusiAcctException;
import com.zlebank.zplatform.acc.exception.AccBussinessException;
import com.zlebank.zplatform.acc.exception.IllegalEntryRequestException;
import com.zlebank.zplatform.acc.service.AccEntryService;
import com.zlebank.zplatform.acc.service.entry.EntryEvent;
import com.zlebank.zplatform.commons.enums.BusinessCodeEnum;
import com.zlebank.zplatform.commons.utils.BeanCopyUtil;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.member.service.MerchMKService;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.enums.ChannelEnmu;
import com.zlebank.zplatform.trade.cmbc.exception.CMBCTradeException;
import com.zlebank.zplatform.trade.cmbc.service.ICMBCTransferService;
import com.zlebank.zplatform.trade.dao.ConfigInfoDAO;
import com.zlebank.zplatform.trade.dao.RealnameAuthDAO;
import com.zlebank.zplatform.trade.dao.RealnameAuthOrderDAO;
import com.zlebank.zplatform.trade.exception.BalanceNotEnoughException;
import com.zlebank.zplatform.trade.exception.DuplicateOrderIdException;
import com.zlebank.zplatform.trade.exception.MessageDecryptFailException;
import com.zlebank.zplatform.trade.exception.RealNameAuthFailException;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.insteadPay.message.RealnameAuthFile;
import com.zlebank.zplatform.trade.insteadPay.message.RealnameAuthQuery_Request;
import com.zlebank.zplatform.trade.insteadPay.message.RealnameAuthQuery_Response;
import com.zlebank.zplatform.trade.insteadPay.message.RealnameAuth_Request;
import com.zlebank.zplatform.trade.model.ConfigInfoModel;
import com.zlebank.zplatform.trade.model.PojoRealnameAuth;
import com.zlebank.zplatform.trade.model.PojoRealnameAuthOrder;
import com.zlebank.zplatform.trade.service.RealNameAuthService;
import com.zlebank.zplatform.trade.utils.OrderNumber;

/**
 * 代付业务
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月25日 上午10:48:24
 * @since 
 */
@Service
public class RealNameAuthServiceImpl  implements RealNameAuthService{
    
    private static final Log log = LogFactory.getLog(RealNameAuthServiceImpl.class);

    @Autowired
    private RealnameAuthDAO realnameAuthDAO;

    @Autowired
    private RealnameAuthOrderDAO realnameAuthOrderDAO;
    
    @Autowired
    private MerchMKService merchMKService;
    
    @Autowired
    private ICMBCTransferService cmbcTransferService;
    
    @Autowired
    private ConfigInfoDAO configInfoDAO;

    @Autowired
    private AccEntryService accEntryService;
    
    /**
     * 实名认证处理
     * @param request
     * @throws MessageDecryptFailException 
     * @throws RealNameAuthFailException 
     * @throws BalanceNotEnoughException 
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public boolean realNameAuth(RealnameAuth_Request request, RealnameAuthFile realNameAuth, Long orderId) throws MessageDecryptFailException, RealNameAuthFailException, BalanceNotEnoughException {
        if (log.isDebugEnabled()) {
            log.debug("认证处理开始");
            log.debug(JSONObject.fromObject(request));
            log.debug(JSONObject.fromObject(realNameAuth));
        }
        String cardNo = null; // 查询用卡号
        String accName = null; // 查询用持卡人姓名
        cardNo = realNameAuth.getCardNo();
        accName = realNameAuth.getCustomerNm();
        
        PojoRealnameAuthOrder pojoRealnameAuthOrder = realnameAuthOrderDAO.get(orderId);

        // 实名认证表
        boolean isCost = false;
        PojoRealnameAuth pojo = realnameAuthDAO.getByCardNoAndName(cardNo, accName, realNameAuth.getCertifId(), realNameAuth.getPhoneNo());
        if (pojo == null || !"00".equals(pojo.getStatus())) {
            try {
                // Pojo属性设定
                pojo = new PojoRealnameAuth();
                pojo.setOrderId(request.getOrderId());
                pojo.setTxnTime(request.getTxnTime());
                setPojoProperty(pojo,realNameAuth);
                // 实名认证处理调用
                ResultBean rb = cmbcTransferService.realNameAuth(pojo);
                if (rb != null && StringUtil.isNotEmpty(rb.getErrMsg())) {
                    throw new RealNameAuthFailException(new Object[]{rb.getErrMsg()});
                } else {
                    isCost = true;
                    pojoRealnameAuthOrder.setStatus("00");
                }
            } catch (TradeException e) {
                log.error(e.getMessage(), e);
                throw new RealNameAuthFailException(new Object[]{e.getMessage()});
            } catch (CMBCTradeException e) {
                log.error(e.getMessage(), e);
                throw new RealNameAuthFailException(new Object[]{e.getMessage()});
            }
        } else {
            pojoRealnameAuthOrder.setStatus("00");
        }

        realnameAuthOrderDAO.updateWithCommit(pojoRealnameAuthOrder);

        PojoRealnameAuthOrder para = BeanCopyUtil.copyBean(PojoRealnameAuthOrder.class, pojoRealnameAuthOrder);
        if (log.isDebugEnabled()) {
            log.debug("认证处理结束");
        }
        // 分录流水插入
        try {
            accEntryInsert(para, isCost ? BusinessCodeEnum.REALNAME_AUTH_COST : BusinessCodeEnum.REALNAME_AUTH_NO_COST);
        } catch (AccBussinessException e) {
            log.error(e.getMessage(),e);
            if ("E000019".equals(e.getCode()) || "E000018".equals(e.getCode())) {
                throw new BalanceNotEnoughException();
            }
        } catch (AbstractBusiAcctException e) {
            log.error(e.getMessage(),e);
        } catch (NumberFormatException e) {
            log.error(e.getMessage(),e);
        } catch (IllegalEntryRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(),e);
		} 
        return true;
    }
    
    
    
    /**
     * 【实名认证】分录规则设定
     * @param request
     * @throws NumberFormatException 
     * @throws AbstractBusiAcctException 
     * @throws AccBussinessException 
     * @throws IllegalEntryRequestException 
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    private void accEntryInsert(PojoRealnameAuthOrder order, BusinessCodeEnum busiCode) throws AccBussinessException, AbstractBusiAcctException, NumberFormatException, IllegalEntryRequestException {
        ConfigInfoModel startTime = configInfoDAO.getConfigByParaName("REALNAME_AUTH_PRICE");
        // 记录分录流水
        TradeInfo tradeInfo = new TradeInfo();
        tradeInfo.setTxnseqno(order.getTxnseqno());
        tradeInfo.setAmount(BigDecimal.ZERO);
        tradeInfo.setCommission(BigDecimal.ZERO);
        tradeInfo.setAmountD(BigDecimal.ZERO);
        tradeInfo.setAmountE(BigDecimal.ZERO);
        tradeInfo.setBusiCode(busiCode.getBusiCode());
        tradeInfo.setPayMemberId(order.getMerId());
        tradeInfo.setCharge(new BigDecimal(startTime.getPara()));// 手续费
        tradeInfo.setChannelId(ChannelEnmu.CMBCWITHHOLDING.getChnlcode());
        accEntryService.accEntryProcess(tradeInfo,EntryEvent.TRADE_SUCCESS);
       
    }



    /**
     * 实名认证订单保存
     *
     * @param request
     * @param realNameAuth
     * @return
     * @throws DuplicateOrderIdException 
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Long saveRealNameAuthOrder(RealnameAuth_Request request, RealnameAuthFile realNameAuth) throws DuplicateOrderIdException {
        PojoRealnameAuthOrder old = realnameAuthOrderDAO.getByOrderIdAndTxnTime(request.getOrderId(), request.getTxnTime());
        if (old != null) throw new DuplicateOrderIdException();
        
        PojoRealnameAuthOrder pojo = new PojoRealnameAuthOrder();
        pojo.setMerId(request.getMerId());
        pojo.setOrderId(request.getOrderId());
        pojo.setTxnTime(request.getTxnTime());
        pojo.setCardNo(realNameAuth.getCardNo());
        pojo.setCardType(realNameAuth.getCardType());
        pojo.setCertifTp(realNameAuth.getCertifTp());
        pojo.setCertifId(realNameAuth.getCertifId());
        pojo.setCustomerNm(realNameAuth.getCustomerNm());
        pojo.setCvn2(realNameAuth.getCvn2());
        pojo.setExpired(realNameAuth.getExpired());
        pojo.setPhoneNo(Long.valueOf(realNameAuth.getPhoneNo()));
        pojo.setIntime(new Date());
        pojo.setInuser(0L);
        pojo.setUptime(new Date());
        pojo.setUpuser(0L);
        pojo.setStatus("01");
        pojo.setTxnseqno(OrderNumber.getInstance().generateTxnseqno(BusinessCodeEnum.REALNAME_AUTH_COST.getBusiCode()));
        pojo = realnameAuthOrderDAO.merge(pojo);
        return pojo.getId();
    }

    /**
     * Pojo属性设定
     * @param pojo
     * @param realNameAuth 
     */
    private void setPojoProperty(PojoRealnameAuth pojo, RealnameAuthFile realNameAuth) {
        pojo.setCardNo(realNameAuth.getCardNo());
        pojo.setCardType(realNameAuth.getCardType().equals("1") ? "0" : "2");
        pojo.setCertifTp(realNameAuth.getCertifTp());
        pojo.setCertifId(realNameAuth.getCertifId());
        pojo.setCustomerNm(realNameAuth.getCustomerNm());
        pojo.setCvn2(realNameAuth.getCvn2());
        pojo.setExpired(realNameAuth.getExpired());
        pojo.setPhoneNo(Long.valueOf(realNameAuth.getPhoneNo()));
    }

    /**
     *  实名认证查询处理
     * @param requestBean
     * @param responseBean
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public void realNameAuthQuery(RealnameAuthQuery_Request requestBean, RealnameAuthQuery_Response responseBean) {
        if (log.isDebugEnabled()) {
            log.debug("认证处理查询开始");
            log.debug(JSONObject.fromObject(requestBean));
            log.debug(JSONObject.fromObject(responseBean));
        }
        PojoRealnameAuthOrder pojo = realnameAuthOrderDAO.getByOrderIdAndTxnTime(requestBean.getOrderId(), requestBean.getTxnTime());
        if (pojo != null && "00".equals(pojo.getStatus())) {
            responseBean.setValidateStatus("00");
            responseBean.setOrigRespCode("00");
            responseBean.setOrigRespMsg("认证成功！");
            return;
        } else {
            responseBean.setValidateStatus("99");
            responseBean.setOrigRespCode("99");
            responseBean.setOrigRespMsg("未认证！");
            return;
        }
    }
}
