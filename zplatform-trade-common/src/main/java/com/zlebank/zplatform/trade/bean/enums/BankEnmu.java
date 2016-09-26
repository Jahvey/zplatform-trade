package com.zlebank.zplatform.trade.bean.enums;

public enum BankEnmu {
    /*
                工商银行    01020000
                农业银行    01030000
                建设银行    01050000
                中国银行    01040000
                邮储银行    01000000
                招商银行    03080000
                光大银行    03030000
                广发银行    03060000
                华夏银行    03040000
                兴业银行    03090000
                中信银行    03020000
                平安银行    03070000
                交通银行    03010000
                浦发银行    03100000
                兰州银行    04470000
     */
    ICBC("01020000","工商银行"),
    ABC("01030000","农业银行"),
    CCB("01050000","建设银行"),
    CHINA("01040000","中国银行"),
    PSBC("01000000","邮储银行"),
    CMBC("03080000","招商银行"),
    CEB("03030000","光大银行"),
    CGBC("03060000","广发银行"),
    HXB("03040000","华夏银行"),
    CIB("03090000","兴业银行"),
    ECITIC("03020000","中信银行"),
    PINGAN("03070000","平安银行"),
    COMM("03010000","交通银行"),
    SPDB("03100000","浦发银行"),
    LZ("04470000","兰州银行"),
    UNKNOW("","");//未知
    private String bankcode;
    private String bankname;
    private BankEnmu(String bankcode,String bankname){
        this.bankcode = bankcode;
        this.bankname = bankname;
    }
    
    public static BankEnmu fromValue(String value) {
        for(BankEnmu busi:values()){
            if(busi.bankcode.equals(value)){
                return busi;
            }
        }
        return UNKNOW;
    }
    
    public String getBankCode(){
        return bankcode;
    }
    
    public String getBankName(){
        return bankname;
    }
    
}
