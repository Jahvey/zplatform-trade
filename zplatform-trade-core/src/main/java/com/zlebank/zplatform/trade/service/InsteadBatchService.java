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

import java.util.List;

import com.zlebank.zplatform.commons.service.IBasePageService;
import com.zlebank.zplatform.trade.bean.InsteadPayBatchBean;
import com.zlebank.zplatform.trade.bean.InsteadPayBatchQuery;
import com.zlebank.zplatform.trade.exception.FailToInsertAccEntryException;
import com.zlebank.zplatform.trade.exception.InvalidAuditDataException;
import com.zlebank.zplatform.trade.exception.RecordsAlreadyExistsException;

/**
 * 代付业务
 *
 * @author Luxiaoshuai
 * @version
 * @date 2015年11月25日 上午10:47:55
 * @since 
 */
public interface InsteadBatchService  extends IBasePageService<InsteadPayBatchQuery, InsteadPayBatchBean>{

    /**
     * 通过批次号进行批量审核（将该批次号下的所有未审核的明细改为已审核状态）
     * @param batchIds
     * @throws RecordsAlreadyExistsException 
     * @throws FailToInsertAccEntryException 
     */
  void batchAudit(List<Long> batchIds, boolean pass) throws RecordsAlreadyExistsException, FailToInsertAccEntryException;
  
  /**
   * 多条明细的审核
   * @param detailIds
 * @throws RecordsAlreadyExistsException 
 * @throws FailToInsertAccEntryException 
 * @throws InvalidAuditDataException 
   */
  public void detailsAudit(List<Long> detailIds, boolean pass) throws RecordsAlreadyExistsException, FailToInsertAccEntryException, InvalidAuditDataException;
    
}
