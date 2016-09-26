/* 
 * TxnsLogServiceImpl.java  
 * 
 * version TODO
 *
 * 2015年8月29日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.zlebank.zplatform.acc.bean.TradeInfo;
import com.zlebank.zplatform.acc.exception.AbstractBusiAcctException;
import com.zlebank.zplatform.acc.exception.AccBussinessException;
import com.zlebank.zplatform.acc.exception.IllegalEntryRequestException;
import com.zlebank.zplatform.acc.service.AccEntryService;
import com.zlebank.zplatform.acc.service.entry.EntryEvent;
import com.zlebank.zplatform.commons.dao.pojo.BusiTypeEnum;
import com.zlebank.zplatform.commons.utils.StringUtil;
import com.zlebank.zplatform.member.dao.CoopInstiDAO;
import com.zlebank.zplatform.member.pojo.PojoCoopInsti;
import com.zlebank.zplatform.member.pojo.PojoMember;
import com.zlebank.zplatform.member.pojo.PojoMerchDeta;
import com.zlebank.zplatform.member.service.MemberService;
import com.zlebank.zplatform.member.service.MerchService;
import com.zlebank.zplatform.trade.bean.AccountTradeBean;
import com.zlebank.zplatform.trade.bean.AppPartyBean;
import com.zlebank.zplatform.trade.bean.PayPartyBean;
import com.zlebank.zplatform.trade.bean.ReaPayResultBean;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.enums.BusinessEnum;
import com.zlebank.zplatform.trade.bean.enums.ChannelEnmu;
import com.zlebank.zplatform.trade.bean.enums.ChnlTypeEnum;
import com.zlebank.zplatform.trade.bean.enums.OrderStatusEnum;
import com.zlebank.zplatform.trade.bean.enums.RiskLevelEnum;
import com.zlebank.zplatform.trade.bean.enums.TradeStatFlagEnum;
import com.zlebank.zplatform.trade.bean.enums.UnknowRetCodeEnum;
import com.zlebank.zplatform.trade.bean.gateway.QueryBean;
import com.zlebank.zplatform.trade.bean.queue.TradeQueueBean;
import com.zlebank.zplatform.trade.dao.ITxnsLogDAO;
import com.zlebank.zplatform.trade.dao.InsteadPayDetailDAO;
import com.zlebank.zplatform.trade.dao.RspmsgDAO;
import com.zlebank.zplatform.trade.dao.TransferDataDAO;
import com.zlebank.zplatform.trade.exception.TradeException;
import com.zlebank.zplatform.trade.model.MemberBaseModel;
import com.zlebank.zplatform.trade.model.PojoBankTransferData;
import com.zlebank.zplatform.trade.model.PojoInsteadPayDetail;
import com.zlebank.zplatform.trade.model.PojoRspmsg;
import com.zlebank.zplatform.trade.model.PojoTranData;
import com.zlebank.zplatform.trade.model.RiskTradeLogModel;
import com.zlebank.zplatform.trade.model.TxncodeDefModel;
import com.zlebank.zplatform.trade.model.TxnsLogModel;
import com.zlebank.zplatform.trade.model.TxnsWithholdingModel;
import com.zlebank.zplatform.trade.service.IMemberService;
import com.zlebank.zplatform.trade.service.IRiskTradeLogService;
import com.zlebank.zplatform.trade.service.ITxncodeDefService;
import com.zlebank.zplatform.trade.service.ITxnsLogService;
import com.zlebank.zplatform.trade.service.TradeQueueService;
import com.zlebank.zplatform.trade.service.base.BaseServiceImpl;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.DateUtil;
import com.zlebank.zplatform.trade.utils.OrderNumber;
import com.zlebank.zplatform.trade.utils.UUIDUtil;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年8月29日 下午3:25:00
 * @since 
 */
@Transactional
@Service("txnsLogService")
public class TxnsLogServiceImpl extends BaseServiceImpl<TxnsLogModel, String> implements ITxnsLogService{
    private static final Log log = LogFactory.getLog(TxnsLogServiceImpl.class);
    @Autowired
    private ITxnsLogDAO txnsLogDAO;
    @Autowired 
    private ITxncodeDefService txncodeDefService;
    @Autowired 
    private RspmsgDAO rspmsgDAO;
    @Autowired
    private IMemberService memberService;
    @Autowired
    private IRiskTradeLogService riskTradeLogService;
    @Autowired
    private TransferDataDAO transferDataDAO;
    @Autowired
    private AccEntryService accEntryService;
    @Autowired
    private MemberService memberService2;
    @Autowired
    private InsteadPayDetailDAO insteadPayDetailDAO;
    @Autowired
    private MerchService merchService;
    @Autowired
	private TradeQueueService tradeQueueService;
    
