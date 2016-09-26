package com.zlebank.zplatform.trade.bean.enums;

public enum CertifTypeEnmu {
    //对私
    /** 01：身份证    ZR01
    02：军官证   ZR04
    03：护照       ZR13
    04：回乡证 
    05：台胞证   ZR11
    06：警官证  ZR05*/
    //对公
    /**
     *  ZC01 组织机构代码证号 
        ZC02    营业执照号码
        ZC03    登记证书
        ZC04    国税登记证号码
        ZC05    地税登记证号码
        ZC06    开户许可证
        ZC07    事业单位编号
        ZC08    其他证件
        ZC09    金融许可证编号
        
        08  组织机构代码证号 
                    　   09  营业执照号码
                    　   10  登记证书
                    　   11  国税登记证号码
                    　   12  地税登记证号码
                    　   13  开户许可证
        14  事业单位编号
                    　   15  其他证件
                    　   16  金融许可证编号
     */
    
    /**身份证**/
    IdCard("01","ZR01"),
    /**军官证  **/
    Officer("02","ZR04"),
    /**护照  **/
    Passport("03","ZR13"),
    /**台胞证 */
    TaiwanCompatriots("05","ZR11"),
    /**警官证*/
    PoliceOfficer("06","ZR05"), 
    //组织机构代码证号 
    Organization("08","ZC01"),
    //营业执照号码
    BusinessLicense("09","ZC02"), 
    //登记证书
    Registration("10","ZC03"),
    //国税登记证号码
    NationalTax("11","ZC04"),
    //地税登记证号码
    LocalTaxRegistration("12","ZC05"),
    //开户许可证
    OpeningPermit("13","ZC06"),
    //事业单位编号
    BusinessUnit("14","ZC07"),
    //其他
    Other("15","ZC08"),
    //金融许可证编号
    FinancialLicense("16","ZC09"),
    /**未知状态**/
    UNKNOW("99","99");

    private String code;
    private String cmbcCode;
    private CertifTypeEnmu(String code,String cmbcCode){
        this.code = code;
        this.cmbcCode = cmbcCode;
    }
    
    public static CertifTypeEnmu fromValue(String value) {
        for(CertifTypeEnmu status:values()){
            if(status.code.equals(value)){
                return status;
            }
        }
        return UNKNOW;
    }
    
    public static CertifTypeEnmu fromCmbcCode(String value) {
        for(CertifTypeEnmu status:values()){
            if(status.cmbcCode.equals(value)){
                return status;
            }
        }
        return UNKNOW;
    }
    public String getCode(){
        return code;
    }
    public String getCmbcCode(){
        return cmbcCode;
    }
    
    
}
