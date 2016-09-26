/* 
 * BossPayServiceImpl.java  
 * 
 * version TODO
 *
 * 2016年4月6日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bosspay.service.impl;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.wisvalley.esk.util.StringAsc;
import com.wisvalley.key.EncryptRSA;
import com.zlebank.zplatform.trade.bean.ResultBean;
import com.zlebank.zplatform.trade.bean.TradeBean;
import com.zlebank.zplatform.trade.bosspay.bean.BtchcolltnRequestBean;
import com.zlebank.zplatform.trade.bosspay.bean.BtchpmtQueryResponseBean;
import com.zlebank.zplatform.trade.bosspay.bean.BtchpmtRequestBean;
import com.zlebank.zplatform.trade.bosspay.bean.BtchpmtResponseBean;
import com.zlebank.zplatform.trade.bosspay.bean.RealtmcolltnQueryRequestBean;
import com.zlebank.zplatform.trade.bosspay.bean.RealtmcolltnQueryResponseBean;
import com.zlebank.zplatform.trade.bosspay.bean.RealtmcolltnRequestBean;
import com.zlebank.zplatform.trade.bosspay.bean.RealtmcolltnResponseBean;
import com.zlebank.zplatform.trade.bosspay.bean.RealtmpmtRequestBean;
import com.zlebank.zplatform.trade.bosspay.bean.SendMsgBean;
import com.zlebank.zplatform.trade.bosspay.client.ColltnAndPmtService;
import com.zlebank.zplatform.trade.bosspay.client.ColltnAndPmtServiceLocator;
import com.zlebank.zplatform.trade.bosspay.service.BossPayService;
import com.zlebank.zplatform.trade.service.IRouteConfigService;
import com.zlebank.zplatform.trade.utils.ConsUtil;
import com.zlebank.zplatform.trade.utils.DateUtil;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年4月6日 上午11:18:40
 * @since 
 */
@Service("bossPayService")
public class BossPayServiceImpl implements BossPayService{
	private static final Log log = LogFactory.getLog(BossPayServiceImpl.class);
	
	@Autowired
	private IRouteConfigService routeConfigService;
	