    @Autowired
    private CoopInstiDAO coopInstiDAO;
    /**
     *
     * @return
     */
    @Override
    public Session getSession() {
        // TODO Auto-generated method stub
        return txnsLogDAO.getSession();
    }
    
    
    public ResultBean verifyRepeatOrder(String orderId){
        ResultBean resultBean = null;
        String queryString = "from TxnsLogModel txn where accordno = ?";
        TxnsLogModel txnsLog = super.getUniqueByHQL(queryString, new Object[]{orderId});
        if(txnsLog!=null){
            resultBean = new ResultBean("RC37", "订单号重复");
        }else{
            resultBean = new ResultBean("success");
        }
        return resultBean;
    }
    
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public ResultBean updatePayInfo_Fast(PayPartyBean payPartyBean){
        TxnsLogModel txnsLog = getTxnsLogByTxnseqno(payPartyBean.getTxnseqno());
        Map<String, Object> cardMap = null;
        if(StringUtil.isNotEmpty(payPartyBean.getCardNo())){
        	cardMap = getCardInfo(payPartyBean.getCardNo());
        }
        String hql = "update TxnsLogModel set paytype=?,payordno=?,payinst=?,payfirmerno=?,payordcomtime=?,pan=?,cardtype=?,cardinstino=?,txnfee=?,pan_name=?,payrettsnseqno=? where txnseqno=?";
        super.updateByHQL(hql, 
        		new Object[]{
        				StringUtil.isNotEmpty(payPartyBean.getPaytype())?payPartyBean.getPaytype():"01",
        				payPartyBean.getPayordno(),
        				payPartyBean.getPayinst(),
        				payPartyBean.getPayfirmerno(),
        				payPartyBean.getPayordcomtime(),
        				payPartyBean.getCardNo(),cardMap==null?"":cardMap.get("TYPE")+"",
        			    cardMap==null?"":cardMap.get("BANKCODE")+"",
        			    getTxnFee(txnsLog),
        			    payPartyBean.getPanName(),
        			    payPartyBean.getPayrettsnseqno(),
        			    payPartyBean.getTxnseqno()});
        return null;
    }
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updatePayInfo_Fast_result(String txnseqno,String retcode,String retinfo){
    	String hql = "update TxnsLogModel set payretcode=?,payretinfo=? where txnseqno=?";
    	
    	
    	super.updateByHQL(hql, new Object[]{retcode,retinfo,txnseqno});
        /*TxnsLogModel txnsLog = super.get(txnseqno);
        txnsLog.setPayretcode(retcode);
        txnsLog.setPayretinfo(retinfo);
        txnsLog.setPayordfintime(DateUtil.getCurrentDateTime());
        super.update(txnsLog);*/
    }
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updatePayInfo_Fast_result(String txnseqno,String payrettsnseqno,String retcode,String retinfo){
       /* TxnsLogModel txnsLog = super.get(txnseqno);
        txnsLog.setPayretcode(retcode);
        txnsLog.setPayretinfo(retinfo);
        txnsLog.setPayordfintime(DateUtil.getCurrentDateTime());
        txnsLog.setPayrettsnseqno(payrettsnseqno);*/
        String hql = "update TxnsLogModel set payretcode=?,payretinfo=?,payrettsnseqno=?,payordfintime=? where txnseqno=?";
        super.updateByHQL(hql, new Object[]{retcode,retinfo,payrettsnseqno,DateUtil.getCurrentDateTime(),txnseqno});
        /*super.update(txnsLog);*/
    }
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateCoreRetResult(String txnseqno,String retcode,String retinfo){
        /* TxnsLogModel txnsLog = super.get(txnseqno);
        txnsLog.setRetcode(retcode);
        txnsLog.setRetinfo(retinfo);
        if("0000".equals(retcode)){
        	txnsLog.setTradestatflag(TradeStatFlagEnum.FINISH_SUCCESS.getStatus());
        }else{
        	txnsLog.setTradestatflag(TradeStatFlagEnum.FINISH_FAILED.getStatus());
        }*/
        String hql = "update TxnsLogModel set retcode=?,retinfo=?,tradestatflag=? where txnseqno=?";
        super.updateByHQL(hql,new Object[]{retcode,retinfo,"0000".equals(retcode)?TradeStatFlagEnum.FINISH_SUCCESS.getStatus():TradeStatFlagEnum.FINISH_FAILED.getStatus(),txnseqno});
        //super.update(txnsLog);
    }
    
    
    @Transactional
    @Deprecated
    public ResultBean updatePayInfo_ecitic(PayPartyBean payPartyBean){
        ResultBean resultBean =null;
          try {
            TxnsLogModel txnsLog = super.get(payPartyBean.getTxnseqno());
            txnsLog.setPaytype(payPartyBean.getPaytype()); //支付类型（01：快捷，02：网银，03：账户）
            txnsLog.setPayordno(payPartyBean.getPayordno());//支付定单号
            txnsLog.setPayinst("97000001");//支付所属机构
            txnsLog.setPayfirmerno(payPartyBean.getPayfirmerno());//支付一级商户号
            //txnsLog.setPaysecmerno(payPartyBean.getPaysecmerno());//支付二级商户号
            txnsLog.setPayordcomtime(payPartyBean.getPayordcomtime());//支付定单提交时间
            
            //更新路由信息
            txnsLog.setRoutver(payPartyBean.getRout());
            txnsLog.setTxncode(payPartyBean.getRoutlvl());
            
            //更新交易流水表交易位
            txnsLog.setRelate("01000000");
            txnsLog.setTradetxnflag("01000000");
            txnsLog.setCashcode(payPartyBean.getCashCode());
            //支付定单完成时间
            super.update(txnsLog);
            resultBean = new ResultBean("success");
        } catch (Exception e) {
            resultBean = new ResultBean("RC33","业务处理失败");
            e.printStackTrace();
        }
        return resultBean;
    }
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public ResultBean updateGateWayPayResult(PayPartyBean payPartyBean){
    	TxnsLogModel txnsLog = getTxnsLogByTxnseqno(payPartyBean.getTxnseqno());
    	txnsLog.setPayordfintime(DateUtil.getCurrentDateTime());
    	txnsLog.setPayrettsnseqno(payPartyBean.getPayrettsnseqno());
    	txnsLog.setPayretcode(payPartyBean.getPayretcode());
    	txnsLog.setPayretinfo(payPartyBean.getPayretinfo());
    	txnsLog.setAccordfintime(DateUtil.getCurrentDateTime());
    	
    	txnsLog.setTradeseltxn(UUIDUtil.uuid());
    	txnsLog.setTradetxnflag("10000000");
    	txnsLog.setRelate("10000000");
    	txnsLog.setRetdatetime(DateUtil.getCurrentDateTime());
    	try {
            PojoRspmsg msg = rspmsgDAO.getRspmsgByChnlCode(ChnlTypeEnum.CHANPAY,payPartyBean.getPayretcode());
            txnsLog.setRetinfo(msg.getRspinfo());
            txnsLog.setRetcode(msg.getWebrspcode());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    	updateTxnsLog(txnsLog);
    	return null;
    }
    
    
    
    @Transactional
    @Deprecated
    public ResultBean updateRoutInfo(String txnseqno,String routId,String currentStep,String cashCode){
        ResultBean resultBean =null;
        try {
          TxnsLogModel txnsLog = super.get(txnseqno);
          txnsLog.setRoutver(routId);
          txnsLog.setTxncode(currentStep);
          txnsLog.setCashcode(cashCode);
          super.update(txnsLog);
          resultBean = new ResultBean("success");
      } catch (Exception e) {
          resultBean = new ResultBean("RC33","业务处理失败");
      }
      return resultBean;
    }
    
    @Transactional
    @Deprecated
    public void updateWapRoutInfo(String txnseqno,String routId) throws TradeException{
        try {
          TxnsLogModel txnsLog = super.get(txnseqno);
          txnsLog.setRoutver(routId);
          //txnsLog.setTxncode(currentStep);
          //txnsLog.setCashcode(cashCode);
          super.update(txnsLog);
      } catch (Exception e) {
          throw new TradeException("T007");
      }
      
    }
    
    private Map<String, Object> getCardInfo(String cardNo){
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("SELECT type,bankcode,bankname ");
        sqlBuffer.append("FROM (SELECT t.TYPE,t.BANKCODE,b.bankname ");
        sqlBuffer.append("FROM t_card_bin t, t_bank_insti b ");
        sqlBuffer.append("WHERE t.bankcode = b.bankcode ");
        sqlBuffer.append("AND ? LIKE t.cardbin || '%' ");
        sqlBuffer.append("AND t.cardlen = ? ");
        sqlBuffer.append("ORDER BY t.cardbin DESC) ");
        sqlBuffer.append("WHERE ROWNUM = 1 ");
        @SuppressWarnings("unchecked")
		List<Map<String, Object>> routList =  (List<Map<String, Object>>) super.queryBySQL(sqlBuffer.toString(), new Object[]{cardNo,cardNo.trim().length()});
       
        if(routList.size()>0){
            return routList.get(0);
        }
        return null;
    }
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public ResultBean updateAppInfo(AppPartyBean appParty){
        ResultBean resultBean =null;
        try {
            String hql = "update TxnsLogModel set appinst=?,appordcommitime=?,appordno=?,appordfintime=?,accordfintime=?,tradestatflag=? where txnseqno = ?";
            int row = super.updateByHQL(hql, new Object[]{appParty.getAppinst(),appParty.getAppordcommitime(),appParty.getAppordno(),appParty.getAppordfintime(),DateUtil.getCurrentDateTime(),TradeStatFlagEnum.FINISH_ACCOUNTING.getStatus(),appParty.getTxnseqno()});
            log.info("effect rows:"+row);
            resultBean = new ResultBean("success");
        } catch (Exception e) {
            resultBean = new ResultBean("RC33","业务处理失败");
            e.printStackTrace();
        }
        return resultBean;
    }


    /**
     *
     * @param txnseqno
     * @return
     */
    @Override
    @Transactional(readOnly=true)
    public TxnsLogModel getTxnsLogByTxnseqno(String txnseqno) {
        return super.get(txnseqno);
    }


    /**
     *
     * @param txnsLog
     * @return
     */
    @Override
    public Long getTxnFee(TxnsLogModel txnsLog){
        //交易序列号，扣率版本，业务类型，交易金额，会员号，原交易序列号，卡类型 
        @SuppressWarnings("unchecked")
		List<Map<String, Object>> feeList = (List<Map<String, Object>>) super.queryBySQL("select FNC_GETFEES(?,?,?,?,?,?,?) as fee from dual", 
                new Object[]{txnsLog.getTxnseqno(),txnsLog.getFeever(),txnsLog.getBusicode(),txnsLog.getAmount(),txnsLog.getAccsecmerno(),txnsLog.getTxnseqnoOg(),txnsLog.getCardtype()});
        if(feeList.size()>0){
            if(StringUtil.isNull(feeList.get(0).get("FEE"))){
                return 0L;
            }else{
                return Long.valueOf(feeList.get(0).get("FEE")+"");
            }
            
        }
        return 0L;
       
    }


    /**
     *
     * @param txnseqno
     * @param payResult
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateReaPayRetInfo(String txnseqno, ReaPayResultBean payResult) {
    	String hql = "update TxnsLogModel set payretcode=?,payretinfo=?,retcode=?,retinfo=?,tradestatflag=? where txnseqno = ?";
    	Object[] paramaters = null;
       /* TxnsLogModel txnsLog = super.get(txnseqno);
        txnsLog.setPayretinfo(payResult.getResult_msg());
        txnsLog.setPayretcode(payResult.getResult_code());*/
        try {
            PojoRspmsg msg =   rspmsgDAO.getRspmsgByChnlCode(ChnlTypeEnum.REAPAY,payResult.getResult_code());
            /*txnsLog.setRetinfo(msg.getRspinfo());
            txnsLog.setRetcode(msg.getWebrspcode());*/
            paramaters = new Object[]{payResult.getResult_code(),payResult.getResult_msg(),msg.getWebrspcode(),msg.getRspinfo(),
            		"0000".equals(msg.getWebrspcode())?TradeStatFlagEnum.FINISH_SUCCESS.getStatus():TradeStatFlagEnum.FINISH_FAILED.getStatus()
            		,txnseqno};
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            paramaters = new Object[]{payResult.getResult_code(),payResult.getResult_msg(),UnknowRetCodeEnum.REAPAY.getCode(),payResult.getResult_msg(),
            		TradeStatFlagEnum.FINISH_FAILED.getStatus(),txnseqno};
        }
        super.updateByHQL(hql, paramaters);
        
        //super.update(txnsLog);
    }
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateCMBCWithholdingRetInfo(String txnseqno, TxnsWithholdingModel withholdin) {
    	String hql = "update TxnsLogModel set payretcode=?,payretinfo=?,retcode=?,retinfo=?,tradestatflag=? where txnseqno = ?";
    	Object[] paramaters = null;
    	
//        TxnsLogModel txnsLog = super.get(txnseqno);
//        txnsLog.setPayretinfo(withholdin.getExecmsg());
//        txnsLog.setPayretcode(withholdin.getExeccode());
        try {
            PojoRspmsg msg = rspmsgDAO.getRspmsgByChnlCode(ChnlTypeEnum.CMBCWITHHOLDING,withholdin.getExeccode().trim());
//            txnsLog.setRetinfo(msg.getRspinfo());
//            txnsLog.setRetcode(msg.getWebrspcode());
            paramaters = new Object[]{withholdin.getExeccode(),withholdin.getExecmsg(),msg.getWebrspcode(),msg.getRspinfo(),
            		"0000".equals(msg.getWebrspcode())?TradeStatFlagEnum.FINISH_SUCCESS.getStatus():TradeStatFlagEnum.FINISH_FAILED.getStatus()
            		,txnseqno};
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
//            txnsLog.setRetinfo(withholdin.getExecmsg());
//            txnsLog.setRetcode(withholdin.getExeccode());
            paramaters = new Object[]{withholdin.getExeccode(),withholdin.getExecmsg(),withholdin.getExeccode(),withholdin.getExecmsg(),
            		TradeStatFlagEnum.FINISH_FAILED.getStatus(),txnseqno};
        }
//        super.update(txnsLog);
        super.updateByHQL(hql, paramaters);
    }

