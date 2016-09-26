/* 
 * UseBankResponse.java  
 * 
 * version TODO
 *
 * 2015年10月9日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.message;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

/**
 * 可受理银行卡信息【应答报文】
 *
 * @author yangpeng
 * @version
 * @date 2015年10月9日 下午2:09:38
 * @since 
 */
public class QueryUsableBankCard_Response  extends BaseMessage {
    /**虚拟代码**/
    private String virtualId;
    /**保留域**/
    private List<Bank> reserved;
    /**响应码**/
    private String respCode;
    /**应答信息**/
    private String respMsg;
    public String getVirtualId() {
        return virtualId;
    }

 
    public String getRespCode() {
        return respCode;
    }
    public String getRespMsg() {
        return respMsg;
    }
    public void setVirtualId(String virtualId) {
        this.virtualId = virtualId;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }
    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }
    
    public List<Bank> getReserved() {
        return reserved;
    }
    public void setReserved(List<Bank> reserved) {
        this.reserved = reserved;
    }
    public static void main(String[] args) {
        QueryUsableBankCard_Response response = new QueryUsableBankCard_Response();
        List<Bank> banks=new ArrayList<Bank>();
        banks.add(new Bank());
        banks.add(new Bank());
        response.setReserved(banks);
        JSONObject jsonData = JSONObject.fromObject(response);
        System.out.println(jsonData);
    }
}
