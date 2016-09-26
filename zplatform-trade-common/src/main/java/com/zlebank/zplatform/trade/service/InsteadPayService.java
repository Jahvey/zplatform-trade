/* 
 * InsteadPayService.java  
 * 
 * version TODO
 *
 * 2015年11月25日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.service;

import com.zlebank.zplatform.commons.service.IBasePageService;
import com.zlebank.zplatform.trade.bean.InsteadPayDetailBean;
import com.zlebank.zplatform.trade.bean.InsteadPayDetailQuery;
import com.zlebank.zplatform.trade.bean.InsteadPayInterfaceParamBean;
import com.zlebank.zplatform.trade.bean.enums.InsteadPayImportTypeEnum;
import com.zlebank.zplatform.trade.exception.BalanceNotEnoughException;
import com.zlebank.zplatform.trade.exception.DuplicateOrderIdException;
import com.zlebank.zplatform.trade.exception.FailToGetAccountInfoException;
import com.zlebank.zplatform.trade.exception.FailToInsertAccEntryException;
import com.zlebank.zplatform.trade.exception.FailToInsertFeeException;
import com.zlebank.zplatform.trade.exception.InconsistentMerchNoException;
import com.zlebank.zplatform.trade.exception.InvalidCardException;
import com.zlebank.zplatform.trade.exception.InvalidInsteadPayDataException;
import com.zlebank.zplatform.trade.exception.MerchWhiteListCheckFailException;
import com.zlebank.zplatform.trade.exception.NotInsteadPayWorkTimeException;
import com.zlebank.zplatform.trade.exception.RealNameCheckFailException;
import com.zlebank.zplatform.trade.insteadPay.message.InsteadPayQuery_Request;
import com.zlebank.zplatform.trade.insteadPay.message.InsteadPayQuery_Response;
import com.zlebank.zplatform.trade.insteadPay.message.InsteadPay_Request;

/**
 * 代付业务
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月25日 上午10:47:55
 * @since 
 */
public interface InsteadPayService  extends IBasePageService<InsteadPayDetailQuery, InsteadPayDetailBean>{

    /**
     *  代付处理
     * @param request 
     * @return 错误代码，如果没有错误则返回NULL
     * @throws NotInsteadPayWorkTimeException 
     * @throws FailToGetAccountInfoException 
     * @throws BalanceNotEnoughException 
     * @throws DuplicateOrderIdException 
     * @throws InvalidCardException 
     * @throws FailToInsertAccEntryException 
     * @throws MerchWhiteListCheckFailException 
     * @throws RealNameCheckFailException 
     * @throws InconsistentMerchNoException 
     * @throws InvalidInsteadPayDataException 
     */
    public void insteadPay(InsteadPay_Request request, InsteadPayImportTypeEnum type,InsteadPayInterfaceParamBean param) throws NotInsteadPayWorkTimeException, FailToGetAccountInfoException, BalanceNotEnoughException, DuplicateOrderIdException, InvalidCardException, FailToInsertAccEntryException, MerchWhiteListCheckFailException, FailToInsertFeeException, RealNameCheckFailException, InconsistentMerchNoException, InvalidInsteadPayDataException;
    /**
     * 代付状态查询处理
     * @param requestBean
     * @param responseBean 
     */
    public void insteadPayQuery(InsteadPayQuery_Request requestBean, InsteadPayQuery_Response responseBean);
    

    

}