	@SuppressWarnings("unused")
	public ResultBean realCollecting(TradeBean trade){
		ResultBean resultBean = null;
		try {
			//查询银行卡的人行联行号（总行）
			String bankNumber = routeConfigService.getCardPBCCode(trade.getCardNo()).get("PBC_BANKCODE")+"";
			String bankName = routeConfigService.getCardPBCCode(trade.getCardNo()).get("BANKNAME")+"";
			if(bankNumber==null){
				resultBean = new ResultBean("T000", "无法获取有效的人行联行号");
				return resultBean;
			}
			RealtmcolltnRequestBean requestBean = new RealtmcolltnRequestBean(DateUtil.getCurrentDateTime(),ConsUtil.getInstance().cons.getBosspay_bank_account(),
					ConsUtil.getInstance().cons.getBosspay_agreement_id(), 
					trade.getCardNo(), 
					trade.getAcctName(), 
					bankNumber,//bankNumber,
					bankName,//bankName, 
					"",//bankProvince, 
					"",//bankCity, 
					trade.getAmount(),
					"09001", 
					"代收业务", 
					ConsUtil.getInstance().cons.getBosspay_userId(),
					ConsUtil.getInstance().cons.getBosspay_user_key());
			if(ConsUtil.getInstance().cons.getBosspay_test_flag()==1){
				requestBean.setBankNumber("313551080046");
			}
			
			ColltnAndPmtService service = new ColltnAndPmtServiceLocator();
			SendMsgBean sendMsgBean = encrypt(JSON.toJSONString(requestBean));
			//String tempMsg = "{ascT:31343436343235383731313337,signature:6535818A74BFEE8ED6819B410480DE5F50D3A19CF771B28156496BE48669EB29568A6D1B151CFA1AFBBA04150D29FF3A901256E94D5613DA4BDE05E1DBF1C4E794DEB9BD5BF83F1264AEB4654D64748676C552DEB1E24F82EBBF149667A181040AF548886B1C41C154CDDA688D2798DB9271F5D6C243376F87ED87874A8AB195,encKey:40BBC0F682C3196669D2BD1746BEBCE50DB1E245C87E4658F6918F1AAD54CCCCC2222D8CE034147770F9BD35FD46C6681892FF0C7E6002FE66A3AA720C68C2DE82C88D760419441B0314DDEB021FF77D882084DE3FE3F8783D904F14B0AD85E5E291AB59B6B515891B42900B9941CABC31BB26BEA18E5AD05474164171CB51C8,encData:CA53968534F4B94BCB405752E7B6A0BC4BFD243E9F7DC82DB48F97A844BCE936015A49CD7641757E614427C9F31FECDEBACF1C784FA2F75AAF2CFB410579F63E5783CF425A1FFEBC9263F791332CDA4602AF6287148EDD77D3D0B6F7A7413B892FCCF511E9B94AB542F645A79D856B08383EFDD7F6F29B527F5D08E963F0FC1449F7F4D66C136521383EFDD7F6F29B523DA0CCF9D693CBAB5915E78FD6CFBAFED5C1FFA3B9CA3D4C5CA2588F8B71C5F5F905CA4F98B0BB99798F369872920033F3901225F879EEDB5BB8DD41D80C2DEE3F2EED85D3ABCAE4C739A2C5173A53645F9186D87DAB8E92F7AAA8B58919DDA9F7DF0EC6AE5BB8EB369CFD6F0B79D0D6FAA3BD821986B514EF6653F05F710A028342BCAAFA6B219FAF2CFB410579F63E62B7E58FDABDAF5D9D1FD3EA16C98800C78D792C0FB84024A5DE0CE3F31A824EA026C8D4A0163DFB047B757030F65930E37504FC9082D0E85A90D126F3278A8ED85BC794C98F079AF8CBB2F886EAB98AC6C59F2190F307849AB602B0558084273C4740121A74E18814B0BBDB70EACE148EB1F09D936A3BBA990DE9B1E747C1D7B744A32EEFB63B2DA80D312BF734AAD0EA4D011CB998BE587F527BC2CD236095F1E4789DF7D5C39D7D5E4162CC8F4D4485241416BE4CB3E7528010096FE90406B5D5968AB9AB18832F1037D17E45205762B7E58FDABDAF5DB96C9DECA2E9F968AEFA5FC0FF425226803B01486DB7A62F808A578CBF3C0D824896F5EA1856DB28AF2CFB410579F63ED97C3CAAE04D0AB1CAC3FBCD9DA21F4F8D6217BEE89975110A4261235614B8E088C15957F6FEF65A,USERID:35}";
			String returnJson = service.getColltnAndPmtServiceImplPort().realTmColltn(JSON.toJSONString(sendMsgBean));
			//String returnJson = service.getColltnAndPmtServiceImplPort().realTmColltn(tempMsg);
			log.info("receive msg:"+returnJson);
			RealtmcolltnResponseBean responseBean = JSON.parseObject(returnJson, RealtmcolltnResponseBean.class);
			if(ConsUtil.getInstance().cons.getBosspay_test_flag()==1){
				RealtmcolltnQueryResponseBean defaultResponseBean = new RealtmcolltnQueryResponseBean();
				defaultResponseBean.setSerialNum(responseBean.getSerialNum());
				defaultResponseBean.setStatus("[\"PR02\",\"已付款\"]");
				defaultResponseBean.setBankAccount(trade.getCardNo());
				resultBean = new ResultBean(defaultResponseBean);
				resultBean.setResultBool(true);
				return resultBean;
			}
			if("0".equals(responseBean.getCode())){
				int [] times = new int[]{2000,4000,8000,16000,32000};
				//查询5次按2的n次方处理
				for(int i=0;i<5;i++){
					ResultBean queryResultBean =  queryRealCollecting(responseBean.getSerialNum());
					RealtmcolltnQueryResponseBean queryResponseBean = (RealtmcolltnQueryResponseBean) queryResultBean.getResultObj();
					List<String> retList = JSON.parseArray(queryResponseBean.getStatus(), String.class);
					String retCode = retList.get(0);
					if("PR10".equals(retCode)){//代收交易成功
						resultBean = new ResultBean(queryResponseBean);
						resultBean.setResultBool(true);
						break;
					}else{
						resultBean = new ResultBean(queryResponseBean);
					}
					Thread.sleep(times[i]);
				}
				
			}else{
				resultBean = new ResultBean(responseBean.getCode(), responseBean.getMessage());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultBean;
	}
	
	
	
	
	/**
	 *
	 * @param serialNum
	 * @return
	 */
	@Override
	public ResultBean queryRealCollecting(String serialNum) {
		ResultBean resultBean = null;
		try {
			RealtmcolltnQueryRequestBean requestBean = new RealtmcolltnQueryRequestBean();
			requestBean.setSerialNum(serialNum);
			requestBean.setUserId("33");
			ColltnAndPmtService service = new ColltnAndPmtServiceLocator();
			SendMsgBean sendMsgBean = encrypt(JSON.toJSONString(requestBean));
			String returnJson = service.getColltnAndPmtServiceImplPort().findRealTmColltn(JSON.toJSONString(sendMsgBean));
			log.info("receive msg:"+returnJson);
			RealtmcolltnQueryResponseBean responseBean = JSON.parseObject(returnJson, RealtmcolltnQueryResponseBean.class);
			resultBean = new ResultBean(responseBean);
			resultBean.setResultBool(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultBean;
	}
	
	private SendMsgBean encrypt(String reqSingleData) throws Exception{
		log.info("发出明文报文:"+  reqSingleData ); 
		byte[] symKeyBytes = new byte[]{0x12,0x21,0x23,0x32,0x53,0x35,0x62,0x26,
		          0x12,0x21,0x23,0x32,0x53,0x35,0x62,0x26,
		          0x12,0x21,0x23,0x32,0x53,0x35,0x62,0x26};
		 
		  String ascTime = StringAsc.String2Asc(String.valueOf(new Date().getTime()));
		  
		  // 加密对称秘钥值   24字节数组
		  String encKey = EncryptRSA.getInstance().encryptSymKey(symKeyBytes,""); 
		  System.out.println(encKey);
		  
		  // 计算签名值
		  String sig = EncryptRSA.getInstance().encryptSignuare(reqSingleData,ascTime,"");
		  System.out.println(sig);
		  
		  // 加密业务值  16字节数组
		  byte[] symKeyBytesTemp = new byte[16];
		  System.arraycopy(symKeyBytes, 0, symKeyBytesTemp, 0, 16);
		  String encData = EncryptRSA.getInstance().encryptDataBySymKey(reqSingleData, symKeyBytesTemp,"");
		  System.out.println(encData);
		  
		  SendMsgBean sendMsgBean = new SendMsgBean();
			sendMsgBean.setAscT(ascTime);
			sendMsgBean.setSignature(sig);
			sendMsgBean.setEncKey(encKey);
			sendMsgBean.setEncData(encData); 
			//sendMsgBean.setUserid(ConsUtil.getInstance().cons.getBosspay_user_id());
			sendMsgBean.setUserid("33");
			log.info("加密后："+JSON.toJSONString(sendMsgBean));
		return sendMsgBean; 
	}




	/**
	 *
	 * @param trade
	 * @return
	 */
	@Override
	public ResultBean realInsteadPay(TradeBean trade) {
		try {
			RealtmpmtRequestBean requestBean = new RealtmpmtRequestBean(ConsUtil.getInstance().cons.getBosspay_bank_account(),
					"1000000004", 
					trade.getCardNo(), 
					trade.getAcctName(), 
					"313551080046",//bankNumber,
					"中国邮政储蓄银行股份有限公司江西省分行",//bankName, 
					"江西恒邦",//bankProvince, 
					"江西恒邦",//bankCity, 
					trade.getAmount(),
					"09001", 
					"123", 
					"33",
					ConsUtil.getInstance().cons.getBosspay_user_key());
			ColltnAndPmtService service = new ColltnAndPmtServiceLocator();
			SendMsgBean sendMsgBean = encrypt(JSON.toJSONString(requestBean));
			//String tempMsg = "{ascT:31343436343235383731313337,signature:6535818A74BFEE8ED6819B410480DE5F50D3A19CF771B28156496BE48669EB29568A6D1B151CFA1AFBBA04150D29FF3A901256E94D5613DA4BDE05E1DBF1C4E794DEB9BD5BF83F1264AEB4654D64748676C552DEB1E24F82EBBF149667A181040AF548886B1C41C154CDDA688D2798DB9271F5D6C243376F87ED87874A8AB195,encKey:40BBC0F682C3196669D2BD1746BEBCE50DB1E245C87E4658F6918F1AAD54CCCCC2222D8CE034147770F9BD35FD46C6681892FF0C7E6002FE66A3AA720C68C2DE82C88D760419441B0314DDEB021FF77D882084DE3FE3F8783D904F14B0AD85E5E291AB59B6B515891B42900B9941CABC31BB26BEA18E5AD05474164171CB51C8,encData:CA53968534F4B94BCB405752E7B6A0BC4BFD243E9F7DC82DB48F97A844BCE936015A49CD7641757E614427C9F31FECDEBACF1C784FA2F75AAF2CFB410579F63E5783CF425A1FFEBC9263F791332CDA4602AF6287148EDD77D3D0B6F7A7413B892FCCF511E9B94AB542F645A79D856B08383EFDD7F6F29B527F5D08E963F0FC1449F7F4D66C136521383EFDD7F6F29B523DA0CCF9D693CBAB5915E78FD6CFBAFED5C1FFA3B9CA3D4C5CA2588F8B71C5F5F905CA4F98B0BB99798F369872920033F3901225F879EEDB5BB8DD41D80C2DEE3F2EED85D3ABCAE4C739A2C5173A53645F9186D87DAB8E92F7AAA8B58919DDA9F7DF0EC6AE5BB8EB369CFD6F0B79D0D6FAA3BD821986B514EF6653F05F710A028342BCAAFA6B219FAF2CFB410579F63E62B7E58FDABDAF5D9D1FD3EA16C98800C78D792C0FB84024A5DE0CE3F31A824EA026C8D4A0163DFB047B757030F65930E37504FC9082D0E85A90D126F3278A8ED85BC794C98F079AF8CBB2F886EAB98AC6C59F2190F307849AB602B0558084273C4740121A74E18814B0BBDB70EACE148EB1F09D936A3BBA990DE9B1E747C1D7B744A32EEFB63B2DA80D312BF734AAD0EA4D011CB998BE587F527BC2CD236095F1E4789DF7D5C39D7D5E4162CC8F4D4485241416BE4CB3E7528010096FE90406B5D5968AB9AB18832F1037D17E45205762B7E58FDABDAF5DB96C9DECA2E9F968AEFA5FC0FF425226803B01486DB7A62F808A578CBF3C0D824896F5EA1856DB28AF2CFB410579F63ED97C3CAAE04D0AB1CAC3FBCD9DA21F4F8D6217BEE89975110A4261235614B8E088C15957F6FEF65A,USERID:35}";
			String returnJson = service.getColltnAndPmtServiceImplPort().realTmPmt(JSON.toJSONString(sendMsgBean));
			//String returnJson = service.getColltnAndPmtServiceImplPort().realTmColltn(tempMsg);
			log.info("receive msg:"+returnJson);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}




	/**
	 *
	 * @param serialNum
	 * @return
	 */
	@SuppressWarnings("unused")
	@Override
	public ResultBean queryRealInsteadPay(String serialNum) {
		try {
			RealtmcolltnQueryRequestBean requestBean = new RealtmcolltnQueryRequestBean();
			requestBean.setSerialNum(serialNum);
			requestBean.setUserId("33");
			ColltnAndPmtService service = new ColltnAndPmtServiceLocator();
			SendMsgBean sendMsgBean = encrypt(JSON.toJSONString(requestBean));
			String returnJson = service.getColltnAndPmtServiceImplPort().findRealTmPmt(JSON.toJSONString(sendMsgBean));
			log.info("receive msg:"+returnJson);
			RealtmcolltnQueryResponseBean responseBean = JSON.parseObject(returnJson, RealtmcolltnQueryResponseBean.class);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}




	/**
	 *
	 * @param requestBean
	 * @return
	 */
	@Override
	public ResultBean batchInsteadPay(BtchpmtRequestBean requestBean) {
		ResultBean resultBean = null;
		try {
			ColltnAndPmtService service = new ColltnAndPmtServiceLocator();
			SendMsgBean sendMsgBean = encrypt(JSON.toJSONString(requestBean));
			String returnJson = service.getColltnAndPmtServiceImplPort().btchPmt(JSON.toJSONString(sendMsgBean));
			log.info("receive msg:"+returnJson);
			BtchpmtResponseBean responseBean = JSON.parseObject(returnJson, BtchpmtResponseBean.class);
			resultBean = new ResultBean(responseBean);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultBean;
	}




	/**
	 *
	 * @param serialNum
	 * @return
	 */
	@Override
	public ResultBean queryBatchInsteadPay(String serialNum) {
		ResultBean resultBean = null;
		try {
			RealtmcolltnQueryRequestBean requestBean = new RealtmcolltnQueryRequestBean();
			requestBean.setSerialNum(serialNum);
			requestBean.setUserId("33");
			ColltnAndPmtService service = new ColltnAndPmtServiceLocator();
			SendMsgBean sendMsgBean = encrypt(JSON.toJSONString(requestBean));
			String returnJson = service.getColltnAndPmtServiceImplPort().findBtchPmt(JSON.toJSONString(sendMsgBean));
			log.info("receive msg:"+returnJson);
			BtchpmtQueryResponseBean responseBean = JSON.parseObject(returnJson, BtchpmtQueryResponseBean.class);
			try {
				List<?> resultList = JSON.parseObject(responseBean.getResult(), List.class);
				resultBean = new ResultBean(resultList);
				resultBean.setResultBool(true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resultBean = new ResultBean("waiting", "no data");
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultBean;
	}




	/**
	 *
	 * @param requestBean
	 * @return
	 */
	@Override
	public ResultBean batchCollecting(BtchcolltnRequestBean requestBean) {
		// TODO Auto-generated method stub
		ResultBean resultBean = null;
		try {
			ColltnAndPmtService service = new ColltnAndPmtServiceLocator();
			SendMsgBean sendMsgBean = encrypt(JSON.toJSONString(requestBean));
			String returnJson = service.getColltnAndPmtServiceImplPort().btchColltn(JSON.toJSONString(sendMsgBean));
			log.info("receive msg:"+returnJson);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultBean;
	}




	/**
	 *
	 * @param serialNum
	 * @return
	 */
	@Override
	public ResultBean queryBatchCollection(String serialNum) {
		ResultBean resultBean = null;
		try {
			RealtmcolltnQueryRequestBean requestBean = new RealtmcolltnQueryRequestBean();
			requestBean.setSerialNum(serialNum);
			requestBean.setUserId("33");
			ColltnAndPmtService service = new ColltnAndPmtServiceLocator();
			SendMsgBean sendMsgBean = encrypt(JSON.toJSONString(requestBean));
			String returnJson = service.getColltnAndPmtServiceImplPort().findBtchColltn(JSON.toJSONString(sendMsgBean));
			log.info("receive msg:"+returnJson);
			BtchpmtQueryResponseBean responseBean = JSON.parseObject(returnJson, BtchpmtQueryResponseBean.class);
			try {
				List<?> resultList = JSON.parseObject(responseBean.getResult(), List.class);
				resultBean = new ResultBean(resultList);
				resultBean.setResultBool(true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resultBean = new ResultBean("waiting", "no data");
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultBean;
	}
	
	

	
}
