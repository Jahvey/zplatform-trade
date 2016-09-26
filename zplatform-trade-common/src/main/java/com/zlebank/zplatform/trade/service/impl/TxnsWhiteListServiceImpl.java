package com.zlebank.zplatform.trade.service.impl;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zlebank.zplatform.trade.dao.ITxnsWhiteListDAO;
import com.zlebank.zplatform.trade.model.TxnsWhiteListModel;
import com.zlebank.zplatform.trade.service.ITxnsWhiteListService;
import com.zlebank.zplatform.trade.service.base.BaseServiceImpl;
/**
 * 
 * 白名单t_txns_white_list service类
 *
 * @author guojia
 * @version
 * @date 2015年12月11日 上午11:12:53
 * @since
 */
@Service("txnsWhiteListService")
public class TxnsWhiteListServiceImpl extends BaseServiceImpl<TxnsWhiteListModel, Long> implements ITxnsWhiteListService{

    @Autowired
    private ITxnsWhiteListDAO txnsWhiteListDAO;
    @Override
    public Session getSession() {
        return txnsWhiteListDAO.getSession();
    }
    /**
     * 保存白名单数据
     * @param whiteList
     */
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void saveWhiteList(TxnsWhiteListModel whiteList){
        String queryString = " from TxnsWhiteListModel where bankaccno=? and bankaccname=? and certno=? and mobile=? ";
        TxnsWhiteListModel model = super.getUniqueByHQL(queryString, new Object[]{whiteList.getBankaccno(),whiteList.getBankaccname(),whiteList.getCertno(),whiteList.getMobile()});
        if(model==null){
            super.save(whiteList);
        }
        
    }
    
    /**
     * 通过卡信息获取白名单数据
     * @param whiteList
     * @return
     */
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public TxnsWhiteListModel getByCardInfo(TxnsWhiteListModel whiteList){
        String queryString = " from TxnsWhiteListModel where bankaccno = ? and bankaccname = ? and certno = ? and mobile = ? ";
        return super.getUniqueByHQL(queryString, new Object[]{whiteList.getBankaccno(),whiteList.getBankaccname(),whiteList.getCertno(),whiteList.getMobile()});
    }
}