    /**
     *
     * @param queryBean
     * @return
     */
    @Override
    @Transactional(readOnly=true)
    public TxnsLogModel queryTrade(QueryBean queryBean) {
        TxncodeDefModel busiModel = txncodeDefService.getBusiCode(queryBean.getTxnType(), queryBean.getTxnSubType(), queryBean.getBizType());
        List<Object> paramList = new ArrayList<Object>();
        StringBuffer queryBuffer = new StringBuffer("from TxnsLogModel where busicode=? and accfirmerno = ? ");
        paramList.add(busiModel.getBusicode());
        paramList.add(queryBean.getMerId());
        if(StringUtil.isNotEmpty(queryBean.getOrderId())){//受理订单号
            queryBuffer.append(" and accordno = ?");
            paramList.add(queryBean.getOrderId());
        }
        if(StringUtil.isNotEmpty(queryBean.getQueryId())){//交易查询码
            queryBuffer.append(" and tradeseltxn = ?");
            paramList.add(queryBean.getQueryId());
        }
        @SuppressWarnings("unchecked")
		List<TxnsLogModel> resultList = (List<TxnsLogModel>) super.queryByHQL(queryBuffer.toString(), paramList.toArray());
        if(resultList.size()>0){
            return resultList.get(0);
        }
        return null;
    }
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void saveAccountTrade(AccountTradeBean accountTrade) throws TradeException{
        
        try {
          TxnsLogModel txnsLog = super.get(accountTrade.getTxnseqno());
          txnsLog.setPaytype("03"); //支付类型（01：快捷，02：网银，03：账户）
          txnsLog.setPayordno(OrderNumber.getInstance().generateAppOrderNo());//支付定单号
          txnsLog.setPayinst("99999999");//支付所属机构
          txnsLog.setPayfirmerno(accountTrade.getMemberId());//支付一级商户号-个人会员
          //txnsLog.setPaysecmerno(payPartyBean.getPaysecmerno());//支付二级商户号
          txnsLog.setPayordcomtime(DateUtil.getCurrentDateTime());//支付定单提交时间
          
          //更新交易流水表交易位
          txnsLog.setRelate("01000000");
          txnsLog.setTradetxnflag("01000000");
          txnsLog.setCashcode("");
          //支付定单完成时间
          super.update(txnsLog);
      } catch (Exception e) {
         throw new TradeException("AC03");
      }
      
    }
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateAccountTrade(AccountTradeBean accountTrade,ResultBean resultBean) throws TradeException{
        try {
            TxnsLogModel txnsLog = super.get(accountTrade.getTxnseqno());
            txnsLog.setPayretinfo(resultBean.getErrMsg());
            txnsLog.setPayretcode(resultBean.getErrCode());
            if(resultBean.isResultBool()){
                txnsLog.setPayordfintime(DateUtil.getCurrentDateTime());
                txnsLog.setRetcode("0000");
                txnsLog.setRetinfo("支付成功");
            }else{
                txnsLog.setRetcode(resultBean.getErrCode());
                txnsLog.setRetinfo(resultBean.getErrMsg());
            }
            super.update(txnsLog);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new TradeException("AC06");
        }
    }
    
    @Transactional(readOnly=true)
    public TxnsLogModel queryLogByTradeseltxn(String queryId){
        return super.getUniqueByHQL(" from TxnsLogModel where  tradeseltxn = ? ", new Object[]{queryId});
    }
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateAppStatus(String txnseqno,String appOrderStatus,String appOrderinfo){
        String hql = "update TxnsLogModel set appordfintime = ?,apporderstatus = ?,apporderinfo = ? where txnseqno = ?";
        super.updateByHQL(hql,new Object[]{DateUtil.getCurrentDateTime(),appOrderStatus,appOrderinfo,txnseqno});
    }
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void tradeRiskControl(String txnseqno,String merchId,String subMerchId,String memberId,String busiCode,String txnAmt,String cardType,String cardNo) throws TradeException{
        log.info("trade risk control start");
        int riskLevel = 0;
        int riskOrder = 0;
        RiskLevelEnum riskLevelEnum = null;
        String riskInfo = "";
        log.info("risk paramaters:"+merchId+"|"+subMerchId+"|"+memberId+"|"+busiCode+"|"+txnAmt+"|"+cardType+"|"+cardNo);
        @SuppressWarnings("unchecked")
		List<Map<String, Object>> riskList = (List<Map<String, Object>>) super.queryBySQL("SELECT FNC_GETRISK(?,?,?,?,?,?,?) AS RISK FROM DUAL", 
                new Object[]{merchId,subMerchId,memberId,busiCode,txnAmt,cardType,cardNo});
        log.info("trade risk result:"+JSON.toJSONString(riskList));
        if(riskList.size()>0){
            riskInfo = riskList.get(0).get("RISK")+"";
            if(riskInfo.indexOf(",")>0){
            	String[] riskInfos =riskInfo.split(",");
            	try {
    				riskOrder = Integer.valueOf(riskInfos[0]);
    				riskLevel = Integer.valueOf(riskInfos[1]);
    				riskLevelEnum = RiskLevelEnum.fromValue(riskLevel);
    				log.info("riskOrder:"+riskOrder);
    				log.info("riskLevel:"+riskLevel);
    			} catch (Exception e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    				throw new TradeException("T034");
    			}
            }else{
            	riskLevelEnum = RiskLevelEnum.fromValue(Integer.valueOf(riskInfo));
            }
            
            
        }else{
            throw new TradeException("T034");
        }
        if(RiskLevelEnum.PASS==riskLevelEnum){//交易通过
            return;
        }
        //记录风控日志
        List<Map<String,String>> riskStrategyList =  getRiskStrategy(riskOrder);
        log.info("riskStrategyList:"+JSON.toJSONString(riskStrategyList));
        if(riskStrategyList.size()>0){
            TxnsLogModel txnsLog = getTxnsLogByTxnseqno(txnseqno);
            Map<String,String> riskMap = riskStrategyList.get(0);
            RiskTradeLogModel tradeLog = new RiskTradeLogModel();
            tradeLog.setRiskTradeType(riskOrder);
            tradeLog.setTxnseqno(txnseqno);
            tradeLog.setOrderno(txnsLog.getAccordno());
            tradeLog.setMerchno(txnsLog.getAccfirmerno());
            //tradeLog.setMerchname(txnsLog.getaccm);
            tradeLog.setSubmerchno(txnsLog.getAccsecmerno());
            // tradeLog.setSummerchname("");
            tradeLog.setMemberid(txnsLog.getAccmemberid());
            tradeLog.setAmount(txnsLog.getAmount());
            tradeLog.setPan(cardNo); 
            //tradeLog.inpan ;
            tradeLog.setTradeRiskLevel(riskLevel);  ;
            Map<String, Object> cardMap = getCardInfo(cardNo);
            tradeLog.setCardIssuerCode(cardMap.get("BANKCODE")+""); ;
            tradeLog.setCardIssuerName(cardMap.get("BANKNAME")+""); ;
            tradeLog.setAcqInistCode(txnsLog.getAccordinst());
            tradeLog.setAcqdatetime(txnsLog.getAccordcommitime()); ;
            tradeLog.setBusicode(busiCode);
            tradeLog.setBusitype(txnsLog.getBusitype());
            tradeLog.setStrategy(riskMap.get("STRATEGY"));
            riskTradeLogService.saveTradeLog(tradeLog);
        }
        if(RiskLevelEnum.REFUSE==riskLevelEnum){//交易拒绝
            throw new TradeException("T035");
        }else{
            
        }
        log.info(" trade risk control end");
    }
    
