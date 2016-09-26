package com.zlebank.zplatform.trade.chanpay.enums;

import com.zlebank.zplatform.trade.bean.enums.CertifTypeEnmu;

public enum ChanPayCertifTypeEnmu {
	//对私
    /** 
    01	身份证
	02	军官证
	03	护照
	04	回乡证
	05	台胞证
	06	警官证
	07	士兵证
	99	其他
    */
    //对公
    /**
     0：身份证,
     1: 户口簿，
     2：护照,
     3.军官证,
     4.士兵证，
     5. 港澳居民来往内地通行证,
     6. 台湾同胞来往内地通行证,
     7. 临时身份证,
     8. 外国人居留证,
     9. 警官证, 
     X.其他证件
     */
    
    /**身份证**/
    IdCard("01","0"),
    /**军官证  **/
    Officer("02","3"),
    /**护照  **/
    Passport("03","2"),
    /**台胞证 */
    TaiwanCompatriots("05","6"),
    /**警官证*/
    PoliceOfficer("06","9"), 
    soldier("07","4"),
    //其他
    Other("99","X"),
    /**未知状态**/
    UNKNOW("99","99");

    private String code;
    private String chanpayCode;
    private ChanPayCertifTypeEnmu(String code,String chanpayCode){
        this.code = code;
        this.chanpayCode = chanpayCode;
    }
    
    public static ChanPayCertifTypeEnmu fromValue(String value) {
        for(ChanPayCertifTypeEnmu status:values()){
            if(status.code.equals(value)){
                return status;
            }
        }
        return UNKNOW;
    }
    
    public static ChanPayCertifTypeEnmu fromCmbcCode(String value) {
        for(ChanPayCertifTypeEnmu status:values()){
            if(status.chanpayCode.equals(value)){
                return status;
            }
        }
        return UNKNOW;
    }
    public String getCode(){
        return code;
    }
    public String getChanpyaCode(){
        return chanpayCode;
    }
    
    
}
