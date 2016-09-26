/* 
 * BiztypeEnum.java  
 * 
 * version TODO
 *
 * 2015年11月25日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.trade.bean.enums;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月25日 上午11:52:45
 * @since 
 */
public enum BiztypeEnum {
    
    
    HouseholdElectricity("10101"),//家用电费
    ProductioElectricityn("10102"),//生产用电费
    WaterCharge("10201"),//用水费
    DrainageCharge("10202"),//排水费
    DrinkingWaterCharge("10203"),//直饮水费
    SewageTreatmentCharge("10204"),//污水处理费
    HeatingCharge("10205"),//暖气费
    GasCharge("10300"),//煤气费
    PipeGasCharge("10301"),//管道煤气费
    TelephoneCharge("10400"),//电话费
    LocalTelephoneCharge("10401"),//市内电话费
    LongDistanceTelephoneCharge("10402"),//长途电话费
    MobileTelephoneCharge("10403"),//移动电话费
    TelephoneInstallationCharge("10404"),//电话初装费
    IPTelephoneCharge("10405"),//IP电话费
    CommunicationCharge("10500"),//通讯费
    DataCommunicationCharge("10501"),//数据通讯费
    LineRental("10502"),//线路月租费
    AgencyMaintenanceCharge("10503"),//代维费
    NetworkUsageCharge("10504"),//网络使用费
    InformationServiceCharge("10505"),//信息服务费
    MobileElectronicCommerce("10506"),//移动电子商务费
    GatewayServiceCharge("10507"),//网关业务费
    MobilePhoneCharge("10508"),//手机话费
    InsuranceCharge("10600"),//保险费
    OtherCharge("14900"),
    UNKNOW("");
    /*10601//续期寿险费
    10602//社会保险费
    10603//养老保险费
    10604//医疗保险费
    10605//车辆保险费
    10700//房屋管理费
    10701//房屋租赁费
    10702//租赁服务费
    10703//物业管理费
    10704//清洁费
    10705//保安服务费
    10706//电梯维护保养费
    10707//绿化费
    10708//停车费
    10800//代理服务费
    10801//押运服务费
    10802//票据传递费
    10803//代理记账服务费
    10900//学教费
    10901//报考费
    10902//学杂费
    10903//保教费
    11000//有线电视费
    11001//有线电视租赁费
    11002//移动电视费
    11100//机构管理费用
    11101//工商行政管理费
    11102//商检费
    14001//基金
    14002//资管
    14802//加油卡费
    14900//其他费用
    14901//还贷
    14902//货款
*/

private String code;

private BiztypeEnum(String code){
    this.code = code;
}

public static BiztypeEnum fromValue(String value) {
    for(BiztypeEnum status:values()){
        if(status.code.equals(value)){
            return status;
        }
    }
    return UNKNOW;
}

public String getCode(){
    return code;
}
}