    @SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
    public List<Map<String,String>> getRiskStrategy(int orders){
        String sql = "select * from T_RISK_LIST where ORDERS = ?";
        return (List<Map<String, String>>) super.queryBySQL(sql, new Object[]{orders});
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateAccBusiCode(String txnseqno, String busicode) {
        // TODO Auto-generated method stub
        String hql = "update TxnsLogModel set accbusicode = ? where txnseqno = ? ";
        super.updateByHQL(hql, new Object[]{busicode,txnseqno});
    }


    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void initretMsg(String txnseqno) throws TradeException {
        // TODO Auto-generated method stub
        try {
            String hql = "update TxnsLogModel set payretcode = '',payretinfo='',retcode='',retinfo='' where txnseqno = ? ";
            super.updateByHQL(hql, new Object[]{txnseqno});
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new TradeException("AC06");
        }
    }


    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateTxnsLog(TxnsLogModel txnsLog) {
        // TODO Auto-generated method stub
        super.update(txnsLog);
    }
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateAnonOrderToMemberOrder(String txnseqno,String memberId){
    	 try {
             String hql = "update TxnsLogModel set accmemberid = ?  where txnseqno = ? ";
             super.updateByHQL(hql, new Object[]{memberId,txnseqno});
             
             hql = "update TxnsOrderinfoModel set memberid = ?  where relatetradetxn = ? ";
             super.updateByHQL(hql, new Object[]{memberId,txnseqno});
             
         } catch (Exception e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
             
         }
    }


    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void saveTransferLogs(List<PojoTranData> transferDataList) {
        for(PojoTranData data : transferDataList){
        	
        	TxnsLogModel txnsLog = getTxnsLogByTxnseqno(data.getTxnseqno());
            if(txnsLog!=null){
                continue;
            }
            txnsLog = new TxnsLogModel();
            MemberBaseModel member = memberService.get(data.getMemberId());
            txnsLog.setTxnseqno(data.getTxnseqno());
            txnsLog.setTxndate(DateUtil.getCurrentDate());
            txnsLog.setTxntime(DateUtil.getCurrentTime());
            if("00".equals(data.getBusiType())){//代付
            	txnsLog.setBusicode(BusiTypeEnum.insteadPay.getCode());
                txnsLog.setBusitype(BusinessEnum.INSTEADPAY.getBusiCode());
                txnsLog.setPaytype("04"); //支付类型（01：快捷，02：网银，03：账户,07：退款）
            }else if("01".equals(data.getBusiType())){
            	txnsLog.setBusicode(BusiTypeEnum.withdrawal.getCode());
                txnsLog.setBusitype(BusinessEnum.WITHDRAWALS.getBusiCode());
                txnsLog.setPaytype("04"); //支付类型（01：快捷，02：网银，03：账户,07：退款）
            }else if("02".equals(data.getBusiType())){
            	txnsLog.setBusicode(BusiTypeEnum.refund.getCode());
                txnsLog.setBusitype(BusinessEnum.REFUND_BANK.getBusiCode());
                txnsLog.setPaytype("07"); //支付类型（01：快捷，02：网银，03：账户,07：退款）
            }
            
            txnsLog.setAmount(data.getTranAmt().longValue());
            txnsLog.setRiskver(member.getRiskver());
            txnsLog.setSplitver(member.getSpiltver());
            txnsLog.setFeever(member.getFeever());
            txnsLog.setPrdtver(member.getPrdtver());
            txnsLog.setCheckstandver(member.getCashver());
            txnsLog.setRoutver(member.getRoutver());
            //txnsLog.setAccordno(data.getRelatedorderno());
            txnsLog.setAccordinst(member.getMerchinsti());
            txnsLog.setAccfirmerno(data.getMemberId());
            txnsLog.setAccordcommitime(DateUtil.getCurrentDateTime());
            txnsLog.setTradestatflag("00000000");//交易初始状态
            txnsLog.setAccsettledate(DateUtil.getSettleDate(Integer.valueOf(member.getSetlcycle().toString())));
            
            txnsLog.setPayordno(data.getTranDataSeqNo());//支付定单号
            txnsLog.setPayinst(ChannelEnmu.CMBCINSTEADPAY.getChnlcode());//支付所属机构
            txnsLog.setPayfirmerno(ConsUtil.getInstance().cons.getCmbc_insteadpay_merid());//支付一级商户号
            txnsLog.setPayordcomtime(DateUtil.getCurrentDateTime());//支付定单提交时间
            //卡信息
            txnsLog.setPan(data.getAccNo());
            /*Map<String, Object> cardMap = getCardInfo(data.getAccNo());
            txnsLog.setCardtype(cardMap.get("TYPE").toString());
            txnsLog.setCardinstino(cardMap.get("BANKCODE").toString());*/
            txnsLog.setTxnfee(data.getTranFee().longValue());
            super.save(txnsLog);
        }
        
    }


    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void saveTxnsLog(TxnsLogModel txnsLogModel) throws TradeException {
        try {
            super.save(txnsLogModel);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new TradeException("T016");
        }
        
    }
    
    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void updateBankTransferOfTxnsLog(TxnsLogModel txnsLog){
        String hql = "update TxnsLogModel set paytype=?, payordno = ?,payinst = ?,payfirmerno = ?,payordcomtime = ?, pan = ?,panName = ?,txnfee = ? where txnseqno = ? ";
        Object[] paramaters = new Object[]{txnsLog.getPaytype(),txnsLog.getPayordno(),txnsLog.getPayinst(),txnsLog.getPayfirmerno(),txnsLog.getPayordcomtime(),txnsLog.getPan(),
        		txnsLog.getPanName(),txnsLog.getTxnfee(),txnsLog.getTxnseqno()};
        super.updateByHQL(hql, paramaters);
    }


	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void saveBankTransferLogs(List<PojoBankTransferData> transferDataList) {
		// TODO Auto-generated method stub
		for(PojoBankTransferData data : transferDataList){
        	TxnsLogModel txnsLog = getTxnsLogByTxnseqno(data.getTranData().getTxnseqno());
            if(txnsLog!=null){
            	if("4000".equals(txnsLog.getBusitype())){
                    txnsLog.setPaytype("07"); //支付类型（01：快捷，02：网银，03：账户 07退款）
                    txnsLog.setPayordno(data.getBankTranDataSeqNo());//支付定单号
                    txnsLog.setPayinst(ChannelEnmu.CMBCINSTEADPAY.getChnlcode());//支付所属机构
                    txnsLog.setPayfirmerno(ConsUtil.getInstance().cons.getCmbc_insteadpay_merid());//支付一级商户号
                    txnsLog.setPayordcomtime(DateUtil.getCurrentDateTime());//支付定单提交时间
                    //卡信息
                    txnsLog.setPan(data.getAccNo());
                    txnsLog.setPanName(data.getAccName());
                    txnsLog.setTxnfee(data.getTranData().getTranFee().longValue());
                    updateBankTransferOfTxnsLog(txnsLog);
            	}else if("3000".equals(txnsLog.getBusitype())){
            		txnsLog.setPaytype("04"); //支付类型（01：快捷，02：网银，03：账户 04代付）
                    txnsLog.setPayordno(data.getBankTranDataSeqNo());//支付定单号
                    txnsLog.setPayinst(ChannelEnmu.CMBCINSTEADPAY.getChnlcode());//支付所属机构
                    txnsLog.setPayfirmerno(ConsUtil.getInstance().cons.getCmbc_insteadpay_merid());//支付一级商户号
                    txnsLog.setPayordcomtime(DateUtil.getCurrentDateTime());//支付定单提交时间
                    //卡信息
                    txnsLog.setPan(data.getAccNo());
                    txnsLog.setPanName(data.getAccName());
                    txnsLog.setTxnfee(data.getTranData().getTranFee().longValue());
                    updateBankTransferOfTxnsLog(txnsLog);
            	}
            	
                continue;
            }
            txnsLog = new TxnsLogModel();

            
            PojoMember mbmberByMemberId = memberService2.getMbmberByMemberId(data.getTranData().getMemberId(), null);
            PojoCoopInsti coopInsti = coopInstiDAO.get(mbmberByMemberId.getInstiId());
            PojoMerchDeta member = merchService.getMerchBymemberId(data.getTranData().getMemberId());
            PojoMember memberPojoMember = memberService2.getMbmberByMemberId(data.getTranData().getMemberId(), null);
            PojoCoopInsti pojoCoopInsti = coopInstiDAO.get(memberPojoMember.getInstiId());

            txnsLog.setTxnseqno(data.getTranData().getTxnseqno());
            txnsLog.setTxndate(DateUtil.getCurrentDate());
            txnsLog.setTxntime(DateUtil.getCurrentTime());
            if("00".equals(data.getTranData().getBusiType())){//代付
            	txnsLog.setBusicode(BusinessEnum.INSTEADPAY.getBusiCode());
                txnsLog.setBusitype(BusiTypeEnum.insteadPay.getCode());
            }else if("01".equals(data.getTranData().getBusiType())){
            	txnsLog.setBusicode(BusinessEnum.WITHDRAWALS.getBusiCode());
                txnsLog.setBusitype(BusiTypeEnum.withdrawal.getCode());
            }else if("02".equals(data.getTranData().getBusiType())){
            	txnsLog.setBusicode(BusinessEnum.REFUND_BANK.getBusiCode());
                txnsLog.setBusitype(BusiTypeEnum.refund.getCode());
            }
            
            txnsLog.setAmount(data.getTranAmt().longValue());
            txnsLog.setRiskver(member.getRiskVer());
            txnsLog.setSplitver(member.getSpiltVer());
            txnsLog.setFeever(member.getFeeVer());
            txnsLog.setPrdtver(member.getPrdtVer());
           
            txnsLog.setRoutver(member.getRoutVer());
            if("7000".equals(txnsLog.getBusitype())){
            	PojoInsteadPayDetail detail = insteadPayDetailDAO.getDetailByTxnseqno(data.getTranData().getTxnseqno());
            	txnsLog.setAccordno(detail.getOrderId());
            }
            

            txnsLog.setAcccoopinstino(coopInsti.getInstiCode());
            txnsLog.setAccfirmerno(data.getTranData().getMemberId());

            txnsLog.setAccordinst(pojoCoopInsti.getInstiCode());
            txnsLog.setAccfirmerno(pojoCoopInsti.getInstiCode());
            txnsLog.setAccsecmerno(data.getTranData().getMemberId());

            txnsLog.setAccordcommitime(DateUtil.getCurrentDateTime());
            txnsLog.setTradestatflag("00000000");//交易初始状态
            txnsLog.setAccsettledate(DateUtil.getSettleDate(Integer.valueOf(member.getSetlCycle().toString())));
            txnsLog.setPaytype("04"); //支付类型（01：快捷，02：网银，03：账户）
            txnsLog.setPayordno(data.getBankTranDataSeqNo());//支付定单号
            txnsLog.setPayinst(ChannelEnmu.fromValue(data.getBankTranBatch().getChannel().getBankChannelCode()).getChnlcode());//支付所属机构
            txnsLog.setPayfirmerno(ConsUtil.getInstance().cons.getCmbc_insteadpay_merid());//支付一级商户号
            txnsLog.setPayordcomtime(DateUtil.getCurrentDateTime());//支付定单提交时间
            //卡信息
            txnsLog.setPan(data.getAccNo());
            txnsLog.setPanName(data.getAccName());
            /*Map<String, Object> cardMap = getCardInfo(data.getAccNo());
            txnsLog.setCardtype(cardMap.get("TYPE").toString());
            txnsLog.setCardinstino(cardMap.get("BANKCODE").toString());
            txnsLog.setTxnfee(data.getTranData().getTranFee().longValue());*/
            txnsLog.setTxnfee(data.getTranData().getTranFee().longValue());
            super.save(txnsLog);
        }
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void saveBossPayBankTransferLogs(List<PojoBankTransferData> transferDataList) {
		// TODO Auto-generated method stub
		for(PojoBankTransferData data : transferDataList){
        	TxnsLogModel txnsLog = getTxnsLogByTxnseqno(data.getTranData().getTxnseqno());
            if(txnsLog!=null){
                continue;
            }
            txnsLog = new TxnsLogModel();
            MemberBaseModel member = memberService.get(data.getTranData().getMemberId());
            txnsLog.setTxnseqno(data.getTranData().getTxnseqno());
            txnsLog.setTxndate(DateUtil.getCurrentDate());
            txnsLog.setTxntime(DateUtil.getCurrentTime());
            if("00".equals(data.getTranData().getBusiType())){//代付
            	txnsLog.setBusicode(BusinessEnum.INSTEADPAY.getBusiCode());
                txnsLog.setBusitype(BusiTypeEnum.insteadPay.getCode());
            }else if("01".equals(data.getTranData().getBusiType())){
            	txnsLog.setBusicode(BusinessEnum.WITHDRAWALS.getBusiCode());
                txnsLog.setBusitype(BusiTypeEnum.withdrawal.getCode());
            }else if("02".equals(data.getTranData().getBusiType())){
            	txnsLog.setBusicode(BusinessEnum.REFUND_BANK.getBusiCode());
                txnsLog.setBusitype(BusiTypeEnum.refund.getCode());
            }
            
            txnsLog.setAmount(data.getTranAmt().longValue());
            txnsLog.setRiskver(member.getRiskver());
            txnsLog.setSplitver(member.getSpiltver());
            txnsLog.setFeever(member.getFeever());
            txnsLog.setPrdtver(member.getPrdtver());
            txnsLog.setCheckstandver(member.getCashver());
            txnsLog.setRoutver(member.getRoutver());
            //txnsLog.setAccordno(data.getRelatedorderno());
            txnsLog.setAccordinst(member.getMerchinsti());
            txnsLog.setAccfirmerno(data.getTranData().getMemberId());
            txnsLog.setAccordcommitime(DateUtil.getCurrentDateTime());
            txnsLog.setTradestatflag("00000000");//交易初始状态
            txnsLog.setAccsettledate(DateUtil.getSettleDate(Integer.valueOf(member.getSetlcycle().toString())));
            txnsLog.setPaytype("04"); //支付类型（01：快捷，02：网银，03：账户）
            txnsLog.setPayordno(data.getTranData().getBusiDataId()+"");//支付定单号
            txnsLog.setPayinst(ChannelEnmu.BOSSPAYCOLLECTION.getChnlcode());//支付所属机构
            txnsLog.setPayfirmerno(ConsUtil.getInstance().cons.getBosspay_user_id());//支付一级商户号
            txnsLog.setPayordcomtime(DateUtil.getCurrentDateTime());//支付定单提交时间
            //卡信息
            txnsLog.setPan(data.getAccNo());
            Map<String, Object> cardMap = getCardInfo(data.getAccNo());
            txnsLog.setCardtype(cardMap.get("TYPE").toString());
            txnsLog.setCardinstino(cardMap.get("BANKCODE").toString());
            txnsLog.setTxnfee(data.getTranData().getTranFee().longValue());
            super.save(txnsLog);
        }
	}

    @Override
    public List<?> getAllMemberByDate(String date) {
        String queryString="select distinct t.ACCSECMERNO, t.ACCSETTLEDATE from t_txns_log t where t.ACCSETTLEDATE=? and t.ACCSECMERNO is not null and SUBSTR (trim(t.retcode),-2) = '00'";
        return (List<?>) super.queryBySQL(queryString, new Object[]{date});
       
    }


    @Override
    public List<?> getAllMemberByDateByCharge(String date) {
        String queryString="select distinct m.member_id ,t.INTIME from t_txns_charge t left join t_member m on t.memberid=m.mem_id  where trunc(t.intime)=TO_DATE(?,'YYYYMMDD')";
        return (List<?>) super.queryBySQL(queryString, new Object[]{date});
    }
 // 消费
    @Override
    public List<?> getSumExpense(String memberId, String date) {
        String queryString = "select count(*) total,"
                + " sum (t.amount) totalAmount," + " sum(t.txnfee) totalfee"
                + " from t_txns_log t" + " where"
                + " t.ACCSECMERNO = ?" + " and t.ACCSETTLEDATE = ?"
                + " and t.busicode in (10000001,10000002)"
                + " and SUBSTR(trim(t.retcode), -2) = '00'";
        return (List<?>) super.queryBySQL(queryString, new Object[]{memberId,
                date});
    }

    
    @Override
    public List<?>  getAllMemberDetailedByDate(String memberId,String date){
    	String queryString = "select t.ACCORDNO,t.TXNSEQNO,t.ACCORDCOMMITIME,t.ACCSETTLEDATE,t.amount,t.busicode,t.TXNFEE, t.TXNSEQNO_OG from t_txns_log t left join t_bnk_txn b on t.payordno=b.payordno where (b.status=9 or b.status is null) and t.accsecmerno=? and t.ACCSETTLEDATE=? and t.payordno is not null and SUBSTR (trim(t.retcode), -2) = '00'  and t.busicode in ('10000001','10000002','40000001','40000002')";
        return (List<?>) super.queryBySQL(queryString, new Object[]{memberId,date});
    }


    @Override
    public List<?> getSumRefund(String memberId, String date) {
        String queryString = "select count(*) total,"
                + " sum(t.amount) totalAmount,"
                + " sum(t.txnfee) totalfee" + " from t_txns_log t"
                + " where t.ACCSECMERNO = ?" + " and t.ACCSETTLEDATE = ?"
                + " and t.busicode in (40000001,40000002)"
                + " and SUBSTR(trim(t.retcode), -2) = '00'";
        return (List<?>) super.queryBySQL(queryString, new Object[]{memberId,
                date});
    }


    @Override
    public List<?> getCountSpendingAccount(String memberId, String date) {
        String queryString="select count(*) total ,sum(t.amount+t.txnfee) clearing ,sum(t.txnfee) totalfee  from  t_txns_log t where t.ACCSECMERNO=?  and t.ACCSETTLEDATE=? and t.busicode in (10000002) and SUBSTR (trim(t.retcode),-2) = '00'";
        return (List<?>) super.queryBySQL(queryString, new Object[]{memberId,date});
    }


    @Override
    public List<?> getCountHandPay(String memberId, String date) {
        String queryString="select count(*) total ,sum(t.amount) clearing   from t_txns_charge t ,t_member m where t.memberid=m.mem_id and m.MEMBER_ID=? and trunc(t.CVLEXATIME)=TO_DATE(?,'YYYYMMDD')   ";//
        return (List<?>) super.queryBySQL(queryString, new Object[]{memberId,date});//,date
    }
    
    @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void excuteRecon(){
    	log.info("start ReconJob");
    	
		List<Map<String, Object>> selfTxnList = (List<Map<String, Object>>) queryBySQL("SELECT * FROM T_SELF_TXN T WHERE STATUS = ? AND RESULT = ?", new Object[]{"9","02"});
		if(selfTxnList.size()>0){
			for(Map<String, Object> value:selfTxnList){
				TxnsLogModel txnsLog = getTxnsLogByTxnseqno(value.get("TXNSEQNO")+"");
				log.info("txnsLog:"+txnsLog.getTxnseqno());
				//通道手续费
				Long channelFee = Long.valueOf(StringUtil.isEmpty(value.get("CFEE")+"")?"0":value.get("CFEE")+"")+Long.valueOf(StringUtil.isEmpty(value.get("DFEE")+"")?"0":value.get("DFEE")+"");
				String payMemberId = "";
        		String payToMemberId = "";
				 //记录提现账务
	            try {
	            	//List<Map<String, Object>> businessList = (List<Map<String, Object>>) txnsLogService.queryBySQL("SELECT * FROM T_BUSINESS WHERE BUSICODE = ?", new Object[]{value.get("BUSICODE").toString()});
	            	//if(businessList.size()>0){
	            		String busiType = txnsLog.getBusitype();
	            		BusiTypeEnum busiTypeEnum = BusiTypeEnum.fromValue(busiType);
	            		switch (busiTypeEnum) {
							case charge:
								if(StringUtil.isEmpty(txnsLog.getAccsecmerno())){
					                payMemberId = txnsLog.getAccmemberid();//
					                payToMemberId = txnsLog.getAccmemberid();
					            }else{
					                String memberId = txnsLog.getAccmemberid();
					                if(StringUtil.isEmpty(memberId)){
					                    break;
					                }
					                payMemberId = memberId;
					                payToMemberId = memberId;
					            }
								break;
							case consumption:
								/**付款方会员ID**/
				                payMemberId = StringUtil.isNotEmpty(txnsLog.getAccmemberid())?txnsLog.getAccmemberid():"999999999999999";
				                /**收款方会员ID**/
				                payToMemberId = StringUtil.isEmpty(txnsLog.getAccsecmerno())?txnsLog.getAccfirmerno():txnsLog.getAccsecmerno();
				                /**渠道**/
				                String channelId = txnsLog.getPayinst();//支付机构代码
				                if("99999999".equals(channelId)){
				                    payMemberId = txnsLog.getPayfirmerno();
				                }else{
				                	
				                }
								break;
							case insteadPay:
								  payMemberId = StringUtil.isEmpty(txnsLog.getAccsecmerno())?txnsLog.getAccfirmerno():txnsLog.getAccsecmerno();
							      payToMemberId = "999999999999999";
								break;
							case refund:
								break;
							case withdrawal:
								if(StringUtil.isEmpty(txnsLog.getAccmemberid())||"999999999999999".equals(txnsLog.getAccmemberid())){
									payMemberId = txnsLog.getAccsecmerno();
							        payToMemberId = txnsLog.getAccsecmerno();
								}else{
									payMemberId = txnsLog.getAccmemberid();
							        payToMemberId = txnsLog.getAccmemberid();
								}
								break;
							default:
								break;
						}
	            	//}
					TradeInfo tradeInfo = new TradeInfo();
					tradeInfo.setBusiCode(txnsLog.getBusicode());
					tradeInfo.setPayMemberId(payMemberId);
					tradeInfo.setPayToMemberId(payToMemberId);
					tradeInfo.setAmount(new BigDecimal(value.get("AMOUNT")+""));
					tradeInfo.setCharge(new BigDecimal(txnsLog.getTxnfee()));
					tradeInfo.setTxnseqno(value.get("TXNSEQNO")+"");
					tradeInfo.setChannelId(txnsLog.getPayinst());
					tradeInfo.setChannelFee(new BigDecimal(channelFee));
					tradeInfo.setCoopInstCode(txnsLog.getAcccoopinstino());
					log.info(JSON.toJSONString(tradeInfo));
					accEntryService.accEntryProcess(tradeInfo, EntryEvent.RECON_SUCCESS);
				
					executeBySQL("UPDATE T_SELF_TXN SET RESULT=? WHERE TID = ?", new Object[]{"03",value.get("TID")});
				} catch (AccBussinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (AbstractBusiAcctException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalEntryRequestException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
			}
		}
		log.info("end ReconJob");
    }
    
    @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void excuteSetted() throws AccBussinessException, AbstractBusiAcctException, NumberFormatException, IllegalEntryRequestException{
    	log.info("start excuteSetted Job");
    	List<Map<String, Object>> selfTxnList = (List<Map<String, Object>>) queryBySQL("SELECT * FROM T_SELF_TXN T WHERE STATUS = ? AND RESULT = ?", new Object[]{"9","03"});
    	if(selfTxnList.size()>0){
    		for(Map<String, Object> value:selfTxnList){
    			TxnsLogModel txnsLog = getTxnsLogByTxnseqno(value.get("TXNSEQNO")+"");
    			
				log.info("txnsLog:"+txnsLog.getTxnseqno());
				//通道手续费
				Long channelFee = Long.valueOf(StringUtil.isEmpty(value.get("CFEE")+"")?"0":value.get("CFEE")+"")+Long.valueOf(StringUtil.isEmpty(value.get("DFEE")+"")?"0":value.get("DFEE")+"");
				String payMemberId = "";
        		String payToMemberId = "";
        		String busiType = txnsLog.getBusitype();
        		BusiTypeEnum busiTypeEnum = BusiTypeEnum.fromValue(busiType);
        		if(busiTypeEnum != BusiTypeEnum.consumption){//必须是消费交易
        			continue;
        		}
        		//当前日期大于等于清算日期
        		if(Long.valueOf(DateUtil.getCurrentDate())>=Long.valueOf(txnsLog.getAccsettledate())){
        			/**付款方会员ID**/
                    payMemberId = StringUtil.isNotEmpty(txnsLog.getAccmemberid())?txnsLog.getAccmemberid():"999999999999999";
                    /**收款方会员ID**/
                    payToMemberId = StringUtil.isEmpty(txnsLog.getAccsecmerno())?txnsLog.getAccfirmerno():txnsLog.getAccsecmerno();
                    /**渠道**/
                    String channelId = txnsLog.getPayinst();//支付机构代码
                    if("99999999".equals(channelId)){
                        payMemberId = txnsLog.getPayfirmerno();
                    }else{
                    	
                    }
                    
    					TradeInfo tradeInfo = new TradeInfo();
    					tradeInfo.setBusiCode(txnsLog.getBusicode());
    					tradeInfo.setPayMemberId(payMemberId);
    					tradeInfo.setPayToMemberId(payToMemberId);
    					tradeInfo.setAmount(new BigDecimal(value.get("AMOUNT")+""));
    					tradeInfo.setCharge(new BigDecimal(txnsLog.getTxnfee()));
    					tradeInfo.setTxnseqno(value.get("TXNSEQNO")+"");
    					tradeInfo.setChannelId(txnsLog.getPayinst());
    					tradeInfo.setChannelFee(new BigDecimal(channelFee));
    					tradeInfo.setCoopInstCode(txnsLog.getAcccoopinstino());
    					accEntryService.accEntryProcess(tradeInfo, EntryEvent.SETTED);
    					executeBySQL("UPDATE T_SELF_TXN SET RESULT=? WHERE TID = ?", new Object[]{"04",value.get("TID")});
    				
        		}
        		}
        		
    	}
    	log.info("end excuteSetted Job");
    }
    
    @Override
    public List<?> getInsteadMemberByDate(String date){
        String queryString="select distinct t.ACCSECMERNO, t.ACCSETTLEDATE from t_txns_log t where t.ACCSETTLEDATE=? and t.ACCSECMERNO is not null and SUBSTR (trim(t.retcode),-2) = '00' and t.busicode='70000001'";
        List<?> result = (List<?>) super.queryBySQL(queryString, new Object[]{date});
        return result;
    }
    @Override
    public List<?> getSumInstead(String memberId, String date){
        String queryString = "select count(*) total,"
                + " sum (t.amount) totalAmount," + " sum(t.txnfee) totalfee"
                + " from t_txns_log t" + " where"
                + " t.ACCSECMERNO = ?" + " and t.ACCSETTLEDATE = ?"
                + " and t.busicode in (70000001)"
                + " and SUBSTR(trim(t.retcode), -2) = '00'";
        return (List<?>) super.queryBySQL(queryString, new Object[]{memberId,
                date});
    }
    @Override
    public List<?> getInsteadMerchantDetailedByDate(String memberId, String date){
        String queryString = "select t.ACCORDNO,t.TXNSEQNO,t.ACCORDCOMMITIME,t.ACCSETTLEDATE,t.amount,t.busicode,t.TXNFEE,t.PAYORDCOMTIME from t_txns_log t left join t_bnk_txn b on t.payordno=b.payordno where (b.status=9 or b.status is null) and t.accsecmerno=? and t.ACCSETTLEDATE=? and t.payordno is not null and SUBSTR (trim(t.retcode), -2) = '00'  and t.busicode in ('70000001')";
        return (List<?>) super.queryBySQL(queryString, new Object[]{memberId,date});
    
    }


	/**
	 * 获取微信支付交易流水
	 * @param payOrderNo
	 * @return
	 */
	@Override
	@Transactional(readOnly=true)
	public TxnsLogModel getTxnsLogByPayOrderNo(String payOrderNo) {
		return super.getUniqueByHQL(" from TxnsLogModel where  payordno = ? and paytype = ?", new Object[]{payOrderNo,"05"});
	}


	/**
	 *
	 * @param txnseqno
	 * @param payrettsnseqno
	 * @param retcode
	 * @param retinfo
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void updateWeChatRefundResult(String txnseqno,
			String payrettsnseqno, String retcode, String retinfo) {
		TxnsLogModel txnsLog = super.get(txnseqno);
		txnsLog.setPayrettsnseqno(payrettsnseqno);
        txnsLog.setPayretcode(retcode);
        txnsLog.setPayretinfo(retinfo);
        txnsLog.setPayordfintime(DateUtil.getCurrentDateTime());
        txnsLog.setAccordfintime(DateUtil.getCurrentDateTime());
        txnsLog.setTradeseltxn(UUIDUtil.uuid());
    	txnsLog.setTradetxnflag("10000000");
    	txnsLog.setRelate("10000000");
    	txnsLog.setRetdatetime(DateUtil.getCurrentDateTime());
    	try {
            PojoRspmsg msg = rspmsgDAO.getRspmsgByChnlCode(ChnlTypeEnum.WECHAT,retcode);
            txnsLog.setRetinfo(msg.getRspinfo());
            txnsLog.setRetcode(msg.getWebrspcode());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        super.update(txnsLog);
	}


	@Override
	@Transactional(readOnly=true)
	public List<?> getRefundOrderInfo(String channelId,int mins ) {
		StringBuffer sb= new StringBuffer();
		sb.append(" select ttl.txnseqno ");
		sb.append(" from t_txns_log ttl ");
		sb.append(" join  t_txns_refund ttr  on ttr.reltxnseqno= ttl.txnseqno  ");
		sb.append(" join t_txns_orderinfo  tto on tto.relatetradetxn= ttl.txnseqno  ");
		sb.append(" where 1=1 ");
		//退款类型
		//sb.append(" and ttr.refundtype=? ");
		//微信渠道
		sb.append(" and ttl.payinst=? ");
		//订单状态为待支付
		sb.append(" and tto.status in (?,?) ");
		//退款申请成功的
		sb.append(" and ttl.payretcode=? ");
		sb.append(" and ceil((to_date(?, 'yyyy-mm-dd hh24:mi:ss')-to_date(ttl.payordcomtime,'yyyy-mm-dd hh24:mi:ss'))*24*60)>=? ");
		List<?> result = (List<?>) super.queryBySQL(sb.toString(), new Object[]{channelId==null?ChannelEnmu.WEBCHAT.getChnlcode():channelId,OrderStatusEnum.INITIAL.getStatus(),OrderStatusEnum.PAYING.getStatus(),"SUCCESS",DateUtil.getTimeStamp(),mins });
	     return result;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Object> queryTxnsLog(Map<String, Object> map) {
		StringBuffer hql= new StringBuffer();
		hql.append(" select t.txnseqno from TxnsLogModel t ,TxnsOrderinfoModel t1 where t.txnseqno=t1.relatetradetxn ");
		//hql.append(" where 1=1 ");
		if(map!=null){
			//订单状态
			List<String> statList =new ArrayList<String>();
			if(map.get("statList")!=null){
				statList= (List<String>) map.get("statList");
			}
			if(statList.size()>0){
				hql.append(" and t1.status in(");
				for(int i=0;i<statList.size();i++){
					if(i==0){
						hql.append(":statId"+i);
					}else{
						hql.append(",:statId"+i);
					}
				}
				hql.append(") ");
			}
			//支付类型
			String paytype = map.get("paytype")==null?"" :map.get("paytype").toString();
			if(StringUtil.isNotEmpty(paytype)){
				hql.append(" and paytype=:paytype ");
			}
			//支付机构
			String payinst = map.get("payinst")==null?"":map.get("payinst").toString();
			if(StringUtil.isNotEmpty(payinst)){
				hql.append(" and payinst=:payinst ");
			}
			//交易类型
			String busitype = map.get("busitype")==null?"":map.get("busitype").toString();
			if(StringUtil.isNotEmpty(busitype)){
				hql.append(" and busitype=:busitype ");
			}
			hql.append(" order by t.txnseqno ");
			Session session = getSession();
	        Query query = session.createQuery(hql.toString());
			for(int i=0;i<statList.size();i++){
				query.setParameter("statId"+i, statList.get(i));
			}
			if(StringUtil.isNotEmpty(paytype)){
				 query.setParameter("paytype", paytype);
			}
			if(StringUtil.isNotEmpty(busitype)){
				 query.setParameter("busitype", busitype);
			}
			if(StringUtil.isNotEmpty(payinst)){
				query.setParameter("payinst", payinst);
			}
	        return query.list();
		}
	       
		return null;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void updateTradeStatFlag(String txnseqno,TradeStatFlagEnum tradeStatFlagEnum){
		String hql = "update TxnsLogModel set tradestatflag = ? where txnseqno = ?";
		super.updateByHQL(hql, new Object[]{tradeStatFlagEnum.getStatus(),txnseqno});
		if(tradeStatFlagEnum == TradeStatFlagEnum.OVERTIME){//交易超时时加入超时队列中
			TxnsLogModel txnsLog = getTxnsLogByTxnseqno(txnseqno);
			TradeQueueBean tradeQueueBean = new TradeQueueBean();
			tradeQueueBean.setTxnseqno(txnseqno);
			tradeQueueBean.setPayInsti(txnsLog.getPayinst());
			tradeQueueBean.setTxnDateTime(txnsLog.getTxndate()+txnsLog.getTxntime());
			tradeQueueBean.setBusiType(txnsLog.getBusitype());
			tradeQueueService.addTimeOutQueue(tradeQueueBean);
		}else if(tradeStatFlagEnum == TradeStatFlagEnum.PAYING){
			TxnsLogModel txnsLog = getTxnsLogByTxnseqno(txnseqno);
			TradeQueueBean tradeQueueBean = new TradeQueueBean();
			tradeQueueBean.setTxnseqno(txnseqno);
			tradeQueueBean.setPayInsti(txnsLog.getPayinst());
			tradeQueueBean.setTxnDateTime(txnsLog.getTxndate()+txnsLog.getTxntime());
			tradeQueueBean.setBusiType(txnsLog.getBusitype());
			tradeQueueService.addTradeQueue(tradeQueueBean);
		}
		
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void updateCMBCTradeData(PayPartyBean payPartyBean){
		Object[] paramaters = null;
        TxnsLogModel txnsLog = getTxnsLogByTxnseqno(payPartyBean.getTxnseqno());
        Map<String, Object> cardMap = null;
        if(StringUtil.isNotEmpty(payPartyBean.getCardNo())){
        	cardMap = getCardInfo(payPartyBean.getCardNo());
        }
        String hql = "update TxnsLogModel set paytype=?,payordno=?,payinst=?,payfirmerno=?,payordcomtime=?,pan=?,cardtype=?,cardinstino=?,txnfee=?,pan_name=?,payrettsnseqno=?,payretcode=?,payretinfo=?,retcode=?,retinfo=?,tradestatflag=?,payordfintime=?, retdatetime=?,tradetxnflag=?,relate=?,tradeseltxn=? where txnseqno=?";
        
	    try {
	        PojoRspmsg msg = rspmsgDAO.getRspmsgByChnlCode(ChnlTypeEnum.CMBCWITHHOLDING,payPartyBean.getPayretcode().trim());
	        paramaters = new Object[]{
	        		StringUtil.isNotEmpty(payPartyBean.getPaytype())?payPartyBean.getPaytype():"01",
					payPartyBean.getPayordno(),
					payPartyBean.getPayinst(),
					payPartyBean.getPayfirmerno(),
					payPartyBean.getPayordcomtime(),
					payPartyBean.getCardNo(),cardMap==null?"":cardMap.get("TYPE")+"",
				    cardMap==null?"":cardMap.get("BANKCODE")+"",
				    getTxnFee(txnsLog),
				    payPartyBean.getPanName(),
				    payPartyBean.getPayrettsnseqno(),
				    payPartyBean.getPayretcode().trim(),
				    payPartyBean.getPayretinfo().trim(),
				    msg.getWebrspcode(),msg.getRspinfo(),
	        		"0000".equals(msg.getWebrspcode())?TradeStatFlagEnum.FINISH_SUCCESS.getStatus():TradeStatFlagEnum.FINISH_FAILED.getStatus(),
	        		DateUtil.getCurrentDateTime(),
	        		DateUtil.getCurrentDateTime(),
	        		"10000000",
	        		"10000000",
	        		UUIDUtil.uuid(),
	        		payPartyBean.getTxnseqno()};
	    } catch (Exception e) {
	        paramaters = new Object[]{
	        		StringUtil.isNotEmpty(payPartyBean.getPaytype())?payPartyBean.getPaytype():"01",
					payPartyBean.getPayordno(),
					payPartyBean.getPayinst(),
					payPartyBean.getPayfirmerno(),
					payPartyBean.getPayordcomtime(),
					payPartyBean.getCardNo(),cardMap==null?"":cardMap.get("TYPE")+"",
				    cardMap==null?"":cardMap.get("BANKCODE")+"",
				    getTxnFee(txnsLog),
				    payPartyBean.getPanName(),
				    payPartyBean.getPayrettsnseqno(),
				    payPartyBean.getPayretcode().trim(),
				    payPartyBean.getPayretinfo().trim(),
				    payPartyBean.getPayretcode().trim(),
				    payPartyBean.getPayretinfo().trim(),
	        		TradeStatFlagEnum.FINISH_FAILED.getStatus(),
	        		DateUtil.getCurrentDateTime(),
	        		DateUtil.getCurrentDateTime(),
	        		"10000000",
	        		"10000000",
	        		UUIDUtil.uuid(),
	        		payPartyBean.getTxnseqno()};
	    }
	    super.updateByHQL(hql, paramaters);
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void updateSMSErrorData(String txnseqno,String retcode,String retinfo){
    	String hql = "update TxnsLogModel set payretcode=?,payretinfo=?,retcode=?,retinfo=?,tradestatflag=? where txnseqno=?";
    	super.updateByHQL(hql, new Object[]{
    			retcode,
    			retinfo,
    			retcode,
    			retinfo,
    			"0000".equals(retcode)?TradeStatFlagEnum.FINISH_SUCCESS.getStatus():TradeStatFlagEnum.FINISH_FAILED.getStatus(),
    			txnseqno}
    	);
    }
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void updateWeChatTradeData(PayPartyBean payPartyBean){
		Object[] paramaters = null;
       
        String hql = "update TxnsLogModel set payrettsnseqno=?,payretcode=?,payretinfo=?,retcode=?,retinfo=?,tradestatflag=?,payordfintime=?, retdatetime=?,tradetxnflag=?,relate=?,tradeseltxn=? where txnseqno=?";
        
	    try {
	        PojoRspmsg msg = rspmsgDAO.getRspmsgByChnlCode(ChnlTypeEnum.WECHAT,payPartyBean.getPayretcode().trim());
	        paramaters = new Object[]{
				    payPartyBean.getPayrettsnseqno(),
				    payPartyBean.getPayretcode().trim(),
				    payPartyBean.getPayretinfo().trim(),
				    msg.getWebrspcode(),
				    msg.getRspinfo(),
	        		"0000".equals(msg.getWebrspcode())?TradeStatFlagEnum.FINISH_SUCCESS.getStatus():TradeStatFlagEnum.FINISH_FAILED.getStatus(),
	        		DateUtil.getCurrentDateTime(),
	        		DateUtil.getCurrentDateTime(),
	        		"10000000",
	        		"10000000",
	        		UUIDUtil.uuid(),
	        		payPartyBean.getTxnseqno()};
	    } catch (Exception e) {
	        paramaters = new Object[]{
				    payPartyBean.getPayrettsnseqno(),
				    payPartyBean.getPayretcode().trim(),
				    payPartyBean.getPayretinfo().trim(),
				    UnknowRetCodeEnum.WECHAT.getCode(),
				    payPartyBean.getPayretinfo().trim(),
	        		TradeStatFlagEnum.FINISH_FAILED.getStatus(),
	        		DateUtil.getCurrentDateTime(),
	        		DateUtil.getCurrentDateTime(),
	        		"10000000",
	        		"10000000",
	        		UUIDUtil.uuid(),
	        		payPartyBean.getTxnseqno()};
	    }
	    super.updateByHQL(hql, paramaters);
	}
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void updateTradeData(PayPartyBean payPartyBean){
		Object[] paramaters = null;
	       
        String hql = "update TxnsLogModel set payrettsnseqno=?,payretcode=?,payretinfo=?,retcode=?,retinfo=?,tradestatflag=?,payordfintime=?, retdatetime=?,tradetxnflag=?,relate=?,tradeseltxn=? where txnseqno=?";
        
	    try {
	        PojoRspmsg msg = rspmsgDAO.getRspmsgByChnlCode(payPartyBean.getChnlTypeEnum(),payPartyBean.getPayretcode().trim());
	        paramaters = new Object[]{
				    payPartyBean.getPayrettsnseqno(),
				    payPartyBean.getPayretcode().trim(),
				    payPartyBean.getPayretinfo().trim(),
				    msg.getWebrspcode(),
				    msg.getRspinfo(),
	        		"0000".equals(msg.getWebrspcode())?TradeStatFlagEnum.FINISH_SUCCESS.getStatus():TradeStatFlagEnum.FINISH_FAILED.getStatus(),
	        		DateUtil.getCurrentDateTime(),
	        		DateUtil.getCurrentDateTime(),
	        		"10000000",
	        		"10000000",
	        		UUIDUtil.uuid(),
	        		payPartyBean.getTxnseqno()};
	    } catch (Exception e) {
	        paramaters = new Object[]{
				    payPartyBean.getPayrettsnseqno(),
				    payPartyBean.getPayretcode().trim(),
				    payPartyBean.getPayretinfo().trim(),
				    UnknowRetCodeEnum.fromChannl(payPartyBean.getPayinst()).getCode(),
				    payPartyBean.getPayretinfo().trim(),
	        		TradeStatFlagEnum.FINISH_FAILED.getStatus(),
	        		DateUtil.getCurrentDateTime(),
	        		DateUtil.getCurrentDateTime(),
	        		"10000000",
	        		"10000000",
	        		UUIDUtil.uuid(),
	        		payPartyBean.getTxnseqno()};
	    }
	    super.updateByHQL(hql, paramaters);
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void updateTradeFailed(PayPartyBean payPartyBean){
		String hql = "update TxnsLogModel set payretcode=?,payretinfo=?,retcode=?,retinfo=?,tradestatflag=?,payordfintime=? where txnseqno=?";
		Object[] paramaters = null;
		
		try {
	        PojoRspmsg msg = rspmsgDAO.getRspmsgByChnlCode(payPartyBean.getChnlTypeEnum(),payPartyBean.getPayretcode().trim());
	        paramaters = new Object[]{
				    payPartyBean.getPayretcode().trim(),
				    payPartyBean.getPayretinfo().trim(),
				    msg.getWebrspcode(),
				    msg.getRspinfo(),
	        		TradeStatFlagEnum.FINISH_FAILED.getStatus(),
	        		DateUtil.getCurrentDateTime(),
	        		payPartyBean.getTxnseqno()};
	    } catch (Exception e) {
	        paramaters = new Object[]{
				    payPartyBean.getPayretcode().trim(),
				    payPartyBean.getPayretinfo().trim(),
				    UnknowRetCodeEnum.fromChannl(payPartyBean.getPayinst()).getCode(),
				    payPartyBean.getPayretinfo().trim(),
	        		TradeStatFlagEnum.FINISH_FAILED.getStatus(),
	        		DateUtil.getCurrentDateTime(),
	        		payPartyBean.getTxnseqno()};
	    }
		 super.updateByHQL(hql, paramaters);
	}
}
